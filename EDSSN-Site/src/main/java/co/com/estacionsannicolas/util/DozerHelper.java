package co.com.estacionsannicolas.util;

import java.util.ArrayList;
import java.util.List;
import org.dozer.Mapper;

public class DozerHelper {

    public static <T, U> ArrayList<U> map(final Mapper mapper, final List<T> source, final Class<U> destType) {

        final ArrayList<U> dest = new ArrayList<>();

        for (T element : source) {
            if (element == null) {
                continue;
            }
            dest.add(mapper.map(element, destType));
        }

        List<U> s1 = new ArrayList<>();
        s1.add(null);
        dest.removeAll(s1);

        return dest;
    }
}