package de.metas.ui.web.quickinput.orderline;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.contracts.ConditionsId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.quickinput.field.DefaultPackingItemCriteria;
import de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ProductAndAttributes;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IBPartnerBL bpartnersService;
	private final PackingItemProductFieldHelper packingItemProductFieldHelper;
	private final UserSession userSession;

	@Builder
	private OrderLineQuickInputCallout(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final PackingItemProductFieldHelper packingItemProductFieldHelper,
			@NonNull final UserSession userSession)
	{
		this.bpartnersService = bpartnersService;
		this.packingItemProductFieldHelper = packingItemProductFieldHelper;
		this.userSession = userSession;
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
		updateCompensationGroup(quickInput);
	}

	private void updateM_HU_PI_Item_Product(@NonNull final QuickInput quickInput)
	{
		if (!quickInput.hasField(IOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
		{
			return; // there are users whose systems don't have M_HU_PI_Item_Product_ID in their quick-input
		}

		final IOrderLineQuickInput quickInputModel = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);
		final ProductAndAttributes productAndAttributes = getProductAndAttributes(quickInputModel);
		if (productAndAttributes == null)
		{
			return;
		}
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

	private void updateCompensationGroup(@NonNull final QuickInput quickInput)
	{
		if (!quickInput.hasField(IOrderLineQuickInput.COLUMNNAME_C_CompensationGroup_Schema_ID))
		{
			return;
		}

		final IOrderLineQuickInput quickInputModel = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);

		final ProductAndAttributes productAndAttributes = getProductAndAttributes(quickInputModel);
		final GroupTemplateId groupTemplateId = getGroupTemplateId(productAndAttributes).orElse(null);

		quickInputModel.setC_CompensationGroup_Schema_ID(GroupTemplateId.toRepoId(groupTemplateId));
		if (groupTemplateId != null)
		{
			quickInputModel.setQty(BigDecimal.ONE);

			final ConditionsId defaultFlatrateConditionsId = userSession.getDefaultFlatrateConditionsId();
			quickInputModel.setC_Flatrate_Conditions_ID(defaultFlatrateConditionsId == null ? -1 : defaultFlatrateConditionsId.getRepoId());
		}
		else
		{
			quickInputModel.setC_Flatrate_Conditions_ID(-1);
		}
	}

	@Nullable
	private static ProductAndAttributes getProductAndAttributes(@NonNull final IOrderLineQuickInput quickInputModel)
	{
		return quickInputModel.getM_Product_ID() != null
				? ProductLookupDescriptor.toProductAndAttributes(quickInputModel.getM_Product_ID())
				: null;
	}

	private Optional<GroupTemplateId> getGroupTemplateId(@Nullable final ProductAndAttributes productAndAttributes)
	{
		return productAndAttributes != null
				? productDAO.getGroupTemplateIdByProductId(productAndAttributes.getProductId())
				: Optional.empty();
	}
}
