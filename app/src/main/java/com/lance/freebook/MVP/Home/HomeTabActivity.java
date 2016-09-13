package com.lance.freebook.MVP.Home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lance.freebook.Date.HttpData.HttpData;
import com.lance.freebook.MVP.Base.BaseActivity;
import com.lance.freebook.MVP.Home.Fragment.FragmentController;
import com.lance.freebook.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class HomeTabActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.hometab_radio)
    RadioGroup hometabRadio;
    @BindView(R.id.hometab_toolbar_textview_title)
    TextView hometabToolbarTextviewTitle;
    private FragmentController controller;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_home_tab);
        controller = FragmentController.getInstance(this, R.id.hometab_context);
        controller.showFragment(0);
    }

    @Override
    protected void findViewById() {
        hometabRadio = (RadioGroup) findViewById(R.id.hometab_radio);
    }

    @Override
    protected void setListener() {
        hometabRadio.setOnCheckedChangeListener(this);
    }

    @Override
    protected void processLogic() {
        HttpData.getInstance().getHtml(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("HomeTabActivity", e.toString());
            }

            @Override
            public void onNext(String html) {
                Document doc = Jsoup.parse(html);
                Element content = doc.select("ul").first();
                Elements links = content.select("li");
                for (Element element : links) {
                    String itemStr= element.html();
                    Log.d("HomeTabActivity", itemStr);
                }
            }
        });
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_recommend:
                hometabToolbarTextviewTitle.setText("推荐");
                controller.showFragment(0);
                break;
            case R.id.rb_stack:
                hometabToolbarTextviewTitle.setText("书库");
                controller.showFragment(1);
                break;
            case R.id.rb_search:
                hometabToolbarTextviewTitle.setText("搜索");
                controller.showFragment(2);
                break;
            case R.id.rb_download:
                hometabToolbarTextviewTitle.setText("下载");
                controller.showFragment(3);
                break;
        }
    }

}
