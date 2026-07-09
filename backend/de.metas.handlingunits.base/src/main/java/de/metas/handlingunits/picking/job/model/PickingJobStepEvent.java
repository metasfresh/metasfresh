package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.serialno.SerialNoSet;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

@Value
@Builder
public class PickingJobStepEvent
{
	@Builder.Default
	@NonNull Instant timestamp = SystemTime.asInstant();

	@NonNull PickingJobLineId pickingLineId;
	@Nullable PickingJobStepId pickingStepId;
	@Nullable PickingJobStepPickFromKey pickFromKey;

	@NonNull PickingJobStepEventType eventType;


	//
	// Common
	@NonNull ScannedCode qrCode;
	
	//
	// Event Type: PICK
	@Nullable BigDecimal qtyPicked;
	@Nullable BigDecimal qtyRejected;
	@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;
	@Nullable BigDecimal catchWeight;
	boolean isPickWholeTU;
	@Builder.Default boolean checkIfAlreadyPacked = true;
	boolean isSetBestBeforeDate;
	@Nullable LocalDate bestBeforeDate;
	boolean isSetLotNo;
	@Nullable String lotNo;
	boolean isSetSerialNos;
	@Nullable SerialNoSet serialNos;
	boolean isCloseTarget;
	boolean isSetGrais;
	@Nullable GRAISet graiCodes;

	//
	// Shelf-life acknowledgement (PICK only)
	/** When {@code true} the picker has acknowledged the shelf-life warning and the guard is skipped. */
	boolean isShelfLifeConfirmed;

	//
	// Event Type: UNPICK
	@Nullable HUQRCode unpickToTargetQRCode;

	/**
	 * Optional partial-unpick selector — when both are set, only HUs carrying this product
	 * (LIFO, whole-HU boundaries) totalling {@code qtyToUnpick} are reversed.
	 * When null the existing whole-step/whole-line unpick behaviour applies.
	 * The qty is unitless (BigDecimal); the UOM is resolved at dispatch time from the job's step.
	 */
	@Nullable ProductId unpickProductId;
	@Nullable BigDecimal qtyToUnpick;

	public static Collection<PickingJobStepEvent> removeDuplicates(@NonNull final Collection<PickingJobStepEvent> events)
	{
		return events
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						// unpickProductId+qtyToUnpick are part of the key so distinct subset-UNPICK events on the
						// same line are not collapsed: for a subset UNPICK, pickingStepId and pickFromKey are both
						// null, so without these two fields the key would degrade to lineId alone and silently drop
						// all-but-the-latest. For legacy whole-step events both are null → key is unchanged.
						event -> Util.ArrayKey.of(event.getPickingLineId(), event.getPickingStepId(), event.getPickFromKey(), event.getUnpickProductId(), event.getQtyToUnpick()),
						event -> event,
						PickingJobStepEvent::latest))
				.values();
	}

	private static PickingJobStepEvent latest(@NonNull final PickingJobStepEvent e1, @NonNull final PickingJobStepEvent e2)
	{
		return e1.getTimestamp().isAfter(e2.getTimestamp()) ? e1 : e2;
	}

}
