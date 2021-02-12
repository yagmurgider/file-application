package com.example.fileapplication.service;

import com.example.fileapplication.abstacts.GenericResponse;
import com.example.fileapplication.dto.FileDTO;
import com.example.fileapplication.util.TPage;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    GenericResponse<FileDTO> store(MultipartFile file) throws IOException;

    String getFileByteArray(Long id) throws IOException;

    FileDTO findById(Long id);

    FileDTO save(FileDTO fileDTO);

    GenericResponse deleteById(Long id) throws IOException;

    List<FileDTO> findByAll();

    TPage<FileDTO> getAllPageable(Pageable pageable);

}

