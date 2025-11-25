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

import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Interceptor(I_ExternalSystem_Config_ScriptedExportConversion.class)
@Component
@RequiredArgsConstructor
public class ExternalSystem_Config_ScriptedExportConversion
{
	@NonNull
	private final ExternalSystemScriptedExportConversionService externalSystemScriptedExportConversionService;
	@NonNull
	private final ExternalSystemScriptedExportConversionInterceptorRegistry externalSystemScriptedExportConversionInterceptorRegistry;

	private ModelValidationEngine engine;

	@Init
	public void initialize()
	{
		this.engine = ModelValidationEngine.get();

		final Set<AdTableAndClientId> alreadyRegisteredTableIds = new HashSet<>();

		externalSystemScriptedExportConversionService.retrieveActiveConfigs()
				.forEach(activeConfig -> {
					final AdTableAndClientId adTableAndClientId = AdTableAndClientId.of(activeConfig.getAdTableId(), activeConfig.getClientId());
					if (!alreadyRegisteredTableIds.contains(adTableAndClientId))
					{
						registerInterceptor(activeConfig);
						alreadyRegisteredTableIds.add(AdTableAndClientId.of(activeConfig.getAdTableId(), activeConfig.getClientId()));
					}
				});
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_IsActive,
					I_ExternalSystem_Config_ScriptedExportConversion.COLUMNNAME_AD_Table_ID })
	public void manageRecordInterceptors(@NonNull final I_ExternalSystem_Config_ScriptedExportConversion config)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion oldRecord = InterfaceWrapperHelper.createOld(config, I_ExternalSystem_Config_ScriptedExportConversion.class);
		final AdTableAndClientId oldTableAndClientId = Optional.ofNullable(AdTableId.ofRepoIdOrNull(oldRecord.getAD_Table_ID()))
				.map(tableId -> AdTableAndClientId.of(tableId, ClientId.ofRepoId(oldRecord.getAD_Client_ID())))
				.orElse(null);
		final AdTableAndClientId tableAndClientId = AdTableAndClientId.of(AdTableId.ofRepoId(config.getAD_Table_ID()), ClientId.ofRepoId(config.getAD_Client_ID()));
		if (config.isActive())
		{
			if (oldTableAndClientId != null && !oldTableAndClientId.equals(tableAndClientId) &&
					!externalSystemScriptedExportConversionService.existsActive(oldTableAndClientId))
			{
				externalSystemScriptedExportConversionInterceptorRegistry.unregisterInterceptorByTableAndClientId(oldTableAndClientId);
			}

			if (externalSystemScriptedExportConversionService.existsActiveOutOfTrx(tableAndClientId))
			{
				return;
			}

			registerInterceptor(config);
		}
		else
		{
			if (!externalSystemScriptedExportConversionService.existsActive(tableAndClientId) && oldRecord.isActive())
			{
				externalSystemScriptedExportConversionInterceptorRegistry.unregisterInterceptorByTableAndClientId(tableAndClientId);
			}
		}
	}

	private void registerInterceptor(@NonNull final ExternalSystemScriptedExportConversionConfig config)
	{
		final ExternalSystemScriptedExportConversionInterceptor interceptor = new ExternalSystemScriptedExportConversionInterceptor(
				engine,
				externalSystemScriptedExportConversionService,
				AdTableAndClientId.of(config.getAdTableId(), config.getClientId()));

		externalSystemScriptedExportConversionInterceptorRegistry.registerInterceptor(interceptor);
	}

	private void registerInterceptor(@NonNull final I_ExternalSystem_Config_ScriptedExportConversion config)
	{
		final ExternalSystemScriptedExportConversionInterceptor interceptor = new ExternalSystemScriptedExportConversionInterceptor(
				engine,
				externalSystemScriptedExportConversionService,
				AdTableAndClientId.of(
						AdTableId.ofRepoId(config.getAD_Table_ID()),
						ClientId.ofRepoId(config.getAD_Client_ID())));

		externalSystemScriptedExportConversionInterceptorRegistry.registerInterceptor(interceptor);
	}
}
