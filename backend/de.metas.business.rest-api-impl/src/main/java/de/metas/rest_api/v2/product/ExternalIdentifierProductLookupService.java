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
import de.metas.handlingunits.IHUPIItemProductDAO;
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
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExternalIdentifierProductLookupService
{
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);

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

		// Primary query: respect validity (ValidFrom <= date AND (ValidTo >= date OR ValidTo IS NULL)).
		// This selects the best PIIP that is actually valid on the requested date.
		I_M_HU_PI_Item_Product hupi = findFirstHupiByGtin(gtin, true, date);

		if (hupi == null && date != null)
		{
			// Fallback: no PIIP is valid on the date (e.g. the only row has ValidFrom in the future).
			// Resolve the product via the unfiltered query so the product is not lost, but do NOT
			// attach the out-of-window PIIP — applying a not-yet-valid packing instruction would be wrong.
			// The downstream caller will use virtual/No-Packing-Item in the absence of a PIIP.
			final I_M_HU_PI_Item_Product fallbackHupi = findFirstHupiByGtin(gtin, false, null);
			if (fallbackHupi != null)
			{
				return ProductAndHUPIItemProductId.opt(ProductId.ofRepoId(fallbackHupi.getM_Product_ID()));
			}
		}

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

	/**
	 * Finds the first {@link I_M_HU_PI_Item_Product} row matching the given GTIN/EAN/UPC value,
	 * ordered by {@code ValidFrom DESC} (most recent first) then by ID ascending as a tiebreak.
	 *
	 * @param gtin           the GTIN/EAN/UPC value to match
	 * @param applyValidity  when {@code true}, adds a "valid on date" filter so only rows whose
	 *                       ValidFrom/ValidTo window covers the given date are returned; when {@code false},
	 *                       no validity filter is applied (fallback to resolve the product even when no row
	 *                       is valid on the requested date)
	 * @param date           the date to use for validity filtering; used only when {@code applyValidity} is {@code true}
	 * @return the first matching row, or {@code null} if none found
	 */
	@Nullable
	private I_M_HU_PI_Item_Product findFirstHupiByGtin(
			@NonNull final String gtin,
			final boolean applyValidity,
			@Nullable final ZonedDateTime date)
	{
		final ICompositeQueryFilter<I_M_HU_PI_Item_Product> hupiFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
				.setJoinOr()
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_GTIN, gtin)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_EAN_TU, gtin)
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_UPC, gtin);

		final IQueryBuilder<I_M_HU_PI_Item_Product> builder = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addOnlyActiveRecordsFilter()
				.filter(hupiFilter)
				.addNotNull(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID);

		if (applyValidity)
		{
			builder.filter(huPIItemProductDAO.createValidOnDateFilter(date));
		}

		return builder.orderBy()
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom, Direction.Descending, Nulls.Last)
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, Direction.Ascending, Nulls.Last)
				.endOrderBy()
				.create()
				.first();
	}

}
