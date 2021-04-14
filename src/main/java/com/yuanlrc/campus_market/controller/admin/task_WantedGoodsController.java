package com.yuanlrc.campus_market.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.bean.Result;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.entity.common.task_WantedGoods;
import com.yuanlrc.campus_market.service.common.StudentService;
import com.yuanlrc.campus_market.service.common.task_WantedGoodsService;

/**
 * 后台求购物品管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/task_wangted_goods")
@Controller
public class task_WantedGoodsController {

	@Autowired
	private task_WantedGoodsService task_wantedGoodsService;
	@Autowired
	private StudentService studentService;
	
	/**
	 * 求购物品管理列表页面
	 * @param name
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(task_WantedGoods task_wantedGoods,PageBean<task_WantedGoods> pageBean,Model model){
		if(task_wantedGoods.getStudent() != null && task_wantedGoods.getStudent().getSn() != null){
			Student student = studentService.findBySn(task_wantedGoods.getStudent().getSn());
			if(student != null){
				task_wantedGoods.setStudent(student);
			}
		}
		model.addAttribute("title", "求购物品列表");
		model.addAttribute("name", task_wantedGoods.getName());
		model.addAttribute("sn", task_wantedGoods.getStudent() == null ? null : task_wantedGoods.getStudent().getSn());
		model.addAttribute("pageBean", task_wantedGoodsService.findWantedGoodslist(pageBean, task_wantedGoods));
		return "admin/task_wanted_goods/list";
	}

	
	/**
	 * 求购物品删除操作
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
		task_wantedGoodsService.delete(id);
		return Result.success(true);
	}
}
