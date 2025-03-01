package de.metas.handlingunits.pporder.api;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPCostCollectorId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.List;

/**
 * Generates manufacturing receipt candidates ({@link I_PP_Order_Qty}) together with the planning HUs.
 * The generated receipt candidates are not processed.
 */
public interface IPPOrderReceiptHUProducer
{
	/**
	 * Creates planning HUs to be received.
	 * It also creates draft manufacturing receipt candidates ({@link I_PP_Order_Qty}).
	 */
	List<I_M_HU> createDraftReceiptCandidatesAndPlanningHUs();

	I_M_HU receiveVHU(Quantity qtyToReceive);

	HuId receiveTUsToNewLU(
			@NonNull Quantity qtyToReceive,
			@NonNull HUPIItemProductId tuPIItemProductId,
			@NonNull HuPackingInstructionsItemId luPIItemId);

	ReceiveTUsToLUResult receiveTUsToExistingLU(
			@NonNull Quantity qtyToReceive,
			@NonNull HUPIItemProductId tuPIItemProductId,
			@NonNull I_M_HU existingLU);

	I_M_HU receiveSingleTU(@NonNull Quantity qtyToReceive, @NonNull final HuPackingInstructionsId tuPackingInstructionsId);

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

	List<I_M_HU> receiveIndividualPlanningCUs(@NonNull Quantity qtyToReceive);

	IPPOrderReceiptHUProducer withPPOrderLocatorId();

	ImmutableList<I_M_HU> receiveTUs(@NonNull Quantity qtyToReceive, @NonNull HUPIItemProductId tuPIItemProductId);
}
