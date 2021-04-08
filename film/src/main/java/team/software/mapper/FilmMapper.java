package team.software.mapper;

import org.springframework.stereotype.Repository;
import team.software.bean.FilmBean;
import team.software.bean.FilmDetailBean;
import team.software.bean.StarBean;

import java.util.List;
import java.util.Map;

/**
 * @author huao
 * @ClassName FilmMapper.java
 * @Description TODO
 * @createTime 2021年04月06日 08:17:00
 */
@Repository(value = "team.software.mapper.FilmMapper")
public interface FilmMapper {

    List<FilmBean> queryFilm(Map<String, Object> param);

    FilmDetailBean queryFilmDetail(Map<String, String> param);

    List<FilmDetailBean> queryHotFilm(Map<String, Object> param);

    List<StarBean> queryFilmDirector(String film_id);

    List<StarBean> queryFilmScriptwriter(String film_id);

    List<StarBean> queryFilmStar(String film_id);
}
