package org.adempiere.pricing.spi.impl.rules;

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
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.CalculateDiscountRequest;
import org.adempiere.pricing.api.DiscountResult;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.IPricingRule;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

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

		if (pricingCtx.getC_BPartner_ID() <= 0)
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
		final int bpartner_ID = pricingCtx.getC_BPartner_ID();
		final int productID = pricingCtx.getM_Product_ID();
		final boolean isSOTrx = pricingCtx.isSOTrx();
		//
		boolean isUseDiscountSchema = false;

		final Properties ctx = pricingCtx.getCtx();
		final String trxName = pricingCtx.getTrxName();

		final I_C_BPartner partner = InterfaceWrapperHelper.create(ctx, bpartner_ID, I_C_BPartner.class, trxName);

		BigDecimal flatDiscount = partner.getFlatDiscount();

		if (flatDiscount == null)
		{
			flatDiscount = BigDecimal.ZERO;
		}

		final I_M_DiscountSchema discountSchema = Services.get(IMDiscountSchemaBL.class).getDiscountSchemaForPartner(partner, isSOTrx);

		if (discountSchema == null)
		{
			return;
		}

		// the discount schema was found, therefore it will be used
		isUseDiscountSchema = true;

		// metas us1064
		/*
		 * m_PriceStd = sd.calculatePrice(m_Qty, m_PriceStd, m_M_Product_ID, m_M_Product_Category_ID, FlatDiscount);
		 */

		// 08660
		// In case we are dealing with an asi aware object, make sure the value(s) from that asi
		// are also taken into account when the discount is calculated

		final CalculateDiscountRequest request;

		final IAttributeSetInstanceAware asiAware = Services
				.get(IAttributeSetInstanceAwareFactoryService.class)
				.createOrNull(pricingCtx.getReferencedObject());

		if (asiAware == null)
		{
			request = CalculateDiscountRequest.builder()
					.schema(discountSchema)
					.qty(pricingCtx.getQty())
					.Price(result.getPriceStd())
					.M_Product_ID(productID)
					.M_Product_Category_ID(result.getM_Product_Category_ID())
					.instances(null)
					.bPartnerFlatDiscount(flatDiscount)
					.pricingCtx(pricingCtx)
					.build();
		}

		else
		{
			final I_M_AttributeSetInstance asi = asiAware.getM_AttributeSetInstance();

			final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(asi);

			request = CalculateDiscountRequest.builder()
					.schema(discountSchema)
					.qty(pricingCtx.getQty())
					.Price(result.getPriceStd())
					.M_Product_ID(productID)
					.M_Product_Category_ID(result.getM_Product_Category_ID())
					.instances(instances)
					.bPartnerFlatDiscount(flatDiscount)
					.pricingCtx(pricingCtx)
					.build();
		}

		final DiscountResult disccountResult = Services.get(IMDiscountSchemaBL.class).calculateDiscount(request);

		result.setUsesDiscountSchema(isUseDiscountSchema);
		result.setM_DiscountSchema_ID(discountSchema.getM_DiscountSchema_ID());
		result.setDiscount(disccountResult.getDiscount());
		result.setC_PaymentTerm_ID(disccountResult.getC_PaymentTerm_ID());
		result.setM_DiscountSchemaBreak_ID(disccountResult.getDiscountSchemaBreakId());
		
		final BigDecimal priceStdOverride = disccountResult.getPriceStdOverride();
		final BigDecimal priceListOverride = disccountResult.getPriceListOverride();
		final BigDecimal priceLimitOverride = disccountResult.getPriceLimitOverride();
		
		if(priceStdOverride != null)
		{
			result.setPriceStd(priceStdOverride);
		}
		
		if(priceListOverride != null)
		{
			result.setPriceList(priceListOverride);
		}
		
		if(priceLimitOverride != null)
		{
			result.setPriceLimit(priceLimitOverride);
		}
		// metas us1064 end
	}
}
