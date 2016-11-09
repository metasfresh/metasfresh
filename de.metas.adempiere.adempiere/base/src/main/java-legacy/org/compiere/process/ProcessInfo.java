/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_Process;
import de.metas.logging.LogManager;

/**
 * Process Information (Value Object)
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfo.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 * @author victor.perez@e-evolution.com
 * @see FR 1906632 http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1906632&group_id=176962
 */
public final class ProcessInfo implements Serializable
{
	public static final ProcessInfoBuilder builder()
	{
		return new ProcessInfoBuilder();
	}

	private ProcessInfo(final ProcessInfoBuilder builder)
	{
		super();
		ctx = builder.getCtx();

		m_AD_Process_ID = builder.getAD_Process_ID();
		m_AD_PInstance_ID = builder.getAD_PInstance_ID();
		
		m_AD_Client_ID = builder.getAD_Client_ID();
		m_AD_Org_ID = builder.getAD_Org_ID();
		m_AD_User_ID = builder.getAD_User_ID();
		
		m_Title = builder.getTitle();
		
		_className = builder.getClassname();
		_dbProcedureName = builder.getDBProcedureName();
		adWorkflowId = builder.getAD_Workflow_ID();
		serverProcess = builder.isServerProcess();
		
		_refreshAllAfterExecution = builder.isRefreshAllAfterExecution();

		m_Table_ID = builder.getAD_Table_ID();
		m_Record_ID = builder.getRecord_ID();
		m_whereClause = builder.getWhereClause();

		m_windowNo = builder.getWindowNo();
		m_tabNo = builder.getTabNo();

		printPreview = builder.isPrintPreview();
		reportingProcess = builder.isReportingProcess();
		reportLanguage = builder.getReportLanguage();
		reportTemplate = builder.getReportTemplate();

		final List<ProcessInfoParameter> parameters = builder.getParametersOrNull();
		if (parameters == null)
		{
			this.m_parameter = null;
			this.m_parameterLoaded = false;
		}
		else
		{
			this.m_parameter = parameters.toArray(new ProcessInfoParameter[parameters.size()]);
			this.m_parameterLoaded = true;
		}
	}

	/** Serialization Info **/
	static final long serialVersionUID = -1993220053515488725L;

	// services
	private static final transient Logger logger = LogManager.getLogger(ProcessInfo.class);

	/** Title of the Process/Report */
	private final String m_Title;
	/** Process ID */
	private int m_AD_Process_ID;
	/** Table ID if the Process */
	private final int m_Table_ID;
	/** Record ID if the Process */
	private final int m_Record_ID;
	/** Client_ID */
	private Integer m_AD_Client_ID;
	/** User_ID */
	private Integer m_AD_User_ID;
	private final int m_AD_Org_ID;
	private final int m_windowNo;
	private final int m_tabNo;
	/** Class Name */
	private Optional<String> _className = Optional.empty();
	private final Optional<String> _dbProcedureName;
	private final int adWorkflowId;
	private final boolean serverProcess;

	// -- Optional --

	/** Process Instance ID */
	private int m_AD_PInstance_ID = 0;

	/** Summary of Execution */
	private String m_Summary = "";
	/** Execution had an error */
	private boolean m_Error = false;
	private transient boolean m_ErrorWasReportedToUser = false;

	/* General Data Object */
	private Serializable m_SerializableObject = null;
	/* General Data Object */
	private transient Object m_TransientObject = null;
	/** Batch */
	private boolean m_batch = false;
	/** Process timed out */
	private boolean m_timeout = false;

	/** Log Info */
	private List<ProcessInfoLog> _logs = new ArrayList<>();
	private ShowProcessLogs showProcessLogsPolicy = ShowProcessLogs.Always;

	/** Log Info */
	private ProcessInfoParameter[] m_parameter = null;
	private boolean m_parameterLoaded = false;

	private final Properties ctx;

	private final boolean printPreview;
	private final boolean reportingProcess;
	private final Optional<String> reportTemplate;
	private final Language reportLanguage;

	/**
	 * If the process fails with an Throwable, the Throwable is caught and stored here
	 */
	// 03152: motivation to add this is that now in ait we can assert that a certain exception was thrown.
	private Throwable m_throwable = null;

