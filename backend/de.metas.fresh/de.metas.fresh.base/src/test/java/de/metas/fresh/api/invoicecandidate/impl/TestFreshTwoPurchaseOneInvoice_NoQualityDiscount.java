package de.metas.fresh.api.invoicecandidate.impl;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.currency.CurrencyRepository;
import de.metas.greeting.GreetingRepository;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.money.MoneyService;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;

public class TestFreshTwoPurchaseOneInvoice_NoQualityDiscount extends AbstractFreshTwoInOutsOneInvoice_NoQualityDiscountTests
{
	@Override
	public void init()
	{
		super.init();

		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new GreetingRepository());
	}

	@Override
	protected BigDecimal config_GetPriceEntered_Override()
	{
		return null;
	}
}
