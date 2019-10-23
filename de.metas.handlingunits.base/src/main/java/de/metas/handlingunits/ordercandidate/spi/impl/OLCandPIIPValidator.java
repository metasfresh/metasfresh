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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OLCandHUPackingAware;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * @task 08147: validate if the C_OLCand's PIIP is OK
 */
@Component
public class OLCandPIIPValidator implements IOLCandValidator
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
			// Run calculate QtyTU just to make sure everything is ok.
			// In case of any errors, an exception will be thrown
			final OLCandHUPackingAware huPackingWare = new OLCandHUPackingAware(olCand);
			huPackingAwareBL.calculateQtyTU(huPackingWare);

			// 2.
			// If there is a PIIP, then verify that there is pricing info for the packing material. Otherwise, completing the order will fail later on.
			final I_M_HU_PI_Item_Product huPIItemProduct = extractHUPIItemProductOrNull(huPackingWare);
			if (huPIItemProduct != null)
			{
				IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
				final List<I_M_HU_PackingMaterial> packingMaterials = packingMaterialDAO.retrievePackingMaterials(huPIItemProduct);
				for (I_M_HU_PackingMaterial pm : packingMaterials)
				{
					final ProductId packingMaterialProductId = ProductId.ofRepoIdOrNull(pm.getM_Product_ID());
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

	private I_M_HU_PI_Item_Product extractHUPIItemProductOrNull(final IHUPackingAware huPackingAware)
	{
		final IHUPIItemProductBL piPIItemProductBL = Services.get(IHUPIItemProductBL.class);

		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(huPackingAware.getM_HU_PI_Item_Product_ID());
		return piItemProductId != null
				? piPIItemProductBL.getById(piItemProductId)
				: null;
	}

	private void checkForPrice(final I_C_OLCand olCand, final ProductId packingMaterialProductId)
	{
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(olCand.getM_PricingSystem_ID());

		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		final LocalDate datePromisedEffective = TimeUtil.asLocalDate(olCandEffectiveValuesBL.getDatePromised_Effective(olCand));
		final BPartnerLocationId billBPLocationId = olCandEffectiveValuesBL.getBillLocationEffectiveId(olCand);

		final PriceListId plId = Services.get(IPriceListDAO.class).retrievePriceListIdByPricingSyst(pricingSystemId, billBPLocationId, SOTrx.SALES);
		if (plId == null)
		{
			throw new AdempiereException("@PriceList@ @NotFound@: @M_PricingSystem@ " + pricingSystemId + ", @Bill_Location@ " + billBPLocationId);
		}

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setBPartnerId(BPartnerId.ofRepoIdOrNull(olCand.getBill_BPartner_ID()));
		pricingCtx.setSOTrx(SOTrx.SALES);
		pricingCtx.setQty(BigDecimal.ONE); // we don't care for the actual quantity we just want to verify that there is a price

		pricingCtx.setPricingSystemId(pricingSystemId);
		pricingCtx.setPriceListId(plId);
		pricingCtx.setProductId(packingMaterialProductId);
		pricingCtx.setPriceDate(datePromisedEffective);
		pricingCtx.setCurrencyId(CurrencyId.ofRepoId(olCand.getC_Currency_ID()));

		final IPricingResult pricingResult = Services.get(IPricingBL.class).calculatePrice(pricingCtx);
		if (pricingResult == null || !pricingResult.isCalculated())
		{
			final int documentLineNo = -1; // not needed, the msg will be shown in the line itself
			throw new ProductNotOnPriceListException(pricingCtx, documentLineNo);
		}
	}
}
