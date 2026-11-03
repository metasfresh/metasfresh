/*
 * #%L
 * de.metas.business
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

package de.metas.request.api.impl;

import de.metas.request.RequestStatusCategoryId;
import de.metas.request.RequestStatusId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.I_R_Status;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestStatusService
{
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
	@NonNull private final RequestStatusRepository requestStatusRepository;

	public RequestStatusId getStatusIdByRequestTypeIdAndName(final RequestTypeId requestTypeId, final String statusName)
	{
		final I_R_RequestType requestType = requestTypeDAO.getById(requestTypeId);
		final RequestStatusCategoryId requestStatusCategoryId = RequestStatusCategoryId.ofRepoId(requestType.getR_StatusCategory_ID());
		final I_R_Status status = requestStatusRepository.getByCategoryAndName(requestStatusCategoryId, statusName);
		return RequestStatusId.ofRepoId(status.getR_Status_ID());
	}

	public I_R_Status getById(@NonNull final RequestStatusId statusId)
	{
		return requestStatusRepository.getById(statusId);
	}

}
