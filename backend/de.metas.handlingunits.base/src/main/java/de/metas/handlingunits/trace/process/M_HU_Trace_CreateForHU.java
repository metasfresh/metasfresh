package de.metas.handlingunits.trace.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MovementLine;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_HU> selectedHUsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final Iterator<I_M_HU> hus = queryBL.createQueryBuilder(I_M_HU.class)
				.filter(selectedHUsFilter)
				.create()
				.iterate(I_M_HU.class);

		for (final I_M_HU hu : IteratorUtils.asIterable(hus))
		{
			writeTraceForHu(hu);
		}

		return MSG_OK;
	}

	private void writeTraceForHu(final I_M_HU hu)
	{
		addLog("Writing trace records hu={}", hu);

		final HUTraceEventsService huTraceEventsCreateAndAdd = Adempiere.getBean(HUTraceEventsService.class);

		// write the HU_Assigment related trace
		{

			final List<I_M_InOutLine> iols = retrieveAnyModelForHU(hu, I_M_InOutLine.class);
			for (final I_M_InOutLine iol : iols)
			{
				addLog("Checking for M_InOutLine={}", iol);
				huTraceEventsCreateAndAdd.createAndAddFor(iol.getM_InOut(), ImmutableList.of(iol));
			}

			final List<I_M_MovementLine> movementLines = retrieveAnyModelForHU(hu, I_M_MovementLine.class);
			for (final I_M_MovementLine movementLine : movementLines)
			{
				addLog("Checking for M_MovementLine={}", movementLine);
				huTraceEventsCreateAndAdd.createAndAddFor(movementLine.getM_Movement(), ImmutableList.of(movementLine));
			}

			final List<I_PP_Cost_Collector> costCollectors = retrieveAnyModelForHU(hu, I_PP_Cost_Collector.class);
			for (final I_PP_Cost_Collector costCollector : costCollectors)
			{
				addLog("Checking for PP_Cost_Collector={}", costCollector);
				huTraceEventsCreateAndAdd.createAndAddFor(costCollector);
			}
		}

		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		final List<I_M_ShipmentSchedule_QtyPicked> schedsQtyPicked = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(hu);
		for (final I_M_ShipmentSchedule_QtyPicked schedQtyPicked : schedsQtyPicked)
		{
			addLog("Checking for M_ShipmentSchedule_QtyPicked={}", schedQtyPicked);
			huTraceEventsCreateAndAdd.createAndAddFor(schedQtyPicked);
		}

		final List<I_M_HU_Trx_Line> huTrxLines = Services.get(IHUTrxDAO.class).retrieveReferencingTrxLinesForHU(hu);
		for (final I_M_HU_Trx_Line huTrxLine : huTrxLines)
		{
			addLog("Checking for M_HU_Trx_Line={}", huTrxLine);

			final Map<Boolean, List<HUTraceEvent>> createdEvents = huTraceEventsCreateAndAdd.createAndAddFor(huTrxLine.getM_HU_Trx_Hdr(), ImmutableList.of(huTrxLine));
			final List<HUTraceEvent> newlyInsertedEvents = createdEvents.get(true);
			for (final HUTraceEvent newlyInsertedEvent : newlyInsertedEvents)
			{
				if (newlyInsertedEvent.getVhuSourceId() != null)
				{
					final I_M_HU sourceHU = handlingUnitsDAO.getById(newlyInsertedEvent.getVhuSourceId());

					addLog("Recursing for for source M_HU={}", sourceHU);
					writeTraceForHu(sourceHU);
				}
			}
		}
	}

	private <T> List<T> retrieveAnyModelForHU(@NonNull final I_M_HU hu, @NonNull final Class<T> clazz)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_HU_Assignment> filter = queryBL.createCompositeQueryFilter(I_M_HU_Assignment.class)
				.setJoinOr()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_VHU_ID, hu.getM_HU_ID());

		final List<T> result = queryBL.createQueryBuilder(I_M_HU_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(clazz))
				.filter(filter)
				.create()
				.stream()
				.map(huAssignment -> TableRecordReference.ofReferenced(huAssignment))
				.sorted(Comparator.comparing(TableRecordReference::getRecord_ID))
				.distinct()
				.map(ref -> ref.getModel(this, clazz))
				.collect(Collectors.toList());

		return result;
	}
}
