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

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.GenericPO;

import java.util.Iterator;

public class ProcessDocumentsBySelection extends ProcessDocuments implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
		final String tableName = getTableName();
		Check.assumeNotNull(tableName, "The process must be associated with a table!");

		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(tableName)
				.filter(getProcessInfo().getQueryFilterOrElseFalse());

		final String docStatus = getP_DocStatus();
		if (docStatus != null)
		{
			queryBuilder.addEqualsFilter(PARAM_DocStatus, docStatus);
		}

		return queryBuilder
				.create()
				.iterate(GenericPO.class);
	}
}
