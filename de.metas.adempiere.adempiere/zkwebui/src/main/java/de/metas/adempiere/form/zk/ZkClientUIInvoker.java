package de.metas.adempiere.form.zk;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.event.ZkInvokeLaterSupport;
import org.adempiere.webui.session.SessionManager;

import de.metas.adempiere.form.AbstractClientUIInvoker;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;

public class ZkClientUIInvoker extends AbstractClientUIInvoker
{

	public ZkClientUIInvoker(IClientUIInstance clientUI)
	{
		super(clientUI);
	}

	@Override
	protected Runnable asInvokeLaterRunnable(final Runnable runnable)
	{
		return new Runnable()
		{
			@Override
			public String toString()
			{
				return "InvokeLater[" + runnable + "]";
			}

			@Override
			public void run()
			{
				ZkInvokeLaterSupport.invokeLater(getParentWindowNo(), runnable);
			}
		};
	}

	@Override
	protected Runnable asLongOperationRunnable(Runnable runnable)
	{
		// TODO: not implemented, but we pass it thru now
		return runnable;
	}

	@Override
	public IClientUIInvoker setParentComponent(Object parentComponent)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IClientUIInvoker setParentComponentByWindowNo(int windowNo)
	{
		IDesktop desktop = SessionManager.getAppDesktop();
		if (desktop == null)
		{
			return this;
		}

		final Object win = desktop.findWindow(windowNo);
		setParentComponent(windowNo, win);
		return this;
	}

}
