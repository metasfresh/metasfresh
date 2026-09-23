package de.metas.product.impl;

import de.metas.gs1.GTIN;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static de.metas.product.IProductBL.SYSCONFIG_ENFORCE_PURCHASE_SALES_FLAGS;
import static de.metas.util.Services.get;
import static org.assertj.core.api.Assertions.*;

class ProductBLTest
{
	private IProductBL productBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		productBL = de.metas.util.Services.get(IProductBL.class);

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);
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
		assertThatThrownBy(() -> productBL.assertPurchasable(notPurchased))
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());
		assertThatCode(() -> productBL.assertPurchasable(createProduct(true, true))).doesNotThrowAnyException();
	}

	@Test
	void assertSellable_throwsWhenNotSold()
	{
		final ProductId notSold = createProduct(true, false);
		assertThatThrownBy(() -> productBL.assertSellable(notSold))
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());
		assertThatCode(() -> productBL.assertSellable(createProduct(true, true))).doesNotThrowAnyException();
	}

	@Test
	void isPurchaseSalesEnforcementEnabled_defaultFalse()
	{
		// No SysConfig row set → must return false (default off)
		assertThat(productBL.isPurchaseSalesEnforcementEnabled(ClientId.SYSTEM, OrgId.ANY)).isFalse();
	}

	@Test
	void isPurchaseSalesEnforcementEnabled_trueWhenSysConfigSetToTrue()
	{
		get(ISysConfigBL.class).setValue(SYSCONFIG_ENFORCE_PURCHASE_SALES_FLAGS, true, ClientId.SYSTEM, OrgId.ANY);
		assertThat(productBL.isPurchaseSalesEnforcementEnabled(ClientId.SYSTEM, OrgId.ANY)).isTrue();
	}

	@Nested
	class getProductIdByGTIN
	{
		private ProductId product(final String value, final String gtin, final String ean13ProductCode)
		{
			final I_M_Product p = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			p.setValue(value);
			p.setGTIN(gtin);
			p.setEAN13_ProductCode(ean13ProductCode);
			InterfaceWrapperHelper.saveRecord(p);
			return ProductId.ofRepoId(p.getM_Product_ID());
		}

		@Test
		void prefix28_resolvesViaEAN13ProductCode_notValuePrefix() // value "XXXX" rules out the Value route
		{
			final ProductId p = product("XXXX", null, "59414");
			assertThat(productBL.getProductIdByGTIN(GTIN.ofString("2859414004825"))).contains(p);
		}

		@Test
		void prefix29_resolvesViaEAN13ProductCode()
		{
			final ProductId p = product("YYYY", null, "1234");
			assertThat(productBL.getProductIdByGTIN(GTIN.ofString("2912345005009"))).contains(p);
		}

		@Test
		void prefix28_withoutProductCode_resolvesViaUniqueValuePrefix() // no EAN13_ProductCode set; falls back to the Value-startsWith supplier
		{
			final ProductId p = product("594143", null, null);
			assertThat(productBL.getProductIdByGTIN(GTIN.ofString("2859414004825"))).contains(p);
		}

		@Test
		void prefix28_productCodeWinsOverValuePrefix() // ordering of suppliers
		{
			final ProductId byCode = product("XXXX", null, "59414");
			product("594143", null, null);
			assertThat(productBL.getProductIdByGTIN(GTIN.ofString("2859414004825"))).contains(byCode);
		}

		@Test
		void fixedPrefix_resolvesByGTIN() // exact M_Product.GTIN match
		{
			final ProductId p = product("F1", "7617027667210", null);
			assertThat(productBL.getProductIdByGTIN(GTIN.ofString("7617027667210"))).contains(p);
		}

		@Test
		void fixedPrefix_fallsBackToEAN13ProductCode_whenNoGTINMatch() // fixed-prefix GTIN, no exact GTIN match: falls back to EAN13_ProductCode
		{
			// "702766721" = barcode.substring(3, 12) (EAN13Parser#parseStandardProduct) -- same value
			// pinned by the pre-existing EAN13Test.StandardProductCodes.happyCase2 for this exact barcode.
			final ProductId p = product("F2", null, "702766721");
			assertThat(productBL.getProductIdByGTIN(GTIN.ofString("7617027667210"))).contains(p);
		}
	}
}
