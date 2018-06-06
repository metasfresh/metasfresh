package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.DocTypeQuery;
import de.metas.document.DocTypeQuery.DocTypeQueryBuilder;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyFactory;
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

/**
 * Supposed to be used by {@link InvoiceCandidateRepository} only;
 * For the outside world, {@link InvoiceCandidateRepository} is the only source of {@link InvoiceCandidate} instances.
 */
class InvoiceCandidateFactory
{
	private final InvoiceCandidateRepository invoiceCandidateRepository;
	private final RefundContractRepository refundContractRepository;
	private final MoneyFactory moneyFactory;

	public InvoiceCandidateFactory(
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final MoneyFactory moneyFactory)
	{
		this.refundContractRepository = refundContractRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.moneyFactory = moneyFactory;
	}

	public <T extends InvoiceCandidate> T ofRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		final Optional<T> recordOrNull = ofNullableRecord(record);

		final Supplier<? extends AdempiereException> exceptionSupplier = //
				() -> new AdempiereException(
						"Unable to create and InvoiceCandidate instance for the given I_C_Invoice_Candidate record")
								.appendParametersToMessage()
								.setParameter("record", record);

		return recordOrNull.orElseThrow(exceptionSupplier);
	}

	@SuppressWarnings("unchecked")
	public <T extends InvoiceCandidate> Optional<T> ofNullableRecord(@Nullable final I_C_Invoice_Candidate record)
	{
		if (record == null)
		{
			return Optional.empty();
		}

		final Optional<RefundInvoiceCandidate> o = ofNullableRefundRecord(record);
		if (o.isPresent())
		{
			return Optional.of((T)o.get());
		}
		return Optional.of((T)ofAssignableRecord(record));

	}

	public Optional<RefundInvoiceCandidate> ofNullableRefundRecord(@Nullable final I_C_Invoice_Candidate refundRecord)
	{
		if (refundRecord == null)
		{
			return Optional.empty();
		}

		final TableRecordReference reference = refundRecord.getAD_Table_ID() > 0
				? TableRecordReference.ofReferenced(refundRecord)
				: null;

		final Optional<RefundContract> refundContract = retrieveRefundContractOrNull(reference);
		if (!refundContract.isPresent())
		{
			return Optional.empty();
		}

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(refundRecord.getC_Invoice_Candidate_ID());

		final Timestamp invoicableFromDate = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);

		final BigDecimal priceActual = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_PriceActual);
		final Money money = moneyFactory.forAmountAndCurrencyId(
				priceActual,
				CurrencyId.ofRepoId(refundRecord.getC_Currency_ID()));

		final RefundInvoiceCandidate invoiceCandidate = RefundInvoiceCandidate
				.builder()
				.id(invoiceCandidateId)
				.refundContract(refundContract.get())
				.bpartnerId(BPartnerId.ofRepoId(refundRecord.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.build();
		return Optional.of(invoiceCandidate);
	}

	private AssignableInvoiceCandidate ofAssignableRecord(@Nullable final I_C_Invoice_Candidate assignableRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(assignableRecord.getC_Invoice_Candidate_ID());

		final Optional<AssignmentToRefundCandidate> assignmentToRefundCandidate = //
				invoiceCandidateRepository.getAssignmentToRefundCandidate(invoiceCandidateId);

		final Timestamp invoicableFromDate = getValueOverrideOrValue(assignableRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		final BigDecimal moneyAmount = assignableRecord
				.getNetAmtInvoiced()
				.add(assignableRecord.getNetAmtToInvoice());

		final CurrencyId currencyId = CurrencyId.ofRepoId(assignableRecord.getC_Currency_ID());
		final Money money = moneyFactory.forAmountAndCurrencyId(moneyAmount, currencyId);

		final AssignableInvoiceCandidate invoiceCandidate = AssignableInvoiceCandidate.builder()
				.id(invoiceCandidateId)
				.assignmentToRefundCandidate(assignmentToRefundCandidate.orElse(null))
				.bpartnerId(BPartnerId.ofRepoId(assignableRecord.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.productId(ProductId.ofRepoId(assignableRecord.getM_Product_ID()))
				.build();

		return invoiceCandidate;
	}

	private Optional<RefundContract> retrieveRefundContractOrNull(@Nullable final TableRecordReference reference)
	{
		if (reference == null)
		{
			return Optional.empty();
		}

		if (!I_C_Flatrate_Term.Table_Name.equals(reference.getTableName()))
		{
			return Optional.empty();
		}

		final I_C_Flatrate_Term term = reference.getModel(I_C_Flatrate_Term.class);
		if (!X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions()))
		{
			return Optional.empty();
		}

		final RefundContract refundContract = refundContractRepository.getById(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()));
		return Optional.of(refundContract);
	}

	public RefundInvoiceCandidate createRefundInvoiceCandidate(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate,
			@NonNull final FlatrateTermId contractId)
	{
		final I_C_Invoice_Candidate assignableInvoiceCandidateRecord = load(
				invoiceCandidate.getId().getRepoId(),
				I_C_Invoice_Candidate.class);

		final I_C_Invoice_Candidate refundInvoiceCandidateRecord = Services.get(IInvoiceCandBL.class)
				.splitCandidate(assignableInvoiceCandidateRecord);

		final I_C_ILCandHandler handlerRecord = Services.get(IInvoiceCandidateHandlerDAO.class)
				.retrieveForClassOneOnly(Env.getCtx(), FlatrateTerm_Handler.class);
		refundInvoiceCandidateRecord.setC_ILCandHandler(handlerRecord);

		refundInvoiceCandidateRecord.setC_Order(null);
		refundInvoiceCandidateRecord.setC_OrderLine(null);

		final I_C_Flatrate_Term contractRecord = loadOutOfTrx(contractId.getRepoId(), I_C_Flatrate_Term.class);
		refundInvoiceCandidateRecord.setRecord_ID(contractRecord.getC_Flatrate_Term_ID());
		refundInvoiceCandidateRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));

		refundInvoiceCandidateRecord.setPriceActual(ZERO);
		refundInvoiceCandidateRecord.setPriceEntered(ZERO);

		refundInvoiceCandidateRecord.setQtyOrdered(ONE);
		refundInvoiceCandidateRecord.setQtyDelivered(ONE);

		final RefundConfig refundConfig = retrieveConfig(refundInvoiceCandidateRecord);

		refundInvoiceCandidateRecord.setC_InvoiceSchedule_ID(refundConfig.getInvoiceSchedule().getId().getRepoId());
		refundInvoiceCandidateRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung);
		refundInvoiceCandidateRecord.setInvoiceRule_Override(null);
		refundInvoiceCandidateRecord.setDateToInvoice_Override(null);

		final boolean soTrx = assignableInvoiceCandidateRecord.isSOTrx();
		refundInvoiceCandidateRecord.setIsSOTrx(soTrx);

		final LocalDate dateToInvoice = refundConfig
				.getInvoiceSchedule()
				.calculateNextDateToInvoice(invoiceCandidate.getInvoiceableFrom());
		refundInvoiceCandidateRecord.setDateOrdered(TimeUtil.asTimestamp(dateToInvoice));
		refundInvoiceCandidateRecord.setDeliveryDate(TimeUtil.asTimestamp(dateToInvoice));

		try
		{
			final int docTypeId = computeDocType(assignableInvoiceCandidateRecord, refundConfig);
			refundInvoiceCandidateRecord.setC_DocTypeInvoice_ID(docTypeId);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("invoiceCandidate", invoiceCandidate)
					.setParameter("refundConfig", refundConfig)
					.setParameter("assignableInvoiceCandidateRecord", assignableInvoiceCandidateRecord);
		}

		saveRecord(refundInvoiceCandidateRecord);

		invalidateNewRefundRecordIfNeeded(refundInvoiceCandidateRecord);

		return ofNullableRefundRecord(refundInvoiceCandidateRecord).get();
	}

	private RefundConfig retrieveConfig(@NonNull final I_C_Invoice_Candidate refundInvoiceCandidateRecord)
	{
		final FlatrateTermId contractId = FlatrateTermId.ofRepoId(refundInvoiceCandidateRecord.getRecord_ID());
		final RefundContract refundContract = refundContractRepository.getById(contractId);

		return refundContract.getRefundConfig();
	}

	private int computeDocType(
			final I_C_Invoice_Candidate assignableInvoiceCandidateRecord,
			final RefundConfig refundConfig)
	{
		final boolean soTrx = assignableInvoiceCandidateRecord.isSOTrx();

		final DocTypeQueryBuilder docTypeQueryBuilder = DocTypeQuery
				.builder()
				.isSOTrx(soTrx)
				.adClientId(assignableInvoiceCandidateRecord.getAD_Client_ID())
				.adOrgId(assignableInvoiceCandidateRecord.getAD_Org_ID())
				.docSubType(DocTypeQuery.DOCSUBTYPE_NONE);

		if (soTrx)
		{
			docTypeQueryBuilder.docBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		}
		else
		{
			docTypeQueryBuilder.docBaseType(X_C_DocType.DOCBASETYPE_APCreditMemo);
		}

		switch (refundConfig.getRefundInvoiceType())
		{
			case INVOICE:
				docTypeQueryBuilder.docSubType(X_C_DocType.DOCSUBTYPE_Rueckverguetungsrechnung);
				break;
			case CREDITMEMO:
				docTypeQueryBuilder.docSubType(X_C_DocType.DOCSUBTYPE_Rueckverguetungsgutschrift);
				break;
			default:
				Check.fail("The current refundConfig has an ussupported invoice type={}", refundConfig.getRefundInvoiceType());
		}

		final int docTypeId = Services.get(IDocTypeDAO.class)
				.getDocTypeIdOrNull(docTypeQueryBuilder.build());

		return Check.assumeGreaterThanZero(docTypeId, "docTypeId");
	}

	private void invalidateNewRefundRecordIfNeeded(@NonNull final I_C_Invoice_Candidate refundInvoiceCandidateRecord)
	{
		if (!Services.get(IInvoiceCandBL.class).isUpdateProcessInProgress())
		{
			return; // it's not necessary to make an explicit call because that's already done by a model interceptor
		}
		Services.get(IInvoiceCandDAO.class).invalidateCand(refundInvoiceCandidateRecord);
	}

}
