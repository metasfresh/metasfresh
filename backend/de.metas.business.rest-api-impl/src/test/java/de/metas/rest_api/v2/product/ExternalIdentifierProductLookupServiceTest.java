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

import de.metas.externalreference.ExternalIdentifier;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.ProductAndHUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalIdentifierProductLookupServiceTest
{
	private ExternalIdentifierProductLookupService productLookupService;
	
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		productLookupService = ExternalIdentifierProductLookupService.newInstanceForUnitTesting();
	}

	@Test
	void lookupProductByGTIN_from_M_HU_PI_Item_Product_GTIN()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product.getM_Product_ID());
		hupiItemProduct.setGTIN("12345678");
		hupiItemProduct.setIsActive(true);
		InterfaceWrapperHelper.save(hupiItemProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-12345678");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.ofRepoId(hupiItemProduct.getM_HU_PI_Item_Product_ID()));
	}

	@Test
	void lookupProductByGTIN_from_M_HU_PI_Item_Product_EAN_TU()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product.getM_Product_ID());
		hupiItemProduct.setEAN_TU("87654321");
		hupiItemProduct.setIsActive(true);
		InterfaceWrapperHelper.save(hupiItemProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-87654321");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.ofRepoId(hupiItemProduct.getM_HU_PI_Item_Product_ID()));
	}

	@Test
	void lookupProductByGTIN_from_M_HU_PI_Item_Product_UPC()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product.getM_Product_ID());
		hupiItemProduct.setUPC("98765432");
		hupiItemProduct.setIsActive(true);
		InterfaceWrapperHelper.save(hupiItemProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-98765432");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.ofRepoId(hupiItemProduct.getM_HU_PI_Item_Product_ID()));
	}

	@Test
	void lookupProductByGTIN_from_C_BPartner_Product_GTIN()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setM_Product_ID(product.getM_Product_ID());
		bpartnerProduct.setGTIN("11223344");
		bpartnerProduct.setIsActive(true);
		InterfaceWrapperHelper.save(bpartnerProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-11223344");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void lookupProductByGTIN_from_C_BPartner_Product_EAN_CU()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setM_Product_ID(product.getM_Product_ID());
		bpartnerProduct.setEAN_CU("44332211");
		bpartnerProduct.setIsActive(true);
		InterfaceWrapperHelper.save(bpartnerProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-44332211");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void lookupProductByGTIN_from_C_BPartner_Product_UPC()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setM_Product_ID(product.getM_Product_ID());
		bpartnerProduct.setUPC("55667788");
		bpartnerProduct.setIsActive(true);
		InterfaceWrapperHelper.save(bpartnerProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-55667788");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void lookupProductByGTIN_from_M_Product_GTIN()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		product.setGTIN("99887766");
		product.setIsActive(true);
		InterfaceWrapperHelper.save(product);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-99887766");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void lookupProductByGTIN_from_M_Product_EAN13_ProductCode()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		product.setEAN13_ProductCode("66778899");
		product.setIsActive(true);
		InterfaceWrapperHelper.save(product);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-66778899");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void lookupProductByGTIN_from_M_Product_UPC()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		product.setUPC("77889900");
		product.setIsActive(true);
		InterfaceWrapperHelper.save(product);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-77889900");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void lookupProductByGTIN_not_found()
	{
		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-00000000");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isEmpty();
	}

	/**
	 * Proves that {@code lookupProductByGTIN} honours the {@code date} parameter and selects the
	 * {@link I_M_HU_PI_Item_Product} row whose {@code ValidFrom} is on or before the given date.
	 *
	 * <p>Setup: one product, two M_HU_PI_Item_Product rows sharing the same GTIN.
	 * NEW is inserted first (lower ID) to eliminate any ordering coincidence — without the date filter
	 * the ascending-ID tiebreak would return NEW for both dates, masking a date-filter regression.
	 * <ul>
	 *   <li>NEW row: Qty=6, ValidFrom=2026-07-01 — inserted first → lower ID</li>
	 *   <li>OLD row: Qty=9, ValidFrom=2019-01-01 — inserted second → higher ID</li>
	 * </ul>
	 *
	 * <p>Expected behaviour (implemented by {@code HUPIItemProductDAO.createValidOnDateFilter}):
	 * <ul>
	 *   <li>date=2026-06-26 (before NEW's ValidFrom) → only OLD is valid → OLD row (Qty=9)</li>
	 *   <li>date=2026-07-05 (on/after NEW's ValidFrom, both rows valid) → latest ValidFrom wins → NEW row (Qty=6)</li>
	 * </ul>
	 */
	@Test
	void gtin_respects_validity_at_datePromised()
	{
		// given — one active product
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product-validity");
		product.setIsActive(true);
		InterfaceWrapperHelper.save(product);

		final String gtin = "77700001";

		// NEW row inserted FIRST → gets the lower M_HU_PI_Item_Product_ID.
		// Without the date filter the ascending-ID tiebreak would return this row for BOTH dates,
		// masking a regression. The date filter (ValidFrom <= date) correctly excludes it pre-switch.
		// ValidFrom=2026-07-01 → valid only for dates on/after 2026-07-01.
		final I_M_HU_PI_Item_Product newRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		newRow.setM_Product_ID(product.getM_Product_ID());
		newRow.setGTIN(gtin);
		newRow.setQty(new BigDecimal("6"));
		newRow.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2026-07-01"));
		newRow.setIsActive(true);
		InterfaceWrapperHelper.save(newRow);

		// OLD row inserted SECOND → gets the higher M_HU_PI_Item_Product_ID.
		// ValidFrom=2019-01-01 → valid for all dates before 2026-07-01; also valid after (but ranked lower
		// by ValidFrom DESC, so NEW wins when both are valid).
		final I_M_HU_PI_Item_Product oldRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		oldRow.setM_Product_ID(product.getM_Product_ID());
		oldRow.setGTIN(gtin);
		oldRow.setQty(new BigDecimal("9"));
		oldRow.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2019-01-01"));
		oldRow.setIsActive(true);
		InterfaceWrapperHelper.save(oldRow);

		final HUPIItemProductId oldRowId = HUPIItemProductId.ofRepoId(oldRow.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId newRowId = HUPIItemProductId.ofRepoId(newRow.getM_HU_PI_Item_Product_ID());
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-" + gtin);

		// when — date before NEW's ValidFrom: only OLD is valid → expect OLD row (Qty=9)
		final ZonedDateTime beforeSwitch = LocalDate.of(2026, 6, 26).atStartOfDay(ZoneOffset.UTC);
		final Optional<ProductAndHUPIItemProductId> resultBeforeSwitch = productLookupService.lookupProductByGTIN(identifier, beforeSwitch);
		assertThat(resultBeforeSwitch).isPresent();
		assertThat(resultBeforeSwitch.get().getHupiItemProductId())
				.as("date=2026-06-26 (pre-switch): only OLD row is valid → expect OLD (Qty=9)")
				.isEqualTo(oldRowId);

		// when — date on/after NEW's ValidFrom: both rows valid, latest ValidFrom wins → expect NEW row (Qty=6)
		final ZonedDateTime afterSwitch = LocalDate.of(2026, 7, 5).atStartOfDay(ZoneOffset.UTC);
		final Optional<ProductAndHUPIItemProductId> resultAfterSwitch = productLookupService.lookupProductByGTIN(identifier, afterSwitch);
		assertThat(resultAfterSwitch).isPresent();
		assertThat(resultAfterSwitch.get().getHupiItemProductId())
				.as("date=2026-07-05 (post-switch): both rows valid, NEW has the latest ValidFrom → expect NEW (Qty=6)")
				.isEqualTo(newRowId);
	}

	/**
	 * Strict validity test: when the only PIIP row has a {@code ValidFrom} date AFTER the query date,
	 * branch 1 finds nothing (the row is not yet valid), branches 2 and 3 also find nothing
	 * (no {@code C_BPartner_Product} or {@code M_Product} carries this GTIN) → result must be empty.
	 *
	 * <p>Setup: ValidFrom=2022-01-01, query date=2021-04-15 (ValidFrom is AFTER the query date).
	 */
	@Test
	void gtin_returns_empty_when_only_piip_row_is_not_yet_valid()
	{
		// given — one active product with a single PIIP whose ValidFrom is AFTER the query date
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product-future-validfrom");
		product.setIsActive(true);
		InterfaceWrapperHelper.save(product);

		final String gtin = "20220101GT";

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product.getM_Product_ID());
		hupiItemProduct.setGTIN(gtin);
		hupiItemProduct.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2022-01-01")); // ValidFrom is in the FUTURE relative to the query date
		hupiItemProduct.setIsActive(true);
		InterfaceWrapperHelper.save(hupiItemProduct);

		final ZonedDateTime queryDate = LocalDate.of(2021, 4, 15).atStartOfDay(ZoneOffset.UTC);
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-" + gtin);
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, queryDate);

		// then — no valid PIIP, no BPartner_Product, no M_Product carrying the GTIN → empty
		assertThat(result)
				.as("No PIIP is valid on the query date and no other source carries the GTIN → must be empty")
				.isEmpty();
	}

	@Test
	void lookupProductByGTIN_with_inactive_records()
	{
		// given
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("test-product");
		InterfaceWrapperHelper.save(product);

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product.getM_Product_ID());
		hupiItemProduct.setGTIN("12345678");
		hupiItemProduct.setIsActive(false); // Inactive record
		InterfaceWrapperHelper.save(hupiItemProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-12345678");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void lookupProductByGTIN_excludes_piip_with_inactive_product()
	{
		// given — PIIP is active but points to an inactive product (post-consolidation orphan).
		// A product-consolidation run may deactivate M_Product while leaving the PIIP rows active;
		// only PIIPs whose M_Product_ID points to an active product are valid matches.
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("consolidated-away-product");
		product.setIsActive(false); // Product is inactive — the key difference from lookupProductByGTIN_with_inactive_records
		InterfaceWrapperHelper.save(product);

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product.getM_Product_ID());
		hupiItemProduct.setGTIN("99001234");
		hupiItemProduct.setIsActive(true); // PIIP itself is active — only the product is inactive
		InterfaceWrapperHelper.save(hupiItemProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-99001234");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then — stale PIIP pointing to inactive product must be excluded
		assertThat(result).isEmpty();
	}

	@Test
	void lookupProductByGTIN_excludes_bpartnerProduct_with_inactive_product()
	{
		// given — C_BPartner_Product is active but points to an inactive product (post-consolidation orphan).
		// A product-consolidation run (F5001.1) may deactivate M_Product while leaving its
		// C_BPartner_Product rows active; only rows whose M_Product_ID points to an active product are valid matches.
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("consolidated-away-bpp-product");
		product.setIsActive(false); // Product is inactive — the C_BPartner_Product row below stays active
		InterfaceWrapperHelper.save(product);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setM_Product_ID(product.getM_Product_ID());
		bpartnerProduct.setGTIN("99005678");
		bpartnerProduct.setIsActive(true); // the join row itself is active — only the product is inactive
		InterfaceWrapperHelper.save(bpartnerProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-99005678");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then — stale C_BPartner_Product pointing to inactive product must be excluded
		assertThat(result).isEmpty();
	}

	@Test
	void lookupProductByGTIN_prioritizes_HU_PI_Item_Product_over_M_Product()
	{
		// given
		final I_M_Product product1 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product1.setValue("product1");
		product1.setGTIN("12345678");
		product1.setIsActive(true);
		InterfaceWrapperHelper.save(product1);

		final I_M_Product product2 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product2.setValue("product2");
		InterfaceWrapperHelper.save(product2);

		final I_M_HU_PI_Item_Product hupiItemProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		hupiItemProduct.setM_Product_ID(product2.getM_Product_ID());
		hupiItemProduct.setGTIN("12345678"); // Same GTIN as product1
		hupiItemProduct.setIsActive(true);
		InterfaceWrapperHelper.save(hupiItemProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-12345678");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product2.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.ofRepoId(hupiItemProduct.getM_HU_PI_Item_Product_ID()));
	}

	@Test
	void lookupProductByGTIN_prioritizes_C_BPartner_Product_over_M_Product()
	{
		// given
		final I_M_Product product1 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product1.setValue("product1");
		product1.setGTIN("12345678");
		product1.setIsActive(true);
		InterfaceWrapperHelper.save(product1);

		final I_M_Product product2 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product2.setValue("product2");
		InterfaceWrapperHelper.save(product2);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setM_Product_ID(product2.getM_Product_ID());
		bpartnerProduct.setGTIN("12345678"); // Same GTIN as product1
		bpartnerProduct.setIsActive(true);
		InterfaceWrapperHelper.save(bpartnerProduct);

		// when
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-12345678");
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier, null);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product2.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}
}
