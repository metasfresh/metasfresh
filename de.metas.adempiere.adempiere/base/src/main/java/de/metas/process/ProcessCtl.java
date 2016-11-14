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
import java.util.function.Consumer;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.MPInstance;
import org.compiere.model.MRule;
import org.compiere.print.JRReportViewerProvider;
import org.compiere.print.JRReportViewerProviderAware;
import org.compiere.print.ReportCtl;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessExecutionResult;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.util.ASyncProcess;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.wf.MWFProcess;
import org.compiere.wf.MWorkflow;
import org.slf4j.Logger;

//import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;

/**
 * Process Interface Controller.
 *
 * @author Jorg Janke
 * @version $Id: ProcessCtl.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * @author Low Heng Sin
 *         - Added support for having description and parameter in one dialog
 *         - Added support to run db process remotely on server
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1757523 ] Server Processes are using Server's context
 *         <li>FR [ 1807922 ] Pocess threads should have a better name
 *         <li>BF [ 1960523 ] Server
 *         Process functionality not working
 */
public final class ProcessCtl
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

//	private static final String JASPER_STARTER_CLASS = "org.compiere.report.ReportStarter";

	//
	// Thread locals
	private static final ThreadLocal<Integer> s_currentProcess_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro
	private static final ThreadLocal<Integer> s_currentOrg_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro

	// services
	private static final transient Logger logger = LogManager.getLogger(ProcessCtl.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ASyncProcess parent;
	private final ProcessInfo pi;
	private final JRReportViewerProvider jrReportViewerProvider;
	private final boolean onErrorThrowException;

	private Thread m_thread; // metas


	private ProcessCtl(final Builder builder)
	{
		super();
		pi = builder.getProcessInfo();
		parent = builder.getASyncParent();
		jrReportViewerProvider = builder.getJRReportViewerProvider();
		onErrorThrowException = builder.onErrorThrowException;
	}

	/**
	 * Execute synchronously/asynchronously.
	 *
	 * @return this
	 */
	private ProcessCtl execute()
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
		logger.debug("running: {}", pi);

		//
		final Integer previousProcessId = s_currentProcess_ID.get();
		final Integer previousOrgId = s_currentOrg_ID.get();
		s_currentProcess_ID.set(pi.getAD_Process_ID());
		s_currentOrg_ID.set(pi.getAD_Org_ID());

		//
		final TrxRunnableAdapter processExecutor = new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				// Lock
				lock();

				//
				// Execute the process (workflow/java/db process)
				if(pi.getAD_Workflow_ID() > 0)
				{
					startWorkflow();
					return;
				}
				else if (!Check.isEmpty(pi.getClassName(), true))
				{
					if (!startJavaOrScriptProcess())
					{
						return;
					}
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
							.setJRReportViewerProvider(jrReportViewerProvider)
							.start();
					pi.getResult().setSummary("Report");
				}
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				logger.warn("Got error", e);
				final ProcessExecutionResult result = pi.getResult();
				result.setThrowable(e);
				result.setSummary(e.getLocalizedMessage());
				result.setError(true);
				return ROLLBACK;
			}

			@Override
			public void doFinally()
			{
				s_currentOrg_ID.set(previousOrgId);
				s_currentProcess_ID.set(previousProcessId);

				unlock();
			}
		};

		if (pi.getProcessClassInfo().isRunDoItOutOfTransaction())
		{
			trxManager.runOutOfTransaction(processExecutor);
		}
		else
		{
			trxManager.run(ITrx.TRXNAME_ThreadInherited, processExecutor);
		}
		
		//
		// Propagate the error if asked
		if(onErrorThrowException)
		{
			pi.getResult().propagateErrorIfAny();
		}
	}

	/**
	 * Lock UI & show Waiting
	 */
	private void lock()
	{
		if (parent != null)
		{
			parent.lockUI(pi);
		}
	}

	/**
	 * Unlock UI & dispose Waiting.
	 * Called from run()
	 */
	private void unlock()
	{
		final ProcessExecutionResult result = pi.getResult();
		
		if (pi.isBatch())
		{
			result.setTimeout(true);
		}

		//
		// Translate process summary if needed
		final String summary = result.getSummary();
		if (summary != null && summary.indexOf('@') >= 0)
		{
			result.setSummary(msgBL.parseTranslation(pi.getCtx(), summary));
		}

		if (parent != null)
		{
			parent.unlockUI(pi);
		}
	}   // unlock

	/**************************************************************************
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
	private boolean startJavaOrScriptProcess() throws Exception
	{
		logger.debug("startProcess: {}", pi);

		if (pi.getClassName().toLowerCase().startsWith(MRule.SCRIPT_PREFIX))
		{
			startScriptProcess();
			return true;
		}
		else
		{
			return startJavaProcess();
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

	private final boolean startJavaProcess() throws Exception
	{
		final String className = pi.getClassName();

		// use context classloader if available
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
			classLoader = getClass().getClassLoader();

		final ProcessCall process = (ProcessCall)classLoader.loadClass(className).newInstance();
		
		if(process instanceof JRReportViewerProviderAware)
		{
			final JRReportViewerProviderAware jrReportViewerProviderAware = (JRReportViewerProviderAware)process;
			jrReportViewerProviderAware.setJRReportViewerProvider(jrReportViewerProvider);
		}

		final Properties ctx = pi.getCtx();
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		final boolean success = process.startProcess(ctx, pi, trx);
		return success;
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
			ProcessInfoUtil.loadSummaryFromDB(result);
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
		private JRReportViewerProvider jrReportViewerProvider;
		private boolean onErrorThrowException = false;
		private Consumer<ProcessInfo> beforeCallback = null;

		private Builder()
		{
			super();
		}

		public void execute()
		{
			final ProcessCtl worker = build();
			worker.execute();
		}

		public ProcessCtl executeSync()
		{
			final ProcessCtl worker = build();
			worker.executeSync();
			return worker;
		}

		private ProcessCtl build()
		{
			Check.assumeNotNull(pi, "Parameter pi is not null");

			try
			{
				prepareAD_PInstance(pi);
			}
			catch (final Throwable e)
			{
				final ProcessExecutionResult result = pi.getResult();
				result.setSummary(e.getLocalizedMessage());
				result.setThrowable(e);
				result.setError(true);

				if(asyncParent != null)
				{
					asyncParent.onProcessInitError(pi);
				}
				else
				{
					throw AdempiereException.wrapIfNeeded(e);
				}
			}

			return new ProcessCtl(this);
		}

		private void prepareAD_PInstance(final ProcessInfo pi)
		{
			//
			// Create a new AD_PInstance_ID if there is none (task 05978)
			if (pi.getAD_PInstance_ID() <= 0)
			{
				final I_AD_PInstance adPInstance = new MPInstance(pi.getCtx(), pi);
				InterfaceWrapperHelper.save(adPInstance);
				pi.setAD_PInstance_ID(adPInstance.getAD_PInstance_ID());
			}

			//
			// Save Parameters to AD_PInstance_Para, if needed
			final List<ProcessInfoParameter> parameters = pi.getParametersNoLoad();
			if(parameters != null && !parameters.isEmpty())
			{
				adPInstanceDAO.saveParameterToDB(pi.getAD_PInstance_ID(), parameters);
			}

			//
			// Execute before call callback
			if(beforeCallback != null)
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
		
		public Builder setJRReportViewerProvider(final JRReportViewerProvider jrReportViewerProvider)
		{
			this.jrReportViewerProvider = jrReportViewerProvider;
			return this;
		}
		
		private JRReportViewerProvider getJRReportViewerProvider()
		{
			return jrReportViewerProvider;
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
