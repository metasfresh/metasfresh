/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.edi.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.EDIDesadvId;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@code EDI_Desadv_M_InOut} junction table that records
 * which DESADVs are associated with a given shipment.
 */
@Repository
public class EDIDesadvInOutRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Creates a row in {@code EDI_Desadv_M_InOut} linking the given DESADV to the given shipment.
	 * If a row already exists for the {@code (desadvId, inOutId)} pair, the method does nothing (idempotent).
	 */
	public void assignDesadvToInOut(@NonNull final EDIDesadvId desadvId, @NonNull final InOutId inOutId)
	{
		final int existingId = queryBL.createQueryBuilder(I_EDI_Desadv_M_InOut.class)
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_EDI_Desadv_ID, desadvId)
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_M_InOut_ID, inOutId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
		if (existingId > 0)
		{
			return;
		}

		final I_EDI_Desadv_M_InOut row = InterfaceWrapperHelper.newInstance(I_EDI_Desadv_M_InOut.class);
		row.setEDI_Desadv_ID(desadvId.getRepoId());
		row.setM_InOut_ID(inOutId.getRepoId());
		InterfaceWrapperHelper.saveRecord(row);
	}

	/**
	 * Lists all DESADVs linked to the given {@code InOut} via the {@code EDI_Desadv_M_InOut} junction table.
	 * <p>
	 * The junction is the authoritative source of {@code (DESADV, InOut)} links for consolidated shipments
	 * (i.e. shipments aggregating multiple orders, where each order maps to its own DESADV).
	 * Callers walking from an {@code M_InOut} to its associated DESADV(s) must use this accessor
	 * rather than relying on {@code EDI_DesadvLine.M_InOutLine_ID} alone — the junction reflects the
	 * full set of links, including DESADVs for orders not represented in the current shipment lines.
	 * <p>
	 * Returns an empty list if no junction rows exist for the given {@code InOut}. This can happen for
	 * single-order shipments created before the junction migration backfilled the historical links;
	 * callers are expected to handle the empty case (typically by falling back to the legacy direct
	 * {@code EDI_Desadv.M_InOut_ID} reference or treating the shipment as un-DESADV'd).
	 * <p>
	 * Order of the returned list is unspecified.
	 */
	public ImmutableList<EDIDesadvId> listDesadvsForInOut(@NonNull final InOutId inOutId)
	{
		return queryBL.createQueryBuilder(I_EDI_Desadv_M_InOut.class)
				.addEqualsFilter(I_EDI_Desadv_M_InOut.COLUMNNAME_M_InOut_ID, inOutId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(row -> EDIDesadvId.ofRepoId(row.getEDI_Desadv_ID()))
				.collect(ImmutableList.toImmutableList());
	}
}
