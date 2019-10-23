package de.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Immutable Product/Qty pair
 * 
 * @author tsa
 * 
 */
public class ProductQty
{
	private final int productId;
	private final BigDecimal qty;

	public ProductQty(int productId, BigDecimal qty)
	{
		super();

		this.productId = productId;
		this.qty = qty;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		result = prime * result + ((qty == null) ? 0 : qty.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductQty other = (ProductQty)obj;
		if (productId != other.productId)
			return false;
		if (qty == null)
		{
			if (other.qty != null)
				return false;
		}
		else if (!qty.equals(other.qty))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ProductQty [productId=" + productId + ", qty=" + qty + "]";
	}

	public int getProductId()
	{
		return productId;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

}
