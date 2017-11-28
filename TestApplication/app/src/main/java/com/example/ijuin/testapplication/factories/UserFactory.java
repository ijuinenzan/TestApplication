package com.example.ijuin.testapplication.factories;

import android.net.Uri;

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
            userModel.setImageUrl(new FieldModel<String>(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString(), false));
        }
        else
        {
            userModel.setImageUrl(new FieldModel<String>("", false));
        }

        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null)
        {
            userModel.setDisplayName(new FieldModel<String>(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), false));
        }
        else
        {
            userModel.setDisplayName(new FieldModel<String>("", false));
        }
        userModel.setYearBorn(new FieldModel<Integer>(0, false));
        userModel.setGender(new FieldModel<Boolean>(MyUtils.MALE, false));
        userModel.setCity(new FieldModel<String>("", false));
        userModel.setCountry(new FieldModel<String>("", false));
        userModel.setWeight(new FieldModel<Float>(0.f, false));
        userModel.setHeight(new FieldModel<Float>(0.f, false));
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null)
        {
            userModel.setPhoneNumber(new FieldModel<String>(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), false));
        }
        else
        {
            userModel.setPhoneNumber(new FieldModel<String>("", false));
        }
        userModel.setFacebook(new FieldModel<String>("", false));
        userModel.setTwitter(new FieldModel<String>("", false));
        userModel.setAddress(new FieldModel<String>("", false));
        userModel.setJob(new FieldModel<String>("", false));
        userModel.setState("0");
        userModel.setIsFindingFemale(true);
        userModel.setIsFindingMale(true);
        userModel.setMinAge(0);
        userModel.setMaxAge(80);

        return userModel;
    }
}
