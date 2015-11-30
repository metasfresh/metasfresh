package de.metas.materialtracking.qualityBasedInvoicing.impl;

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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Order;

import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionHandlerDAO;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;

public class QualityInspectionHandlerDAO implements IQualityInspectionHandlerDAO
{
	@Override
	public Iterator<de.metas.materialtracking.model.I_PP_Order> retrievePPOrdersWithMissingICs(final Properties ctx, final int limit, final String trxName)
	{
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_PP_Order> ppOrderQueryBuilder = queryBL.createQueryBuilder(I_PP_Order.class, ctx, trxName)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter();

		//
		// Only those manufacturing orders which are invoiceable
		ppOrderQueryBuilder.filter(getInvoiceableOrderFilter());

		//
		// Only those manufacturing orders which do not have an invoice candidate
		{
			final int ppOrderTableId = InterfaceWrapperHelper.getTableId(I_PP_Order.class);
			final IQuery<I_C_Invoice_Candidate> invoiceCandidatesForPPOrderQuery = queryBL
					.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
					.addEqualsFilter(de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, ppOrderTableId)
					.create();

			ppOrderQueryBuilder.addNotInSubQueryFilter(I_PP_Order.COLUMNNAME_PP_Order_ID,
					de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_Record_ID,
					invoiceCandidatesForPPOrderQuery);
		}

		//
		// Order by
		// (just to have a predictable order)
		ppOrderQueryBuilder.orderBy()
				.addColumn(I_PP_Order.COLUMN_PP_Order_ID);

		//
		// Execute query and return
		return ppOrderQueryBuilder
				.setLimit(limit)
				.create()
				.iterate(de.metas.materialtracking.model.I_PP_Order.class);
	}

	/**
	 * Gets a filter which accepts only those {@link I_PP_Order}s which are invoiceable.
	 *
	 * More precisely, manufacturing orders which are:
	 * <ul>
	 * <li>quality inspection
	 * <li>are closed
	 * </ul>
	 *
	 * @return
	 */
	@Override
	public IQueryFilter<I_PP_Order> getInvoiceableOrderFilter()
	{
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		final ICompositeQueryFilter<I_PP_Order> filters = queryBL.createCompositeQueryFilter(I_PP_Order.class);

		//
		// Only those manufacturing orders which are closed
		filters.addInArrayFilter(I_PP_Order.COLUMN_DocStatus, X_PP_Order.DOCSTATUS_Closed);

		//
		// Only those manufacturing orders which are quality inspection
		filters.addFilter(materialTrackingPPOrderBL.getQualityInspectionFilter());

		return filters;
	}

	/**
	 *
	 * @param ppOrder
	 * @return true if given manufacturing order is invoiceable
	 * @see #getInvoiceableOrderFilter()
	 */
	@Override
	public boolean isInvoiceable(final I_PP_Order ppOrder)
	{
		final IQueryFilter<I_PP_Order> invoiceableFilter = getInvoiceableOrderFilter();
		return invoiceableFilter.accept(ppOrder);
	}

	/**
	 * Gets a list of {@link I_C_Invoice_Candidate}s of original purchase order.
	 */
	@Override
	public <T extends I_C_Invoice_Candidate> List<T> retrieveOriginalInvoiceCandidates(final I_M_Material_Tracking materialTracking, final Class<T> clazz)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final List<I_C_OrderLine> orderLines = materialTrackingDAO.retrieveReferences(materialTracking, I_C_OrderLine.class);

		final List<T> result = new ArrayList<T>();
		for (final I_C_OrderLine orderLine : orderLines)
		{
			final List<T> invoiceCandidates = InterfaceWrapperHelper.createList(
					invoiceCandDAO.retrieveInvoiceCandidatesForOrderLine(orderLine),
					clazz);
			result.addAll(invoiceCandidates);
		}

