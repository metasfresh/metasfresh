package de.metas.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.compiere.model.Null;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.process.ui.ProcessParameterPanelModel;

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
 * Helper class used to fill process parameters with default values provided by {@link IProcessDefaultParametersProvider}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ProcessDefaultParametersUpdater
{
	public static final ProcessDefaultParametersUpdater newInstance()
	{
		return new ProcessDefaultParametersUpdater();
	}

	private static final transient Logger logger = LogManager.getLogger(ProcessDefaultParametersUpdater.class);

	private static final IProcessDefaultParametersProvider NULL_DefaultPrametersProvider = parameterName -> IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;

	private final List<IProcessDefaultParametersProvider> defaultParametersProviders = new ArrayList<>();
	private BiConsumer<IProcessDefaultParameter, Object> defaultValueConsumer;

	private ProcessDefaultParametersUpdater()
	{
		super();
	}

	public ProcessDefaultParametersUpdater addDefaultParametersProvider(final IProcessDefaultParametersProvider provider)
	{
		if (provider == null || provider == NULL_DefaultPrametersProvider)
		{
			return this;
		}

		if (defaultParametersProviders.contains(provider))
		{
			return this;
		}

		defaultParametersProviders.add(provider);
		return this;
	}

	/**
	 * Extracts, if possible, the {@link IProcessDefaultParametersProvider} from given <code>processInfo</code> and registers it.
	 *
	 * @param processInfo
	 */
	public ProcessDefaultParametersUpdater addDefaultParametersProvider(final ProcessInfo processInfo)
	{
		final IProcessDefaultParametersProvider provider = createProcessDefaultParametersProvider(processInfo);
		addDefaultParametersProvider(provider);
		return this;
	}

	private static IProcessDefaultParametersProvider createProcessDefaultParametersProvider(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "Parameter pi is not null");

		final String classname = pi.getClassName();
		if (Check.isEmpty(classname, true))
		{
			return NULL_DefaultPrametersProvider;
		}

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
			{
				classLoader = ProcessParameterPanelModel.class.getClassLoader();
			}

			final Class<?> processClass = classLoader.loadClass(classname);
			if (!IProcessDefaultParametersProvider.class.isAssignableFrom(processClass))
			{
				return NULL_DefaultPrametersProvider;
			}

			final IProcessDefaultParametersProvider defaultsProvider = (IProcessDefaultParametersProvider)processClass.newInstance();
			if (defaultsProvider instanceof JavaProcess)
			{
				((JavaProcess)defaultsProvider).init(pi);
			}
			return defaultsProvider;

		}
		catch (final Throwable e)
		{
			if (pi.isServerProcess() && Ini.isClient())
			{
				// NOTE: in case of server process, it might be that the class is not present, which could be fine
				logger.debug("Failed instantiating class '{}'. Skipped.", classname, e);
			}
			else
			{
				logger.warn("Failed instantiating class '{}'. Skipped.", classname, e);
			}
			return NULL_DefaultPrametersProvider;
		}
	}

	/**
	 * Configures the default value consumer to be used by methods like {@link #updateDefaultValue(IProcessDefaultParameter)}.
	 *
	 * @param defaultValueConsumer
	 */
	public ProcessDefaultParametersUpdater onDefaultValue(final BiConsumer<IProcessDefaultParameter, Object> defaultValueConsumer)
	{
		Check.assumeNotNull(defaultValueConsumer, "Parameter defaultValueConsumer is not null");
		this.defaultValueConsumer = defaultValueConsumer;
		return this;
	}

	/**
	 * Asks this updater to fetch the default value for given parameter and forward it to default value consumer configured by {@link #onDefaultValue(BiConsumer)}.
	 *
	 * @param parameter
	 */
	public void updateDefaultValue(final IProcessDefaultParameter parameter)
	{
		for (final IProcessDefaultParametersProvider provider : defaultParametersProviders)
		{
			//
			// Ask provider for default value
			Object value = null;
			try
			{
				value = provider.getParameterDefaultValue(parameter);
			}
			catch (final Exception e)
			{
				// ignore the error, but log it
				final String parameterName = parameter.getColumnName();
				logger.error("Failed retrieving the parameters default value from defaults provider: ParameterName={}, Provider={}", parameterName, provider, e);
				continue;
			}

			// If the provider cannot provide a default value, we are skipping it and we will ask the next provider if any.
			if (value == IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE)
			{
				continue;
			}

			// We got a default value from provider.
			// Set it and stop here.
			defaultValueConsumer.accept(parameter, Null.unbox(value));
			break;
		}
	}

	/**
	 * Asks this updater to
	 * <ul>
	 * <li>iterate given <code>parameterObjs</code> collection
	 * <li>convert the parameter object to {@link IProcessDefaultParameter} using given <code>processDefaultParameterConverter</code>
	 * <li>fetch the default value for each parameter
	 * <li>forward the default value to default value consumer configured by {@link #onDefaultValue(BiConsumer)}.
	 * </ul>
	 *
	 * @param parameterObjs
	 * @param processDefaultParameterConverter
	 */
	public <T> void updateDefaultValue(final Collection<T> parameterObjs, final Function<T, IProcessDefaultParameter> processDefaultParameterConverter)
	{
		if (parameterObjs == null || parameterObjs.isEmpty())
		{
			return;
		}

		parameterObjs.stream() // stream parameter objects
				.map(processDefaultParameterConverter) // convert parameter object to IProcessDefaultParameter
				.forEach(this::updateDefaultValue) // update the default value if available
				;
	}

}
