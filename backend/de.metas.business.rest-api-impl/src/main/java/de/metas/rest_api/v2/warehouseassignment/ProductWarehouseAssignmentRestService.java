/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.warehouseassignment;

import com.google.common.collect.ImmutableSet;
import de.metas.common.product.v2.request.JsonRequestProductWarehouseAssignmentSave;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.product.ExternalIdentifierResolver;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.warehouseassignment.ProductWarehouseAssignmentRepository;
import de.metas.warehouseassignment.ProductWarehouseAssignments;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class ProductWarehouseAssignmentRestService
{
	private final ProductWarehouseAssignmentRepository productWarehouseAssignmentRepository;
	private final ExternalIdentifierResolver externalIdentifierResolver;

	public ProductWarehouseAssignmentRestService(
			@NonNull final ProductWarehouseAssignmentRepository productWarehouseAssignmentRepository,
			@NonNull final ExternalIdentifierResolver externalIdentifierResolver)
	{
		this.productWarehouseAssignmentRepository = productWarehouseAssignmentRepository;
		this.externalIdentifierResolver = externalIdentifierResolver;
	}

	public void processProductWarehouseAssignments(
			@Nullable final JsonRequestProductWarehouseAssignmentSave request,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		if (request == null)
		{
			return;
		}

		final SyncAdvise effectiveSyncAdvise = request.getSyncAdvise();

		final ImmutableSet<WarehouseId> warehouseIdsToAssign = getWarehouseIds(orgId, request);

		if (effectiveSyncAdvise.isFailIfNotExists() && !warehouseIdsToAssign.isEmpty())
		{
			final ProductWarehouseAssignments warehouseAssignments = productWarehouseAssignmentRepository.getByProductId(productId)
					.orElseThrow(() -> new AdempiereException("No assignments found for productId=" + productId.getRepoId()));

			final ImmutableSet<WarehouseId> missingWarehouseIds = warehouseIdsToAssign.stream()
					.filter(warehouseId -> !warehouseAssignments.isWarehouseAssigned(warehouseId))
					.collect(ImmutableSet.toImmutableSet());

			if (!missingWarehouseIds.isEmpty())
			{
				throw MissingResourceException.builder()
						.resourceName("M_Product_Warehouse")
						.resourceIdentifier("Missing WarehouseIds:" + missingWarehouseIds + ", m_product_id: " + productId.getRepoId())
						.build()
						.setParameter("effectiveSyncAdvise", effectiveSyncAdvise);
			}
		}

		if (effectiveSyncAdvise.getIfExists().isReplace())
		{
			productWarehouseAssignmentRepository.save(ProductWarehouseAssignments.builder()
															  .productId(productId)
															  .warehouseIds(warehouseIdsToAssign)
															  .build());
		}
		else
		{
			final ProductWarehouseAssignments assignments = productWarehouseAssignmentRepository.getByProductId(productId)
					.map(existingAssignments -> existingAssignments.addAssignments(warehouseIdsToAssign))
					.orElseGet(() -> ProductWarehouseAssignments.builder()
							.productId(productId)
							.warehouseIds(warehouseIdsToAssign)
							.build());

			productWarehouseAssignmentRepository.save(assignments);
		}
	}

	@NonNull
	private ImmutableSet<WarehouseId> getWarehouseIds(
			@NonNull final OrgId orgId,
			@NonNull final JsonRequestProductWarehouseAssignmentSave request)
	{
		final ImmutableSet.Builder<WarehouseId> warehouseIdBuilder = ImmutableSet.builder();

		request.getWarehouseIdentifiers()
				.stream()
				.map(ExternalIdentifier::of)
				.forEach(externalIdentifier -> {
					final WarehouseId warehouseId = externalIdentifierResolver.resolveWarehouseExternalIdentifier(externalIdentifier, orgId)
							.orElseThrow(() -> new AdempiereException("WarehouseIdentifier could not be found: " + externalIdentifier.getRawValue()));

					warehouseIdBuilder.add(warehouseId);
				});

		return warehouseIdBuilder.build();
	}
}
