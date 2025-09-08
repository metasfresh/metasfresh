/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.adempiere.ad.process;

import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.GenericPO;

import java.util.Iterator;

public class ProcessDocumentsBySelection extends AbstractProcessDocumentsTemplate implements IProcessPrecondition
{
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public static final String PARAM_DocStatus = "DocStatus";
	@Param(parameterName = PARAM_DocStatus)
	private DocStatus p_DocStatus;

	public static final String PARAM_DocAction = "DocAction";
	@Param(parameterName = PARAM_DocAction, mandatory = true)
	private String p_DocAction;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@NonNull
	protected Iterator<GenericPO> retrieveDocumentsToProcess()
	{
		final String tableName = Check.assumeNotNull(getTableName(), "The process must be associated with a table!");

		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(tableName)
				.addOnlyActiveRecordsFilter()
				.filter(getProcessInfo().getQueryFilterOrElseFalse());

		if (p_DocStatus != null)
		{
			queryBuilder.addEqualsFilter(PARAM_DocStatus, p_DocStatus);
		}

		return queryBuilder.create().iterate(GenericPO.class);
	}

	@Override
	protected String getDocAction() {return p_DocAction;}
}
