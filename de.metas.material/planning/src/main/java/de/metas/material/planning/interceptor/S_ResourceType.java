package de.metas.material.planning.interceptor;

import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.ModelValidator;

import de.metas.material.planning.IResourceProductService;

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
@Interceptor(I_S_ResourceType.class)
public class S_ResourceType
{
	static final S_ResourceType INSTANCE = new S_ResourceType();

	private S_ResourceType()
	{
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_S_ResourceType.COLUMNNAME_IsTimeSlot,
			I_S_ResourceType.COLUMNNAME_TimeSlotStart,
			I_S_ResourceType.COLUMNNAME_TimeSlotEnd
	})
	public void validateStartEndDate(final I_S_ResourceType resourceType)
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
	public void updateProducts(final I_S_ResourceType resourceType)
	{
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);

		Services.get(IQueryBL.class).createQueryBuilder(I_S_Resource.class, resourceType)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Resource.COLUMNNAME_S_ResourceType_ID, resourceType.getS_ResourceType_ID())
				.create()
				.list().forEach(r -> {

					final I_M_Product product = resourceProductService.retrieveProductForResource(r);
					resourceProductService.setResourceTypeToProduct(resourceType, product);
				});
	}

}
