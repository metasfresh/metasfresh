package de.metas.pricing.rules;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import javax.annotation.Nullable;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.Percent;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest.CalculatePricingConditionsRequestBuilder;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;

/**
 * Discount Calculations
 *
 * @author Jorg Janke
 * @author tobi42 metas us1064
 *         <li>calculateDiscount only calculates (retrieves) the discount, but does not alter priceStd.
 *         <li>Therefore, <code>m_PriceStd</code> is not changed from its
 *         respective productPrice.
 * @author Teo Sarca - refactory
 *
 * @version $Id: MProductPricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class Discount implements IPricingRule
{
	private final transient Logger log = LogManager.getLogger(getClass());

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!result.isCalculated())
		{
			log.debug("Cannot apply discount if the price was not calculated - {}", result);
			return false;
		}

		if (result.isDisallowDiscount())
		{
			return false;
		}

		if (pricingCtx.getBPartnerId() == null)
		{
			return false;
		}

		if (pricingCtx.getM_Product_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		//
		final CalculatePricingConditionsRequest request = createCalculatePricingConditionsRequest(pricingCtx, result);
		if (request == null)
		{
			return;
		}

		final IPricingConditionsService pricingConditionsService = Services.get(IPricingConditionsService.class);
		final PricingConditionsResult pricingConditionsResult = pricingConditionsService.calculatePricingConditions(request).orElse(null);

		result.setUsesDiscountSchema(true);
		updatePricingResultFromPricingConditionsResult(result, pricingConditionsResult);
	}

	private CalculatePricingConditionsRequest createCalculatePricingConditionsRequest(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final BPartnerId bpartnerId = pricingCtx.getBPartnerId();
		final SOTrx soTrx = pricingCtx.getSoTrx();

		final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
		final Percent bpartnerFlatDiscount = Percent.of(bpartner.getFlatDiscount());

		final int discountSchemaId = Services.get(IBPartnerBL.class).getDiscountSchemaId(bpartner, soTrx);
		if (discountSchemaId <= 0)
		{
			return null;
		}

		final CalculatePricingConditionsRequestBuilder builder = CalculatePricingConditionsRequest.builder()
				.pricingConditionsId(PricingConditionsId.ofDiscountSchemaId(discountSchemaId))
				.bpartnerFlatDiscount(bpartnerFlatDiscount)
				.pricingCtx(pricingCtx);

		if (pricingCtx.getForcePricingConditionsBreak() != null)
		{
			builder.forcePricingConditionsBreak(pricingCtx.getForcePricingConditionsBreak());
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(pricingCtx.getM_Product_ID());
			final ProductAndCategoryId productAndCategoryId = Services.get(IProductDAO.class).retrieveProductAndCategoryIdByProductId(productId);

			builder.pricingConditionsBreakQuery(PricingConditionsBreakQuery.builder()
					.qty(pricingCtx.getQty())
					.price(result.getPriceStd())
					.productAndCategoryId(productAndCategoryId)
					.attributeInstances(getAttributeInstances(pricingCtx.getReferencedObject()))
					.build());
		}

		return builder.build();
	}

	private List<I_M_AttributeInstance> getAttributeInstances(final Object pricingReferencedObject)
	{
		if (pricingReferencedObject == null)
		{
			return ImmutableList.of();
		}

		final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class)
				.createOrNull(pricingReferencedObject);
		if (asiAware == null)
		{
			return ImmutableList.of();
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asiAware.getM_AttributeSetInstance_ID());
		final List<I_M_AttributeInstance> attributeInstances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(asiId);
		return attributeInstances;
	}

	private static void updatePricingResultFromPricingConditionsResult(
			@NonNull final IPricingResult pricingResult,
			@Nullable final PricingConditionsResult pricingConditionsResult)
	{
		pricingResult.setPricingConditions(pricingConditionsResult);

		if (pricingConditionsResult == null)
		{
			return;
		}

		pricingResult.setDiscount(pricingConditionsResult.getDiscount());

		final BigDecimal priceStdOverride = pricingConditionsResult.getPriceStdOverride();
		final BigDecimal priceListOverride = pricingConditionsResult.getPriceListOverride();
		final BigDecimal priceLimitOverride = pricingConditionsResult.getPriceLimitOverride();
		if (priceStdOverride != null)
		{
			pricingResult.setPriceStd(priceStdOverride);
		}
		if (priceListOverride != null)
		{
			pricingResult.setPriceList(priceListOverride);
		}
		if (priceLimitOverride != null)
		{
			pricingResult.setPriceLimit(priceLimitOverride);
		}
	}
}
