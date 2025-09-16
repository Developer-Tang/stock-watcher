package cn.tangshh.stock_watcher.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.tangshh.stock_watcher.entity.StockData;
import cn.tangshh.stock_watcher.enums.PopupDetailView;
import cn.tangshh.stock_watcher.service.StockDataService;
import cn.tangshh.stock_watcher.util.LogUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 新浪股票数据服务
 *
 * @author Tang
 * @version v1.0
 */
public class SinaStockDataService extends StockDataService {
    private static final String SINA_API_URL = "http://hq.sinajs.cn/list={}";
    private static final String TIME_SHARING_IMG_URL = "http://image.sinajs.cn/newchart/min/n/{}.gif";
    private static final String DAY_K_URL = "http://image.sinajs.cn/newchart/daily/n/{}.gif";
    private static final String WEEK_K_URL = "http://image.sinajs.cn/newchart/weekly/{}.gif";
    private static final String MONTH_K_URL = "http://image.sinajs.cn/newchart/monthly/{}.gif";
    private static final String REFERER = "https://finance.sina.com.cn";

    @Override
    public List<StockData> batchGetStockData(List<String> codes) {
        List<StockData> result = new ArrayList<>();
        if (codes == null || codes.isEmpty()) {
            return result;
        }

        HttpRequest req = HttpUtil.createGet(StrUtil.format(SINA_API_URL, StrUtil.join(StrUtil.COMMA, codes)));
        req.header("User-Agent", AGENT);
        req.header("Referer", REFERER);

        try (HttpResponse resp = req.execute()) {
            if (resp.isOk()) {
                List<String> lines = StrUtil.split(resp.body(), StrUtil.LF);
                lines.stream()
                        .filter(StrUtil::isNotBlank)
                        .forEach(line -> result.add(parseStockData(line)));
            }
        } catch (Exception e) {
            LogUtil.print("获取股票数据异常：{}", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 解析新浪接口返回的股票数据
     *
     * @param line 接口返回的一行数据
     * @return 解析后的StockData对象
     */
    private StockData parseStockData(String line) {
        try {
            // 提取股票代码和数据部分
            int startIndex = line.indexOf("var hq_str_") + 11;
            int endIndex = line.indexOf(",");
            if (startIndex < 0 || endIndex < 0) {
                return null;
            }

            String code = line.substring(startIndex, line.indexOf("=\""));
            String dataPart = line.substring(line.indexOf("=\"") + 2, line.lastIndexOf('"'));
            String[] dataArray = dataPart.split(",");

            // 解析股票名称
            String name = dataArray.length > 0 ? dataArray[0] : "";

            if (dataArray.length < 33) {
                // 股票数据格式不正确
                return null;
            }

            // 解析各种价格数据
            BigDecimal openPrice = Convert.toBigDecimal(dataArray[1], BigDecimal.ZERO);
            BigDecimal prevClosePrice = Convert.toBigDecimal(dataArray[2], BigDecimal.ZERO);
            BigDecimal currentPrice = Convert.toBigDecimal(dataArray[3], BigDecimal.ZERO);
            BigDecimal highestPrice = Convert.toBigDecimal(dataArray[4], BigDecimal.ZERO);
            BigDecimal lowestPrice = Convert.toBigDecimal(dataArray[5], BigDecimal.ZERO);

            // 创建StockData对象
            return new StockData()
                    .setCode(code)
                    .setName(name)
                    .setPrevClosePrice(prevClosePrice)
                    .setOpenPrice(openPrice)
                    .setCurrentPrice(currentPrice)
                    .setHighPrice(highestPrice)
                    .setLowPrice(lowestPrice);
        } catch (Exception e) {
            System.out.println("解析股票数据异常: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String kLineImg(String stockCode, PopupDetailView view) {
        return switch (view) {
            case TIME_SHARE -> StrUtil.format(TIME_SHARING_IMG_URL, stockCode);
            case DAY_K -> StrUtil.format(DAY_K_URL, stockCode);
            case WEEK_K -> StrUtil.format(WEEK_K_URL, stockCode);
            case MONTH_K -> StrUtil.format(MONTH_K_URL, stockCode);
            default -> "";
        };
    }
}