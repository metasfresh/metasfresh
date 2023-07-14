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

import com.google.common.collect.ImmutableList;
import de.metas.common.product.v2.request.JsonRequestProductWarehouseAssignmentCreate;
import de.metas.common.product.v2.request.JsonRequestProductWarehouseAssignmentCreateItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.warehouse.WarehouseService;
import de.metas.warehouseassignment.model.ProductWarehouseAssignmentCreateRequest;
import de.metas.warehouseassignment.repository.ProductWarehouseAssignmentRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class ProductWarehouseAssignmentRestService
{
	private static final Logger logger = LogManager.getLogger(ProductWarehouseAssignmentRestService.class);

	private final WarehouseService warehouseService;
	private final ProductWarehouseAssignmentRepository productWarehouseAssignmentRepository;
	private final JsonRetrieverService jsonRetrieverService;

	public ProductWarehouseAssignmentRestService(
			@NonNull final WarehouseService warehouseService,
			@NonNull final ProductWarehouseAssignmentRepository productWarehouseAssignmentRepository,
			@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.warehouseService = warehouseService;
		this.productWarehouseAssignmentRepository = productWarehouseAssignmentRepository;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
	}

	public void createOrReplaceProductWarehouseAssignments(
			@Nullable final JsonRequestProductWarehouseAssignmentCreate jsonRequestProductWarehouseAssignmentCreate,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		if (jsonRequestProductWarehouseAssignmentCreate != null)
		{
			final SyncAdvise effectiveSyncAdvise = jsonRequestProductWarehouseAssignmentCreate.getSyncAdvise();

			if (effectiveSyncAdvise.getIfExists().isReplace())
			{
				final int noOfDeletedRecord = deleteForProductId(productId);
				logger.debug("Deleted warehouse assignment records: " + noOfDeletedRecord);

				create(ProductWarehouseAssignmentCreateRequest.builder()
							   .productId(productId)
							   .warehouseIds(getWarehouseIds(orgId, jsonRequestProductWarehouseAssignmentCreate.getRequestItems()))
							   .build());
			}
			else
			{
				throw new AdempiereException("Warehouse assignments only support REPLACE SyncAdvise !");
			}
		}
	}

	@NonNull
	private ImmutableList<WarehouseId> getWarehouseIds(
			@NonNull final OrgId orgId,
			@NonNull final List<JsonRequestProductWarehouseAssignmentCreateItem> requestItems)
	{
		final ImmutableList.Builder<WarehouseId> warehouseIdBuilder = ImmutableList.builder();

		for (final JsonRequestProductWarehouseAssignmentCreateItem requestItem : requestItems)
		{
			if (requestItem.getWarehouseIdentifier() != null)
			{
				final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestItem.getWarehouseIdentifier());

				jsonRetrieverService.resolveWarehouseExternalIdentifier(externalIdentifier, orgId)
						.ifPresent(warehouseIdBuilder::add);
			}
			else if (requestItem.getName() != null)
			{
				warehouseService.getWarehouseByName(orgId, requestItem.getName())
						.ifPresent(warehouseIdBuilder::add);
			}
			else
			{
				throw new AdempiereException("At least one of warehouseIdentifier or name has to be specified.");
			}
		}

		return warehouseIdBuilder.build();
	}

	private int deleteForProductId(@NonNull final ProductId productId)
	{
		return productWarehouseAssignmentRepository.deleteForProductId(productId);
	}

	private void create(@NonNull final ProductWarehouseAssignmentCreateRequest request)
	{
		productWarehouseAssignmentRepository.create(request);
	}
}
