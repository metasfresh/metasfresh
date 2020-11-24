package de.metas.fresh.api.invoicecandidate.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class FreshInvoiceCandBLTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * If isSOTrx=Y, then the method shall return and *not* call the candidte's setC_DocTypeInvoice() or setC_DocTypeInvoice_ID() method.
	 */
	@Test
	public void test_updateC_DocTypeInvoice_does_nothing_if_IsSOTrx_true()
	{
		final I_C_Invoice_Candidate candidate = newInstance(I_C_Invoice_Candidate.class);
		candidate.setIsSOTrx(true);

		new FreshInvoiceCandBL().updateC_DocTypeInvoice(candidate);

		assertThat(candidate.getC_DocTypeInvoice_ID()).isLessThanOrEqualTo(0);
	}

}
