package org.adempiere.product.service.impl;

import de.metas.product.IProductBL;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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

	/**
	 * Verifies that we do not return the product's attribute set.
	 * The product's attribute set is there just for the product's ASI.
	 */
	@Test
	public void testGetAttributeSet_Product()
	{

		as1 = create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		save(as1);

		product1 = create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1.setM_AttributeSet_ID(as1.getM_AttributeSet_ID());
		save(product1);

		final AttributeSetId productAS_ID = Services.get(IProductBL.class).getAttributeSetId(product1);

		assertThat(productAS_ID).isEqualTo(AttributeSetId.NONE);
	}

	@Test
	public void testGetAttributeSet_ProductCategory()
	{
		as1 = create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		save(as1);

		category1 = create(ctx, I_M_Product_Category.class, ITrx.TRXNAME_None);
		category1.setM_AttributeSet(as1);
		save(category1);

		product1 = create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		save(product1);

		final int productAS_ID = Services.get(IProductBL.class).getAttributeSetId(product1).getRepoId();

		assertThat(productAS_ID).isEqualTo(category1.getM_AttributeSet_ID());
	}

	/** 
	 * Verifies that we do not fall back to the product's attribute set.
	 * The product's attribute set is there just for the product's ASI.
	 */
	@Test
	public void testGetAttributeSet_ProductCategory_And_Product()
	{
		as1 = create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		save(as1);

		final I_M_AttributeSet as2 = create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		save(as2);

		category1 = create(ctx, I_M_Product_Category.class, ITrx.TRXNAME_None);
		category1.setM_AttributeSet(as1);
		save(category1);

		product1 = create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product1.setM_AttributeSet_ID(as2.getM_AttributeSet_ID());
		product1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
		save(product1);

		final int productAS_ID = Services.get(IProductBL.class).getAttributeSetId(product1).getRepoId();

		assertThat(productAS_ID).isEqualTo(category1.getM_AttributeSet_ID());
	}

}
