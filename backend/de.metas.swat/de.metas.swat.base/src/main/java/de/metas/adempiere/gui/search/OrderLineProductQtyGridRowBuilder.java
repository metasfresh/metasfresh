package de.metas.adempiere.gui.search;

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


import java.math.BigDecimal;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.model.I_C_OrderLine;
import de.metas.adempiere.model.I_C_Order;

/**
 * {@link IGridTabRowBuilder} which set <code>qty</code> to {@link I_C_OrderLine}s.
 * 
 * @author tsa
 * 
 */
public class OrderLineProductQtyGridRowBuilder implements IGridTabRowBuilder
{
	private static final transient Logger logger = LogManager.getLogger(OrderLineProductQtyGridRowBuilder.class);
	
	private int productId;
	private BigDecimal qty;

	public OrderLineProductQtyGridRowBuilder()
	{
		super();
	}

	@Override
	public void setSource(final Object model)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(model, I_C_Order.class))
		{
			logger.trace("Skip setting the source because model does not apply: {}", model);
			return;
		}

		final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);
		final int productId = order.getM_Product_ID();
		final BigDecimal qty = order.getQty_FastInput();
		setSource(productId, qty);
	}

	public void setSource(final int productId, final BigDecimal qty)
	{
		this.productId = productId;
		this.qty = qty;

	}

	@Override
	public boolean isValid()
	{
		if (productId <= 0)
		{
			return false;
		}

		if (qty == null || qty.signum() == 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public void apply(final Object model)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(model, I_C_OrderLine.class))
		{
			logger.debug("Skip applying because it's not an order line: {}", model);
			return;
		}

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		apply(orderLine);
	}

	private void apply(final I_C_OrderLine orderLine)
	{
		// Note: There is only the product's stocking-UOM.,.the C_UOM can't be changed in the product info UI.
		// That's why we don't need to convert
		orderLine.setQtyEntered(qty);
		orderLine.setQtyOrdered(qty);
	}

	@Override
	public boolean isCreateNewRecord()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
