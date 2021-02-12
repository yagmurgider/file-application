package com.example.fileapplication.controller;

import com.example.fileapplication.dto.FileDTO;
import com.example.fileapplication.dto.ResponseMessage;
import com.example.fileapplication.service.FileService;
import com.example.fileapplication.util.ApiPaths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiPaths.FileCtrl.CTRL)
@Api(value = ApiPaths.FileCtrl.CTRL, description = "File APIs")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create File", response = FileDTO.class)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            this.fileService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!" + "\n" + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Get All File", response = FileDTO.class)
    public ResponseEntity<List<FileDTO>> getAll() {
        List<FileDTO> files = fileService.findByAll().stream().map(file -> {
            return new FileDTO(
                    file.getName(),
                    file.getPath(),
                    file.getSize(),
                    file.getExtention()
                    );
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @GetMapping("/getFileByteArray/{id}")
    @ApiOperation(value = "Get File With Byte Array", response = FileDTO.class)
    public ResponseEntity<String> getFileByteArray(@PathVariable Long id) throws IOException {
        try {
            String byteArray = fileService.getFileByteArray(id);
            //TODO: filename
            return ResponseEntity.status(HttpStatus.OK).body(byteArray);
        } catch (IOException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get By Id", response = FileDTO.class)
    public ResponseEntity<FileDTO> getById(@PathVariable Long id) {
        FileDTO fileDTO = fileService.findById(id);
        if (fileDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(fileDTO);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete By Id", response = FileDTO.class)
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable("id") Long id){
        try {
            Boolean result = fileService.deleteById(id);
            if (result) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File deleted."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("File doesn't exist!"));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
