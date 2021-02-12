package com.example.fileapplication.service.impl;

import com.example.fileapplication.dto.GenericResponse;
import com.example.fileapplication.dto.FileDTO;
import com.example.fileapplication.dto.ResponseMessage;
import com.example.fileapplication.entity.FileEntity;
import com.example.fileapplication.enums.ContentType;
import com.example.fileapplication.repository.FileRepository;
import com.example.fileapplication.service.FileService;
import com.example.fileapplication.util.TPage;
import org.aspectj.util.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private HttpServletRequest request;


    @Override
    public GenericResponse<FileDTO> store(MultipartFile file) throws IOException {
        GenericResponse<FileDTO> response = new GenericResponse<>();

        if (!this.checkContentType(file.getContentType())) {
            response.setResponseMessage(new ResponseMessage("Unexpected file extension: " + file.getContentType()));
            return response;
        }

        if(this.fileRepository.findByName(file.getOriginalFilename()) != null) {
            response.setResponseMessage(new ResponseMessage("Multiple files with the same name cannot be uploaded!"));
            return response;
        }

        String realPathtoUploads =  request.getServletContext().getRealPath("/uploads/");
        if(!new File(realPathtoUploads).exists()) {
            new File(realPathtoUploads).mkdir();
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = realPathtoUploads + fileName;
        File dest = new File(filePath);
        file.transferTo(dest);

        FileDTO entity = this.save(new FileDTO(fileName, filePath, file.getSize(), file.getContentType()));
        response.setData(modelMapper.map(entity, FileDTO.class));
        response.setResponseMessage(new ResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
        return response;
    }

    private boolean checkContentType(String extension) {
        ContentType contentType = ContentType.findByName(extension);
        return contentType != null;
    }

    @Override
    public String getFileByteArray(Long id) throws IOException {
        Optional<FileEntity> entity = fileRepository.findById(id);
        if (entity.isPresent() && entity.get().getPath() != null) {
            File file = new File(entity.get().getPath());
            byte[] bytes = FileUtil.readAsByteArray(file);
            return new String(bytes, "UTF-8");
        }
        return null;
    }

    @Override
    public FileDTO findById(Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        if (fileEntity.isPresent()) {
            return modelMapper.map(fileEntity.get(),FileDTO.class);
        }
        return null;
    }

    @Override
    public FileDTO save(FileDTO fileDTO) {
        FileEntity entity = modelMapper.map(fileDTO,FileEntity.class);
        fileRepository.save(entity);
        return fileDTO;
    }

    @Override
    public GenericResponse deleteById (Long id) throws IOException{
        GenericResponse response = new GenericResponse();
        Optional<FileEntity> entity = fileRepository.findById(id);
        if (entity.isPresent() && entity.get().getPath() != null) {
                boolean isDeletedFromPath = Files.deleteIfExists(Paths.get(entity.get().getPath()));
                if (isDeletedFromPath) {
                    this.fileRepository.deleteById(entity.get().getId());
                    response.setResponseMessage(new ResponseMessage("File deleted."));
                }

        } else {
            response.setResponseMessage(new ResponseMessage("File does not exist ! ID: " + id));
        }
        return response;
    }

    @Override
    public List<FileDTO> findByAll() {
        List<FileEntity> files = fileRepository.findAll();
        List<FileDTO> fileDTOS = new ArrayList<>();
        for (FileEntity item : files) {
            fileDTOS.add(modelMapper.map(item, FileDTO.class));
        }
        return fileDTOS;
    }

    @Override
    public TPage<FileDTO> getAllPageable(Pageable pageable) {
        Page<FileEntity> data = this.fileRepository.findAll(pageable);
        TPage<FileDTO> response = new TPage<FileDTO>();
        response.setStat(data, Arrays.asList(modelMapper.map(data.getContent(), FileDTO[].class)));
        return response;
    }
}
