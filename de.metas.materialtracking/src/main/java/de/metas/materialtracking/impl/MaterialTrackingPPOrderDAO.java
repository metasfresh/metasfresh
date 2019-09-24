package de.metas.materialtracking.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;

import de.metas.materialtracking.IMaterialTrackingPPOrderDAO;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MaterialTrackingPPOrderDAO implements IMaterialTrackingPPOrderDAO
{

	@Override
	public int deleteInvoiceDetails(final I_PP_Order ppOrder)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Detail.class, ppOrder)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMN_PP_Order_ID, ppOrder.getPP_Order_ID())
				.create()
				.delete();
	}

	@Override
	public int deleteRelatedUnprocessedICs(final I_PP_Order ppOrder)
	{
		// there might be many ICs with the regular PP_Order product but for different qualityInvoiceLineGroups
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// load the IC that whose C_Invoice_Detail references ppOrder.
		// we expect just one, but want to be lenient if there are > 1.
		final List<de.metas.materialtracking.model.I_C_Invoice_Candidate> ppOrderICs =
				queryBL.createQueryBuilder(I_C_Invoice_Detail.class, ppOrder)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_C_Invoice_Detail.COLUMN_PP_Order_ID, ppOrder.getPP_Order_ID())
						.andCollect(de.metas.invoicecandidate.model.I_C_Invoice_Detail.COLUMN_C_Invoice_Candidate_ID)
						.addOnlyActiveRecordsFilter()
						.create()
						.list(de.metas.materialtracking.model.I_C_Invoice_Candidate.class);

		if (ppOrderICs.isEmpty())
		{
			return 0;
		}

		int result = 0;
		for (final de.metas.materialtracking.model.I_C_Invoice_Candidate ppOrderIC : ppOrderICs)
		{
			// use ppOrderIC as a representative to delete all unprocessed ICs with the same PLV and M_Material_Tracking
			result += queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, ppOrder)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, Services.get(IADTableDAO.class).retrieveTableId(org.eevolution.model.I_PP_Order.Table_Name))
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, ppOrderIC.getRecord_ID())
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_PriceList_Version_ID, ppOrderIC.getM_PriceList_Version_ID())
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
					.addEqualsFilter(de.metas.materialtracking.model.I_C_Invoice_Candidate.COLUMNNAME_M_Material_Tracking_ID, ppOrderIC.getM_Material_Tracking_ID())
					.create()
					.delete();
		}
		return result;
	}

	@Override
	public boolean isPPOrderInvoicedForMaterialTracking(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final I_M_Material_Tracking materialTrackingRecord)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_Invoice_Detail.class, ppOrder)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.andCollect(de.metas.invoicecandidate.model.I_C_Invoice_Detail.COLUMN_C_Invoice_Candidate_ID)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, true)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_Material_Tracking_ID, materialTrackingRecord.getM_Material_Tracking_ID())
				.create()
				.match();
	}
}
