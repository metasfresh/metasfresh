/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.impl;

import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineDetailCreateRequest;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine_Detail;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLineDetailRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createNew(
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final OrgId orgId,
			@NonNull final OrderLineDetailCreateRequest request)
	{
		final I_C_OrderLine_Detail record = InterfaceWrapperHelper.newInstance(I_C_OrderLine_Detail.class);
		record.setC_Order_ID(orderAndLineId.getOrderRepoId());
		record.setC_OrderLine_ID(orderAndLineId.getOrderLineRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		record.setQty(request.getQty().toBigDecimal());
		record.setPrice(request.getPrice().toBigDecimal());
		record.setAmount(request.getAmount().toBigDecimal());
		record.setDescription(request.getDescription());
		InterfaceWrapperHelper.saveRecord(record);
	}

	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_OrderLine_Detail.class)
				.addEqualsFilter(I_C_OrderLine_Detail.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.deleteDirectly();
	}

	public void deleteByOrderLineId(@NonNull final OrderAndLineId orderAndLineId)
	{
		queryBL.createQueryBuilder(I_C_OrderLine_Detail.class)
				.addEqualsFilter(I_C_OrderLine_Detail.COLUMNNAME_C_Order_ID, orderAndLineId.getOrderId())
				.addEqualsFilter(I_C_OrderLine_Detail.COLUMNNAME_C_OrderLine_ID, orderAndLineId.getOrderLineId())
				.create()
				.deleteDirectly();
	}
}
