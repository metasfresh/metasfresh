/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.product.impl;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractIntegerAssert;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.annotation.Nullable;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDAOTest
{
	private ProductDAO productDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		productDAO = (ProductDAO)Services.get(IProductDAO.class);
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, AdempiereTestHelper.createOrgWithTimeZone());
	}

	@Nested
	class getGuaranteeDuration
	{
		AbstractIntegerAssert<?> assertGuaranteeDays(final ProductId productId)
		{
			final int expectedGuaranteeDays = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);
			return assertThat(expectedGuaranteeDays);
		}

		@Test
		void not_defined()
		{
			final ProductId productId = product().build();
			assertGuaranteeDays(productId).isEqualTo(0);
		}

		@Test
		void productGuaranteeDays_is_set()
		{
			final ProductId productId = product().productGuaranteeDaysMin(10).productGuaranteeMonths("12").productCategoryGuaranteeDaysMin(20).build();
			assertGuaranteeDays(productId).isEqualTo(10);
		}

		@ParameterizedTest(name = "months={arguments}")
		@CsvSource({ "1,30", "2,60", "3,91", "4,121", "5,152", "6,182", "7,212", "8,243", "9,273", "10,304	", "11,334", "12,365", "15,456", "18,547", "19,577", "24,730", "36,1095", "60,1825" })
		void productGuaranteeMonths_is_set(final String months, int expectedGuaranteeDays)
		{
			final ProductId productId = product().productGuaranteeMonths(months).productCategoryGuaranteeDaysMin(20).build();
			assertGuaranteeDays(productId).isEqualTo(expectedGuaranteeDays);
		}

		@Test
		void productCategoryGuaranteeDaysMin_is_set()
		{
			final ProductId productId = product().productCategoryGuaranteeDaysMin(33).build();
			assertGuaranteeDays(productId).isEqualTo(33);
		}
	}

	@Builder(builderMethodName = "product", builderClassName = "$ProductBuilder")
	ProductId createProduct(
			@Nullable final Integer productCategoryGuaranteeDaysMin,
			@Nullable final Integer productGuaranteeDaysMin,
			@Nullable final String productGuaranteeMonths)
	{
		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		productCategory.setIsActive(true);
		productCategory.setName("Test Category");
		if (productCategoryGuaranteeDaysMin != null)
		{
			productCategory.setGuaranteeDaysMin(productCategoryGuaranteeDaysMin);
		}
		save(productCategory);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setIsActive(true);
		product.setName("Product");
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		if (productGuaranteeDaysMin != null)
		{
			product.setGuaranteeDaysMin(productGuaranteeDaysMin);
		}
		if (productGuaranteeMonths != null)
		{
			product.setGuaranteeMonths(productGuaranteeMonths);
		}
		save(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}
}
