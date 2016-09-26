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
package org.compiere.process;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.process.ISvrProcessDefaultParametersProvider;
import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutableReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.MPInstance;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo.ShowProcessLogs;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.process.Param;
import de.metas.process.Process;
import de.metas.process.RunOutOfTrx;

/**
 * Server Process base class.
 *
 * Also see
 * <ul>
 * <li> {@link ISvrProcessPrecondition} if you need to dynamically decide whenever a process shall be available in the Gear.
 * <li> {@link ISvrProcessDefaultParametersProvider} if you want to provide some default values for parameters, when the UI parameters dialog is loaded
 * <li> {@link RunOutOfTrx} which is an annotation for the {@link #prepare()} and {@link #doIt()} method
 * <li> {@link Process} annotation if you add more info about how the process shall be executed
 * <li> {@link Param} annotation if you want to avoid implementing the {@link #prepare()} method
 * </ul>
 *
 *
 * @author Jorg Janke
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <ul>
 *         <li>FR [ 1646891 ] SvrProcess - post process support
 *         <li>BF [ 1877935 ] SvrProcess.process should catch all throwables
 *         <li>FR [ 1877937 ] SvrProcess: added commitEx method
 *         <li>BF [ 1878743 ] SvrProcess.getAD_User_ID
 *         <li>BF [ 1935093 ] SvrProcess.unlock() is setting invalid result
 *         <li>FR [ 2788006 ] SvrProcess: change access to some methods https://sourceforge.net/tracker/?func=detail&aid=2788006&group_id=176962&atid=879335
 *         </ul>
 *
 */
public abstract class SvrProcess implements ProcessCall, ILoggable, IContextAware
{
	// services
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Server Process. Note that the class is initiated by startProcess.
	 */
	protected SvrProcess()
	{
		super();
	}   // SvrProcess

	private Properties m_ctx;
	private ProcessInfo m_pi;

	/** Logger */
	protected final Logger log = LogManager.getLogger(getClass());
	static final transient Logger s_log = LogManager.getLogger(SvrProcess.class);

	/** Is the Object locked */
	private boolean m_locked = false;
	/** Loacked Object */
	private PO m_lockedObject = null;

	//
	// Transaction management
	/** Process Main transaction */
	private ITrx m_trx = ITrx.TRX_None;
	private Boolean m_trxIsLocal;
	private ImmutableReference<String> m_trxNameThreadInheritedBackup;
	private boolean m_dbContraintsChanged = false;
	/** Transaction name prefix (in case of local transaction) */
	private static final String TRXNAME_Prefix = "SvrProcess";

	// Common Return Messages
	protected static String MSG_SaveErrorRowNotFound = "@SaveErrorRowNotFound@";
	protected static String MSG_InvalidArguments = "@InvalidArguments@";
	protected static String MSG_OK = "OK";
	/**
	 * Process failed error message. To be returned from {@link #doIt()}.
	 *
	 * In case it's returned the process will be rolled back.
	 */
	protected static final String MSG_Error = "@Error@";

