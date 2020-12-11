package com.dbDesign.Jacky.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListUtil
 * @Author Jacky
 * @Description
 **/
public class ListUtil {
    private ListUtil(){}

    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
