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
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static de.metas.invoice.InvoiceDocBaseType.CustomerInvoice;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class RemittanceAdviceTest
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

		remittanceAdviceService = new RemittanceAdviceService(moneyService, remittanceAdviceRepository);

		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chfCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

	}

	@Builder(builderMethodName = "remittanceLine")
	private RemittanceAdviceLine getRemittanceLine(
			@Nullable final String invoiceIdentifier,
			final int invoiceId,
			@NonNull final BigDecimal remittanceAmt,
			@Nullable final CurrencyCode currencyCode
	)
	{
		final RemittanceAdviceLine remittanceAdviceLine;

		remittanceAdviceLine = RemittanceAdviceLine.builder()
				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(nextRemittanceLineId++))//
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(nextRemittanceId++))//
				.invoiceIdentifier(invoiceIdentifier)//
				.invoiceId(InvoiceId.ofRepoIdOrNull(invoiceId))//
				.remittedAmount(Amount.of(remittanceAmt, currencyCode != null ? currencyCode : CurrencyCode.EUR))//
				.build();

		return remittanceAdviceLine;
	}

	@Builder(builderMethodName = "remittance")
	private RemittanceAdvice getRemittance()
	{
		final RemittanceAdvice remittanceAdvice;

		remittanceAdvice = RemittanceAdvice.builder()
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(123))
				.orgId(OrgId.ofRepoId(123))
				.clientId(ClientId.ofRepoId(123))
				.sourceBPartnerId(BPartnerId.ofRepoId(111))
				.documentDate(Instant.now())
				.remittedAmountCurrencyId(euroCurrencyId)
				.sourceBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(BPartnerId.ofRepoId(111), 123))
				.destinationBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(BPartnerId.ofRepoId(123), 111))
				.destinationBPartnerId(bpartnerId)
				.documentNumber("123")
				.documentDate(Instant.now())
				.docStatus("CO")
				.docTypeId(DocTypeId.ofRepoId(1))
				.remittedAmountSum(new BigDecimal(100))
				.build();

		return remittanceAdvice;
	}

	@Builder(builderMethodName = "invoice")
	private I_C_Invoice getInvoice(
			final InvoiceDocBaseType type,
			final String open,
			@Nullable final CurrencyId currency)
	{
		final Money openAmt = money(open, currency);

		final LocalDate acctDate = LocalDate.parse("2020-09-04");

		final I_C_Invoice invoice;

		final Money invoiceGrandTotal = openAmt
				.negateIf(type.isCreditMemo())
				.negateIf(!type.isSales());

		final int invoiceId = nextInvoiceId++;
		final I_C_DocType docType = getInvoiceDocType(type);
		invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Invoice_ID(invoiceId);
		invoice.setDocumentNo("InvoiceDocNo" + invoiceId);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setIsSOTrx(docType.isSOTrx());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(acctDate));
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_Currency_ID(invoiceGrandTotal.getCurrencyId().getRepoId());
		invoice.setGrandTotal(invoiceGrandTotal.toBigDecimal());
		invoice.setProcessed(true);
		invoice.setDocStatus(IDocument.STATUS_Completed);
		invoice.setDateAcct(TimeUtil.asTimestamp(acctDate));
		InterfaceWrapperHelper.saveRecord(invoice);

		return invoice;
	}

	private Money money(final String amount, @Nullable CurrencyId currencyId)
	{
		final BigDecimal amountBD = Check.isNotBlank(amount) ? new BigDecimal(amount) : BigDecimal.ZERO;
		currencyId = currencyId == null ? euroCurrencyId : currencyId;
		return Money.of(amountBD, currencyId);
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
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(100)).build();

		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		assertThat(remittanceAdviceLine.getInvoiceAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.getOverUnderAmt() != null).isTrue();

		assertThat(remittanceAdviceLine.getInvoiceAmt().compareTo(new BigDecimal(100))).isEqualByComparingTo(0);
		assertThat(remittanceAdviceLine.getOverUnderAmt().compareTo(new BigDecimal(100))).isEqualTo(0);

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyOverAmount()
	{
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(120)).build();

		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		assertThat(remittanceAdviceLine.getInvoiceAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.getOverUnderAmt() != null).isTrue();

		assertThat(remittanceAdviceLine.getInvoiceAmt().compareTo(new BigDecimal(100))).isEqualByComparingTo(0);
		assertThat(remittanceAdviceLine.getOverUnderAmt().compareTo(new BigDecimal(100))).isEqualTo(-1);

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyUnderAmount()
	{
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(80)).build();

		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		assertThat(remittanceAdviceLine.getInvoiceAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.getOverUnderAmt() != null).isTrue();

		assertThat(remittanceAdviceLine.getInvoiceAmt().compareTo(new BigDecimal(100))).isEqualByComparingTo(0);
		assertThat(remittanceAdviceLine.getOverUnderAmt().compareTo(new BigDecimal(100))).isEqualTo(1);

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceOtherCurrencyOverAmount()
	{
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().currencyCode(CurrencyCode.CHF).invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(100)).build();

		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		assertThat(remittanceAdviceLine.getInvoiceAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.getOverUnderAmt() != null).isTrue();

		assertThat(remittanceAdviceLine.getInvoiceAmt().compareTo(new BigDecimal(100))).isEqualByComparingTo(0);
		assertThat(remittanceAdviceLine.getOverUnderAmt().compareTo(new BigDecimal(100))).isEqualTo(-1);

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceOtherCurrencyUnderAmount()
	{
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().currencyCode(CurrencyCode.CHF).invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(1000)).build();

		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		assertThat(remittanceAdviceLine.getInvoiceAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.getOverUnderAmt() != null).isTrue();

		assertThat(remittanceAdviceLine.getInvoiceAmt().compareTo(new BigDecimal(100))).isEqualByComparingTo(0);
		assertThat(remittanceAdviceLine.getOverUnderAmt().compareTo(new BigDecimal(100))).isEqualTo(1);

	}

	@Test
	public void testResolveRemittanceAdviceLineWithInvoiceSameCurrencyMatchingAmount_CheckBooleans()
	{
		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();
		final RemittanceAdvice remittanceAdvice = remittance().build();
		final RemittanceAdviceLine remittanceAdviceLine = remittanceLine().invoiceId(invoice.getC_Invoice_ID()).remittanceAmt(new BigDecimal(100)).build();

		remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);

		assertThat(remittanceAdviceLine.getInvoiceAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.getOverUnderAmt() != null).isTrue();
		assertThat(remittanceAdviceLine.isInvoiceResolved()).isTrue();
		assertThat(remittanceAdviceLine.isAmountValid()).isTrue();
		assertThat(remittanceAdviceLine.isInvoiceDateValid()).isTrue();
		assertThat(remittanceAdviceLine.isBPartnerValid()).isTrue();
		assertThat(remittanceAdviceLine.isServiceFeeResolved()).isFalse();
		assertThat(remittanceAdviceLine.isInvoiceDocTypeValid()).isFalse();

	}

}
