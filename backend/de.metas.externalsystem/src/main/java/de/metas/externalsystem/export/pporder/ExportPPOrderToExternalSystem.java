/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.export.pporder;

import ch.qos.logback.classic.Level;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public abstract class ExportPPOrderToExternalSystem extends ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportPPOrderToExternalSystem.class);

	protected final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);

	private final ExternalSystemConfigService externalSystemConfigService;

	protected ExportPPOrderToExternalSystem(
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender);

		this.externalSystemConfigService = externalSystemConfigService;
	}

	@Override
	protected Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference ppOrderRecordReference,
			@NonNull final PInstanceId pInstanceId)
	{
		final PPOrderId ppOrderId = ppOrderRecordReference.getIdAssumingTableName(I_PP_Order.Table_Name, PPOrderId::ofRepoId);

		final ExternalSystemParentConfig config = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		if (!config.isActive())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemParentConfig: {} isActive = false! No action is performed!", config.getId());
			return Optional.empty();
		}

		final Map<String, String> parameters = buildParameters(config.getChildConfig(), ppOrderId);
		if (parameters.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No parameters computed for ExternalSystemParentConfig: {}! No action is performed!", config.getId());
			return Optional.empty();
		}

		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		final String orgCode = orgDAO.getById(ppOrder.getAD_Org_ID()).getValue();

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
								   .orgCode(orgCode)
								   .adPInstanceId(JsonMetasfreshId.of(pInstanceId.getRepoId()))
								   .command(getExternalCommand())
								   .parameters(parameters)
								   .traceId(externalSystemConfigService.getTraceId())
								   .externalSystemChildConfigValue(config.getChildConfig().getValue())
								   .writeAuditEndpoint(config.getAuditEndpointIfEnabled())
								   .build());
	}

	@Override
	protected void runPreExportHook(final TableRecordReference recordReferenceToExport)
	{
	}

	protected abstract Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final PPOrderId ppOrderId);

	protected abstract String getExternalCommand();
}
