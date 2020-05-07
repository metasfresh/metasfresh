/**
 *
 */
package de.metas.adempiere.form.terminal.swing;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.LayoutManager;
import java.lang.ref.WeakReference;

import javax.swing.JPanel;

import org.adempiere.util.Check;
import org.jdesktop.swingx.JXPanel;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import net.miginfocom.swing.MigLayout;

/**
 * @author tsa
 *
 */
/* package */class SwingTerminalContainer implements IContainer
{
	public static JPanel castPanelComponent(final Object panel)
	{
		return (JPanel)panel;
	}

	private final WeakReference<ITerminalFactory> terminalFactoryRef;

	private final JPanel panel;

	private boolean disposed = false;

	protected SwingTerminalContainer(final SwingTerminalFactory terminalFactory)
	{
		this(terminalFactory, "fill, ins 0 0");
	}

	protected SwingTerminalContainer(final SwingTerminalFactory terminalFactory, final String layoutConstraints)
	{
		this(terminalFactory,
				layoutConstraints,
				(String)null,  // colConstraints,
				(String)null // rowConstraints
		);
	}

	protected SwingTerminalContainer(final SwingTerminalFactory terminalFactory, final String layoutConstraints, final String colConstraints)
	{
		this(terminalFactory,
				layoutConstraints,
				colConstraints,
				(String)null // rowConstraints
		);
	}

	public SwingTerminalContainer(SwingTerminalFactory terminalFactory, String layoutConstraints, String colConstraints, String rowConstraints)
	{
		this(terminalFactory, createPanelComponent());
		final String layoutConstraintsToUse = terminalFactory.createMigLayoutConstraints(layoutConstraints);
		panel.setLayout(new MigLayout(layoutConstraintsToUse, colConstraints, rowConstraints));
	}

	public SwingTerminalContainer(final SwingTerminalFactory terminalFactory, final JPanel panel)
	{
		super();
		Check.assumeNotNull(terminalFactory, "terminalFactory not null");
		this.terminalFactoryRef = new WeakReference<ITerminalFactory>(terminalFactory);

		Check.assumeNotNull(panel, "panel not null");
		this.panel = panel;

		terminalFactory.getTerminalContext().addToDisposableComponents(this);
	}

	private static final JPanel createPanelComponent()
	{
		// NOTE: use JXPanel instead of JPanel because JXPanel is much more friendly combined with JScrollPane.
		// * JPanel was fucking up the layout
		// * JXPanel implements Scrollable and it has options like setScrollable Width/Height ...
		// NOTE2: de.metas.adempiere.form.terminal.swing.SwingTerminalScrollPane knows how to handle this
		final JXPanel panel = new JXPanel();

		return panel;
	}

	@Override
	public void add(final IComponent component, final Object constraints)
	{
		SwingTerminalFactory.addChild(this, component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		SwingTerminalFactory.addChildAfter(this, component, componentBefore, constraints);
	}

	@Override
	public JPanel getComponent()
	{
		return panel;
	}

	@Override
	public void remove(final IComponent component)
	{
		SwingTerminalFactory.removeChild(this, component);
	}

	private final ITerminalFactory getTerminalFactory()
	{
		final ITerminalFactory terminalFactory = terminalFactoryRef.get();
		if (terminalFactory == null)
		{
			throw new RuntimeException("Terminal Factory expired");
		}
		return terminalFactory;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();
		return terminalFactory.getTerminalContext();
	}

	@Override
	public void removeAll()
	{
		// NOTE: because MigLayout is EXTREMELLY slow when removing a lot of components
		// if we are not setting container's layout to null and then removeAll
		// then removing something like 1000 components will take an eternity

		final LayoutManager layoutOld = panel.getLayout();

		panel.setLayout(null);
		panel.removeAll();

		panel.setLayout(layoutOld);
	}

	@Override
	public void dispose()
	{
		removeAll();
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}
}
