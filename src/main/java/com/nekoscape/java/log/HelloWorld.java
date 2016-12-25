package com.nekoscape.java.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class HelloWorld {
    private Logger logger = LogManager.getLogger(this);

    void sayHello(String name) {
        logger.info("Hello " + name);
    }
}
