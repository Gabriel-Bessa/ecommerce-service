package br.com.ecommerce.ecommerceservice.service.utils;

import br.com.ecommerce.ecommerceservice.config.exceptions.BusinessException;
import static br.com.ecommerce.ecommerceservice.service.ProductService.BUCKET_URL;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public interface Uploader {
    Logger log = LoggerFactory.getLogger(Uploader.class);

    default void uploadFile(AmazonS3 amazonS3, String bucketName, long size, String filename, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        metadata.setContentType(URLConnection.guessContentTypeFromName(filename));
        amazonS3.putObject(bucketName, filename, inputStream, metadata);
    }

    default String saveImageFileName(AmazonS3 amazonS3, String bucketName, MultipartFile file, String filePath) {
        if (file.getSize() <= 0) {
            throw new BusinessException("file.error", "file.invalid");
        }
        String filename = UUID.randomUUID().toString();
        try {
            uploadFile(amazonS3, bucketName, file.getSize(), filename, file.getInputStream());
            return BUCKET_URL + get(amazonS3, bucketName, filename);
        } catch (IOException | AmazonClientException e) {
            log.error("Failure Upload Image {}", e.getMessage());
            throw new BusinessException("file.error", "file.invalid");
        }
    }

    default String get(AmazonS3 amazonS3, String bucketName, String filename) {
        S3Object s3Object = amazonS3.getObject(bucketName, filename);
        return s3Object.getKey();
    }
}
