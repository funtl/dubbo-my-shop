package com.funtl.myshop.commons.mapper;

import com.funtl.myshop.commons.domain.TbUser;
import com.funtl.myshop.commons.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.MyMapper;

@CacheNamespace(implementation = RedisCache.class)
public interface TbUserMapper extends MyMapper<TbUser> {
}