/*
 * #%L
 * de.metas.materialtracking
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

package de.metas.materialtracking.impl;

import de.metas.materialtracking.MaterialTrackingId;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MaterialTrackingInvoiceCandRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<I_C_Invoice_Candidate> listByMaterialTrackingId(@NonNull final MaterialTrackingId materialTrackingId)
	{
		return getQueryForMaterialTrackingId(materialTrackingId)
				.list();
	}

	public Optional<I_C_Invoice_Candidate> getFirstForMaterialTrackingId(@NonNull final MaterialTrackingId materialTrackingId)
	{
		return getQueryForMaterialTrackingId(materialTrackingId)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.firstOptional();
	}

	private IQueryBuilder<I_C_Invoice_Candidate> getQueryForMaterialTrackingId(final @NonNull MaterialTrackingId materialTrackingId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_Material_Tracking_ID, materialTrackingId);
	}

	public void save(final @NonNull I_C_Invoice_Candidate candidate)
	{
		InterfaceWrapperHelper.save(candidate);
	}
}
