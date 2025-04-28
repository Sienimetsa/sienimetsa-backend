package sienimetsa.sienimetsa_backend;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import sienimetsa.sienimetsa_backend.service.AwsUploadService;
import sienimetsa.sienimetsa_backend.web.BucketController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@ExtendWith(MockitoExtension.class)
public class BucketControllerTest {

    private static final String FILE_NAME = "test.jpg";
    private static final String FILE_CONTENT = "test image content";
    private static final String FILE_MIME_TYPE = "image/jpeg";
    private static final String EXPECTED_URL = "https://s3-bucket.aws.com/test.jpg";
    private static final String ERROR_MESSAGE = "Access denied";

    private BucketController bucketController;

    @Mock
    private S3Client s3Client;

    @Mock
    private AwsUploadService awsUploadService;

    @BeforeEach
    public void setup() {
        bucketController = new BucketController(s3Client, awsUploadService);
    }

    @Test
    public void testGetBuckets() {
        //Arrange
        Bucket bucket1 = Bucket.builder().name("bucket1").build();
        Bucket bucket2 = Bucket.builder().name("bucket2").build();
        ListBucketsResponse response = ListBucketsResponse.builder()
                .buckets(bucket1, bucket2)
                .build();
        when(s3Client.listBuckets()).thenReturn(response);
        //Act
        List<String> result = bucketController.getBuckets();
        //Assert
        assertEquals(2, result.size());
        assertTrue(result.contains("bucket1"));
        assertTrue(result.contains("bucket2"));
        verify(s3Client, times(1)).listBuckets();
    }

    @Test
    public void testUploadImage_Success() throws Exception {
        //Arrange
        MultipartFile file = new MockMultipartFile("file", FILE_NAME, FILE_MIME_TYPE, FILE_CONTENT.getBytes());
        when(awsUploadService.uploadImage(file)).thenReturn(EXPECTED_URL);
        //Act
        ResponseEntity<String> response = bucketController.uploadImage(file);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String responseBody = response.getBody();
        assertTrue(responseBody != null, "Response body should not be null");
        if (responseBody != null) {
            assertTrue(responseBody.contains("File uploaded successfully"));
            assertTrue(responseBody.contains(EXPECTED_URL));
        }
        verify(awsUploadService, times(1)).uploadImage(file);
    }

    @Test
    public void testUploadImage_Failure() throws Exception {
        //Arrange
        MultipartFile file = new MockMultipartFile("file", FILE_NAME, FILE_MIME_TYPE, FILE_CONTENT.getBytes());
        when(awsUploadService.uploadImage(file)).thenThrow(new RuntimeException(ERROR_MESSAGE));
        //Act
        ResponseEntity<String> response = bucketController.uploadImage(file);
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        String responseBody = response.getBody();
        assertTrue(responseBody != null, "Response body should not be null");
        if (responseBody != null) {
            assertTrue(responseBody.contains("Failed to upload file"));
            assertTrue(responseBody.contains(ERROR_MESSAGE));
        }
        verify(awsUploadService, times(1)).uploadImage(file);
    }
}