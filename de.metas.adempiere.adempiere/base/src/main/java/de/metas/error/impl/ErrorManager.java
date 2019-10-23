package de.metas.error.impl;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.X_AD_Issue;
import org.compiere.util.Util;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class ErrorManager implements IErrorManager
{
	@Override
	public AdIssueId createIssue(final Throwable t)
	{
		final AdIssueId issueId = IssueReportableExceptions.getAdIssueIdOrNull(t);
		if (issueId != null)
		{
			return issueId;
		}

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.callInNewTrx(() -> createIssue0(t));
	}

	private AdIssueId createIssue0(@NonNull final Throwable t)
	{
		final I_AD_Issue issue = InterfaceWrapperHelper.newInstance(I_AD_Issue.class);

		String summary = t.getLocalizedMessage();
		if (Check.isEmpty(summary, true))
		{
			summary = t.toString();
		}
		issue.setIssueSummary(summary);

		issue.setName("Error");
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
		final AdIssueId adIssueId = AdIssueId.ofRepoId(issue.getAD_Issue_ID());

		IssueReportableExceptions.markReportedIfPossible(t, adIssueId);

		return adIssueId;
	}
}
