package me.jp.wheelview.util;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 全国省份城市操作类
 *
 * @author JiangPing
 */
public class AreaDataUtil {

    /**
     * 所有的省市String
     */
    public final String AREAS = "北京&&&" +
            "北京&&东城区&西城区&崇文区&宣武区&朝阳区&丰台区&石景山区&海淀区&门头沟区&房山区&通州区&顺义区&昌平区&大兴区&怀柔区&平谷区&密云县&延庆县" +
            "&&&&" +
            "北京1" +
            "&&&" +
            "北京11&&东城区111&西城区111&崇文区111&宣武区111&朝阳区111&丰台区111&石景山区111&海淀区111&门头沟区111&房山区111&通州区111&顺义区111&昌平区111&大兴区111&怀柔区111&平谷区111&密云县111&延庆县111" +
            "&&" +
            "北京22&&东城区222&西城区222&崇文区222&宣武区222&朝阳区222&丰台区222&石景山区222&海淀区222&门头沟区222&房山区222&通州区222&顺义区222&昌平区222&大兴区222&怀柔区222&平谷区222&密云县222&延庆县222";
    /**
     * /**
     * 一个省份对应多个城市
     */
    private String[] single_province_city;
    /**
     * 全国省市Map key:省份 |Value:城市集合
     */
    private HashMap<String, List<String>> mCitysMap = new HashMap<String, List<String>>();

    private HashMap<String, HashMap<String, List<String>>> mReginonMap = new HashMap<>();

    public AreaDataUtil() {
        splitProvice();
        getAllCityMap();
    }

    /**
     * 将省份和对应城市分割出来
     * <p/>
     * 得到：宁夏&&&银川&&石嘴山&&吴忠&&固原
     */
    private void splitProvice() {
        single_province_city = AREAS.split("&&&&");
    }

    /**
     * 获得全国省份的列表
     *
     * @return
     */
    public ArrayList<String> getProvinces() {
        ArrayList<String> provinceList = new ArrayList<String>();
        for (String str : single_province_city) {
            String province = str.split("&&&")[0];
            provinceList.add(province);
        }
        return provinceList;
    }

    /**
     * 根据省份获取城市列表
     *
     * @return
     */
    private void getAllCityMap() {

        for (String str : single_province_city) {
            String[] provinceSplit = str.split("&&&");
            // 得到省份
            String province = provinceSplit[0];
            // 得到当前省份对应的城市
            String citysStr = provinceSplit[1];
            String[] citySplit = citysStr.split("&&");
            ArrayList<String> cityList = new ArrayList<>();
            HashMap<String, List<String>> regionMap = new HashMap<>();
            for (int i = 0; i < citySplit.length; ) {
                String city = citySplit[i];
                i++;
                // 分离城市放入集合
                cityList.add(city);
                String regionsStr = citySplit[i];
                i++;
                List<String> regions = Arrays.asList(regionsStr.split("&"));
                // 省份和城市放入Map中
                regionMap.put(city, regions);
            }
            mReginonMap.put(province, regionMap);
            mCitysMap.put(province, cityList);
        }
    }

    /**
     * 根据省份查找对应的城市列表
     *
     * @return 城市集合
     */
    public ArrayList<String> getCitysByProvince(String provinceStr) {

        List<String> list = mCitysMap.get(provinceStr);
        ArrayList<String> arrList = new ArrayList<String>();
        for (String citys : list) {
            arrList.add(citys);
        }
        return arrList;
    }

    public ArrayList<String> getRegionByCity(String cityStr) {
        ArrayList<String> arrList = new ArrayList<String>();
        Iterator<String> iterator = mCitysMap.keySet().iterator();
        while (iterator.hasNext()) {
            String provice = iterator.next();
            List<String> cityList = mCitysMap.get(provice);
            for (String city : cityList) {
                if (city.endsWith(cityStr)) {
                    HashMap<String, List<String>> regionMap = mReginonMap.get(provice);
                    List<String> regions = regionMap.get(cityStr);

                    for (String citys : regions) {
                        arrList.add(citys);
                    }
                    break;
                }
            }
        }
        return arrList;
    }


}
