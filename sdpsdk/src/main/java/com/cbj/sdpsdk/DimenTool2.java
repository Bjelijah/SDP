package com.cbj.sdpsdk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author cbj
 * @Description 自动生成竖屏与横屏 dimens 适配文件工具类
 */
public class DimenTool2 {

    public static void main(String[] args) {
        // 1. 执行原有的竖屏生成逻辑（基准宽度 375dp）
        genPortrait();

        // 2. 执行新增的横屏生成逻辑（基准宽度 1920dp）
        genLandscape();
    }

    /**
     * 竖屏适配生成：基准最小宽度为 375
     */
    public static void genPortrait() {
        System.out.println("====== 开始生成竖屏 dimens ======");
        // 维持你原有的常用竖屏 sw 矩阵
        int[] portraitSwSizes = {240, 270, 300, 320, 330, 360, 390, 420, 450, 480, 500, 540, 570, 600, 630, 660, 690, 720, 750, 780, 810, 840, 870, 900, 930, 1080};
        gen("./sdpsdk", 375, portraitSwSizes, "values-sw%ddp");
    }

    /**
     * 横屏适配生成：基准横屏设计图宽度为 1920
     * 生成至 values-land-swXXdp 目录
     */
    public static void genLandscape() {
        System.out.println("====== 开始生成横屏 dimens ======");
        // 横屏设备（眼镜、车载、平板）在横向状态下，其物理 SmallestWidth（最窄边/高度）常见的 dp 矩阵
        int[] landscapeSwSizes = {360, 400, 411, 480, 540, 600, 720, 800, 900, 1080};

        // 🌟 核心：横屏下，系统按高度(最窄边)匹配sw，而设计图总宽是1920。
        // 设备横向真实宽度 dp = sw * (16 / 9) ≈ sw * 1.778
        // 缩放系数 scale = (sw * 1.778) / 1920
        genLand("./sdpsdk", 1920, landscapeSwSizes);
    }

    /**
     * 通用竖屏生成核心
     */
    public static void gen(String moduleName, int baseSw, int[] swSizes, String folderPattern) {
        String dimenFilePath = moduleName + "/src/main/res/values/dimens.xml";
        File file = new File(dimenFilePath);
        if (!file.exists()) {
            System.err.println("错误：基准 dimens.xml 文件不存在 -> " + dimenFilePath);
            return;
        }

        try {
            for (int sw : swSizes) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String tempString;

                // 竖屏缩放系数：目标最小宽度 / 基准最小宽度
                double scale = (double) sw / baseSw;

                while ((tempString = reader.readLine()) != null) {
                    if (tempString.contains("</dimen>")) {
                        String start = tempString.substring(0, tempString.indexOf(">") + 1);
                        String end = tempString.substring(tempString.lastIndexOf("<"));
                        String numStr = tempString.substring(tempString.indexOf(">") + 1, tempString.lastIndexOf("<"));

                        if (numStr.contains("dp")) {
                            double num = Double.parseDouble(numStr.replace("dp", ""));
                            sb.append(start).append(String.format("%.2f", num * scale)).append("dp").append(end).append("\n");
                        } else if (numStr.contains("sp")) {
                            double num = Double.parseDouble(numStr.replace("sp", ""));
                            sb.append(start).append(String.format("%.2f", num * scale)).append("sp").append(end).append("\n");
                        } else {
                            sb.append(tempString).append("\n");
                        }
                    } else {
                        sb.append(tempString).append("\n");
                    }
                }
                reader.close();

                String folderName = String.format(folderPattern, sw);
                String outFilePath = moduleName + "/src/main/res/" + folderName + "/dimens.xml";
                writeFile(outFilePath, sb.toString());
            }
            System.out.println("竖屏 dimens 生成成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 🌟 新增：横屏专用生成核心（支持 1920 设计图等比转换）
     */
    public static void genLand(String moduleName, int baseWidth, int[] swSizes) {
        String dimenFilePath = moduleName + "/src/main/res/values/dimens.xml";
        File file = new File(dimenFilePath);
        if (!file.exists()) {
            return;
        }

        try {
            for (int sw : swSizes) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String tempString;

                // 🌟 横屏算法核心：16:9 标准设备下，横屏的实际物理宽度 dp 约等于 sw * 1.7778
                // 这样可以直接把 1920 设计图上的 px 完美的映射到横屏物理宽度上
                double realWidthDp = sw * (16.0 / 9.0);
                double scale = realWidthDp / baseWidth;

                while ((tempString = reader.readLine()) != null) {
                    if (tempString.contains("</dimen>")) {
                        String start = tempString.substring(0, tempString.indexOf(">") + 1);
                        String end = tempString.substring(tempString.lastIndexOf("<"));
                        String numStr = tempString.substring(tempString.indexOf(">") + 1, tempString.lastIndexOf("<"));

                        if (numStr.contains("dp")) {
                            double num = Double.parseDouble(numStr.replace("dp", ""));
                            sb.append(start).append(String.format("%.2f", num * scale)).append("dp").append(end).append("\n");
                        } else if (numStr.contains("sp")) {
                            double num = Double.parseDouble(numStr.replace("sp", ""));
                            // 考虑到横屏大屏眼镜文字不宜缩放过小，文字可共享宽度缩放系数或略微调高，这里保持等比
                            sb.append(start).append(String.format("%.2f", num * scale)).append("sp").append(end).append("\n");
                        } else {
                            sb.append(tempString).append("\n");
                        }
                    } else {
                        sb.append(tempString).append("\n");
                    }
                }
                reader.close();

                // 生成到 values-land-swXXdp 专属横屏目录下
                String folderName = String.format("values-sw%ddp-land", sw);
                String outFilePath = moduleName + "/src/main/res/" + folderName + "/dimens.xml";
                writeFile(outFilePath, sb.toString());
            }
            System.out.println("横屏 values-land-swXXdp dimens 生成成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            String[] dirStr = file.split("/dimens.xml");
            File dir = new File(dirStr[0]);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(file);
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new PrintWriter(new FileWriter(f));
            out.print(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}