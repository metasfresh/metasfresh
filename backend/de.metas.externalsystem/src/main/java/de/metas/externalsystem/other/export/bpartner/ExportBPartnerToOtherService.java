/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem.other.export.bpartner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.bpartner.ExportBPartnerToExternalSystem;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ExportBPartnerToOtherService extends ExportBPartnerToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER = "exportBPartner";

	protected ExportBPartnerToOtherService(
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull ExternalSystemConfigService externalSystemConfigService)
	{
		super(externalSystemConfigRepo,
			  externalSystemMessageSender,
			  dataExportAuditLogRepository,
			  dataExportAuditRepository,
			  externalSystemConfigService);
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Other;
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_BPARTNER;
	}

	@Override
	@NonNull
	protected Map<String, String> buildParameters(final @NonNull IExternalSystemChildConfig childConfig, final @NonNull BPartnerId bPartnerId)
	{
		final ExternalSystemOtherConfig otherConfig = ExternalSystemOtherConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();
		otherConfig.getParameters().forEach(parameter -> parameters.put(parameter.getName(), parameter.getValue()));

		parameters.put(ExternalSystemConstants.PARAM_BPARTNER_ID, String.valueOf(bPartnerId.getRepoId()));

		return parameters;
	}

	@Override
	@NonNull
	protected Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final BPartnerId bPartnerId)
	{
		final IBPartnerDAO.BPartnerLocationQuery bPartnerLocationQuery = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(bPartnerId)
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.build();

		final I_C_BPartner_Location partnerLocationId = bPartnerDAO.retrieveBPartnerLocation(bPartnerLocationQuery);

		if (partnerLocationId == null)
		{
			return Optional.empty();
		}

		final ImmutableList<ExternalSystemParentConfig> parentConfigs = externalSystemConfigRepo.getActiveByType(ExternalSystemType.Other);

		return Optional.of(parentConfigs.stream()
								   .filter(ExternalSystemParentConfig::isActive)
								   .map(ExternalSystemParentConfig::getChildConfig)
								   .map(ExternalSystemOtherConfig::cast)
								   .filter(childConfig -> isSyncBPartnerEnabled(childConfig) && childConfig.isAutoSendDefaultShippingAddress())
								   .map(IExternalSystemChildConfig::getId)
								   .collect(ImmutableSet.toImmutableSet()));
	}

	@Override
	protected boolean isSyncBPartnerEnabled(final @NonNull IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemOtherConfig externalSystemOtherConfig = ExternalSystemOtherConfig.cast(childConfig);

		return externalSystemOtherConfig.isSyncBPartnerEnabled();
	}
}
