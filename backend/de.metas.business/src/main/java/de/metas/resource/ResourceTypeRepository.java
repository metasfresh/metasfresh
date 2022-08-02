/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.resource;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_ResourceType;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.temporal.TemporalUnit;

@Repository
public class ResourceTypeRepository
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public ResourceType getById(@NonNull final ResourceTypeId resourceTypeId)
	{
		final I_S_ResourceType record = InterfaceWrapperHelper.loadOutOfTrx(resourceTypeId, I_S_ResourceType.class);
		return toResourceType(record);
	}

	ResourceType toResourceType(final I_S_ResourceType record)
	{
		final UomId durationUomId = UomId.ofRepoId(record.getC_UOM_ID());
		final TemporalUnit durationUnit = uomDAO.getTemporalUnitByUomId(durationUomId);

		return ResourceType.builder()
				.id(ResourceTypeId.ofRepoId(record.getS_ResourceType_ID()))
				.active(record.isActive())
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.durationUomId(durationUomId)
				.durationUnit(durationUnit)
				.availableDaysOfWeek(extractAvailableDaysOfWeek(record))
				.timeSlot(record.isTimeSlot())
				.timeSlotStart(TimeUtil.asLocalTime(record.getTimeSlotStart()))
				.timeSlotEnd(TimeUtil.asLocalTime(record.getTimeSlotEnd()))
				.build();
	}

	private static ImmutableSet<DayOfWeek> extractAvailableDaysOfWeek(@NonNull final I_S_ResourceType resourceType)
	{
		if (resourceType.isDateSlot())
		{
			final ImmutableSet.Builder<DayOfWeek> days = ImmutableSet.builder();
			if (resourceType.isOnMonday())
			{
				days.add(DayOfWeek.MONDAY);
			}
			if (resourceType.isOnTuesday())
			{
				days.add(DayOfWeek.TUESDAY);
			}
			if (resourceType.isOnWednesday())
			{
				days.add(DayOfWeek.WEDNESDAY);
			}
			if (resourceType.isOnThursday())
			{
				days.add(DayOfWeek.THURSDAY);
			}
			if (resourceType.isOnFriday())
			{
				days.add(DayOfWeek.FRIDAY);
			}
			if (resourceType.isOnSaturday())
			{
				days.add(DayOfWeek.SATURDAY);
			}
			if (resourceType.isOnSunday())
			{
				days.add(DayOfWeek.SUNDAY);
			}

			return days.build();
		}
		else
		{
			return ImmutableSet.copyOf(DayOfWeek.values());
		}
	}

}
