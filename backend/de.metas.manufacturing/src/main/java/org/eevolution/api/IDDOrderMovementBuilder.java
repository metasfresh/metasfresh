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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Date;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;

/**
 * Generate a movement for a distribution order lines.
 * 
 * Use {@link IDDOrderBL#createMovementBuilder()} to get an instance.
 *
 */
public interface IDDOrderMovementBuilder
{
	I_M_Movement process();

	void setMovementDate(Date movementDate);

	void setDD_Order(I_DD_Order ddOrder);
	
	void setLocatorToIdOverride(int locatorToId);

	I_M_MovementLine addMovementLineShipment(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt);

	I_M_MovementLine addMovementLineShipment(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt, BigDecimal movementQtySrc, I_C_UOM movementQtyUOM);

	I_M_MovementLine addMovementLineReceipt(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt);

	I_M_MovementLine addMovementLineReceipt(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt, BigDecimal movementQtySrc, I_C_UOM movementQtyUOM);
	
	I_M_MovementLine addMovementLineDirect(I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt, BigDecimal movementQtySrc, I_C_UOM movementQtyUOM);

}
