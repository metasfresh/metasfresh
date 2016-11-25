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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/**
 * Class used to produce Shipment/Receipt for empties (i.e. packing materials) returns to vendor or returns from customer.
 *
 * @author tsa
 *
 */
public interface IEmptiesInOutProducer
{
	/**
	 * Creates Shipment/Receipt.
	 *
	 * @return created {@link I_M_InOut} or null.
	 */
	I_M_InOut create();

	void addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty);

	void setC_BPartner(final I_C_BPartner bpartner);

	void setC_BPartner_Location(final I_C_BPartner_Location bpLocation);

	void setMovementType(String movementType);

	void setM_Warehouse(I_M_Warehouse warehouse);

	void setMovementDate(Date movementDate);

	/**
	 * Check if this producer is empty.
	 *
	 * A producer is considered empty, when there are no packing material added.
	 *
	 * @return true if empty.
	 */
	boolean isEmpty();

	/**
	 * Set the order based on which the inout is created ( if it was selected)
	 * 
	 * @param order
	 */
	void setC_Order(I_C_Order order);

	/**
	 * The order that was selected to created the empties inout based on.
	 * It's acceptable for it to be null in case it wasn't set
	 * 
	 * @return
	 */
	I_C_Order getC_Order();
}
