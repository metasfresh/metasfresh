package de.metas.process;

import com.google.common.base.Stopwatch;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.organization.OrgId;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.DocumentReportRequest;
import de.metas.report.DocumentReportResult;
import de.metas.report.DocumentReportService;
import de.metas.script.IADRuleDAO;
import de.metas.script.ScriptEngineFactory;
import de.metas.script.ScriptExecutor;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.execution.WorkflowExecutionResult;
import de.metas.workflow.execution.WorkflowExecutor;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.X_AD_Rule;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Process executor: executes a process (sync or async) which was defined by given {@link ProcessInfo}.
 *
 * @author authors of earlier versions of this class are: Jorg Janke, Low Heng Sin, Teo Sarca
 * @author metas-dev dev@metasfresh.com>
 */
public final class ProcessExecutor
{
	public static Builder builder(final ProcessInfo processInfo)
	{
		return new Builder(processInfo);
	}

	public static OrgId getCurrentOrgId()
	{
		final OrgId orgId = s_currentOrg_ID.get();
		return orgId != null ? orgId : OrgId.ANY;
	}

	public static AdProcessId getCurrentProcessIdOrNull()
	{
		return s_currentProcess_ID.get();
	}

	private static final AdMessageKey MSG_DONE = AdMessageKey.of("AD_Process_Execution_Done");

	// Thread locals
	private static final ThreadLocal<AdProcessId> s_currentProcess_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro
	private static final ThreadLocal<OrgId> s_currentOrg_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro

	// services
	private static final Logger logger = LogManager.getLogger(ProcessExecutor.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final transient INotificationBL notificationBL = Services.get(INotificationBL.class);

	private final IProcessExecutionListener listener;
	private final ProcessInfo pi;
	private final boolean switchContextWhenRunning;
	private final boolean onErrorThrowException;

	private Thread m_thread; // metas

	private ProcessExecutor(@NonNull final Builder builder)
	{
		pi = builder.getProcessInfo();

		// gh #2092 verify that we have an AD_Role_ID; otherwise, the assertPermissions() call we are going to do will fail
		Check.errorIf(pi.getRoleId() == null, "Process info has AD_Role_ID={}; builder={}", pi.getRoleId(), builder);

		listener = builder.getListener();
		switchContextWhenRunning = builder.switchContextWhenRunning;
		onErrorThrowException = builder.onErrorThrowException;
	}

	private String buildThreadName()
	{
		return pi.getTitle() + "-" + PInstanceId.toRepoIdOr(pi.getPinstanceId(), 0);
	}

	/**
	 * Run this process asynchronously
	 */
	private void executeAsync()
	{
		Check.assumeNull(m_thread, "not already started");

		final Thread thread = new Thread(this::executeSync);
		thread.setName(buildThreadName());

		thread.start();

		m_thread = thread;
	}

	/**
	 * Run this process synchronously
	 */
	private void executeSync()
	{
		//
		// Case: our process has some parts that requires to be executed out of transaction
		// but we are currently running in a transaction.
		// => spawn a new thread, run the process there and wait for the thread to finish
		if (pi.getProcessClassInfo().isRunOutOfTransaction()
				&& trxManager.hasThreadInheritedTrx())
		{
			final Thread thread = new Thread(this::executeNow);
			thread.setName(buildThreadName());
			logger.debug("Starting thread with name={}", thread.getName());
			thread.start();

			try
			{
				thread.join();
				logger.debug("Join returned for thread with name={}", thread.getName());
			}
			catch (final InterruptedException ex)
			{
				throw AdempiereException.wrapIfNeeded(ex);
			}

			//
			// Propagate the error if asked
			if (onErrorThrowException)
			{
				pi.getResult().propagateErrorIfAny();
			}
		}
		//
		// Case: standard case
		// => run the process now
		else
		{
			executeNow();
		}
	}

	private void executeNow()
	{
		logger.debug("running: {}", pi);

		//
		// set up the processExecutor that we will run further down
		final TrxRunnableAdapter processExecutor = new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				//
				// Execute the process (workflow/java/db process)
				if (pi.getWorkflowId() != null)
				{
					startWorkflow();
					return;
				}
				else if (!Check.isEmpty(pi.getClassName(), true))
				{
					startJavaOrScriptProcess();
				}
				else if (pi.getDBProcedureName().isPresent())
				{
					startDBProcess();
				}

				//
				// Prepare report
				final boolean isReport = pi.isReportingProcess();
				final boolean hasProcessClass = !Check.isBlank(pi.getClassName());
				if (isReport && hasProcessClass)
				{
					// nothing to do, the Jasper process class implementation is responsible for triggering the report preview if any
					//noinspection UnnecessaryReturnStatement
					return;
				}
				else if (isReport)
				{
					// TODO: check if this case is still valid. I think we always have a process classname set
					final DocumentReportService documentReportService = SpringContextHolder.instance.getBean(DocumentReportService.class);
					final DocumentReportResult reportResult = documentReportService.createReport(toDocumentReportRequest(pi));
					pi.getResult().setSummary("Report");
					pi.getResult().setReportData(reportResult.getReportResultData());
				}
			}

			@Override
			public boolean doCatch(final Throwable e)
			{
				logger.warn("Got error", e);
				final ProcessExecutionResult result = pi.getResult();
				result.markAsError(e);
				return ROLLBACK;
			}
		};

