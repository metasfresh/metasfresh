package de.metas.procurement.webui.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.google.gwt.thirdparty.guava.common.base.Objects.ToStringHelper;

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
@Table(name = RfqQty.TABLE_NAME //
, uniqueConstraints = @UniqueConstraint(name = "rfq_qty_uq", columnNames = { "rfq_id", "datePromised" })   //
)
@SuppressWarnings("serial")
public class RfqQty extends AbstractSyncConfirmAwareEntity
{
	/* package */static final String TABLE_NAME = "rfq_qty";

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private Rfq rfq;

	@NotNull
	private Date datePromised;

	@NotNull
	private BigDecimal qtyPromised;

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("datePromised", datePromised)
				.add("qtyPromised", qtyPromised);
	}

	public Rfq getRfq()
	{
		return rfq;
	}

	public void setRfq(final Rfq rfq)
	{
		this.rfq = rfq;
	}

	public Date getDatePromised()
	{
		return datePromised;
	}

	public void setDatePromised(final Date datePromised)
	{
		this.datePromised = datePromised;
	}

	public BigDecimal getQtyPromised()
	{
		return qtyPromised;
	}

	public void setQtyPromised(final BigDecimal qtyPromised)
	{
		this.qtyPromised = qtyPromised;
	}
}
