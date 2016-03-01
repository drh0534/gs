package com.gs.pp.common.utils;

import org.apache.commons.lang.StringUtils;

public class RedisUtils {

    /*****************************************H5***************************************************/

    /**
     * 用户收藏
     * USER_ID
     * PLATFORM_ID
     */
    //public final static String DECIVE_API_COLLECTION = "DECIVE_API_COLLECTION-{0}-{1}";

    /**
     * 用户收藏
     * USER_ID
     * CPID
     * PLATFORM_ID
     * TYPE_ID
     * ID
     */
    //public final static String DECIVE_API_COLLECTION = "DECIVE_API_COLLECTION-{0}-{1}-{2}-{3}-{4}";


    /**
     * 用户播放记录
     * USER_ID
     * PLATFORM_ID
     * TYPE
     */
    //public final static String DECIVE_API_RECORD = "DECIVE-API-RECORD-{0}-{1}-{2}";//只保存最后一条的记录
    //public final static String DECIVE_API_RECORD_LIST = "DECIVE-API-RECORD-LIST-{0}-{1}";


    /**
     * 访问的url记录
     * URL
     */
    public final static String LIST_ALBUM_ALL_VISITED = "LIST-ALBUM-ALL-VISITED-{0}";
    /**
     * 用于在redis中缓存一定时效性的推荐数据
     *
     * @param {0} request.getURI
     * @param {1} foucusId 正版 59,聚合版 19
     * @param {2} page
     * @param {3} size
     */
    public static final String LIST_SUBJECT_PAGE_LIST = "LIST-SUBJECT-PAGE-LIST-{0}:{1}:{2}:{3}";

    /**
     * redis中缓存一定时间得猜你新欢的列表，初步预定为300s
     * {0} request.getURI
     * {1} channelId
     * {2} roleId
     * {3} page
     * {4} size
     */
    public static final String REDIS_RECOMMENDATION_LIST = "REDIS_RECOMMENDATION_LIST-{0}:{1}:{2}:{3}:{4}:{5}";
    /**
     * 视频播放位置
     * 保存格式：USER_ID:ALBUM_ID:VIDEO_ID
     */
    //public final static String DECIVE_PLAY_SEEK = "VIDEO-PLAY-SEEK-{0}:{1}:{2}:{3}:{4}:{5}:{6}:{7}:{8}";
    //public final static String DECIVE_PLAY_SEEK_PREFIX = "DECIVE-VIDEO-PLAY-SEEK-";

    /**
     * 跨年活动中的点播数据做缓存
     * {0} 点播专辑id
     */
    public static final String REDIS_YUANDAN_ON_DEMAND = "REDIS-YUANDAN-ON-EMAND-{0}";

    /**
     * 直播數據做緩存,初步定為緩存300秒
     * {0} 请求地址
     * ｛1｝ menuId
     * {2} terminalId
     * {3} page
     * {4} size
     * {5} tvchannal
     */
    public static final String REDIS_ALBUM_LIST_URL = "REDIS_ALBUM_LIST_URL-{0}:{1}:{2}:{3}:{4}:{5}";

    /**
     * 搜索列表做缓存
     * 0- requestURI
     * 1-channelId
     * 2 categoryId
     * 3 areaId
     * 4 terminalId
     * 5 ischarge
     * 6 page
     * 7 size
     */
    public static final String REDIS_SEARCH_CACHE_LIST = "REDIS_SEARCH_CACHE_LIST-{0}:{1}:{2}:{3}:{4}:{5}:{6}:{7}";

    /**
     * 缓存专辑详细信息
     * 0 - 请求url
     * 1 - albumId
     */
    public static final String REDIS_VRS_ALBUM_PAY_INFO = "REDIS_VRS_ALBUM_PAY_INFO-{0}:{1}";
    /**
     * 1个小时,3600秒
     */
    public final static int TIME_3600 = 3600;


    public final static long TIME_300 = 300;

    /**
     * 10分钟  600 秒
     */
    public static final long TIME_600 = 600;

    /**
     * 1分钟  60 秒
     */
    public final static long TIME_60 = 60;

    public final static long INTEGER_ZERO = 0;
    public final static long INTEGER_MAX = Integer.MAX_VALUE;

    public static final String REDIS_CP_HOME_MENU = "REDIS_CP_HOME_MENU";
    /**
     * 0-album_id
     * 1-page
     * 2-size
     */
    public  static  final  String REDIS_CP_ALBUMINFO="REDIS_CP_ALBUM_INFO-{0}:{1}:{2}";


    public static String getKeyPrefix(String key) {
        if (!StringUtils.isEmpty(key)) {
            return key.substring(0, key.indexOf("-{"));
        }
        return "";
    }

    public static String getKeyPrefixNext(String key) {
        if (!StringUtils.isEmpty(key)) {
            return key.substring(0, key.indexOf("{"));
        }
        return "";
    }

}
