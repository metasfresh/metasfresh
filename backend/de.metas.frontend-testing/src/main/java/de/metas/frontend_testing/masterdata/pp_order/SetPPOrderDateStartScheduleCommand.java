package de.metas.frontend_testing.masterdata.pp_order;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order;
import de.metas.util.Services;

import java.time.ZonedDateTime;

/**
 * Sets {@code PP_Order.DateStartSchedule} of an existing manufacturing order, e.g. to put it on a given day of the mobile job list.
 */
@Builder
public class SetPPOrderDateStartScheduleCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final Request request;

	@Value
	@Builder
	@Jacksonized
	public static class Request
	{
		/** the order's {@code DocumentNo} */
		@NonNull String ppOrder;
		@NonNull ZonedDateTime dateStartSchedule;
	}

	public void execute()
	{
		// Direct column update, not a model save: saving a PP_Order runs its interceptors, which reject any change
		// (e.g. "Cannot quantity is not allowed because there is something already processed") once the order has
		// cost collectors, and the start date is plain scheduling data.
		final int updatedCount = queryBL.createQueryBuilder(I_PP_Order.class)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_DocumentNo, request.getPpOrder())
				.create()
				.updateDirectly()
				.addSetColumnValue(I_PP_Order.COLUMNNAME_DateStartSchedule, TimeUtil.asTimestamp(request.getDateStartSchedule()))
				.execute();
		if (updatedCount != 1)
		{
			throw new AdempiereException("Expected to update exactly one PP_Order with DocumentNo=" + request.getPpOrder() + " but updated " + updatedCount);
		}
	}
}
