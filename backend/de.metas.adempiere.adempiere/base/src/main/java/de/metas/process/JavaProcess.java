package de.metas.process;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.api.RangeAwareParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ImmutableReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.Profile;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.process.ProcessExecutionResult.ShowProcessLogs;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/**
 * Java Process base class.
 *
 * Also see
 * <ul>
 * <li>{@link IProcessPrecondition} if you need to dynamically decide whenever a process shall be available in the Gear.
 * <li>{@link IProcessDefaultParametersProvider} if you want to provide some default values for parameters, when the UI parameters dialog is loaded
 * <li>{@link IProcessParametersCallout} if you want to be notified of users' process parameter changes
 * <li>{@link RunOutOfTrx} which is an annotation for the {@link #prepare()} and {@link #doIt()} method
 * <li>{@link Process} annotation if you add more info or constraints about how the process shall be executed
 * <li>{@link Param} annotation if you want to avoid implementing the {@link #prepare()} method
 * <li>{@link Profile} annotation if you want want to show/hide this process to/from Gear based on spring profiles
 * <li>{@link ClientOnlyProcess} annotation if you want to mark the process as client only
 * </ul>
 *
 *
 * @author authors of earlier versions of this class are: Jorg Janke, Teo Sarca
 * @author metas-dev <dev@metasfresh.com>
 */
public abstract class JavaProcess implements ILoggable, IContextAware
{
	// services
	protected final transient Logger log = LogManager.getLogger(getClass());
	private static final Logger slogger = LogManager.getLogger(JavaProcess.class);
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final ThreadLocal<Object> currentInstanceHolder = new ThreadLocal<>();

	private Properties _ctx;
	private ProcessInfo _processInfo;

	private final IProcessParametersCallout parametersCallout;

	//
	// Transaction management
	/** Process Main transaction */
	@Nullable private ITrx m_trx = ITrx.TRX_None;
	private Boolean m_trxIsLocal;
	@Nullable private ImmutableReference<String> m_trxNameThreadInheritedBackup;
	private boolean m_dbConstraintsChanged = false;
	/** Transaction name prefix (in case of local transaction) */
	private static final String TRXNAME_Prefix = "JavaProcess";

	// Common Return Messages
	protected static final String MSG_SaveErrorRowNotFound = "@SaveErrorRowNotFound@";
	protected static final String MSG_InvalidArguments = "@InvalidArguments@";
	public static final String MSG_OK = "OK";
	/**
	 * Process failed error message. To be returned from {@link #doIt()}.
	 *
	 * In case it's returned the process will be rolled back.
	 */
	protected static final String MSG_Error = "@Error@";

	/** Internal cache for ProcessClassInfo */
	private final transient ImmutableMap<ArrayKey, Field> _fieldsIndexedByFieldKey;

	protected JavaProcess()
	{
		parametersCallout = this instanceof IProcessParametersCallout ? (IProcessParametersCallout)this : null;
		_fieldsIndexedByFieldKey = ProcessClassInfo.retrieveParameterFieldsIndexedByFieldKey(getClass());
	}

