package main;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.io.File;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

public class Mainer {

    public static void main(String[] args) throws Exception {

        System.out.println("start...");
        String saveMp4name = "C:/Users/oz/Desktop/f1.mp4"; //保存的视频名称
        // 目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
        String imagesPath = "C:/Users/oz/Desktop/img/"; // 图片集合的目录
        test(saveMp4name, imagesPath);
        System.out.println("end...");
    }

    public static void test(String saveMp4name, String imagesPath) throws Exception {
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(saveMp4name, 1080, 1920);
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H265); // 28
//		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
        recorder.setFormat("mp4");
        //	recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
        recorder.setFrameRate(20);
        recorder.setPixelFormat(0); // yuv420p
        recorder.start();
        //
        OpenCVFrameConverter.ToIplImage conveter = new OpenCVFrameConverter.ToIplImage();
        // 列出目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
        File file = new File(imagesPath);
        File[] flist = file.listFiles();
        // 循环所有图片
        for (int i = 1; i <= flist.length; i++) {
            String fname = imagesPath + "1 (" + i + ").jpg";
            opencv_core.IplImage image = cvLoadImage(fname); // 非常吃内存！！
            recorder.record(conveter.convert(image));
            // 释放内存？ cvLoadImage(fname); // 非常吃内存！！
            opencv_core.cvReleaseImage(image);
        }
        recorder.stop();
        recorder.release();
    }

}
