package de.metas.invoicecandidate.api.impl.aggregationEngine;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class TestTwoShipmentsOneInvoicePriceOverride extends AbstractTwoInOutsOneInvoiceSalesTests
{
	/**
	 * Makes sure that our <code>priceEntered_Override</code> is actually different from the "normal" price, to make sure that the tests actually matter.
	 */
	@Override
	protected void step_validate_before_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> inOutLines)
	{
		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			assertThat(invoiceCandidate.getPriceEntered(), not(comparesEqualTo(config_GetPriceEntered_Override())));
		}

		super.step_validate_before_aggregation(invoiceCandidates, inOutLines);
	}

	@Override
	protected boolean config_IsSOTrx()
	{
		return true;
	}

	@Override
	protected BigDecimal config_GetPriceEntered_Override()
	{
		return TEN;
	}

}
