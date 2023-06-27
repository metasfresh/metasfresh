package de.metas.handlingunits.expiry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.OptionalInt;

import lombok.Getter;
import org.adempiere.mm.attributes.api.AttributeConstants;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import lombok.Builder;
import lombok.NonNull;

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

final class UpdateMonthsUntilExpiryCommand
{
	// services
	private final HUWithExpiryDatesRepository huWithExpiryDatesRepository;

	@Getter
	private final LocalDate today;

	@Builder
	public UpdateMonthsUntilExpiryCommand(@NonNull final HUWithExpiryDatesRepository huWithExpiryDatesRepository,
			@NonNull final LocalDate today)
	{
		this.huWithExpiryDatesRepository = huWithExpiryDatesRepository;
		this.today = today;
	}

	static OptionalInt computeMonthsUntilExpiry(@NonNull final IAttributeStorage huAttributes, @NonNull final LocalDate today)
	{
		final LocalDate bestBeforeDate = huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate);
		if (bestBeforeDate == null)
		{
			return OptionalInt.empty();
		}

		final int monthsUntilExpiry = (int)ChronoUnit.MONTHS.between(today, bestBeforeDate);
		return OptionalInt.of(monthsUntilExpiry);
	}

	public static class UpdateMonthsUntilExpiryCommandBuilder
	{
		public UpdateMonthsResult execute()
		{
			final UpdateMonthsUntilExpiryCommand cmd = build();

			final Iterator<HuId> huIdIterator = huWithExpiryDatesRepository.getAllWithBestBeforeDate();

			final UpdateAttributesHelper helper = new UpdateAttributesHelper();

			return helper.execute(huIdIterator, huAttributes -> {
				if (!huAttributes.hasAttribute(AttributeConstants.ATTR_MonthsUntilExpiry))
				{
					return false;
				}

				final OptionalInt monthsUntilExpiry = computeMonthsUntilExpiry(huAttributes, cmd.getToday());
				final int monthsUntilExpiryOld = huAttributes.getValueAsInt(AttributeConstants.ATTR_MonthsUntilExpiry);
				if (monthsUntilExpiry.orElse(0) == monthsUntilExpiryOld)
				{
					return false;
				}

				huAttributes.setSaveOnChange(true);

				if (monthsUntilExpiry.isPresent())
				{
					huAttributes.setValue(AttributeConstants.ATTR_MonthsUntilExpiry, monthsUntilExpiry.getAsInt());
				}
				else
				{
					huAttributes.setValue(AttributeConstants.ATTR_MonthsUntilExpiry, null);
				}

				huAttributes.saveChangesIfNeeded();

				return true;
			});
		}

	}
}
