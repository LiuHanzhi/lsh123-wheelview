package me.jp.wheelview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jp.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

import me.jp.wheelview.R;
import me.jp.wheelview.util.AreaDataUtil;

/**
 * CityPicker
 * put two WheelView into same Linearlayout
 *
 * @author JiangPing
 */
public class CityPicker extends LinearLayout {
    private static final int REFRESH_VIEW = 0x001;

    private WheelView mProvincePicker;
    private WheelView mCityPicker;
    private WheelView mRegionPicker;

    private int mProvinceIndex = -1;
    private int mCityIndex = -1;
    private int mRegionIndex = -1;

    private String mProvinceText;
    private String mCityText;
    private String mRegioneText;

    private AreaDataUtil mAreaDataUtil;
    private ArrayList<String> mProvinceList = new ArrayList<String>();
    private ArrayList<String> mCityceList = new ArrayList<String>();

    public CityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAreaInfo();
    }

    public CityPicker(Context context) {
        this(context, null);
    }

    private void getAreaInfo() {

        mProvinceIndex = 0;
        mCityIndex = 0;
        mRegionIndex = 1;

        mAreaDataUtil = new AreaDataUtil();
        mProvinceList = mAreaDataUtil.getProvinces();
        mCityceList = mAreaDataUtil.getCitysByProvince(mProvinceList.get(mProvinceIndex));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);

        mProvincePicker = (WheelView) findViewById(R.id.province);
        mCityPicker = (WheelView) findViewById(R.id.city);
        mRegionPicker = (WheelView) findViewById(R.id.region);

        mProvincePicker.setData(mProvinceList);
        mProvincePicker.setDefault(mProvinceIndex);
        mProvinceText = mProvinceList.get(mProvinceIndex);


        String defaultProvince = mProvinceList.get(mProvinceIndex);
        ArrayList<String> defaultCitys = mAreaDataUtil.getCitysByProvince(defaultProvince);
        mCityPicker.setData(defaultCitys);
        mCityPicker.setDefault(mCityIndex);
        mCityText = defaultCitys.get(mCityIndex);

        String defaultCity = mCityceList.get(mCityIndex);
        ArrayList<String> defaultRegions = mAreaDataUtil.getRegionByCity(defaultCity);
        mRegionPicker.setData(defaultRegions);
        mRegionPicker.setDefault(mRegionIndex);
        mRegioneText = defaultRegions.get(mRegionIndex);

        mProvincePicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                Log.d("lhz", "id:" + id + "text:" + text);
                if (text.equals("") || text == null)
                    return;
                if (mProvinceIndex != id) {
                    mProvinceIndex = id;
                    mProvinceText = text;
                    String selectProvince = mProvincePicker.getSelectedText();
                    if (selectProvince == null || selectProvince.equals(""))
                        return;
                    // get city names by province
                    ArrayList<String> citys = mAreaDataUtil
                            .getCitysByProvince(mProvinceList.get(id));
                    if (citys.size() == 0) {
                        return;
                    }

                    mCityPicker.setData(citys);
                    mCityPicker.setDefault(0);

                    ArrayList<String> regions = mAreaDataUtil.getRegionByCity(citys.get(0));
                    mRegionPicker.setData(regions);
                    mRegionPicker.setDefault(0);

                    mCityceList = mAreaDataUtil.getCitysByProvince(mProvinceList.get(id));
                }

            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mCityPicker.setOnSelectListener(new WheelView.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                if (mCityIndex != id) {
                    mCityIndex = id;
                    mCityText = text;
                    String selectCity = mCityPicker.getSelectedText();
                    if (selectCity == null || selectCity.equals(""))
                        return;
                    int lastIndex = Integer.valueOf(mCityPicker.getListSize());
//                    int defaultIdex = id>lastIndex?lastIndex:id;
//                    if (id > lastIndex) {
//                        mCityPicker.setDefault(lastIndex - 1);
                    // get region names by city
                    ArrayList<String> regions = mAreaDataUtil
                            .getRegionByCity(mCityceList.get(id));
                    mRegionPicker.setData(regions);
                    mRegionPicker.setDefault(0);


                }

//                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        mRegionPicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                if (mCityIndex != id) {
                    mCityIndex = id;
                    mCityText = text;
                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

    }

    public String getRegion() {
        return mProvinceText + "省，" + mCityText + "市，" + mRegioneText + "区";
    }

}
