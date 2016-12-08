package de.metas.script;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ScriptExecutor
{
	// global or login context variable prefix
	private final static String GLOBAL_CONTEXT_PREFIX = "G_";
	// window context variable prefix
	private final static String WINDOW_CONTEXT_PREFIX = "W_";
	// method call arguments prefix
	private final static String ARGUMENTS_PREFIX = "A_";
	// process parameters prefix
	private final static String PARAMETERS_PREFIX = "P_";

	private final ScriptEngine _engine;
	private boolean throwExceptionIfResultNotEmpty = false;

	/* package */ ScriptExecutor(final ScriptEngine scriptEngine)
	{
		super();
		Check.assumeNotNull(scriptEngine, "Parameter scriptEngine is not null");
		_engine = scriptEngine;
	}

	public Object execute(final String script)
	{
		final ScriptEngine engine = getEngine();
		try
		{
			final Object result = engine.eval(script);

			if (throwExceptionIfResultNotEmpty)
			{
				final String errmsg = result == null ? null : result.toString();
				if (!Check.isEmpty(errmsg))
				{
					throw new AdempiereException(errmsg);
				}
			}

			return result;
		}
		catch (final ScriptException e)
		{
			throw new AdempiereException("Script execution failed: " + e.getLocalizedMessage()
					+ "\n Engine: " + engine
					+ "\n Script: " + script, e);
		}
	}

	private ScriptEngine getEngine()
	{
		return _engine;
	}

	public ScriptExecutor putContext(final Properties ctx, final int windowNo)
	{
		Check.assumeNotNull(ctx, "Parameter ctx is not null");

		final ScriptEngine engine = getEngine();

		for (final Enumeration<?> en = ctx.propertyNames(); en.hasMoreElements();)
		{
			final String key = en.nextElement().toString();
			// filter
			if (key == null || key.length() == 0
					|| key.startsWith("P")              // Preferences
					|| key.indexOf('|') != -1 && !key.startsWith(String.valueOf(windowNo))    // other Window Settings
					|| key.indexOf('|') != -1 && key.indexOf('|') != key.lastIndexOf('|') // other tab
			)
			{
				continue;
			}

			final String value = ctx.getProperty(key);
			if (value != null)
			{
				final String engineKey = convertToEngineKey(key, windowNo);
				engine.put(engineKey, value);
			}
		}

		//
		// Also put the context and windowNo as argument
		putArgument("Ctx", ctx);
		putArgument("WindowNo", windowNo);

		return this;
	}

	public ScriptExecutor putArgument(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		getEngine().put(ARGUMENTS_PREFIX + name, value);
		return this;
	}

	public ScriptExecutor putProcessParameter(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name is not empty");
		getEngine().put(PARAMETERS_PREFIX + name, value);
		return this;
	}

	public static String convertToEngineKey(final String key, final int windowNo)
	{
		final String keyPrefix = windowNo + "|";
		if (key.startsWith(keyPrefix))
		{
			String retValue = WINDOW_CONTEXT_PREFIX + key.substring(keyPrefix.length());
			retValue = Util.replace(retValue, "|", "_");
			return retValue;
		}
		else
		{
			String retValue = null;
			if (key.startsWith("#"))
			{
				retValue = GLOBAL_CONTEXT_PREFIX + key.substring(1);
			}
			else
			{
				retValue = key;
			}
			retValue = Util.replace(retValue, "#", "_");
			return retValue;
		}
	}

	public ScriptExecutor putAll(final Map<String, ? extends Object> context)
	{
		final ScriptEngine engine = getEngine();
		context.entrySet()
				.stream()
				.forEach(entry -> engine.put(entry.getKey(), entry.getValue()));

		return this;
	}

	public ScriptExecutor setThrowExceptionIfResultNotEmpty()
	{
		throwExceptionIfResultNotEmpty = true;
		return this;
	}

}
