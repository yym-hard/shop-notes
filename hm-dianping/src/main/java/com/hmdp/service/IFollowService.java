package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IFollowService extends IService<Follow> {

    //关注和取消关注功能
    Result follow(Long followUserId, Boolean isFollow);

    //查询是否关注该用户
    Result isFollow(Long followUserId);

    //共同关注功能
    Result followCommons(Long id);
}
