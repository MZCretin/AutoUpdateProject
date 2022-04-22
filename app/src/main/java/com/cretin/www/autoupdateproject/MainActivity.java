package com.cretin.www.autoupdateproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.cretin.www.autoupdateproject.adapter.RecyclerviewAdapter;
import com.cretin.www.autoupdateproject.model.ListModel;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppUpdateInfoListener;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerviewAdapter recyclerviewAdapter;
    private List<ListModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        list = new ArrayList<>();
        obtainData();
        recyclerviewAdapter = new RecyclerviewAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerviewAdapter);
    }

    //造假数据
    private void obtainData() {
        for (int i = 0; i < 13; i++) {
            ListModel listModel = new ListModel();
            listModel.setForceUpdate(false);
            listModel.setUiTypeValue(300 + i);
            listModel.setCheckFileMD5(true);
            listModel.setSourceTypeVaule(TypeConfig.DATA_SOURCE_TYPE_MODEL);
            list.add(listModel);
        }
    }

    //清除数据
    public void clear() {
        AppUpdateUtils.getInstance().clearAllData();
        Toast.makeText(this, "下载的所有数据清除成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_01:
                //使用说明
                startActivity(new Intent(this, InfoActivity.class));
                break;
            case R.id.action_02:
                //清除本地缓存
                clear();
                break;
            case R.id.action_03:
                //自定义UI
                AppUpdateUtils.getInstance().getUpdateConfig().setUiThemeType(TypeConfig.UI_THEME_CUSTOM);//类型为自定义样式类型
                AppUpdateUtils.getInstance().getUpdateConfig().setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_JSON); //使用本地json提供数据
                AppUpdateUtils.getInstance().checkUpdate(recyclerviewAdapter.jsonDataUnForce);
                Toast.makeText(this, "为了展示是真的可以自定义UI的，我写了个很丑的页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_04:
                //获取文件MD5检验码工具
                startActivity(new Intent(this, MD5HelperActivity.class));
                break;
            case R.id.action_05:
                //带提示的更新
                AppUpdateUtils.getInstance().getUpdateConfig().setUiThemeType(TypeConfig.UI_THEME_A);//类型为自定义样式类型
                AppUpdateUtils.getInstance().getUpdateConfig().setDataSourceType(TypeConfig.DATA_SOURCE_TYPE_JSON); //使用本地json提供数据
                AppUpdateUtils.getInstance().addAppUpdateInfoListener(new AppUpdateInfoListener() {
                    @Override
                    public void isLatestVersion(boolean isLatest) {
                        if(isLatest){
                            Toast.makeText(MainActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "发现新版本，将会提示更新", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).checkUpdate(recyclerviewAdapter.jsonDataNoUpdate);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}