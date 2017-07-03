package de.metas.handlingunits.trace.process;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MovementLine;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUTrxDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.trace.HUTraceUtil;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class M_HU_Trace_CreateForHU extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final I_M_HU hu = getRecord(I_M_HU.class);

		// write the HU_Assigment related trace
		{
			final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
			for (final I_M_InOutLine iol : huAssignmentDAO.retrieveModelsForHU(hu, I_M_InOutLine.class))
			{
				HUTraceUtil.createdAndAddFor(iol.getM_InOut(), Stream.of(iol));
			}

			for (final I_M_MovementLine movementLine : huAssignmentDAO.retrieveModelsForHU(hu, I_M_MovementLine.class))
			{
				HUTraceUtil.createdAndAddFor(movementLine.getM_Movement(), Stream.of(movementLine));
			}

			for (final I_PP_Cost_Collector costCollector : huAssignmentDAO.retrieveModelsForHU(hu, I_PP_Cost_Collector.class))
			{
				HUTraceUtil.createdAndAddFor(costCollector);
			}
		}

		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		final List<I_M_ShipmentSchedule_QtyPicked> schedsQtyPicked = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(hu);
		for (final I_M_ShipmentSchedule_QtyPicked schedQtyPicked : schedsQtyPicked)
		{
			HUTraceUtil.createdAndAddFor(schedQtyPicked);
		}

		final List<I_M_HU_Trx_Line> huTrxLines = Services.get(IHUTrxDAO.class).retrieveReferencingTrxLinesForHU(hu);
		for(final I_M_HU_Trx_Line huTrxLine:huTrxLines)
		{
			HUTraceUtil.createdAndAddFor(huTrxLine.getM_HU_Trx_Hdr(), Stream.of(huTrxLine));
		}
		
		return MSG_OK;
	}

}
