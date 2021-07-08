package de.metas.ui.web.inoutcandidate.process;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class M_ShipmentSchedule_Recompute extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IShipmentScheduleInvalidateRepository shipmentScheduleInvalidateRepository = Services.get(IShipmentScheduleInvalidateRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_ShipmentSchedule> queryFilterOrElseFalse = getProcessInfo().getQueryFilterOrElseFalse();

		final IQuery<I_M_ShipmentSchedule> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule.class).filter(queryFilterOrElseFalse).create();

		shipmentScheduleInvalidateRepository.invalidateShipmentSchedulesFor(query);

		return MSG_OK;
	}

}
