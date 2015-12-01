package me.jp.wheelview.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import me.jp.wheelview.R;
import me.jp.wheelview.widget.CityPicker;


/**
 * Created by liuhanzhi on 15/7/16.
 */
public class DialogTools {
    /**
     * 底部dialog
     *
     * @param activity
     * @param listener1
     * @return
     */
    public static Dialog showBottomDialog(final Activity activity, final View.OnClickListener listener1) {
        View view = UITool.inflate(activity, R.layout.photo_choose_dialog,
                null);
        final Dialog dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
//        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = UITool.getScreenHeight(activity);
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        final Button view1 = (Button) view.findViewById(R.id.ok);
        final CityPicker cityPicker = (CityPicker) view.findViewById(R.id.citypicker);
        final Button view2 = (Button) view.findViewById(R.id.cancel);
        view1.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (null != listener1) {
                            listener1.onClick(view1);
                        }
                        Toast.makeText(activity, cityPicker.getRegion(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
        view.findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
        return dialog;
    }
}
