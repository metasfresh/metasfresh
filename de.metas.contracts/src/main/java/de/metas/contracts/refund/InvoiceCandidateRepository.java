package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.invoicecandidate.HandlerTools;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.DocTypeQuery;
import de.metas.document.DocTypeQuery.DocTypeQueryBuilder;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.money.MoneyFactory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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

@Repository
public class InvoiceCandidateRepository
{
	@VisibleForTesting
	@Getter(AccessLevel.PACKAGE)
	private final InvoiceCandidateFactory invoiceCandidateFactory;

	private final RefundConfigRepository refundConfigRepository;

	public InvoiceCandidateRepository(
			@NonNull final RefundConfigRepository refundConfigRepository,
			@NonNull final MoneyFactory moneyFactory)
	{
		this.invoiceCandidateFactory = new InvoiceCandidateFactory(
				this,
				refundConfigRepository,
				moneyFactory);
		this.refundConfigRepository = refundConfigRepository;
	}

	public <T extends InvoiceCandidate> T ofRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		return invoiceCandidateFactory.ofRecord(record);
	}

	public boolean isRefundInvoiceCandidateRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		if (record.getAD_Table_ID() != getTableId(I_C_Flatrate_Term.class))
		{
			return false;
		}

		final I_C_Flatrate_Term term = loadOutOfTrx(record.getRecord_ID(), I_C_Flatrate_Term.class);
		final boolean recordIsARefundCandidate = X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions());

		return recordIsARefundCandidate;
	}

	public Optional<InvoiceCandidateId> getId(@NonNull final RefundInvoiceCandidateQuery query)
	{
		final int recordId = createRefundInvoiceCandidateQuery(query)
				.firstId();

		if (recordId > 0)
		{
			Optional.of(InvoiceCandidateId.ofRepoId(recordId));
		}
		return Optional.empty();
	}

	public Optional<RefundInvoiceCandidate> getRefundInvoiceCandidate(@NonNull final RefundInvoiceCandidateQuery query)
	{
		final I_C_Invoice_Candidate recordOrNull = createRefundInvoiceCandidateQuery(query)
				.first();

		return invoiceCandidateFactory
				.ofNullableRefundRecord(recordOrNull)
				.map(RefundInvoiceCandidate::cast);
	}

	private IQuery<I_C_Invoice_Candidate> createRefundInvoiceCandidateQuery(final RefundInvoiceCandidateQuery query)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_AD_Table_ID,
						getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_Record_ID,
						query.getRefundContractId().getRepoId())
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_Processed,
						false)
				.addCoalesceEqualsFilter(
						query.getInvoicableFrom(),
						I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override,
						I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice)
				.orderBy()
				.addColumnAscending(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Effective)
				.endOrderBy()
				.create();
	}

	public AssignableInvoiceCandidate saveCandidateAssignment(@Nullable final UnassignedPairOfCandidates request)
	{
		final I_C_Invoice_Candidate_Assignment assignmentRecord = loadOrCreateAssignmentRecord(request);

		final RefundInvoiceCandidate refundInvoiceCandidate = request.getRefundInvoiceCandidate();
		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundInvoiceCandidate.getId().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(refundInvoiceCandidate.getRefundContractId().getRepoId());

		saveRecord(assignmentRecord);

		return request
				.getAssignableInvoiceCandidate()
				.toBuilder()
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();
	}

	private I_C_Invoice_Candidate_Assignment loadOrCreateAssignmentRecord(@NonNull final UnassignedPairOfCandidates request)
	{
		final int repoId = request.getAssignableInvoiceCandidate().getId().getRepoId();

		final I_C_Invoice_Candidate_Assignment existingAssignment = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID, repoId)
				.create()
				.firstOnly(I_C_Invoice_Candidate_Assignment.class);

		if (existingAssignment != null)
		{
			return existingAssignment;
		}
		final I_C_Invoice_Candidate_Assignment newAssignment = newInstance(I_C_Invoice_Candidate_Assignment.class);
		newAssignment.setC_Invoice_Candidate_Assigned_ID(repoId);
		return newAssignment;
	}

	@Value
	@Builder
	public static final class RefundInvoiceCandidateQuery
	{
		@NonNull
		FlatrateTermId refundContractId;

		@NonNull
		LocalDate invoicableFrom;
	}

	public void deleteAssignments(@Nullable final DeleteAssignmentsRequest request)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Invoice_Candidate_Assignment> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class);

		if (request.isOnlyActive())
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		final ICompositeQueryFilter<I_C_Invoice_Candidate_Assignment> invoiceCandidateIDsOrFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate_Assignment.class)
				.setJoinOr();

		final InvoiceCandidateId removeForContractCandidateId = request.getRemoveForRefundCandidateId();
		if (removeForContractCandidateId != null)
		{
			invoiceCandidateIDsOrFilter.addEqualsFilter(
					I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
					removeForContractCandidateId.getRepoId());
		}
		final InvoiceCandidateId removeForAssignedCandidateId = request.getRemoveForAssignedCandidateId();
		if (removeForAssignedCandidateId != null)
		{
			invoiceCandidateIDsOrFilter.addEqualsFilter(
					I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assignment_ID,
					removeForAssignedCandidateId.getRepoId());
		}

		queryBuilder
				.filter(invoiceCandidateIDsOrFilter)
				.create()
				.delete();
	}

	/**
	 *
	 * Note: {@link DeleteAssignmentsRequestBuilder#removeForAssignedCandidateId(InvoiceCandidateId)}
	 * and {@link DeleteAssignmentsRequestBuilder#removeForRefundCandidateId}
	 * are {@code OR}ed and at least one of them has to be no-null.
	 *
	 */
	@Value
	public static final class DeleteAssignmentsRequest
	{
		InvoiceCandidateId removeForRefundCandidateId;
		InvoiceCandidateId removeForAssignedCandidateId;

		boolean onlyActive;

		@Builder
		private DeleteAssignmentsRequest(
				@Nullable final InvoiceCandidateId removeForRefundCandidateId,
				@Nullable final InvoiceCandidateId removeForAssignedCandidateId,
				@Nullable final Boolean onlyActive)
		{
			Check.errorIf(
					removeForRefundCandidateId == null
							&& removeForAssignedCandidateId == null,
					"At least one of the two invoiceCandidateId needs to be not-null");

			this.onlyActive = Util.coalesce(onlyActive, true);

			this.removeForRefundCandidateId = removeForRefundCandidateId;
			this.removeForAssignedCandidateId = removeForAssignedCandidateId;
		}
	}

	public Optional<RefundInvoiceCandidate> getRefundInvoiceCandidate(
			@NonNull final InvoiceCandidateId assignableInvoiceCandidateId)
	{
		final I_C_Invoice_Candidate refundRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						assignableInvoiceCandidateId.getRepoId())
				.andCollect(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
						I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.create()
				// we have a UC-constraint on C_Invoice_Candidate_Assigned_ID, i.e. each assigned candidate may be assigned to max. refundContractCandidate
				.firstOnly(I_C_Invoice_Candidate.class);

		if (refundRecord == null)
		{
			return Optional.empty();
		}

		return invoiceCandidateFactory.ofNullableRefundRecord(refundRecord);
	}

	public void save(@NonNull final InvoiceCandidate invoiceCandidate)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidate.getId().getRepoId(), I_C_Invoice_Candidate.class);
		final Money money = invoiceCandidate.getMoney();

		invoiceCandidateRecord.setPriceActual(money.getValue());
		invoiceCandidateRecord.setC_Currency_ID(money.getCurrency().getId().getRepoId());
		saveRecord(invoiceCandidateRecord);
	}

	public Iterator<AssignableInvoiceCandidate> getAllAssigned(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
						refundInvoiceCandidate.getId().getRepoId())
				.andCollect(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(invoiceCandidateFactory::ofRecord)
				.map(AssignableInvoiceCandidate::cast)
				.iterator();
	}

	public RefundInvoiceCandidate createRefundInvoiceCandidate(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate,
			@NonNull final FlatrateTermId contractId)
	{
		final I_C_Invoice_Candidate assignableInvoiceCandidateRecord = load(invoiceCandidate.getId().getRepoId(), I_C_Invoice_Candidate.class);

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

		final RefundConfig refundConfig = retrieveConfig(refundInvoiceCandidateRecord);

		refundInvoiceCandidateRecord.setC_InvoiceSchedule_ID(refundConfig.getInvoiceScheduleId().getRepoId());
		refundInvoiceCandidateRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung);
		refundInvoiceCandidateRecord.setInvoiceRule_Override(null);
		refundInvoiceCandidateRecord.setDateToInvoice_Override(null);

		final boolean soTrx = !assignableInvoiceCandidateRecord.isSOTrx();

		refundInvoiceCandidateRecord.setIsSOTrx(soTrx);

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

		return invoiceCandidateFactory.ofNullableRefundRecord(refundInvoiceCandidateRecord).get();
	}

	private int computeDocType(
			final I_C_Invoice_Candidate assignableInvoiceCandidateRecord,
			final RefundConfig refundConfig)
	{
		final boolean soTrx = !assignableInvoiceCandidateRecord.isSOTrx();

		final DocTypeQueryBuilder docTypeQueryBuilder = DocTypeQuery
				.builder()
				.isSOTrx(soTrx)
				.adClientId(assignableInvoiceCandidateRecord.getAD_Client_ID())
				.adOrgId(assignableInvoiceCandidateRecord.getAD_Org_ID())
				.docSubType(DocTypeQuery.DOCSUBTYPE_NONE);

		switch (refundConfig.getRefundInvoiceType())
		{
			case INVOICE:
				if (soTrx)
				{
					docTypeQueryBuilder.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice); // Rechnung (Debitorenkonten) = outgoing "they pay" invoice
				}
				else
				{
					docTypeQueryBuilder.docBaseType(X_C_DocType.DOCBASETYPE_APInvoice); // Rechnung (Kreditorenkonten) = incoming "we pay" invoice
				}
				break;
			case CREDITMEMO:
				if (soTrx)
				{
					docTypeQueryBuilder.docBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo); // Gutschrift (Debitorenkonten)
				}
				else
				{
					docTypeQueryBuilder.docBaseType(X_C_DocType.DOCBASETYPE_ARInvoice); // Gutschrift (Debitorenkonten)
				}
				break;
			default:
				Check.fail("The current refundConfig has an ussupported invoice type={}", refundConfig.getRefundInvoiceType());
		}

		final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeIdOrNull(docTypeQueryBuilder.build());
		return Check.assumeGreaterThanZero(docTypeId, "doctype");
	}

	private RefundConfig retrieveConfig(@NonNull final I_C_Invoice_Candidate refundInvoiceCandidateRecord)
	{
		final I_C_Flatrate_Term term = HandlerTools.retrieveTerm(refundInvoiceCandidateRecord);

		final RefundConfig config = refundConfigRepository.getByRefundContractId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()));
		return config;
	}
}
