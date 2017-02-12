package de.metas.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.document.engine.IDocActionBL;

/**
 * Process Information (Value Object)
 *
 * @author authors of earlier versions of this class are: Jorg Janke, victor.perez@e-evolution.com
 * @author metas-dev <dev@metasfresh.com>
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
		sqlStatement = builder.getSQLStatement();
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
		reportApplySecuritySettings = builder.isReportApplySecuritySettings();
		jrDesiredOutputType = builder.getJRDesiredOutputType();

		if (builder.isLoadParametersFromDB())
		{
			this.parameters = null; // to be loaded on demand

			final List<ProcessInfoParameter> parametersOverride = builder.getParametersOrNull();
			this.parametersOverride = parametersOverride == null ? null : ImmutableList.copyOf(parametersOverride);
		}
		else
		{
			final List<ProcessInfoParameter> parameters = builder.getParametersOrNull();
			this.parameters = parameters == null ? null : ImmutableList.copyOf(parameters);

			this.parametersOverride = null;
		}

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
	private transient ProcessClassInfo processClassInfo = null; // lazy
	private final Optional<String> dbProcedureName;
	private final Optional<String> sqlStatement;
	private final int adWorkflowId;
	private final boolean serverProcess;

	/** Process Instance ID */
	private int adPInstanceId;

	/** Batch */
	private final boolean batch = false;

	/** Parameters */
	private List<ProcessInfoParameter> parameters = null; // lazy loaded
	private final List<ProcessInfoParameter> parametersOverride;

	//
	// Reporting related
	private final boolean printPreview;
	private final boolean reportingProcess;
	private final Optional<String> reportTemplate;
	private final Language reportLanguage;
	private final boolean reportApplySecuritySettings;
	private final OutputType jrDesiredOutputType;

	/** Process result */
	private final ProcessExecutionResult result;

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
				.add("reportTemplate", reportTemplate.orElse(null))
				.add("reportLanguage", reportLanguage)
				.add("jrDesiredOutputType", jrDesiredOutputType)
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

	public Optional<String> getSQLStatement()
	{
		return sqlStatement;
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

	public TableRecordReference getRecordRefOrNull()
	{
		if (adTableId <= 0 || recordId < 0)
		{
			return null;
		}
		return TableRecordReference.of(adTableId, recordId);
	}

	public boolean isRecordSet()
	{
		return getTable_ID() > 0 && getRecord_ID() > 0;
	}

	/**
	 * Retrieve underlying model for AD_Table_ID/Record_ID using ITrx#TRXNAME_ThreadInherited.
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
			// NOTE: usually the error message will be displayed directly to user, so we shall have one as friendly as possible
			throw new AdempiereException("@NoSelection@");
		}

		final int recordId = getRecord_ID();
		if (recordId < 0)
		{
			// NOTE: usually the error message will be displayed directly to user, so we shall have one as friendly as possible
			throw new AdempiereException("@NoSelection@");
		}

		final ModelType record = InterfaceWrapperHelper.create(getCtx(), tableName, recordId, modelClass, trxName);
		if (record == null || InterfaceWrapperHelper.isNew(record))
		{
			// NOTE: usually the error message will be displayed directly to user, so we shall have one as friendly as possible
			throw new AdempiereException("@NoSelection@");
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

	private static final List<ProcessInfoParameter> mergeParameters(final List<ProcessInfoParameter> parameters, final List<ProcessInfoParameter> parametersOverride)
	{
		if (parametersOverride == null || parametersOverride.isEmpty())
		{
			return parameters == null ? ImmutableList.of() : parameters;
		}

		if (parameters == null || parameters.isEmpty())
		{
			return parametersOverride == null ? ImmutableList.of() : parametersOverride;
		}

		final Map<String, ProcessInfoParameter> parametersEffective = new HashMap<>();
		parameters.forEach(pip -> parametersEffective.put(pip.getParameterName(), pip));
		parametersOverride.forEach(pip -> parametersEffective.put(pip.getParameterName(), pip));

		return ImmutableList.copyOf(parametersEffective.values());
	}

	/**
	 * Get Process Parameters.
	 *
	 * If they were not already set, they will be loaded from database.
	 *
	 * @return parameters; never returns null
	 */
	public final List<ProcessInfoParameter> getParameter()
	{
		if (parameters == null)
		{
			final List<ProcessInfoParameter> parametersFromDB = Services.get(IADPInstanceDAO.class).retrieveProcessInfoParameters(getCtx(), getAD_PInstance_ID());
			parameters = mergeParameters(parametersFromDB, parametersOverride);
		}
		return parameters;
	}	// getParameter

	public final List<ProcessInfoParameter> getParametersNoLoad()
	{
		return mergeParameters(parameters, parametersOverride);
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
		// default: use a "neutral" filter that does not exclude anything
		final ConstantQueryFilter<T> defaultQueryFilter = ConstantQueryFilter.of(true);
		return getQueryFilterOrElse(defaultQueryFilter);
	}

	/**
	 * @param defaultQueryFilter filter to be returned if this process info does not have a whereClause set.
	 * @return a query filter for the current m_whereClause or if there is none, return <code>defaultQueryFilter</code>
	 */
	public <T> IQueryFilter<T> getQueryFilterOrElse(final IQueryFilter<T> defaultQueryFilter)
	{
		if (Check.isEmpty(whereClause, true))
		{
			return defaultQueryFilter;
		}
		return new TypedSqlQueryFilter<>(whereClause);
	}

	/**
	 * @return language used to reports; could BE <code>null</code>
	 */
	public Language getReportLanguage()
	{
		return this.reportLanguage;
	}

	/**
	 * @return AD_Language used to reports; could BE <code>null</code>
	 */
	public String getReportAD_Language()
	{
		return reportLanguage == null ? null : reportLanguage.getAD_Language();
	}

	public Optional<String> getReportTemplate()
	{
		return reportTemplate;
	}

	public boolean isReportApplySecuritySettings()
	{
		return reportApplySecuritySettings;
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
		if (processClassInfo == null)
		{
			processClassInfo = ProcessClassInfo.ofClassname(getClassName());
		}
		return processClassInfo;
	}

	public static final class ProcessInfoBuilder
	{
		private Properties ctx;
		private boolean createTemporaryCtx = false;
		/**
		 * Window context variables to copy when {@link #createTemporaryCtx}
		 *
		 * NOTE to developer: before changing and mainly removing some context variables from this list,
		 * please do a text search and check the code which is actually relying on this list.
		 */
		public static final List<String> WINDOW_CTXNAMES_TO_COPY = ImmutableList.of("AD_Language", "C_BPartner_ID");
		private static final String SYSCONFIG_JasperLanguage = "de.metas.report.jasper.OrgLanguageForDraftDocuments";

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
		private boolean loadParametersFromDB = false; // backward compatibility

		private ProcessInfoBuilder()
		{
			super();
		}

		public ProcessInfo build()
		{
			Properties ctx = getCtx();
			if (createTemporaryCtx)
			{
				ctx = createTemporaryCtx(ctx);
			}

			return new ProcessInfo(ctx, this);
		}

		public ProcessExecutor.Builder buildAndPrepareExecution()
		{
			final ProcessInfo processInfo = build();
			return ProcessExecutor.builder(processInfo);
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
			if (adRoleId < 0)
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

			//
			// Copy relevant properties from window context
			final int windowNo = getWindowNo();
			if (Env.isRegularWindowNo(windowNo))
			{
				for (final String windowCtxName : WINDOW_CTXNAMES_TO_COPY)
				{
					final boolean onlyWindow = true;
					final String value = Env.getContext(ctx, windowNo, windowCtxName, onlyWindow);
					if (Env.isPropertyValueNull(windowCtxName, value))
					{
						continue;
					}

					Env.setContext(processCtx, windowNo, windowCtxName, value);
				}
			}

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
				_adPInstance = Services.get(IADPInstanceDAO.class).retrieveAD_PInstance(getCtx(), adPInstanceId);
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

		public ProcessInfoBuilder setAD_Process(final org.compiere.model.I_AD_Process adProcess)
		{
			this._adProcess = InterfaceWrapperHelper.create(adProcess, I_AD_Process.class);
			setAD_Process_ID(_adProcess.getAD_Process_ID());
			return this;
		}

		public ProcessInfoBuilder setAD_ProcessByValue(final String processValue)
		{
			final I_AD_Process adProcess = Services.get(IADProcessDAO.class).retriveProcessByValue(getCtx(), processValue);
			setAD_Process(adProcess);
			return this;
		}

		public ProcessInfoBuilder setAD_ProcessByClassname(final String processClassname)
		{
			final int adProcessId = Services.get(IADProcessDAO.class).retriveProcessIdByClassIfUnique(getCtx(), processClassname);
			if (adProcessId <= 0)
			{
				throw new AdempiereException("@NotFound@ @AD_Process_ID@ (@Classname@: " + processClassname + ")");
			}

			setAD_Process_ID(adProcessId);
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

		private Optional<String> getSQLStatement()
		{
			final I_AD_Process process = getAD_ProcessOrNull();
			final String sqlStatement = process == null ? null : process.getSQLStatement();
			if (Check.isEmpty(sqlStatement, true))
			{
				return Optional.empty();
			}
			else
			{
				return Optional.of(sqlStatement.trim());
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

		private boolean isReportApplySecuritySettings()
		{
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			if (adProcess == null)
			{
				return false;
			}
			return adProcess.isApplySecuritySettings();
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

		public ProcessInfoBuilder setRecord(final ITableRecordReference recordRef)
		{
			if (recordRef == null)
			{
				adTableId = null;
				recordId = null;
			}
			else
			{
				adTableId = recordRef.getAD_Table_ID();
				recordId = recordRef.getRecord_ID();
			}

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

		private TableRecordReference getRecordOrNull()
		{
			final int adTableId = getAD_Table_ID();
			if (adTableId <= 0)
			{
				return null;
			}

			final int recordId = getRecord_ID();
			if (recordId <= 0)
			{
				return null;
			}

			return TableRecordReference.of(adTableId, recordId);
		}

		/**
		 * Sets language to be used in reports.
		 *
		 * @param reportLanguage optional report language
		 */
		public ProcessInfoBuilder setReportLanguage(final Language reportLanguage)
		{
			this.reportLanguage = reportLanguage;
			return this;
		}

		/**
		 * Sets language to be used in reports.
		 *
		 * @param adLanguage optional report language
		 */
		public ProcessInfoBuilder setReportLanguage(final String adLanguage)
		{
			this.reportLanguage = Check.isEmpty(adLanguage, true) ? null : Language.getLanguage(adLanguage);
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
			//
			// Configured reporting language
			if (reportLanguage != null)
			{
				return reportLanguage;
			}

			//
			// Load language from AD_PInstance, if any
			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				final String adLanguage = adPInstance.getAD_Language();
				if (!Check.isEmpty(adLanguage, true))
				{
					return Language.getLanguage(adLanguage);
				}
			}

			//
			// Find reporting language
			if (isReportingProcess())
			{
				return findReportingLanguage();
			}

			//
			// Fallback: no reporting language
			return null;
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

		/**
		 * Advises the builder to also try loading the parameters from database.
		 * 
		 * @param loadParametersFromDB
		 *            <ul>
		 *            <li><code>true</code> - the parameters will be loaded from database and the parameters which were added here will be used as overrides.
		 *            <li><code>false</code> - the parameters will be loaded from database only if they were not specified here. If at least one parameter was added to this builder, no parameters will
		 *            be loaded from database but only those added here will be used.
		 *            </ul>
		 */
		public ProcessInfoBuilder setLoadParametersFromDB(boolean loadParametersFromDB)
		{
			this.loadParametersFromDB = loadParametersFromDB;
			return this;
		}

		private boolean isLoadParametersFromDB()
		{
			return loadParametersFromDB || parameters == null;
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

		private ProcessInfoBuilder addParameter(ProcessInfoParameter param)
		{
			if (parameters == null)
			{
				parameters = new ArrayList<>();
			}
			parameters.add(param);

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

		/**
		 * Extracts reporting language.
		 * 
		 * @param pi
		 * @return Language; never returns null
		 */
		private Language findReportingLanguage()
		{
			final Properties ctx = getCtx();
			final int windowNo = getWindowNo();
			final boolean runningFromRegularWindow = Env.isRegularWindowNo(windowNo);

			//
			// Get status of the InOut Document, if any, to have de_CH in case that document in DR or IP (03614)
			if (runningFromRegularWindow)
			{
				final Language lang = extractLanguageFromDraftInOut();
				if (lang != null)
				{
					return lang;
				}
			}

			//
			// Get Language directly from window context, if any (08966)
			if (runningFromRegularWindow)
			{
				// Note: onlyWindow is true, otherwise the login language would be returned if no other language was found
				final String languageString = Env.getContext(ctx, windowNo, "AD_Language", true);
				if (!Env.isPropertyValueNull("AD_Language", languageString))
				{
					return Language.getLanguage(languageString);
				}
			}

			//
			// Get Language from the BPartner set in window context, if any (03040)
			if (runningFromRegularWindow)
			{
				final int bpartnerId = Env.getContextAsInt(ctx, windowNo, "C_BPartner_ID");
				if (bpartnerId > 0)
				{
					final Language lang = Services.get(IBPartnerBL.class).getLanguage(ctx, bpartnerId);
					if (lang != null)
					{
						return lang;
					}
				}
			}

			// task 09740
			// In case the report is not linked to a window but it has C_BPartner_ID as parameter and it is set, take the language of that bpartner
			{
				final List<ProcessInfoParameter> parametersList = getParametersOrNull();
				if (parametersList != null && !parametersList.isEmpty())
				{
					final ProcessParams parameters = new ProcessParams(parametersList);
					final int bpartnerId = parameters.getParameterAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
					if (bpartnerId > 0)
					{
						final Language lang = Services.get(IBPartnerBL.class).getLanguage(ctx, bpartnerId);
						if (lang != null)
						{
							return lang;
						}
					}
				}
			}

			//
			// Get Organization Language if any (03040)
			{
				final Language lang = Services.get(ILanguageBL.class).getOrgLanguage(ctx, getAD_Org_ID());
				if (lang != null)
				{
					return lang;
				}
			}

			//
			// Fallback: get it from client context
			return Env.getLanguage(ctx);
		}

		/**
		 * Method to extract the language from login in case of drafted documents with docType {@link X_C_DocType#DOCBASETYPE_MaterialDelivery}.
		 * <p>
		 * TODO: extract some sort of language-provider-SPI
		 *
		 * @param ctx
		 * @param pi
		 * @return the login language if conditions fulfilled, null otherwise.
		 * @task http://dewiki908/mediawiki/index.php/09614_Support_de_DE_Language_in_Reports_%28101717274915%29
		 */
		private final Language extractLanguageFromDraftInOut()
		{

			final boolean isUseLoginLanguage = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_JasperLanguage, true);

			// in case the sys config is not set, there is no need to continue
			if (!isUseLoginLanguage)
			{
				return null;
			}

			final TableRecordReference recordRef = getRecordOrNull();
			if (recordRef == null)
			{
				return null;
			}

			final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
			final boolean isDocument = docActionBL.isDocumentTable(recordRef.getTableName()); // fails for processes

			// Make sure the process is for a document
			if (!isDocument)
			{
				return null;
			}

			final Properties ctx = getCtx();
			final Object document = recordRef.getModel(PlainContextAware.newWithThreadInheritedTrx(ctx));
			if (document == null)
			{
				return null;
			}
			final I_C_DocType doctype = docActionBL.getDocTypeOrNull(document);

			// make sure the document has a doctype
			if (doctype == null)
			{
				return null; // this shall never happen
			}

			final String docBaseType = doctype.getDocBaseType();

			// make sure the doctype has a base doctype
			if (docBaseType == null)
			{
				return null;
			}

			// Nothing to do if not dealing with a sales inout.
			if (!X_C_DocType.DOCBASETYPE_MaterialDelivery.equals(docBaseType))
			{
				return null;
			}

			// Nothing to do if the document is not a draft or in progress.
			if (!docActionBL.isStatusDraftedOrInProgress(document))
			{
				return null;
			}

			// If all the conditions described above are fulfilled, take the language from the login
			final String languageString = Env.getAD_Language(ctx);

			return Language.getLanguage(languageString);
		}
	} // ProcessInfoBuilder

}   // ProcessInfo
