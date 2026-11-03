/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.handlingunits.rest_api;

import de.metas.JsonMapperUtil;
import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.IMasterDataExportAuditService;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.service.DataExportAuditRequest;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

@Service
public class HUAuditService implements IMasterDataExportAuditService
{
	//dev-note: meant to capture any rest calls made against `HANDLING_UNITS_RESOURCES`
	private final static String[] HANDLING_UNITS_RESOURCES = {
			DeprecatedHandlingUnitsRestController.HU_REST_CONTROLLER_PATH + "/**",
			HandlingUnitsRestController.HU_REST_CONTROLLER_PATH + "/**" };

	private final DataExportAuditService dataExportAuditService;

	public HUAuditService(@NonNull final DataExportAuditService dataExportAuditService)
	{
		this.dataExportAuditService = dataExportAuditService;
	}

	@Override
	public void performDataAuditForRequest(final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		if (!isHandled(genericDataExportAuditRequest))
		{
			return;
		}

		final ExternalSystemParentConfigId externalSystemParentConfigId = genericDataExportAuditRequest.getExternalSystemParentConfigId();
		final PInstanceId pInstanceId = genericDataExportAuditRequest.getPInstanceId();

		final Object exportedObject = genericDataExportAuditRequest.getExportedObject();

		final Optional<JsonGetSingleHUResponse> jsonGetSingleHUResponse = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonGetSingleHUResponse.class);
		jsonGetSingleHUResponse.ifPresent(getSingleHUResponse -> auditHUResponse(getSingleHUResponse, externalSystemParentConfigId, pInstanceId));
	}

	@Override
	public boolean isHandled(final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		return Arrays.stream(HANDLING_UNITS_RESOURCES)
				.anyMatch(resource -> antPathMatcher.match(resource, genericDataExportAuditRequest.getRequestURI()));
	}

	private void auditHUResponse(
			@NonNull final JsonGetSingleHUResponse jsonGetSingleHUResponse,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		final JsonHU jsonHU = jsonGetSingleHUResponse.getResult();
		auditHU(jsonHU, /*parentExportAuditId*/ null, externalSystemParentConfigId, pInstanceId);
	}

	private void auditHU(
			@NonNull final JsonHU jsonHU,
			@Nullable final DataExportAuditId parentExportAuditId,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		if(jsonHU.getId() == null)
		{
			return;
		}
		
		final HuId huId = HuId.ofObject(jsonHU.getId());

		final Action exportAction = parentExportAuditId != null ? Action.AlongWithParent : Action.Standalone;

		final DataExportAuditRequest huDataExportAuditRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_M_HU.Table_Name, huId.getRepoId()))
				.action(exportAction)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.parentExportAuditId(parentExportAuditId)
				.build();

		final DataExportAuditId dataExportAuditId = dataExportAuditService.createExportAudit(huDataExportAuditRequest);

		if (jsonHU.getIncludedHUs() == null)
		{
			return;
		}

		jsonHU.getIncludedHUs().forEach(includedHU -> auditHU(includedHU, dataExportAuditId, externalSystemParentConfigId, pInstanceId));
	}
}
