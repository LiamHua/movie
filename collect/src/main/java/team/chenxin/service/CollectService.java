package team.chenxin.service;

import team.chenxin.bean.Collect;

import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.service
 * @NAME: CollectService
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
public interface CollectService {

    List<Collect> getColletionsByFaid(int fa_id);
    void addCollection(int fa_id);
    void copyCollection(int film_id,int fa_id);
    void deleteCollection(int film_id);
    void moveCollection(int fa_id);
}
