/*
 * #%L
 * de.metas.business
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

package de.metas.order.returnpackage.core.repository;

import de.metas.order.OrderId;
import de.metas.order.returnpackage.PalletType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_ReturnPackage;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class OrderReturnPackageRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);


	public boolean hasReturnPackages(final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Order_ReturnPackage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_ReturnPackage.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.anyMatch();
	}


	public void createRow(@NonNull final I_C_Order order, @NonNull final PalletType palletType)
	{
		final I_C_Order_ReturnPackage row = InterfaceWrapperHelper.newInstance(I_C_Order_ReturnPackage.class);

		row.setAD_Org_ID(order.getAD_Org_ID());
		row.setC_Order_ID(order.getC_Order_ID());
		row.setPalletType(palletType.getCode());
		// Quantities default to 0; the user enters the actual counts.
		saveRecord(row);
	}
}
