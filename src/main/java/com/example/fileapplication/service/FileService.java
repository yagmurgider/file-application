package com.example.fileapplication.service;

import com.example.fileapplication.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileDTO store(MultipartFile file) throws IOException;

    String getFileByteArray(Long id) throws IOException ;

    FileDTO findById(Long id);

    FileDTO save(FileDTO fileDTO);

    Boolean deleteById(Long id);

    List<FileDTO> findByAll();

}
