package de.metas.handlingunits.ordercandidate.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PriceList;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValdiator;
import de.metas.product.IProductPA;

public class OLCandPIIPValidator implements IOLCandValdiator
{
	/**
	 * Validates
	 * <ul>
	 * <li>the UOM conversion; we will need convertToProductUOM in order to get the QtyOrdered in the order line.
	 * <li>the pricing into to the PIIP's packing material product
	 * </ul>
	 */
	@Override
	public boolean validate(final I_C_OLCand olCand)
	{
		try
		{
			final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);

			// 1.
			// Run calculate QtyPacks just to make sure everything is ok.
			// In case of any errors, an exception will be thrown
			final OLCandHUPackingAware huPackingWare = new OLCandHUPackingAware(olCand);
			huPackingAwareBL.calculateQtyPacks(huPackingWare);

			// 2.
			// If there is a PIIP, then verify that there is pricing info for the packing material. Otherwise, completing the order will fail later on.
			if (huPackingWare.getM_HU_PI_Item_Product_ID() > 0)
			{
				IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
				final List<I_M_HU_PackingMaterial> packingMaterials = packingMaterialDAO.retrievePackingMaterials(huPackingWare.getM_HU_PI_Item_Product());
				for (I_M_HU_PackingMaterial pm : packingMaterials)
				{
					final int packingMaterialProductId = pm.getM_Product_ID();
					checkForPrice(olCand, packingMaterialProductId);
				}
			}
		}
		catch (final AdempiereException e)
		{
			olCand.setErrorMsg(e.getLocalizedMessage());
			olCand.setIsError(true);
			return false;
		}
		return true;
	}

	private void checkForPrice(final I_C_OLCand olCand, final int packingMaterialProductId)
	{
		final int pricingSystemId = olCand.getM_PricingSystem_ID();

		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		final Timestamp datePromisedEffective = olCandEffectiveValuesBL.getDatePromisedEffective(olCand);
		final int bill_Location_ID = olCandEffectiveValuesBL.getBill_Location_Effective_ID(olCand);

		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final I_M_PriceList pl = Services.get(IProductPA.class).retrievePriceListByPricingSyst(ctx, pricingSystemId, bill_Location_ID, true, trxName);
		if (pl == null)
		{
			throw new AdempiereException("@PriceList@ @NotFound@: @M_PricingSystem@ " + pricingSystemId + ", @Bill_Location@ " + bill_Location_ID);
		}

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setC_BPartner_ID(olCand.getBill_BPartner_ID());
		pricingCtx.setSOTrx(true);
		pricingCtx.setQty(BigDecimal.ONE); // we don't care for the actual quantity we just want to verify that there is a price

		pricingCtx.setM_PricingSystem_ID(pricingSystemId);
		pricingCtx.setM_PriceList_ID(pl.getM_PriceList_ID());
		pricingCtx.setM_Product_ID(packingMaterialProductId);
		pricingCtx.setPriceDate(datePromisedEffective);
		pricingCtx.setC_Currency_ID(olCand.getC_Currency_ID());

		final IPricingResult pricingResult = Services.get(IPricingBL.class).calculatePrice(pricingCtx);
		if (pricingResult == null || !pricingResult.isCalculated())
		{
			final int documentLineNo = -1; // not needed, the msg will be shown in the line itself
			throw new ProductNotOnPriceListException(pricingCtx, documentLineNo);
		}
	}
}
