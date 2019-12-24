package io.github.tools.commom.utils;

import org.junit.Test;

import java.math.BigInteger;

public class FactorialRecursionTest {
    @Test
    public void testRecursion(){
        System.out.println(factorialRecursion0(1_000_000));
    }

    private long factorialRecursion0(final long number) {
        if (number == 1)
            return number;
        else
            return number+factorialRecursion0(number-1);
    }

    private String factorialRecursion(final long number) {
        BigInteger integer = new BigInteger("1");
        for (long i=0;i<=number;i++){
            integer =  integer.add(new BigInteger(String.valueOf(i)));
        }
        return integer.toString();
    }

    @Test
    public void testTailRecursion0(){
        System.out.println(factorialTailRecursion0(1,100_000));
    }

    private int factorialTailRecursion0(final int factorial, final int number) {
        if (number == 1) return factorial;
        else return factorialTailRecursion0(factorial + number, number - 1);
    }
    @Test
    public void testTailRecursion(){
        System.out.println(factorialTailRecursion(1,1_000_000).invoke());
    }

    private TailRecursion<Integer> factorialTailRecursion(final int factorial, final int number) {
        if (number == 1)
            return TailInvoke.done(factorial);
        else
            return TailInvoke.call(() -> factorialTailRecursion(factorial + number, number - 1));
    }
}
