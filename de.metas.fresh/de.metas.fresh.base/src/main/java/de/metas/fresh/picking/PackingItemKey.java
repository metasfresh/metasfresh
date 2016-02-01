package de.metas.fresh.picking;

/*
 * #%L
 * de.metas.fresh.base
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


import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

public class PackingItemKey
{
	private final int bpartnerId;
	private final int bpartnerLocationId;
	private final int productId;

	public PackingItemKey(int bpartnerId, int bpartnerLocationId, int productId)
	{
		super();
		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.productId = productId;
	}

	@Override
	public String toString()
	{
		return "PackingItemKey [bpartnerId=" + bpartnerId + ", bpartnerLocationId=" + bpartnerLocationId + ", productId=" + productId + "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(bpartnerId)
				.append(bpartnerLocationId)
				.append(productId)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final PackingItemKey other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.bpartnerId, other.bpartnerId)
				.append(this.bpartnerLocationId, other.bpartnerLocationId)
				.append(this.productId, other.productId)
				.isEqual();
	}

	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	public int getC_BPartner_Location_ID()
	{
		return bpartnerLocationId;
	}

	public int getM_Product_ID()
	{
		return productId;
	}
}
