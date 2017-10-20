package org.adempiere.mm.attributes.listeners.adr;

/*
 * #%L
 * de.metas.fresh.base
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

import java.util.Arrays;
import java.util.List;

import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;

/**
 * 
 * This listener wraps an {@link OrderLineADRModelAttributeSetInstanceListener} and invokes it for each order line of an order.
 *
 */
public class OrderADRModelAttributeSetInstanceListener implements IModelAttributeSetInstanceListener
{
	private final OrderLineADRModelAttributeSetInstanceListener orderLineListener = new OrderLineADRModelAttributeSetInstanceListener();
	private static final List<String> sourceColumnNames = Arrays.asList(I_C_Order.COLUMNNAME_C_BPartner_ID);

	@Override
	public String getSourceTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public List<String> getSourceColumnNames()
	{
		return sourceColumnNames;
	}

	@Override
	public void modelChanged(final Object model)
	{
		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

		final I_C_Order order = InterfaceWrapperHelper.create(model, I_C_Order.class);
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);

		for (final I_C_OrderLine line : orderLines)
		{
			orderLineListener.modelChanged(line);
		}
	}
}
