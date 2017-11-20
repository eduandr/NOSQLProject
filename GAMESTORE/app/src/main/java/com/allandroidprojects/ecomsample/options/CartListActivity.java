package com.allandroidprojects.ecomsample.options;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.ecomsample.MainActivity1;
import com.allandroidprojects.ecomsample.Product;
import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.Register_activity;
import com.allandroidprojects.ecomsample.notification.NotificationCountSetClass;
import com.allandroidprojects.ecomsample.product.ItemDetailsActivity;
import com.allandroidprojects.ecomsample.startup.MainActivity;
import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.allandroidprojects.ecomsample.utility.MyDialogFragment;
import com.allandroidprojects.ecomsample.utility.MyDialogFragment2;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.allandroidprojects.ecomsample.R.id.item_price;
import static com.allandroidprojects.ecomsample.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.allandroidprojects.ecomsample.fragments.ImageListFragment.STRING_IMAGE_URI;

public class CartListActivity extends AppCompatActivity {
    private static Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mContext = CartListActivity.this;

        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
        final ArrayList<Product> cartlistImageUri =imageUrlUtils.getCartListImageUri();
        //Show cart layout based on items

        TextView text_action_bottom2 = (TextView)findViewById(R.id.text_action_bottom2);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        CartListActivity.SimpleStringRecyclerViewAdapter cart = new CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri);
        recyclerView.setAdapter(cart);
        float sum = cart.calculateTotal();
        setCartLayout(sum);

        text_action_bottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogFragment2 myDialogFragment = new MyDialogFragment2("¿Estas Seguro?","¿Deseas procesar tu pedido?",(Activity) mContext);
                myDialogFragment.show(getSupportFragmentManager(), "MyDialogFragmentTag");
                //Toast.makeText(getBaseContext(),"holi",Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private static ArrayList<Product> mCartlistImageUri;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView item_name;
            public final TextView item_desc;
            public final TextView item_price;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove , mLayoutEdit;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
                mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                item_name= (TextView)view.findViewById(R.id.item_name);
                item_desc= (TextView)view.findViewById(R.id.item_desc);
                item_price= (TextView)view.findViewById(R.id.item_price);
            }
        }
        public float calculateTotal()
        {
            float sum = 0;
            for (int i = 0; i < mCartlistImageUri.size(); i ++)
            {
                String number  = mCartlistImageUri.get(i).getPrice().substring(0, mCartlistImageUri.get(i).getPrice().length() - 1);
                float num = Float.parseFloat(number);
                sum += num;

            }
            return sum;
        }
        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, ArrayList<Product> wishlistImageUri) {
            mCartlistImageUri = wishlistImageUri;
            mRecyclerView = recyclerView;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            final Uri uri = Uri.parse(mCartlistImageUri.get(position).getImg());
            holder.mImageView.setImageURI(uri);
            holder.item_name.setText(mCartlistImageUri.get(position).getName());
            holder.item_desc.setText(mCartlistImageUri.get(position).getShortDesc());
            //Log.i("DESC", mCartlistImageUri.get(position).getName());
            holder.item_price.setText(mCartlistImageUri.get(position).getPrice());

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI,mCartlistImageUri.get(position).getImg());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mContext.startActivity(intent);
                }
            });

           //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.removeCartListImageUri(position);
                    notifyDataSetChanged();
                    //Decrease notification count
                    MainActivity.notificationCountCart--;

                }
            });

            //Set click action
            holder.mLayoutEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCartlistImageUri.size();
        }

    }



    protected void setCartLayout(float sum){
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);
        TextView textView = (TextView) layoutCartPayments.findViewById(R.id.text_action_price1);
        textView.setText(String.valueOf(sum)+"$");
        if(MainActivity.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
