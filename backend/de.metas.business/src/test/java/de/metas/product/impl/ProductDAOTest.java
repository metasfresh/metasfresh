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
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class ProductDAOTest
{
	private IProductDAO productDAO;
	private I_M_Product productWithMinGuaranteeDays;
	private I_M_Product productWithMinGuarantee36Months;
	private I_M_Product productWithMinGuaranteeDaysOnCategory;
	private I_M_Product productWithMinGuarantee12Months;
	private I_M_Product productWithMinGuarantee24Months;
	private I_M_Product productWithMinGuarantee60Months;

	@BeforeEach
	public void init() {
		AdempiereTestHelper.get().init();
		productDAO = Services.get(IProductDAO.class);
		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
		Env.setOrgId(ctx, AdempiereTestHelper.createOrgWithTimeZone());

		setUpProducts();
	}

	@Test
	public void testGetProductGuaranteeDaysMinFallbackProductCategory() {

		int expectedGuaranteeDays = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(ProductId.ofRepoId(productWithMinGuaranteeDays.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(10);

		expectedGuaranteeDays = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(ProductId.ofRepoId(productWithMinGuarantee36Months.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(ProductDAO.THREE_YEAR_DAYS);

		expectedGuaranteeDays = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(ProductId.ofRepoId(productWithMinGuaranteeDaysOnCategory.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(5);
	}

	@Test
	public void testGetGuaranteeMonthsInDays() {
		int expectedGuaranteeDays = productDAO.getGuaranteeMonthsInDays(ProductId.ofRepoId(productWithMinGuarantee12Months.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(ProductDAO.ONE_YEAR_DAYS);

		expectedGuaranteeDays = productDAO.getGuaranteeMonthsInDays(ProductId.ofRepoId(productWithMinGuarantee24Months.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(ProductDAO.TWO_YEAR_DAYS);

		expectedGuaranteeDays = productDAO.getGuaranteeMonthsInDays(ProductId.ofRepoId(productWithMinGuarantee36Months.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(ProductDAO.THREE_YEAR_DAYS);

		expectedGuaranteeDays = productDAO.getGuaranteeMonthsInDays(ProductId.ofRepoId(productWithMinGuarantee60Months.getM_Product_ID()));
		assertThat(expectedGuaranteeDays).isEqualTo(ProductDAO.FIVE_YEAR_DAYS);
	}

	private void setUpProducts() {
		final I_M_Product_Category prodCat = newInstance(I_M_Product_Category.class);
		prodCat.setIsActive(true);
		prodCat.setName("TestCat");
		prodCat.setGuaranteeDaysMin(5);
		save(prodCat);

		productWithMinGuaranteeDays = newInstance(I_M_Product.class);
		productWithMinGuaranteeDays.setIsActive(true);
		productWithMinGuaranteeDays.setName("Product1");
		productWithMinGuaranteeDays.setGuaranteeDaysMin(10);
		productWithMinGuaranteeDays.setGuaranteeMonths(X_M_Product.GUARANTEEMONTHS_36);
		productWithMinGuaranteeDays.setM_Product_Category_ID(prodCat.getM_Product_Category_ID());
		save(productWithMinGuaranteeDays);

		productWithMinGuarantee36Months = newInstance(I_M_Product.class);
		productWithMinGuarantee36Months.setIsActive(true);
		productWithMinGuarantee36Months.setName("Product2");
		productWithMinGuarantee36Months.setGuaranteeMonths(X_M_Product.GUARANTEEMONTHS_36);
		productWithMinGuarantee36Months.setM_Product_Category_ID(prodCat.getM_Product_Category_ID());
		save(productWithMinGuarantee36Months);

		productWithMinGuarantee24Months = newInstance(I_M_Product.class);
		productWithMinGuarantee24Months.setIsActive(true);
		productWithMinGuarantee24Months.setName("Product24");
		productWithMinGuarantee24Months.setGuaranteeMonths(X_M_Product.GUARANTEEMONTHS_24);
		productWithMinGuarantee24Months.setM_Product_Category_ID(prodCat.getM_Product_Category_ID());
		save(productWithMinGuarantee24Months);

		productWithMinGuarantee12Months = newInstance(I_M_Product.class);
		productWithMinGuarantee12Months.setIsActive(true);
		productWithMinGuarantee12Months.setName("Product12");
		productWithMinGuarantee12Months.setGuaranteeMonths(X_M_Product.GUARANTEEMONTHS_12);
		save(productWithMinGuarantee12Months);

		productWithMinGuaranteeDaysOnCategory = newInstance(I_M_Product.class);
		productWithMinGuaranteeDaysOnCategory.setIsActive(true);
		productWithMinGuaranteeDaysOnCategory.setName("Product3");
		productWithMinGuaranteeDaysOnCategory.setM_Product_Category_ID(prodCat.getM_Product_Category_ID());
		save(productWithMinGuaranteeDaysOnCategory);

		productWithMinGuarantee60Months = newInstance(I_M_Product.class);
		productWithMinGuarantee60Months.setIsActive(true);
		productWithMinGuarantee60Months.setName("Product21");
		productWithMinGuarantee60Months.setGuaranteeMonths(X_M_Product.GUARANTEEMONTHS_60);
		save(productWithMinGuarantee60Months);
	}
}
