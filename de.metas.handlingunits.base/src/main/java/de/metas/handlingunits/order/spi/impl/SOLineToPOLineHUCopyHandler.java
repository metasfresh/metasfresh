package de.metas.handlingunits.order.spi.impl;

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


import java.math.BigDecimal;

import org.adempiere.document.service.ICopyHandler;
import org.adempiere.util.Services;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.adempiere.service.IOrderLineBL;
import de.metas.handlingunits.model.I_C_OrderLine;

/**
 * Copy the Handling Units related detail from the linked order line to the order line
 *
 * @param orderLine
 * @param linkedOrderLine
 * @see IOrderLineBL#copySpecificValuesFrom(de.metas.interfaces.I_C_OrderLine, de.metas.interfaces.I_C_OrderLine)
 */
public class SOLineToPOLineHUCopyHandler implements ICopyHandler<I_C_OrderLine>
{

	/**
	 * Does nothing
	 */
	@Override
	public void copyPreliminaryValues(final I_C_OrderLine from, final I_C_OrderLine to)
	{
		// nothing to do
	}

	@Override
	public void copyValues(final I_C_OrderLine from, final I_C_OrderLine to)
	{
		// TODO: put QtyItemCapacity and PackDescription in IHUPackingAware
		// workaround: set qtyItemCapacity and packDescription manually

		final BigDecimal qtyItemCapacity = from.getQtyItemCapacity();
		to.setQtyItemCapacity(qtyItemCapacity);

		final String packDescription = from.getPackDescription();
		to.setPackDescription(packDescription);

		final boolean overridePartner = false;
		Services.get(IHUPackingAwareBL.class).copy(
				new OrderLineHUPackingAware(to),
				new OrderLineHUPackingAware(from),
				overridePartner);
	}

	@Override
	public Class<I_C_OrderLine> getSupportedItemsClass()
	{
		return I_C_OrderLine.class;
	}

}
