package io.github.tools.commom.utils;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LazyTest {
    @Test
    public void testMap() {
        Lazy<Optional<Integer>> lazy = Lazy.of(() -> compute(42))
                .map(this::compute)
                .flatMap(this::lazyCompute)
                .filter(v -> v > 0);

        System.out.println("-------------");
        System.out.println(lazy.get());
    }

    private Integer compute(int i) {
        System.out.println("compute...." + i);
        return i + 1;
    }

    private Lazy<Integer> lazyCompute(int i) {
        System.out.println("lazyCompute...." + i);
        return Lazy.of(() -> compute(i));
    }

}
