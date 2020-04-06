package de.metas.handlingunits.document.impl;

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

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.product.ProductId;

public class PlainHUDocumentLine extends AbstractHUDocumentLine
{
	public PlainHUDocumentLine(final ProductId productId, final BigDecimal qty, final I_C_UOM uom)
	{
		super(
				new PlainProductStorage(productId, uom, qty),
				productId.toTableRecordReference() // reference model; we use product just to have something here
		);

	}

	private I_M_HU_PackingMaterial suggestedPackingMaterial;
	private I_M_HU_PI_Item_Product suggestedItemProduct;
	private I_M_HU_PI suggestedPI;

	@Override
	public IHUAllocations getHUAllocations()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not implemented");
	}

	public void setSuggestedPackingMaterial(final I_M_HU_PackingMaterial suggestedPackingMaterial)
	{
		this.suggestedPackingMaterial = suggestedPackingMaterial;
	}

	public void setSuggestedItemProduct(final I_M_HU_PI_Item_Product suggestedItemProduct)
	{
		this.suggestedItemProduct = suggestedItemProduct;
	}

	public I_M_HU_PackingMaterial getSuggestedPackingMaterial()
	{
		return suggestedPackingMaterial;
	}

	public I_M_HU_PI_Item_Product getSuggestedItemProduct()
	{
		return suggestedItemProduct;
	}

	public I_M_HU_PI getSuggestedPI()
	{
		return suggestedPI;
	}

	public void setSuggestedPI(final I_M_HU_PI suggestedPI)
	{
		this.suggestedPI = suggestedPI;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return -1;
	}
}
