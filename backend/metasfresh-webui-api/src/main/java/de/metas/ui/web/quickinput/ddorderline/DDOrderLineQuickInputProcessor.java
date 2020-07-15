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

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.ddorder.api.DDOrderLineCreateRequest;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Services;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.model.I_DD_Order;

import java.math.BigDecimal;
import java.util.Set;

public class DDOrderLineQuickInputProcessor implements IQuickInputProcessor
{
	private final IHUDDOrderBL huddOrderBL = Services.get(IHUDDOrderBL.class);

	@Override
	public Set<DocumentId> process(final QuickInput quickInput)
	{
		final IDDOrderLineQuickInput ddOrderQuickInput = quickInput.getQuickInputDocumentAs(IDDOrderLineQuickInput.class);

		final ProductId productId = ProductId.ofRepoId(ddOrderQuickInput.getM_Product_ID());
		final HUPIItemProductId mHUPIProductID = HUPIItemProductId.ofRepoIdOrNull(ddOrderQuickInput.getM_HU_PI_Item_Product_ID());
		final BigDecimal qty = ddOrderQuickInput.getQty();

		final I_DD_Order ddOrder = quickInput.getRootDocumentAs(I_DD_Order.class);

		final DDOrderLineCreateRequest ddOrderLineCreateRequest = DDOrderLineCreateRequest.builder()
				.ddOrder(ddOrder)
				.productId(productId)
				.mHUPIProductID(mHUPIProductID)
				.qtyEntered(qty)
				.build();

		final DDOrderLineId ddOrderLineId = huddOrderBL.addDDOrderLine(ddOrderLineCreateRequest);

		final DocumentId documentId = DocumentId.of(ddOrderLineId.getRepoId());
		return ImmutableSet.of(documentId);
	}
}
