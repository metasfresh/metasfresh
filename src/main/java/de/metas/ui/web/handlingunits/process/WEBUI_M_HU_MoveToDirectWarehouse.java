package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
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
	@Autowired
	private DocumentCollection documentsCollection;
	
	private static final HUEditorRowFilter rowsFilter = HUEditorRowFilter.builder().select(Select.ONLY_TOPLEVEL).onlyActiveHUs().build();

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final Set<Integer> huIds = getSelectedHUIds(rowsFilter);
		if (huIds.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final List<I_M_HU> selectedTopLevelHUs = getSelectedHUs(rowsFilter);
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
