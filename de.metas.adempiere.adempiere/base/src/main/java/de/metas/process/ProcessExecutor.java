package de.metas.process;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.X_AD_Rule;
import org.compiere.print.ReportCtl;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.wf.MWFProcess;
import org.compiere.wf.MWorkflow;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;

//import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;
import de.metas.script.IADRuleDAO;
import de.metas.script.ScriptEngineFactory;
import de.metas.script.ScriptExecutor;
import de.metas.session.jaxrs.IServerService;

/**
 * Process executor: executes a process (sync or async) which was defined by given {@link ProcessInfo}.
 *
 * @author authors of earlier versions of this class are: Jorg Janke, Low Heng Sin, Teo Sarca
 * @author metas-dev <dev@metasfresh.com>
 */
public final class ProcessExecutor
{
	public static final Builder builder(final ProcessInfo processInfo)
	{
		return new Builder(processInfo);
	}

	public static int getCurrentOrgId()
	{
		final Integer orgId = s_currentOrg_ID.get();
		if (orgId == null)
		{
			return 0;
		}
		return orgId;
	}

	public static int getCurrentProcessId()
	{
		final Integer processId = s_currentProcess_ID.get();
		if (processId == null)
		{
			return 0;
		}
		return processId;
	}

	//
	// Thread locals
	private static final ThreadLocal<Integer> s_currentProcess_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro
	private static final ThreadLocal<Integer> s_currentOrg_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro

