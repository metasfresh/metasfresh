package de.metas.invoicecandidate.api.impl;

import de.metas.invoicecandidate.process.params.InvoicingParamsFactory;
import de.metas.invoicecandidate.process.params.PlainInvoicingParams;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class PlainInvoicingParamsTest
{
	private PlainInvoicingParams defaults;
	private PlainInvoicingParams params;

	@Test
	public void testOverrides()
	{
		defaults = InvoicingParamsFactory.newPlain();
		params = InvoicingParamsFactory.overriding(defaults);

		testBoolean(params::isOnlyApprovedForInvoicing, defaults::setOnlyApprovedForInvoicing);
		testBoolean(params::isConsolidateApprovedICs, defaults::setConsolidateApprovedICs);
		testBoolean(params::isIgnoreInvoiceSchedule, defaults::setIgnoreInvoiceSchedule);
		testBoolean(params::isSupplementMissingPaymentTermIds, defaults::setSupplementMissingPaymentTermIds);

		testBoolean(params::isStoreInvoicesInResult, defaults::setStoreInvoicesInResult);
		testBoolean(params::isAssumeOneInvoice, defaults::setAssumeOneInvoice);

		testValue(
				PlainInvoicingParams::getDateInvoiced,
				PlainInvoicingParams::setDateInvoiced,
				LocalDate.parse("2019-09-01"),
				LocalDate.parse("2019-09-02"));

		testValue(
				PlainInvoicingParams::getDateAcct,
				PlainInvoicingParams::setDateAcct,
				LocalDate.parse("2019-09-01"),
				LocalDate.parse("2019-09-02"));

		testValue(
				PlainInvoicingParams::getPOReference,
				PlainInvoicingParams::setPOReference,
				"poReference1",
				"poReference2");

		testValue(
				PlainInvoicingParams::getCheck_NetAmtToInvoice,
				PlainInvoicingParams::setCheck_NetAmtToInvoice,
				new BigDecimal("123.45"),
				new BigDecimal("123.46"));
	}

	private void testBoolean(
			final BooleanSupplier getter,
			final Consumer<Boolean> setter)
	{
		// Default value
		assertThat(getter.getAsBoolean()).isFalse();

		setter.accept(false);
		assertThat(getter.getAsBoolean()).isFalse();

		setter.accept(true);
		assertThat(getter.getAsBoolean()).isTrue();
	}

	private <T> void testValue(
			final Function<PlainInvoicingParams, T> getter,
			final BiConsumer<PlainInvoicingParams, T> setter,
			final T value1,
			final T value2)
	{
		// Default value
		assertThat(getter.apply(defaults)).isNull();
		assertThat(getter.apply(params)).isNull();

		setter.accept(defaults, value1);
		assertThat(getter.apply(defaults)).isSameAs(value1);
		assertThat(getter.apply(params)).isSameAs(value1);

		setter.accept(params, value2);
		assertThat(getter.apply(defaults)).isSameAs(value1);
		assertThat(getter.apply(params)).isSameAs(value2);
	}

}
