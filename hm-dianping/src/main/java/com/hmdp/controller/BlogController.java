package com.hmdp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Blog;
import com.hmdp.entity.User;
import com.hmdp.service.IBlogService;
import com.hmdp.service.IUserService;
import com.hmdp.utils.SystemConstants;
import com.hmdp.utils.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.hmdp.utils.SystemConstants.MAX_PAGE_SIZE;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;

    //发布博客并保存到数据库
    @PostMapping
    public Result saveBlog(@RequestBody Blog blog) {
       return blogService.saveBlog(blog);
    }

    /**
     * 点赞功能
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    public Result likeBlog(@PathVariable("id") Long id) {

        return blogService.likeBlog(id);
    }

    @GetMapping("/of/me")
    public Result queryMyBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        // 获取登录用户
        UserDTO user = UserHolder.getUser();
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .eq("user_id", user.getId()).page(new Page<>(current, MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }

    @GetMapping("/hot")
    public Result queryHotBlog(@RequestParam(value = "current", defaultValue = "1") Integer current) {
        return blogService.queryHotVlog(current);
    }

    @GetMapping("/{id}")
    public Result queryBlogById(@PathVariable("id") Long id){
        return blogService.queryBlogById(id);
    }

    //根据博客id查询点赞的用户
    @GetMapping("likes/{id}")
    public Result queryBlogLikes(@PathVariable("id") Long id){
        return blogService.queryBlogLikes(id);
    }

    //根据用户id查询该用户发表的博客，并且分页查询
    @GetMapping("/of/user")
    public Result queryBlogByUserId(
            @RequestParam("id") Long userId,
            @RequestParam(value = "current",defaultValue = "1") Integer current){
        //分页查询器
        Page<Blog> page =new Page(current,MAX_PAGE_SIZE);
        //条件构造器
        LambdaQueryWrapper<Blog> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getUserId,userId);
        //执行查询
        blogService.page(page, queryWrapper);
        //获取当前页数据
        List<Blog> records = page.getRecords();
        return Result.ok(records);
    }

    //实现对关注的人发布的博客进行滚动分页查询
    @GetMapping("/of/follow")
    public Result queryBlogOfFollow(
            @RequestParam("lastId") Long max,@RequestParam(value = "offset",defaultValue = "0") Integer offset){
        return blogService.queryBlogOfFollow(max,offset);
    }
}
