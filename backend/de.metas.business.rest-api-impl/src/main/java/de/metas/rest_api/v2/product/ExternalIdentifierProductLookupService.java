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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.ProductAndHUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExternalIdentifierProductLookupService
{
	private static final String GTIN_UNKNOWN_TEXT_FORMAT = "No active packing instruction (M_HU_PI_Item_Product) of an active product for the ordering business partner or without business partner,"
			+ " no active partner product (C_BPartner_Product) and no active product (M_Product) carries GTIN %s.";
	private static final String GTIN_NOT_VALID_ON_DATE_TEXT_FORMAT = "GTIN %s is only on packing instructions that are not valid on the delivery date %s: %s.";

	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);

	@NonNull private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	@VisibleForTesting
	public static ExternalIdentifierProductLookupService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();

		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ExternalIdentifierProductLookupService.class,
				() -> new ExternalIdentifierProductLookupService(ExternalReferenceRestControllerService.newInstanceForUnitTesting()));
	}

	@NonNull
	public Optional<ProductAndHUPIItemProductId> resolveProductExternalIdentifier(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId)
	{
		return resolveProductExternalIdentifier(productIdentifier, orgId, null, null);
	}

	@NonNull
	public Optional<ProductAndHUPIItemProductId> resolveProductExternalIdentifier(
			@NonNull final ExternalIdentifier productIdentifier,
			@NonNull final OrgId orgId,
			@Nullable final ZonedDateTime date,
			@Nullable final BPartnerId bpartnerId)
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
				return lookupProductByGTIN(productIdentifier, date, bpartnerId);
			default:
				throw new InvalidIdentifierException(productIdentifier.getRawValue());
		}
	}

	@VisibleForTesting
	@NonNull
	Optional<ProductAndHUPIItemProductId> lookupProductByGTIN(
			@NonNull final ExternalIdentifier productIdentifier,
			@Nullable final ZonedDateTime date,
			@Nullable final BPartnerId bpartnerId)
	{
		final GTIN gtin = GTIN.ofString(productIdentifier.asGTIN());

		// Branch 1: M_HU_PI_Item_Product — scoped to the given partner (and partner-less rows) if bpartnerId is set; validity-filtered (ValidFrom <= date AND (ValidTo >= date OR ValidTo IS NULL)).
		// If no valid row exists for the given date, falls through to branch 2.
		final Optional<ProductAndHUPIItemProductId> hupiOpt = huPIItemProductDAO.findFirstByGtin(gtin, bpartnerId, date);
		if (hupiOpt.isPresent())
		{
			return hupiOpt;
		}

		// Branch 2: C_BPartner_Product — GTIN / EAN_CU / UPC match.
		final Optional<ProductId> bppProductIdOpt = bPartnerProductDAO.findFirstProductIdByGtin(gtin);

		// Branch 3: M_Product — GTIN / EAN13_ProductCode / UPC match.
		return bppProductIdOpt.map(ProductAndHUPIItemProductId::opt).orElseGet(() -> productDAO.findFirstProductIdByGtin(gtin)
				.flatMap(ProductAndHUPIItemProductId::opt));
	}

	/**
	 * Call only after {@link #lookupProductByGTIN} returned empty.
	 * Validity dates in the text are shown as calendar dates (yyyy-MM-dd) while validity is checked on the full timestamp,
	 * so a row valid from later the same day can read "valid from <same date>".
	 */
	@NonNull
	public String explainUnresolvedGTIN(
			@NonNull final ExternalIdentifier gtinIdentifier,
			@Nullable final ZonedDateTime date,
			@Nullable final BPartnerId bpartnerId)
	{
		final GTIN gtin = GTIN.ofString(gtinIdentifier.asGTIN());
		final List<I_M_HU_PI_Item_Product> piips = huPIItemProductDAO.retrieveByGtinIgnoringDate(gtin, bpartnerId);
		// date is null when the order candidate request has no dateRequired (JsonConverters)
		if (piips.isEmpty() || date == null)
		{
			return String.format(GTIN_UNKNOWN_TEXT_FORMAT, gtin.getAsString());
		}

		final String piipsDescription = piips.stream()
				.map(piip -> toDescription(piip, date))
				.collect(Collectors.joining(", "));
		return String.format(GTIN_NOT_VALID_ON_DATE_TEXT_FORMAT, gtin.getAsString(), date.toLocalDate(), piipsDescription);
	}

	private static String toDescription(@NonNull final I_M_HU_PI_Item_Product piip, @NonNull final ZonedDateTime date)
	{
		final StringBuilder description = new StringBuilder("M_HU_PI_Item_Product_ID=")
				.append(piip.getM_HU_PI_Item_Product_ID())
				.append(" valid from ")
				.append(TimeUtil.asLocalDate(piip.getValidFrom(), date.getZone()));
		if (piip.getValidTo() != null)
		{
			description.append(" to ").append(TimeUtil.asLocalDate(piip.getValidTo(), date.getZone()));
		}
		return description.toString();
	}
}
