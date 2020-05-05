package de.metas.fresh.api.invoicecandidate.impl;

import org.junit.Test;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

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


import mockit.Expectations;
import mockit.Mocked;

public class FreshInvoiceCandBLTest
{

	@Mocked
	I_C_Invoice_Candidate candidate;

	/**
	 * If isSOTrx=Y, then the method shall return and *not* call the candidte's setC_DocTypeInvoice() or setC_DocTypeInvoice_ID() method.
	 */
	@Test
	public void test_updateC_DocTypeInvoice_does_nothing_if_IsSOTrx_true()
	{
		// @formatter:off
		new Expectations()
		{{
			candidate.isSOTrx(); result = true; times = 1;
			candidate.setC_DocTypeInvoice_ID(anyInt); times = 0;
		}};
		// @formatter:on
		new FreshInvoiceCandBL().updateC_DocTypeInvoice(candidate);
	}

}
