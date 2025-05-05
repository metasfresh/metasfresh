package de.metas.materialtracking.qualityBasedInvoicing.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import de.metas.document.engine.IDocument;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.IMaterialTrackingAware;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.common.util.pair.ImmutablePair;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link MaterialTrackingDocuments}'s pricing informations
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */final class MaterialTrackingDocumentsPricingInfo
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final ImmutableListMultimap<Integer, IQualityInspectionOrder> plvId2qiOrders;
	private final ImmutableMap<Integer, IVendorReceipt<I_M_InOutLine>> plvId2vendorReceipt;
	private final ImmutableMap<Integer, I_M_PriceList_Version> plvs;

	private MaterialTrackingDocumentsPricingInfo(@NonNull final Builder builder)
	{
		plvId2qiOrders = ImmutableListMultimap.copyOf(builder.plvId2qiOrders);
		plvId2vendorReceipt = ImmutableMap.copyOf(builder.plvId2vendorReceipt);
		plvs = ImmutableMap.copyOf(builder.plvs);
	}

	public Collection<I_M_PriceList_Version> getPriceListVersions()
	{
		return plvs.values();
	}

	/**
	 * @return those {@link IQualityInspectionOrder}s that are not yet invoiced.
	 */
	public List<IQualityInspectionOrder> getQualityInspectionOrdersForPLV(@NonNull final I_M_PriceList_Version plv)
	{
		return plvId2qiOrders.get(plv.getM_PriceList_Version_ID());
	}

	/**
	 * @return the vendor receipt info that belongs to the given <code>plv</code> and that was not yet issued at all or that was issued to <code>PP_Order</code>s this were not yet invoiced.
	 */
	public IVendorReceipt<I_M_InOutLine> getVendorReceiptForPLV(final I_M_PriceList_Version plv)
	{
		Check.assumeNotNull(plv, "Param plv not null");
		return plvId2vendorReceipt.get(plv.getM_PriceList_Version_ID());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public static final class Builder
	{
		// services
		private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
		private final transient IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		//
		// Parameters
		private I_M_PricingSystem _pricingSystem;
		private I_M_Material_Tracking _materialTracking;
		private List<IQualityInspectionOrder> _allProductionOrders;
		private Set<Integer> _notYetInvoicedPPOrderIDs;

		//
		// Built/prepared values

		/**
		 * Those qiOrders that were not yet invoiced
		 */
		private final ListMultimap<Integer, IQualityInspectionOrder> plvId2qiOrders = ArrayListMultimap.create();
		private final Map<Integer, IVendorReceipt<I_M_InOutLine>> plvId2vendorReceipt = new HashMap<>();
		private final Map<Integer, I_M_PriceList_Version> plvs = new TreeMap<>();
		private final Map<Integer, List<I_M_InOutLine>> ppOrderId2Iols = new HashMap<>();

		private final Set<Integer> inOutLinesThatWereAlreadyInvoiced = new HashSet<>();

		private Builder()
		{
		}

		public MaterialTrackingDocumentsPricingInfo build()
		{
			//
			// Build: M_PriceList_Version_ID to Quality Inspection Orders
			// Build: Price List Versions map
			for (final IQualityInspectionOrder productionOrder : getProductionOrders())
			{
				final I_PP_Order ppOrder = productionOrder.getPP_Order();
				final ImmutablePair<I_M_PriceList_Version, List<I_M_InOutLine>> plvAndIols = providePriceListVersionOrNullForPPOrder(ppOrder);

				// also add them if there is no PLV. In that case the iols will be an empty list, but there won't be an NPE when accessing it.
				ppOrderId2Iols.put(ppOrder.getPP_Order_ID(), plvAndIols.getRight());

				final I_M_PriceList_Version plv = plvAndIols.getLeft();
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
				// add to plvId2qiOrders, unless it was already invoiced
				if (getNotYetInvoicedPPOrderIDs().contains(ppOrder.getPP_Order_ID()))
				{
					plvId2qiOrders.put(plvId, productionOrder);
				}
				ppOrderId2Iols.put(ppOrder.getPP_Order_ID(), plvAndIols.getRight());
			}

			//
			// collect those iols that were already invoiced.
			for (final IQualityInspectionOrder productionOrder : getProductionOrders())
			{
				final int ppOrderId = productionOrder.getPP_Order().getPP_Order_ID();
				if (getNotYetInvoicedPPOrderIDs().contains(ppOrderId))
				{
					continue;
				}
				for (final I_M_InOutLine alreadyInvoicedIol : ppOrderId2Iols.get(ppOrderId))
				{
					inOutLinesThatWereAlreadyInvoiced.add(alreadyInvoicedIol.getM_InOutLine_ID());
				}
			}

			//
			// Build: M_PriceList_Version_ID to IVendorReceipt map
			for (final I_M_PriceList_Version plv : plvs.values())
			{
				final IVendorReceipt<I_M_InOutLine> vendorReceipt = createVendorReceipt(plv);
				if (vendorReceipt.getModels().size() > 0)
				{
					plvId2vendorReceipt.put(plv.getM_PriceList_Version_ID(), vendorReceipt);
				}
			}

			return new MaterialTrackingDocumentsPricingInfo(this);
		}

		private ImmutablePair<I_M_PriceList_Version, List<I_M_InOutLine>> providePriceListVersionOrNullForPPOrder(@NonNull final I_PP_Order ppOrder)
		{
			I_M_PriceList_Version plv = null;

			// we don't really care about the PP_Order's date. what we need to know is the movementDate of the material receipt (inout)
			final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
			final List<I_M_InOutLine> issuedInOutLinesForPPOrder = materialTrackingPPOrderBL.retrieveIssuedInOutLines(ppOrder);
			for (final I_M_InOutLine inOutLine : issuedInOutLinesForPPOrder)
			{
				if (inOutLine.getM_Material_Tracking_ID() != _materialTracking.getM_Material_Tracking_ID())
				{
					// someone issued stuff from an unrelated inoutLine to our ppOrder => ignore that iol
					continue;
				}

				final I_M_PriceList_Version inOutLinePLV = retrivePLV(inOutLine);
				Check.errorIf(inOutLinePLV == null, "Unable to retrieve a plv for inOutLine {} and pricingSystem {}.", inOutLine, getM_PricingSystem());

				if (plv == null)
				{
					plv = inOutLinePLV;
				}
				else if (plv.getM_PriceList_Version_ID() != inOutLinePLV.getM_PriceList_Version_ID())
				{
					// note that this shall actually be prevented by the system in the first place
					Check.errorIf(
							true,
							"For an earlier inOutLine the PLV {} was retrieved, but for inOutLine {}, the PVL {} was retrieved;\npricingSystem: {};\nppOrder: {}",
							plv, inOutLine, inOutLinePLV, getM_PricingSystem(), ppOrder);
				}
			}

			return ImmutablePair.of(plv, issuedInOutLinesForPPOrder);
		}

		private I_M_PriceList_Version retrivePLV(final I_M_InOutLine inOutLine)
		{
			final I_M_PricingSystem pricingSystem = getM_PricingSystem();
			final I_M_InOut inOut = InterfaceWrapperHelper.load(inOutLine.getM_InOut_ID(), I_M_InOut.class);
			final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.load(inOut.getC_BPartner_Location_ID(), I_C_BPartner_Location.class);
			final I_C_Location location = InterfaceWrapperHelper.load(bPartnerLocation.getC_Location_ID(), I_C_Location.class);

			final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());
			final Iterator<I_M_PriceList> priceLists = priceListDAO.retrievePriceLists(
					PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID()),
					countryId,
					SOTrx.PURCHASE)
					.iterator();

			if (!priceLists.hasNext())
			{
				Loggables.addLog("Unable to retrieve a priceList for pricingSystem {0} and country {1}.", pricingSystem, countryId);
				return null;
			}

			final ZonedDateTime movementDate = TimeUtil.asZonedDateTime(inOutLine.getM_InOut().getMovementDate());
			final I_M_PriceList priceList = priceLists.next();
			final Boolean processedPLVFiltering = true; // task 09533: in material-tracking we work only with PLVs that are cleared
			final I_M_PriceList_Version plv = priceListDAO.retrievePriceListVersionOrNull(priceList, movementDate, processedPLVFiltering);
			if (plv == null)
			{
				Loggables.addLog("Unable to retrieve a processed priceListVersion for priceList {0} and movementDate {1}.", priceList, movementDate);
			}
			return plv;
		}

		/**
		 * The created vendor receipt contains only those iols that do not belong to a PP_Order that are already invoiced.
		 */
		private IVendorReceipt<I_M_InOutLine> createVendorReceipt(@NonNull final I_M_PriceList_Version plv)
		{
			// now get *all* the inout lines that took place while the PLV was valid
			final ICompositeQueryFilter<I_M_InOut> inOutFilter = queryBL.createCompositeQueryFilter(I_M_InOut.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayOrAllFilter(I_M_InOut.COLUMN_DocStatus, IDocument.STATUS_Completed, IDocument.STATUS_Closed)
					.addCompareFilter(I_M_InOut.COLUMN_MovementDate, Operator.GREATER_OR_EQUAL, plv.getValidFrom());

			final I_M_PriceList_Version nextPLV = priceListDAO.retrieveNextVersionOrNull(plv, true /* onlyProcessed */);
			if (nextPLV != null)
			{
				inOutFilter.addCompareFilter(I_M_InOut.COLUMN_MovementDate, Operator.LESS, nextPLV.getValidFrom());
			}

			final Iterator<I_M_InOutLine> inOutLines = queryBL.createQueryBuilder(I_M_InOut.class, plv)
					.filter(inOutFilter)
					.andCollectChildren(org.compiere.model.I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOutLine.class)
					.addEqualsFilter(IMaterialTrackingAware.COLUMNNAME_M_Material_Tracking_ID, getM_Material_Tracking_ID())
					.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_M_Product_ID, _materialTracking.getM_Product_ID())
					.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_IsPackagingMaterial, false) //

					// can't hurt to be a bit predictable
					.orderBy().addColumn(org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID).endOrderBy()
					.create()
					.iterate(I_M_InOutLine.class);

			final ProductId productId = ProductId.ofRepoId(_materialTracking.getM_Product_ID());
			final I_M_Product product = Services.get(IProductDAO.class).getById(productId);

			final InOutLineAsVendorReceipt vendorReceipt = new InOutLineAsVendorReceipt(product);
			vendorReceipt.setPlv(plv);

			while (inOutLines.hasNext())
			{
				final I_M_InOutLine iol = inOutLines.next();
				if (inOutLinesThatWereAlreadyInvoiced.contains(iol.getM_InOutLine_ID()))
				{
					continue;
				}
				vendorReceipt.add(iol);
			}
			return vendorReceipt;
		}

		public Builder setM_PricingSystem(final I_M_PricingSystem pricingSystem)
		{
			_pricingSystem = pricingSystem;
			return this;
		}

		public Builder setM_PricingSystemOf(@NonNull final I_M_PriceList_Version plv)
		{
			final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

			final PriceListId priceListId = PriceListId.ofRepoId(plv.getM_PriceList_ID());
			I_M_PriceList priceList = priceListsRepo.getById(priceListId);

			final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(priceList.getM_PricingSystem_ID());
			final I_M_PricingSystem pricingSystem = priceListsRepo.getPricingSystemById(pricingSystemId);

			return setM_PricingSystem(pricingSystem);
		}

		private I_M_PricingSystem getM_PricingSystem()
		{
			Check.assumeNotNull(_pricingSystem, "_pricingSystem not null");
			return _pricingSystem;
		}

		public Builder setM_Material_Tracking(final I_M_Material_Tracking materialTracking)
		{
			_materialTracking = materialTracking;
			return this;
		}

		private int getM_Material_Tracking_ID()
		{
			Check.assumeNotNull(_materialTracking, "_materialTracking not null");
			return _materialTracking.getM_Material_Tracking_ID();
		}

		public Builder setAllProductionOrders(final List<IQualityInspectionOrder> qualityInspectionOrders)
		{
			_allProductionOrders = qualityInspectionOrders;
			return this;
		}

		private List<IQualityInspectionOrder> getProductionOrders()
		{
			Check.assumeNotNull(_allProductionOrders, "_qualityInspectionOrders not null");
			return _allProductionOrders;
		}

		public Builder setNotYetInvoicedProductionOrders(final List<IQualityInspectionOrder> qualityInspectionOrders)
		{
			_notYetInvoicedPPOrderIDs = new HashSet<>();
			for (final IQualityInspectionOrder order : qualityInspectionOrders)
			{
				_notYetInvoicedPPOrderIDs.add(order.getPP_Order().getPP_Order_ID());
			}
			return this;
		}

		private Set<Integer> getNotYetInvoicedPPOrderIDs()
		{
			Check.assumeNotNull(_notYetInvoicedPPOrderIDs, "_notYetInvoicedProductionOrders not null");
			return _notYetInvoicedPPOrderIDs;
		}
	}
}
