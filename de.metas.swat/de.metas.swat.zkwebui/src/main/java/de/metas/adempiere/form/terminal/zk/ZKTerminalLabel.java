/**
 * 
 */
package de.metas.adempiere.form.terminal.zk;

/*
 * #%L
 * de.metas.swat.zkwebui
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

import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;

import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 * 
 */
public class ZKTerminalLabel implements ITerminalLabel
{
	private final ITerminalContext tc;
	private final org.adempiere.webui.component.Label label;

	protected ZKTerminalLabel(final ITerminalContext tc, final String label)
	{
		this.tc = tc;
		this.label = new org.adempiere.webui.component.Label(Msg.translate(tc.getCtx(), label));
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
		return label.getValue();
	}

	@Override
	public void setLabel(final String label)
	{
		this.label.setValue(label);
	}

	@Override
	public void setFont(final float size)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBackgroundColor(final Color color)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Color getBackgroundColor()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}
}
