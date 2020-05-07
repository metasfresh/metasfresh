package org.adempiere.ad.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.service.IErrorManager;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.X_AD_Issue;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.util.Util;

public class ErrorManager implements IErrorManager
{
	@Override
	public I_AD_Issue createIssue(final String name, final Throwable t)
	{
		final I_AD_Issue[] issue = new I_AD_Issue[1];

		Services.get(ITrxManager.class).run(new TrxRunnableAdapter()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				issue[0] = createIssue0(name, t, localTrxName);
			}
		});

		return issue[0];
	}
	private I_AD_Issue createIssue0(final String name, final Throwable t, final String trxName)
	{
		Check.assumeNotNull(t, "'t' not null");

		final I_AD_Issue issue = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Issue.class, trxName);

		String summary = t.getLocalizedMessage();
		if (Check.isEmpty(summary, true))
		{
			summary = t.toString();
		}
		issue.setIssueSummary(summary);

		if (Check.isEmpty(name, true))
		{
			issue.setName("Error");
		}
		else
		{
			issue.setName(name);
		}

		issue.setIssueSource(X_AD_Issue.ISSUESOURCE_Process);

		final StackTraceElement[] tes = t.getStackTrace();
		if (tes != null && tes.length > 0)
		{
			issue.setSourceClassName(tes[0].getClassName());
			issue.setSourceMethodName(tes[0].getMethodName());
			issue.setLineNo(tes[0].getLineNumber());
		}

		// StackTrace
		final String stackTrace = Util.dumpStackTraceToString(t);
		issue.setStackTrace(stackTrace);

		InterfaceWrapperHelper.save(issue);

		IssueReportableExceptions.markReportedIfPossible(t, issue.getAD_Issue_ID());

		return issue;
	}
}
