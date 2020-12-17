package de.metas.handlingunits.pporder.api;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.quantity.Quantity;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPCostCollectorId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * Generates manufacturing receipt candidates ({@link I_PP_Order_Qty}) together with the planning HUs.
 *
 * The generated receipt candidates are not processed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPPOrderReceiptHUProducer
{
	/**
	 * Creates planning HUs to be received.
	 * It also creates draft manufacturing receipt candidates ({@link I_PP_Order_Qty}).
	 */
	void createDraftReceiptCandidatesAndPlanningHUs();

	I_M_HU receiveVHU(Quantity qtyToReceive);

	/**
	 * NOTE: by default current system time is considered.
	 */
	IPPOrderReceiptHUProducer movementDate(final ZonedDateTime movementDate);

	IPPOrderReceiptHUProducer locatorId(LocatorId locatorId);

	/**
	 * Sets LU/TU configuration to be used.
	 * If not set, the PP_Order/BOM line's current configuration will be used.
	 */
	IPPOrderReceiptHUProducer packUsingLUTUConfiguration(I_M_HU_LUTU_Configuration lutuConfiguration);

	IPPOrderReceiptHUProducer pickingCandidateId(PickingCandidateId pickingCandidateId);

	IPPOrderReceiptHUProducer lotNumber(String lotNumber);

	IPPOrderReceiptHUProducer bestBeforeDate(@Nullable LocalDate bestBeforeDate);

	Set<PPCostCollectorId> getCreatedCostCollectorIds();
}
