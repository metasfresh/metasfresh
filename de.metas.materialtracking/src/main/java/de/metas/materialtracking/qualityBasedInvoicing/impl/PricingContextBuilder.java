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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.product.service.IProductPA;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.CLogger;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;

/**
 * Helper class used to create the inital pricing context when invoicing.
 *
 * @author tsa
 *
 */
public class PricingContextBuilder
{
	// Services
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IProductPA productPA = Services.get(IProductPA.class);

	private static final transient CLogger logger = CLogger.getCLogger(PricingContextBuilder.class);

	//
	// Parameters
	private final IContextAware _context;
	private IVendorInvoicingInfo _vendorInvoicingInfo = null;
	private IQualityInspectionOrder _qualityInspectionOrder = null;

	public PricingContextBuilder(final IContextAware context)
	{
		super();

		Check.assumeNotNull(context, "context not null");
		_context = context;
	}

	public void setVendorInvoicingInfo(final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		_vendorInvoicingInfo = vendorInvoicingInfo;
	}

	/**
	 * @return vendor invoicing info; never return null
	 */
	private final IVendorInvoicingInfo getVendorInvoicingInfo()
	{
		Check.assumeNotNull(_vendorInvoicingInfo, "_vendorInvoicingInfo not null");
		return _vendorInvoicingInfo;
	}

	private final IContextAware getContext()
	{
		return _context;
	}

	public void setQualityInspectionOrder(final IQualityInspectionOrder qualityInspectionOrder)
	{
		_qualityInspectionOrder = qualityInspectionOrder;
	}

	private final IQualityInspectionOrder getQualityInspectionOrder()
	{
		Check.assumeNotNull(_qualityInspectionOrder, "_qualityInspectionOrder not null");
		return _qualityInspectionOrder;
	}

	/**
	 * Creates {@link IPricingContext} for parameters set.
	 *
	 * Following informations will be set:
	 * <ul>
	 * <li>bill bpartner
	 * <li>pricing system and price list
	 * <li>currency
	 * <li>IsSOTrx to <code>false</code>
	 * <li>Price Date - as quality order's production date
	 * <li>will NOT be set: product, qty, uom
	 * </ul>
	 *
	 * @return pricing context; never return null.
	 */
	public final IPricingContext create()
	{
		//
		// Create Pricing Context
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				-1, // M_Product_ID - will be set later
				-1, // billBPartnerId - will be set later
				-1, // C_UOM_ID - will be set later
				BigDecimal.ZERO, // Qty - will be set later
				false // IsSOTrx=false => we are on purchasing side
				);

		//
		// Update pricing context
		updatePricingContextFromVendorInvoicingInfo(pricingCtx);
		updatePricingContextFromQualityInspectionOrder(pricingCtx);

		return pricingCtx;
	}

	/**
	 * Updates the pricing context from original invoice candidate. Following informations will be set:
	 * <ul>
	 * <li>bill bpartner
	 * <li>pricing system and price list
	 * <li>currency
	 * <li>IsSOTrx to <code>false</code>
	 * <li>will NOT be set: product
	 * </ul>
	 *
	 * @param pricingCtx
	 */
	private void updatePricingContextFromVendorInvoicingInfo(final IEditablePricingContext pricingCtx)
	{
		final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();

		//
		// Extract infos from original invoice candidate
		final int billBPartnerId = vendorInvoicingInfo.getBill_BPartner_ID();
		final int billBPLocationId = vendorInvoicingInfo.getBill_Location_ID();
		final int pricingSytemId = vendorInvoicingInfo.getM_PricingSystem_ID();
		final int currencyId = vendorInvoicingInfo.getC_Currency_ID();
		final boolean isSOTrx = false; // we are always on purchase side

		//
		// Update pricing context
		pricingCtx.setSOTrx(isSOTrx);
		pricingCtx.setC_BPartner_ID(billBPartnerId);
		pricingCtx.setC_Currency_ID(currencyId);
		pricingCtx.setM_PricingSystem_ID(pricingSytemId);

		//
		// Update pricing context: price list
		final IContextAware context = getContext();
		final I_M_PriceList priceListToUse = productPA.retrievePriceListByPricingSyst(context.getCtx(),
				pricingSytemId,
				billBPLocationId,
				isSOTrx,
				context.getTrxName());
		if (priceListToUse != null)
		{
			final int priceListToUseId = priceListToUse.getM_PriceList_ID();
			pricingCtx.setM_PriceList_ID(priceListToUseId);
		}
		else
		{
			final AdempiereException ex = new AdempiereException("@NotFound@ @M_PriceList_ID@"
					+ "\n Vendor Invoicing Info: " + vendorInvoicingInfo);
			logger.log(Level.WARNING, "Cannot find M_PriceList. Ignored.", ex);
		}
	}

	private void updatePricingContextFromQualityInspectionOrder(final IEditablePricingContext pricingCtx)
	{
		final IQualityInspectionOrder order = getQualityInspectionOrder();
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		//
		// task 09221: get the earliest purchase order and use its DateOrdred as the relevant pricing date
		final List<I_M_Material_Tracking_Ref> orderLineMtrs = materialTrackingDAO.retrieveMaterialTrackingRefForType(order.getM_Material_Tracking(), I_C_OrderLine.class);
		Timestamp earliestOrderDate = null;
		for (final I_M_Material_Tracking_Ref mtr : orderLineMtrs)
		{
			final I_C_OrderLine orderLine = new TableRecordReference(mtr.getAD_Table_ID(), mtr.getRecord_ID()).getModel(_context, I_C_OrderLine.class);
			final Timestamp currentDateOrdered = orderLine.getC_Order().getDateOrdered();
			if (earliestOrderDate == null || earliestOrderDate.after(currentDateOrdered))
			{
				earliestOrderDate = currentDateOrdered;
			}
		}

		//
		// update pricing context
		pricingCtx.setPriceDate(earliestOrderDate);
	}
}
