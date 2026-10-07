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

import de.metas.bpartner.BPartnerId;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.product.ExternalIdentifierProductLookupService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import de.metas.util.web.exception.MissingResourceException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		final ProductMasterDataProvider.ProductInfo infoBeforeSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, beforeSwitch, null);

		// then — OLD row (Qty=9)
		assertThat(infoBeforeSwitch.getProductId()).isEqualTo(productId);
		assertThat(infoBeforeSwitch.getHupiItemProductId())
				.as("date=2026-06-26 (pre-switch): should resolve OLD PIIP row (Qty=9)")
				.isEqualTo(oldRowId);

		// when — on/after switch: date on NEW's ValidFrom → both valid, pick latest ValidFrom → NEW wins
		final ZonedDateTime afterSwitch = LocalDate.of(2026, 7, 5).atStartOfDay(ZoneOffset.UTC);
		final ProductMasterDataProvider.ProductInfo infoAfterSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, afterSwitch, null);

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
		final ProductMasterDataProvider.ProductInfo infoBeforeSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, beforeSwitch, null);
		assertThat(infoBeforeSwitch.getHupiItemProductId())
				.as("first call (beforeSwitch): cache miss → resolve → OLD row")
				.isEqualTo(oldRowId);

		// Call afterSwitch SECOND — must NOT return the cached beforeSwitch result
		final ProductMasterDataProvider.ProductInfo infoAfterSwitch = productMasterDataProvider.getProductInfo(identifier, ANY_ORG, afterSwitch, null);
		assertThat(infoAfterSwitch.getHupiItemProductId())
				.as("second call (afterSwitch): different date → separate cache entry → NEW row (if date is part of key)")
				.isEqualTo(newRowId);
	}

	/**
	 * Two partners each own a carton row with the same GTIN. Both requests go through the same provider
	 * instance (shared cache); each partner must get its own packing instruction.
	 */
	@Test
	void getProductInfo_is_cached_per_ordering_partner()
	{
		// given
		final I_M_Product product = createProduct("feta-200g");
		final BPartnerId partnerA = createBPartner("partnerA");
		final BPartnerId partnerB = createBPartner("partnerB");
		final HUPIItemProductId rowA = createPiip(product, "90000000001", partnerA);
		final HUPIItemProductId rowB = createPiip(product, "90000000001", partnerB);
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-90000000001");

		// when / then
		assertThat(productMasterDataProvider.getProductInfo(identifier, ANY_ORG, null, partnerA).getHupiItemProductId())
				.as("partner A").isEqualTo(rowA);
		assertThat(productMasterDataProvider.getProductInfo(identifier, ANY_ORG, null, partnerB).getHupiItemProductId())
				.as("partner B (must not get A's cached row)").isEqualTo(rowB);
		assertThat(productMasterDataProvider.getProductInfo(identifier, ANY_ORG, null, partnerA).getHupiItemProductId())
				.as("partner A again").isEqualTo(rowA);
	}

	@Nested
	class getProductInfoForOrderCandidate
	{
		private String existingText(final String identifier)
		{
			return "The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=" + identifier + " -  could not be found.";
		}

		@Test
		void gtin_not_valid_on_date_explains()
		{
			final I_M_Product product = createProduct("gtin-explain");
			final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
			piip.setM_Product_ID(product.getM_Product_ID());
			piip.setGTIN("90000000101");
			piip.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2022-09-01"));
			piip.setIsActive(true);
			InterfaceWrapperHelper.save(piip);
			final ZonedDateTime date = LocalDate.of(2020, 1, 27).atStartOfDay(ZoneId.systemDefault());

			assertThatThrownBy(() -> productMasterDataProvider.getProductInfoForOrderCandidate(ExternalIdentifier.of("gtin-90000000101"), ANY_ORG, date, null))
					.isInstanceOf(OLCandProductNotFoundException.class)
					.hasMessage(existingText("gtin-90000000101") + " "
							+ "GTIN 90000000101 is only on packing instructions that are not valid on the delivery date 2020-01-27: "
							+ "M_HU_PI_Item_Product_ID=" + piip.getM_HU_PI_Item_Product_ID() + " valid from 2022-09-01.");
		}

		@Test
		void val_not_found_keeps_text()
		{
			assertThatThrownBy(() -> productMasterDataProvider.getProductInfoForOrderCandidate(ExternalIdentifier.of("val-unknown"), ANY_ORG, null, null))
					.isInstanceOf(OLCandProductNotFoundException.class)
					.hasMessage(existingText("val-unknown"));
		}
	}

	@Test
	void getProductInfo_unchanged_for_other_apis()
	{
		final I_M_Product product = createProduct("gtin-unchanged");
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_Product_ID(product.getM_Product_ID());
		piip.setGTIN("90000000102");
		piip.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2022-09-01"));
		piip.setIsActive(true);
		InterfaceWrapperHelper.save(piip);
		final ZonedDateTime date = LocalDate.of(2020, 1, 27).atStartOfDay(ZoneId.systemDefault());

		assertThatThrownBy(() -> productMasterDataProvider.getProductInfo(ExternalIdentifier.of("gtin-90000000102"), ANY_ORG, date, null))
				.isInstanceOf(MissingResourceException.class)
				.hasMessage("The resource with resourceName=productIdentifier - which is identified by resourceIdentifier=gtin-90000000102 -  could not be found.");
	}

	private BPartnerId createBPartner(final String value)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setValue(value);
		bpartner.setName(value);
		InterfaceWrapperHelper.save(bpartner);
		return BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
	}

	private HUPIItemProductId createPiip(final I_M_Product product, final String gtin, @Nullable final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_Product_ID(product.getM_Product_ID());
		piip.setGTIN(gtin);
		piip.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		piip.setIsActive(true);
		InterfaceWrapperHelper.save(piip);
		return HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID());
	}
}
