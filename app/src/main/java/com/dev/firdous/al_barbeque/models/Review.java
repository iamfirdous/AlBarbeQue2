package com.dev.firdous.al_barbeque.models;

import org.joda.time.DateTime;

/**
 * Created by firdous on 9/9/17.
 */

public class Review {

    private String reviewId;
    private String review;
    private int rating;
    private String uid;
    private String restaurantId;
    private DateTime dateTime;

    public Review() {
        dateTime = new DateTime();
    }

    public Review(String reviewId, String review, int rating, String uid, String restaurantId, DateTime dateTime) {
        this.reviewId = reviewId;
        this.review = review;
        this.rating = rating;
        this.uid = uid;
        this.restaurantId = restaurantId;
        this.dateTime = dateTime;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}