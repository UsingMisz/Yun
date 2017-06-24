package zzy.com.zidingyi.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.List;

import main.zzy.com.yun.R;


/**
 * Created by z on 2016/8/10.
 */
public class DropMenu extends RelativeLayout {

	private Context context;
	View view;
	private TextView tvTitle;
	private ImageView ivArrow;

	private String text;
	private int src;
	private List<String> dataSourceList;
	private PopupWindow popupWindow;

	//是否第一次进行onDraw，因为系统onDraw是在不断的进行刷新的，所以你自己要写个判断
	private boolean isFirstOnDraw=true;

	//当没数时，点击dropMenu的时候箭头改变的标志
	private boolean arrowChange=false;

	//点击dropmenu显示的单元格的相应事件
	private DropMenuClickInterface dropMenuClickInterface;
	private DroupMenuArrowStatusInterface droupMenuArrowStatusInterface;

	//设置显示的箭头图标
	private int downArrowId=R.mipmap.arrow_right_height,upArrowId=R.mipmap.arrow_down_hight;


	public DropMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		this.context=context;

		TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.dropmrnu);
		text=typedArray.getString(R.styleable.dropmrnu_android_text);

		//控件初始化
		view= LayoutInflater.from(context).inflate(R.layout.droupmenu_title_item, null);

		tvTitle= (TextView) view.findViewById(R.id.drop_menu_tv_title);
		if (!TextUtils.isEmpty(text)){
			tvTitle.setText(text);
		}

		ivArrow= (ImageView) view.findViewById(R.id.drop_menu_iv_arrow);

	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirstOnDraw){
			//控件初始化
			init();
			isFirstOnDraw=false;
		}

	}

	private void init(){

		LayoutParams layoutParams=null;
		if (getWidth()==0 || getHeight()==0){
			layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}else {
			layoutParams=new LayoutParams(getWidth(),getHeight());
		}
		view.setLayoutParams(layoutParams);
		changeImageArrow(downArrowId);
		addView(view);


		//title的点击触发事件
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dataSourceList != null && dataSourceList.size() > 0) {
					showPopupWindow(v);
					changeImageArrow(upArrowId);
				} else {
					//Toast.makeText(context, "暂无数据，请您稍等", Toast.LENGTH_SHORT).show();
					if (!arrowChange) {
						changeImageArrow(upArrowId);
						arrowChange = true;
					} else {
						changeImageArrow(downArrowId);
						arrowChange = false;
					}

					if (droupMenuArrowStatusInterface != null) {
						droupMenuArrowStatusInterface.arrowStatus(arrowChange, getId());
					}

				}

			}
		});
	}


	//获得数据源
	public void setDataSouece(List<String> dataSouece){
		this.dataSourceList=dataSouece;
	}

	//设置title的内容
	public void setTitleText(String text){
		tvTitle.setText(text);
	}

	//设置title的颜色
	public void setTitleFontColor(int id){
		tvTitle.setTextColor(id);
	}

	//设置title的字体大小
	public void setTitleFontSize(float size){
		tvTitle.setTextSize(size);
	}

	//设置背景颜色
	public void setBackgroud(int color){
		setBackgroundColor(color);
	}

	public void setDownArrowIcon(int id){
		downArrowId=id;
	}

	public void setUpArrowId(int id){
		upArrowId=id;
	}

	public boolean getArrowStatus(){
		return arrowChange;
	}

	public void setArrowStatus(boolean status){
		arrowChange=status;
	}

	//获得箭头ImageView控件，便于代码中改变，箭头方向
	public ImageView getIvArrow(){
		return ivArrow;
	}

	//关闭popwindows
	public void closePopwindows(){
		if (popupWindow!=null && popupWindow.isShowing()){
			popupWindow.dismiss();
		}
	}

	public void setDropMenuClickInterface(DropMenuClickInterface dropMenuClickInterface){
		this.dropMenuClickInterface=dropMenuClickInterface;
	}

	public void setDroupMenuArrowStatusInterface(DroupMenuArrowStatusInterface droupMenuArrowStatusInterface){
		this.droupMenuArrowStatusInterface=droupMenuArrowStatusInterface;
	}


	//显示popwindows
	private void showPopupWindow(View view) {

		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(context).inflate(R.layout.dropmenu_popupwindow, null);

		ListView listView= (ListView) contentView.findViewById(R.id.dropmenu_listview_menu);
		listView.setAdapter(new DropMenuAdpter());



		popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

		popupWindow.setTouchable(true);
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//箭头向下
				changeImageArrow(downArrowId);
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});

		// 设置好参数之后再show
		popupWindow.showAsDropDown(view);
	}


	class DropMenuAdpter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataSourceList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataSourceList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView==null){
				viewHolder=new ViewHolder();
				convertView= LayoutInflater.from(context).inflate(R.layout.dropmenu_listview_item,null);
				viewHolder.tvName= (TextView) convertView.findViewById(R.id.droupemu_listview_item_tv);
				viewHolder.ivIcoMake= (ImageView) convertView.findViewById(R.id.droupemu_listview_item_iv_select);

				convertView.setTag(viewHolder);
			}else {
				viewHolder= (ViewHolder) convertView.getTag();
			}

			viewHolder.tvName.setText(dataSourceList.get(position));

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					closePopwindows();
					//调用给该接口，方便acyivity回调
					if (dropMenuClickInterface!=null){
						dropMenuClickInterface.dropMenuItemClick(position,getId());
					}
				}
			});

			return convertView;
		}
	}

	class ViewHolder{
		TextView tvName;
		ImageView ivIcoMake;
	}


	private void changeImageArrow(int id){
		Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), id);
		ivArrow.setImageBitmap(bitmap);
	}

}
