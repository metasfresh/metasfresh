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

import de.metas.handlingunits.storage.impl.AbstractProductStorage;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Product storage for a manufacturing order BOM Line.
 * Use this storage when you want to manipulate manufacturing order components (i.e. issuing raw materials to a manufacturing order)
 *
 * @author tsa
 */
public class PPOrderBOMLineProductStorage extends AbstractProductStorage
{
	// Services
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

	private final I_PP_Order_BOMLine orderBOMLine;
	private final ProductId productId;
	private boolean staled = false;

	public PPOrderBOMLineProductStorage(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		this(orderBOMLine, null);
	}

	public PPOrderBOMLineProductStorage(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@Nullable final ProductId productId)
	{
		this.orderBOMLine = orderBOMLine;
		this.productId = computeProductIdEffective(orderBOMLine, productId);
	}

	private static ProductId computeProductIdEffective(
			@NonNull final I_PP_Order_BOMLine orderBOMLine,
			@Nullable final ProductId productId)
	{
		final ProductId bomLineProductId = ProductId.ofRepoId(orderBOMLine.getM_Product_ID());
		if (productId == null)
		{
			return bomLineProductId;
		}
		else if (orderBOMLine.isAllowIssuingAnyProduct())
		{
			return productId;
		}
		else
		{
			if (!ProductId.equals(productId, bomLineProductId))
			{
				throw new AdempiereException("Product " + productId + " is not matching BOM line product " + bomLineProductId)
						.appendParametersToMessage()
						.setParameter("orderBOMLine", orderBOMLine);
			}
			return bomLineProductId;
		}

	}

	/**
	 * @return infinite capacity because we don't want to enforce how many items we can allocate on this storage
	 */
	@Override
	protected Capacity retrieveTotalCapacity()
	{
		checkStaled();

		final I_C_UOM uom = ppOrderBOMBL.getBOMLineUOM(orderBOMLine);
		return Capacity.createInfiniteCapacity(productId, uom);
	}

	/**
	 * @return quantity that was already issued/received on this order BOM Line
	 */
	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		checkStaled();

		final Quantity qtyCapacity;
		final Quantity qtyToIssueOrReceive;
		final BOMComponentType componentType = BOMComponentType.ofCode(orderBOMLine.getComponentType());
		if (componentType.isReceipt())
		{
			qtyCapacity = ppOrderBOMBL.getQtyRequiredToReceive(orderBOMLine);
			qtyToIssueOrReceive = ppOrderBOMBL.getQtyToReceive(orderBOMLine);
		}
		else
		{
			qtyCapacity = ppOrderBOMBL.getQtyRequiredToIssue(orderBOMLine);
			qtyToIssueOrReceive = ppOrderBOMBL.getQtyToIssue(orderBOMLine);
		}

		final Quantity qtyIssued = qtyCapacity.subtract(qtyToIssueOrReceive);
		return qtyIssued.toBigDecimal();
	}

	@Override
	protected void beforeMarkingStalled()
	{
		staled = true;
	}

	/**
	 * refresh BOM line if staled
	 */
	private void checkStaled()
	{
		if (!staled)
		{
			return;
		}

		InterfaceWrapperHelper.refresh(orderBOMLine);
		staled = false;
	}
}
