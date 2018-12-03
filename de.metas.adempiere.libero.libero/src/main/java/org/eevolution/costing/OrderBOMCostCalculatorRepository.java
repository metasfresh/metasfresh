package org.eevolution.costing;

import java.util.Optional;

import org.eevolution.model.I_PP_Order;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class OrderBOMCostCalculatorRepository implements BOMCostCalculatorRepository
{
	// services
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);

	private final PPOrderId orderId;
	private final ProductId mainProductId;

	public OrderBOMCostCalculatorRepository(final I_PP_Order ppOrder)
	{
		orderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		mainProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
	}

	@Override
	public Optional<BOM> getBOM(final ProductId productId)
	{
		Check.assumeEquals(productId, mainProductId, "productId");

//		for (I_PP_Order_BOMLine orderBOMLineRecord : orderBOMsRepo.retrieveOrderBOMLines(orderId, I_PP_Order_BOMLine.class))
//		{
//			BOMLine.builder()
//			.componentType(BOMComponentType.ofCode(orderBOMLineRecord.getComponentType()))
//			.componentId(ProductId.ofRepoId(orderBOMLineRecord.getM_Product_ID()))
//			.qty(qty)
//			.componentId(componentId)
//			.build();
//		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(final BOM bom)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resetComponentsCostPrices(final ProductId productId)
	{
		// TODO Auto-generated method stub

	}

}
