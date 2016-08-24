package de.metas.handlingunits.shipmentschedule.api;

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

import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;

/**
 * Shipment Schedule + LU and TU that are ready to be received
 *
 * @author tsa
 *
 */
public interface IShipmentScheduleWithHU
{
	IHUContext getHUContext();
	
	int getM_Product_ID();

	I_M_Product getM_Product();

	int getM_AttributeSetInstance_ID();

	Object getAttributesAggregationKey();

	int getC_OrderLine_ID();

	/**
	 *
	 * @return shipment schedule
	 */
	I_M_ShipmentSchedule getM_ShipmentSchedule();

	/**
	 *
	 * @return Qty CU that was picked and it's ready to be received
	 */
	BigDecimal getQtyPicked();

	/**
	 * @return {@link #getQtyPicked()}'s UOM
	 */
	I_C_UOM getQtyPickedUOM();

	/**
	 * @return Virtual HU
	 */
	I_M_HU getVHU();

	/**
	 *
	 * @return Transport Unit (TU)
	 */
	I_M_HU getM_TU_HU();

	/**
	 *
	 * @return Loading Unit (LU) or null
	 */
	I_M_HU getM_LU_HU();

	/**
	 * Called by API when a shipment line that includes this candidate was just created.
	 *
	 * @param shipmentLine
	 */
	void setM_InOutLine(I_M_InOutLine shipmentLine);

	/**
	 *
	 * @return shipment line or null
	 * @see #setM_InOutLine(I_M_InOutLine)
	 */
	I_M_InOutLine getM_InOutLine();

	/**
	 * Called by API when a shipment that includes this candidate was generated and processed.
	 *
	 * @param inout
	 */
	void setM_InOut(final I_M_InOut inout);
}
