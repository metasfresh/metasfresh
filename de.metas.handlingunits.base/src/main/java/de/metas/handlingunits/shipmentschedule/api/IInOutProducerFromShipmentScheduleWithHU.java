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

import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;

import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/**
 * Interface responsible creating {@link I_M_InOut} shipments from {@link IShipmentScheduleWithHU}s.
 *
 *
 */
public interface IInOutProducerFromShipmentScheduleWithHU extends ITrxItemChunkProcessor<IShipmentScheduleWithHU, InOutGenerateResult>
{
	/**
	 * Determines if the shipments shall be completed or left in their initial status (i.e. "draft").
	 *
	 * Notes:
	 * <ul>
	 * <li>the default (in case this method is not used) is <code>'CO'</code> (complete).
	 * <li>if no processing is desired, call this method with <code>docAction == null</code>.
	 * </ul>
	 *
	 * @param completeShipments
	 * @return this instance
	 */
	IInOutProducerFromShipmentScheduleWithHU setProcessShipmentsDocAction(String docAction);

	/**
	 * Determines if this producer shall explicitly invoke {@link IHUInOutBL#createPackingMaterialLines(org.compiere.model.I_M_InOut)} to create packing inOutLines.
	 *
	 * Notes:
	 * <ul>
	 * <li>if this is set to <code>true</code>, then packing lines will be created <b>before</b> the shipment's DocAction is processed
	 * <li>{@link IHUInOutBL#createPackingMaterialLines(org.compiere.model.I_M_InOut) createPackingMaterialLines()} will also be called when the shipment is prepared, but at that stage it is much more
	 * complicated to delete/reset those lines.
	 * </ul>
	 *
	 * @param createPackingLines
	 * @return this instance
	 */
	IInOutProducerFromShipmentScheduleWithHU setCreatePackingLines(boolean createPackingLines);

	/**
	 * Sets if shipment lines shall be flagged as manual packing materials
	 *
	 * @param manualPackingMaterial
	 * @return this
	 * @see I_M_InOutLine#setIsManualPackingMaterial(boolean)
	 */
	IInOutProducerFromShipmentScheduleWithHU setManualPackingMaterial(boolean manualPackingMaterial);

	/**
	 * If the flag IsShipmentDateToday is true, the shipment will be created for today, no matter what delivery dates are set in the shipment schedules.
	 * Otherwise, the date on the shipment will be the minimum date of the shipment schedules, not older than today.
	 * 
	 * @param isShipmentDateToday
	 * @return
	 */
	IInOutProducerFromShipmentScheduleWithHU setShipmentDateToday(boolean isShipmentDateToday);
}
