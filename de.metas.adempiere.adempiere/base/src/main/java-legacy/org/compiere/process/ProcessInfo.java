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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_Process;
import de.metas.adempiere.report.jasper.OutputType;

/**
 * Process Information (Value Object)
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfo.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 * @author victor.perez@e-evolution.com
 * @see FR 1906632 http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1906632&group_id=176962
 */
@SuppressWarnings("serial")
public final class ProcessInfo implements Serializable
{
	public static final ProcessInfoBuilder builder()
	{
		return new ProcessInfoBuilder();
	}

	private ProcessInfo(final Properties ctx, final ProcessInfoBuilder builder)
	{
		super();
		this.ctx = ctx;

		adProcessId = builder.getAD_Process_ID();
		adPInstanceId = builder.getAD_PInstance_ID();

		adClientId = builder.getAD_Client_ID();
		adOrgId = builder.getAD_Org_ID();
		adUserId = builder.getAD_User_ID();
		adRoleId = builder.getAD_Role_ID();

		title = builder.getTitle();

		className = builder.getClassname();
		dbProcedureName = builder.getDBProcedureName();
		adWorkflowId = builder.getAD_Workflow_ID();
		serverProcess = builder.isServerProcess();

		adTableId = builder.getAD_Table_ID();
		recordId = builder.getRecord_ID();
		whereClause = builder.getWhereClause();

		windowNo = builder.getWindowNo();
		tabNo = builder.getTabNo();

		printPreview = builder.isPrintPreview();
		reportingProcess = builder.isReportingProcess();
		reportLanguage = builder.getReportLanguage();
		reportTemplate = builder.getReportTemplate();
		jrDesiredOutputType = builder.getJRDesiredOutputType();

		final List<ProcessInfoParameter> parameters = builder.getParametersOrNull();
		this.parameters = parameters == null ? null : ImmutableList.copyOf(parameters);

		result = new ProcessExecutionResult();
		result.setAD_PInstance_ID(adPInstanceId);
		result.setRefreshAllAfterExecution(builder.isRefreshAllAfterExecution());
	}

	private final Properties ctx;

	/** Title of the Process/Report */
	private final String title;
	/** AD_Process_ID */
	private final int adProcessId;
	/** Table ID if the Process */
	private final int adTableId;
	/** Record ID if the Process */
	private final int recordId;
	private final String whereClause;
	private final int adClientId;
	private final int adOrgId;
	private final int adUserId;
	private final int adRoleId;
	private final int windowNo;
	private final int tabNo;
	/** Class Name */
	private final Optional<String> className;
	private final Optional<String> dbProcedureName;
	private final int adWorkflowId;
	private final boolean serverProcess;

	/** Process Instance ID */
	private int adPInstanceId;

	/** Batch */
	private final boolean batch = false;

	/** Parameters */
	private List<ProcessInfoParameter> parameters = null; // lazy loaded

	//
	// Reporting related
	private final boolean printPreview;
	private final boolean reportingProcess;
	private final Optional<String> reportTemplate;
	private final Language reportLanguage;
	private final OutputType jrDesiredOutputType;

	/** Process result */
	private final ProcessExecutionResult result;

	/**
	 * String representation
	 *
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("title", title)
				.add("AD_Process_ID", adProcessId)
				.add("AD_PInstance_ID", adPInstanceId)
				.add("AD_Table_ID", adTableId)
				.add("Record_ID", recordId)
				.add("Classname", className.orElse(null))
				.toString();
	}

	public Properties getCtx()
	{
		return Env.coalesce(ctx);
	}

	/**
	 * @return execution result
	 */
	public ProcessExecutionResult getResult()
	{
		return result;
	}

	/**
	 * Batch - i.e. UI not blocked
	 *
	 * @return boolean
	 */
	public boolean isBatch()
	{
		return batch;
	}	// isBatch

	/**
	 * Method getAD_PInstance_ID
	 *
	 * @return int
	 */
	public int getAD_PInstance_ID()
	{
		return adPInstanceId;
	}

	/**
	 * Method setAD_PInstance_ID
	 *
	 * @param AD_PInstance_ID int
	 */
	public void setAD_PInstance_ID(final int AD_PInstance_ID)
	{
		adPInstanceId = AD_PInstance_ID;
		result.setAD_PInstance_ID(AD_PInstance_ID);
	}

	/**
	 * Method getAD_Process_ID
	 *
	 * @return int
	 */
	public int getAD_Process_ID()
	{
		return adProcessId;
	}

	/**
	 * Method getClassName
	 *
	 * @return String or null
	 */
	public String getClassName()
	{
		return className.orElse(null);
	}

	public Optional<String> getDBProcedureName()
	{
		return dbProcedureName;
	}

	public int getAD_Workflow_ID()
	{
		return adWorkflowId;
	}

	public boolean isServerProcess()
	{
		return serverProcess;
	}

	public String getTableNameOrNull()
	{
		if (adTableId <= 0)
		{
			return null;
		}
		return Services.get(IADTableDAO.class).retrieveTableName(adTableId);
	}

