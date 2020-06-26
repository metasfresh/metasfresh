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
		// To be replaced with table id of the selected row's table and row id
		// Also, if we are gonna support multiple rows cloning we could put the following 2 lines in a foreach (rowId : rowIds) statement
		final TableRecordReference tableRecordReference = TableRecordReference.of(getTable_ID(), getRecord_ID());
		documentCollectionService.duplicateDocumentInTrxGeneric(tableRecordReference, getProcessInfo().getAdWindowId());
		return null;
	}

	@Override
	public void flush()
	{

	}

	@Override
	public boolean isAllowThreadInherited()
	{
		return false;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.getAdTabId() == null)
		{
			return ProcessPreconditionsResolution.reject();
		}
		if (!CopyRecordFactory.isEnabledForTableName(getTableName())) // To be replaced with the table name of the sub tab selected row's table
		{
			return ProcessPreconditionsResolution.reject();
		}
		return ProcessPreconditionsResolution.accept();
	}
}
