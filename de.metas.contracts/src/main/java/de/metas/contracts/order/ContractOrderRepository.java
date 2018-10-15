/**
 * 
 */
package de.metas.contracts.order;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.springframework.stereotype.Repository;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class ContractOrderRepository
{
	@Cached(cacheName = I_C_Flatrate_Term.Table_Name + "#by#OrderId")
	public boolean isContractSalesOrder(@NonNull final OrderId orderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addNotNull(I_C_OrderLine.COLUMN_C_Flatrate_Conditions_ID)
				.create()
				.match();
	}
	
	public void save(@NonNull final I_C_Order order)
	{
		InterfaceWrapperHelper.save(order);
	}
	
	public void setOrderContractStatusAndSave(@NonNull final I_C_Order order, @NonNull final String contractStatus)
	{
		order.setContractStatus(contractStatus);
		save(order);
	}

}