	@Override
	public final String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("processInfo", _processInfo)
				.toString();
	}

	/**
	 * @return current active {@link JavaProcess}; never returns null
	 */
	public static <T extends JavaProcess> T currentInstance()
	{
		@SuppressWarnings("unchecked")
		final T currentInstances = (T)currentInstanceHolder.get();
		if (currentInstances == null)
		{
			throw new AdempiereException("No active process found in this thread");
		}
		return currentInstances;
	}

	/**
	 * Set the current process instance, assuming that none is set right now. If one is already set, then throw an exception.
	 */
	public static IAutoCloseable temporaryChangeCurrentInstance(@NonNull final Object instance)
	{
		final boolean overrideCurrentInstance = false;
		return temporaryChangeCurrentInstance(instance, overrideCurrentInstance);
	}

	/**
	 * Set the current process instance. If one is already set, then overwrite it.
	 */
	public static IAutoCloseable temporaryChangeCurrentInstanceOverriding(final Object instance)
	{
		final boolean overrideCurrentInstance = true;
		return temporaryChangeCurrentInstance(instance, overrideCurrentInstance);
	}

	/**
	 *
	 * @param overrideCurrentInstance of {@code false} and there is already an instance set, then throw an exception.
	 */
	private static IAutoCloseable temporaryChangeCurrentInstance(@NonNull final Object instance, final boolean overrideCurrentInstance)
	{
		final Object previousInstance = currentInstanceHolder.get();
		if (!overrideCurrentInstance && previousInstance != null && !Util.same(previousInstance, instance))
		{
			throw new AdempiereException("Changed current instance not allowed when there is a currently active one"
					+ "\n Current active: " + previousInstance
					+ "\n New: " + instance);
		}

		//
		// Actually set the the new instance as current one
		currentInstanceHolder.set(instance);
		slogger.debug("currentInstance: Temporary changed to {} (previous={})", instance, previousInstance);

		return new IAutoCloseable()
		{
			private boolean restored = false;

			@Override
			public String toString()
			{
				return MoreObjects.toStringHelper("currentInstanceRestorer")
						.omitNullValues()
						.add("alreadyRestored", restored)
						.add("temporaryInstance", instance)
						.add("previousInstance", previousInstance)
						.toString();
			}

			@Override
			public void close()
			{
				// Make sure we are not restoring more then once
				if (restored)
				{
					slogger.warn("currentInstance: Skip restoring current instance because we already did that: {}", this);
					return;
				}
				restored = true;

				// Actually restore it
				final Object currentInstanceFound = currentInstanceHolder.get();
				currentInstanceHolder.set(previousInstance);
				slogger.debug("currentInstance: Restored previous instance: {} (from={})", previousInstance, instance);

				// Warn if the current instance expected it's not the one the we temporary set. Shall not happen.
				if (!Objects.equal(currentInstanceFound, instance))
				{
					slogger.warn("Invalid current process instance found while restoring the current instance"
							+ "\n Current instance found: {}"
							+ "\n Current instance expected: {}"
							+ "\n => Current instance restored: {}", currentInstanceFound, instance, currentInstanceHolder.get());
				}
			}
		};
	}

	/**
	 * Note: This method shall be called by the framework.
	 *
	 * @param pi Process Info
	 * @param trx existing/inherited transaction if any
	 */
	public synchronized final void startProcess(final ProcessInfo pi, final ITrx trx)
	{
		Check.assume(this == currentInstance(), "This process shall be the current active instance: {}", this);

		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_AD_PInstance.Table_Name, pi.getPinstanceId());
				final MDCCloseable ignored1 = TableRecordMDC.putTableRecordReference(I_AD_Process.Table_Name, pi.getAdProcessId()))
		{
			// Initialize process instance state from given process instance info.
			init(pi);

			// Trx: we are setting it to null to be consistent with running prepare() out-of-transaction
			// Later we will set the actual transaction or we will start a local transaction.
			m_trx = ITrx.TRX_None;

			boolean success = false;
			try (final IAutoCloseable ignored2 = Loggables.temporarySetLoggable(this);
					final IAutoCloseable ignored3 = Env.switchContext(_ctx) // FRESH-314: make sure our derived context will always be used
			)
			{
				//
				// Prepare out of transaction, if needed
				final ProcessClassInfo processClassInfo = getProcessInfo().getProcessClassInfo();
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
			}

			//
			// outside transaction processing [ teo_sarca, 1646891 ]
			postProcess(!getResult().isError());

			// NOTE: we shall check again the result because it might be changed by postProcess()
			getResult().propagateErrorIfAny();
		}   // startProcess
	}

	/**
	 * Initialize this process from given process instance info.
	 *
	 * NOTE: don't call this method directly. Only the API is allowed to call it.
	 *
	 * @param pi process instance info
	 */
	public final void init(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "ProcessInfo not null");

		// Do nothing if already initialized exactly with same info
		if (Util.same(this._processInfo, pi))
		{
			return;
		}

		Check.assumeNull(_processInfo, "ProcessInfo not already configured: {}", this);
		_processInfo = pi;

		// Preparation
		// FRESH-314: store #AD_PInstance_ID in a copied context (shall only live as long as this process does).
		// We might want to access this information (currently in AD_ChangeLog)
		// Note: using copyCtx because derviveCtx is not safe with Env.switchContext()
		_ctx = Env.copyCtx(pi.getCtx());
		Env.setContext(_ctx, Env.CTXNAME_AD_PInstance_ID, PInstanceId.toRepoId(pi.getPinstanceId()));

		//
		// Load annotated parameters
		loadParametersFromContext(false);
	}

	/**
	 * Initialize this process from given preconditions context.
	 *
	 * NOTE: don't call this method directly. Only the API is allowed to call it.
	 *
	 * @param context preconditions context
	 */
	@OverridingMethodsMustInvokeSuper
	protected void init(final IProcessPreconditionsContext context)
	{
		// nothing at this level
	}

	private Field getAnnotatedParamField(final ProcessClassParamInfo paramInfo)
	{
		final ArrayKey fieldKey = paramInfo.getFieldKey();
		return ((Map<ArrayKey, Field>)_fieldsIndexedByFieldKey).get(fieldKey);
	}

	/**
	 * Load "@Param" annotated parameters from {@link ProcessInfo}.
	 */
	@OverridingMethodsMustInvokeSuper
	protected void loadParametersFromContext(final boolean failIfNotValid)
	{
		final ProcessInfo pi = getProcessInfo();
		final ProcessClassInfo processClassInfo = pi.getProcessClassInfo();

		// No parameters => nothing to do
		final Collection<ProcessClassParamInfo> parameterInfos = processClassInfo.getParameterInfos();
		if (parameterInfos.isEmpty())
		{
			return;
		}

		//
		// Iterate all process class info parameters and try to update the corresponding field
		final IRangeAwareParams source = pi.getParameterAsIParams();
		parameterInfos.forEach(paramInfo -> {
			final Field processField = getAnnotatedParamField(paramInfo);
			paramInfo.loadParameterValue(this, processField, source, failIfNotValid);
		});
	}

	/**
	 * Load process autowired parameter from given <code>source</code>.
	 *
	 * If the parameter value is not valid (e.g. mandatory required but was null),
	 * this method won't fail but will simply not set the value.
	 */
	public final void loadParameterValueNoFail(final String parameterName, final IRangeAwareParams source)
	{
		final Collection<ProcessClassParamInfo> parameterInfos = getParameterInfos(parameterName);

		final boolean failIfNotValid = false;

		// Iterate all process class info parameters and try to update the corresponding field
		// No parameters => nothing to do
		parameterInfos.forEach(paramInfo -> {
			final Field processField = getAnnotatedParamField(paramInfo);
			paramInfo.loadParameterValue(this, processField, source, failIfNotValid);
		});

		//
		// Fire parameters callout if any
		if (parametersCallout != null)
		{
			parametersCallout.onParameterChanged(parameterName);
		}
	}

	public final boolean hasParametersCallout()
	{
		return parametersCallout != null;
	}

	private Collection<ProcessClassParamInfo> getParameterInfos(final String parameterName)
	{
		final ProcessInfo pi = getProcessInfo();
		final ProcessClassInfo processClassInfo = pi.getProcessClassInfo();

		return processClassInfo.getParameterInfos(parameterName);
	}

	public final IRangeAwareParams getParametersFromAnnotatedFields()
	{
		final HashMap<String, Object> values = new HashMap<>();
		final HashMap<String, Object> valuesTo = new HashMap<>();

		final ProcessInfo pi = getProcessInfo();
		final ProcessClassInfo processClassInfo = pi.getProcessClassInfo();
		for (final ProcessClassParamInfo paramInfo : processClassInfo.getParameterInfos())
		{
			final Object fieldValue = getAnnotatedParamFieldValue(paramInfo);
			if (paramInfo.isParameterTo())
			{
				valuesTo.put(paramInfo.getParameterName(), fieldValue);
			}
			else
			{
				values.put(paramInfo.getParameterName(), fieldValue);
			}
		}

		return RangeAwareParams.ofMaps(values, valuesTo);
	}

	@Nullable
	private Object getAnnotatedParamFieldValue(final ProcessClassParamInfo paramInfo)
	{
		final Field field = getAnnotatedParamField(paramInfo);
		if (field == null)
		{
			return null;
		}

		try
		{
			if(!field.isAccessible())
			{
				field.setAccessible(true);
			}

			return field.get(this);
		}
		catch (final Exception ex)
		{
			log.warn("Cannot get field's value: {}", field, ex);
			return null;
		}
	}

	/**
	 * Asserts we are running out of transaction.
	 */
	private void assertOutOfTransaction(final ITrx trx, final String stage)
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
	private void startTrx(final ITrx trxExisting)
	{
		// NOTE: in endTrx() method we shall do the reverse operations, BUT in reversed order than in startTrx().

		//
		// Start local transaction
		{
			final boolean isLocalTrx = trxManager.isNull(trxExisting);
			if (isLocalTrx)
			{
				final String trxNameLocal = trxManager.createTrxName(TRXNAME_Prefix + "_" + getClass().getSimpleName());
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
			this.m_dbConstraintsChanged = true;
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
	 */
	private void endTrx(final boolean success)
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
		if (m_dbConstraintsChanged)
		{
			DB.restoreConstraints();
			m_dbConstraintsChanged = false;
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
					catch (final Exception e)
					{
						log.error("Commit failed.", e);
						final ProcessExecutionResult result = getResult();
						result.markAsError("Commit Failed.");
						// Set the ProcessInfo throwable only it is not already set.
						// Because if it's set, that's the main error and not this one.
						result.setThrowableIfNotSet(e);
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

	private void setProcessResultOK(final String msg)
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

		getResult().markAsSuccess(msgTrl);
	}

	private void setProcessResultError(final Throwable e)
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
		final boolean error = !(e instanceof ProcessCanceledException);

		//
		// Update ProcessInfo's result
		final ProcessExecutionResult result = getResult();
		final String msgTrl = msgBL.parseTranslation(getCtx(), msg);

		if (!error)
		{
			result.markAsSuccess(msgTrl);
		}
		else
		{
			final Throwable cause = AdempiereException.extractCause(e);
			result.markAsError(msgTrl, cause);
			log.error(msg, cause);
		}
	}

	/**
	 * Prepares process instance for execution.
	 * <ul>
	 * <li>loads parameters which were annotated with {@link Param}.
	 * <li>calls {@link #prepare()}
	 * </ul>
	 */
	private void prepareProcess()
	{
		// Load annotated process parameters,
		// but this time we need to fail in case some parameters are not valid.
		loadParametersFromContext(true);

		//
		// Call the actual prepare custom implementation
		prepare();
	}

	/**
	 * Prepare process run.
	 * This method is called after the {@link Param} annotated parameters were loaded.
	 *
	 * <p>
	 * Here you would implement process preparation business logic (e.g. parameters retrieval).
	 * <p>
	 * If you want to run this method out of transaction, please annotate it with {@link RunOutOfTrx}. By default, this method is executed in transaction.<br>
	 * <b>Important:</b> This method is also run out of transaction of only the {@link #doIt()} method is annotated like this.
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
	 * If you want to run this method out of transaction, please annotate it with {@link RunOutOfTrx}.
	 * By default, this method is executed in transaction.
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
	 * Schedule runnable to be executed whenever the current transaction is committed.
	 * If there is no current transaction, the runnable will be executed right away.
	 */
	protected final void runAfterCommit(@NonNull final Runnable runnable)
	{
		trxManager.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> runnable.run());
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
		{
			m_trx.commit(true);
		}
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
		{
			m_trx.rollback();
		}
	}

	/**
	 * @return Process Info; never returns null
	 */
	protected final ProcessInfo getProcessInfo()
	{
		final ProcessInfo processInfo = getProcessInfoOrNull();
		if (processInfo == null)
		{
			throw new AdempiereException("ProcessInfo not configured for " + this);
		}
		return processInfo;
	}

	private ProcessInfo getProcessInfoOrNull()
	{
		return _processInfo;
	}

	protected final ProcessExecutionResult getResult()
	{
		return getProcessInfo().getResult();
	}

	@Nullable
	protected final ProcessExecutionResult getResultOrNull()
	{
		final ProcessInfo processInfo = getProcessInfoOrNull();
		return processInfo != null ? processInfo.getResult() : null;
	}

	/**
	 * @return context; never returns <code>null</code>
	 */
	@Override
	public final Properties getCtx()
	{
		final Properties ctx = _ctx;
		if (ctx == null)
		{
			log.warn("No context configured for {}. Returning global context", this);
			return Env.getCtx();
		}

		return ctx;
	}

	/**
	 * Get Name/Title
	 *
	 * @return Name
	 */
	protected final String getName()
	{
		return getProcessInfo().getTitle();
	}   // getName

	public final PInstanceId getPinstanceId()
	{
		return getProcessInfo().getPinstanceId();
	}

	/**
	 * Get Table_ID
	 *
	 * @return AD_Table_ID
	 */
	protected final int getTable_ID()
	{
		return getProcessInfo().getTable_ID();
	}   // getRecord_ID

	/**
	 * Get Record_ID
	 *
	 * @return Record_ID
	 */
	protected final int getRecord_ID()
	{
		return getProcessInfo().getRecord_ID();
	}   // getRecord_ID

	/**
	 * Retrieve underlying model for AD_Table_ID/Record_ID using current transaction (i.e. {@link #getTrxName()}).
	 *
	 * @return record; never returns null
	 * @throws AdempiereException if no model found
	 * @deprecated use proper Repository
	 */
	@Deprecated
	protected final <ModelType> ModelType getRecord(final Class<ModelType> modelClass)
	{
		String trxName = getTrxName();

		// In case the transaction is null, it's better to use the thread inherited trx marker.
		// This will cover the cases when the process runs out of transaction
		// but the transaction is managed inside the process implementation and this method is called from there.
		if (trxManager.isNull(trxName))
		{
			trxName = ITrx.TRXNAME_ThreadInherited;
		}

		return getProcessInfo().getRecord(modelClass, trxName);
	}

	/** @return selected included row IDs of current single selected document */
	protected final <T> Set<Integer> getSelectedIncludedRecordIds(final Class<T> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return getProcessInfo().getSelectedIncludedRecords().stream()
				.filter(recordRef -> recordRef.getTableName().equals(tableName))
				.map(TableRecordReference::getRecord_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return selected included rows of current single selected document */
	protected final <T> List<T> getSelectedIncludedRecords(final Class<T> modelClass)
	{
		final Set<Integer> recordIds = getSelectedIncludedRecordIds(modelClass);
		if (recordIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelClass);
		return Services.get(IQueryBL.class)
				.createQueryBuilder(modelClass)
				.addInArrayFilter(keyColumnName, recordIds)
				.orderBy()
				.addColumn(keyColumnName)
				.endOrderBy()
				.create()
				.list(modelClass);
	}

	/**
	 * @return AD_User_ID of Process owner
	 */
	protected final int getAD_User_ID()
	{
		return getProcessInfo().getAD_User_ID();
	}

	/**
	 * @return process owner
	 */
	protected final UserId getUserId()
	{
		return getProcessInfo().getUserId();
	}

	protected final ClientId getClientId()
	{
		return getProcessInfo().getClientId();
	}

	/**
	 * @deprecated please use {@link #getClientID()}.
	 */
	@Deprecated
	protected final int getAD_Client_ID()
	{
		return getProcessInfo().getAD_Client_ID();
	}

	protected final OrgId getOrgId()
	{
		return getProcessInfo().getOrgId();
	}

	/**
	 * @return AD_Client_ID of Process owner
	 */
	protected final ClientId getClientID()
	{
		return getProcessInfo().getClientId();
	}

	/**
	 * Gets parameters.
	 *
	 * @return parameters
	 */
	protected final List<ProcessInfoParameter> getParameters()
	{
		return getProcessInfo().getParameter();
	}

	/**
	 * Gets parameters as array.
	 *
	 * Please consider using {@link #getParameters()}.
	 *
	 * @return parameters array
	 */
	protected final ProcessInfoParameter[] getParametersAsArray()
	{
		final List<ProcessInfoParameter> parameters = getProcessInfo().getParameter();
		return parameters.toArray(new ProcessInfoParameter[parameters.size()]);
	}

	/**
	 * @return the process parameters as IParams instance
	 */
	protected final IRangeAwareParams getParameterAsIParams()
	{
		return getProcessInfo().getParameterAsIParams();
	}

	/**
	 * Sets if the process logs (if any) shall be displayed to user
	 */
	protected final void setShowProcessLogs(final ShowProcessLogs showProcessLogsPolicy)
	{
		getResult().setShowProcessLogs(showProcessLogsPolicy);
	}

	/**************************************************************************
	 * Add Log Entry
	 *
	 * @param id record id or 0
	 * @param date date or null
	 * @param number number or null
	 * @param msg message or null
	 */
	public final void addLog(final int id, final Timestamp date, final BigDecimal number, final String msg)
	{
		getResult().addLog(id, date, number, msg);
	}	// addLog

	/**
	 * Add Log, if the given <code>msg<code> is not <code>null</code>
	 *
	 * @param msg message
	 */
	@Override
	public final ILoggable addLog(final String msg, final Object... msgParameters)
	{
		if (msg != null)
		{
			addLog(0, SystemTime.asTimestamp(), null, StringUtils.formatMessage(msg, msgParameters));
		}
		return this;
	}	// addLog

	/**
	 * Return the main transaction of the current process.
	 *
	 * @return the transaction name
	 */
	@Nullable
	public final String get_TrxName()
	{
		final ITrx trx = m_trx;
		return trx == null ? ITrx.TRXNAME_None : trx.getTrxName();
	}

	// org.adempiere.model.IContextAware#getTrxName()
	@Override
	@Nullable
	public final String getTrxName()
	{
		return get_TrxName();
	}

	@Nullable
	protected final String getTableName()
	{
		return getProcessInfo().getTableNameOrNull();
	}

	/**
	 * Retrieves the records which where selected and attached to this process execution, i.e.
	 * <ul>
	 * <li>if there is any {@link ProcessInfo#getQueryFilterOrElse(IQueryFilter)} that will be used to fetch the records
	 * <li>else if the single record is set ({@link ProcessInfo}'s AD_Table_ID/Record_ID) that will will be used
	 * <li>else an exception is thrown
	 * </ul>
	 *
	 * @return query builder which will provide selected record(s)
	 */
	protected final <ModelType> IQueryBuilder<ModelType> retrieveSelectedRecordsQueryBuilder(final Class<ModelType> modelClass)
	{
		final ProcessInfo pi = getProcessInfo();
		final String tableName = pi.getTableNameOrNull();
		final int singleRecordId = pi.getRecord_ID();

		final IContextAware contextProvider = PlainContextAware.newWithThreadInheritedTrx(getCtx());
		final IQueryBuilder<ModelType> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(modelClass, tableName, contextProvider);

		//
		// Try fetching the selected records from AD_PInstance's WhereClause.
		final IQueryFilter<ModelType> selectionQueryFilter = pi.getQueryFilterOrElse(null);
		if (selectionQueryFilter != null)
		{
			queryBuilder.filter(selectionQueryFilter)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient();
		}
		//
		// Try fetching the single selected record from AD_PInstance's AD_Table_ID/Record_ID.
		else if (tableName != null && singleRecordId >= 0)
		{
			final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
			queryBuilder.addEqualsFilter(keyColumnName, singleRecordId);
			// .addOnlyActiveRecordsFilter() // NOP, return it as is
			// .addOnlyContextClient(); // NOP, return it as is
		}
		else
		{
			throw new AdempiereException("@NoSelection@");
		}

		return queryBuilder;
	}

	/**
	 * Exceptions to be thrown if we want to cancel the process run.
	 *
	 * If this exception is thrown:
	 * <ul>
	 * <li>the process will be terminated right away
	 * <li>transaction (if any) will be rolled back
	 * <li>process summary message will be set from this exception message
	 * <li>process will NOT be flagged as error
	 * </ul>
	 */
	public static final class ProcessCanceledException extends AdempiereException
	{
		@VisibleForTesting
		public static final AdMessageKey MSG_Canceled = AdMessageKey.of("Canceled");

		public ProcessCanceledException()
		{
			super(MSG_Canceled);
		}
	}

	protected final <T> int createSelection(@NonNull final IQueryBuilder<T> queryBuilder, @NonNull final PInstanceId pinstanceId)
	{
		return queryBuilder
				.create()
				.setRequiredAccess(Access.READ)
				.createSelection(pinstanceId);
	}

	protected final UserId getLoggedUserId()
	{
		return Env.getLoggedUserId();
	}
}
