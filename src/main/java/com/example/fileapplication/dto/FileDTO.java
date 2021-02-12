package com.example.fileapplication.dto;

import com.example.fileapplication.abstacts.AbstractGenericType;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "File Data Transfer Object")
public class FileDTO extends AbstractGenericType {

    @NotNull
    @ApiModelProperty(required = true, value = "Name Of File")
    private String name;

    @NotNull
    @ApiModelProperty(required = true, value = "Path Of File")
    private String path;

    @NotNull
    @ApiModelProperty(required = true, value = "Size Of File")
    private Long size;

    @NotNull
    @ApiModelProperty(required = true, value = "Extention Of File")
    private String extention;
}
