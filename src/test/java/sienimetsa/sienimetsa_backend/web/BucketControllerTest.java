package sienimetsa.sienimetsa_backend.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


public class BucketControllerTest {

    private S3Client s3Client;
    private BucketController bucketController;

    @BeforeEach
    public void setUp() {
        s3Client = mock(S3Client.class);
        bucketController = new BucketController(s3Client);
    }

    @Test
    public void testGetBuckets() {
        // Arrange
        Bucket bucket1 = mock(Bucket.class);
        Bucket bucket2 = mock(Bucket.class);
        when(bucket1.name()).thenReturn("bucket1");
        when(bucket2.name()).thenReturn("bucket2");

        ListBucketsResponse mockResponse = mock(ListBucketsResponse.class);
        when(mockResponse.buckets()).thenReturn(Arrays.asList(bucket1, bucket2));
        when(s3Client.listBuckets()).thenReturn(mockResponse);

        // Act
        List<String> result = bucketController.getBuckets();

        // Assert
        assertEquals(2, result.size());
        assertEquals("bucket1", result.get(0));
        assertEquals("bucket2", result.get(1));
    }

    @Test
    public void testUploadImage_Success() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test.jpg", 
            "image/jpeg", 
            "test image content".getBytes()
        );

        // Act
        ResponseEntity<String> response = bucketController.uploadImage(file);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("File uploaded successfully"));
        
        // Verify S3 client was called with correct parameters
        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        ArgumentCaptor<RequestBody> bodyCaptor = ArgumentCaptor.forClass(RequestBody.class);
        
        verify(s3Client).putObject(requestCaptor.capture(), bodyCaptor.capture());
        
        PutObjectRequest capturedRequest = requestCaptor.getValue();
        assertEquals("sienimetsa-img", capturedRequest.bucket());
        assertTrue(capturedRequest.key().endsWith("_test.jpg"));
        assertEquals("image/jpeg", capturedRequest.contentType());
    }

    @Test
    public void testUploadImage_Failure() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test.jpg", 
            "image/jpeg", 
            "test image content".getBytes()
        );
        
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
            .thenThrow(new RuntimeException("S3 error"));

        // Act
        ResponseEntity<String> response = bucketController.uploadImage(file);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Failed to upload file"));
    }
@Test
public void testUploadImage_VerifyUrl() throws IOException {
    // Arrange
    String timestamp = String.valueOf(System.currentTimeMillis());
    MockMultipartFile file = new MockMultipartFile(
        "file", 
        "test.jpg", 
        "image/jpeg", 
        "test image content".getBytes()
    );
    
    // Use mockito to replace System.currentTimeMillis() with a fixed value
    // through PowerMockito or similar if needed for consistent testing
    
    // Act
    ResponseEntity<String> response = bucketController.uploadImage(file);
    
    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    String expectedUrl = "https://sienimetsa-img.s3.amazonaws.com/";
    assertTrue(response.getBody().contains(expectedUrl));
    
    // Verify the S3 call parameters
    ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
    verify(s3Client).putObject(requestCaptor.capture(), any(RequestBody.class));
    
    PutObjectRequest capturedRequest = requestCaptor.getValue();
    assertEquals("sienimetsa-img", capturedRequest.bucket());
    assertTrue(capturedRequest.key().contains("test.jpg"));
}

@Test
public void testUploadImage_EmptyFile() throws IOException {
    // Arrange
    MockMultipartFile emptyFile = new MockMultipartFile(
        "file", 
        "", 
        "image/jpeg", 
        new byte[0]
    );
    
    // Act
    ResponseEntity<String> response = bucketController.uploadImage(emptyFile);
    
    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    
    // Verify that even empty files are attempted to be uploaded
    verify(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
}

@Test
public void testUploadImage_LargeFile() throws IOException {
    // Arrange
    byte[] largeContent = new byte[1024 * 1024]; // 1MB file
    Arrays.fill(largeContent, (byte) 'x');
    
    MockMultipartFile largeFile = new MockMultipartFile(
        "file", 
        "large.jpg", 
        "image/jpeg", 
        largeContent
    );
    
    // Act
    ResponseEntity<String> response = bucketController.uploadImage(largeFile);
    
    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    
    // Verify the correct file size was passed to S3
    ArgumentCaptor<RequestBody> bodyCaptor = ArgumentCaptor.forClass(RequestBody.class);
    verify(s3Client).putObject(any(PutObjectRequest.class), bodyCaptor.capture());
    
    assertEquals(1024 * 1024, bodyCaptor.getValue().contentLength());
}
}