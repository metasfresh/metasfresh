package de.metas.adempiere.form.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Component;
import java.awt.Cursor;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.SwingUtilities;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import de.metas.adempiere.form.AbstractClientUIInvoker;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;

/* package */class SwingClientUIInvoker extends AbstractClientUIInvoker
{
	public SwingClientUIInvoker(IClientUIInstance clientUI)
	{
		super(clientUI);
	}

	@Override
	public final IClientUIInvoker setParentComponent(final Object parentComponent)
	{
		final Component componentSwing = (Component)parentComponent;
		final int windowNo = Env.getWindowNo(componentSwing);
		setParentComponent(windowNo, componentSwing);
		return this;
	}

	@Override
	public final IClientUIInvoker setParentComponentByWindowNo(final int windowNo)
	{
		final Component window = Env.getWindow(windowNo);
		setParentComponent(windowNo, window);
		return this;
	}

	@Override
	protected Runnable asLongOperationRunnable(final Runnable runnable)
	{
		final Component componentSwing = (Component)getParentComponent();
		if (componentSwing == null)
		{
			if (developerModeBL.isEnabled())
			{
				final AdempiereException ex = new AdempiereException("We were asked to run as a long operation but the parent component is not set."
						+ "Ignore, but please check because this could be a development error."
						+ "\n Invoker: " + this
						+ "\n Runnable: " + runnable);
				logger.warn(ex.getLocalizedMessage(), ex);
			}

			return runnable;
		}

		return new Runnable()
		{
			@Override
			public String toString()
			{
				return "LongOp[" + runnable + "]";
			}

			@Override
			public void run()
			{
				final Cursor cursorOld = componentSwing.getCursor();
				try
				{
					componentSwing.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					runnable.run();
					// final Thread thread = createUserThread(runnable, null); // TODO (won't work in Swing event queue)
					// thread.start();
					// thread.join();
				}
				finally
				{
					componentSwing.setCursor(cursorOld);
				}
			}

		};
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
				SwingUtilities.invokeLater(runnable);
			}
		};
	}

}
