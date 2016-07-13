package com.example.gobywind.xcccf;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.Method;


/**
 * 封装pop类，创建回调
 * @author AHF
 *
 */
public class TradePwdPopUtils {
	private  PopupWindow popWindow = null;
	private  CallBackTradePwd callBackTradePwd;
	public TradePwdPopUtils() {
		super();
	}
	
	public CallBackTradePwd getCallBackTradePwd() {
		return callBackTradePwd;
	}

	public void setCallBackTradePwd(CallBackTradePwd callBackTradePwd) {
		this.callBackTradePwd = callBackTradePwd;
	}

	public interface CallBackTradePwd{
		void callBaceTradePwd(String pwd);
	}

	protected void showPopWindow(Context context,Activity ac,LinearLayout lin) {
		popWindow = null;
		if (popWindow == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.trade_key_layout, null);
			SecurityPasswordEditText myEdit = (SecurityPasswordEditText) view.findViewById(R.id.my_edit);
			TextView tvClose = (TextView) view.findViewById(R.id.tv_close);
			myEdit.setSecurityEditCompleListener(new SecurityPasswordEditText.SecurityEditCompleListener() {
				
				@Override
				public void onNumCompleted(String num) {
					popWindow.dismiss();
					if(callBackTradePwd!=null){
						callBackTradePwd.callBaceTradePwd(num);
					}
				}
			});
			tvClose.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					popWindow.dismiss();
				}
			});
			ac.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			Method setShowSoftInputOnFocus;
			try {
				setShowSoftInputOnFocus = myEdit.getClass().getMethod(
						"setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(myEdit, false);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}

			new KeyboardUtil(view, context, myEdit).showKeyboard();
			popWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			popWindow.setFocusable(true);
			popWindow.setOutsideTouchable(true);
			popWindow.setBackgroundDrawable(new BitmapDrawable());
			popWindow.setAnimationStyle(R.style.PopupAnimation);
			popWindow.showAtLocation(lin, Gravity.BOTTOM, 0, 0);

		}
	}
}
