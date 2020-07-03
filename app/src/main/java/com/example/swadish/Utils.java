package com.example.swadish;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.AllPermission;
import java.util.ArrayList;

public class Utils {
    private static int ID = 0;
    private static int orderID=0;
    public static final String ALL_ITEMS_KEY = "all_items";
    public static final String DB_NAME = "fake_database";
    public static final String CART_ITEMS_KEY = "cart items";
    private static Gson gson = new Gson();
    private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();

    public static void initSharedPrefernces(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null == allItems) {
            initAllItems(context);
        }
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private static void initAllItems(Context context) {
        ArrayList<GroceryItem> allItems = new ArrayList<>();
        GroceryItem milk = new GroceryItem("Milk", "Milk is a white, nutrient-rich liquid food produced in the mammary glands of mammals. It is the primary source of nutrition for infant mammals (including humans who are breastfed) before they are able to digest other types of food."
                , "https://www.psdmockups.com/wp-content/uploads/2019/12/Milkshake-Cup-Dome-Lid-PSD-Mockup.jpg",
                "Breakfast", 2.3, 8);
        allItems.add(milk);
        GroceryItem bread = new GroceryItem("Bread", "Bread is a type of baked food. It is mainly made from dough, which is made mainly from flour and water. Usually, salt and yeast are added. Bread is often baked in an oven. ... Leavened bread is made by adding yeast or other leavening to the dough."
                , "https://www.psdmockups.com/wp-content/uploads/2018/10/Fresh-Bread-Loaf-Branded-Bag-Packaging-PSD-Mockup.jpg",
                "Breakfast", 5.4, 10);
        allItems.add(bread);
        GroceryItem butter = new GroceryItem("Butter", "Butter is a dairy product made from the fat and protein components of milk or cream. ... Most frequently made from cow's milk, butter can also be manufactured from the milk of other mammals, including sheep, goats, buffalo, and yaks. It is made by churning milk or cream to separate the fat globules from the buttermilk.\n"
                , "https://www.psdmockups.com/wp-content/uploads/2020/03/Isometric-Butter-Block-Packaging-PSD-Mockup.jpg",
                "Breakfast", 8.99, 15);
        allItems.add(butter);
        GroceryItem pringles = new GroceryItem("Pringles", "Pringles is an American brand of potato and wheat-based stackable snack chips. ... Originally developed by Procter & Gamble (P&G) in 1967, and marketed as \"Pringle's Newfangled Potato Chips\", the brand was sold to Kellogg's in 2012. As of 2011 Pringles are sold in more than 140 countries."
                , "https://www.psdmockups.com/wp-content/uploads/2018/11/40g-Tube-Canister-Chips-PSD-Mockup.jpg",
                "Snacks", 7.3, 16);
        allItems.add(pringles);
        GroceryItem frenchfries = new GroceryItem("French Fries", "French fries, or simply fries (North American English), chips (British and Commonwealth English, Hiberno-English), finger chips (Indian English), or french-fried potatoes, are batonnet or allumette-cut deep-fried potatoes. ... A baked variant, oven chips, uses less oil or no oil."
                , "https://www.psdmockups.com/wp-content/uploads/2019/09/Fast-Food-French-Fries-Packaging-PSD-Mockup.jpg",
                "Snacks", 15.4, 17);
        allItems.add(frenchfries);
        GroceryItem candy = new GroceryItem("Candy", "Candy, also called sweets or lollies, is a confection that features sugar as a principal ingredient. The category, called sugar confectionery, encompasses any sweet confection, including chocolate, chewing gum, and sugar candy."
                , "https://www.psdmockups.com/wp-content/uploads/2019/05/Chocolate-Candy-Bar-Wrapper-Branding-PSD-Mockup.jpg",
                "Snacks", 2.0, 20);
        allItems.add(candy);
        GroceryItem cake = new GroceryItem("Cake", "Cake is a form of sweet food made from flour, sugar, and other ingredients, that is usually baked. ... The most commonly used cake ingredients include flour, sugar, eggs, butter or oil or margarine, a liquid, and leavening agents, such as baking soda or baking powder."
                , "https://www.psdmockups.com/wp-content/uploads/2019/10/Cupcake-Sealed-Coffee-Cup-PSD-Mockup.jpg",
                "Breakfast", 18.6, 16);
        allItems.add(cake);
        GroceryItem soda = new GroceryItem("Soda", "A soft drink is a drink that usually contains carbonated water, a sweetener, and a natural or artificial flavoring. The sweetener may be a sugar, high-fructose corn syrup, fruit juice, a sugar substitute, or some combination of these."
                , "https://www.psdmockups.com/wp-content/uploads/2020/05/Aluminium-Drink-Soda-Can-PSD-Mockup.jpg",
                "Drink", 14, 17);
        allItems.add(soda);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();
    }

