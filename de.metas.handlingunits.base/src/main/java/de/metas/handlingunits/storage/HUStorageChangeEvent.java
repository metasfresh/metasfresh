package de.metas.handlingunits.storage;

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

import org.adempiere.mm.attributes.api.CurrentAttributeValueContextProvider;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;

public class HUStorageChangeEvent
{
	private final IHUStorage huStorage;
	private final I_M_Product product;
	private final I_C_UOM uom;
	private final BigDecimal qtyOld;
	private final BigDecimal qtyNew;
	private final IAttributeValueContext attributesContext;

	private IAttributeStorage attributeStorage;
	private IAttributeValue attributeValue;

	public HUStorageChangeEvent(final IHUStorage huStorage, final I_M_Product product, final I_C_UOM uom, final BigDecimal qtyOld, final BigDecimal qtyNew)
	{
		this.huStorage = huStorage;
		this.product = product;
		this.uom = uom;
		this.qtyOld = qtyOld;
		this.qtyNew = qtyNew;

		attributesContext = CurrentAttributeValueContextProvider.getCurrentAttributesContextOrNull();
	}

	public IHUStorage getHUStorage()
	{
		return huStorage;
	}

	public I_M_Product getM_Product()
	{
		return product;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	public BigDecimal getQtyOld()
	{
		return qtyOld;
	}

	public BigDecimal getQtyNew()
	{
		return qtyNew;
	}

	public IAttributeValueContext getAttributeValueContext()
	{
		return attributesContext;
	}

	public IAttributeStorage getAttributeStorage()
	{
		return attributeStorage;
	}

	public void setAttributeStorage(final IAttributeStorage attributeStorage)
	{
		this.attributeStorage = attributeStorage;
	}

	public IAttributeValue getAttributeValue()
	{
		return attributeValue;
	}

	public void setAttributeValue(final IAttributeValue attributeValue)
	{
		this.attributeValue = attributeValue;
	}
}
