package de.metas.process;

import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition.PreconditionsContext;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Helper class used to check if all preconditions are met in order to show a process to Gear.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ProcessPreconditionChecker
{
	public static final ProcessPreconditionChecker newInstance()
	{
		return new ProcessPreconditionChecker();
	}

	private static final transient Logger logger = LogManager.getLogger(ProcessPreconditionChecker.class);

	private Class<?> _processClass;
	private Class<? extends IProcessPrecondition> _preconditionsClass;
	private ProcessClassInfo _processClassInfo = null;
	private Supplier<PreconditionsContext> _preconditionsContextSupplier;

	private boolean _notApplicable = false;

	private ProcessPreconditionChecker()
	{
	}

	public boolean checkApplies()
	{
		if (wasMarkedAsNotApplicable())
		{
			logger.debug("checkApplies=false: already marked as not applicable");
			return false;
		}

		final ProcessClassInfo processClassInfo = getProcessClassInfo();
		if (processClassInfo != null)
		{
			if (!processClassInfo.isAllowedForCurrentProfiles())
			{
				logger.debug("checkApplies=false: Not allowed for current profiles: {}", processClassInfo);
				return false;
			}
		}

		final Class<? extends IProcessPrecondition> preconditionsClass = getPreconditionsClass();
		if (preconditionsClass != null)
		{
			try
			{
				final IProcessPrecondition preconditions = preconditionsClass.asSubclass(IProcessPrecondition.class).newInstance();
				if (!preconditions.isPreconditionApplicable(getPreconditionsContext()))
				{
					return false;
				}
			}
			catch (final Exception e)
			{
				logger.error("checkApplies=false: Failed checking preconditions for {}", preconditionsClass, e);
				return false;
			}
		}

		//
		return true; // applies
	}

	public ProcessPreconditionChecker setProcess(final String processClassname)
	{
		_processClass = null;
		_preconditionsClass = null;
		_processClassInfo = null;

		//
		// Load process class if possible
		final Class<?> processClass = loadClass(processClassname, false);
		if (wasMarkedAsNotApplicable())
		{
			return this;
		}

		//
		// Load preconditions class if possible
		Class<? extends IProcessPrecondition> preconditionsClass = null;
		if (processClass != null)
		{
			if (IProcessPrecondition.class.isAssignableFrom(processClass))
			{
				preconditionsClass = processClass.asSubclass(IProcessPrecondition.class);
			}
		}

		_processClass = processClass;
		_preconditionsClass = preconditionsClass;

		return this;
	}

	public ProcessPreconditionChecker setProcess(final I_AD_Process adProcess)
	{
		_processClass = null;
		_preconditionsClass = null;
		_processClassInfo = null;

		//
		// Load process class if possible
		final String processClassname = adProcess.getClassname();
		Class<?> processClass = null;
		if (!Check.isEmpty(processClassname, true))
		{
			processClass = loadClass(processClassname, adProcess.isServerProcess());
		}

		if (wasMarkedAsNotApplicable())
		{
			return this;
		}

		//
		// Load preconditions class if possible
		Class<? extends IProcessPrecondition> preconditionsClass = null;
		if (!Check.isEmpty(processClassname, true))
		{
			preconditionsClass = toProcessPreconditionsClassOrNull(processClass);
		}
		else
		{
			// Extract preconditions class from AD_Form's class (task #05089)
			final I_AD_Form adForm = adProcess.getAD_Form();
			if (adForm != null && !Check.isEmpty(adForm.getClassname(), true))
			{
				final String adFormClassname = adForm.getClassname();
				final Class<?> adFormClass = loadClass(adFormClassname, false);
				if (wasMarkedAsNotApplicable())
				{
					return this;
				}

				preconditionsClass = toProcessPreconditionsClassOrNull(adFormClass);
			}
			else
			{
				preconditionsClass = null;
			}

		}

		_processClass = processClass;
		_preconditionsClass = preconditionsClass;

		return this;

	}

	private final Class<?> loadClass(final String classname, final boolean isServerProcess)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = getClass().getClassLoader();
		}

		try
		{
			return classLoader.loadClass(classname);
		}
		catch (final ClassNotFoundException ex)
		{
			if (isServerProcess && Ini.isClient())
			{
				// it might be that the class is available only on server
				// so, for now, we consider the preconditions are applicable.
				return null;
			}
			else
			{
				markAsNotApplicable("cannot load class " + classname, ex);
				return null;
			}
		}
	}

	private final Class<? extends IProcessPrecondition> toProcessPreconditionsClassOrNull(final Class<?> clazz)
	{
		if (clazz != null && IProcessPrecondition.class.isAssignableFrom(clazz))
		{
			return clazz.asSubclass(IProcessPrecondition.class);
		}
		else
		{
			return null;
		}

	}

	private final void markAsNotApplicable(final String errorMessage, final Throwable ex)
	{
		logger.warn("Marked checker as not applicable: {} because {}", this, errorMessage, ex);
		_notApplicable = true;
	}

	private final boolean wasMarkedAsNotApplicable()
	{
		return _notApplicable;
	}

	private Class<? extends IProcessPrecondition> getPreconditionsClass()
	{
		return _preconditionsClass;
	}

	private ProcessClassInfo getProcessClassInfo()
	{
		if (_processClassInfo == null)
		{
			_processClassInfo = ProcessClassInfo.of(_processClass);
		}
		return _processClassInfo;
	}

	public ProcessPreconditionChecker setPreconditionsContext(final Supplier<PreconditionsContext> preconditionsContextSupplier)
	{
		_preconditionsContextSupplier = preconditionsContextSupplier;
		return this;
	}

	public ProcessPreconditionChecker setPreconditionsContext(final PreconditionsContext preconditionsContext)
	{
		_preconditionsContextSupplier = () -> preconditionsContext;
		return this;
	}

	private PreconditionsContext getPreconditionsContext()
	{
		Check.assumeNotNull(_preconditionsContextSupplier, "Parameter preconditionsContextSupplier is not null");

		final PreconditionsContext preconditionsContext = _preconditionsContextSupplier.get();
		Check.assumeNotNull(preconditionsContext, "Parameter preconditionsContext is not null");

		return preconditionsContext;
	}

}
