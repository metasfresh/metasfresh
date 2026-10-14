package de.metas.frontend_testing.masterdata.shipment;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

/**
 * Waits (bounded) until the shipment schedules of a sales order can be shipped: they exist, none is flagged for recompute and, if the QtyToDeliver is what is shipped, every schedule's recomputed QtyToDeliver is committed.
 * <p>
 * Schedules are created and recomputed by async workpackages after the order was completed.
 */
class ShipmentSchedulesAwaiter
{
	private static final Logger logger = LogManager.getLogger(ShipmentSchedulesAwaiter.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleInvalidateRepository invalidateRepository = Services.get(IShipmentScheduleInvalidateRepository.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	@NonNull private final OrderId orderId;
	@NonNull private final ImmutableSet<OrderLineId> orderLineIds;
	private final boolean requirePositiveQtyToDeliver;
	@NonNull private final Duration timeout;
	@NonNull private final Duration pollInterval;
	@NonNull private final Consumer<Duration> sleeper;

	@Builder
	private ShipmentSchedulesAwaiter(
			@NonNull final OrderId orderId,
			@NonNull final ImmutableSet<OrderLineId> orderLineIds,
			final boolean requirePositiveQtyToDeliver,
			@NonNull final Duration timeout,
			@NonNull final Duration pollInterval,
			@NonNull final Consumer<Duration> sleeper)
	{
		this.orderId = orderId;
		this.orderLineIds = orderLineIds;
		this.requirePositiveQtyToDeliver = requirePositiveQtyToDeliver;
		this.timeout = timeout;
		this.pollInterval = pollInterval;
		this.sleeper = sleeper;
	}

	public List<I_M_ShipmentSchedule> await()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		while (true)
		{
			final List<I_M_ShipmentSchedule> schedules = shipmentSchedulePA.getByOrderLineIds(orderLineIds);
			final String notReadyReason = getNotReadyReason(schedules);
			if (notReadyReason == null)
			{
				logger.info("{} shipment schedules of order {} are ready after {}", schedules.size(), orderId, stopwatch);
				return schedules;
			}

			if (stopwatch.elapsed().compareTo(timeout) >= 0)
			{
				throw new AdempiereException("Timeout waiting for the shipment schedules of order " + orderId + ": " + notReadyReason + ". Waited " + stopwatch.elapsed());
			}

			logger.info("Shipment schedules of order {} not ready yet: {}. Sleeping... (elapsed: {})", orderId, notReadyReason, stopwatch);
			sleeper.accept(pollInterval);
		}
	}

	@Nullable
	private String getNotReadyReason(@NonNull final List<I_M_ShipmentSchedule> schedules)
	{
		// Not every order line gets a schedule (e.g. not the lines of a service product), so we don't wait for one per line.
		// The sales order seed already waited for the schedules of the lines it created.
		if (schedules.isEmpty())
		{
			return "no shipment schedules yet";
		}

		final ImmutableSet<ShipmentScheduleId> scheduleIds = schedules.stream()
				.map(schedule -> ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());
		if (!invalidateRepository.isAllValid(scheduleIds))
		{
			return "some shipment schedules are flagged for recompute";
		}

		// The recompute deletes a schedule's M_ShipmentSchedule_Recompute marker in its own, already committed transaction
		// (ShipmentScheduleUpdater -> deleteRecomputeMarkersOutOfTrx), while the recomputed QtyToDeliver is committed a moment
		// later, with the recompute's batch. So a schedule without a marker can still have the QtyToDeliver it was created with.
		if (requirePositiveQtyToDeliver)
		{
			final long withoutQtyToDeliver = schedules.stream()
					.filter(schedule -> !schedule.isProcessed())
					.filter(schedule -> shipmentScheduleEffectiveBL.getQtyToDeliverBD(schedule).signum() <= 0)
					.count();
			if (withoutQtyToDeliver > 0)
			{
				return withoutQtyToDeliver + " shipment schedules have no QtyToDeliver (yet)";
			}
		}

		return null;
	}
}
