package de.metas.ui.web.order.sales.pricingConditions.view;

import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ViewFactory(windowId = ProductPricingConditionsViewFactory.WINDOW_ID_STRING)
public class ProductPricingConditionsViewFactory extends PricingConditionsViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "productPricingConditions";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	public ProductPricingConditionsViewFactory()
	{
		super(WINDOW_ID);
	}

	@Override
	protected PricingConditionsRowData createPricingConditionsRowData(final CreateViewRequest request)
	{
		final Set<Integer> productIds = request.getFilterOnlyIds();
		Check.assumeNotEmpty(productIds, "productIds is not empty");

		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final Set<ProductAndCategoryId> productAndCategoryIds = productsRepo.retrieveProductCategoriesByProductIds(productIds);

		final int adClientId = Env.getAD_Client_ID();

		return preparePricingConditionsRowData()
				.pricingConditionsBreaksExtractor(pricingConditions -> pricingConditions.streamBreaksMatchingAnyOfProducts(productAndCategoryIds))
				.priceNetCalculator(new PriceNetCalculator()) // TODO
				.adClientId(adClientId)
				.load();
	}

}
