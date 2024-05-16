/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.util.web.audit.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_API_Request_Audit;

public class API_Audit_Request_Toggle_IsErrorAcknowledge extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final String PARAM_IS_ERROR_ACKNOWLEDGE = "IsErrorAcknowledged";
	@Param(parameterName = PARAM_IS_ERROR_ACKNOWLEDGE)
	private boolean IsErrorAcknowledged;

	@Override
	protected String doIt() throws Exception
	{
		final ICompositeQueryUpdater<I_API_Request_Audit> queryUpdater = queryBL.createCompositeQueryUpdater(I_API_Request_Audit.class)
				.addSetColumnValue(I_API_Request_Audit.COLUMNNAME_IsErrorAcknowledged, IsErrorAcknowledged);

		retrieveSelectedRecordsQueryBuilder(I_API_Request_Audit.class)
				.create()
				.update(queryUpdater);

		return MSG_OK;
	}
}
