/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_PriceList_Version;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
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

		//if not found, check the default packing item for product
		return huPIItemProductsRepo.retrieveDefaultForProduct( defaultPackingItemCriteria.getProductId(),
				defaultPackingItemCriteria.getBPartnerLocationId().getBpartnerId(), defaultPackingItemCriteria.getDate() );
	}

	private Optional<I_M_HU_PI_Item_Product> getDefaultPackingMaterialFromPriceList(@NonNull final DefaultPackingItemCriteria defaultPackingItemCriteria)
	{

		final PriceListId priceListId = Optional.ofNullable( defaultPackingItemCriteria.getPriceListId() )
												.orElseGet( () -> getPriceListIdFor(defaultPackingItemCriteria) );

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

		return ProductPrices
				.newQuery(priceListVersion)
				.setProductId(defaultPackingItemCriteria.getProductId())
				// TODO: check ASI too
				.list(I_M_ProductPrice.class)
				.stream()
				.map(productPrice -> HUPIItemProductId.ofRepoIdOrNone(productPrice.getM_HU_PI_Item_Product_ID()))
				.filter(id -> HUPIItemProductId.isRegular(id))
				.findFirst()
				.map(huPIItemProductsRepo::getById);
	}

	@Nullable
	private PriceListId getPriceListIdFor(@NonNull final DefaultPackingItemCriteria defaultPackingItemCriteria)
	{
		return priceListsRepo.retrievePriceListIdByPricingSyst(
				defaultPackingItemCriteria.getPricingSystemId(),
				defaultPackingItemCriteria.getBPartnerLocationId(),
				defaultPackingItemCriteria.getSoTrx() );
	}
}