	private boolean _refreshAllAfterExecution;

	private ITableRecordReference _recordToSelectAfterExecution = null;

	/**
	 * String representation
	 *
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("ProcessInfo[");
		sb.append(m_Title)
				.append(",Process_ID=").append(m_AD_Process_ID);
		if (m_AD_PInstance_ID != 0)
			sb.append(",AD_PInstance_ID=").append(m_AD_PInstance_ID);
		if (m_Record_ID > 0)
			sb.append(",Record_ID=").append(m_Record_ID);
		if (_className != null)
			sb.append(",ClassName=").append(_className);
		sb.append(",Error=").append(isError());
		if (m_TransientObject != null)
			sb.append(",Transient=").append(m_TransientObject);
		if (m_SerializableObject != null)
			sb.append(",Serializable=").append(m_SerializableObject);

		sb.append(",Summary=").append(getSummary());

		final List<ProcessInfoLog> logs = _logs;
		sb.append(",Log=").append(logs == null ? 0 : logs.size());

		sb.append("]");
		return sb.toString();
	}   // toString

	public Properties getCtx()
	{
		return Env.coalesce(ctx);
	}

	/**************************************************************************
	 * Set Summary
	 *
	 * @param summary summary (will be translated)
	 */
	public void setSummary(String summary)
	{
		m_Summary = summary;
	}	// setSummary

	/**
	 * Method getSummary
	 *
	 * @return String
	 */
	public String getSummary()
	{
		return Util.cleanAmp(m_Summary);
	}	// getSummary

	/**
	 * Method setSummary
	 *
	 * @param translatedSummary String
	 * @param error boolean
	 */
	public void setSummary(String translatedSummary, boolean error)
	{
		setSummary(translatedSummary);
		setError(error);
	}	// setSummary

	/**
	 * Method addSummary
	 *
	 * @param additionalSummary String
	 */
	public void addSummary(String additionalSummary)
	{
		m_Summary += additionalSummary;
	}	// addSummary

	/**
	 * @param error true if the process execution failed
	 */
	public void setError(final boolean error)
	{
		m_Error = error;
	}	// setError

	/**
	 * @return true if the process execution failed
	 */
	public boolean isError()
	{
		return m_Error;
	}	// isError

	public void setErrorWasReportedToUser()
	{
		m_ErrorWasReportedToUser = true;
	}

	public boolean isErrorWasReportedToUser()
	{
		return m_ErrorWasReportedToUser;
	}

	/**
	 * Batch
	 *
	 * @param batch true if batch processing
	 */
	public void setIsBatch(boolean batch)
	{
		m_batch = batch;
	}	// setTimeout

	/**
	 * Batch - i.e. UI not blocked
	 *
	 * @return boolean
	 */
	public boolean isBatch()
	{
		return m_batch;
	}	// isBatch

	/**
	 * Timeout
	 *
	 * @param timeout true still running
	 */
	public void setIsTimeout(boolean timeout)
	{
		m_timeout = timeout;
	}	// setTimeout

