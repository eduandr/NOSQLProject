package com.allandroidprojects.ecomsample.utility;

import com.allandroidprojects.ecomsample.Product;

import java.util.ArrayList;

/**
 * Created by 06peng on 2015/6/24.
 */
public class ImageUrlUtils {
    static ArrayList<Product> wishlistImageUri = new ArrayList<>();
    static ArrayList<Product> cartListImageUri = new ArrayList<>();
    // Methods for Wishlist
    public void addWishlistImageUri(Product product) {
        this.wishlistImageUri.add(0,product);
    }

    public void removeWishlistImageUri(int position) {
        this.wishlistImageUri.remove(position);
    }

    public ArrayList<Product> getWishlistImageUri(){ return this.wishlistImageUri; }

    // Methods for Cart
    public void addCartListImageUri(Product wishlistImageUri) {
        this.cartListImageUri.add(0,wishlistImageUri);
    }

    public void removeCartListImageUri(int position) {
        this.cartListImageUri.remove(position);
    }

    public ArrayList<Product> getCartListImageUri(){ return this.cartListImageUri; }
}
