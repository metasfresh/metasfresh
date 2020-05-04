package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.Ordering;
import com.vaadin.ui.UI;

import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.service.ISendService;

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

/**
 * RfQ header.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class RfqHeader implements ISendService.ISendAwareBean
{
	public static RfqHeader of(final Rfq rfq, final List<RfqQuantityReport> rfqQuantities)
	{
		return new RfqHeader(rfq, rfqQuantities);
	}

	public static final Ordering<RfqHeader> ORDERING_ByDateStart = Ordering.from(new Comparator<RfqHeader>()
	{
		@Override
		public int compare(final RfqHeader o1, final RfqHeader o2)
		{
			final Date dateStart1 = o1.getDateStart();
			final Date dateStart2 = o2.getDateStart();
			return dateStart1.compareTo(dateStart2);
		}
	});

	public static final Ordering<RfqHeader> ORDERING_ByProductName = Ordering.from(new Comparator<RfqHeader>()
	{
		@Override
		public int compare(final RfqHeader o1, final RfqHeader o2)
		{
			final String productName1 = o1.getProductName();
			final String productName2 = o2.getProductName();
			return productName1.compareTo(productName2);
		}
	});

	private final String rfq_uuid;

	private final String product_uuid;
	public static final String PROPERTY_ProductName = "productName";
	private final String productName;
	public static final String PROPERTY_ProductPackingInfo = "productPackingInfo";
	private final String productPackingInfo;

	public static final String PROPERTY_DateStart = "dateStart";
	private final Date dateStart;
	public static final String PROPERTY_DateEnd = "dateEnd";
	private final Date dateEnd;
	public static final String PROPERTY_DateClose = "dateClose";
	private final Date dateClose;

	public static final String PROPERTY_Price = "price";
	private BigDecimal price;
	private BigDecimal priceSent;
	private final String currencyCode;

	public static final String PROPERTY_QtyRequested = "qtyRequested";
	private final BigDecimal qtyRequested;
	public static final String PROPERTY_QtyPromised = "qtyPromised";
	private BigDecimal qtyPromised = BigDecimal.ZERO;
	private final String qtyCUInfo;

	private boolean sent;

	private final List<RfqQuantityReport> quantities;


	private RfqHeader(final Rfq rfq, final List<RfqQuantityReport> quantities)
	{
		super();

		final Locale locale = UI.getCurrent().getLocale();

		rfq_uuid = rfq.getUuid();

		final Product product = rfq.getProduct();
		product_uuid = product.getUuid();
		productName = product.getName(locale);

		productPackingInfo = product.getPackingInfo(locale);
		dateStart = rfq.getDateStart();
		dateEnd = rfq.getDateEnd();
		dateClose = rfq.getDateClose();
		
		qtyRequested = rfq.getQtyRequested();
		qtyCUInfo = rfq.getQtyCUInfo();

		currencyCode = rfq.getCurrencyCode();
		setPrice(rfq.getPricePromised());
		priceSent = price;
		sent = true;

		this.quantities = quantities == null ? ImmutableList.<RfqQuantityReport> of() : ImmutableList.copyOf(quantities);

		setQtyPromised(calculateQtyPromisedSum());
	}

	@Override
	public int hashCode()
	{
		return rfq_uuid.hashCode();
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
		if (!getClass().equals(obj.getClass()))
		{
			return false;
		}

		final RfqHeader other = (RfqHeader)obj;
		return rfq_uuid.equals(other.rfq_uuid);
	}

	public String getRfq_uuid()
	{
		return rfq_uuid;
	}

	public String getProduct_uuid()
	{
		return product_uuid;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getProductPackingInfo()
	{
		return productPackingInfo;
	}

	public Date getDateStart()
	{
		return dateStart;
	}

	public Date getDateEnd()
	{
		return dateEnd;
	}

	public Date getDateClose()
	{
		return dateClose;
	}

	public BigDecimal getPrice()
	{
		return price == null ? BigDecimal.ZERO : price;
	}

	public void setPrice(final BigDecimal price)
	{
		if (price == null)
		{
			this.price = BigDecimal.ZERO;
		}
		this.price = price;
	}

	public void setPriceSent(final BigDecimal priceSent)
	{
		this.priceSent = priceSent == null ? BigDecimal.ZERO : priceSent;
	}

	public BigDecimal getPriceSent()
	{
		return priceSent == null ? BigDecimal.ZERO : priceSent;
	}

	public BigDecimal getQtyRequested()
	{
		return qtyRequested;
	}

	public BigDecimal getQtyPromised()
	{
		return qtyPromised;
	}

	public void setQtyPromised(final BigDecimal qtyPromised)
	{
		this.qtyPromised = qtyPromised;
	}

	public List<RfqQuantityReport> getQuantities()
	{
		return quantities;
	}

	public BigDecimal calculateQtyPromisedSum()
	{
		BigDecimal qtyPromisedSum = BigDecimal.ZERO;
		for (final RfqQuantityReport bean : getQuantities())
		{
			final BigDecimal qtyPromised = bean.getQty();
			if (qtyPromised == null || qtyPromised.signum() == 0)
			{
				continue;
			}

			qtyPromisedSum = qtyPromisedSum.add(qtyPromised);
		}

		return qtyPromisedSum;
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

	@Override
	public boolean checkSent()
	{
		if (getPrice().compareTo(getPriceSent()) != 0)
		{
			return false;
		}

		for (final RfqQuantityReport quantity : getQuantities())
		{
			if (!quantity.checkSent())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void setSentFieldsFromActualFields()
	{
		setPriceSent(getPrice());

		for (final RfqQuantityReport rfqQuantity : getQuantities())
		{
			rfqQuantity.setSentFieldsFromActualFields();
		}
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}
	
	public String getQtyCUInfo()
	{
		return qtyCUInfo;
	}
}
