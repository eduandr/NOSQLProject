package com.allandroidprojects.ecomsample.options;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.product.ItemDetailsActivity;
import com.allandroidprojects.ecomsample.startup.MainActivity;
import com.allandroidprojects.ecomsample.utility.Connection;
import com.allandroidprojects.ecomsample.utility.MyDialogFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SearchResultActivity extends AppCompatActivity {
    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    public static final String STRING_NAME = "Name";
    public static final String STRING_CONTENT = "Content";
    public static final String STRING_PRICE = "Price";
    public static final String STRING_SHORT_DESC = "Short_desc";
    private static SearchResultActivity mActivity;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mActivity = (SearchResultActivity)this;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.getItem(0);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                clearViews();
                Connection con = new Connection();
                JSONArray laJson = null;
                try {
                    laJson = con.busqueda(newText);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    if (laJson != null)
                    {
                        for (int i = 0; i< laJson.length(); i++){
                            JSONObject obj = laJson.getJSONObject(i);
                            JSONObject prod = obj.getJSONObject("prod" + String.valueOf(i + 1));
                            //Toast.makeText(getBaseContext(),String.valueOf(laJson.length()),Toast.LENGTH_SHORT).show();
                            String lcItem_name = prod.getString("CITNAME");
                            String lcItem_price = prod.getString("CITPRIC");
                            String lcItem_img = prod.getString("CIMGURL");
                            String lcItem_desc = prod.getString("CDESCRI");
                            String lcItem_shortdesc = prod.getString("CITDESC");
                            setContentDeuda1(lcItem_name,lcItem_price, lcItem_desc, lcItem_shortdesc, lcItem_img);
                        }
                    }

                } catch (JSONException e) {
                    DialogFragment dialog = new MyDialogFragment("Error de conexión","No se pudo establecer conexión con el servidor",mActivity);
                    dialog.show(getSupportFragmentManager(),"MyDialogFragmentTag");
                    e.printStackTrace();
                }
//              }
                return true;
            }

            public void callSearch(String query) {
                //Do searching
            }

        });
        searchItem.expandActionView();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

    }
    public void clearViews()
    {
        TableLayout layout = (TableLayout)findViewById(R.id.tlayout1);
        layout.removeAllViews();
    }
    public void setContentDeuda1(final String p_item_name, final String p_item_price, final String p_item_desc, final String p_item_shortdesc, final String p_imgurl)
    {
        View v = LayoutInflater.from(this).inflate(R.layout.rowsearch, null);
        TableLayout layout = (TableLayout)findViewById(R.id.tlayout1);
        final TextView item_name1 = (TextView)v.findViewById(R.id.item_name);
        TextView item_price1 = (TextView)v.findViewById(R.id.item_price);
        SimpleDraweeView mImageView = (SimpleDraweeView) v.findViewById(R.id.image1);
        Uri uri = Uri.parse(p_imgurl);
        mImageView.setImageURI(uri);
        item_name1.setText(p_item_name);
        item_price1.setText(p_item_price);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ItemDetailsActivity.class);
                TextView item_name1 = (TextView)v.findViewById(R.id.item_name);
                intent.putExtra(STRING_IMAGE_URI, p_imgurl);
                intent.putExtra(STRING_NAME, p_item_name);
                intent.putExtra(STRING_CONTENT, p_item_desc);
                intent.putExtra(STRING_PRICE, p_item_price);
                intent.putExtra(STRING_SHORT_DESC,p_item_shortdesc );
                intent.putExtra(STRING_IMAGE_POSITION, 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
            }
        });
        layout.addView(v);

    }


}
