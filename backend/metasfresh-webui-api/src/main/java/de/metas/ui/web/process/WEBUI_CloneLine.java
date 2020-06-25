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
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.NonNull;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class WEBUI_CloneLine extends ViewBasedProcessTemplate implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (getAdTabId() == null)
		{
			return ProcessPreconditionsResolution.reject();
		}
		if (!CopyRecordFactory.isEnabledForTableName(getTableName()))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	protected String doIt() throws Exception
	{
		final TableRecordReference tableRecordReference = getView().getTableRecordReferenceOrNull(getSelectedRowIds().getSingleDocumentId());
		SpringContextHolder.instance.getBean(DocumentCollection.class).duplicateDocumentInTrxGeneric(tableRecordReference,
				getSelectedRowIds().getSingleDocumentId(), getWindowId());
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


}