	/**
	 * Start the process.
	 *
	 * It should only return false, if the function could not be performed as this causes the process to abort.
	 *
	 * @param ctx Context
	 * @param pi Process Info
	 * @param trx existing/inherited transaction if any
	 * @return <code>true</code> if process was executed successfully
	 *
	 * @see org.compiere.process.ProcessCall#startProcess(Properties, ProcessInfo, ITrx)
	 */
	@Override
	public synchronized final boolean startProcess(final Properties ctx, final ProcessInfo pi, final ITrx trx)
	{
		Check.assumeNotNull(pi, "ProcessInfo not null");
		pi.setClassName(getClass().getName()); // make sure that we have the correct className in place. We need it to get the ProcessClassInfo

		// Preparation
		// FRESH-314: store #AD_PInstance_ID in a copied context (shall only live as long as this process does).
		// We might want to access this information (currently in AD_ChangeLog)
		// Note: using copyCtx because derviveCtx is not safe with Env.switchContext()
		m_ctx = Env.copyCtx(Env.coalesce(ctx));

		Env.setContext(m_ctx, Env.CTXNAME_AD_PInstance_ID, pi.getAD_PInstance_ID());

		m_pi = pi;

		// Trx: we are setting it to null to be consistent with running prepare() out-of-transaction
		// Later we will set the actual transaction or we will start a local transaction.
		m_trx = ITrx.TRX_None;

		boolean success = false;
		try (final IAutoCloseable loggableRestorer = ILoggable.THREADLOCAL.temporarySetLoggable(this);
				final IAutoCloseable contextRestorer = Env.switchContext(m_ctx) // FRESH-314: make sure our derived context will always be used
		)
		{
			lock();

			//
			// Prepare out of transaction, if needed
			final ProcessClassInfo processClassInfo = pi.getProcessClassInfo();
			boolean prepareExecuted = false;
			if (processClassInfo.isRunPrepareOutOfTransaction())
			{
				assertOutOfTransaction(trx, "prepare"); // make sure we were asked to run out of transaction
				prepareExecuted = true;
				prepareProcess();
			}

			//
			// doIt out of transaction, if needed
			String doItResult = null;
			boolean doItExecuted = false;
			if (processClassInfo.isRunDoItOutOfTransaction())
			{
				assertOutOfTransaction(trx, "run"); // make sure we were asked to run out of transaction
				doItExecuted = true;
				doItResult = doIt();
			}

			//
			// Prepare and doIt in transaction, if not already executed
			if (!prepareExecuted || !doItExecuted)
			{
				startTrx(trx);

				if (!prepareExecuted)
				{
					prepareExecuted = true;
					prepareProcess();
				}
				if (!doItExecuted)
				{
					doItExecuted = true;
					doItResult = doIt();
				}
			}

			// Legacy: transaction should rollback if there are error in process
			if (MSG_Error.equals(doItResult))
			{
				throw new AdempiereException(doItResult);
			}

			setProcessResultOK(doItResult);
			success = true;
		}
		catch (final Throwable e)
		{
			success = false;
			setProcessResultError(e);
		}
		finally
		{
			// NOTE: at this point the thread local loggable was restored

			endTrx(success);
			unlock();
		}

		//
		// outside transaction processing [ teo_sarca, 1646891 ]
		postProcess(!m_pi.isError());

		return !m_pi.isError();
	}   // startProcess

