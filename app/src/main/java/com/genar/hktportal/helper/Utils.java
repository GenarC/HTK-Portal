package com.genar.hktportal.helper;

import android.app.Activity;
import android.content.Intent;

public class Utils {

    public static int BarkodOperatorRequest = 1;
    public static int BarkodMakineRequest = 2;

    private static String currentRegistryNo;

    public static String getCurrentRegistryNo() {
        return currentRegistryNo;
    }

    public static void setCurrentRegistryNo(String currentRegistryNo) {
        Utils.currentRegistryNo = currentRegistryNo;
    }

    public static void startActivity(Activity activity, Class c){
        activity.startActivity(new Intent(activity,c));
        activity.finish();
    }
    public static void startActivityWithoutFinish(Activity activity, Class c){
        activity.startActivity(new Intent(activity,c));
    }

    public static void startActivityForResult(Activity activity, Class c, int requestCode){
        activity.startActivityForResult(new Intent(activity,c), requestCode);
    }


}
