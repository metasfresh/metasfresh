package de.metas.material.planning.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_ResourceType;
import org.compiere.util.TimeUtil;

import de.metas.material.planning.IResourceProductService;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class ResourceProductService implements IResourceProductService
{
	@Override
	public I_M_Product getProductByResourceId(@NonNull final ResourceId resourceId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		final ProductId productId = productsRepo.getProductIdByResourceId(resourceId);
		return productsRepo.getById(productId);
	}

	private ProductId getProductIdByResourceId(final ResourceId resourceId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		return productsRepo.getProductIdByResourceId(resourceId);
	}

	@Override
	public Timestamp getDayStartForResourceType(final I_S_ResourceType resourceType, final Timestamp date)
	{
		if (resourceType.isTimeSlot())
		{
			return TimeUtil.getDayBorder(date, resourceType.getTimeSlotStart(), false);
		}
		else
		{
			return TimeUtil.getDayBorder(date, null, false);
		}
	}

	@Override
	public Timestamp getDayEndForResourceType(final I_S_ResourceType resourceType, final Timestamp date)
	{
		if (resourceType.isTimeSlot())
		{
			return TimeUtil.getDayBorder(date, resourceType.getTimeSlotEnd(), true);
		}
		else
		{
			return TimeUtil.getDayBorder(date, null, true);
		}
	}

	public long getDayDurationMillisForResourceType(final I_S_ResourceType resourceType)
	{
		if (resourceType.isTimeSlot())
		{
			return resourceType.getTimeSlotEnd().getTime() - resourceType.getTimeSlotStart().getTime();
		}
		else
		{
			return 24 * 60 * 60 * 1000; // 24 hours
		}
	}

	@Override
	public int getTimeSlotHoursForResourceType(@NonNull final I_S_ResourceType resourceType)
	{
		long hours;
		if (resourceType.isTimeSlot())
		{
			hours = (resourceType.getTimeSlotEnd().getTime() - resourceType.getTimeSlotStart().getTime()) / (60 * 60 * 1000);
		}
		else
		{
			hours = 24;
		}
		return (int)hours;
	}

	@Override
	public int getAvailableDaysWeekForResourceType(@NonNull final I_S_ResourceType resourceType)
	{
		int availableDays = 0;
		if (resourceType.isDateSlot())
		{
			if (resourceType.isOnMonday())
			{
				availableDays += 1;
			}
			if (resourceType.isOnTuesday())
			{
				availableDays += 1;
			}
			if (resourceType.isOnWednesday())
			{
				availableDays += 1;
			}
			if (resourceType.isOnThursday())
			{
				availableDays += 1;
			}
			if (resourceType.isOnFriday())
			{
				availableDays += 1;
			}
			if (resourceType.isOnSaturday())
			{
				availableDays += 1;
			}
			if (resourceType.isOnSunday())
			{
				availableDays += 1;
			}
		}
		else
		{
			availableDays = 7;
		}
		return availableDays;
	}

	@Override
	public boolean isDayAvailableForResourceType(final I_S_ResourceType resourceType, final Timestamp dateTime)
	{
		if (!resourceType.isActive())
		{
			return false;
		}
		if (!resourceType.isDateSlot())
		{
			return true;
		}

		final GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(dateTime.getTime());

		boolean retValue = false;
		switch (gc.get(Calendar.DAY_OF_WEEK))
		{
			case Calendar.SUNDAY:
				retValue = resourceType.isOnSunday();
				break;

			case Calendar.MONDAY:
				retValue = resourceType.isOnMonday();
				break;

			case Calendar.TUESDAY:
				retValue = resourceType.isOnTuesday();
				break;

			case Calendar.WEDNESDAY:
				retValue = resourceType.isOnWednesday();
				break;

			case Calendar.THURSDAY:
				retValue = resourceType.isOnThursday();
				break;

			case Calendar.FRIDAY:
				retValue = resourceType.isOnFriday();
				break;

			case Calendar.SATURDAY:
				retValue = resourceType.isOnSaturday();
				break;
		}

		return retValue;
	}

	@Override
	public boolean isAvailableForResourceType(final I_S_ResourceType resourceType)
	{
		if (!resourceType.isActive())
		{
			return false;
		}
		return getAvailableDaysWeekForResourceType(resourceType) > 0
				&& getTimeSlotHoursForResourceType(resourceType) > 0;
	}

	@Override
	public I_C_UOM getResourceUOM(@NonNull final ResourceId resourceId)
	{
		final ProductId productId = getProductIdByResourceId(resourceId);
		return Services.get(IProductBL.class).getStockingUOM(productId);
	}
}
