package com.poscodx.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:^(?!assets|admin).*$}")
public class BlogController {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private FileUploadService fileuploadService;
	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
		@PathVariable("id") String id,
		@PathVariable("pathNo1") Optional<Long> pathNo1,
		@PathVariable("pathNo2") Optional<Long> pathNo2,
		Model model) {
		
		BlogVo blogVo = blogService.getBlogById(id);
		model.addAttribute("blogVo",blogVo);
		
		List<Map<String, Object>> cVo = blogService.getCategoryById(id);
		model.addAttribute("cList",cVo);
		
		Map<String,Object> map = new HashMap();
		map.put("blogId", id);
		
		// categoryNo X, postNo X
		if(pathNo1.isEmpty()&&pathNo2.isEmpty()){
			List<PostVo> pList=blogService.getPost(map);
			model.addAttribute("pList", pList);
		}
		
		// categoryNo O, postNo X
		else if(pathNo1.isPresent()&&pathNo2.isEmpty()) {
			map.put("categoryNo", pathNo1.get());
			List<PostVo> pList=blogService.getPost(map);
			model.addAttribute("pList", pList);
		}
		
		// categoryNo O, postNo O
		else {
			map.put("categoryNo", pathNo1.get());
			map.put("postNo", pathNo2.get());
			List<PostVo> pList=blogService.getPost(map);
			model.addAttribute("pList", pList);
		}
		
		return "blog/main";
	}
	
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(
		@AuthUser UserVo authUser,	
		@PathVariable("id") String blogId, 
		Model model) {
		
		BlogVo blogVo = blogService.getBlogById(blogId);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/admin-basic";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic/update",method=RequestMethod.POST)
	public String adminBasic(
		@AuthUser UserVo authUser,
		Optional<BlogVo> blogVo, 
		Optional<MultipartFile> file,
		Model model) {
		
		if(file.isEmpty() && blogVo.get().getTitle() == null) {
			model.addAttribute("message", "변경된 내역이 없습니다.");
			
			return "error/404";
		}
		
		String profile = fileuploadService.restore(file.get());
		blogVo.get().setUserId(authUser.getId());
		
		if(profile != null) {
			blogVo.get().setLogo(profile);
		}
		
		blogService.update(blogVo.get());
		model.addAttribute("blogVo", blogVo.get());

		return "blog/admin-basic";
	}
	
	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(
		@AuthUser UserVo authUser,	
		@PathVariable("id") String blogId, 
		Model model) {
		
		BlogVo blogVo = blogService.getBlogById(blogId);
		model.addAttribute("blogVo",blogVo);
	
		List<Map<String, Object>> cVo = blogService.getCategoryById(authUser.getId());
		model.addAttribute("list",cVo);
		
		return "blog/admin-category";
	}
	
	@Auth
	@RequestMapping("/admin/category_delete/{no}")
	public String deleteCategory(
		@PathVariable("no") Long no,
		@AuthUser UserVo authUser) {
	
		int count = blogService.categoryPostCount(no);
		if(count != 0) {
			blogService.changeCategoryPosts(no,authUser.getId());
			blogService.deleteCategory(no);
		} else {			
			blogService.deleteCategory(no);
		}
		
		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/category/add",method=RequestMethod.POST)
	public String addCategory(
		@AuthUser UserVo authUser,	
		String name, String description,
		Model model) {
		
		CategoryVo cVo = new CategoryVo();
		cVo.setName(name);
		cVo.setBlogId(authUser.getId());
		cVo.setDescription(description);
		blogService.insertCategory(cVo);
		
		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(
		@AuthUser UserVo authUser,
		@PathVariable("id") String blogId, 
		Model model) {
		
		BlogVo blogVo = blogService.getBlogById(blogId);
		model.addAttribute("blogVo", blogVo);
		
		List<Map<String, Object>> cVo = blogService.getCategoryById(authUser.getId());
		model.addAttribute("list", cVo);
		
		return "blog/admin-write";
	}
	
	@Auth
	@RequestMapping(value="/admin/write",method=RequestMethod.POST)
	public String addWrite(
		@AuthUser UserVo authUser,	
		String title, Long category, String content) {
		
		PostVo pVo = new PostVo();
		pVo.setCategoryNo(category);
		pVo.setContents(content);
		pVo.setTitle(title);
		blogService.insertPost(pVo);

		return "redirect:/" + authUser.getId();
	}
}