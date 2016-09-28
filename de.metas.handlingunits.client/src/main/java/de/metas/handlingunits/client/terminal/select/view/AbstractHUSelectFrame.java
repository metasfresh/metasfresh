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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
import org.adempiere.util.lang.IPair;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
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
	private final IPair<ITerminalContext, ITerminalContextReferences> contextAndRefs;

	private JFrame frame;

	private final IHUSelectPanel huSelectPanel;

	private boolean _disposing = false;

	private boolean disposed = false;

	public AbstractHUSelectFrame(final JFrame frame, final int windowNo)
	{
		//
		// Create Terminal Context
		contextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		final ITerminalContext terminalContext = contextAndRefs.getLeft();

		terminalContext.setWindowNo(windowNo);

		//
		// Setup Panel
		huSelectPanel = createHUSelectPanel();
		terminalContext.addToDisposableComponents(huSelectPanel);

		huSelectPanel.addPropertyChangeListener(
				IHUSelectPanel.PROPERTY_Disposed,
				new MethodActionForwardListener(this, ACTIONMETHOD_Dispose));

		//
		// Init frame
		Check.assumeNotNull(frame, "frame not null");
		this.frame = frame;
		initFrame();

		//
		// Register this as disposable (to be automatically cleaned up)
		contextAndRefs.getLeft().addToDisposableComponents(this);
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return contextAndRefs.getLeft();
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

	/**
	 * Implementors don't need call {@link ITerminalContext#addToDisposableComponents(de.metas.adempiere.form.terminal.IDisposable)} for the new panel.
	 * That's already done by the caller of this method.
	 *
	 * @return
	 */
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
		if (isDisposed())
		{
			// This method can be called by both the WindowAdapter and ITerminalContext.
			// Therefore we need to make sure not to try and call deleteReferences() twice because the second time there will be an error.
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

			// delete this instance's references in an orderly fashion
			final ITerminalContext terminalContext = contextAndRefs.getLeft();
			final ITerminalContextReferences references = contextAndRefs.getRight();
			terminalContext.deleteReferences(references);

			//
			// Destroy the context / terminal factory
			final TerminalContextFactory terminalContextFactory = TerminalContextFactory.get();
			terminalContextFactory.destroy(terminalContext);
			disposed = true;
		}
		finally
		{
			_disposing = false;
		}
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}
}
