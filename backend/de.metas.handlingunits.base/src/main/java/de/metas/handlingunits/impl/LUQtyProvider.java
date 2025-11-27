/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.ILUQtyProvider;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

@Service
public class LUQtyProvider implements ILUQtyProvider
{
	private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@Override
	public int getRequiredLUCount(@NonNull final I_C_Order order, @NonNull final I_C_OrderLine orderLine)
	{
		final de.metas.handlingunits.model.I_C_OrderLine line = InterfaceWrapperHelper.create(orderLine, de.metas.handlingunits.model.I_C_OrderLine.class);

		final ProductId productId = ProductId.ofRepoId(line.getM_Product_ID());
		final Quantity qtyInStockUOM = uomConversionBL.convertToProductUOM(Quantitys.of(line.getQtyEntered(), UomId.ofRepoId(line.getC_UOM_ID())), productId);

		final I_M_HU_PI_Item_Product tuPIItemProduct = huPIItemProductBL.extractHUPIItemProduct(order, line);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());

		final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				productId,
				qtyInStockUOM.getUomId(),
				bpartnerId,
				false/* noLUForVirtualTU */);

		return huPIItemProductBL.getRequiredLUCount(qtyInStockUOM, lutuConfigurationInStockUOM);
	}
}
