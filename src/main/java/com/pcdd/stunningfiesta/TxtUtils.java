package com.pcdd.stunningfiesta;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author pcdd
 */
@UtilityClass
public class TxtUtils {

    private static final Log log = LogFactory.get();

    public String searchContext(String fileName, String keyword) {
        return searchContext(fileName, keyword, 100);
    }

    /**
     * 搜索关键字的上下文
     *
     * @param fileName txt文件
     * @param keyword  关键字
     * @param length   关键字上下文长度，默认100
     */
    public String searchContext(String fileName, String keyword, int length) {
        String content = getFileText(fileName);
        int i = content.indexOf(keyword);
        return i > -1 ? content.substring(i - length, i + length) : null;
    }

    public Set<String> fullSearch(String fileName, String regex, String... ignores) {
        String content = getFileText(fileName);
        // 存放符合条件的关键字
        Set<String> results = new LinkedHashSet<>(ReUtil.findAllGroup0(regex, content));
        // 忽略其他词
        for (String ignore : ignores) {
            results.remove(ignore);
        }
        FileUtil.writeUtf8Lines(results, Constants.RESOURCES_PATH + regex + ".txt");
        return results;
    }

    /**
     * 全文搜索关键字
     *
     * @param fileName txt文件
     * @param regex    正则表达式
     * @return 符合条件的关键字集合（无重复）
     */
    public Map<String, String> fullSearchMap(String fileName, String regex, String... ignores) {
        String content = getFileText(fileName);
        // 存放符合条件的关键字
        Set<String> results = new LinkedHashSet<>(ReUtil.findAllGroup0(regex, content));
        // 忽略其他词
        for (String ignore : ignores) {
            results.remove(ignore);
        }
        // k：匹配串，v：匹配串的上下文
        Map<String, String> map = new LinkedHashMap<>();
        results.forEach(e -> {
            int i = content.indexOf(e);
            map.put(e, content.substring(i - 100, i + 100));
        });
        FileUtil.writeUtf8Map(map, FileUtil.newFile(Constants.RESOURCES_PATH + regex + ".map.txt"), "：", false);
        return map;
    }

    /**
     * 获取txt文件全文内容
     *
     * @param fileName txt文件名
     * @return 全文内容
     */
    @SneakyThrows
    public String getFileText(String fileName) {
        InputStream is = ClassLoaderUtil.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            log.error("文件输入流获取失败");
            System.exit(-1);
        }
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] bytes = bis.readAllBytes();
        String content = new String(bytes, Charset.forName("GBK"));
        bis.close();
        is.close();
        return content.replaceAll("\\s*|\r|\n|\t", "");
    }

}
