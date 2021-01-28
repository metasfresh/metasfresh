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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.impl.PricingBL;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/* package */class MaterialTrackingDocuments implements IMaterialTrackingDocuments
{
	// Services
	private final transient IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
	private final transient IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
	private final IMaterialTrackingPPOrderDAO materialTrackingPPOrderDAO = Services.get(IMaterialTrackingPPOrderDAO.class);
	private final transient IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	// Parameters
	private final I_M_Material_Tracking _materialTracking;

	// Loaded values
	private List<IQualityInspectionOrder> _qualityInspectionOrders = null;
	private List<IQualityInspectionOrder> _allProductionOrders = null;
	private List<IQualityInspectionOrder> _notInvoicedProductionOrders = null;

	private MaterialTrackingDocumentsPricingInfo pricingInfo;

	/**
	 * @task http://dewiki908/mediawiki/index.php/09657_WP-Auswertung_wird_beim_Schlie%C3%9Fen_nicht_erstellt_%28109750474442%29
	 */
	private Set<Integer> ppOrdersToBeConsideredClosed = new HashSet<>();

	private Set<Integer> ppOrdersToBeConsideredNotClosed = new HashSet<>();

	public MaterialTrackingDocuments(@NonNull final I_M_Material_Tracking materialTracking)
	{
		_materialTracking = materialTracking;
	}

	@Override
	public I_M_Material_Tracking getM_Material_Tracking()
	{
		return _materialTracking;
	}

	@Override
	public List<IQualityInspectionOrder> getQualityInspectionOrders()
	{
		if (_qualityInspectionOrders == null)
		{
			_qualityInspectionOrders = ImmutableList.copyOf(retrieveQualityInspectionOrders());
		}

		return _qualityInspectionOrders;
	}

	List<IQualityInspectionOrder> getAllProductionOrders()
	{
		if (_allProductionOrders == null)
		{
			_allProductionOrders = ImmutableList.copyOf(retrieveAllProductionOrders());

		}
		return _allProductionOrders;
	}

	List<IQualityInspectionOrder> getNotInvoicedProductionOrders()
	{
		if (_notInvoicedProductionOrders == null)
		{
			_notInvoicedProductionOrders = ImmutableList.copyOf(filterOutInvoicedPPOrders(getAllProductionOrders()));
		}
		return _notInvoicedProductionOrders;
	}

	/**
	 *
	 * @param allProductionOrders
	 * @return the IQualityInspectionOrder that were not yet invoiced according to {@link IMaterialTrackingPPOrderDAO#isInvoiced(I_PP_Order)}.
	 */
	private List<? extends IQualityInspectionOrder> filterOutInvoicedPPOrders(final List<IQualityInspectionOrder> allProductionOrders)
	{
		final ArrayList<IQualityInspectionOrder> result = new ArrayList<>();
		for (final IQualityInspectionOrder order : allProductionOrders)
		{
			if (materialTrackingPPOrderDAO.isPPOrderInvoicedForMaterialTracking(order.getPP_Order(), _materialTracking))
			{
				continue;
			}
			result.add(order);
		}
		return result;
	}

	private List<? extends IQualityInspectionOrder> retrieveAllProductionOrders()
	{
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		final I_M_Material_Tracking materialTracking = getM_Material_Tracking();

		// note that at this point we also retrieve those PP_Orders that already have invoice candidates
		final ArrayList<IQualityInspectionOrder> qualityInspectionOrders = new ArrayList<>();
		for (final I_PP_Order ppOrder : materialTrackingDAO.retrieveReferences(materialTracking, I_PP_Order.class))
		{
			if (!IDocument.STATUS_Closed.equals(ppOrder.getDocStatus())
					&& !ppOrdersToBeConsideredClosed.contains(ppOrder.getPP_Order_ID()) // task 09657
			)
			{
				continue; // for the invoice candidates and also for the PP_Order report lines, only closed PP_Orders matter.
			}

			if (ppOrdersToBeConsideredNotClosed.contains(ppOrder.getPP_Order_ID()))
			{
				continue; // only closed PP_Orders matter.
			}

			final QualityInspectionOrder qiOrder = new QualityInspectionOrder(ppOrder, materialTracking);
			qualityInspectionOrders.add(qiOrder);
		}

		// task 08848: we need to order the QualityInspections by their production dates.
		// Just ordering them by M_Material_Tracking_Ref_ID is not OK because some of data records in might have been created late.
		Collections.sort(qualityInspectionOrders, (o1, o2) -> {
			final Timestamp date1 = materialTrackingPPOrderBL.getDateOfProduction(o1.getPP_Order());
			final Timestamp date2 = materialTrackingPPOrderBL.getDateOfProduction(o2.getPP_Order());

			return date1.compareTo(date2);
		});
		return qualityInspectionOrders;
	}

	private List<IQualityInspectionOrder> retrieveQualityInspectionOrders()
	{
		//
		// Iterate all referenced orders and create quality inspection order documents
		int nextInspectionNumber = 1;
		final List<IQualityInspectionOrder> qiOrders = new ArrayList<>();
		// List<IQualityInspectionOrder> previousOrders = new ArrayList<>();

		// only PP_Orders that were not yet dealt with
		final List<IQualityInspectionOrder> allProductionOrders = getAllProductionOrders();

		for (final IQualityInspectionOrder qiOrderInterface : allProductionOrders)
		{
			QualityInspectionOrder qiOrder = (QualityInspectionOrder)qiOrderInterface;

			//
			// Case: current order is an inspection order
			final boolean isQualityInspection = qiOrder.isQualityInspection();
			if (isQualityInspection)
			{
				qiOrder.setInspectionNumber(nextInspectionNumber);
				nextInspectionNumber++;

				qiOrder.setAllOrders(getNotInvoicedProductionOrders());

				qiOrders.add(qiOrder);
			}
		}
		return qiOrders;
	}

	@Override
	public void considerPPOrderAsClosed(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "Param 'ppOrder is not null");
		ppOrdersToBeConsideredClosed.add(ppOrder.getPP_Order_ID());
	}

	@Override
	public void considerPPOrderAsNotClosed(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "Param 'ppOrder is not null");
		ppOrdersToBeConsideredNotClosed.add(ppOrder.getPP_Order_ID());
	}

	@Override
	public Collection<I_M_PriceList_Version> getPriceListVersions()
	{
		return getPricingInfo().getPriceListVersions();
	}

	@Override
	public IVendorInvoicingInfo getVendorInvoicingInfoForPLV(I_M_PriceList_Version plv)
	{
		final MaterialTrackingAsVendorInvoicingInfo materialTrackingAsVendorInvoicingInfo = new MaterialTrackingAsVendorInvoicingInfo(getM_Material_Tracking());
		materialTrackingAsVendorInvoicingInfo.setM_PriceList_Version(plv);
		return materialTrackingAsVendorInvoicingInfo;
	}

	private final MaterialTrackingDocumentsPricingInfo getPricingInfo()
	{
		if (pricingInfo == null)
		{
			final I_M_Material_Tracking materialTracking = getM_Material_Tracking();
			final I_C_Flatrate_Term flatrateTerm = Services.get(IFlatrateDAO.class).getById(materialTracking.getC_Flatrate_Term_ID());
			final I_M_PricingSystem pricingSystem = priceListsRepo.getPricingSystemById(PricingSystemId.ofRepoIdOrNull(flatrateTerm
					.getC_Flatrate_Conditions()
					.getM_PricingSystem_ID()));

			pricingInfo = MaterialTrackingDocumentsPricingInfo
					.builder()
					.setM_Material_Tracking(materialTracking)

			// note that we give to the builder also those that were already processed into invoice candidates,
			// because it needs that information to distinguish InOutLines that were not yet issued
			// from those that were issued and whose issue-PP_Order already have IsInvoiceCanidate='Y'
					.setAllProductionOrders(getAllProductionOrders())

			.setNotYetInvoicedProductionOrders(getNotInvoicedProductionOrders())
					.setM_PricingSystem(pricingSystem)
					.build();
		}
		return pricingInfo;
	}

	@Override
	public List<IQualityInspectionOrder> getProductionOrdersForPLV(final I_M_PriceList_Version plv)
	{
		return getPricingInfo().getQualityInspectionOrdersForPLV(plv);
	}

	@Override
	public IVendorReceipt<I_M_InOutLine> getVendorReceiptForPLV(final I_M_PriceList_Version plv)
	{
		return getPricingInfo().getVendorReceiptForPLV(plv);
	}

	@Override
	public IQualityInspectionOrder getQualityInspectionOrderOrNull()
	{
		final List<IQualityInspectionOrder> qualityInspectionOrders = getQualityInspectionOrders();
		if (qualityInspectionOrders.isEmpty())
		{
			return null;
		}

		// as of now, return the last one
		// TODO: consider returning an aggregated/averaged one
		return qualityInspectionOrders.get(qualityInspectionOrders.size() - 1);
	}

	@Override
	public void linkModelToMaterialTracking(final Object model)
	{
		final I_M_Material_Tracking materialTracking = getM_Material_Tracking();
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.model(model)
						.materialTrackingRecord(materialTracking)
						.build());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

}
