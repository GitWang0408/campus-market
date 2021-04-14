package com.yuanlrc.campus_market.service.common;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
/**
 * 求购物品service
 */
import org.springframework.stereotype.Service;

import com.yuanlrc.campus_market.bean.PageBean;
import com.yuanlrc.campus_market.dao.common.task_WantedGoodsDao;
import com.yuanlrc.campus_market.entity.common.Student;
import com.yuanlrc.campus_market.entity.common.task_WantedGoods;

@Service
public class task_WantedGoodsService {

	@Autowired
	private task_WantedGoodsDao task_wantedGoodsDao;
	
	/**
	 * 求购物品信息添加/编辑（传入id则为编辑，否则是添加）
	 * @param wantedGoods
	 * @return
	 */
	public task_WantedGoods save(task_WantedGoods task_wantedGoods){
		return task_wantedGoodsDao.save(task_wantedGoods);
	}
	
	/**
	 * 根据学生查询
	 * @param student
	 * @return
	 */
	public List<task_WantedGoods> findByStudent(Student student){
		return task_wantedGoodsDao.findByStudent(student);
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public task_WantedGoods find(Long id){
		return task_wantedGoodsDao.find(id);
	}
	
	/**
	 * 根据id删除
	 * @param id
	 */
	public void delete(Long id){
		task_wantedGoodsDao.deleteById(id);
	}
	
	/**
	 * 分页展示求购物品列表
	 * @param pageBean
	 * @param task_WantedGoods
	 * @return
	 */
	public PageBean<task_WantedGoods> findlist(PageBean<task_WantedGoods> pageBean,task_WantedGoods task_WantedGoods){
		ExampleMatcher exampleMatcher = ExampleMatcher.matching();
		exampleMatcher = exampleMatcher.withMatcher("name", GenericPropertyMatchers.contains());
		exampleMatcher = exampleMatcher.withIgnorePaths("viewNumber");
		Example<task_WantedGoods> example = Example.of(task_WantedGoods, exampleMatcher);
		Sort sort = Sort.by(Direction.DESC, "createTime");
		PageRequest pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(), sort);
		Page<task_WantedGoods> findAll = task_wantedGoodsDao.findAll(example, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}
	
	public PageBean<task_WantedGoods> findWantedGoodslist(PageBean<task_WantedGoods> pageBean,task_WantedGoods task_wantedGoods){
		
		Specification<task_WantedGoods> specification = new Specification<task_WantedGoods>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<task_WantedGoods> root,
					CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + (task_wantedGoods.getName() == null ? "" : task_wantedGoods.getName()) + "%");
				if(task_wantedGoods.getStudent() != null && task_wantedGoods.getStudent().getId() != null){
					Predicate equal1 = criteriaBuilder.equal(root.get("student"), task_wantedGoods.getStudent().getId());
					predicate = criteriaBuilder.and(predicate,equal1);
				}
				return predicate;
			}
		};
		Sort sort = Sort.by(Direction.DESC, "createTime");
		PageRequest pageable = PageRequest.of(pageBean.getCurrentPage()-1, pageBean.getPageSize(), sort);
		Page<task_WantedGoods> findAll = task_wantedGoodsDao.findAll(specification, pageable);
		pageBean.setContent(findAll.getContent());
		pageBean.setTotal(findAll.getTotalElements());
		pageBean.setTotalPage(findAll.getTotalPages());
		return pageBean;
	}
	
	/**
	 * 求购物品总数
	 * @return
	 */
	public long total(){
		return task_wantedGoodsDao.count();
	}
}
