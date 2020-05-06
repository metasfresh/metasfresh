package org.adempiere.product.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.product.IProductBL;
import de.metas.util.Services;

public class ProductBLTest
{
	private static I_M_Product product1;

	private static I_M_Product_Category category1;

	private static I_M_AttributeSet as1;

	private Properties ctx;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

		POJOWrapper.setDefaultStrictValues(false);
	}

	@Test
	public void testGetAttributeSet_Product()
	{

		as1 = InterfaceWrapperHelper.create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(as1);

		product1 = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1.setM_AttributeSet_ID(as1.getM_AttributeSet_ID());
		InterfaceWrapperHelper.save(product1);

		final int productAS_ID = Services.get(IProductBL.class).getAttributeSetId(product1).getRepoId();

		assertThat(productAS_ID).isEqualTo(as1.getM_AttributeSet_ID());
	}

	@Test
	public void testGetAttributeSet_ProductCategory()
	{
		as1 = InterfaceWrapperHelper.create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(as1);

		category1 = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, ITrx.TRXNAME_None);
		category1.setM_AttributeSet(as1);
		InterfaceWrapperHelper.save(category1);

		product1 = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		InterfaceWrapperHelper.save(product1);

		final int productAS_ID = Services.get(IProductBL.class).getAttributeSetId(product1).getRepoId();

		assertThat(productAS_ID).isEqualTo(category1.getM_AttributeSet_ID());
	}

	@Test
	public void testGetAttributeSet_ProductCategory_And_Product()
	{

		as1 = InterfaceWrapperHelper.create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(as1);

		final I_M_AttributeSet as2 = InterfaceWrapperHelper.create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(as2);

		category1 = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, ITrx.TRXNAME_None);
		category1.setM_AttributeSet(as1);
		InterfaceWrapperHelper.save(category1);

		product1 = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1.setM_AttributeSet_ID(as2.getM_AttributeSet_ID());
		product1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		InterfaceWrapperHelper.save(product1);

		final int productAS_ID = Services.get(IProductBL.class).getAttributeSetId(product1).getRepoId();

		assertThat(productAS_ID).isEqualTo(product1.getM_AttributeSet_ID());
	}

}
