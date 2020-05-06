package org.eevolution;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.metas.material.event.MaterialEventConfiguration;
import de.metas.material.planning.MaterialPlanningConfiguration;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
		LiberoConfiguration.class, // scan the classes in *this* package and its subpackages
		MaterialPlanningConfiguration.class, // scan the classes in the material planning sub-packages for components. Without this, we need to have @Bean annotated methods in here
		MaterialEventConfiguration.class,
})
public class LiberoConfiguration
{
}
