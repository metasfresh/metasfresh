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
import de.metas.i18n.TranslatableStrings;
import de.metas.util.web.exception.MissingResourceException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OLCandBulkLineErrorCollectorTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	private static OLCandLineRef refOf(final Integer line)
	{
		return OLCandLineRef.of(line, "ext-" + line, "hdr-1");
	}

	private static OLCandProductNotFoundException productNotFound(final String msg)
	{
		return new OLCandProductNotFoundException(TranslatableStrings.constant(msg));
	}

	private static Function<Integer, String> failingFor(final RuntimeException error, final Integer... failingLines)
	{
		final List<Integer> failing = Arrays.asList(failingLines);
		return line -> {
			if (failing.contains(line))
			{
				throw error;
			}
			return "r" + line;
		};
	}

	@Nested
	public class mapAll
	{
		@Test
		void all_lines_ok_returns_results()
		{
			final ImmutableList<String> result = OLCandBulkLineErrorCollector.mapAll(
					ImmutableList.of(1, 2, 3),
					line -> "r" + line,
					OLCandBulkLineErrorCollectorTest::refOf);

			assertThat(result).containsExactly("r1", "r2", "r3");
		}

		@Test
		void two_product_errors_collected_in_order()
		{
			final Function<Integer, String> mapper = line -> {
				if (line == 1)
				{
					throw productNotFound("no product for gtin-A");
				}
				if (line == 3)
				{
					throw productNotFound("no product for gtin-B");
				}
				return "r" + line;
			};

			assertThatThrownBy(() -> OLCandBulkLineErrorCollector.mapAll(ImmutableList.of(1, 2, 3), mapper, OLCandBulkLineErrorCollectorTest::refOf))
					.isInstanceOfSatisfying(OLCandBulkCreateException.class, e -> {
						assertThat(e.getErrors()).hasSize(2);
						assertThat(e.getErrors().get(0).getMessage())
								.isEqualTo("Line 1 (externalLineId=ext-1, externalHeaderId=hdr-1): no product for gtin-A");
						assertThat(e.getErrors().get(1).getMessage())
								.isEqualTo("Line 3 (externalLineId=ext-3, externalHeaderId=hdr-1): no product for gtin-B");
						assertThat(e.getMessage()).isEqualTo("2 order-candidate line(s) could not be created: "
								+ "Line 1 (externalLineId=ext-1, externalHeaderId=hdr-1): no product for gtin-A | "
								+ "Line 3 (externalLineId=ext-3, externalHeaderId=hdr-1): no product for gtin-B");
						assertThat(e.getCause()).isNull();
						assertThat(e.getErrors().get(0).getCause()).isNull();
						assertThat(e.getErrors().get(0)).isInstanceOfSatisfying(AdempiereException.class, ae -> {
							assertThat(ae.getParameters()).containsEntry("line", 1)
									.containsEntry("externalLineId", "ext-1")
									.containsEntry("externalHeaderId", "hdr-1");
						});
					});
		}

		@Test
		void product_error_then_other_error_carries_both()
		{
			final IllegalStateException stopper = new IllegalStateException("boom");
			final Function<Integer, String> mapper = line -> {
				if (line == 1)
				{
					throw productNotFound("no product for gtin-A");
				}
				if (line == 2)
				{
					throw stopper;
				}
				return "r" + line;
			};

			assertThatThrownBy(() -> OLCandBulkLineErrorCollector.mapAll(ImmutableList.of(1, 2, 3), mapper, OLCandBulkLineErrorCollectorTest::refOf))
					.isInstanceOfSatisfying(OLCandBulkCreateException.class, e -> {
						assertThat(e.getErrors()).hasSize(2);
						assertThat(e.getErrors().get(0).getMessage()).contains("no product for gtin-A");
						assertThat(e.getErrors().get(1)).isSameAs(stopper);
						assertThat(e.getSuppressed()).containsExactly(stopper);
						assertThat(e.getCause()).isNull();
					});
		}

		@Test
		void single_other_error_rethrown_unchanged()
		{
			final IllegalStateException other = new IllegalStateException("boom");

			assertThatThrownBy(() -> OLCandBulkLineErrorCollector.mapAll(
					ImmutableList.of(1, 2),
					failingFor(other, 1),
					OLCandBulkLineErrorCollectorTest::refOf))
					.isSameAs(other);
		}

		@Test
		void missing_line_fields_shown_as_absent()
		{
			final Function<Integer, String> mapper = line -> {
				throw productNotFound("no product");
			};

			assertThatThrownBy(() -> OLCandBulkLineErrorCollector.mapAll(
					ImmutableList.of(1),
					mapper,
					line -> OLCandLineRef.of(null, null, "hdr-1")))
					.isInstanceOfSatisfying(OLCandBulkCreateException.class, e -> {
						assertThat(e.getErrors().get(0).getMessage())
								.isEqualTo("Line - (externalLineId=-, externalHeaderId=hdr-1): no product");
						assertThat(e.getErrors().get(0)).isInstanceOfSatisfying(AdempiereException.class, ae -> {
							assertThat(ae.getParameters()).containsOnlyKeys("externalHeaderId");
						});
					});
		}

		@Test
		void empty_input_returns_empty()
		{
			final ImmutableList<String> result = OLCandBulkLineErrorCollector.mapAll(
					ImmutableList.<Integer>of(),
					line -> "r" + line,
					OLCandBulkLineErrorCollectorTest::refOf);

			assertThat(result).isEmpty();
		}

		@Test
		void other_error_without_message_shows_class_name_in_aggregate_text()
		{
			final Function<Integer, String> mapper = line -> {
				if (line == 1)
				{
					throw productNotFound("no product");
				}
				throw new IllegalStateException();
			};

			assertThatThrownBy(() -> OLCandBulkLineErrorCollector.mapAll(ImmutableList.of(1, 2), mapper, OLCandBulkLineErrorCollectorTest::refOf))
					.isInstanceOfSatisfying(OLCandBulkCreateException.class, e -> assertThat(e.getMessage())
							.isEqualTo("2 order-candidate line(s) could not be created: "
									+ "Line 1 (externalLineId=ext-1, externalHeaderId=hdr-1): no product | IllegalStateException"));
		}

		@Test
		void missing_resource_exception_is_not_collected()
		{
			final MissingResourceException partnerMissing = MissingResourceException.builder()
					.resourceName("partner")
					.resourceIdentifier("ext-partner")
					.build();

			assertThatThrownBy(() -> OLCandBulkLineErrorCollector.mapAll(
					ImmutableList.of(1, 2),
					failingFor(partnerMissing, 1),
					OLCandBulkLineErrorCollectorTest::refOf))
					.isSameAs(partnerMissing);
		}
	}
}
