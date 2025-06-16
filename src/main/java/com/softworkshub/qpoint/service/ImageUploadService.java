package com.softworkshub.qpoint.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploadService {


    @Autowired
    private Cloudinary cloudinary;


    public String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "qpoint_images",
                "public_id", "img_" + System.currentTimeMillis() // unique name
        ));
        return uploadResult.get("secure_url").toString(); // always use secure_url
    }
}