	/**
	 * Method getTable_ID
	 *
	 * @return int
	 */
	public int getTable_ID()
	{
		return adTableId;
	}

	public int getRecord_ID()
	{
		return recordId;
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
		return title;
	}

	public int getAD_Client_ID()
	{
		return adClientId;
	}

	public int getAD_User_ID()
	{
		return adUserId;
	}
	
	public int getAD_Role_ID()
	{
		return adRoleId;
	}

	/**
	 * Get Process Parameters.
	 *
	 * If they were not already set, they will be loaded from database.
	 *
	 * @return Parameter Array
	 */
	public final List<ProcessInfoParameter> getParameter()
	{
		if (parameters == null)
		{
			parameters = Services.get(IADPInstanceDAO.class).retrieveProcessInfoParameters(getCtx(), getAD_PInstance_ID());
		}
		return parameters;
	}	// getParameter

	public final List<ProcessInfoParameter> getParametersNoLoad()
	{
		return parameters;
	}

	/**
	 * Get Process Parameters as IParams instance.
	 */
	public final IRangeAwareParams getParameterAsIParams()
	{
		return new ProcessParams(getParameter());
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
		return adOrgId;
	}
	// metas: end

	// metas: end

	// metas: cg
	// 03040
	/**
	 * @return Parent's WindowNo
	 */
	public int getWindowNo()
	{
		return windowNo;
	}

	public int getTabNo()
	{
		return this.tabNo;
	}

	// metas end

	/**
	 * @return the m_whereClause
	 * @deprecated please use {@link #getQueryFilter()} instead
	 */
	@Deprecated
	public String getWhereClause()
	{
		return whereClause;
	}

	/**
	 *
	 * @return a query filter for the current m_whereClause
	 * @task 03685
	 */
	public <T> IQueryFilter<T> getQueryFilter()
	{
		if (Check.isEmpty(whereClause, true))
		{
			// no whereClause: return a "neutral" filter that does not exclude anything
			return ConstantQueryFilter.of(true);
		}
		return new TypedSqlQueryFilter<T>(whereClause);
	}

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

