/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

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
import org.compiere.model.MRule;
import org.compiere.print.ReportCtl;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.wf.MWFProcess;
import org.compiere.wf.MWorkflow;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

//import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;
import de.metas.session.jaxrs.IServerService;

/**
 * Process executor
 *
 * @author authors of earlier versions of this class are: Jorg Janke, Low Heng Sin, Teo Sarca
 */
public final class ProcessExecutor
{
	public static final Builder builder()
	{
		return new Builder();
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

	private final ASyncProcess parent;
	private final ProcessInfo pi;
	private final boolean onErrorThrowException;

	private Thread m_thread; // metas

	private ProcessExecutor(final Builder builder)
	{
		super();
		pi = builder.getProcessInfo();
		parent = builder.getASyncParent();
		onErrorThrowException = builder.onErrorThrowException;
	}

	/**
	 * Execute synchronously/asynchronously.
	 *
	 * @return this
	 */
	private ProcessExecutor execute()
	{
		if (parent != null)
		{
			executeAsync();
		}
		else
		{
			// synchrous
			executeSync();
		}

		return this;
	}

	/**
	 * Run this process asynchronously
	 */
	private void executeAsync()
	{
		Check.assumeNull(m_thread, "not already started");

		final Thread thread = new Thread(() -> executeSync());
		// Set thread name - teo_sarca FR [ 1807922 ]
		if (pi != null)
		{
			thread.setName(pi.getTitle() + "-" + pi.getAD_PInstance_ID());
		}

		thread.start();

		m_thread = thread;
	}

	/**
	 * Run this process synchronously
	 */
	private void executeSync()
	{
		if (pi.isServerProcess() && Ini.isClient())
		{
			executeSync_Remote();
		}
		else
		{
			executeSync_Local();
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

	private void executeSync_Local()
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
		try
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
			if (pi.getProcessClassInfo().isRunDoItOutOfTransaction())
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
		if (parent != null)
		{
			parent.lockUI(pi);
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
			if (parent != null)
			{
				parent.unlockUI(pi);
			}
		}
		catch (Exception ex)
		{
			logger.warn("Failed notifying the parent {} to unlock {}", parent, pi, ex);
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

		if (pi.getClassName().toLowerCase().startsWith(MRule.SCRIPT_PREFIX))
		{
			startScriptProcess();
		}
		else
		{
			startJavaProcess();
		}
	}

	private final void startScriptProcess() throws ScriptException
	{
		final Properties ctx = pi.getCtx();
		final String cmd = pi.getClassName();
		final MRule rule = MRule.get(ctx, cmd.substring(MRule.SCRIPT_PREFIX.length()));
		if (rule == null)
		{
			throw new AdempiereException("@ScriptNotFound@: " + cmd);
		}
		if (!(rule.getEventType().equals(MRule.EVENTTYPE_Process)
				&& rule.getRuleType().equals(MRule.RULETYPE_JSR223ScriptingAPIs)))
		{
			throw new AdempiereException("@ScriptNotFound@: " + cmd + " must be of type JSR 223 and eventType must be Process");
		}

		final ScriptEngine engine = rule.getScriptEngine();

		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		final String trxName = trx == null ? null : trx.getTrxName();

		// Window context are W_
		// Login context are G_
		// Method arguments context are A_
		// Parameter context are P_
		MRule.setContext(engine, ctx, 0);  // no window
		// now add the method arguments to the engine
		engine.put(MRule.ARGUMENTS_PREFIX + "Ctx", ctx);
		engine.put(MRule.ARGUMENTS_PREFIX + "Trx", trx);
		engine.put(MRule.ARGUMENTS_PREFIX + "TrxName", trxName);
		engine.put(MRule.ARGUMENTS_PREFIX + "Record_ID", pi.getRecord_ID());
		engine.put(MRule.ARGUMENTS_PREFIX + "AD_Client_ID", pi.getAD_Client_ID());
		engine.put(MRule.ARGUMENTS_PREFIX + "AD_User_ID", pi.getAD_User_ID());
		engine.put(MRule.ARGUMENTS_PREFIX + "AD_PInstance_ID", pi.getAD_PInstance_ID());
		engine.put(MRule.ARGUMENTS_PREFIX + "Table_ID", pi.getTable_ID());
		// Add process parameters
		final List<ProcessInfoParameter> parameters = pi.getParameter();
		if (parameters != null)
		{
			engine.put(MRule.ARGUMENTS_PREFIX + "Parameter", parameters);
			for (final ProcessInfoParameter para : parameters)
			{
				String name = para.getParameterName();
				if (para.getParameter_To() == null)
				{
					Object value = para.getParameter();
					if (name.endsWith("_ID") && (value instanceof BigDecimal))
						engine.put(MRule.PARAMETERS_PREFIX + name, ((BigDecimal)value).intValue());
					else
						engine.put(MRule.PARAMETERS_PREFIX + name, value);
				}
				else
				{
					Object value1 = para.getParameter();
					Object value2 = para.getParameter_To();
					if (name.endsWith("_ID") && (value1 instanceof BigDecimal))
						engine.put(MRule.PARAMETERS_PREFIX + name + "1", ((BigDecimal)value1).intValue());
					else
						engine.put(MRule.PARAMETERS_PREFIX + name + "1", value1);
					if (name.endsWith("_ID") && (value2 instanceof BigDecimal))
						engine.put(MRule.PARAMETERS_PREFIX + name + "2", ((BigDecimal)value2).intValue());
					else
						engine.put(MRule.PARAMETERS_PREFIX + name + "2", value2);
				}
			}
		}
		engine.put(MRule.ARGUMENTS_PREFIX + "ProcessInfo", pi);

		final String msg = engine.eval(rule.getScript()).toString();
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

		final ProcessCall process = (ProcessCall)classLoader.loadClass(className).newInstance();

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

	public ProcessExecutionResult getResult()
	{
		return pi.getResult();
	}

	public static final class Builder
	{
		private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

		private ProcessInfo pi;
		private ASyncProcess asyncParent = null;
		private boolean onErrorThrowException = false;
		private Consumer<ProcessInfo> beforeCallback = null;

		private Builder()
		{
			super();
		}

		public void execute()
		{
			final ProcessExecutor worker = build();
			worker.execute();
		}

		public ProcessExecutor executeSync()
		{
			final ProcessExecutor worker = build();
			worker.executeSync();
			return worker;
		}

		private ProcessExecutor build()
		{
			Check.assumeNotNull(pi, "Parameter pi is not null");

			try
			{
				prepareAD_PInstance(pi);
			}
			catch (final Throwable e)
			{
				final ProcessExecutionResult result = pi.getResult();
				result.markAsError(e);

				if (asyncParent != null)
				{
					asyncParent.onProcessInitError(pi);
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

		public Builder setProcessInfo(final ProcessInfo pi)
		{
			this.pi = pi;
			return this;
		}

		private ProcessInfo getProcessInfo()
		{
			return pi;
		}

		public Builder setAsyncParent(final ASyncProcess asyncParent)
		{
			this.asyncParent = asyncParent;
			return this;
		}

		private ASyncProcess getASyncParent()
		{
			return asyncParent;
		}

		/**
		 * Advice the executor to propagate the error in case the execution failed.
		 */
		public Builder onErrorThrowException()
		{
			this.onErrorThrowException = true;
			return this;
		}

		public Builder callBefore(final Consumer<ProcessInfo> beforeCallback)
		{
			this.beforeCallback = beforeCallback;
			return this;
		}
	}

}	// ProcessCtl
