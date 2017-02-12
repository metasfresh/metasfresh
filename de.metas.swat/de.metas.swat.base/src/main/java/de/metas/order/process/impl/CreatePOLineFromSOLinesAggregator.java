package de.metas.order.process.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.collections.MapReduceAggregator;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.process.IC_Order_CreatePOFromSOsBL;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * Creates purchase order lines for sales order lines. One instance of this aggregator is called with sales order lines that all belong to the same purchase order.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CreatePOLineFromSOLinesAggregator extends MapReduceAggregator<I_C_OrderLine, I_C_OrderLine>
{
	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private final I_C_Order purchaseOrder;

	private final String purchaseQtySource;

	private final Map<I_C_OrderLine, List<I_C_OrderLine>> purchaseOrderLine2saleOrderLines = new IdentityHashMap<>();

	/**
	 *
	 * @param purchaseOrder
	 * @param purchaseQtySource column name of the sales order line column to get the qty from. Can be either can be either QtyOrdered or QtyReserved.
	 */
	/* package */public CreatePOLineFromSOLinesAggregator(final I_C_Order purchaseOrder, final String purchaseQtySource)
	{
		this.purchaseOrder = purchaseOrder;

		this.purchaseQtySource = purchaseQtySource;
	}

	@Override
	protected I_C_OrderLine createGroup(final Object itemHashKey, final I_C_OrderLine salesOrderLine)
	{
		final I_C_OrderLine purchaseOrderLine;
		try
		{
			purchaseOrderLine = createPurchaseOrderLine(salesOrderLine);
			Services.get(IC_Order_CreatePOFromSOsBL.class)
					.getCompositeListener()
					.afterPurchaseOrderLineCreatedBeforeSave(purchaseOrderLine, salesOrderLine);
		}
		catch (final Throwable t)
		{
			Loggables.get().addLog("@Error@: " + t);
			throw AdempiereException.wrapIfNeeded(t);
		}

		purchaseOrderLine2saleOrderLines.put(purchaseOrderLine, new ArrayList<I_C_OrderLine>());
		return purchaseOrderLine;

	}

	private I_C_OrderLine createPurchaseOrderLine(final I_C_OrderLine salesOrderLine)
	{
		final I_M_AttributeSetInstance poASI;
		if (salesOrderLine.getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance soASI = salesOrderLine.getM_AttributeSetInstance();
			poASI = attributeDAO.copy(soASI);
		}
		else
		{
			poASI = null;
		}

		final I_C_OrderLine purchaseOrderLine = orderLineBL.createOrderLine(purchaseOrder);

		salesOrderLine.getC_Charge();
		purchaseOrderLine.setC_Charge_ID(salesOrderLine.getC_Charge_ID());

		if (salesOrderLine.getM_Product_ID() > 0)
		{
			purchaseOrderLine.setM_Product_ID(salesOrderLine.getM_Product_ID());
			// we use the product's uom, i.e. the internal stocking uom, because
			// 1. we can assume to have an UOM conversion from any sales order line's UOM.
			// 2. that way we can use the "internal-UOMs" Qty also for QtyEntered in addItemToGroup()
			purchaseOrderLine.setC_UOM_ID(salesOrderLine.getM_Product().getC_UOM_ID());
		}

		purchaseOrderLine.setQtyEntered(BigDecimal.ZERO);

		purchaseOrderLine.setDescription(salesOrderLine.getDescription());
		purchaseOrderLine.setDatePromised(salesOrderLine.getDatePromised());

		purchaseOrderLine.setC_BPartner(purchaseOrder.getC_BPartner());
		purchaseOrderLine.setC_BPartner_Location(purchaseOrder.getC_BPartner_Location());

		final int userID = salesOrderLine.getAD_User_ID();
		purchaseOrderLine.setAD_User_ID(userID);

		purchaseOrderLine.setM_AttributeSetInstance(poASI);
		IModelAttributeSetInstanceListener.DYNATTR_DisableASIUpdateOnModelChange.setValue(purchaseOrderLine, true); // (08091)

		return purchaseOrderLine;
	}

	@Override
	protected void closeGroup(final I_C_OrderLine purchaseOrderLine)
	{
		InterfaceWrapperHelper.save(purchaseOrderLine);
		for (final I_C_OrderLine salesOrderLine : purchaseOrderLine2saleOrderLines.get(purchaseOrderLine))
		{
			salesOrderLine.setLink_OrderLine(purchaseOrderLine);
			InterfaceWrapperHelper.save(salesOrderLine);
		}
	}

	@Override
	protected void addItemToGroup(final I_C_OrderLine purchaseOrderLine, final I_C_OrderLine salesOrderLine)
	{
		final BigDecimal oldQtyEntered = purchaseOrderLine.getQtyEntered();

		// the purchase order line's UOM is the internal stocking UOM, so we don't need to convert from qtyOrdered/qtyReserved to qtyEntered.
		final BigDecimal purchaseQty;
		if (I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(purchaseQtySource))
		{
			purchaseQty = salesOrderLine.getQtyOrdered();
		}
		else if (I_C_OrderLine.COLUMNNAME_QtyReserved.equals(purchaseQtySource))
		{
			purchaseQty = salesOrderLine.getQtyReserved();
		}
		else
		{
			Check.errorIf(true, "Unsupported purchaseQtySource={}", purchaseQtySource);
			purchaseQty = null; // won't be reached
		}

		final BigDecimal newQtyEntered = oldQtyEntered.add(purchaseQty);

		// setting QtyEntered, because qtyOrdered will be set from qtyEntered by a model interceptor
		purchaseOrderLine.setQtyEntered(newQtyEntered);

		purchaseOrderLine2saleOrderLines.get(purchaseOrderLine).add(salesOrderLine); // no NPE, because the list for this key was added in createGroup()
	}

	I_C_Order getPurchaseOrder()
	{
		return purchaseOrder;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
