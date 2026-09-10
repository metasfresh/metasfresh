package de.metas.distribution.ddorder.replenishment.alloc;

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
}
