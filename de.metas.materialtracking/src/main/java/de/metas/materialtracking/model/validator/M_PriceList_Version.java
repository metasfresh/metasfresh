package de.metas.materialtracking.model.validator;

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

import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidator;

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingPPOrderDAO;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_PP_Order;

@Interceptor(I_M_PriceList_Version.class)
public class M_PriceList_Version
{
	/**
	 * Retrieves the <code>C_Invoice_Candidate</code>s that belong to the given <code>plv</code>, deletes their invoice candidates and calls
	 * {@link IInvoiceCandidateHandlerBL#invalidateCandidatesFor(Object)} which will cause them to be recreated.
	 *
	 * Notes
	 * <ul>
	 * <li><code>if plv.isProcessed==true</code>, then the PLV just became relevant for pricing. therefore so the previous PLV just "lost" a number of ICs it was previously in charge of.
	 * <li><code>if plv.isProcessed==false</code>, then the PLV just became irrelevant for pricing. Therefore the previous PLV just "gained" a number of ICs of which 'plv' was previously in charge.
	 * </ul>
	 *
	 * @param plv
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_M_PriceList_Version.COLUMNNAME_Processed)
	public void reenqueuePPOrders(final I_M_PriceList_Version plv)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IMaterialTrackingPPOrderDAO materialTrackingPPOrderDAO = Services.get(IMaterialTrackingPPOrderDAO.class);

		final org.compiere.model.I_M_PriceList_Version previousPLV = priceListDAO.retrievePreviousVersionOrNull(plv);

		final ICompositeQueryFilter<I_C_Invoice_Candidate> plvFilter =
				queryBL.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
						.setJoinOr()
						.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_PriceList_Version_ID, plv.getM_PriceList_Version_ID());
		if (previousPLV != null)
		{
			plvFilter.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_PriceList_Version_ID, previousPLV.getM_PriceList_Version_ID());
		}

		final int ppOrderTableID = adTableDAO.retrieveTableId(I_PP_Order.Table_Name);

		final List<Map<String, Object>> listDistinct = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, plv)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.filter(plvFilter)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, ppOrderTableID)
				.andCollectChildren(I_C_Invoice_Detail.COLUMN_C_Invoice_Candidate_ID, I_C_Invoice_Detail.class)
				.addNotEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_PP_Order_ID, null)
				.orderBy().addColumn(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Detail_ID).endOrderBy()
				.create()
				.listDistinct(I_C_Invoice_Detail.COLUMNNAME_PP_Order_ID);

		for (final Map<String, Object> colName2Value : listDistinct)
		{
			final Integer ppOrderId = (Integer)colName2Value.get(I_C_Invoice_Detail.COLUMNNAME_PP_Order_ID);
			if (ppOrderId == null)
			{
				continue; // imho this can't happen, but better safe than sorry
			}
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(
					InterfaceWrapperHelper.getCtx(plv),
					ppOrderId,
					I_PP_Order.class,
					InterfaceWrapperHelper.getTrxName(plv));
			ppOrder.setIsInvoiceCandidate(false);
			InterfaceWrapperHelper.save(ppOrder);

			// need to delete them, because if a PLV was un-processed, then its IC would not be deleted
			materialTrackingPPOrderDAO.deleteRelatedUnprocessedICs(ppOrder);
			invoiceCandidateHandlerBL.invalidateCandidatesFor(ppOrder);
		}
	}
}
