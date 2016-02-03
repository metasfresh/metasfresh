package org.compiere.process;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.process.SvrProcess.RunOutOfTrx;
import org.reflections.ReflectionUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

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

/**
 * Contains informations about the process class.
 *
 * This instance will be build by introspecting a particular process class and fetching it's annotations.
 *
 * To create a new instance, call {@link #of(Class)} builder.
 *
 * @author tsa
 */
public final class ProcessClassInfo
{
	/**
	 * @return process class info or {@link #NULL} in case the given <code>processClass</code> is <code>null</code> or in case of failure.
	 */
	public static final ProcessClassInfo of(final Class<?> processClass)
	{
		if (processClass == null)
		{
			return ProcessClassInfo.NULL;
		}
		try
		{
			return processClassInfoCache.get(processClass);
		}
		catch (ExecutionException e)
		{
			// shall never happen
			SvrProcess.s_log.log(Level.SEVERE, "Failed fetching ProcessClassInfo from cache for " + processClass, e);
		}
		return ProcessClassInfo.NULL;
	}

	/** "Process class" to {@link ProcessClassInfo} cache */
	private static final LoadingCache<Class<?>, ProcessClassInfo> processClassInfoCache = CacheBuilder.newBuilder()
			.weakKeys() // to prevent ClassLoader memory leaks nightmare
			.build(new CacheLoader<Class<?>, ProcessClassInfo>()
			{
				@Override
				public ProcessClassInfo load(final Class<?> processClass) throws Exception
				{
					return createProcessClassInfo(processClass);
				}
			});

	/**
	 * Introspect given process class and return info.
	 *
	 * @param processClass
	 * @return process class info or {@link #NULL} in case of failure.
	 */
	static final ProcessClassInfo createProcessClassInfo(final Class<?> processClass)
	{
		try
		{
			boolean runPrepareOutOfTransaction = isRunOutOfTrx(processClass, void.class, "prepare");
			final boolean runDoItOutOfTransaction = isRunOutOfTrx(processClass, String.class, "doIt");
			if (runDoItOutOfTransaction)
			{
				runPrepareOutOfTransaction = true;
			}

			return new ProcessClassInfo(runPrepareOutOfTransaction, runDoItOutOfTransaction);
		}
		catch (Throwable e)
		{
			SvrProcess.s_log.log(Level.SEVERE, "Failed introspecting process class info: " + processClass + ". Fallback to defaults: " + NULL, e);
			return NULL;
		}
	}

	private static final boolean isRunOutOfTrx(final Class<?> processClass, final Class<?> returnType, final String methodName)
	{
		// Get all methods with given format,
		// from given processClass and it's super classes,
		// ordered by methods of processClass first, methods from super classes after
		@SuppressWarnings("unchecked")
		final Set<Method> methods = ReflectionUtils.getAllMethods(processClass
				, ReflectionUtils.withName(methodName)
				, ReflectionUtils.withParameters()
				, ReflectionUtils.withReturnType(returnType));

		// No methods of given format were found. This can be problematic because we assume given method is declared somewhere.
		if (methods.isEmpty())
		{
			throw new IllegalStateException("Method " + methodName + " with return type " + returnType + " was not found in " + processClass + " or in its inerited types");
		}

		// Iterate all methods and return on first RunOutOfTrx annotation found.
		for (final Method method : methods)
		{
			final RunOutOfTrx runOutOfTrxAnnotation = method.getAnnotation(RunOutOfTrx.class);
			if (runOutOfTrxAnnotation != null)
			{
				return true;
			}
		}

		// Fallback: no RunOutOfTrx annotation found
		return false;
	}

	public static final ProcessClassInfo NULL = new ProcessClassInfo();

	private final boolean runPrepareOutOfTransaction;
	private final boolean runDoItOutOfTransaction;

	// NOTE: NEVER EVER store the process class as field because we want to have a weak reference to it to prevent ClassLoader memory leaks nightmare.
	// Remember that we are caching this object.

	/** null constructor */
	ProcessClassInfo()
	{
		super();
		runPrepareOutOfTransaction = false;
		runDoItOutOfTransaction = false;
	}

	ProcessClassInfo(final boolean runPrepareOutOfTransaction, final boolean runDoItOutOfTransaction)
	{
		super();
		this.runPrepareOutOfTransaction = runPrepareOutOfTransaction;
		this.runDoItOutOfTransaction = runDoItOutOfTransaction;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** @return <code>true</code> if we shall run {@link SvrProcess#prepare()} method out of transaction */
	public boolean isRunPrepareOutOfTransaction()
	{
		return runPrepareOutOfTransaction;
	}

	/** @return <code>true</code> if we shall run {@link SvrProcess#doIt()} method out of transaction */
	public boolean isRunDoItOutOfTransaction()
	{
		return runDoItOutOfTransaction;
	}
}