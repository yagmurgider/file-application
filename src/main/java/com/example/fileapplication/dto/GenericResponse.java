package com.example.fileapplication.dto;

import com.example.fileapplication.abstacts.AbstractGenericType;
import com.example.fileapplication.dto.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T extends AbstractGenericType> {
    protected T data;
    protected ResponseMessage responseMessage;


}
