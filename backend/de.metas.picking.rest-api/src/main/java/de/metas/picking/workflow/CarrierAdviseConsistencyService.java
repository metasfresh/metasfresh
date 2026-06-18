package de.metas.picking.workflow;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.shipper.gateway.commons.model.ShipperConfigRepository;
import de.metas.shipping.CarrierProductId;
import de.metas.shipping.Shipper;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Validates that the carrier-advise attributes are consistent across all shipment schedules
 * linked to each packed top-level HU in a picking job.
 *
 * <p>Raises a user-visible {@link AdempiereException} when an inconsistency is detected
 * (e.g. mixed manual/non-manual, or divergent carrier product or goods-type across lines on the same HU).
 */
@Service
@RequiredArgsConstructor
public class CarrierAdviseConsistencyService
{
	private static final AdMessageKey MSG_ManualInconsistentOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU");
	private static final AdMessageKey MSG_NonManualDivergentOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU");

	@NonNull private final HUShipmentScheduleResolver huShipmentScheduleResolver;
	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final ShipperConfigRepository shipperConfigRepository;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@VisibleForTesting
	public static CarrierAdviseConsistencyService newInstanceForUnitTesting(
			@NonNull final HUShipmentScheduleResolver huShipmentScheduleResolver,
			@NonNull final ShipperRepository shipperRepository,
			@NonNull final ShipperConfigRepository shipperConfigRepository)
	{
		Adempiere.assertUnitTestMode();
		return new CarrierAdviseConsistencyService(huShipmentScheduleResolver, shipperRepository, shipperConfigRepository);
	}

	/**
	 * Checks every distinct top-level HU that has been picked on the given job.
	 * Throws on the first inconsistency detected.
	 */
	public void assertConsistentForJob(@NonNull final PickingJob pickingJob)
	{
		final ImmutableSet<HuId> pickedHuIds = pickingJob.getAllPickedHuIds();
		if (pickedHuIds.isEmpty())
		{
			return;
		}

		// The picking-job LINE is the carrier-advise source of truth: the carrier VALUES + manual flag
		// are read from the line (matched to a schedule by ShipmentScheduleId), not from the schedule.
		final ImmutableMap<ShipmentScheduleId, PickingJobLine> linesByScheduleId = pickingJob.streamLines()
				.collect(ImmutableMap.toImmutableMap(
						line -> line.getScheduleId().getShipmentScheduleId(),
						Function.identity(),
						// First-wins is safe ONLY while there is at most one picking-job line per shipment schedule
						// (today's reality). me03 #30350 T9 will enable N picking-job-schedules (lines) per shipment
						// schedule — each independently re-advised at packing, so they CAN diverge — at which point this
						// consistency check must become line-centric (group all advised lines per top-level HU) rather
						// than collapsing to one line per schedule.
						(first, second) -> first));

		// (two picked HUs sharing the same top-level LU can yield distinct I_M_HU instances)
		final ImmutableMap<HuId, I_M_HU> topLevelHUsById = handlingUnitsBL.getTopLevelHUsByHuIds(pickedHuIds);

		for (final I_M_HU topLevelHU : topLevelHUsById.values())
		{
			assertConsistentForHU(topLevelHU, linesByScheduleId);
		}
	}

