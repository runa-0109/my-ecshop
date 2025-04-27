package com.example.demo.controller;


import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Category;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
	private final ProductService productService;
	private final CartService cartService;
	private final CategoryService categoryService;
	private final FavoriteService favoriteService;
    // 全商品を取得
	@GetMapping
	public String list(Model model) {
	    model.addAttribute("productList", productService.findAllProduct());
		model.addAttribute("categoryList", categoryService.findAllCategories());
		model.addAttribute("selectedCategoryId", null); // 未選択
		return "user/product-list";
	}
	//特定のIDの商品を取得
	@GetMapping("/{id}")
	public String detail(@PathVariable Integer id,Model model,RedirectAttributes attributes) {
		 Product product = productService.findById(id);
		if(product != null) {
			model.addAttribute("product",productService.findById(id)); 
			return "user/product-detail";
		}else {
			attributes.addFlashAttribute("errorMessage","対象のデータがありません");
			return "redirect:/product";
		}
	}
	//お気に入り商品の追加
	@PostMapping("/favorite/add/{id}")
	public String addFavorite(@PathVariable Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		if(username == null || username.equals("anonymousUser")) {
			return "redirect:/login";
		}
		favoriteService.addFavorite(username, id);
		return "redirect:/product/" + id;
	}
	//お気に入り一覧ページ
	@GetMapping("/favorite/list")
	public String showFavoriteList(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName(); 
		if(username == null || username.equals("anonymousUser")) {
			return "redirect:/login";
		}
		List<Favorite> favorites = favoriteService.getFavoriteByUserId(username);
		model.addAttribute("favorites",favorites);
		return "user/favorite-list";
	}
	@PostMapping("/favorite/delete/{id}")
	public String deleteFavorite(@PathVariable("id") Integer favoriteId,RedirectAttributes attributes) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    if (username == null || username.equals("anonymousUser")) {
	        return "redirect:/login";
	    }

	    favoriteService.removeFavorite(username,favoriteId);
	    attributes.addFlashAttribute("message","商品をお気に入りから削除しました");
	    return "redirect:/product/favorite/list";
	}

	// カートに商品を追加
	@PostMapping("/cart/add/{id}")
	public String addToCart(@PathVariable Integer id) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName(); 
	    System.out.println("ユーザー名:"+username);
	    if (username == null) {
	        return "redirect:/login"; // ログイン画面へ
	    }

	    // カートサービスを使ってDBに追加
	    cartService.addToCart(username, id);
	    return "redirect:/product/cart"; // カート一覧にリダイレクト
	}
	

	 //カートの一覧表示
	@GetMapping("/cart")
	public String viewCart(HttpSession session, Model model) {
	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 

        // DBからカート情報を取得
        List<CartItem> cartItems = cartService.getCartItems(username);
        
        // デバッグ用出力
        System.out.println("カート内の商品一覧:");
        for (CartItem item : cartItems) {
            System.out.println("商品ID: " + item.getProductId() +
                               ", 商品名: " + (item.getProduct() != null ? item.getProduct().getName() : "不明") +
                               ", 価格: " + (item.getProduct() != null ? item.getProduct().getPrice() : "不明") +
                               ", 数量: " + item.getQuantity());
        }
        // 小計と合計金額を計算
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            double itemTotal = item.getProduct().getPrice() * item.getQuantity();
            item.setSubtotal(itemTotal);  // 各商品の小計をセット
            totalAmount += itemTotal;     // 合計金額を加算
        }
	    if (cartItems.isEmpty()) {
	        model.addAttribute("message", "カートに商品を追加できません");
	    } else {
	        model.addAttribute("cartItems", cartItems);
	        model.addAttribute("totalAmount", totalAmount); // 合計金額をビューに渡す

	    }
	    return "user/cart"; // カートのページ
	}
	//特定の商品の削除
	@PostMapping("/cart/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
	    cartService.removeFromCart(id); // カートから商品を削除
	    attributes.addFlashAttribute("message", "商品がカートから削除されました");
	    return "redirect:/product/cart";
	}
	//カート内すべての商品を削除
    @PostMapping("/cart/delete/all")
    public String clearCart(HttpSession session) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();        
        // usernameがnullの場合のチェック
        if (username != null) {
            // cartServiceのclearCartメソッドを呼び出してカートを削除
            cartService.clearCart(username);
        }
        
        return "redirect:/product/cart"; // カートページにリダイレクト
    }
    //cart内の商品数を変更
    @PostMapping("/cart/update")
    public String updateCartQuantity(@RequestParam Integer productId,
            						@RequestParam Integer quantity,
            						@RequestParam String action,
            						RedirectAttributes attributes) {
       	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  
        // 2. ボタンに応じて +1 / -1
        if ("plus".equals(action))  quantity++;
        if ("minus".equals(action)) quantity--;

        // 3. Service に頼む
        String error = cartService.changeQuantity(username, productId, quantity);

        // 4. エラーが返ってきたら画面に渡す
        if (error != null) {
            attributes.addFlashAttribute("errorMessage", error);
        }
    	return "redirect:/product/cart";
    }
    //カテゴリ検索
    @GetMapping("/category")
    public String listByCategory(@RequestParam(value = "categoryId",required = false) Integer id, Model model) {
    	List<Product> list = (id == null) ? productService.findAllProduct() : productService.findByCategoryId(id);
        List<Category> categoryList = categoryService.findAllCategories();
    	System.out.println("選択されたカテゴリID: " + id);    
    	model.addAttribute("productList",list);
        model.addAttribute("categoryList", categoryList); // セレクト用
        model.addAttribute("selectedCategoryId", id);
    	return "user/product-list";
    }
 

}
