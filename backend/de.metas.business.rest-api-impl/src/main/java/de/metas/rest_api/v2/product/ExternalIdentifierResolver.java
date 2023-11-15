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

package de.metas.rest_api.v2.product;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalreference.warehouse.WarehouseExternalReferenceType;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExternalIdentifierResolver
{
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final ExternalReferenceRestControllerService externalReferenceService;

	public ExternalIdentifierResolver(@NonNull final ExternalReferenceRestControllerService externalReferenceService)
	{
		this.externalReferenceService = externalReferenceService;
	}

	@NonNull
	public Optional<ProductId> resolveProductExternalIdentifier(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (productIdentifier.getType())
		{
			case METASFRESH_ID:
				return Optional.of(ProductId.ofRepoId(productIdentifier.asMetasfreshId().getValue()));

			case EXTERNAL_REFERENCE:
				return externalReferenceService
						.getJsonMetasfreshIdFromExternalReference(orgId, productIdentifier, ProductExternalReferenceType.PRODUCT)
						.map(JsonMetasfreshId::getValue)
						.map(ProductId::ofRepoId);

			case VALUE:
				final IProductDAO.ProductQuery query = IProductDAO.ProductQuery.builder()
						.value(productIdentifier.asValue())
						.orgId(orgId)
						.includeAnyOrg(true)
						.build();

				return Optional.ofNullable(productsRepo.retrieveProductIdBy(query));
			default:
				throw new InvalidIdentifierException(productIdentifier.getRawValue());
		}
	}

	@NonNull
	public Optional<WarehouseId> resolveWarehouseExternalIdentifier(
			@NonNull final ExternalIdentifier warehouseExternalIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (warehouseExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseExternalIdentifier.asMetasfreshId().getValue());
				return Optional.of(warehouseId);
			case EXTERNAL_REFERENCE:
				return externalReferenceService.getJsonMetasfreshIdFromExternalReference(orgId, warehouseExternalIdentifier, WarehouseExternalReferenceType.WAREHOUSE)
						.map(JsonMetasfreshId::getValue)
						.map(WarehouseId::ofRepoId);
			case VALUE:
				final IWarehouseDAO.WarehouseQuery valQuery = IWarehouseDAO.WarehouseQuery.builder()
						.orgId(orgId)
						.value(warehouseExternalIdentifier.asValue())
						.build();
				return Optional.ofNullable(warehouseDAO.retrieveWarehouseIdBy(valQuery));
			case NAME:
				final IWarehouseDAO.WarehouseQuery nameQuery = IWarehouseDAO.WarehouseQuery.builder()
						.orgId(orgId)
						.name(warehouseExternalIdentifier.asName())
						.build();
				return Optional.ofNullable(warehouseDAO.retrieveWarehouseIdBy(nameQuery));
			default:
				throw new InvalidIdentifierException("Given external identifier type is not supported!")
						.setParameter("externalIdentifierType", warehouseExternalIdentifier.getType())
						.setParameter("rawExternalIdentifier", warehouseExternalIdentifier.getRawValue());
		}
	}

}
