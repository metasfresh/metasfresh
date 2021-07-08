package de.metas.procurement.base.order.impl;

import java.sql.Timestamp;
import java.util.Properties;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.IOrderBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class OrderHeaderAggregation
{
	// services
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	private I_C_Order order;
	private OrderLinesAggregator orderLinesAggregator = null;

	public I_C_Order build()
	{
		if (order == null)
		{
			// nothing to build
			return null;
		}

		//
		// Build and save all order lines
		orderLinesAggregator.closeAllGroups();

		//
		// Process the order
		docActionBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return order;
	}

	public void add(final PurchaseCandidate candidate)
	{
		if (candidate.isZeroQty())
		{
			return;
		}

		if (order == null)
		{
			order = createOrder(candidate);
			orderLinesAggregator = new OrderLinesAggregator(order);
		}
		orderLinesAggregator.add(candidate);
	}

	private I_C_Order createOrder(final PurchaseCandidate candidate)
	{
		final int adOrgId = candidate.getAD_Org_ID();
		final int warehouseId = candidate.getM_Warehouse_ID();

		final I_C_BPartner bpartner = bpartnersRepo.getById(candidate.getC_BPartner_ID());

		final int pricingSystemId = candidate.getM_PricingSystem_ID();

		// the price is taken from the candidates and C_OrderLine.IsManualPrice is set to 'Y'
		// gh #1088 I have no clue wtf the comment above "the price is taken from..." is supposed to mean.
		// So instead of using M_PriceList_ID_None here, we use the candidate's PL. 
		// Because otherwise, de.metas.order.model.interceptor.C_Order#onPriceListChangeInterceptor(...) will update the order pricing system to M_PricingSystem_ID_None
		// and then the system won't be able to get the new order lines' C_TaxCategories and MOrderLine.beforeSave() will fail
		// final int priceListId =  MPriceList.M_PriceList_ID_None;
		final int priceListId = candidate.getM_PriceList_ID();

		final int currencyId = candidate.getC_Currency_ID();

		final Timestamp datePromised = candidate.getDatePromised();
		final Timestamp dateOrdered = TimeUtil.min(SystemTime.asDayTimestamp(), datePromised);

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);

		//
		// Doc type
		order.setAD_Org_ID(adOrgId);
		order.setIsSOTrx(false);
		orderBL.setDocTypeTargetId(order);

		//
		// Warehouse
		order.setM_Warehouse_ID(warehouseId);

		//
		// BPartner
		orderBL.setBPartner(order, bpartner);
		orderBL.setBill_User_ID(order);

		//
		// Dates
		order.setDateOrdered(dateOrdered);
		order.setDateAcct(dateOrdered);
		order.setDatePromised(datePromised);

		//
		// Price list
		if (pricingSystemId > 0)
		{
			order.setM_PricingSystem_ID(pricingSystemId);
		}
		if (priceListId > 0)
		{
			order.setM_PriceList_ID(priceListId);
		}
		order.setC_Currency_ID(currencyId);

		//
		// SalesRep:
		// * let it to be set from BPartner (this was done above, by orderBL.setBPartner method)
		// * if not set use it from context
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		if (order.getSalesRep_ID() <= 0)
		{
			order.setSalesRep_ID(Env.getContextAsInt(ctx, Env.CTXNAME_SalesRep_ID));
		}
		if (order.getSalesRep_ID() <= 0)
		{
			order.setSalesRep_ID(Env.getAD_User_ID(ctx));
		}

		order.setDocStatus(DocStatus.Drafted.getCode());
		order.setDocAction(IDocument.ACTION_Complete);

		//
		// Save & return
		InterfaceWrapperHelper.save(order);
		return order;
	}
}
