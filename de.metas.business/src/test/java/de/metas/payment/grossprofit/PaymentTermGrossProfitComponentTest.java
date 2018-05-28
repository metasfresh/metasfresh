package de.metas.payment.grossprofit;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.Before;
import org.junit.Test;

import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.api.PaymentTermId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PaymentTermGrossProfitComponentTest
{
	private Currency currency;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		currency = Currency
				.builder()
				.id(CurrencyId.ofRepoId(10))
				.precision(2)
				.build();
	}

	@Test
	public void applyToInput()
	{
		final I_C_PaymentTerm paymentTermRecord = newInstance(I_C_PaymentTerm.class);
		paymentTermRecord.setDiscount(new BigDecimal("3"));
		save(paymentTermRecord);

		final PaymentTermGrossProfitComponent component = new PaymentTermGrossProfitComponent(PaymentTermId.ofRepoId(paymentTermRecord.getC_PaymentTerm_ID()));

		// invoke the method under test
		final Money result = component.applyToInput(Money.of(ONE, currency));
		assertThat(result.getValue()).isEqualByComparingTo("0.97");
	}

	@Test
	public void applyToInput_no_paymentterm()
	{
		final PaymentTermGrossProfitComponent component = new PaymentTermGrossProfitComponent(null);

		// invoke the method under test
		final Money result = component.applyToInput(Money.of(ONE, currency));
		assertThat(result.getValue()).isEqualByComparingTo("1");
	}

}
