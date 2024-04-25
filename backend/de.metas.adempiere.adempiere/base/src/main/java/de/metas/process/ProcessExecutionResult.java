package de.metas.process;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.report.ReportResultData;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.async.Debouncer;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.print.MPrintFormat;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ProcessExecutionResult
{
	private static final String DEBOUNCER_BUFFER_MAX_SIZE_SYSCONFIG_NAME = "de.metas.process.pinstaceLogPersister.debouncer.bufferMaxSize";
	private static final String DEBOUNCER_DELAY_IN_MILLIS_SYSCONFIG_NAME = "de.metas.process.pinstaceLogPersister.debouncer.delayInMillis";

	public static ProcessExecutionResult newInstanceForADPInstanceId(final PInstanceId pinstanceId)
	{
		return new ProcessExecutionResult(pinstanceId);
	}

	/**
	 * Display process logs to user policy
	 */
	public enum ShowProcessLogs
	{
		/**
		 * Always display them
		 */
		Always,
		/**
		 * Display them only if the process failed
		 */
		OnError,
		/**
		 * Never display them
		 */
		Never
	}

	private static final Logger logger = LogManager.getLogger(ProcessExecutionResult.class);

	private PInstanceId pinstanceId;

	/**
	 * Summary of Execution
	 */
	private String summary = "";
	/**
	 * Execution had an error
	 */
	private boolean error = false;
	private transient boolean errorWasReportedToUser = false;

	/**
	 * Process timed out
	 */
	private boolean timeout = false;

	private ShowProcessLogs showProcessLogsPolicy = ShowProcessLogs.Always;
	@JsonIgnore
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@JsonIgnore
	private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);

	private final transient Debouncer<ProcessInfoLog> logsDebouncer = Debouncer.<ProcessInfoLog>builder()
			.name(ProcessExecutionResult.class.getName() + ".Debouncer")
			.bufferMaxSize(sysConfigBL.getIntValue(DEBOUNCER_BUFFER_MAX_SIZE_SYSCONFIG_NAME, 100))
			.delayInMillis(sysConfigBL.getIntValue(DEBOUNCER_DELAY_IN_MILLIS_SYSCONFIG_NAME, 1000))
			.consumer(this::syncCollectedLogsToDB)
			.build();

	//
	// Reporting
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private transient MPrintFormat printFormat;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	private ReportResultData reportData;

	/**
	 * If the process fails with an Throwable, the Throwable is caught and stored here
	 */
	// 03152: motivation to add this is that now in ait we can assert that a certain exception was thrown.
	@Nullable
	private transient Throwable throwable = null;

	private boolean refreshAllAfterExecution = false;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private TableRecordReference recordToRefreshAfterExecution = null;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private TableRecordReference recordToSelectAfterExecution = null;

	/**
	 * Records to be opened (UI) after this process was successfully executed
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	private RecordsToOpen recordsToOpen = null;

	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private WebuiViewToOpen webuiViewToOpen = null;

	@Getter
	@Setter
	private boolean closeWebuiModalView = false;

	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DisplayQRCode displayQRCode;

	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private CalendarToOpen calendarToOpen;

	/**
	 * Webui's viewId on which this process was executed.
	 */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Setter
	@Getter
	@Nullable
	private String webuiViewId = null;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	@Nullable
	private String stringResult = null;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	@Nullable
	private String stringResultContentType = null;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	@Setter
	@Nullable
	private WebuiNewRecord webuiNewRecord;

	private ProcessExecutionResult(final PInstanceId pinstanceId)
	{
		this.pinstanceId = pinstanceId;
	}

	// note: my local ProcessExecutionResultTest failed without this constructor
	@JsonCreator
	public ProcessExecutionResult(
			@JsonProperty("pinstanceId") final PInstanceId pinstanceId,
			@JsonProperty("summary") final String summary,
			@JsonProperty("error") final boolean error,
			// @JsonProperty("errorWasReportedToUser") final boolean errorWasReportedToUser, // transient
			@JsonProperty("timeout") final boolean timeout,
			// @JsonProperty("logs") final List<ProcessInfoLog> logs, // transient
			@JsonProperty("showProcessLogsPolicy") final ShowProcessLogs showProcessLogsPolicy,
			// @JsonProperty("printFormat") final MPrintFormat printFormat, // transient
			@JsonProperty("reportData") @Nullable final ReportResultData reportData,
			// @JsonProperty("throwable") final Throwable throwable, // transient
			@JsonProperty("refreshAllAfterExecution") final boolean refreshAllAfterExecution,
			@JsonProperty("recordToRefreshAfterExecution") final TableRecordReference recordToRefreshAfterExecution,
			@JsonProperty("recordToSelectAfterExecution") final TableRecordReference recordToSelectAfterExecution,
			@JsonProperty("recordsToOpen") @Nullable final RecordsToOpen recordsToOpen,
			@JsonProperty("webuiViewToOpen") final WebuiViewToOpen webuiViewToOpen,
			@JsonProperty("displayQRCode") final DisplayQRCode displayQRCode,
			@JsonProperty("webuiViewId") @Nullable final String webuiViewId,
			@JsonProperty("stringResult") @Nullable final String stringResult,
			@JsonProperty("stringResultContentType") @Nullable final String stringResultContentType)
	{
		this.pinstanceId = pinstanceId;
		this.summary = summary;
		this.error = error;
		this.timeout = timeout;
		this.showProcessLogsPolicy = showProcessLogsPolicy;
		this.reportData = reportData;
		this.refreshAllAfterExecution = refreshAllAfterExecution;
		this.recordToRefreshAfterExecution = recordToRefreshAfterExecution;
		this.recordToSelectAfterExecution = recordToSelectAfterExecution;
		this.recordsToOpen = recordsToOpen;
		this.webuiViewToOpen = webuiViewToOpen;
		this.displayQRCode = displayQRCode;
		this.webuiViewId = webuiViewId;
		this.stringResult = stringResult;
		this.stringResultContentType = stringResultContentType;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("summary", summary)
				.add("error", error)
				.add("printFormat", printFormat)
				.add("logs.size", logsDebouncer.getCurrentBufferSize())
				.add("AD_PInstance_ID", pinstanceId)
				.add("recordToSelectAfterExecution", recordToSelectAfterExecution)
				.add("recordsToOpen", recordsToOpen)
				.add("viewToOpen", webuiViewToOpen)
				.toString();
	}

	/* package */void setPInstanceId(final PInstanceId pinstanceId)
	{
		this.pinstanceId = pinstanceId;
	}

	public PInstanceId getPinstanceId()
	{
		return pinstanceId;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(final String summary)
	{
		this.summary = summary;
	}

	public void addSummary(final String additionalSummary)
	{
		if (summary == null)
		{
			summary = additionalSummary;
		}
		else
		{
			summary += additionalSummary;
		}

	}

	public void markAsSuccess(final String summary)
	{
		this.summary = summary;
		throwable = null;
		error = false;
	}

	public void markAsError(final Throwable throwable)
	{
		Check.assumeNotNull(throwable, "Parameter throwable is not null");
		final String summary = throwable.getLocalizedMessage();
		markAsError(summary, throwable);
	}

	public void markAsError(final String errorMsg)
	{
		final Throwable throwable = null;
		markAsError(errorMsg, throwable);
	}

	public void markAsError(final String summary, @Nullable final Throwable throwable)
	{
		this.summary = summary;
		this.throwable = throwable;
		error = true;
	}

	/**
	 * @return true if the process execution failed
	 */
	public boolean isError()
	{
		return error;
	}

	public void setThrowableIfNotSet(final Throwable throwable)
	{
		// Don't set it if it was already set
		if (this.throwable != null)
		{
			return;
		}

		this.throwable = throwable;
	}

	/**
	 * If the process has failed with a Throwable, that Throwable can be retrieved using this getter.
	 *
	 * @return throwable
	 * Task 03152
	 */
	@Nullable
	public Throwable getThrowable()
	{
		return throwable;
	}

	public void setErrorWasReportedToUser()
	{
		errorWasReportedToUser = true;
	}

	public boolean isErrorWasReportedToUser()
	{
		return errorWasReportedToUser;
	}

	public void setTimeout(final boolean timeout)
	{
		this.timeout = timeout;
	}

	public boolean isTimeout()
	{
		return timeout;
	}

	/**
	 * Sets if the process logs (if any) shall be displayed to user
	 */
	public final void setShowProcessLogs(final ShowProcessLogs showProcessLogsPolicy)
	{
		Check.assumeNotNull(showProcessLogsPolicy, "showProcessLogsPolicy not null");
		this.showProcessLogsPolicy = showProcessLogsPolicy;
	}

	public void setStringResult(@Nullable final String result, @NonNull final String contentType)
	{
		this.stringResult = result;
		this.stringResultContentType = contentType;
	}

	/**
	 * @return true if the process logs (if any) shall be displayed to user
	 */
	@JsonIgnore
	public final boolean isShowProcessLogs()
	{
		switch (showProcessLogsPolicy)
		{
			case Always:
				return true;
			case Never:
				return false;
			case OnError:
				return isError();
			default:
				logger.warn("Unknown ShowProcessLogsPolicy: {}. Considering {}", showProcessLogsPolicy, ShowProcessLogs.Always);
				return true;
		}
	}

	/**
	 * Sets if the whole window tab shall be refreshed after process execution (applies only when the process was started from a user window)
	 */
	public void setRefreshAllAfterExecution(final boolean refreshAllAfterExecution)
	{
		this.refreshAllAfterExecution = refreshAllAfterExecution;
	}

	/**
	 * @return if the whole window tab shall be refreshed after process execution (applies only when the process was started from a user window)
	 */
	public boolean isRefreshAllAfterExecution()
	{
		return refreshAllAfterExecution;
	}

	public void setRecordToRefreshAfterExecution(final TableRecordReference recordToRefreshAfterExecution)
	{
		this.recordToRefreshAfterExecution = recordToRefreshAfterExecution;
	}

	public TableRecordReference getRecordToRefreshAfterExecution()
	{
		return recordToRefreshAfterExecution;
	}

	/**
	 * @return the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 */
	public TableRecordReference getRecordToSelectAfterExecution()
	{
		return recordToSelectAfterExecution;
	}

	/**
	 * Sets the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 */
	public void setRecordToSelectAfterExecution(final TableRecordReference recordToSelectAfterExecution)
	{
		this.recordToSelectAfterExecution = recordToSelectAfterExecution;
	}

	public void setRecordsToOpen(final Collection<TableRecordReference> records, final int adWindowId)
	{
		setRecordsToOpen(records, String.valueOf(adWindowId));
	}

	public void setRecordsToOpen(final Collection<TableRecordReference> records, final String adWindowId)
	{
		if (records == null || records.isEmpty())
		{
			setRecordToOpen(null);
		}
		else
		{
			setRecordToOpen(RecordsToOpen.builder()
					.records(records)
					.adWindowId(adWindowId)
					.target(OpenTarget.GridView)
					.targetTab(RecordsToOpen.TargetTab.SAME_TAB_OVERLAY)
					.automaticallySetReferencingDocumentPaths(true)
					.build());
		}
	}

	public void setRecordsToOpen(@NonNull final String tableName, final Collection<Integer> recordIds, final String adWindowId)
	{
		if (recordIds == null || recordIds.isEmpty())
		{
			setRecordToOpen(null);
		}
		else
		{
			final Set<TableRecordReference> records = recordIds.stream()
					.map(recordId -> TableRecordReference.of(tableName, recordId))
					.collect(ImmutableSet.toImmutableSet());
			setRecordToOpen(RecordsToOpen.builder()
					.records(records)
					.adWindowId(adWindowId)
					.target(OpenTarget.GridView)
					.targetTab(RecordsToOpen.TargetTab.SAME_TAB_OVERLAY)
					.automaticallySetReferencingDocumentPaths(true)
					.build());
		}
	}

	public void setRecordsToOpen(final Collection<TableRecordReference> records)
	{
		if (records == null || records.isEmpty())
		{
			setRecordToOpen(null);
		}
		else
		{
			setRecordToOpen(RecordsToOpen.builder()
					.records(records)
					.adWindowId(null)
					.target(OpenTarget.GridView)
					.targetTab(RecordsToOpen.TargetTab.SAME_TAB_OVERLAY)
					.automaticallySetReferencingDocumentPaths(true)
					.build());
		}
	}

	public void setRecordToOpen(@Nullable final TableRecordReference record, final int adWindowId, @NonNull final OpenTarget target)
	{
		setRecordToOpen(record, String.valueOf(adWindowId), target);
	}

	public void setRecordToOpen(@Nullable final TableRecordReference record, @Nullable final AdWindowId adWindowId, @NonNull final OpenTarget target)
	{
		setRecordToOpen(record, adWindowId != null ? String.valueOf(adWindowId.getRepoId()) : null, target);
	}

	public void setRecordToOpen(@Nullable final TableRecordReference record, final int adWindowId, @NonNull final OpenTarget target, @Nullable final RecordsToOpen.TargetTab targetTab)
	{
		setRecordToOpen(record, String.valueOf(adWindowId), target, targetTab);
	}

	public void setRecordToOpen(@Nullable final TableRecordReference record, final @Nullable String adWindowId, @NonNull final OpenTarget target)
	{
		if (record == null)
		{
			setRecordToOpen(null);
		}
		else
		{
			setRecordToOpen(RecordsToOpen.builder()
					.record(record)
					.adWindowId(adWindowId)
					.target(target)
					.targetTab(RecordsToOpen.TargetTab.SAME_TAB)
					.automaticallySetReferencingDocumentPaths(true)
					.build());
		}
	}

	public void setRecordToOpen(@Nullable final TableRecordReference record, final @Nullable String adWindowId, @NonNull final OpenTarget target, @Nullable final RecordsToOpen.TargetTab targetTab)
	{
		if (record == null)
		{
			setRecordToOpen(null);
		}
		else
		{
			setRecordToOpen(RecordsToOpen.builder()
					.record(record)
					.adWindowId(adWindowId)
					.target(target)
					.targetTab(targetTab)
					.automaticallySetReferencingDocumentPaths(true)
					.build());
		}
	}

	public void setRecordToOpen(@Nullable final RecordsToOpen recordsToOpen)
	{
		this.recordsToOpen = recordsToOpen;
	}

	@Nullable
	public RecordsToOpen getRecordsToOpen()
	{
		return recordsToOpen;
	}

	public void setPrintFormat(final MPrintFormat printFormat)
	{
		this.printFormat = printFormat;
	}

	public MPrintFormat getPrintFormat()
	{
		return printFormat;
	}

	public void setReportData(@NonNull final Resource data)
	{
		final String filename = Check.assumeNotNull(data.getFilename(), "Resource shall have the filename set: {}", data);
		setReportData(data, filename, MimeType.getMimeType(data.getFilename()));
	}

	public void setReportData(@NonNull final Resource data, @Nullable final String filename, final String contentType)
	{
		setReportData(ReportResultData.builder()
				.reportData(data)
				.reportFilename(filename)
				.reportContentType(contentType)
				.build());
	}

	public void setReportData(@NonNull final File file)
	{
		setReportData(ReportResultData.ofFile(file));
	}

	public void setReportData(@NonNull final File file, @NonNull final String fileName)
	{
		setReportData(ReportResultData.ofFile(file, fileName));
	}
	public void setReportData(@Nullable final ReportResultData reportData)
	{
		this.reportData = reportData;
	}

	@Nullable
	public ReportResultData getReportData()
	{
		return reportData;
	}

	@Nullable
	public Resource getReportDataResource()
	{
		return reportData != null ? reportData.getReportData() : null;
	}

	@Nullable
	public String getReportFilename()
	{
		return reportData != null
				? reportData.getReportFilename()
				: null;
	}

	@Nullable
	public String getReportContentType()
	{
		return reportData != null
				? reportData.getReportContentType()
				: null;
	}

	/**
	 * Set Log of Process.
	 *
	 * <pre>
	 *  - Translated Process Message
	 *  - List of log entries
	 *      Date - Number - Msg
	 * </pre>
	 *
	 * @param html if true with HTML markup
	 * @return Log Info
	 */
	public String getLogInfo(final boolean html)
	{
		final List<ProcessInfoLog> logs = getLogsInnerList();
		if (logs.isEmpty())
		{
			return "";
		}

		//
		final StringBuilder sb = new StringBuilder();
		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.DateTime);
		if (html)
		{
			sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\">");
		}
		//
		for (final ProcessInfoLog log : logs)
		{
			if (html)
			{
				sb.append("<tr>");
			}
			else
			{
				sb.append("\n");
			}

			//
			if (log.getP_Date() != null)
			{
				sb.append(html ? "<td>" : "")
						.append(dateFormat.format(log.getP_Date()))
						.append(html ? "</td>" : " \t");
			}
			//
			if (log.getP_Number() != null)
			{
				sb.append(html ? "<td>" : "")
						.append(log.getP_Number())
						.append(html ? "</td>" : " \t");
			}
			//
			if (log.getP_Msg() != null)
			{
				sb.append(html ? "<td>" : "")
						.append(Services.get(IMsgBL.class).parseTranslation(Env.getCtx(), log.getP_Msg()))
						.append(html ? "</td>" : "");
			}
			//
			if (html)
			{
				sb.append("</tr>");
			}
		}
		if (html)
		{
			sb.append("</table>");
		}
		return sb.toString();
	}    // getLogInfo

	/**
	 * Get ASCII Log Info
	 *
	 * @return Log Info
	 */
	public String getLogInfo()
	{
		return getLogInfo(false);
	}

	/**
	 * Gets current stored logs.
	 * <p>
	 *
	 * @return logs inner list; never fails
	 */
	private List<ProcessInfoLog> getLogsInnerList()
	{
		try
		{
			return new ArrayList<>(pInstanceDAO.retrieveProcessInfoLogs(getPinstanceId()));
		}
		catch (final Exception ex)
		{
			// Don't fail log lines failed loading because most of the APIs rely on this.
			// In case we would propagate the exception we would face:
			// * worst case would be that it will stop some important execution.
			// * best case the exception would be lost somewhere without any notification
			final ArrayList<ProcessInfoLog> tempLogs = new ArrayList<>();
			tempLogs.add(ProcessInfoLog.ofMessage("Ops, sorry we failed loading the log lines. (details in console)"));
			logger.warn("Failed loading log lines for {}", this, ex);

			return tempLogs;
		}
	}

	public void markLogsAsStale()
	{
		// TODO: shall we save existing ones ?!
		logsDebouncer.purgeBuffer();
	}

	/**************************************************************************
	 * Add to Log
	 *
	 * @param Log_ID Log ID
	 * @param P_Date Process Date
	 * @param P_Number Process Number
	 * @param P_Msg Process Message
	 * @param adIssueId AD_Issue reference of an issue created during process execution.
	 */
	public void addLog(final int Log_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg, final AdIssueId adIssueId)
	{
		final ITableRecordReference recordReference = null;
		final String trxName = null;

		addLog(new ProcessInfoLog(Log_ID, P_Date, P_Number, P_Msg, recordReference, adIssueId, trxName));
	}

	/**************************************************************************
	 * Add to Log
	 *
	 * @param Log_ID Log ID
	 * @param P_Date Process Date
	 * @param P_Number Process Number
	 * @param P_Msg Process Message
	 */
	public void addLog(final int Log_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		final AdIssueId adIssueId = null;
		final ITableRecordReference tableRecordReference = null;
		final String trxName = null;

		final ProcessInfoLogRequest request = ProcessInfoLogRequest.builder()
				.log_ID(Log_ID)
				.pDate(P_Date)
				.p_Number(P_Number)
				.p_Msg(P_Msg)
				.ad_Issue_ID(adIssueId)
				.trxName(trxName)
				.tableRecordReference(tableRecordReference)
				.warningMessages(null)
				.build();
		addLog(new ProcessInfoLog(request));
	}    // addLog

	public void addLog(final RepoIdAware Log_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		final AdIssueId adIssueId = null;
		final ITableRecordReference tableRecordReference = null;
		final String trxName = null;

		final ProcessInfoLogRequest request = ProcessInfoLogRequest.builder()
				.log_ID(Log_ID != null ? Log_ID.getRepoId() : -1)
				.pDate(P_Date)
				.p_Number(P_Number)
				.p_Msg(P_Msg)
				.ad_Issue_ID(adIssueId)
				.trxName(trxName)
				.tableRecordReference(tableRecordReference)
				.warningMessages(null)
				.build();

		addLog(new ProcessInfoLog(request));
	}    // addLog

	public void addLog(final int Log_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg, @Nullable final List<String> warningMessages)
	{
		final ProcessInfoLogRequest request = ProcessInfoLogRequest.builder()
				.log_ID(Log_ID)
				.pDate(P_Date)
				.p_Number(P_Number)
				.p_Msg(P_Msg)
				.ad_Issue_ID(null)
				.trxName(null)
				.tableRecordReference(null)
				.warningMessages(warningMessages)
				.build();
		addLog(new ProcessInfoLog(request));
	}

	/**
	 * Add to Log.
	 *
	 * @param P_Date   Process Date if <code>null</code> then the current {@link SystemTime} is used.
	 * @param P_Number Process Number
	 * @param P_Msg    Process Message
	 */
	public void addLog(final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		final Timestamp timestampToUse = P_Date != null ? P_Date : SystemTime.asTimestamp();

		final AdIssueId adIssueId = null;
		final ITableRecordReference tableRecordReference = null;
		final String trxName = null;

		addLog(new ProcessInfoLog(timestampToUse, P_Number, P_Msg, tableRecordReference, adIssueId, trxName));
	}    // addLog

	/**
	 * Add to Log
	 *
	 * @param logEntry log entry
	 */
	public void addLog(final ProcessInfoLog logEntry)
	{
		if (logEntry == null)
		{
			return;
		}

		logsDebouncer.add(logEntry);
	}

	public void propagateErrorIfAny()
	{
		if (!isError())
		{
			return;
		}

		final Throwable throwable = getThrowable();
		if (throwable != null)
		{
			throw AdempiereException.wrapIfNeeded(throwable);
		}
		else
		{
			throw new AdempiereException(getSummary());
		}
	}

	public void updateFrom(final ProcessExecutionResult otherResult)
	{
		summary = otherResult.getSummary();
		error = otherResult.isError();
		throwable = otherResult.getThrowable();

		errorWasReportedToUser = otherResult.isErrorWasReportedToUser();

		timeout = otherResult.isTimeout();

		// Logs
		markLogsAsStale(); // IMPORTANT: not copying them because they are transient
		showProcessLogsPolicy = otherResult.showProcessLogsPolicy;

		//
		// Reporting
		printFormat = otherResult.printFormat;
		reportData = otherResult.reportData;

		refreshAllAfterExecution = otherResult.refreshAllAfterExecution;

		recordToSelectAfterExecution = otherResult.recordToSelectAfterExecution;
		recordsToOpen = otherResult.recordsToOpen;
		webuiViewToOpen = otherResult.webuiViewToOpen;
		displayQRCode = otherResult.displayQRCode;
	}

	public void syncLogsToDB()
	{
		logsDebouncer.processAndClearBufferSync();
	}

	private void syncCollectedLogsToDB(@NonNull final List<ProcessInfoLog> collectedProcessInfoLogs)
	{
		if (collectedProcessInfoLogs.isEmpty())
		{
			return;
		}

		pInstanceDAO.saveProcessInfoLogs(getPinstanceId(), collectedProcessInfoLogs);
	}

	//
	//
	//
	//

	@lombok.Value
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class RecordsToOpen
	{
		@JsonProperty("records")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		List<TableRecordReference> records;

		@JsonProperty("adWindowId")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Nullable
		String windowIdString;

		public enum TargetTab
		{
			SAME_TAB, SAME_TAB_OVERLAY, NEW_TAB,
		}

		@JsonProperty("targetTab")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		TargetTab targetTab;

		public enum OpenTarget
		{
			SingleDocument, SingleDocumentModal, GridView,
		}

		@JsonProperty("target")
		@NonNull
		OpenTarget target;

		@JsonProperty("automaticallySetReferencingDocumentPaths")
		boolean automaticallySetReferencingDocumentPaths;

		@JsonProperty("useAutoFilters")
		boolean useAutoFilters;

		@JsonCreator
		@Builder
		private RecordsToOpen(
				@JsonProperty("records") @NonNull @Singular final List<TableRecordReference> records,
				@JsonProperty("adWindowId") @Nullable final String adWindowId,
				@JsonProperty("target") @Nullable final OpenTarget target,
				@JsonProperty("targetTab") final TargetTab targetTab,
				@JsonProperty("automaticallySetReferencingDocumentPaths") @Nullable final Boolean automaticallySetReferencingDocumentPaths,
				@JsonProperty("useAutoFilters") @Nullable final Boolean useAutoFilters)
		{
			Check.assumeNotEmpty(records, "records is not empty");

			this.records = ImmutableList.copyOf(records);
			this.windowIdString = StringUtils.trimBlankToNull(adWindowId);
			this.target = target != null ? target : OpenTarget.GridView;
			this.targetTab = targetTab != null ? targetTab : TargetTab.SAME_TAB;
			this.automaticallySetReferencingDocumentPaths = automaticallySetReferencingDocumentPaths != null ? automaticallySetReferencingDocumentPaths : true;
			this.useAutoFilters = useAutoFilters != null ? useAutoFilters : true;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("adWindowId", windowIdString)
					.add("target", target)
					.add("automaticallySetReferencingDocumentPaths", automaticallySetReferencingDocumentPaths)
					.add("records", records)
					.add("automaticallySetReferencingDocumentPaths", automaticallySetReferencingDocumentPaths)
					.toString();
		}

		public TableRecordReference getFirstRecord()
		{
			return getRecords().get(0);
		}
	}

	//
	//
	//
	//
	//

	public enum ViewOpenTarget
	{
		IncludedView, ModalOverlay, NewBrowserTab
	}

	@Immutable
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Value
	public static class WebuiViewToOpen
	{
		@JsonProperty("viewId")
		String viewId;
		@JsonProperty("profileId")
		String profileId;
		@JsonProperty("target")
		ViewOpenTarget target;

		@lombok.Builder
		@JsonCreator
		private WebuiViewToOpen(
				@JsonProperty("viewId") @NonNull final String viewId,
				@JsonProperty("profileId") @Nullable final String profileId,
				@JsonProperty("target") @NonNull final ViewOpenTarget target)
		{
			this.viewId = viewId;
			this.profileId = profileId;
			this.target = target;
		}

		public static WebuiViewToOpen modalOverlay(@NonNull final String viewId)
		{
			return builder().viewId(viewId).target(ViewOpenTarget.ModalOverlay).build();
		}
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Value
	public static class DisplayQRCode
	{
		@JsonProperty("code")
		String code;

		@lombok.Builder
		@JsonCreator
		private DisplayQRCode(@JsonProperty("code") @NonNull final String code)
		{
			this.code = code;
		}
	}

	@Value
	@Builder
	@Jacksonized
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class CalendarToOpen
	{
		@Nullable String simulationId;
		@Nullable String calendarResourceId;
		@Nullable String projectId;
		@Nullable BPartnerId customerId;
		@Nullable UserId responsibleId;
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Value
	@lombok.Builder
	public static class WebuiNewRecord
	{
		/**
		 * If this string is used as field value
		 * then the frontend will try to open the new record modal window to populate that field.
		 * <p>
		 * Used mainly to trigger new BPartner.
		 */
		public static final String FIELD_VALUE_NEW = "NEW";

		@NonNull String windowId;

		/**
		 * Field values to be set by frontend, after the NEW record is created
		 */
		@NonNull @Singular Map<String, String> fieldValues;

		public enum TargetTab
		{
			SAME_TAB, NEW_TAB,
		}

		@NonNull @Builder.Default TargetTab targetTab = TargetTab.SAME_TAB;
	}
}