	private void assertConsistentForHU(
			@NonNull final I_M_HU topLevelHU,
			@NonNull final Map<ShipmentScheduleId, PickingJobLine> linesByScheduleId)
	{
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById =
				huShipmentScheduleResolver.resolveSchedulesByIdForHU(topLevelHU);
		if (schedulesById.isEmpty())
		{
			return;
		}

		// The advise-enabled GATE stays schedule/shipper-based: the shipper is legitimately header-level.
		// collect shipper IDs from all schedules (null shipper IDs filtered out)
		final ImmutableSet<ShipperId> allShipperIds = schedulesById.values().stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final Map<ShipperId, Shipper> shippersById = shipperRepository.getByIds(allShipperIds);

		// restrict to advise-enabled schedules that have a corresponding line in this job; read each
		// schedule's carrier VALUES + manual flag from its picking-job LINE. A schedule with no line in
		// this job is not part of this job's picked state, so it is skipped.
		final ImmutableSet<ShipmentSchedule> adviseEnabledSchedules = schedulesById.values().stream()
				.filter(s -> isAdviseEnabled(s, shippersById))
				.filter(s -> linesByScheduleId.get(s.getId()) != null)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<PickingJobLine> adviseEnabledLines = adviseEnabledSchedules.stream()
				.map(s -> linesByScheduleId.get(s.getId()))
				.collect(ImmutableSet.toImmutableSet());

		if (adviseEnabledLines.isEmpty())
		{
			return;
		}

		final HuId huId = HuId.ofRepoId(topLevelHU.getM_HU_ID());

		final boolean anyManual = adviseEnabledLines.stream()
				.anyMatch(PickingJobLine::isManual);
		final boolean anyNonManual = adviseEnabledLines.stream()
				.anyMatch(line -> !line.isManual());

		// (E1) mix of manual + non-manual
		if (anyManual && anyNonManual)
		{
			throw new AdempiereException(MSG_ManualInconsistentOnHU, huId.getRepoId())
					.markAsUserValidationError();
		}

		if (anyManual)
		{
			// (E1) all manual: all must share identical (CarrierProductId, CarrierGoodsTypeId, CarrierServiceIds)
			final ImmutableSet<ManualAdviseKey> manualKeys = adviseEnabledLines.stream()
					.map(CarrierAdviseConsistencyService::toManualAdviseKey)
					.collect(ImmutableSet.toImmutableSet());
			if (manualKeys.size() > 1)
			{
				throw new AdempiereException(MSG_ManualInconsistentOnHU, huId.getRepoId())
						.markAsUserValidationError();
			}
		}
		else
		{
			// (E2) all non-manual: the divergence check applies ONLY when the HU's shipper has selection rules
			// OFF (Carrier_Config.IsSelectionRules='N') — then the explicit carrier product is authoritative and
			// divergence is a real problem the picker must fix. When selection rules are ON (the column default
			// 'Y', incl. no config row), nShift resolves the carrier via its rules and a re-advise harmonises it,
			// so divergence is not a completion blocker → skip E2 (the job completes silently). E1 (manual) is
			// unaffected — manual overrides are authoritative regardless of selection rules.
			if (anyShipperHasSelectionRulesOn(adviseEnabledSchedules))
			{
				return;
			}

			// (E2) all non-manual: check for divergent CarrierProductId or CarrierGoodsTypeId.
			// A carrier product/goods-type is set whenever advise succeeded; it is null when advise failed
			// (the QtyToDeliver=0 case never reaches here — it has no picked records).
			// Counting distinct values including null routes a failed-advise line (null vs a set
			// product on the same HU) to E2, which the picker resolves with re-advise.
			// Stream.distinct() tolerates null, unlike ImmutableSet.toImmutableSet().
			final long distinctProductIds = adviseEnabledLines.stream()
					.map(PickingJobLine::getCarrierProductId)
					.distinct()
					.count();
			if (distinctProductIds > 1)
			{
				throw new AdempiereException(MSG_NonManualDivergentOnHU, huId.getRepoId())
						.markAsUserValidationError();
			}

			final long distinctGoodsTypeIds = adviseEnabledLines.stream()
					.map(PickingJobLine::getCarrierGoodsTypeId)
					.distinct()
					.count();
			if (distinctGoodsTypeIds > 1)
			{
				throw new AdempiereException(MSG_NonManualDivergentOnHU, huId.getRepoId())
						.markAsUserValidationError();
			}
		}
	}

	private static boolean isAdviseEnabled(
			@NonNull final ShipmentSchedule schedule,
			@NonNull final Map<ShipperId, Shipper> shippersById)
	{
		final ShipperId shipperId = schedule.getShipperId();
		if (shipperId == null)
		{
			return false;
		}
		final Shipper shipper = shippersById.get(shipperId);
		return shipper != null && shipper.isApiCarrierAdvise();
	}

	/**
	 * True if at least one of the given advise-enabled schedules has a shipper with selection rules ON
	 * ({@code Carrier_Config.IsSelectionRules='Y'} / no config row → default 'Y'). One shipper per HU is the
	 * norm post-E3; with selection rules ON nShift auto-resolves the carrier, so the E2 divergence check is
	 * skipped. Schedules carry a non-null shipper id here (they passed {@link #isAdviseEnabled}).
	 */
	private boolean anyShipperHasSelectionRulesOn(@NonNull final ImmutableSet<ShipmentSchedule> adviseEnabledSchedules)
	{
		return adviseEnabledSchedules.stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.anyMatch(shipperConfigRepository::isSelectionRules);
	}

	@NonNull
	private static ManualAdviseKey toManualAdviseKey(@NonNull final PickingJobLine line)
	{
		return new ManualAdviseKey(
				line.getCarrierProductId(),
				line.getCarrierGoodsTypeId(),
				ImmutableSet.copyOf(line.getCarrierServices())
		);
	}

	@Value
	private static class ManualAdviseKey
	{
		@Nullable CarrierProductId carrierProductId;
		@Nullable CarrierGoodsTypeId carrierGoodsTypeId;
		@NonNull ImmutableSet<CarrierServiceId> carrierServiceIds;
	}
}
