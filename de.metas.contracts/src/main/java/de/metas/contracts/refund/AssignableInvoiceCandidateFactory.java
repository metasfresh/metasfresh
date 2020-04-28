package de.metas.contracts.refund;

import static de.metas.util.NumberUtils.stripTrailingDecimalZeros;
import static org.adempiere.model.InterfaceWrapperHelper.createOld;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Nullable;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
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

@Service
public class AssignableInvoiceCandidateFactory
{
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;
	private final CurrencyRepository currenciesRepo;

	@VisibleForTesting
	public static AssignableInvoiceCandidateFactory newForUnitTesting()
	{
		final InvoiceScheduleRepository invoiceScheduleRepository = new InvoiceScheduleRepository();
		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(invoiceScheduleRepository);
		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);
		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);
		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(refundContractRepository, assignmentAggregateService);
		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = new RefundInvoiceCandidateRepository(refundContractRepository, refundInvoiceCandidateFactory);
		final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository);

		final CurrencyRepository currenciesRepo = new CurrencyRepository();

		return new AssignableInvoiceCandidateFactory(assignmentToRefundCandidateRepository, currenciesRepo);
	}

	public AssignableInvoiceCandidateFactory(
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final CurrencyRepository currenciesRepo)
	{
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
		this.currenciesRepo = currenciesRepo;
	}

	/** Note: does not load&include {@link AssignmentToRefundCandidate}s; those need to be retrieved using {@link AssignmentToRefundCandidateRepository}. */
	public AssignableInvoiceCandidate ofRecord(@Nullable final I_C_Invoice_Candidate assignableRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(assignableRecord.getC_Invoice_Candidate_ID());

		final Timestamp invoicableFromDate = getValueOverrideOrValue(assignableRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		final BigDecimal moneyAmount = assignableRecord
				.getNetAmtInvoiced()
				.add(assignableRecord.getNetAmtToInvoice());

		final CurrencyId currencyId = CurrencyId.ofRepoId(assignableRecord.getC_Currency_ID());
		final CurrencyPrecision precision = currenciesRepo.getStdPrecision(currencyId);
		final Money money = Money.of(stripTrailingDecimalZeros(moneyAmount), currencyId);

		final Quantity quantity = extractQuantity(assignableRecord);
		final Quantity quantityOld = extractQuantity(createOld(assignableRecord, I_C_Invoice_Candidate.class));

		final List<AssignmentToRefundCandidate> assignments = assignmentToRefundCandidateRepository.getAssignmentsByAssignableCandidateId(invoiceCandidateId);

		final AssignableInvoiceCandidate invoiceCandidate = AssignableInvoiceCandidate.builder()
				.id(invoiceCandidateId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(assignableRecord.getBill_BPartner_ID(),assignableRecord.getBill_Location_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.precision(precision.toInt())
				.quantity(quantity)
				.quantityOld(quantityOld)
				.productId(ProductId.ofRepoId(assignableRecord.getM_Product_ID()))
				.assignmentsToRefundCandidates(assignments)
				.build();

		return invoiceCandidate;
	}

	private Quantity extractQuantity(@NonNull final I_C_Invoice_Candidate assignableRecord)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_Product product = productDAO.getById(assignableRecord.getM_Product_ID());
		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

		final Quantity quantity = Quantity.of(
				assignableRecord.getQtyToInvoice().add(stripTrailingDecimalZeros(assignableRecord.getQtyInvoiced())),
				uom);
		return quantity;
	}
}
