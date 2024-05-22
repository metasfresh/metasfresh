/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interest;

import de.metas.contracts.modular.interest.log.CreateModularLogInterestRequest;
import de.metas.contracts.modular.interest.run.InterestRunId;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
public class InvoiceAllocations
{
	@NonNull private final InterestRunId interestRunId;
	@NonNull private final Integer additionalInterestDays;
	@NonNull @Getter private final ModularContractLogEntry invoiceEntry;

	@NonNull private final IOrgDAO orgDAO;

	@NonNull @Getter private Money openAmount;
	@Nullable private Instant cachedInvoiceInterimDate;
	@Getter @NonNull private final List<CreateModularLogInterestRequest> allocatedShippingNotifications = new ArrayList<>();

	public synchronized boolean canAllocate(@Nullable final AllocationItem shippingNotification)
	{
		if (shippingNotification == null)
		{
			return false;
		}

		if (shippingNotification.openAmountSignum() == 0)
		{
			return false;
		}

		return !isInvoiceCreatedAfter(shippingNotification);
	}

	public synchronized boolean isInvoiceCreatedAfter(@NonNull final AllocationItem shippingNotification)
	{
		return invoiceEntry.getTransactionDate().toInstant(orgDAO::getTimeZone)
				.isAfter(shippingNotification.getShippingNotificationEntry().getTransactionDate().toInstant(orgDAO::getTimeZone));
	}

	@NonNull
	public synchronized BigDecimal getAllocatedInterestScore()
	{
		return allocatedShippingNotifications.stream()
				.map(CreateModularLogInterestRequest::getInterestScore)
				.map(InterestScore::getScore)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	public synchronized AllocationItem allocate(@NonNull final AllocationItem shippingNotification)
	{
		if (openAmount.isZero())
		{
			return shippingNotification;
		}

		final CreateModularLogInterestRequest allocationRequest = getAllocationRequest(shippingNotification);
		allocatedShippingNotifications.add(allocationRequest);

		openAmount = openAmount.subtract(allocationRequest.getAllocatedAmt());
		Check.assume(openAmount.signum() >= 0, "OpenAmount cannot be negative!");

		return shippingNotification.subtractAllocatedAmount(allocationRequest.getAllocatedAmt());
	}

	public synchronized int openAmountSignum()
	{
		return openAmount.signum();
	}

	@NonNull
	private Money getAmountToAllocate(@NonNull final AllocationItem shippingNotification)
	{
		if (shippingNotification.openAmountSignum() == 0)
		{
			return Money.zero(openAmount.getCurrencyId());
		}

		return shippingNotification.getOpenAmount().min(openAmount);
	}

	@NonNull
	private CreateModularLogInterestRequest getAllocationRequest(@NonNull final AllocationItem shippingNotification)
	{
		return CreateModularLogInterestRequest.builder()
				.interestRunId(interestRunId)
				.shippingNotificationLogId(shippingNotification.getShippingNotificationEntry().getId())
				.interimInvoiceLogId(invoiceEntry.getId())
				.allocatedAmt(getAmountToAllocate(shippingNotification))
				.interestDays(getInterestDays(shippingNotification))
				.build();
	}

	private int getInterestDays(@NonNull final AllocationItem shippingNotification)
	{
		final Instant shippingDate = shippingNotification
				.getShippingNotificationEntry()
				.getTransactionDate()
				.toInstant(orgDAO::getTimeZone);

		return TimeUtil.getDaysBetween(Date.from(getInterimDate()), Date.from(shippingDate)) + additionalInterestDays;
	}

	@NonNull
	private Instant getInterimDate()
	{
		if (cachedInvoiceInterimDate != null)
		{
			return cachedInvoiceInterimDate;
		}

		cachedInvoiceInterimDate = invoiceEntry.getTransactionDate().toInstant(orgDAO::getTimeZone);
		return cachedInvoiceInterimDate;
	}

	@Value
	public static class AllocationItem
	{
		@NonNull ModularContractLogEntry shippingNotificationEntry;
		@NonNull @With Money openAmount;

		@Builder(toBuilder = true)
		private AllocationItem(
				@NonNull final ModularContractLogEntry shippingNotificationEntry,
				@NonNull final Money openAmount)
		{
			Check.assume(openAmount.signum() >= 0, "OpenAmount cannot be negative!");

			this.shippingNotificationEntry = shippingNotificationEntry;
			this.openAmount = openAmount;
		}

		public AllocationItem subtractAllocatedAmount(@NonNull final Money allocatedAmt)
		{
			return toBuilder()
					.openAmount(openAmount.subtract(allocatedAmt))
					.build();
		}

		public int openAmountSignum()
		{
			return openAmount.signum();
		}
	}
}
