package de.metas.error;

import de.metas.error.impl.ErrorManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Pins the wiring between {@link ErrorManager} and {@link LoggableWithThrowableUtil}: a failure while an AD_Issue is
 * being persisted must not produce a second AD_Issue.
 */
class ErrorManagerRecursionGuardTest
{
	private CountingErrorManagerDecorator errorManager;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		errorManager = new CountingErrorManagerDecorator();
		Services.registerService(IErrorManager.class, errorManager);
	}

	/**
	 * Reproduces the production cycle without a database:
	 * <ol>
	 * <li>{@code createIssueInTrx} fails while rendering the summary, because the throwable's message cannot be
	 * rendered ({@code AdempiereException.extractMessage} calls {@code getLocalizedMessage()} unguarded).</li>
	 * <li>{@code AbstractTrxManager.call0} catches it and logs it through the ambient {@link ILoggable}, passing the
	 * throwable as the last parameter.</li>
	 * <li>The ambient loggable here mimics {@code ApiAuditLoggable}: it routes that throwable back into AD_Issue
	 * creation.</li>
	 * </ol>
	 * Without the guard in {@link ErrorManager#createIssue(IssueCreateRequest)} that second creation proceeds — and in
	 * production, where each level embeds the stacktrace of the level below, it recurses until the heap is gone.
	 */
	@Test
	void doesNotCreateASecondAdIssue_whenPersistingTheFirstOneFails()
	{
		final RuntimeException unrenderableFailure = new RuntimeException("outer")
		{
			@Override
			public String getLocalizedMessage()
			{
				throw new IllegalStateException("this message cannot be rendered");
			}
		};

		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(new RecursingLoggable()))
		{
			assertThatThrownBy(() -> errorManager.createIssue(IssueCreateRequest.builder()
					.throwable(unrenderableFailure)
					.build()))
					.as("the original failure must still propagate to the caller")
					.isInstanceOf(Throwable.class);
		}

		assertThat(errorManager.createIssueCount)
				.as("only the outer AD_Issue attempt may reach the error manager; the nested one must be suppressed")
				.isEqualTo(1);
	}

	/** Mimics {@code ApiAuditLoggable}, which turns a trailing {@link Throwable} into an AD_Issue. */
	private static class RecursingLoggable implements ILoggable
	{
		@Override
		public ILoggable addLog(final String msg, final Object... msgParameters)
		{
			LoggableWithThrowableUtil.extractMsgAndAdIssue(msg, msgParameters);
			return this;
		}
	}

	/**
	 * Counts the calls that arrive through {@code Services.get(IErrorManager.class)} while delegating to the real
	 * implementation — a stub would not run the {@code createIssueInTrx} logic under test.
	 */
	private static class CountingErrorManagerDecorator implements IErrorManager
	{
		private final IErrorManager delegate = new ErrorManager();

		private int createIssueCount = 0;

		@Override
		public AdIssueId createIssue(final Throwable t)
		{
			createIssueCount++;
			return delegate.createIssue(t);
		}

		@Override
		public AdIssueId createIssue(final IssueCreateRequest request)
		{
			createIssueCount++;
			return delegate.createIssue(request);
		}

		@Override
		public AdIssueId insertRemoteIssue(final InsertRemoteIssueRequest request)
		{
			return delegate.insertRemoteIssue(request);
		}

		@Override
		public void markIssueAcknowledged(final AdIssueId adIssueId)
		{
			delegate.markIssueAcknowledged(adIssueId);
		}

		@Override
		public IssueCountersByCategory getIssueCountersByCategory(
				final TableRecordReference recordRef,
				final boolean onlyNotAcknowledged)
		{
			return IssueCountersByCategory.of(Collections.emptyMap());
		}
	}
}
