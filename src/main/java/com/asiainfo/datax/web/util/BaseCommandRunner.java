/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.asiainfo.datax.web.util;

import org.jboss.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class BaseCommandRunner implements CommandLineRunner {

    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        log.info("-------------EXECUTE DATAX SYSTEM ACTION START----------------");
    }
}
