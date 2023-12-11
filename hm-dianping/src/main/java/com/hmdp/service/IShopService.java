package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IShopService extends IService<Shop> {

    Result queryById(Long id);

    Result update(Shop shop);

//    根据商铺类型和地理坐标位置分页查询商铺信息
    Result queryShopByType(Integer typeId, Integer current, Double x, Double y);
}
