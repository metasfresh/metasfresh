package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.CalloutEngine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

public class OrderLine extends CalloutEngine
{
	public String product(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId != null)
		{
			final IProductBL productBL = Services.get(IProductBL.class);
			orderLine.setIsDiverse(productBL.isDiverse(productId));
		}
		else
		{
			orderLine.setIsDiverse(false);
		}

		return NO_ERROR;
	}

	public String warehouse(final ICalloutField calloutField)
	{
		final I_C_OrderLine orderLine = calloutField.getModel(I_C_OrderLine.class);
		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		if (orderLine.getM_Warehouse_ID() == order.getM_Warehouse_ID())
		{
			// warehouse of order and order line are the same; nothing to do
			return NO_ERROR;
		}

		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final WarehouseId orgDropShipWarehouseId = orgsRepo.getOrgDropshipWarehouseId(orgId);
		if (orgDropShipWarehouseId != null && orderLine.getM_Warehouse_ID() == orgDropShipWarehouseId.getRepoId())
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

		if (orderLine.isManualPrice())
		{
			return NO_ERROR;
		}

		if (orderLine.getM_Product_ID() <= 0)
		{
			return NO_ERROR; // if we don't even have a product yet, then there is nothing to update
		}
		Services.get(IOrderLineBL.class).updatePrices(orderLine);
		return NO_ERROR;
	}

}
