package de.metas.contracts.pricing;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.pricing.service.impl.ProductPriceBuilder;
import lombok.Builder;
import lombok.NonNull;

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

public class SubscriptionPricingTestHelper extends PricingTestHelper
{
	private I_C_Flatrate_Conditions defautlFlatrateTermConditions;

	public SubscriptionPricingTestHelper()
	{
		defautlFlatrateTermConditions = createFlatrateTermConditions();
	}

	@Override
	protected List<String> getPricingRuleClassnamesToRegister()
	{
		return ImmutableList.copyOf(new String[] {
				"de.metas.contracts.pricing.SubscriptionPricingRule"//
				, "org.adempiere.pricing.spi.impl.rules.PriceListVersion" //
				, "org.adempiere.pricing.spi.impl.rules.Discount" //
		});
	}

	public ProductPriceBuilder newProductPriceBuilder(I_M_PriceList_Version priceListVersion)
	{
		return new ProductPriceBuilder(priceListVersion, getDefaultProduct());
	}

	public final I_C_Flatrate_Conditions createFlatrateTermConditions()
	{
		final I_C_Flatrate_Conditions flatrateConditions = newInstance(I_C_Flatrate_Conditions.class);
		flatrateConditions.setM_PricingSystem(getDefaultPricingSystem());
		save(flatrateConditions);
		return flatrateConditions;
	}

	@Builder(builderMethodName = "subscriptionPricingContextNew")
	public final IEditablePricingContext createSubscriptionPricingContext(@NonNull final I_M_PriceList priceList,
			@NonNull final I_M_PriceList_Version priceListVersion,
			@NonNull final I_C_Country country)
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setM_PricingSystem_ID(getDefaultPricingSystem().getM_PricingSystem_ID());
		pricingCtx.setM_PriceList_ID(priceList.getM_PriceList_ID());
		pricingCtx.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		pricingCtx.setM_Product_ID(getDefaultProduct().getM_Product_ID());
		pricingCtx.setReferencedObject(defautlFlatrateTermConditions);
		pricingCtx.setC_Country_ID(country.getC_Country_ID());
		pricingCtx.setC_Currency_ID(country.getC_Currency_ID());

		return pricingCtx;
	}

}
