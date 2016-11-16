package de.metas.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.print.MPrintFormat;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ProcessExecutionResult
{
	private static final transient Logger logger = LogManager.getLogger(ProcessExecutionResult.class);

	private int AD_PInstance_ID;

	/** Summary of Execution */
	private String summary = "";
	/** Execution had an error */
	private boolean error = false;
	private transient boolean errorWasReportedToUser = false;

	/** Process timed out */
	private boolean timeout = false;

	/** Log Info */
	private transient List<ProcessInfoLog> logs;
	private ShowProcessLogs showProcessLogsPolicy = ShowProcessLogs.Always;

	//
	// Reporting
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private transient MPrintFormat printFormat;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private byte[] reportData;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String reportFilename;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String reportContentType;

	/**
	 * Display process logs to user policy
	 */
	public static enum ShowProcessLogs
	{
		/** Always display them */
		Always, /** Display them only if the process failed */
		OnError, /** Never display them */
		Never,
	};

	/**
	 * If the process fails with an Throwable, the Throwable is caught and stored here
	 */
	// 03152: motivation to add this is that now in ait we can assert that a certain exception was thrown.
	private transient Throwable throwable = null;

	private boolean refreshAllAfterExecution = false;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private TableRecordReference recordToSelectAfterExecution = null;

	public ProcessExecutionResult()
	{
		super();
		logs = new ArrayList<>();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("summary", summary)
				.add("error", error)
				.add("printFormat", printFormat)
				.add("logs.size", logs == null ? 0 : logs.size())
				.add("AD_PInstance_ID", AD_PInstance_ID)
				.toString();
	}

	/* package */void setAD_PInstance_ID(final int AD_PInstance_ID)
	{
		this.AD_PInstance_ID = AD_PInstance_ID;
	}

	public int getAD_PInstance_ID()
	{
		return AD_PInstance_ID;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(final String summary)
	{
		this.summary = summary;
	}

//	/**
//	 * Sets summary and error flag.
//	 *
//	 * @param translatedSummary String
//	 * @param error boolean
//	 */
//	public void setSummary(final String translatedSummary, final boolean error)
//	{
//		setSummary(translatedSummary);
//		setError(error);
//	}

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
		this.throwable = null;
		this.error = false;
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

	public void markAsError(final String summary, final Throwable throwable)
	{
		this.summary = summary;
		this.throwable = throwable;
		this.error = true;
	}


//	/**
//	 * @param error true if the process execution failed
//	 */
//	public void setError(final boolean error)
//	{
//		this.error = error;
//	}

	/**
	 * @return true if the process execution failed
	 */
	public boolean isError()
	{
		return error;
	}

//	public void setThrowable(final Throwable throwable)
//	{
//		this.throwable = throwable;
//	}

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
	 * @task 03152
	 */
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
	 *
	 * @param showProcessLogsPolicy
	 */
	public final void setShowProcessLogs(final ShowProcessLogs showProcessLogsPolicy)
	{
		Check.assumeNotNull(showProcessLogsPolicy, "showProcessLogsPolicy not null");
		this.showProcessLogsPolicy = showProcessLogsPolicy;
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

	/** Sets if the whole window tab shall be refreshed after process execution (applies only when the process was started from a user window) */
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

	/**
	 * @return the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 */
	public TableRecordReference getRecordToSelectAfterExecution()
	{
		return recordToSelectAfterExecution;
	}

	/**
	 * Sets the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 *
	 * @param recordToSelectAfterExecution
	 */
	public void setRecordToSelectAfterExecution(final TableRecordReference recordToSelectAfterExecution)
	{
		this.recordToSelectAfterExecution = recordToSelectAfterExecution;
	}

	public void setPrintFormat(final MPrintFormat printFormat)
	{
		this.printFormat = printFormat;
	}

	public MPrintFormat getPrintFormat()
	{
		return printFormat;
	}

	public void setReportData(final byte[] data, final String filename, final String contentType)
	{
		reportData = data;
		reportFilename = filename;
		reportContentType = contentType;
	}

	public byte[] getReportData()
	{
		return reportData;
	}

	public String getReportFilename()
	{
		return reportFilename;
	}

	public String getReportContentType()
	{
		return reportContentType;
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
	}	// getLogInfo

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
	 * Gets current logs.
	 *
	 * If needed, it will load the logs.
	 *
	 * @return logs inner list
	 */
	private final List<ProcessInfoLog> getLogsInnerList()
	{
		if (logs == null)
		{
			logs = new ArrayList<>(Services.get(IADPInstanceDAO.class).retrieveProcessInfoLogs(getAD_PInstance_ID()));
		}
		return logs;
	}

	/**
	 * Get current logs (i.e. logs which were recorded to this instance).
	 *
	 * This method will not load the logs.
	 *
	 * @return current logs
	 */
	public List<ProcessInfoLog> getCurrentLogs()
	{
		// NOTE: don't load them!
		final List<ProcessInfoLog> logs = this.logs;
		return logs == null ? ImmutableList.of() : ImmutableList.copyOf(logs);
	}

	public void markLogsAsStale()
	{
		// TODO: shall we save existing ones ?!
		logs = null;
	}

	/**************************************************************************
	 * Add to Log
	 *
	 * @param Log_ID Log ID
	 * @param P_ID Process ID
	 * @param P_Date Process Date
	 * @param P_Number Process Number
	 * @param P_Msg Process Message
	 */
	public void addLog(final int Log_ID, final int P_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		addLog(new ProcessInfoLog(Log_ID, P_ID, P_Date, P_Number, P_Msg));
	}	// addLog

	/**
	 * Add to Log.
	 *
	 * @param P_ID Process ID
	 * @param P_Date Process Date if <code>null</code> then the current {@link SystemTime} is used.
	 * @param P_Number Process Number
	 * @param P_Msg Process Message
	 */
	public void addLog(final int P_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		final Timestamp timestampToUse = P_Date != null ? P_Date : SystemTime.asTimestamp();

		addLog(new ProcessInfoLog(P_ID, timestampToUse, P_Number, P_Msg));
	}	// addLog

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

		final List<ProcessInfoLog> logs;
		if (this.logs == null)
		{
			logs = this.logs = new ArrayList<>();
		}
		else
		{
			logs = this.logs;
		}

		logs.add(logEntry);
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
		reportFilename = otherResult.reportFilename;
		reportContentType = otherResult.reportContentType;

		refreshAllAfterExecution = otherResult.refreshAllAfterExecution;

		recordToSelectAfterExecution = otherResult.recordToSelectAfterExecution;
	}
}
