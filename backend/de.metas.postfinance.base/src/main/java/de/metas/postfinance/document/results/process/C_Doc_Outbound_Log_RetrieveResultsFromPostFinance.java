/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.document.results.process;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.organization.OrgId;
import de.metas.postfinance.document.results.GetResultsFromPostFinanceService;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;

import javax.annotation.Nullable;

public class C_Doc_Outbound_Log_RetrieveResultsFromPostFinance extends JavaProcess
{
	private final GetResultsFromPostFinanceService getResultsFromPostFinanceService = SpringContextHolder.instance.getBean(GetResultsFromPostFinanceService.class);
	private static final String PARAM_AD_Org_ID = I_C_Doc_Outbound_Log.COLUMNNAME_AD_Org_ID;

	@Nullable
	private OrgId p_AD_Org_ID;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())

		{
			if (para.getParameter() == null)
			{
				// skip if no parameter value
				continue;
			}

			final String name = para.getParameterName();
			if (PARAM_AD_Org_ID.equals(name))
			{
				p_AD_Org_ID = para.getParameterAsRepoId(OrgId::ofRepoIdOrNull);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final TableRecordReference adPInstanceReference = TableRecordReference.of(I_AD_PInstance.Table_Name, getPinstanceId());

		getResultsFromPostFinanceService.handleResultsFromPostFinance(p_AD_Org_ID, adPInstanceReference);

		return MSG_OK;
	}
}
