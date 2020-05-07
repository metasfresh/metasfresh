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


import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.quantity.Quantity;

public interface IHUAttributeTransferRequestBuilder
{
	/**
	 * Set product that's being transferred
	 *
	 * @param product
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setProduct(I_M_Product product);

	/**
	 * Set qty that's being transferred
	 *
	 * @param qty
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setQty(BigDecimal qty);

	/**
	 * Set UOM for the product that's being transferred
	 *
	 * @param uom
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setUOM(I_C_UOM uom);

	/**
	 * Set Qty/UOM for the product that's being transferred
	 *
	 * @param quantity
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setQuantity(Quantity quantity);

	/**
	 * Set attribute storage for the HU storage FROM which we're transferring attributes (Source)
	 *
	 * @param attributeStorageFrom
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setAttributeStorageFrom(IAttributeStorage attributeStorageFrom);

	/**
	 * Set attribute storage for the HU storage TO which we're transferring attributes (Target)
	 *
	 * @param attributeStorageTo
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setAttributeStorageTo(IAttributeStorage attributeStorageTo);

	/**
	 * Set HU storage FROM which we're transferring attributes (Source)
	 *
	 * @param huStorageFrom
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setHUStorageFrom(IHUStorage huStorageFrom);

	/**
	 * Set HU storage TO which we're transferring attributes (Target)
	 *
	 * @param huStorageTo
	 * @return builder
	 */
	IHUAttributeTransferRequestBuilder setHUStorageTo(IHUStorage huStorageTo);

	/**
	 * Set qty already unloaded
	 *
	 * @param qtyUnloaded
	 * @return builder
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
	 *
	 * @param vhuTransfer
	 * @return
	 */
	IHUAttributeTransferRequestBuilder setVHUTransfer(boolean vhuTransfer);
}
