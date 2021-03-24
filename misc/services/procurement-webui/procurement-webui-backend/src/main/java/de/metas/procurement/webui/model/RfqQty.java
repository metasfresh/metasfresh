package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;



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
@Table(name = RfqQty.TABLE_NAME,
		uniqueConstraints = @UniqueConstraint(name = "rfq_qty_uq", columnNames = { "rfq_id", "datePromised" })
)
public class RfqQty extends AbstractSyncConfirmAwareEntity
{
	/* package */static final String TABLE_NAME = "rfq_qty";

	@ManyToOne(fetch = FetchType.LAZY)
	@NonNull
	@Setter
	private Rfq rfq;

	@NonNull
	private java.sql.Date datePromised;

	@NonNull
	@Getter
	private BigDecimal qtyPromised = BigDecimal.ZERO;

	@NonNull
	@Getter
	private BigDecimal qtyPromisedUserEntered = BigDecimal.ZERO;

	protected RfqQty() {}

	@Builder
	private RfqQty(
			@NonNull final Rfq rfq,
			@NonNull final LocalDate datePromised)
	{
		this.rfq = rfq;
		this.datePromised = DateUtils.toSqlDate(datePromised);
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("datePromised", datePromised)
				.add("qtyPromisedUserEntered", qtyPromisedUserEntered)
				.add("qtyPromised", qtyPromised);
	}

	public LocalDate getDatePromised()
	{
		return DateUtils.toLocalDate(datePromised);
	}

	public void setQtyPromisedUserEntered(@NonNull final BigDecimal qtyPromisedUserEntered)
	{
		this.qtyPromisedUserEntered = qtyPromisedUserEntered;
	}

	public boolean isConfirmedByUser()
	{
		return getQtyPromised().compareTo(getQtyPromisedUserEntered()) == 0;
	}

	public void confirmByUser()
	{
		this.qtyPromised = getQtyPromisedUserEntered();
	}
}