package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.logging.LogManager;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.IPMMPricingBL;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PMMPricingBL implements IPMMPricingBL
{
	private static final Logger logger = LogManager.getLogger(PMMPricingBL.class);

	@Override
	public void updatePricing(final IPMMPricingAware pricingAware)
	{
		// Get BPartner
		final I_C_BPartner bpartner = pricingAware.getC_BPartner();
		if (bpartner == null || bpartner.getC_BPartner_ID() <= 0)
		{
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_C_BPartner_ID + "@");
		}

		// Get Product and UOM
		final I_M_Product product = pricingAware.getM_Product();
		if (product == null)
		{
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_M_Product_ID + "@");
		}
		final I_C_UOM uom = pricingAware.getC_UOM();
		if (uom == null)
		{
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_C_UOM_ID + "@");
		}

		// Always get the pricing from masterdata.
		// We need it to be there, e.g. to know if the price is incl VAT and which VAT category to use.
		updatePriceFromPricingMasterdata(pricingAware);

		//
		// contract product: override the price amount and currency from the contract, if one is set there
		if (pricingAware.isContractedProduct())
		{
			updatePriceFromContract(pricingAware);
		}
	}

	private void updatePriceFromPricingMasterdata(final IPMMPricingAware pricingAware)
	{
		final boolean soTrx = false;
		final Properties ctx = pricingAware.getCtx();

		final I_C_BPartner bpartner = pricingAware.getC_BPartner();
		Check.assumeNotNull(bpartner, "bpartner not null"); // shall not happen
		final int bpartnerId = bpartner.getC_BPartner_ID();
		final I_M_Product product = pricingAware.getM_Product();
		final I_C_UOM uom = pricingAware.getC_UOM();
		final Timestamp date = pricingAware.getDate();

		// Pricing system
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final int pricingSystemId = bpartnerDAO.retrievePricingSystemId(ctx, bpartnerId, soTrx, ITrx.TRXNAME_ThreadInherited);
		if (pricingSystemId <= 0)
		{
			// no term and no pricing system means that we can't figure out the price
			throw new AdempiereException("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_M_PricingSystem_ID + "@ ("
					+ "@C_BPartner_ID@:" + bpartner
					+ ", @IsSOTrx@:" + soTrx
					+ ")");
		}

		// BPartner location -> Country
		final List<I_C_BPartner_Location> shipToLocations = bpartnerDAO.retrieveBPartnerShipToLocations(bpartner);
		if (shipToLocations.isEmpty())
		{
			throw new AdempiereException("@Missing@ @" + org.compiere.model.I_C_BPartner_Location.COLUMNNAME_IsShipTo + "@");
		}
		final int countryId = shipToLocations.get(0).getC_Location().getC_Country_ID();

		//
		// Fetch price from pricing engine
		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final BigDecimal qty = pricingAware.getQty();
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(product.getM_Product_ID(), bpartnerId, uom.getC_UOM_ID(), qty, soTrx);
		pricingCtx.setM_PricingSystem_ID(pricingSystemId);
		pricingCtx.setPriceDate(date);
		pricingCtx.setC_Country_ID(countryId);
		pricingCtx.setReferencedObject(pricingAware.getWrappedModel()); // important for ASI pricing

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
		if (!pricingResult.isCalculated())
		{
			throw new AdempiereException("@Missing@ @" + I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + "@: " + pricingResult);
		}

		// these will be "empty" results, if the price was not calculated
		logger.trace("Updating {} from {}", pricingAware, pricingResult);
		pricingAware.setM_PricingSystem_ID(pricingResult.getM_PricingSystem_ID());
		pricingAware.setM_PriceList_ID(pricingResult.getM_PriceList_ID());
		pricingAware.setC_Currency_ID(pricingResult.getC_Currency_ID());
		pricingAware.setPrice(pricingResult.getPriceStd());
	}

	@Override
	public boolean updatePriceFromContract(final IPMMPricingAware pricingAware)
	{
		final I_C_Flatrate_Term flatrateTerm = pricingAware.getC_Flatrate_Term();
		if (flatrateTerm == null)
		{
			throw new AdempiereException("@Missing@ @" + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "@");
		}

		final I_M_Product product = pricingAware.getM_Product();
		final I_C_UOM uom = pricingAware.getC_UOM();

		final I_C_Flatrate_DataEntry dataEntryForProduct = pricingAware.getC_Flatrate_DataEntry();
		if (dataEntryForProduct == null)
		{
			logger.info("Event is missing C_Flatrate_DataEntry: {}", pricingAware);
			return false;
		}
		
		final BigDecimal flatrateAmtPerUOM = dataEntryForProduct.getFlatrateAmtPerUOM();
		if (flatrateAmtPerUOM == null || flatrateAmtPerUOM.signum() <= 0)
		{
			logger.info("Invalid FlatrateAmtPerUOM: {} (event={})", flatrateAmtPerUOM, pricingAware);
			return false;
		}

		//
		// Convert the price
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final BigDecimal price = uomConversionBL.convertPrice(
				product,
				flatrateAmtPerUOM,
				flatrateTerm.getC_UOM(),  								// this is the flatrateAmt's UOM
				uom,  													// this is the qtyReportEvent's UOM
				flatrateTerm.getC_Currency().getStdPrecision());

		pricingAware.setC_Currency_ID(flatrateTerm.getC_Currency_ID());
		pricingAware.setPrice(price);
		return true;
	}

}
