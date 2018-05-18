package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.Services;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceScheduleId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
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

public class RefundTestTools
{
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal NINE = new BigDecimal("9");
	private static final BigDecimal HUNDRED = new BigDecimal("100");

	public static I_C_Invoice_Candidate retrieveRecord(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID,
						invoiceCandidateId.getRepoId())
				.create()
				.firstOnly(I_C_Invoice_Candidate.class);

		return invoiceCandidateRecord;
	}

	public static RefundInvoiceCandidate createRefundCandidate()
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final ProductId productId = ProductId.ofRepoId(20);
		final DocTypeId docTypeId = DocTypeId.ofRepoId(30);
		final InvoiceScheduleId invoiceScheduleId = InvoiceScheduleId.ofRepoId(40);

		final I_C_Flatrate_RefundConfig refundConfig = newInstance(I_C_Flatrate_RefundConfig.class);
		refundConfig.setC_Flatrate_Conditions(conditions);
		refundConfig.setM_Product_ID(productId.getRepoId());
		refundConfig.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
		refundConfig.setC_InvoiceSchedule_ID(invoiceScheduleId.getRepoId());
		refundConfig.setPercent(TWENTY);
		saveRecord(refundConfig);

		final I_C_Flatrate_Term contract = newInstance(I_C_Flatrate_Term.class);
		contract.setC_Flatrate_Conditions(conditions);
		contract.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		saveRecord(contract);

		final CurrencyId currencyId = CurrencyId.ofRepoId(30);

		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setPriceActual(HUNDRED);
		invoiceCandidateRecord.setC_Currency_ID(currencyId.getRepoId());
		saveRecord(invoiceCandidateRecord);

		final Money money = Money.of(HUNDRED, Currency.builder().id(currencyId).precision(2).build());

		return RefundInvoiceCandidate.builder()
				.id(InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.bpartnerId(BPartnerId.ofRepoId(10))
				.refundConfig(RefundConfig
						.builder()
						.productId(productId)
						.percent(TWENTY)
						.conditionsId(ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID()))
						.invoiceScheduleId(invoiceScheduleId)
						.refundInvoiceType(RefundInvoiceType.CREDITMEMO)
						.build())
				.refundContractId(FlatrateTermId.ofRepoId(contract.getC_Flatrate_Term_ID()))
				.money(money)
				.invoiceableFrom(LocalDate.now().plusDays(1))
				.build();
	}

	public static AssignableInvoiceCandidate createAssignableCandidate(@Nullable final RefundInvoiceCandidate refundInvoiceCandidateOrNull)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(30);

		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setNetAmtInvoiced(ONE);
		invoiceCandidateRecord.setNetAmtToInvoice(NINE);
		invoiceCandidateRecord.setC_Currency_ID(currencyId.getRepoId());
		saveRecord(invoiceCandidateRecord);

		final Money money = Money.of(TEN, Currency.builder().id(currencyId).precision(2).build());

		return AssignableInvoiceCandidate
				.builder()
				.id(InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID()))
				.bpartnerId(BPartnerId.ofRepoId(10))
				.productId(ProductId.ofRepoId(20))
				.money(money)
				.invoiceableFrom(LocalDate.now())
				.refundInvoiceCandidate(refundInvoiceCandidateOrNull)
				.build();
	}
}
