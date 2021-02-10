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

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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
	public RemittanceAdvice createRemittanceAdvice(@NonNull final CreateRemittanceAdviceRequest remittanceAdviceRequest)
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
	public List<RemittanceAdvice> getRemittanceAdvices(final IQueryFilter<I_C_RemittanceAdvice> filter)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice.class)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.create()
				.list()
				.stream()
				.map(this::toRemittanceAdvice)
				.collect(Collectors.toList());

	}

	@Nullable
	public RemittanceAdvice getRemittanceAdvice(final RemittanceAdviceId remittanceAdviceId)
	{
		final I_C_RemittanceAdvice record = queryBL.createQueryBuilder(I_C_RemittanceAdvice.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_RemittanceAdvice.COLUMN_C_RemittanceAdvice_ID, remittanceAdviceId)
				.create()
				.firstOnly(I_C_RemittanceAdvice.class);

		if (record != null)
		{
			return toRemittanceAdvice(record);
		}
		else
		{
			return null;
		}
	}

	public void updateRemittanceAdvice(final RemittanceAdvice remittanceAdvice)
	{
		if (remittanceAdvice != null)
		{
			final I_C_RemittanceAdvice record = toRemittanceAdvice(remittanceAdvice);
			if (record != null)
			{
				saveRecord(record);
			}

			final List<I_C_RemittanceAdvice_Line> recordLines = remittanceAdvice.getLines()
					.stream()
					.map(this::toRemittanceAdviceLine)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());

			saveAll(recordLines);
		}
	}

	public void updateRemittanceAdviceLine(final RemittanceAdviceLine remittanceAdviceLine){
		final I_C_RemittanceAdvice_Line record = toRemittanceAdviceLine(remittanceAdviceLine);
		if(record != null){
			saveRecord(record);
		}
	}

	@Nullable
	private I_C_RemittanceAdvice_Line toRemittanceAdviceLine(final RemittanceAdviceLine remittanceAdviceLine){

		final I_C_RemittanceAdvice_Line record = queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_Line_ID, remittanceAdviceLine.getRemittanceAdviceLineId())
				.create()
				.firstOnly(I_C_RemittanceAdvice_Line.class);

		if(record != null){
			record.setInvoiceAmt(remittanceAdviceLine.getInvoiceAmt());
			record.setInvoiceAmtInREMADVCurrency(remittanceAdviceLine.getInvoiceAmtInREMADVCurrency());
			record.setOverUnderAmt(remittanceAdviceLine.getOverUnderAmt());
			record.setC_Invoice_Currency_ID(remittanceAdviceLine.getInvoiceCurrencyId() != null ? remittanceAdviceLine.getInvoiceCurrencyId().getRepoId() : record.getC_Invoice_Currency_ID());
			record.setBill_BPartner_ID(remittanceAdviceLine.getBillBPartnerId() != null ? remittanceAdviceLine.getBillBPartnerId().getRepoId() : record.getBill_BPartner_ID());
			record.setInvoiceDate(TimeUtil.asTimestamp(remittanceAdviceLine.getDateInvoiced()));
			record.setService_Fee_Invoice_ID(remittanceAdviceLine.getServiceFeeInvoiceId() != null ? remittanceAdviceLine.getServiceFeeInvoiceId().getRepoId() : record.getService_Fee_Invoice_ID());
			record.setService_Product_ID(remittanceAdviceLine.getServiceFeeProductId() != null ? remittanceAdviceLine.getServiceFeeProductId().getRepoId() : record.getService_Product_ID());
			record.setService_BPartner_ID(remittanceAdviceLine.getServiceFeeBPartnerId() != null ? remittanceAdviceLine.getServiceFeeBPartnerId().getRepoId() : record.getService_BPartner_ID());
			record.setService_Tax_ID(remittanceAdviceLine.getTaxId() != null ? remittanceAdviceLine.getTaxId().getRepoId() : record.getService_Tax_ID());
			record.setIsInvoiceResolved(remittanceAdviceLine.isInvoiceResolved());
			record.setIsInvoiceDocTypeValid(remittanceAdviceLine.isInvoiceDocTypeValid());
			record.setIsAmountValid(remittanceAdviceLine.isAmountValid());
			record.setIsInvoiceDateValid(remittanceAdviceLine.isInvoiceDateValid());
			record.setIsBPartnerValid(remittanceAdviceLine.isBPartnerValid());
			record.setIsServiceColumnsResolved(remittanceAdviceLine.isServiceFeeResolved());
		}

		return record;
	}

	@Nullable
	private I_C_RemittanceAdvice toRemittanceAdvice(final RemittanceAdvice remittanceAdvice)
	{
		final I_C_RemittanceAdvice record = queryBL.createQueryBuilder(I_C_RemittanceAdvice.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_RemittanceAdvice.COLUMN_C_RemittanceAdvice_ID, remittanceAdvice.getRemittanceAdviceId())
				.create()
				.firstOnly(I_C_RemittanceAdvice.class);

		if(record != null){
			record.setDocStatus(remittanceAdvice.getDocStatus());
			record.setRemittanceAmt(remittanceAdvice.getRemittedAmountSum());
			record.setSendAt(TimeUtil.asTimestamp(remittanceAdvice.getSendDate()));
			record.setServiceFeeAmount(remittanceAdvice.getServiceFeeAmount());
			record.setPaymentDiscountAmountSum(remittanceAdvice.getPaymentDiscountAmountSum());
			record.setC_Payment_ID(remittanceAdvice.getPaymentId() != null ? remittanceAdvice.getPaymentId().getRepoId() : record.getC_Payment_ID());
			record.setIsSOTrx(remittanceAdvice.isSOTrx());
		}

		return record;

	}

	@NonNull
	private I_C_RemittanceAdvice buildRemittanceAdviceRecord(@NonNull final CreateRemittanceAdviceRequest createRemittanceAdviceRequest)
	{
		final I_C_RemittanceAdvice record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice.class);

		record.setAD_Org_ID(createRemittanceAdviceRequest.getOrgId().getRepoId());

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

		return record;
	}

	@NonNull
	private I_C_RemittanceAdvice_Line buildRemittanceAdviceLineRecord(
			@NonNull final CreateRemittanceAdviceLineRequest remittanceAdviceLineRequest)
	{
		final I_C_RemittanceAdvice_Line record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice_Line.class);

		record.setC_RemittanceAdvice_ID(remittanceAdviceLineRequest.getRemittanceAdviceId().getRepoId());
		record.setInvoiceIdentifier(remittanceAdviceLineRequest.getInvoiceIdentifier());
		record.setC_BPartner_ID(BPartnerId.toRepoId(remittanceAdviceLineRequest.getBpartnerIdentifier()));

		record.setExternalInvoiceDocBaseType(remittanceAdviceLineRequest.getExternalInvoiceDocBaseType());

		record.setInvoiceDate(TimeUtil.asTimestamp(remittanceAdviceLineRequest.getDateInvoiced()));
		record.setInvoiceAmt(remittanceAdviceLineRequest.getInvoiceGrossAmount());
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

		return RemittanceAdvice.builder()
				.remittanceAdviceId(remittanceAdviceId)
				.isSOTrx(record.isSOTrx())

				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))

				.sourceBPartnerId(BPartnerId.ofRepoId(record.getSource_BPartner_ID()))
				.sourceBPartnerBankAccountId(sourceBPBankAccountId)

				.destinationBPartnerId(BPartnerId.ofRepoId(record.getDestintion_BPartner_ID()))
				.destinationBPartnerBankAccountId(destinationBPBankAccountId)

				.documentNumber(record.getDocumentNo())
				.externalDocumentNumber(record.getExternalDocumentNo())
				.documentDate(TimeUtil.asInstant(record.getDateDoc()))
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
				.lines(retrieveLines(remittanceAdviceId, remittanceCurrencyId))
				.build();
	}

	@NonNull
	public RemittanceAdviceLine toRemittanceAdviceLine(@NonNull final I_C_RemittanceAdvice_Line record, @NonNull final CurrencyId remittanceCurrencyId)
	{
		final CurrencyCode remittanceCurrencyCode = currencyDAO.getCurrencyCodeById(remittanceCurrencyId);

		final Function<BigDecimal, Amount> toAmountOrNull =
				(amountValue) -> amountValue != null ? Amount.of(amountValue, remittanceCurrencyCode) : null;

		return RemittanceAdviceLine.builder()
				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(record.getC_RemittanceAdvice_Line_ID()))

				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID()))

				.invoiceIdentifier(record.getInvoiceIdentifier())

				.invoiceId(InvoiceId.ofRepoIdOrNull(record.getC_Invoice_ID()))

				.remittedAmount(Amount.of(record.getRemittanceAmt(), remittanceCurrencyCode))

				.invoiceAmtInREMADVCurrency(record.getInvoiceAmtInREMADVCurrency())

				.invoiceGrossAmount(toAmountOrNull.apply(record.getInvoiceAmt()))

				.paymentDiscountAmount(toAmountOrNull.apply(record.getPaymentDiscountAmt()))

				.serviceFeeAmount(toAmountOrNull.apply(record.getServiceFeeAmount()))

				.serviceFeeVatRate(record.getServiceFeeVatRate())

				.dateInvoiced(TimeUtil.asInstant(record.getInvoiceDate()))

				.bpartnerIdentifier(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))

				.externalInvoiceDocBaseType(record.getExternalInvoiceDocBaseType())

				.isAmountValid(record.isAmountValid())
				.isBPartnerValid(record.isBPartnerValid())
				.isInvoiceDocTypeValid(record.isInvoiceDocTypeValid())
				.isInvoiceDateValid(record.isInvoiceDateValid())
				.isInvoiceResolved(record.isInvoiceResolved())
				.isServiceFeeResolved(record.isServiceColumnsResolved())

				.taxId(TaxId.ofRepoIdOrNull(record.getService_Tax_ID()))
				.serviceFeeBPartnerId(BPartnerId.ofRepoIdOrNull(record.getService_BPartner_ID()))
				.serviceFeeInvoiceId(InvoiceId.ofRepoIdOrNull(record.getC_Invoice_ID()))
				.serviceFeeProductId(ProductId.ofRepoIdOrNull(record.getService_Product_ID()))
				.serviceFeeVatRate(record.getServiceFeeVatRate())

				.overUnderAmt(record.getOverUnderAmt())

				.invoiceCurrencyId(CurrencyId.ofRepoIdOrNull(record.getC_Invoice_Currency_ID()))

				.build();
	}

	@NonNull
	private List<RemittanceAdviceLine> retrieveLines(@NonNull final RemittanceAdviceId remittanceAdviceId, @NonNull final CurrencyId currencyId)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_ID, remittanceAdviceId.getRepoId())
				.create()
				.list()
				.stream()
				.map(line -> toRemittanceAdviceLine(line, currencyId))
				.collect(Collectors.toList());
	}
}
