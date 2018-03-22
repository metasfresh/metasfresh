package org.adempiere.ad.modelvalidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.DocValidates;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.ModelChanges;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_AD_Client;
import org.compiere.model.ModelValidator;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;

import de.metas.logging.LogManager;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Wrapping class which introspect an object, identifies it's pointcuts (ModelChange, DocValidate etc) and maps them to {@link ModelValidator} interface.
 *
 * @author tsa
 *
 */

@EqualsAndHashCode(of = { "annotatedObject", "annotatedClass", "clientId" })
/* package */class AnnotatedModelInterceptor implements IModelInterceptor
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final transient Object annotatedObject;
	private final transient Class<?> annotatedClass;
	/**
	 * Model class which is intercepted.
	 *
	 * Specified by {@link Interceptor} annotation.
	 */
	private Class<?> modelClass = null;
	/**
	 * List of table names on which binding is allowed
	 */
	private transient List<?> allowedTargetTableNames = null;

	private final transient List<InterceptorInit> initializers = new ArrayList<>();

	/**
	 * Map PointcutType(TableName,PointcutType) -> pointcuts list.<br>
	 * FRESH-318: order then, because without a specified ordering, java7 executes them in a different ordering that java8.
	 */
	private final transient Map<PointcutKey, SortedSet<Pointcut>> mapPointcuts = new HashMap<>();

	private int clientId = -1;

	/**
	 *
	 * @param annotatedObject
	 * @throws AdempiereException if annotations were not correctly used
	 */
	AnnotatedModelInterceptor(@NonNull final Object annotatedObject) throws AdempiereException
	{
		this.annotatedObject = annotatedObject;
		this.annotatedClass = annotatedObject.getClass();

		loadAnnotatedClassDefinition();
		loadPointcuts();
	}

	/**
	 *
	 * @return true if annotated class has any pointcuts (ModelChange, DocValidate etc)
	 */
	public boolean hasPointcuts()
	{
		return !mapPointcuts.isEmpty();
	}

	/**
	 *
	 * @return true if it has no initializers, no pointcuts, no nothing
	 */
	public boolean isEmpty()
	{
		if (!initializers.isEmpty())
		{
			return false;
		}
		if (hasPointcuts())
		{
			return false;
		}

		return true;
	}

	@Override
	public void initialize(final IModelValidationEngine engine, final I_AD_Client client)
	{
		if (client != null)
		{
			this.clientId = client.getAD_Client_ID();
		}

		bindPointcuts(engine);

		//
		// Execute initializers
		for (final InterceptorInit init : initializers)
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
			catch (Exception e)
			{
				throw new AdempiereException("Cannot initialize " + annotatedClass + ". Initializer " + init + " failed."
						+ "\n Method: " + method
						+ "\n Params:" + params
						+ "\n Method Descriptor: " + init, Throwables.getRootCause(e));
			}

			logger.debug("Initializer {} executed successfully.", init);
		}
	}

	private void loadAnnotatedClassDefinition()
	{
		final Class<?> modelClass;

		final Interceptor annIterceptor = annotatedClass.getAnnotation(Interceptor.class);
		if (annIterceptor == null)
		{
			// Fallback to Validator annotation
			final Validator annValidator = annotatedClass.getAnnotation(Validator.class);
			if (annValidator == null)
			{
				throw new AdempiereException("Each model interceptor class shall be marked with " + Interceptor.class + " annotation");
			}
			else
			{
				modelClass = annValidator.value();
			}
		}
		else
		{
			modelClass = annIterceptor.value();
		}

		//
		// Make sure model class is specified
		if (modelClass == null)
		{
			throw new AdempiereException("Annotation for " + annotatedObject + " does not specify the model class on which we are binding");
		}

		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(modelClass);
		if (tableName == null)
		{
			throw new AdempiereException("Cannot find tableName for " + modelClass + ". Please make sure it's a model interface.");
		}
		this.modelClass = modelClass;
		this.allowedTargetTableNames = Arrays.asList(tableName);

		//
		// validate ModelValidator classname and:
		// * throw exception (if development mode)
		// * log warning if production mode
		// ts: relaxing the check; in package de.metas.inoutcandidate.modelvalidator we have C_Order related MVs for both shipment and receipts schedule..therefore it shall be OK if the MV's class
		// name *starts* with the table name.
		if (!annotatedClass.getSimpleName().startsWith(tableName))
		{
			final AdempiereException ex = new AdempiereException("According to metas best practices, model validator shall have the same name as the table."
					+ "Please rename class " + annotatedClass + " to " + tableName);
			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				throw ex;
			}
			else
			{
				logger.warn(ex.getLocalizedMessage(), ex);
			}
		}
	}

	private void loadPointcuts()
	{
		//
		// Get all methods from annotated class, including those from super classes
		@SuppressWarnings("unchecked")
		final Set<Method> annotatedClassMethods = ReflectionUtils.getAllMethods(annotatedClass);

		for (final Method method : annotatedClassMethods)
		{
			final Init annInit = method.getAnnotation(Init.class);
			if (annInit != null)
			{
				loadInitMethod(annInit, method);
			}

			final ModelChange annModelChange = method.getAnnotation(ModelChange.class);
			if (annModelChange != null)
			{
				loadPointcut(annModelChange, method);
			}

			final ModelChanges annModelChanges = method.getAnnotation(ModelChanges.class);
			if (annModelChanges != null && annModelChanges.value().length > 0)
			{
				for (ModelChange a : annModelChanges.value())
				{
					loadPointcut(a, method);
				}
			}

			final DocValidate annDocValidate = method.getAnnotation(DocValidate.class);
			if (annDocValidate != null)
			{
				loadPointcut(annDocValidate, method);
			}

			final DocValidates annDocValidates = method.getAnnotation(DocValidates.class);
			if (annDocValidates != null && annDocValidates.value().length > 0)
			{
				for (DocValidate a : annDocValidates.value())
				{
					loadPointcut(a, method);
				}
			}
		}
	}

	private void loadInitMethod(final Init annInit, final Method method)
	{
		final InterceptorInit init = new InterceptorInit(method);

		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length >= 1)
		{
			if (parameterTypes[0].isAssignableFrom(IModelValidationEngine.class))
			{
				init.setMethodRequiresEngine(true);
			}
			else
			{
				throw new AdempiereException("Invalid initalizer method format. First argument shall be " + IModelValidationEngine.class
						+ "\n Method: " + method
						+ "\n Annotation:" + annInit);
			}
		}

		initializers.add(init);
	}

	private void loadPointcut(final ModelChange annModelChange, final Method method)
	{
		final Pointcut pointcut = new Pointcut(PointcutType.ModelChange, method, annModelChange.timings(), annModelChange.afterCommit());
		pointcut.setChangedColumns(annModelChange.ifColumnsChanged());
		pointcut.setIgnoredColumns(annModelChange.ignoreColumnsChanged());
		pointcut.setOnlyIfUIAction(annModelChange.ifUIAction());
		pointcut.setSkipIfCopying(annModelChange.skipIfCopying());
		initPointcutAndAddToMap(pointcut);
	}

	private void loadPointcut(final DocValidate annDocValidate, final Method method)
	{
		final Pointcut pointcut = new Pointcut(PointcutType.DocValidate, method, annDocValidate.timings(), annDocValidate.afterCommit());
		initPointcutAndAddToMap(pointcut);
	}

	private void initPointcutAndAddToMap(final Pointcut pointcut)
	{
		final Method method = pointcut.getMethod();
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes == null || parameterTypes.length == 0)
		{
			throw new AdempiereException("Invalid method " + pointcut.getMethod() + ": It has no arguments");
		}

		if (method.getReturnType() != void.class)
		{
			throw new AdempiereException("Return type should be void for " + method);
		}

		//
		// Model Class for binding
		{
			Class<?> modelClass = parameterTypes[0];

			// Case: our parameter comes from Java generics
			// e.g. method comes from an abstract class which have annotated methods with Java Generics parameters.
			// In this case it's quite hard to get the actual type so we assume the modelClass.
			if (Object.class.equals(modelClass))
			{
				modelClass = this.modelClass;
			}

			pointcut.setModelClass(modelClass);
		}

		//
		// Get table name
		final String tableName = InterfaceWrapperHelper.getTableNameOrNull(pointcut.getModelClass());
		if (Check.isEmpty(tableName))
		{
			throw new AdempiereException("Cannot find tablename for " + pointcut.getModelClass());
		}
		if (allowedTargetTableNames != null && !allowedTargetTableNames.contains(tableName))
		{
			throw new AdempiereException("Table " + tableName + " is not in the list of allowed tables names specified by " + Interceptor.class + " annotation: " + allowedTargetTableNames);
		}
		pointcut.setTableName(tableName);
		if (pointcut.getTableName() == null)
		{
			throw new AdempiereException("Invalid method " + pointcut.getMethod() + ": first argument is not a model class: " + pointcut.getModelClass());
		}

		//
		// Get/Validate timings
		if (pointcut.getTimings().isEmpty())
		{
			throw new AdempiereException("Invalid method " + pointcut.getMethod() + ": no timings were specified");
		}

		if (parameterTypes.length == 1)
		{
			pointcut.setMethodTimingParameterType(null); // no timing parameter required
		}
		else if (parameterTypes.length == 2)
		{
			pointcut.setMethodTimingParameterType(parameterTypes[1]);
		}
		else
		{
			throw new AdempiereException("Invalid method " + pointcut.getMethod() + ": definition not supported");
		}

		// Add to map
		final PointcutKey key = mkKey(pointcut);
		SortedSet<Pointcut> set = mapPointcuts.get(key);
		if (set == null)
		{
			set = new TreeSet<>();
			mapPointcuts.put(key, set);
		}

		if (!set.add(pointcut))
		{
			// shall not happen
			final String msg = StringUtils.formatMessage("Pointcut {} was not added because another one was found in the list: {}", pointcut, set);
			new AdempiereException(msg).throwOrLogSevere(Services.get(IDeveloperModeBL.class).isEnabled(), logger);
		}

		logger.debug("Loaded {}", pointcut);
	}

	private void bindPointcuts(@NonNull final IModelValidationEngine engine)
	{
		if (!hasPointcuts())
		{
			return;
		}

		logger.debug("Binding pointcuts for {}", annotatedClass);
		for (final Map.Entry<PointcutKey, SortedSet<Pointcut>> e : mapPointcuts.entrySet())
		{
			final Set<Pointcut> list = e.getValue();
			if (list == null || list.isEmpty())
			{
				continue;
			}

			final PointcutKey key = e.getKey();
			logger.debug("Binding pointcuts for {} on {}", annotatedClass, key);

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
		logger.debug("Binding pointcuts for {} done.", annotatedClass);
	}

	@Override
	public int getAD_Client_ID()
	{
		return clientId;
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing
	}

	@Override
	public final void onModelChange(final Object model, final ModelChangeType changeType)
	{
		final int type = changeType.getChangeType();
		execute(PointcutType.ModelChange, model, type);
	}

	@Override
	public final void onDocValidate(final Object model, final DocTimingType timing)
	{
		final int timingCode = timing.getTiming();
		execute(PointcutType.DocValidate, model, timingCode);
	}

	private void execute(final PointcutType type, final Object po, final int timing)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(po);
		final PointcutKey key = mkKey(tableName, type);
		final SortedSet<Pointcut> list = mapPointcuts.get(key);
		if (list == null || list.isEmpty())
		{
			return;
		}

		for (final IPointcut info : list)
		{
			execute(info, po, timing);
		}
	}

	private void execute(final IPointcut pointcut, final Object po, final int timing)
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
			final Set<String> columnsToCheckForChanges = pointcut.getColumnsToCheckForChanges();
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
			logger.debug("Scheduling to be executed after commit: {}", pointcut);
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

	@VisibleForTesting
	private final void executeNow(
			@NonNull final Object po,
			@NonNull final IPointcut pointcut,
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
			final AdempiereException adempiereException = appendAndLogHowtoDisableMessage(e, pointcut);
			throw adempiereException;
		}
	}

	private AdempiereException appendAndLogHowtoDisableMessage(
			@NonNull final Exception e,
			@NonNull final IPointcut pointcut)
	{
		final String parameterName = "HowtoDisableModelInterceptor";
		final AdempiereException ae = AdempiereException.wrapIfNeeded(e);

		if (!ae.hasParameter(parameterName))
		{
			final String howtoDisableMsg = AnnotatedModelInterceptorDisabler.createHowtoDisableMessage(pointcut);

			logger.error(howtoDisableMsg);
			ae.setParameter(parameterName, howtoDisableMsg);
		}
		return ae;
	}

	private void executeNow0(
			@NonNull final Object model,
			@NonNull final IPointcut pointcut,
			final int timing) throws IllegalAccessException, InvocationTargetException
	{
		final Method method = pointcut.getMethod();

		// Make sure the method is accessible
		if (!method.isAccessible())
		{
			method.setAccessible(true);
		}

		logger.debug("Executing: {}", pointcut);

		if (pointcut.isMethodRequiresTiming())
		{
			final Object timingParam = pointcut.convertToMethodTimingParameterType(timing);
			method.invoke(annotatedObject, model, timingParam);
		}
		else
		{
			method.invoke(annotatedObject, model);
		}
	}

	/**
	 *
	 * @param timing
	 * @return true if timing is change (before, after)
	 */
	private static final boolean isTimingChange(final int timing)
	{
		return ModelValidator.TYPE_BEFORE_CHANGE == timing
				|| ModelValidator.TYPE_AFTER_CHANGE == timing
				|| ModelValidator.TYPE_AFTER_CHANGE_REPLICATION == timing;
	}

	@Override
	public String toString()
	{
		return "annotated[" + this.annotatedClass.getName() + "]";
	}

	private final PointcutKey mkKey(@NonNull final Pointcut pointcut)
	{
		return mkKey(pointcut.getTableName(), pointcut.getType());
	}

	private final PointcutKey mkKey(@NonNull final String tableName, @NonNull final PointcutType type)
	{
		return new PointcutKey(tableName, type);
	}

	@lombok.Value
	private class PointcutKey
	{
		@NonNull
		String tableName;

		@NonNull
		PointcutType type;
	}
}
