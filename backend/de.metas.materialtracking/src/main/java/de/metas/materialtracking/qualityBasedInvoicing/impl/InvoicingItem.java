package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.util.Check;

/* package */class InvoicingItem implements IInvoicingItem
{
	private final I_M_Product product;
	private final BigDecimal qty;
	private final I_C_UOM uom;

	public InvoicingItem(final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		super();

		Check.assumeNotNull(product, "product not null");
		this.product = product;

		Check.assumeNotNull(qty, "qty not null");
		this.qty = qty;

		Check.assumeNotNull(uom, "uom not null");
		this.uom = uom;
	}

	@Override
	public String toString()
	{
		return "ProductionMaterial ["
				+ "product=" + product.getName()
				+ ", qty=" + qty
				+ ", uom=" + uom.getUOMSymbol()
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
		.append(product)
		.append(qty)
		.append(uom)
		.toHashcode();
	};

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final InvoicingItem other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
		.append(product, other.product)
		.append(qty, other.qty)
		.append(uom, other.uom)
		.isEqual();
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
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
}
