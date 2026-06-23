package com.example.qrcodegenerator.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.qrcodegenerator.Entity.QREntity;
import com.example.qrcodegenerator.Entity.users;
import com.example.qrcodegenerator.Repository.QREntityRepo;
import com.example.qrcodegenerator.Repository.usersRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class qrService {
    @Autowired
    private QREntityRepo repo;
    @Autowired
    private usersRepo userRepo;
    @Autowired
    private Cloudinary cloudinary;

    public byte[] generateQRCode(String text) throws Exception {

        BitMatrix matrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                300,
                300
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(
                matrix,
                "PNG",
                outputStream
        );

        return outputStream.toByteArray();
    }
    public String uploadImage(MultipartFile file) throws Exception {

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );

        return uploadResult.get("secure_url").toString();
    }
    public String textToQr(String s,String email) {
        users user=userRepo.findByEmail(email).orElseThrow();
        try{
            String message=(s.isBlank())?"":s;
            QREntity qr=new QREntity();
            qr.setText(s);
            repo.save(qr);
            String dynamicUrl="http://localhost:8080/qr/" + qr.getId();
            byte[] qrImage=generateQRCode(dynamicUrl);
            Map uploadResult=cloudinary.uploader().upload(qrImage,ObjectUtils.emptyMap());
            String qrUrl=uploadResult.get("secure_url").toString();
            qr.setQR_URL(qrUrl);
            qr.setUser(user);
            qr.setType("Text");
            repo.save(qr);
            System.out.println(qrUrl);
            return qr.getQR_URL();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String imageToQr(MultipartFile image,String email) throws Exception   {
        users user=userRepo.findByEmail(email).orElseThrow();
        //upload original image in cloud
        String imageUrl=uploadImage(image);
        QREntity qr=new QREntity();
        qr.setImageURL(imageUrl);
        qr.setRedirectURL(imageUrl);
        repo.save(qr);
        //Remainder: have to update after deployment
        String dynamicUrl="http://localhost:8080/qr/" + qr.getId();
        //generate QR
        byte[] qrImage=generateQRCode(dynamicUrl);
        //upload qr in cloud
        Map uploadResult=cloudinary.uploader().upload(qrImage,ObjectUtils.emptyMap());
        String qrUrl=uploadResult.get("secure_url").toString();
        String publicId= uploadResult.get("public_id").toString();
        qr.setQR_URL(qrUrl);
        qr.setUser(user);
        qr.setPublicId(publicId);
        qr.setType("Image");
        repo.save(qr);
        return qr.getQR_URL();
    }
    public Resource downloadQR(Long id) throws Exception {

            QREntity qr = repo.findById(id)
                    .orElseThrow();

            URL url = new URL(qr.getQR_URL());

            InputStream inputStream = url.openStream();

            return new InputStreamResource(inputStream);
    }
    public ResponseEntity<Void> decode(Long id) {
        QREntity qr=repo.findById(id).orElseThrow();
        HttpHeaders headers=new HttpHeaders();
        headers.setLocation(URI.create(qr.getRedirectURL()));
        return new ResponseEntity<>(
                headers,
                HttpStatus.FOUND
        );
    }

    public String editText(String newText, Long qrId) {
        QREntity qr=repo.findById(qrId).orElseThrow();
        qr.setText(newText);
        repo.save(qr);
        return "updated Successfully";

    }

    public String editImage(Long qrId, MultipartFile image) throws IOException {
        QREntity qr=repo.findById(qrId).orElseThrow();
        Map update=cloudinary.uploader().upload(image.getBytes(),ObjectUtils.asMap(
                "public_id", qr.getPublicId(),
                "overwrite", true,
                "invalidate", true
                ));
        String newUrl = (String) update.get("secure_url");
        qr.setImageURL(newUrl);
        return "updated successfully";

    }
}