	// services
	private static final transient Logger logger = LogManager.getLogger(ProcessExecutor.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

	private final IProcessExecutionListener listener;
	private final ProcessInfo pi;
	private final boolean switchContextWhenRunning;
	private final boolean onErrorThrowException;

	private Thread m_thread; // metas

	private ProcessExecutor(final Builder builder)
	{
		super();
		pi = builder.getProcessInfo();
		listener = builder.getListener();
		switchContextWhenRunning = builder.switchContextWhenRunning;
		onErrorThrowException = builder.onErrorThrowException;
	}
	
	private final String buildThreadName()
	{
		return pi.getTitle() + "-" + pi.getAD_PInstance_ID();
	}

	/**
	 * Run this process asynchronously
	 */
	private void executeAsync()
	{
		Check.assumeNull(m_thread, "not already started");

		final Thread thread = new Thread(() -> executeSync());
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
		// Case: the process requires to be executed on server, but we are not running on server
		// => execute the process remotely
		if (pi.isServerProcess() && Ini.isClient())
		{
			executeSync_Remote();
		}
		//
		// Case: our process has some parts that requires to be executed out of transaction
		// but we are currently running in a transaction.
		// => spawn a new thread, run the process there and wait for the thread to finish
		else if (pi.getProcessClassInfo().isRunOutOfTransaction()
				&& trxManager.hasThreadInheritedTrx())
		{
			final Thread thread = new Thread(()->executeNow());
			thread.setName(buildThreadName());
			thread.start();
			
			try
			{
				thread.join();
			}
			catch (InterruptedException ex)
			{
				throw Throwables.propagate(ex);
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

	private void executeSync_Remote()
	{
		// Make sure process info is persisted
		adPInstanceDAO.saveProcessInfoOnly(pi);

		try
		{
			lock(false);

			final ProcessExecutionResult result = pi.getResult();

			final ProcessExecutionResult remoteResult = Services.get(IServerService.class).process(pi.getAD_PInstance_ID());
			result.updateFrom(remoteResult);
		}
		catch (Exception e)
		{
			final Throwable cause = AdempiereException.extractCause(e);
			logger.warn("Got error", cause);

			final ProcessExecutionResult result = pi.getResult();
			result.markAsError(cause);
			result.markLogsAsStale();
		}
		finally
		{
			unlock(false);
		}
	}
	
	private void executeNow()
	{
		logger.debug("running: {}", pi);

		//
		final TrxRunnableAdapter processExecutor = new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				//
				// Execute the process (workflow/java/db process)
				if (pi.getAD_Workflow_ID() > 0)
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
				final boolean isJasperReport = pi.getReportTemplate().isPresent();
				if (isJasperReport)
				{
					// nothing do to, the Jasper process class implementation is responsible for triggering the report preview if any
					return;
				}
				else if (isReport)
				{
					ReportCtl.builder()
							.setProcessInfo(pi)
							.start();
					pi.getResult().setSummary("Report");
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

		final Integer previousProcessId = s_currentProcess_ID.get();
		final Integer previousOrgId = s_currentOrg_ID.get();
		Stopwatch duration = null;
		try (final IAutoCloseable contextRestorer = switchContextIfNeeded())
		{
			s_currentProcess_ID.set(pi.getAD_Process_ID());
			s_currentOrg_ID.set(pi.getAD_Org_ID());

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
				IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
				adProcessDAO.addProcessStatistics(pi.getCtx(), pi.getAD_Process_ID(), pi.getAD_Client_ID(), duration.elapsed(TimeUnit.MILLISECONDS)); // never throws exception
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

	private final void assertPermissions()
	{
		final IUserRolePermissions permissions = Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(
				pi.getAD_Role_ID() //
				, pi.getAD_User_ID() //
				, pi.getAD_Client_ID() //
				, Env.getDate(pi.getCtx()) //
		);

		if (permissions.getAD_Role_ID() > 0)
		{
			final int adProcessId = pi.getAD_Process_ID();
			final Boolean access = permissions.getProcessAccess(adProcessId);
			if (access == null || !access.booleanValue())
			{
				throw new AdempiereException("Cannot access Process " + adProcessId + " with role: " + permissions.getName());
			}
		}
	}

	/**
	 * Lock the process instance and notify the parent
	 * 
	 * NOTE: it's OK to throw exceptions
	 */
	private void lock(final boolean runningLocally)
	{
		//
		// Database: lock the AD_PInstance
		if (runningLocally)
		{
			adPInstanceDAO.lock(pi.getCtx(), pi.getAD_PInstance_ID());
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
	 * 
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
		catch (Exception ex)
		{
			logger.warn("Failed notifying the listener {} to unlock {}", listener, pi, ex);
		}

		//
		// Database: unlock and save the result
		if (runningLocally)
		{
			try
			{
				adPInstanceDAO.unlockAndSaveResult(ctx, result);
			}
			catch (Throwable e)
			{
				logger.error("Failed unlocking for {}", result, e);
			}
		}
	}

	/**
	 * Start Workflow.
	 *
	 * @param AD_Workflow_ID workflow
	 * @return true if started
	 */
	private boolean startWorkflow()
	{
		final int AD_Workflow_ID = pi.getAD_Workflow_ID();
		Check.assume(AD_Workflow_ID > 0, "AD_Workflow_ID > 0");
		logger.debug("startWorkflow: {} ({})", AD_Workflow_ID, pi);

		final MWorkflow wf = MWorkflow.get(pi.getCtx(), AD_Workflow_ID);
		MWFProcess wfProcess = null;
		if (pi.isBatch())
			wfProcess = wf.start(pi);		// may return null
		else
		{
			wfProcess = wf.startWait(pi);	// may return null
		}
		final boolean started = wfProcess != null;
		logger.debug("startWorkflow finish: started={}, wfProcess={}", started, wfProcess);

		return started;
	}   // startWorkflow

	/**
	 * Start Java/Script process.
	 *
	 * @return true if success
	 * @throws Exception
	 */
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

	private final void startScriptProcess(final String ruleValue)
	{
		final Properties ctx = pi.getCtx();
		final I_AD_Rule rule = Services.get(IADRuleDAO.class).retrieveByValue(ctx, ruleValue);
		if (rule == null)
		{
			throw new AdempiereException("@ScriptNotFound@: " + ruleValue);
		}
		if (! X_AD_Rule.EVENTTYPE_Process.equals(rule.getEventType()))
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
				.putArgument("AD_Role_ID", pi.getAD_Role_ID())
				.putArgument("AD_PInstance_ID", pi.getAD_PInstance_ID());
		
		final List<ProcessInfoParameter> parameters = pi.getParameter();
		if (parameters != null)
		{
			scriptExecutor.putArgument("Parameter", parameters.toArray(new ProcessInfoParameter[parameters.size()])); // put as array for backward compatibility
			for (final ProcessInfoParameter para : parameters)
			{
				String name = para.getParameterName();
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

	private final void startJavaProcess() throws Exception
	{
		final String className = pi.getClassName();

		// use context classloader if available
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
			classLoader = getClass().getClassLoader();

		final IProcess process = (IProcess)classLoader.loadClass(className).newInstance();

		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		process.startProcess(pi, trx);
	}

	/**
	 * Start Database Process
	 *
	 * @return true if success
	 */
	private final void startDBProcess()
	{
		final String dbProcedureName = pi.getDBProcedureName().get();
		logger.debug("startDBProcess: {} ({})", dbProcedureName, pi);

		final String sql = "{call " + dbProcedureName + "(?)}";
		final Object[] sqlParams = new Object[] { pi.getAD_PInstance_ID() };
		try (final CallableStatement cstmt = DB.prepareCall(sql, ResultSet.CONCUR_UPDATABLE, ITrx.TRXNAME_ThreadInherited))
		{
			DB.setParameters(cstmt, sqlParams);
			cstmt.executeUpdate();

			final ProcessExecutionResult result = pi.getResult();
			adPInstanceDAO.loadResultSummary(result);
			result.markLogsAsStale();
		}
		catch (Exception e)
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

		private Builder(final ProcessInfo processInfo)
		{
			super();

			Check.assumeNotNull(processInfo, "Parameter processInfo is not null");
			this.processInfo = processInfo;
		}

		public void executeASync()
		{
			final ProcessExecutor worker = build();
			worker.executeAsync();
		}

		public ProcessExecutor executeSync()
		{
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
			// Create a new AD_PInstance_ID if there is none (task 05978)
			adPInstanceDAO.saveProcessInfoOnly(pi);

			//
			// Save Parameters to AD_PInstance_Para, if needed
			final List<ProcessInfoParameter> parameters = pi.getParametersNoLoad();
			if (parameters != null && !parameters.isEmpty())
			{
				adPInstanceDAO.saveParameterToDB(pi.getAD_PInstance_ID(), parameters);
			}

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
		 * 
		 * @param beforeCallback
		 */
		public Builder callBefore(final Consumer<ProcessInfo> beforeCallback)
		{
			this.beforeCallback = beforeCallback;
			return this;
		}
	}

}	// ProcessCtl
