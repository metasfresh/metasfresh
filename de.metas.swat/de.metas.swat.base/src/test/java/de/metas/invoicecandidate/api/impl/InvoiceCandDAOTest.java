package de.metas.invoicecandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refreshAll;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
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

public class InvoiceCandDAOTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void updateMissingPaymentTermIds()
	{
		// create two ICs without a payment term; those shall be updated by the method under test
		final I_C_Invoice_Candidate invoiceCandidateWithoutPaymentTerm1 = newInstance(I_C_Invoice_Candidate.class);
		save(invoiceCandidateWithoutPaymentTerm1);

		final I_C_Invoice_Candidate invoiceCandidateWithoutPaymentTerm2 = newInstance(I_C_Invoice_Candidate.class);
		save(invoiceCandidateWithoutPaymentTerm2);

		// Create two ICs with different payment terms.
		// By virtue of it's lower C_Invoice_Candidate_ID, the first IC's payment term ID shall be set in the first two ICs we creates
		final I_C_PaymentTerm paymentTerm1 = newInstance(I_C_PaymentTerm.class);
		save(paymentTerm1);
		final I_C_Invoice_Candidate invoiceCandidateWithPaymentTerm1 = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateWithPaymentTerm1.setC_PaymentTerm(paymentTerm1);
		save(invoiceCandidateWithPaymentTerm1);

		final I_C_PaymentTerm paymentTerm2 = newInstance(I_C_PaymentTerm.class);
		save(paymentTerm2);
		final I_C_Invoice_Candidate invoiceCandidateWithPaymentTerm2 = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateWithPaymentTerm2.setC_PaymentTerm(paymentTerm2);
		save(invoiceCandidateWithPaymentTerm2);

		final int selectionId = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class)
				.create()
				.createSelection();

		// create an unrelated IC that also has no payment term; it shall be left untouched
		final I_C_Invoice_Candidate unrelatedInvoiceCandidateWithoutPaymentTerm = newInstance(I_C_Invoice_Candidate.class);
		save(unrelatedInvoiceCandidateWithoutPaymentTerm);

		// invoke the method under test and refresh all ICs
		new InvoiceCandDAO().updateMissingPaymentTermIds(selectionId);

		refreshAll(ImmutableList.of(
				invoiceCandidateWithoutPaymentTerm1,
				invoiceCandidateWithoutPaymentTerm2,
				invoiceCandidateWithPaymentTerm1,
				invoiceCandidateWithPaymentTerm2,
				unrelatedInvoiceCandidateWithoutPaymentTerm));

		// verify
		assertThat(getPaymentTermId(invoiceCandidateWithoutPaymentTerm1)).isEqualTo(paymentTerm1.getC_PaymentTerm_ID());
		assertThat(getPaymentTermId(invoiceCandidateWithoutPaymentTerm2)).isEqualTo(paymentTerm1.getC_PaymentTerm_ID());
		assertThat(getPaymentTermId(invoiceCandidateWithPaymentTerm1)).isEqualTo(paymentTerm1.getC_PaymentTerm_ID());

		assertThat(getPaymentTermId(invoiceCandidateWithPaymentTerm2))
				.as("invoiceCandidateWithPaymentTerm2 shall be left unchanged because it already has a C_PaymentTerm_ID")
				.isEqualTo(paymentTerm2.getC_PaymentTerm_ID());

		assertThat(getPaymentTermId(unrelatedInvoiceCandidateWithoutPaymentTerm))
				.as("unrelatedInvoiceCandidateWithoutPaymentTerm shall be left unchanged because it's not part of the selection")
				.isLessThanOrEqualTo(0);
	}

	private int getPaymentTermId(@NonNull final I_C_Invoice_Candidate ic)
	{
		final Integer paymentTermIdOrNull = getValueOverrideOrValue(ic, I_C_Invoice_Candidate.COLUMNNAME_C_PaymentTerm_ID);
		return paymentTermIdOrNull == null ? 0 : paymentTermIdOrNull;
	}
}
