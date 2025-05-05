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

package de.metas.externalsystem.printingclient;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem_Config_PrintingClient;
import de.metas.externalsystem.model.X_ExternalSystem_Config_PrintingClient;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.printing.PrintingClientRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PrintingClientExternalSystemService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ExternalSystemMessageSender externalSystemMessageSender;

	private final ExternalSystemConfigService externalSystemConfigService;
	private final IQueryBL queryBL;

	private final static String EXTERNAL_SYSTEM_COMMAND_PRINTING_CLIENT = "printingClient";

	public PrintingClientExternalSystemService(
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalSystemConfigService externalSystemConfigService,
			@NonNull final IQueryBL queryBL)
	{
		this.externalSystemMessageSender = externalSystemMessageSender;
		this.externalSystemConfigService = externalSystemConfigService;
		this.queryBL = queryBL;
	}

	public void notifyExternalSystemsAboutPrintJob(@NonNull final PrintingClientRequest request)
	{
		externalSystemMessageSender.send(toPrintingClientExternalSystemRequest(request));
	}

	@NonNull
	private JsonExternalSystemRequest toPrintingClientExternalSystemRequest(@NonNull final PrintingClientRequest request)
	{
		final I_ExternalSystem_Config_PrintingClient childConfigRecord = queryBL.createQueryBuilder(I_ExternalSystem_Config_PrintingClient.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(X_ExternalSystem_Config_PrintingClient.COLUMNNAME_ExternalSystem_Config_ID, request.getExternalSystemParentConfigId())
				.create()
				.firstOnlyNotNull(I_ExternalSystem_Config_PrintingClient.class);

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(ExternalSystemConstants.PARAM_TARGET_DIRECTORY, childConfigRecord.getTarget_Directory());
		parameters.put(ExternalSystemConstants.PARAM_PRINTING_QUEUE_ID, String.valueOf(request.getPrintingQueueId()));

		return JsonExternalSystemRequest.builder()
				.externalSystemConfigId(JsonMetasfreshId.of(childConfigRecord.getExternalSystem_Config_ID()))
				.externalSystemName(JsonExternalSystemName.of(ExternalSystemType.PrintClient.getName()))
				.parameters(parameters)
				.orgCode(orgDAO.getById(OrgId.ofRepoId(request.getOrgId())).getValue())
				.command(EXTERNAL_SYSTEM_COMMAND_PRINTING_CLIENT)
				.adPInstanceId(request.getPInstanceId() != null ? JsonMetasfreshId.of(request.getPInstanceId()) : null)
				.traceId(externalSystemConfigService.getTraceId())
				.externalSystemChildConfigValue(childConfigRecord.getExternalSystemValue())
				.writeAuditEndpoint(null)
				.build();
	}
}
