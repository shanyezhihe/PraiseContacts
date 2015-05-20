package com.example.praisecontacts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CallFrament extends Fragment {
	private ListView mMsgs;
	private ChatMessageAdapter mAdapter;
	private List<ChatMessage> mDatas;
	
	private EditText mInputMsg;
	private Button mSendMsg;
	
	private Handler mHandler;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frament_call, container, false);
		
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				//等待接收，子线程完成数据 的返回
				ChatMessage fromMessage = (ChatMessage) msg.obj;
				mDatas.add(fromMessage);
				mAdapter.notifyDataSetChanged();
				mMsgs.setSelection(mDatas.size() - 1);
			}
			
		};
		
		
		mMsgs = (ListView) view.findViewById(R.id.id_listview_msgs);
		mInputMsg = (EditText) view.findViewById(R.id.id_input_msg);
		mSendMsg = (Button) view.findViewById(R.id.id_send_msg);
		
		mDatas = new ArrayList<ChatMessage>();
		mDatas.add(new ChatMessage("上知天文下晓地理的小二竭诚为您服务", com.example.praisecontacts.ChatMessage.Type.INCOMING, new Date()));
		mAdapter = new ChatMessageAdapter(getActivity(), mDatas);
		mMsgs.setAdapter(mAdapter);
		mSendMsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String toMsg = mInputMsg.getText().toString();
				if(TextUtils.isEmpty(toMsg)) {
					Toast.makeText(getActivity(), "发送消息不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				
				ChatMessage toMessage = new ChatMessage();
				toMessage.setDate(new Date());
				toMessage.setMsg(toMsg);
				toMessage.setType(com.example.praisecontacts.ChatMessage.Type.OUTCOMING);
				mDatas.add(toMessage);
				mAdapter.notifyDataSetChanged();
				mMsgs.setSelection(mDatas.size() - 1);
				
				mInputMsg.setText("");
				
				new Thread() {
					public void run() {
						ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
						Message m = Message.obtain();
						m.obj = fromMessage;
						mHandler.sendMessage(m);
					}
				}.start();
			}
		});
		return view;
	}
	
}
