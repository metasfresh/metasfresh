package org.adempiere.ad.callout.api.impl;

import com.google.common.base.MoreObjects;
import de.metas.script.IADRuleDAO;
import de.metas.script.ScriptEngineFactory;
import de.metas.script.ScriptExecutor;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.exceptions.CalloutExecutionException;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Rule;
import org.compiere.model.X_AD_Rule;
import org.compiere.util.Env;

import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;

public final class RuleCalloutInstance implements ICalloutInstance
{
	public static Supplier<ICalloutInstance> supplier(final String ruleValue)
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
			throw CalloutExecutionException.wrapIfNeeded(e)
					.setCalloutInstance(this);
		}
	}
}
