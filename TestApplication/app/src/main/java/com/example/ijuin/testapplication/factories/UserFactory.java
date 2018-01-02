package com.example.ijuin.testapplication.factories;

import com.example.ijuin.testapplication.models.FieldModel;
import com.example.ijuin.testapplication.models.UserModel;
import com.example.ijuin.testapplication.utils.MyUtils;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ijuin on 11/26/2017.
 */

public class UserFactory {
    public static UserModel createNewUser()
    {
        UserModel userModel = new UserModel();

        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
        {
            userModel.setImageUrl(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), false));
        }
        else
        {
            userModel.setImageUrl(new FieldModel<>("", false));
        }

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null)
        {
            userModel.setDisplayName(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), false));
        }
        else
        {
            userModel.setDisplayName(new FieldModel<>("", false));
        }
        userModel.setYearBorn(new FieldModel<>(0, false));
        userModel.setGender(new FieldModel<>(MyUtils.MALE, false));
        userModel.setCity(new FieldModel<>("", false));
        userModel.setCountry(new FieldModel<>("", false));
        userModel.setWeight(new FieldModel<>(0.f, false));
        userModel.setHeight(new FieldModel<>(0.f, false));
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null)
        {
            userModel.setPhoneNumber(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), false));
        }
        else
        {
            userModel.setPhoneNumber(new FieldModel<>("", false));
        }
        userModel.setFacebook(new FieldModel<>("", false));
        userModel.setTwitter(new FieldModel<>("", false));
        userModel.setAddress(new FieldModel<>("", false));
        userModel.setJob(new FieldModel<>("", false));
        userModel.setState("Not Finding");
        userModel.setIsFindingFemale(true);
        userModel.setIsFindingMale(true);
        userModel.setMinAge(0);
        userModel.setMaxAge(80);

        return userModel;
    }

    public static UserModel createNewUserFromTwitter(String twitterId)
    {
        UserModel userModel = new UserModel();

        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
        {
            userModel.setImageUrl(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), false));
        }
        else
        {
            userModel.setImageUrl(new FieldModel<>("", false));
        }

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null)
        {
            userModel.setDisplayName(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), false));
        }
        else
        {
            userModel.setDisplayName(new FieldModel<>("", false));
        }
        userModel.setYearBorn(new FieldModel<>(0, false));
        userModel.setGender(new FieldModel<>(MyUtils.MALE, false));
        userModel.setCity(new FieldModel<>("", false));
        userModel.setCountry(new FieldModel<>("", false));
        userModel.setWeight(new FieldModel<>(0.f, false));
        userModel.setHeight(new FieldModel<>(0.f, false));
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null)
        {
            userModel.setPhoneNumber(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), false));
        }
        else
        {
            userModel.setPhoneNumber(new FieldModel<>("", false));
        }
        userModel.setFacebook(new FieldModel<>("", false));
        userModel.setTwitter(new FieldModel<>("https://www.twitter.com/" + twitterId, false));
        userModel.setAddress(new FieldModel<>("", false));
        userModel.setJob(new FieldModel<>("", false));
        userModel.setState("Not Finding");
        userModel.setIsFindingFemale(true);
        userModel.setIsFindingMale(true);
        userModel.setMinAge(0);
        userModel.setMaxAge(80);

        return userModel;
    }

    public static UserModel createNewUserFromFacebook(String facebookId)
    {
        UserModel userModel = new UserModel();

        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
        {
            userModel.setImageUrl(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), false));
        }
        else
        {
            userModel.setImageUrl(new FieldModel<>("", false));
        }

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null)
        {
            userModel.setDisplayName(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), false));
        }
        else
        {
            userModel.setDisplayName(new FieldModel<>("", false));
        }
        userModel.setYearBorn(new FieldModel<>(0, false));
        userModel.setGender(new FieldModel<>(MyUtils.MALE, false));
        userModel.setCity(new FieldModel<>("", false));
        userModel.setCountry(new FieldModel<>("", false));
        userModel.setWeight(new FieldModel<>(0.f, false));
        userModel.setHeight(new FieldModel<>(0.f, false));
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null)
        {
            userModel.setPhoneNumber(new FieldModel<>(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), false));
        }
        else
        {
            userModel.setPhoneNumber(new FieldModel<>("", false));
        }
        userModel.setFacebook(new FieldModel<>("https://www.facebook.com/" + facebookId, false));
        userModel.setTwitter(new FieldModel<>("", false));
        userModel.setAddress(new FieldModel<>("", false));
        userModel.setJob(new FieldModel<>("", false));
        userModel.setState("Not Finding");
        userModel.setIsFindingFemale(true);
        userModel.setIsFindingMale(true);
        userModel.setMinAge(0);
        userModel.setMaxAge(80);

        return userModel;
    }
}
