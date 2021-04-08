package team.chenxin.service;

import team.chenxin.bean.Favorite;

import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.service
 * @NAME: FavoriteService
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
public interface FavoriteService {

    List<Favorite> listAllFavorites(int user_id);
    void addFavorite(Favorite favorite);
    boolean getFavorite(String favorite_name, int user_id);

    void deleteFavorite(int fa_id);

    void modifyFavorite(int fa_id,String name);

    boolean getFavoriteById(int fa_id, int user_id);
}
