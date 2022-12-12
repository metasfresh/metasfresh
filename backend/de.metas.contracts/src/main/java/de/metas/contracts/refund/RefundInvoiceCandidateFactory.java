package de.metas.contracts.refund;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundContract.NextInvoiceDate;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.DocTypeQuery.DocTypeQueryBuilder;
import de.metas.document.IDocTypeDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.util.collections.CollectionUtils.extractSingleElement;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.TimeUtil.asTimestamp;

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
public class RefundInvoiceCandidateFactory
{
	private final RefundContractRepository refundContractRepository;

	@Getter
	private final AssignmentAggregateService assignmentAggregateService;

	public RefundInvoiceCandidateFactory(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final AssignmentAggregateService assignmentAggregateService)
	{
		this.refundContractRepository = refundContractRepository;
		this.assignmentAggregateService = assignmentAggregateService;
	}

	/**
	 * With the given {@link AssignableInvoiceCandidate} and {@link RefundContract}, this method creates one {@link RefundInvoiceCandidate} for each given {@link RefundConfig}.
	 */
	public List<RefundInvoiceCandidate> createRefundCandidates(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract,
			@NonNull final List<RefundConfig> refundConfigs)
	{
		Check.assumeNotEmpty(refundConfigs, "The Given refundConfigs list may not be empty; refundContract={}; assignableCandidate={}", refundContract, assignableCandidate);

		final ImmutableList.Builder<RefundInvoiceCandidate> result = ImmutableList.builder();

		final RefundMode refundMode = refundContract.extractRefundMode();

		if (RefundMode.APPLY_TO_EXCEEDING_QTY.equals(refundMode))
		{
			for (final RefundConfig refundConfig : refundConfigs)
			{
				final RefundInvoiceCandidate resultCandidate = createSingleRefundCandidate(
						assignableCandidate,
						refundContract,
						ImmutableList.of(refundConfig));
				result.add(resultCandidate);
			}
		}
		else
		{
			final RefundInvoiceCandidate resultCandidate = createSingleRefundCandidate(
					assignableCandidate,
					refundContract,
					refundConfigs);
			result.add(resultCandidate);
		}

		return result.build();
	}

	private RefundInvoiceCandidate createSingleRefundCandidate(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract,
			@NonNull final List<RefundConfig> refundConfigs)
	{
		final I_C_Invoice_Candidate assignableInvoiceCandidateRecord = load(
				assignableCandidate.getId().getRepoId(),
				I_C_Invoice_Candidate.class);

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

		refundInvoiceCandidateRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery);
		refundInvoiceCandidateRecord.setInvoiceRule_Override(null);
		refundInvoiceCandidateRecord.setDateToInvoice_Override(null);

		final NextInvoiceDate nextRefundInvoiceDate = refundContract.computeNextInvoiceDate(assignableCandidate.getInvoiceableFrom());
		refundInvoiceCandidateRecord.setC_InvoiceSchedule_ID(nextRefundInvoiceDate.getInvoiceSchedule().getId().getRepoId());

		final Timestamp dateToInvoiceFromInvoiceSchedule = asTimestamp(nextRefundInvoiceDate.getDateToInvoice());
		refundInvoiceCandidateRecord.setDateOrdered(dateToInvoiceFromInvoiceSchedule);
		refundInvoiceCandidateRecord.setDeliveryDate(dateToInvoiceFromInvoiceSchedule);

		final RefundInvoiceType refundInvoiceType = extractSingleElement(refundConfigs, RefundConfig::getRefundInvoiceType);
		try
		{
			final DocTypeId docTypeId = computeDocType(assignableInvoiceCandidateRecord, refundInvoiceType);
			refundInvoiceCandidateRecord.setC_DocTypeInvoice_ID(docTypeId.getRepoId());
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("invoiceCandidate", assignableCandidate)
					.setParameter("refundConfigs", refundConfigs)
					.setParameter("assignableInvoiceCandidateRecord", assignableInvoiceCandidateRecord);
		}

		saveRecord(refundInvoiceCandidateRecord);

		invalidateNewRefundRecordIfNeeded(refundInvoiceCandidateRecord);

		final RefundInvoiceCandidate refundInvoiceCandidate = ofNullableRefundRecord(refundInvoiceCandidateRecord)
				.get();

