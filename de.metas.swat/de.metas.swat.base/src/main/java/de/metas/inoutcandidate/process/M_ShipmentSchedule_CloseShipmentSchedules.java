package de.metas.inoutcandidate.process;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class M_ShipmentSchedule_CloseShipmentSchedules extends SvrProcess
{
	@Override
	protected void prepare()
	{

	}

	@Override
	protected String doIt() throws Exception
	{
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter = getProcessInfo().getQueryFilter();

		IQueryBuilder<I_M_ShipmentSchedule> queryBuilderForShipmentScheduleSelection = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter);

		final Iterator<I_M_ShipmentSchedule> schedulesToUpdateIterator = queryBuilderForShipmentScheduleSelection
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_QtyPickList, Env.ZERO)
				.create()
				.iterate(I_M_ShipmentSchedule.class);

		if (!schedulesToUpdateIterator.hasNext())
		{
			throw new AdempiereException("@NoSelection@");
		}

		while (schedulesToUpdateIterator.hasNext())
		{
			final I_M_ShipmentSchedule schedule = schedulesToUpdateIterator.next();

			shipmentScheduleBL.closeShipmentSchedule(schedule);
		}

		return MSG_OK;
	}

}
