package de.metas.adempiere.form.terminal.swing.jmx;

/*
 * #%L
 * de.metas.swat.base
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


import java.lang.ref.WeakReference;
import java.util.UUID;

import org.adempiere.util.jmx.IJMXNameAware;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.util.Check;

public class JMXSwingTerminalFactory implements JMXSwingTerminalFactoryMBean, IJMXNameAware
{
	private final WeakReference<SwingTerminalFactory> terminalFactoryRef;
	private final String jmxName;

	public JMXSwingTerminalFactory(final SwingTerminalFactory terminalFactory)
	{
		super();

		Check.assumeNotNull(terminalFactory, "terminalFactory not null");
		// NOTE: we are creating a weak reference to not prevent "terminalFactory" from being garbage collected
		this.terminalFactoryRef = new WeakReference<SwingTerminalFactory>(terminalFactory);

		this.jmxName = JMXSwingTerminalFactory.class.getName() + ":type=Instance-" + UUID.randomUUID();

	}

	@Override
	public String getJMXName()
	{
		return jmxName;
	}

	private final SwingTerminalFactory getSwingTerminalFactory()
	{
		final SwingTerminalFactory terminalFactory = terminalFactoryRef.get();
		if (terminalFactory == null)
		{
			throw new RuntimeException("SwingTerminalFactory instance is not available anymore");
		}
		return terminalFactory;
	}

	@Override
	public String getTerminalContextStr()
	{
		return getSwingTerminalFactory().getTerminalContext().toString();
	}

	@Override
	public boolean isMigLayoutDebugEnabled()
	{
		return getSwingTerminalFactory().isMigLayoutDebugEnabled();
	}

	@Override
	public void setMigLayoutDebugEnabled(boolean migLayoutDebugEnabled)
	{
		getSwingTerminalFactory().setMigLayoutDebugEnabled(migLayoutDebugEnabled);
	}

	@Override
	public void destroyContext()
	{
		final ITerminalContext terminalContext = getSwingTerminalFactory().getTerminalContext();
		TerminalContextFactory.get().destroy(terminalContext);
	}

}
