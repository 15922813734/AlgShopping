package com.alg.shopping.tools;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import org.xutils.x;

public class PermissionsChecker {
    // 判断权限集合
    public static boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }
    // 判断是否缺少权限
    private static boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(x.app(), permission) == PackageManager.PERMISSION_DENIED;
    }
}
