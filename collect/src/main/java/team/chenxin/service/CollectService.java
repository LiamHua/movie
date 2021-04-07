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

    List<Collect> getColletionsByFaid(long fa_id);
    boolean getCollectionByFaiIddFilmId(long fa_id, int film_id);
    void addCollection(long fa_id,int film_id,String collect_time);
    void copyCollection(long fa_id,int film_id,String collect_time);
    void deleteCollection(long fa_id,int film_id);
    void moveCollection(long fa_id,int film_id);
}
