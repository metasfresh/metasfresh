/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.product;

import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import java.util.List;

public interface IProductPlanningSchemaBL extends ISingletonService
{

	/**
	 * Create Product Planning entries for all the product that don't have any, based on the Product Planning Schema Selectors set in products and the schemas existing in the database.
	 * A product will have a product planning if it has the same selector as an existing schema.
	 *
	 * @return all the product planning entries that were created
	 */
	List<ProductPlanning> createDefaultProductPlanningsForAllProducts();

	/**
	 * Create product planning entries for all products with the same Product Planning Schema Selector as the given schema.
	 * In case something changed in the schema, update the product plannings that were already created.
	 * In case the schema selector was changed in the schema or the product with existing product plannings, delete the old plannings.
	 */
	List<ProductPlanning> createOrUpdateDefaultProductPlanningsForSchemaId(@NonNull ProductPlanningSchemaId schemaId);

	List<ProductPlanning> createOrUpdateProductPlanningsForSelector(@NonNull ProductId productId, final @NonNull OrgId orgId, @NonNull ProductPlanningSchemaSelector selector);
}
