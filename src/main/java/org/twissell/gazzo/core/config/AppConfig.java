package org.twissell.gazzo.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@ComponentScan(basePackages = {"org.twissell.gazzo.view", "org.twissell.gazzo.core", "org.twissell.gazzo.people"})
public class AppConfig {
 
}
