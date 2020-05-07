package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.CalloutOrder;
import org.compiere.model.MOrgInfo;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_OrderLine;

public class OrderLine extends CalloutEngine
{
	public String product(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		if (orderLine.getM_Product_ID() > 0)
		{
			I_M_Product product = InterfaceWrapperHelper.create(orderLine.getM_Product(), I_M_Product.class);
			orderLine.setIsDiverse(product.isDiverse());
		}
		else
		{
			orderLine.setIsDiverse(false);
		}

		return NO_ERROR;
	}

	public String warehouse(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine =  calloutField.getModel(I_C_OrderLine.class);
		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		if (orderLine.getM_Warehouse_ID() == order.getM_Warehouse_ID())
		{
			// warehouse of order and order line are the same; nothing to do
			return NO_ERROR;
		}

		if (orderLine.getM_Warehouse_ID() == MOrgInfo.get(calloutField.getCtx(), order.getAD_Org_ID(), ITrx.TRXNAME_None).getDropShip_Warehouse_ID())
		{
			// order line's warehouse is the dropship warehouse; nothing to do
			return NO_ERROR;
		}

		// correcting order line's warehouse id
		orderLine.setM_Warehouse_ID(order.getM_Warehouse_ID());
		return NO_ERROR;
	}

	/**
	 * Called for: C_OrderLine.IsPriceManual
	 */
	public String chkManualPrice(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		if (!orderLine.isManualPrice())
		{
			CalloutOrder.updatePrices(orderLine);
		}

		return NO_ERROR;
	}

}
