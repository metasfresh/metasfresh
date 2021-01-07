package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
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
@Table(name = RfqQty.TABLE_NAME //
		, uniqueConstraints = @UniqueConstraint(name = "rfq_qty_uq", columnNames = { "rfq_id", "datePromised" })   //
)
public class RfqQty extends AbstractSyncConfirmAwareEntity
{
	/* package */static final String TABLE_NAME = "rfq_qty";

	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	@Getter
	private Rfq rfq;

	@NonNull
	private java.sql.Date datePromised;

	@NonNull
	@Getter
	@Setter
	private BigDecimal qtyPromised;

	protected RfqQty() {}

	@Builder
	private RfqQty(
			@NonNull final Rfq rfq,
			@NonNull final LocalDate datePromised,
			@Nullable final BigDecimal qtyPromised)
	{
		this.rfq = rfq;
		this.datePromised = DateUtils.toSqlDate(datePromised);
		this.qtyPromised = qtyPromised;
		this.qtyPromised = qtyPromised != null ? qtyPromised : BigDecimal.ZERO;
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("datePromised", datePromised)
				.add("qtyPromised", qtyPromised);
	}

	public LocalDate getDatePromised()
	{
		return DateUtils.toLocalDate(datePromised);
	}
}
