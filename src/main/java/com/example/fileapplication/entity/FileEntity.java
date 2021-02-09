package com.example.fileapplication.entity;

import com.example.fileapplication.abstacts.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity extends AbstractEntity {
    private String name;
    private String path;
    private Long size;
    private String extention;
}

