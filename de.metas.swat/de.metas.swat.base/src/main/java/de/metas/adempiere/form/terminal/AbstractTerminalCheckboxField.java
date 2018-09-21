package de.metas.adempiere.form.terminal;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

public abstract class AbstractTerminalCheckboxField
		extends AbstractTerminalField<Boolean>
		implements ITerminalCheckboxField
{
	private final String name;

	public AbstractTerminalCheckboxField(final ITerminalContext terminalContext, final String name)
	{
		super(terminalContext);

		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	protected Boolean convertValueToType(final Object value)
	{
		if (value == null)
		{
			return Boolean.FALSE;
		}

		if (value instanceof Boolean)
		{
			return (Boolean)value;
		}

		//
		// Attempt conversion from string
		final String valueTrimmed = value.toString().trim();
		if (Check.isEmpty(valueTrimmed))
		{
			return Boolean.FALSE;
		}
		else if ("Y".equals(valueTrimmed))
		{
			return Boolean.TRUE;
		}
		else if ("N".equals(valueTrimmed))
		{
			return Boolean.FALSE;
		}
		else
		{
			return Boolean.valueOf(valueTrimmed);
		}
	}
}
