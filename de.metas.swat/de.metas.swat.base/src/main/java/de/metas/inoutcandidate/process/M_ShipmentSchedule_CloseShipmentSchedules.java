package de.metas.inoutcandidate.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.JavaProcess;

public class M_ShipmentSchedule_CloseShipmentSchedules extends JavaProcess
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
