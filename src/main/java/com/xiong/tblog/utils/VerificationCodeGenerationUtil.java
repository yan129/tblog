package com.xiong.tblog.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Random;

/**
 * 生成验证码的工具类
 */
public class VerificationCodeGenerationUtil {

    private Integer width = 97; // 生成验证码图片的宽度
    private Integer height = 40; // 生成验证码图片的高度
    private String[] fontNames = {"宋体","楷体","隶书","微软雅黑"};
    private Color bgColor = new Color(255,255,255); //定义验证码图片的背景颜色为白色
    private String codes = "123456789abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
    private Random random = new Random();
    private String text;    //记录随机字符串

    /**
     * 获取一个随意颜色
     */
    private Color randomColor(){
        int red = random.nextInt(150);
        int green = random.nextInt(150);
        int blue = random.nextInt(150);
        return new Color(red, green, blue);
    }

    /**
     * 获取一个随机字体
     */
    private Font randomFont(){
        String name = fontNames[random.nextInt(fontNames.length)];
        int style = random.nextInt(4);
        int size = random.nextInt(5) + 24;
        return new Font(name, style, size);
    }

    /**
     * 获取一个随机字符
     */
    private char randomChar(){
        return codes.charAt(random.nextInt(codes.length()));
    }

    /**
     * 创建一个空白的BufferedImage对象
     */
    private BufferedImage createImage(){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(bgColor);
        g2.fillRect(0, 0, width, height);
        return image;
    }

    public BufferedImage getImage(){
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String code = randomChar() + "";
            sb.append(code);
            g2.setColor(randomColor());
            g2.setFont(randomFont());
            float x = i * width * 1.0f / 4;
            g2.drawString(code, x, height - 8);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 绘制干扰线
     */
    private void drawLine(BufferedImage image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < 4; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2.setColor(randomColor());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public String getText(){
        return text;
    }

    //    输出验证码图片
    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
        IOUtils.closeQuietly(out);
    }

    public static String output(BufferedImage image) throws IOException {
        ByteArrayOutputStream bos = null;
        try{
            bos = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", bos);
            byte[] bytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            return "data:image/JPEG;base64," +  encoder.encodeToString(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(bos);
        }
        return null;
    }
}
