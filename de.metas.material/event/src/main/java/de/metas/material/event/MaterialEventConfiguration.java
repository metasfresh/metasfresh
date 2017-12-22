package de.metas.material.event;

import java.util.Collection;
import java.util.Optional;

import org.compiere.Adempiere;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import lombok.NonNull;

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
	@Bean(name = "materialEventService")
	@Profile(Profiles.PROFILE_Test)
	public MaterialEventService createLocalMaterialEventService(@NonNull final Optional<Collection<MaterialEventListener>> listeners)
	{
		return MaterialEventService
				.createLocalServiceThatIsReadyToUse(listeners.orElse(ImmutableList.of()));
	}

	@Bean(name = "materialEventService")
	@DependsOn(Adempiere.BEAN_NAME)
	@Profile(Profiles.PROFILE_NotTest)
	public MaterialEventService createDistributedMaterialEventService(@NonNull final Optional<Collection<MaterialEventListener>> listeners)
	{
		final MaterialEventService materialEventService = MaterialEventService
				.createDistributedServiceThatNeedsToSubscribe(listeners.orElse(ImmutableList.of()));

		materialEventService.subscribeToEventBus();
		return materialEventService;
	}
}
