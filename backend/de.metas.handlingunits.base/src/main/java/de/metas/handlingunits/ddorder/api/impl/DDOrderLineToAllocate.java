package de.metas.handlingunits.ddorder.api.impl;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.quantity.Quantity;

/**
 * Wraps a {@link I_DD_OrderLine_Or_Alternative} and keeps track of changes done to it's qty via movements (which are not completed).
 *
 * @author al
 */
/* package */final class DDOrderLineToAllocate
{
	private final transient IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final I_DD_OrderLine_Or_Alternative _ddOrderLineOrAlt;

	private final Map<Integer, I_M_HU> huId2HUs = new HashMap<>();

	private final IUOMConversionContext uomConversionCtx;
	private Quantity qtyToShipScheduled;
	private Quantity qtyToShipRemaining;

	public DDOrderLineToAllocate(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		Check.assumeNotNull(ddOrderLineOrAlt, "ddOrderLineOrAlt not null");
		_ddOrderLineOrAlt = ddOrderLineOrAlt;

		uomConversionCtx = uomConversionBL.createConversionContext(ddOrderLineOrAlt.getM_Product());

		final BigDecimal qtyToAllocateMax = ddOrderBL.getQtyToShip(ddOrderLineOrAlt);
		final I_C_UOM qtyToAllocateMaxUOM = ddOrderLineOrAlt.getC_UOM();

		qtyToShipRemaining = new Quantity(qtyToAllocateMax, qtyToAllocateMaxUOM);
		qtyToShipScheduled = qtyToShipRemaining.toZero();
	}

	public I_DD_OrderLine_Or_Alternative getDD_OrderLine_Or_Alternative()
	{
		return _ddOrderLineOrAlt;
	}
	
	public int getProductId()
	{
		return getDD_OrderLine_Or_Alternative().getM_Product_ID();
	}

	public Collection<I_M_HU> getM_HUs()
	{
		return huId2HUs.values();
	}

	public Quantity getQtyToShipRemaining()
	{
		return qtyToShipRemaining;
	}

	public Quantity getQtyToShipScheduled()
	{
		return qtyToShipScheduled;
	}

	public boolean isFullyShipped()
	{
		return qtyToShipRemaining.signum() <= 0;
	}

	/**
	 *
	 * @param hu
	 * @param qtyToAllocate
	 * @param force if <code>true</code> it will allocate entire quantity no matter what
	 * @return actual quantity that was added to shipped quantity
	 */
	public Quantity addQtyShipped(final I_M_HU hu, final Quantity qtyToAllocate, final boolean force)
	{
		//
		// Get how much we can allocate (maximum)
		final Quantity qtyToAllocateMax;
		if (force)
		{
			qtyToAllocateMax = qtyToAllocate;
		}
		else
		{
			qtyToAllocateMax = getQtyToShipRemaining();
		}

		final Quantity qtyToAllocateConv = uomConversionBL.convertQuantityTo(qtyToAllocate, uomConversionCtx, qtyToAllocateMax.getUOM());
		final Quantity qtyToAllocateRemaining = qtyToAllocateMax.subtract(qtyToAllocateConv);

		final Quantity qtyShipped;
		if (qtyToAllocateRemaining.signum() < 0)
		{
			qtyShipped = qtyToAllocateMax; // allocate the maximum allowed qty if it's lower than the remaining qty
		}
		else
		{
			qtyShipped = qtyToAllocateConv; // allocate the full remaining qty because it's within the maximum boundaries
		}

		if (qtyShipped.isZero())
		{
			return qtyShipped;
		}

		huId2HUs.put(hu.getM_HU_ID(), hu);

		final Quantity qtyShippedConv = uomConversionBL.convertQuantityTo(qtyShipped, uomConversionCtx, qtyToShipRemaining.getUOM());
		qtyToShipRemaining = qtyToShipRemaining.subtract(qtyShippedConv);
		qtyToShipScheduled = qtyToShipScheduled.add(qtyShippedConv);

		return qtyShippedConv;
	}

	public I_DD_OrderLine getDD_OrderLine()
	{
		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = getDD_OrderLine_Or_Alternative();

		final I_DD_OrderLine ddOrderLine;
		if (InterfaceWrapperHelper.isInstanceOf(ddOrderLineOrAlt, I_DD_OrderLine.class))
		{
			ddOrderLine = InterfaceWrapperHelper.create(ddOrderLineOrAlt, I_DD_OrderLine.class);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(ddOrderLineOrAlt, I_DD_OrderLine_Alternative.class))
		{
			final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.create(ddOrderLineOrAlt, I_DD_OrderLine_Alternative.class);
			ddOrderLine = ddOrderLineAlt.getDD_OrderLine();
		}
		else
		{
			//
			// Shall not happen; developer error
			throw new AdempiereException("Invalid I_DD_OrderLine_Or_Alternative implementation passed; Expected {} or {}, but was {}",
					new Object[] { I_DD_OrderLine.class, I_DD_OrderLine_Alternative.class, ddOrderLineOrAlt });
		}
		return ddOrderLine;
	}
}
