package com.allandroidprojects.ecomsample.product;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.ecomsample.MainActivity1;
import com.allandroidprojects.ecomsample.Product;
import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.fragments.ImageListFragment;
import com.allandroidprojects.ecomsample.fragments.ViewPagerActivity;
import com.allandroidprojects.ecomsample.notification.NotificationCountSetClass;
import com.allandroidprojects.ecomsample.options.CartListActivity;
import com.allandroidprojects.ecomsample.startup.MainActivity;
import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class ItemDetailsActivity extends AppCompatActivity {
    int imagePosition;
    Product loProd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        SimpleDraweeView mImageView = (SimpleDraweeView)findViewById(R.id.image1);
        TextView tvItem_name = (TextView)findViewById(R.id.item_name);
        TextView tvItem_dec = (TextView)findViewById(R.id.item_desc);
        TextView tvItem_price = (TextView)findViewById(R.id.item_price);
        TextView textViewAddToCart = (TextView)findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView)findViewById(R.id.text_action_bottom2);
        loProd = new Product();
        //Getting image uri from previous screen
        if (getIntent() != null) {
            loProd.setImg(getIntent().getStringExtra(ImageListFragment.STRING_IMAGE_URI));
            imagePosition = getIntent().getIntExtra(ImageListFragment.STRING_IMAGE_URI,0);
            loProd.setName(getIntent().getStringExtra(ImageListFragment.STRING_NAME));
            loProd.setDesc(getIntent().getStringExtra(ImageListFragment.STRING_CONTENT));
            loProd.setPrice(getIntent().getStringExtra(ImageListFragment.STRING_PRICE));
            loProd.setShortDesc(getIntent().getStringExtra(ImageListFragment.STRING_SHORT_DESC));
        }
        Uri uri = Uri.parse(loProd.getImg());
        mImageView.setImageURI(uri);
        tvItem_name.setText(loProd.getName());
        tvItem_dec.setText(loProd.getDesc());
        tvItem_price.setText(loProd.getPrice());
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                    intent.putExtra("position", imagePosition);
                    intent.putExtra("img",loProd.getImg());
                    startActivity(intent);

            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(loProd);
                Toast.makeText(ItemDetailsActivity.this,"Agregaste este producto al carrito.",Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addCartListImageUri(loProd);
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));

            }
        });

    }
}
