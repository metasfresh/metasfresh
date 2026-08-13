/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.quickinput.field;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;

@Component
public class PackingItemProductFieldHelper
{
	private final IHUPIItemProductDAO huPIItemProductsRepo = Services.get(IHUPIItemProductDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public Optional<I_M_HU_PI_Item_Product> getDefaultPackingMaterial(@NonNull final DefaultPackingItemCriteria defaultPackingItemCriteria)
	{
		//try to load the packing item defined at price list level
		final Optional<I_M_HU_PI_Item_Product> defaultPIProduct = getDefaultPackingMaterialFromPriceList(defaultPackingItemCriteria);
		if (defaultPIProduct.isPresent())
		{
			return defaultPIProduct;
		}

		if (!SOTrx.SALES.equals(defaultPackingItemCriteria.getSoTrx()))
		{
			// Purchase Orders: only the Packing Instructions which are set on Purchase Product Prices are used.
			// Therefore at this step we return nothing.
			return Optional.empty();
		}

		if (isEnforcePrecisePricePerHUItemProduct(defaultPackingItemCriteria.getClientId()))
		{
			// The price list step above already returned every packing instruction that a product price of
			// this price list version references. So the IsDefaultForProduct fallback below could only ever
			// yield one that no product price references -- which is exactly what this setting forbids, and
			// such a packing instruction makes the order line uncompletable ("Produkt ist nicht auf der
			// Preisliste"), because pricing matches on it.
			return Optional.empty();
		}

		// Sales Orders: if no Packing Instruction is set on Product Price, check the default Packing Item for Product (set in CU-TU Allocations)
		return huPIItemProductsRepo.retrieveDefaultForProduct(defaultPackingItemCriteria.getProductId(),
				defaultPackingItemCriteria.getBPartnerLocationId().getBpartnerId(), defaultPackingItemCriteria.getDate());
	}

	private Optional<I_M_HU_PI_Item_Product> getDefaultPackingMaterialFromPriceList(@NonNull final DefaultPackingItemCriteria defaultPackingItemCriteria)
	{

		final PriceListId priceListId = Optional.ofNullable(defaultPackingItemCriteria.getPriceListId())
				.orElseGet(() -> getPriceListIdFor(defaultPackingItemCriteria));

		if (priceListId == null)
		{
			return Optional.empty();
		}

		final I_M_PriceList_Version priceListVersion = priceListsRepo
				.retrievePriceListVersionOrNull(priceListId, defaultPackingItemCriteria.getDate(), null);

		if (priceListVersion == null)
		{
			return Optional.empty();
		}

		final ClientId clientId = defaultPackingItemCriteria.getClientId();
		final boolean enforcePrecisePricePerHUItemProduct = isEnforcePrecisePricePerHUItemProduct(clientId);

		// TODO: check ASI too
		final IHUPIItemProductQuery queryVO = huPIItemProductsRepo.createHUPIItemProductQuery();
		queryVO.setM_Product_ID(defaultPackingItemCriteria.getProductId().getRepoId());
		queryVO.setBPartnerId(defaultPackingItemCriteria.getBPartnerLocationId().getBpartnerId());
		queryVO.setAllowVirtualPI(false);
		queryVO.setDate(defaultPackingItemCriteria.getDate());
		queryVO.setAllowAnyProduct(false);
		queryVO.setAllowAnyPartner(false);
		if (enforcePrecisePricePerHUItemProduct)
		{
			queryVO.setPriceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
		}
		final List<I_M_HU_PI_Item_Product> itemProducts = huPIItemProductsRepo.retrieveHUItemProducts(Env.getCtx(), queryVO, ITrx.TRXNAME_ThreadInherited);
		return itemProducts.stream().findFirst();
	}

	/**
	 * @return {@code true} if a packing instruction may only be auto-defaulted when a product price of the
	 *         relevant price list version references it. Defaults to {@code false} when unset.
	 */
	private boolean isEnforcePrecisePricePerHUItemProduct(@NonNull final ClientId clientId)
	{
		return sysConfigBL.getBooleanValue(
				IHUPIItemProductBL.SYSCONFIG_EnforcePrecisePricePerHUItemProduct, false, clientId.getRepoId());
	}

	@Nullable
	private PriceListId getPriceListIdFor(@NonNull final DefaultPackingItemCriteria defaultPackingItemCriteria)
	{
		return priceListsRepo.retrievePriceListIdByPricingSyst(
				defaultPackingItemCriteria.getPricingSystemId(),
				defaultPackingItemCriteria.getBPartnerLocationId(),
				defaultPackingItemCriteria.getSoTrx());
	}
}
