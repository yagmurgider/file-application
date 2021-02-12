package com.example.fileapplication.abstacts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;

public class AbstractGenericType implements Serializable {
    protected final Logger logger = LogManager.getLogger(this.getClass());
}