	public OutputType getJRDesiredOutputType()
	{
		return jrDesiredOutputType;
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

	public static final class ProcessInfoBuilder
	{
		private Properties ctx;
		private boolean createTemporaryCtx = false;

		private int adPInstanceId;
		private transient I_AD_PInstance _adPInstance;
		private int adProcessId;
		private transient I_AD_Process _adProcess;
		private Integer _adClientId;
		private Integer _adUserId;
		private Integer _adRoleId;
		private String title = null;
		private Optional<String> classname;
		private Boolean refreshAllAfterExecution;

		private Integer adTableId;
		private Integer recordId;

		private Optional<String> whereClause = null;

		private int windowNo = Env.WINDOW_None;
		private int tabNo = Env.TAB_None;

		private Language reportLanguage;
		private Boolean printPreview;
		private OutputType jrDesiredOutputType = null;

		private List<ProcessInfoParameter> parameters = null;

		private ProcessInfoBuilder()
		{
			super();
		}

		public ProcessInfo build()
		{
			Properties ctx = getCtx();
			if(createTemporaryCtx)
			{
				ctx = createTemporaryCtx(ctx);
			}
			
			return new ProcessInfo(ctx, this);
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
		
		public ProcessInfoBuilder setCreateTemporaryCtx()
		{
			this.createTemporaryCtx = true;
			return this;
		}

		private Properties createTemporaryCtx(final Properties ctx)
		{
			final Properties processCtx = Env.newTemporaryCtx();

			//
			// AD_Client, AD_Language
			final IClientDAO clientDAO = Services.get(IClientDAO.class);
			final int adClientId = getAD_Client_ID();
			final I_AD_Client processClient = clientDAO.retriveClient(ctx, adClientId);
			Env.setContext(processCtx, Env.CTXNAME_AD_Client_ID, processClient.getAD_Client_ID());
			Env.setContext(processCtx, Env.CTXNAME_AD_Language, processClient.getAD_Language());

			//
			// AD_Org, M_Warehouse
			final int adOrgId = getAD_Org_ID();
			Env.setContext(processCtx, Env.CTXNAME_AD_Org_ID, adOrgId);
			if (adOrgId > 0)
			{
				final I_AD_OrgInfo schedOrg = Services.get(IOrgDAO.class).retrieveOrgInfo(processCtx, adOrgId, ITrx.TRXNAME_None);
				if (schedOrg.getM_Warehouse_ID() > 0)
				{
					Env.setContext(processCtx, Env.CTXNAME_M_Warehouse_ID, schedOrg.getM_Warehouse_ID());
				}
			}

			//
			// AD_User_ID, SalesRep_ID
			final int adUserId = getAD_User_ID();
			Env.setContext(processCtx, Env.CTXNAME_AD_User_ID, adUserId);
			Env.setContext(processCtx, Env.CTXNAME_SalesRep_ID, adUserId);

			//
			// AD_Role
			int adRoleId = getAD_Role_ID();
			if(adRoleId < 0)
			{
				// Use the first user role, which has access to our organization.
				final IUserRolePermissions role = Services.get(IUserRolePermissionsDAO.class)
						.retrieveFirstUserRolesPermissionsForUserWithOrgAccess(processCtx, adUserId, adOrgId)
						.orNull();
				adRoleId = role == null ? Env.CTXVALUE_AD_Role_ID_NONE : role.getAD_Role_ID();
			}
			Env.setContext(processCtx, Env.CTXNAME_AD_Role_ID, adRoleId);

			//
			// Date
			final Timestamp date = SystemTime.asDayTimestamp();
			Env.setContext(processCtx, Env.CTXNAME_Date, date);

			return processCtx;
		}

		private int getAD_Client_ID()
		{
			if (_adClientId != null)
			{
				return _adClientId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getAD_Client_ID();
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
			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getAD_Org_ID();
			}

			return Env.getAD_Org_ID(getCtx());
		}

		private int getAD_User_ID()
		{
			if (_adUserId != null)
			{
				return _adUserId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getAD_User_ID();
			}

			return Env.getAD_User_ID(getCtx());
		}

		public ProcessInfoBuilder setAD_User_ID(final int adUserId)
		{
			this._adUserId = adUserId;
			return this;
		}

		public ProcessInfoBuilder setAD_Role_ID(final int adRoleId)
		{
			this._adRoleId = adRoleId;
			return this;
		}

		private int getAD_Role_ID()
		{
			if (_adRoleId != null)
			{
				return _adRoleId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getAD_Role_ID();
			}

			return Env.getAD_Role_ID(getCtx());
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

		private I_AD_PInstance getAD_PInstanceOrNull()
		{
			final int adPInstanceId = getAD_PInstance_ID();
			if (adPInstanceId <= 0)
			{
				return null;
			}
			if (_adPInstance != null && _adPInstance.getAD_PInstance_ID() != adPInstanceId)
			{
				_adPInstance = null;
			}
			if (_adPInstance == null)
			{
				final Properties ctx = getCtx();
				_adPInstance = InterfaceWrapperHelper.create(ctx, adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
			}
			return _adPInstance;
		}

		public ProcessInfoBuilder setAD_PInstance_ID(final int adPInstanceId)
		{
			this.adPInstanceId = adPInstanceId;
			return this;
		}

		public ProcessInfoBuilder setAD_PInstance(final I_AD_PInstance adPInstance)
		{
			this._adPInstance = adPInstance;
			setAD_PInstance_ID(adPInstance.getAD_PInstance_ID());
			return this;
		}

		private int getAD_PInstance_ID()
		{
			return adPInstanceId;
		}

		private int getAD_Process_ID()
		{
			if (adProcessId <= 0)
			{
				final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
				if (adPInstance != null)
				{
					adProcessId = adPInstance.getAD_Process_ID();
				}
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
			if (adProcess == null)
			{
				return Optional.empty();
			}

			final String reportTemplate = adProcess.getJasperReport();
			if (Check.isEmpty(reportTemplate, true))
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
			this.recordId = 0;
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
			if (adTableId != null)
			{
				return adTableId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getAD_Table_ID();
			}

			return 0;
		}

		private int getRecord_ID()
		{
			if (recordId != null)
			{
				return recordId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getRecord_ID();
			}

			return 0;
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

		public ProcessInfoBuilder setJRDesiredOutputType(OutputType jrDesiredOutputType)
		{
			this.jrDesiredOutputType = jrDesiredOutputType;
			return this;
		}

		private OutputType getJRDesiredOutputType()
		{
			return jrDesiredOutputType;
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

			if (Ini.isClient() && Ini.isPropertyBool(Ini.P_PRINTPREVIEW))
			{
				return true;
			}

			final I_AD_Process adProcess = getAD_ProcessOrNull();
			if (adProcess != null && !adProcess.isDirectPrint())
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

		public ProcessInfoBuilder setWhereClause(final String whereClause)
		{
			this.whereClause = Optional.ofNullable(whereClause);
			return this;
		}

		private String getWhereClause()
		{
			if (whereClause != null)
			{
				return whereClause.orElse(null);
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return adPInstance.getWhereClause();
			}

			return null;
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

		public ProcessInfoBuilder addParameter(final String parameterName, final int parameterValue)
		{
			addParameter(ProcessInfoParameter.of(parameterName, parameterValue));
			return this;
		}

		public ProcessInfoBuilder addParameter(final String parameterName, final String parameterValue)
		{
			addParameter(ProcessInfoParameter.of(parameterName, parameterValue));
			return this;
		}

		public ProcessInfoBuilder addParameter(final String parameterName, final java.util.Date parameterValue)
		{
			addParameter(ProcessInfoParameter.of(parameterName, parameterValue));
			return this;
		}

		public ProcessInfoBuilder addParameter(final String parameterName, final BigDecimal parameterValue)
		{
			addParameter(ProcessInfoParameter.of(parameterName, parameterValue));
			return this;
		}

		public ProcessInfoBuilder addParameter(final String parameterName, final boolean parameterValue)
		{
			addParameter(ProcessInfoParameter.of(parameterName, parameterValue));
			return this;
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
