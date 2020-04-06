package de.metas.order;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
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
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private final OrderFactory parent;
	private boolean built = false;

	private ProductId productId;
	private int asiId = AttributeConstants.M_AttributeSetInstance_ID_None;
	private Quantity qty;

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

		Check.assumeNotNull(qty, "qty is not null");

		// assume returned order is already saved
		final I_C_OrderLine orderLine = Services.get(IOrderLineBL.class).createOrderLine(parent.getC_Order());

		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setM_AttributeSetInstance_ID(asiId);

		orderLine.setQtyEntered(qty.toBigDecimal());
		orderLine.setC_UOM_ID(qty.getUomId().getRepoId());

		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qty, productId);
		orderLine.setQtyOrdered(qtyOrdered.toBigDecimal());

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

		Services.get(IOrderLineBL.class).updatePrices(orderLine);
		save(orderLine);
		try (final MDCCloseable orderLineMDC = TableRecordMDC.putTableRecordReference(orderLine))
		{
			// would be great to also have the pricing engine run in here..if there is a way for this without the need to save twice
			logger.debug("Set C_OrderLine.QtyOrdered={} as converted from qty={} and productId={}", qtyOrdered.toBigDecimal(), qty, productId);
		}
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

	public OrderAndLineId getCreatedOrderAndLineId()
	{
		return OrderAndLineId.ofRepoIds(createdOrderLine.getC_Order_ID(), createdOrderLine.getC_OrderLine_ID());
	}

	public OrderLineBuilder productId(final ProductId productId)
	{
		assertNotBuilt();
		this.productId = productId;
		return this;
	}

	public ProductId getProductId()
	{
		return productId;
	}

	public OrderLineBuilder asiId(final int asiId)
	{
		assertNotBuilt();
		this.asiId = asiId;
		return this;
	}

	public OrderLineBuilder qty(@NonNull final Quantity qty)
	{
		assertNotBuilt();

		this.qty = qty;
		return this;
	}

	public OrderLineBuilder addQty(@NonNull final Quantity qtyToAdd)
	{
		assertNotBuilt();

		this.qty = Quantity.addNullables(this.qty, qtyToAdd);
		return this;
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

	public boolean isProductAndUomMatching(final ProductId productId, final UomId uomId)
	{
		return Objects.equals(getProductId(), productId)
				&& Objects.equals(getUomId(), uomId);
	}

	private UomId getUomId()
	{
		return qty != null ? qty.getUomId() : null;
	}

}
