package de.metas.handlingunits.inout;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

/**
 * Service used to manage the relation between {@link I_M_HU} and shipment line.
 * NOTE: please always use this service instead of bypassing it and assign HUs directly for example.
 *
 * @author tsa
 *
 */
public interface IHUShipmentAssignmentBL extends ISingletonService
{
	/**
	 * Assign given LU/TU/VHU to shipment line
	 */
	void assignHU(I_M_InOutLine shipmentLine, HUTopLevel huToAssign, boolean isTransferPackingMaterials);

	/**
	 * Update HUStatus (i.e. it is setting it to Shipped) for assigned HUs when shipment is completed.
	 */
	void updateHUsOnShipmentComplete(I_M_InOut shipment);

	/**
	 * Remove HU Assignments for given Shipment.
	 * Also, this method restores HU's HUStatus (i.e. it is setting it to Picked).
	 */
	void removeHUAssignments(I_M_InOut shipment);

	/**
	 * Remove HU Assignments for given Shipment Line.
	 * Also, this method restores HU's HUStatus (i.e. it is setting it to Picked).
	 */
	void removeHUAssignments(de.metas.handlingunits.model.I_M_InOutLine shipmentLine);

	void reactivateVendorReturnLine(@NonNull de.metas.inout.model.I_M_InOutLine vendorReturnLine);

	void reactivateCustomerReturnLine(@NonNull de.metas.inout.model.I_M_InOutLine returnLine);

	QtyPickedRecords retrieveAssignedQuantities(I_M_InOut shipment);

	boolean hasHUAssignments(final I_M_InOut inout);

	void moveAssignments(@NonNull I_M_InOut source, @NonNull I_M_InOut dest);
}
