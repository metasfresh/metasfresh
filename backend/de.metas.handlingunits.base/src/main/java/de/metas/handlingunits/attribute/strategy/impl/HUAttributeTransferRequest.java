package de.metas.handlingunits.attribute.strategy.impl;

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

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.util.Check;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

/* package */final class HUAttributeTransferRequest implements IHUAttributeTransferRequest
{
	private final IHUContext huContext;

	private final ProductId productId;
	private final BigDecimal qty;
	private final I_C_UOM uom;

	private final IAttributeSet attributeStorageFrom;
	private final IAttributeStorage attributeStorageTo;

	private final IHUStorage huStorageFrom;
	private final IHUStorage huStorageTo;

	private final BigDecimal qtyUnloaded;

	private final boolean vhuTransfer;

	public HUAttributeTransferRequest(final IHUContext huContext,
									  final ProductId productId,
									  final BigDecimal qty,
									  final I_C_UOM uom,
									  final IAttributeSet attributeStorageFrom,
									  final IAttributeStorage attributeStorageTo,
									  final IHUStorage huStorageFrom,
									  final IHUStorage huStorageTo,
									  final BigDecimal qtyUnloaded,
									  final boolean vhuTransfer)
	{
		super();

		Check.assumeNotNull(huContext, "huContext not null");
		this.huContext = huContext;

		Check.assumeNotNull(productId, "product not null");
		this.productId = productId;

		Check.assumeNotNull(qty, "qty not null");
		this.qty = qty;

		Check.assumeNotNull(uom, "uom not null");
		this.uom = uom;

		Check.assumeNotNull(attributeStorageFrom, "attributeStorageFrom not null");
		this.attributeStorageFrom = attributeStorageFrom;

		Check.assumeNotNull(attributeStorageTo, "attributeStorageTo not null");
		this.attributeStorageTo = attributeStorageTo;

		this.huStorageFrom = huStorageFrom;
		this.huStorageTo = huStorageTo;

		this.qtyUnloaded = qtyUnloaded;

		this.vhuTransfer = vhuTransfer;
	}

	@Override
	public String toString()
	{
		return "HUAttributeTransferRequest [huContext=" + huContext + ", product=" + productId + ", qty=" + qty + ", uom=" + uom + ", attributeStorageFrom=" + attributeStorageFrom
				+ ", attributeStorageTo=" + attributeStorageTo + ", huStorageFrom=" + huStorageFrom + ", huStorageTo=" + huStorageTo + "]";
	}

	@Override
	public IHUContext getHUContext()
	{
		return huContext;
	}

	@Override
	public ProductId getProductId()
	{
		return productId;
	}

	@Override
	public BigDecimal getQty()
	{
		return qty;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public IAttributeSet getAttributesFrom()
	{
		return attributeStorageFrom;
	}

	@Override
	public IAttributeStorage getAttributesTo()
	{
		return attributeStorageTo;
	}

	@Override
	public IHUStorage getHUStorageFrom()
	{
		return huStorageFrom;
	}

	@Override
	public IHUStorage getHUStorageTo()
	{
		return huStorageTo;
	}

	@Override
	public BigDecimal getQtyUnloaded()
	{
		return qtyUnloaded;
	}

	@Override
	public boolean isVHUTransfer()
	{
		return vhuTransfer;
	}
}
