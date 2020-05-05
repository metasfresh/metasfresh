package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;

import com.google.gwt.thirdparty.guava.common.base.Objects;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class RfqPriceReport
{
	public static final RfqPriceReport of(final long rfq_id, final BigDecimal price)
	{
		return new RfqPriceReport(rfq_id, price);
	}
	public static final String PROPERTY_Price = "price";

	private final String id;
	private final long rfq_id;
	private BigDecimal price;

	private RfqPriceReport(long rfq_id, BigDecimal price)
	{
		super();
		this.rfq_id = rfq_id;
		this.price = price == null ? BigDecimal.ZERO : price;

		this.id = "" + rfq_id;
	}
	
	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("price", price)
				.add("rfq_id", rfq_id)
				.add("id", id)
				.toString();
	}
	
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final RfqPriceReport other = (RfqPriceReport)obj;
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		return true;
	}
	
	public long getRfq_id()
	{
		return rfq_id;
	}
	
	public BigDecimal getPrice()
	{
		return price;
	}
	
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

}
