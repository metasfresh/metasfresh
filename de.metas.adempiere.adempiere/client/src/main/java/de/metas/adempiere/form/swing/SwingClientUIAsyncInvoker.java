package de.metas.adempiere.form.swing;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Window;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.MetasfreshGlassPane;
import org.adempiere.util.Check;
import org.compiere.apps.AEnv;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.adempiere.form.IClientUIAsyncInvoker;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
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

class SwingClientUIAsyncInvoker implements IClientUIAsyncInvoker
{
	private static final Logger logger = LogManager.getLogger(SwingClientUIAsyncInvoker.class);

	private Component _parentComponent;

	private Object _initialValue;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("initialValue", _initialValue)
				.add("parentComponent", _parentComponent)
				.toString();
	}

	@Override
	public <InitialValueType, ResultType, PartialResultType> void invoke(final IClientUIAsyncRunnable<InitialValueType, ResultType, PartialResultType> asyncRunnable)
	{
		final Component parentComponent = getParentComponent();
		final InitialValueType initialValue = getInitialValue();
		final AsyncExecutor<InitialValueType, ResultType, PartialResultType> executor = new AsyncExecutor<>(parentComponent, asyncRunnable, initialValue);
		executor.prepareAndExecute();
	}

	@Override
	public IClientUIAsyncInvoker setParentComponent(final Object parentComponentObj)
	{
		_parentComponent = (Component)parentComponentObj;
		return this;
	}

	private Component getParentComponent()
	{
		return _parentComponent;
	}

	@Override
	public <InitialValueType> IClientUIAsyncInvoker setInitialValue(final InitialValueType initialValue)
	{
		_initialValue = initialValue;
		return this;
	}

	private final <InitialValueType> InitialValueType getInitialValue()
	{
		@SuppressWarnings("unchecked")
		final InitialValueType initialValueTypeCasted = (InitialValueType)_initialValue;
		return initialValueTypeCasted;
	}

	private static final class AsyncExecutor<InitialValueType, ResultType, PartialResultType>
			extends SwingWorker<ResultType, PartialResultType>
			implements IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType>
	{
		// Parameters
		private final Component parentComponent;
		private final IClientUIAsyncRunnable<InitialValueType, ResultType, PartialResultType> runnable;
		private final InitialValueType initialValue;
		private int _estimatedDurationSeconds = 0;
		private String _waitingMessage = null;

		// State
		private Cursor cursorOld;
		private Window window;
		private MetasfreshGlassPane glassPane;
		private boolean glassPaneVisibleOld;
		private String glassPaneMessageOld;

		public AsyncExecutor(
				final Component parentComponent //
				, final IClientUIAsyncRunnable<InitialValueType, ResultType, PartialResultType> runnable //
				, final InitialValueType initialValue //
		)
		{
			super();
			this.parentComponent = parentComponent;

			Check.assumeNotNull(runnable, "runnable not null");
			this.runnable = runnable;

			this.initialValue = initialValue;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("runnable", runnable)
					.add("inialValue", initialValue)
					.add("state", getState())
					.add("estimatedDurationSeconds", _estimatedDurationSeconds > 0 ? _estimatedDurationSeconds : null)
					.add("parentComponent", parentComponent)
					.omitNullValues()
					.toString();
		}

		@Override
		public final InitialValueType getInitialValue()
		{
			return initialValue;
		}

		@Override
		public void setEstimatedDurationSeconds(final int estimatedDurationSeconds)
		{
			if (this._estimatedDurationSeconds == estimatedDurationSeconds)
			{
				return;
			}
			this._estimatedDurationSeconds = estimatedDurationSeconds;

			// Update the glass pane
			final MetasfreshGlassPane glassPane = this.glassPane;
			if (glassPane != null && glassPane.isVisible())
			{
				glassPane.setBusyTimer(estimatedDurationSeconds);
			}
		}

		private int getEstimatedDurationSeconds()
		{
			return _estimatedDurationSeconds;
		}

		@Override
		public void setWaitingMessage(final String waitingMessage)
		{
			if (Check.equals(this._waitingMessage, waitingMessage))
			{
				return;
			}
			this._waitingMessage = waitingMessage;

			// Update the glass pane
			final MetasfreshGlassPane glassPane = this.glassPane;
			if (glassPane != null && glassPane.isVisible() && waitingMessage != null)
			{
				glassPane.setMessagePlain(waitingMessage);
			}
		}

		private String getWaitingMessage()
		{
			return _waitingMessage;
		}

		public final void prepareAndExecute()
		{
			if (SwingUtilities.isEventDispatchThread())
			{
				prepareAndExecuteInUI();
				return;
			}

			SwingUtilities.invokeLater(() -> prepareAndExecuteInUI());
		}

		private final void prepareAndExecuteInUI()
		{
			logger.debug("Preparing and executing in UI thread: {}", this);

			try
			{
				// Prepare in UI
				doPrepareInUI();
				runnable.prepareInUI(this);

				// Start processing
				execute(); // NOTE: this will start a new thread
			}
			catch (final Exception ex)
			{
				handleExceptionInUI(ex);
				return;
			}
		}

		/** Before {@link #runnable} was executed. This method runs in EDT. */
		private void doPrepareInUI()
		{
			//
			// Get the root pane container and window.
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
			// Set mouse cursor
			cursorOld = window.getCursor();
			window.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			//
			// Show the glass pane
			glassPane = MetasfreshGlassPane.getOrNull(rootPaneContainer);
			if (glassPane == null)
			{
				final String errmsg = "We were asked to show a glasspane but the root pane does not have a glass pane set."
						+ "Ignore, but please check because this could be a development error."
						+ "\n Root container: " + rootPaneContainer
						+ "\n Runnable: " + runnable;
				new AdempiereException(errmsg).throwIfDeveloperModeOrLogWarningElse(logger);
				return;
			}
			glassPaneVisibleOld = glassPane.isVisible();
			glassPaneMessageOld = glassPane.getMessagePlain();
			glassPane.setBusyTimer(getEstimatedDurationSeconds());
			final String waitingMessage = getWaitingMessage();
			if (waitingMessage != null)
			{
				glassPane.setMessagePlain(waitingMessage);
			}
			glassPane.setVisible(true);
		}

		/** After {@link #runnable} was executed. This method runs in EDT. */
		private void doFinallyInUI()
		{
			logger.debug("Final UI update: {}", this);

			//
			// Hide the glass pane and restore it's state
			if (glassPane != null)
			{
				glassPane.setBusyTimer(0);
				glassPane.setVisible(glassPaneVisibleOld);
				glassPane.setMessagePlain(glassPaneMessageOld);
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
			glassPaneVisibleOld = false;
			glassPaneMessageOld = null;
		}

		@Override
		protected ResultType doInBackground() throws Exception
		{
			logger.debug("Processing in background thread: {}", this);

			final ResultType result = runnable.runInBackground(this);
			return result;
		}

		@Override
		protected void process(final List<PartialResultType> partialResults)
		{
			logger.debug("Processing partial results in UI thread: {} ({})", partialResults, this);
			if (partialResults == null || partialResults.isEmpty())
			{
				return;
			}
			for (final PartialResultType partialResult : partialResults)
			{
				runnable.partialUpdateUI(this, partialResult);
			}
		}

		@Override
		protected void done()
		{
			logger.debug("Done processing: {}", this);

			try
			{
				final ResultType result = get(); // => throw exceptions if any
				runnable.finallyUpdateUI(this, result);
			}
			catch (final InterruptedException ex)
			{
				handleExceptionInUI(ex);
			}
			catch (final ExecutionException ex)
			{
				final Throwable cause = Util.coalesce(ex.getCause(), ex);
				handleExceptionInUI(cause);
			}
			finally
			{
				doFinallyInUI();
			}
		}

		private void handleExceptionInUI(final Throwable ex)
		{
			runnable.handleExceptionInUI(this, ex);
		}

		@Override
		public void publishPartialResults(final List<PartialResultType> partialResults)
		{
			logger.debug("Publishing partial results to UI thread: {} ({})", partialResults, this);

			if (partialResults == null || partialResults.isEmpty())
			{
				return;
			}

			for (final PartialResultType partialResult : partialResults)
			{
				publish(partialResult);
			}
		}

		@Override
		public void publishPartialResult(final PartialResultType partialResult)
		{
			logger.debug("Publishing partial result to UI thread: {} ({})", partialResult, this);

			publish(partialResult);
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

	}
}
