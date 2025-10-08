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
import de.metas.organization.OrgId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.stream.Stream;

@Value
public class PaymentTerm
{
	@NonNull PaymentTermId id;
	@NonNull OrgId orgId;
	@NonNull ClientId clientId;

	@NonNull String value;
	@NonNull String name;
	@Nullable String description;

	@Nullable Percent discount;
	@Nullable Percent discount2;
	@Nullable String netDay;

	int discountDays;
	int discountDays2;
	int graceDays;
	int netDays;
	boolean allowOverrideDueDate;
	boolean _default;
	boolean isComplex;

	@Nullable ImmutableList<PaymentTermBreak> breaks;

	@Builder
	private PaymentTerm(
			final @NonNull PaymentTermId id,
			final @NonNull OrgId orgId,
			final @NonNull ClientId clientId,
			final @NonNull String value,
			final @NonNull String name,
			final @Nullable String description,
			final @Nullable Percent discount,
			final @Nullable Percent discount2,
			final @Nullable String netDay,
			final int discountDays,
			final int discountDays2,
			final int graceDays,
			final int netDays,
			final boolean allowOverrideDueDate,
			final boolean _default,
			final boolean isComplex,
			final @NonNull ImmutableList<PaymentTermBreak> breaks)
	{
		this.id = id;
		this.orgId = orgId;
		this.clientId = clientId;
		this.value = value;
		this.name = name;
		this.description = description;
		this.discount = discount;
		this.discount2 = discount2;
		this.netDay = netDay;
		this.discountDays = discountDays;
		this.discountDays2 = discountDays2;
		this.graceDays = graceDays;
		this.netDays = netDays;
		this.allowOverrideDueDate = allowOverrideDueDate;
		this._default = _default;
		this.isComplex = isComplex;

		this.breaks = isComplex ? breaks : null;

	}

	public Stream<PaymentTermBreak> getSortedBreaks()
	{
		if (breaks == null || breaks.isEmpty())
		{
			return Stream.empty();
		}

		return breaks.stream()
				.sorted(Comparator.comparing(PaymentTermBreak::getSeqNo));
	}

}

