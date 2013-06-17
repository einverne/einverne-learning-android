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
        
        // ����
        ArrayList<Category> listData = getData();
        
        mCustomBaseAdapter = new CategoryAdapter(getBaseContext(), listData);
        
        // ��������ListView��
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
	 * ������������
	 */
	private ArrayList<Category> getData() {
		ArrayList<Category> listData = new ArrayList<Category>();
        Category categoryOne = new Category("·�˼�");
        categoryOne.addItem("������");
        categoryOne.addItem("�Ա�ɽ");
        categoryOne.addItem("���¸�");
        categoryOne.addItem("������");
        Category categoryTwo = new Category("�¼���");
        categoryTwo.addItem("**̰��");
        categoryTwo.addItem("**����");
        Category categoryThree = new Category("�鼮��");
        categoryThree.addItem("10��ѧ��***");
        categoryThree.addItem("**��ȫ");
        categoryThree.addItem("**�ؼ�");
        categoryThree.addItem("**����");
        categoryThree.addItem("10��ѧ��***");
        categoryThree.addItem("10��ѧ��***");
        categoryThree.addItem("10��ѧ��***");
        categoryThree.addItem("10��ѧ��***");
        Category categoryFour = new Category("�鼮��");
        categoryFour.addItem("����");
        categoryFour.addItem("���");
        categoryFour.addItem("����");
        categoryFour.addItem("�Ϻ�");
        categoryFour.addItem("����");
        categoryFour.addItem("����");
        categoryFour.addItem("����");
        categoryFour.addItem("ɽ��");
        categoryFour.addItem("����");
        
        listData.add(categoryOne);
        listData.add(categoryTwo);
        listData.add(categoryThree);
        listData.add(categoryFour);
        
		return listData;
	}

    
    
}