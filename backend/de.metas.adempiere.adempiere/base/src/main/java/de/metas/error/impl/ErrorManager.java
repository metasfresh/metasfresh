package de.metas.error.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.function.IntFunction;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.compiere.model.I_AD_Issue;
import org.compiere.util.Util;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessMDC;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

public class ErrorManager implements IErrorManager
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Override
	public AdIssueId createIssue(@NonNull final Throwable t)
	{
		final AdIssueId issueId = IssueReportableExceptions.getAdIssueIdOrNull(t);
		if (issueId != null)
		{
			return issueId;
		}

		return createIssue(IssueCreateRequest.builder()
				.throwable(t)
				.build());
	}

	@Override
	public AdIssueId createIssue(@NonNull final IssueCreateRequest request)
	{
		return trxManager.callInNewTrx(() -> createIssueInTrx(request));
	}

	private AdIssueId createIssueInTrx(@NonNull final IssueCreateRequest request)
	{
		final Throwable throwable = request.getThrowable();

		//
		// Create AD_Issue
		final AdIssueId adIssueId;
		{
			final I_AD_Issue issue = newInstance(I_AD_Issue.class);

			issue.setIssueSummary(buildIssueSummary(request));
			issue.setLoggerName(request.getLoggerName());

			// Source class/method name
			// might be overridden below
			issue.setSourceClassName(request.getSourceClassname());
			issue.setSourceMethodName(request.getSourceMethodName());

			//
			// ErrorTrace
			if (throwable != null)
			{
				final StringBuilder errorTrace = new StringBuilder();
				int count = 0;
				for (final StackTraceElement element : throwable.getStackTrace())
				{
					final String s = element.toString();
					if (s.indexOf("adempiere") != -1)
					{
						errorTrace.append(s).append("\n");
						if (count == 0)
						{
							issue.setSourceClassName(element.getClassName());
							issue.setSourceClassName(element.getMethodName());
							issue.setLineNo(element.getLineNumber());
						}
						count++;
					}
					if (count > 5 || errorTrace.length() > 2000)
					{
						break;
					}
				}
				issue.setErrorTrace(errorTrace.toString());
			}

			//
			// StackTrace
			if (throwable != null)
			{
				final String stackTrace = Util.dumpStackTraceToString(throwable);
				issue.setStackTrace(stackTrace);
			}

			//
			// Table/Record
			issue.setRecord_ID(1); // just to have something there because it's mandatory

			//
			// References
			if (throwable != null)
			{
				final AdProcessId adProcessId = extractAdProcessIdOrNull(throwable);
				issue.setAD_Process_ID(AdProcessId.toRepoId(adProcessId));

				final PInstanceId pinstanceId = extractPInstanceIdOrNull(throwable);
				issue.setAD_PInstance_ID(PInstanceId.toRepoId(pinstanceId));
			}

			//
			// Save
			saveRecord(issue);
			adIssueId = AdIssueId.ofRepoId(issue.getAD_Issue_ID());
		}

		if (throwable != null)
		{
			IssueReportableExceptions.markReportedIfPossible(throwable, adIssueId);
		}

		return adIssueId;
	}

	private static String buildIssueSummary(final IssueCreateRequest request)
	{
		String summary = request.getSummary();

		final Throwable throwable = request.getThrowable();
		if (throwable != null)
		{
			final String throwableMessage = AdempiereException.extractMessage(throwable);

			summary = Check.isNotBlank(summary)
					? throwableMessage + " " + summary
					: throwableMessage;
		}

		if (Check.isBlank(summary))
		{
			summary = "??";
		}

		return summary;
	}

	private static AdProcessId extractAdProcessIdOrNull(final Throwable t)
	{
		return extractIdOrNull(t, ProcessMDC.NAME_AD_Process_ID, AdProcessId::ofRepoIdOrNull);
	}

	private static PInstanceId extractPInstanceIdOrNull(final Throwable t)
	{
		return extractIdOrNull(t, ProcessMDC.NAME_AD_PInstance_ID, PInstanceId::ofRepoIdOrNull);
	}

	private static <T extends RepoIdAware> T extractIdOrNull(
			@NonNull final Throwable t,
			@NonNull final String name,
			@NonNull final IntFunction<T> mapper)
	{
		if (t instanceof AdempiereException)
		{
			final AdempiereException metasfreshException = (AdempiereException)t;

			final String mdcValueStr = metasfreshException.getMDC(name);
			if (mdcValueStr != null && !mdcValueStr.isEmpty())
			{
				final Integer valueInt = NumberUtils.asIntegerOrNull(mdcValueStr);
				return valueInt != null
						? mapper.apply(valueInt)
						: null;
			}

			final Object paramValueObj = metasfreshException.getParameter(name);
			if (paramValueObj != null)
			{
				final Integer valueInt = NumberUtils.asIntegerOrNull(paramValueObj);
				return valueInt != null
						? mapper.apply(valueInt)
						: null;
			}
		}

		// Fallback
		return null;
	}
}
