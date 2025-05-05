/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.remittanceadvice;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveAll;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class RemittanceAdviceRepository
{
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public RemittanceAdvice createRemittanceAdviceHeader(@NonNull final CreateRemittanceAdviceRequest remittanceAdviceRequest)
	{
		final I_C_RemittanceAdvice record = buildRemittanceAdviceRecord(remittanceAdviceRequest);
		saveRecord(record);
		return toRemittanceAdvice(record);
	}

	public void createRemittanceAdviceLine(
			@NonNull final CreateRemittanceAdviceLineRequest remittanceAdviceLineRequest)
	{
		final I_C_RemittanceAdvice_Line record = buildRemittanceAdviceLineRecord(remittanceAdviceLineRequest);

		saveRecord(record);
	}

	@NonNull
	public ImmutableList<RemittanceAdvice> getRemittanceAdvices(final IQueryFilter<I_C_RemittanceAdvice> filter)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice.class)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.create()
				.list()
				.stream()
				.map(this::toRemittanceAdvice)
				.collect(ImmutableList.toImmutableList());

	}

	@NonNull
	public RemittanceAdvice getRemittanceAdvice(final RemittanceAdviceId remittanceAdviceId)
	{
		final I_C_RemittanceAdvice record = getRecordById(remittanceAdviceId);

		return toRemittanceAdvice(record);
	}

	public void updateRemittanceAdvice(@NonNull final RemittanceAdvice remittanceAdvice)
	{
		final I_C_RemittanceAdvice record = toRemittanceAdviceRecord(remittanceAdvice);

		saveRecord(record);

		final List<I_C_RemittanceAdvice_Line> recordLines = remittanceAdvice.getLines()
				.stream()
				.map(this::toRemittanceAdviceLineRecord)
				.collect(Collectors.toList());

		saveAll(recordLines);
	}

	public void updateRemittanceAdviceLine(@NonNull final RemittanceAdviceLine remittanceAdviceLine)
	{
		final I_C_RemittanceAdvice_Line record = toRemittanceAdviceLineRecord(remittanceAdviceLine);
		saveRecord(record);
	}

	@NonNull
	private I_C_RemittanceAdvice_Line toRemittanceAdviceLineRecord(final RemittanceAdviceLine remittanceAdviceLine)
	{
		final I_C_RemittanceAdvice_Line record = getLineRecordById(remittanceAdviceLine.getRemittanceAdviceLineId());

		final Function<Amount, BigDecimal> asBigDecimalOrNll = (amount) -> amount != null ? amount.toBigDecimal() : null;

		record.setAD_Org_ID(remittanceAdviceLine.getOrgId().getRepoId());

		record.setInvoiceAmtInREMADVCurrency(asBigDecimalOrNll.apply(remittanceAdviceLine.getInvoiceAmtInREMADVCurrency()));
		record.setOverUnderAmt(asBigDecimalOrNll.apply(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()));
		record.setPaymentDiscountAmt(asBigDecimalOrNll.apply(remittanceAdviceLine.getPaymentDiscountAmount()));
		record.setRemittanceAmt(remittanceAdviceLine.getRemittedAmount().toBigDecimal());

		record.setC_Invoice_ID(NumberUtils.asInt(remittanceAdviceLine.getInvoiceId(), -1));
		record.setInvoiceAmt(remittanceAdviceLine.getInvoiceAmt());
		record.setC_Invoice_Currency_ID(NumberUtils.asInt(remittanceAdviceLine.getInvoiceCurrencyId(), -1));
		record.setBill_BPartner_ID(NumberUtils.asInt(remittanceAdviceLine.getBillBPartnerId(), -1));
		record.setInvoiceDate(TimeUtil.asTimestamp(remittanceAdviceLine.getDateInvoiced()));
		record.setInvoiceGrossAmount(asBigDecimalOrNll.apply(remittanceAdviceLine.getInvoiceGrossAmount()));

		record.setService_Fee_Invoice_ID(NumberUtils.asInt(remittanceAdviceLine.getServiceFeeInvoiceId(), -1));
		record.setService_Product_ID(NumberUtils.asInt(remittanceAdviceLine.getServiceFeeProductId(), -1));
		record.setService_BPartner_ID(NumberUtils.asInt(remittanceAdviceLine.getServiceFeeBPartnerId(), -1));
		record.setService_Tax_ID(NumberUtils.asInt(remittanceAdviceLine.getTaxId(), -1));

		record.setIsInvoiceResolved(remittanceAdviceLine.isInvoiceResolved());
		record.setIsInvoiceDocTypeValid(remittanceAdviceLine.isInvoiceDocTypeValid());
		record.setIsAmountValid(remittanceAdviceLine.isAmountValid());
		record.setIsInvoiceDateValid(remittanceAdviceLine.isInvoiceDateValid());
		record.setIsBPartnerValid(remittanceAdviceLine.isBPartnerValid());
		record.setIsServiceColumnsResolved(remittanceAdviceLine.isServiceFeeResolved());

		record.setInvoiceIdentifier(remittanceAdviceLine.getInvoiceIdentifier());
		record.setC_BPartner_ID(BPartnerId.toRepoId(remittanceAdviceLine.getBpartnerIdentifier()));
		record.setExternalInvoiceDocBaseType(remittanceAdviceLine.getExternalInvoiceDocBaseType());

		record.setServiceFeeAmount(asBigDecimalOrNll.apply(remittanceAdviceLine.getServiceFeeAmount()));
		record.setServiceFeeVatRate(remittanceAdviceLine.getServiceFeeVatRate());
		record.setIsLineAcknowledged(remittanceAdviceLine.isLineAcknowledged());

		record.setProcessed(remittanceAdviceLine.isProcessed());

		return record;
	}

	@NonNull
	private I_C_RemittanceAdvice toRemittanceAdviceRecord(@NonNull final RemittanceAdvice remittanceAdvice)
	{
		final I_C_RemittanceAdvice record = getRecordById(remittanceAdvice.getRemittanceAdviceId());

		record.setDocStatus(remittanceAdvice.getDocStatus());
		record.setRemittanceAmt(remittanceAdvice.getRemittedAmountSum());
		record.setSendAt(TimeUtil.asTimestamp(remittanceAdvice.getSendDate()));
		record.setServiceFeeAmount(remittanceAdvice.getServiceFeeAmount());
		record.setPaymentDiscountAmountSum(remittanceAdvice.getPaymentDiscountAmountSum());
		record.setC_Payment_ID(remittanceAdvice.getPaymentId() != null ? remittanceAdvice.getPaymentId().getRepoId() : record.getC_Payment_ID());
		record.setIsSOTrx(remittanceAdvice.isSOTrx());

		record.setAD_Org_ID(remittanceAdvice.getOrgId().getRepoId());
		record.setI_IsImported(remittanceAdvice.isImported());

		record.setSource_BPartner_ID(remittanceAdvice.getSourceBPartnerId().getRepoId());
		record.setSource_BP_BankAccount_ID(BPartnerBankAccountId.toRepoId(remittanceAdvice.getSourceBPartnerBankAccountId()));

		record.setDestintion_BPartner_ID(remittanceAdvice.getDestinationBPartnerId().getRepoId());
		record.setDestination_BP_BankAccount_ID(BPartnerBankAccountId.toRepoId(remittanceAdvice.getDestinationBPartnerBankAccountId()));

		record.setDocumentNo(remittanceAdvice.getDocumentNumber());
		record.setExternalDocumentNo(remittanceAdvice.getExternalDocumentNumber());
		record.setDateDoc(TimeUtil.asTimestamp(remittanceAdvice.getDocumentDate()));
		record.setC_DocType_ID(remittanceAdvice.getDocTypeId().getRepoId());

		record.setRemittanceAmt_Currency_ID(remittanceAdvice.getRemittedAmountCurrencyId().getRepoId());
		record.setServiceFeeAmount_Currency_ID(CurrencyId.toRepoId(remittanceAdvice.getServiceFeeCurrencyId()));
		record.setAdditionalNotes(remittanceAdvice.getAdditionalNotes());

		record.setIsDocumentAcknowledged(remittanceAdvice.isDocumentAcknowledged());
		record.setCurrenciesReadOnlyFlag(remittanceAdvice.isCurrenciesReadOnlyFlag());
		record.setProcessed(remittanceAdvice.isProcessed());

		return record;

	}

	@NonNull
	private I_C_RemittanceAdvice buildRemittanceAdviceRecord(@NonNull final CreateRemittanceAdviceRequest createRemittanceAdviceRequest)
	{
		final I_C_RemittanceAdvice record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice.class);

		record.setAD_Org_ID(createRemittanceAdviceRequest.getOrgId().getRepoId());
		record.setI_IsImported(createRemittanceAdviceRequest.isImported());

		record.setSource_BPartner_ID(createRemittanceAdviceRequest.getSourceBPartnerId().getRepoId());
		record.setSource_BP_BankAccount_ID(BPartnerBankAccountId.toRepoId(createRemittanceAdviceRequest.getSourceBPartnerBankAccountId()));

		record.setDestintion_BPartner_ID(createRemittanceAdviceRequest.getDestinationBPartnerId().getRepoId());
		record.setDestination_BP_BankAccount_ID(createRemittanceAdviceRequest.getDestinationBPartnerBankAccountId().getRepoId());

		record.setDocumentNo(createRemittanceAdviceRequest.getDocumentNumber());
		record.setExternalDocumentNo(createRemittanceAdviceRequest.getExternalDocumentNumber());
		record.setDateDoc(TimeUtil.asTimestamp(createRemittanceAdviceRequest.getDocumentDate()));
		record.setC_DocType_ID(createRemittanceAdviceRequest.getDocTypeId().getRepoId());

		record.setPaymentDiscountAmountSum(createRemittanceAdviceRequest.getPaymentDiscountAmountSum());
		record.setRemittanceAmt(createRemittanceAdviceRequest.getRemittedAmountSum());
		record.setRemittanceAmt_Currency_ID(createRemittanceAdviceRequest.getRemittedAmountCurrencyId().getRepoId());

		record.setServiceFeeAmount(createRemittanceAdviceRequest.getServiceFeeAmount());
		record.setServiceFeeAmount_Currency_ID(CurrencyId.toRepoId(createRemittanceAdviceRequest.getServiceFeeCurrencyId()));

		record.setC_Payment_Doctype_Target_ID(createRemittanceAdviceRequest.getTargetPaymentDocTypeId().getRepoId());

		record.setAdditionalNotes(createRemittanceAdviceRequest.getAdditionalNotes());
		record.setSendAt(TimeUtil.asTimestamp(createRemittanceAdviceRequest.getSendDate()));

		record.setDocStatus(DocStatus.Drafted.getCode());

		return record;
	}

	@NonNull
	private I_C_RemittanceAdvice_Line buildRemittanceAdviceLineRecord(
			@NonNull final CreateRemittanceAdviceLineRequest remittanceAdviceLineRequest)
	{
		final I_C_RemittanceAdvice_Line record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice_Line.class);

		record.setAD_Org_ID(remittanceAdviceLineRequest.getOrgId().getRepoId());

		record.setC_RemittanceAdvice_ID(remittanceAdviceLineRequest.getRemittanceAdviceId().getRepoId());
		record.setInvoiceIdentifier(remittanceAdviceLineRequest.getInvoiceIdentifier());
		record.setC_BPartner_ID(BPartnerId.toRepoId(remittanceAdviceLineRequest.getBpartnerIdentifier()));

		record.setExternalInvoiceDocBaseType(remittanceAdviceLineRequest.getExternalInvoiceDocBaseType());

		record.setInvoiceDate(TimeUtil.asTimestamp(remittanceAdviceLineRequest.getDateInvoiced()));
		record.setInvoiceGrossAmount(remittanceAdviceLineRequest.getInvoiceGrossAmount());
		record.setPaymentDiscountAmt(remittanceAdviceLineRequest.getPaymentDiscountAmount());

		record.setRemittanceAmt(remittanceAdviceLineRequest.getRemittedAmount());

		record.setServiceFeeAmount(remittanceAdviceLineRequest.getServiceFeeAmount());
		record.setServiceFeeVatRate(remittanceAdviceLineRequest.getServiceFeeVatRate());
		return record;
	}

	@NonNull
	private RemittanceAdvice toRemittanceAdvice(@NonNull final I_C_RemittanceAdvice record)
	{
		final BPartnerBankAccountId sourceBPBankAccountId = BPartnerBankAccountId
				.ofRepoId(record.getSource_BPartner_ID(), record.getSource_BP_BankAccount_ID());

		final BPartnerBankAccountId destinationBPBankAccountId = BPartnerBankAccountId
				.ofRepoId(record.getDestintion_BPartner_ID(), record.getDestination_BP_BankAccount_ID());

		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final CurrencyId remittanceCurrencyId = CurrencyId.ofRepoId(record.getRemittanceAmt_Currency_ID());
		final CurrencyId serviceFeeCurrencyId = CurrencyId.ofRepoIdOrNull(record.getServiceFeeAmount_Currency_ID());

		return RemittanceAdvice.builder()
				.remittanceAdviceId(remittanceAdviceId)
				.isSOTrx(record.isSOTrx())
				.isImported(record.isI_IsImported())

				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))

				.sourceBPartnerId(BPartnerId.ofRepoId(record.getSource_BPartner_ID()))
				.sourceBPartnerBankAccountId(sourceBPBankAccountId)

				.destinationBPartnerId(BPartnerId.ofRepoId(record.getDestintion_BPartner_ID()))
				.destinationBPartnerBankAccountId(destinationBPBankAccountId)

				.documentNumber(record.getDocumentNo())
				.externalDocumentNumber(record.getExternalDocumentNo())
				.documentDate(TimeUtil.asInstant(record.getDateDoc()))
				.paymentDate(TimeUtil.asInstant(record.getPaymentDate()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.docStatus(record.getDocStatus())

				.paymentDiscountAmountSum(record.getPaymentDiscountAmountSum())
				.remittedAmountSum(record.getRemittanceAmt())
				.remittedAmountCurrencyId(remittanceCurrencyId)

				.serviceFeeAmount(record.getServiceFeeAmount())
				.serviceFeeCurrencyId(CurrencyId.ofRepoIdOrNull(record.getServiceFeeAmount_Currency_ID()))

				.paymentId(PaymentId.ofRepoIdOrNull(record.getC_Payment_ID()))

				.sendDate(TimeUtil.asInstant(record.getSendAt()))
				.additionalNotes(record.getAdditionalNotes())

				.isDocumentAcknowledged(record.isDocumentAcknowledged())
				.currenciesReadOnlyFlag(record.isCurrenciesReadOnlyFlag())
				.processed(record.isProcessed())

				.lines(retrieveLines(remittanceAdviceId, remittanceCurrencyId, serviceFeeCurrencyId ))
				.build();
	}

	@NonNull
	private RemittanceAdviceLine toRemittanceAdviceLine(@NonNull final I_C_RemittanceAdvice_Line record,
			@NonNull final CurrencyId remittanceCurrencyId,
			@Nullable final CurrencyId serviceFeeCurrencyId)
	{
		if (record.getServiceFeeAmount() != null
				&& record.getServiceFeeAmount().signum() != 0
				&& serviceFeeCurrencyId == null)
		{
			throw new AdempiereException("Missing service fee currency for remittance line!")
					.appendParametersToMessage()
					.setParameter("RemittanceAdviceLineId", record.getC_RemittanceAdvice_ID());
		}
		final CurrencyCode remittanceCurrencyCode = currencyDAO.getCurrencyCodeById(remittanceCurrencyId);
		final CurrencyCode serviceFeeCurrencyCode = serviceFeeCurrencyId != null ? currencyDAO.getCurrencyCodeById(serviceFeeCurrencyId) : null;

		final BiFunction<BigDecimal,CurrencyCode, Amount> toAmountOrNull =
				(amountValue, currencyCode) -> amountValue != null && currencyCode != null ? Amount.of(amountValue, currencyCode) : null;

		return RemittanceAdviceLine.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))

				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(record.getC_RemittanceAdvice_Line_ID()))

				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID()))

				.invoiceIdentifier(record.getInvoiceIdentifier())

				.invoiceId(InvoiceId.ofRepoIdOrNull(record.getC_Invoice_ID()))

				.remittedAmount(Amount.of(record.getRemittanceAmt(), remittanceCurrencyCode))

				.invoiceAmtInREMADVCurrency(Amount.of(record.getInvoiceAmtInREMADVCurrency(), remittanceCurrencyCode))

				.invoiceGrossAmount(toAmountOrNull.apply(record.getInvoiceGrossAmount(),remittanceCurrencyCode))

				.paymentDiscountAmount(toAmountOrNull.apply(record.getPaymentDiscountAmt(),remittanceCurrencyCode))

				.serviceFeeAmount(toAmountOrNull.apply(record.getServiceFeeAmount(),serviceFeeCurrencyCode))

				.serviceFeeVatRate(record.getServiceFeeVatRate())

				.dateInvoiced(TimeUtil.asInstant(record.getInvoiceDate()))

				.bpartnerIdentifier(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))

				.externalInvoiceDocBaseType(record.getExternalInvoiceDocBaseType())

				.invoiceAmt(record.getInvoiceAmt())

				.billBPartnerId(BPartnerId.ofRepoIdOrNull(record.getBill_BPartner_ID()))

				.isAmountValid(record.isAmountValid())
				.isBPartnerValid(record.isBPartnerValid())
				.isInvoiceDocTypeValid(record.isInvoiceDocTypeValid())
				.isInvoiceDateValid(record.isInvoiceDateValid())
				.isInvoiceResolved(record.isInvoiceResolved())
				.isServiceFeeResolved(record.isServiceColumnsResolved())
				.isLineAcknowledged(record.isLineAcknowledged())

				.processed(record.isProcessed())

				.taxId(TaxId.ofRepoIdOrNull(record.getService_Tax_ID()))
				.serviceFeeBPartnerId(BPartnerId.ofRepoIdOrNull(record.getService_BPartner_ID()))
				.serviceFeeInvoiceId(InvoiceId.ofRepoIdOrNull(record.getService_Fee_Invoice_ID()))
				.serviceFeeProductId(ProductId.ofRepoIdOrNull(record.getService_Product_ID()))
				.serviceFeeVatRate(record.getServiceFeeVatRate())

				.overUnderAmt(Amount.of(record.getOverUnderAmt(), remittanceCurrencyCode))

				.invoiceCurrencyId(CurrencyId.ofRepoIdOrNull(record.getC_Invoice_Currency_ID()))

				.build();
	}

	@NonNull
	private List<RemittanceAdviceLine> retrieveLines(@NonNull final RemittanceAdviceId remittanceAdviceId,
			@NonNull final CurrencyId remittanceCurrencyId,
			@Nullable final CurrencyId serviceFeeCurrencyId)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_ID, remittanceAdviceId.getRepoId())
				.create()
				.list()
				.stream()
				.map(line -> toRemittanceAdviceLine(line, remittanceCurrencyId, serviceFeeCurrencyId))
				.collect(Collectors.toList());
	}

	@NonNull
	private I_C_RemittanceAdvice_Line getLineRecordById(@NonNull final RemittanceAdviceLineId remittanceAdviceLineId)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_Line_ID, remittanceAdviceLineId)
				.create()
				.firstOnlyNotNull(I_C_RemittanceAdvice_Line.class);
	}

	@NonNull
	private I_C_RemittanceAdvice getRecordById(@NonNull final RemittanceAdviceId remittanceAdviceId)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice.class)
				.addEqualsFilter(I_C_RemittanceAdvice.COLUMNNAME_C_RemittanceAdvice_ID, remittanceAdviceId)
				.create()
				.firstOnlyNotNull(I_C_RemittanceAdvice.class);
	}
}
