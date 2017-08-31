package de.metas.ui.web.handlingunits.process;

import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrx;

import com.google.common.collect.Iterators;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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
public class WEBUI_M_HU_MoveToDirectWarehouse extends WEBUI_M_HU_MoveToDirectWarehouse_Mass implements IProcessPrecondition
{
	public WEBUI_M_HU_MoveToDirectWarehouse()
	{
		super();
		setFailOnFirstError(true);
	}
	
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
		if(!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		
		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		final HUEditorRow huRow = getView().getById(rowId);
		if(!huRow.isTopLevel())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a top level HU");
		}
		
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected Iterator<I_M_HU> retrieveHUs()
	{
		final I_M_HU hu = getProcessInfo().getRecord(I_M_HU.class, ITrx.TRXNAME_None);
		return Iterators.singletonIterator(hu);
	}
}
