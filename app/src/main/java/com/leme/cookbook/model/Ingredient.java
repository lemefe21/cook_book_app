package com.leme.cookbook.model;

import android.os.Parcelable;

import java.io.Serializable;

public class Ingredient implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
