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
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExternalIdentifierProductLookupService
{
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);

	@NonNull private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	@NonNull
	public Optional<ProductAndHUPIItemProductId> resolveProductExternalIdentifier(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId,
			@Nullable final ZonedDateTime date)
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
				return lookupProductByGTIN(productIdentifier, date);
			default:
				throw new InvalidIdentifierException(productIdentifier.getRawValue());
		}
	}

	@VisibleForTesting
	@NonNull
	Optional<ProductAndHUPIItemProductId> lookupProductByGTIN(
			@NonNull final ExternalIdentifier productIdentifier,
			@Nullable final ZonedDateTime date)
	{
		final String gtin = productIdentifier.asGTIN();

		// Branch 1: M_HU_PI_Item_Product — try validity-filtered first, then unfiltered fallback.
		// Primary query: respect validity (ValidFrom <= date AND (ValidTo >= date OR ValidTo IS NULL)).
		final Optional<I_M_HU_PI_Item_Product> hupiOpt = huPIItemProductDAO.findFirstByGtin(gtin, true, date);

		if (hupiOpt.isPresent())
		{
			final I_M_HU_PI_Item_Product hupi = hupiOpt.get();
			return ProductAndHUPIItemProductId.opt(
					ProductId.ofRepoId(hupi.getM_Product_ID()),
					HUPIItemProductId.ofRepoId(hupi.getM_HU_PI_Item_Product_ID()));
		}

		if (date != null)
		{
			// Fallback: no PIIP is valid on the date (e.g. the only row has ValidFrom in the future).
			// Resolve the product via the unfiltered query so the product is not lost, but do NOT
			// attach the out-of-window PIIP — applying a not-yet-valid packing instruction would be wrong.
			// The downstream caller will use virtual/No-Packing-Item in the absence of a PIIP.
			final Optional<I_M_HU_PI_Item_Product> fallbackHupiOpt = huPIItemProductDAO.findFirstByGtin(gtin, false, null);
			if (fallbackHupiOpt.isPresent())
			{
				return ProductAndHUPIItemProductId.opt(ProductId.ofRepoId(fallbackHupiOpt.get().getM_Product_ID()));
			}
		}

		// Branch 2: C_BPartner_Product — GTIN / EAN_CU / UPC match.
		final Optional<ProductId> bppProductIdOpt = bPartnerProductDAO.findFirstProductIdByGtin(gtin);
		if (bppProductIdOpt.isPresent())
		{
			return ProductAndHUPIItemProductId.opt(bppProductIdOpt.get());
		}

		// Branch 3: M_Product — GTIN / EAN13_ProductCode / UPC match.
		return productDAO.findFirstProductIdByGtin(gtin)
				.flatMap(ProductAndHUPIItemProductId::opt);
	}
}
