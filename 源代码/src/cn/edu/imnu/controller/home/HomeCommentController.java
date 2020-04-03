package cn.edu.imnu.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.imnu.entity.common.Account;
import cn.edu.imnu.entity.common.Comment;
import cn.edu.imnu.entity.common.Product;
import cn.edu.imnu.service.common.AccountService;
import cn.edu.imnu.service.common.CommentService;
import cn.edu.imnu.service.common.ProductCategoryService;
import cn.edu.imnu.service.common.ProductService;
import cn.edu.imnu.util.MenuUtil;

/**
 * ǰ̨���ۿ�����
 * @author Administrator
 *
 */
@RequestMapping("/comment")
@Controller
public class HomeCommentController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CommentService commentService;
	/**
	 * �����б�ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ModelAndView index(ModelAndView model,Integer page,HttpServletRequest request){
		model.addObject("productCategoryList", MenuUtil.getTreeCategory(productCategoryService.findList(new HashMap<String, Object>())));
		model.addObject("allCategoryId","shop_hd_menu_all_category");
		Account onlineAccount = (Account)request.getSession().getAttribute("account");
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(page == null || page.intValue() <= 0){
			page = 1;
		}
		queryMap.put("offset", (page -1) * 10);
		queryMap.put("pageSize", 10);
		queryMap.put("userId", onlineAccount.getId());
		queryMap.put("orderBy", "createTime");
		model.addObject("commentList", commentService.findList(queryMap));
		model.addObject("currentUser", "current_");
		model.addObject("page", page);
		model.setViewName("home/comment/list");
		return model;
	}
	
	
	/**
	 * �������
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Comment comment,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		Account onlineAccount = (Account)request.getSession().getAttribute("account");
		ret.put("type", "error");
		if(comment == null){
			ret.put("msg", "����д��ȷ��������Ϣ");
			return ret;
		}
		if(StringUtils.isEmpty(comment.getContent())){
			ret.put("msg", "����д�������ݣ�");
			return ret;
		}
		comment.setCreateTime(new Date());
		comment.setUserId(onlineAccount.getId());
		if(commentService.add(comment) <= 0){
			ret.put("msg", "����ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		//������Ʒ��������
		Product product = productService.findById(comment.getProductId());
		product.setCommentNum(product.getCommentNum()+1);
		productService.updateNum(product);
		ret.put("type", "success");
		return ret;
	}
	
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(Long id){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("type", "error");
		if(id == null){
			ret.put("msg", "��ѡ��Ҫɾ��������");
			return ret;
		}
		Comment comment = commentService.findById(id);
		if(comment == null){
			ret.put("msg", "���۲�����!");
			return ret;
		}
		if(commentService.delete(id) <= 0){
			ret.put("msg", "ɾ����������ϵ����Ա!");
			return ret;
		}
		//������Ʒ��������
		Product product = productService.findById(comment.getProductId());
		product.setCommentNum(product.getCommentNum()-1);
		productService.updateNum(product);
		ret.put("type", "success");
		return ret;
	}
}
