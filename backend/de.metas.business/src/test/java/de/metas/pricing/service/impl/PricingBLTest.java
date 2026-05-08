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

package de.metas.pricing.service.impl;

import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static de.metas.money.CurrencyId.ofRepoId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class PricingBLTest
{
	private PricingTestHelper helper;
	protected PricingBL pricingBL = new PricingBL();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new PricingTestHelper();
	}

	/**
	 * UOM conversion: one KRT contains 4 PCE
	 * The pricing result is 2€ per PCE and is then converted to KRT
	 * As each KRT has 4PCE in it, we expect 2€*4=8€ per KRT
	 */
	@Test
	public void convertResultToContextUOMIfNeeded_Test()
	{
		//given
		final I_C_UOM testUom_KRT = newInstance(I_C_UOM.class);
		saveRecord(testUom_KRT);
		final UomId uomIdKRT = UomId.ofRepoId(testUom_KRT.getC_UOM_ID());

		final I_C_UOM testUom_Each = newInstance(I_C_UOM.class);
		saveRecord(testUom_Each);
		final UomId uomIdEach = UomId.ofRepoId(testUom_Each.getC_UOM_ID());

		final I_M_Product product = helper.getDefaultProduct();

		final I_C_UOM_Conversion uomConversion = newInstance(I_C_UOM_Conversion.class);
		uomConversion.setM_Product_ID(product.getM_Product_ID());
		uomConversion.setC_UOM_ID(uomIdEach.getRepoId());
		uomConversion.setC_UOM_To_ID(uomIdKRT.getRepoId());
		uomConversion.setMultiplyRate(BigDecimal.valueOf(0.25));
		uomConversion.setDivideRate(BigDecimal.valueOf(4));
		saveRecord(uomConversion);

		final LocalDate localDate = LocalDate.of(2021, Month.SEPTEMBER, 20);

		final IPricingResult result = PricingResult.builder()
				.priceDate(localDate)
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.currencyId(ofRepoId(PricingTestHelper.C_Currency_ID_EUR))
				.build();

		result.setPriceStd(BigDecimal.valueOf(2));
		result.setPriceList(BigDecimal.valueOf(3));
		result.setPriceLimit(BigDecimal.valueOf(4));
		result.setPriceUomId(uomIdEach);

		final IEditablePricingContext pricingCtx = helper.createPricingContext();
		pricingCtx.setUomId(uomIdKRT);
		pricingCtx.setConvertPriceToContextUOM(true);

		//when
		pricingBL.convertResultToContextUOMIfNeeded(result, pricingCtx);

		//then
		assertThat(result.getPriceUomId()).isEqualTo(uomIdKRT);
		assertThat(result.getPriceStd()).isEqualTo(BigDecimal.valueOf(8));
		assertThat(result.getPriceList()).isEqualTo(BigDecimal.valueOf(12));
		assertThat(result.getPriceLimit()).isEqualTo(BigDecimal.valueOf(16));
	}
}
