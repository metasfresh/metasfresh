package org.adempiere.ad.callout.api.impl;

import java.util.Objects;

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

import java.util.Properties;
import java.util.function.Supplier;

import javax.script.ScriptEngine;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.MRule;
import org.compiere.model.X_AD_Rule;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;

public final class RuleCalloutInstance implements ICalloutInstance
{
	public static final Supplier<ICalloutInstance> supplier(final String ruleValue)
	{
		Check.assumeNotEmpty(ruleValue, "ruleValue not empty");

		final Properties ctx = Env.getCtx();

		final I_AD_Rule rule = MRule.get(ctx, ruleValue.trim());
		if (rule == null)
		{
			throw new CalloutInitException("Cannot find rule for callout value '" + ruleValue + "'");
		}

		if (!X_AD_Rule.EVENTTYPE_Callout.equals(rule.getEventType()))
		{
			throw new CalloutInitException("Invalid callout rule " + rule + ". EventType shall be Callout");
		}
		if (!X_AD_Rule.RULETYPE_JSR223ScriptingAPIs.equals(rule.getRuleType()))
		{
			throw new CalloutInitException("Invalid callout rule type for " + rule + ". Only JSR223 is supported at the moment.");
		}

		final String id = RuleCalloutInstance.class.getSimpleName() + "-" + ruleValue.trim();
		final String script = rule.getScript();

		final MRule rulePO = LegacyAdapters.convertToPO(rule);
		final Supplier<ScriptEngine> scriptEngineSupplier = rulePO.supplyScriptEngine();

		return () -> new RuleCalloutInstance(id, script, scriptEngineSupplier);
	}

	private static final transient Logger logger = LogManager.getLogger(RuleCalloutInstance.class);

	private final String id;
	private final String script;
	private final Supplier<ScriptEngine> scriptEngineSupplier;

	public RuleCalloutInstance(final String id, final String script, final Supplier<ScriptEngine> scriptEngineSupplier)
	{
		super();

		this.id = id;
		this.script = script;
		this.scriptEngineSupplier = scriptEngineSupplier;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final RuleCalloutInstance other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(id, other.id)
				.isEqual();
	}

	@Override
	public void execute(final ICalloutExecutor executor, final ICalloutField field)
	{
		final Properties ctx = field.getCtx();
		final int windowNo = field.getWindowNo();
		final Object value = field.getValue();
		final Object valueOld = field.getOldValue();

		final ScriptEngine engine = scriptEngineSupplier.get();

		// Window context are W_
		// Login context are G_
		MRule.setContext(engine, ctx, windowNo);
		// now add the callout parameters windowNo, tab, field, value, oldValue to the engine
		// Method arguments context are A_
		engine.put(MRule.ARGUMENTS_PREFIX + "WindowNo", windowNo);
		engine.put(MRule.ARGUMENTS_PREFIX + "Value", value);
		engine.put(MRule.ARGUMENTS_PREFIX + "OldValue", valueOld);
		engine.put(MRule.ARGUMENTS_PREFIX + "Ctx", ctx);

		if (field instanceof GridField)
		{
			final GridField gridField = (GridField)field;
			final GridTab gridTab = gridField.getGridTab();
			engine.put(MRule.ARGUMENTS_PREFIX + "Tab", gridTab);
			engine.put(MRule.ARGUMENTS_PREFIX + "Field", gridField);
		}

		String retValue;
		try
		{
			final Object result = engine.eval(script);
			retValue = result == null ? null : result.toString();
		}
		catch (final Exception e)
		{
			logger.error("Error while executing callout", e);
			throw new CalloutExecutionException(this, "Error while executing callout: " + e.getLocalizedMessage(), e);
		}

		if (!Check.isEmpty(retValue, true))
		{
			throw new CalloutExecutionException(this, "Error while executing callout: " + retValue);
		}
	}
}
