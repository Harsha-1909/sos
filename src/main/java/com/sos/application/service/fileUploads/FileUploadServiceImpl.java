package com.sos.application.service.fileUploads;

import com.sos.application.exception.BadRequestBodyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Value("${upload.dir}")
    private String BASE_UPLOAD_DIR;

    @Value("${extension}")
    private String EXTENSION;

    @Override
    public String uploadFile(MultipartFile file, Long url) throws BadRequestBodyException, IOException {
        try {
            logger.info("Started reading file");
            byte[] bytes = file.getBytes();
            String dir = BASE_UPLOAD_DIR + url + EXTENSION;
            logger.debug("Uploading file into directory: {}", dir);
            Path path = Paths.get(dir);
            Files.write(path, bytes);
            logger.debug("uploaded file");
            return dir;
        } catch (MaxUploadSizeExceededException e) {
           throw new BadRequestBodyException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] getFile(String dir) throws IOException {
        logger.info("Fetching file from dir: {}", dir);
        Path path = Paths.get(dir);
        byte[] bytes = Files.readAllBytes(path);
        return bytes;
    }
}
