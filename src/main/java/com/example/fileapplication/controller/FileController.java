package com.example.fileapplication.controller;

import com.example.fileapplication.dto.FileDTO;
import com.example.fileapplication.dto.ResponseMessage;
import com.example.fileapplication.service.FileService;
import com.example.fileapplication.util.ApiPaths;
import com.example.fileapplication.util.TPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.FileCtrl.CTRL)
@Api(value = ApiPaths.FileCtrl.CTRL, description = "File APIs")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create File", response = FileDTO.class)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity(this.fileService.store(file), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "Get All File", response = FileDTO.class)
    public ResponseEntity<List<FileDTO>> getAll() {
        return new ResponseEntity(fileService.findByAll(), HttpStatus.OK);
    }

    @GetMapping("/pagination")
    @ApiOperation(value = "Get By Pagination Operation", response = FileDTO.class)
    public ResponseEntity<TPage<FileDTO>> getAllByPagination(Pageable pageable) {
        TPage<FileDTO> data = this.fileService.getAllPageable(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/getFileByteArray/{id}")
    @ApiOperation(value = "Get File With Byte Array", response = String.class)
    public ResponseEntity<String> getFileByteArray(@PathVariable Long id) throws IOException {
        return new ResponseEntity(fileService.getFileByteArray(id), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get By Id", response = FileDTO.class)
    public ResponseEntity<FileDTO> getById(@PathVariable Long id) {
        return new ResponseEntity(this.fileService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete By Id", response = ResponseMessage.class)
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable("id") Long id){
        try {
            return new ResponseEntity(this.fileService.deleteById(id), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
