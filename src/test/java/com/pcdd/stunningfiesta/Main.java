package com.pcdd.stunningfiesta;

import cn.hutool.core.date.StopWatch;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.SneakyThrows;

import java.util.Set;

/**
 * @author pcdd
 */
public class Main {

    private static final Log log = LogFactory.get();

    @SneakyThrows
    public static void main(String[] args) {
        String reg = ".{2}之主";
        String[] ignores = {"宇宙之主", "法则之主", "灭人之主"};

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Set<String> results = TxtUtils.fullSearch("吞噬星空.txt", reg, ignores);
        stopWatch.stop();

        results.forEach(log::info);

        log.info("{} 个统计结果", results.size());
        log.info("耗时 {} ms", stopWatch.getTotalTimeMillis());
    }

}
