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
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

		final ExternalReferenceRepository externalReferenceRepository = ExternalReferenceRepository.newInstanceForUnitTesting(new ExternalReferenceTypes());
		final ExternalReferenceRestControllerService externalReferenceRestControllerService = new ExternalReferenceRestControllerService(externalReferenceRepository, new ExternalSystemRepository(), new ExternalReferenceTypes());

		productLookupService = new ExternalIdentifierProductLookupService(externalReferenceRestControllerService);
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
	 * RED test — proves the bug: {@code lookupProductByGTIN} currently ignores the {@code date} parameter
	 * and always returns the record with the lowest {@code M_HU_PI_Item_Product_ID} (oldest by insertion order).
	 *
	 * <p>Setup: one product, two M_HU_PI_Item_Product rows sharing the same GTIN:
	 * <ul>
	 *   <li>OLD row: Qty=9, ValidFrom=2019-01-01</li>
	 *   <li>NEW row: Qty=6, ValidFrom=2026-07-01</li>
	 * </ul>
	 *
	 * <p>Expected (after Task 3 fix):
	 * <ul>
	 *   <li>date=2026-06-26 (before NEW's ValidFrom) → OLD row (Qty=9)</li>
	 *   <li>date=2026-07-05 (on/after NEW's ValidFrom, both rows valid) → NEW row (Qty=6, latest ValidFrom wins)</li>
	 * </ul>
	 *
	 * <p>Current behaviour (FAILING — date is ignored): the second assertion returns the OLD row (Qty=9)
	 * instead of the NEW row (Qty=6), because the query orders by {@code M_HU_PI_Item_Product_ID} ascending
	 * and OLD was inserted first (lower ID). The first assertion passes trivially for the same reason.
	 *
	 * <p>This test MUST FAIL until Task 3 implements the validity filter.
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

		// OLD row: valid from 2019-01-01, no ValidTo → still valid as of 2026-06-26 but superseded after 2026-07-01
		final I_M_HU_PI_Item_Product oldRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		oldRow.setM_Product_ID(product.getM_Product_ID());
		oldRow.setGTIN(gtin);
		oldRow.setQty(new BigDecimal("9"));
		oldRow.setValidFrom(Timestamp.from(LocalDate.of(2019, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		oldRow.setIsActive(true);
		InterfaceWrapperHelper.save(oldRow);

		// NEW row: valid from 2026-07-01, no ValidTo → applies from 2026-07-01 onward
		final I_M_HU_PI_Item_Product newRow = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		newRow.setM_Product_ID(product.getM_Product_ID());
		newRow.setGTIN(gtin);
		newRow.setQty(new BigDecimal("6"));
		newRow.setValidFrom(Timestamp.from(LocalDate.of(2026, 7, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		newRow.setIsActive(true);
		InterfaceWrapperHelper.save(newRow);

		final HUPIItemProductId oldRowId = HUPIItemProductId.ofRepoId(oldRow.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId newRowId = HUPIItemProductId.ofRepoId(newRow.getM_HU_PI_Item_Product_ID());
		final ExternalIdentifier identifier = ExternalIdentifier.of("gtin-" + gtin);

		// when — date before NEW's ValidFrom: only OLD is valid → expect OLD row (Qty=9)
		final ZonedDateTime beforeSwitch = LocalDate.of(2026, 6, 26).atStartOfDay(ZoneOffset.UTC);
		final Optional<ProductAndHUPIItemProductId> resultBeforeSwitch = productLookupService.lookupProductByGTIN(identifier, beforeSwitch);
		assertThat(resultBeforeSwitch).isPresent();
		assertThat(resultBeforeSwitch.get().getHupiItemProductId())
				.as("date=2026-06-26: should return OLD row (Qty=9) but date is currently ignored → FAILS with NEW row")
				.isEqualTo(oldRowId);

		// when — date on/after NEW's ValidFrom: both valid, pick latest ValidFrom → expect NEW row (Qty=6)
		final ZonedDateTime afterSwitch = LocalDate.of(2026, 7, 5).atStartOfDay(ZoneOffset.UTC);
		final Optional<ProductAndHUPIItemProductId> resultAfterSwitch = productLookupService.lookupProductByGTIN(identifier, afterSwitch);
		assertThat(resultAfterSwitch).isPresent();
		assertThat(resultAfterSwitch.get().getHupiItemProductId())
				.as("date=2026-07-05: should return NEW row (Qty=6, latest ValidFrom) — currently returns OLD due to ascending ID ordering")
				.isEqualTo(newRowId);
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
