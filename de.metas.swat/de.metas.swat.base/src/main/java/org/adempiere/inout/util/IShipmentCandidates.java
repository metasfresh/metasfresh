package org.adempiere.inout.util;

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


import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ICandidateProcessor;

/**
 * NOTE: this is a legacy interface. Please keep in mind that {@link I_M_InOut}s and {@link I_M_InOutLine}s from here are used only for internal computing and it will never ever been saved.
 * 
 * @author ts
 * 
 */
public interface IShipmentCandidates
{

	public static enum CompleteStatus
	{
		OK,
		INCOMPLETE_LINE,
		INCOMPLETE_ORDER
	};

	/**
	 * This enum is used by {@link ICandidateProcessor}s to indicate if a given inOutLine shall be revalidated in the second run or not.
	 * 
	 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
	 */
	public static enum OverallStatus
	{
		REVALIDATE,
		DISCARD
	};

	/**
	 * 
	 * @return a copy of the list of {@link MInOut}s stored in this instance.
	 */
	List<I_M_InOut> getCandidates();

	/**
	 * 
	 * @return the number of {@link MInOut}s this instance contains.
	 */
	int size();

	CompleteStatus getCompleteStatus(I_M_InOutLine inOutLine);

	/**
	 * @param inOutLine
	 * @return
	 * 
	 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
	 */
	boolean isLineDiscarded(I_M_InOutLine inOutLine);

	/**
	 * 
	 * @param inOutLine
	 * @param status
	 * 
	 * @see "<a href='http://dewiki908/mediawiki/index.php?title=Auftrag_versenden_mit_Abo-Lieferung_(2009_0027_G62)'>(2009 0027 G62)</a>"
	 */
	void setOverallStatus(I_M_InOutLine inOutLine, OverallStatus status);

	BigDecimal getQtyDeliverable(int orderLineId);

	/**
	 * 
	 * @param inOut
	 * @return a copy of the list of {@link MInOutLine}s that belong to the given <code>inOut</code>.
	 * @throws NullPointerException if <code>inOut</code> is <code>null</code>
	 */
	List<I_M_InOutLine> getLines(I_M_InOut inOut);

	I_M_InOut getInOut(I_M_InOutLine inOutLine);

	/**
	 * 
	 * @param inOut
	 * @return <code>true</code> if the given inOut has no inOutLines
	 * @throws NullPointerException if <code>inOut</code> is <code>null</code> or hasn't been added to this instance using {@link #addInOut(MInOut)} before.
	 */
	boolean hasNoLines(I_M_InOut inOut);

	/**
	 * Note: no need for a 'shipperID'-parameter (as in {@link #getInOutForShipper(int, int)}) because there are not two different shipperId for the same order.
	 * 
	 * @param orderId
	 * @param bPartnerLocationId
	 * @return
	 */
	public I_M_InOut getInOutForOrderId(int orderId, int warehouseId, String bPartnerAddress);

	public void addInOut(I_M_InOut inOut);

	/**
	 * 
	 * @param shipperId
	 * @param bPartNerLocationId
	 * @return the inOut with the given parameters
	 * @throws IllegalStateException if no inOut with the given bPartnerLocationId and shipperId has been added
	 * 
	 */
	public I_M_InOut getInOutForShipper(int shipperId, int warehouseId, String bPartnerAddress);

	public void addLine(I_M_InOut inOut, I_M_InOutLine inOutLine, I_M_ShipmentSchedule sched, CompleteStatus completeStatus, I_C_Order order);

	public I_M_InOutLine getInOutLineForOrderLine(int orderLineId);

	/**
	 * Adds a custom status info for the given iol. Usally the info explains, why an open order line won't be delivered this time.
	 * 
	 * @param inOutLine
	 * @param string
	 */
	void addStatusInfo(I_M_InOutLine inOutLine, String string);

	String getStatusInfos(I_M_InOutLine inOutLine);

	I_M_ShipmentSchedule getShipmentSchedule(I_M_InOutLine inOutLine);

	I_C_OrderLine getOrderLine(int olId);

	Set<I_M_ShipmentSchedule> getAllShipmentSchedules();

}
