package cn.licoy.wdog.core.service.appot;import cn.licoy.wdog.common.service.QueryService;import cn.licoy.wdog.core.dto.appot.FindFavoritesDTO;import cn.licoy.wdog.core.entity.appot.Favorites;import com.alibaba.fastjson.JSONObject;import com.baomidou.mybatisplus.service.IService;import java.util.List;/** * @author mc * @version Fri Dec 18 19:07:13 2020 */public interface FavoritesService extends IService<Favorites>,QueryService<Favorites, FindFavoritesDTO>{void saveFavorites(JSONObject opidJsonObject );    Favorites isFavorites(Favorites favorites);};