		final RefundInvoiceCandidate resultCandidate = refundInvoiceCandidate
				.toBuilder()
				.clearRefundConfigs()
				.refundConfigs(refundConfigs)
				.build();
		return resultCandidate;
	}

	private DocTypeId computeDocType(
			final I_C_Invoice_Candidate assignableInvoiceCandidateRecord,
			final RefundInvoiceType refundInvoiceType)
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
			docTypeQueryBuilder.docBaseType(DocBaseType.ARCreditMemo);
		}
		else
		{
			docTypeQueryBuilder.docBaseType(DocBaseType.APCreditMemo);
		}

		switch (refundInvoiceType)
		{
			case INVOICE:
				docTypeQueryBuilder.docSubType(X_C_DocType.DOCSUBTYPE_Rueckverguetungsrechnung);
				break;
			case CREDITMEMO:
				docTypeQueryBuilder.docSubType(X_C_DocType.DOCSUBTYPE_Rueckverguetungsgutschrift);
				break;
			default:
				Check.fail("The current refundConfig has an ussupported invoice type={}", refundInvoiceType);
				break;
		}

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(docTypeQueryBuilder.build());

		return docTypeId;
	}

	private void invalidateNewRefundRecordIfNeeded(@NonNull final I_C_Invoice_Candidate refundInvoiceCandidateRecord)
	{
		if (!Services.get(IInvoiceCandBL.class).isUpdateProcessInProgress())
		{
			return; // it's not necessary to make an explicit call because that's already done by a model interceptor
		}
		Services.get(IInvoiceCandDAO.class).invalidateCand(refundInvoiceCandidateRecord);
	}

	public RefundInvoiceCandidate ofRecord(@Nullable final I_C_Invoice_Candidate refundRecord)
	{
		return ofNullableRefundRecord(refundRecord).get();
	}

	public Optional<RefundInvoiceCandidate> ofNullableRefundRecord(@Nullable final I_C_Invoice_Candidate refundRecord)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		if (refundRecord == null)
		{
			return Optional.empty();
		}

		final TableRecordReference reference = refundRecord.getAD_Table_ID() > 0
				? TableRecordReference.ofReferenced(refundRecord)
				: null;

		final RefundContract refundContract = retrieveRefundContractOrNull(reference);
		if (refundContract == null)
		{
			return Optional.empty();
		}

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(refundRecord.getC_Invoice_Candidate_ID());

		final Map<RefundConfig, BigDecimal> configIdAndQuantity = assignmentAggregateService.retrieveAssignedQuantities(invoiceCandidateId);
		final List<RefundConfig> refundConfigs;
		final BigDecimal assignedQuantity;
		if (configIdAndQuantity.isEmpty())
		{
			refundConfigs = ImmutableList.of(refundContract.getRefundConfig(ZERO));
			assignedQuantity = ZERO;
		}
		else
		{
			// add assigned quantities for the different refund configs
			assignedQuantity = configIdAndQuantity.values().stream().reduce(ZERO, BigDecimal::add);

			refundConfigs = ImmutableList.copyOf(configIdAndQuantity.keySet());
		}

		final Timestamp invoicableFromDate = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);

		final BigDecimal priceActual = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_PriceActual);
		final Money money = Money.of(
				priceActual,
				CurrencyId.ofRepoId(refundRecord.getC_Currency_ID()));

		final I_C_UOM productUom = productBL.getStockUOM(ProductId.ofRepoId(refundRecord.getM_Product_ID()));

		final BPartnerLocationAndCaptureId billLocationId = InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(refundRecord)
				.getBPartnerLocationAndCaptureId();

		final RefundInvoiceCandidate invoiceCandidate = RefundInvoiceCandidate
				.builder()
				.id(invoiceCandidateId)
				.refundContract(refundContract)
				.refundConfigs(refundConfigs)
				.assignedQuantity(Quantity.of(assignedQuantity, productUom))
				.bpartnerId(billLocationId.getBpartnerId())
				.bpartnerLocationId(billLocationId.getBpartnerLocationId())
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.build();
		return Optional.of(invoiceCandidate);
	}

	private RefundContract retrieveRefundContractOrNull(@Nullable final TableRecordReference reference)
	{
		if (reference == null)
		{
			return null;
		}

		if (!I_C_Flatrate_Term.Table_Name.equals(reference.getTableName()))
		{
			return null;
		}

		final I_C_Flatrate_Term term = reference.getModel(I_C_Flatrate_Term.class);
		if (!X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions()))
		{
			return null;
		}

		final FlatrateTermId contractId = FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID());

		final RefundContract refundContract = refundContractRepository.getById(contractId);
		return refundContract;
	}
}
