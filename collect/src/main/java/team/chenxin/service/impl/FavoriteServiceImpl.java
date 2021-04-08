package team.chenxin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.chenxin.bean.Favorite;
import team.chenxin.controller.FavoriteController;
import team.chenxin.dao.FavoriteMapper;
import team.chenxin.service.FavoriteService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.service.impl
 * @NAME: FavoriteServiceImpl
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    @Override
    public List<Favorite> listAllFavorites(int user_id) {
        return favoriteMapper.listAllFavorites(user_id);
    }

    @Override
    public void addFavorite(Favorite favorite) {
        favoriteMapper.addFavorite(favorite);
    }

    @Override
    public boolean getFavorite(String favorite_name, int user_id) {
        return favoriteMapper.getFavorite(favorite_name,user_id)!=null;
    }

    @Override
    public void deleteFavorite(long fa_id) {
        favoriteMapper.deleteFavorite(fa_id);
    }

    @Override
    public void modifyFavorite(long fa_id,String name) {
        favoriteMapper.modifyFavorite(fa_id,name);
    }

    @Override
    public boolean getFavoriteById(int fa_id, int user_id) {
        return favoriteMapper.getFavoriteById(fa_id,user_id)!=null;
    }
}
