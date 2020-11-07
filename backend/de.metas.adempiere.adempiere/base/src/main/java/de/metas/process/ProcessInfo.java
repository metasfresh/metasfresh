package de.metas.process;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.report.server.OutputType;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WorkflowId;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * Process Instance informations.
 * <p>
 * NOTE to developers: when changing this class, please keep in mind that it always shall be fully restorable from AD_PInstance_ID.
 *
 * @author authors of earlier versions of this class are: Jorg Janke, victor.perez@e-evolution.com
 * @author metas-dev <dev@metasfresh.com>
 */
@SuppressWarnings("serial")
public final class ProcessInfo implements Serializable
{
	private static final transient Logger logger = LogManager.getLogger(ProcessInfo.class);

	public static ProcessInfoBuilder builder()
	{
		return new ProcessInfoBuilder();
	}

	private ProcessInfo(
			@NonNull final Properties ctx,
			@NonNull final ProcessInfoBuilder builder)
	{
		this.ctx = ctx;

		adProcessId = builder.getAD_Process_ID();
		pinstanceId = builder.getPInstanceId();

		clientId = builder.getAdClientId();
		Check.assumeNotNull(clientId, "Parameter adClientId is not null");

		orgId = builder.getAdOrgId();
		userId = builder.getAdUserId();
		roleId = builder.getRoleId();

		adWindowId = builder.getAdWindowId();

		title = builder.getTitle();

		className = builder.getClassname();
		dbProcedureName = builder.getDBProcedureName();
		sqlStatement = builder.getSQLStatement();
		translateExcelHeaders = builder.isTranslateExcelHeaders();
		adWorkflowId = builder.getWorkflowId();
		serverProcess = builder.isServerProcess();
		invokedByScheduler = builder.isInvokedByScheduler();
		notifyUserAfterExecution = builder.isNotifyUserAfterExecution();

		adTableId = builder.getAD_Table_ID();
		recordId = builder.getRecord_ID();
		whereClause = builder.getWhereClause();
		selectedIncludedRecords = ImmutableSet.copyOf(builder.getSelectedIncludedRecords());

		windowNo = builder.getWindowNo();
		tabNo = builder.getTabNo();

		printPreview = builder.isPrintPreview();
		archiveReportData = builder.isArchiveReportData();

		reportingProcess = builder.isReportingProcess();
		reportLanguage = builder.getReportLanguage();
		reportTemplate = builder.getReportTemplate();
		reportApplySecuritySettings = builder.isReportApplySecuritySettings();
		jrDesiredOutputType = builder.getJRDesiredOutputType();

		type = builder.getType();
		jsonPath = builder.getJsonPath();

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

		result = ProcessExecutionResult.newInstanceForADPInstanceId(pinstanceId);
		result.setRefreshAllAfterExecution(builder.isRefreshAllAfterExecution());
	}

	private final Properties ctx;

	/**
	 * Title of the Process/Report
	 */
	@Getter
	private final String title;
	@Getter
	private final AdProcessId adProcessId;

	/**
	 * Table ID if the Process
	 */
	private final int adTableId;
	/**
	 * Record ID if the Process
	 */
	private final int recordId;
	private final Set<TableRecordReference> selectedIncludedRecords;

	private final String whereClause;
	private final ClientId clientId;
	private final OrgId orgId;
	private final UserId userId;
	private final RoleId roleId;
	@Getter
	private final AdWindowId adWindowId;

	private final int windowNo;
	private final int tabNo;

	/**
	 * Class Name
	 */
	private final Optional<String> className;
	private transient ProcessClassInfo _processClassInfo = null; // lazy

	private final Optional<String> dbProcedureName;
	private final Optional<String> sqlStatement;

	@Getter
	private final boolean translateExcelHeaders;
	private final WorkflowId adWorkflowId;

	@Getter
	private final boolean serverProcess;

	@Getter
	private final boolean invokedByScheduler;

	/**
	 * IF true, then expect the user to be notified, with a link to the AD_Pssntance_ID
	 */
	@Getter
	private final boolean notifyUserAfterExecution;

	/**
	 * Process Instance ID
	 */
	@Getter
	private PInstanceId pinstanceId;

	private Boolean async = null;

