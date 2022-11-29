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
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static de.metas.invoice.InvoiceDocBaseType.CustomerInvoice;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class RemittanceAdviceServiceTest
{
	private RemittanceAdviceService remittanceAdviceService;
	private int nextRemittanceLineId = 1;
	private int nextInvoiceId = 10;
	private int nextRemittanceId = 100;
	private BPartnerId bpartnerId;
	private Map<InvoiceDocBaseType, I_C_DocType> invoiceDocTypes;
	private CurrencyId euroCurrencyId;
	private CurrencyId chfCurrencyId;

	@Before
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		bpartnerId = createBPartnerId();
		invoiceDocTypes = new HashMap<>();

		SpringContextHolder.registerJUnitBean(RemittanceAdviceRepository.class, new RemittanceAdviceRepository());
		final RemittanceAdviceRepository remittanceAdviceRepository = SpringContextHolder.instance.getBean(RemittanceAdviceRepository.class);

		SpringContextHolder.registerJUnitBean(MoneyService.class, new MoneyService(new CurrencyRepository()));
		final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

		remittanceAdviceService = new RemittanceAdviceService(moneyService);

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chfCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
	}

	/**
	 * multiplyRate for EURO is set; multiplyRate for CHF is derived
	 * <p>
	 * example:
	 * 1 euro = 10 chf and
	 * 1 chf  = 0.1 euro (1/10)
	 */
	void createConversionRates(final double multiplyRate)
	{
		final LocalDate date = LocalDate.now();

		final CurrencyConversionTypeId conversionType = Services.get(ICurrencyDAO.class).getConversionTypeId(ConversionTypeMethod.Spot);

		final I_C_Conversion_Rate euroChfRate = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
		euroChfRate.setC_ConversionType_ID(conversionType.getRepoId());
		euroChfRate.setC_Currency_ID(euroCurrencyId.getRepoId());
		euroChfRate.setC_Currency_ID_To(chfCurrencyId.getRepoId());
		euroChfRate.setMultiplyRate(BigDecimal.valueOf(multiplyRate));
		euroChfRate.setValidFrom(TimeUtil.asTimestamp(date.minusYears(5))); // using +- 5 years because there are different dates used when creating the Invoices and Payments
		euroChfRate.setValidTo(TimeUtil.asTimestamp(date.plusYears(5)));
		InterfaceWrapperHelper.save(euroChfRate);

		final I_C_Conversion_Rate chfEuroRate = InterfaceWrapperHelper.newInstance(I_C_Conversion_Rate.class);
		chfEuroRate.setC_ConversionType_ID(conversionType.getRepoId());
		chfEuroRate.setC_Currency_ID(chfCurrencyId.getRepoId());
		chfEuroRate.setC_Currency_ID_To(euroCurrencyId.getRepoId());
		chfEuroRate.setMultiplyRate(BigDecimal.ONE.divide(BigDecimal.valueOf(multiplyRate), 13, RoundingMode.HALF_UP));
		chfEuroRate.setValidFrom(TimeUtil.asTimestamp(date.minusYears(5)));
		chfEuroRate.setValidTo(TimeUtil.asTimestamp(date.plusYears(5)));
		InterfaceWrapperHelper.save(chfEuroRate);
	}

	@Builder(builderMethodName = "remittanceLine")
	private RemittanceAdviceLine getRemittanceLine(
			@Nullable final String invoiceIdentifier,
			final int invoiceId,
			@NonNull final BigDecimal remittanceAmt,
			@Nullable final CurrencyCode currencyCode,
			@Nullable final BPartnerId bPartnerId,
			@Nullable final LocalDate targetInvoicedDate
	)
	{
		final RemittanceAdviceLine remittanceAdviceLine;

		remittanceAdviceLine = RemittanceAdviceLine.builder()
				.orgId(OrgId.ofRepoId(100))
				.bpartnerIdentifier(bpartnerId)
				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(nextRemittanceLineId++))//
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(nextRemittanceId++))//
				.invoiceIdentifier(invoiceIdentifier)//
				.invoiceId(InvoiceId.ofRepoIdOrNull(invoiceId))//
				.dateInvoiced(targetInvoicedDate != null ? TimeUtil.asInstant(targetInvoicedDate) : null)
				.remittedAmount(Amount.of(remittanceAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))//
				.build();

		return remittanceAdviceLine;
	}

	@Builder(builderMethodName = "remittance")
	private RemittanceAdvice getRemittance(@Nullable final CurrencyId remittedCurrencyId)
	{
		final RemittanceAdvice remittanceAdvice;

		remittanceAdvice = RemittanceAdvice.builder()
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(123))
				.orgId(OrgId.ofRepoId(123))
				.clientId(ClientId.ofRepoId(123))
				.sourceBPartnerId(BPartnerId.ofRepoId(111))
				.remittedAmountCurrencyId(remittedCurrencyId != null ? remittedCurrencyId : euroCurrencyId)
				.sourceBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(BPartnerId.ofRepoId(111), 123))
				.destinationBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(BPartnerId.ofRepoId(123), 111))
				.destinationBPartnerId(bpartnerId)
				.documentNumber("123")
				.externalDocumentNumber("ext-123")
				.documentDate(Instant.now())
				.docStatus("CO")
				.docTypeId(DocTypeId.ofRepoId(1))
				.remittedAmountSum(new BigDecimal(100))
				.lines(ImmutableList.of())
				.build();

		return remittanceAdvice;
	}

	@Builder(builderMethodName = "invoice")
	private I_C_Invoice getInvoice(
			final InvoiceDocBaseType type,
			@NonNull final BigDecimal open,
			@Nullable final CurrencyId currency,
			@Nullable final LocalDate invoicedDate)
	{
		final Money openAmt = money(open, currency);

		final I_C_Invoice invoice;

		final Money invoiceGrandTotal = openAmt
				.negateIf(type.isCreditMemo())
				.negateIf(!type.isSales());

		final LocalDate invoicedDateToUse = invoicedDate != null
				? invoicedDate
				: LocalDate.now();

		final int invoiceId = nextInvoiceId++;
		final I_C_DocType docType = getInvoiceDocType(type);
		invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Invoice_ID(invoiceId);
		invoice.setDocumentNo("InvoiceDocNo" + invoiceId);
		invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		invoice.setIsSOTrx(docType.isSOTrx());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(invoicedDateToUse));
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_Currency_ID(invoiceGrandTotal.getCurrencyId().getRepoId());
		invoice.setGrandTotal(invoiceGrandTotal.toBigDecimal());
		invoice.setProcessed(true);
		invoice.setDocStatus(IDocument.STATUS_Completed);
		invoice.setDateAcct(TimeUtil.asTimestamp(invoicedDateToUse));
		InterfaceWrapperHelper.saveRecord(invoice);

		return invoice;
	}

	private Money money(@NonNull final BigDecimal amount, @Nullable CurrencyId currencyId)
	{
		currencyId = currencyId == null ? euroCurrencyId : currencyId;
		return Money.of(amount, currencyId);
	}

	private BPartnerId createBPartnerId()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	private I_C_DocType getInvoiceDocType(final InvoiceDocBaseType InvoiceDocBaseType)
	{
		return invoiceDocTypes.computeIfAbsent(InvoiceDocBaseType, this::createInvoiceDocType);
	}

	private I_C_DocType createInvoiceDocType(final InvoiceDocBaseType invoiceDocBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(invoiceDocBaseType.getCode());
		docType.setName(invoiceDocBaseType.toString());
		docType.setIsSOTrx(invoiceDocBaseType.isSales());
		InterfaceWrapperHelper.save(docType);
		return docType;
	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyMatchingAmount()
	{
		//given
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open(BigDecimal.valueOf(100)).currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(100)).build();

		//when
		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		//then
		assertThat(remittanceAdviceLine.getInvoiceAmt()).isNotNull();
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isNotNull();

		assertThat(remittanceAdviceLine.getInvoiceAmt()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isEqualTo(Amount.zero(CurrencyCode.EUR));

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyOverAmount()
	{
		//given
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open(BigDecimal.valueOf(100)).currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine()
				.invoiceId(invoice.getC_Invoice_ID())
				.remittanceAmt(new BigDecimal(120))
				.build();

		//when
		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		//then
		assertThat(remittanceAdviceLine.getInvoiceAmt()).isNotNull();
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isNotNull();

		assertThat(remittanceAdviceLine.getInvoiceAmt()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isEqualTo(Amount.of(BigDecimal.valueOf(20), CurrencyCode.EUR));
	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyUnderAmount()
	{
		//given
		final I_C_Invoice invoice = invoice()
				.type(CustomerInvoice)
				.open(BigDecimal.valueOf(100))
				.currency(euroCurrencyId)
				.build();

		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine()
				.invoiceId(invoice.getC_Invoice_ID())
				.remittanceAmt(new BigDecimal(80)).build();

		//when
		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		//then
		assertThat(remittanceAdviceLine.getInvoiceAmt()).isNotNull();
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isNotNull();

		assertThat(remittanceAdviceLine.getInvoiceAmt()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isEqualTo(Amount.of(BigDecimal.valueOf(-20), CurrencyCode.EUR));
	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceOtherCurrencyUnderOverAmountMatch()
	{
		//given
		createConversionRates(2);

		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open(BigDecimal.valueOf(100)).currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().remittedCurrencyId(chfCurrencyId).build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine()
				.currencyCode(CurrencyCode.CHF)
				.invoiceId(invoice.getC_Invoice_ID())
				.remittanceAmt(new BigDecimal(200))
				.build();

		//when
		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		//then
		assertThat(remittanceAdviceLine.getInvoiceAmt()).isNotNull();
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isNotNull();

		assertThat(remittanceAdviceLine.getInvoiceAmt()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isEqualTo(Amount.zero(CurrencyCode.CHF));

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceOtherCurrencyUnderAmount()
	{
		//given
		createConversionRates(2);

		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open(BigDecimal.valueOf(100)).currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().remittedCurrencyId(chfCurrencyId).build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine()
				.currencyCode(CurrencyCode.CHF)
				.invoiceId(invoice.getC_Invoice_ID())
				.remittanceAmt(new BigDecimal(100))
				.build();

		//when
		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		//then
		assertThat(remittanceAdviceLine.getInvoiceAmt()).isNotNull();
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isNotNull();

		assertThat(remittanceAdviceLine.getInvoiceAmt()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isEqualTo(Amount.of(BigDecimal.valueOf(-100), CurrencyCode.CHF));

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyMatchingAmount_CheckBooleans()
	{
		//given
		final LocalDate invoicedDate = LocalDate.now();

		final I_C_Invoice invoice = invoice()
				.invoicedDate(invoicedDate)
				.type(CustomerInvoice)
				.open(BigDecimal.valueOf(100))
				.currency(euroCurrencyId)
				.build();

		final RemittanceAdvice remittanceAdvice = remittance().build();

		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine()
				.targetInvoicedDate(invoicedDate)
				.invoiceId(invoice.getC_Invoice_ID())
				.bPartnerId(bpartnerId)
				.remittanceAmt(new BigDecimal(100))
				.build();

		//when
		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		//then
		assertThat(remittanceAdviceLine.getInvoiceAmt()).isNotNull();
		assertThat(remittanceAdviceLine.getOverUnderAmtInREMADVCurrency()).isNotNull();
		assertThat(remittanceAdviceLine.isInvoiceResolved()).isTrue();
		assertThat(remittanceAdviceLine.isAmountValid()).isTrue();
		assertThat(remittanceAdviceLine.isInvoiceDateValid()).isTrue();
		assertThat(remittanceAdviceLine.isServiceFeeResolved()).isFalse();
		assertThat(remittanceAdviceLine.isInvoiceDocTypeValid()).isFalse();
	}

}
