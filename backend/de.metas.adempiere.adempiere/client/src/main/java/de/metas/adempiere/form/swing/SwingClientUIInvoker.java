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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.MetasfreshGlassPane;
import org.compiere.apps.AEnv;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.Throwables;

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

		return new Runnable()
		{
			@Override
			public String toString()
			{
				return "LongOp[" + runnable + "]";
			}

			private Component cursorHolder;
			private Cursor cursorOld;

			@Override
			public void run()
			{
				try
				{
					showWaitingCursor();

					//
					// Actually invoke the runner
					runnable.run();
				}
				finally
				{
					hideWaitingCursor();
				}

			}

			private final void showWaitingCursor()
			{
				// If we were also asked to show the glass pane, don't show the waiting cursor here
				if (isShowGlassPane())
				{
					return;
				}
				
				cursorHolder = componentSwing;
				if (cursorHolder != null)
				{
					cursorOld = cursorHolder.getCursor();
					cursorHolder.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				}
			}

			private final void hideWaitingCursor()
			{
				final Component cursorHolder = this.cursorHolder;
				this.cursorHolder = null;

				if (cursorHolder != null)
				{
					cursorHolder.setCursor(cursorOld);
				}
			}
		};
	}

	@Override
	protected Runnable asShowGlassPaneRunnable(final Runnable runnable)
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
				return "ShowGlassPane[" + runnable + "]";
			}

			private RootPaneContainer rootPaneContainer;
			private Window window;
			//
			private Component cursorHolder;
			private Cursor cursorOld;
			//
			private MetasfreshGlassPane glassPane;
			private boolean glassPaneVisibleOld;
			private String glassPaneMessageOld;

			@Override
			public void run()
			{

				try
				{
					prepareUI();

					//
					// Actually invoke the runner
					// NOTE: we need to use invoke later because if we are not calling the our runnable in next UI event, the glass pane will never be displayed.
					if(isInvokeLater())
					{
						// Case: the invoke later was already applied. So there is no need to apply it twice.
						executeRunnableAndCleanupUI(runnable);
					}
					else
					{
						SwingUtilities.invokeLater(() -> executeRunnableAndCleanupUI(runnable));
					}
				}
				catch (Exception e)
				{
					cleanupUI();
					throw Throwables.propagate(e);
				}

			}
			
			private final void executeRunnableAndCleanupUI(final Runnable runnable)
			{
				try
				{
					runnable.run();
				}
				finally
				{
					cleanupUI();
				}
			}
			
			private final void prepareUI()
			{
				// Get the root pane container and window.
				rootPaneContainer = findRootPaneContainer(componentSwing);
				window = findWindow(rootPaneContainer);
				
				showWaitingCursor();
				showGlassPane();
			}

			private final void cleanupUI()
			{
				hideGlassPane();
				hideWaitingCursor();

				rootPaneContainer = null;
				window = null;
			}

			private final void showWaitingCursor()
			{
				cursorHolder = Util.coalesce(window, componentSwing);
				if (cursorHolder != null)
				{
					cursorOld = cursorHolder.getCursor();
					cursorHolder.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				}
			}

			private final void hideWaitingCursor()
			{
				final Component cursorHolder = this.cursorHolder;
				this.cursorHolder = null;

				if (cursorHolder != null)
				{
					cursorHolder.setCursor(cursorOld);
				}
			}

			private final void showGlassPane()
			{
				if (window == null)
				{
					final String errmsg = "We were asked to show a glasspane but no window found."
							+ "Ignore, but please check because this could be a development error."
							+ "\n Component: " + componentSwing
							+ "\n Runnable: " + runnable;
					new AdempiereException(errmsg).throwIfDeveloperModeOrLogWarningElse(logger);
					return;
				}
				glassPane = MetasfreshGlassPane.getOrNull(rootPaneContainer);
				if (glassPane == null)
				{
					final String errmsg = "We were asked to show a glasspane but the root pane does not have a glass pane set."
							+ "Ignore, but please check because this could be a development error."
							+ "\n Component: " + componentSwing
							+ "\n Root container: " + rootPaneContainer
							+ "\n Runnable: " + runnable;
					new AdempiereException(errmsg).throwIfDeveloperModeOrLogWarningElse(logger);
					return;
				}

				glassPaneVisibleOld = glassPane.isVisible();
				glassPaneMessageOld = glassPane.getMessagePlain();
				glassPane.setVisible(true);
			}

			private final void hideGlassPane()
			{
				final MetasfreshGlassPane glassPane = this.glassPane;
				this.glassPane = null;

				if (glassPane == null)
				{
					return;
				}

				glassPane.setBusyTimer(0);
				glassPane.setVisible(glassPaneVisibleOld);
				glassPane.setMessagePlain(glassPaneMessageOld);
			}
		};
	}

	private final RootPaneContainer findRootPaneContainer(@Nullable final Component comp)
	{
		final RootPaneContainer rootPaneContainer = AEnv.getParentComponent(comp, RootPaneContainer.class);
		if (rootPaneContainer == null)
		{
			final String errmsg = "No " + RootPaneContainer.class + " found."
					+ "Ignore, but please check because this could be a development error."
					+ "\n Component: " + comp
					+ "\n Executor: " + this;
			new AdempiereException(errmsg).throwIfDeveloperModeOrLogWarningElse(logger);
		}
		return rootPaneContainer;
	}

	private final Window findWindow(@Nullable final RootPaneContainer rootPaneContainer)
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
			final String errmsg = "No " + Window.class + " found."
					+ "Ignore, but please check because this could be a development error."
					+ "\n RootPaneContainer: " + rootPaneContainer
					+ "\n Component: " + comp
					+ "\n Executor: " + this;
			new AdempiereException(errmsg).throwIfDeveloperModeOrLogWarningElse(logger);
		}
		return window;
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
