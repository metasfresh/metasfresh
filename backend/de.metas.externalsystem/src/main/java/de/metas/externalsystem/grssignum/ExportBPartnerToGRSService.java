/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.grssignum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExportDirectorySettings;
import de.metas.common.util.Check;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.bpartner.ExportBPartnerToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ExportBPartnerToGRSService extends ExportBPartnerToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER = "exportBPartner";

	protected ExportBPartnerToGRSService(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		super(externalSystemConfigRepo, externalSystemMessageSender, dataExportAuditLogRepository, dataExportAuditRepository, externalSystemConfigService);
	}

	@Override
	@NonNull
	protected Map<String, String> buildParameters(
			@NonNull final IExternalSystemChildConfig childConfig,
			@NonNull final BPartnerId bPartnerId)
	{
		final ExternalSystemGRSSignumConfig grsSignumConfig = ExternalSystemGRSSignumConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL, grsSignumConfig.getBaseUrl());
		parameters.put(ExternalSystemConstants.PARAM_TENANT_ID, grsSignumConfig.getTenantId());
		parameters.put(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE, grsSignumConfig.getValue());
		parameters.put(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_AUTH_TOKEN, grsSignumConfig.getAuthToken());
		parameters.put(ExternalSystemConstants.PARAM_BPARTNER_ID, String.valueOf(bPartnerId.getRepoId()));

		getJsonExportDirectorySettings(grsSignumConfig).ifPresent(settings -> parameters
				.put(ExternalSystemConstants.PARAM_JSON_EXPORT_DIRECTORY_SETTINGS, settings));

		return parameters;
	}

	@Override
	protected boolean isSyncBPartnerEnabled(final @NonNull IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemGRSSignumConfig grsSignumConfig = ExternalSystemGRSSignumConfig.cast(childConfig);

		return grsSignumConfig.isSyncBPartnersToRestEndpoint();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.GRSSignum;
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER;
	}

	@Override
	@NonNull
	protected Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final BPartnerId bPartnerId)
	{
		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);

		final boolean isVendor = bPartner.isVendor();
		final boolean isCustomer = bPartner.isCustomer();

		if (!isCustomer && !isVendor)
		{
			return Optional.empty();
		}

		final ImmutableList<ExternalSystemParentConfig> grsParentConfigs = externalSystemConfigRepo.getActiveByType(ExternalSystemType.GRSSignum);

		return Optional.of(grsParentConfigs.stream()
							.filter(ExternalSystemParentConfig::isActive)
							.map(ExternalSystemParentConfig::getChildConfig)
							.map(ExternalSystemGRSSignumConfig::cast)
							.filter(grsConfig -> (grsConfig.isAutoSendVendors() && isVendor) || (grsConfig.isAutoSendCustomers() && isCustomer))
							.map(IExternalSystemChildConfig::getId)
							.collect(ImmutableSet.toImmutableSet()));
	}

	@NonNull
	private static Optional<String> getJsonExportDirectorySettings(@NonNull final ExternalSystemGRSSignumConfig grsSignumConfig)
	{
		if (!grsSignumConfig.isCreateBPartnerFolders())
		{
			return Optional.empty();
		}

		if (Check.isBlank(grsSignumConfig.getBPartnerExportDirectories()) || Check.isBlank(grsSignumConfig.getBasePathForExportDirectories()))
		{
			throw new AdempiereException("BPartnerExportDirectories and BasePathForExportDirectories must be set!")
					.appendParametersToMessage()
					.setParameter("ExternalSystem_Config_GRSSignum_ID", grsSignumConfig.getId());
		}

		final List<String> directories = Arrays
				.stream(grsSignumConfig.getBPartnerExportDirectories().split(","))
				.filter(Check::isNotBlank)
				.collect(ImmutableList.toImmutableList());

		final JsonExportDirectorySettings exportDirectorySettings = JsonExportDirectorySettings.builder()
				.basePath(grsSignumConfig.getBasePathForExportDirectories())
				.directories(directories)
				.build();
		try
		{
			final String serializedExportDirectorySettings = JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(exportDirectorySettings);

			return Optional.of(serializedExportDirectorySettings);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
