package com.yuanlrc.campus_market.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.entity.common.task_WantedGoods;
import com.yuanlrc.campus_market.service.common.task_WantedGoodsService;

/**
 * 求购物品控制器
 * @author Administrator
 *
 */
@RequestMapping("/home/task_wanted_goods")
@Controller
public class task_HomeWantedGoodsController {

	@Autowired
	private task_WantedGoodsService task_wantedGoodsService;
	
	/**
	 * 求购物品列表页面
	 * @param model
	 * @param pageBean
	 * @param WantedGoods
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model,PageBean<task_WantedGoods> pageBean,task_WantedGoods task_WantedGoods){
		System.out.println("访问/home/task_wanted_goods/list成功");
		System.out.println(model);
		System.out.println(task_WantedGoods);
		model.addAttribute("pageBean", task_wantedGoodsService.findlist(pageBean, task_WantedGoods));
		return "访问成功";
	}
}
