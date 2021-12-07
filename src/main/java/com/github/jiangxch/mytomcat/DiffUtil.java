package com.github.jiangxch.mytomcat;

import java.util.HashMap;
import java.util.Map;
import net.javacrumbs.jsonunit.core.Configuration;
import net.javacrumbs.jsonunit.core.Option;
import net.javacrumbs.jsonunit.core.internal.Diff;
import org.assertj.core.util.Lists;

/**
 * @author: jiangxch
 * @date: 2021/12/7 17:30
 */
public class DiffUtil {
    private static Configuration configuration = Configuration.empty().withOptions(Option.IGNORING_ARRAY_ORDER)
        .whenIgnoringPaths(Lists.newArrayList("_id", "id", ".*\\._id", ".*\\.id", "[*]._id", "[*].id"));

    public static void main(String[] args) {
        Map left = new HashMap<>();
        Map right = new HashMap<>();
        left.put("1",1);
        right.put("1",1);
        left.put("a","22.2");
        right.put("a","22.2");
        left.put("xxxwww我怕","11");
        right.put("xxxwww我怕","a我11");
        String diff = diff(left, right);
        System.out.println(diff);
    }
    public static String diff(Object expect, Object actual) {
        if (expect == null && actual == null) {
            return null;
        }
        if (expect == null || actual == null) {
            //log.warn("diff error-[{}]:expect={},actual={}", getCurrentDaoMethod(), expect, actual);
            return "";
        }
        try {
            Diff diff = Diff.create(expect, actual, "fullJson", "", configuration);
            if (!diff.similar()) {
                //log.warn("diff error-[{}]:{} ,mongo={} ,pg={}", methodInfo, diff.differences(), JsonUtil.toJson(expect), JsonUtil.toJson(actual));
                return diff.differences();
            }
        } catch (Throwable e) {
            //log.warn("", e);
            return "";
        }
        return "";
    }
}
