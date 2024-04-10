package de.metas.error.impl;

import de.metas.common.util.EmptyUtil;
import de.metas.error.AdIssueFactory;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.InsertRemoteIssueRequest;
import de.metas.error.IssueCategory;
import de.metas.error.IssueCountersByCategory;
import de.metas.error.IssueCreateRequest;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessMDC;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.IntFunction;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ErrorManager implements IErrorManager
{
	private static final Logger logger = LogManager.getLogger(ErrorManager.class);

	static
	{
		LogManager.skipIssueReportingForLoggerName(logger);
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
	@NonNull
	public AdIssueId insertRemoteIssue(@NonNull final InsertRemoteIssueRequest request)
	{
		return trxManager.callInNewTrx(() -> insertRemoteIssueInTrx(request));
	}

	@NonNull
	private AdIssueId insertRemoteIssueInTrx(@NonNull final InsertRemoteIssueRequest request)
	{
		final I_AD_Issue issue = AdIssueFactory.prepareNewIssueRecord(Env.getCtx());
		issue.setIssueCategory(IssueCategory.ofNullableCodeOrOther(request.getIssueCategory()).getCode());
		issue.setIssueSummary(request.getIssueSummary());
		issue.setSourceClassName(request.getSourceClassName());
		issue.setSourceMethodName(request.getSourceMethodName());
		issue.setStackTrace(request.getStacktrace());
		issue.setAD_PInstance_ID(PInstanceId.toRepoId(request.getPInstance_ID()));
		issue.setAD_Org_ID(request.getOrgId().getRepoId());
		issue.setFrontendURL(StringUtils.trimBlankToNull(request.getFrontendUrl()));
		saveRecord(issue);
		return AdIssueId.ofRepoId(issue.getAD_Issue_ID());
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
			final I_AD_Issue issue = AdIssueFactory.prepareNewIssueRecord(Env.getCtx());

			final IssueCategory issueCategory = extractIssueCategory(throwable);
			issue.setIssueCategory(issueCategory.getCode());

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
					if (s.contains("adempiere"))
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
			else if (EmptyUtil.isNotBlank(request.getStackTrace()))
			{
				issue.setStackTrace(request.getStackTrace());
			}

			//
			// Table/Record
			final TableRecordReference recordRef = extractRecordRef(throwable);
			if (recordRef != null)
			{
				issue.setAD_Table_ID(recordRef.getAD_Table_ID());
				issue.setRecord_ID(recordRef.getRecord_ID());
			}

			//
			// Process/Instance
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

	private IssueCategory extractIssueCategory(@Nullable final Throwable t)
	{
		return t instanceof AdempiereException
				? ((AdempiereException)t).getIssueCategory()
				: IssueCategory.OTHER;
	}

	private static TableRecordReference extractRecordRef(@Nullable final Throwable t)
	{
		return t instanceof AdempiereException
				? ((AdempiereException)t).getRecord()
				: null;
	}

	private static AdProcessId extractAdProcessIdOrNull(@NonNull final Throwable t)
	{
		return extractIdOrNull(t, ProcessMDC.NAME_AD_Process_ID, AdProcessId::ofRepoIdOrNull);
	}

	private static PInstanceId extractPInstanceIdOrNull(@NonNull final Throwable t)
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

	@Override
	public void markIssueAcknowledged(@NonNull AdIssueId adIssueId)
	{
		try
		{
			final I_AD_Issue adIssueRecord = getById(adIssueId).orElse(null);
			if (adIssueRecord == null)
			{
				return;
			}

			adIssueRecord.setProcessed(true);
			saveRecord(adIssueRecord);
		}
		catch (Exception ex)
		{
			logger.warn("Failed marking {} as deprecated. Ignored.", adIssueId, ex);
		}
	}

	private Optional<I_AD_Issue> getById(@NonNull final AdIssueId adIssueId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Issue_ID, adIssueId)
				.create()
				.firstOnlyOptional(I_AD_Issue.class);
	}

	@Override
	public IssueCountersByCategory getIssueCountersByCategory(
			@NonNull final TableRecordReference recordRef,
			final boolean onlyNotAcknowledged)
	{
		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT "
				+ " " + I_AD_Issue.COLUMNNAME_IssueCategory
				+ ", count(1) as count"
				+ "\n FROM " + I_AD_Issue.Table_Name
				+ "\n WHERE "
				+ " " + I_AD_Issue.COLUMNNAME_IsActive + "=?"
				+ " AND " + I_AD_Issue.COLUMNNAME_AD_Table_ID + "=?"
				+ " AND " + I_AD_Issue.COLUMNNAME_Record_ID + "=?");
		sqlParams.add(true); // IsActive
		sqlParams.add(recordRef.getAD_Table_ID());
		sqlParams.add(recordRef.getRecord_ID());

		if (onlyNotAcknowledged)
		{
			sql.append(" AND ").append(I_AD_Issue.COLUMNNAME_Processed).append("=?");
			sqlParams.add(false);
		}

		sql.append("\n GROUP BY ").append(I_AD_Issue.COLUMNNAME_IssueCategory);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final HashMap<IssueCategory, Integer> counters = new HashMap<>();
			while (rs.next())
			{
				final IssueCategory issueCategory = IssueCategory.ofNullableCodeOrOther(rs.getString(I_AD_Issue.COLUMNNAME_IssueCategory));
				final int count = rs.getInt("count");

				counters.compute(issueCategory, (k, previousCounter) -> {
					return previousCounter != null
							? previousCounter + count
							: count;
				});
			}

			return IssueCountersByCategory.of(counters);
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
