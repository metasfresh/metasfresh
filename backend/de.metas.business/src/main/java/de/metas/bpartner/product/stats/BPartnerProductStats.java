package de.metas.bpartner.product.stats;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.invoice.InvoiceId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * 
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Builder
@Getter
@ToString
public class BPartnerProductStats
{
	public static BPartnerProductStats newInstance(final BPartnerId bpartnerId, final ProductId productId)
	{
		return builder()
				.bpartnerId(bpartnerId)
				.productId(productId)
				.build();
	}

	@Setter(AccessLevel.PACKAGE)
	private int repoId;

	@NonNull
	private final BPartnerId bpartnerId;
	@NonNull
	private final ProductId productId;

	@Setter(AccessLevel.PACKAGE)
	private ZonedDateTime lastShipmentDate;

	@Setter(AccessLevel.PACKAGE)
	private ZonedDateTime lastReceiptDate;

	@Setter(AccessLevel.PACKAGE)
	private LastInvoiceInfo lastSalesInvoice;

	public Integer getLastReceiptInDays()
	{
		return calculateDaysFrom(getLastReceiptDate());
	}

	public Integer getLastShipmentInDays()
	{
		return calculateDaysFrom(getLastShipmentDate());
	}

	private static Integer calculateDaysFrom(final ZonedDateTime date)
	{
		if (date != null)
		{
			return (int)Duration.between(date, SystemTime.asZonedDateTime()).toDays();
		}
		else
		{
			return null;
		}
	}

	public void updateLastReceiptDate(@NonNull final ZonedDateTime receiptDate)
	{
		lastReceiptDate = max(lastReceiptDate, receiptDate);
	}

	public void updateLastShipmentDate(@NonNull final ZonedDateTime shipmentDate)
	{
		lastShipmentDate = max(lastShipmentDate, shipmentDate);
	}

	private static final ZonedDateTime max(final ZonedDateTime date1, final ZonedDateTime date2)
	{
		if (date1 == null)
		{
			return date2;
		}
		else if (date2 == null)
		{
			return date1;
		}
		else
		{
			return date1.isAfter(date2) ? date1 : date2;
		}
	}

	public void updateLastSalesInvoiceInfo(@NonNull final LastInvoiceInfo lastSalesInvoice)
	{
		if (lastSalesInvoice.isAfter(this.lastSalesInvoice))
		{
			this.lastSalesInvoice = lastSalesInvoice;
		}
	}

	@Value
	@Builder
	public static class LastInvoiceInfo
	{
		@NonNull
		private InvoiceId invoiceId;
		@NonNull
		private LocalDate invoiceDate;

		@NonNull
		private Money price;

		public boolean isAfter(@Nullable LastInvoiceInfo other)
		{
			if (other == null)
			{
				return true;
			}
			else if (this.invoiceDate.compareTo(other.invoiceDate) > 0)
			{
				return true;
			}
			else if (this.invoiceDate.compareTo(other.invoiceDate) == 0)
			{
				return this.invoiceId.getRepoId() > other.invoiceId.getRepoId();
			}
			else // if(this.invoiceDate.compareTo(other.invoiceDate) < 0)
			{
				return false;
			}
		}
	}
}
