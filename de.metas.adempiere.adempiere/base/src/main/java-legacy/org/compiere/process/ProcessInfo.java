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

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.Optional;

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
public class ProcessInfo implements Serializable, IContextAware
{
	/**
	 * Constructor
	 *
	 * @param Title Title
	 * @param AD_Process_ID AD_Process_ID
	 * @param Table_ID AD_Table_ID
	 * @param Record_ID Record_ID
	 */
	public ProcessInfo(String Title, int AD_Process_ID, int Table_ID, int Record_ID)
	{
		super();
		setTitle(Title);
		setAD_Process_ID(AD_Process_ID);
		setTable_ID(Table_ID);
		setRecord_ID(Record_ID);
		if (Ini.isPropertyBool(Ini.P_PRINTPREVIEW))
		{
			m_printPreview = true;
		}
		else
		{
			m_printPreview = false;
		}
	}   // ProcessInfo

	/**
	 * Constructor
	 *
	 * @param Title Title
	 * @param AD_Process_ID AD_Process_ID
	 */
	public ProcessInfo(String Title, int AD_Process_ID)
	{
		this(Title, AD_Process_ID, 0, 0);
	}   // ProcessInfo

	/** Serialization Info **/
	static final long serialVersionUID = -1993220053515488725L;

	// services
	private static final transient Logger logger = LogManager.getLogger(ProcessInfo.class);

	/** Title of the Process/Report */
	private String m_Title;
	/** Process ID */
	private int m_AD_Process_ID;
	private transient I_AD_Process _processModel;
	/** Table ID if the Process */
	private int m_Table_ID;
	/** Record ID if the Process */
	private int m_Record_ID;
	/** User_ID */
	private Integer m_AD_User_ID;
	/** Client_ID */
	private Integer m_AD_Client_ID;
	/** Class Name */
	private String _className = null;
	private boolean _classNameSet = false;

	// -- Optional --

	/** Process Instance ID */
	private int m_AD_PInstance_ID = 0;

	/** Summary of Execution */
	private String m_Summary = "";
	/** Execution had an error */
	private boolean m_Error = false;

	/* General Data Object */
	private Serializable m_SerializableObject = null;
	/* General Data Object */
	private transient Object m_TransientObject = null;
	/** Estimated Runtime */
	private int m_EstSeconds = 5;
	/** Batch */
	private boolean m_batch = false;
	/** Process timed out */
	private boolean m_timeout = false;

	/** Log Info */
	private ArrayList<ProcessInfoLog> m_logs = null;
	private ShowProcessLogs showProcessLogsPolicy = ShowProcessLogs.Always;

	/** Log Info */
	private ProcessInfoParameter[] m_parameter = null;
	private boolean m_parameterLoaded = false;

	private Properties ctx;
	/** Transaction Name */
	private String m_transactionName = null;

	private boolean m_printPreview = false;

	private boolean m_reportingProcess = false;
	// FR 1906632
	private File m_pdf_report = null;

	/**
	 * If the process fails with an Throwable, the Throwable is caught and stored here
	 */
	// 03152: motivation to add this is that now in ait we can assert that a certain exception was thrown.
	private Throwable m_throwable = null;

