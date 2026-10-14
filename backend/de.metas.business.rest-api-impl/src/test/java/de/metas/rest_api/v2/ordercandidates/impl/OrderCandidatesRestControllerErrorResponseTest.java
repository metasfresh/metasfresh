/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.ordercandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.i18n.TranslatableStrings;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCandidatesRestControllerErrorResponseTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	private static AdempiereException lineError(final String message, final int line, final String externalLineId)
	{
		final AdempiereException error = new AdempiereException(TranslatableStrings.constant(message));
		error.setParameter("line", line);
		error.setParameter("externalLineId", externalLineId);
		return error;
	}

	@Nested
	public class toErrorResponse
	{
		@Test
		void bulk_exception_rendered_as_one_error_per_line()
		{
			final OLCandBulkCreateException bulkException = new OLCandBulkCreateException(ImmutableList.of(
					lineError("Line 20: gtin-B not resolvable", 20, "00020"),
					lineError("Line 30: gtin-C not resolvable", 30, "00030")));

			final JsonOLCandCreateBulkResponse response = OrderCandidatesRestController.toErrorResponse(bulkException, "en_US");

			assertThat(response.getResult()).isEmpty();
			assertThat(response.getErrors()).hasSize(2);
			assertThat(response.getErrors().get(0).getMessage()).isEqualTo("Line 20: gtin-B not resolvable");
			assertThat(response.getErrors().get(0).getParameters()).containsEntry("line", "20").containsEntry("externalLineId", "00020");
			assertThat(response.getErrors().get(1).getMessage()).isEqualTo("Line 30: gtin-C not resolvable");
			assertThat(response.getErrors().get(1).getParameters()).containsEntry("line", "30").containsEntry("externalLineId", "00030");
		}

		@Test
		void mixed_product_errors_and_stopping_error_rendered_one_item_each_in_order()
		{
			final IllegalStateException stopper = new IllegalStateException("boom");
			final OLCandBulkCreateException bulkException = new OLCandBulkCreateException(ImmutableList.of(
					lineError("Line 20: gtin-B not resolvable", 20, "00020"),
					lineError("Line 30: gtin-C not resolvable", 30, "00030"),
					stopper));

			final JsonOLCandCreateBulkResponse response = OrderCandidatesRestController.toErrorResponse(bulkException, "en_US");

			assertThat(response.getErrors()).extracting(error -> error.getMessage())
					.containsExactly("Line 20: gtin-B not resolvable", "Line 30: gtin-C not resolvable", "boom");
		}

		@Test
		void other_exception_rendered_as_single_error_unchanged()
		{
			final AdempiereException ex = new AdempiereException(TranslatableStrings.constant("boom"));

			final JsonOLCandCreateBulkResponse response = OrderCandidatesRestController.toErrorResponse(ex, "en_US");

			assertThat(response.getErrors()).hasSize(1);
			assertThat(response.getErrors().get(0).getMessage()).isEqualTo("boom");
		}
	}
}
