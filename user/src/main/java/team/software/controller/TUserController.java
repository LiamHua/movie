package team.software.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import team.software.bean.TUser;
import team.software.service.TUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.api.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * TUser表服务接口层
 *
 * @author liam
 * @date 2021/04/06 09:10
 */
@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class TUserController {

    /**
     * 服务对象
     */
    TUserService tUserService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param tUser 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R<IPage<TUser>> selectAll(Page<TUser> page, TUser tUser) {
        return R.ok(tUserService.page(page, new QueryWrapper<>(tUser)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get/{id}")
    public R<TUser> selectOne(@PathVariable Serializable id) {
        return R.ok(tUserService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param tUser 实体对象
     * @return 新增结果
     */
    @PostMapping("/register")
    public R<Map<String, String>> insert(@RequestParam Map<String, String> tUser) {
        log.info(tUser.toString());



        //boolean rs = tUserService.save(tUser);
        return R.ok(tUser);
    }

    /**
     * 修改数据
     *
     * @param tUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R<Boolean> update(@RequestBody TUser tUser) {
        return R.ok(tUserService.updateById(tUser));
    }

    /**
     * 单条/批量删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @DeleteMapping
    public R<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return R.ok(tUserService.removeByIds(idList));
    }
}
