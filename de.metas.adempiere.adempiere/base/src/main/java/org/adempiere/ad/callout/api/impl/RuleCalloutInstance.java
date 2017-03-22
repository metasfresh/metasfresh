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

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.X_AD_Rule;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.script.IADRuleDAO;
import de.metas.script.ScriptEngineFactory;
import de.metas.script.ScriptExecutor;

public final class RuleCalloutInstance implements ICalloutInstance
{
	public static final Supplier<ICalloutInstance> supplier(final String ruleValue)
	{
		Check.assumeNotEmpty(ruleValue, "ruleValue not empty");

		final Properties ctx = Env.getCtx();

		final I_AD_Rule rule = Services.get(IADRuleDAO.class).retrieveByValue(ctx, ruleValue.trim());
		if (rule == null)
		{
			throw new CalloutInitException("Cannot find rule for callout value '" + ruleValue + "'");
		}

		if (!X_AD_Rule.EVENTTYPE_Callout.equals(rule.getEventType()))
		{
			throw new CalloutInitException("Invalid callout rule " + rule + ". EventType shall be Callout");
		}

		final String id = RuleCalloutInstance.class.getSimpleName() + "-" + ruleValue.trim();
		final String script = rule.getScript();
		final Supplier<ScriptExecutor> scriptExecutorSupplier = ScriptEngineFactory.get().createExecutorSupplier(rule);

		return () -> new RuleCalloutInstance(id, script, scriptExecutorSupplier);
	}

	private static final transient Logger logger = LogManager.getLogger(RuleCalloutInstance.class);

	private final String id;
	private final String script;
	private final Supplier<ScriptExecutor> scriptExecutorSupplier;


	public RuleCalloutInstance(final String id, final String script, final Supplier<ScriptExecutor> scriptExecutorSupplier)
	{
		super();

		this.id = id;
		this.script = script;
		this.scriptExecutorSupplier = scriptExecutorSupplier;
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
		final GridField gridField = (field instanceof GridField ? (GridField)field : null);

		try
		{
			scriptExecutorSupplier.get()
					.putContext(ctx, windowNo)
					.putArgument("Value", value)
					.putArgument("OldValue", valueOld)
					.putArgument("Field", gridField)
					.putArgument("Tab", gridField == null ? null : gridField.getGridTab())
					.setThrowExceptionIfResultNotEmpty()
					.execute(script);
		}
		catch (final Exception e)
		{
			final String errmsg = CalloutExecutionException.extractMessage(e);
			throw new CalloutExecutionException(this, errmsg, e);
		}
	}
}
