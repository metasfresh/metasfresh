package de.metas.ui.web.quickinput.orderline;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.adempiere.gui.search.impl.PlainHUPackingAware;
import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;

/*
 * #%L
 * metasfresh-webui-api
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

public class OrderLineQuickInputProcessor implements IQuickInputProcessor
{
	// services
	private final transient IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final transient IHUPIItemProductBL piPIItemProductBL = Services.get(IHUPIItemProductBL.class);

	public OrderLineQuickInputProcessor()
	{
		super();
	}

	@Override
	public Object process(final QuickInput quickInput)
	{
		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);

		final I_C_OrderLine orderLineNew = OrderFastInput.addOrderLine(ctx, order, orderLineObj -> updateOrderLine(orderLineObj, quickInput));

		return orderLineNew;
	}

	private final void updateOrderLine(final Object orderLineObj, final QuickInput fromQuickInput)
	{
		final I_C_Order order = fromQuickInput.getRootDocumentAs(I_C_Order.class);
		final IOrderLineQuickInput fromOrderLineQuickInput = fromQuickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);
		final IHUPackingAware quickInputPackingAware = createQuickInputPackingAware(order, fromOrderLineQuickInput);

		final I_C_OrderLine orderLineToUpdate = InterfaceWrapperHelper.create(orderLineObj, I_C_OrderLine.class);
		final IHUPackingAware orderLinePackingAware = OrderLineHUPackingAware.of(orderLineToUpdate);

		final boolean overridePartner = false;
		huPackingAwareBL.copy(orderLinePackingAware, quickInputPackingAware, overridePartner);
	}

	public IHUPackingAware createQuickInputPackingAware(final I_C_Order order, final IOrderLineQuickInput quickInput)
	{
		final PlainHUPackingAware huPackingAware = new PlainHUPackingAware();
		huPackingAware.setC_BPartner(order.getC_BPartner());
		huPackingAware.setDateOrdered(order.getDateOrdered());
		huPackingAware.setInDispute(false);

		huPackingAware.setM_Product_ID(quickInput.getM_Product_ID());
		huPackingAware.setC_UOM(quickInput.getM_Product().getC_UOM());
		// huPackingAware.setM_AttributeSetInstance_ID(-1);

		final I_M_HU_PI_Item_Product piItemProduct = quickInput.getM_HU_PI_Item_Product();
		huPackingAware.setM_HU_PI_Item_Product(piItemProduct);

		if (piItemProduct == null || piPIItemProductBL.isVirtualHUPIItemProduct(piItemProduct) || piItemProduct.isInfiniteCapacity())
		{
			huPackingAware.setQty(quickInput.getQty());
			huPackingAware.setQtyPacks(BigDecimal.ONE);
		}
		else
		{
			final BigDecimal qtyTU = quickInput.getQty();
			huPackingAware.setQtyPacks(qtyTU);
			huPackingAwareBL.setQty(huPackingAware, qtyTU.intValue());
		}

		return huPackingAware;
	}
}
