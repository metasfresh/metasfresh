package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

/**
 * Instances of this class can be passed to {@link IPPCostCollectorBL#createReceipt(IReceiptCostCollectorCandidate)} to have it generate and process a receipt. <br>
 * To obtain an instace, call {@link IPPCostCollectorBL#createReceiptCostCollectorCandidate()}.
 * <p>
 * Note that in the context of a "co-product", a receipt is a negative issue (but that should not bother the user).
 *
 */
public interface IReceiptCostCollectorCandidate
{
	I_PP_Order getPP_Order();

	/**
	 * 
	 * @return manufacturing order's BOM Line if this is a co/by-product receipt; <code>null</code> otherwise 
	 */
	I_PP_Order_BOMLine getPP_Order_BOMLine();

	Timestamp getMovementDate();

	void setMovementDate(Timestamp movementDate);

	BigDecimal getQtyToReceive();

	void setQtyToReceive(BigDecimal qtyToReceive);

	BigDecimal getQtyScrap();

	BigDecimal getQtyReject();

	int getM_Locator_ID();

	int getM_AttributeSetInstance_ID();

	I_C_UOM getC_UOM();

	void setC_UOM(final I_C_UOM uom);

	I_M_Product getM_Product();
	
	void setM_Product(I_M_Product product);
}
