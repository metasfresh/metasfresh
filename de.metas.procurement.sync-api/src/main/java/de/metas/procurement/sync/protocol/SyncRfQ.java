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

	private String bpartner_uuid;

	private Date dateClose;
	private boolean closed;
	private boolean winner;

	private String product_uuid;
	private BigDecimal qtyRequested;

	@Override
	public String toString()
	{
		return "SyncRfQ ["
				+ "dateStart=" + dateStart
				+ ", dateEnd=" + dateEnd
				//
				+ ", bpartner_uuid=" + bpartner_uuid
				//
				+ ", dateClose=" + dateClose
				+ ", closed=" + closed
				+ ", winner=" + winner
				//
				+ ", product_uuid=" + product_uuid
				+ ", qtyRequested=" + qtyRequested
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
	
	public void setBpartner_uuid(String bpartner_uuid)
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

	public boolean isClosed()
	{
		return closed;
	}

	public void setClosed(final boolean closed)
	{
		this.closed = closed;
	}

	public boolean isWinner()
	{
		return winner;
	}

	public void setWinner(final boolean winner)
	{
		this.winner = winner;
	}

	public String getProduct_uuid()
	{
		return product_uuid;
	}

	public void setProduct_uuid(final String product_uuid)
	{
		this.product_uuid = product_uuid;
	}

	public BigDecimal getQtyRequested()
	{
		return qtyRequested;
	}

	public void setQtyRequested(final BigDecimal qtyRequested)
	{
		this.qtyRequested = qtyRequested;
	}
}
