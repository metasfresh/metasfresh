package org.adempiere.ad.modelvalidator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.DocValidates;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.ModelChanges;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.ModelValidator;
import org.reflections.ReflectionUtils;

import com.google.common.base.Throwables;

/**
 * Wrapping class which introspect an object, identifies it's pointcuts (ModelChange, DocValidate etc) and maps them to {@link ModelValidator} interface.
 * 
 * @author tsa
 * 
 */
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

	private final transient List<InterceptorInit> initializers = new ArrayList<InterceptorInit>();

	/** Map PointcutType(TableName,PointcutType) -> pointcuts list */
	private final transient Map<PointcutKey, List<Pointcut>> mapPointcuts = new HashMap<PointcutKey, List<Pointcut>>();

	private int clientId = -1;

	/**
	 * 
	 * @param annotatedObject
	 * @throws AdempiereException if annotations were not correctly used
	 */
	AnnotatedModelInterceptor(final Object annotatedObject) throws AdempiereException
	{
		Check.assumeNotNull(annotatedObject, "annotatedObject is not null");
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
						+ "\n Method Descriptor: " + init
						, Throwables.getRootCause(e));
			}

			logger.info("Initializer " + init + " executed successfully.");
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

	private void loadPointcut(ModelChange annModelChange, Method method)
	{
		final Pointcut pointcut = new Pointcut(PointcutType.ModelChange, method, annModelChange.timings());
		pointcut.setChangedColumns(annModelChange.ifColumnsChanged());
		pointcut.setIgnoredColumns(annModelChange.ignoreColumnsChanged());
		pointcut.setOnlyIfUIAction(annModelChange.ifUIAction());
		initPointcutAndAddToMap(pointcut);
	}

	private void loadPointcut(DocValidate annDocValidate, Method method)
	{
		final Pointcut pointcut = new Pointcut(PointcutType.DocValidate, method, annDocValidate.timings());
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
		List<Pointcut> list = mapPointcuts.get(key);
		if (list == null)
		{
			list = new ArrayList<Pointcut>();
			mapPointcuts.put(key, list);
		}
		list.add(pointcut);

		logger.info("Loaded " + pointcut);
	}

	private void bindPointcuts(IModelValidationEngine engine)
	{
		if (!hasPointcuts())
		{
			return;
		}

		logger.info("Binding pointcuts for " + annotatedClass);
		for (Map.Entry<PointcutKey, List<Pointcut>> e : mapPointcuts.entrySet())
		{
			final List<Pointcut> list = e.getValue();
			if (list == null || list.isEmpty())
			{
				continue;
			}

			final PointcutKey key = e.getKey();
			logger.info("Binding pointcuts for " + annotatedClass + " on " + key);

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
		logger.info("Binding pointcuts for " + annotatedClass + " done.");
	}

	@Override
	public int getAD_Client_ID()
	{
		return clientId;
	}

	@Override
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
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
	public final void onDocValidate(Object model, final DocTimingType timing)
	{
		final int timingCode = timing.getTiming();
		execute(PointcutType.DocValidate, model, timingCode);
	}

	private void execute(final PointcutType type, final Object po, final int timing)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(po);
		final PointcutKey key = mkKey(tableName, type);
		final List<Pointcut> list = mapPointcuts.get(key);
		if (list == null || list.isEmpty())
		{
			return;
		}

		for (IPointcut info : list)
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
		// Check changed columns (only if timing is not new).
		// If timing is new we always consider column was changed
		if (!isTimingNew(timing))
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

		final Object model = InterfaceWrapperHelper.create(po, pointcut.getModelClass());
		try
		{
			final Method method = pointcut.getMethod();

			// Make sure the method is accessible
			if (!method.isAccessible())
			{
				method.setAccessible(true);
			}

			if (logger.isDebugEnabled())
			{
				logger.debug("Executing: " + pointcut);
			}
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
		catch (IllegalArgumentException e)
		{
			throw new AdempiereException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new AdempiereException(e);
		}
		catch (InvocationTargetException e)
		{
			final Throwable cause = e.getCause();
			// 03444 if the pointcut method threw an adempiere exception, just forward it
			if (cause instanceof AdempiereException)
			{
				throw (AdempiereException)cause;
			}
			// 03444 end
			throw new AdempiereException(cause);
		}
	}

	/**
	 * 
	 * @param timing
	 * @return true if timing is new (before, after)
	 */
	private static final boolean isTimingNew(final int timing)
	{
		return ModelValidator.TYPE_BEFORE_NEW == timing
				|| ModelValidator.TYPE_AFTER_NEW == timing
				|| ModelValidator.TYPE_AFTER_NEW_REPLICATION == timing;
	}

	@Override
	public String toString()
	{
		return "annotated[" + this.annotatedClass.getName() + "]";
	}

	private final PointcutKey mkKey(Pointcut pointcut)
	{
		return mkKey(pointcut.getTableName(), pointcut.getType());
	}

	private final PointcutKey mkKey(String tableName, PointcutType type)
	{
		return new PointcutKey(tableName, type);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annotatedClass == null) ? 0 : annotatedClass.hashCode());
		result = prime * result + ((annotatedObject == null) ? 0 : annotatedObject.hashCode());
		result = prime * result + clientId;
		return result;
	}

	@SuppressWarnings("PMD")
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotatedModelInterceptor other = (AnnotatedModelInterceptor)obj;
		if (annotatedClass == null)
		{
			if (other.annotatedClass != null)
				return false;
		}
		else if (!annotatedClass.equals(other.annotatedClass))
			return false;
		if (annotatedObject == null)
		{
			if (other.annotatedObject != null)
				return false;
		}
		else if (!annotatedObject.equals(other.annotatedObject))
			return false;
		if (clientId != other.clientId)
			return false;
		return true;
	}

	private static class PointcutKey
	{
		private final String tableName;
		private final PointcutType type;

		public PointcutKey(String tableName, PointcutType type)
		{
			super();
			this.tableName = tableName;
			this.type = type;
		}

		public String getTableName()
		{
			return tableName;
		}

		public PointcutType getType()
		{
			return type;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@SuppressWarnings("PMD")
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PointcutKey other = (PointcutKey)obj;
			if (tableName == null)
			{
				if (other.tableName != null)
					return false;
			}
			else if (!tableName.equals(other.tableName))
				return false;
			if (type != other.type)
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			return "PointcutKey [tableName=" + tableName + ", type=" + type + "]";
		}
	}
}
