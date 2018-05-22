package de.metas.ui.web.order.sales.pricingConditions.view;

import java.math.BigDecimal;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.CalculatePricingConditionsResult;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.ui.web.order.sales.pricingConditions.view.PriceNetCalculator.PriceNetCalculateRequest;
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

		final PriceNetCalculator priceNetCalculator = PriceNetCalculator.builder()
				.basePriceCalculator(this::calculateBasePrice)
				.build();

		return preparePricingConditionsRowData()
				.pricingConditionsBreaksExtractor(pricingConditions -> pricingConditions.streamBreaksMatchingAnyOfProducts(productAndCategoryIds))
				.priceNetCalculator(priceNetCalculator)
				.adClientId(adClientId)
				.load();
	}

	private BigDecimal calculateBasePrice(final PriceNetCalculateRequest request)
	{
		final IPricingConditionsService pricingConditionsService = Services.get(IPricingConditionsService.class);

		final CalculatePricingConditionsResult result = pricingConditionsService.calculatePricingConditions(CalculatePricingConditionsRequest.builder()
				.forcePricingConditionsBreak(request.getPricingConditionsBreak())
				.pricingCtx(createPricingContext(request))
				.build());

		return result.getPriceStdOverride();
	}

	private IPricingContext createPricingContext(final PriceNetCalculateRequest request)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final PricingConditionsBreak pricingConditionsBreak = request.getPricingConditionsBreak();

		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		final ProductId productId = pricingConditionsBreak.getMatchCriteria().getProductId();
		pricingCtx.setM_Product_ID(productId != null ? productId.getRepoId() : -1);
		pricingCtx.setQty(BigDecimal.ONE);
		pricingCtx.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		pricingCtx.setSOTrx(request.isSOTrx());

		return pricingCtx;
	}

}
