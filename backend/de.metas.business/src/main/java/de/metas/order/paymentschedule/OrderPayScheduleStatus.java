/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_OrderPaySchedule;

import javax.annotation.Nullable;
import java.util.Objects;

@AllArgsConstructor
public enum OrderPayScheduleStatus implements ReferenceListAwareEnum
{
	Pending(X_C_OrderPaySchedule.STATUS_Pending_Ref),
	Awaiting_Pay(X_C_OrderPaySchedule.STATUS_Awaiting_Pay),
	Paid(X_C_OrderPaySchedule.STATUS_Paid);

	private static final ImmutableSetMultimap<OrderPayScheduleStatus, OrderPayScheduleStatus> allowedTransitions = ImmutableSetMultimap.<OrderPayScheduleStatus, OrderPayScheduleStatus>builder()
			.put(Pending, Awaiting_Pay)
			.put(Awaiting_Pay, Paid)
			.build();

	@Getter @NonNull final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<OrderPayScheduleStatus> index = ReferenceListAwareEnums.index(values());

	public static OrderPayScheduleStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static OrderPayScheduleStatus ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	@Nullable
	public static String toCodeOrNull(final @Nullable OrderPayScheduleStatus status)
	{
		return status != null ? status.getCode() : null;
	}

	public boolean isPending() {return this == Pending;}

	public static boolean equals(@Nullable final OrderPayScheduleStatus status1, @Nullable final OrderPayScheduleStatus status2) {return Objects.equals(status1, status2);}

	public boolean isAllowTransitionTo(final @NonNull OrderPayScheduleStatus nextStatus)
	{
		return allowedTransitions.containsEntry(this, nextStatus);
	}
}