	/**
	 * Asserts we are running out of transaction.
	 *
	 * @param trx
	 */
	private final void assertOutOfTransaction(final ITrx trx, final String stage)
	{
		// Make sure we are really running out-of-transaction
		if (!trxManager.isNull(trx))
		{
			throw new IllegalStateException("Process wants to " + stage + " out-of-transaction but we are already in transaction."
					+ "\n Process: " + getClass()
					+ "\n Trx: " + trx);
		}
		final String threadInheritedTrxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isNull(threadInheritedTrxName))
		{
			throw new IllegalStateException("Process wants to " + stage + " out-of-transaction but we are running in a thread inherited transaction."
					+ "\n Process: " + getClass()
					+ "\n Thread inherited transaction: " + threadInheritedTrxName);
		}
	}

	/**
	 * Starts transaction.
	 *
	 * @param trxExisting existing transaction, if any
	 */
	private final void startTrx(final ITrx trxExisting)
	{
		// NOTE: in endTrx() method we shall do the reverse operations, BUT in reversed order than in startTrx().

		//
		// Start local transaction
		{
			final boolean isLocalTrx = trxManager.isNull(trxExisting);
			if (isLocalTrx)
			{
				final String trxNameLocal = trxManager.createTrxName(TRXNAME_Prefix);
				m_trx = trxManager.get(trxNameLocal, true);
				Check.assumeNotNull(m_trx, "Local transaction shall be created for {}", trxNameLocal); // shall not happen
			}
			else
			{
				m_trx = trxExisting;
			}
			this.m_trxIsLocal = isLocalTrx; // set it last, after we make sure everything is fine
		}

		//
		// Making sure that the current thread doesn't do any weird stuff while processing (02355)
		{
			DB.saveConstraints();
			DB.getConstraints().setActive(true)
					.setOnlyAllowedTrxNamePrefixes(true)
					.addAllowedTrxNamePrefix(m_trx.getTrxName())
					.setMaxTrx(1)
					.setMaxSavepoints(1)
					.setAllowTrxAfterThreadEnd(false);
			this.m_dbContraintsChanged = true;
		}

		//
		// Make sure we have our transaction set on thread level
		// It will be restored on endTrx();
		{
			final String threadInheritedTrxNameBkp = trxManager.setThreadInheritedTrxName(m_trx.getTrxName());
			this.m_trxNameThreadInheritedBackup = ImmutableReference.valueOf(threadInheritedTrxNameBkp);
		}
	}

	/**
	 * Ends current transaction, if a local transaction.
	 *
	 * This method can be called as many times as possible and even if the transaction was not started before.
	 *
	 * @param success
	 */
	private final void endTrx(final boolean success)
	{
		// NOTE: in endTrx() method we shall do the reverse operations, BUT in reversed order than in startTrx().

		//
		// Restore the thread inherited transaction name
		if (m_trxNameThreadInheritedBackup != null)
		{
			trxManager.setThreadInheritedTrxName(m_trxNameThreadInheritedBackup.getValue());
			m_trxNameThreadInheritedBackup = null;
		}

		//
		// Changing the constraints back to their default (02355)
		if (m_dbContraintsChanged)
		{
			DB.restoreConstraints();
			m_dbContraintsChanged = false;
		}

		//
		// Commit/Rollback local transaction
		// NOTE: we do this only if we really handled the transaction before
		final boolean wasTransactionHandled = m_trxIsLocal != null;
		if (wasTransactionHandled)
		{
			final boolean localTrx = m_trxIsLocal;
			if (localTrx)
			{
				if (success)
				{
					try
					{
						m_trx.commit(true);
					}
					catch (Exception e)
					{
						log.error("Commit failed.", e);
						m_pi.addSummary("Commit Failed.");
						m_pi.setError(true);
						// Set the ProcessInfo throwable only it is not already set.
						// Because if it's set, that's the main error and not this one.
						if (m_pi.getThrowable() == null)
						{
							m_pi.setThrowable(e);
						}
					}
				}
				else
				{
					m_trx.rollback();
				}
				m_trx.close();
				m_trx = null;
				m_trxIsLocal = null;
			}
		}
	}

	private final void setProcessResultOK(final String msg)
	{
		final String msgTrl;
		if (Check.isEmpty(msg, true))
		{
			msgTrl = msg;
		}
		else
		{
			msgTrl = msgBL.parseTranslation(getCtx(), msg);
		}

		final boolean error = false;
		m_pi.setSummary(msgTrl, error);

	}

	private final void setProcessResultError(final Throwable e)
	{
		//
		// Get error message
		String msg = e.getLocalizedMessage();
		if (Check.isEmpty(msg, true))
		{
			msg = e.toString();
		}

		//
		// Check if it's really an error
		final boolean error;
		if (e instanceof ProcessCanceledException)
		{
			error = false;
		}
		else
		{
			error = true;
		}

		//
		// Update ProcessInfo
		final String msgTrl = msgBL.parseTranslation(getCtx(), msg);
		m_pi.setSummary(msgTrl, error);
		if (error)
		{
			if (e.getCause() != null)
			{
				log.error(msg, e.getCause());
			}
			else
			{
				log.error(msg, e);
			}
			m_pi.setThrowable(e); // only if it's really an error
		}
	}

	/**
	 * Prepares process instance for execution.
	 * <ul>
	 * <li>loads parameters which were annotated with {@link Param}.
	 * <li>calls {@link #prepare()}
	 * </ul>
	 */
	private final void prepareProcess()
	{
		//
		// Load annotated process parameters
		final ProcessClassInfo processClassInfo = getProcessInfo().getProcessClassInfo();
		processClassInfo.loadParameterValues(this, getParameterAsIParams());

		//
		// Call the actual prepare custom implementation
		prepare();
	}

	/**
	 * Prepare process run. See {@link Param} for a way to avoid having to implement this method.
	 * <b>
	 * Here you would implement process preparation business logic (e.g. parameters retrieval).
	 * <b>
	 * If you want to run this method out of transaction, please annotate it with {@link RunOutOfTrx}. By default, this method is executed in transaction.
	 *
	 * @throws ProcessCanceledException in case there is a cancel request on prepare
	 * @throws RuntimeException in case of any failure
	 */
	protected void prepare()
	{
		// default implementation does nothing
	}

	/**
	 * Actual process business logic to be executed.
	 *
	 * This method is called after {@link #prepare()}.
	 *
	 * If you want to run this method out of transaction, please annotate it with {@link RunOutOfTrx}. By default, this method is executed in transaction.
	 *
	 * @return Message (variables are parsed)
	 * @throws ProcessCanceledException in case there is a cancel request on doIt
	 * @throws Exception if not successful e.g. <code>throw new AdempiereException ("@MyExceptionADMessage@");</code>
	 */
	abstract protected String doIt() throws Exception;

	/**
	 * Post process actions (outside trx). Please note that at this point the transaction is committed so you can't rollback. This method is useful if you need to do some custom work when the process
	 * complete the work (e.g. open some windows).
	 *
	 * @param success true if the process was success
	 * @since 3.1.4
	 */
	protected void postProcess(final boolean success)
	{
		// nothing at this level
	}

	/**
	 * Commit
	 *
	 * @deprecated suggested to use commitEx instead
	 */
	@Deprecated
	protected final void commit()
	{
		if (m_trx != null)
		{
			trxManager.commit(m_trx.getTrxName());
		}
	}	// commit

	/**
	 * Commit and throw exception if error
	 *
	 * @throws SQLException on commit error
	 * @deprecated Please consider not managing the transaction and using other APIs.
	 */
	@Deprecated
	protected final void commitEx() throws SQLException
	{
		if (m_trx != null)
			m_trx.commit(true);
	}

	/**
	 * Rollback.
	 *
	 * @deprecated Please consider not managing the transaction, throwing an exception or using other APIs.
	 */
	@Deprecated
	protected final void rollback()
	{
		if (m_trx != null)
			m_trx.rollback();
	}	// rollback

	/**************************************************************************
	 * Lock Object. Needs to be explicitly called. Unlock is automatic.
	 *
	 * @param po object
	 * @return true if locked
	 */
	protected final boolean lockObject(PO po)
	{
		// Unlock existing
		if (m_locked || m_lockedObject != null)
			unlockObject();
		// Nothing to lock
		if (po == null)
			return false;
		m_lockedObject = po;
		m_locked = m_lockedObject.lock();
		return m_locked;
	}	// lockObject

	/**
	 * Is an object Locked?
	 *
	 * @return true if object locked
	 */
	protected final boolean isLocked()
	{
		return m_locked;
	}	// isLocked

	/**
	 * Unlock Object. Is automatically called at the end of process.
	 *
	 * @return true if unlocked or if there was nothing to unlock
	 */
	protected final boolean unlockObject()
	{
		boolean success = true;
		if (m_locked || m_lockedObject != null)
		{
			success = m_lockedObject.unlock(null);
		}
		m_locked = false;
		m_lockedObject = null;
		return success;
	}	// unlock

	/**************************************************************************
	 * Get Process Info
	 *
	 * @return Process Info
	 */
	public final ProcessInfo getProcessInfo()
	{
		return m_pi;
	}   // getProcessInfo

	/**
	 * Get Properties
	 *
	 * @return context; never returns <code>null</code>
	 */
	@Override
	public final Properties getCtx()
	{
		return Env.coalesce(m_ctx);
	}   // getCtx

	/**
	 * Get Name/Title
	 *
	 * @return Name
	 */
	protected final String getName()
	{
		return m_pi.getTitle();
	}   // getName

	/**
	 * Get Process Instance
	 *
	 * @return Process Instance
	 */
	protected final int getAD_PInstance_ID()
	{
		return m_pi.getAD_PInstance_ID();
	}   // getAD_PInstance_ID

	/**
	 * Get Table_ID
	 *
	 * @return AD_Table_ID
	 */
	protected final int getTable_ID()
	{
		return m_pi.getTable_ID();
	}   // getRecord_ID

	/**
	 * Get Record_ID
	 *
	 * @return Record_ID
	 */
	protected final int getRecord_ID()
	{
		return m_pi.getRecord_ID();
	}   // getRecord_ID

	/**
	 * Retrieve underlying model for AD_Table_ID/Record_ID using current transaction (i.e. {@link #getTrxName()}).
	 *
	 * @param modelClass
	 * @return record; never returns null
	 * @throws AdempiereException if no model found
	 */
	public final <ModelType> ModelType getRecord(final Class<ModelType> modelClass)
	{
		String trxName = getTrxName();

		// In case the transaction is null, it's better to use the thread inherited trx marker.
		// This will cover the cases when the process runs out of transaction
		// but the transaction is managed inside the process implementation and this method is called from there.
		if(trxManager.isNull(trxName))
		{
			trxName = ITrx.TRXNAME_ThreadInherited;
		}

		return m_pi.getRecord(modelClass, trxName);
	}

	/**
	 * Get AD_User_ID
	 *
	 * @return AD_User_ID of Process owner or -1 if not found
	 */
	protected final int getAD_User_ID()
	{
		if (m_pi.getAD_User_ID() == null || m_pi.getAD_Client_ID() == null)
		{
			try
			{
				final I_AD_PInstance pinstance = retrievePInstance();
				if (pinstance != null && pinstance.getAD_PInstance_ID() > 0)
				{
					m_pi.setAD_User_ID(pinstance.getAD_User_ID());
					m_pi.setAD_Client_ID(pinstance.getAD_User_ID());
				}
			}
			catch (Exception e)
			{
				// make sure this method never fails (to keep the legacy contract)
				log.error("Failed loading AD_User_ID/AD_Client_ID from AD_PInstance. Ignored.", e);
			}
		}
		if (m_pi.getAD_User_ID() == null)
		{
			return -1;
		}
		return m_pi.getAD_User_ID().intValue();
	}   // getAD_User_ID

	/**
	 * Get AD_User_ID
	 *
	 * @return AD_User_ID of Process owner
	 */
	protected final int getAD_Client_ID()
	{
		if (m_pi.getAD_Client_ID() == null)
		{
			getAD_User_ID();	// sets also Client
			if (m_pi.getAD_Client_ID() == null)
				return 0;
		}
		return m_pi.getAD_Client_ID().intValue();
	}	// getAD_Client_ID

	/**************************************************************************
	 * Get Parameter
	 *
	 * @return parameter
	 */
	protected final ProcessInfoParameter[] getParameter()
	{
		return m_pi.getParameter();
	}	// getParameter

	/**
	 * @return the process parameters as IParams instance
	 */
	protected final IRangeAwareParams getParameterAsIParams()
	{
		return m_pi.getParameterAsIParams();
	}

	/**
	 * Sets if the process logs (if any) shall be displayed to user
	 *
	 * @param showProcessLogsPolicy
	 * @see ProcessInfo#setShowProcessLogs(ShowProcessLogs)
	 */
	protected final void setShowProcessLogs(final ShowProcessLogs showProcessLogsPolicy)
	{
		m_pi.setShowProcessLogs(showProcessLogsPolicy);
	}

	/**************************************************************************
	 * Add Log Entry, if our internal {@link ProcessInfo} reference is not <code>null</code>
	 *
	 * @param date date or null
	 * @param id record id or 0
	 * @param number number or null
	 * @param msg message or null
	 */
	public final void addLog(int id, Timestamp date, BigDecimal number, String msg)
	{
		if (m_pi != null)
			m_pi.addLog(id, date, number, msg);

		if (log.isDebugEnabled())
		{
			if (id == 0 && date == null && number == null)
			{
				log.debug("Log: {} - {} - {} - {}", id, date, number, msg);
			}
			else
			{
				log.debug("Log: {}", msg);
			}
		}
	}	// addLog

	/**
	 * Add Log, if the given <code>msg<code> is not <code>null</code>
	 *
	 * @param msg message
	 */
	@Override
	public final void addLog(final String msg, final Object... msgParameters)
	{
		if (msg != null)
		{
			addLog(0, null, null, StringUtils.formatMessage(msg, msgParameters));
		}
	}	// addLog

	/**************************************************************************
	 * Execute function
	 *
	 * @param className class
	 * @param methodName method
	 * @param args arguments
	 * @return result
	 */
	public final Object doIt(String className, String methodName, Object args[])
	{
		try
		{
			Class<?> clazz = Class.forName(className);
			Object object = clazz.newInstance();
			Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++)
			{
				if (methods[i].getName().equals(methodName))
					return methods[i].invoke(object, args);
			}

			return null;
		}
		catch (Exception ex)
		{
			log.error("doIt", ex);
			throw new AdempiereException(ex);
		}
	}	// doIt

	/**
	 * Lock Process Instance
	 */
	private final void lock()
	{
		log.debug("Locking AD_PInstance_ID={}", m_pi.getAD_PInstance_ID());
		try
		{
			Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_PInstance.class, getCtx(), ITrx.TRXNAME_None) // outside trx
					.addEqualsFilter(I_AD_PInstance.COLUMN_AD_PInstance_ID, m_pi.getAD_PInstance_ID())
					.create()
					.updateDirectly()
					.addSetColumnValue(I_AD_PInstance.COLUMNNAME_IsProcessing, true)
					.execute();
		}
		catch (Exception e)
		{
			log.error("Lock failed", e);
		}
	}   // lock

	/**
	 * Unlock Process Instance. Update Process Instance DB and write option return message.
	 */
	private final void unlock()
	{
		try
		{
			final I_AD_PInstance mpi = retrievePInstance();
			if (mpi == null || mpi.getAD_PInstance_ID() <= 0)
			{
				throw new AdempiereException("Did not find PInstance " + m_pi.getAD_PInstance_ID());
			}
			mpi.setWhereClause(m_pi.getWhereClause()); // make sure the WhereClause is set
			mpi.setIsProcessing(false);
			mpi.setResult(m_pi.isError() ? MPInstance.RESULT_ERROR : MPInstance.RESULT_OK);
			mpi.setErrorMsg(m_pi.getSummary());
			InterfaceWrapperHelper.save(mpi);

			log.debug("Unlocked: {}", mpi);

			ProcessInfoUtil.saveLogToDB(m_pi);
		}
		catch (Throwable e)
		{
			// NOTE: it's very important this method to never throw exception.

			log.error("Unlock failed", e);
		}
	}   // unlock

	/**
	 * @return {@link I_AD_PInstance} (out of transaction) or null if not found
	 */
	private final I_AD_PInstance retrievePInstance()
	{
		final int adPInstanceId = m_pi.getAD_PInstance_ID();
		if (adPInstanceId <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(getCtx(), adPInstanceId, I_AD_PInstance.class, ITrx.TRXNAME_None);
	}

	/**
	 * Return the main transaction of the current process.
	 *
	 * @return the transaction name
	 */
	public final String get_TrxName()
	{
		if (m_trx != null)
			return m_trx.getTrxName();
		return ITrx.TRXNAME_None;
	}	// get_TrxName

	// org.adempiere.model.IContextAware#getTrxName()
	@Override
	public final String getTrxName()
	{
		return get_TrxName();
	}

	// metas: begin
	public final String getTableName()
	{
		return m_pi.getTableNameOrNull();
	}

	protected final <T> IQueryBuilder<T> retrieveSelectedRecordsQueryBuilder(final Class<T> recordType)
	{
		return retrieveSelectedRecordsQueryBuilder(recordType, getTrxName());
	}

	protected final <T> IQueryBuilder<T> retrieveSelectedRecordsQueryBuilder(final Class<T> recordType, final String trxName)
	{
		final IQueryFilter<T> selectedRecordsQueryFilter = getProcessInfo().getQueryFilter();
		return Services.get(IQueryBL.class)
				.createQueryBuilder(recordType, getCtx(), trxName)
				.filter(selectedRecordsQueryFilter)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
	}

	/**
	 * Sets the record to be selected in window, after this process is executed (applies only when the process was started from a user window).
	 *
	 * @param recordToSelectAfterExecution
	 */
	protected final void setRecordToSelectAfterExecution(final ITableRecordReference recordToSelectAfterExecution)
	{
		m_pi.setRecordToSelectAfterExecution(recordToSelectAfterExecution);
	}


	/**
	 * Exceptions to be thrown if we want to cancel the process run.
	 *
	 * If this exception is thrown:
	 * <ul>
	 * <li>the process will be terminated right away
	 * <li>transaction (if any) will be rolled back
	 * <li>process summary message will be set from this exception message (i.e. {@link ProcessInfo#getSummary()})
	 * <li>process will NOT be flagged as error (i.e. {@link ProcessInfo#isError()} will return <code>false</code>)
	 * </ul>
	 *
	 * @author tsa
	 */
	public static final class ProcessCanceledException extends AdempiereException
	{
		private static final long serialVersionUID = 1L;

		@VisibleForTesting
		public static final String MSG_Canceled = "Canceled";

		public ProcessCanceledException()
		{
			super("@" + MSG_Canceled + "@");
		}
	}
}   // SvrProcess
