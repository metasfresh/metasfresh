package de.metas.handlingunits.shipmentschedule.api;

import java.util.List;

import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;

import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/**
 * Interface responsible creating {@link I_M_InOut} shipments from {@link IShipmentScheduleWithHU}s.
 *
 *
 */
public interface IInOutProducerFromShipmentScheduleWithHU
{
	InOutGenerateResult createShipments(List<ShipmentScheduleWithHU> candidates);

	IInOutProducerFromShipmentScheduleWithHU setProcessShipments(boolean processShipments);

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
	 * Sets if shipment lines shall be flagged as manual packing materials.<br>
	 * Makes sense for dirty "adhoc" shipments that don't have actually picked handling units.
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
	 * @param forceDateToday
	 * @return
	 */
	IInOutProducerFromShipmentScheduleWithHU computeShipmentDate(boolean forceDateToday);

	IInOutProducerFromShipmentScheduleWithHU setTrxItemExceptionHandler(ITrxItemExceptionHandler trxItemExceptionHandler);
}
