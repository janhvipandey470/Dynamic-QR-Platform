package com.example.qrcodegenerator.Controller;

import com.example.qrcodegenerator.service.qrService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.Authenticator;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class qrController {
    @Autowired
    private qrService service;
    @PostMapping("/encodeText")
    public String getQr(@RequestParam String text){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return service.textToQr(text,email);
    }
    @PostMapping("/encodeImage")
    public String getQRImage(@RequestPart("image") MultipartFile image) throws Exception {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return service.imageToQr(image,email);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadQR(@PathVariable Long id) throws Exception {

        Resource resource = service.downloadQR(id);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=QRCode"+id+".png")
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
    @GetMapping("/qr/{id}")
    public ResponseEntity<Void> decode(@PathVariable Long id){
        return service.decode(id);
    }
    @PutMapping("/{qrId}/editText")
    public String editText(@RequestParam String newText,@PathVariable Long qrId){
        return service.editText(newText,qrId);
    }
    @PutMapping("{qrId}/editImage")
    public String editImage(@PathVariable Long qrId,@RequestPart("image")MultipartFile image)throws Exception{
        return service.editImage(qrId,image);
    }
}
