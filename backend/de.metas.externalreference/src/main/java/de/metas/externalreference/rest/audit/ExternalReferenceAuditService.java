/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference.rest.audit;

import de.metas.JsonMapperUtil;
import de.metas.audit.data.Action;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.IMasterDataExportAuditService;
import de.metas.audit.data.service.DataExportAuditRequest;
import de.metas.audit.data.service.DataExportAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.v2.JsonExternalReferenceResponseItem;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalreference.rest.v2.ExternalReferenceRestController;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class ExternalReferenceAuditService implements IMasterDataExportAuditService
{
	//dev-note: meant to capture any rest calls made against `EXTERNAL_REFERENCE_RESOURCE`
	private final static String EXTERNAL_REFERENCE_RESOURCE = ExternalReferenceRestController.EXTERNAL_REFERENCE_REST_CONTROLLER_PATH_V2 + "/**";

	private final DataExportAuditService dataExportAuditService;

	public ExternalReferenceAuditService(@NonNull final DataExportAuditService dataExportAuditService)
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

		final Object exportedObject = genericDataExportAuditRequest.getExportedObject();
		final Optional<JsonExternalReferenceLookupResponse> jsonExternalReferenceLookupResponse = JsonMapperUtil.tryDeserializeToType(exportedObject, JsonExternalReferenceLookupResponse.class);

		final ExternalSystemParentConfigId externalSystemParentConfigId = genericDataExportAuditRequest.getExternalSystemParentConfigId();
		final PInstanceId pInstanceId = genericDataExportAuditRequest.getPInstanceId();

		jsonExternalReferenceLookupResponse.ifPresent(lookupResponse -> lookupResponse.getItems()
				.forEach(item -> auditExternalReference(item, externalSystemParentConfigId, pInstanceId)));
	}

	@Override
	public boolean isHandled(final GenericDataExportAuditRequest genericDataExportAuditRequest)
	{
		final AntPathMatcher antPathMatcher = new AntPathMatcher();

		return antPathMatcher.match(EXTERNAL_REFERENCE_RESOURCE, genericDataExportAuditRequest.getRequestURI());
	}

	private void auditExternalReference(
			@NonNull final JsonExternalReferenceResponseItem jsonExternalReferenceRequestItem,
			@Nullable final ExternalSystemParentConfigId externalSystemParentConfigId,
			@Nullable final PInstanceId pInstanceId)
	{
		if (jsonExternalReferenceRequestItem.getExternalReferenceId() == null)
		{
			return;
		}

		final DataExportAuditRequest jsonExternalReferenceItemRequest = DataExportAuditRequest.builder()
				.tableRecordReference(TableRecordReference.of(I_S_ExternalReference.Table_Name, jsonExternalReferenceRequestItem.getExternalReferenceId().getValue()))
				.action(Action.Standalone)
				.externalSystemConfigId(externalSystemParentConfigId)
				.adPInstanceId(pInstanceId)
				.build();

		dataExportAuditService.createExportAudit(jsonExternalReferenceItemRequest);
	}
}
