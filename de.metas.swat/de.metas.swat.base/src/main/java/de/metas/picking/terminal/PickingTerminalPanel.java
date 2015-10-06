/**
 * 
 */
package de.metas.picking.terminal;

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


import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.util.CLogger;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalLoginDialog;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;

/**
 * @author cg
 * 
 */
public abstract class PickingTerminalPanel implements ITerminalBasePanel
{

	protected final CLogger log = CLogger.getCLogger(getClass());

	private ITerminalContext terminalContext;
	protected IContainer panel;
	private PickingOKPanel pickingOKPanel;

	private boolean _disposed = false;

	public PickingTerminalPanel()
	{
		super();
		// Init Context:
		this.terminalContext = TerminalContextFactory.get().createContext();
	}

	@Override
	public Object getComponent()
	{
		return this;
	}

	@Override
	public final ITerminalFactory getTerminalFactory()
	{
		return terminalContext.getTerminalFactory();
	}

	public final PickingOKPanel getPickingOKPanel()
	{
		return pickingOKPanel;
	}

	protected void setPickingOKPanel(final PickingOKPanel pickingOKPanel)
	{
		Check.assumeNull(this.pickingOKPanel, "pickingOKPanel was not initialized before");
		Check.assumeNotNull(pickingOKPanel, "pickingOKPanel not null");
		this.pickingOKPanel = pickingOKPanel;
	}

	@Override
	public void add(IComponent component, Object constraints)
	{
		panel.add(component, constraints);
	}

	@Override
	public void addAfter(IComponent component, IComponent componentBefore, Object constraints)
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
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public void updateInfo()
	{
		pickingOKPanel.setIsPos(true);
		pickingOKPanel.setSelection(pickingOKPanel.getSelectedScheduleIds(pickingOKPanel.getMiniTable()));
		pickingOKPanel.createPackingDetails(getCtx(), pickingOKPanel.getSelectedRows());
	}

	@Override
	public Properties getCtx()
	{
		return terminalContext.getCtx();
	}

	@Override
	public final void keyPressed(ITerminalKey key)
	{
		// nothing to do
	}

	@Override
	public void logout()
	{
		doLogin();
	}

	protected abstract void setFrame(Object frame);

	protected abstract void setFrameVisible(boolean visible);

	@Override
	public void dispose()
	{
		if (pickingOKPanel != null)
		{
			pickingOKPanel.dispose();
			pickingOKPanel = null;
		}

		final TerminalContextFactory terminalContextFactory = TerminalContextFactory.get();
		terminalContextFactory.destroy(terminalContext);
		terminalContext = null;
		
		_disposed = true;
	}
	
	public final boolean isDisposed()
	{
		return _disposed;
	}

	protected void doLogin()
	{
		while (true)
		{
			setFrameVisible(false);
			final ITerminalLoginDialog login = getTerminalFactory().createTerminalLoginDialog(this);

			if (getAD_User_ID() >= 0)
			{
				login.setAD_User_ID(getAD_User_ID());
			}

			login.activate();
			if (login.isExit())
			{
				dispose();
				return;
			}
			else if (login.isLogged())
			{
				setAD_User_ID(login.getAD_User_ID());
				setFrameVisible(true);
				return;
			}
		}
	}

	private void setAD_User_ID(int AD_User_ID)
	{
		terminalContext.setAD_User_ID(AD_User_ID);
	}

	public int getAD_User_ID()
	{
		return terminalContext.getAD_User_ID();
	}
}
