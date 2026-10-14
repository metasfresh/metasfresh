package de.metas.error;

import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

class LoggableWithThrowableUtilTest
{
	private CountingErrorManager errorManager;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		errorManager = new CountingErrorManager();
		Services.registerService(IErrorManager.class, errorManager);
	}

	@Test
	void createsAnAdIssue_whenNotSuppressed()
	{
		LoggableWithThrowableUtil.extractMsgAndAdIssue("boom", new RuntimeException("one"));

		assertThat(errorManager.createIssueCount).isEqualTo(1);
	}

	/**
	 * Persisting an AD_Issue is itself a DB write; when it fails, the transaction manager logs that failure through
	 * the ambient loggable, which lands back here. Without the suppression region every failed attempt would create
	 * another AD_Issue whose save fails the same way — unbounded recursion that ends in an OutOfMemoryError, because
	 * each level embeds the stacktrace of the level below it.
	 */
	@Test
	void doesNotCreateAnAdIssue_whileSuppressed()
	{
		try (final IAutoCloseable ignored = LoggableWithThrowableUtil.suppressAdIssueCreation())
		{
			LoggableWithThrowableUtil.extractMsgAndAdIssue("boom", new RuntimeException("nested"));
		}

		assertThat(errorManager.createIssueCount)
				.as("a throwable logged while an AD_Issue is being created must not create another AD_Issue")
				.isZero();
	}

	@Test
	void restoresThePreviousState_whenTheRegionIsClosed()
	{
		assertThat(LoggableWithThrowableUtil.isAdIssueCreationSuppressed()).isFalse();

		try (final IAutoCloseable ignored = LoggableWithThrowableUtil.suppressAdIssueCreation())
		{
			assertThat(LoggableWithThrowableUtil.isAdIssueCreationSuppressed()).isTrue();

			// nested region: closing the inner one must NOT re-enable issue creation
			try (final IAutoCloseable ignoredInner = LoggableWithThrowableUtil.suppressAdIssueCreation())
			{
				assertThat(LoggableWithThrowableUtil.isAdIssueCreationSuppressed()).isTrue();
			}
			assertThat(LoggableWithThrowableUtil.isAdIssueCreationSuppressed()).isTrue();
		}

		assertThat(LoggableWithThrowableUtil.isAdIssueCreationSuppressed()).isFalse();

		// and issue creation works again afterwards
		LoggableWithThrowableUtil.extractMsgAndAdIssue("boom", new RuntimeException("after"));
		assertThat(errorManager.createIssueCount).isEqualTo(1);
	}

	@Test
	void stillFormatsTheMessage_whileSuppressed()
	{
		final LoggableWithThrowableUtil.FormattedMsgWithAdIssueId result;
		try (final IAutoCloseable ignored = LoggableWithThrowableUtil.suppressAdIssueCreation())
		{
			result = LoggableWithThrowableUtil.extractMsgAndAdIssue("value was {}", "42", new RuntimeException("nested"));
		}

		assertThat(result.getFormattedMessage()).isEqualTo("value was 42");
		assertThat(result.getAdIsueId()).isEmpty();
	}
}
