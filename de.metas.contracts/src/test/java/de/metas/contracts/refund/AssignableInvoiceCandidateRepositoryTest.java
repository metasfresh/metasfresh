package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

public class AssignableInvoiceCandidateRepositoryTest
{
	private static final BigDecimal TWENTY = new BigDecimal("20");

	private static final LocalDate NOW = LocalDate.now();

	private final static BigDecimal FIVE = new BigDecimal("5");

	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;
	private RefundTestTools refundTestTools;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		assignableInvoiceCandidateRepository = new AssignableInvoiceCandidateRepository(new AssignableInvoiceCandidateFactory());

		refundTestTools = new RefundTestTools();
	}

	@Test
	public void getById_assignableInvoiceCandidate()
	{
		final I_C_Invoice_Candidate assignableCandidateRecord = createAssignableCandidateRecord(refundTestTools);

		// invoke the method under test
		final AssignableInvoiceCandidate assignableCandidate = assignableInvoiceCandidateRepository.getById(InvoiceCandidateId.ofRepoId(assignableCandidateRecord.getC_Invoice_Candidate_ID()));

		assertThat(assignableCandidate.getQuantity().getAsBigDecimal()).isEqualByComparingTo("15");
		assertThat(assignableCandidate.getQuantity().getUOMId()).isEqualTo(assignableCandidateRecord.getM_Product().getC_UOM_ID());
		assertThat(assignableCandidate.getBpartnerId().getRepoId()).isEqualTo(20);
		assertThat(assignableCandidate.getMoney().getValue()).isEqualTo(TWENTY);
		assertThat(assignableCandidate.getMoney().getCurrencyId()).isEqualTo(refundTestTools.getCurrency().getId());
		assertThat(assignableCandidate.getInvoiceableFrom()).isEqualTo(NOW);
	}

	public static I_C_Invoice_Candidate createAssignableCandidateRecord(
			@NonNull final RefundTestTools refundTestTools)
	{
		final I_C_Invoice_Candidate assignableCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		assignableCandidateRecord.setIsSOTrx(true);
		assignableCandidateRecord.setBill_BPartner_ID(20);
		assignableCandidateRecord.setDateToInvoice(TimeUtil.asTimestamp(NOW));
		assignableCandidateRecord.setNetAmtToInvoice(TWENTY);
		assignableCandidateRecord.setC_Currency(refundTestTools.getCurrencyRecord());
		assignableCandidateRecord.setM_Product(refundTestTools.getProductRecord());
		assignableCandidateRecord.setQtyInvoiced(TEN);
		assignableCandidateRecord.setQtyToInvoice(FIVE);
		saveRecord(assignableCandidateRecord);
		return assignableCandidateRecord;
	}

}
