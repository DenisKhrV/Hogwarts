package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        return port;
    }

    @GetMapping("/sum")
    public void getSum() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("result: {}, execution time: {}", sum, finish - start);
    }
    @GetMapping("/sum")
    public void getSumParallel() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("result: {}, execution time: {}", sum, finish - start);
    }
    @GetMapping("/sum-with-IntStream")
    public void getSumWithIntStream() {
        long start = System.currentTimeMillis();
        int sum = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("result with IntStream: {}, execution time: {}", sum, finish - start);
        //Этот метод возвращает значение за меньшее количество времени
    }

}
