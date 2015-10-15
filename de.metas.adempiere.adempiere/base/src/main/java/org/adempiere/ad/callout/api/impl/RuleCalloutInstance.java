package org.adempiere.ad.callout.api.impl;

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
import java.util.logging.Level;

import javax.script.ScriptEngine;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.MRule;
import org.compiere.model.X_AD_Rule;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

public class RuleCalloutInstance implements ICalloutInstance
{
	private static final transient CLogger logger = CLogger.getCLogger(RuleCalloutInstance.class);

	private final I_AD_Rule rule;

	private final String id;

	public RuleCalloutInstance(final String ruleValue)
	{
		super();

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

		this.id = getClass().getSimpleName() + "-" + ruleValue.trim();
		this.rule = rule;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "rule=" + rule
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(id)
				.append(rule)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
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
				.append(this.id, other.id)
				.append(this.rule, other.rule)
				.isEqual();
	}

	@Override
	public void execute(final ICalloutExecutor executor, final ICalloutField field)
	{
		final Properties ctx = executor.getCtx();
		final int windowNo = executor.getWindowNo();
		final Object value = field.getValue();
		final Object valueOld = field.getOldValue();

		final ScriptEngine engine = createScriptEngine();

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
			retValue = engine.eval(rule.getScript()).toString();
		}
		catch (Exception e)
		{
			logger.log(Level.SEVERE, "Error while executing callout", e);
			throw new CalloutExecutionException(this, "Error while executing callout: " + e.getLocalizedMessage(), e);
		}

		if (!Check.isEmpty(retValue, true))
		{
			throw new CalloutExecutionException(this, "Error while executing callout: " + retValue);
		}
	}

	private ScriptEngine createScriptEngine()
	{
		final MRule rulePO = LegacyAdapters.convertToPO(rule);
		return rulePO.getScriptEngine();
	}

}
