package com.zybooks.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsUtil {

    public static boolean hasPermissions(final Activity activity, final String permission,
                                         int rationaleMessageId, final int requestCode) {
        // See if permission is granted
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Explain why permission needed?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                // Show why permission is needed
                showPermissionRationaleDialog(activity, rationaleMessageId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Request permission again
                        ActivityCompat.requestPermissions(activity,
                                new String[] { permission }, requestCode);
                    }
                });
            }
            else {
                // Request permission
                ActivityCompat.requestPermissions(activity,
                        new String[] { permission }, requestCode);
            }
            return false;
        }
        return true;
    }

    private static void showPermissionRationaleDialog(Activity activity, int messageId,
                                                      DialogInterface.OnClickListener onClickListener) {
        // Show dialog explaining why permission is needed
        new AlertDialog.Builder(activity)
                .setTitle(R.string.permission_needed)
                .setMessage(messageId)
                .setPositiveButton(R.string.ok, onClickListener)
                .create()
                .show();
    }
}
