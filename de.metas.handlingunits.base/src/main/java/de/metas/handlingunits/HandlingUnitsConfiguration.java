package de.metas.handlingunits;

import org.eevolution.LiberoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
		// scan the classes in the material planning sub-packages for components. Without this, we need to have @Bean annotated methods in here
		HandlingUnitsConfiguration.class,

		// we need libero for some PPOrder and DDOrder related things. Note that when scanning the packages in org.eevolution, spring will also stumble over further configs that will also set up material-planning and material-event code.
		// if and when that material-* code is directly used in handlingunits, I suggest to explicitly add the classes here.
		LiberoConfiguration.class
})
public class HandlingUnitsConfiguration
{
}
