package de.metas.handlingunits.expiry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.OptionalInt;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.util.Env;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
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
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final LocalDate today;

	@Builder
	public UpdateMonthsUntilExpiryCommand(
			@NonNull final HUWithExpiryDatesRepository huWithExpiryDatesRepository,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			//
			@NonNull final LocalDate today)
	{
		this.huWithExpiryDatesRepository = huWithExpiryDatesRepository;
		this.handlingUnitsBL = handlingUnitsBL;

		this.today = today;
	}

	public static class UpdateMonthsUntilExpiryCommandBuilder
	{
		public UpdateMonthsUntilExpiryResult execute()
		{
			return build().execute();
		}
	}

	public UpdateMonthsUntilExpiryResult execute()
	{
		int countChecked = 0;
		int countUpdated = 0;

		final Iterator<HuId> husWithExpiryDates = huWithExpiryDatesRepository.getAllWithBestBeforeDate();
		while (husWithExpiryDates.hasNext())
		{
			final HuId huId = husWithExpiryDates.next();
			countChecked++;

			final boolean updated = updateTopLevelHU(huId);
			if (updated)
			{
				countUpdated++;
			}
		}

		return UpdateMonthsUntilExpiryResult.builder()
				.countChecked(countChecked)
				.countUpdated(countUpdated)
				.build();
	}

	private boolean updateTopLevelHU(@NonNull final HuId topLevelHUId)
	{
		final ClientAndOrgId clientAndOrgId = handlingUnitsDAO.getClientAndOrgId(topLevelHUId);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(Env.getCtx(), clientAndOrgId);
		final IAttributeStorage huAttributes = getHUAttributes(topLevelHUId, huContext);
		return updateRecursive(huAttributes);
	}

	private boolean updateRecursive(@NonNull final IAttributeStorage huAttributes)
	{
		boolean updated = update(huAttributes);

		for (final IAttributeStorage childHUAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (updateRecursive(childHUAttributes))
			{
				updated = true;
			}
		}

		return updated;
	}

	private boolean update(@NonNull final IAttributeStorage huAttributes)
	{
		if (!huAttributes.hasAttribute(AttributeConstants.ATTR_MonthsUntilExpiry))
		{
			return false;
		}

		final OptionalInt monthsUntilExpiry = computeMonthsUntilExpiry(huAttributes, today);
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

	private IAttributeStorage getHUAttributes(@NonNull final HuId huId, @NonNull final IHUContext huContext)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		return huContext
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
	}
}
