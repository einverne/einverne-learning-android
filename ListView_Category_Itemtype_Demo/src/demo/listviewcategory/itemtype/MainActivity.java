package demo.listviewcategory.itemtype;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

    private CategoryAdapter mCustomBaseAdapter;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView listView = (ListView) findViewById(R.id.listView1);
        
        // 数据
        ArrayList<Category> listData = getData();
        
        mCustomBaseAdapter = new CategoryAdapter(getBaseContext(), listData);
        
        // 适配器与ListView绑定
        listView.setAdapter(mCustomBaseAdapter);
        
        listView.setOnItemClickListener(new ItemClickListener());
    }

    
    private class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(getBaseContext(),  (String)mCustomBaseAdapter.getItem(position),
					Toast.LENGTH_SHORT).show();
		}
    	
    }
    
    
	/**
	 * 创建测试数据
	 */
	private ArrayList<Category> getData() {
		ArrayList<Category> listData = new ArrayList<Category>();
        Category categoryOne = new Category("路人甲");
        categoryOne.addItem("马三立");
        categoryOne.addItem("赵本山");
        categoryOne.addItem("郭德纲");
        categoryOne.addItem("周立波");
        Category categoryTwo = new Category("事件乙");
        categoryTwo.addItem("**贪污");
        categoryTwo.addItem("**照门");
        Category categoryThree = new Category("书籍丙");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("**大全");
        categoryThree.addItem("**秘籍");
        categoryThree.addItem("**宝典");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("10天学会***");
        categoryThree.addItem("10天学会***");
        Category categoryFour = new Category("书籍丙");
        categoryFour.addItem("河南");
        categoryFour.addItem("天津");
        categoryFour.addItem("北京");
        categoryFour.addItem("上海");
        categoryFour.addItem("广州");
        categoryFour.addItem("湖北");
        categoryFour.addItem("重庆");
        categoryFour.addItem("山东");
        categoryFour.addItem("陕西");
        
        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);
        listData.add(categoryFour);
        
		return listData;
	}

    
    
}