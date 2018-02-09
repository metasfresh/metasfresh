package de.metas.order;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.interfaces.I_C_OrderLine;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Order line builder. Used exclusively by {@link OrderFactory}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class OrderLineBuilder
{
	private final OrderFactory parent;
	private boolean built = false;

	private int productId;
	private int asiId = AttributeConstants.M_AttributeSetInstance_ID_None;
	private BigDecimal qty;
	private int uomId;

	private BigDecimal manualPrice;
	private BigDecimal manualDiscount;

	private I_C_OrderLine createdOrderLine;

	/* package */ OrderLineBuilder(@NonNull final OrderFactory parent)
	{
		this.parent = parent;
	}

	/* package */void build()
	{
		assertNotBuilt();
		built = true;

		// assume returned order is already saved
		final I_C_OrderLine orderLine = Services.get(IOrderLineBL.class).createOrderLine(parent.getC_Order());

		orderLine.setM_Product_ID(productId);
		orderLine.setM_AttributeSetInstance_ID(asiId);

		orderLine.setQtyEntered(qty);
		orderLine.setQtyOrdered(qty);
		orderLine.setC_UOM_ID(uomId);

		if (manualPrice != null)
		{
			orderLine.setIsManualPrice(true);
			orderLine.setPriceEntered(manualPrice);
		}

		if (manualDiscount != null)
		{
			orderLine.setIsManualDiscount(true);
			orderLine.setDiscount(manualDiscount);
		}

		save(orderLine);
		this.createdOrderLine = orderLine;
	}

	private void assertNotBuilt()
	{
		if (built)
		{
			throw new AdempiereException("Already built: " + this);
		}
	}

	public OrderFactory endOrderLine()
	{
		return parent;
	}

	public int getCreatedOrderLineId()
	{
		return createdOrderLine.getC_OrderLine_ID();
	}

	public OrderLineBuilder productId(final int productId)
	{
		assertNotBuilt();
		this.productId = productId;
		return this;
	}

	public int getProductId()
	{
		return productId;
	}

	public OrderLineBuilder asiId(final int asiId)
	{
		assertNotBuilt();
		this.asiId = asiId;
		return this;
	}

	public OrderLineBuilder qty(@NonNull final BigDecimal qty, final int uomId)
	{
		assertNotBuilt();

		Check.assume(uomId > 0, "uomId > 0");
		this.qty = qty;
		this.uomId = uomId;
		return this;
	}

	public OrderLineBuilder addQty(@NonNull final BigDecimal qtyToAdd, final int uomId)
	{
		assertNotBuilt();

		Check.assume(uomId > 0, "uomId > 0");
		if (this.uomId > 0 && this.uomId != uomId)
		{
			throw new AdempiereException("UOM does not match");
		}

		this.qty = this.qty != null ? this.qty.add(qtyToAdd) : qtyToAdd;
		this.uomId = this.uomId > 0 ? this.uomId : uomId;

		return this;
	}

	public int getUomId()
	{
		return uomId;
	}

	public OrderLineBuilder manualPrice(final BigDecimal manualPrice)
	{
		assertNotBuilt();
		this.manualPrice = manualPrice;
		return this;
	}

	public OrderLineBuilder manualDiscount(final BigDecimal manualDiscount)
	{
		assertNotBuilt();
		this.manualDiscount = manualDiscount;
		return this;
	}
}
