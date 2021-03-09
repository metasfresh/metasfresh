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
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.Builder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class RemittanceAdviceRepositoryTest
{
	BPartnerId bpartnerId = BPartnerId.ofRepoId(1234);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	RemittanceAdviceRepository remittanceAdviceRepository;

	@Before
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(RemittanceAdviceRepository.class, new RemittanceAdviceRepository());
		remittanceAdviceRepository = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);
	}

	@Test
	public void testUpdateRemittanceAdvice()
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		saveRecord(invoice);

		I_C_RemittanceAdvice record = newInstance(I_C_RemittanceAdvice.class);
		saveRecord(record);
		record = getRemittanceAdviceEntity(record.getC_RemittanceAdvice_ID());

		I_C_RemittanceAdvice_Line recordLine = newInstance(I_C_RemittanceAdvice_Line.class);
		recordLine.setC_RemittanceAdvice_ID(record.getC_RemittanceAdvice_ID());
		saveRecord(recordLine);
		recordLine = getRemittanceAdviceLineEntity(record.getC_RemittanceAdvice_ID());

		final Instant currentTime = Instant.now();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine()
				.remittanceAdviceId(record.getC_RemittanceAdvice_ID())
				.remittanceAdviceLineId(recordLine.getC_RemittanceAdvice_Line_ID())
				.invoiceIdentifier("doc-123")
				.invoiceId(invoice.getC_Invoice_ID())
				.remittanceAmt(100)
				.discountAmt(10)
				.invoiceAmt(100)
				.overUnderAmount(0)
				.currencyCode(CurrencyCode.EUR)
				.externalInvoiceDocBaseType("APP")
				.serviceFeeAmt(5)
				.serviceFeeVATRate(2)
				.isLineAcknowledged(true)
				.isBPartnerValid(true)
				.isInvoiceResolved(true)
				.isAmountValid(true)
				.isInvoiceDocTypeValid(true)
				.isInvoiceDateValid(true)
				.isServiceFeeResolved(true)
				.processed(true)
				.dateInvoiced(currentTime)
				.serviceFeeInvoiceID(11)
				.serviceFeeBPartnerId(10)
				.serviceFeeProductId(9)
				.serviceFeeTaxId(8)
				.build();

		final RemittanceAdvice remittanceAdvice = remittance()
				.remittanceAdviceId(record.getC_RemittanceAdvice_ID())
				.documentDate(currentTime)
				.documentNumber("test")
				.externalDocumentNumber("test")
				.documentStatus("CO")
				.additionalNotes("test")
				.isSOTrx(true)
				.isImported(true)
				.isProcessed(true)
				.currenciesReadOnlyFlag(true)
				.isDocumentAcknowledged(true)
				.discountAmt(10)
				.serviceFeeAmt(5)
				.remittedAmountSum(100)
				.lines(ImmutableList.of(remittanceAdviceLine)).build();

		remittanceAdviceRepository.updateRemittanceAdvice(remittanceAdvice);

		record = getRemittanceAdviceEntity(record.getC_RemittanceAdvice_ID());
		assertThat(remittanceAdviceModelSimilarToEntity(remittanceAdvice, record)).isTrue();

	}

	private I_C_RemittanceAdvice getRemittanceAdviceEntity(final int id)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice.COLUMN_C_RemittanceAdvice_ID, id)
				.create()
				.first();
	}

	private I_C_RemittanceAdvice_Line getRemittanceAdviceLineEntity(final int id)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_ID, id)
				.create()
				.first();
	}

	@Builder(builderMethodName = "remittance")
	private RemittanceAdvice getRemittance(
			final int remittanceAdviceId,
			final Instant documentDate,
			final String documentNumber,
			final String externalDocumentNumber,
			final String documentStatus,
			final String additionalNotes,
			final boolean isSOTrx,
			final boolean isImported,
			final boolean isProcessed,
			final boolean currenciesReadOnlyFlag,
			final boolean isDocumentAcknowledged,
			final int discountAmt,
			final int serviceFeeAmt,
			final int remittedAmountSum,
			final ImmutableList<RemittanceAdviceLine> lines
	)
	{
		final RemittanceAdvice remittanceAdvice;

		remittanceAdvice = RemittanceAdvice.builder()
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(remittanceAdviceId))
				.orgId(OrgId.ofRepoId(123))
				.clientId(ClientId.ofRepoId(123))
				.sourceBPartnerId(BPartnerId.ofRepoId(111))
				.sourceBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(BPartnerId.ofRepoId(111), 123))
				.destinationBPartnerId(bpartnerId)
				.destinationBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(BPartnerId.ofRepoId(123), 111))
				.documentNumber(documentNumber)
				.documentDate(documentDate)
				.externalDocumentNumber(externalDocumentNumber)
				.docStatus(documentStatus)
				.remittedAmountCurrencyId(CurrencyId.ofRepoId(102))
				.sendDate(documentDate)
				.serviceFeeCurrencyId(CurrencyId.ofRepoId(102))
				.additionalNotes(additionalNotes)
				.isSOTrx(isSOTrx)
				.isImported(isImported)
				.serviceFeeAmount(new BigDecimal(serviceFeeAmt))
				.paymentDiscountAmountSum(new BigDecimal(discountAmt))
				.paymentId(PaymentId.ofRepoId(1000))
				.docTypeId(DocTypeId.ofRepoId(1))
				.remittedAmountSum(new BigDecimal(remittedAmountSum))
				.isDocumentAcknowledged(isDocumentAcknowledged)
				.currenciesReadOnlyFlag(currenciesReadOnlyFlag)
				.processed(isProcessed)
				.lines(lines)
				.build();

		return remittanceAdvice;
	}

	@Builder(builderMethodName = "remittanceLine")
	private RemittanceAdviceLine getRemittanceLine(
			final int remittanceAdviceId,
			final int remittanceAdviceLineId,
			@Nullable final String invoiceIdentifier,
			final int invoiceId,
			final int remittanceAmt,
			final int discountAmt,
			final int serviceFeeAmt,
			final int invoiceAmt,
			final int overUnderAmount,
			@Nullable final CurrencyCode currencyCode,
			@Nullable final String externalInvoiceDocBaseType,
			final int serviceFeeVATRate,
			final boolean isLineAcknowledged,
			final boolean isBPartnerValid,
			final boolean isInvoiceResolved,
			final boolean isAmountValid,
			final boolean isInvoiceDocTypeValid,
			final boolean isInvoiceDateValid,
			final boolean isServiceFeeResolved,
			final boolean processed,
			final Instant dateInvoiced,
			final int serviceFeeBPartnerId,
			final int serviceFeeProductId,
			final int serviceFeeTaxId,
			final int serviceFeeInvoiceID
	)
	{
		final RemittanceAdviceLine remittanceAdviceLine;

		remittanceAdviceLine = RemittanceAdviceLine.builder()
				.orgId(OrgId.ofRepoId(100))
				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(remittanceAdviceLineId))
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(remittanceAdviceId))
				.remittedAmount(Amount.of(remittanceAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))
				.invoiceGrossAmount(Amount.of(remittanceAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))
				.paymentDiscountAmount(Amount.of(discountAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))
				.serviceFeeAmount(Amount.of(serviceFeeAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))
				.externalInvoiceDocBaseType(externalInvoiceDocBaseType)
				.bpartnerIdentifier(bpartnerId)
				.invoiceIdentifier(invoiceIdentifier)
				.invoiceId(InvoiceId.ofRepoIdOrNull(invoiceId))
				.serviceFeeVatRate(new BigDecimal(serviceFeeVATRate))
				.isLineAcknowledged(isLineAcknowledged)
				.invoiceAmt(new BigDecimal(invoiceAmt))
				.invoiceAmtInREMADVCurrency(Amount.of(serviceFeeAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))
				.overUnderAmt(Amount.of(overUnderAmount, currencyCode != null ? currencyCode : CurrencyCode.EUR))
				.invoiceCurrencyId(CurrencyId.ofRepoId(102))
				.billBPartnerId(bpartnerId)
				.dateInvoiced(dateInvoiced)
				.serviceFeeInvoiceId(InvoiceId.ofRepoId(serviceFeeInvoiceID))
				.serviceFeeBPartnerId(BPartnerId.ofRepoId(serviceFeeBPartnerId))
				.serviceFeeProductId(ProductId.ofRepoId(serviceFeeProductId))
				.taxId(TaxId.ofRepoId(serviceFeeTaxId))
				.isBPartnerValid(isBPartnerValid)
				.isInvoiceResolved(isInvoiceResolved)
				.isAmountValid(isAmountValid)
				.isInvoiceDocTypeValid(isInvoiceDocTypeValid)
				.isInvoiceDateValid(isInvoiceDateValid)
				.isServiceFeeResolved(isServiceFeeResolved)
				.processed(processed)
				.build();

		return remittanceAdviceLine;
	}

	@SuppressWarnings("ConstantConditions")
	private boolean remittanceAdviceModelSimilarToEntity(final RemittanceAdvice remittanceAdviceModel, final I_C_RemittanceAdvice remittanceAdviceEntity)
	{

		boolean similar = remittanceAdviceModel.getRemittanceAdviceId().getRepoId() == remittanceAdviceEntity.getC_RemittanceAdvice_ID()
				&& remittanceAdviceModel.getOrgId().getRepoId() == remittanceAdviceEntity.getAD_Org_ID()
				&& remittanceAdviceModel.getSourceBPartnerId().getRepoId() == remittanceAdviceEntity.getSource_BPartner_ID()
				&& remittanceAdviceModel.getSourceBPartnerBankAccountId().getRepoId() == remittanceAdviceEntity.getSource_BP_BankAccount_ID()
				&& remittanceAdviceModel.getDestinationBPartnerId().getRepoId() == remittanceAdviceEntity.getDestintion_BPartner_ID()
				&& remittanceAdviceModel.getDestinationBPartnerBankAccountId().getRepoId() == remittanceAdviceEntity.getDestination_BP_BankAccount_ID()
				&& remittanceAdviceModel.getDocumentNumber().equals(remittanceAdviceEntity.getDocumentNo())
				&& remittanceAdviceModel.getDocumentDate().equals(remittanceAdviceEntity.getDateDoc().toInstant())
				&& remittanceAdviceEntity.getExternalDocumentNo().equals(remittanceAdviceModel.getExternalDocumentNumber())
				&& remittanceAdviceModel.getDocStatus().equals(remittanceAdviceEntity.getDocStatus())
				&& remittanceAdviceModel.getRemittedAmountCurrencyId().getRepoId() == remittanceAdviceEntity.getRemittanceAmt_Currency_ID()
				&& remittanceAdviceModel.getSendDate().equals(remittanceAdviceEntity.getSendAt().toInstant())
				&& remittanceAdviceModel.getServiceFeeCurrencyId().getRepoId() == remittanceAdviceEntity.getServiceFeeAmount_Currency_ID()
				&& remittanceAdviceModel.getAdditionalNotes().equals(remittanceAdviceEntity.getAdditionalNotes())
				&& remittanceAdviceModel.isSOTrx() == remittanceAdviceEntity.isSOTrx()
				&& remittanceAdviceModel.isImported() == remittanceAdviceEntity.isI_IsImported()
				&& remittanceAdviceModel.getServiceFeeAmount().compareTo(remittanceAdviceEntity.getServiceFeeAmount()) == 0
				&& remittanceAdviceModel.getPaymentDiscountAmountSum().compareTo(remittanceAdviceEntity.getPaymentDiscountAmountSum()) == 0
				&& remittanceAdviceModel.getPaymentId().getRepoId() == remittanceAdviceEntity.getC_Payment_ID()
				&& remittanceAdviceModel.getDocTypeId().getRepoId() == remittanceAdviceEntity.getC_DocType_ID()
				&& remittanceAdviceModel.getRemittedAmountSum().compareTo(remittanceAdviceEntity.getRemittanceAmt()) == 0
				&& remittanceAdviceModel.isDocumentAcknowledged() == remittanceAdviceEntity.isDocumentAcknowledged()
				&& remittanceAdviceModel.isCurrenciesReadOnlyFlag() == remittanceAdviceEntity.isCurrenciesReadOnlyFlag()
				&& remittanceAdviceModel.isProcessed() == remittanceAdviceEntity.isProcessed();

		final Map<RemittanceAdviceLineId, I_C_RemittanceAdvice_Line> entityLinesById = queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_ID, remittanceAdviceEntity.getC_RemittanceAdvice_ID())
				.create()
				.list()
				.stream()
				.collect(Collectors.toMap(line -> RemittanceAdviceLineId.ofRepoId(line.getC_RemittanceAdvice_Line_ID()), line -> line));

		similar = similar && remittanceAdviceModel.getLines().size() == entityLinesById.size();

		similar = similar && remittanceAdviceModel
				.getLines()
				.stream()
				.allMatch(modelLine -> remittanceAdviceLineModelSimilarToEntity(modelLine, entityLinesById.get(modelLine.getRemittanceAdviceLineId())));

		return similar;
	}

	@SuppressWarnings("ConstantConditions")
	private boolean remittanceAdviceLineModelSimilarToEntity(final RemittanceAdviceLine lineModel, final I_C_RemittanceAdvice_Line lineEntity)
	{
		return lineModel.getOrgId().getRepoId() == lineEntity.getAD_Org_ID()
				&& lineModel.getRemittanceAdviceId().getRepoId() == lineEntity.getC_RemittanceAdvice_ID()
				&& lineModel.getRemittanceAdviceLineId().getRepoId() == lineEntity.getC_RemittanceAdvice_Line_ID()
				&& lineModel.getInvoiceGrossAmount().getAsBigDecimal().compareTo(lineEntity.getInvoiceGrossAmount()) == 0
				&& lineModel.getPaymentDiscountAmount().getAsBigDecimal().compareTo(lineEntity.getPaymentDiscountAmt()) == 0
				&& lineModel.getServiceFeeAmount().getAsBigDecimal().compareTo(lineEntity.getServiceFeeAmount()) == 0
				&& lineModel.getExternalInvoiceDocBaseType().equals(lineEntity.getExternalInvoiceDocBaseType())
				&& lineModel.getBpartnerIdentifier().getRepoId() == lineEntity.getC_BPartner_ID()
				&& lineModel.getInvoiceIdentifier().equals(lineEntity.getInvoiceIdentifier())
				&& lineModel.getInvoiceId().getRepoId() == lineEntity.getC_Invoice_ID()
				&& lineModel.getServiceFeeVatRate().compareTo(lineEntity.getServiceFeeVatRate()) == 0
				&& lineModel.getInvoiceAmt().compareTo(lineEntity.getInvoiceAmt()) == 0
				&& lineModel.getInvoiceAmtInREMADVCurrency().getAsBigDecimal().compareTo(lineEntity.getInvoiceAmtInREMADVCurrency()) == 0
				&& lineModel.getOverUnderAmtInREMADVCurrency().getAsBigDecimal().compareTo(lineEntity.getOverUnderAmt()) == 0
				&& lineModel.getInvoiceCurrencyId().getRepoId() == lineEntity.getC_Invoice_Currency_ID()
				&& lineModel.getBillBPartnerId().getRepoId() == lineEntity.getBill_BPartner_ID()
				&& lineModel.getDateInvoiced().equals(lineEntity.getInvoiceDate().toInstant())
				&& lineModel.getServiceFeeInvoiceId().getRepoId() == lineEntity.getService_Fee_Invoice_ID()
				&& lineModel.getServiceFeeBPartnerId().getRepoId() == lineEntity.getService_BPartner_ID()
				&& lineModel.getServiceFeeProductId().getRepoId() == lineEntity.getService_Product_ID()
				&& lineModel.getTaxId().getRepoId() == lineEntity.getService_Tax_ID()
				&& lineModel.isBPartnerValid() == lineEntity.isBPartnerValid()
				&& lineModel.isInvoiceDateValid() == lineEntity.isInvoiceDateValid()
				&& lineModel.isInvoiceResolved() == lineEntity.isInvoiceResolved()
				&& lineModel.isAmountValid() == lineEntity.isAmountValid()
				&& lineModel.isInvoiceDocTypeValid() == lineEntity.isInvoiceDocTypeValid()
				&& lineModel.isServiceFeeResolved() == lineEntity.isServiceColumnsResolved()
				&& lineModel.isProcessed() == lineEntity.isProcessed();
	}

}
