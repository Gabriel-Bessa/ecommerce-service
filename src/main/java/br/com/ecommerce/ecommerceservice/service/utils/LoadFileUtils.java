package br.com.ecommerce.ecommerceservice.service.utils;

import javax.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class LoadFileUtils {

    private static final List<String> imageExtension = List.of("png", "jpg", "jpeg", "gif");
    private static final List<String> imageMimeType = List.of("image/png", "image/jpg", "image/jpeg", "image/gif");

    public static boolean isImage(@NotNull MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return false;
        }
        String fileExtension = getFileExtension(fileName);
        return imageExtension.contains(fileExtension) && imageMimeType.contains(file.getContentType());
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return null;
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }
}
