package de.metas.ui.web.quickinput.orderline;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ProductAndAttributes;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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

final class OrderLineQuickInputCallout
{
	private final IHUPIItemProductDAO huPIItemProductsRepo = Services.get(IHUPIItemProductDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	// private final IHUOrderBL huOrderBL;
	private final IBPartnerBL bpartnersService;

	@Builder
	private OrderLineQuickInputCallout(
			@NonNull final IBPartnerBL bpartnersService
	// @NonNull final IHUOrderBL huOrderBL
	)
	{
		this.bpartnersService = bpartnersService;
		// this.huOrderBL = huOrderBL;
	}

	public void onProductChanged(final ICalloutField calloutField)
	{
		final QuickInput quickInput = QuickInput.getQuickInputOrNull(calloutField);
		if (quickInput == null)
		{
			return;
		}

		updateM_HU_PI_Item_Product(quickInput);
		updateBestBeforePolicy(quickInput);
	}

	private void updateM_HU_PI_Item_Product(@NonNull final QuickInput quickInput)
	{
		if (!quickInput.hasField(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
		{
			return; // there are users whose systems don't have M_HU_PI_Item_Product_ID in their quick-input
		}

		final IOrderLineQuickInput quickInputModel = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);
		final LookupValue productLookupValue = quickInputModel.getM_Product_ID();
		if (productLookupValue == null)
		{
			return;
		}

		final ProductAndAttributes productAndAttributes = ProductLookupDescriptor.toProductAndAttributes(productLookupValue);
		final ProductId quickInputProductId = productAndAttributes.getProductId();

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);

		final I_M_HU_PI_Item_Product huPIItemProduct = getDefaultPackingMaterial(order, quickInputProductId).orElse(null);
		quickInputModel.setM_HU_PI_Item_Product(huPIItemProduct);
	}

	private void updateBestBeforePolicy(@NonNull final QuickInput quickInput)
	{
		if (!quickInput.hasField(IOrderLineQuickInput.COLUMNNAME_ShipmentAllocation_BestBefore_Policy))
		{
			return;
		}

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = bpartnersService.getBestBeforePolicy(bpartnerId);
		final IOrderLineQuickInput quickInputModel = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);
		quickInputModel.setShipmentAllocation_BestBefore_Policy(bestBeforePolicy.getCode());
	}

	private Optional<I_M_HU_PI_Item_Product> getDefaultPackingMaterial(
			@NonNull final I_C_Order order,
			@NonNull final ProductId quickInputProductId)
	{
		//
		// Check price list
		{
			final Optional<I_M_HU_PI_Item_Product> defaultPackingMaterialFromPriceList = getDefaultPackingMaterialFromPriceList(order, quickInputProductId)
					.map(huPIItemProductsRepo::getById);
			if (defaultPackingMaterialFromPriceList.isPresent())
			{
				return defaultPackingMaterialFromPriceList;
			}
		}

		//
		// Check default for product
		{
			final Optional<I_M_HU_PI_Item_Product> defaultPackingMaterialForProduct = getDefaultPackingMaterialForProduct(order, quickInputProductId);
			if (defaultPackingMaterialForProduct.isPresent())
			{
				return defaultPackingMaterialForProduct;
			}
		}

		//
		// Default
		return Optional.empty();
	}

	private Optional<HUPIItemProductId> getDefaultPackingMaterialFromPriceList(final I_C_Order order, final ProductId productId)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
		if (bpartnerLocationId == null)
		{
			return Optional.empty();
		}

		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		if (pricingSystemId == null)
		{
			return Optional.empty();
		}

		final ZonedDateTime date = TimeUtil.asZonedDateTime(order.getDatePromised());
		if (date == null)
		{
			return Optional.empty();
		}

		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		return getDefaultPackingMaterialFromPriceList(bpartnerLocationId, pricingSystemId, soTrx, date, productId);
	}

	private Optional<HUPIItemProductId> getDefaultPackingMaterialFromPriceList(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final SOTrx soTrx,
			@NonNull final ZonedDateTime date,
			@NonNull final ProductId productId)
	{
		final PriceListId priceListId = priceListsRepo.retrievePriceListIdByPricingSyst(pricingSystemId, bpartnerLocationId, soTrx);
		if (priceListId == null)
		{
			return Optional.empty();
		}

		final I_M_PriceList_Version priceListVersion = priceListsRepo.retrievePriceListVersionOrNull(priceListId, date, (Boolean)null /* processed */);
		if (priceListVersion == null)
		{
			return Optional.empty();
		}

		final ImmutableSet<HUPIItemProductId> packingMaterialIds = ProductPrices.newQuery(priceListVersion)
				.setProductId(productId)
				// TODO: check ASI too
				.list(I_M_ProductPrice.class)
				.stream()
				.map(productPrice -> HUPIItemProductId.ofRepoIdOrNone(productPrice.getM_HU_PI_Item_Product_ID()))
				.filter(HUPIItemProductId::isRegular)
				.collect(ImmutableSet.toImmutableSet());
		if (packingMaterialIds.isEmpty())
		{
			return Optional.empty();
		}

		final HUPIItemProductId packingMaterialId = packingMaterialIds.iterator().next();
		return Optional.of(packingMaterialId);
	}

	private Optional<I_M_HU_PI_Item_Product> getDefaultPackingMaterialForProduct(
			@NonNull final I_C_Order order,
			@NonNull final ProductId quickInputProductId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return Optional.empty();
		}

		final ZonedDateTime date = TimeUtil.asZonedDateTime(order.getDatePromised());
		if (date == null)
		{
			return Optional.empty();
		}

		return huPIItemProductsRepo.retrieveDefaultForProduct(quickInputProductId, bpartnerId, date);
	}
}
