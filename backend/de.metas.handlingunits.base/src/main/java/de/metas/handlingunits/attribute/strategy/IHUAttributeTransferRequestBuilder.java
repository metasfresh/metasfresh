package de.metas.handlingunits.attribute.strategy;

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

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

@SuppressWarnings("UnusedReturnValue")
public interface IHUAttributeTransferRequestBuilder
{
	IHUAttributeTransferRequestBuilder setProductId(ProductId productId);

	IHUAttributeTransferRequestBuilder setQty(BigDecimal qty);

	/**
	 * Set UOM for the product that's being transferred
	 */
	IHUAttributeTransferRequestBuilder setUOM(I_C_UOM uom);

	/**
	 * Set Qty/UOM for the product that's being transferred
	 */
	IHUAttributeTransferRequestBuilder setQuantity(Quantity quantity);

	/**
	 * Set attribute storage for the HU storage FROM which we're transferring attributes (Source)
	 */
	IHUAttributeTransferRequestBuilder setAttributeStorageFrom(IAttributeSet attributeStorageFrom);

	/**
	 * Set attribute storage for the HU storage TO which we're transferring attributes (Target)
	 */
	IHUAttributeTransferRequestBuilder setAttributeStorageTo(IAttributeStorage attributeStorageTo);

	/**
	 * Set HU storage FROM which we're transferring attributes (Source)
	 */
	IHUAttributeTransferRequestBuilder setHUStorageFrom(IHUStorage huStorageFrom);

	/**
	 * Set HU storage TO which we're transferring attributes (Target)
	 */
	IHUAttributeTransferRequestBuilder setHUStorageTo(IHUStorage huStorageTo);

	/**
	 * Set qty already unloaded
	 */
	IHUAttributeTransferRequestBuilder setQtyUnloaded(BigDecimal qtyUnloaded);

	/**
	 * Build actual request
	 *
	 * @return request
	 */
	IHUAttributeTransferRequest create();

	/**
	 * Sets if this is a transfer for Virtual HU attributes
	 */
	IHUAttributeTransferRequestBuilder setVHUTransfer(boolean vhuTransfer);
}
