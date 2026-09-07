/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.externalreference.ExternalIdentifier;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.product.ExternalIdentifierProductLookupService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ProductMasterDataProvider}.
 *
 * <p>Covers:
 * <ul>
 *   <li>AC3 / AC4: getProductInfo with a GTIN identifier forwards the {@code date} parameter into
 *       {@link ExternalIdentifierProductLookupService#resolveProductExternalIdentifier} so that the correct
 *       {@code M_HU_PI_Item_Product} row is selected based on ValidFrom.</li>
 *   <li>Cache-key correctness: two calls with different {@code date} values produce two independent
 *       lookups (i.e., the cache key includes {@code date}).</li>
 * </ul>
 */
class ProductMasterDataProviderTest
{
	private ProductMasterDataProvider productMasterDataProvider;
	private static final OrgId ANY_ORG = OrgId.ofRepoId(1);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		productMasterDataProvider = ProductMasterDataProvider.newInstanceForUnitTesting();
	}

	/** Creates a minimal UOM record and returns its ID (needed for product stock UOM lookup). */
	private int createUomRepoId()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setName("PCE");
		uom.setX12DE355("PCE");
		uom.setIsActive(true);
		InterfaceWrapperHelper.save(uom);
		return uom.getC_UOM_ID();
	}

	/** Creates a product with the given value and a UOM, returning the saved record. */
	private I_M_Product createProduct(final String value)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(value);
		product.setIsActive(true);
		product.setC_UOM_ID(createUomRepoId());
		InterfaceWrapperHelper.save(product);
		return product;
	}

	/**
	 * Proves AC4: getProductInfo with a pre-switch DatePromised resolves the OLD (9 CU/TU) PIIP row,
	 * and with an on/after-switch DatePromised resolves the NEW (6 CU/TU) PIIP row.
	 *
	 * <p>Setup: one product, two M_HU_PI_Item_Product rows for the same GTIN.
	 * <ul>
	 *   <li>NEW row: Qty=6, ValidFrom=2026-07-01 — inserted first (lower ID, would win without date filter)</li>
	 *   <li>OLD row: Qty=9, ValidFrom=2019-01-01 — inserted second (higher ID)</li>
	 * </ul>
	 */
	@Test
	void getProductInfo_gtin_respects_datePromised_for_piip_validity()
	{
		// given — one active product (with UOM so getStockUOMId returns a valid value)
		final I_M_Product product = createProduct("cheese-250g");
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final String gtin = "88800042";

		// NEW row inserted FIRST → lower M_HU_PI_Item_Product_ID; without date filter it wins on ascending-ID ordering.
		// ValidFrom = 2026-07-01: should only be returned for dates >= 2026-07-01.
		final I_M_HU_PI_Item_Product newRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		newRow.setM_Product_ID(product.getM_Product_ID());
		newRow.setGTIN(gtin);
		newRow.setQty(new BigDecimal("6"));
		newRow.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2026-07-01"));
		newRow.setIsActive(true);
		InterfaceWrapperHelper.save(newRow);
		final HUPIItemProductId newRowId = HUPIItemProductId.ofRepoId(newRow.getM_HU_PI_Item_Product_ID());

		// OLD row inserted SECOND → higher M_HU_PI_Item_Product_ID.
		// ValidFrom = 2019-01-01: should be returned for dates before 2026-07-01.
		final I_M_HU_PI_Item_Product oldRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		oldRow.setM_Product_ID(product.getM_Product_ID());
		oldRow.setGTIN(gtin);
		oldRow.setQty(new BigDecimal("9"));
		oldRow.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2019-01-01"));
		oldRow.setIsActive(true);
		InterfaceWrapperHelper.save(oldRow);
		final HUPIItemProductId oldRowId = HUPIItemProductId.ofRepoId(oldRow.getM_HU_PI_Item_Product_ID());

		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-" + gtin);

		// when — pre-switch: date before NEW's ValidFrom → only OLD is valid
		final ZonedDateTime beforeSwitch = LocalDate.of(2026, 6, 26).atStartOfDay(ZoneOffset.UTC);
		final ProductMasterDataProvider.ProductInfo infoBeforeSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, beforeSwitch);

		// then — OLD row (Qty=9)
		assertThat(infoBeforeSwitch.getProductId()).isEqualTo(productId);
		assertThat(infoBeforeSwitch.getHupiItemProductId())
				.as("date=2026-06-26 (pre-switch): should resolve OLD PIIP row (Qty=9)")
				.isEqualTo(oldRowId);

		// when — on/after switch: date on NEW's ValidFrom → both valid, pick latest ValidFrom → NEW wins
		final ZonedDateTime afterSwitch = LocalDate.of(2026, 7, 5).atStartOfDay(ZoneOffset.UTC);
		final ProductMasterDataProvider.ProductInfo infoAfterSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, afterSwitch);

		// then — NEW row (Qty=6)
		assertThat(infoAfterSwitch.getProductId()).isEqualTo(productId);
		assertThat(infoAfterSwitch.getHupiItemProductId())
				.as("date=2026-07-05 (on/after switch): should resolve NEW PIIP row (Qty=6)")
				.isEqualTo(newRowId);
	}

	/**
	 * Proves that the cache key includes {@code date}: two calls with different dates do NOT return
	 * the same cached result when the underlying data differs per date.
	 *
	 * <p>This is the guard for AC3: without {@code date} in the cache key, a first call with
	 * {@code beforeSwitch} would cache the OLD row, and a subsequent call with {@code afterSwitch}
	 * would incorrectly return the cached OLD row instead of the NEW one.
	 */
	@Test
	void getProductInfo_cacheKey_includes_date()
	{
		// given — one active product with two PIIP rows (same setup as above)
		final I_M_Product product = createProduct("cheese-500g");

		final String gtin = "88800043";

		// NEW row: inserted first → lower ID; ValidFrom = 2026-07-01
		final I_M_HU_PI_Item_Product newRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		newRow.setM_Product_ID(product.getM_Product_ID());
		newRow.setGTIN(gtin);
		newRow.setQty(new BigDecimal("6"));
		newRow.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2026-07-01"));
		newRow.setIsActive(true);
		InterfaceWrapperHelper.save(newRow);
		final HUPIItemProductId newRowId = HUPIItemProductId.ofRepoId(newRow.getM_HU_PI_Item_Product_ID());

		// OLD row: inserted second → higher ID; ValidFrom = 2019-01-01
		final I_M_HU_PI_Item_Product oldRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		oldRow.setM_Product_ID(product.getM_Product_ID());
		oldRow.setGTIN(gtin);
		oldRow.setQty(new BigDecimal("9"));
		oldRow.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2019-01-01"));
		oldRow.setIsActive(true);
		InterfaceWrapperHelper.save(oldRow);
		final HUPIItemProductId oldRowId = HUPIItemProductId.ofRepoId(oldRow.getM_HU_PI_Item_Product_ID());

		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-" + gtin);
		final ZonedDateTime beforeSwitch = LocalDate.of(2026, 6, 26).atStartOfDay(ZoneOffset.UTC);
		final ZonedDateTime afterSwitch = LocalDate.of(2026, 7, 5).atStartOfDay(ZoneOffset.UTC);

		// Call beforeSwitch FIRST — populates cache for (identifier, orgId, beforeSwitch)
		final ProductMasterDataProvider.ProductInfo infoBeforeSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, beforeSwitch);
		assertThat(infoBeforeSwitch.getHupiItemProductId())
				.as("first call (beforeSwitch): cache miss → resolve → OLD row")
				.isEqualTo(oldRowId);

		// Call afterSwitch SECOND — must NOT return the cached beforeSwitch result
		final ProductMasterDataProvider.ProductInfo infoAfterSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, afterSwitch);
		assertThat(infoAfterSwitch.getHupiItemProductId())
				.as("second call (afterSwitch): different date → separate cache entry → NEW row (if date is part of key)")
				.isEqualTo(newRowId);
	}
}
