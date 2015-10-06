package de.metas.handlingunits.client.terminal.select.view;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

import javax.swing.JFrame;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.DisposableHelper;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.adempiere.form.terminal.event.MethodActionForwardListener;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;

/**
 * Frame Panel for HU POS, first screens.
 *
 * It decorates a given {@link JFrame}.
 *
 * @author tsa
 *
 * @param <MT> model type
 */
public abstract class AbstractHUSelectFrame<MT> implements IComponent
{
	private ITerminalContext terminalContext;
	private JFrame frame;

	private IHUSelectPanel huSelectPanel;

	public AbstractHUSelectFrame(final JFrame frame, final int windowNo)
	{
		super();

		//
		// Create Terminal Context
		this.terminalContext = TerminalContextFactory.get().createContext();
		this.terminalContext.setWindowNo(windowNo);

		//
		// Setup Panel
		huSelectPanel = createHUSelectPanel();
		huSelectPanel.addPropertyChangeListener(
				IHUSelectPanel.PROPERTY_Disposed,
				new MethodActionForwardListener(this, ACTIONMETHOD_Dispose));

		//
		// Init frame
		Check.assumeNotNull(frame, "frame not null");
		this.frame = frame;
		initFrame();

		//
		// Register it as disposable (to be automatically cleaned up in case is needed)
		terminalContext.addToDisposableComponents(this);
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	@Override
	public final Object getComponent()
	{
		return frame;
	}

	protected final IHUSelectPanel getHUSelectPanel()
	{
		return huSelectPanel;
	}

	protected abstract IHUSelectPanel createHUSelectPanel();

	private final void initFrame()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final Dimension screenResolution = terminalContext.getScreenResolution();

		frame.setLayout(new BorderLayout());

		final Component documentSelectPanelComp = SwingTerminalFactory.getUI(huSelectPanel);

		frame.getContentPane().add(documentSelectPanelComp, BorderLayout.CENTER);

		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setResizable(true);
		frame.setJMenuBar(null);
		frame.setMinimumSize(screenResolution);
		frame.setPreferredSize(screenResolution);
		frame.setMaximumSize(screenResolution);

		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(final WindowEvent e)
			{
				dispose();
			}
		});
	}

	// static because we need to introspect this only once
	public static final Method ACTIONMETHOD_Dispose = MethodActionForwardListener.findActionMethod(AbstractHUSelectFrame.class, "dispose");

	@Override
	public final void dispose()
	{
		if (_disposing)
		{
			return;
		}

		_disposing = true;
		try
		{
			if (frame != null)
			{
				frame.dispose();
				frame = null;
			}

			huSelectPanel = DisposableHelper.dispose(huSelectPanel);

			//
			// Destroy the context / terminal factory
			final TerminalContextFactory terminalContextFactory = TerminalContextFactory.get();
			terminalContextFactory.destroy(terminalContext);
			terminalContext = null;
		}
		finally
		{
			_disposing = false;
		}
	}

	private boolean _disposing = false;

}
