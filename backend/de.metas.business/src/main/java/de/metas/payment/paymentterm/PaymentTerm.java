/*
 * #%L
 * de.metas.business
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

package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.BooleanWithReason;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

@Value
public class PaymentTerm
{
	@NonNull PaymentTermId id;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;

	@NonNull String value;
	@NonNull String name;
	@Nullable String description;

	@NonNull Percent discount;
	@NonNull Percent discount2;
	int discountDays;
	int discountDays2;
	@Nullable String netDay;
	int netDays;
	int graceDays;
	boolean isDefault;
	boolean isComplex;
	boolean isActive;

	@NonNull ImmutableList<PaymentTermBreak> sortedBreaks;
	@NonNull ImmutableMap<PaymentTermBreakId, PaymentTermBreak> breaksById;

	@NonNull ImmutableList<PaySchedule> paySchedules;

	@NonNull BooleanWithReason valid;

	@Builder
	private PaymentTerm(
			final @NonNull PaymentTermId id,
			final @NonNull ClientId clientId,
			final @NonNull OrgId orgId,
			final @NonNull String value,
			final @NonNull String name,
			final @Nullable String description,
			final @Nullable Percent discount,
			final @Nullable Percent discount2,
			final int discountDays,
			final int discountDays2,
			final @Nullable String netDay,
			final int netDays,
			final int graceDays,
			final boolean isDefault,
			final boolean isActive,
			final @NonNull List<PaymentTermBreak> breaks,
			final @NonNull List<PaySchedule> paySchedules)
	{
		this.id = id;
		this.orgId = orgId;
		this.clientId = clientId;
		this.value = value;
		this.name = name;
		this.description = description;
		this.discount = discount != null ? discount : Percent.ZERO;
		this.discount2 = discount2 != null ? discount2 : Percent.ZERO;
		this.netDay = netDay;
		this.discountDays = discountDays;
		this.discountDays2 = discountDays2;
		this.graceDays = graceDays;
		this.netDays = netDays;
		this.isDefault = isDefault;
		this.isActive = isActive;

		if (!breaks.isEmpty())
		{
			this.valid = validatePaymentTermWithBreaks(breaks);

			this.isComplex = true;
			this.sortedBreaks = breaks.stream()
					.sorted(Comparator.comparing(PaymentTermBreak::getSeqNo).thenComparing(PaymentTermBreak::getId))
					.collect(ImmutableList.toImmutableList());
			this.breaksById = Maps.uniqueIndex(sortedBreaks, PaymentTermBreak::getId);
			this.paySchedules = ImmutableList.of();
		}
		else
		{
			this.valid = validatePaymentTermWithSchedules(paySchedules);
			this.isComplex = false;
			this.sortedBreaks = ImmutableList.of();
			this.breaksById = ImmutableMap.of();
			this.paySchedules = ImmutableList.copyOf(paySchedules);
		}
	}

	private static BooleanWithReason validatePaymentTermWithBreaks(@NonNull final List<PaymentTermBreak> breaks)
	{
		final Percent totalPercent = breaks.stream().map(PaymentTermBreak::getPercent).reduce(Percent.ZERO, Percent::add);
		if (!totalPercent.isOneHundred())
		{
			return BooleanWithReason.falseBecause("Total percent must be exactly 100%, but it was: " + totalPercent);
		}

		return BooleanWithReason.TRUE;
	}

	private static BooleanWithReason validatePaymentTermWithSchedules(@NonNull final List<PaySchedule> schedules)
	{
		if (schedules.isEmpty())
		{
			return BooleanWithReason.TRUE;
		}
		else
		{
			final Percent totalPercent = schedules.stream().map(PaySchedule::getPercentage).reduce(Percent.ZERO, Percent::add);
			if (!totalPercent.isOneHundred())
			{
				return BooleanWithReason.falseBecause("Total percent must be exactly 100%, but it was: " + totalPercent);
			}

			return BooleanWithReason.TRUE;
		}
	}

	public boolean isValid() {return valid.isTrue();}

	public PaymentTermBreak getBreakById(final @NonNull PaymentTermBreakId id)
	{
		Check.assumeNotEmpty(breaksById, "Payment term does not support breaks: {}", this);

		final PaymentTermBreak paymentTermBreak = breaksById.get(id);
		if (paymentTermBreak == null)
		{
			throw new AdempiereException("No break found for id: " + id);
		}
		return paymentTermBreak;
	}
}

