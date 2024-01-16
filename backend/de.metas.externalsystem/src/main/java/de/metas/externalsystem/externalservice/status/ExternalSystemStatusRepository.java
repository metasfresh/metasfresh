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

package de.metas.externalsystem.externalservice.status;

import de.metas.error.AdIssueId;
import de.metas.externalsystem.externalservice.common.ExternalStatus;
import de.metas.externalsystem.externalservice.externalserviceinstance.ExternalSystemServiceInstanceId;
import de.metas.externalsystem.model.I_ExternalSystem_Status;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ExternalSystemStatusRepository
{
	@NonNull
	public ExternalSystemStatus create(@NonNull final ExternalSystemServiceInstanceId instanceId, @NonNull final StoreExternalSystemStatusRequest request)
	{
		final I_ExternalSystem_Status record = newInstance(I_ExternalSystem_Status.class);

		record.setExternalSystem_Service_Instance_ID(instanceId.getRepoId());

		record.setExternalSystemStatus(request.getStatus().getCode());

		record.setExternalSystemMessage(request.getMessage());
		record.setAD_PInstance_ID(PInstanceId.toRepoId(request.getPInstanceId()));
		record.setAD_Issue_ID(AdIssueId.toRepoId(request.getAdIssueId()));

		saveRecord(record);

		return ofStatusRecord(record);
	}

	@NonNull
	public ExternalSystemStatus ofStatusRecord(@NonNull final I_ExternalSystem_Status record)
	{
		return ExternalSystemStatus.builder()
				.id(ExternalSystemStatusId.ofRepoId(record.getExternalSystem_Status_ID()))

				.instanceId(ExternalSystemServiceInstanceId.ofRepoId(record.getExternalSystem_Service_Instance_ID()))
				.status(ExternalStatus.ofCode(record.getExternalSystemStatus()))

				.issueId(AdIssueId.ofRepoIdOrNull(record.getAD_Issue_ID()))
				.pInstanceId(PInstanceId.ofRepoIdOrNull(record.getAD_PInstance_ID()))
				.message(record.getExternalSystemMessage())

				.build();
	}
}
