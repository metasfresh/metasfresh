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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_C_BPartner_Location;
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
	 * Creates Shipment/Receipt.
	 *
	 * @return created {@link I_M_InOut} or null.
	 */
	I_M_InOut create();

	// IEmptiesInOutProducer addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty);
	IReturnsInOutProducer setC_BPartner(final I_C_BPartner bpartner);

	IReturnsInOutProducer setC_BPartner_Location(final I_C_BPartner_Location bpLocation);

	IReturnsInOutProducer setMovementType(String movementType);

	IReturnsInOutProducer setM_Warehouse(I_M_Warehouse warehouse);

	IReturnsInOutProducer setMovementDate(Date movementDate);

	/**
	 * Set the order based on which the inout is created ( if it was selected)
	 * 
	 * @param order
	 */
	IReturnsInOutProducer setC_Order(I_C_Order order);

	IReturnsInOutProducer dontComplete();

	boolean isEmpty();

	//TODO Check how this will be implemented for returns
	IReturnsInOutProducer addPackingMaterial(I_M_HU_PackingMaterial packingMaterial, int qty);

}
