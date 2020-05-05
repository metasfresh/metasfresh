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


import java.awt.Graphics;
import java.awt.Rectangle;

import org.compiere.swing.CButton;

import de.metas.adempiere.form.terminal.IExecuteBeforePainingSupport;

/**
 * Swing button implementation to be used in Terminal API.
 *
 * Mainly, it's an extension of {@link CButton} which also supports {@link IExecuteBeforePainingSupport}.
 *
 * In future we can add more things related to our Terminal API look&feel.
 *
 * @author tsa
 *
 */
public class SwingTerminalButton extends CButton implements IExecuteBeforePainingSupport
{
	/**
	 *
	 */
	private static final long serialVersionUID = -5389801189458326537L;

	private final ComponentExecuteBeforePainingSupport executeBeforePaintingSupport = new ComponentExecuteBeforePainingSupport(this);

	public SwingTerminalButton(final String text)
	{
		super(text);
	}

	@Override
	public void paint(final Graphics g)
	{
		executeUpdatesBeforePainting();
		super.paint(g);
	}

	@Override
	public void paintAll(final Graphics g)
	{
		executeUpdatesBeforePainting();
		super.paintAll(g);
	}

	@Override
	public void paintComponents(final Graphics g)
	{
		executeUpdatesBeforePainting();
		super.paintComponents(g);
	}

	@Override
	protected void paintComponent(final Graphics g)
	{
		executeUpdatesBeforePainting();
		super.paintComponent(g);
	}

	@Override
	public void repaint()
	{
		executeUpdatesBeforePainting();
		super.repaint();
	}

	@Override
	public void repaint(final int x, final int y, final int width, final int height)
	{
		executeUpdatesBeforePainting();
		super.repaint(x, y, width, height);
	}

	@Override
	public void repaint(final Rectangle r)
	{
		executeUpdatesBeforePainting();
		super.repaint(r);
	}

	@Override
	public void update(final Graphics g)
	{
		executeUpdatesBeforePainting();
		super.update(g);
	}

	private final void executeUpdatesBeforePainting()
	{
		// guard against null when it's called when object is not yet constructed
		if (executeBeforePaintingSupport == null)
		{
			return;
		}

		executeBeforePaintingSupport.executeEnqueuedUpdaters();
	}

	@Override
	public void executeBeforePainting(final Runnable updater)
	{
		executeBeforePaintingSupport.executeBeforePainting(updater);
	}

	@Override
	public final void clearExecuteBeforePaintingQueue()
	{
		executeBeforePaintingSupport.clearExecuteBeforePaintingQueue();
	}
}