		//
		// now run the process executor
		final AdProcessId previousProcessId = s_currentProcess_ID.get();
		final OrgId previousOrgId = s_currentOrg_ID.get();
		Stopwatch duration = null;
		try (final IAutoCloseable ignored = switchContextIfNeeded();
				final IAutoCloseable ignored1 = ProcessMDC.putProcessAndInstanceId(pi.getAdProcessId(), pi.getPinstanceId()))
		{
			s_currentProcess_ID.set(pi.getAdProcessId());
			s_currentOrg_ID.set(pi.getOrgId());

			//
			// Check permissions
			assertPermissions();

			// Lock
			lock(true);

			duration = Stopwatch.createStarted();

			//
			// Execute
			if (pi.getProcessClassInfo().isRunOutOfTransaction())
			{
				trxManager.runOutOfTransaction(processExecutor);
			}
			else
			{
				trxManager.run(ITrx.TRXNAME_ThreadInherited, processExecutor);
			}
		}
		finally
		{
			//
			// Update statistics
			if (duration != null)
			{
				duration.stop();
				final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
				adProcessDAO.addProcessStatistics(pi.getAdProcessId(), pi.getClientId(), duration.elapsed(TimeUnit.MILLISECONDS)); // never throws exception
			}

			// Unlock
			unlock(true); // never throws exception

			// Clear thread local AD_Org_ID/AD_Process_ID/etc
			s_currentOrg_ID.set(previousOrgId);
			s_currentProcess_ID.set(previousProcessId);
		}

