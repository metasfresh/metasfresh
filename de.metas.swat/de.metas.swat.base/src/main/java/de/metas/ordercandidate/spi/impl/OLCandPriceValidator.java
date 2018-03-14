package de.metas.ordercandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Services;
import org.compiere.model.MUOM;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;

/**
 * Validates and sets the given OLCand's pricing data.
 *
 */
@Component
public class OLCandPriceValidator implements IOLCandValidator
{
	private static final transient Logger logger = LogManager.getLogger(OLCandPriceValidator.class);

	/**
	 * Dynamic attribute name used to pass on the pricing result obtained by this class to potential listeners like {@link OLCandPricingASIListener}.
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	/* package */static final ModelDynAttributeAccessor<I_C_OLCand, IPricingResult> DYNATTR_OLCAND_PRICEVALIDATOR_PRICING_RESULT =
			new ModelDynAttributeAccessor<>( OLCandPriceValidator.class.getSimpleName() + "#pricingResult", IPricingResult.class);

	@Override
	public boolean validate(final I_C_OLCand olCand)
	{
		olCand.setErrorMsg(null);
		olCand.setIsError(false);

		if (olCand.isManualPrice())
		{
			// Set the price actual as the price entered
			olCand.setPriceActual(olCand.getPriceEntered());

			// still, make sure that we have a currency set
			if (olCand.getC_Currency_ID() <= 0)
			{
				olCand.setIsError(true);
				final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);

				final String msg = "@NotFound@ @C_Currency@";

				olCand.setErrorMsg(Services.get(IMsgBL.class).parseTranslation(ctx, msg));
				return false;
			}

			// set the internal pricing info for the user's information, if we have it
			final boolean setErrorIfNoResult = false;
			final IPricingResult pricingResult = getPricingResult(olCand, setErrorIfNoResult);
			if (pricingResult != null)
			{
				olCand.setPriceInternal(pricingResult.getPriceStd());
				olCand.setPrice_UOM_Internal_ID(pricingResult.getPrice_UOM_ID());
			}

			// further validation on manual price is not needed
			return true;
		}

		// task 08072
		// before validating, unset the isserror and set the error message on null.
		// this way they will be up to date after validation
		olCand.setIsError(false);
		olCand.setErrorMsg(null);

		// task 08803: reset the ASI because we do *not* want a pre-existing ASI (from a pre-existing validation) to influence the price.
		// As it is now, the price (incl. pricing ASI) shall be determined only by the product, packaging, and the pricing master data, but not by the OLcand's current ASI values.
		// NOTE: this was introduced mainly for EDI C_OLCand imports.
		olCand.setM_AttributeSetInstance(null);

		final boolean setErrorIfNoResult = true;
		final IPricingResult pricingResult = getPricingResult(olCand, setErrorIfNoResult);
		if (pricingResult == null)
		{
			return false;
		}

		final BigDecimal priceInternal = pricingResult.getPriceStd();
		final int priceUOMInternalId = pricingResult.getPrice_UOM_ID();

		// FIXME: move this part to handlingUnits !!!
		if ("TU".equals(MUOM.get(InterfaceWrapperHelper.getCtx(olCand), priceUOMInternalId).getX12DE355()))
		{
			// this olCand has a TU/Gebinde price-UOMthat mean that despite the importated UOM may be PCE, we import UOM="TU" into our order line.
			olCand.setC_UOM_Internal_ID(priceUOMInternalId);
		}
		else
		{
			// this olCand has no TU/Gebinde price-UOM, so we just continuer with the olCand's imported UOM
			olCand.setC_UOM_Internal_ID(olCand.getC_UOM_ID());
		}

		// note: the customer's price remains as it is in the "PriceEntered" column

		// set the internal pricing info for the user's information
		olCand.setPriceInternal(priceInternal);
		olCand.setPrice_UOM_Internal_ID(priceUOMInternalId);

		olCand.setPriceActual(priceInternal);
		olCand.setC_Currency_ID(pricingResult.getC_Currency_ID());

		olCand.setM_PricingSystem_ID(pricingResult.getM_PricingSystem_ID());

		// task 08803: we provide the pricing result and expect that OLCandPricingASIListener will keep the ASI up to date
		DYNATTR_OLCAND_PRICEVALIDATOR_PRICING_RESULT.setValue(olCand, pricingResult);

		return true;
	}

	/**
	 * 
	 * @param olCand
	 * @param setErrorIfNoResult
	 * @return
	 */
	private IPricingResult getPricingResult(final I_C_OLCand olCand, final boolean setErrorIfNoResult)
	{
		try
		{
			final IOLCandBL olCandBL = Services.get(IOLCandBL.class);
			final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

			final BigDecimal qtyOverride = null;
			final int pricingSystemIdOverride = 0;
			final Timestamp datePromisedEffective = olCandEffectiveValuesBL.getDatePromisedEffective(olCand);
			final IPricingResult pricingResult = olCandBL.computePriceActual(olCand, qtyOverride, pricingSystemIdOverride, datePromisedEffective);

			return pricingResult;
		}
		catch (final AdempiereException e)
		{
			if (setErrorIfNoResult)
			{
				olCand.setErrorMsg(e.getLocalizedMessage());
				olCand.setIsError(true);

				// Warn developer that something went wrong.
				// In this way he/she can early see the issue and where it happend.
				if (Services.get(IDeveloperModeBL.class).isEnabled())
				{
					logger.warn(e.getLocalizedMessage(), e);
				}
			}
		}
		return null;
	}

}
