package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



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
public class Rfq extends AbstractSyncConfirmAwareEntity
{
	/* package */static final String TABLE_NAME = "rfq";

	@NonNull
	private java.sql.Date dateStart;
	@NonNull
	private java.sql.Date dateEnd;

	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	@Getter
	private BPartner bpartner;

	@NonNull
	private java.sql.Date dateClose;

	@Getter
	private boolean closed;

	@Getter
	@Setter
	private boolean winnerKnown;

	@Setter
	private boolean winner;

	@Getter
	@Setter
	private long product_id;

	@NonNull
	@Getter
	@Setter
	private BigDecimal qtyRequested = BigDecimal.ZERO;

	@NonNull
	@Getter
	@Setter
	private String qtyCUInfo;

	@NonNull
	@Getter
	private BigDecimal pricePromised = BigDecimal.ZERO;

	@NonNull
	@Getter
	private BigDecimal pricePromisedUserEntered = BigDecimal.ZERO;

	@NonNull
	@Getter
	@Setter
	private String currencyCode;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "rfq", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<RfqQty> quantities = new ArrayList<>();

	@Getter
	private boolean confirmedByUser = true;

	protected Rfq() {}

	@Builder
	private Rfq(@NonNull final BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

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
				.add("product_id", product_id)
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

	public LocalDate getDateStart()
	{
		return DateUtils.toLocalDate(dateStart);
	}

	public void setDateStart(final LocalDate dateStart)
	{
		this.dateStart = DateUtils.toSqlDate(dateStart);
	}

	public LocalDate getDateEnd()
	{
		return DateUtils.toLocalDate(dateEnd);
	}

	public void setDateEnd(final LocalDate dateEnd)
	{
		this.dateEnd = DateUtils.toSqlDate(dateEnd);
	}

	public Long getBpartnerId()
	{
		return getBpartner().getId();
	}

	public LocalDate getDateClose()
	{
		return DateUtils.toLocalDate(dateClose);
	}

	public void setDateClose(final LocalDate dateClose)
	{
		this.dateClose = DateUtils.toSqlDate(dateClose);
	}

	public List<RfqQty> getQuantities()
	{
		return quantities;
	}

	public ImmutableSet<LocalDate> generateAllDaysSet()
	{
		final ArrayList<LocalDate> dates = DateUtils.getDaysList(getDateStart(), getDateEnd());
		dates.addAll(quantities
				.stream()
				.map(RfqQty::getDatePromised)
				.collect(ImmutableSet.toImmutableSet()));

		dates.sort(LocalDate::compareTo);

		return ImmutableSet.copyOf(dates);
	}

	public void setPricePromisedUserEntered(@NonNull final BigDecimal pricePromisedUserEntered)
	{
		this.pricePromisedUserEntered = pricePromisedUserEntered;
	}

	public BigDecimal getQtyPromisedUserEntered()
	{
		return quantities.stream()
				.map(RfqQty::getQtyPromisedUserEntered)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setQtyPromised(@NonNull final LocalDate date, @NonNull final BigDecimal qtyPromised)
	{
		final RfqQty rfqQty = getOrCreateQty(date);
		rfqQty.setQtyPromisedUserEntered(qtyPromised);
		updateConfirmedByUser();
	}

	private RfqQty getOrCreateQty(@NonNull final LocalDate date)
	{
		final RfqQty existingRfqQty = getRfqQtyByDate(date);
		if (existingRfqQty != null)
		{
			return existingRfqQty;
		}
		else
		{
			final RfqQty rfqQty = RfqQty.builder()
					.rfq(this)
					.datePromised(date)
					.build();
			addRfqQty(rfqQty);
			return rfqQty;
		}
	}

	private void addRfqQty(final RfqQty rfqQty)
	{
		rfqQty.setRfq(this);
		quantities.add(rfqQty);
	}

	@Nullable
	public RfqQty getRfqQtyByDate(@NonNull final LocalDate date)
	{
		for (final RfqQty rfqQty : quantities)
		{
			if (date.equals(rfqQty.getDatePromised()))
			{
				return rfqQty;
			}
		}

		return null;
	}

	private void updateConfirmedByUser()
	{
		this.confirmedByUser = computeConfirmedByUser();
	}

	private boolean computeConfirmedByUser()
	{
		if (pricePromised.compareTo(pricePromisedUserEntered) != 0)
		{
			return false;
		}

		for (final RfqQty rfqQty : quantities)
		{
			if (!rfqQty.isConfirmedByUser())
			{
				return false;
			}
		}

		return true;
	}

	public void closeIt()
	{
		this.closed = true;
	}

	public void confirmByUser()
	{
		this.pricePromised = getPricePromisedUserEntered();
		quantities.forEach(RfqQty::confirmByUser);
		updateConfirmedByUser();
	}
}
