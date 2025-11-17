/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExternalIdentifierProductLookupService
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	@NonNull
	public Optional<ProductAndHUPIItemProductId> resolveProductExternalIdentifierSingle(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (productIdentifier.getType())
		{
			case METASFRESH_ID:
				return ProductAndHUPIItemProductId.opt(ProductId.ofRepoId(productIdentifier.asMetasfreshId().getValue()));

			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgId, productIdentifier, ProductExternalReferenceType.PRODUCT)
						.map(JsonMetasfreshId::getValue)
						.map(ProductId::ofRepoId)
						.map(ProductAndHUPIItemProductId::of);

			case VALUE:
				final IProductDAO.ProductQuery query = IProductDAO.ProductQuery.builder()
						.value(productIdentifier.asValue())
						.orgId(orgId)
						.includeAnyOrg(true)
						.build();
				final ProductId productId = productDAO.retrieveProductIdBy(query);
				return ProductAndHUPIItemProductId.opt(productId);

			case GTIN:
				return lookupProductByGTIN(productIdentifier);

			default:
				throw new InvalidIdentifierException(productIdentifier.getRawValue());
		}
	}

	@VisibleForTesting
	@NonNull
	Optional<ProductAndHUPIItemProductId> lookupProductByGTIN(
			@NonNull final ExternalIdentifier productIdentifier)
	{
		final String gtin = productIdentifier.asGTIN();
		final ImmutableList<HUPIItemProduct> hupiItemProducts = Services.get(IHUPIItemProductDAO.class).retrieveByGTIN(GTIN.ofString(gtin));

		if (hupiItemProducts.size() > 1)
		{
			throw new InvalidIdentifierException(productIdentifier.getRawValue(), null, new DBMoreThanOneRecordsFoundException("More than one product found for GTIN: " + productIdentifier.asGTIN()));
		}
		if (hupiItemProducts.isEmpty())
		{
			return Optional.empty();
		}
		
		final HUPIItemProduct hupiItemProduct = hupiItemProducts.get(0);
		return ProductAndHUPIItemProductId.opt(hupiItemProduct.getProductId(), hupiItemProduct.getId());
	}
}
