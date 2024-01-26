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
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest;
import de.metas.pricing.conditions.service.CalculatePricingConditionsRequest.CalculatePricingConditionsRequestBuilder;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
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
import org.compiere.model.I_M_DiscountSchema;
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
	private final transient Logger log = LogManager.getLogger(getClass());
	final IPricingConditionsService pricingConditionsService = Services.get(IPricingConditionsService.class);
	final IPricingConditionsRepository pricingConditionsRepository = Services.get(IPricingConditionsRepository.class);
	final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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

		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		final Percent bpartnerFlatDiscount = Percent.of(bpartner.getFlatDiscount());

		final int discountSchemaIdSchemaId = bpartnerBL.getDiscountSchemaId(bpartner, soTrx);
		final I_M_DiscountSchema discountSchemaRecord = pricingConditionsRepository.getDiscountSchemaById(discountSchemaIdSchemaId);

		boolean isDiscountSchemaApplicable = true; // by default, we apply discount

		// search for cases when the shcmena is not valid
		if (!discountSchemaRecord.isActive())
		{
			isDiscountSchemaApplicable = false;
		}
		else if (discountSchemaRecord.getValidFrom() != null)
		{
			final ZoneId timeZone = orgDAO.getTimeZone(pricingCtx.getOrgId());
			final LocalDate validfrom = TimeUtil.asLocalDate(discountSchemaRecord.getValidFrom(), timeZone);
			if (!pricingCtx.getPriceDate().isAfter(validfrom))
			{
				isDiscountSchemaApplicable = false;
			}
		}

		final PricingConditionsId pricingConditionsId;
		if (isDiscountSchemaApplicable)
		{
			pricingConditionsId = PricingConditionsId.ofRepoIdOrNull(discountSchemaIdSchemaId);
			if (pricingConditionsId == null)
			{
				return null;
			}
		}
		else
		{
			return null;
		}

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
