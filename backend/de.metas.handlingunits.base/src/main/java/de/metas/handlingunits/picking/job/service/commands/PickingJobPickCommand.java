package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesRequest;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PickingJobPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;

	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final PickingJobStep initialStep;
	@NonNull private final PickingJobStepPickFromKey pickFromKey;
	@NonNull private final Quantity qtyToPick;
	@Nullable private final QtyRejectedWithReason qtyRejected;

	@Builder
	private PickingJobPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull PickingJobStepId pickingJobStepId,
			final @Nullable PickingJobStepPickFromKey pickFromKey,
			final @NonNull BigDecimal qtyToPickBD,
			final @Nullable BigDecimal qtyRejectedBD,
			final @Nullable QtyRejectedReasonCode qtyRejectedReasonCode)
	{
		Check.assumeGreaterOrEqualToZero(qtyToPickBD, "qtyToPickBD");

		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;
		this.initialStep = initialPickingJob.getStepById(pickingJobStepId);
		this.pickFromKey = pickFromKey != null ? pickFromKey : PickingJobStepPickFromKey.MAIN;

		final I_C_UOM uom = initialStep.getUOM();
		this.qtyToPick = Quantity.of(qtyToPickBD, uom);

		if (qtyRejectedReasonCode != null)
		{
			final Quantity qtyRejected = qtyRejectedBD != null
					? Quantity.of(qtyRejectedBD, uom)
					: computeQtyRejected(this.initialStep, this.pickFromKey, this.qtyToPick);

			this.qtyRejected = QtyRejectedWithReason.of(qtyRejected, qtyRejectedReasonCode);
		}
		else
		{
			this.qtyRejected = null;
		}
	}

	private static Quantity computeQtyRejected(
			@NonNull PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey,
			@NonNull Quantity qtyPicked)
	{
		if (pickFromKey.isMain())
		{
			return step.getQtyToPick().subtract(qtyPicked);
		}
		else
		{
			// NOTE: because, in case of alternatives, we don't know which is the qty scheduled to pick
			// we cannot calculate the qtyRejected
			throw new AdempiereException("Cannot calculate QtyRejected in case of alternatives");
		}

	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		initialStep.getPickFrom(pickFromKey).assertNotPicked();

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		final ImmutableList<PickedToHU> pickedHUs = createAndProcessPickingCandidate();

		final PickingJob pickingJob = initialPickingJob.withChangedStep(
				initialStep.getId(),
				step -> updateStepFromPickingCandidate(step, pickFromKey, pickedHUs));

		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}

	private ImmutableList<PickedToHU> createAndProcessPickingCandidate()
	{
		final ImmutableList<PickedToHU> pickedToHUs = splitOutPickToHUs();
		if (!pickedToHUs.isEmpty())
		{
			for (final PickedToHU pickedToHU : pickedToHUs)
			{
				final PickHUResult pickResult = pickingCandidateService.pickHU(PickRequest.builder()
						.shipmentScheduleId(initialStep.getShipmentScheduleId())
						.pickFrom(PickFrom.ofHuId(pickedToHU.getActuallyPickedToHUId()))
						.packToSpec(pickedToHU.getPickToSpecUsed())
						.qtyToPick(pickedToHU.getQtyPicked())
						.pickingSlotId(initialPickingJob.getPickingSlotId().orElse(null))
						.autoReview(true)
						.build());

				pickedToHU.setPickingCandidateId(pickResult.getPickingCandidateId());
			}

			pickingCandidateService.process(ProcessPickingCandidatesRequest.builder()
					.pickingCandidateIds(extractPickingCandidateIds(pickedToHUs))
					.alwaysPackEachCandidateInItsOwnHU(true)
					.build());

			return pickedToHUs;
		}
		else
		{
			return ImmutableList.of();
		}
	}

	private ImmutableList<PickedToHU> splitOutPickToHUs()
	{
		if (qtyToPick.isZero())
		{
			return ImmutableList.of();
		}

		final PackToHUsProducer packToHUsProducer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(huPIItemProductBL)
				.huCapacityBL(huCapacityBL)
				.build();

		final ProductId productId = initialStep.getProductId();
		final PackToSpec packToSpec = initialStep.getPackToSpec();
		final PickingJobStepPickFrom pickFrom = initialStep.getPickFrom(pickFromKey);

		final PackToHUsProducer.PackToInfo packToInfo = packToHUsProducer.extractPackToInfo(
				packToSpec,
				initialPickingJob.getDeliveryBPLocationId(),
				pickFrom.getPickFromLocator().getId());

		trxManager.assertThreadInheritedTrxExists();
		final IHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing();
		final HUInfo pickFromHU = pickFrom.getPickFromHU();
		final List<I_M_HU> packedHUs = packToHUsProducer.packToHU(
				huContext,
				pickFromHU.getId(),
				packToInfo,
				productId,
				qtyToPick,
				initialStep.getId().toTableRecordReference(),
				true);

		if (packedHUs.isEmpty())
		{
			throw new AdempiereException("Cannot pack to HUs from " + pickFromHU + " using " + packToInfo + ", qtyToPick=" + qtyToPick);
		}
		else if (packedHUs.size() == 1)
		{
			final I_M_HU packedHU = packedHUs.get(0);
			return ImmutableList.of(PickedToHU.builder()
					.pickedFromHUId(pickFromHU.getId())
					.actuallyPickedToHUId(HuId.ofRepoId(packedHU.getM_HU_ID()))
					.pickToSpecUsed(packToSpec)
					.qtyPicked(qtyToPick)
					.build());
		}
		else
		{
			final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
			final ImmutableList.Builder<PickedToHU> result = ImmutableList.builder();
			for (final I_M_HU packedHU : packedHUs)
			{
				final Quantity qtyPicked = huStorageFactory.getStorage(packedHU).getQuantity(productId, qtyToPick.getUOM());
				result.add(PickedToHU.builder()
						.pickedFromHUId(pickFromHU.getId())
						.actuallyPickedToHUId(HuId.ofRepoId(packedHU.getM_HU_ID()))
						.pickToSpecUsed(packToSpec)
						.qtyPicked(qtyPicked)
						.build());
			}

			return result.build();
		}
	}

	private PickingJobStep updateStepFromPickingCandidate(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey,
			@NonNull final ImmutableList<PickedToHU> pickedHUs)
	{
		final PickingJobStepPickedTo picked = toPickingJobStepPickedTo(pickedHUs);

		return step.reduceWithPickedEvent(pickFromKey, picked);
	}

	private PickingJobStepPickedTo toPickingJobStepPickedTo(@NonNull final ImmutableList<PickedToHU> pickedHUs)
	{
		return PickingJobStepPickedTo.builder()
				.qtyRejected(qtyRejected)
				.actualPickedHUs(pickedHUs.stream()
						.map(PickingJobPickCommand::toPickingJobStepPickedToHU)
						.collect(ImmutableList.toImmutableList())
				)
				.build();
	}

	private static PickingJobStepPickedToHU toPickingJobStepPickedToHU(final PickedToHU pickedHU)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(pickedHU.getPickedFromHUId())
				.actualPickedHUId(pickedHU.getActuallyPickedToHUId())
				.qtyPicked(pickedHU.getQtyPicked())
				.pickingCandidateId(Objects.requireNonNull(pickedHU.getPickingCandidateId()))
				.build();
	}

	private static ImmutableSet<PickingCandidateId> extractPickingCandidateIds(final ImmutableList<PickedToHU> pickedToHUs)
	{
		return pickedToHUs
				.stream()
				.map(PickedToHU::getPickingCandidateId)
				.peek(Objects::requireNonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	//
	// -------------------------------------
	//
	@Data
	@Builder
	private static class PickedToHU
	{
		@NonNull final HuId pickedFromHUId;

		@NonNull final PackToSpec pickToSpecUsed;
		@NonNull final HuId actuallyPickedToHUId;

		@NonNull final Quantity qtyPicked;

		@Nullable PickingCandidateId pickingCandidateId;
	}
}
