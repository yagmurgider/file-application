package com.example.fileapplication.abstacts;

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
