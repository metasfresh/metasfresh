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

import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Component
public class PackingItemProductFieldHelper
{
	private final IHUPIItemProductDAO huPIItemProductsRepo = Services.get(IHUPIItemProductDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	public Optional<I_M_HU_PI_Item_Product> getDefaultPackingMaterial(@NonNull final DefaultPackingItemCriteria defaultPackingItemCriteria)
	{
		//try to load the packing item defined at price list level
		final Optional<I_M_HU_PI_Item_Product> defaultPIProduct = getDefaultPackingMaterialFromPriceList(defaultPackingItemCriteria);
		if (defaultPIProduct.isPresent())
		{
			return defaultPIProduct;
		}

		if (SOTrx.SALES.equals(defaultPackingItemCriteria.getSoTrx()))
		{
			// Sales Orders: if no Packing Instruction is set on Product Price, check the default Packing Item for Product (set in CU-TU Allocations)
			return huPIItemProductsRepo.retrieveDefaultForProduct(defaultPackingItemCriteria.getProductId(),
					defaultPackingItemCriteria.getBPartnerLocationId().getBpartnerId(), defaultPackingItemCriteria.getDate());
		}
		else
		{
			// Purchase Orders: only the Packing Instructions which are set on Purchase Product Prices are used.
			// Therefore at this step we return nothing.
			return Optional.empty();
		}
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

		// TODO: check ASI too
		final IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final IHUPIItemProductQuery queryVO = piItemProductDAO.createHUPIItemProductQuery();
		queryVO.setM_Product_ID(defaultPackingItemCriteria.getProductId().getRepoId());
		queryVO.setBPartnerId(defaultPackingItemCriteria.getBPartnerLocationId().getBpartnerId());
		queryVO.setAllowVirtualPI(false);
		queryVO.setDate(defaultPackingItemCriteria.getDate());
		queryVO.setAllowAnyProduct(false);
		queryVO.setAllowAnyPartner(false);
		queryVO.setPriceListVersionId(PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID()));
		final List<I_M_HU_PI_Item_Product> itemProducts = piItemProductDAO.retrieveHUItemProducts(Env.getCtx(), queryVO, ITrx.TRXNAME_ThreadInherited);
		return itemProducts.stream().findFirst();
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
