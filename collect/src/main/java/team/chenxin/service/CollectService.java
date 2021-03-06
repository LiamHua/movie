package team.chenxin.service;

import team.chenxin.bean.Collect;

import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.service
 * @NAME: CollectService
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: ζζδΊ
 * @PROJECT_NAME: movie
 **/
public interface CollectService {

    List<Collect> getColletionsByFaid(int fa_id);
    boolean getCollectionByFaiIddFilmId(int fa_id, int film_id);
    void addCollection(int fa_id,int film_id,String collect_time);
    void copyCollection(int fa_id,int film_id,String collect_time);
    void deleteCollection(int fa_id,int film_id);
    void moveCollection(int fa_id,int film_id);
}
