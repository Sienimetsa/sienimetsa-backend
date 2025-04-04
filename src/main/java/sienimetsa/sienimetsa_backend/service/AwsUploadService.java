package sienimetsa.sienimetsa_backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class AwsUploadService {
    
    @Autowired
    private S3Client s3Client;
    
    /**
     * Service that uploads an image to S3 and returns its URL
     * 
     * @param file File that needs to be sent with the request
     * @return file name
     * @throws IOException If something goes wrong
     */

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String bucketName = "sienimetsa-img";
        
        byte[] fileBytes = file.getBytes();
        
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build(),
                RequestBody.fromBytes(fileBytes));
        
        return fileName;
    }
}
