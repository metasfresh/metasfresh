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

package de.metas.ui.web.quickinput.ddorderline;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.quickinput.field.DefaultPackingItemCriteria;
import de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@Component
public class DDOrderLineQuickInputCallout
{
	private final PackingItemProductFieldHelper packingItemProductFieldHelper;

	public DDOrderLineQuickInputCallout(final PackingItemProductFieldHelper packingItemProductFieldHelper)
	{
		this.packingItemProductFieldHelper = packingItemProductFieldHelper;
	}

	public void onProductChange(@NonNull final ICalloutField calloutField)
	{
		final QuickInput quickInput = QuickInput.getQuickInputOrNull(calloutField);
		if (quickInput == null)
		{
			return;
		}

		boolean isPackingInstructionFieldMissing = !quickInput.hasField(IDDOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID);
		if (isPackingInstructionFieldMissing)
		{
			return;
		}

		final IDDOrderLineQuickInput ddOrderLineQuickInput = quickInput.getQuickInputDocumentAs(IDDOrderLineQuickInput.class);

		final ProductId productId = ProductId.ofRepoIdOrNull( ddOrderLineQuickInput.getM_Product_ID() );

		if ( productId == null)
		{
			return;
		}
		final I_DD_Order ddOrder = quickInput.getRootDocumentAs(I_DD_Order.class);

		ddOrderLineQuickInput.setM_HU_PI_Item_Product(getDefaultPI(ddOrder, productId));
	}

	@Nullable
	private I_M_HU_PI_Item_Product getDefaultPI(@NonNull final I_DD_Order ddOrder, @NonNull final ProductId productId)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(ddOrder.getC_BPartner_ID(), ddOrder.getC_BPartner_Location_ID());
		final ZonedDateTime date = TimeUtil.asZonedDateTime(ddOrder.getDateOrdered());
		final ClientId clientId = ClientId.ofRepoId(ddOrder.getAD_Client_ID());
		
		final DefaultPackingItemCriteria defaultPackingItemCriteria = DefaultPackingItemCriteria
				.builder()
				.productId(productId)
				.bPartnerLocationId(bpartnerLocationId)
				.date(date)
				.clientId(clientId)
				.build();

		return packingItemProductFieldHelper.getDefaultPackingMaterial(defaultPackingItemCriteria).orElse(null);
	}
}
