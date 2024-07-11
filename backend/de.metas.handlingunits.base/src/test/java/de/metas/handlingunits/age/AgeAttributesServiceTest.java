/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.age;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class AgeAttributesServiceTest
{
	private AgeAttributesService ageAttributesService;

	private I_C_BPartner vendor;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		ageAttributesService = new AgeAttributesService();
		SpringContextHolder.registerJUnitBean(ageAttributesService); // needed for HUPickingSlotBL

		vendor = createPartner("Vendor");
	}

	@Test
	public void computeAgeRangeForProducts_NoBPProduct_0_0()
	{
		final I_M_Product product = createProduct("Product 1", 0, 0);

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(Collections.singleton(productId),
																				  Collections.emptySet());

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(0);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(0);
	}

	@Test
	public void computeAgeRangeForProducts_NoBPProduct_2_3()
	{
		final I_M_Product product = createProduct("Product 1", 2, 3);

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(Collections.singleton(productId),
																				  Collections.emptySet());

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(2);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(3);
	}

	@Test
	public void computeAgeRangeForProducts_NoBPProduct_MultipleProduct()
	{
		final I_M_Product product1 = createProduct("Product 1", 2, 3);
		final I_M_Product product2 = createProduct("Product 2", 3, 3);
		final I_M_Product product3 = createProduct("Product 3", 3, 1);

		final Set<ProductId> productIDs = new HashSet<>();
		productIDs.add(ProductId.ofRepoId(product1.getM_Product_ID()));
		productIDs.add(ProductId.ofRepoId(product2.getM_Product_ID()));
		productIDs.add(ProductId.ofRepoId(product3.getM_Product_ID()));

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(productIDs,
																				  Collections.emptySet());

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(2);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(1);
	}

	@Test
	public void computeAgeRangeForProducts_With_BPProduct_4_6()
	{
		final I_M_Product product = createProduct("Product 1", 2, 3);

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final I_C_BPartner partner = createPartner("Partner 1");
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());

		createBPartnerProduct(bpartnerId, productId, 4, 6);

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(Collections.singleton(productId),
																				  Collections.singleton(bpartnerId));

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(4);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(6);
	}

	@Test
	public void computeAgeRangeForProducts_With_Another_BPProduct_2_3()
	{
		final I_M_Product product = createProduct("Product 1", 2, 3);

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final I_C_BPartner partner1 = createPartner("Partner 1");
		final BPartnerId bpartnerId1 = BPartnerId.ofRepoId(partner1.getC_BPartner_ID());

		createBPartnerProduct(bpartnerId1, productId, 4, 6);

		final I_C_BPartner partner2 = createPartner("Partner 2");

		final BPartnerId bpartnerId2 = BPartnerId.ofRepoId(partner2.getC_BPartner_ID());

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(Collections.singleton(productId),
																				  Collections.singleton(bpartnerId2));

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(2);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(3);
	}

	private void createBPartnerProduct(final BPartnerId bpartnerId1,
			final ProductId productId,
			final int picking_AgeTolerance_BeforeMonths,
			final int picking_AgeTolerance_AfterMonths)
	{
		final I_C_BPartner_Product bparnterProduct = newInstance(I_C_BPartner_Product.class);

		bparnterProduct.setUsedForCustomer(true);
		bparnterProduct.setC_BPartner_Vendor_ID(vendor.getC_BPartner_ID());
		bparnterProduct.setC_BPartner_ID(bpartnerId1.getRepoId());
		bparnterProduct.setM_Product_ID(productId.getRepoId());

		bparnterProduct.setPicking_AgeTolerance_BeforeMonths(picking_AgeTolerance_BeforeMonths);
		bparnterProduct.setPicking_AgeTolerance_AfterMonths(picking_AgeTolerance_AfterMonths);

		save(bparnterProduct);
	}

	private I_C_BPartner createPartner(final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);

		partner.setName(name);
		partner.setValue(name);

		save(partner);
		return partner;
	}

	private I_M_Product createProduct(final String name, final int picking_AgeTolerance_BeforeMonths, final int picking_AgeTolerance_AfterMonths)
	{
		final I_M_Product product = newInstance(I_M_Product.class);

		product.setName(name);
		product.setValue(name);

		product.setPicking_AgeTolerance_BeforeMonths(picking_AgeTolerance_BeforeMonths);
		product.setPicking_AgeTolerance_AfterMonths(picking_AgeTolerance_AfterMonths);

		save(product);
		return product;
	}

	@Test
	public void computeAgeRangeForProducts_With_BPProduct_MultipleProduct()
	{
		final I_M_Product product1 = createProduct("Product 1", 1, 1);
		final I_M_Product product2 = createProduct("Product 2", 2, 3);
		final I_M_Product product3 = createProduct("Product 3", 3, 2);

		final Set<ProductId> productIDs = new HashSet<>();
		productIDs.add(ProductId.ofRepoId(product1.getM_Product_ID()));
		productIDs.add(ProductId.ofRepoId(product2.getM_Product_ID()));
		productIDs.add(ProductId.ofRepoId(product3.getM_Product_ID()));

		final I_C_BPartner partner1 = createPartner("Partner1");
		createBPartnerProduct(BPartnerId.ofRepoId(partner1.getC_BPartner_ID()),
							  ProductId.ofRepoId(product1.getM_Product_ID()),
							  6,
							  6);

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(productIDs,
																				  Collections.singleton(BPartnerId.ofRepoId(partner1.getC_BPartner_ID())));

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(2);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(2);
	}

	@Test
	public void computeAgeRangeForProducts_With_MultipleBPProducts_MultipleProducts()
	{
		final I_M_Product product1 = createProduct("Product 1", 1, 1);
		final I_M_Product product2 = createProduct("Product 2", 2, 3);
		final I_M_Product product3 = createProduct("Product 3", 3, 2);

		final Set<ProductId> productIDs = new HashSet<>();
		productIDs.add(ProductId.ofRepoId(product1.getM_Product_ID()));
		productIDs.add(ProductId.ofRepoId(product2.getM_Product_ID()));
		productIDs.add(ProductId.ofRepoId(product3.getM_Product_ID()));

		final I_C_BPartner partner1 = createPartner("Partner1");
		createBPartnerProduct(BPartnerId.ofRepoId(partner1.getC_BPartner_ID()),
							  ProductId.ofRepoId(product1.getM_Product_ID()),
							  10,
							  11);

		createBPartnerProduct(BPartnerId.ofRepoId(partner1.getC_BPartner_ID()),
							  ProductId.ofRepoId(product2.getM_Product_ID()),
							  11,
							  12);

		createBPartnerProduct(BPartnerId.ofRepoId(partner1.getC_BPartner_ID()),
							  ProductId.ofRepoId(product3.getM_Product_ID()),
							  14,
							  10);

		final AgeRange ageRange = ageAttributesService.computeAgeRangeForProducts(productIDs,
																				  Collections.singleton(BPartnerId.ofRepoId(partner1.getC_BPartner_ID())));

		Assertions.assertThat(ageRange.getPickingAgeTolerance_BeforeMonths()).isEqualTo(10);
		Assertions.assertThat(ageRange.getPickingAgeTolerance_AfterMonths()).isEqualTo(10);
	}
}
