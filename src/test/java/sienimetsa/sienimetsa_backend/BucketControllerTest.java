package sienimetsa.sienimetsa_backend;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import sienimetsa.sienimetsa_backend.service.AwsUploadService;
import sienimetsa.sienimetsa_backend.web.BucketController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;




public class BucketControllerTest {

    private BucketController bucketController;

    @Mock
    private S3Client s3Client;

    @Mock
    private AwsUploadService awsUploadService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bucketController = new BucketController(s3Client, awsUploadService);
    }

    @Test
    public void testGetBuckets() {
        // Arrange
        Bucket bucket1 = Bucket.builder().name("bucket1").build();
        Bucket bucket2 = Bucket.builder().name("bucket2").build();
        ListBucketsResponse response = ListBucketsResponse.builder()
                .buckets(bucket1, bucket2)
                .build();
        
        when(s3Client.listBuckets()).thenReturn(response);

        // Act
        List<String> result = bucketController.getBuckets();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("bucket1"));
        assertTrue(result.contains("bucket2"));
        verify(s3Client, times(1)).listBuckets();
    }

    @Test
    public void testUploadImage_Success() throws Exception {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());
        String expectedUrl = "https://s3-bucket.aws.com/test.jpg";
        
        when(awsUploadService.uploadImage(file)).thenReturn(expectedUrl);

        // Act
        ResponseEntity<String> response = bucketController.uploadImage(file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("File uploaded successfully"));
        assertTrue(response.getBody().contains(expectedUrl));
        verify(awsUploadService, times(1)).uploadImage(file);
    }

    @Test
    public void testUploadImage_Failure() throws Exception {
        // Arrange
        MultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());
        String errorMessage = "Access denied";
        
        when(awsUploadService.uploadImage(file)).thenThrow(new RuntimeException(errorMessage));

        // Act
        ResponseEntity<String> response = bucketController.uploadImage(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Failed to upload file"));
        assertTrue(response.getBody().contains(errorMessage));
        verify(awsUploadService, times(1)).uploadImage(file);
    }
}