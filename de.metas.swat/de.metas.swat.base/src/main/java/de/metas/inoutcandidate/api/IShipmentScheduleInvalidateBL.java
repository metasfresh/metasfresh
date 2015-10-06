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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public interface IShipmentScheduleInvalidateBL extends ISingletonService
{
	/**
	 * Invalidate just the shipment schedules that directly reference the inOut's lines. Use this method if you know that no re-allocation of on-hand-qtys is required, but just the affected schedules
	 * need to be updated (e.g. QtyPicked => QtyDelivered, if an InOut is completed).
	 * 
	 * @param shipment
	 */
	void invalidateJustForLines(I_M_InOut shipment);

	/**
	 * See {@link #invalidateJustForLines(I_M_InOut)}.
	 * 
	 * @param shipmentLine
	 */
	void invalidateJustForLine(I_M_InOutLine shipmentLine);

	/**
	 * See {@link #invalidateSegmentForLine(I_M_InOutLine)}.
	 * 
	 * @param shipment
	 */
	void invalidateSegmentsForLines(I_M_InOut shipment);

	/**
	 * Invalidates all shipment schedules that have the same product, bPartner, ASI and locator as the given line<br>
	 * and <b>that that do not have "force" as delivery rule</b>.<br>
	 * Notes:
	 * <ul>
	 * <li>the set of such schedules is usually relatively small, compared with the set of all schedules that have the same product.
	 * <li>As stated, do not invalidate scheds with delivery rule force, because to get their QtyToDeliver, they don't need to care about other schedules anyways. That means that a dev might need to
	 * call {@link #invalidateJustForLines(I_M_InOut)} in addition to this method.
	 * <ul>
	 * 
	 * @param shipmentLine
	 * @see IShipmentSchedulePA#invalidate(java.util.Collection)
	 */
	void invalidateSegmentForLine(I_M_InOutLine shipmentLine);

	/**
	 * For the given <code>schedule</code>, invalidate all shipment schedules that have the same product and warehouse and a matching ASI.
	 */
	void invalidateSegmentForShipmentSchedule(I_M_ShipmentSchedule schedule);

	/**
	 * For the given <code>orderLine</code>, invalidate all shipment schedules that have the same product and warehouse and a matching ASI.
	 * and <b>that that do not have "force" as delivery rule</b>.<br>
	 */
	void invalidateSegmentForOrderLine(I_C_OrderLine orderLine);

	/**
	 * Invalidate the shipment schedule referencing the given <code>orderLine</code>.
	 * 
	 * @param orderLine
	 */
	void invalidateJustForOrderLine(I_C_OrderLine orderLine);
}
