package de.metas.handlingunits.pricing.spi.impl;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.impl.PricingTestHelper;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

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
		return ImmutableList.copyOf(new String[] {
				"de.metas.handlingunits.pricing.spi.impl.HUPricing" //
				, "de.metas.pricing.attributebased.impl.AttributePricing" //
				, "de.metas.adempiere.pricing.spi.impl.rules.ProductScalePrice" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceListVersionVB" //
				, "org.adempiere.pricing.spi.impl.rules.PriceListVersion" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceListVB" //
				// , "org.adempiere.pricing.spi.impl.rules.PriceList" //
				// , "org.adempiere.pricing.spi.impl.rules.BasePriceListVB" //
				// , "org.adempiere.pricing.spi.impl.rules.BasePriceList" //
				, "org.adempiere.pricing.spi.impl.rules.Discount" //
				// , "de.metas.procurement.base.pricing.spi.impl.ProcurementFlatrateRule" //
				, "de.metas.flatrate.pricing.spi.impl.ContractDiscount" //
		});
	}
	
	@Override
	public HUProductPriceBuilder newProductPriceBuilder()
	{
		return new HUProductPriceBuilder(defaultPriceListVerion, defaultProduct);
	}

	private I_M_HU_PI_Item_Product createM_HU_PI_Item_Product(final String name)
	{
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.create(Env.getCtx(), I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		piip.setName(name);
		piip.setM_Product(defaultProduct);
		InterfaceWrapperHelper.save(piip);
		return piip;
	}

}
