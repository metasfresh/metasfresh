package de.metas.materialtracking.qualityBasedInvoicing.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.process.DocAction;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.materialtracking.model.IMaterialTrackingAware;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.spi.IPPOrderMInOutLineRetrievalService;

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

/**
 * {@link MaterialTrackingDocuments}'s pricing informations
 *
 * @author metas-dev <dev@metas-fresh.com>
 */
/* package */final class MaterialTrackingDocumentsPricingInfo
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ImmutableListMultimap<Integer, IQualityInspectionOrder> plvId2qiOrders;
	private final ImmutableMap<Integer, IVendorReceipt<I_M_InOutLine>> plvId2vendorReceipt;
	private final ImmutableMap<Integer, I_M_PriceList_Version> plvs;

	private MaterialTrackingDocumentsPricingInfo(final Builder builder)
	{
		super();

		plvId2qiOrders = ImmutableListMultimap.copyOf(builder.plvId2qiOrders);
		plvId2vendorReceipt = ImmutableMap.copyOf(builder.plvId2vendorReceipt);
		plvs = ImmutableMap.copyOf(builder.plvs);
	}

	public final Collection<I_M_PriceList_Version> getPriceListVersions()
	{
		return plvs.values();
	}

	public final List<IQualityInspectionOrder> getQualityInspectionOrdersForPLV(final I_M_PriceList_Version plv)
	{
		Check.assumeNotNull(plv, "Param plv not null");
		return plvId2qiOrders.get(plv.getM_PriceList_Version_ID());
	}

	public IVendorReceipt<I_M_InOutLine> getVendorReceiptForPLV(final I_M_PriceList_Version plv)
	{
		Check.assumeNotNull(plv, "Param plv not null");
		return plvId2vendorReceipt.get(plv.getM_PriceList_Version_ID());
	}

	public static final class Builder
	{
		// services
		private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
		private final transient IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		private final transient IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		private final transient IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		private final transient IPPOrderMInOutLineRetrievalService ppOrderMInOutLineRetrievalService = Services.get(IPPOrderMInOutLineRetrievalService.class);

		//
		// Parameters
		private I_M_PricingSystem _pricingSystem;
		private Integer _materialTrackingId;
		private List<IQualityInspectionOrder> _qualityInspectionOrders;

		//
		// Built/prepared values
		private final ListMultimap<Integer, IQualityInspectionOrder> plvId2qiOrders = ArrayListMultimap.create();
		private final Map<Integer, IVendorReceipt<I_M_InOutLine>> plvId2vendorReceipt = new HashMap<>();
		private final Map<Integer, I_M_PriceList_Version> plvs = new TreeMap<>();

		private Builder()
		{
			super();
		}

		public MaterialTrackingDocumentsPricingInfo build()
		{
			//
			// Build: M_PriceList_Version_ID to Quality Inspection Orders
			// Build: Price List Versions map
			for (final IQualityInspectionOrder productionOrder : getQualityInspectionOrders())
			{
				final I_PP_Order ppOrder = productionOrder.getPP_Order();
				final Pair<I_M_PriceList_Version, List<I_M_InOutLine>> plvAndIols = providePriceListVersionOrNullForPPOrder(ppOrder);
				final I_M_PriceList_Version plv = plvAndIols.getFirst();
				if (plv == null)
				{
					// this shouldn't happen, unless the PP_Order was closed without any previous issue
					// OR! unless we have a pruned-down database dump that lacks M_HUs
					continue;
				}

				final int plvId = plv.getM_PriceList_Version_ID();
				plvs.put(plvId, plv);

				//
				// NOTE: We don't create a vendorReceipt and add the iols now, because further down we will add *all* iols for the plv.
				// Not just the ones that were already issued, but all of them which were received within the valid-period of the given plv.

				//
				// add to plvId2qiOrders
				plvId2qiOrders.put(plvId, productionOrder);
			}

			//
			// Build: M_PriceList_Version_ID to IVendorReceipt map
			for (final I_M_PriceList_Version plv : plvs.values())
			{
				final IVendorReceipt<I_M_InOutLine> vendorReceipt = createVendorReceipt(plv);
				plvId2vendorReceipt.put(plv.getM_PriceList_Version_ID(), vendorReceipt);
			}

			return new MaterialTrackingDocumentsPricingInfo(this);
		}

		private Pair<I_M_PriceList_Version, List<I_M_InOutLine>> providePriceListVersionOrNullForPPOrder(final I_PP_Order ppOrder)
		{
			I_M_PriceList_Version plv = null;

			final List<I_M_InOutLine> allIssuedInOutLines = new ArrayList<>();

			// we don't really care about the PP_Order's date. what we need to know is the movementDate of the material receipt (inout)
			for (final I_PP_Cost_Collector cc : ppCostCollectorDAO.retrieveForOrder(ppOrder))
			{
				if (!ppCostCollectorBL.isMaterialIssue(cc, true))
				{
					continue;
				}

				final List<I_M_InOutLine> issuedInOutLinesForCC = ppOrderMInOutLineRetrievalService.provideIssuedInOutLines(cc);
				allIssuedInOutLines.addAll(issuedInOutLinesForCC);

				for (final I_M_InOutLine inOutLine : issuedInOutLinesForCC)
				{
					final I_M_PriceList_Version inOutLinePLV = retrivePLV(inOutLine);
					Check.errorIf(inOutLinePLV == null, "Unable to retrieve a priceListVersion for inOutLine {0} and pricingSystem {1}.", inOutLine, getM_PricingSystem());

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
								plv, inOutLine, inOutLinePLV, getM_PricingSystem(), ppOrder, cc);
					}
				}
			}

			return new Pair<I_M_PriceList_Version, List<I_M_InOutLine>>(plv, allIssuedInOutLines);
		}

		private I_M_PriceList_Version retrivePLV(final I_M_InOutLine inOutLine)
		{
			final I_M_PricingSystem pricingSystem = getM_PricingSystem();
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

		private final IVendorReceipt<I_M_InOutLine> createVendorReceipt(final I_M_PriceList_Version plv)
		{
			//
			// now get *all* the receipt lines that took place while the PLV was valid
			final ICompositeQueryFilter<I_M_InOut> inOutFilter = queryBL.createCompositeQueryFilter(I_M_InOut.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_InOut.COLUMN_DocStatus, DocAction.STATUS_Completed, DocAction.STATUS_Closed)
					.addCompareFilter(I_M_InOut.COLUMN_MovementDate, Operator.GREATER_OR_EQUAL, plv.getValidFrom());

			final I_M_PriceList_Version nextPLV = priceListDAO.retrieveNextVersionOrNull(plv);
			if (nextPLV != null)
			{
				inOutFilter.addCompareFilter(I_M_InOut.COLUMN_MovementDate, Operator.LESS, nextPLV.getValidFrom());
			}

			final Iterator<I_M_InOutLine> inOutLines = queryBL.createQueryBuilder(I_M_InOut.class, plv)
					.filter(inOutFilter)
					.andCollectChildren(org.compiere.model.I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOutLine.class)
					.addEqualsFilter(IMaterialTrackingAware.COLUMNNAME_M_Material_Tracking_ID, getM_Material_Tracking_ID())
					.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_IsPackagingMaterial, false)
					.orderBy().addColumn(org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID).endOrderBy() // can't hurt to be a bit predictable
					.create()
					.iterate(I_M_InOutLine.class);

			final IVendorReceipt<I_M_InOutLine> vendorReceipt = new InOutLineAsVendorReceipt();
			while (inOutLines.hasNext())
			{
				vendorReceipt.add(inOutLines.next());
			}

			return vendorReceipt;
		}

		public Builder setM_PricingSystem(final I_M_PricingSystem pricingSystem)
		{
			_pricingSystem = pricingSystem;
			return this;
		}

		public Builder setM_PricingSystemOf(final I_M_PriceList_Version plv)
		{
			Check.assumeNotNull(plv, "plv not null");
			final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(plv.getM_PriceList(), I_M_PriceList.class).getM_PricingSystem();
			return setM_PricingSystem(pricingSystem);
		}

		private final I_M_PricingSystem getM_PricingSystem()
		{
			Check.assumeNotNull(_pricingSystem, "_pricingSystem not null");
			return _pricingSystem;
		}

		public Builder setM_Material_Tracking_ID(final int materialTrackingId)
		{
			_materialTrackingId = materialTrackingId;
			return this;
		}

		private final int getM_Material_Tracking_ID()
		{
			Check.assumeNotNull(_materialTrackingId, "_materialTrackingId not null");
			return _materialTrackingId;
		}

		public Builder setQualityInspectionOrders(final List<IQualityInspectionOrder> qualityInspectionOrders)
		{
			_qualityInspectionOrders = qualityInspectionOrders;
			return this;
		}

		private List<IQualityInspectionOrder> getQualityInspectionOrders()
		{
			Check.assumeNotNull(_qualityInspectionOrders, "_qualityInspectionOrders not null");
			return _qualityInspectionOrders;
		}
	}
}