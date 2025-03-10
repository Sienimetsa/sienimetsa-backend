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
     * Service joka lataa kuvan S3:een ja palauttaa sen URL:n
     * 
     * @param file Tiedosto joka pitää pyynnön mukana lähettää
     * @return URL josta kuva on ladattavissa (jos on oikeudet)
     * @throws IOException Jos hommat ns. leipoo
     */
    
    public String uploadImage(MultipartFile file) throws IOException {
        // Generoidaan "uniikki" tiedostonimi niin ettei tule ongelmia
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String bucketName = "sienimetsa-img";
        
        byte[] fileBytes = file.getBytes();
        
        // Lähetetään kuva S3:een
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build(),
                RequestBody.fromBytes(fileBytes));
        
        // Generoidaan entityyn varastoitava URL
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
}
