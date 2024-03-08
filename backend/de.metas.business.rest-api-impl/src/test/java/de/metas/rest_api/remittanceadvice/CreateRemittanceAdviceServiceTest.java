/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.remittanceadvice;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceRequest;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceResponse;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdvice;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdviceLine;
import de.metas.common.rest_api.v1.remittanceadvice.RemittanceAdviceType;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.rest_api.remittanceadvice.impl.CreateRemittanceAdviceService;
import de.metas.util.JSONObjectMapper;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class CreateRemittanceAdviceServiceTest
{
	private static final String ORG_VALUE = "orgCode";
	private static final String SENDER_ID = "senderId";
	private static final String RECIPIENT_ID = "recipientId";
	private static final String CURRENCY_CODE_EUR = "EUR";
	private static final String CURRENCY_CODE_CHF = "CHF";
	public static final String TARGET_IBAN = "012345678901234";
	public static final String SEND_DATE = "2019-11-22T00:00:00Z";
	public static final String DOCUMENT_DATE = "2020-11-22T00:00:00Z";
	public static final String DOCUMENT_NB = "001";
	public static final String CREATED_DOCUMENT_NB = "testDocNo";
	public static final String ADDITIONAL_NOTES = "test notes";

	public static final String INVOICE_BASE_DOCTYPE = "PAY";
	public static final String INVOICE_IDENTIFIER = "PAY";
	public static final String DATE_INVOICE_LINE = "2019-11-22T00:00:00Z";
	public static final int REMITTED_AMOUNT = 30;
	public static final int INVOICE_GROSS_AMOUNT = 30;
	public static final int PAYMENT_DISCOUNT_AMOUNT = 30;
	public static final int SERVICE_FEE_AMOUNT = 30;
	public static final int SERVICE_FEE_VAT_RATE = 3;

	private I_C_BP_Group groupRecord;
	private I_C_BPartner senderBPRecord;
	private I_C_BPartner destinationBPRecord;
	private I_C_BP_BankAccount senderBPBankAccountRecord;
	private I_C_BP_BankAccount destinationBPBankAccountRecord;
	private I_C_DocType docTypeARR;
	private I_C_DocType docTypeAPP;
	private I_C_DocType docTypeRMADV;
	private CurrencyId currencyIdEUR;
	private CurrencyId currencyIdCHF;

	private CreateRemittanceAdviceService createRemittanceAdviceService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final RemittanceAdviceRepository remittanceAdviceRepository = new RemittanceAdviceRepository();

		final CurrencyCode convertedToCurrencyCodeEUR = CurrencyCode.ofThreeLetterCode(CURRENCY_CODE_EUR);
		currencyIdEUR = currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCodeEUR);

		final CurrencyCode convertedToCurrencyCodeCHF = CurrencyCode.ofThreeLetterCode(CURRENCY_CODE_CHF);
		currencyIdCHF = currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCodeCHF);

		final CountryId countryId_DE = BusinessTestHelper.createCountry("DE");

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(countryId_DE.getRepoId());
		saveRecord(locationRecord);

		groupRecord = newInstance(I_C_BP_Group.class);
		groupRecord.setName(ORG_VALUE + "-name");
		saveRecord(groupRecord);

		senderBPRecord = newInstance(I_C_BPartner.class);
		senderBPRecord.setValue(SENDER_ID);
		senderBPRecord.setName(SENDER_ID + "-name");
		senderBPRecord.setC_BP_Group_ID(groupRecord.getC_BP_Group_ID());
		saveRecord(senderBPRecord);

		destinationBPRecord = newInstance(I_C_BPartner.class);
		destinationBPRecord.setValue(RECIPIENT_ID);
		destinationBPRecord.setName(RECIPIENT_ID + "-name");
		destinationBPRecord.setC_BP_Group_ID(groupRecord.getC_BP_Group_ID());
		saveRecord(destinationBPRecord);

		final I_C_BPartner_Location senderBPLocationRecord = newInstance(I_C_BPartner_Location.class);
		senderBPLocationRecord.setC_BPartner_ID(senderBPRecord.getC_BPartner_ID());
		senderBPLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		senderBPLocationRecord.setGLN(SENDER_ID);
		saveRecord(senderBPLocationRecord);

		final I_C_BPartner_Location destinationBPLocationRecord = newInstance(I_C_BPartner_Location.class);
		destinationBPLocationRecord.setC_BPartner_ID(destinationBPRecord.getC_BPartner_ID());
		destinationBPLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		destinationBPLocationRecord.setGLN(RECIPIENT_ID);
		saveRecord(destinationBPLocationRecord);

		senderBPBankAccountRecord = newInstance(I_C_BP_BankAccount.class);
		senderBPBankAccountRecord.setIBAN(TARGET_IBAN);
		senderBPBankAccountRecord.setC_BPartner_ID(senderBPRecord.getC_BPartner_ID());
		saveRecord(senderBPBankAccountRecord);

		destinationBPBankAccountRecord = newInstance(I_C_BP_BankAccount.class);
		destinationBPBankAccountRecord.setIBAN(TARGET_IBAN);
		destinationBPBankAccountRecord.setC_BPartner_ID(destinationBPRecord.getC_BPartner_ID());
		saveRecord(destinationBPBankAccountRecord);

		docTypeARR = newInstance(I_C_DocType.class);
		docTypeARR.setDocBaseType(X_C_DocType.DOCBASETYPE_ARReceipt);
		save(docTypeARR);

		docTypeAPP = newInstance(I_C_DocType.class);
		docTypeAPP.setDocBaseType(X_C_DocType.DOCBASETYPE_APPayment);
		save(docTypeAPP);

		docTypeRMADV = newInstance(I_C_DocType.class);
		docTypeRMADV.setDocBaseType(X_C_DocType.DOCBASETYPE_RemittanceAdvice);
		save(docTypeRMADV);

		createRemittanceAdviceService = Mockito.spy(new CreateRemittanceAdviceService(currencyRepository, remittanceAdviceRepository));
		Mockito.doReturn(CREATED_DOCUMENT_NB).when(createRemittanceAdviceService).buildDocumentNo(Mockito.any(DocTypeId.class));
	}

	@Test
	void createRemittanceAdviceRequestWithTwoLinesAndNoServiceFee()
	{

		final IDocumentNoBuilderFactory documentNoBuilderFactory = new DocumentNoBuilderFactory(Optional.empty());
		Services.registerService(IDocumentNoBuilderFactory.class, documentNoBuilderFactory);

		final ImmutableList.Builder<JsonRemittanceAdviceLine> lines = ImmutableList.builder();

		for (int count = 0; count < 2; count++)
		{
			final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
					.invoiceIdentifier(INVOICE_IDENTIFIER + count)
					.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
					.bpartnerIdentifier("gln-" + SENDER_ID)
					.dateInvoiced(DATE_INVOICE_LINE)
					.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
					.invoiceGrossAmount(BigDecimal.valueOf(INVOICE_GROSS_AMOUNT))
					.paymentDiscountAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
					.serviceFeeAmount(BigDecimal.valueOf(0))
					.serviceFeeVatRate(BigDecimal.valueOf(0))
					.build();

			lines.add(line);
		}

		final JsonRemittanceAdvice jsonRemittanceAdvice = createJsonRemittanceAdviceBuilder()
				.orgCode(ORG_VALUE)
				.senderId("gln-" + SENDER_ID)
				.recipientId("gln-" + RECIPIENT_ID)
				.documentNumber(DOCUMENT_NB)
				.documentDate(DOCUMENT_DATE)
				.sendDate(SEND_DATE)
				.type(RemittanceAdviceType.INBOUND)
				.remittedAmountSum(BigDecimal.valueOf(REMITTED_AMOUNT))
				.remittanceAmountCurrencyISO(CURRENCY_CODE_EUR)
				.serviceFeeAmt(BigDecimal.valueOf(0))
				.serviceFeeCurrencyISO(null)
				.paymentDiscountAmountSum(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
				.additionalNotes(ADDITIONAL_NOTES)
				.lines(lines.build())
				.build();

		final ImmutableList.Builder<JsonRemittanceAdvice> jsonRemittanceAdviceBuilderLines = ImmutableList.builder();
		jsonRemittanceAdviceBuilderLines.add(jsonRemittanceAdvice);

		final JsonCreateRemittanceAdviceRequest request = createJsonCreateRemittanceAdviceRequestBuilder()
				.remittanceAdviceList(jsonRemittanceAdviceBuilderLines.build())
				.build();
		JSONObjectMapper.forClass(JsonCreateRemittanceAdviceRequest.class).writeValueAsString(request);

		// invoke the method under test
		final JsonCreateRemittanceAdviceResponse response = createRemittanceAdviceService.createRemittanceAdviceList(request);

		final JsonMetasfreshId remittanceAdviceId = response.getIds().get(0).getRemittanceAdviceId();

		final List<I_C_RemittanceAdvice> records = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice.class);
		assertThat(records).hasSize(1);

		final I_C_RemittanceAdvice c_remittanceAdvice = records.get(0);
		assertThat(c_remittanceAdvice.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(c_remittanceAdvice.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(c_remittanceAdvice.getAD_Client_ID()).isEqualTo(groupRecord.getAD_Client_ID());

		assertThat(c_remittanceAdvice.getSource_BPartner_ID()).isEqualTo(senderBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice.getSource_BP_BankAccount_ID()).isEqualTo(senderBPBankAccountRecord.getC_BP_BankAccount_ID());
		assertThat(c_remittanceAdvice.getDestintion_BPartner_ID()).isEqualTo(destinationBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice.getDestination_BP_BankAccount_ID()).isEqualTo(destinationBPBankAccountRecord.getC_BP_BankAccount_ID());

		assertThat(c_remittanceAdvice.getDocumentNo()).isEqualTo(CREATED_DOCUMENT_NB);
		assertThat(c_remittanceAdvice.getExternalDocumentNo()).isEqualTo(DOCUMENT_NB);
		assertThat(TimeUtil.asInstant(c_remittanceAdvice.getDateDoc())).isEqualTo(Instant.parse(DOCUMENT_DATE));
		assertThat(TimeUtil.asInstant(c_remittanceAdvice.getSendAt())).isEqualTo(Instant.parse(SEND_DATE));

		assertThat(c_remittanceAdvice.getC_DocType_ID()).isEqualTo(docTypeRMADV.getC_DocType_ID());
		assertThat(c_remittanceAdvice.getC_Payment_Doctype_Target_ID()).isEqualTo(docTypeARR.getC_DocType_ID());

		assertThat(c_remittanceAdvice.getRemittanceAmt_Currency_ID()).isEqualTo(currencyIdEUR.getRepoId());
		assertThat(c_remittanceAdvice.getServiceFeeAmount_Currency_ID()).isEqualTo(-1);
		assertThat(c_remittanceAdvice.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(0));
		assertThat(c_remittanceAdvice.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(c_remittanceAdvice.getPaymentDiscountAmountSum()).isEqualTo(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT));

		assertThat(c_remittanceAdvice.getAdditionalNotes()).isEqualTo(ADDITIONAL_NOTES);
		assertThat(c_remittanceAdvice.isI_IsImported()).isEqualTo(true);

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords).hasSize(2);

		final I_C_RemittanceAdvice_Line remittanceAdvice_line0 = lineRecords.get(0);
		assertThat(remittanceAdvice_line0.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(remittanceAdvice_line0.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(remittanceAdvice_line0.getC_BPartner_ID()).isEqualTo(senderBPRecord.getC_BPartner_ID());
		assertThat(remittanceAdvice_line0.getInvoiceIdentifier()).isEqualTo(INVOICE_IDENTIFIER + "0");
		assertThat(remittanceAdvice_line0.getExternalInvoiceDocBaseType()).isEqualTo(INVOICE_BASE_DOCTYPE);

		assertThat(remittanceAdvice_line0.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(remittanceAdvice_line0.getInvoiceGrossAmount()).isEqualTo(BigDecimal.valueOf(INVOICE_GROSS_AMOUNT));
		assertThat(remittanceAdvice_line0.getPaymentDiscountAmt()).isEqualTo(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT));
		assertThat(remittanceAdvice_line0.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(0));
		assertThat(remittanceAdvice_line0.getServiceFeeVatRate()).isEqualTo(BigDecimal.valueOf(0));

		final I_C_RemittanceAdvice_Line remittanceAdvice_line1 = lineRecords.get(1);
		assertThat(remittanceAdvice_line1.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(remittanceAdvice_line1.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(remittanceAdvice_line1.getC_BPartner_ID()).isEqualTo(senderBPRecord.getC_BPartner_ID());
		assertThat(remittanceAdvice_line1.getInvoiceIdentifier()).isEqualTo(INVOICE_IDENTIFIER + "1");
		assertThat(remittanceAdvice_line1.getExternalInvoiceDocBaseType()).isEqualTo(INVOICE_BASE_DOCTYPE);

		assertThat(remittanceAdvice_line1.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(remittanceAdvice_line1.getInvoiceGrossAmount()).isEqualTo(BigDecimal.valueOf(INVOICE_GROSS_AMOUNT));
		assertThat(remittanceAdvice_line1.getPaymentDiscountAmt()).isEqualTo(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT));
		assertThat(remittanceAdvice_line1.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(0));
		assertThat(remittanceAdvice_line1.getServiceFeeVatRate()).isEqualTo(BigDecimal.valueOf(0));
	}

	@Test
	void createRemittanceAdviceRequestWithServiceFeeAndDifferentISOCurrency()
	{
		final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
				.invoiceIdentifier(INVOICE_IDENTIFIER)
				.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
				.bpartnerIdentifier("gln-" + SENDER_ID)
				.dateInvoiced(DATE_INVOICE_LINE)
				.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
				.invoiceGrossAmount(BigDecimal.valueOf(INVOICE_GROSS_AMOUNT))
				.paymentDiscountAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
				.serviceFeeAmount(BigDecimal.valueOf(SERVICE_FEE_AMOUNT))
				.serviceFeeVatRate(BigDecimal.valueOf(SERVICE_FEE_VAT_RATE))
				.build();

		final ImmutableList.Builder<JsonRemittanceAdviceLine> lines = ImmutableList.builder();
		lines.add(line);

		final JsonRemittanceAdvice jsonRemittanceAdvice = createJsonRemittanceAdviceBuilder()
				.orgCode(ORG_VALUE)
				.senderId("gln-" + SENDER_ID)
				.recipientId("gln-" + RECIPIENT_ID)
				.documentNumber(DOCUMENT_NB)
				.documentDate(DOCUMENT_DATE)
				.sendDate(null)
				.type(RemittanceAdviceType.INBOUND)
				.remittedAmountSum(BigDecimal.valueOf(REMITTED_AMOUNT))
				.remittanceAmountCurrencyISO(CURRENCY_CODE_EUR)
				.serviceFeeAmt(BigDecimal.valueOf(SERVICE_FEE_AMOUNT))
				.serviceFeeCurrencyISO(CURRENCY_CODE_CHF)
				.paymentDiscountAmountSum(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
				.additionalNotes(ADDITIONAL_NOTES)
				.lines(lines.build())
				.build();

		final ImmutableList.Builder<JsonRemittanceAdvice> jsonRemittanceAdviceBuilderLines = ImmutableList.builder();
		jsonRemittanceAdviceBuilderLines.add(jsonRemittanceAdvice);

		final JsonCreateRemittanceAdviceRequest request = createJsonCreateRemittanceAdviceRequestBuilder()
				.remittanceAdviceList(jsonRemittanceAdviceBuilderLines.build())
				.build();
		JSONObjectMapper.forClass(JsonCreateRemittanceAdviceRequest.class).writeValueAsString(request);

		// invoke the method under test
		final JsonCreateRemittanceAdviceResponse response = createRemittanceAdviceService.createRemittanceAdviceList(request);

		final JsonMetasfreshId remittanceAdviceId = response.getIds().get(0).getRemittanceAdviceId();

		final List<I_C_RemittanceAdvice> records = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice.class);
		assertThat(records).hasSize(1);

		final I_C_RemittanceAdvice c_remittanceAdvice = records.get(0);
		assertThat(c_remittanceAdvice.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(c_remittanceAdvice.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(c_remittanceAdvice.getAD_Client_ID()).isEqualTo(groupRecord.getAD_Client_ID());

		assertThat(c_remittanceAdvice.getSource_BPartner_ID()).isEqualTo(senderBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice.getSource_BP_BankAccount_ID()).isEqualTo(senderBPBankAccountRecord.getC_BP_BankAccount_ID());
		assertThat(c_remittanceAdvice.getDestintion_BPartner_ID()).isEqualTo(destinationBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice.getDestination_BP_BankAccount_ID()).isEqualTo(destinationBPBankAccountRecord.getC_BP_BankAccount_ID());

		assertThat(c_remittanceAdvice.getDocumentNo()).isEqualTo(CREATED_DOCUMENT_NB);
		assertThat(c_remittanceAdvice.getExternalDocumentNo()).isEqualTo(DOCUMENT_NB);
		assertThat(TimeUtil.asInstant(c_remittanceAdvice.getDateDoc())).isEqualTo(Instant.parse(DOCUMENT_DATE));
		assertThat(c_remittanceAdvice.getSendAt()).isNull();

		assertThat(c_remittanceAdvice.getC_DocType_ID()).isEqualTo(docTypeRMADV.getC_DocType_ID());
		assertThat(c_remittanceAdvice.getC_Payment_Doctype_Target_ID()).isEqualTo(docTypeARR.getC_DocType_ID());

		assertThat(c_remittanceAdvice.getRemittanceAmt_Currency_ID()).isEqualTo(currencyIdEUR.getRepoId());
		assertThat(c_remittanceAdvice.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_AMOUNT));
		assertThat(c_remittanceAdvice.getServiceFeeAmount_Currency_ID()).isEqualTo(currencyIdCHF.getRepoId());
		assertThat(c_remittanceAdvice.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(c_remittanceAdvice.getPaymentDiscountAmountSum()).isEqualTo(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT));

		assertThat(c_remittanceAdvice.getAdditionalNotes()).isEqualTo(ADDITIONAL_NOTES);
		assertThat(c_remittanceAdvice.isI_IsImported()).isEqualTo(true);

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords).hasSize(1);

		final I_C_RemittanceAdvice_Line c_remittanceAdvice_line = lineRecords.get(0);
		assertThat(c_remittanceAdvice_line.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(c_remittanceAdvice_line.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(c_remittanceAdvice_line.getC_BPartner_ID()).isEqualTo(senderBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice_line.getInvoiceIdentifier()).isEqualTo(INVOICE_IDENTIFIER);
		assertThat(c_remittanceAdvice_line.getExternalInvoiceDocBaseType()).isEqualTo(INVOICE_BASE_DOCTYPE);
		assertThat(TimeUtil.asInstant(c_remittanceAdvice_line.getInvoiceDate())).isEqualTo(Instant.parse(DATE_INVOICE_LINE));

		assertThat(c_remittanceAdvice_line.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(c_remittanceAdvice_line.getInvoiceGrossAmount()).isEqualTo(BigDecimal.valueOf(INVOICE_GROSS_AMOUNT));
		assertThat(c_remittanceAdvice_line.getPaymentDiscountAmt()).isEqualTo(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT));
		assertThat(c_remittanceAdvice_line.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_AMOUNT));
		assertThat(c_remittanceAdvice_line.getServiceFeeVatRate()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_VAT_RATE));
	}

	@Test
	void createRemittanceAdviceRequestOutboundType()
	{
		SystemTime.setFixedTimeSource(LocalDate.parse("2023-12-05").atTime(1,2).atZone(ZoneId.of("UTC-8")));

		final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
				.invoiceIdentifier(INVOICE_IDENTIFIER)
				.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
				.bpartnerIdentifier("gln-" + RECIPIENT_ID)
				.dateInvoiced(DATE_INVOICE_LINE)
				.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
				.invoiceGrossAmount(BigDecimal.valueOf(0))
				.paymentDiscountAmount(BigDecimal.valueOf(0))
				.serviceFeeAmount(BigDecimal.valueOf(SERVICE_FEE_AMOUNT))
				.serviceFeeVatRate(BigDecimal.valueOf(SERVICE_FEE_VAT_RATE))
				.build();

		final ImmutableList.Builder<JsonRemittanceAdviceLine> lines = ImmutableList.builder();
		lines.add(line);

		final JsonRemittanceAdvice jsonRemittanceAdvice = createJsonRemittanceAdviceBuilder()
				.orgCode(ORG_VALUE)
				.senderId("gln-" + SENDER_ID)
				.recipientId("gln-" + RECIPIENT_ID)
				.documentNumber(DOCUMENT_NB)
				.documentDate(null) // not providing a date. shall be set to the then-current time
				.sendDate(SEND_DATE)
				.type(RemittanceAdviceType.OUTBOUND)
				.remittedAmountSum(BigDecimal.valueOf(REMITTED_AMOUNT))
				.remittanceAmountCurrencyISO(CURRENCY_CODE_EUR)
				.serviceFeeAmt(BigDecimal.valueOf(SERVICE_FEE_AMOUNT))
				.serviceFeeCurrencyISO(CURRENCY_CODE_EUR)
				.paymentDiscountAmountSum(BigDecimal.valueOf(0))
				.additionalNotes(ADDITIONAL_NOTES)
				.lines(lines.build())
				.build();

		final ImmutableList.Builder<JsonRemittanceAdvice> jsonRemittanceAdviceBuilderLines = ImmutableList.builder();
		jsonRemittanceAdviceBuilderLines.add(jsonRemittanceAdvice);

		final JsonCreateRemittanceAdviceRequest request = createJsonCreateRemittanceAdviceRequestBuilder()
				.remittanceAdviceList(jsonRemittanceAdviceBuilderLines.build())
				.build();
		JSONObjectMapper.forClass(JsonCreateRemittanceAdviceRequest.class).writeValueAsString(request);

		// invoke the method under test
		final JsonCreateRemittanceAdviceResponse response = createRemittanceAdviceService.createRemittanceAdviceList(request);

		final JsonMetasfreshId remittanceAdviceId = response.getIds().get(0).getRemittanceAdviceId();

		final List<I_C_RemittanceAdvice> records = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice.class);
		assertThat(records).hasSize(1);

		final I_C_RemittanceAdvice c_remittanceAdvice = records.get(0);
		assertThat(c_remittanceAdvice.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(c_remittanceAdvice.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(c_remittanceAdvice.getAD_Client_ID()).isEqualTo(groupRecord.getAD_Client_ID());

		assertThat(c_remittanceAdvice.getSource_BPartner_ID()).isEqualTo(senderBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice.getSource_BP_BankAccount_ID()).isEqualTo(senderBPBankAccountRecord.getC_BP_BankAccount_ID());
		assertThat(c_remittanceAdvice.getDestintion_BPartner_ID()).isEqualTo(destinationBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice.getDestination_BP_BankAccount_ID()).isEqualTo(destinationBPBankAccountRecord.getC_BP_BankAccount_ID());

		assertThat(c_remittanceAdvice.getDocumentNo()).isEqualTo(CREATED_DOCUMENT_NB);
		assertThat(c_remittanceAdvice.getExternalDocumentNo()).isEqualTo(DOCUMENT_NB);
		assertThat(TimeUtil.asInstant(c_remittanceAdvice.getDateDoc())).as("DateDoc").isEqualTo(SystemTime.asInstant());
		assertThat(TimeUtil.asInstant(c_remittanceAdvice.getSendAt())).isEqualTo(Instant.parse(SEND_DATE));

		assertThat(c_remittanceAdvice.getC_DocType_ID()).isEqualTo(docTypeRMADV.getC_DocType_ID());
		assertThat(c_remittanceAdvice.getC_Payment_Doctype_Target_ID()).isEqualTo(docTypeAPP.getC_DocType_ID());

		assertThat(c_remittanceAdvice.getRemittanceAmt_Currency_ID()).isEqualTo(currencyIdEUR.getRepoId());
		assertThat(c_remittanceAdvice.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_AMOUNT));
		assertThat(c_remittanceAdvice.getServiceFeeAmount_Currency_ID()).isEqualTo(currencyIdEUR.getRepoId());
		assertThat(c_remittanceAdvice.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(c_remittanceAdvice.getPaymentDiscountAmountSum()).isEqualTo(BigDecimal.valueOf(0));

		assertThat(c_remittanceAdvice.getAdditionalNotes()).isEqualTo(ADDITIONAL_NOTES);
		assertThat(c_remittanceAdvice.isI_IsImported()).isEqualTo(true);

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords).hasSize(1);

		final I_C_RemittanceAdvice_Line c_remittanceAdvice_line = lineRecords.get(0);
		assertThat(c_remittanceAdvice_line.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(c_remittanceAdvice_line.getAD_Org_ID()).isEqualTo(groupRecord.getAD_Org_ID());
		assertThat(c_remittanceAdvice_line.getC_BPartner_ID()).isEqualTo(destinationBPRecord.getC_BPartner_ID());
		assertThat(c_remittanceAdvice_line.getInvoiceIdentifier()).isEqualTo(INVOICE_IDENTIFIER);
		assertThat(c_remittanceAdvice_line.getExternalInvoiceDocBaseType()).isEqualTo(INVOICE_BASE_DOCTYPE);
		assertThat(TimeUtil.asInstant(c_remittanceAdvice_line.getInvoiceDate())).isEqualTo(Instant.parse(DATE_INVOICE_LINE));

		assertThat(c_remittanceAdvice_line.getRemittanceAmt()).isEqualTo(BigDecimal.valueOf(REMITTED_AMOUNT));
		assertThat(c_remittanceAdvice_line.getInvoiceGrossAmount()).isEqualTo(BigDecimal.valueOf(0));
		assertThat(c_remittanceAdvice_line.getPaymentDiscountAmt()).isEqualTo(BigDecimal.valueOf(0));
		assertThat(c_remittanceAdvice_line.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_AMOUNT));
		assertThat(c_remittanceAdvice_line.getServiceFeeVatRate()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_VAT_RATE));
	}

	@Builder(builderMethodName = "createJsonRemittanceAdviceLineBuilder", builderClassName = "JsonRemittanceAdviceLineBuilder")
	private JsonRemittanceAdviceLine createJsonRemittanceAdviceLine(
			@NonNull final String invoiceIdentifier,
			@NonNull final BigDecimal remittedAmount,
			final String bpartnerIdentifier,
			final String dateInvoiced,
			final String invoiceBaseDocType,
			final BigDecimal invoiceGrossAmount,
			final BigDecimal paymentDiscountAmount,
			final BigDecimal serviceFeeAmount,
			final BigDecimal serviceFeeVatRate
	)
	{
		final JsonRemittanceAdviceLine jsonRemittanceAdviceLine = JsonRemittanceAdviceLine.builder()
				.bpartnerIdentifier(bpartnerIdentifier)
				.dateInvoiced(dateInvoiced)
				.invoiceBaseDocType(invoiceBaseDocType)
				.invoiceIdentifier(invoiceIdentifier)
				.invoiceGrossAmount(invoiceGrossAmount)
				.remittedAmount(remittedAmount)
				.paymentDiscountAmount(paymentDiscountAmount)
				.serviceFeeAmount(serviceFeeAmount)
				.serviceFeeVatRate(serviceFeeVatRate)
				.build();

		return jsonRemittanceAdviceLine;
	}

	@Builder(builderMethodName = "createJsonRemittanceAdviceBuilder", builderClassName = "JsonRemittanceAdviceBuilder")
	private JsonRemittanceAdvice createJsonRemittanceAdvice(
			final String orgCode,
			@NonNull final String senderId,
			@NonNull final String recipientId,
			final String documentNumber,
			final String documentDate,
			final String sendDate,
			@NonNull final RemittanceAdviceType type,
			@NonNull final BigDecimal remittedAmountSum,
			@NonNull final String remittanceAmountCurrencyISO,
			final BigDecimal serviceFeeAmt,
			final String serviceFeeCurrencyISO,
			final BigDecimal paymentDiscountAmountSum,
			final String additionalNotes,
			final List<JsonRemittanceAdviceLine> lines
	)
	{
		final JsonRemittanceAdvice jsonRemittanceAdvice = JsonRemittanceAdvice.builder()
				.orgCode(orgCode)
				.senderId(senderId)
				.recipientId(recipientId)
				.documentNumber(documentNumber)
				.sendDate(sendDate)
				.documentDate(documentDate)
				.remittanceAdviceType(type)
				.remittedAmountSum(remittedAmountSum)
				.remittanceAmountCurrencyISO(remittanceAmountCurrencyISO)
				.serviceFeeAmount(serviceFeeAmt)
				.serviceFeeCurrencyISO(serviceFeeCurrencyISO)
				.paymentDiscountAmountSum(paymentDiscountAmountSum)
				.additionalNotes(additionalNotes)
				.lines(lines)
				.build();

		return jsonRemittanceAdvice;
	}

	@Builder(builderMethodName = "createJsonCreateRemittanceAdviceRequestBuilder",
			builderClassName = "JsonCreateRemittanceAdviceRequestBuilder")
	private JsonCreateRemittanceAdviceRequest createJsonCreateRemittanceAdviceRequest(
			@NonNull final List<JsonRemittanceAdvice> remittanceAdviceList
	)
	{
		final JsonCreateRemittanceAdviceRequest request = JsonCreateRemittanceAdviceRequest.builder()
				.remittanceAdviceList(remittanceAdviceList)
				.build();

		return request;
	}
}
