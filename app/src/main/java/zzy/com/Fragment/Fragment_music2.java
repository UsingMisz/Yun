package zzy.com.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.zzy.com.yun.R;
import zzy.com.zidingyi.ui.DropMenu;
import zzy.com.zidingyi.ui.DropMenuClickInterface;
import zzy.com.zidingyi.ui.DroupMenuArrowStatusInterface;

/**
 * Created by Administrator on 2017/3/11.
 */

public class Fragment_music2 extends Fragment implements DropMenuClickInterface,DroupMenuArrowStatusInterface {
    private ArrayList<HashMap<String,Object>> listItem=null;
private ListView listView;
    List<String> strings;
private View view;

    private DropMenu dropMenu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.fragment_music2,null);
        init();
        simpleAdaper();
        dropMenu();
        return view;
    }

    private void dropMenu() {
        dropMenu.setDropMenuClickInterface(this);
        dropMenu.setDataSouece(strings);
        dropMenu.setTitleFontSize(14.0f);
    }


    private void init() {
        listView= (ListView) view.findViewById(R.id.fragment_music2_listView);
        dropMenu= (DropMenu) view.findViewById(R.id.dropmenu);

    }

    /**
     * 添加数据
     */
    private void simpleAdaper() {

        listItem = new ArrayList<>();

            HashMap<String, Object> map = new HashMap<>();
            map.put("fragment_music2_listView_ImageView", R.mipmap.loacation_music);
            map.put("fragment_music2_listView_TextView", "本地音乐");
            map.put("R.id.fragment_music2_listView_smallTextView", "(0)");
            map.put("line","1");
            listItem.add(map);
        map = new HashMap<>();
        map.put("fragment_music2_listView_ImageView", R.mipmap.recent_music);
        map.put("fragment_music2_listView_TextView", "最近播放");
        map.put("R.id.fragment_music2_listView_smallTextView", "(0)");
        map.put("line","1");
        listItem.add(map);
        map = new HashMap<>();
        map.put("fragment_music2_listView_ImageView", R.mipmap.download_manager);
        map.put("fragment_music2_listView_TextView", "下载管理");
        map.put("R.id.fragment_music2_listView_smallTextView", "(0)");
        map.put("line","1");
        listItem.add(map);
         map = new HashMap<>();
        map.put("fragment_music2_listView_ImageView", R.mipmap.my_songer);
        map.put("fragment_music2_listView_TextView", "我的歌手");
        map.put("R.id.fragment_music2_listView_smallTextView", "(0)");
        map.put("line","1");
        listItem.add(map);
        listView.setDivider(null);
        SimpleAdapter sim=new SimpleAdapter(this.getContext(),listItem,R.layout.fragment_music2_simplelistview,new String[]{"fragment_music2_listView_ImageView","fragment_music2_listView_TextView","R.id.fragment_music2_listView_smallTextView","line"},new int[]{R.id.fragment_music2_listView_ImageView,R.id.fragment_music2_listView_TextView,R.id.fragment_music2_listView_smallTextView,R.id.listLine});
        listView.setAdapter(sim);

        strings=new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");


    }


    @Override
    public void dropMenuItemClick(int position, int controlId) {
        if(controlId==dropMenu.getId()){
            Toast.makeText(getContext(),"xxx",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void arrowStatus(boolean status, int controlId) {

    }
}
