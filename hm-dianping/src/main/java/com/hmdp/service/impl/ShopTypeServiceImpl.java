package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_TYPE_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryTypeList() {
        //1.从redis查询商铺类型数据
        String shopTypes = stringRedisTemplate.opsForValue().get(CACHE_SHOP_TYPE_KEY);
        //2.存在，直接返回数据
        if (StrUtil.isNotBlank(shopTypes)) {
            List<ShopType> typeList = JSONUtil.toList(shopTypes, ShopType.class);
            return Result.ok(typeList);
        }
        //3.不存在，查询数据库
        List<ShopType> shopTypeList = this.list();
        //4.不存在，返回错误信息
        if (shopTypeList.isEmpty()) {
            return Result.fail("店铺类型不存在！");
        }
        //5.存在，保存数据到redis中，并返回数据
        String jsonStr = JSONUtil.toJsonStr(shopTypeList);
        stringRedisTemplate.opsForValue().set(CACHE_SHOP_TYPE_KEY,jsonStr);

        return Result.ok(shopTypeList);
    }

}
