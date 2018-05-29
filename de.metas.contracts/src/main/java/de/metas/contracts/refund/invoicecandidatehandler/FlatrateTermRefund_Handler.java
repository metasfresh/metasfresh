package de.metas.contracts.refund.invoicecandidatehandler;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.function.Consumer;

import org.compiere.Adempiere;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
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

public class FlatrateTermRefund_Handler
		implements ConditionTypeSpecificInvoiceCandidateHandler
{
	@Override
	public String getConditionsType()
	{
		return X_C_Flatrate_Term.TYPE_CONDITIONS_Refund;
	}

	/**
	 * @return an empty iterator; invoice candidates that need to be there are created from {@link InvoiceCandidateAssignmentService}.
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(final int limit)
	{
		return ImmutableList
				.<I_C_Flatrate_Term> of()
				.iterator();
	}

	/**
	 * Does nothing
	 */
	@Override
	public void setSpecificInvoiceCandidateValues(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final I_C_Flatrate_Term term)
	{
		// nothing to do
	}

	/**
	 * @return always {@link BigDecimal#ONE}
	 */
	@Override
	public BigDecimal calculateQtyOrdered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return ONE;
	}

	/**
	 * @return {@link PriceAndTax#NONE} because the tax remains unchanged and the price is updated in {@link InvoiceCandidateAssignmentService}.
	 */
	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return PriceAndTax.NONE; // no changes to be made
	}

	@Override
	public Consumer<I_C_Invoice_Candidate> getSetInvoiceScheduleImplementation(
			Consumer<I_C_Invoice_Candidate> IGNORED_defaultImplementation)
	{
		return ic -> {
			final RefundConfigRepository refundConfigRepository = Adempiere.getBean(RefundConfigRepository.class);

			final RefundConfig refundConfig = refundConfigRepository.getByRefundContractId(FlatrateTermId.ofRepoId(ic.getRecord_ID()));
			ic.setC_InvoiceSchedule_ID(refundConfig.getInvoiceSchedule().getId().getRepoId());
		};
	}

	@Override
	public Timestamp calculateDateOrdered(I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return invoiceCandidateRecord.getDateOrdered();
	}
}
