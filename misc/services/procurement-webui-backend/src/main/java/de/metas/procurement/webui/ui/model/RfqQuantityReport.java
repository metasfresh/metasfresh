package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.thirdparty.guava.common.base.Objects;

import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.util.DateUtils;

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

public final class RfqQuantityReport
{
	public static final RfqQuantityReport of(final Rfq rfqRecord, final Date day, final BigDecimal qty, final String qtyCUInfo)
	{
		return new RfqQuantityReport(rfqRecord, day, qty, qtyCUInfo);
	}

	public static final RfqQuantityReport of(final Rfq rfqRecord, final Date day, final String qtyCUInfo)
	{
		final BigDecimal qty = BigDecimal.ZERO;
		return new RfqQuantityReport(rfqRecord, day, qty, qtyCUInfo);
	}

	public static final String PROPERTY_Qty = "qty";

	private final String id;
	private final String rfq_uuid;
	private final String product_uuid;
	private final Date day;

	private final String qtyCUInfo;
	private BigDecimal qty;
	private BigDecimal qtySent;


	private RfqQuantityReport(final Rfq rfqRecord, final Date day, final BigDecimal qty, final String qtyCUInfo)
	{
		super();
		this.rfq_uuid = rfqRecord.getUuid();
		this.product_uuid = rfqRecord.getProduct().getUuid();
		this.day = DateUtils.truncToDay(day);
		this.qtyCUInfo = qtyCUInfo;
		this.qty = qty;
		qtySent = qty;

		id = "" + rfq_uuid + "-" + product_uuid + "-" + day.getTime();
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("product", product_uuid)
				.add("qty", qty)
				.add("qtySent", qtySent)
				.add("day", day)
				.add("rfq_uuid", rfq_uuid)
				.add("id", id)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
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
		final RfqQuantityReport other = (RfqQuantityReport)obj;
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

	public String getRfq_uuid()
	{
		return rfq_uuid;
	}
	
	public String getProduct_uuid()
	{
		return product_uuid;
	}

	public Date getDay()
	{
		return (Date)day.clone();
	}

	public String getQtyCUInfo()
	{
		return qtyCUInfo;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public void setQty(final BigDecimal qty)
	{
		this.qty = qty == null ? BigDecimal.ZERO : qty;
	}

	public BigDecimal getQtySent()
	{
		return qtySent;
	}

	public void setQtySent(final BigDecimal qtySent)
	{
		this.qtySent = qtySent;
	}

	public boolean checkSent()
	{
		return getQty().compareTo(getQtySent()) == 0;
	}

	public void setSentFieldsFromActualFields()
	{
		setQtySent(getQty());
	}
}