		return result;
	}

	/**
	 * Retrieved the original IC's original allocation record..it's the one which has C_Invoice_Candidate_ID = null.
	 *
	 * @param invoiceCandidate assumed to have <code>IsToClear=Y</code>
	 * @return {@link I_C_Invoice_Clearing_Alloc}; never return null
	 */
	@Override
	public I_C_Invoice_Clearing_Alloc retrieveInitialInvoiceClearingAlloc(final I_C_Invoice_Candidate invoiceCandidate)
	{
		Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");
		Check.assume(invoiceCandidate.isToClear(), "Invoice Candidate shall have IsToClear flag set: {0}", invoiceCandidate);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_C_Invoice_Clearing_Alloc invoiceClearingAlloc = queryBL
				.createQueryBuilder(I_C_Invoice_Clearing_Alloc.class, invoiceCandidate)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Cand_ToClear_ID, invoiceCandidate.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, null)
				.create()
				.firstOnly(I_C_Invoice_Clearing_Alloc.class);

		Check.assumeNotNull(invoiceClearingAlloc, "invoiceClearingAlloc shall not be null for {0}", invoiceCandidate);

		return invoiceClearingAlloc;
	}

	@Override
	public void updateICFromMaterialTracking(final I_C_Invoice_Candidate ic, final Object referencedObject)
	{
		if (referencedObject == null)
		{
			return; // nothing to do
		}

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking materialTracking = materialTrackingDAO.retrieveMaterialTrackingForModel(referencedObject);
		if (materialTracking == null)
		{
			return; // the referenced object is not linked to a tracking
		}

		// set the tracking
		final de.metas.materialtracking.model.I_C_Invoice_Candidate icExt = InterfaceWrapperHelper.create(ic, de.metas.materialtracking.model.I_C_Invoice_Candidate.class);
		icExt.setM_Material_Tracking(materialTracking);

		// get the tracking's config and set its C_DocType
		final IQualityBasedConfig config = Services.get(IQualityBasedSpiProviderService.class).getQualityBasedConfigProvider().provideConfigFor(materialTracking);
		ic.setC_DocTypeInvoice_ID(config.getC_DocTypeInvoice_DownPayment_ID());

		// get the original IC via M_Material_Tracking_Refs
		final List<I_M_Material_Tracking_Ref> icMaterialTrackingRefs = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingRefForType(materialTracking, I_C_Invoice_Candidate.class);
		if (icMaterialTrackingRefs.isEmpty())
		{
			return;
		}
		final I_M_Material_Tracking_Ref firstRef = icMaterialTrackingRefs.get(0);
		final I_C_Invoice_Candidate originalIC = TableRecordCacheLocal.getReferencedValue(firstRef, I_C_Invoice_Candidate.class);

		// get C_Invoice_Clearing_Alloc => C_Flatrate_Term => C_Flatrate_Condidtions => M_PricingsSystem, or falls back to the original IC's pricing system
		if (!originalIC.isToClear())
		{
			// this can happen if the user created a tracking with a product & partner that have/has no contract. This shouldn't kill the whole IC-process.
			return; 
		}
		final I_C_Invoice_Clearing_Alloc invoiceClearingAlloc = Services.get(IQualityInspectionHandlerDAO.class).retrieveInitialInvoiceClearingAlloc(originalIC);
		if (invoiceClearingAlloc == null)
		{
			ic.setM_PricingSystem_ID(originalIC.getM_PricingSystem_ID()); // fallback
		}
		else
		{
			final I_C_Flatrate_Conditions flatrateConditions = invoiceClearingAlloc.getC_Flatrate_Term().getC_Flatrate_Conditions();
			if (flatrateConditions == null)
			{
				ic.setM_PricingSystem_ID(originalIC.getM_PricingSystem_ID()); // fallback
			}
			else
			{
				ic.setM_PricingSystem_ID(flatrateConditions.getM_PricingSystem_ID());
			}
		}
	}

}
