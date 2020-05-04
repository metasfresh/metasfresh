package de.metas.adempiere.form;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Helper class to easily process data in a background thread while updating the UI in UI thread.
 *
 * To create a new instance please use {@link IClientUI#invokeAsync()}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IClientUIAsyncInvoker
{
	/**
	 * Actually invoke the asynchronous runnable
	 *
	 * @param asyncRunnable
	 */
	public <InitialValueType, ResultType, PartialResultType> void invoke(IClientUIAsyncRunnable<InitialValueType, ResultType, PartialResultType> asyncRunnable);

	/**
	 * Sets the parent UI component. This will be used to find the UI window on which we need to set the waiting mouse cursor, display a glass pane etc.
	 *
	 * @param parentComponentObj
	 */
	IClientUIAsyncInvoker setParentComponent(Object parentComponentObj);

	/**
	 * Optionally set an initial value which triggered this invocation.
	 *
	 * @param initialValue
	 */
	<InitialValueType> IClientUIAsyncInvoker setInitialValue(InitialValueType initialValue);

	/**
	 * The UI asynchronous executor.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 * @param <InitialValueType> an initial value which triggered current asynchronous processing (optional)
	 * @param <ResultType> final result type
	 * @param <PartialResultType> partial result type
	 */
	public static interface IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType>
	{
		/**
		 * @return initial value which triggered this execution; might be null
		 */
		InitialValueType getInitialValue();

		/** Sets how much time this execution will take (estimated). */
		void setEstimatedDurationSeconds(int estimatedDurationSeconds);

		/** Sets which shall be the message displayed to user while waiting */
		void setWaitingMessage(String waitingMessage);

		/**
		 * Ask the executor to publish some partial results to UI thread.
		 *
		 * The {@link IClientUIAsyncRunnable#partialUpdateUI(IClientUIAsyncExecutor, Object)} method will be called in the UI thread to process them.
		 *
		 * @param partialResults
		 */
		void publishPartialResults(final List<PartialResultType> partialResults);

		/**
		 * Convenient method to publish only one partial result to UI thread.
		 *
		 * @see #publishPartialResults(List)
		 */
		void publishPartialResult(PartialResultType partialResult);
	}

	/**
	 * UI asynchronous runnable.
	 *
	 * NOTE to developer: instead of implementing this interface, consider extending the {@link ClientUIAsyncRunnableAdapter}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 * @param <InitialValueType> an initial value which triggered current asynchronous processing (optional)
	 * @param <ResultType> final result type
	 * @param <PartialResultType> partial result type
	 */
	public static interface IClientUIAsyncRunnable<InitialValueType, ResultType, PartialResultType>
	{
		/**
		 * First method which will be called (in UI thread), before we actually start the background processing ({@link #runInBackground(IClientUIAsyncExecutor)}).
		 *
		 * Here you can prepare the UI.
		 *
		 * @param executor
		 */
		void prepareInUI(IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor);

		/**
		 * Actual processing (executed in a background thread).
		 *
		 * <p>
		 * <b>WARNING: when implementing this method, please make sure you are not calling any UI specific updating (e.g. your are not access Swing components).
		 * <br>
		 * All UI specific things shall happen in UI methods (those postfixed with InUI)
		 * <br>
		 * If you want to ask the UI thread to do some updates to UI, please publish some partial results by calling one of the {@link IClientUIAsyncExecutor#publishPartialResult(Object)} methods.</b>
		 * </p>
		 *
		 * @param executor
		 * @throws Exception
		 */
		ResultType runInBackground(IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor) throws Exception;

		/**
		 * Method called (in UI thread) to update the UI when some partial results were published from the background thread.
		 *
		 * @param executor
		 * @param partialResult
		 */
		void partialUpdateUI(IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor, PartialResultType partialResult);

		/**
		 * Method called (in UI thread) after all processing was done.
		 *
		 * Implement this method to update your UI with the final result.
		 *
		 * @param executor
		 * @param result final result
		 */
		void finallyUpdateUI(IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor, ResultType result);

		/**
		 * Method called (in UI thread) to handle any exceptions which occured while executing this runnable.
		 *
		 * @param executor
		 * @param ex exception that was thrown.
		 */
		void handleExceptionInUI(IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor, final Throwable ex);
	}

	/**
	 * Easy to use {@link IClientUIAsyncRunnable} adapter.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 * @param <InitialValueType>
	 * @param <ResultType>
	 * @param <PartialResultType>
	 */
	public static abstract class ClientUIAsyncRunnableAdapter<InitialValueType, ResultType, PartialResultType>
			implements IClientUIAsyncRunnable<InitialValueType, ResultType, PartialResultType>
	{
		private static final transient Logger logger = LogManager.getLogger(IClientUIAsyncInvoker.ClientUIAsyncRunnableAdapter.class);

		@Override
		public void prepareInUI(final IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor)
		{
			// nothing at this level
		}

		@Override
		public abstract ResultType runInBackground(IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor);

		@Override
		public void partialUpdateUI(final IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor, final PartialResultType partialResult)
		{
			logger.warn("Got a partial result which was not handled. Usually this happens when developer publishes partial results but it does not implement the partialUpdateUI method."
					+ "\n Partial result: " + partialResult
					+ "\n Runnable(this): " + this
					+ "\n Executor: " + executor);
		}

		@Override
		public void finallyUpdateUI(final IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor, final ResultType result)
		{
			// nothing at this level
		}

		@Override
		public void handleExceptionInUI(final IClientUIAsyncExecutor<InitialValueType, ResultType, PartialResultType> executor, final Throwable ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}
}
