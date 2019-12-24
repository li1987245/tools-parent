package io.github.tools.commom.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lazy<T> {
    private transient Supplier<T> supplier;
    private volatile T value;

    /**
     * 持有supplier函数，通过函数实现延迟加载，只有在调用get时，才会真正执行函数
     * @param supplier
     */
    public Lazy(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    /**
     * 工厂方法
     *
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    public <R> Lazy<R> map(Function<T, R> mapper) {
        //this为当前对象，用Lazy包装后返回新对象，并持有当前对象，eg (i) -> i+1; ,i为当前对象get的结果，map返回结果为延迟执行的i+1
        return new Lazy<>(() -> mapper.apply(this.get()));

    }

    public <R> Lazy<R> flatMap(Function<T, Lazy<R>> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()).get());
    }

    public Lazy<Optional<T>> filter(Predicate<T> predicate) {
        return new Lazy<>(() -> Optional.of(get()).filter(predicate));
    }

    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = Objects.requireNonNull(supplier.get());
                    supplier = null;
                }
            }
        }
        return value;
    }
}