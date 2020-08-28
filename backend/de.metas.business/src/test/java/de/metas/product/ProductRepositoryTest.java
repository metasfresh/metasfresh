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
import de.metas.business.BusinessTestHelper;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.model.I_M_Product;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.groups.Tuple;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ProductRepositoryTest
{

	private ProductRepository productRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		productRepository = new ProductRepository();
	}

	@Test
	void getById()
	{
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uomRecord");

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		productRecord.setValue("productNo");
		productRecord.setName("productName");
		saveRecord(productRecord);

		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final Product product = productRepository.getById(productId);

		assertThat(product.getId()).isEqualTo(productId);
		assertThat(product.getProductNo()).isEqualTo("productNo");
		assertThat(product.getName().getDefaultValue()).isEqualTo("productName");
		assertThat(product.getUomId()).isEqualTo(UomId.ofRepoId(uomRecord.getC_UOM_ID()));
	}

	@Test
	void getByIds()
	{
		final I_C_UOM uomRecord1 = BusinessTestHelper.createUOM("uomRecord1");

		final I_M_Product productRecord1 = newInstance(I_M_Product.class);
		productRecord1.setC_UOM_ID(uomRecord1.getC_UOM_ID());
		productRecord1.setValue("productNo1");
		productRecord1.setName("productName1");
		saveRecord(productRecord1);

		final ProductId productId1 = ProductId.ofRepoId(productRecord1.getM_Product_ID());

		final I_C_UOM uomRecord2 = BusinessTestHelper.createUOM("uomRecord2");

		final I_M_Product productRecord2 = newInstance(I_M_Product.class);
		productRecord2.setC_UOM_ID(uomRecord2.getC_UOM_ID());
		productRecord2.setValue("productNo2");
		productRecord2.setName("productName2");
		saveRecord(productRecord2);

		final ProductId productId2 = ProductId.ofRepoId(productRecord2.getM_Product_ID());

		final List<Product> products = productRepository.getByIds(ImmutableSet.of(productId1, productId2));
		assertThat(products).extracting("id", "productNo", "name.defaultValue", "uomId")
				.containsExactlyInAnyOrder(
						tuple(productId1, "productNo1", "productName1", UomId.ofRepoId(uomRecord1.getC_UOM_ID())),
						tuple(productId2, "productNo2", "productName2", UomId.ofRepoId(uomRecord2.getC_UOM_ID())));
	}
}