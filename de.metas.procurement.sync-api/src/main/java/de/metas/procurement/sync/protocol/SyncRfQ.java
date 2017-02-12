package de.metas.procurement.sync.protocol;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * #%L
 * de.metas.procurement.sync-api
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

@XmlRootElement(name = "SyncRfQ")
public class SyncRfQ extends AbstractSyncModel
{
	private Date dateStart;
	private Date dateEnd;
	private Date dateClose;

	private String bpartner_uuid;

	private SyncProduct product;
	private BigDecimal qtyRequested;
	private String qtyCUInfo;

	private String currencyCode;

	@Override
	public String toString()
	{
		return "SyncRfQ ["
				+ "dateStart=" + dateStart
				+ ", dateEnd=" + dateEnd
				+ ", dateClose=" + dateClose
				//
				+ ", bpartner_uuid=" + bpartner_uuid
				//
				+ ", product=" + product
				+ ", qtyRequested=" + qtyRequested + " " + qtyCUInfo
				//
				+ ", currencyCode=" + currencyCode
				+ "]";
	}

	public Date getDateStart()
	{
		return dateStart;
	}

	public void setDateStart(final Date dateStart)
	{
		this.dateStart = dateStart;
	}

	public Date getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(final Date dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public String getBpartner_uuid()
	{
		return bpartner_uuid;
	}

	public void setBpartner_uuid(final String bpartner_uuid)
	{
		this.bpartner_uuid = bpartner_uuid;
	}

	public Date getDateClose()
	{
		return dateClose;
	}

	public void setDateClose(final Date dateClose)
	{
		this.dateClose = dateClose;
	}

	public SyncProduct getProduct()
	{
		return product;
	}

	public void setProduct(final SyncProduct product)
	{
		this.product = product;
	}

	public BigDecimal getQtyRequested()
	{
		return qtyRequested;
	}

	public void setQtyRequested(final BigDecimal qtyRequested)
	{
		this.qtyRequested = qtyRequested;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(final String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public String getQtyCUInfo()
	{
		return qtyCUInfo;
	}

	public void setQtyCUInfo(final String qtyCUInfo)
	{
		this.qtyCUInfo = qtyCUInfo;
	}
}
