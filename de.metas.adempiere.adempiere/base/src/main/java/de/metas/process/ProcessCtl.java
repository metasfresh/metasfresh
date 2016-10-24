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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

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
import org.compiere.print.ReportCtl;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.util.ASyncProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.wf.MWFProcess;
import org.compiere.wf.MWorkflow;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

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

	private static final String JASPER_STARTER_CLASS = "org.compiere.report.ReportStarter";

	//
	// Thread locals
	private static final ThreadLocal<Integer> s_currentProcess_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro
	private static final ThreadLocal<Integer> s_currentOrg_ID = new ThreadLocal<>(); // metas: c.ghita@metas.ro

	// services
	private static final transient Logger logger = LogManager.getLogger(ProcessCtl.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final int windowNo;
	private final ASyncProcess parent;

	//
	// Process info
	private ProcessInfo pi;
	// Additional process info variables which does not exist in ProcesInfo instance
	private String pi_ProcedureName = "";
	private String pi_JasperReport = "";
	private int AD_Workflow_ID = -1;
	private boolean isDirectPrint = false;
	
	// TODO: re-implement server process support
	private boolean isServerProcess = false;

	private Thread m_thread; // metas

	private ProcessCtl(final Builder builder)
	{
		super();
		pi = builder.getProcessInfo();
		windowNo = pi.getWindowNo();
		parent = builder.getASyncParent();
	}

	private Properties getCtx()
	{
		return pi.getCtx();
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

				loadProcessInfoFromAD_PInstance(); // execute before lock because we want to load the title, estimated duration etc

				// Lock
				lock();

				//
				// Execute the process (workflow/java/db process)
				final boolean isJavaProcess = !Check.isEmpty(pi.getClassName(), true);
				final boolean isDBProcess = !Check.isEmpty(pi_ProcedureName, true);
				if (AD_Workflow_ID > 0)
				{
					startWorkflow();
					return;
				}
				else if (isJavaProcess)
				{
					if (!startJavaOrScriptProcess())
					{
						return;
					}
				}
				else if (isDBProcess)
				{
					startDBProcess();
				}

				//
				// Prepare report
				final boolean isReport = pi.isReportingProcess();
				final boolean isJasperReport = !Check.isEmpty(pi_JasperReport, true);
				if (isJasperReport)
				{
					pi.setClassName(JASPER_STARTER_CLASS);
					// 03040: jasper report starter needs the window number to extract the BPartner's language from 'ctx'
					Check.assume(pi.getWindowNo() == windowNo, "m_pi.getWindowNo()='{}' == '{}'", pi.getWindowNo(), windowNo);
					startJavaOrScriptProcess();
					return;
				}
				else if (isReport)
				{
					pi.setPrintPreview(!isDirectPrint);
					ReportCtl.start(null, pi); // sync!
					pi.setSummary("Report");
				}
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				logger.warn("Got error", e);
				pi.setThrowable(e);
				pi.setSummary(e.getLocalizedMessage());
				pi.setError(true);
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
	}

	private final void loadProcessInfoFromAD_PInstance()
	{
		final int adPInstanceId = pi.getAD_PInstance_ID();

		//
		String sql = "SELECT p.Name, p.ProcedureName,p.ClassName, p.AD_Process_ID,"		// 1..4
				+ " p.isReport,p.IsDirectPrint,p.AD_ReportView_ID,p.AD_Workflow_ID,"		// 5..8
				+ " CASE WHEN COALESCE(p.Statistic_Count,0)=0 THEN 0 ELSE p.Statistic_Seconds/p.Statistic_Count END,"
				+ " p.IsServerProcess, p.JasperReport "
				+ "FROM AD_Process p"
				+ " INNER JOIN AD_PInstance i ON (p.AD_Process_ID=i.AD_Process_ID) "
				+ "WHERE p.IsActive='Y'"
				+ " AND i.AD_PInstance_ID=?";
		if (!Env.isBaseLanguage(getCtx(), "AD_Process"))
		{
			sql = "SELECT t.Name, p.ProcedureName,p.ClassName, p.AD_Process_ID,"		// 1..4
					+ " p.isReport, p.IsDirectPrint,p.AD_ReportView_ID,p.AD_Workflow_ID,"	// 5..8
					+ " CASE WHEN COALESCE(p.Statistic_Count,0)=0 THEN 0 ELSE p.Statistic_Seconds/p.Statistic_Count END,"
					+ " p.IsServerProcess, p.JasperReport "
					+ "FROM AD_Process p"
					+ " INNER JOIN AD_PInstance i ON (p.AD_Process_ID=i.AD_Process_ID) "
					+ " INNER JOIN AD_Process_Trl t ON (p.AD_Process_ID=t.AD_Process_ID"
					+ " AND t.AD_Language='" + Env.getAD_Language(getCtx()) + "') "
					+ "WHERE p.IsActive='Y'"
					+ " AND i.AD_PInstance_ID=?";
		}
		//
		final List<Object> sqlParams = ImmutableList.of(adPInstanceId);
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final String title = rs.getString(1);
				pi.setTitle(title);
				pi_ProcedureName = rs.getString(2);
				pi.setClassName(rs.getString(3));
				pi.setAD_Process_ID(rs.getInt(4));

				AD_Workflow_ID = rs.getInt(8);

				//
				final int estimateSeconds = rs.getInt(9);
				if (estimateSeconds > 0)
				{
					pi.setEstSeconds(estimateSeconds + 1);     // admin overhead
				}

				isServerProcess = DisplayType.toBoolean(rs.getString(10));

				//
				// Report
				boolean isReport = DisplayType.toBoolean(rs.getString(5));

				//
				// Jasper report
				pi_JasperReport = rs.getString(11);
				final boolean isJasper = !Check.isEmpty(pi_JasperReport, true);
				// Clear Jasper Report class if default - to be executed later
				if (isJasper)
				{
					isReport = true;
					if (JASPER_STARTER_CLASS.equals(pi.getClassName()))
					{
						pi.setClassName(null);
					}
				}

				pi.setReportingProcess(isReport);

				//
				// Direct print
				if (isReport)
				{
					if (DisplayType.toBoolean(rs.getString(6)) // the process wants to be printed directly
							&& !Ini.isPropertyBool(Ini.P_PRINTPREVIEW) // P_PRINTPREVIEW=Y means "never without preview"
							&& !pi.isPrintPreview())
					{
						isDirectPrint = true;
					}
				}
			}
			else
			{
				throw new AdempiereException("@NotFound@ @AD_PInstance_ID@: " + adPInstanceId);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		// No PL/SQL Procedure
		if (pi_ProcedureName == null)
		{
			pi_ProcedureName = "";
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
		if (pi.isBatch())
		{
			pi.setIsTimeout(true);
		}

		//
		// Translate process summary if needed
		final String summary = pi.getSummary();
		if (summary != null && summary.indexOf('@') >= 0)
		{
			pi.setSummary(msgBL.parseTranslation(getCtx(), summary));
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
		Check.assume(AD_Workflow_ID > 0, "AD_Workflow_ID > 0");
		logger.debug("startWorkflow: {} ({})", AD_Workflow_ID, pi);

		final MWorkflow wf = MWorkflow.get(getCtx(), AD_Workflow_ID);
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
		final Properties ctx = getCtx();
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
		final ProcessInfoParameter[] para = pi.getParameter();
		if (para != null)
		{
			engine.put(MRule.ARGUMENTS_PREFIX + "Parameter", pi.getParameter());
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				if (para[i].getParameter_To() == null)
				{
					Object value = para[i].getParameter();
					if (name.endsWith("_ID") && (value instanceof BigDecimal))
						engine.put(MRule.PARAMETERS_PREFIX + name, ((BigDecimal)value).intValue());
					else
						engine.put(MRule.PARAMETERS_PREFIX + name, value);
				}
				else
				{
					Object value1 = para[i].getParameter();
					Object value2 = para[i].getParameter_To();
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

		// Parse Variables
		pi.setSummary(msgBL.parseTranslation(ctx, msg));
	}

	private final boolean startJavaProcess() throws Exception
	{
		final String className = pi.getClassName();

		// use context classloader if available
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
			classLoader = getClass().getClassLoader();

		final ProcessCall process = (ProcessCall)classLoader.loadClass(className).newInstance();

		final Properties ctx = getCtx();
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
		Check.assumeNotEmpty(pi_ProcedureName, "ProcedureName not empty");
		logger.debug("startDBProcess: {} ({})", pi_ProcedureName, pi);

		final String sql = "{call " + pi_ProcedureName + "(?)}";
		final Object[] sqlParams = new Object[] { pi.getAD_PInstance_ID() };
		try (final CallableStatement cstmt = DB.prepareCall(sql, ResultSet.CONCUR_UPDATABLE, ITrx.TRXNAME_ThreadInherited))
		{
			DB.setParameters(cstmt, sqlParams);
			cstmt.executeUpdate();
			
			ProcessInfoUtil.setSummaryFromDB(pi);
			pi.markLogsAsStale();
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

	public static final class Builder
	{
		private final transient IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);

		private ProcessInfo pi;
		private Integer windowNo = null;
		private ASyncProcess asyncParent = null;

		private Builder()
		{
			super();
		}

		public void execute()
		{
			final ProcessCtl worker = build();
			worker.execute();
		}

		public void executeSync()
		{
			final ProcessCtl worker = build();
			worker.executeSync();
		}

		private ProcessCtl build()
		{
			Check.assumeNotNull(pi, "Parameter pi is not null");

			// 03040: currently, only the jasper report starter needs the current windowNo.
			// but to keep it general, and support future demands, we we add this information to 'm_pi' in any case
			if (windowNo != null && windowNo > 0)
			{
				pi.setWindowNo(windowNo);
			}

			try
			{
				prepareAD_PInstance(pi);
			}
			catch (final Throwable e)
			{
				pi.setSummary(e.getLocalizedMessage());
				pi.setThrowable(e);
				pi.setError(true);

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
			final ProcessInfoParameter[] parameters = pi.getParametersNoLoad();
			if(parameters != null && parameters.length > 0)
			{
				adPInstanceDAO.saveParameterToDB(pi.getAD_PInstance_ID(), parameters);
			}
		}

		public Builder setProcessInfo(final ProcessInfo pi)
		{
			this.pi = pi;
			return this;
		}

		public Builder setWindowNo(final int windowNo)
		{
			this.windowNo = windowNo;
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
	}

}	// ProcessCtl
