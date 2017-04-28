package de.metas.handlingunits;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan // scan the classes in the material planning sub-packages for components. Without this, we need to have @Bean annotated methods in here
public class HandlingUnitsConfiguration
{
}