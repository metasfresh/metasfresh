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

import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_R_RequestType;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class RequestTypeService
{
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);

	@Nullable
	public RequestTypeId retrieveByInternalName(final @NonNull String requestType)
	{
		return requestTypeDAO.retrieveRequestTypeIdByInternalName(requestType);
	}

	public RequestTypeId retrieveCustomerRequestTypeId()
	{
		return requestTypeDAO.retrieveCustomerRequestTypeId();
	}

	public I_R_RequestType getById(final @NonNull RequestTypeId id)
	{
		return requestTypeDAO.getById(id);
	}
}
