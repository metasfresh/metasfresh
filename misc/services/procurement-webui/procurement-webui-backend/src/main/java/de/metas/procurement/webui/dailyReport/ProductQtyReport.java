package de.metas.procurement.webui.dailyReport;

import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.service.ISendService;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@EqualsAndHashCode(of = "id")
@ToString
public final class ProductQtyReport implements ISendService.ISendAwareBean
{
	public static final String PROPERY_Id = "id";
	public static final String PROPERY_ProductName = "productName";
	public static final String PROPERTY_Qty = "qty";

	public static final ProductQtyReport of(final Product product, final LocalDate day)
	{
		final boolean sent = true; // important: we consider a zero qty as "sent" because we don't want to count it when we count the items which are not sent
		return new ProductQtyReport(product, day, BigDecimal.ZERO, sent);
	}

	public static final ProductQtyReport of(final ProductSupply productSupply)
	{
		final boolean sent = true;
		return new ProductQtyReport(
				productSupply.getProduct(),
				productSupply.getDay(),
				productSupply.getQty(),
				sent);
	}

	private final String id;
	private final Product product;
	private final LocalDate day;
	private BigDecimal qty;
	private BigDecimal qtySent;
	private boolean sent;

	private ProductQtyReport(
			@NonNull final Product product,
			@NonNull final LocalDate day,
			@NonNull final BigDecimal qty,
			final boolean sent)
	{
		this.product = product;
		id = "" + product.getId() + "-" + day;
		this.qty = qty;
		this.day = day;
		this.sent = sent;

		qtySent = sent ? qty : BigDecimal.ZERO;
	}

	public String getProductName(final Locale locale)
	{
		return product.getName(locale);
	}

	public String getProductPackingInfo(final Locale locale)
	{
		return product.getPackingInfo(locale);
	}

	public LocalDate getDay()
	{
		return day;
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
