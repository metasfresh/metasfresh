package de.metas.material.planning.interceptor;

import de.metas.material.planning.IResourceDAO;
import de.metas.product.IProductDAO;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_S_Resource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
@Interceptor(I_S_Resource.class)
@Component
public class S_Resource
{
	/**
	 * Creates a resource product.<br>
	 * Note that it's important to create it <b>after</b> new, because otherwise the given {@code resource}'s {@code Value} mit still be {@code null} (https://github.com/metasfresh/metasfresh/issues/1580)
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createResourceProduct(final I_S_Resource resource)
	{
		Services.get(IResourceDAO.class).onResourceChanged(resource);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void updateResourceProduct(final I_S_Resource resource)
	{
		Services.get(IResourceDAO.class).onResourceChanged(resource);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteResourceProduct(final I_S_Resource resource)
	{
		final ResourceId resourceId = ResourceId.ofRepoId(resource.getS_Resource_ID());
		Services.get(IProductDAO.class).deleteProductByResourceId(resourceId);
	}
}
