// Copyright (c) Stefan Botzenhart. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.
package one.flak.datafeeder;

public class SafeCsvGetter {

    public static long getLong(String element) {
        if(isNull(element)) {
            return 0L;
        }

        return Long.parseLong(element);
    }

    public static double getDouble(String element) {
        if(isNull(element)) {
            return 0.0;
        }

        return Double.parseDouble(element);
    }

    public static int getInt(String element) {
        if(isNull(element)) {
            return 0;
        }

        return Integer.parseInt(element);
    }


    private static boolean isNull(String element) {
        if(element == null) {
            return true;
        }

        if(element.isEmpty()) {
            return true;
        }

        return false;
    }

}
