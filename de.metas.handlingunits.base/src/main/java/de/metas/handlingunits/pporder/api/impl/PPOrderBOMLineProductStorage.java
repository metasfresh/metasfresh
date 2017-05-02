package de.metas.handlingunits.pporder.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.storage.impl.AbstractProductStorage;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;

/**
 * Product storage for a manufacturing order BOM Line.
 *
 * Use this storage when you want to manipulate manufacturing order components (i.e. issuing raw materials to a manufacturing order)
 *
 * @author tsa
 *
 */
public class PPOrderBOMLineProductStorage extends AbstractProductStorage
{
	// Services
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

	private final I_PP_Order_BOMLine orderBOMLine;
	private boolean staled = false;

	public PPOrderBOMLineProductStorage(final I_PP_Order_BOMLine orderBOMLine)
	{
		Check.assumeNotNull(orderBOMLine, "orderBOMLine");
		this.orderBOMLine = orderBOMLine;
	}

	/** @return infinite capacity because we don't want to enforce how many items we can allocate on this storage */
	@Override
	protected IHUCapacityDefinition retrieveTotalCapacity()
	{
		checkStaled();
		
		final I_M_Product product = orderBOMLine.getM_Product();
		final I_C_UOM uom = orderBOMLine.getC_UOM();
		return capacityBL.createInfiniteCapacity(product, uom);
	}

	/** @return quantity that was already issued/received on this order BOM Line */
	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		final BigDecimal qtyCapacity;
		final BigDecimal qtyToIssueOrReceive;
		if (PPOrderUtil.isReceipt(orderBOMLine.getComponentType()))
		{
			qtyCapacity = ppOrderBOMBL.getQtyRequiredToReceive(orderBOMLine);
			qtyToIssueOrReceive = ppOrderBOMBL.getQtyToReceive(orderBOMLine);
		}
		else
		{
			qtyCapacity = ppOrderBOMBL.getQtyRequiredToIssue(orderBOMLine);
			qtyToIssueOrReceive = ppOrderBOMBL.getQtyToIssue(orderBOMLine);
		}

		final BigDecimal qtyIssued = qtyCapacity.subtract(qtyToIssueOrReceive);
		return qtyIssued;
	}

	@Override
	protected void beforeMarkingStalled()
	{
		staled = true;
	}

	/** refresh BOM line if staled */
	private final void checkStaled()
	{
		if (!staled)
		{
			return;
		}

		InterfaceWrapperHelper.refresh(orderBOMLine);
		staled = false;
	}
}
