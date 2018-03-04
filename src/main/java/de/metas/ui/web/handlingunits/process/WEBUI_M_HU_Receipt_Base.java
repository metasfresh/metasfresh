package de.metas.ui.web.handlingunits.process;

import org.adempiere.util.lang.MutableInt;

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

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/**
 * Common base class to dedupliate code.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class WEBUI_M_HU_Receipt_Base extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isViewClass(HUEditorView.class))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The current view is not an HUEditorView");
		}

		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final MutableInt checkedDocumentsCount = new MutableInt(0);
		final ProcessPreconditionsResolution firstRejection = getView(HUEditorView.class)
				.streamByIds(selectedRowIds)
				.filter(document -> document.isPureHU())
				//
				.peek(document -> checkedDocumentsCount.incrementAndGet()) // count checked documents
				.map(document -> rejectResolutionOrNull(document)) // create reject resolution if any
				.filter(resolution -> resolution != null) // filter out those which are not errors
				.findFirst()
				.orElse(null);
		if (firstRejection != null)
		{
			// found a record which is not eligible => don't run the process
			return firstRejection;
		}
		if (checkedDocumentsCount.getValue() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible rows");
		}

		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * Check the individual given row, to find out if this process can be applied to it or not.
	 * 
	 * @param document
	 * @return
	 */
	abstract ProcessPreconditionsResolution rejectResolutionOrNull(HUEditorRow document);

}
