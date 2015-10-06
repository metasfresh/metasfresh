package de.metas.handlingunits.callout;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.IHUDocumentHandlerFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.order.api.IHUOrderBL;

public class C_Order extends OrderFastInput
{
	public String UpdateQuickEntry(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		// Check: if is dirty/temporary value skip it
		if (value == null)
		{
			return NO_ERROR;
		}

		final String FLAGNAME = C_Order.class.getName() + "UpdatingQuickEntry";

		if ("Y".equals(Env.getContext(ctx, WindowNo, FLAGNAME)))
		{
			return NO_ERROR;
		}

		Env.setContext(ctx, WindowNo, FLAGNAME, "Y");
		try
		{

			final I_C_Order order = InterfaceWrapperHelper.create(mTab, I_C_Order.class);

			final boolean hasTus = Services.get(IHUOrderBL.class).hasTUs(order);

			// The updates are needed just in case the product is contained in any transportation unit.
			// Otherwise, the fast input shall work as before ( just based on Product and Menge CU)
			if (hasTus)
			{
				Services.get(IHUOrderBL.class).updateQtys(order, mField.getColumnName());
			}
			return CalloutEngine.NO_ERROR;

		}
		finally
		{
			Env.setContext(ctx, WindowNo, FLAGNAME, (String)null);
		}
	}

	// not doint fast-input on M_HU_PI_Item_Product
	// public void onM_HU_PI_Item_Product(final Properties ctx, final int WindowNo,
	// final GridTab mTab, final GridField mField, final Object value)
	// {
	// evalProductQtyInput(ctx, WindowNo, mTab);
	// }

	public void onMengeCU(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		evalProductQtyInput(ctx, WindowNo, mTab);
	}

	public String onMProductChange(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		//
		// services
		final IHUDocumentHandlerFactory huDocumentHandlerFactory = Services.get(IHUDocumentHandlerFactory.class);
		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);

		final I_C_Order order = InterfaceWrapperHelper.create(mTab, I_C_Order.class);

		Check.assumeNotNull(order, "Order cannot be null");

		if (order.getC_BPartner() == null || order.getDateOrdered() == null)
		{
			// in case order's C_BPartner_ID or DateOrdered are null
			// (i.e. when we just hit New to create a new order), there is no point to search for M_HU_PI_Item_Product record.
			// Please assume M_HU_PI_Item_Product is null immediately

			return CalloutEngine.NO_ERROR;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(order);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, order.getM_Product_ID(), I_M_Product.class, trxName);

		final IHUDocumentHandler handler = huDocumentHandlerFactory.createHandler(org.compiere.model.I_C_Order.Table_Name);
		if (null != handler && order.getM_Product_ID() > 0)
		{
			final I_M_HU_PI_Item_Product overridePip = handler.getM_HU_PI_ItemProductFor(order);
			// If we have a default price and it has an M_HU_PI_Item_Product, suggest it in quick entry.
			if (null != overridePip && overridePip.getM_HU_PI_Item_Product_ID() > 0)
			{
				if (overridePip.isAllowAnyProduct())
				{
					order.setM_HU_PI_Item_Product(null);
				}
				else
				{
					order.setM_HU_PI_Item_Product(overridePip);
				}
				return CalloutEngine.NO_ERROR;
			}
		}

		final I_M_HU_PI_Item_Product pip = hupiItemProductDAO.retrieveMaterialItemProduct(product, order.getC_BPartner(), order.getDateOrdered(),
				X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit,
				true); // allowInfiniteCapacity = true

		if (pip == null)
		{
			// nothing to do, product is not included in any Transport Units
			return CalloutEngine.NO_ERROR;
		}

		else if (pip.isAllowAnyProduct())
		{
			return CalloutEngine.NO_ERROR;
		}
		else
		{
			order.setM_HU_PI_Item_Product(pip);
		}

		return CalloutEngine.NO_ERROR;
	}
}