	private Boolean _refreshAllAfterExecution;

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
		sb.append(",Summary=").append(getSummary())
				.append(",Log=").append(m_logs == null ? 0 : m_logs.size());
		// .append(getLogInfo(false));
		sb.append("]");
		return sb.toString();
	}   // toString

	@Override
	public Properties getCtx()
	{
		return Env.coalesce(ctx);
	}

	public void setCtx(final Properties ctx)
	{
		this.ctx = ctx;
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
	 * Method setError
	 *
	 * @param error boolean
	 */
	public void setError(boolean error)
	{
		m_Error = error;
	}	// setError

	/**
	 * Method isError
	 *
	 * @return boolean
	 */
	public boolean isError()
	{
		return m_Error;
	}	// isError

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
		if (m_logs == null)
		{
			return "";
		}

		//
		final StringBuilder sb = new StringBuilder();
		SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.DateTime);
		if (html)
			sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"2\">");
		//
		for (int i = 0; i < m_logs.size(); i++)
		{
			if (html)
				sb.append("<tr>");
			else if (i > 0)
				sb.append("\n");
			//
			ProcessInfoLog log = m_logs.get(i);
			/**
			 * if (log.getP_ID() != 0) sb.append(html ? "<td>" : "") .append(log.getP_ID()) .append(html ? "</td>" : " \t");
			 **/
			//
			if (log.getP_Date() != null)
				sb.append(html ? "<td>" : "")
						.append(dateFormat.format(log.getP_Date()))
						.append(html ? "</td>" : " \t");
			//
			if (log.getP_Number() != null)
				sb.append(html ? "<td>" : "")
						.append(log.getP_Number())
						.append(html ? "</td>" : " \t");
			//
			if (log.getP_Msg() != null)
				sb.append(html ? "<td>" : "")
						.append(Services.get(IMsgBL.class).parseTranslation(getCtx(), log.getP_Msg()))
						.append(html ? "</td>" : "");
			//
			if (html)
				sb.append("</tr>");
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

	/** Gets/Loads the underlying {@link I_AD_Process} definition */
	private I_AD_Process getAD_ProcessOrNull()
	{
		final int processId = getAD_Process_ID();
		if (processId <= 0)
		{
			return null;
		}
		if (_processModel != null && _processModel.getAD_Process_ID() != processId)
		{
			_processModel = null;
		}
		if (_processModel == null)
		{
			_processModel = InterfaceWrapperHelper.create(getCtx(), processId, I_AD_Process.class, ITrx.TRXNAME_None);
		}
		return _processModel;
	}

	/**
	 * Method setAD_Process_ID
	 *
	 * @param AD_Process_ID int
	 */
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
		if (!_classNameSet)
		{
			// Try to load it from underlying process definition (if available)
			final I_AD_Process process = getAD_ProcessOrNull();
			if (process != null)
			{
				setClassName(process.getClassname());
			}
		}
		return _className;
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
			_className = null;
		}
		else
		{
			_className = classname.trim();
		}
		_classNameSet = true;
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

	/**
	 * Method getEstSeconds
	 *
	 * @return int
	 */
	public int getEstSeconds()
	{
		return m_EstSeconds;
	}

	/**
	 * Method setEstSeconds
	 *
	 * @param EstSeconds int
	 */
	public void setEstSeconds(int EstSeconds)
	{
		m_EstSeconds = EstSeconds;
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

	/**
	 * Method setTable_ID
	 *
	 * @param AD_Table_ID int
	 */
	public void setTable_ID(int AD_Table_ID)
	{
		m_Table_ID = AD_Table_ID;
	}

	public void setTableName(final String tableName)
	{
		if (tableName == null)
		{
			m_Table_ID = -1;
		}
		else
		{
			m_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		}
	}

	/**
	 * Method getRecord_ID
	 *
	 * @return int
	 */
	public int getRecord_ID()
	{
		return m_Record_ID;
	}

	/**
	 * Method setRecord_ID
	 *
	 * @param Record_ID int
	 */
	public void setRecord_ID(int Record_ID)
	{
		m_Record_ID = Record_ID;
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
		return getRecord(modelClass, getTrxName());
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
			return Optional.absent();
		}
		final String modelTableName = InterfaceWrapperHelper.getTableName(modelClass);
		if (!Check.equals(tableName, modelTableName))
		{
			return Optional.absent();
		}

		final int recordId = getRecord_ID();
		if (recordId < 0)
		{
			return Optional.absent();
		}

		final ModelType record = InterfaceWrapperHelper.create(getCtx(), tableName, recordId, modelClass, trxName);
		if (record == null || InterfaceWrapperHelper.isNew(record))
		{
			return Optional.absent();
		}

		return Optional.of(record);
	}

	/**
	 * Method getTitle
	 *
	 * @return String
	 */
	public String getTitle()
	{
		return m_Title;
	}

	/**
	 * Method setTitle
	 *
	 * @param Title String
	 */
	public void setTitle(String Title)
	{
		m_Title = Title;
	}	// setTitle

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
			Services.get(IADPInstanceDAO.class).loadParameterFromDB(this);
			Check.assume(m_parameterLoaded, "parameters shall be loaded at this time");
			Check.assumeNotNull(m_parameter, "m_parameter not null");
		}
		return m_parameter;
	}	// getParameter

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
	public void addLog(int Log_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg)
	{
		addLog(new ProcessInfoLog(Log_ID, P_Date, P_Number, P_Msg));
	}	// addLog

	/**
	 * Add to Log.
	 *
	 * @param P_Date Process Date if <code>null</code> then the current {@link SystemTime} is used.
	 * @param P_Number Process Number
	 * @param P_Msg Process Message
	 */
	public void addLog(Timestamp P_Date, BigDecimal P_Number, String P_Msg)
	{
		final Timestamp timestampToUse = P_Date != null ? P_Date : SystemTime.asTimestamp();

		addLog(new ProcessInfoLog(timestampToUse, P_Number, P_Msg));
	}	// addLog

	/**
	 * Add to Log
	 *
	 * @param logEntry log entry
	 */
	public void addLog(ProcessInfoLog logEntry)
	{
		if (logEntry == null)
			return;
		if (m_logs == null)
			m_logs = new ArrayList<>();
		m_logs.add(logEntry);
	}	// addLog

	/**
	 * Method getLogs
	 *
	 * @return ProcessInfoLog[]
	 */
	public ProcessInfoLog[] getLogs()
	{
		if (m_logs == null)
			return null;
		ProcessInfoLog[] logs = new ProcessInfoLog[m_logs.size()];
		m_logs.toArray(logs);
		return logs;
	}	// getLogs

	/**
	 * Method getLogList
	 *
	 * @return ArrayList
	 */
	public ArrayList<ProcessInfoLog> getLogList()
	{
		return m_logs;
	}

	/**
	 * Method setLogList
	 *
	 * @param logs ArrayList
	 */
	public void setLogList(ArrayList<ProcessInfoLog> logs)
	{
		m_logs = logs;
	}

	/**
	 * Get transaction name for this process
	 *
	 * @return String
	 */
	public String getTransactionName()
	{
		return getTrxName();
	}

	/**
	 * Get database transaction name for this process
	 *
	 * @return String
	 */
	@Override
	public String getTrxName()
	{
		return m_transactionName;
	}

	/**
	 * Set transaction name from this process
	 *
	 * @param trxName
	 */
	public void setTransactionName(String trxName)
	{
		m_transactionName = trxName;
	}

	/**
	 * Set print preview flag, only relevant if this is a reporting process.
	 * A <code>false</code> parameter can be overridden by the {@link Ini#P_PRINTPREVIEW} property
	 *
	 * @param b
	 */
	public void setPrintPreview(boolean b)
	{
		m_printPreview = b;
	}

	/**
	 * Is print preview instead of direct print ? Only relevant if this is a reporting process
	 *
	 * @return boolean
	 */
	public boolean isPrintPreview()
	{
		return m_printPreview;
	}

	/**
	 * Is this a reporting process ?
	 *
	 * @return boolean
	 */
	public boolean isReportingProcess()
	{
		return m_reportingProcess;
	}

	/**
	 * Set is this a reporting process
	 *
	 * @param f
	 */
	public void setReportingProcess(boolean f)
	{
		m_reportingProcess = f;
	}

	// FR 1906632
	/**
	 * Set PDF file generate to Jasper Report
	 *
	 * @param PDF File
	 */
	public void setPDFReport(File f)
	{
		m_pdf_report = f;
	}

	/**
	 * Get PDF file generate to Jasper Report
	 *
	 * @param f
	 */
	public File getPDFReport()
	{
		return m_pdf_report;
	}

	// metas: begin
	/** Org_ID */
	private int m_AD_Org_ID = -1; // metas: c.ghita@metas.ro

	/**
	 * Method getAD_Org_ID
	 *
	 * @return Integer
	 */
	// metas: c.ghita@metas.ro
	public int getAD_Org_ID()
	{
		if (m_AD_Org_ID == -1)
		{
			return Env.getAD_Org_ID(getCtx());
		}
		return m_AD_Org_ID;
	}

	/**
	 * Method setAD_Org_ID
	 *
	 * @param AD_Org_ID int
	 */
	// metas: c.ghita@metas.ro
	public void setAD_Org_ID(int AD_Org_ID)
	{
		m_AD_Org_ID = AD_Org_ID;
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

	/**
	 * Set Parent's WindowNo
	 *
	 * @param windowNo
	 */
	public void setWindowNo(final int windowNo)
	{
		this.m_windowNo = windowNo;
	}

	private int m_windowNo = Env.WINDOW_None;

	/**
	 * Set Parent's TabNo
	 *
	 * @param tabNo
	 * @return
	 */
	public void setTabNo(final int tabNo)
	{
		this.m_tabNo = tabNo;
	}

	public int getTabNo()
	{
		return this.m_tabNo;
	}

	private int m_tabNo = Env.TAB_None;

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
		return new TypedSqlQueryFilter<>(m_whereClause);
	}

	/**
	 * @param m_whereClause the m_whereClause to set
	 */
	public void setWhereClause(String m_whereClause)
	{
		this.m_whereClause = m_whereClause;
	}

	private String m_whereClause = "";

	private Language reportLanguage = null;

	/**
	 *
	 * @return language used to reports; could BE <code>null</code>
	 */
	public Language getReportLanguage()
	{
		return this.reportLanguage;
	}

	/**
	 * Sets language to be used in reports.
	 *
	 * @param reportLanguage
	 */
	public void setReportLanguage(final Language reportLanguage)
	{
		this.reportLanguage = reportLanguage;
	}

	// metas end

	private IGridTabSummaryInfo _gridTabSummaryInfo;

	public void setGridTabSummaryInfo(IGridTabSummaryInfo gridTabSummaryInfo)
	{
		this._gridTabSummaryInfo = gridTabSummaryInfo;
	}

	public IGridTabSummaryInfo getGridTabSymmaryInfo()
	{
		if (_gridTabSummaryInfo == null)
		{
			return IGridTabSummaryInfo.NULL;
		}
		return _gridTabSummaryInfo;
	}

	/**
	 * Same as {@link #getGridTabSymmaryInfo()} but the value will be cased to given class.
	 *
	 * If that is not possible or the underlying object is null then null will be returned.
	 *
	 * @param gridTabSummaryInfoClass
	 */
	public final <T extends IGridTabSummaryInfo> T getGridTabSummaryInfoOrNull(Class<T> gridTabSummaryInfoClass)
	{
		Check.assumeNotNull(gridTabSummaryInfoClass, "gridTabSummaryInfoClass not null");
		final IGridTabSummaryInfo gridTabSummaryInfo = getGridTabSymmaryInfo();
		if (gridTabSummaryInfoClass.isInstance(gridTabSummaryInfo))
		{
			return gridTabSummaryInfoClass.cast(gridTabSummaryInfo);
		}

		return null;
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
		if (_refreshAllAfterExecution != null)
		{
			return _refreshAllAfterExecution;
		}

		final I_AD_Process process = getAD_ProcessOrNull();
		if (process == null)
		{
			return false;
		}

		return process.isRefreshAllAfterExecution();
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
	}

}   // ProcessInfo
