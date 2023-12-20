/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.warehouseassignment.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.order.GetOrdersQuery;
import de.metas.order.IOrderBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Interceptor(I_M_Product_Warehouse.class)
public class M_Product_Warehouse
{
	private static final AdMessageKey ORDER_DIFFERENT_WAREHOUSE_MSG_KEY = AdMessageKey.of("ORDER_DIFFERENT_WAREHOUSE");

	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE },
			ifColumnsChanged = { I_M_Product_Warehouse.COLUMNNAME_M_Product_ID, I_M_Product_Warehouse.COLUMNNAME_M_Warehouse_ID })
	public void checkProductWarehouseAssignment(@NonNull final I_M_Product_Warehouse productWarehouseRecord)
	{
		final I_M_Product_Warehouse oldRecord = InterfaceWrapperHelper.createOld(productWarehouseRecord, I_M_Product_Warehouse.class);

		final GetOrdersQuery query = GetOrdersQuery.builder()
				.onlyActiveRecords(true)
				.warehouseId(WarehouseId.ofRepoId(oldRecord.getM_Warehouse_ID()))
				.productId(ProductId.ofRepoId(oldRecord.getM_Product_ID()))
				.build();

		final ImmutableSet<Integer> openOrderIds = orderBL.getOrdersByQuery(query).stream()
				.filter(orderBL::isDraftedOrInProgress)
				.map(I_C_Order::getC_Order_ID)
				.collect(ImmutableSet.toImmutableSet());

		if (!openOrderIds.isEmpty())
		{
			throw new AdempiereException(ORDER_DIFFERENT_WAREHOUSE_MSG_KEY)
					.appendParametersToMessage()
					.setParameter("openOrderIds", openOrderIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
					.markAsUserValidationError();
		}
	}
}
