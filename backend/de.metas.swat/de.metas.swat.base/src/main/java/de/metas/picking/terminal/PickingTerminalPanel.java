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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IPair;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalLoginDialog;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.logging.LogManager;

/**
 * @author cg
 *
 */
public abstract class PickingTerminalPanel implements ITerminalBasePanel
{

	protected final Logger log = LogManager.getLogger(getClass());

	private final IPair<ITerminalContext, ITerminalContextReferences> terminalContextAndRefs;
	private IContainer panel;
	private PickingOKPanel pickingOKPanel;

	private boolean _disposed = false;

	public PickingTerminalPanel()
	{
		terminalContextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		getTerminalContext().addToDisposableComponents(this);
	}

	@Override
	public Object getComponent()
	{
		return this;
	}

	public final PickingOKPanel getPickingOKPanel()
	{
		return pickingOKPanel;
	}

	/**
	 * It's not the job of this class to care about the given <code>pickingOKPanel</code>'s disposal!
	 *
	 * @param pickingOKPanel
	 */
	protected final void setPickingOKPanel(final PickingOKPanel pickingOKPanel)
	{
		Check.assumeNull(this.pickingOKPanel, "pickingOKPanel was not initialized before");
		Check.assumeNotNull(pickingOKPanel, "pickingOKPanel not null");
		this.pickingOKPanel = pickingOKPanel;
	}

	@Override
	public void add(final IComponent component, final Object constraints)
	{
		panel.add(component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		panel.addAfter(component, componentBefore, constraints);
	}

	@Override
	public void remove(final IComponent component)
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
		return terminalContextAndRefs.getLeft();
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
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
		return getTerminalContext().getCtx();
	}

	@Override
	public final void keyPressed(final ITerminalKey key)
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
		if(isDisposed())
		{
			// This method might be called by both the swing framework and ITerminalContext.
			// Therefore we need to make sure not to try and call deleteReferences() twice because the second time there will be an error.
			return;
		}

		// it's important to do this before calling deleteReferences(), because this instance itself was also added as a removable component.
		// so,  deleteReferences() will also call this dispose() method, and we want to avoid a stack overflow error.
		// note: alternatively, we could also add a _disposing variable, like we do e.g. in AbstractHUSelectFrame.
		_disposed = true;

		getTerminalContext().deleteReferences(terminalContextAndRefs.getRight());

		final TerminalContextFactory terminalContextFactory = TerminalContextFactory.get();
		terminalContextFactory.destroy(getTerminalContext());
	}

	@Override
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

	private void setAD_User_ID(final int AD_User_ID)
	{
		getTerminalContext().setAD_User_ID(AD_User_ID);
	}

	private int getAD_User_ID()
	{
		return getTerminalContext().getAD_User_ID();
	}
}
