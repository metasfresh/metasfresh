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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest.CalculatePricingConditionsRequestBuilder;
import de.metas.pricing.conditions.service.IPricingConditionsService;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Discount Calculations
 *
 * @author Jorg Janke
 * @author tobi42 metas us1064
 * <li>calculateDiscount only calculates (retrieves) the discount, but does not alter priceStd.
 * <li>Therefore, <code>m_PriceStd</code> is not changed from its
 * respective productPrice.
 * @author Teo Sarca - refactory
 * @version $Id: MProductPricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class Discount implements IPricingRule
{
	private static final Logger log = LogManager.getLogger(Discount.class);
	private final IPricingConditionsService pricingConditionsService = Services.get(IPricingConditionsService.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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

		if (result.isDontOverrideDiscountAdvice())
		{
			log.debug("Discount should not be modified - {}", result);
			return false;
		}

		if (pricingCtx.getBPartnerId() == null)
		{
			return false;
		}

		return pricingCtx.getProductId() != null;
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

		final PricingConditionsResult pricingConditionsResult = pricingConditionsService.calculatePricingConditions(request).orElse(null);
		result.setUsesDiscountSchema(true);
		updatePricingResultFromPricingConditionsResult(result, pricingConditionsResult);
	}

	@Nullable
	private CalculatePricingConditionsRequest createCalculatePricingConditionsRequest(
			@NonNull final IPricingContext pricingCtx,
			@NonNull final IPricingResult result)
	{
		final BPartnerId bpartnerId = pricingCtx.getBPartnerId();
		final SOTrx soTrx = pricingCtx.getSoTrx();
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);
		final PricingConditionsId pricingConditionsId = PricingConditionsId.ofRepoIdOrNull(bpartnerBL.getDiscountSchemaId(bpartner, soTrx));
		if (!isPricingConditionsApplicable(pricingConditionsId, pricingCtx.getPriceDate(), pricingCtx.getOrgId()))
		{
			return null;
		}

		final Percent bpartnerFlatDiscount = Percent.of(bpartner.getFlatDiscount());
		final CalculatePricingConditionsRequestBuilder builder = CalculatePricingConditionsRequest.builder()
				.pricingConditionsId(pricingConditionsId)
				.bpartnerFlatDiscount(bpartnerFlatDiscount)
				.pricingCtx(pricingCtx);

		if (pricingCtx.getForcePricingConditionsBreak() != null)
		{
			builder.forcePricingConditionsBreak(pricingCtx.getForcePricingConditionsBreak());
		}
		else
		{
			final IProductDAO productsRepo = Services.get(IProductDAO.class);

			final ProductId productId = pricingCtx.getProductId();
			final ProductAndCategoryAndManufacturerId product = productsRepo.retrieveProductAndCategoryAndManufacturerByProductId(productId);

			final PricingConditionsBreakQuery pricingConditionsBreakQuery = PricingConditionsBreakQuery
					.builder()
					.qty(pricingCtx.getQty())
					.price(result.getPriceStd())
					.product(product)
					.attributes(getAttributes(pricingCtx))
					.build();
			builder.pricingConditionsBreakQuery(pricingConditionsBreakQuery);
		}

		return builder.build();
	}

	private boolean isPricingConditionsApplicable(
			@Nullable final PricingConditionsId pricingConditionsId,
			@NonNull final LocalDate date,
			@NonNull final OrgId orgId)
	{
		if (pricingConditionsId == null)
		{
			return false;
		}

		final PricingConditions pricingConditions = pricingConditionsService.getPricingConditionsById(pricingConditionsId);
		if (!pricingConditions.isActive())
		{
			return false;
		}

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		final LocalDate validFrom = TimeUtil.asLocalDate(pricingConditions.getValidFrom(), timeZone);
		return date.isAfter(validFrom) || date.isEqual(validFrom) ;
	}

	private ImmutableAttributeSet getAttributes(final IPricingContext pricingCtx)
	{
		final IAttributeSetInstanceAware asiAware = pricingCtx.getAttributeSetInstanceAware().orElse(null);
		if (asiAware == null)
		{
			return ImmutableAttributeSet.EMPTY;
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asiAware.getM_AttributeSetInstance_ID());
		return Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);
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