    public static ArrayList<GroceryItem> getAllItems(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        return allItems;
    }

    public static void changeRate(Context context, int itemId, int newRate) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == itemId) {
                    i.setRate(newRate);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    public static void addReview(Context context, Review review) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = i.getReviews();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }

    }

    public static ArrayList<Review> getReviewsByID(Context context, int itemId) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            for (GroceryItem i : allItems) {
                if (i.getId() == itemId) {
                    ArrayList<Review> reviews = i.getReviews();
                    return reviews;
                }
            }
        }
        return null;

    }

    public static void addItemToCart(Context context, GroceryItem item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        cartItems.add(item);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.commit();
    }

    public static ArrayList<GroceryItem> getCartItem(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);
        return cartItems;
    }

    public static ArrayList<GroceryItem> searchForItems(Context context, String text) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    items.add(item);
                }

                String[] names = item.getName().split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (text.equalsIgnoreCase(names[i])) {
                        boolean deostExist = false;
                        for (GroceryItem j : items) {
                            if (j.getId() == item.getId()) {
                                deostExist = true;
                            }
                        }
                        if (!deostExist) {
                            items.add(item);
                        }
                    }
                }
            }
            return items;
        }
        return null;
    }

    public static ArrayList<String> getCategory(Context context) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<String> categories = new ArrayList<>();
            for (GroceryItem item : allItems) {
                boolean doesExist = false;
                for (String s : categories) {
                    if (item.getCategory().equals(s)) {
                        doesExist = true;
                    }
                }
                if (!doesExist) {
                    categories.add(item.getCategory());
                }
            }
            return categories;

        }
        return null;
    }

    public static ArrayList<GroceryItem> getItemsByCategory(Context context, String category) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getCategory().equals(category)) {
                    items.add(item);
                }
            }
            return items;
        }
        return null;
    }
    public static void deleteItemsFromCart(Context context,GroceryItem item){
        ArrayList<GroceryItem> cartItems=getCartItem(context);
        if(null!=cartItems){
            ArrayList<GroceryItem> newItems=new ArrayList<>();
            for(GroceryItem i:cartItems){
                if(i.getId()!=item.getId()){
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }
    public static void clearCartItems(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY);
        editor.commit();
    }
    public static void increasePopularityPoint(Context context,GroceryItem item,int points){
        ArrayList<GroceryItem> allItems=getAllItems(context);
        if(null!=allItems){
            ArrayList<GroceryItem> newItems=new ArrayList<>();
            for(GroceryItem i:allItems){
                if(i.getId()==item.getId()){
                    i.setPopularityPoint(i.getPopularityPoint()+points);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }
            SharedPreferences sharedPreferences=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(ALL_ITEMS_KEY);
            Gson gson=new Gson();
            editor.putString(ALL_ITEMS_KEY,gson.toJson(newItems));
            editor.commit();
        }
    }


    public static int getID() {
        ID++;
        return ID;
    }

    public static int getOrderID() {
        orderID++;
        return orderID;
    }

    public static String getLicences(){
        String licenses="";
        licenses+="Gson\n";
        licenses+="Copyright 2008 Google Inc.\n";
        licenses+="Glide\n";
        licenses+="Licenses for everything not in third_party and not otherwise marked\n";
        licenses+="Retrofit\n";
        licenses+="Copyright 2013 Square,Inc \n";
        return licenses;
    }
}
