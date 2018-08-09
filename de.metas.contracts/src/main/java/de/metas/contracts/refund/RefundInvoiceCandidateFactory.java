package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.DocTypeQuery;
import de.metas.document.DocTypeQuery.DocTypeQueryBuilder;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
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

public class RefundInvoiceCandidateFactory
{
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	public RefundInvoiceCandidateFactory(@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository)
	{
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
	}

	public List<RefundInvoiceCandidate> createRefundInvoiceCandidates(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract,
			@NonNull final List<RefundConfig> refundConfigs)
	{
		final I_C_Invoice_Candidate assignableInvoiceCandidateRecord = load(
				assignableCandidate.getId().getRepoId(),
				I_C_Invoice_Candidate.class);

		final ImmutableList.Builder<RefundInvoiceCandidate> result = ImmutableList.builder();

		for (final RefundConfig refundConfig : refundConfigs)
		{
			final I_C_Invoice_Candidate refundInvoiceCandidateRecord = Services.get(IInvoiceCandBL.class)
					.splitCandidate(assignableInvoiceCandidateRecord);

			final I_C_ILCandHandler handlerRecord = Services.get(IInvoiceCandidateHandlerDAO.class)
					.retrieveForClassOneOnly(Env.getCtx(), FlatrateTerm_Handler.class);
			refundInvoiceCandidateRecord.setC_ILCandHandler(handlerRecord);

			refundInvoiceCandidateRecord.setC_Order(null);
			refundInvoiceCandidateRecord.setC_OrderLine(null);

			refundInvoiceCandidateRecord.setRecord_ID(refundContract.getId().getRepoId());
			refundInvoiceCandidateRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));

			refundInvoiceCandidateRecord.setPriceActual(ZERO);
			refundInvoiceCandidateRecord.setPriceEntered(ZERO);

			refundInvoiceCandidateRecord.setQtyOrdered(ONE);
			refundInvoiceCandidateRecord.setQtyDelivered(ONE);

			refundInvoiceCandidateRecord.setC_InvoiceSchedule_ID(refundConfig.getInvoiceSchedule().getId().getRepoId());
			refundInvoiceCandidateRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_KundenintervallNachLieferung);
			refundInvoiceCandidateRecord.setInvoiceRule_Override(null);
			refundInvoiceCandidateRecord.setDateToInvoice_Override(null);

			final LocalDate dateToInvoice = refundConfig
					.getInvoiceSchedule()
					.calculateNextDateToInvoice(assignableCandidate.getInvoiceableFrom());
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
						.setParameter("invoiceCandidate", assignableCandidate)
						.setParameter("refundConfig", refundConfig)
						.setParameter("assignableInvoiceCandidateRecord", assignableInvoiceCandidateRecord);
			}

			saveRecord(refundInvoiceCandidateRecord);

			invalidateNewRefundRecordIfNeeded(refundInvoiceCandidateRecord);

			final RefundInvoiceCandidate refundInvoiceCandidate = assignmentToRefundCandidateRepository
					.getRefundInvoiceCandidateRepository()
					.ofNullableRefundRecord(refundInvoiceCandidateRecord)
					.get();

			result.add(refundInvoiceCandidate
					.toBuilder()
					.refundConfig(refundConfig)
					.build());
		}

		return result.build();
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
