package de.metas.ui.web.material.cockpit.process;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.material.cockpit.model.I_MD_Cockpit_DocumentDetail;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.material.cockpit.MaterialCockpitConstants;
import de.metas.ui.web.material.cockpit.MaterialCockpitView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

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

public class MD_Cockpit_DocumentDetail_Display
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No MaterialCockpitrows are selected");
		}

		final ImmutableSet<Integer> cockpitRowIds = retrieveCockpitRecordIds();
		if (cockpitRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The selected rows are just dummys with all-zero");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<Integer> cockpitRowIds = retrieveCockpitRecordIds();

		final List<TableRecordReference> cockpitDetailRecords = retrieveCockpitDetailRecordReferences(cockpitRowIds);

		getResult().setRecordsToOpen(
				cockpitDetailRecords,
				MaterialCockpitConstants.WINDOWID_MaterialCockpit_Detail_String);
		return MSG_OK;
	}

	private ImmutableSet<Integer> retrieveCockpitRecordIds()
	{
		final MaterialCockpitView materialCockpitView = (MaterialCockpitView)getView();

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		final ImmutableSet<Integer> cockpitRowIds = selectedRowIds.stream()
				.map(rowId -> {
					return materialCockpitView.getById(rowId);
				})
				.flatMap(row -> {
					return row.getAllIncludedCockpitRecordIds().stream();
				})
				.collect(ImmutableSet.toImmutableSet());

		return cockpitRowIds;
	}

	private ImmutableList<TableRecordReference> retrieveCockpitDetailRecordReferences(
			@NonNull final ImmutableSet<Integer> cockpitRowIds)
	{
		final ImmutableList<TableRecordReference> cockpitDetailRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Cockpit_DocumentDetail.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_MD_Cockpit_DocumentDetail.COLUMN_MD_Cockpit_ID, cockpitRowIds)
				.create()
				.listIds()
				.stream()
				.map(cockpitDetailRecordId -> TableRecordReference.of(
						I_MD_Cockpit_DocumentDetail.Table_Name,
						cockpitDetailRecordId))
				.collect(ImmutableList.toImmutableList());

		return cockpitDetailRecords;
	}

}
