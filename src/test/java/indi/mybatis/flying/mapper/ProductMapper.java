package indi.mybatis.flying.mapper;

import indi.mybatis.flying.annotations.CacheAnnotation;
import indi.mybatis.flying.annotations.CacheRoleAnnotation;
import indi.mybatis.flying.pojo.Product;
import indi.mybatis.flying.statics.CacheRoleType;

@CacheRoleAnnotation(ObserverClass = {}, TriggerClass = { Product.class })
public interface ProductMapper {

	@CacheAnnotation(role = CacheRoleType.Observer)
	public Product select(Object id);

	@CacheAnnotation(role = CacheRoleType.Trigger)
	public void insert(Product t);

}
