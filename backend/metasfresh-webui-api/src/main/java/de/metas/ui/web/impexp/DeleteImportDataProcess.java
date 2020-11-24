package de.metas.ui.web.impexp;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import de.metas.impexp.processing.IImportProcess;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.impexp.processing.ImportDataDeleteMode;
import de.metas.impexp.processing.ImportDataDeleteRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class DeleteImportDataProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IImportProcessFactory importProcessFactory = Services.get(IImportProcessFactory.class);

	@Param(parameterName = "ImportDeleteMode", mandatory = true)
	@Getter
	private ImportDataDeleteMode deleteMode;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getView().size() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("view is empty");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final String importTableName = getTableName();

		final IImportProcess<Object> importProcess = importProcessFactory.newImportProcessForTableName(importTableName);
		importProcess.setCtx(getCtx());
		importProcess.setParameters(getParameterAsIParams());
		importProcess.setLoggable(this);

		final ImportDataDeleteMode deleteMode = getDeleteMode();

		final int deletedCount = importProcess.deleteImportRecords(ImportDataDeleteRequest.builder()
				.mode(deleteMode)
				.viewSqlWhereClause(getViewSqlWhereClause(DocumentIdsSelection.ALL)
						.map(SqlViewRowsWhereClause::toSqlString)
						.orElse(null))
				.selectionSqlWhereClause(
						ImportDataDeleteMode.ONLY_SELECTED.equals(deleteMode)
								? getSelectionSqlWhereClause().map(SqlViewRowsWhereClause::toSqlString).orElse(null)
								: null)
				.build());

		return "@Deleted@ " + deletedCount;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private Optional<SqlViewRowsWhereClause> getSelectionSqlWhereClause()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		if (rowIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		return getViewSqlWhereClause(rowIds);
	}

	private Optional<SqlViewRowsWhereClause> getViewSqlWhereClause(@NonNull final DocumentIdsSelection rowIds)
	{
		final IView view = getView();
		final String importTableName = getTableName();
		final SqlViewRowsWhereClause viewRowsWhereClause = view.getSqlWhereClause(rowIds, SqlOptions.usingTableName(importTableName));
		return Optional.ofNullable(viewRowsWhereClause);
	}
}
