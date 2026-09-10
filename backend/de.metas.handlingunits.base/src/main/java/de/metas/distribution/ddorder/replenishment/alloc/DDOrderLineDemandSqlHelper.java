package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_OrderLine;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Builds line-side demand restrictions for the distribution launcher: an unexecuted {@link IQuery} over
 * {@code DD_OrderLine}, handed to {@link de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO} to run.
 * <p>
 * This is a NEW class rather than extra methods on {@link DDOrderLineContributorRepository}: the restrictions span
 * the association table, {@code M_Picking_Job_Schedule}, {@code M_ShipmentSchedule} and {@code DD_OrderLine.C_OrderLineSO_ID}
 * — well past what that repository's name claims. It is also the home for further demand-side facets cut off from
 * the order by aggregation (carrier here; sales order is the next one).
 * <p>
 * <b>Known, deliberate exception to the "IQueryBL only inside DAO/Repository classes" rule.</b> This class performs
 * no persistence I/O itself — it hands back an unexecuted query for a DAO to run — which satisfies that rule's
 * purpose (keep query construction out of business-logic classes) while not matching its letter (this class is
 * named neither {@code *DAO} nor {@code *Repository}). That is intentional: renaming this class to fit the keyword
 * would misdescribe it, so it stays a named, understood exception rather than a naming workaround.
 */
@UtilityClass
public class DDOrderLineDemandSqlHelper
{
	/**
	 * A distribution order line is matched when at least one of its active contributor rows leads to a shipment
	 * schedule carrying one of {@code carrierProductIds} — via EITHER of two routes, OR-ed inside this one query
	 * (never two separate {@code addInSubQueryFilter} calls on the caller's side, which would AND):
	 * <ul>
	 * <li>the association route: {@code DD_OrderLine_PickingJobSchedule} -> {@code M_Picking_Job_Schedule} -> {@code M_ShipmentSchedule.Carrier_Product_ID}</li>
	 * <li>the sales-order-line route: {@code DD_OrderLine.C_OrderLineSO_ID} -> {@code M_ShipmentSchedule.C_OrderLine_ID}</li>
	 * </ul>
	 */
	public IQuery<I_DD_OrderLine> byCarrierProductIds(@NonNull final Set<CarrierProductId> carrierProductIds)
	{
		Check.assumeNotEmpty(carrierProductIds, "carrierProductIds is not empty");

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_DD_OrderLine> builder = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter();
		builder.addCompositeQueryFilter().setJoinOr()
				// route 1: contributors -> picking job schedule -> shipment schedule
				.addInSubQueryFilter(
						I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID,
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID,
						contributorsOfSchedulesWithCarrier(carrierProductIds))
				// route 2: the line's own sales-order line
				.addInSubQueryFilter(
						I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID,
						I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID,
						schedulesWithCarrier(carrierProductIds));
		return builder.create();
	}

