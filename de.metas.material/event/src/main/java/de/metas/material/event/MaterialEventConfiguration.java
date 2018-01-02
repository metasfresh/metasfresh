package de.metas.material.event;

import org.compiere.Adempiere;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.material.event.eventbus.MetasfreshEventBusService;

/*
 * #%L
 * metasfresh-material-event
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
@ComponentScan(basePackageClasses = MaterialEventConfiguration.class)
public class MaterialEventConfiguration
{
	public static final String BEAN_NAME = "metasfreshEventBusService";

	@Bean(name = BEAN_NAME)
	@Profile(Profiles.PROFILE_Test)
	public MetasfreshEventBusService createLocalMaterialEventService()
	{
		final MetasfreshEventBusService materialEventService = MetasfreshEventBusService
				.createLocalServiceThatIsReadyToUse();

		return materialEventService;
	}

	@Bean(name = BEAN_NAME)
	@DependsOn(Adempiere.BEAN_NAME)
	@Profile(Profiles.PROFILE_NotTest)
	public MetasfreshEventBusService createDistributedMaterialEventService()
	{
		final MetasfreshEventBusService materialEventService = MetasfreshEventBusService
				.createDistributedServiceThatNeedsToSubscribe();

		return materialEventService;
	}
}
