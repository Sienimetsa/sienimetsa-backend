package sienimetsa.sienimetsa_backend.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RestController
@RequestMapping("/buckets")
public class BucketController {

    private final S3Client s3Client;
    
    public BucketController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/all")
    public List<String> getBuckets() {
        List<String> bucketList = new ArrayList<>();
        
        ListBucketsResponse response = s3Client.listBuckets();
        for (Bucket bucket : response.buckets()) {
            bucketList.add(bucket.name());
        }
        
        return bucketList;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Generoi tiedostonimen ettei tuu duplikaatteja
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String bucketName = "sienimetsa-img";
            
            byte[] fileBytes = file.getBytes();
            
            // Tiedosto lähetetään S3:een
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build(),
                    RequestBody.fromBytes(fileBytes));
            
            // Generoidaan palautettava URL
            String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            
            return new ResponseEntity<>("File uploaded successfully. URL: " + fileUrl, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
