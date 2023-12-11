package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IBlogService extends IService<Blog> {

    //根据id查询笔记
    Result queryBlogById(Long id);

    //个人主页博客分页查询
    Result queryHotVlog(Integer current);

    //点赞功能
    Result likeBlog(Long id);

    //根据博客id查询点赞的用户
    Result queryBlogLikes(Long id);

    //发布博客并保存到数据库
    Result saveBlog(Blog blog);

    //实现对关注的人发布的博客进行滚动分页查询
    Result queryBlogOfFollow(Long max, Integer offset);
}
