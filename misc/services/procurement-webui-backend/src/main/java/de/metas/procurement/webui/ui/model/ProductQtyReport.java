package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;

import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.service.ISendService;
import de.metas.procurement.webui.util.DateUtils;

/*
 * #%L
 * de.metas.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class ProductQtyReport implements ISendService.ISendAwareBean
{
	public static final String PROPERY_Id = "id";
	public static final String PROPERY_ProductName = "productName";
	public static final String PROPERTY_Qty = "qty";

	public static final ProductQtyReport of(final Product product, final Date day)
	{
		final boolean sent = true; // important: we consider a zero qty as "sent" because we don't want to count it when we count the items which are not sent 
		return new ProductQtyReport(product, day, BigDecimal.ZERO, sent);
	}

	public static final ProductQtyReport of(final ProductSupply productSupply)
	{
		final boolean sent = true;
		return new ProductQtyReport(productSupply.getProduct(), productSupply.getDay(), productSupply.getQty(), sent);
	}

	private final Object id;
	private final Product product;
	private final Date day;
	private BigDecimal qty;
	private BigDecimal qtySent;
	private boolean sent;

	private ProductQtyReport(final Product product, final Date day, final BigDecimal qty, final boolean sent)
	{
		super();
		this.product = Preconditions.checkNotNull(product);
		id = "" + product.getId() + "-" + day.getTime();
		this.qty = Preconditions.checkNotNull(qty);
		this.day = DateUtils.truncToDay(Preconditions.checkNotNull(day));
		this.sent = sent;

		qtySent = sent ? qty : BigDecimal.ZERO;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("product", product)
				.add("qty", qty)
				.add("qtySent", qtySent)
				.add("day", day)
				.add("sent", sent)
				.add("id", id)
				.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
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
		final ProductQtyReport other = (ProductQtyReport)obj;
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

	public String getProductName(final Locale locale)
	{
		return product.getName(locale);
	}

	public String getProductPackingInfo(final Locale locale)
	{
		return product.getPackingInfo(locale);
	}
	
	public Date getDay()
	{
		return (Date)day.clone();
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public void setQty(BigDecimal qty)
	{
		if (qty == null)
		{
			qty = BigDecimal.ZERO;
		}

		this.qty = qty;
	}

	@Override
	public boolean isSent()
	{
		return sent;
	}

	@Override
	public void setSent(final boolean sent)
	{
		this.sent = sent;
	}

	public Product getProduct()
	{
		return product;
	}

	public BigDecimal getQtySent()
	{
		return qtySent;
	}

	public void setQtySent(final BigDecimal qtySent)
	{
		this.qtySent = qtySent;
	}
	
	@Override
	public boolean checkSent()
	{
		return getQty().compareTo(getQtySent()) == 0;
	}
	
	@Override
	public void setSentFieldsFromActualFields()
	{
		setQtySent(getQty());
	}
}