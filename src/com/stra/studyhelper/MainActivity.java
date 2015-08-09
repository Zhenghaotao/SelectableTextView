package com.stra.studyhelper;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.stra.studyhelper.utils.LogUtils;
import com.stra.studyhelper.widget.SelectableTextView;
import com.stra.studyhelper.widget.SelectableTextView.MenuListener;
import com.stra.studyhelper.widget.SelectableTextView.SelectionInfo;

public class MainActivity extends Activity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private SelectableTextView mTextView;
	private ClipboardManager clipboard;
	private View menuView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		initListener();
	}
	
	
	/**
	 * 初始化监听事件
	 */
	private void initListener() {
		mTextView.setOnMenuListener(new MenuListener(){

			@Override
			public void onCopy(String selectedText) {
				LogUtils.i(TAG, "onCopy");
				// 调用系统剪切版
				if (clipboard == null) {
					clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				}
				clipboard.setText(selectedText);
				LogUtils.i(TAG, "onCopy, the text is :" +  clipboard.getText().toString());
			}

			@Override
			public void onDictSearch(String selectedText) {
				LogUtils.i(TAG, "onDictSearch");
				
			}

			@Override
			public void onBrowser(String selectedText) {
				LogUtils.i(TAG, "onBrowser");
				Intent intent = new Intent();
				 Uri content_url = Uri.parse("http://www.baidu.com");
		        intent.setAction("android.intent.action.VIEW");
		        intent.setData(content_url);
		        startActivity(intent);
			}
		});
		
		menuView.findViewById(R.id.tv_copy).setOnClickListener(this);
		menuView.findViewById(R.id.tv_dictsearch).setOnClickListener(this);
		menuView.findViewById(R.id.tv_browser).setOnClickListener(this);
	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		// make sure the TextView's BufferType is Spannable, see the main.xml
		mTextView = (SelectableTextView) findViewById(R.id.main_text);
		mTextView.setDefaultSelectionColor(0x40FF00FF);

		menuView = getLayoutInflater().inflate(R.layout.select_menu, null);
		mTextView.setPopupMenuView(menuView);
		
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		SelectionInfo info = mTextView.getCursorSelection();
		// 用户选中的文字
		String selectText = info.getSelectedText().toString();

		switch (id) {
		case R.id.tv_copy:// 复制
			mTextView.copyText(selectText);
			break;
		case R.id.tv_dictsearch:// 查询字典
			mTextView.dictSearch(selectText);
			break;
		case R.id.tv_browser:// 浏览器
			mTextView.browser(selectText);
			break;
		}
		mTextView.hideCursor();

	}

}
