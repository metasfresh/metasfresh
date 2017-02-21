package de.metas.handlingunits.pricing.spi.impl;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.api.impl.ASIBuilder;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.handlingunits.model.I_C_OrderLine;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUPricingTests
{
	@Rule
	public AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private HUPricingTestHelper helper;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new HUPricingTestHelper();
	}

	@Test
	public void test_PriceWithAttributes_Bio_and_NonBio()
	{
		//
		// Setup prices
		helper.newProductPriceBuilder()
				.setM_HU_PI_Item_Product(helper.defaultPIItemProduct1)
				.setASI(ASIBuilder.newInstance()
						.setAttribute(helper.attr_Country, helper.attr_Country_CH)
						.setAttribute(helper.attr_Label, helper.attr_Label_Bio)
						.build())
				.setPrice(3)
				.build();
		helper.newProductPriceBuilder()
				.setM_HU_PI_Item_Product(helper.defaultPIItemProduct1)
				.setASI(ASIBuilder.newInstance()
						.setAttribute(helper.attr_Country, helper.attr_Country_CH)
						.setAttribute(helper.attr_Label, helper.attr_Label_NULL)
						.build())
				.setPrice(2)
				.build();

		//
		// Test not-Bio price
		{
			final IEditablePricingContext pricingCtx = helper.createPricingContext();
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OrderLine.class, ITrx.TRXNAME_None);
			orderLine.setM_AttributeSetInstance(ASIBuilder.newInstance()
					.setAttribute(helper.attr_Country, helper.attr_Country_CH)
					.build());
			orderLine.setM_HU_PI_Item_Product(helper.defaultPIItemProduct1);
			pricingCtx.setReferencedObject(orderLine);

			final IPricingResult result = helper.calculatePrice(pricingCtx);
			Assert.assertThat("not-Bio PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(2)));
		}

		//
		// Test Bio price
		{
			final IEditablePricingContext pricingCtx = helper.createPricingContext();
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OrderLine.class, ITrx.TRXNAME_None);
			orderLine.setM_AttributeSetInstance(ASIBuilder.newInstance()
					.setAttribute(helper.attr_Country, helper.attr_Country_CH)
					.setAttribute(helper.attr_Label, helper.attr_Label_Bio)
					.build());
			orderLine.setM_HU_PI_Item_Product(helper.defaultPIItemProduct1);
			pricingCtx.setReferencedObject(orderLine);

			final IPricingResult result = helper.calculatePrice(pricingCtx);
			Assert.assertThat("Bio PriceStd\n" + result, result.getPriceStd(), Matchers.comparesEqualTo(BigDecimal.valueOf(3)));
		}
	}
}
