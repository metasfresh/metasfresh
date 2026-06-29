package de.metas.product.impl;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductBLTest
{
	private IProductBL productBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		productBL = de.metas.util.Services.get(IProductBL.class);
	}

	private ProductId createProduct(final boolean purchased, final boolean sold)
	{
		final I_M_Product p = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		p.setValue("P");
		p.setName("Test Product");
		p.setIsPurchased(purchased);
		p.setIsSold(sold);
		InterfaceWrapperHelper.saveRecord(p);
		return ProductId.ofRepoId(p.getM_Product_ID());
	}

	@Test
	void isPurchased_isSold_reflectFlags()
	{
		final ProductId both = createProduct(true, true);
		assertThat(productBL.isPurchased(both)).isTrue();
		assertThat(productBL.isSold(both)).isTrue();

		final ProductId neither = createProduct(false, false);
		assertThat(productBL.isPurchased(neither)).isFalse();
		assertThat(productBL.isSold(neither)).isFalse();
	}

	@Test
	void assertPurchasable_throwsWhenNotPurchased()
	{
		final ProductId notPurchased = createProduct(false, true);
		assertThatThrownBy(() -> productBL.assertPurchasable(notPurchased)).isInstanceOf(AdempiereException.class);
		assertThatCode(() -> productBL.assertPurchasable(createProduct(true, true))).doesNotThrowAnyException();
	}

	@Test
	void assertSellable_throwsWhenNotSold()
	{
		final ProductId notSold = createProduct(true, false);
		assertThatThrownBy(() -> productBL.assertSellable(notSold)).isInstanceOf(AdempiereException.class);
		assertThatCode(() -> productBL.assertSellable(createProduct(true, true))).doesNotThrowAnyException();
	}
}
