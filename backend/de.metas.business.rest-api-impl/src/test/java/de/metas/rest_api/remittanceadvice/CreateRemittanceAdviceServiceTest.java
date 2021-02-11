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
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.remittanceadvice.JsonCreateRemittanceAdviceRequest;
import de.metas.common.rest_api.remittanceadvice.JsonCreateRemittanceAdviceResponse;
import de.metas.common.rest_api.remittanceadvice.JsonRemittanceAdvice;
import de.metas.common.rest_api.remittanceadvice.JsonRemittanceAdviceLine;
import de.metas.common.rest_api.remittanceadvice.RemittanceAdviceType;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.location.CountryId;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
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
	public static final String TARGET_IBAN = "012345678901234";
	public static final String SEND_DATE = "2019-11-22T00:00:00Z";
	public static final String DOCUMENT_DATE = "2020-11-22T00:00:00Z";
	public static final String DOCUMENT_NB = "001";
	public static final String ADDITIONAL_NOTES = "test notes";

	public static final String INVOICE_BASE_DOCTYPE = "PAY";
	public static final String INVOICE_IDENTIFIER = "PAY";
	public static final String DATE_INVOICE_LINE = "2019-11-22T00:00:00Z";
	public static final int REMITTED_AMOUNT = 30;
	public static final int PAYMENT_DISCOUNT_AMOUNT = 30;
	public static final int SERVICE_FEE_AMOUNT = 30;
	public static final int SERVICE_FEE_VAT_RATE = 30;

	private CreateRemittanceAdviceService createRemittanceAdviceService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final RemittanceAdviceRepository remittanceAdviceRepository = new RemittanceAdviceRepository();

		final CountryId countryId_DE = BusinessTestHelper.createCountry("DE");

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(countryId_DE.getRepoId());
		saveRecord(locationRecord);

		final I_C_BP_Group groupRecord = newInstance(I_C_BP_Group.class);
		groupRecord.setName(ORG_VALUE + "-name");
		saveRecord(groupRecord);

		final I_C_BPartner senderBPRecord = newInstance(I_C_BPartner.class);
		senderBPRecord.setValue(SENDER_ID);
		senderBPRecord.setName(SENDER_ID + "-name");
		senderBPRecord.setC_BP_Group_ID(groupRecord.getC_BP_Group_ID());
		saveRecord(senderBPRecord);

		final I_C_BPartner destinationBPRecord = newInstance(I_C_BPartner.class);
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

		final I_C_BP_BankAccount senderBPBankAccountRecord = newInstance(I_C_BP_BankAccount.class);
		senderBPBankAccountRecord.setIBAN(TARGET_IBAN);
		senderBPBankAccountRecord.setC_BPartner_ID(senderBPRecord.getC_BPartner_ID());
		saveRecord(senderBPBankAccountRecord);

		final I_C_BP_BankAccount destinationBPBankAccountRecord = newInstance(I_C_BP_BankAccount.class);
		destinationBPBankAccountRecord.setIBAN(TARGET_IBAN);
		destinationBPBankAccountRecord.setC_BPartner_ID(destinationBPRecord.getC_BPartner_ID());
		saveRecord(destinationBPBankAccountRecord);

		final I_C_DocType docTypeARR = newInstance(I_C_DocType.class);
		docTypeARR.setDocBaseType(X_C_DocType.DOCBASETYPE_ARReceipt);
		save(docTypeARR);

		final I_C_DocType docTypeAPP = newInstance(I_C_DocType.class);
		docTypeAPP.setDocBaseType(X_C_DocType.DOCBASETYPE_APPayment);
		save(docTypeAPP);

		final I_C_DocType docTypeRMADV = newInstance(I_C_DocType.class);
		docTypeRMADV.setDocBaseType(X_C_DocType.DOCBASETYPE_RemittanceAdvice);
		save(docTypeRMADV);

		createRemittanceAdviceService = Mockito.spy(new CreateRemittanceAdviceService(currencyRepository, remittanceAdviceRepository));
		Mockito.doReturn("testDocNo").when(createRemittanceAdviceService).buildDocumentNo(Mockito.any(DocTypeId.class));
	}

	@Test
	void createRemittanceAdviceRequestWithTwoLines()
	{

		final IDocumentNoBuilderFactory documentNoBuilderFactory = new DocumentNoBuilderFactory(Optional.empty());
		Services.registerService(IDocumentNoBuilderFactory.class, documentNoBuilderFactory);

		final ImmutableList.Builder<JsonRemittanceAdviceLine> lines = ImmutableList.builder();

		for (int count = 1; count <= 2; count++)
		{
			final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
					.invoiceIdentifier(INVOICE_IDENTIFIER + count)
					.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
					.bpartnerIdentifier("gln-" + SENDER_ID)
					.dateInvoiced(DATE_INVOICE_LINE)
					.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
					.invoiceGrossAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
					.paymentDiscountAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
					.serviceFeeAmount(BigDecimal.valueOf(SERVICE_FEE_AMOUNT))
					.serviceFeeVatRate(BigDecimal.valueOf(SERVICE_FEE_VAT_RATE))
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
		assertThat(c_remittanceAdvice.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(0));

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords).hasSize(2);
		assertThat(lineRecords.get(0).getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
		assertThat(lineRecords.get(1).getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
	}

	@Test
	void createRemittanceAdviceRequestWithServiceFee()
	{
		final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
				.invoiceIdentifier(INVOICE_IDENTIFIER)
				.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
				.bpartnerIdentifier("gln-" + SENDER_ID)
				.dateInvoiced(DATE_INVOICE_LINE)
				.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
				.invoiceGrossAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
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
				.serviceFeeCurrencyISO(CURRENCY_CODE_EUR)
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
		assertThat(c_remittanceAdvice.getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(SERVICE_FEE_AMOUNT));

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords).hasSize(1);

		final I_C_RemittanceAdvice_Line c_remittanceAdvice_line = lineRecords.get(0);
		assertThat(c_remittanceAdvice_line.getC_RemittanceAdvice_ID()).isEqualTo(JsonMetasfreshId.toValue(remittanceAdviceId));
	}

	@Test
	void createRemittanceAdviceRequestWithoutServiceFee()
	{
		final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
				.invoiceIdentifier(INVOICE_IDENTIFIER)
				.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
				.bpartnerIdentifier("gln-" + SENDER_ID)
				.dateInvoiced(DATE_INVOICE_LINE)
				.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
				.invoiceGrossAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
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

		final List<I_C_RemittanceAdvice> records = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice.class);
		assertThat(JsonMetasfreshId.of(records.get(0).getC_RemittanceAdvice_ID()).equals(response.getIds().get(0).getRemittanceAdviceId()));
		assertThat(records.get(0).getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(0));

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords.get(0).getC_RemittanceAdvice_ID()).isEqualTo(records.get(0).getC_RemittanceAdvice_ID());
	}

	@Test
	void createRemittanceAdviceRequestOutboundType()
	{
		final JsonRemittanceAdviceLine line = createJsonRemittanceAdviceLineBuilder()
				.invoiceIdentifier(INVOICE_IDENTIFIER)
				.remittedAmount(BigDecimal.valueOf(REMITTED_AMOUNT))
				.bpartnerIdentifier("gln-" + SENDER_ID)
				.dateInvoiced(DATE_INVOICE_LINE)
				.invoiceBaseDocType(INVOICE_BASE_DOCTYPE)
				.invoiceGrossAmount(BigDecimal.valueOf(PAYMENT_DISCOUNT_AMOUNT))
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
				.type(RemittanceAdviceType.OUTBOUND)
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

		final List<I_C_RemittanceAdvice> records = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice.class);
		assertThat(JsonMetasfreshId.of(records.get(0).getC_RemittanceAdvice_ID()).equals(response.getIds().get(0).getRemittanceAdviceId()));
		assertThat(records.get(0).getServiceFeeAmount()).isEqualTo(BigDecimal.valueOf(0));

		final List<I_C_RemittanceAdvice_Line> lineRecords = POJOLookupMap.get().getRecords(I_C_RemittanceAdvice_Line.class);
		assertThat(lineRecords.get(0).getC_RemittanceAdvice_ID()).isEqualTo(records.get(0).getC_RemittanceAdvice_ID());
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
