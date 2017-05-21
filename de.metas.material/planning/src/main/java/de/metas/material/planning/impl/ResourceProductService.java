package de.metas.material.planning.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_M_Product;
import org.compiere.util.TimeUtil;

import com.jgoodies.common.base.Objects;

import de.metas.adempiere.util.CacheModel;
import de.metas.material.planning.IResourceProductService;
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
	public boolean setResourceToProduct(@NonNull final I_S_Resource resource, @NonNull final I_M_Product product)
	{
		boolean changed = false;
		if (!X_M_Product.PRODUCTTYPE_Resource.equals(product.getProductType()))
		{
			product.setProductType(X_M_Product.PRODUCTTYPE_Resource);
			changed = true;
		}
		if (resource.getS_Resource_ID() != product.getS_Resource_ID())
		{
			product.setS_Resource_ID(resource.getS_Resource_ID());
			changed = true;
		}
		if (resource.isActive() != product.isActive())
		{
			product.setIsActive(resource.isActive());
			changed = true;
		}

		// the "PR" is a QnD solution to the possible problem that if the production resource's value is set to its ID (like '1000000") there is probably already a product with the same value.
		if (!Objects.equals("PR" + resource.getValue(), product.getValue()))
		{
			product.setValue("PR" + resource.getValue());
			changed = true;
		}
		if (!resource.getName().equals(product.getName()))
		{
			product.setName(resource.getName());
			changed = true;
		}
		if (resource.getDescription() == null && product.getDescription() != null
				|| resource.getDescription() != null && !resource.getDescription().equals(product.getDescription()))
		{
			product.setDescription(resource.getDescription());
			changed = true;
		}
		//
		return changed;
	}	// setResource

	/**
	 * Set Resource Type
	 *
	 * @param parent resource type
	 * @return true if changed
	 */
	@Override
	public boolean setResourceTypeToProduct(final I_S_ResourceType parent, final I_M_Product product)
	{
		boolean changed = false;
		if (X_M_Product.PRODUCTTYPE_Resource.equals(product.getProductType()))
		{
			product.setProductType(X_M_Product.PRODUCTTYPE_Resource);
			changed = true;
		}
		//
		if (parent.getC_UOM_ID() != product.getC_UOM_ID())
		{
			product.setC_UOM_ID(parent.getC_UOM_ID());
			changed = true;
		}
		if (parent.getM_Product_Category_ID() != product.getM_Product_Category_ID())
		{
			product.setM_Product_Category_ID(parent.getM_Product_Category_ID());
			changed = true;
		}

		//
		// metas 05129 end
		return changed;
	}	// setResource

	@Override
	@Cached(cacheName = I_M_Product.Table_Name + "#by#" + I_M_Product.COLUMNNAME_S_Resource_ID)
	public I_M_Product retrieveProductForResource(@CacheModel final I_S_Resource resource)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class, resource)
				.addEqualsFilter(I_M_Product.COLUMN_S_Resource_ID, resource.getS_Resource_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.firstOnly(I_M_Product.class);
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
}
