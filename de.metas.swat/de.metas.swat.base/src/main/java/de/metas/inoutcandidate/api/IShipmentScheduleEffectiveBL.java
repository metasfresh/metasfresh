package de.metas.inoutcandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_AD_User;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;

/**
 * Returns the "effective" values for a given shipment schedules when it has both an "original" and an "override" column.
 *
 * @author ts
 *
 */
// TODO 06178: clean up the method names (both ID and Object getters are fine, but the names should be alligned)
public interface IShipmentScheduleEffectiveBL extends ISingletonService
{
	I_M_Warehouse getWarehouse(I_M_ShipmentSchedule sched);

	int getWarehouseId(I_M_ShipmentSchedule sched);

	I_M_Locator getDefaultLocator(I_M_ShipmentSchedule sched);

	I_C_BPartner_Location getBPartnerLocation(I_M_ShipmentSchedule sched);

	/**
	 * return the "effective" C_BPartner_ID (either C_BPartner_ID or if set C_BPartner_Override_ID)
	 *
	 * @param sched
	 * @return
	 */
	int getC_BPartner_ID(I_M_ShipmentSchedule sched);

	String getDeliveryRule(I_M_ShipmentSchedule sched);

	BigDecimal getQtyToDeliver(I_M_ShipmentSchedule sched);

	I_C_BPartner getBPartner(I_M_ShipmentSchedule sched);

	int getC_BP_Location_ID(I_M_ShipmentSchedule sched);

	I_AD_User getAD_User(I_M_ShipmentSchedule sched);

	int getAD_User_ID(I_M_ShipmentSchedule sched);

	/**
	 * @param sched
	 * @return the effective {@code QtyOrdered}. Where it's coming from is determined by from different values and flags of the given {@code sched}.
	 */
	BigDecimal computeQtyOrdered(I_M_ShipmentSchedule sched);

	/**
	 * Get the delivery date effective based on DeliveryDate and DeliveryDate_Override
	 *
	 * @param sched
	 * @return
	 */
	Timestamp getDeliveryDate(I_M_ShipmentSchedule sched);

	/**
	 * Get the preparation date effective based on PreparationDate and PreparationDate_Override.
	 * If none of them is set, try to fallback to the given {@code sched}'s order's preparation date. If the order has no proparation date, falls back to the order's promised date.
	 * If the given {@code sched} doesn't have an order, return the current time.,
	 *
	 * @param sched
	 * @return
	 */
	Timestamp getPreparationDate(I_M_ShipmentSchedule sched);
}
