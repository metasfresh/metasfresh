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

import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_S_Resource_Group;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_Resource_Group.class)
@Component
public class S_Resource_Group
{
	private final ResourceService resourceService;

	public S_Resource_Group(
			@NonNull final ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	void afterSave(final I_S_Resource_Group record)
	{
		resourceService.onResourceGroupChanged(record);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	void beforeDelete(final I_S_Resource_Group record)
	{
		resourceService.onResourceGroupBeforeDelete(ResourceGroupId.ofRepoId(record.getS_Resource_Group_ID()));
	}
}
