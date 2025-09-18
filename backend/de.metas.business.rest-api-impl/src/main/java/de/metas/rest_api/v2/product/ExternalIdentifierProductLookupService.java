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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExternalIdentifierProductLookupService
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	@NonNull
	public Optional<ProductAndHUPIItemProductId> resolveProductExternalIdentifier(
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
		final ICompositeQueryFilter<I_M_HU_PI_Item_Product> hupiFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
				.setJoinOr()
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_GTIN, gtin)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_EAN_TU, gtin)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_UPC, gtin);

		final I_M_HU_PI_Item_Product hupi = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addOnlyActiveRecordsFilter()
				.filter(hupiFilter)
				.addNotNull(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID)
				.orderBy(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.create().first();
		if (hupi != null)
		{
			return ProductAndHUPIItemProductId.opt(
					ProductId.ofRepoId(hupi.getM_Product_ID()),
					HUPIItemProductId.ofRepoId(hupi.getM_HU_PI_Item_Product_ID()));
		}

		// TODO refactor this logic and use some BPartnerProductDAO methods
		final ICompositeQueryFilter<I_C_BPartner_Product> bppFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.setJoinOr()
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_GTIN, gtin)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_EAN_CU, gtin)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UPC, gtin);

		final I_C_BPartner_Product bpp = queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addOnlyActiveRecordsFilter()
				.filter(bppFilter)
				.addNotNull(I_C_BPartner_Product.COLUMNNAME_M_Product_ID)
				.orderBy(I_C_BPartner_Product.COLUMNNAME_C_BPartner_Product_ID)
				.create().first();
		if (bpp != null)
		{
			return ProductAndHUPIItemProductId.opt(ProductId.ofRepoId(bpp.getM_Product_ID()));
		}

		final ICompositeQueryFilter<I_M_Product> pFilter = queryBL.createCompositeQueryFilter(I_M_Product.class)
				.setJoinOr()
				.addEqualsFilter(I_M_Product.COLUMNNAME_GTIN, gtin)
				.addEqualsFilter(I_M_Product.COLUMNNAME_EAN13_ProductCode, gtin)
				.addEqualsFilter(I_M_Product.COLUMNNAME_UPC, gtin);
		final I_M_Product p = queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.filter(pFilter)
				.orderBy(I_M_Product.COLUMNNAME_M_Product_ID)
				.create().first();
		if (p != null)
		{
			return ProductAndHUPIItemProductId.opt(ProductId.ofRepoId(p.getM_Product_ID()));
		}
		return Optional.empty();
	}

}
