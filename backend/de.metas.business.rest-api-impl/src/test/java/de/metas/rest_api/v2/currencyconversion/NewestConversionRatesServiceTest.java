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

package de.metas.rest_api.v2.currencyconversion;

import de.metas.currency.ConversionRateQuery;
import de.metas.currency.ConversionRateRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The newest-rates behavior (newest-per-combo reduction, org/from/to/type filters, client scoping) is now done
 * DB-side via native {@code DISTINCT ON} SQL in {@link ConversionRateRepository#getNewestRatesOrderedByValidFromDesc},
 * which the in-memory POJO query layer used by unit tests cannot run. That behavior is covered by the cucumber
 * {@code CurrencyConversion_api} {@code GET newestRates} scenario (exercised against a real DB).
 * <p>
 * What remains here is the pure-Java guard on the repository read path ({@link #getByQuery_emptyQuery_isRejected}),
 * which is POJO-runnable.
 */
@ExtendWith(AdempiereTestWatcher.class)
class NewestConversionRatesServiceTest
{
	private ConversionRateRepository conversionRateRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		conversionRateRepository = new ConversionRateRepository();
	}

	@Test
	void getByQuery_emptyQuery_isRejected()
	{
		// EMPTY is exactly the default-built query, and an empty query would match every row → reject it.
		assertThat(ConversionRateQuery.builder().build()).isEqualTo(ConversionRateQuery.EMPTY);

		assertThatThrownBy(() -> conversionRateRepository.getByQuery(ConversionRateQuery.EMPTY))
				.as("an empty query must be rejected (it would match every row)")
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("non-empty query");
	}
}