	/**
	 * Timeout - i.e process did not complete
	 *
	 * @return boolean
	 */
	public boolean isTimeout()
	{
		return m_timeout;
	}	// isTimeout

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
				logger.warn("Unknown ShowProcessLogsPolicy: " + showProcessLogsPolicy + ". Considering " + ShowProcessLogs.Always);
				return true;
		}
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
		SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.DateTime);
		if (html)
			sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\">");
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
						.append(Services.get(IMsgBL.class).parseTranslation(getCtx(), log.getP_Msg()))
						.append(html ? "</td>" : "");
			}
			//
			if (html)
			{
				sb.append("</tr>");
			}
		}
		if (html)
			sb.append("</table>");
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
	}	// getLogInfo

	/**
	 * Method getAD_PInstance_ID
	 *
	 * @return int
	 */
	public int getAD_PInstance_ID()
	{
		return m_AD_PInstance_ID;
	}

	/**
	 * Method setAD_PInstance_ID
	 *
	 * @param AD_PInstance_ID int
	 */
	public void setAD_PInstance_ID(int AD_PInstance_ID)
	{
		m_AD_PInstance_ID = AD_PInstance_ID;
	}

	/**
	 * Method getAD_Process_ID
	 *
	 * @return int
	 */
	public int getAD_Process_ID()
	{
		return m_AD_Process_ID;
	}

	/**
	 * Method setAD_Process_ID
	 *
	 * @param AD_Process_ID int
	 */
	@Deprecated
	public void setAD_Process_ID(int AD_Process_ID)
	{
		m_AD_Process_ID = AD_Process_ID;
	}

	/**
	 * Method getClassName
	 *
	 * @return String or null
	 */
	public String getClassName()
	{
		return _className.orElse(null);
	}

	/**
	 * Method setClassName
	 *
	 * @param classname String
	 */
	public void setClassName(final String classname)
	{
		if (Check.isEmpty(classname, true))
		{
			_className = Optional.empty();
		}
		else
		{
			_className = Optional.of(classname.trim());
		}
	}
	
	public Optional<String> getDBProcedureName()
	{
		return _dbProcedureName;
	}
	
	public int getAD_Workflow_ID()
	{
		return adWorkflowId;
	}

	public boolean isServerProcess()
	{
		return serverProcess;
	}
	
	/**
	 * Method getTransientObject
	 *
	 * @return Object
	 */
	public Object getTransientObject()
	{
		return m_TransientObject;
	}

	/**
	 * Method setTransientObject
	 *
	 * @param TransientObject Object
	 */
	public void setTransientObject(Object TransientObject)
	{
		m_TransientObject = TransientObject;
	}

	/**
	 * Method getSerializableObject
	 *
	 * @return Serializable
	 */
	public Serializable getSerializableObject()
	{
		return m_SerializableObject;
	}

	/**
	 * Method setSerializableObject
	 *
	 * @param SerializableObject Serializable
	 */
	public void setSerializableObject(Serializable SerializableObject)
	{
		m_SerializableObject = SerializableObject;
	}

	public String getTableNameOrNull()
	{
		if (m_Table_ID <= 0)
		{
			return null;
		}
		return Services.get(IADTableDAO.class).retrieveTableName(m_Table_ID);
	}

	/**
	 * Method getTable_ID
	 *
	 * @return int
	 */
	public int getTable_ID()
	{
		return m_Table_ID;
	}

	public int getRecord_ID()
	{
		return m_Record_ID;
	}

	/**
	 * Retrieve underlying model for AD_Table_ID/Record_ID.
	 *
	 * @param modelClass
	 * @return record; never returns null
	 * @throws AdempiereException if no model found
	 */
	public <ModelType> ModelType getRecord(final Class<ModelType> modelClass)
	{
		return getRecord(modelClass, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Retrieve underlying model for AD_Table_ID/Record_ID.
	 *
	 * @param modelClass
	 * @param trxName transaction to be used when loading the record
	 * @return record; never returns null
	 * @throws AdempiereException if no model found
	 */
	public <ModelType> ModelType getRecord(final Class<ModelType> modelClass, final String trxName)
	{
		Check.assumeNotNull(modelClass, "modelClass not null");

		final String tableName = getTableNameOrNull();
		if (Check.isEmpty(tableName, true))
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@");
		}

		final int recordId = getRecord_ID();
		if (recordId < 0)
		{
			throw new AdempiereException("@NotFound@ @Record_ID@");
		}

		final ModelType record = InterfaceWrapperHelper.create(getCtx(), tableName, recordId, modelClass, trxName);
		if (record == null || InterfaceWrapperHelper.isNew(record))
		{
			throw new AdempiereException("@NotFound@ @Record_ID@ (@TableName@:" + tableName + ", @Record_ID@:" + recordId + ")");
		}

		return record;
	}

	/**
	 * Retrieve underlying model for AD_Table_ID/Record_ID.
	 *
	 * @param modelClass
	 * @param trxName
	 * @return record or {@link Optional#absent()} if record does not exist or it does not match given <code>modelClass</code>
	 */
	public final <ModelType> Optional<ModelType> getRecordIfApplies(final Class<ModelType> modelClass, final String trxName)
	{
		Check.assumeNotNull(modelClass, "modelClass not null");
		final String tableName = getTableNameOrNull();
		if (Check.isEmpty(tableName, true))
		{
			return Optional.empty();
		}
		final String modelTableName = InterfaceWrapperHelper.getTableName(modelClass);
		if (!Check.equals(tableName, modelTableName))
		{
			return Optional.empty();
		}

		final int recordId = getRecord_ID();
		if (recordId < 0)
		{
			return Optional.empty();
		}

		final ModelType record = InterfaceWrapperHelper.create(getCtx(), tableName, recordId, modelClass, trxName);
		if (record == null || InterfaceWrapperHelper.isNew(record))
		{
			return Optional.empty();
		}

		return Optional.of(record);
	}

	/**
	 * @return process title/name
	 */
	public String getTitle()
	{
		return m_Title;
	}

	/**
	 * Method setAD_Client_ID
	 *
	 * @param AD_Client_ID int
	 */
	public void setAD_Client_ID(int AD_Client_ID)
	{
		this.m_AD_Client_ID = AD_Client_ID;
	}

	/**
	 * Method getAD_Client_ID
	 *
	 * @return Integer
	 */
	public Integer getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	/**
	 * Method setAD_User_ID
	 *
	 * @param AD_User_ID int
	 */
	public void setAD_User_ID(int AD_User_ID)
	{
		m_AD_User_ID = AD_User_ID;
	}

	/**
	 * Method getAD_User_ID
	 *
	 * @return Integer
	 */
	public Integer getAD_User_ID()
	{
		return m_AD_User_ID;
	}

	/**
	 * Get Process Parameters.
	 *
	 * If they were not already set, they will be loaded from database.
	 *
	 * @return Parameter Array
	 */
	public final ProcessInfoParameter[] getParameter()
	{
		if (!m_parameterLoaded)
		{
			Services.get(IADPInstanceDAO.class).loadFromDB(this);
			Check.assume(m_parameterLoaded, "parameters shall be loaded at this time");
			Check.assumeNotNull(m_parameter, "m_parameter not null");
		}
		return m_parameter;
	}	// getParameter

	public final ProcessInfoParameter[] getParametersNoLoad()
	{
		return m_parameter;
	}

	/**
	 * Get Process Parameters as IParams instance.
	 */
	public final IRangeAwareParams getParameterAsIParams()
	{
		return new ProcessParams(getParameter());
	}

	/**
	 * Set Parameters.
	 *
	 * NOTE: calling this method will override current existing paramters (if any) and it will prevent them to be loaded from database.
	 *
	 * @param parameter Parameter Array
	 */
	@Deprecated
	public void setParameter(final ProcessInfoParameter[] parameter)
	{
		m_parameter = parameter;
		m_parameterLoaded = true;
	}	// setParameter

	/**************************************************************************
	 * Add to Log
	 *
	 * @param Log_ID Log ID
	 * @param P_ID Process ID
	 * @param P_Date Process Date
	 * @param P_Number Process Number
	 * @param P_Msg Process Message
	 */
	public void addLog(int Log_ID, int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg)
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
	public void addLog(int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg)
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
		if (_logs == null)
		{
			logs = _logs = new ArrayList<>();
		}
		else
		{
			logs = _logs;
		}

		logs.add(logEntry);
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
		if (_logs == null)
		{
			_logs = new ArrayList<>(ProcessInfoUtil.retrieveLogsFromDB(getAD_PInstance_ID()));
		}
		return _logs;
	}

	public void markLogsAsStale()
	{
		// TODO: shall we save existing ones ?!
		_logs = null;
	}

	/**
	 * Get current logs (i.e. logs which were recorded to this instance).
	 * 
	 * This method will not load the logs.
	 * 
	 * @return current logs
	 */
	/* package */List<ProcessInfoLog> getCurrentLogs()
	{
		// NOTE: don't load them!
		final List<ProcessInfoLog> logs = _logs;
		return logs == null ? ImmutableList.of() : ImmutableList.copyOf(logs);
	}

	/**
	 * Is print preview instead of direct print ? Only relevant if this is a reporting process
	 *
	 * @return boolean
	 */
	public boolean isPrintPreview()
	{
		return printPreview;
	}

	/**
	 * Is this a reporting process ?
	 *
	 * @return boolean
	 */
	public boolean isReportingProcess()
	{
		return reportingProcess;
	}

	/**
	 * Method getAD_Org_ID
	 *
	 * @return Integer
	 */
	// metas: c.ghita@metas.ro
	public int getAD_Org_ID()
	{
		if (m_AD_Org_ID < 0)
		{
			return Env.getAD_Org_ID(getCtx());
		}
		return m_AD_Org_ID;
	}
	// metas: end

	// metas: t.schoeneberg@metas.de
	// 03152
	/**
	 * If the process has failed with a Throwable, that Throwable can be retrieved using this getter.
	 *
	 * @return
	 */
	public Throwable getThrowable()
	{
		return m_throwable;
	}

	public void setThrowable(Throwable t)
	{
		this.m_throwable = t;
	}

	// metas: end

	// metas: cg
	// 03040
	/**
	 * @return Parent's WindowNo
	 */
	public int getWindowNo()
	{
		return m_windowNo;
	}

	public int getTabNo()
	{
		return this.m_tabNo;
	}

	// metas end

	/**
	 * @return the m_whereClause
	 * @deprecated please use {@link #getQueryFilter()} instead
	 */
	@Deprecated
	public String getWhereClause()
	{
		return m_whereClause;
	}

	/**
	 *
	 * @return a query filter for the current m_whereClause
	 * @task 03685
	 */
	public <T> IQueryFilter<T> getQueryFilter()
	{
		if (Check.isEmpty(m_whereClause, true))
		{
			// no whereClause: return a "neutral" filter that does not exclude anything
			return ConstantQueryFilter.of(true);
		}
		return new TypedSqlQueryFilter<T>(m_whereClause);
	}

	private final String m_whereClause;

	/**
	 *
	 * @return language used to reports; could BE <code>null</code>
	 */
	public Language getReportLanguage()
	{
		return this.reportLanguage;
	}
	
	public Optional<String> getReportTemplate()
	{
		return reportTemplate;
	}

	/**
	 *
	 * @return the {@link ProcessClassInfo} for this instance's <code>className</code> (see {@link #getClassName()}) or {@link ProcessClassInfo#NULL} if this instance has no classname or the
	 *         instance's classname's class can't be loaded.
	 */
	public ProcessClassInfo getProcessClassInfo()
	{
		Class<?> processClass = null;
		final String classname = getClassName();
		if (!Check.isEmpty(classname))
		{
			try
			{
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				if (classLoader == null)
				{
					classLoader = getClass().getClassLoader();
				}
				processClass = classLoader.loadClass(classname);
			}
			catch (ClassNotFoundException e)
			{
				// nothing
			}
		}
		return ProcessClassInfo.of(processClass);
	}

	/**
	 * @return if the whole window tab shall be refreshed after process execution (applies only when the process was started from a user window)
	 */
	public boolean isRefreshAllAfterExecution()
	{
		return _refreshAllAfterExecution;
	}

	/** Sets if the whole window tab shall be refreshed after process execution (applies only when the process was started from a user window) */
	public void setRefreshAllAfterExecution(final boolean refreshAllAfterExecution)
	{
		this._refreshAllAfterExecution = refreshAllAfterExecution;
	}

	/**
	 * @return the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 */
	public ITableRecordReference getRecordToSelectAfterExecution()
	{
		return _recordToSelectAfterExecution;
	}

	/**
	 * Sets the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 *
	 * @param recordToSelectAfterExecution
	 */
	public void setRecordToSelectAfterExecution(final ITableRecordReference recordToSelectAfterExecution)
	{
		this._recordToSelectAfterExecution = recordToSelectAfterExecution;
	}

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

	public static final class ProcessInfoBuilder
	{
		private Properties ctx;

		private int adPInstanceId;
		private I_AD_Process _adProcess;
		private int adProcessId;
		private Integer _adClientId;
		private Integer _adUserId;
		private String title = null;
		private Optional<String> classname;
		private Boolean refreshAllAfterExecution;

		private int adTableId;
		private int recordId;

		private String whereClause = "";

		private int windowNo = Env.WINDOW_None;
		private int tabNo = Env.TAB_None;

		private Language reportLanguage;
		private Boolean printPreview;

		private List<ProcessInfoParameter> parameters = null;

		private ProcessInfoBuilder()
		{
			super();
		}

		public ProcessInfo build()
		{
			return new ProcessInfo(this);
		}

		public ProcessInfoBuilder setCtx(final Properties ctx)
		{
			this.ctx = ctx;
			return this;
		}

		private Properties getCtx()
		{
			return Env.coalesce(ctx);
		}
		
		private int getAD_Client_ID()
		{
			if(_adClientId != null)
			{
				return _adClientId;
			}
			return Env.getAD_Client_ID(getCtx());
		}
		
		public ProcessInfoBuilder setAD_Client_ID(final int adClientId)
		{
			this._adClientId = adClientId;
			return this;
		}
		
		private int getAD_Org_ID()
		{
			return Env.getAD_Org_ID(getCtx());
		}

		
		private int getAD_User_ID()
		{
			if(_adUserId != null)
			{
				return _adUserId;
			}
			return Env.getAD_User_ID(getCtx());
		}
		
		public ProcessInfoBuilder setAD_User_ID(final int adUserId)
		{
			this._adUserId = adUserId;
			return this;
		}


		private String getTitle()
		{
			if (title != null)
			{
				return title;
			}

			final I_AD_Process adProcess = getAD_ProcessOrNull();
			if (adProcess == null)
			{
				return "";
			}

			final I_AD_Process adProcessTrl = InterfaceWrapperHelper.translate(adProcess, I_AD_Process.class);
			return adProcessTrl.getName();
		}

		public ProcessInfoBuilder setTitle(final String title)
		{
			this.title = title;
			return this;
		}
		
		public ProcessInfoBuilder setAD_PInstance_ID(final int adPInstanceId)
		{
			this.adPInstanceId = adPInstanceId;
			return this;
		}
		
		private int getAD_PInstance_ID()
		{
			return adPInstanceId;
		}

		private int getAD_Process_ID()
		{
			if(adProcessId <= 0 && adPInstanceId > 0)
			{
				final I_AD_PInstance adPInstance = InterfaceWrapperHelper.create(getCtx(), adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
				adProcessId = adPInstance.getAD_Process_ID();
			}
			return adProcessId;
		}

		public ProcessInfoBuilder setFromAD_Process(final org.compiere.model.I_AD_Process adProcess)
		{
			this._adProcess = InterfaceWrapperHelper.create(adProcess, I_AD_Process.class);
			setAD_Process_ID(_adProcess.getAD_Process_ID());
			return this;
		}

		private I_AD_Process getAD_ProcessOrNull()
		{
			final int processId = getAD_Process_ID();
			if (processId <= 0)
			{
				return null;
			}
			if (_adProcess != null && _adProcess.getAD_Process_ID() != processId)
			{
				_adProcess = null;
			}
			if (_adProcess == null)
			{
				final Properties ctx = Env.coalesce(getCtx());
				_adProcess = InterfaceWrapperHelper.create(ctx, processId, I_AD_Process.class, ITrx.TRXNAME_None);
			}
			return _adProcess;
		}

		public ProcessInfoBuilder setAD_Process_ID(final int adProcessId)
		{
			this.adProcessId = adProcessId;
			return this;
		}

		public ProcessInfoBuilder setClassname(final String classname)
		{
			if (Check.isEmpty(classname, true))
			{
				this.classname = Optional.empty();
			}
			else
			{
				this.classname = Optional.of(classname.trim());
			}
			return this;
		}

		private Optional<String> getClassname()
		{
			if (this.classname == null)
			{
				// Try to load it from underlying process definition (if available)
				final I_AD_Process process = getAD_ProcessOrNull();
				final String classname = process == null ? null : process.getClassname();
				if (Check.isEmpty(classname, true))
				{
					this.classname = Optional.empty();
				}
				else
				{
					this.classname = Optional.of(classname.trim());
				}
			}

			return classname;
		}
		
		private Optional<String> getDBProcedureName()
		{
			final I_AD_Process process = getAD_ProcessOrNull();
			final String dbProcedureName = process == null ? null : process.getProcedureName();
			if (Check.isEmpty(dbProcedureName, true))
			{
				return Optional.empty();
			}
			else
			{
				return Optional.of(dbProcedureName.trim());
			}
		}
		
		private Optional<String> getReportTemplate()
		{
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			if(adProcess == null)
			{
				return Optional.empty();
			}
			
			final String reportTemplate = adProcess.getJasperReport();
			if(Check.isEmpty(reportTemplate, true))
			{
				return Optional.empty();
			}
			
			return Optional.of(reportTemplate.trim());
		}
		
		private int getAD_Workflow_ID()
		{
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			final int adWorkflowId = adProcess != null ? adProcess.getAD_Workflow_ID() : -1;
			return adWorkflowId > 0 ? adWorkflowId : -1;
		}

		/**
		 * Sets if the whole window tab shall be refreshed after process execution (applies only when the process was started from a user window)
		 * 
		 * @return
		 */
		public ProcessInfoBuilder setRefreshAllAfterExecution(final boolean refreshAllAfterExecution)
		{
			this.refreshAllAfterExecution = refreshAllAfterExecution;
			return this;
		}

		private boolean isRefreshAllAfterExecution()
		{
			if (refreshAllAfterExecution != null)
			{
				return refreshAllAfterExecution;
			}

			final I_AD_Process process = getAD_ProcessOrNull();
			if (process == null)
			{
				return false;
			}

			return process.isRefreshAllAfterExecution();
		}

		public ProcessInfoBuilder setTableName(final String tableName)
		{
			this.adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			return this;
		}

		public ProcessInfoBuilder setRecord(final int adTableId, final int recordId)
		{
			this.adTableId = adTableId;
			this.recordId = recordId;
			return this;
		}

		public ProcessInfoBuilder setRecord(final String tableName, final int recordId)
		{
			this.adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			this.recordId = recordId;
			return this;
		}

		private int getAD_Table_ID()
		{
			return adTableId;
		}

		private int getRecord_ID()
		{
			return recordId;
		}

		/**
		 * Sets language to be used in reports.
		 * 
		 * @param reportLanguage optional report language
		 * @return
		 */
		public ProcessInfoBuilder setReportLanguage(final Language reportLanguage)
		{
			this.reportLanguage = reportLanguage;
			return this;
		}

		private Language getReportLanguage()
		{
			return reportLanguage;
		}

		public ProcessInfoBuilder setPrintPreview(final boolean printPreview)
		{
			this.printPreview = printPreview;
			return this;
		}

		public boolean isPrintPreview()
		{
			final Boolean printPreview = this.printPreview;
			if (printPreview != null)
			{
				return printPreview;
			}

			if (Ini.isPropertyBool(Ini.P_PRINTPREVIEW))
			{
				return true;
			}
			
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			if(adProcess != null && !adProcess.isDirectPrint())
			{
				return true;
			}
			
			return false;
		}

		public ProcessInfoBuilder setWindowNo(int windowNo)
		{
			this.windowNo = windowNo;
			return this;
		}

		private int getWindowNo()
		{
			return windowNo;
		}

		public ProcessInfoBuilder setTabNo(int tabNo)
		{
			this.tabNo = tabNo;
			return this;
		}

		private int getTabNo()
		{
			return tabNo;
		}

		public ProcessInfoBuilder setWhereClause(String whereClause)
		{
			this.whereClause = whereClause;
			return this;
		}

		private String getWhereClause()
		{
			return whereClause;
		}
		
		private boolean isServerProcess()
		{
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			return adProcess != null ? adProcess.isServerProcess() : false;
		}
		
		private boolean isReportingProcess()
		{
			if (getReportTemplate().isPresent())
			{
				return true;
			}
			
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			return adProcess != null ? adProcess.isReport() : false;
		}

		private List<ProcessInfoParameter> getParametersOrNull()
		{
			return parameters;
		}

		public ProcessInfoBuilder addParameter(ProcessInfoParameter param)
		{
			if (parameters == null)
			{
				parameters = new ArrayList<>();
			}
			parameters.add(param);

			return this;
		}

		public ProcessInfoBuilder addParameters(final ProcessInfoParameter[] params)
		{
			if (params == null || params.length == 0)
			{
				return this;
			}

			if (parameters == null)
			{
				parameters = new ArrayList<>();
			}

			for (ProcessInfoParameter param : params)
			{
				parameters.add(param);
			}

			return this;
		}

		public ProcessInfoBuilder addParameters(final List<ProcessInfoParameter> params)
		{
			if (parameters == null)
			{
				parameters = new ArrayList<>();
			}

			parameters.addAll(params);

			return this;
		}

	}
}   // ProcessInfo
