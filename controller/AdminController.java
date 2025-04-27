package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.form.ProductForm;
import com.example.demo.helper.ProductHelper;
import com.example.demo.service.CategoryService;
import com.example.demo.service.InquiryService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	private final ProductService productService;
	private final CategoryService categoryService;
	private final OrderService orderService;
	private final InquiryService inquiryService;
		//管理者用ダッシュボード
		@GetMapping("/dashboard")
		public String getAdminDashboard() {
			return "admin/dashboard";
		}

		// 商品一覧
	    @GetMapping("/product")
	    public String list(Model model) {
	        model.addAttribute("products", productService.findAllProduct());
	        return "admin/product-list"; // admin用の一覧ページ
	    }
	  //特定のIDの商品を取得
		@GetMapping("/product/{id}")
		public String detail(@PathVariable Integer id,Model model,RedirectAttributes attributes) {
			 Product product = productService.findById(id);
			if(product != null) {
				model.addAttribute("product",product); {
				}return "product/detail";
			}else {
				attributes.addFlashAttribute("errorMessage","対象のデータがありません");
				return "redirect:/product";
			}
		}
		//登録画面表示
		@GetMapping("/product/add")
		public String showCreateForm(Model model) {
		    ProductForm form = new ProductForm();
		    form.setIsNew(true);
		    model.addAttribute("productForm", form);
		    model.addAttribute("categoryList", categoryService.findAllCategories());
		    return "admin/product-form";
		}
		//登録処理(post)
		@PostMapping("/product/add")
		public String createProduct(@ModelAttribute("productForm") @Valid ProductForm form,
		                            BindingResult bindingResult,
		                            Model model) {
		    if (bindingResult.hasErrors()) {
		        form.setIsNew(true);
		        model.addAttribute("categoryList", categoryService.findAllCategories());
		        return "admin/product-form";
		    }

		    productService.insertProduct(ProductHelper.convertProduct(form));
		    return "redirect:/admin/product";
		}
		
		@PostMapping("/product/save")
		public String createProduct(@ModelAttribute("productForm") @Valid ProductForm form,
		                            BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes) {
		    if (bindingResult.hasErrors()) {
		        form.setIsNew(true);
		        model.addAttribute("categoryList", categoryService.findAllCategories());
		        return "admin/product-form";
		    }
		    MultipartFile imageFile = form.getImageFile();
		    // アップロード処理
		    if (!imageFile.isEmpty()) {
		        try {
		            // 保存先のパスを指定（static/images/ に保存）
		            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
		            Path uploadPath = Paths.get("src/main/resources/static/images/" + fileName);
		            Files.copy(imageFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

		            // ファイル名をFormにセット（DB保存用）
		            form.setImageUrl(fileName);
		        } catch (IOException e) {
		            e.printStackTrace();
		            bindingResult.rejectValue("imageUrl", null, "画像の保存に失敗しました");
		            return "admin/product-form";
		        }
		    }
		    productService.insertProduct(ProductHelper.convertProduct(form));
		    redirectAttributes.addFlashAttribute("message", "商品が登録されました");
		    return "redirect:/admin/product";
		}

		//編集画面表示
		@GetMapping("/product/edit/{id}")
		public String showEditForm(@PathVariable("id") Integer id, Model model) {
		    Product product = productService.findById(id);
		    ProductForm form = ProductHelper.convertProductForm(product);
		    form.setIsNew(false);
		    model.addAttribute("productForm", form);
		    model.addAttribute("categoryList", categoryService.findAllCategories());
		    return "admin/product-form";
		}
		@PostMapping("/product/update")
		public String updateProduct(@ModelAttribute("productForm") @Valid ProductForm form,
		                            BindingResult bindingResult,
		                            Model model,
		                            RedirectAttributes redirectAttributes) {
		    if (bindingResult.hasErrors()) {
		        form.setIsNew(false);
		        model.addAttribute("categoryList", categoryService.findAllCategories());
		        return "admin/product-form";
		    }

		    productService.updateProduct(ProductHelper.convertProduct(form));
		    redirectAttributes.addFlashAttribute("message", "商品が更新されました");
		    return "redirect:/admin/product";
		}

		
		
		//商品を削除します
		@GetMapping("/product/delete/{id}")
		public String delete(@PathVariable Integer id,RedirectAttributes attributes) {
			productService.deleteProduct(id);
			attributes.addFlashAttribute("message","商品が削除されました");			return "redirect:/admin/product";
		}
		//注文内容の一覧を表示する
		@GetMapping("/orders")
		public String showOrderList(Model model) {
			List<Order> orders = orderService.findAllOrders();
			System.out.println("=== 注文一覧の確認 ===");
		    System.out.println("注文件数: " + orders.size());
		    for (Order order : orders) {
		        System.out.println("注文ID: " + order.getId());
		        System.out.println("ユーザー名: " + order.getUsername());
		        System.out.println("注文日: " + order.getOrderDate());
		        System.out.println("合計: " + order.getTotalPrice());
		        System.out.println("---------------");
		    }

			model.addAttribute("orderList",orders);
			return "admin/order-list";
		}
		//注文内容のステータス変更
		@PostMapping("/order/{id}/status")
		public String updateOrderStatus(@PathVariable Integer id,@RequestParam("status") Integer status) {
			orderService.updateOrderStatus(id,status);
			return "redirect:/admin/orders";
		}
		//特定の注文の詳細を表示
		@GetMapping("/order/{id}")
		public String showAdminOrderDetail(@PathVariable Integer id, Model model) {
		    Order order = orderService.findOrderById(id);
		    List<OrderDetail> detailList = orderService.findOrderDetailsByOrderId(id);
		    
		    // デバッグ出力
		    System.out.println("=== 注文情報 ===");
		    System.out.println("注文ID: " + order.getId());
		    System.out.println("ユーザー名: " + order.getUsername());
		    System.out.println("注文日: " + order.getOrderDate());
		    System.out.println("合計金額: " + order.getTotalPrice());
		    System.out.println("ステータス: " + order.getStatus());
		    System.out.println("=== 配送先情報 ===");
		    System.out.println("宛名:        " + order.getRecipientName());
		    System.out.println("住所:        " + order.getAddress());
		    System.out.println("電話番号:    " + order.getPhoneNumber());
		    System.out.println("支払い方法:  " + order.getPaymentMethod());
		    System.out.println("=== 商品明細 ===");
		    for (OrderDetail detail : detailList) {
		        System.out.println("商品名: " + detail.getProductName());
		        System.out.println("単価: " + detail.getPrice());
		        System.out.println("数量: " + detail.getQuantity());
		        System.out.println("小計: " + detail.getSubtotal());
		        System.out.println("------");
		        System.out.println("=== 配送先情報 ===");
			    System.out.println("宛名:        " + detail.getRecipientName());
			    System.out.println("住所:        " + detail.getAddress());
			    System.out.println("電話番号:    " + detail.getPhoneNumber());
			    System.out.println("支払い方法:  " + detail.getPaymentMethod());
		    }

		    model.addAttribute("order", order);
		    model.addAttribute("detailList", detailList);
		    return "admin/order-detail";
		}
		//お問い合わせの一覧表示
		@GetMapping("/inquiry/list")
		public String showInquiryList(Model model) {
	        List<Inquiry> inquiryList = inquiryService.findAll();
	        model.addAttribute("inquiryList", inquiryList);
	        return "admin/inquiry-list";
	    }
		// お問い合わせ詳細表示
	    @GetMapping("/inquiry/{id}")
	    public String showInquiryDetail(@PathVariable Integer id, Model model) {
	        Inquiry inquiry = inquiryService.findById(id);
	    	if(!inquiry.getIsRead()) {
	    		inquiryService.markAsRead(id);
	    		inquiry.setIsRead(true);
	    	}
	        model.addAttribute("inquiry", inquiry);
	        return "admin/inquiry-detail"; 
	    }
	    //既読ステータス
	    @PostMapping("inquiry/{id}/read")
	    public String markAsRead(@PathVariable Integer id) {
	    	inquiryService.markAsRead(id);
	    	return "redirect:/admin/inquiry/list";
	    }
}



