package org.adempiere.ad.modelvalidator;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Client;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * Wrapping class which introspect an object, identifies it's pointcuts (ModelChange, DocValidate etc) and maps them to {@link ModelValidator} interface.
 *
 * @author tsa
 *
 */

@EqualsAndHashCode(of = { "annotatedObject", "descriptor", "clientId" })
/* package */class AnnotatedModelInterceptor implements IModelInterceptor
{
	private static final transient Logger logger = LogManager.getLogger(AnnotatedModelInterceptor.class);

	private final AnnotatedModelInterceptorDescriptor descriptor;
	private final transient Object annotatedObject;
	private ClientId clientId;

	AnnotatedModelInterceptor(@NonNull final Object annotatedObject)
	{
		this.annotatedObject = annotatedObject;
		this.descriptor = new AnnotatedModelInterceptorDescriptorBuilder(annotatedObject.getClass())
				.build();
	}

	@Override
	public String toString()
	{
		return "annotated[" + annotatedObject.getClass().getName() + "]";
	}

	/**
	 *
	 * @return true if it has no initializers, no pointcuts, no nothing
	 */
	public boolean isEmpty()
	{
		return descriptor.isEmpty();
	}

	@Override
	public void initialize(final IModelValidationEngine engine, final I_AD_Client client)
	{
		if (client != null)
		{
			this.clientId = ClientId.ofRepoId(client.getAD_Client_ID());
		}

		bindPointcuts(engine);

		//
		// Execute initializers
		for (final InterceptorInit init : descriptor.getInitializers())
		{
			Object[] params = new Object[] {};
			if (init.isMethodRequiresEngine())
			{
				params = new Object[] { engine };
			}

			final Method method = init.getMethod();
			try
			{
				// Make sure the method is accessible
				if (!method.isAccessible())
				{
					method.setAccessible(true);
				}
				// Execute
				method.invoke(annotatedObject, params);
			}
			catch (final Exception e)
			{
				throw new AdempiereException("Cannot initialize " + annotatedObject + ". Initializer " + init + " failed."
						+ "\n Method: " + method
						+ "\n Params:" + Arrays.toString(params)
						+ "\n Method Descriptor: " + init, Throwables.getRootCause(e));
			}

			logger.debug("Initializer {} executed successfully.", init);
		}
	}

	private void bindPointcuts(@NonNull final IModelValidationEngine engine)
	{
		logger.debug("Binding pointcuts for {}", annotatedObject);
		for (final PointcutKey key : descriptor.getPointcutKeys())
		{
			final Set<Pointcut> list = descriptor.getPointcuts(key);
			if (list.isEmpty())
			{
				continue;
			}

			logger.debug("Binding pointcuts for {} on {}", annotatedObject, key);

			switch (key.getType())
			{
				case ModelChange:
					engine.addModelChange(key.getTableName(), this);
					break;
				case DocValidate:
					engine.addDocValidate(key.getTableName(), this);
					break;
				default:
					throw new AdempiereException("Unknown PointcutType " + key.getType());
			}
		}
		logger.debug("Binding pointcuts for {} done.", annotatedObject);
	}

	@Override
	public int getAD_Client_ID()
	{
		return ClientId.toRepoId(clientId);
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing
	}

	@Override
	public final void onModelChange(final Object model, final ModelChangeType changeType)
	{
		execute(PointcutType.ModelChange, model, changeType.toInt());
	}

	@Override
	public final void onDocValidate(final Object model, final DocTimingType timing)
	{
		execute(PointcutType.DocValidate, model, timing.toInt());
	}

	private void execute(final PointcutType type, final Object po, final int timing)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(po);
		final PointcutKey key = PointcutKey.of(tableName, type);
		final ImmutableSet<Pointcut> pointcuts = descriptor.getPointcuts(key);
		if (pointcuts.isEmpty())
		{
			return;
		}

		for (final Pointcut pointcut : pointcuts)
		{
			execute(pointcut, po, timing);
		}
	}

	private void execute(final Pointcut pointcut, final Object po, final int timing)
	{
		//
		// Check timings
		final Set<Integer> timings = pointcut.getTimings();
		if (!timings.contains(timing))
		{
			return;
		}

		//
		// Check if UserAction required (i.e. user manually changed this record from a window)
		if (pointcut.isOnlyIfUIAction())
		{
			final boolean isUIAction = InterfaceWrapperHelper.isUIAction(po);
			if (!isUIAction)
			{
				return;
			}
		}

		//
		// Check if we shall skip when copying
		if (pointcut.isSkipIfCopying())
		{
			final boolean isCopying = InterfaceWrapperHelper.isCopy(po);
			if (isCopying)
			{
				return;
			}
		}

		//
		// Check changed columns (only if timing is before/after change).
		// #105 FRESH-442: only check for changed columns on "change". For "new" and "delete", execute the pointcut.
		// Else, does not make sense.
		if (isTimingChange(timing))
		{
			final Set<String> columnsToCheckForChanges = pointcut.getColumnNamesToCheckForChanges();
			if (!columnsToCheckForChanges.isEmpty())
			{
				// none of our columns had changed; skip
				if (!InterfaceWrapperHelper.isValueChanged(po, columnsToCheckForChanges))
				{
					return;
				}
			}
		}

		//
		// Execute the method now
		if (!pointcut.isAfterCommit())
		{
			executeNow(po, pointcut, timing);
		}
		//
		// ... or schedule it to be executed after commit
		else
		{
			logger.trace("Scheduling to be executed after commit: {}", pointcut);
			final String trxName = InterfaceWrapperHelper.getTrxName(po);

			Services.get(ITrxManager.class)
					.getTrxListenerManagerOrAutoCommit(trxName)
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.additionalToStringInfo(() -> MoreObjects.toStringHelper(this)
							.add("pointcut", pointcut)
							.add("po", po)
							.add("timing", timing)
							.toString())
					.invokeMethodJustOnce(true)
					.registerHandlingMethod(transaction -> {

						executeNow(po, pointcut, timing);
						InterfaceWrapperHelper.setTrxName(po, ITrx.TRXNAME_ThreadInherited);
					});
		}
	}


	private void executeNow(
			@NonNull final Object po,
			@NonNull final Pointcut pointcut,
			final int timing)
	{
		if (AnnotatedModelInterceptorDisabler.get().isDisabled(pointcut))
		{
			logger.info("Not executing pointCut because it is disabled via sysconfig (name-prefix={}); pointcut={}",
					AnnotatedModelInterceptorDisabler.SYS_CONFIG_NAME_PREFIX, pointcut);
			return;
		}

		final Object model = InterfaceWrapperHelper.create(po, pointcut.getModelClass());
		try
		{
			executeNow0(model, pointcut, timing);
		}
		catch (final Exception e)
		{
			throw  appendAndLogHowtoDisableMessage(e, pointcut);
		}
	}

	private static AdempiereException appendAndLogHowtoDisableMessage(
			@NonNull final Exception e,
			@NonNull final Pointcut pointcut)
	{
		final String parameterName = "HowtoDisableModelInterceptor";
		final AdempiereException ae = AdempiereException.wrapIfNeeded(e);

		if (!ae.hasParameter(parameterName))
		{
			final String howtoDisableMsg = AnnotatedModelInterceptorDisabler.createHowtoDisableMessage(pointcut);

			logger.error(howtoDisableMsg, e);
			ae.setParameter(parameterName, howtoDisableMsg);
		}
		return ae;
	}

	private void executeNow0(
			@NonNull final Object model,
			@NonNull final Pointcut pointcut,
			final int timing) throws IllegalAccessException, InvocationTargetException
	{
		final Method method = pointcut.getMethod();

		// Make sure the method is accessible
		if (!method.isAccessible())
		{
			method.setAccessible(true);
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		if (pointcut.isMethodRequiresTiming())
		{
			final Object timingParam = pointcut.convertToMethodTimingParameterType(timing);
			method.invoke(annotatedObject, model, timingParam);
		}
		else
		{
			method.invoke(annotatedObject, model);
		}

		logger.trace("Executed in {}: {} (timing={}) on {}", stopwatch, pointcut, timing, model);
	}

	/**
	 * @return true if timing is change (before, after)
	 */
	private static boolean isTimingChange(final int timing)
	{
		return ModelValidator.TYPE_BEFORE_CHANGE == timing
				|| ModelValidator.TYPE_AFTER_CHANGE == timing
				|| ModelValidator.TYPE_AFTER_CHANGE_REPLICATION == timing;
	}
}
