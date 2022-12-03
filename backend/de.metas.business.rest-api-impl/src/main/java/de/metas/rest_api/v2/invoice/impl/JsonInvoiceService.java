package de.metas.rest_api.v2.invoice.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.RestUtils;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.invoice.JsonInvoicePaymentCreateRequest;
import de.metas.common.rest_api.v2.invoice.JsonPaymentAllocationLine;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.inout.IInOutDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceQuery;
import de.metas.invoice.InvoiceService;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidatesResponseItem;
import de.metas.rest_api.invoicecandidates.response.JsonReverseInvoiceResponse;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.payment.PaymentService;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class JsonInvoiceService
{
	private final static transient Logger logger = LogManager.getLogger(InvoiceService.class);

	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	private final CurrencyService currencyService;
	private final PaymentService paymentService;
	private final JsonRetrieverService jsonRetrieverService;
	private final InvoiceService invoiceService;

	public JsonInvoiceService(
			@NonNull final CurrencyService currencyService,
			@NonNull final PaymentService paymentService,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final InvoiceService invoiceService)
	{
		this.currencyService = currencyService;
		this.paymentService = paymentService;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
		this.invoiceService = invoiceService;
	}

	public Optional<byte[]> getInvoicePDF(@NonNull final InvoiceId invoiceId)
	{
		return getLastArchive(invoiceId).map(archiveBL::getBinaryData);
	}

	@NonNull
	public JSONInvoiceInfoResponse getInvoiceInfo(@NonNull final InvoiceId invoiceId, final String ad_language)
	{
		final JSONInvoiceInfoResponse.JSONInvoiceInfoResponseBuilder result = JSONInvoiceInfoResponse.builder();

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		final CurrencyCode currency = currencyDAO.getCurrencyCodeById(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final List<I_C_InvoiceLine> lines = invoiceDAO.retrieveLines(invoiceId);
		for (final I_C_InvoiceLine line : lines)
		{
			final String productName = productBL.getProductNameTrl(ProductId.ofRepoId(line.getM_Product_ID())).translate(ad_language);
			final Percent taxRate = taxDAO.getRateById(TaxId.ofRepoId(line.getC_Tax_ID()));

			result.lineInfo(JSONInvoiceLineInfo.builder()
									.lineNumber(line.getLine())
									.productName(productName)
									.qtyInvoiced(line.getQtyEntered())
									.price(line.getPriceEntered())
									.taxRate(taxRate)
									.lineNetAmt(line.getLineNetAmt())
									.currency(currency)
									.build());
		}

		result.invoiceId(JsonMetasfreshId.of(invoiceId.getRepoId()));

		return result.build();
	}

	@NonNull
	public Optional<JsonReverseInvoiceResponse> reverseInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice documentRecord = invoiceDAO.getByIdInTrx(invoiceId);
		if (documentRecord == null)
		{
			return Optional.empty();
		}

		documentBL.processEx(documentRecord, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		final JsonReverseInvoiceResponse.JsonReverseInvoiceResponseBuilder responseBuilder = JsonReverseInvoiceResponse.builder();

		invoiceCandDAO
				.retrieveInvoiceCandidates(invoiceId)
				.stream()
				.map(this::buildJSONItem)
				.forEach(responseBuilder::affectedInvoiceCandidate);

		return Optional.of(responseBuilder.build());
	}

	public void createInboundPaymentFromJson(@NonNull @RequestBody final JsonInvoicePaymentCreateRequest request)
	{
		final LocalDate dateTrx = CoalesceUtil.coalesce(request.getTransactionDate(), SystemTime.asLocalDate());

		final List<JsonPaymentAllocationLine> lines = request.getLines();
		if (validateAllocationLineAmounts(lines))
		{
			throw new AdempiereException("At least one of the following allocation amounts are mandatory in every line: amount, discountAmt, writeOffAmt");
		}

		final CurrencyId currencyId = currencyService.getCurrencyId(request.getCurrencyCode());
		if (currencyId == null)
		{
			throw new AdempiereException("Wrong currency: " + request.getCurrencyCode());
		}

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Cannot find the orgId from either orgCode=" + request.getOrgCode() + " or the current user's context.");
		}

		final BankAccountId bankAccountId = paymentService.determineInboundBankAccountId(orgId, currencyId, request.getTargetIBAN())
				.orElseThrow(() -> new AdempiereException(String.format(
						"Cannot find Bank Account for org-id: %s, currency: %s and iban: %s", orgId, currencyId, request.getTargetIBAN())));

		final ExternalIdentifier bPartnerExternalIdentifier = ExternalIdentifier.of(request.getBpartnerIdentifier());
		final BPartnerId bPartnerId = jsonRetrieverService.resolveBPartnerExternalIdentifier(bPartnerExternalIdentifier, orgId)
				.orElseThrow(() -> new AdempiereException("No BPartner could be found for the given external BPartner identifier!")
						.appendParametersToMessage()
						.setParameter("externalBPartnerIdentifier", bPartnerExternalIdentifier.getRawValue())
						.setParameter("orgId", orgId));

		trxManager.runInThreadInheritedTrx(() -> {

			final I_C_Payment payment = paymentService.newInboundReceiptBuilder()
					.bpartnerId(bPartnerId)
					.payAmt(request.getAmount())
					.discountAmt(request.getDiscountAmt())
					.writeoffAmt(request.getWriteOffAmt())
					.currencyId(currencyId)
					.orgBankAccountId(bankAccountId)
					.adOrgId(orgId)
					.tenderType(TenderType.DirectDeposit)
					.dateAcct(dateTrx)
					.dateTrx(dateTrx)
					.externalId(ExternalId.ofOrNull(request.getExternalPaymentId()))
					.isAutoAllocateAvailableAmt(true)
					.createAndProcess();

			InterfaceWrapperHelper.save(payment);

			createAllocationsForPayment(payment, lines);
		});
	}

	@NonNull
	public List<JSONInvoiceInfoResponse> buildInvoiceInfoResponseFromOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		final List<I_M_InOutLine> shipmentLines = inOutDAO.retrieveShipmentLinesForOrderId(orderIds);

		final Set<InvoiceId> invoiceIds = invoiceService.retrieveInvoiceCandsByInOutLines(shipmentLines).stream()
				.map(invoiceCandDAO::retrieveIlForIc)
				.flatMap(List::stream)
				.map(org.compiere.model.I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return invoiceIds.stream()
				.map(invoiceId -> getInvoiceInfo(invoiceId, Env.getAD_Language()))
				.collect(ImmutableList.toImmutableList());
	}

	private JsonInvoiceCandidatesResponseItem buildJSONItem(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return JsonInvoiceCandidatesResponseItem
				.builder()
				.externalHeaderId(JsonExternalId.ofOrNull(invoiceCandidate.getExternalHeaderId()))
				.externalLineId(JsonExternalId.ofOrNull(invoiceCandidate.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidate.getC_Invoice_Candidate_ID()))
				.build();
	}

	private boolean validateAllocationLineAmounts(@Nullable final List<JsonPaymentAllocationLine> lines)
	{
		return !Check.isEmpty(lines) && lines.stream().anyMatch(line -> Check.isEmpty(line.getAmount()));
	}

	private void createAllocationsForPayment(@NonNull final I_C_Payment payment, @Nullable final List<JsonPaymentAllocationLine> allocationLines)
	{
		if (Check.isEmpty(allocationLines))
		{
			return;
		}

		final int orgId = payment.getAD_Org_ID();

		final C_AllocationHdr_Builder allocationBuilder = allocationBL.newBuilder()
				.currencyId(payment.getC_Currency_ID())
				.dateTrx(payment.getDateTrx())
				.dateAcct(payment.getDateAcct())
				.orgId(orgId);

		for (final JsonPaymentAllocationLine line : allocationLines)
		{
			final String invoiceId = line.getInvoiceIdentifier();
			final String docBaseType = line.getDocBaseType();

			final DocBaseAndSubType docType = Check.isBlank(docBaseType)
					? null
					: DocBaseAndSubType.of(docBaseType, line.getDocSubType());

			final Optional<InvoiceId> invoice = retrieveInvoice(IdentifierString.of(invoiceId), OrgId.ofRepoIdOrNull(orgId), docType);

			Check.assumeNotEmpty(invoice, "Cannot find invoice for identifier: " + invoiceId);
			allocationBuilder.addLine()
					.skipIfAllAmountsAreZero()
					.invoiceId(invoice.get().getRepoId())
					.orgId(orgId)
					.bpartnerId(payment.getC_BPartner_ID())
					.amount(line.getAmount())
					.discountAmt(line.getDiscountAmt())
					.writeOffAmt(line.getWriteOffAmt())
					.paymentId(payment.getC_Payment_ID())
					.lineDone();
		}
		allocationBuilder.createAndComplete();
	}

	private Optional<InvoiceId> retrieveInvoice(final IdentifierString invoiceIdentifier, final OrgId orgId, final DocBaseAndSubType docType)
	{
		final InvoiceQuery invoiceQuery = createInvoiceQuery(invoiceIdentifier, orgId, docType);
		return invoiceDAO.retrieveIdByInvoiceQuery(invoiceQuery);
	}

	private InvoiceQuery createInvoiceQuery(
			@NonNull final IdentifierString identifierString,
			@NonNull final OrgId orgId,
			@Nullable final DocBaseAndSubType docType)
	{
		final InvoiceQuery.InvoiceQueryBuilder invoiceQueryBuilder = InvoiceQuery.builder()
				.orgId(orgId)
				.docType(docType);

		switch (identifierString.getType())
		{
			case METASFRESH_ID:
				invoiceQueryBuilder.invoiceId(identifierString.asMetasfreshId().getValue());
				break;
			case EXTERNAL_ID:
				invoiceQueryBuilder.externalId(identifierString.asExternalId());
				break;
			case DOC:
				invoiceQueryBuilder.documentNo(identifierString.asDoc());
				break;
			default:
				throw new AdempiereException("Invalid identifierString: " + identifierString);
		}
		return invoiceQueryBuilder.build();
	}

	private Optional<I_AD_Archive> getLastArchive(@NonNull final InvoiceId invoiceId)
	{
		return archiveBL.getLastArchive(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId));
	}
}
