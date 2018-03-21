package de.metas.order.process.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.collections.MapReduceAggregator;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;
import de.metas.order.IOrderBL;

/*
 * #%L
 * de.metas.swat.base
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
 * Created new purchase orders for sales order lines and contains one instance of {@link CreatePOLineFromSOLinesAggregator} for each created purchase order line.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreatePOFromSOsAggregator extends MapReduceAggregator<I_C_Order, I_C_OrderLine>
{
	private static final String MSG_PURCHASE_ORDER_CREATED = "de.metas.order.C_Order_CreatePOFromSOs.PurchaseOrderCreated";
	private final IContextAware context;
	private final boolean p_IsDropShip;
	private final String purchaseQtySource;

	private final I_C_Order dummyOrder;

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	final Map<String, CreatePOLineFromSOLinesAggregator> orderKey2OrderLineAggregator = new HashMap<>();

	public CreatePOFromSOsAggregator(final IContextAware context,
			final boolean p_IsDropShip,
			final String purchaseQtySource)
	{
		this.context = context;
		this.p_IsDropShip = p_IsDropShip;
		this.purchaseQtySource = purchaseQtySource;

		dummyOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, context);
		dummyOrder.setDocumentNo(CreatePOFromSOsAggregationKeyBuilder.KEY_SKIP);
	}

	/**
	 * Creates a new purchase order.
	 */
	@Override
	protected I_C_Order createGroup(final Object vendorBPartnerValue, final I_C_OrderLine salesOrderLine)
	{
		if (CreatePOFromSOsAggregationKeyBuilder.KEY_SKIP.equals(vendorBPartnerValue))
		{
			return dummyOrder;
			// nothing to do;
		}

		final I_C_Order salesOrder = salesOrderLine.getC_Order();

		final I_C_BPartner vendor = bpartnerDAO.retrieveBPartnerByValue(context.getCtx(), (String)vendorBPartnerValue);

		final I_C_Order purchaseOrder = createPurchaseOrder(vendor, salesOrder);

		final String msg = msgBL.getMsg(context.getCtx(),
				MSG_PURCHASE_ORDER_CREATED,
				new Object[] { purchaseOrder.getDocumentNo() });
		Loggables.get().addLog(msg);

		return purchaseOrder;
	}

	@Override
	protected void closeGroup(final I_C_Order purchaseOrder)
	{
		final CreatePOLineFromSOLinesAggregator orderLineAggregator = getCreateLineAggregator(purchaseOrder);
		orderLineAggregator.closeAllGroups();

		try
		{
			if (orderLineAggregator.getItemsCount() <= 0)
			{
				InterfaceWrapperHelper.delete(purchaseOrder);
			}
			else
			{
				// task 09802: Make docAction COmplete
				purchaseOrder.setDocAction(IDocument.ACTION_Complete);

				InterfaceWrapperHelper.save(purchaseOrder);
			}
		}
		catch (Throwable t)
		{
			Loggables.get().addLog("@Error@: " + t);
			throw AdempiereException.wrapIfNeeded(t);
		}
	}

	@Override
	protected void addItemToGroup(final I_C_Order purchaseOrder, final I_C_OrderLine salesOrderLine)
	{
		final CreatePOLineFromSOLinesAggregator orderLineAggregator = getCreateLineAggregator(purchaseOrder);
		if (orderLineAggregator.getPurchaseOrder() == dummyOrder)
		{
			return;// nothing to do
		}

		orderLineAggregator.add(salesOrderLine);

		final I_C_Order salesOrder = salesOrderLine.getC_Order();

		if (purchaseOrder.getLink_Order_ID() > 0 &&
				purchaseOrder.getLink_Order_ID() != salesOrder.getC_Order_ID())
		{
			purchaseOrder.setLink_Order(null);
		}
	}

	private CreatePOLineFromSOLinesAggregator getCreateLineAggregator(final I_C_Order pruchaseOrder)
	{
		CreatePOLineFromSOLinesAggregator orderLinesAggregator = orderKey2OrderLineAggregator.get(pruchaseOrder.getDocumentNo());
		if (orderLinesAggregator == null)
		{
			orderLinesAggregator = new CreatePOLineFromSOLinesAggregator(pruchaseOrder, purchaseQtySource);
			orderLinesAggregator.setItemAggregationKeyBuilder(CreatePOLineFromSOLinesAggregationKeyBuilder.INSTANCE);
			orderLinesAggregator.setGroupsBufferSize(100);

			orderKey2OrderLineAggregator.put(pruchaseOrder.getDocumentNo(), orderLinesAggregator);
		}
		return orderLinesAggregator;
	}

	private I_C_Order createPurchaseOrder(final I_C_BPartner vendor,
			final I_C_Order salesOrder)
	{

		final I_C_Order purchaseOrder = InterfaceWrapperHelper.newInstance(I_C_Order.class, context);

		// services
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(salesOrder);
		final String trxName = InterfaceWrapperHelper.getTrxName(salesOrder);

		final I_AD_Org org = salesOrder.getAD_Org();

		purchaseOrder.setAD_Org(org);
		purchaseOrder.setLink_Order_ID(salesOrder.getC_Order_ID());
		purchaseOrder.setIsSOTrx(false);
		orderBL.setDocTypeTargetId(purchaseOrder);
		//
		purchaseOrder.setDescription(salesOrder.getDescription());

		if (salesOrder.getPOReference() == null)
		{
			purchaseOrder.setPOReference(salesOrder.getDocumentNo());
		}
		else
		{
			purchaseOrder.setPOReference(salesOrder.getPOReference());
		}
		purchaseOrder.setPriorityRule(salesOrder.getPriorityRule());
		purchaseOrder.setM_Warehouse_ID(findWareousePOId(salesOrder));

		// 08812: Make sure the users are correctly set

		orderBL.setBPartner(purchaseOrder, vendor);
		orderBL.setBill_User_ID(purchaseOrder);

		//
		// SalesRep:
		// * let it to be set from BPartner (this was done above, by orderBL.setBPartner method)
		// * if not set use it from context
		if (purchaseOrder.getSalesRep_ID() <= 0)
		{
			purchaseOrder.setSalesRep_ID(Env.getContextAsInt(ctx, Env.CTXNAME_SalesRep_ID));
		}
		if (purchaseOrder.getSalesRep_ID() <= 0)
		{
			purchaseOrder.setSalesRep_ID(Env.getAD_User_ID(ctx));
		}

		// Drop Ship
		if (p_IsDropShip)
		{
			purchaseOrder.setIsDropShip(p_IsDropShip);

			if (salesOrder.isDropShip() && salesOrder.getDropShip_BPartner_ID() != 0)
			{
				purchaseOrder.setDropShip_BPartner_ID(salesOrder.getDropShip_BPartner_ID());
				purchaseOrder.setDropShip_Location_ID(salesOrder.getDropShip_Location_ID());
				purchaseOrder.setDropShip_User_ID(salesOrder.getDropShip_User_ID());
			}
			else
			{
				purchaseOrder.setDropShip_BPartner_ID(salesOrder.getC_BPartner_ID());
				purchaseOrder.setDropShip_Location_ID(salesOrder.getC_BPartner_Location_ID());
				purchaseOrder.setDropShip_User_ID(salesOrder.getAD_User_ID());
			}

			// get default drop ship warehouse
			final I_AD_OrgInfo orginfo = orgDAO.retrieveOrgInfo(ctx, org.getAD_Org_ID(), trxName);

			if (orginfo.getDropShip_Warehouse_ID() != 0)
			{
				purchaseOrder.setM_Warehouse_ID(orginfo.getDropShip_Warehouse_ID());
			}
			else
			{
				Loggables.get().addLog("@Missing@ @AD_OrgInfo@ @DropShip_Warehouse_ID@");
			}
		}
		// References
		purchaseOrder.setC_Activity_ID(salesOrder.getC_Activity_ID());
		purchaseOrder.setC_Campaign_ID(salesOrder.getC_Campaign_ID());
		purchaseOrder.setC_Project_ID(salesOrder.getC_Project_ID());
		purchaseOrder.setUser1_ID(salesOrder.getUser1_ID());
		purchaseOrder.setUser2_ID(salesOrder.getUser2_ID());
		purchaseOrder.setC_Currency_ID(salesOrder.getC_Currency_ID());
		//

		InterfaceWrapperHelper.save(purchaseOrder);
		return purchaseOrder;
	}	// createPOForVendor

	private int findWareousePOId(final I_C_Order salesOrder)
	{
		final int warehousePOId = warehouseDAO.retrieveOrgWarehousePOId(salesOrder.getAD_Org_ID());
		if (warehousePOId > 0)
		{
			return warehousePOId;
		}

		return salesOrder.getM_Warehouse_ID();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
