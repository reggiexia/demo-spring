package com.xhh.demo.multi.datasource;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 基准测试
 *
 * @author tiger
 */
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
public class BenchTest {

    @Benchmark
    public void bench() {
        add(1, 1);
    }

    private static int add(int a, int b) {
        return a + b;
    }

    @Benchmark
    public String stringConcat() {
        String a = "a";
        String b = "b";
        String c = "c";
        return a + b + c;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchTest.class.getSimpleName())
                .exclude("stringConcat")
                .forks(1)
                .warmupIterations(1)
                .measurementIterations(1)
                .build();
        new Runner(options).run();
    }
}
