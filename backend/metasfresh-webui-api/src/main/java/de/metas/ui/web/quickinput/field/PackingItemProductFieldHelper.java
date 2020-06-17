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

		//if not found, check the default packing item for product
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
		// piItemProductDAO.createHU_PI_Item_Product_QueryBuilder(Env.getCtx(), queryVO, ITrx.TRXNAME_ThreadInherited).create();
		final List<I_M_HU_PI_Item_Product> itemProducts = piItemProductDAO.retrieveHUItemProducts(Env.getCtx(), queryVO, ITrx.TRXNAME_ThreadInherited);
		return itemProducts.stream().findFirst();

		// final IQueryBL queryBL = Services.get(IQueryBL.class);
		//
		// final List<BPartnerId> allowedBpartnerIds = Arrays.asList(defaultPackingItemCriteria.getBPartnerLocationId().getBpartnerId(), null);
		//
		// final IQuery<I_M_HU_PI_Item_Product> queryPI = queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
		// 		.addOnlyActiveRecordsFilter()
		// 		.addInArrayFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, allowedBpartnerIds)
		// 		.create();
		//
		// final ICompositeQueryFilter<org.compiere.model.I_M_ProductPrice> queryProductPrice = queryBL.createCompositeQueryFilter(org.compiere.model.I_M_ProductPrice.class)
		// 		.addInSubQueryFilter()
		// 		.matchingColumnNames(I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID)
		// 		.subQuery(queryPI)
		// 		.end();
		//
		//
		// final ProductPriceQueryMatcher bpartnerMatcher = ProductPriceQueryMatcher.of(I_M_HU_PI_Item_Product.Table_Name + "." + I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, queryProductPrice);
		//
		// return ProductPrices
		// 		.newQuery(priceListVersion)
		// 		.setProductId(defaultPackingItemCriteria.getProductId())
		// 		.matching(bpartnerMatcher)
		// 		.list(I_M_ProductPrice.class)
		// 		.stream()
		// 		.map(productPrice -> HUPIItemProductId.ofRepoIdOrNone(productPrice.getM_HU_PI_Item_Product_ID()))
		// 		.filter(id -> HUPIItemProductId.isRegular(id))
		// 		.findFirst()
		// 		.map(huPIItemProductsRepo::getById);
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
