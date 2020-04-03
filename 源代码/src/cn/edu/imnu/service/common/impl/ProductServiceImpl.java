package cn.edu.imnu.service.common.impl;
/**
 * ��Ʒ�ӿ�ʵ����
 */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.imnu.dao.common.ProductDao;
import cn.edu.imnu.entity.common.Product;
import cn.edu.imnu.service.common.ProductService;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public int add(Product product) {
		// TODO Auto-generated method stub
		return productDao.add(product);
	}

	@Override
	public int edit(Product product) {
		// TODO Auto-generated method stub
		return productDao.edit(product);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return productDao.delete(id);
	}

	@Override
	public List<Product> findList(Map<String, Object> queMap) {
		// TODO Auto-generated method stub
		return productDao.findList(queMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return productDao.getTotal(queryMap);
	}

	@Override
	public Product findById(Long id) {
		// TODO Auto-generated method stub
		return productDao.findById(id);
	}

	@Override
	public int updateNum(Product product) {
		// TODO Auto-generated method stub
		return productDao.updateNum(product);
	}

}
