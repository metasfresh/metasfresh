/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.utils;

import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.error.AdIssueId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.IssueReportableExceptions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JsonErrorsTest
{
	@Nested
	class ofThrowable
	{
		@Test
		void AdempiereException_no_cause()
		{
			final AdempiereException exception = new AdempiereException("exception message");
			IssueReportableExceptions.markReportedIfPossible(exception, AdIssueId.ofRepoId(123));
			assertThat(exception.getAdIssueId()).isEqualTo(AdIssueId.ofRepoId(123));

			final JsonErrorItem errorItem = JsonErrors.ofThrowable(exception, "en_US");
			assertThat(errorItem.getMessage()).isEqualTo("exception message");
			assertThat(errorItem.isUserFriendlyError()).isFalse();
			assertThat(errorItem.getAdIssueId().getValue()).isEqualTo(123);
			assertThat(errorItem.getThrowable()).isSameAs(exception);
		}

		@Test
		void AdempiereException_withCause_IllegalStateException()
		{
			final IllegalArgumentException cause = new IllegalArgumentException("cause message");
			final AdempiereException exception = new AdempiereException("exception message", cause);
			IssueReportableExceptions.markReportedIfPossible(exception, AdIssueId.ofRepoId(123));
			assertThat(exception.getAdIssueId()).isEqualTo(AdIssueId.ofRepoId(123));

			final JsonErrorItem errorItem = JsonErrors.ofThrowable(exception, "en_US");
			assertThat(errorItem.getMessage()).isEqualTo("cause message"); // NOT sure if this is OK, but it's what we have now
			assertThat(errorItem.isUserFriendlyError()).isFalse();
			assertThat(errorItem.getAdIssueId().getValue()).isEqualTo(123);
			assertThat(errorItem.getThrowable()).isSameAs(exception);
		}
	}
}