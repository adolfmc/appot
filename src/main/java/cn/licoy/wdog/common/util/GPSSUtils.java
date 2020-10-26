package cn.licoy.wdog.common.util;
// 导入包
import static java.lang.Math.asin;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

public class GPSSUtils {


    /**
     * 将角度转化为弧度
     */
    public static double radians(double d) {
        return d * Math.PI / 180.0;
    }
    /**
     * 根据两点经纬度坐标计算直线距离
     * <p>
     * S = 2arcsin√sin²(a/2)+cos(lat1)*cos(lat2)*sin²(b/2)￣*6378.137
     * <p>
     * 1. lng1 lat1 表示A点经纬度，lng2 lat2 表示B点经纬度；<br>
     * 2. a=lat1 – lat2 为两点纬度之差  b=lng1 -lng2 为两点经度之差；<br>
     * 3. 6378.137为地球赤道半径，单位为千米；
     *
     * @param lng1 点1经度
     * @param lat1 点1纬度
     * @param lng2 点2经度
     * @param lat2 点2纬度
     * @return 距离，单位千米(KM)
     * @see <a href="https://zh.wikipedia.org/wiki/%E5%8D%8A%E6%AD%A3%E7%9F%A2%E5%85%AC%E5%BC%8F">半正矢(Haversine)公式</a>
     */
    public static double getDistanceFrom2LngLat(double lng1, double lat1, double lng2, double lat2) {
        //将角度转化为弧度
        double radLng1 = radians(lng1);
        double radLat1 = radians(lat1);
        double radLng2 = radians(lng2);
        double radLat2 = radians(lat2);

        double a = radLat1 - radLat2;
        double b = radLng1 - radLng2;

        return 2 * asin(sqrt(sin(a / 2) * sin(a / 2) + cos(radLat1) * cos(radLat2) * sin(b / 2) * sin(b / 2))) * 6378.137*1000;
    }

    public static void main(String[] args){
        System.out.println(  getDistanceFrom2LngLat( 121.515244,31.181736,121.534218,31.195534) );
    }
}
