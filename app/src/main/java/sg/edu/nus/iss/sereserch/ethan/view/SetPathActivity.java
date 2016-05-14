package sg.edu.nus.iss.sereserch.ethan.view;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.radaee.reader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.sereserch.ethan.controller.ControlFactory;
import sg.edu.nus.iss.sereserch.ethan.controller.MainControl;
import sg.edu.nus.iss.sereserch.ethan.controller.SetPathControl;

/**
 * Created by A0136363H on 8/14/2015.
 */
public class SetPathActivity extends ListActivity {

    private List<String> item = null;
    private List<String> path = null;
    private String root = "/mnt/sdcard/";
    private TextView myPath;
    public String newPath = null;
    public SetPathControl spc = ControlFactory.getInstance().getSetPathControl();
    private static String imagefilename = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpath);
        myPath = (TextView) findViewById(R.id.path);



        getDir(root);


    }

    private void getDir(String dirPath) {


        myPath.setText("Location:" + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();
        if (!dirPath.equals(root)) {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            path.add(file.getPath());
            if (file.isDirectory())
                item.add(file.getName() + "/");
            else
                item.add(file.getName());
        }
        ArrayAdapter<String> fileList =
                new ArrayAdapter<String>(this, R.layout.row, item);
        setListAdapter(fileList);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        final File file = new File(path.get(position));
        if (file.isDirectory()) {
            if (file.canRead())
                getDir(path.get(position));
            else {
                new AlertDialog.Builder(this)
                        //.setIcon(R.drawable.icon)
                        .setTitle("[" + file.getName() + "] folder can't be read!")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).show();
            }


            new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.icon)
                    .setTitle("Set Folder")
                    .setMessage("Do you want to set [" + file.getName() + "] as the new path?. Click cancel to select the sub folder ")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    newPath = file.getPath();
                                    spc.settingPreference(newPath);
                                    callImageView();

                                }


                            }
                    )
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();


        } else {

            newPath = file.getName();
            new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.icon)
                    .setTitle("Cannot set a single File as Path [" + file.getName() + "]")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();





           /* new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.icon)
                    .setTitle("[" + file.getName() + "]")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                }
                            }).show();
                            */


        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    public void callImageView() {
        MainControl mc = ControlFactory.getMainControl();
        mc.onClickImageView();
    }

}
