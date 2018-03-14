package de.metas.ordercandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.order.IOrderLineBL;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandOrderDefaults;
import de.metas.ordercandidate.api.OLCandProcessorDescriptor;
import de.metas.ordercandidate.api.OLCandRegistry;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandSource;
import de.metas.ordercandidate.api.OLCandsProcessorExecutor;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandCreator;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;

public class OLCandBL implements IOLCandBL
{
	private static final Logger logger = LogManager.getLogger(OLCandBL.class);

	@Override
	public void process(final OLCandProcessorDescriptor processor)
	{
		final OLCandRegistry olCandRegistry = Adempiere.getBean(OLCandRegistry.class);
		final OLCandRepository olCandRepo = Adempiere.getBean(OLCandRepository.class);
		final OLCandSource candidatesSource = olCandRepo.getForProcessor(processor);

		OLCandsProcessorExecutor.builder()
				.processorDescriptor(processor)
				.olCandListeners(olCandRegistry.getListeners())
				.groupingValuesProviders(olCandRegistry.getGroupingValuesProviders())
				.candidatesSource(candidatesSource)
				.build()
				.process();
	}

	@Override
	public int getPricingSystemId(@NonNull final I_C_OLCand olCand, final OLCandOrderDefaults orderDefaults)
	{
		Check.assumeNotNull(olCand, "Param 'olCand' not null");

		if (olCand.getM_PricingSystem_ID() > 0)
		{
			return olCand.getM_PricingSystem_ID();
		}
		else if (orderDefaults != null && orderDefaults.getPricingSystemId() > 0)
		{
			return orderDefaults.getPricingSystemId();
		}
		else
		{
			final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

			final int bpartnerId = effectiveValuesBL.getBill_BPartner_Effective_ID(olCand);
			final boolean soTrx = true;

			final int pricingSystemId = bPartnerDAO.retrievePricingSystemId(Env.getCtx(), bpartnerId, soTrx, ITrx.TRXNAME_None);
			return pricingSystemId;
		}
	}

	@Override
	public I_C_OLCand invokeOLCandCreator(final PO po, final IOLCandCreator olCandCreator)
	{
		Check.assumeNotNull(olCandCreator, "olCandCreator is not null");

		final I_C_OLCand olCand = olCandCreator.createFrom(po);
		if (po.set_ValueOfColumn("Processed", true))
		{
			po.saveEx();
		}

		if (olCand == null)
		{
			logger.info("{} returned null for {}; Nothing to do.", olCandCreator, po);
			return null;
		}

		olCand.setAD_Table_ID(po.get_Table_ID());
		olCand.setRecord_ID(po.get_ID());

		InterfaceWrapperHelper.save(olCand);

		Services.get(IWFExecutionFactory.class).notifyActivityPerformed(po, olCand); // 03745

		return olCand;
	}

	@Override
	public IPricingResult computePriceActual(
			final I_C_OLCand olCand,
			final BigDecimal qtyOverride,
			final int pricingSystemIdOverride,
			final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setReferencedObject(olCand);

		final IPricingResult pricingResult;
		if (olCand.isManualDiscount() && olCand.isManualPrice())
		{
			// create an empty one. we won't use the pricing engine to fill it.
			pricingResult = Services.get(IPricingBL.class).createInitialResult(pricingCtx);
		}
		else
		{
			final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

			final int bill_BPartner_ID = effectiveValuesBL.getBill_BPartner_Effective_ID(olCand);

			final I_C_BPartner_Location dropShipLocation = effectiveValuesBL.getDropShip_Location_Effective(olCand);

			pricingCtx.setC_Country_ID(dropShipLocation.getC_Location().getC_Country_ID());

			final BigDecimal qty = qtyOverride != null ? qtyOverride : olCand.getQty();
			final int pricingSystemId = pricingSystemIdOverride > 0 ? pricingSystemIdOverride : getPricingSystemId(olCand, OLCandOrderDefaults.NULL);

			if (pricingSystemId <= 0)
			{
				throw new AdempiereException("@M_PricingSystem@ @NotFound@");
			}
			pricingCtx.setM_PricingSystem_ID(pricingSystemId); // set it to the context that way it will also be in the result, even if the pricing rules won't need it

			pricingCtx.setC_BPartner_ID(bill_BPartner_ID);
			pricingCtx.setQty(qty);
			pricingCtx.setPriceDate(date);
			pricingCtx.setSOTrx(true);

			pricingCtx.setDisallowDiscount(olCand.isManualDiscount());

			final I_M_PriceList pl = priceListDAO.retrievePriceListByPricingSyst(pricingSystemId, dropShipLocation, true);
			if (pl == null)
			{
				throw new AdempiereException("@M_PriceList@ @NotFound@: @M_PricingSystem@ " + pricingSystemId + ", @Bill_Location@ " + dropShipLocation.getC_BPartner_Location_ID());
			}
			pricingCtx.setM_PriceList_ID(pl.getM_PriceList_ID());
			pricingCtx.setM_Product_ID(effectiveValuesBL.getM_Product_Effective_ID(olCand));

			pricingResult = pricingBL.calculatePrice(pricingCtx);

			// Just for safety: in case the product price was not found, the code below shall not be reached.
			// The exception shall be already thrown
			// ts 2015-07-03: i think it is not, at least i don't see from where
			if (pricingResult == null || !pricingResult.isCalculated())
			{
				final int documentLineNo = -1; // not needed, the msg will be shown in the line itself
				throw new ProductNotOnPriceListException(pricingCtx, documentLineNo);
			}
		}

		final BigDecimal priceEntered;
		final BigDecimal discount;
		final int currencyId;

		if (olCand.isManualPrice())
		{
			// both price and currency need to be already set in the olCand (only a price amount doesn't make sense with an unspecified currency)
			priceEntered = olCand.getPriceEntered();
			currencyId = olCand.getC_Currency_ID();
		}
		else
		{
			priceEntered = pricingResult.getPriceStd();
			currencyId = pricingResult.getC_Currency_ID();
		}

		if (olCand.isManualDiscount())
		{
			discount = olCand.getDiscount();
		}
		else
		{
			discount = pricingResult.getDiscount();
		}

		if (currencyId <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Currency@"
					+ "\n Pricing context: " + pricingCtx
					+ "\n Pricing result: " + pricingResult);
		}

		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(ctx, currencyId);
		final BigDecimal priceActual = Services.get(IOrderLineBL.class).subtractDiscount(priceEntered, discount, currency.getStdPrecision());

		pricingResult.setPriceStd(priceActual);
		pricingResult.setDiscount(discount);

		return pricingResult;
	}
}
