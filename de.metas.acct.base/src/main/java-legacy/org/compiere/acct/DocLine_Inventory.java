package org.compiere.acct;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InventoryLine;

import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.acct.base
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

public class DocLine_Inventory extends DocLine<Doc_Inventory>
{
	public DocLine_Inventory(final I_M_InventoryLine inventoryLine, final Doc_Inventory doc)
	{
		super(InterfaceWrapperHelper.getPO(inventoryLine), doc);

		BigDecimal qty = inventoryLine.getQtyInternalUse();
		if (qty.signum() != 0)
		{
			qty = qty.negate();		// Internal Use entered positive
		}
		else
		{
			BigDecimal QtyBook = inventoryLine.getQtyBook();
			BigDecimal QtyCount = inventoryLine.getQtyCount();
			qty = QtyCount.subtract(QtyBook);
		}

		setQty(Quantity.of(qty, getProductStockingUOM()), false);		// -5 => -5

		setReversalLine_ID(inventoryLine.getReversalLine_ID());
	}

}
