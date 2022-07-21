import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.io.File;

public class Generics {
    public static <T, G> List<G> fromArrayToList(T[] a, Function<T, G> mapperFunction) {
        return Arrays.stream(a)
                .map(mapperFunction)
                .collect(Collectors.toList());
    }
    public static void FileInputOutputAPI() {
    }

//    public static void main(String[] args)
//    {
//        //lets try to convert an array of integers to array of strings
//        Integer a[] = {1,2,3,4,5,6,7,8,9,10};
//        List<String> str = fromArrayToList(a,Object::toString);
//        //lets print and see
//        Iterator iter = str.iterator();
//        while(iter.hasNext()) {
//            System.out.println(iter.next());
//        }
//    }
}
