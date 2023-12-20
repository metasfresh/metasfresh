/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.warehouseassignment;

import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductWarehouseAssignmentService
{
	private static final String SYSCONFIG_ENFORE_WAREHOUSE_ASSIGNMENTS_FOR_PRODUCTS = //
			"de.metas.warehouseassignment.ProductWarehouseAssignmentService.EnforceWarehouseAssignmentsForProduct";

	private static final AdMessageKey PRODUCT_NO_WAREHOUSE_ASSIGNED = AdMessageKey.of("PRODUCT_NO_WAREHOUSE_ASSIGNED");

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final ProductWarehouseAssignmentRepository productWarehouseAssignmentRepository;

	public ProductWarehouseAssignmentService(@NonNull final ProductWarehouseAssignmentRepository productWarehouseAssignmentRepository)
	{
		this.productWarehouseAssignmentRepository = productWarehouseAssignmentRepository;
	}

	public boolean enforceWarehouseAssignmentsForProducts()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_ENFORE_WAREHOUSE_ASSIGNMENTS_FOR_PRODUCTS, false);
	}

	@NonNull
	public ProductWarehouseAssignments getByProductIdOrError(@NonNull final ProductId productId)
	{
		return productWarehouseAssignmentRepository.getByProductId(productId)
				.orElseThrow(() -> new AdempiereException(PRODUCT_NO_WAREHOUSE_ASSIGNED)
						.appendParametersToMessage()
						.setParameter("M_Product_ID", productId.getRepoId())
						.markAsUserValidationError());
	}

	@NonNull
	public Optional<ProductWarehouseAssignments> getByProductId(@NonNull final ProductId productId)
	{
		return productWarehouseAssignmentRepository.getByProductId(productId);
	}

	public void save(@NonNull final ProductWarehouseAssignments productWarehouseAssignment)
	{
		productWarehouseAssignmentRepository.save(productWarehouseAssignment);
	}
}
