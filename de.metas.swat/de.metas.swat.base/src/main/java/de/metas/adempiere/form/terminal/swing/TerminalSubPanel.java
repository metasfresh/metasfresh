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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.compiere.apps.AppsAction;
import org.compiere.swing.CButton;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalPanel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * Terminal Sub Panel Base Class.
 *
 * @author Teo Sarca
 * @author Comunidad de Desarrollo OpenXpertya *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright (c) Jorg Janke
 *
 */
public abstract class TerminalSubPanel
		implements ITerminalPanel, ActionListener
{
	public final IContainer panel;

	public TerminalSubPanel(ITerminalBasePanel basePanel)
	{
		super();
		p_basePanel = basePanel;
		panel = getTerminalFactory().createContainer();
		init();

		getTerminalContext().addToDisposableComponents(this);
	}

	protected ITerminalBasePanel p_basePanel;
	protected Properties p_ctx = Env.getCtx();

	private boolean disposed = false;

	/** Button Width = 50 */
	private static final int WIDTH = 50;
	/** Button Height = 50 */
	private static final int HEIGHT = 50;

	@Override
	public Properties getCtx()
	{
		return getTerminalBasePanel().getCtx();
	}

	/**
	 * Initialize
	 */
	protected abstract void init();

	/**
	 * Does nothing, only sets our internal disposed flag.
	 */
	@Override
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}

	public ITerminalBasePanel getTerminalBasePanel()
	{
		return p_basePanel;
	}

	protected Container getUI()
	{
		return SwingTerminalFactory.getUI(this);
	}

	/**
	 * Create Action Button
	 *
	 * @param action
	 *            action
	 * @return button
	 */
	protected ITerminalButton createButtonAction(String action, KeyStroke accelerator, float fontSize)
	{
		AppsAction act = AppsAction.builder()
				.setAction(action)
				.setAccelerator(accelerator)
				.build();
		act.setDelegate(this);
		CButton button = (CButton)act.getButton();
		if (fontSize > 0f)
		{
			button.setFont(button.getFont().deriveFont(fontSize));
		}
		button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		button.setMinimumSize(getPreferredSize());
		button.setMaximumSize(getPreferredSize());
		button.setFocusable(false);
		return new SwingTerminalButtonWrapper(getTerminalContext(), button);
	} // getButtonAction

	private Dimension getPreferredSize()
	{
		return SwingTerminalFactory.getUI(panel).getPreferredSize();
	}

	/**
	 * Create Standard Button
	 *
	 * @param text
	 *            text
	 * @return button
	 */
	protected CButton createButton(String text)
	{
		CButton button = new CButton(text);
		button.addActionListener(this);
		button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		button.setMinimumSize(getPreferredSize());
		button.setMaximumSize(getPreferredSize());
		button.setFocusable(false);
		return button;
	} // getButton

	@Override
	public void actionPerformed(ActionEvent e)
	{
	} // actionPerformed

	@Override
	public JPanel getComponent()
	{
		return SwingTerminalContainer.castPanelComponent(panel.getComponent());
	}

	@Override
	public void add(IComponent component, Object constraints)
	{
		panel.add(component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		panel.addAfter(component, componentBefore, constraints);
	}

	@Override
	public void remove(IComponent component)
	{
		panel.remove(component);
	}

	@Override
	public void removeAll()
	{
		panel.removeAll();
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return getTerminalBasePanel().getTerminalFactory();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return getTerminalBasePanel().getTerminalContext();
	}

} // PosSubPanel
