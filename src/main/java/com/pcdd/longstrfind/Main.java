package com.pcdd.longstrfind;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.pcdd.longstrfind.util.StrFindUtils;
import lombok.SneakyThrows;

import java.util.Set;

/**
 * @author pcdd
 */
public class Main {

    private static final Log log = LogFactory.get();

    @SneakyThrows
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Set<String> results = StrFindUtils.fullSearch("novels/吞噬星空.txt", ".{2}之主", "宇宙之主", "法则之主", "灭人之主");
        results.forEach(log::info);
        log.info("{}个统计结果", results.size());
        log.info("耗时：{}ms", System.currentTimeMillis() - start);
    }

}
