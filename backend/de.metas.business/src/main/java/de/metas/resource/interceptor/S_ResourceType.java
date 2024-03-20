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

package de.metas.resource.interceptor;

import de.metas.resource.ResourceService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Interceptor(I_S_ResourceType.class)
@Component
public class S_ResourceType
{
	private final ResourceService resourceService;

	public S_ResourceType(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_S_ResourceType.COLUMNNAME_IsTimeSlot, I_S_ResourceType.COLUMNNAME_TimeSlotStart, I_S_ResourceType.COLUMNNAME_TimeSlotEnd })
	public void validateTimeSlot(final I_S_ResourceType resourceType)
	{
		if (!resourceType.isTimeSlot())
		{
			return; // nothing to do
		}

		final Timestamp start = resourceType.getTimeSlotStart();
		if (start == null)
		{
			throw new FillMandatoryException(I_S_ResourceType.COLUMNNAME_TimeSlotStart);
		}
		final Timestamp end = resourceType.getTimeSlotEnd();
		if (end == null)
		{
			throw new FillMandatoryException(I_S_ResourceType.COLUMNNAME_TimeSlotEnd);
		}
		if (start.compareTo(end) >= 0)
		{
			throw new AdempiereException("@TimeSlotStart@ > @TimeSlotEnd@");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_S_ResourceType resourceType)
	{
		resourceService.onResourceTypeChanged(resourceType);
	}

}
