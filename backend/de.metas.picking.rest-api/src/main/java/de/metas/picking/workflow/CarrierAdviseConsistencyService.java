package de.metas.picking.workflow;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentSchedule;
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

/**
 * Validates that the carrier-advise attributes are consistent across all shipment schedules
 * linked to each packed top-level HU in a picking job.
 *
 * <p>Raises a user-visible {@link AdempiereException} when an inconsistency is detected
 * (e.g. multiple shippers, mixed manual/non-manual, divergent product or goods-type).
 */
@Service
@RequiredArgsConstructor
public class CarrierAdviseConsistencyService
{
	private static final AdMessageKey MSG_ManualInconsistentOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_ManualInconsistentOnHU");
	private static final AdMessageKey MSG_NonManualDivergentOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_NonManualDivergentOnHU");
	private static final AdMessageKey MSG_MultipleShippersOnHU =
			AdMessageKey.of("de.metas.picking.CarrierAdvise_MultipleShippersOnHU");

	@NonNull private final HUShipmentScheduleResolver huShipmentScheduleResolver;
	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@VisibleForTesting
	public static CarrierAdviseConsistencyService newInstanceForUnitTesting(
			@NonNull final HUShipmentScheduleResolver huShipmentScheduleResolver,
			@NonNull final ShipperRepository shipperRepository)
	{
		Adempiere.assertUnitTestMode();
		return new CarrierAdviseConsistencyService(huShipmentScheduleResolver, shipperRepository);
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

		// (two picked HUs sharing the same top-level LU can yield distinct I_M_HU instances)
		final ImmutableMap<HuId, I_M_HU> topLevelHUsById = handlingUnitsBL.getTopLevelHUsByHuIds(pickedHuIds);

		for (final I_M_HU topLevelHU : topLevelHUsById.values())
		{
			assertConsistentForHU(topLevelHU);
		}
	}

	private void assertConsistentForHU(@NonNull final I_M_HU topLevelHU)
	{
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> schedulesById =
				huShipmentScheduleResolver.resolveSchedulesByIdForHU(topLevelHU);
		if (schedulesById.isEmpty())
		{
			return;
		}

		// collect shipper IDs from all schedules (null shipper IDs filtered out)
		final ImmutableSet<ShipperId> allShipperIds = schedulesById.values().stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final Map<ShipperId, Shipper> shippersById = shipperRepository.getByIds(allShipperIds);

		// restrict to advise-enabled schedules
		final ImmutableSet<ShipmentSchedule> adviseEnabledSchedules = schedulesById.values().stream()
				.filter(s -> isAdviseEnabled(s, shippersById))
				.collect(ImmutableSet.toImmutableSet());

		if (adviseEnabledSchedules.isEmpty())
		{
			return;
		}

		final HuId huId = HuId.ofRepoId(topLevelHU.getM_HU_ID());

		// (E3) all advise-enabled schedules must belong to the same shipper
		final ImmutableSet<ShipperId> adviseShipperIds = adviseEnabledSchedules.stream()
				.map(ShipmentSchedule::getShipperId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (adviseShipperIds.size() > 1)
		{
			throw new AdempiereException(MSG_MultipleShippersOnHU, huId.getRepoId())
					.markAsUserValidationError();
		}

		final boolean anyManual = adviseEnabledSchedules.stream()
				.anyMatch(s -> s.getCarrierAdvisingStatus().isManual());
		final boolean anyNonManual = adviseEnabledSchedules.stream()
				.anyMatch(s -> !s.getCarrierAdvisingStatus().isManual());

		// (E1) mix of manual + non-manual
		if (anyManual && anyNonManual)
		{
			throw new AdempiereException(MSG_ManualInconsistentOnHU, huId.getRepoId())
					.markAsUserValidationError();
		}

		if (anyManual)
		{
			// (E1) all manual: all must share identical (CarrierProductId, CarrierGoodsTypeId, CarrierServiceIds)
			final ImmutableSet<ManualAdviseKey> manualKeys = adviseEnabledSchedules.stream()
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
			// (E2) all non-manual: check for divergent CarrierProductId or CarrierGoodsTypeId.
			// A carrier product/goods-type is set whenever the shipper is set; it is null only when
			// advise failed (the QtyToDeliver=0 case never reaches here — it has no picked records).
			// Counting distinct values including null routes a failed-advise schedule (null vs a set
			// product on the same HU) to E2, which the picker resolves with re-advise.
			// Stream.distinct() tolerates null, unlike ImmutableSet.toImmutableSet().
			final long distinctProductIds = adviseEnabledSchedules.stream()
					.map(ShipmentSchedule::getCarrierProductId)
					.distinct()
					.count();
			if (distinctProductIds > 1)
			{
				throw new AdempiereException(MSG_NonManualDivergentOnHU, huId.getRepoId())
						.markAsUserValidationError();
			}

			final long distinctGoodsTypeIds = adviseEnabledSchedules.stream()
					.map(ShipmentSchedule::getCarrierGoodsTypeId)
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

	@NonNull
	private static ManualAdviseKey toManualAdviseKey(@NonNull final ShipmentSchedule schedule)
	{
		return new ManualAdviseKey(
				schedule.getCarrierProductId(),
				schedule.getCarrierGoodsTypeId(),
				ImmutableSet.copyOf(schedule.getCarrierServicesIfLoaded())
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