		//
		// Propagate the error if asked
		if (onErrorThrowException)
		{
			pi.getResult().propagateErrorIfAny();
		}
	}

	private static DocumentReportRequest toDocumentReportRequest(final ProcessInfo processInfo)
	{
		return DocumentReportRequest.builder()
				.flavor(DocumentReportFlavor.PRINT)
				.reportProcessId(processInfo.getAdProcessId())
				.documentRef(processInfo.getRecordRefNotNull())
				.clientId(processInfo.getClientId())
				.orgId(processInfo.getOrgId())
				.userId(processInfo.getUserId())
				.roleId(processInfo.getRoleId())
				.windowNo(processInfo.getWindowNo())
				.tabNo(processInfo.getTabNo())
				.printPreview(processInfo.isPrintPreview())
				.reportLanguage(processInfo.getReportLanguage())
				.build();
	}

	private IAutoCloseable switchContextIfNeeded()
	{
		if (switchContextWhenRunning)
		{
			return Env.switchContext(pi.getCtx());
		}
		else
		{
			return NullAutoCloseable.instance;
		}
	}

	private void assertPermissions()
	{
		final IUserRolePermissions permissions = Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(
				pi.getRoleId(),
				pi.getUserId(),
				pi.getClientId(),
				Env.getLocalDate(pi.getCtx()));

		if (!permissions.getRoleId().isSystem())
		{
			final AdProcessId adProcessId = pi.getAdProcessId();
			final Boolean access = permissions.getProcessAccess(adProcessId.getRepoId());
			if (access == null || !access)
			{
				// get the process value, such that an admin can directly insert the right process
				final I_AD_Process processRecord = adProcessDAO.getById(adProcessId);
				final String processValue = processRecord != null ? processRecord.getValue() : "<NULL>";
				throw new AdempiereException("Cannot access AD_Process.Value=" + processValue + " with role: " + permissions.getName())
						.appendParametersToMessage()
						.setParameter("AD_Process_ID", adProcessId.getRepoId());
			}
		}
	}

	/**
	 * Lock the process instance and notify the parent
	 * <p>
	 * NOTE: it's OK to throw exceptions
	 */
	private void lock(final boolean runningLocally)
	{
		//
		// Database: lock the AD_PInstance
		if (runningLocally)
		{
			adPInstanceDAO.lock(pi.getPinstanceId());
		}

		//
		// Notify parent
		if (listener != null)
		{
			listener.lockUI(pi);
		}
	}

	/**
	 * Unlock the process instance and notify the parent.
	 * <p>
	 * NOTE: it's very important this method to never throw exception.
	 */
	private void unlock(final boolean runningLocally)
	{
		final Properties ctx = pi.getCtx();
		final ProcessExecutionResult result = pi.getResult();

		//
		// Translate process summary if needed
		if (runningLocally)
		{
			final String summary = result.getSummary();
			if (summary != null && summary.indexOf('@') >= 0)
			{
				result.setSummary(msgBL.parseTranslation(ctx, summary));
			}
		}

		//
		// Notify parent
		try
		{
			if (listener != null)
			{
				listener.unlockUI(pi);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed notifying the listener {} to unlock {}", listener, pi, ex);
		}

		//
		// Database: unlock and save the result
		if (runningLocally)
		{
			try
			{
				adPInstanceDAO.unlockAndSaveResult(result);
			}
			catch (final Throwable e)
			{
				logger.error("Failed unlocking for {}", result, e);
			}
		}
	}

	private void startWorkflow()
	{
		final WorkflowId workflowId = Check.assumeNotNull(pi.getWorkflowId(), "workflowId");
		logger.debug("startWorkflow: {} ({})", workflowId, pi);

		final WorkflowExecutionResult workflowExecutionResult = WorkflowExecutor.builder()
				.clientId(pi.getClientId())
				.documentRef(pi.getRecordRefNotNull())
				.userId(pi.getUserId())
				.build()
				.start(workflowId);
		logger.debug("Executed {} and got {}", workflowId, workflowExecutionResult);

		//
		// Update process result
		final ProcessExecutionResult processExecutionResult = pi.getResult();
		if (workflowExecutionResult.isError())
		{
			processExecutionResult.markAsError(workflowExecutionResult.getSummary(), workflowExecutionResult.getException());
		}
		else
		{
			processExecutionResult.markAsSuccess(workflowExecutionResult.getSummary());
		}
	}   // startWorkflow

	private void startJavaOrScriptProcess() throws Exception
	{
		logger.debug("startProcess: {}", pi);

		final Optional<String> ruleValue = ScriptEngineFactory.extractRuleValueFromClassname(pi.getClassName());
		if (ruleValue.isPresent())
		{
			startScriptProcess(ruleValue.get());
		}
		else
		{
			startJavaProcess();
		}
	}

	private void startScriptProcess(final String ruleValue)
	{
		final Properties ctx = pi.getCtx();
		final I_AD_Rule rule = Services.get(IADRuleDAO.class).retrieveByValue(ctx, ruleValue);
		if (rule == null)
		{
			throw new AdempiereException("@ScriptNotFound@: " + ruleValue);
		}
		if (!X_AD_Rule.EVENTTYPE_Process.equals(rule.getEventType()))
		{
			throw new AdempiereException("@ScriptNotFound@: " + ruleValue + " - eventType must be Process");
		}

		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		final String trxName = trx == null ? null : trx.getTrxName();

		final ScriptExecutor scriptExecutor = ScriptEngineFactory.get()
				.createExecutor(rule)
				.putContext(ctx, pi.getWindowNo())
				.putArgument("Trx", trx)
				.putArgument("TrxName", trxName)
				.putArgument("Table_ID", pi.getTable_ID())
				.putArgument("Record_ID", pi.getRecord_ID())
				.putArgument("AD_Client_ID", pi.getAD_Client_ID())
				.putArgument("AD_Org_ID", pi.getAD_Org_ID())
				.putArgument("AD_User_ID", pi.getAD_User_ID())
				.putArgument("AD_Role_ID", RoleId.toRepoId(pi.getRoleId()))
				.putArgument("AD_PInstance_ID", PInstanceId.toRepoId(pi.getPinstanceId()));

		final List<ProcessInfoParameter> parameters = pi.getParameter();
		if (parameters != null)
		{
			scriptExecutor.putArgument("Parameter", parameters.toArray(new ProcessInfoParameter[parameters.size()])); // put as array for backward compatibility
			for (final ProcessInfoParameter para : parameters)
			{
				final String name = para.getParameterName();
				if (para.getParameter_To() == null)
				{
					final Object value = para.getParameter();
					if (name.endsWith("_ID") && (value instanceof Number))
					{
						scriptExecutor.putProcessParameter(name, ((Number)value).intValue());
					}
					else
					{
						scriptExecutor.putProcessParameter(name, value);
					}
				}
				else
				{
					final Object value1 = para.getParameter();
					final Object value2 = para.getParameter_To();
					if (name.endsWith("_ID") && (value1 instanceof Number))
					{
						scriptExecutor.putProcessParameter(name + "1", ((Number)value1).intValue());
					}
					else
					{
						scriptExecutor.putProcessParameter(name + "1", value1);
					}
					if (name.endsWith("_ID") && (value2 instanceof Number))
					{
						scriptExecutor.putProcessParameter(name + "2", ((Number)value2).intValue());
					}
					else
					{
						scriptExecutor.putProcessParameter(name + "2", value2);
					}
				}
			}
		}
		scriptExecutor.putArgument("ProcessInfo", pi);

		final Object scriptResult = scriptExecutor.execute(rule.getScript());
		final String msg = scriptResult != null ? scriptResult.toString() : null;
		// transaction should rollback if there are error in process
		if ("@Error@".equals(msg))
		{
			throw new AdempiereException(msg);
		}

		// Update result
		final ProcessExecutionResult result = pi.getResult();
		result.setSummary(msgBL.parseTranslation(ctx, msg)); // Parse Variables
	}

	private void startJavaProcess()
	{
		final ProcessInfo pi = this.pi;

		final JavaProcess process = pi.newProcessClassInstanceOrNull();
		if (process == null)
		{
			throw new AdempiereException("Cannot create process class instance for " + pi); // shall not happen
		}

		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);

		try (final IAutoCloseable ignored = JavaProcess.temporaryChangeCurrentInstanceOverriding(process))
		{
			process.startProcess(pi, trx);

			if (pi.isNotifyUserAfterExecution())
			{
				final UserId loggedUserId = Env.getLoggedUserId();
				logger.debug("ProcessInfo.isNotifyUserAfterExecution is true, so we notify loggedUserId={}", loggedUserId.getRepoId());

				final String processName = pi.getTitle();
				final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
						.contentADMessage(MSG_DONE)
						.contentADMessageParam(processName)
						.recipientUserId(loggedUserId)
						.targetAction(TargetRecordAction.of(TableRecordReference.of(I_AD_PInstance.Table_Name, pi.getPinstanceId())))
						.build();
				notificationBL.send(userNotificationRequest);
			}
		}
	}

	/**
	 * Start Database Process
	 *
	 */
	private void startDBProcess()
	{
		final String dbProcedureName = pi.getDBProcedureName().orElseThrow();
		logger.debug("startDBProcess: {} ({})", dbProcedureName, pi);

		final String sql = "{call " + dbProcedureName + "(?)}";
		final Object[] sqlParams = new Object[] { pi.getPinstanceId() };
		try (final CallableStatement cstmt = DB.prepareCall(sql, ResultSet.CONCUR_UPDATABLE, ITrx.TRXNAME_ThreadInherited))
		{
			DB.setParameters(cstmt, sqlParams);
			cstmt.executeUpdate();

			final ProcessExecutionResult result = pi.getResult();
			adPInstanceDAO.loadResultSummary(result);
			result.markLogsAsStale();
		}
		catch (final Exception e)
		{
			throw new DBException(e, sql, sqlParams);
		}
	}

	/**
	 * In case the process is running asynchronously, wait until thread completes
	 */
	public void waitToComplete()
	{
		final Thread thread = m_thread;
		if (thread != null && thread.isAlive())
		{
			try
			{
				thread.join();
			}
			catch (final InterruptedException e)
			{
				// somebody stopped the thread by sending an INTERRUPT signal
				logger.info("Process thread interrupted", e);
			}
		}
	}

	public ProcessInfo getProcessInfo()
	{
		return pi;
	}

	public ProcessExecutionResult getResult()
	{
		return pi.getResult();
	}

	public static final class Builder
	{
		private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

		private final ProcessInfo processInfo;
		private IProcessExecutionListener listener = null;
		private boolean switchContextWhenRunning = false;
		private boolean onErrorThrowException = false;
		private Consumer<ProcessInfo> beforeCallback = null;

		private Builder(@NonNull final ProcessInfo processInfo)
		{
			this.processInfo = processInfo;
		}

		public void executeASync()
		{
			processInfo.setAsync(true); // #1160 advise the product info, that we want an asynchronous execution
			final ProcessExecutor worker = build();
			worker.executeAsync();
		}

		public ProcessExecutor executeSync()
		{
			processInfo.setAsync(false); // #1160 advise the process info, that we want a synchronous execution
			final ProcessExecutor worker = build();
			worker.executeSync();
			return worker;
		}

		private ProcessExecutor build()
		{
			try
			{
				prepareAD_PInstance(processInfo);
			}
			catch (final Throwable e)
			{
				final ProcessExecutionResult result = processInfo.getResult();
				result.markAsError(e);

				if (listener != null)
				{
					listener.onProcessInitError(processInfo);
				}
				else
				{
					throw AdempiereException.wrapIfNeeded(e);
				}
			}

			return new ProcessExecutor(this);
		}

		private void prepareAD_PInstance(final ProcessInfo pi)
		{
			//
			// Save process info to database, including parameters.
			adPInstanceDAO.saveProcessInfo(pi);

			//
			// Execute before call callback
			if (beforeCallback != null)
			{
				beforeCallback.accept(pi);
			}
		}

		private ProcessInfo getProcessInfo()
		{
			return processInfo;
		}

		public Builder setListener(final IProcessExecutionListener listener)
		{
			this.listener = listener;
			return this;
		}

		private IProcessExecutionListener getListener()
		{
			return listener;
		}

		/**
		 * Advice the executor to propagate the error in case the execution failed.
		 */
		public Builder onErrorThrowException()
		{
			this.onErrorThrowException = true;
			return this;
		}

		public Builder onErrorThrowException(final boolean onErrorThrowException)
		{
			this.onErrorThrowException = onErrorThrowException;
			return this;
		}

		/**
		 * Advice the executor to switch current context with process info's context.
		 *
		 * @see ProcessInfo#getCtx()
		 * @see Env#switchContext(Properties)
		 */
		public Builder switchContextWhenRunning()
		{
			this.switchContextWhenRunning = true;
			return this;
		}

		/**
		 * Sets the callback to be executed after AD_PInstance is created but before the actual process is started.
		 * If the callback fails, the exception is propagated, so the process will not be started.
		 * <p>
		 * A common use case of <code>beforeCallback</code> is to create to selections which are linked to this process instance.
		 */
		public Builder callBefore(final Consumer<ProcessInfo> beforeCallback)
		{
			this.beforeCallback = beforeCallback;
			return this;
		}
	}
}    // ProcessCtl
