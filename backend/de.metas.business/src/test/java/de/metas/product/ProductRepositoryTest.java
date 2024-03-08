/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.product;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import de.metas.product.model.I_M_Product;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_AD_OrgInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class ProductRepositoryTest
{
	private final static OrgId ORG_ID = OrgId.ofRepoId(100000);
	private ProductRepository productRepository;
	private final ProductCategoryId defaultProductCategoryId = ProductCategoryId.ofRepoId(1000000);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		productRepository = new ProductRepository();
		createOrg();
	}

	@Test
	void getById()
	{
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uomRecord");

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		productRecord.setValue("productNo");
		productRecord.setName("productName");
		productRecord.setProductType("ITEM");
		saveRecord(productRecord);

		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final Product product = productRepository.getById(productId);

		assertThat(product.getId()).isEqualTo(productId);
		assertThat(product.getProductNo()).isEqualTo("productNo");
		assertThat(product.getName().getDefaultValue()).isEqualTo("productName");
		assertThat(product.getUomId()).isEqualTo(UomId.ofRepoId(uomRecord.getC_UOM_ID()));
		assertThat(product.getProductType()).isEqualTo("ITEM");
	}

	@Test
	void getByIds()
	{
		final I_C_UOM uomRecord1 = BusinessTestHelper.createUOM("uomRecord1");

		final I_M_Product productRecord1 = newInstance(I_M_Product.class);
		productRecord1.setC_UOM_ID(uomRecord1.getC_UOM_ID());
		productRecord1.setValue("productNo1");
		productRecord1.setName("productName1");
		productRecord1.setProductType("ITEM");

		saveRecord(productRecord1);

		final ProductId productId1 = ProductId.ofRepoId(productRecord1.getM_Product_ID());

		final I_C_UOM uomRecord2 = BusinessTestHelper.createUOM("uomRecord2");

		final I_M_Product productRecord2 = newInstance(I_M_Product.class);
		productRecord2.setC_UOM_ID(uomRecord2.getC_UOM_ID());
		productRecord2.setValue("productNo2");
		productRecord2.setName("productName2");
		productRecord2.setProductType("ITEM");

		saveRecord(productRecord2);

		final ProductId productId2 = ProductId.ofRepoId(productRecord2.getM_Product_ID());

		final List<Product> products = productRepository.getByIds(ImmutableSet.of(productId1, productId2));
		assertThat(products).extracting("id", "productNo", "name.defaultValue", "uomId")
				.containsExactlyInAnyOrder(
						tuple(productId1, "productNo1", "productName1", UomId.ofRepoId(uomRecord1.getC_UOM_ID())),
						tuple(productId2, "productNo2", "productName2", UomId.ofRepoId(uomRecord2.getC_UOM_ID())));
	}

	@Test
	void createProduct()
	{
		final I_C_UOM uomRecord1 = BusinessTestHelper.createUOM("uomRecord1");
		final OrgId orgId = OrgId.ofRepoId(100000);
		final String name = "test name";
		final String type = "ITEM";
		final boolean stocked = true;
		final boolean active = true;
		final boolean discontinued = false;
		final String description = "test description";
		final String gtin = "test gtin";
		final String ean = "test ean";
		final String code = "p1";

		final CreateProductRequest createProductRequest =
				CreateProductRequest.builder()
						.orgId(orgId)
						.productName(name)
						.productType(type)
						.productCategoryId(defaultProductCategoryId)
						.uomId(UomId.ofRepoId(uomRecord1.getC_UOM_ID()))
						.stocked(stocked)
						.active(active)
						.discontinued(discontinued)
						.description(description)
						.gtin(gtin)
						.ean(ean)
						.productValue(code)
						.build();

		final Product createdProduct = productRepository.createProduct(createProductRequest);

		assertThat(createdProduct).isNotNull();
		assertThat(createdProduct.getOrgId()).isEqualTo(orgId);
		assertThat(createdProduct.getName().getDefaultValue()).isEqualTo(name);
		assertThat(createdProduct.getProductType()).isEqualTo(type);
		assertThat(createdProduct.getProductCategoryId()).isEqualTo(defaultProductCategoryId);
		assertThat(createdProduct.isStocked()).isEqualTo(stocked);
		assertThat(createdProduct.getActive()).isEqualTo(active);
		assertThat(createdProduct.getDiscontinued()).isEqualTo(discontinued);
		assertThat(createdProduct.getGtin()).isEqualTo(gtin);
		assertThat(createdProduct.getEan()).isEqualTo(ean);
		assertThat(createdProduct.getDescription().getDefaultValue()).isEqualTo(description);
		assertThat(createdProduct.getProductNo()).isEqualTo(code);
		assertThat(createdProduct.getUomId()).isEqualTo(createProductRequest.getUomId());
	}

	@Test
	void updateProduct()
	{
		final I_C_UOM uomRecord1 = BusinessTestHelper.createUOM("uomRecord1");
		final ProductId productId = BusinessTestHelper.createProductId("test", uomRecord1);

		final OrgId orgId = OrgId.ofRepoId(100000);
		final String name = "test name";
		final String type = "ITEM";
		final boolean stocked = true;
		final boolean active = true;
		final boolean discontinued = false;
		final String description = "test description";
		final String gtin = "test gtin";
		final String ean = "test ean";
		final String code = "p1";
		final String packageSize = "1";
		final BigDecimal weight = new BigDecimal(100);

		final CommodityNumberId commodityNumberId = CommodityNumberId.ofRepoId(1000);
		final BPartnerId manufacturerId = BPartnerId.ofRepoId(1000);

		final Product product = Product.builder()
				.id(productId)
				.name(TranslatableStrings.constant(name))
				.productCategoryId(defaultProductCategoryId)
				.productType(type)
				.uomId(UomId.ofRepoId(uomRecord1.getC_UOM_ID()))
				.ean(ean)
				.gtin(gtin)
				.description(TranslatableStrings.constant(description))
				.discontinued(discontinued)
				.active(active)
				.stocked(stocked)
				.orgId(orgId)
				.productNo(code)
				.commodityNumberId(commodityNumberId)
				.manufacturerId(manufacturerId)
				.packageSize(packageSize)
				.weight(weight)
				.build();

		productRepository.updateProduct(product);

		final Product updatedProduct = productRepository.getById(productId);

		assertThat(updatedProduct).isNotNull();
		assertThat(updatedProduct.getOrgId()).isEqualTo(orgId);
		assertThat(updatedProduct.getName().getDefaultValue()).isEqualTo(name);
		assertThat(updatedProduct.getProductType()).isEqualTo(type);
		assertThat(updatedProduct.getProductCategoryId()).isEqualTo(defaultProductCategoryId);
		assertThat(updatedProduct.isStocked()).isEqualTo(stocked);
		assertThat(updatedProduct.getActive()).isEqualTo(active);
		assertThat(updatedProduct.getDiscontinued()).isEqualTo(discontinued);
		assertThat(updatedProduct.getGtin()).isEqualTo(gtin);
		assertThat(updatedProduct.getEan()).isEqualTo(ean);
		assertThat(updatedProduct.getDescription().getDefaultValue()).isEqualTo(description);
		assertThat(updatedProduct.getProductNo()).isEqualTo(code);
		assertThat(updatedProduct.getCommodityNumberId()).isEqualTo(commodityNumberId);
		assertThat(updatedProduct.getManufacturerId()).isEqualTo(manufacturerId);
		assertThat(updatedProduct.getPackageSize()).isEqualTo(packageSize);
		assertThat(updatedProduct.getWeight()).isEqualTo(weight);
		assertThat(updatedProduct.getUomId()).isEqualTo(product.getUomId());
	}

	private void createOrg()
	{
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(ORG_ID.getRepoId());
		orgInfo.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_Letzte4Stellen);
		save(orgInfo);
	}
}