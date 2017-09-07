package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.DocumentCollection;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * HU Editor: Move selected HU to direct warehouse (aka Materialentnahme)
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_M_HU_MoveToDirectWarehouse extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Autowired
	private DocumentCollection documentsCollection;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean hasTopLevelHUsSelected = getView()
				.streamByIds(selectedRowIds)
				.filter(HUEditorRow::isTopLevel)
				.anyMatch(HUEditorRow::isTopLevel);

		if (!hasTopLevelHUsSelected)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a top level HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final List<I_M_HU> selectedTopLevelHUs = getSelectedHUs()
				.stream()
				.filter(handlingUnitsBL::isTopLevel)
				.collect(ImmutableList.toImmutableList());
		if (selectedTopLevelHUs.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		HUMoveToDirectWarehouseService.newInstance()
				.setDocumentsCollection(documentsCollection)
				.setHUView(getView())
				// .setMovementDate(movementDate) // now
				// .setDescription(description) // none
				.setFailOnFirstError(true)
				.setLoggable(this)
				.move(selectedTopLevelHUs.iterator());

		return MSG_OK;
	}
}
