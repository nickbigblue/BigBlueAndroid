package com.bigblueocean.nick.bigblueocean.helpers;
import android.content.Context;
import android.graphics.Color;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by nick on 12/9/17.
 */

public class SweetAlertHelper {

    public SweetAlertDialog createSweetDialog(Context context, String type, String title, int stringID, String confirmText){
        int alertType = 0;
        switch(type){
            case "Normal":
                alertType = SweetAlertDialog.NORMAL_TYPE;
            case "Success":
                alertType = SweetAlertDialog.SUCCESS_TYPE;
            case "Error":
                alertType = SweetAlertDialog.ERROR_TYPE;
            case "Progress":
                alertType = SweetAlertDialog.PROGRESS_TYPE;
            case "Warning":
                alertType = SweetAlertDialog.WARNING_TYPE;
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context, alertType);
        dialog.setTitleText(title);
        dialog.setContentText(context.getResources().getString(stringID));
        dialog.setConfirmText(confirmText);
        return dialog;
    }

    public SweetAlertDialog createSweetDialog(Context context, String type, String title, String content, String confirmText){
        int alertType = 0;
        switch(type){
            case "Normal":
                alertType = SweetAlertDialog.NORMAL_TYPE;
                break;
            case "Success":
                alertType = SweetAlertDialog.SUCCESS_TYPE;
                break;
            case "Error":
                alertType = SweetAlertDialog.ERROR_TYPE;
                break;
            case "Progress":
                alertType = SweetAlertDialog.PROGRESS_TYPE;
                break;
            case "Warning":
                alertType = SweetAlertDialog.WARNING_TYPE;
                break;
        }
        SweetAlertDialog dialog = new SweetAlertDialog(context, alertType);
        dialog.setTitleText(title);
        dialog.setContentText(content);
        dialog.setConfirmText(confirmText);
        return dialog;
    }

    public SweetAlertDialog createProgressSweetDialog(Context context){
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Loading...");
        dialog.getProgressHelper().setRimColor(Color.DKGRAY);
        dialog.getProgressHelper().setBarColor(Color.LTGRAY);
        dialog.setCancelable(false);
        return dialog;
    }
}
