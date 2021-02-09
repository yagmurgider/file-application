package com.example.fileapplication.service.impl;

import com.example.fileapplication.dto.FileDTO;
import com.example.fileapplication.entity.FileEntity;
import com.example.fileapplication.repository.FileRepository;
import com.example.fileapplication.service.FileService;
import org.aspectj.util.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    public FileDTO store(MultipartFile file) throws IOException {
        String realPathtoUploads =  request.getServletContext().getRealPath("/uploads/");
        if(!new File(realPathtoUploads).exists()) {
            new File(realPathtoUploads).mkdir();
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = realPathtoUploads + fileName;
        File dest = new File(filePath);
        file.transferTo(dest);

        return this.save(new FileDTO(fileName, filePath, file.getSize(), file.getContentType()));
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
    public Boolean deleteById(Long id) {
        Optional<FileEntity> entity = fileRepository.findById(id);
        if (entity.isPresent() && entity.get().getPath() != null) {
            try {
                boolean isDeletedFromPath = Files.deleteIfExists(Paths.get(entity.get().getPath()));
                if (isDeletedFromPath) {
                    this.fileRepository.deleteById(entity.get().getId());
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
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
}
