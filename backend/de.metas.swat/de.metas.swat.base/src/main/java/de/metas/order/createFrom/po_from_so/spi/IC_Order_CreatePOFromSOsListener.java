package de.metas.order.createFrom.po_from_so.spi;

import org.compiere.model.I_C_OrderLine;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public interface IC_Order_CreatePOFromSOsListener
{
	/**
	 * Invoked after a new purchase order line was created for the given sales order line.
	 */
	void afterPurchaseOrderLineCreatedBeforeSave(
			I_C_OrderLine purchaseOrderLine,
			I_C_OrderLine salesOrderLine);
}
