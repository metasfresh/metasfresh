package de.metas.ui.web.picking;

import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewInvalidationAdvisor;
import de.metas.ui.web.view.SqlViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
class PickingTerminalViewInvalidationAdvisor implements IViewInvalidationAdvisor
{
	@Autowired
	private PickingCandidateRepository pickingCandidateRepository;

	@Override
	public WindowId getWindowId()
	{
		return PickingConstants.WINDOWID_PackageableView;
	}

	@Override
	public Set<DocumentId> findAffectedRowIds(final Set<TableRecordReference> recordRefs, final IView view)
	{
		final Set<Integer> shipmentScheduleIds = extractShipmentScheduleIds(recordRefs);
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final SqlViewKeyColumnNamesMap keyColumnNamesMap = SqlViewKeyColumnNamesMap.ofIntKeyField(I_M_Packageable_V.COLUMNNAME_M_ShipmentSchedule_ID);
		return SqlViewRowIdsOrderedSelectionFactory.retrieveRowIdsForLineIds(keyColumnNamesMap, view.getViewId(), shipmentScheduleIds);
	}

	private Set<Integer> extractShipmentScheduleIds(final Set<TableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return ImmutableSet.of();
		}

		final Set<Integer> shipmentScheduleIds = new HashSet<>();
		final Set<Integer> pickingCandidateIds = new HashSet<>();
		for (TableRecordReference recordRef : recordRefs)
		{
			final String tableName = recordRef.getTableName();
			if (I_M_ShipmentSchedule.Table_Name.equals(tableName))
			{
				shipmentScheduleIds.add(recordRef.getRecord_ID());
			}
			else if (I_M_Picking_Candidate.Table_Name.equals(tableName))
			{
				pickingCandidateIds.add(recordRef.getRecord_ID());
			}
		}

		if (!pickingCandidateIds.isEmpty())
		{
			shipmentScheduleIds.addAll(pickingCandidateRepository.retrieveShipmentScheduleIdsForPickingCandidateIds(pickingCandidateIds));
		}

		return shipmentScheduleIds;
	}
}
