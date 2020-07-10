/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class WEBUI_CloneLine extends JavaProcess implements IProcessPrecondition
{
	DocumentCollection documentCollectionService = SpringContextHolder.instance.getBean(DocumentCollection.class);

	@Nullable
	@Override
	protected String doIt() throws Exception
	{
		final TableRecordReference tableRecordReference = CollectionUtils.singleElementOrNull(getProcessInfo().getSelectedIncludedRecords());
		if (tableRecordReference == null)
		{
			addLog("The precondition of one selected included record does not hold anymore; -> doing nothing");
			return MSG_OK;
		}
		final TableRecordReference parentRef = TableRecordReference.of(getTable_ID(), getRecord_ID());
		documentCollectionService.duplicateTabRowInTrx(parentRef, tableRecordReference, getProcessInfo().getAdWindowId());
		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.getAdTabId() == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No row(s) from a tab selected.");
		}

		//making sure only one row is selected at a time
		if (context.getSelectedIncludedRecords().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("More or less than one row selected.");
		}

		final TableRecordReference ref = CollectionUtils.singleElement(context.getSelectedIncludedRecords());
		if (!CopyRecordFactory.isEnabledForTableName(ref.getTableName())) // To be replaced with the table name of the sub tab selected row's table
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("CopyRecordFactory not enabled for the table the row belongs to.");
		}

		if (ref.getTableName().equals(context.getTableName()))
		{
			// we have e.g. in the C_BPartner-window two subtabs ("Customer" and "Vendor") that show a different view on the same C_BPartner record. 
			// There, cloning the subtab-line makes no sense
			return ProcessPreconditionsResolution.rejectWithInternalReason("The main-window has the same Record as the sub-tab, there can only be one line.");
		}
		return ProcessPreconditionsResolution.accept();
	}
}
