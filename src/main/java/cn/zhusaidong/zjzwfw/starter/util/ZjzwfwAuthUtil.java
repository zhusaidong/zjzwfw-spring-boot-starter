package cn.zhusaidong.zjzwfw.starter.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 浙里办鉴权工具类
 *
 * @author zhusaidong
 */
public class ZjzwfwAuthUtil {
    private static final Logger log = LoggerFactory.getLogger(ZjzwfwAuthUtil.class);

    /**
     * 构造请求header
     * @param urlStr 请求url，全路径格式
     * @param requestMethod 请求方法,大写格式，如：GET,POST
     * @param accessKey 应用的AK
     * @param secretKey 应用的SK
     * @return header
     */
    public static Map<String, String> generateHeader(String urlStr, String requestMethod, String accessKey, String secretKey) {
        Map<String, String> header = new HashMap<>(4);
        String date = getRfc1123Time();
        try {
            URI uri = new URL(urlStr).toURI();
            String queryString = getCanonicalQueryString(uri.getQuery());
            String message = requestMethod.toUpperCase() + "\n" + uri.getPath() + "\n" + queryString + "\n" + accessKey + "\n" + date + "\n";

            Mac sha256 = Mac.getInstance("HmacSHA256");
            sha256.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
            byte[] hash = sha256.doFinal(message.getBytes());
            String sign = new String(Base64.getEncoder().encode(hash));

            header.put("X-BG-HMAC-SIGNATURE", sign);
            header.put("X-BG-HMAC-ALGORITHM", "hmac-sha256");
            header.put("X-BG-HMAC-ACCESS-KEY", accessKey);
            header.put("X-BG-DATE-TIME", date);
            return header;
        } catch (Exception e) {
            log.error("generate error", e);
            throw new RuntimeException("generate header error");
        }
    }

    /**
     * 生成Rfc1123格式时间
     * @return Rfc1123格式时间
     */
    private static String getRfc1123Time() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(new Date());
    }

    private static String getCanonicalQueryString(String query) {
        if (StringUtils.isEmpty(query)) {
            return "";
        }
        List<Pair<String, String>> queryParamList = new ArrayList<>();
        String[] params = query.split("&");
        for (String param : params) {
            int eqIndex = param.indexOf("=");
            String key = param.substring(0, eqIndex);
            String value = param.substring(eqIndex + 1);
            Pair<String, String> pair = new MutablePair<>(key, value);
            queryParamList.add(pair);
        }
        List<Pair<String, String>> sortedParamList = queryParamList.stream()
                .sorted(Comparator.comparing(param -> param.getKey() + "=" + Optional.ofNullable(param.getValue())
                        .orElse("")))
                .collect(Collectors.toList());
        List<Pair<String, String>> encodeParamList = new ArrayList<>();
        sortedParamList.forEach(param -> {
            try {
                String key = URLEncoder.encode(param.getKey(), "utf-8");
                String value = URLEncoder.encode(Optional.ofNullable(param.getValue()).orElse(""), "utf-8")
                        .replaceAll("%2B", "%20")
                        .replaceAll("\\+", "%20")
                        .replaceAll("%21", "!")
                        .replaceAll("%27", "'")
                        .replaceAll("%28", "(")
                        .replaceAll("%29", ")")
                        .replaceAll("%7E", "~")
                        .replaceAll("%25", "%");
                encodeParamList.add(new MutablePair<>(key, value));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("encoding error");
            }
        });
        StringBuilder queryParamString = new StringBuilder(64);
        for (Pair<String, String> encodeParam : encodeParamList) {
            queryParamString.append(encodeParam.getKey())
                    .append("=")
                    .append(Optional.ofNullable(encodeParam.getValue()).orElse(""));
            queryParamString.append("&");
        }
        return queryParamString.substring(0, queryParamString.length() - 1);
    }
}
