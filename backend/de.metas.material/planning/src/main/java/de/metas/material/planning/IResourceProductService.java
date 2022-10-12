package de.metas.material.planning;

import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.resource.ResourceType;
import de.metas.resource.ResourceTypeId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;

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

public interface IResourceProductService extends ISingletonService
{
	ResourceType getResourceTypeById(final ResourceTypeId resourceTypeId);

	ResourceType getResourceTypeByResourceId(ResourceId resourceId);

	I_M_Product getProductByResourceId(ResourceId resourceId);

	ProductId getProductIdByResourceId(ResourceId resourceId);

	I_S_Resource getById(ResourceId resourceId);
}
