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


import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.quantity.Quantity;

/**
 * @author al
 */
public final class HUAttributeTransferRequestBuilder implements IHUAttributeTransferRequestBuilder
{
	private final IHUContext huContext;

	private I_M_Product product = null;
	private BigDecimal qty = null;
	private I_C_UOM uom = null;

	private IHUStorage huStorageFrom = null;
	private IHUStorage huStorageTo = null;

	private IAttributeStorage attributeStorageFrom = null;
	private IAttributeStorage attributeStorageTo = null;

	private BigDecimal qtyUnloaded = null;

	/** Virtual HU attributes transfer */
	private boolean vhuTransfer = false;

	public HUAttributeTransferRequestBuilder(final IHUContext huContext)
	{
		super();

		this.huContext = huContext;
	}

	/**
	 * Create request in give {@link IHUContext} for (product, qty, UOM) and between given {@link IProductStorage}s and {@link IAttributeStorage}s
	 *
	 * @return request
	 */
	@Override
	public IHUAttributeTransferRequest create()
	{
		return new HUAttributeTransferRequest(huContext,
				product, qty, uom,
				attributeStorageFrom, attributeStorageTo,
				huStorageFrom, huStorageTo,
				qtyUnloaded,
				vhuTransfer);
	}

	@Override
	public IHUAttributeTransferRequestBuilder setProduct(final I_M_Product product)
	{
		this.product = product;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setQty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setUOM(final I_C_UOM uom)
	{
		this.uom = uom;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setQuantity(final Quantity quantity)
	{
		qty = quantity.getQty();
		uom = quantity.getUOM();
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setAttributeStorageFrom(final IAttributeStorage attributeStorageFrom)
	{
		this.attributeStorageFrom = attributeStorageFrom;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setAttributeStorageTo(final IAttributeStorage attributeStorageTo)
	{
		this.attributeStorageTo = attributeStorageTo;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setHUStorageFrom(final IHUStorage huStorageFrom)
	{
		this.huStorageFrom = huStorageFrom;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setHUStorageTo(final IHUStorage huStorageTo)
	{
		this.huStorageTo = huStorageTo;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setQtyUnloaded(final BigDecimal qtyUnloaded)
	{
		this.qtyUnloaded = qtyUnloaded;
		return this;
	}

	@Override
	public IHUAttributeTransferRequestBuilder setVHUTransfer(final boolean vhuTransfer)
	{
		this.vhuTransfer = vhuTransfer;
		return this;
	}
}
