package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_DD_Order;
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
 * the order by aggregation (carrier and sales order).
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
	 * A distribution order line is matched when at least one of THREE routes leads to one of {@code salesOrderIds},
	 * OR-ed inside this one query (never separate {@code addInSubQueryFilter} calls on the caller's side, which
	 * would AND):
	 * <ul>
	 * <li>the association route: {@code DD_OrderLine_PickingJobSchedule} -> {@code M_Picking_Job_Schedule} ->
	 * {@code M_ShipmentSchedule} -> its sales-order line -> {@code C_Order_ID}</li>
	 * <li>the sales-order-line route: {@code DD_OrderLine.C_OrderLineSO_ID} -> {@code C_OrderLine.C_Order_ID}</li>
	 * <li>the header route: {@code DD_Order.C_Order_ID} -- retained because {@code DD_OrderLine.C_OrderLineSO_ID} is
	 * only populated while the line-level aggregation sysconfig is on ({@code DDOrderCandidateService}'s
	 * {@code aggregateBySalesOrderLineId}); dropping the header would regress candidate orders wherever that
	 * sysconfig is off.</li>
	 * </ul>
	 */
	public IQuery<I_DD_OrderLine> bySalesOrderIds(@NonNull final Set<OrderId> salesOrderIds)
	{
		Check.assumeNotEmpty(salesOrderIds, "salesOrderIds is not empty");

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_DD_OrderLine> builder = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter();
		builder.addCompositeQueryFilter().setJoinOr()
				// route 1: contributors -> picking job schedule -> shipment schedule -> its sales-order line's order
				.addInSubQueryFilter(
						I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID,
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID,
						contributorsOfSchedulesWithSalesOrder(salesOrderIds))
				// route 2: the line's own sales-order line
				.addInSubQueryFilter(
						I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID,
						I_C_OrderLine.COLUMNNAME_C_OrderLine_ID,
						orderLinesOfSalesOrders(salesOrderIds))
				// route 3: the header (see javadoc above for why it stays)
				.addInSubQueryFilter(
						I_DD_OrderLine.COLUMNNAME_DD_Order_ID,
						I_DD_Order.COLUMNNAME_DD_Order_ID,
						ddOrdersWithSalesOrder(salesOrderIds));
		return builder.create();
	}

	private IQuery<I_DD_OrderLine_PickingJobSchedule> contributorsOfSchedulesWithSalesOrder(@NonNull final Set<OrderId> salesOrderIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_Picking_Job_Schedule> pickingJobSchedulesWithSalesOrder = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID,
						I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
						schedulesWithSalesOrder(salesOrderIds))
				.create();

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						pickingJobSchedulesWithSalesOrder)
				.create();
	}

	private IQuery<I_M_ShipmentSchedule> schedulesWithSalesOrder(@NonNull final Set<OrderId> salesOrderIds)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID,
						I_C_OrderLine.COLUMNNAME_C_OrderLine_ID,
						orderLinesOfSalesOrders(salesOrderIds))
				.create();
	}

	private IQuery<I_C_OrderLine> orderLinesOfSalesOrders(@NonNull final Set<OrderId> salesOrderIds)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, salesOrderIds)
				.create();
	}

	private IQuery<I_DD_Order> ddOrdersWithSalesOrder(@NonNull final Set<OrderId> salesOrderIds)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_DD_Order.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_Order.COLUMNNAME_C_Order_ID, salesOrderIds)
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

	/**
	 * The batched pair lookup behind the sales-order facet's chip counts: for the given orders, every DISTINCT
	 * {@code (DD_Order_ID, C_Order_ID)} pair reachable through any of the three routes {@link #bySalesOrderIds}
	 * matches on (association, sales-order-line, or header) — never a plain distinct set of order ids, for the
	 * same reason as {@link #getCarrierProductIdsByDDOrderIds}: the per-job association is what the hit counter
	 * needs. Folding the header route into this SAME multimap — rather than reading
	 * {@code I_DD_Order.getC_Order_ID()} separately in the collector — matters for correctness, not just
	 * uniformity: a real candidate order normally carries BOTH the header AND one of the line-level signals (both
	 * aggregation sysconfigs default {@code true}), and the returned type is a {@code SetMultimap}, so the same
	 * {@code (DD_Order_ID, C_Order_ID)} pair contributed by two routes collapses to one entry — collecting the
	 * header separately would double-count such an order's hit.
	 */
	public ImmutableSetMultimap<DDOrderId, OrderId> getSalesOrderIdsByDDOrderIds(@NonNull final Collection<DDOrderId> ddOrderIds)
	{
		if (ddOrderIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ImmutableSetMultimap.Builder<DDOrderId, OrderId> result = ImmutableSetMultimap.builder();

		// route 3: the header
		queryBL.createQueryBuilder(I_DD_Order.class)
				.addInArrayFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.iterateAndStream()
				.forEach(ddOrder -> {
					final OrderId salesOrderId = OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID());
					if (salesOrderId != null)
					{
						result.put(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()), salesOrderId);
					}
				});

		final List<I_DD_OrderLine> lines = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addInArrayFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrderIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		if (lines.isEmpty())
		{
			return result.build();
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

		// route 2: the line's own sales-order line -> C_OrderLine.C_Order_ID
		if (!ddOrderIdByOrderLineSOId.isEmpty())
		{
			queryBL.createQueryBuilder(I_C_OrderLine.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID, ddOrderIdByOrderLineSOId.keySet())
					.create()
					.iterateAndStream()
					.forEach(orderLine -> {
						final OrderId salesOrderId = OrderId.ofRepoIdOrNull(orderLine.getC_Order_ID());
						final DDOrderId ddOrderId = ddOrderIdByOrderLineSOId.get(orderLine.getC_OrderLine_ID());
						if (salesOrderId != null && ddOrderId != null)
						{
							result.put(ddOrderId, salesOrderId);
						}
					});
		}

		// route 1: contributors -> picking job schedule -> shipment schedule -> its sales-order line -> sales order
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
			final HashMap<Integer, Integer> orderLineIdByShipmentScheduleId = new HashMap<>();
			if (!shipmentScheduleIds.isEmpty())
			{
				queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
						.addOnlyActiveRecordsFilter()
						.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
						.create()
						.iterateAndStream()
						.forEach(schedule -> orderLineIdByShipmentScheduleId.put(schedule.getM_ShipmentSchedule_ID(), schedule.getC_OrderLine_ID()));
			}

			final ImmutableSet<Integer> orderLineIds = ImmutableSet.copyOf(orderLineIdByShipmentScheduleId.values());
			final HashMap<Integer, OrderId> salesOrderIdByOrderLineId = new HashMap<>();
			if (!orderLineIds.isEmpty())
			{
				queryBL.createQueryBuilder(I_C_OrderLine.class)
						.addOnlyActiveRecordsFilter()
						.addInArrayFilter(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID, orderLineIds)
						.create()
						.iterateAndStream()
						.forEach(orderLine -> {
							final OrderId salesOrderId = OrderId.ofRepoIdOrNull(orderLine.getC_Order_ID());
							if (salesOrderId != null)
							{
								salesOrderIdByOrderLineId.put(orderLine.getC_OrderLine_ID(), salesOrderId);
							}
						});
			}

			for (final I_DD_OrderLine_PickingJobSchedule contributor : contributors)
			{
				final Integer shipmentScheduleId = shipmentScheduleIdByPickingJobScheduleId.get(contributor.getM_Picking_Job_Schedule_ID());
				final Integer orderLineId = shipmentScheduleId != null ? orderLineIdByShipmentScheduleId.get(shipmentScheduleId) : null;
				final OrderId salesOrderId = orderLineId != null ? salesOrderIdByOrderLineId.get(orderLineId) : null;
				final DDOrderId ddOrderId = ddOrderIdByLineId.get(contributor.getDD_OrderLine_ID());
				if (salesOrderId != null && ddOrderId != null)
				{
					result.put(ddOrderId, salesOrderId);
				}
			}
		}

		return result.build();
	}
}
