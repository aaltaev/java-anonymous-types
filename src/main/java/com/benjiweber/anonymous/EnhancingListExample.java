package com.benjiweber.anonymous;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.benjiweber.anonymous.AnonymousTypes.with;
import static java.util.Arrays.asList;

public class EnhancingListExample {

    public static void main(String... args) {
        List<String> stringList = asList("alpha", "bravo");

        with((ForwardingList<String> & Mappable<String>)() -> stringList, list -> {
            List<String> strings = list.map(String::toUpperCase);
            strings.forEach(System.out::println);
        });

    }

    interface Mappable<T> extends DelegatesTo<List<T>> {
        default <R, U extends ForwardingList<R> & Mappable<R>> U map(Function<T, R> mapper) {
            return (U) (ForwardingList<R> & Mappable<R>) () -> delegate().stream()
                .map(mapper)
                .collect(Collectors.toList());
        }
    }

}
