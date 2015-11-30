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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.process.DocAction;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;

/* package */class MaterialTrackingDocuments implements IMaterialTrackingDocuments
{
	// Services
	private final transient IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
	private final transient IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

	// Parameters
	private final I_M_Material_Tracking _materialTracking;

	// Loaded values
	private List<IQualityInspectionOrder> _qualityInspectionOrders = null;

	private List<IQualityInspectionOrder> _allProductionOrders = null;

	private I_M_PricingSystem pricingSystem = null;

	public MaterialTrackingDocuments(final I_M_Material_Tracking materialTracking)
	{
		super();

		Check.assumeNotNull(materialTracking, "materialTracking not null");
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
			_qualityInspectionOrders = Collections.unmodifiableList(retrieveQualityInspectionOrders());
		}

		return _qualityInspectionOrders;
	}

	private List<IQualityInspectionOrder> getAllProductionOrders()
	{
		if (_allProductionOrders == null)
		{
			_allProductionOrders = Collections.unmodifiableList(retrieveProductionOrders());
		}

		return _allProductionOrders;
	}

	private List<? extends IQualityInspectionOrder> retrieveProductionOrders()
	{
		final I_M_Material_Tracking materialTracking = getM_Material_Tracking();

		// task 08848: we need to order the QualityInspections by their production dates.
		// Just ordering them by M_Material_Tracking_Ref_ID is not OK because some of data records in might have been created late.
		final ArrayList<IQualityInspectionOrder> qualityInspectionOrders = new ArrayList<IQualityInspectionOrder>();
		for (final I_PP_Order ppOrder : materialTrackingDAO.retrieveReferences(materialTracking, I_PP_Order.class))
		{
			if (!DocAction.STATUS_Closed.equals(ppOrder.getDocStatus()))
			{
				continue; // for the invoice candidates, only closed PP_Orders matter.
			}

			final QualityInspectionOrder qiOrder = new QualityInspectionOrder(ppOrder, materialTracking);
			qualityInspectionOrders.add(qiOrder);
		}

		Collections.sort(qualityInspectionOrders, new Comparator<IQualityInspectionOrder>()
		{
			@Override
			public int compare(IQualityInspectionOrder o1, IQualityInspectionOrder o2)
			{
				return o1.getDateOfProduction().compareTo(o2.getDateOfProduction());
			}
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

				qiOrder.setAllOrders(allProductionOrders);

				qiOrders.add(qiOrder);
			}
		}
		return qiOrders;
	}

	private Map<Integer, List<IQualityInspectionOrder>> plvId2qiOrders = null;

	private Map<Integer, IVendorReceipt<I_M_InOutLine>> plvId2vendorReceipt = null;

	private SortedMap<Integer, I_M_PriceList_Version> plvs = null;

	@Override
	public List<I_M_PriceList_Version> setPricingSystemLoadPLVs(final I_M_PricingSystem pricingSystem)
	{
		Check.assumeNotNull(pricingSystem, "Param pricingSystem is not null");

		this.pricingSystem = pricingSystem;
		loadPlvRelatedData(pricingSystem);

		return new ArrayList<I_M_PriceList_Version>(plvs.values());
	}

	@Override
	public List<IQualityInspectionOrder> getQualityInspectionOrdersForPLV(final I_M_PriceList_Version plv)
	{
		Check.assumeNotNull(plv, "Param plv not null");
		if (plvId2qiOrders == null)
		{
			final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(plv.getM_PriceList(), I_M_PriceList.class).getM_PricingSystem();
			loadPlvRelatedData(pricingSystem);
		}
		return plvId2qiOrders.get(plv.getM_PriceList_Version_ID());
	}

	@Override
	public IVendorReceipt<I_M_InOutLine> getVendorReceiptForPLV(final I_M_PriceList_Version plv)
	{
		Check.assumeNotNull(plv, "Param plv not null");
		if (plvId2vendorReceipt == null)
		{
			final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(plv.getM_PriceList(), I_M_PriceList.class).getM_PricingSystem();
			loadPlvRelatedData(pricingSystem);
		}
		return plvId2vendorReceipt.get(plv.getM_PriceList_Version_ID());
	}

	private void loadPlvRelatedData(final I_M_PricingSystem pricingSystem)
	{
		plvId2vendorReceipt = new HashMap<>();
		plvId2qiOrders = new HashMap<>();
		plvs = new TreeMap<>();

		final List<IQualityInspectionOrder> productionOrders = getAllProductionOrders();
		for (final IQualityInspectionOrder productionOrder : productionOrders)
		{
			final I_PP_Order ppOrder = productionOrder.getPP_Order();
			final Pair<I_M_PriceList_Version, List<I_M_InOutLine>> plvAndIols = providePriceListVersionOrNullForPPOrder(ppOrder, pricingSystem);
			final I_M_PriceList_Version plv = plvAndIols.getFirst();
			if (plv == null)
			{
				continue; // this shouldn't happen, unless the PP_Order was closed without any previous issue
			}

			plvs.put(plv.getM_PriceList_Version_ID(), plv);

			((QualityInspectionOrder)productionOrder).setPriceListversion(plv);
			final int plvID = plv.getM_PriceList_Version_ID();

			//
			// We don't create a vendorReceipt and add the iols now, because further down we will add *all* iols for the plv.
			// Not just the ones that were already issued, but all of them which were received within the valid-period of the given plv.

			//
			// add to plvId2qiOrders
			List<IQualityInspectionOrder> qiOrders = plvId2qiOrders.get(plvID);
			if (qiOrders == null)
			{
				qiOrders = new ArrayList<>();
				plvId2qiOrders.put(plvID, qiOrders);
			}
			qiOrders.add(productionOrder);
		}

		for (final I_M_PriceList_Version plv : plvs.values())
		{
			//
			// now get *all* the receipt lines that took place while the PLV was valid
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final ICompositeQueryFilter<I_M_InOut> inOutFilter = queryBL.createCompositeQueryFilter(I_M_InOut.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_InOut.COLUMN_DocStatus, DocAction.STATUS_Completed, DocAction.STATUS_Closed)
					.addCompareFilter(I_M_InOut.COLUMN_MovementDate, Operator.GreatherOrEqual, plv.getValidFrom());

			final I_M_PriceList_Version nextPLV = Services.get(IPriceListDAO.class).retrieveNextVersionOrNull(plv);
			if (nextPLV != null)
			{
				inOutFilter.addCompareFilter(I_M_InOut.COLUMN_MovementDate, Operator.Less, nextPLV.getValidFrom());
			}

			final Iterator<I_M_InOutLine> inOutLines = queryBL.createQueryBuilder(I_M_InOut.class, plv)
					.filter(inOutFilter)
					.andCollectChildren(I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Material_Tracking_ID, getM_Material_Tracking().getM_Material_Tracking_ID())
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_IsPackagingMaterial, false)
					.orderBy().addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID).endOrderBy() // can't hurt to be a bit predictable
					.create()
					.iterate(I_M_InOutLine.class);

			final IVendorReceipt<I_M_InOutLine> vendorReceipt = new InOutLineAsVendorReceipt();
			while (inOutLines.hasNext())
			{
				vendorReceipt.add(inOutLines.next());
			}
			plvId2vendorReceipt.put(plv.getM_PriceList_Version_ID(), vendorReceipt);
		}
	}

	private Pair<I_M_PriceList_Version, List<I_M_InOutLine>> providePriceListVersionOrNullForPPOrder(final I_PP_Order ppOrder, final I_M_PricingSystem pricingSystem)
	{
		I_M_PriceList_Version plv = null;

		final List<I_M_InOutLine> allIssuedInOutLines = new ArrayList<>();

		// we don't really care about the PP_Order's date. what we need to know is the movementDate of the material receipt (inout)
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		for (final I_PP_Cost_Collector cc : ppCostCollectorDAO.retrieveForOrder(ppOrder))
		{
			if (!Services.get(IPPCostCollectorBL.class).isMaterialIssue(cc, true))
			{
				continue;
			}
			final IPPOrderMInOutLineRetrievalService ppOrderMInOutLineRetrievalService = Services.get(IPPOrderMInOutLineRetrievalService.class);
			final List<I_M_InOutLine> issuedInOutLinesForCC = ppOrderMInOutLineRetrievalService.provideIssuedInOutLines(cc);

			allIssuedInOutLines.addAll(issuedInOutLinesForCC);

			for (final I_M_InOutLine inOutLine : issuedInOutLinesForCC)
			{
				final I_M_PriceList_Version inOutLinePLV = retrivePLV(inOutLine, pricingSystem);
				Check.errorIf(inOutLinePLV == null, "Unable to retrieve a priceListVersion for inOutLine {0} and pricingSystem {1}.", inOutLine, pricingSystem);

				if (plv == null)
				{
					plv = inOutLinePLV;
				}
				else if (plv.getM_PriceList_Version_ID() != inOutLinePLV.getM_PriceList_Version_ID())
				{
					// note that this shall actually be prevented by the system in the first place
					Check.errorIf(
							true,
							"For an earlier inOutLine the priceListVersion {0} was retreived, but for inOutLine {1}, the priceListVersion {2} was retrieved;\npricingSystem: {3};\nppOrder: {4};\ncostCollector: {5}",
							plv, inOutLine, inOutLinePLV, pricingSystem, ppOrder, cc);
				}
			}
		}
		return new Pair<I_M_PriceList_Version, List<I_M_InOutLine>>(plv, allIssuedInOutLines);
	}

	private I_M_PriceList_Version retrivePLV(final I_M_InOutLine inOutLine,
			final I_M_PricingSystem pricingSystem)
	{
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		final I_C_Country country = inOutLine.getM_InOut().getC_BPartner_Location().getC_Location().getC_Country();
		final Iterator<I_M_PriceList> priceLists = priceListDAO.retrievePriceLists(
				pricingSystem,
				country,
				false); // IsSOTrx=false

		if (!priceLists.hasNext())
		{
			ILoggable.THREADLOCAL.getLoggable().addLog("Unable to retrieve a priceList for pricingSystem {0} and country {1}.", pricingSystem, country);
			return null;
		}

		final Timestamp movementDate = inOutLine.getM_InOut().getMovementDate();
		final I_M_PriceList priceList = priceLists.next();
		final Boolean processedPLVFiltering = true; // task 09533: in material-tracking we work only with PLVs that are cleared
		final I_M_PriceList_Version plv = priceListDAO.retrievePriceListVersionOrNull(priceList, movementDate, processedPLVFiltering);
		if (plv == null)
		{
			ILoggable.THREADLOCAL.getLoggable().addLog("Unable to retrieve a processed priceListVersion for priceList {0} and movementDate {1}.", priceList, movementDate);
		}
		return plv;
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
						.setModel(model)
						.setMaterialTracking(materialTracking)
						.build());
	}

	public I_M_PricingSystem getPricingSystem()
	{
		return this.pricingSystem;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
