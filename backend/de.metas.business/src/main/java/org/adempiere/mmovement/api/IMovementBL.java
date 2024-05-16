package org.adempiere.mmovement.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.document.DocTypeId;
import de.metas.organization.ClientAndOrgId;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;

public interface IMovementBL extends ISingletonService
{

	DocTypeId getDocTypeId(@NonNull ClientAndOrgId clientAndOrgId);

	I_C_UOM getC_UOM(I_M_MovementLine movementLine);

	/**
	 * Gets movement qty converted to given UOM
	 */
	Quantity getMovementQty(I_M_MovementLine movementLine, I_C_UOM uom);

	void setMovementQty(I_M_MovementLine movementLine, Quantity movementQty);

	/**
	 * Set the correct activities (from and to) in the movement line This is, usually, the activity of the warehouses
	 * <p>
	 * Fall back: if the warehouses don't have a c_activity, pick the one from of the product
	 * <p>
	 * NOTE: The movement line is saved
	 */
	void setC_Activities(I_M_MovementLine movementLine);

	/**
	 * Checks if given movement is a true reversal (and not the original document which was reversed).
	 *
	 * @return true if given movement is the true reversal
	 */
	boolean isReversal(I_M_Movement movement);

	void complete(@NonNull I_M_Movement movement);

	void voidMovement(I_M_Movement movement);

	void save(I_M_Movement movement);

	void save(@NonNull I_M_MovementLine movementLine);
}
