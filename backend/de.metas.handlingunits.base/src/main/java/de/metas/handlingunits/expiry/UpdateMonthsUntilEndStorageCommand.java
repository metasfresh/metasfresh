package de.metas.handlingunits.expiry;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.OptionalInt;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class UpdateMonthsUntilEndStorageCommand
{
	// services
	private final HUWithExpiryDatesRepository huWithExpiryDatesRepository;

	@Getter
	private final LocalDate today;

	@Builder
	public UpdateMonthsUntilEndStorageCommand(@NonNull final HUWithExpiryDatesRepository huWithExpiryDatesRepository,
											  @NonNull final LocalDate today)
	{
		this.huWithExpiryDatesRepository = huWithExpiryDatesRepository;
		this.today = today;
	}

	static OptionalInt computeMonthsUntilEndStorageDate(@NonNull final IAttributeStorage huAttributes, @NonNull final LocalDate today)
	{
		final LocalDate endStorageDate = huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_endStorageDate);
		if (endStorageDate == null)
		{
			return OptionalInt.empty();
		}

		final int monthsUntilEndStorageDate = (int)ChronoUnit.MONTHS.between(endStorageDate, today);
		return OptionalInt.of(monthsUntilEndStorageDate);
	}

	public static class UpdateMonthsUntilEndStorageCommandBuilder
	{
		public UpdateMonthsResult execute()
		{
			final UpdateMonthsUntilEndStorageCommand cmd = build();

			final Iterator<HuId> huIdIterator = huWithExpiryDatesRepository.getAllWithEndStorageDate();

			final UpdateAttributesHelper helper = new UpdateAttributesHelper();

			return helper.execute(huIdIterator, huAttributes -> {
				if (!huAttributes.hasAttribute(AttributeConstants.ATTR_MonthsUntilEndStorageDate))
				{
					return false;
				}

				final OptionalInt monthsUntilEndStorageDate = computeMonthsUntilEndStorageDate(huAttributes, cmd.getToday());
				final int monthsUntilEndStorageDateOld = huAttributes.getValueAsInt(AttributeConstants.ATTR_MonthsUntilEndStorageDate);
				if (monthsUntilEndStorageDate.orElse(0) == monthsUntilEndStorageDateOld)
				{
					return false;
				}

				huAttributes.setSaveOnChange(true);

				if (monthsUntilEndStorageDate.isPresent())
				{
					huAttributes.setValue(AttributeConstants.ATTR_MonthsUntilEndStorageDate, monthsUntilEndStorageDate.getAsInt());
				}
				else
				{
					huAttributes.setValue(AttributeConstants.ATTR_MonthsUntilEndStorageDate, null);
				}

				huAttributes.saveChangesIfNeeded();

				return true;

			});
		}
	}
}
