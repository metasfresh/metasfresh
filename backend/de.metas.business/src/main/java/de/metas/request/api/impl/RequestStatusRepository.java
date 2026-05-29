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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_R_Status;
import org.springframework.stereotype.Repository;

@Repository
public class RequestStatusRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_R_Status getById(@NonNull final RequestStatusId statusId)
	{
		return queryBL.createQueryBuilder(I_R_Status.class)
				.addEqualsFilter(I_R_Status.COLUMNNAME_R_Status_ID, statusId)
				.create()
				.firstOnlyNotNull(I_R_Status.class);
	}

	public I_R_Status getByCategoryAndName(@NonNull final RequestStatusCategoryId categoryId, @NonNull final String name)
	{
		return queryBL.createQueryBuilder(I_R_Status.class)
				.addEqualsFilter(I_R_Status.COLUMNNAME_R_StatusCategory_ID, categoryId)
				.addEqualsFilter(I_R_Status.COLUMNNAME_Name, name)
				.create()
				.firstOnlyNotNull();
	}

}
