/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;

@Interceptor(I_ExternalSystem_Config_ScriptedExportConversion.class)
@Component
@RequiredArgsConstructor
public class ExternalSystem_Config_ScriptedExportConversion
{
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(ExternalSystem_Config_ScriptedExportConversion.class);
	@NonNull private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService;
	@NonNull private final ExternalSystemScriptedExportConversionInterceptorRegistry externalSystemScriptedExportConversionInterceptorRegistry;

    // States
	@NonNull private ImmutableSet<AdTableAndClientId> tableAndClientIds = ImmutableSet.of();
	@NonNull private final HashSet<AdTableAndClientId> registeredTableAndClientIds = new HashSet<>();
	@Nullable private ModelValidationEngine engine;

	@NonNull private ModelValidationEngine getEngine() {return Check.assumeNotNull(this.engine, "engine is set");}

	@Init
	public void init()
	{
		this.engine = ModelValidationEngine.get();
		externalSystemScriptedExportConversionService.addCacheResetListener(this::updateFromRepository);

		updateFromRepository();
	}

	private synchronized void updateFromRepository()
	{
		final ImmutableSet<AdTableAndClientId> tableAndClientIdsPrev = ImmutableSet.copyOf(this.tableAndClientIds);
		this.tableAndClientIds = externalSystemScriptedExportConversionService.getTriggerOnCompleteDistinctTableAndClientIds();
		if(Objects.equals(this.tableAndClientIds, tableAndClientIdsPrev))
		{
			return;
		}

		updateRegisteredInterceptors();
	}

	private synchronized void updateRegisteredInterceptors()
	{
		final HashSet<AdTableAndClientId> registeredClientAndTableIdsNoLongerNeeded = new HashSet<>(this.registeredTableAndClientIds);

		for (final AdTableAndClientId tableAndClientId : this.tableAndClientIds)
		{
			registeredClientAndTableIdsNoLongerNeeded.remove(tableAndClientId);

			if (registeredTableAndClientIds.contains(tableAndClientId))
			{
				// already registered
				continue;
			}

			registerInterceptor(tableAndClientId);
		}

		//
		// Remove no longer necessary interceptors
		for (final AdTableAndClientId tableAndClientId : registeredClientAndTableIdsNoLongerNeeded)
		{
			unregisterInterceptor(tableAndClientId);
		}
	}

	private void registerInterceptor(@NonNull final AdTableAndClientId tableAndClientId)
	{
		final ExternalSystemScriptedExportConversionInterceptor interceptor = new ExternalSystemScriptedExportConversionInterceptor(
				getEngine(),
				externalSystemScriptedExportConversionService,
				tableAndClientId);

		externalSystemScriptedExportConversionInterceptorRegistry.registerInterceptor(interceptor);
		registeredTableAndClientIds.add(tableAndClientId);
		logger.info("Registered producer for {}", tableAndClientId);
	}

	private void unregisterInterceptor(@NonNull final AdTableAndClientId tableAndClientId)
	{
		externalSystemScriptedExportConversionInterceptorRegistry.unregisterInterceptorByTableAndClientId(tableAndClientId);
		registeredTableAndClientIds.remove(tableAndClientId);
		logger.info("Unregistered trigger for {}", tableAndClientId);
	}
}