	/**
	 * Parameters
	 */
	private ImmutableList<ProcessInfoParameter> parameters = null; // lazy loaded
	private final ImmutableList<ProcessInfoParameter> parametersOverride;

	//
	// Reporting related

	/**
	 * Is print preview instead of direct print ? Only relevant if this is a reporting process
	 */
	@Getter
	private final boolean printPreview;
	@Getter
	private final boolean archiveReportData;
	@Getter
	private final boolean reportingProcess;
	@Getter
	private final Optional<String> reportTemplate;
	private final Language reportLanguage;
	@Getter
	private final boolean reportApplySecuritySettings;
	private final OutputType jrDesiredOutputType;
	@Getter
	@NonNull
	private final ProcessType type;
	@Getter
	private final Optional<String> jsonPath;

	/**
	 * Process result
	 */
	@Getter
	private final ProcessExecutionResult result;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("title", title)
				.add("AD_Process_ID", adProcessId)
				.add("pinstanceId", pinstanceId)
				.add("AD_Table_ID", adTableId)
				.add("Record_ID", recordId)
				.add("Classname", className.orElse(null))
				.add("reportTemplate", reportTemplate.orElse(null))
				.add("reportLanguage", reportLanguage)
				.add("jrDesiredOutputType", jrDesiredOutputType)
				.add("JSONPath", jsonPath)
				.add("type", type)
				.toString();
	}

	public Properties getCtx()
	{
		return Env.coalesce(ctx);
	}

	/**
	 * Advise if we want business logic to be executed asynchronously further down the road, or not.
	 *
	 * @return boolean
	 */
	public boolean isAsync()
	{
		return async;
	}    // isBatch

	/**
	 * Shall only be called once. Intended to be called by {@link ProcessExecutor} only.
	 */
	/* package */ void setAsync(final boolean async)
	{
		Preconditions.checkState(this.async == null, "The async property shall be set only once");
		this.async = async;
	}

	public void setPInstanceId(final PInstanceId pinstanceId)
	{
		this.pinstanceId = pinstanceId;
		result.setPInstanceId(pinstanceId);
	}

	public String getClassName()
	{
		return className.orElse(null);
	}

	/**
	 * Creates a new instance of {@link #getClassName()}.
	 * If the classname is empty, null will be returned.
	 *
	 * @return new instance or null
	 */
	public JavaProcess newProcessClassInstanceOrNull()
	{
		final String classname = getClassName();
		if (Check.isEmpty(classname, true))
		{
			return null;
		}

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = getClass().getClassLoader();
		}

		try
		{
			final Class<?> processClass = classLoader.loadClass(classname);
			final JavaProcess processClassInstance = (JavaProcess)processClass.newInstance();
			if (processClassInstance instanceof JavaProcess)
			{
				processClassInstance.init(this);
			}

			return processClassInstance;
		}
		catch (final Throwable e)
		{
			if (isServerProcess() && Ini.isSwingClient())
			{
				// NOTE: in case of server process, it might be that the class is not present, which could be fine
				logger.debug("Failed instantiating class '{}'. Skipped.", classname, e);
			}
			else
			{
				logger.warn("Failed instantiating class '{}'. Skipped.", classname, e);
			}
			return null;
		}
	}

	public Optional<String> getDBProcedureName()
	{
		return dbProcedureName;
	}

	public Optional<String> getSQLStatement()
	{
		return sqlStatement;
	}

	public WorkflowId getWorkflowId()
	{
		return adWorkflowId;
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
	 * @param trxName    transaction to be used when loading the record
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
	 * @return record or {@link Optional#empty()} if record does not exist or it does not match given <code>modelClass</code>
	 */
	public <ModelType> Optional<ModelType> getRecordIfApplies(final Class<ModelType> modelClass, final String trxName)
	{
		Check.assumeNotNull(modelClass, "modelClass not null");
		final String tableName = getTableNameOrNull();
		if (Check.isEmpty(tableName, true))
		{
			return Optional.empty();
		}
		final String modelTableName = InterfaceWrapperHelper.getTableName(modelClass);
		if (!Objects.equals(tableName, modelTableName))
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

	public ClientId getClientId()
	{
		return clientId;
	}

	public int getAD_Client_ID()
	{
		return getClientId().getRepoId();
	}

	public UserId getUserId()
	{
		return userId;
	}

	public int getAD_User_ID()
	{
		return UserId.toRepoId(getUserId());
	}

	public RoleId getRoleId()
	{
		return roleId;
	}

	public int getAD_Window_ID()
	{
		return AdWindowId.toRepoId(getAdWindowId());
	}

	private static ImmutableList<ProcessInfoParameter> mergeParameters(final List<ProcessInfoParameter> parameters, final List<ProcessInfoParameter> parametersOverride)
	{
		if (parametersOverride == null || parametersOverride.isEmpty())
		{
			return parameters == null ? ImmutableList.of() : ImmutableList.copyOf(parameters);
		}

		if (parameters == null || parameters.isEmpty())
		{
			return parametersOverride == null ? ImmutableList.of() : ImmutableList.copyOf(parametersOverride);
		}

		final Map<String, ProcessInfoParameter> parametersEffective = new HashMap<>();
		parameters.forEach(pip -> parametersEffective.put(pip.getParameterName(), pip));
		parametersOverride.forEach(pip -> parametersEffective.put(pip.getParameterName(), pip));

		return ImmutableList.copyOf(parametersEffective.values());
	}

	/**
	 * Get Process Parameters.
	 * <p>
	 * If they were not already set, they will be loaded from database.
	 *
	 * @return parameters; never returns null
	 */
	public List<ProcessInfoParameter> getParameter()
	{
		if (parameters == null)
		{
			final List<ProcessInfoParameter> parametersFromDB = Services.get(IADPInstanceDAO.class).retrieveProcessInfoParameters(getPinstanceId());
			parameters = mergeParameters(parametersFromDB, parametersOverride);
		}
		return parameters;
	}    // getParameter

	public List<ProcessInfoParameter> getParametersNoLoad()
	{
		return mergeParameters(parameters, parametersOverride);
	}

	/**
	 * Get Process Parameters as IParams instance.
	 */
	public IRangeAwareParams getParameterAsIParams()
	{
		return new ProcessParams(getParameter());
	}

	public OrgId getOrgId()
	{
		return orgId;
	}

	public int getAD_Org_ID()
	{
		return OrgId.toRepoId(getOrgId());
	}

	/**
	 * @return Parent's WindowNo or -1
	 * @deprecated this only work in the context of the swing client.
	 */
	@Deprecated
	public int getWindowNo()
	{
		return windowNo;
	}

	/**
	 * @return the current WindowNo or -1
	 * @deprecated this only works in the context of the swing client.
	 */
	@Deprecated
	public int getTabNo()
	{
		return this.tabNo;
	}

	/**
	 * @return the whereClause <b>but without org restrictions</b>
	 * @deprecated please use on of getQueryFilter methods instead
	 */
	@Deprecated
	public String getWhereClause()
	{
		return whereClause;
	}

	/**
	 * IMPORTANT: in most cases, {@link #getQueryFilterOrElseFalse()} is what you probably want to use.
	 *
	 * @return a query filter for the current {@code whereClause}, or an "all inclusive" {@link ConstantQueryFilter} if the {@code whereClause} is empty.<br>
	 * gh #1348: in both cases, the filter also contains a client and org restriction that is according to the logged-on user's role as returned by {@link Env#getUserRolePermissions(Properties)}.
	 * @task 03685
	 * @see JavaProcess#retrieveSelectedRecordsQueryBuilder(Class)
	 */
	public <T> IQueryFilter<T> getQueryFilterOrElseTrue()
	{
		// default: use a "neutral" filter that does not exclude anything
		final ConstantQueryFilter<T> defaultQueryFilter = ConstantQueryFilter.of(true);
		return getQueryFilterOrElse(defaultQueryFilter);
	}

	/**
	 * Like {@link #getQueryFilterOrElseTrue()} but returns an "all exclusive" query filter if the {@code whereClause} is empty.
	 */
	public <T> IQueryFilter<T> getQueryFilterOrElseFalse()
	{
		final ConstantQueryFilter<T> defaultQueryFilter = ConstantQueryFilter.of(false);
		return getQueryFilterOrElse(defaultQueryFilter);
	}

	/**
	 * @param defaultQueryFilter filter to be returned if this process info does not have a whereClause set.
	 * @return a query filter for the current m_whereClause or if there is none, return <code>defaultQueryFilter</code>
	 */
	public <T> IQueryFilter<T> getQueryFilterOrElse(final IQueryFilter<T> defaultQueryFilter)
	{
		final IQueryFilter<T> whereFilter;
		if (Check.isEmpty(whereClause, true))
		{
			whereFilter = defaultQueryFilter;

			// In case te default filter is null, return null
			if (whereFilter == null)
			{
				return null;
			}
		}
		else
		{
			whereFilter = TypedSqlQueryFilter.of(whereClause);
		}

		// https://github.com/metasfresh/metasfresh/issues/1348
		// also restrict to the client(s) and org(s) the user shall see with its current role.
		final IUserRolePermissions role = Env.getUserRolePermissions(this.ctx);

		// Note that getTableNameOrNull() might as well return null, plus the method does not need the table name
		final TypedSqlQueryFilter<T> orgFilter = TypedSqlQueryFilter.of(role.getOrgWhere(null, Access.WRITE));
		final TypedSqlQueryFilter<T> clientFilter = TypedSqlQueryFilter.of(role.getClientWhere(null, null, Access.WRITE));

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// Note that getTableNameOrNull() might as well return null, plus the method does not need the table name in this case
		final ICompositeQueryFilter<T> compositeFilter = queryBL.createCompositeQueryFilter((String)null);

		compositeFilter.addFilter(whereFilter)
				.addFilter(clientFilter)
				.addFilter(orgFilter);

		return compositeFilter;
	}

	/**
	 * @return selected included rows of current single selected document
	 */
	public Set<TableRecordReference> getSelectedIncludedRecords()
	{
		return selectedIncludedRecords;
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

	public OutputType getJRDesiredOutputType()
	{
		return jrDesiredOutputType;
	}

	/**
	 * @return the {@link ProcessClassInfo} for this instance's <code>className</code> (see {@link #getClassName()}) or {@link ProcessClassInfo#NULL} if this instance has no classname or the
	 * instance's classname's class can't be loaded.
	 */
	public ProcessClassInfo getProcessClassInfo()
	{
		if (_processClassInfo == null)
		{
			_processClassInfo = ProcessClassInfo.ofClassname(getClassName());
		}
		return _processClassInfo;
	}

	public static final class ProcessInfoBuilder
	{
		private Properties ctx;
		private boolean createTemporaryCtx = false;
		/**
		 * Window context variables to copy when {@link #createTemporaryCtx}
		 * <p>
		 * NOTE to developer: before changing and mainly removing some context variables from this list,
		 * please do a text search and check the code which is actually relying on this list.
		 */
		public static final List<String> WINDOW_CTXNAMES_TO_COPY = ImmutableList.of("AD_Language", "C_BPartner_ID");
		private static final String SYSCONFIG_UseLoginLanguageForDraftDocuments = "de.metas.report.jasper.OrgLanguageForDraftDocuments";

		private PInstanceId pInstanceId;
		private transient I_AD_PInstance _adPInstance;
		private AdProcessId adProcessId;
		private transient I_AD_Process _adProcess;
		private ClientId _adClientId;
		private UserId _adUserId;
		private RoleId _adRoleId;
		private AdWindowId _adWindowId = null;
		private String title = null;
		private Optional<String> classname;
		private Boolean refreshAllAfterExecution;

		private Integer adTableId;
		private Integer recordId;

		private Set<TableRecordReference> selectedIncludedRecords;

		private Optional<String> whereClause = null;

		private int windowNo = Env.WINDOW_None;
		private int tabNo = Env.TAB_None;

		private Language reportLanguage;
		private Boolean printPreview;
		private Boolean archiveReportData;

		private OutputType jrDesiredOutputType = null;

		private List<ProcessInfoParameter> parameters = null;
		private boolean loadParametersFromDB = false; // backward compatibility

		@Getter
		private boolean invokedByScheduler = false;

		private Boolean notifyUserAfterExecution;

		private ProcessInfoBuilder()
		{
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
			final MFSession mfSession = Services.get(ISessionBL.class).getCurrentSession(ctx);
			if (mfSession != null)
			{
				mfSession.updateContext(processCtx);
			}

			//
			// AD_Client, AD_Language
			final IClientDAO clientDAO = Services.get(IClientDAO.class);
			final ClientId adClientId = getAdClientId();
			Env.setClientId(processCtx, adClientId);

			final String contextLanguage = Env.getAD_Language(ctx);
			final I_AD_Client processClient = clientDAO.getById(adClientId);
			final String languageToUse = contextLanguage != null ? contextLanguage : processClient.getAD_Language();
			Env.setContext(processCtx, Env.CTXNAME_AD_Language, languageToUse);

			//
			// AD_Org, M_Warehouse
			final OrgId adOrgId = getAdOrgId();
			Env.setOrgId(processCtx, adOrgId);
			if (!adOrgId.isAny())
			{
				final OrgInfo schedOrg = Services.get(IOrgDAO.class).getOrgInfoById(adOrgId);
				if (schedOrg.getWarehouseId() != null)
				{
					Env.setContext(processCtx, Env.CTXNAME_M_Warehouse_ID, schedOrg.getWarehouseId().getRepoId());
				}
			}

			//
			// AD_User_ID, SalesRep_ID
			final UserId adUserId = getAdUserId();
			Env.setLoggedUserId(processCtx, adUserId);
			Env.setSalesRepId(processCtx, adUserId);

			//
			// AD_Role
			RoleId adRoleId = getRoleId();
			if (adRoleId == null)
			{
				// Use the first user role, which has access to our organization.
				final IUserRolePermissions role = Services.get(IUserRolePermissionsDAO.class)
						.retrieveFirstUserRolesPermissionsForUserWithOrgAccess(
								adClientId,
								adOrgId,
								adUserId,
								Env.getLocalDate(processCtx))
						.orNull();
				adRoleId = role == null ? null : role.getRoleId();
			}
			Env.setContext(processCtx, Env.CTXNAME_AD_Role_ID, RoleId.toRepoId(adRoleId, Env.CTXVALUE_AD_Role_ID_NONE));

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

		private ClientId getAdClientId()
		{
			if (_adClientId != null)
			{
				return _adClientId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return ClientId.ofRepoId(adPInstance.getAD_Client_ID());
			}

			return Env.getClientId(getCtx());
		}

		public ProcessInfoBuilder setAD_Client_ID(final int adClientId)
		{
			return setClientId(ClientId.ofRepoIdOrNull(adClientId));
		}

		public ProcessInfoBuilder setClientId(final ClientId adClientId)
		{
			this._adClientId = adClientId;
			return this;
		}

		private OrgId getAdOrgId()
		{
			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return OrgId.ofRepoId(adPInstance.getAD_Org_ID());
			}

			return Env.getOrgId(getCtx());
		}

		private UserId getAdUserId()
		{
			if (_adUserId != null)
			{
				return _adUserId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return UserId.ofRepoId(adPInstance.getAD_User_ID());
			}

			return Env.getLoggedUserId(getCtx());
		}

		public ProcessInfoBuilder setAD_User_ID(final int adUserId)
		{
			return setUserId(UserId.ofRepoIdOrNull(adUserId));
		}

		public ProcessInfoBuilder setUserId(final UserId adUserId)
		{
			this._adUserId = adUserId;
			return this;
		}

		public ProcessInfoBuilder setAD_Role_ID(final int adRoleId)
		{
			return setRoleId(RoleId.ofRepoIdOrNull(adRoleId));
		}

		public ProcessInfoBuilder setRoleId(final RoleId adRoleId)
		{
			this._adRoleId = adRoleId;
			return this;
		}

		private RoleId getRoleId()
		{
			if (_adRoleId != null)
			{
				return _adRoleId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null)
			{
				return RoleId.ofRepoId(adPInstance.getAD_Role_ID());
			}

			return Env.getLoggedRoleId(getCtx());
		}

		public ProcessInfoBuilder setAdWindowId(@Nullable final AdWindowId adWindowId)
		{
			_adWindowId = adWindowId;
			return this;
		}

		private AdWindowId getAdWindowId()
		{
			if (_adWindowId != null)
			{
				return _adWindowId;
			}

			final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
			if (adPInstance != null && adPInstance.getAD_Window_ID() > 0)
			{
				return AdWindowId.ofRepoId(adPInstance.getAD_Window_ID());
			}

			return null;
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
			final PInstanceId adPInstanceId = getPInstanceId();
			if (adPInstanceId == null)
			{
				return null;
			}
			if (_adPInstance != null && _adPInstance.getAD_PInstance_ID() != adPInstanceId.getRepoId())
			{
				_adPInstance = null;
			}
			if (_adPInstance == null)
			{
				_adPInstance = Services.get(IADPInstanceDAO.class).getById(adPInstanceId);
			}
			return _adPInstance;
		}

		public ProcessInfoBuilder setPInstanceId(final PInstanceId pinstanceId)
		{
			this.pInstanceId = pinstanceId;
			return this;
		}

		public ProcessInfoBuilder setAD_PInstance(final I_AD_PInstance adPInstance)
		{
			this._adPInstance = adPInstance;
			setPInstanceId(PInstanceId.ofRepoId(adPInstance.getAD_PInstance_ID()));
			return this;
		}

		private PInstanceId getPInstanceId()
		{
			return pInstanceId;
		}

		private AdProcessId getAD_Process_ID()
		{
			if (adProcessId == null)
			{
				final I_AD_PInstance adPInstance = getAD_PInstanceOrNull();
				if (adPInstance != null)
				{
					adProcessId = AdProcessId.ofRepoId(adPInstance.getAD_Process_ID());
				}
			}
			return adProcessId;
		}

		public ProcessInfoBuilder setAD_Process(final org.compiere.model.I_AD_Process adProcess)
		{
			this._adProcess = InterfaceWrapperHelper.create(adProcess, I_AD_Process.class);

			setAD_Process_ID(_adProcess.getAD_Process_ID());
			setNotifyUserAfterExecution(adProcess.isNotifyUserAfterExecution());
			return this;
		}

		public ProcessInfoBuilder setAD_ProcessByValue(final String processValue)
		{
			final I_AD_Process adProcess = Services.get(IADProcessDAO.class).retrieveProcessByValue(getCtx(), processValue);
			setAD_Process(adProcess);
			return this;
		}

		public ProcessInfoBuilder setAD_ProcessByClassname(final String processClassname)
		{
			final AdProcessId adProcessId = Services.get(IADProcessDAO.class).retrieveProcessIdByClassIfUnique(processClassname);
			if (adProcessId == null)
			{
				throw new AdempiereException("@NotFound@ @AD_Process_ID@ (@Classname@: " + processClassname + ")");
			}

			setAD_Process_ID(adProcessId);
			return this;
		}

		private I_AD_Process getAD_ProcessOrNull()
		{
			final AdProcessId processId = getAD_Process_ID();
			if (processId == null)
			{
				return null;
			}
			if (_adProcess != null && _adProcess.getAD_Process_ID() != processId.getRepoId())
			{
				_adProcess = null;
			}
			if (_adProcess == null)
			{
				_adProcess = Services.get(IADProcessDAO.class).getById(processId);
			}
			return _adProcess;
		}

		public ProcessInfoBuilder setAD_Process_ID(final int adProcessId)
		{
			return setAD_Process_ID(AdProcessId.ofRepoIdOrNull(adProcessId));
		}

		public ProcessInfoBuilder setAD_Process_ID(final AdProcessId adProcessId)
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

		private boolean isTranslateExcelHeaders()
		{
			final I_AD_Process process = getAD_ProcessOrNull();
			return process != null ? process.isTranslateExcelHeaders() : false;
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

		private WorkflowId getWorkflowId()
		{
			final I_AD_Process adProcess = getAD_ProcessOrNull();
			return adProcess != null ? WorkflowId.ofRepoIdOrNull(adProcess.getAD_Workflow_ID()) : null;
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

		public ProcessInfoBuilder setSelectedIncludedRecords(final Set<TableRecordReference> selectedIncludedRecords)
		{
			this.selectedIncludedRecords = selectedIncludedRecords != null ? selectedIncludedRecords : ImmutableSet.of();
			return this;
		}

		private Set<TableRecordReference> getSelectedIncludedRecords()
		{
			if (selectedIncludedRecords != null)
			{
				return selectedIncludedRecords;
			}

			final PInstanceId pinstanceId = getPInstanceId();
			if (pinstanceId != null)
			{
				return Services.get(IADPInstanceDAO.class).retrieveSelectedIncludedRecords(pinstanceId);
			}

			return ImmutableSet.of();
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

		public ProcessType getType()
		{
			final I_AD_Process process = getAD_ProcessOrNull();
			return process == null ? null : ProcessType.ofCode(process.getType());
		}

		public Optional<String> getJsonPath()
		{
			final I_AD_Process process = getAD_ProcessOrNull();
			final String JSONPath = process == null ? null : process.getJSONPath();
			if (Check.isEmpty(JSONPath, true))
			{
				return Optional.empty();
			}
			else
			{
				return Optional.of(JSONPath.trim());
			}
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

		/**
		 * Only really matters when forking with the swing client.
		 * {@code true} means that the system shall just if the report data shall just be returned.
		 * If not set, then the system will look at {@link Ini#P_PRINTPREVIEW} and {@code AD_Process.IsDirectPrint}.
		 */
		public ProcessInfoBuilder setPrintPreview(final Boolean printPreview)
		{
			this.printPreview = printPreview;
			return this;
		}

		/**
		 * Relevant for report processes.
		 * {@code true} means that the system shall just if the report data shall just be returned
		 */
		private boolean isPrintPreview()
		{
			if (this.printPreview != null)
			{
				return this.printPreview;
			}

			if (Ini.isSwingClient() && Ini.isPropertyBool(Ini.P_PRINTPREVIEW))
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

		/**
		 * Set to {@code false} if you only want to invoke the report engine and get the resulting PDF data, without having it archived.
		 * <p>
		 * Important: doesn't matter if print preview is {@code true}, because in that case, the report result is never archived.
		 */
		public ProcessInfoBuilder setArchiveReportData(final Boolean archiveReportData)
		{
			this.archiveReportData = archiveReportData;
			return this;
		}

		private boolean isArchiveReportData()
		{
			return CoalesceUtil.coalesce(archiveReportData, Boolean.TRUE);
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

		public ProcessInfoBuilder setInvokedByScheduler(final boolean invokedByScheduler)
		{
			this.invokedByScheduler = invokedByScheduler;
			if (invokedByScheduler && notifyUserAfterExecution)
			{
				logger.debug("invokedByScheduler is set to true; -> set notifyUserAfterExecution to false because the scheduler has its own notification settings");
				this.notifyUserAfterExecution = false;
			}
			return this;
		}

		public ProcessInfoBuilder setNotifyUserAfterExecution(final boolean notifyUserAfterExecution)
		{
			if (invokedByScheduler && notifyUserAfterExecution)
			{
				logger.debug("invokedByScheduler is set to true; -> set notifyUserAfterExecution to false because the scheduler has its own notification settings");
				this.notifyUserAfterExecution = false;
				return this;
			}
			this.notifyUserAfterExecution = notifyUserAfterExecution;
			return this;
		}

		public boolean isNotifyUserAfterExecution()
		{
			if (notifyUserAfterExecution == null)
			{
				if (invokedByScheduler)
				{
					logger.debug("invokedByScheduler=true; -> set notifyUserAfterExecution to false because the scheduler has its own notification settings");
					this.notifyUserAfterExecution = false;
				}
				else
				{
					final I_AD_Process processRecord = getAD_ProcessOrNull();
					if (processRecord != null)
					{
						this.notifyUserAfterExecution = processRecord.isNotifyUserAfterExecution();
						logger.debug("invokedByScheduler=false; -> set notifyUserAfterExecution={} from AD_Process_ID={}", notifyUserAfterExecution, processRecord.getAD_Process_ID());
					}
					else
					{
						logger.debug("invokedByScheduler=false and AD_Process=null; -> set notifyUserAfterExecution=false");
						this.notifyUserAfterExecution = false;
					}
				}
			}
			return notifyUserAfterExecution;
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
		 * @param loadParametersFromDB <ul>
		 *                                        <li><code>true</code> - the parameters will be loaded from database and the parameters which were added here will be used as overrides.
		 *                                        <li><code>false</code> - the parameters will be loaded from database only if they were not specified here. If at least one parameter was added to this builder, no parameters will
		 *                                        be loaded from database but only those added here will be used.
		 *                                        </ul>
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
		 * @return Language; never returns null
		 */
		private Language findReportingLanguage()
		{
			final Properties ctx = getCtx();
			final TableRecordReference recordRef = getRecordOrNull(); // NOTE: loaded here because recordRef is caching the model so we will have to load it once

			//
			// Get status of the InOut Document, if any, to have de_CH in case that document in DR or IP (03614)
			{
				final Language lang = extractLanguageFromDraftInOut(ctx, recordRef);
				if (lang != null)
				{
					return lang;
				}
			}

			//
			// Extract Language directly from window context, if any (08966)
			{
				final Language lang = extractLanguageFromWindowContext(ctx, getWindowNo());
				if (lang != null)
				{
					return lang;
				}
			}

			//
			// Extract Language directly from record, if any
			{
				final Language lang = extractLanguageFromRecordRef(ctx, recordRef);
				if (lang != null)
				{
					return lang;
				}
			}

			//
			// Extract Language from process parameters
			// In case the report is not linked to a window but it has C_BPartner_ID as parameter and it is set, take the language of that bpartner (task 09740)
			{
				final List<ProcessInfoParameter> parametersList = getParametersOrNull();
				if (parametersList != null && !parametersList.isEmpty())
				{
					final ProcessParams parameters = new ProcessParams(parametersList);
					final int bpartnerId = parameters.getParameterAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID, -1);
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
				final Language lang = Services.get(ILanguageBL.class).getOrgLanguage(ctx, getAdOrgId().getRepoId());
				if (lang != null)
				{
					return lang;
				}
			}

			//
			// Fallback: get it from client context
			return Env.getLanguage(ctx);
		}

		private static Language extractLanguageFromWindowContext(final Properties ctx, final int windowNo)
		{
			if (!Env.isRegularWindowNo(windowNo))
			{
				return null;
			}

			//
			// Get Language directly from window context, if any (08966)
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

			return null;
		}

		private static Language extractLanguageFromRecordRef(final Properties ctx, @Nullable final TableRecordReference recordRef)
		{
			Check.assumeNotNull(ctx, "Parameter ctx is not null");

			if (recordRef == null)
			{
				return null;
			}

			final Object record = recordRef.getModel(PlainContextAware.newWithThreadInheritedTrx(ctx));
			if (record == null)
			{
				return null;
			}

			//
			// Get Language directly from AD_Language field, if any
			{
				// Note: onlyWindow is true, otherwise the login language would be returned if no other language was found
				final String languageString = InterfaceWrapperHelper.getValueOrNull(record, "AD_Language");
				if (!Check.isEmpty(languageString, true))
				{
					return Language.getLanguage(languageString);
				}
			}

			//
			// Get Language from the C_BPartner_ID, if any
			{
				final Integer bpartnerId = InterfaceWrapperHelper.getValueOrNull(record, "C_BPartner_ID");
				if (bpartnerId != null && bpartnerId > 0)
				{
					final Language lang = Services.get(IBPartnerBL.class).getLanguage(ctx, bpartnerId);
					if (lang != null)
					{
						return lang;
					}
				}
			}

			return null;
		}

		/**
		 * Method to extract the language from login in case of drafted documents with docType {@link X_C_DocType#DOCBASETYPE_MaterialDelivery}.
		 * <p>
		 * TODO: extract some sort of language-provider-SPI
		 *
		 * @return the login language if conditions fulfilled, null otherwise.
		 * @task http://dewiki908/mediawiki/index.php/09614_Support_de_DE_Language_in_Reports_%28101717274915%29
		 */
		private static Language extractLanguageFromDraftInOut(@NonNull final Properties ctx, @Nullable final TableRecordReference recordRef)
		{
			final boolean isUseLoginLanguage = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_UseLoginLanguageForDraftDocuments, true);

			// in case the sys config is not set, there is no need to continue
			if (!isUseLoginLanguage)
			{
				return null;
			}

			if (recordRef == null)
			{
				return null;
			}

			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
			final boolean isDocument = docActionBL.isDocumentTable(recordRef.getTableName()); // fails for processes

			// Make sure the process is for a document
			if (!isDocument)
			{
				return null;
			}

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
			if (!docActionBL.issDocumentDraftedOrInProgress(document))
			{
				return null;
			}

			// If all the conditions described above are fulfilled, take the language from the login
			final String languageString = Env.getAD_Language(ctx);

			return Language.getLanguage(languageString);
		}
	} // ProcessInfoBuilder
}   // ProcessInfo
