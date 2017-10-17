package newsongs.fr.newsongs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FriendsActivity extends BaseActivity {

    ListView lv;
    ArrayList prgmName;

    public static int [] prgmImages={R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp};
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));
    }
}