	private IQuery<I_DD_OrderLine_PickingJobSchedule> contributorsOfSchedulesWithCarrier(@NonNull final Set<CarrierProductId> carrierProductIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Job_Schedule> pickingJobSchedulesWithCarrier = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID,
						I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
						schedulesWithCarrier(carrierProductIds))
				.create();

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						pickingJobSchedulesWithCarrier)
				.create();
	}

	private IQuery<I_M_ShipmentSchedule> schedulesWithCarrier(@NonNull final Set<CarrierProductId> carrierProductIds)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_Carrier_Product_ID, carrierProductIds)
				.create();
	}

	/**
	 * The batched pair lookup behind the carrier facet's chip counts: for the given orders, every DISTINCT
	 * {@code (DD_Order_ID, Carrier_Product_ID)} pair reachable through either demand route (association or
	 * sales-order-line), never a plain distinct set of carrier ids. A plain distinct set — mirroring
	 * {@code DDOrderLowLevelDAO.getProductIdsByDDOrderIds}, which returns {@code listDistinct(M_Product_ID)} over
	 * the whole batch — would lose the per-job association the hit counter needs: every chip would count 1
	 * regardless of how many jobs share that carrier. The returned multimap keeps that association — one entry per
	 * distinct (order, carrier) combination — so {@code DistributionFacetsCollector#collectCarrier} can increment
	 * the hit counter once per job that actually carries the chip's carrier product.
	 * <p>
	 * This method lives here — not on {@code DDOrderLowLevelDAO} (module {@code de.metas.manufacturing}) — because
	 * that module has no dependency on {@code de.metas.swat.base} ({@link I_M_ShipmentSchedule},
	 * {@link I_M_Picking_Job_Schedule}) or on this module's own {@link I_DD_OrderLine_PickingJobSchedule}, and must
	 * gain none (constraint: restrictions reach it only as opaque {@link IQuery}). This module already has all four.
	 */
	public ImmutableSetMultimap<DDOrderId, CarrierProductId> getCarrierProductIdsByDDOrderIds(@NonNull final Collection<DDOrderId> ddOrderIds)
	{
		if (ddOrderIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_DD_OrderLine> lines = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addInArrayFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrderIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		if (lines.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final HashMap<Integer, DDOrderId> ddOrderIdByLineId = new HashMap<>();
		final HashMap<Integer, DDOrderId> ddOrderIdByOrderLineSOId = new HashMap<>();
		for (final I_DD_OrderLine line : lines)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(line.getDD_Order_ID());
			ddOrderIdByLineId.put(line.getDD_OrderLine_ID(), ddOrderId);
			if (line.getC_OrderLineSO_ID() > 0)
			{
				ddOrderIdByOrderLineSOId.put(line.getC_OrderLineSO_ID(), ddOrderId);
			}
		}

		final ImmutableSetMultimap.Builder<DDOrderId, CarrierProductId> result = ImmutableSetMultimap.builder();

		// route 2: the line's own sales-order line -> M_ShipmentSchedule.C_OrderLine_ID -> Carrier_Product_ID
		if (!ddOrderIdByOrderLineSOId.isEmpty())
		{
			queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID, ddOrderIdByOrderLineSOId.keySet())
					.create()
					.iterateAndStream()
					.forEach(schedule -> {
						final CarrierProductId carrierProductId = CarrierProductId.ofRepoIdOrNull(schedule.getCarrier_Product_ID());
						final DDOrderId ddOrderId = ddOrderIdByOrderLineSOId.get(schedule.getC_OrderLine_ID());
						if (carrierProductId != null && ddOrderId != null)
						{
							result.put(ddOrderId, carrierProductId);
						}
					});
		}

		// route 1: contributors -> picking job schedule -> shipment schedule
		final List<I_DD_OrderLine_PickingJobSchedule> contributors = queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, ddOrderIdByLineId.keySet())
				.create()
				.list();

		if (!contributors.isEmpty())
		{
			final ImmutableSet<Integer> pickingJobScheduleIds = contributors.stream()
					.map(I_DD_OrderLine_PickingJobSchedule::getM_Picking_Job_Schedule_ID)
					.collect(ImmutableSet.toImmutableSet());

			final HashMap<Integer, Integer> shipmentScheduleIdByPickingJobScheduleId = new HashMap<>();
			queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds)
					.create()
					.iterateAndStream()
					.forEach(pickingJobSchedule -> shipmentScheduleIdByPickingJobScheduleId.put(
							pickingJobSchedule.getM_Picking_Job_Schedule_ID(),
							pickingJobSchedule.getM_ShipmentSchedule_ID()));

			final ImmutableSet<Integer> shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIdByPickingJobScheduleId.values());
			final HashMap<Integer, CarrierProductId> carrierProductIdByShipmentScheduleId = new HashMap<>();
			if (!shipmentScheduleIds.isEmpty())
			{
				queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
						.addOnlyActiveRecordsFilter()
						.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
						.create()
						.iterateAndStream()
						.forEach(schedule -> {
							final CarrierProductId carrierProductId = CarrierProductId.ofRepoIdOrNull(schedule.getCarrier_Product_ID());
							if (carrierProductId != null)
							{
								carrierProductIdByShipmentScheduleId.put(schedule.getM_ShipmentSchedule_ID(), carrierProductId);
							}
						});
			}

			for (final I_DD_OrderLine_PickingJobSchedule contributor : contributors)
			{
				final Integer shipmentScheduleId = shipmentScheduleIdByPickingJobScheduleId.get(contributor.getM_Picking_Job_Schedule_ID());
				final CarrierProductId carrierProductId = shipmentScheduleId != null ? carrierProductIdByShipmentScheduleId.get(shipmentScheduleId) : null;
				final DDOrderId ddOrderId = ddOrderIdByLineId.get(contributor.getDD_OrderLine_ID());
				if (carrierProductId != null && ddOrderId != null)
				{
					result.put(ddOrderId, carrierProductId);
				}
			}
		}

		return result.build();
	}
}
