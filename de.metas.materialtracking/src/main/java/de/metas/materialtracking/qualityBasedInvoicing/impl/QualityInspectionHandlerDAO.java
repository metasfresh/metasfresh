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
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList_Version;

import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionHandlerDAO;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;

public class QualityInspectionHandlerDAO implements IQualityInspectionHandlerDAO
{
	@Override
	public <T extends I_C_Invoice_Candidate> List<T> retrieveOriginalInvoiceCandidates(final I_M_Material_Tracking materialTracking, final Class<T> clazz)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final List<I_C_OrderLine> orderLines = materialTrackingDAO.retrieveReferences(materialTracking, I_C_OrderLine.class);

		final List<T> result = new ArrayList<>();
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
	public I_C_Invoice_Clearing_Alloc retrieveInitialInvoiceClearingAlloc(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate invoiceCandidate)
	{
		Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");
		Check.assume(invoiceCandidate.isToClear(), "Invoice Candidate shall have IsToClear flag set: {}", invoiceCandidate);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_C_Invoice_Clearing_Alloc invoiceClearingAlloc = queryBL
				.createQueryBuilder(I_C_Invoice_Clearing_Alloc.class, invoiceCandidate)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Cand_ToClear_ID, invoiceCandidate.getC_Invoice_Candidate_ID())
				.addEqualsFilter(I_C_Invoice_Clearing_Alloc.COLUMNNAME_C_Invoice_Candidate_ID, null)
				.create()
				.firstOnly(I_C_Invoice_Clearing_Alloc.class);

		Check.assumeNotNull(invoiceClearingAlloc, "invoiceClearingAlloc shall not be null for {}", invoiceCandidate);

		return invoiceClearingAlloc;
	}

	@Override
	public void updateICFromMaterialTracking(final I_C_Invoice_Candidate ic, final Object referencedObject)
	{
		if (referencedObject == null)
		{
			return; // nothing to do
		}

		//
		// set values from the referencedObject's material tracking
		//
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
		// task 09668: for "two-phase" material trackings (with downpayment and finla settlement), the packaging shall *not* be part of the invoice
		final IQualityBasedSpiProviderService qualityBasedSpiProviderService = Services.get(IQualityBasedSpiProviderService.class);
		final IQualityBasedConfig config = qualityBasedSpiProviderService.getQualityBasedConfigProvider().provideConfigFor(materialTracking);
		if (config.getOverallNumberOfInvoicings() == 1)
		{
			ic.setC_DocTypeInvoice_ID(config.getC_DocTypeInvoice_FinalSettlement_ID());
		}

		// ----------------
		ic.setM_PricingSystem_ID(materialTracking.getC_Flatrate_Term().getM_PricingSystem_ID());

		if (!InterfaceWrapperHelper.isInstanceOf(referencedObject, I_M_InOutLine.class))
		{
			return; // we are done
		}

		final I_M_InOutLine iol = InterfaceWrapperHelper.create(referencedObject, I_M_InOutLine.class);

		final I_M_InOut inOut = iol.getM_InOut();
		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

		final boolean processedPLVFiltering = true; // in the material tracking context, only processed PLVs matter.
		final I_M_PriceList_Version plv = priceListBL.getCurrentPriceListVersionOrNull(
				ic.getM_PricingSystem_ID(),
				inOut.getC_BPartner_Location().getC_Location().getC_Country_ID(),
				inOut.getMovementDate(),
				inOut.isSOTrx(),
				processedPLVFiltering);
		ic.setM_PriceList_Version(plv);
	}

	@Override
	public List<I_C_Invoice_Candidate> retrieveRelatedICs(final Object model)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking materialTrackingForModel = materialTrackingDAO.retrieveMaterialTrackingForModel(model);
		if (materialTrackingForModel == null)
		{
			return Collections.emptyList();
		}

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class, model)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.materialtracking.model.I_C_Invoice_Candidate.COLUMNNAME_M_Material_Tracking_ID, materialTrackingForModel.getM_Material_Tracking_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsPackagingMaterial, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsToClear, false)
				.orderBy().addColumn(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID).endOrderBy()
				.create()
				.list();
	}
}
