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


import java.awt.Color;
import java.awt.Component;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CLabel;

import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/* package */class SwingTerminalLabel implements ITerminalLabel, IComponentSwing
{
	private final ITerminalContext tc;
	private final CLabel label;

	private boolean disposed = false;

	/**
	 * @param tc
	 * @param label
	 * @param translate true if label needs to be translated (so it's not already translated)
	 */
	protected SwingTerminalLabel(final ITerminalContext tc, final String label, final boolean translate)
	{
		this.tc = tc;

		final String labelTrl = translate ? Services.get(IMsgBL.class).translate(tc.getCtx(), label) : label;
		this.label = new CLabel(labelTrl);

		// override the font size
		final float fontSize = tc.getDefaultFontSize();
		if (fontSize != 0)
		{
			this.label.setFont(this.label.getFont().deriveFont(fontSize));
		}

		tc.addToDisposableComponents(this);
	}

	@Override
	public Component getComponent()
	{
		return label;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public String getLabel()
	{
		return label.getText();
	}

	@Override
	public void setLabel(final String label)
	{
		this.label.setText(label);
	}

	@Override
	public void setFont(final float size)
	{
		label.setFont(label.getFont().deriveFont(size));
	}

	@Override
	public void setBackgroundColor(final Color color)
	{
		label.setBackground(color);
	}

	@Override
	public Color getBackgroundColor()
	{
		return label.getBackground();
	}

	/**
	 * Does nothing, only sets our internal disposed flag.
	 */
	@Override
	public void dispose()
	{
		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}
}
