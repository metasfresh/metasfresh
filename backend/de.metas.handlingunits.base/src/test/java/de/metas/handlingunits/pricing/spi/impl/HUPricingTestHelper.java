package de.metas.handlingunits.pricing.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.contracts.pricing.ContractDiscount;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.pricing.rules.price_list_version.PriceListVersionConfiguration;
import de.metas.pricing.service.impl.PricingTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

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

public class HUPricingTestHelper extends PricingTestHelper
{
	public final I_M_HU_PI_Item_Product defaultPIItemProduct1;
	public final I_M_HU_PI_Item_Product defaultPIItemProduct2;

	static
	{
		de.metas.handlingunits.model.validator.Main.setupPricing();
	}

	public HUPricingTestHelper()
	{
		defaultPIItemProduct1 = createM_HU_PI_Item_Product("PI1");
		defaultPIItemProduct2 = createM_HU_PI_Item_Product("PI2");
	}

	@Override
	protected List<String> getPricingRuleClassnamesToRegister()
	{
		HUPricing.install();
		AdempiereTestHelper.get().onCleanup("Reset PriceListVersionConfiguration", PriceListVersionConfiguration::reset);

		return ImmutableList.of(
				de.metas.pricing.rules.price_list_version.PriceListVersionPricingRule.class.getName(),
				de.metas.pricing.rules.Discount.class.getName(),
				ContractDiscount.class.getName());
	}

	@Override
	public HUProductPriceBuilder newProductPriceBuilder()
	{
		return new HUProductPriceBuilder(getDefaultPriceListVerion(), getDefaultProduct())
				.setTaxCategoryId(getTaxCategoryId());
	}

	private I_M_HU_PI_Item_Product createM_HU_PI_Item_Product(final String name)
	{
		final I_M_Product product = getDefaultProduct();
		
		final I_M_HU_PI_Item_Product piip = newInstanceOutOfTrx(I_M_HU_PI_Item_Product.class);
		piip.setName(name);
		piip.setM_Product_ID(product != null ? product.getM_Product_ID() : -1);
		InterfaceWrapperHelper.save(piip);
		return piip;
	}

}
