package com.as.huanxindemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends AppCompatActivity {

    private EditText etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();

    }

    //点击开始聊天
    public void chat(View view) {
        String userId = etId.getText().toString().trim();
        if(userId!=null){
            EaseChatFragment chatFragment = new EaseChatFragment();
            Bundle args = new Bundle();
            args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
            args.putString(EaseConstant.EXTRA_USER_ID, userId);
            chatFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        }
    }

    private void initView() {
        etId = (EditText) findViewById(R.id.etId);
    }

    private void submit() {
        // validate
        String etIdString = etId.getText().toString().trim();
        if (TextUtils.isEmpty(etIdString)) {
            Toast.makeText(this, "请输入对方的id", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

    }


}
