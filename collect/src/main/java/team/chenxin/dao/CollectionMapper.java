package team.chenxin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import team.chenxin.bean.Collect;

import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.mapper
 * @NAME: CollectionMapper
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Repository(value = "team.chenxin.mapper")
public interface CollectionMapper {

    List<Collect> getColletionsByFaid(int fa_id);
}
