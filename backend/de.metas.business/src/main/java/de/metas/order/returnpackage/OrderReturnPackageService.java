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

package de.metas.order.returnpackage;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_ReturnPackage;
import org.springframework.stereotype.Service;

/**
 * Creates the default return-package (Rücknahme Gebinde) rows (one per pallet type: EUR + H1) for a sales order.
 * The two quantity columns (QtyDeliveredLU, QtyReturnedLU) are left empty; the user types them.
 */
@Service
public class OrderReturnPackageService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Idempotent: creates the two return-package rows (EUR, H1) for the given order if it has none yet.
	 */
	public void createDefaultsForOrder(@NonNull final I_C_Order order)
	{
		if (hasReturnPackages(order.getC_Order_ID()))
		{
			return;
		}

		for (final PalletType palletType : PalletType.values())
		{
			createRow(order, palletType);
		}
	}

	private boolean hasReturnPackages(final int orderId)
	{
		return queryBL.createQueryBuilder(I_C_Order_ReturnPackage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_ReturnPackage.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.anyMatch();
	}

	private void createRow(@NonNull final I_C_Order order, @NonNull final PalletType palletType)
	{
		final I_C_Order_ReturnPackage row = InterfaceWrapperHelper.newInstance(I_C_Order_ReturnPackage.class, order);
		row.setAD_Org_ID(order.getAD_Org_ID());
		row.setC_Order_ID(order.getC_Order_ID());
		row.setC_BPartner_ID(order.getC_BPartner_ID());
		row.setPalletType(palletType.getCode());
		// Quantities default to 0; the user enters the actual counts.
		InterfaceWrapperHelper.saveRecord(row);
	}
}
