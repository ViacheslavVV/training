package com.train;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws JRException, WriterException, IOException {
        //это ты уже умеешь, получаем файл отчёта
        InputStream employeeReportStream = Main.class.getResourceAsStream("/test.jrxml");
        //это тоже уже умеешь, компилируем его
        JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
        //создаем мапу для параметров, это ты тоже уже умеешь
        Map<String, Object> parameters = new HashMap<>();


        //используя подключенные библиотеки создаем объект, который сможет создать
        // матрицу для qr кода (матрица - двумерный массив, если забыл)
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //создаем битовую матрицу
        BitMatrix matrix = qrCodeWriter.encode(
                "https://jut.su", //строка, которая будет переведена в qr
                BarcodeFormat.QR_CODE, //формат, который обязательно нужно передать для QR
                100,//ширина qr
                100//высота qr (я выбрал 100x100)
        );
        //создаем поток, в который запишем битовую матрицу созданную на предыдущей строке
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //сам процесс записи матрицы в byteArrayOutputStream
        MatrixToImageWriter.writeToStream(matrix, "png", byteArrayOutputStream);
        //получаем результат записи матрицы в поток byteArrayOutputStream (получаем байты из потока)
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        //создаем поток для чтения из тех байт, которые получили из потока byteArrayOutputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        //задаём значение параметру qrCode (посмотри в test.jrxml , у него там тип java.io.InputStream )
        parameters.put("qrCode", byteArrayInputStream);

        //это ты уже умеешь, заполняем отчёт с параметрами
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, parameters, new JREmptyDataSource());

        //-- когда получен jasperPrint - осталось только его отправить куда-то,
        //-- ты у себя отправляешь в HttpResponse, я записываю в файл (код дальше тебе не нужен)

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(
                        "reportForVlad_scanMe.pdf"
                )
        );

        exporter.exportReport();
    }
}
