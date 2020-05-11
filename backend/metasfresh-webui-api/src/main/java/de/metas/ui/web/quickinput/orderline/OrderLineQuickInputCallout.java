package de.metas.ui.web.quickinput.orderline;

import java.util.Optional;

import de.metas.ui.web.quickinput.field.DefaultPackingItemCriteria;
import de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.SpringContextHolder;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.quickinput.field.DefaultPackingItemCriteria;
import de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ProductAndAttributes;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.SpringContextHolder;

import java.util.Optional;

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
	private final IBPartnerBL bpartnersService;
	private final PackingItemProductFieldHelper packingItemProductFieldHelper = SpringContextHolder.instance.getBean(PackingItemProductFieldHelper.class);

	@Builder
	private OrderLineQuickInputCallout(@NonNull final IBPartnerBL bpartnersService)
	{
		this.bpartnersService = bpartnersService;
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

		final Optional<DefaultPackingItemCriteria> defaultPackingItemCriteria = DefaultPackingItemCriteria.of(order, quickInputProductId);

		final I_M_HU_PI_Item_Product huPIItemProduct = defaultPackingItemCriteria.flatMap(packingItemProductFieldHelper::getDefaultPackingMaterial).orElse(null);

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
			}
