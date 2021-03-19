/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns;

import java.util.Date;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/**
 * Class used to produce Shipment/Receipt for empties (i.e. packing materials) returns to vendor or returns from customer.
 *
 * @author tsa
 *
 */
public interface IReturnsInOutProducer
{
	/**
	 * Creates material return.
	 *
	 * @return material return or null
	 */
	I_M_InOut create();

	/**
	 * Fills a given material returns header using the informations provided to this producer.
	 * 
	 * @param returnsInOut
	 */
	void fillReturnsInOutHeader(final I_M_InOut returnsInOut);

	/**
	 * Set partner for the return inout.
	 * 
	 * @param bpartner
	 * @return
	 */
	IReturnsInOutProducer setC_BPartner(final I_C_BPartner bpartner);

	/**
	 * Set partner location to the return inout.
	 * 
	 * @param bpLocation
	 * @return
	 */
	IReturnsInOutProducer setC_BPartner_Location(final I_C_BPartner_Location bpLocation);

	/**
	 * Set return inout's movement type.
	 * 
	 * @param movementType
	 * @return
	 */
	IReturnsInOutProducer setMovementType(String movementType);

	/**
	 * Set warehouse into the return inout.
	 * 
	 * @param warehouse
	 * @return
	 */
	IReturnsInOutProducer setM_Warehouse(I_M_Warehouse warehouse);

	/**
	 * Set movement date into the return inout. Currently this value is used as DateOrdered too.
	 * 
	 * @param movementDate
	 * @return
	 */
	IReturnsInOutProducer setMovementDate(Date movementDate);

	/**
	 * Set the order based on which the inout is created (if it was selected).
	 * 
	 * @param order
	 */
	IReturnsInOutProducer setC_Order(I_C_Order order);

	/**
	 * Set the created return inout to not be completed.
	 * 
	 * @return
	 */
	IReturnsInOutProducer dontComplete();

	/**
	 * Check if there are lines for the return inout.
	 * 
	 * @return tru if there is no line created for the return inout, false if there is at least one line.
	 */
	boolean isEmpty();

	/**
	 * Add packing maerial source for creating packing material lines in the return inout.
	 * 
	 * @param packingMaterial
	 * @param qty
	 * @return
	 */
	IReturnsInOutProducer addPackingMaterial(I_M_HU_PackingMaterial packingMaterial, int qty);

}
