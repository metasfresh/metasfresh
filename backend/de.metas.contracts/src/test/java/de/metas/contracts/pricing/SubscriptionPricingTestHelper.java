package de.metas.contracts.pricing;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.Discount;
import de.metas.pricing.rules.price_list_version.PriceListVersionPricingRule;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.pricing.service.impl.ProductPriceBuilder;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
		return ImmutableList.of(
				SubscriptionPricingRule.class.getName(),
				PriceListVersionPricingRule.class.getName(),
				Discount.class.getName());
	}

	public ProductPriceBuilder newProductPriceBuilder(I_M_PriceList_Version priceListVersion)
	{
		return new ProductPriceBuilder(priceListVersion, getDefaultProduct())
				.setTaxCategoryId(getTaxCategoryId());
	}

	public final I_C_Flatrate_Conditions createFlatrateTermConditions()
	{
		final I_C_Flatrate_Conditions flatrateConditions = newInstance(I_C_Flatrate_Conditions.class);
		flatrateConditions.setM_PricingSystem_ID(getDefaultPricingSystem().getM_PricingSystem_ID());
		save(flatrateConditions);
		return flatrateConditions;
	}

	@Builder(builderMethodName = "subscriptionPricingContextNew")
	public final IEditablePricingContext createSubscriptionPricingContext(
			@NonNull final OrgId orgId,
			@NonNull final I_M_PriceList priceList,
			@NonNull final I_M_PriceList_Version priceListVersion,
			@NonNull final I_C_Country country)
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setOrgId(orgId);
		pricingCtx.setPricingSystemId(PricingSystemId.ofRepoId(getDefaultPricingSystem().getM_PricingSystem_ID()));
		pricingCtx.setPriceListId(PriceListId.ofRepoId(priceList.getM_PriceList_ID()));
		pricingCtx.setPriceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
		pricingCtx.setProductId(ProductId.ofRepoId(getDefaultProduct().getM_Product_ID()));
		pricingCtx.setReferencedObject(defautlFlatrateTermConditions);
		pricingCtx.setCountryId(CountryId.ofRepoId(country.getC_Country_ID()));
		pricingCtx.setCurrencyId(CurrencyId.ofRepoId(country.getC_Currency_ID()));

		return pricingCtx;
	}

}
