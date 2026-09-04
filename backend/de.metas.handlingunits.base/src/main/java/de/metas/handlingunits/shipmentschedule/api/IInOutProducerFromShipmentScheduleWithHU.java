package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.shipmentschedule.spi.impl.CalculateShippingDateRule;
import de.metas.handlingunits.shipmentschedule.spi.impl.ShipmentScheduleExternalInfo;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;

import java.util.List;

/**
 * Interface responsible creating {@link I_M_InOut} shipments from {@link ShipmentScheduleWithHU}s.
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
	 */
	IInOutProducerFromShipmentScheduleWithHU setCreatePackingLines(boolean createPackingLines);

	IInOutProducerFromShipmentScheduleWithHU computeShipmentDate(CalculateShippingDateRule calculateShippingDateType);

	IInOutProducerFromShipmentScheduleWithHU setScheduleIdToExternalInfo(ImmutableMap<ShipmentScheduleId, ShipmentScheduleExternalInfo> scheduleId2ExternalInfo);

	/**
	 * {@code M_Delivery_Planning_ID} to stamp onto each shipment header this producer creates, or {@code 0}
	 * for none. Stamped on the draft, i.e. before the shipment is completed, so the document's
	 * {@code TIMING_AFTER_COMPLETE} interceptors see it.
	 */
	IInOutProducerFromShipmentScheduleWithHU setDeliveryPlanningId(int deliveryPlanningId);

	IInOutProducerFromShipmentScheduleWithHU setTrxItemExceptionHandler(ITrxItemExceptionHandler trxItemExceptionHandler);
}
