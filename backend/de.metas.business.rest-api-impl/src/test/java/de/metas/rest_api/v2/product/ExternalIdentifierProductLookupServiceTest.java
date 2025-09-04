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
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalIdentifierProductLookupServiceTest
{
	private ExternalIdentifierProductLookupService productLookupService;
	
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		
		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();
		final ExternalSystems externalSystems = new ExternalSystems();
		final ExternalReferenceRepository externalReferenceRepository =
				new ExternalReferenceRepository(Services.get(IQueryBL.class), externalSystems, externalReferenceTypes);
		final ExternalReferenceRestControllerService externalReferenceRestControllerService = new ExternalReferenceRestControllerService(externalReferenceRepository, new ExternalSystems(), new ExternalReferenceTypes());
		
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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

		// then
		assertThat(result).isEmpty();
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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

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
		final Optional<ProductAndHUPIItemProductId> result = productLookupService.lookupProductByGTIN(identifier);

		// then
		assertThat(result).isPresent();
		assertThat(result.get().getProductId()).isEqualTo(ProductId.ofRepoId(product2.getM_Product_ID()));
		assertThat(result.get().getHupiItemProductId()).isEqualTo(HUPIItemProductId.VIRTUAL_HU);
	}
}
