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

import de.metas.product.ResourceId;
import de.metas.resource.ResourceService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_Resource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_Resource.class)
@Component
public class S_Resource
{
	private final ResourceService resourceService;

	public S_Resource(@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	void beforeSave(final I_S_Resource resourceRecord)
	{
		resourceService.validateResourceBeforeSave(resourceRecord);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	void afterSave(final I_S_Resource resourceRecord)
	{
		resourceService.onResourceChanged(resourceRecord);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	void beforeDelete(final I_S_Resource resourceRecord)
	{
		final ResourceId resourceId = ResourceId.ofRepoId(resourceRecord.getS_Resource_ID());
		resourceService.onResourceBeforeDelete(resourceId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_S_Resource.COLUMNNAME_CapacityPerProductionCycle, I_S_Resource.COLUMNNAME_CapacityPerProductionCycle_UOM_ID })
	public void validateCapacityUOMID(final I_S_Resource resource)
	{
		if (resource.getCapacityPerProductionCycle().signum() < 0)
		{
			throw new AdempiereException("CapacityPerProductionCycle cannot go below 0!")
					.appendParametersToMessage()
					.setParameter("S_Resource_ID", resource.getS_Resource_ID())
					.markAsUserValidationError();
		}

		if (resource.getCapacityPerProductionCycle().signum() > 0 && resource.getCapacityPerProductionCycle_UOM_ID() <= 0)
		{
			throw new AdempiereException("Unit of measurement for capacity per production cycle cannot be missing if capacity is provided!")
					.appendParametersToMessage()
					.setParameter("S_Resource_ID", resource.getS_Resource_ID())
					.markAsUserValidationError();
		}
	}
}
