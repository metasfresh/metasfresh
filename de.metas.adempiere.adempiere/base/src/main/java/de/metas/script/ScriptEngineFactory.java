package de.metas.script;

import java.util.Optional;
import java.util.function.Supplier;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.X_AD_Rule;

import com.google.common.base.Suppliers;

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

public class ScriptEngineFactory
{
	public static final ScriptEngineFactory get()
	{
		return instance;
	}

	private static final transient ScriptEngineFactory instance = new ScriptEngineFactory();

	private static final String SCRIPT_PREFIX = "@script:";

	private final com.google.common.base.Supplier<ScriptEngineManager> scriptEngineManager = Suppliers.memoize(() -> new ScriptEngineManager());

	public final ScriptExecutor createExecutor(final String scriptEngineType, final String scriptEngineName)
	{
		if (X_AD_Rule.RULETYPE_JSR223ScriptingAPIs.equals(scriptEngineType))
		{
			final ScriptEngine scriptEngine = createScriptEngine_JSR223(scriptEngineName);
			return new ScriptExecutor(scriptEngine);
		}
		else
		{
			throw new AdempiereException("Script engine type not supported: " + scriptEngineType);
		}
	}

	public final ScriptExecutor createExecutor(final I_AD_Rule rule)
	{
		Check.assumeNotNull(rule, "Parameter rule is not null");

		final String scriptEngineType = rule.getRuleType();
		final String scriptEngineName = extractEngineNameFromRuleValue(rule.getValue());

		return createExecutor(scriptEngineType, scriptEngineName);
	}

	public final Supplier<ScriptExecutor> createExecutorSupplier(final I_AD_Rule rule)
	{
		Check.assumeNotNull(rule, "Parameter rule is not null");

		final String scriptEngineType = rule.getRuleType();
		final String scriptEngineName = extractEngineNameFromRuleValue(rule.getValue());

		return () -> createExecutor(scriptEngineType, scriptEngineName);
	}

	public final void assertScriptEngineExists(final I_AD_Rule rule)
	{
		try
		{
			final ScriptExecutor executor = createExecutor(rule);
			Check.assumeNotNull(executor, "executor is not null"); // shall not happen
		}
		catch (final Throwable e)
		{
			throw new AdempiereException("@WrongScriptValue@", e);
		}
	}

	private static final String extractEngineNameFromRuleValue(final String ruleValue)
	{
		if (ruleValue == null)
		{
			return null;
		}
		final int colonPosition = ruleValue.indexOf(":");
		if (colonPosition < 0)
		{
			return null;
		}
		return ruleValue.substring(0, colonPosition);
	}

	public static final Optional<String> extractRuleValueFromClassname(final String classname)
	{
		if (classname == null || classname.isEmpty())
		{
			return Optional.empty();
		}

		if (!classname.toLowerCase().startsWith(SCRIPT_PREFIX))
		{
			return Optional.empty();
		}

		final String ruleValue = classname.substring(SCRIPT_PREFIX.length()).trim();
		return Optional.of(ruleValue);
	}

	private final ScriptEngine createScriptEngine_JSR223(final String engineName)
	{
		Check.assumeNotEmpty(engineName, "engineName is not empty");
		
		final ScriptEngineManager factory = getScriptEngineManager();
		final ScriptEngine engine = factory.getEngineByName(engineName);
		if (engine == null)
		{
			throw new AdempiereException("Script engine was not found for engine name: '" + engineName + "'");
		}
		return engine;
	}

	private final ScriptEngineManager getScriptEngineManager()
	{
		return scriptEngineManager.get();
	}

}
