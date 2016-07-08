package de.metas.adempiere.form.swing;

import java.awt.Color;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;

import javax.annotation.Nullable;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.apps.AEnv;
import org.compiere.util.Env;

import de.metas.adempiere.form.AbstractClientUIInvoker;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;

/* package */class SwingClientUIInvoker extends AbstractClientUIInvoker
{
	public SwingClientUIInvoker(final IClientUIInstance clientUI)
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

		if (isUseSeparateThread())
		{
			return new SwingWorkerRunnable(componentSwing, runnable);
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
					// Show waiting cursor
					componentSwing.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					//
					// Actually invoke the runner
					runnable.run();
				}
				finally
				{
					// Restore the cursor
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

	private static final RootPaneContainer findRootPaneContainer(@Nullable final Component comp)
	{
		final RootPaneContainer rootPaneContainer = AEnv.getParentComponent(comp, RootPaneContainer.class);
		if (rootPaneContainer == null)
		{
			final AdempiereException ex = new AdempiereException("No " + RootPaneContainer.class + " found."
					+ "Ignore, but please check because this could be a development error."
					+ "\n Component: " + comp);
			logger.warn(ex.getLocalizedMessage(), ex);
		}
		return rootPaneContainer;
	}

	private static final Window findWindow(@Nullable final RootPaneContainer rootPaneContainer)
	{
		final Component comp;
		if (rootPaneContainer == null)
		{
			comp = null;
		}
		else if (rootPaneContainer instanceof Window)
		{
			return (Window)rootPaneContainer;
		}
		else if (rootPaneContainer instanceof Component)
		{
			comp = (Component)rootPaneContainer;
		}
		else
		{
			comp = rootPaneContainer.getRootPane();
		}

		final Window window = AEnv.getParentComponent(comp, Window.class);
		if (window == null)
		{
			final AdempiereException ex = new AdempiereException("No " + Window.class + " found."
					+ "Ignore, but please check because this could be a development error."
					+ "\n RootPaneContainer: " + rootPaneContainer
					+ "\n Component: " + comp);
			logger.warn(ex.getLocalizedMessage(), ex);
		}
		return window;
	}

	private final class SwingWorkerRunnable implements Runnable
	{
		// Parameters
		private final Component parentComponent;
		private final Runnable runnable;

		// State
		private Cursor cursorOld;
		private Window window;
		private Component glassPane;
		private Boolean glassPaneVisibleOld;

		public SwingWorkerRunnable(@Nullable final Component parentComponent, final Runnable runnable)
		{
			super();

			this.parentComponent = parentComponent;

			Check.assumeNotNull(runnable, "runnable not null");
			this.runnable = runnable;
		}

		@Override
		public String toString()
		{
			return "SwingWorker[" + runnable + "]";
		}


		@Override
		public void run()
		{
			if (SwingUtilities.isEventDispatchThread())
			{
				runNow();
				return;
			}

			SwingUtilities.invokeLater(() -> runNow());
		}

		private final void runNow()
		{
			beforeRunnable();

			final SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>()
			{
				@Override
				protected Void doInBackground() throws Exception
				{
					System.out.println("Do in background runnable in " + Thread.currentThread() + ", runnable=" + runnable);
					runnable.run();
					return null;
				}

				@Override
				protected void done()
				{
					try
					{
						get(); // => throw exceptions if any
					}
					catch (final Exception e)
					{
						handleException(e);
					}
					finally
					{
						afterRunnable();
					}
				}
			};
			swingWorker.execute();
		}

		/** Before {@link #runnable} was executed. This method runs in EDT. */
		private void beforeRunnable()
		{
			System.out.println("Before runnable in " + Thread.currentThread() + ", runnable=" + runnable);

			final RootPaneContainer rootPaneContainer = findRootPaneContainer(parentComponent);
			if (rootPaneContainer == null)
			{
				return;
			}
			window = findWindow(rootPaneContainer);
			if (window == null)
			{
				return;
			}

			//
			// Cursor
			cursorOld = window.getCursor();
			window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			//
			// Glass pane
			glassPane = rootPaneContainer.getGlassPane();
			if (glassPane == null)
			{
				final AdempiereException ex = new AdempiereException("We were asked to show a glasspane but the root pane does not have a glass pane set."
						+ "Ignore, but please check because this could be a development error."
						+ "\n Root container: " + rootPaneContainer
						+ "\n Runnable: " + runnable);
				logger.warn(ex.getLocalizedMessage(), ex);
				return;
			}
			glassPaneVisibleOld = glassPane.isVisible();
			glassPane.setVisible(true);
			glassPane.setBackground(Color.RED);
		}

		/** After {@link #runnable} was executed. This method runs in EDT. */
		private void afterRunnable()
		{
			System.out.println("After runnable in " + Thread.currentThread() + ", runnable=" + runnable);

			//
			// Hide the glass pane
			if (glassPane != null && glassPaneVisibleOld != null)
			{
				glassPane.setVisible(glassPaneVisibleOld);
			}

			//
			// Restore the cursor
			if (window != null)
			{
				window.setCursor(cursorOld);
			}

			//
			// Reset state
			window = null;
			cursorOld = null;
			//
			glassPane = null;
			glassPaneVisibleOld = null;
		}
	}
}
