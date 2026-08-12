/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.order;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;

/**
 * Applies packing instruction (PI) inheritance to compensation group order lines.
 * <p>
 * Given a main article's {@link HUPIItemProductId}, resolves a product-specific compatible
 * {@link I_M_HU_PI_Item_Product} for each regular line in the group, using the same
 * {@code M_HU_PI_Item} (TU type) as the main article.
 * <p>
 * If no compatible PI exists for a sub-article's product, that order line is left unchanged
 * (retains default PI=101 "No Packing Item").
 * <p>
 * Used by:
 * <ul>
 *     <li>{@code OrderLineQuickInputProcessor} — production path via Quick Input</li>
 *     <li>Cucumber step defs — integration testing</li>
 * </ul>
 */
public class OrderGroupPIInheritanceService
{
	private final IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	/**
	 * Applies PI inheritance to all regular lines in the given compensation group.
	 *
	 * @param order                the target order (used to resolve BPartner for PI lookup)
	 * @param group                the compensation group whose regular lines will be updated
	 * @param mainPiItemProductId  the main article's PI Item Product (determines the TU type to match)
	 */
	public void applyPackingInstructionInheritance(
			@NonNull final I_C_Order order,
			@NonNull final Group group,
			@NonNull final HUPIItemProductId mainPiItemProductId)
	{
		final HUPIItemProduct mainPiItemProduct = piItemProductDAO.getById(mainPiItemProductId);
		final HuPackingInstructionsItemId piItemId = mainPiItemProduct.getPiItemId();
		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.load(piItemId.getRepoId(), I_M_HU_PI_Item.class);

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());

		for (final GroupRegularLine regularLine : group.getRegularLines())
		{
			final de.metas.handlingunits.model.I_C_OrderLine orderLine = InterfaceWrapperHelper.load(
					regularLine.getRepoId(), de.metas.handlingunits.model.I_C_OrderLine.class);
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

			final I_M_HU_PI_Item_Product matchingPiItemProduct = piItemProductDAO.retrievePIMaterialItemProduct(
					piItem, bpartnerId, productId, null);
			if (matchingPiItemProduct != null)
			{
				orderLine.setM_HU_PI_Item_Product_ID(matchingPiItemProduct.getM_HU_PI_Item_Product_ID());
				InterfaceWrapperHelper.save(orderLine);
			}
		}
	}
}
