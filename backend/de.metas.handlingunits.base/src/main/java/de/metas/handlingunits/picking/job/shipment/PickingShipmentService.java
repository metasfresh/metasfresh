package de.metas.handlingunits.picking.job.shipment;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

import static de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

@Service
@RequiredArgsConstructor
public class PickingShipmentService
{
	@NonNull private final MobileUIPickingUserProfileRepository configRepository;
	@NonNull private final IShipmentService shipmentService;

	@VisibleForTesting
	public static PickingShipmentService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new PickingShipmentService(
				new MobileUIPickingUserProfileRepository(),
				ShipmentService.getInstance()
		);
	}

	public void createShipmentIfNeeded(final PickingJob pickingJob)
	{
		prepareShipmentCandidates(pickingJob, ImmutableSet.of(), null)
				.forEach(this::createShipment);
	}

	public void createShipmentForLUs(@NonNull final PickingJob pickingJob, @NonNull final Set<HuId> luIds)
	{
		prepareShipmentCandidates(pickingJob, ImmutableSet.copyOf(luIds), CreateShipmentPolicy.CREATE_COMPLETE_CLOSE)
				.forEach(this::createShipment);
	}

	private Collection<PickingShipmentCandidate> prepareShipmentCandidates(
			@NonNull final PickingJob pickingJob,
			@NonNull final ImmutableSet<HuId> onlyLUIds,
			@Nullable final CreateShipmentPolicy createShipmentPolicyOverride
	)
	{
		final LinkedHashMap<PickingShipmentCandidateKey, PickingShipmentCandidate> shipmentCandidates = new LinkedHashMap<>();
		for (final PickingJobLine line : pickingJob.getLines())
		{
			final PickingShipmentCandidateKey key = PickingShipmentCandidateKey.of(line);
			PickingShipmentCandidate shipmentCandidate = shipmentCandidates.get(key);
			if (shipmentCandidate == null)
			{
				final CreateShipmentPolicy createShipmentPolicyEffective = CoalesceUtil.coalesceNotNull(
						createShipmentPolicyOverride,
						() -> configRepository.getPickingJobOptions(key.getCustomerId()).getCreateShipmentPolicy()
				);
				if (!createShipmentPolicyEffective.isCreateShipment())
				{
					continue;
				}

				shipmentCandidate = PickingShipmentCandidate.builder()
						.key(key)
						.onlyLUIds(onlyLUIds)
						.createShipmentPolicy(createShipmentPolicyEffective)
						.build();
				shipmentCandidates.put(key, shipmentCandidate);
			}

			if (shipmentCandidate != null)
			{
				shipmentCandidate.addLine(line);
			}
		}

		return shipmentCandidates.values();
	}

	private void createShipment(@NonNull final PickingShipmentCandidate shipmentCandidate)
	{
		final CreateShipmentPolicy createShipmentPolicy = shipmentCandidate.getCreateShipmentPolicy();
		if (!createShipmentPolicy.isCreateShipment())
		{
			return;
		}

		shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
				.scheduleIds(shipmentCandidate.getShipmentScheduleIds())
				.quantityTypeToUse(TYPE_PICKED_QTY)
				.onlyLUIds(shipmentCandidate.getOnlyLUIds())
				.onTheFlyPickToPackingInstructions(true)
				.isCompleteShipment(createShipmentPolicy.isCreateAndCompleteShipment())
				.isCloseShipmentSchedules(createShipmentPolicy.isCloseShipmentSchedules())
				// since we are not going to immediately create invoices, we want to move on and to not wait for shipments
				.waitForShipments(false)
				.build());
	}

}
