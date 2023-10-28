package com.sos.application.service.fileUploads;

import com.sos.application.exception.BadRequestBodyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String uploadFile(MultipartFile file, Long url) throws BadRequestBodyException, IOException;

    byte[] getFile(String dir) throws IOException;
}