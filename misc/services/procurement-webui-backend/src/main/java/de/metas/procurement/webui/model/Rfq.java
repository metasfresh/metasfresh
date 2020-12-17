package de.metas.procurement.webui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import lombok.NonNull;



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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Entity
@Table(name = Rfq.TABLE_NAME)
@SuppressWarnings("serial")
public class Rfq extends AbstractSyncConfirmAwareEntity
{
	/* package */static final String TABLE_NAME = "rfq";

	@NonNull
	private Date dateStart;
	@NonNull
	private Date dateEnd;

	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	private BPartner bpartner;

	@NonNull
	private Date dateClose;
	private boolean closed;
	private boolean winnerKnown;
	private boolean winner;

	@ManyToOne
	@NonNull
	private Product product;

	@NonNull
	private BigDecimal qtyRequested = BigDecimal.ZERO;
	@NonNull
	private String qtyCUInfo;

	@NonNull
	private BigDecimal pricePromised = BigDecimal.ZERO;
	@NonNull
	private String currencyCode;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private final List<RfqQty> quantities = new ArrayList<>();




	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.omitNullValues()
				.add("dateStart", dateStart)
				.add("dateEnd", dateEnd)
				//
				.add("bpartner", bpartner)
				//
				.add("dateClosed", dateClose)
				.add("closed", closed)
				.add("winnerKnown", winnerKnown)
				.add("winner", winner)
				//
				.add("product", product)
				//
				.add("qtyRequested", qtyRequested)
				.add("CU Info", qtyCUInfo)
				//
				.add("quantities", quantities)
				//
				.add("pricePromised", pricePromised)
				.add("currency", currencyCode)
				//
				;
		
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

	public BPartner getBpartner()
	{
		return bpartner;
	}

	public void setBpartner(final BPartner bpartner)
	{
		this.bpartner = bpartner;
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
	
	public boolean isWinnerKnown()
	{
		return winnerKnown;
	}
	
	public void setWinnerKnown(boolean winnerKnown)
	{
		this.winnerKnown = winnerKnown;
	}

	public boolean isWinner()
	{
		return winner;
	}

	public void setWinner(final boolean winner)
	{
		this.winner = winner;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(final Product product)
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

	public BigDecimal getPricePromised()
	{
		return pricePromised;
	}

	public void setPricePromised(final BigDecimal pricePromised)
	{
		this.pricePromised = pricePromised;
	}

	public List<RfqQty> getQuantities()
	{
		return quantities;
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

	public void setQtyCUInfo(String qtyCUInfo)
	{
		this.qtyCUInfo = qtyCUInfo;
	}
}
