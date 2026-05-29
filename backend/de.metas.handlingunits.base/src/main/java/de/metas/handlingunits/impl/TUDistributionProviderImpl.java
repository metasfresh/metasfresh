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

package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.ITUDistributionProvider;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.NonNull;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TUDistributionProviderImpl implements ITUDistributionProvider
{
	private final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Override
	public ImmutableList<BigDecimal> distributeTuUsingLutu(
			@NonNull final I_C_Order order,
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final BigDecimal totalTU,
			final int luCount)
	{
		if (luCount <= 0)
		{
			return ImmutableList.of();
		}

		final de.metas.handlingunits.model.I_C_OrderLine huOrderLine = InterfaceWrapperHelper.create(orderLine, de.metas.handlingunits.model.I_C_OrderLine.class);
		final I_M_HU_PI_Item_Product tuPIItemProduct = hupiItemProductBL.extractHUPIItemProduct(order, huOrderLine);
		final Quantity qtyOrderedCU = orderLineBL.getQtyOrdered(huOrderLine);

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				ProductId.ofRepoId(orderLine.getM_Product_ID()),
				qtyOrderedCU.getUomId(),
				orderBL.getEffectiveDropshipPartnerId(order),
				false,
				HuPackingInstructionsId.ofRepoIdOrNull(huOrderLine.getM_LU_HU_PI_ID()),
				huOrderLine.getQtyLU());

		if (lutuConfiguration.isInfiniteQtyTU())
		{
			Check.assume(luCount == 1, "LU quantity shall be 1 but is: {}", luCount);
			return ImmutableList.of(totalTU);
		}

		if (lutuConfiguration.isInfiniteQtyCU())
		{
			Check.assume(BigDecimal.ONE.compareTo(totalTU) == 0, "totalTU shall be 1 but is: {}", totalTU);
			return ImmutableList.of(BigDecimal.ONE);
		}

		final BigDecimal capacityPerLU = lutuConfiguration.getQtyTU();
		if (capacityPerLU.signum() <= 0)
		{
			return ImmutableList.of();
		}

		BigDecimal remaining = totalTU;
		final ImmutableList.Builder<BigDecimal> result = ImmutableList.builderWithExpectedSize(luCount);
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < luCount; i++)
		{
			final BigDecimal assign = remaining.min(capacityPerLU);
			result.add(assign);
			sum = sum.add(assign);
			remaining = remaining.subtract(assign);
			if (remaining.signum() <= 0)
			{
				// fill the rest with zeros if any LUs left, should not happen
				for (int j = i + 1; j < luCount; j++)
				{
					result.add(BigDecimal.ZERO);
				}
				break;
			}
		}

		return result.build();
	}
}
