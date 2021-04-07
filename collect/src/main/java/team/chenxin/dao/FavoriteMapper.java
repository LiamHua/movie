package team.chenxin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.chenxin.bean.Favorite;

import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.mapper
 * @NAME: FavoriteMapper
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Mapper
@Repository(value = "team.chenxin.dao.FavoriteMapper")
public interface FavoriteMapper {
    List<Favorite> listAllFavorites(@Param("user_id")int user_id);

    Favorite getFavorite(@Param("fa_id") long fa_id,@Param("user_id") int user_id);

    void addFavorite(@Param("favorite") Favorite favorite);

    void deleteFavorite(@Param("fa_id") long fa_id);

    void modifyFavorite(@Param("fa_id") long fa_id,
                        @Param("favorite_name")String name);
}
