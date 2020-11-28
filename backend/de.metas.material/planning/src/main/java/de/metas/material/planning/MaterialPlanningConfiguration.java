package de.metas.material.planning;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.metas.ShutdownListener;
import de.metas.StartupListener;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Configuration
@ComponentScan(basePackageClasses = {
		MaterialPlanningConfiguration.class, // scan the classes in the material planning sub-packages for components. Without this, we need to have @Bean annotated methods in here
		StartupListener.class,
		ShutdownListener.class,
		})
public class MaterialPlanningConfiguration
{

}
