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

package de.metas.inoutcandidate.process;

import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;

public class M_ShipmentSchedule_ChangeExportStatus extends JavaProcess implements IProcessPrecondition
{
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

	private static final AdMessageKey MSG_NO_UNPROCESSED_LINES = AdMessageKey.of("shipmentschedule.noUnprocessedLines");

	private static final String PARAM_ExportStatus = "ExportStatus";
	@Param(parameterName = PARAM_ExportStatus, mandatory = true)
	private String exportStatus;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected void prepare()
	{
		final IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter = getProcessInfo().getQueryFilterOrElseFalse();
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilderForShipmentSchedulesSelection = shipmentSchedulePA.createQueryForShipmentScheduleSelection(getCtx(), userSelectionFilter);

		// Create selection and return how many items were added
		final int selectionCount = queryBuilderForShipmentSchedulesSelection
				.create()
				.createSelection(getPinstanceId());

		if (selectionCount <= 0)
		{
			throw new AdempiereException(MSG_NO_UNPROCESSED_LINES)
					.markAsUserValidationError();
		}

	}

	@Override
	protected String doIt() throws Exception
	{
		final PInstanceId pinstanceId = getPinstanceId();

		// update delivery date
		// no invalidation
		shipmentSchedulePA.updateExportStatus(exportStatus, pinstanceId);

		return MSG_OK;
	}
}
