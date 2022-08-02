package de.metas.contracts.refund.invoicecandidatehandler;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.compiere.util.TimeUtil.asLocalDate;
import static org.compiere.util.TimeUtil.asTimestamp;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.function.Consumer;

import de.metas.contracts.refund.CandidateAssignmentService;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.ConditionTypeSpecificInvoiceCandidateHandler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContract.NextInvoiceDate;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
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
	 * @return an empty iterator; invoice candidates that need to be there are created from {@link CandidateAssignmentService}.
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveTermsWithMissingCandidates(final QueryLimit limit_IGNORED)
	{
		return ImmutableList
				.<I_C_Flatrate_Term> of()
				.iterator();
	}

	@NonNull
	@Override
	public CandidatesAutoCreateMode isMissingInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm)
	{
		return CandidatesAutoCreateMode.DONT;
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
	 * @return always one, in the respective term's UOM
	 */
	@Override
	public Quantity calculateQtyEntered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		final UomId uomId = HandlerTools.retrieveUomId(invoiceCandidateRecord);
		final I_C_UOM uomRecord = loadOutOfTrx(uomId, I_C_UOM.class);

		return Quantity.of(ONE, uomRecord);
	}

	/**
	 * @return {@link PriceAndTax#NONE} because the tax remains unchanged and the price is updated in {@link de.metas.contracts.CandidateAssignmentServive}.
	 */
	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return PriceAndTax.NONE; // no changes to be made
	}

	@Override
	public Consumer<I_C_Invoice_Candidate> getInvoiceScheduleSetterFunction(
			final Consumer<I_C_Invoice_Candidate> IGNORED_defaultImplementation)
	{
		return ic -> {

			final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(ic.getRecord_ID());

			final RefundContractRepository refundContractRepository = SpringContextHolder.instance.getBean(RefundContractRepository.class);
			final RefundContract refundContract = refundContractRepository.getById(flatrateTermId);
			final NextInvoiceDate nextInvoiceDate = refundContract.computeNextInvoiceDate(asLocalDate(ic.getDeliveryDate()));

			ic.setC_InvoiceSchedule_ID(nextInvoiceDate.getInvoiceSchedule().getId().getRepoId());
			ic.setDateToInvoice(asTimestamp(nextInvoiceDate.getDateToInvoice()));
		};
	}

	/** Just return the record's current date */
	@Override
	public Timestamp calculateDateOrdered(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return invoiceCandidateRecord.getDateOrdered();
	}
}
