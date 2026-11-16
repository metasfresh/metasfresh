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

package de.metas.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins what lands in {@code AD_Issue.IssueSummary} for a parameterized log statement.
 */
class MetasfreshIssueAppenderTest
{
	private Logger logger;
	private ListAppender<ILoggingEvent> logAppender;

	@BeforeEach
	void init()
	{
		logger = (Logger)LoggerFactory.getLogger(MetasfreshIssueAppenderTest.class);
		logAppender = new ListAppender<>();
		logAppender.start();
		logger.addAppender(logAppender);
	}

	@AfterEach
	void tearDown()
	{
		logger.detachAppender(logAppender);
		logAppender.stop();
	}

	/** Captures the event logback itself builds, so the test never hand-encodes the event's argument contract. */
	private ILoggingEvent errorEvent(final String message, final Object... arguments)
	{
		logAppender.list.clear();
		logger.error(message, arguments);
		return logAppender.list.get(0);
	}

	@Nested
	class ToIssueCreateRequest
	{
		/**
		 * The summary has to be the rendered message. Storing the raw SLF4J template instead keeps the literal
		 * <code>{}</code> placeholders and drops every argument — which is the whole diagnostic content of the row.
		 */
		@Test
		void rendersTheMessageArguments()
		{
			final ILoggingEvent event = errorEvent("Nodes w/o parent - adding to root - {}", "[node-1, node-2]");

			assertThat(MetasfreshIssueAppender.toIssueCreateRequest(event).getSummary())
					.isEqualTo("Nodes w/o parent - adding to root - [node-1, node-2]");
		}

		@Test
		void rendersSeveralArguments()
		{
			final ILoggingEvent event = errorEvent("No LookupInfo for {} on table {}", "C_BPartner_ID", "C_Order");

			assertThat(MetasfreshIssueAppender.toIssueCreateRequest(event).getSummary())
					.isEqualTo("No LookupInfo for C_BPartner_ID on table C_Order");
		}

		@Test
		void keepsAMessageWithoutArgumentsUnchanged()
		{
			final ILoggingEvent event = errorEvent("No Provider Selected");

			assertThat(MetasfreshIssueAppender.toIssueCreateRequest(event).getSummary())
					.isEqualTo("No Provider Selected");
		}

		@Test
		void carriesTheThrowable()
		{
			final RuntimeException cause = new RuntimeException("boom");
			final ILoggingEvent event = errorEvent("Failed for {}", "C_Order", cause);

			assertThat(MetasfreshIssueAppender.toIssueCreateRequest(event).getThrowable()).isSameAs(cause);
		}

		@Test
		void carriesTheLoggerName()
		{
			final ILoggingEvent event = errorEvent("whatever");

			assertThat(MetasfreshIssueAppender.toIssueCreateRequest(event).getLoggerName())
					.isEqualTo(MetasfreshIssueAppenderTest.class.getName());
		}
	}
}
