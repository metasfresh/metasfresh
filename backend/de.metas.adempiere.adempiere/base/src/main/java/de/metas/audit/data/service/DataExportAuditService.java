/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.data.service;

import de.metas.audit.data.model.CreateDataExportAuditLogRequest;
import de.metas.audit.data.model.CreateDataExportAuditRequest;
import de.metas.audit.data.model.DataExportAudit;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

/**
 * "Low-level" service that handles the actual master data audit. Should be used by implementations of {@link de.metas.audit.data.IMasterDataExportAuditService}.
 */
@Service
public class DataExportAuditService
{
	private final DataExportAuditRepository dataExportAuditRepository;
	private final DataExportAuditLogRepository dataExportAuditLogRepository;

	public DataExportAuditService(
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository)
	{
		this.dataExportAuditRepository = dataExportAuditRepository;
		this.dataExportAuditLogRepository = dataExportAuditLogRepository;
	}

	@NonNull
	public DataExportAuditId createExportAudit(@NonNull final DataExportAuditRequest dataExportAuditRequest)
	{
		final CreateDataExportAuditRequest.CreateDataExportAuditRequestBuilder createDataExportAuditRequestBuilder = CreateDataExportAuditRequest.builder()
				.tableRecordReference(dataExportAuditRequest.getTableRecordReference())
				.parentId(dataExportAuditRequest.getParentExportAuditId());

		dataExportAuditRepository.getByTableRecordReference(dataExportAuditRequest.getTableRecordReference())
				.ifPresent(dataExportAudit -> createDataExportAuditRequestBuilder.dataExportAuditId(dataExportAudit.getId()));

		final DataExportAudit savedDataExportAudit = dataExportAuditRepository.save(createDataExportAuditRequestBuilder.build());

		final CreateDataExportAuditLogRequest newAuditLog = CreateDataExportAuditLogRequest.builder()
				.dataExportAuditId(savedDataExportAudit.getId())
				.action(dataExportAuditRequest.getAction())
				.externalSystemConfigId(dataExportAuditRequest.getExternalSystemConfigId())
				.adPInstanceId(dataExportAuditRequest.getAdPInstanceId())
				.build();

		dataExportAuditLogRepository.createNew(newAuditLog);

		return savedDataExportAudit.getId();
	}
}
