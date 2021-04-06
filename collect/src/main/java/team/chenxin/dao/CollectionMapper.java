package team.chenxin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
@Mapper
@Repository(value = "team.chenxin.dao.CollectionMapper")
public interface CollectionMapper {

    List<Collect> getColletionsByFaid(@Param("fa_id") int fa_id);

    void addCollection(@Param("fa_id") int fa_id,
                       @Param("film_id") int film_id,
                       @Param("collect_time")int collect_time);

    void copyCollection(@Param("fa_id") int fa_id,
                        @Param("film_id") int film_id);

    void deleteCollection(@Param("fa_id") int fa_id,
                          @Param("film_id") int film_id);

    void moveCollection(@Param("fa_id") int fa_id,
                         @Param("film_id")int film_id);
}
