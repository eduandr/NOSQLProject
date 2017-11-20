/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.allandroidprojects.ecomsample.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.ecomsample.Product;
import com.allandroidprojects.ecomsample.R;
import com.allandroidprojects.ecomsample.product.ItemDetailsActivity;
import com.allandroidprojects.ecomsample.startup.MainActivity;
import com.allandroidprojects.ecomsample.utility.Connection;
import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.allandroidprojects.ecomsample.utility.MyDialogFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class ImageListFragment extends Fragment {

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
    public static final String STRING_NAME = "Name";
    public static final String STRING_CONTENT = "Content";
    public static final String STRING_PRICE = "Price";
    public static final String STRING_SHORT_DESC = "Short_desc";
    private static MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
      /*  if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }*/
        Connection con = new Connection();
        JSONArray obj = null;
        //String[] items=null;
        //Log.i("HICE:", String.valueOf(ImageListFragment.this.getArguments().getInt("type")));
        if (ImageListFragment.this.getArguments().getInt("type") == 1){
            //items =ImageUrlUtils.getOffersUrls();
            try {
                obj = con.oferQuery("00");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (ImageListFragment.this.getArguments().getInt("type") == 2){
            //items =ImageUrlUtils.getElectronicsUrls();
            try {
                obj = con.oferQuery("C1");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (ImageListFragment.this.getArguments().getInt("type") == 3){
            //items =ImageUrlUtils.getLifeStyleUrls();
            try {
                obj = con.oferQuery("C2");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (ImageListFragment.this.getArguments().getInt("type") == 4){
            //items =ImageUrlUtils.getHomeApplianceUrls();
            try {
                obj = con.oferQuery("C3");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (ImageListFragment.this.getArguments().getInt("type") == 5){
            //items =ImageUrlUtils.getBooksUrls();
            try {
                obj = con.oferQuery("C4");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if (ImageListFragment.this.getArguments().getInt("type") == 6){
            try {
                obj = con.oferQuery("C4");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //items = ImageUrlUtils.getImageUrls();
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, obj));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private String[] mValues;
        private JSONArray mValues1;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final TextView item_name;
            public final TextView item_desc;
            public final TextView item_price;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;

            public ViewHolder(View view) throws InterruptedException, ExecutionException, JSONException {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                item_name = (TextView) view.findViewById((R.id.item_name));
                item_desc = (TextView) view.findViewById((R.id.item_desc));
                item_price = (TextView) view.findViewById((R.id.item_price));
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);



            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, JSONArray items) {
            mValues1 = items;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            try {
                return new ViewHolder(view);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/

                Uri uri = null;
                try {
                    JSONObject obj = mValues1.getJSONObject(position).getJSONObject("prod"+String.valueOf(position+1));
                    uri = Uri.parse(obj.getString("CIMGURL"));
                    holder.mImageView.setImageURI(uri);
                    holder.item_name.setText(obj.getString("CITNAME")); // OJO
                    holder.item_desc.setText(obj.getString("CITDESC")); // OJO
                    holder.item_price.setText(obj.getString("CITPRIC")); // OJO

                } catch (JSONException e) {
                    if (mValues1 == null){
                        DialogFragment dialog = new MyDialogFragment("Error de conexión","No se pudo establecer conexión con el servidor",mActivity);
                        dialog.show(mActivity.getSupportFragmentManager(), "MyDialogFragmentTag");
                    }
                    e.printStackTrace();
                }

                holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                        JSONObject obj = null;
                        try {
                            obj = mValues1.getJSONObject(position).getJSONObject("prod"+String.valueOf(position+1));
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        try {
                            intent.putExtra(STRING_IMAGE_URI, obj.getString("CIMGURL"));
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                        intent.putExtra(STRING_IMAGE_POSITION, position);
                        try {
                            intent.putExtra(STRING_NAME, obj.getString("CITNAME"));
                            intent.putExtra(STRING_CONTENT, obj.getString("CDESCRI"));
                            intent.putExtra(STRING_PRICE, obj.getString("CITPRIC"));
                            intent.putExtra(STRING_SHORT_DESC, obj.getString("CITDESC"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mActivity.startActivity(intent);

                    }
                });
                //Set click action for wishlist
                holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Product loProd = new Product();
                        ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                        JSONObject obj = null;
                        String img = "";
                        String name = "";
                        String desc = "";
                        String price = "";
                        String cdescri = "";
                        try {
                            obj = mValues1.getJSONObject(position).getJSONObject("prod"+String.valueOf(position+1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            img = obj.getString("CIMGURL");
                            name = obj.getString("CITNAME");
                            desc = obj.getString("CITDESC");
                            price = obj.getString("CITPRIC");
                            cdescri = obj.getString("CDESCRI");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loProd.setImg(img);
                        loProd.setDesc(desc);
                        loProd.setName(name);
                        loProd.setPrice(price);
                        loProd.setShortDesc(cdescri);

                        imageUrlUtils.addWishlistImageUri(loProd);
                        holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                        notifyDataSetChanged();
                        Toast.makeText(mActivity, "Producto agregado a la lista de desaeados.", Toast.LENGTH_SHORT).show();
                    }
                });
        }

        @Override
        public int getItemCount() {
            return mValues1.length();
        }
    }
}
