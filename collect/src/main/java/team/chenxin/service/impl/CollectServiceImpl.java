package team.chenxin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.chenxin.bean.Collect;
import team.chenxin.dao.CollectionMapper;
import team.chenxin.service.CollectService;

import java.util.List;

/**
 * @Description TODO
 * @PACKAGE_NAME: team.chenxin.service.impl
 * @NAME: CollectServiceImpl
 * @USER: Chenxin
 * @DATE: 2021/4/2
 * @DAY_NAME_FULL: 星期五
 * @PROJECT_NAME: movie
 **/
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public List<Collect> getColletionsByFaid(int fa_id) {
        return collectionMapper.getColletionsByFaid(fa_id);
    }

    @Override
    public void addCollection(int fa_id, int film_id, int collect_time) {
        collectionMapper.addCollection(fa_id,film_id,collect_time);
    }

    @Override
    public void copyCollection(int fa_id, int film_id) {
        collectionMapper.copyCollection(fa_id,film_id);
    }

    @Override
    public void deleteCollection(int fa_id, int film_id) {
        collectionMapper.deleteCollection(fa_id,film_id);
    }

    @Override
    public void moveCollection(int fa_id, int film_id) {
        collectionMapper.moveCollection(fa_id, film_id);
    }

}
