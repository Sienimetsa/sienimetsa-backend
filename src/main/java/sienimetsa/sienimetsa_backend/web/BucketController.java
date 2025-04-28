package sienimetsa.sienimetsa_backend.web;

import java.io.IOException;
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

import sienimetsa.sienimetsa_backend.service.AwsUploadService;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@RestController
@RequestMapping("/buckets")
public class BucketController {

    private final S3Client s3Client;
    private final AwsUploadService awsUploadService;
    
    public BucketController(S3Client s3Client, AwsUploadService awsUploadService) {
        this.s3Client = s3Client;
        this.awsUploadService = awsUploadService;
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
            String fileUrl = awsUploadService.uploadImage(file);
            
            return new ResponseEntity<>("File uploaded successfully. URL: " + fileUrl, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
