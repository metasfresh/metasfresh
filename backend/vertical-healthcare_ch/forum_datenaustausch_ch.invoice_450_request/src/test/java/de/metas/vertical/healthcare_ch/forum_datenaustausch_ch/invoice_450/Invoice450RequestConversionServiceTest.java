/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_450.request
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

package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPBankAcctUse;
import de.metas.greeting.GreetingRepository;
import de.metas.location.LocationId;
import de.metas.location.LocationRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlCompany;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.commontypes.XmlPostal;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.ProcessingMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.RequestMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlEsr;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlAddress;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrQR;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.TransportMod;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.annotation.Nullable;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.xmlunit.assertj.XmlAssert.assertThat;

@Disabled("waiting for the fix of: Server returned HTTP response code: 429 for URL: https://www.w3.org/2001/XMLSchema.dtd")
@ExtendWith(SnapshotExtension.class)
public class Invoice450RequestConversionServiceTest
{

	public static final String BANK_NAME = "Test Bank";
	public static final String BANK_COUNTRY_CODE = "DE";
	public static final String BANK_CITY = "Bonn";
	public static final String BANK_POSTAL = "12345";
	private Invoice450RequestConversionService invoice450RequestConversionService;

	private static final int bPartnerId = 10;
	private static final int bpBankAccountId = 20;

	private Expect expect;

	@BeforeAll
	public static void initStatic()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@BeforeEach
	public void init()
	{
		invoice450RequestConversionService = new Invoice450RequestConversionService();
		invoice450RequestConversionService.setUsePrettyPrint(true);
		SpringContextHolder.registerJUnitBean(LocationRepository.class, new LocationRepository());
		SpringContextHolder.registerJUnitBean(BankRepository.class, new BankRepository());
		SpringContextHolder.registerJUnitBean(new GreetingRepository());
	}

	@Test
	public void exampleFile_streha_invoice_03()
	{
		testWithPublicExampleXmlFile("streha_invoice_03.xml");
	}

	@Test
	public void exampleFile_Abrechnung_Praktischer_Arzt_TARMED_450()
	{
		testWithPublicExampleXmlFile("Abrechnung Praktischer Arzt TARMED 450.xml");
	}

	@Test
	public void exampleFile_streha_invoice_03_mod_test_additionalVia()
	{
		final String inputXmlFileName = "/public_examples/streha_invoice_03.xml";
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice450RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));
		assertThat(xRequest.getModus()).isEqualTo(XmlMode.PRODUCTION); // guard
		assertThat(xRequest.getProcessing().getTransport().getVias().size()).isEqualTo(1); // guard

		final XmlRequest withMod = xRequest
				.withMod(RequestMod.builder()
						.modus(XmlMode.TEST)
						.processingMod(ProcessingMod.builder()
								.transportMod(TransportMod.builder()
										.from("1234567890123")
										.additionalViaEAN("2234567890123")
										.build())
								.build())
						.build());

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoice450RequestConversionService.fromCrossVersionRequest(withMod, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
		assertOutputMatchesSnapshot(outputStream);
	}

	private void assertOutputMatchesSnapshot(final ByteArrayOutputStream outputStream)
	{

		final String exportXmlString = outputStream.toString();

		expect.serializer("orderedJson").toMatchSnapshot(exportXmlString);
	}

	@Test
	public void exampleFile_streha_invoice_03_mod_test_replaceVia()
	{
		final String inputXmlFileName = "/public_examples/streha_invoice_03.xml";
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice450RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));
		assertThat(xRequest.getModus()).isEqualTo(XmlMode.PRODUCTION); // guard
		assertThat(xRequest.getProcessing().getTransport().getVias().size()).isEqualTo(1); // guard

		final XmlRequest withMod = xRequest
				.withMod(RequestMod.builder()
						.modus(XmlMode.TEST)
						.processingMod(ProcessingMod.builder()
								.transportMod(TransportMod.builder()
										.from("1234567890123")
										.replacementViaEAN("2234567890123")
										.build())
								.build())
						.build());

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoice450RequestConversionService.fromCrossVersionRequest(withMod, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
		assertOutputMatchesSnapshot(outputStream);
	}

	@Test
	public void exampleFile_Abrechnung_Praktischer_Arzt_TARMED_450_with_augment_BankFromXML()
	{
		createBPartnerTestSetup(false);
		final String inputXmlFileName = "/public_examples/Abrechnung Praktischer Arzt TARMED 450.xml";
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice450RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));

		assertThat(xRequest.getPayload().getBody().getEsr()).isOfAnyClassIn(XmlEsr9.class);

		final XmlRequest withMod = invoice450RequestConversionService.augmentRequest(xRequest, BPartnerId.ofRepoId(bPartnerId));

		assertThat(withMod.getPayload().getBody().getEsr()).isOfAnyClassIn(XmlEsrQR.class);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		invoice450RequestConversionService.fromCrossVersionRequest(withMod, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
		assertOutputMatchesSnapshot(outputStream);
	}

	@Test
	public void exampleFile_Abrechnung_Praktischer_Arzt_TARMED_450_with_augment_no_BankInDB()
	{
		createBPartnerTestSetup(false);
		final String inputXmlFileName = "/public_examples/Abrechnung Praktischer Arzt TARMED 450_noEsrBank.xml";
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice450RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));

		assertThat(xRequest.getPayload().getBody().getEsr()).isOfAnyClassIn(XmlEsr9.class);
		assertThat(((XmlEsr9)xRequest.getPayload().getBody().getEsr()).getBank()).isNull();

		final XmlRequest withMod = invoice450RequestConversionService.augmentRequest(xRequest, BPartnerId.ofRepoId(bPartnerId));

		assertThat(withMod.getPayload().getBody().getEsr()).isOfAnyClassIn(XmlEsr9.class);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		invoice450RequestConversionService.fromCrossVersionRequest(withMod, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
		assertOutputMatchesSnapshot(outputStream);
	}

	@Test
	public void exampleFile_Abrechnung_Praktischer_Arzt_TARMED_450_with_augment_with_BankInDB()
	{
		createBPartnerTestSetup(true);
		final String inputXmlFileName = "/public_examples/Abrechnung Praktischer Arzt TARMED 450_noEsrBank.xml";
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice450RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));

		assertThat(xRequest.getPayload().getBody().getEsr()).isOfAnyClassIn(XmlEsr9.class);
		assertThat(((XmlEsr9)xRequest.getPayload().getBody().getEsr()).getBank()).isNull();

		final XmlRequest withMod = invoice450RequestConversionService.augmentRequest(xRequest, BPartnerId.ofRepoId(bPartnerId));

		final XmlEsr esr = withMod.getPayload().getBody().getEsr();
		assertThat(esr).isOfAnyClassIn(XmlEsrQR.class);

		final XmlAddress bank = ((XmlEsrQR)esr).getBank();
		assertThat(bank).isNotNull();

		final XmlCompany company = bank.getCompany();
		assertThat(company.getCompanyname()).isEqualTo(BANK_NAME);

		final XmlPostal postal = company.getPostal();
		assertThat(postal.getCity()).isEqualTo(BANK_CITY);
		assertThat(postal.getCountryCode()).isEqualTo(BANK_COUNTRY_CODE);
		assertThat(postal.getZip()).isEqualTo(BANK_POSTAL);

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoice450RequestConversionService.fromCrossVersionRequest(withMod, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
		assertOutputMatchesSnapshot(outputStream);
	}

	private void createBPartnerTestSetup(final boolean createBankDetails)
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bPartner.setC_BPartner_ID(bPartnerId);
		InterfaceWrapperHelper.save(bPartner);

		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_BP_BankAccount_ID(bpBankAccountId);
		bankAccount.setC_BPartner_ID(bPartnerId);
		bankAccount.setQR_IBAN("CH0930769016110591261");
		bankAccount.setC_Currency_ID(123);
		bankAccount.setBPBankAcctUse(BPBankAcctUse.DEPOSIT.getCode());
		bankAccount.setIBAN("123");
		bankAccount.setSwiftCode("123");
		if (createBankDetails)
		{
			final BankId bankId = addBankRecord();
			bankAccount.setC_Bank_ID(bankId.getRepoId());
		}
		InterfaceWrapperHelper.save(bankAccount);
	}

	private BankId addBankRecord()
	{
		final LocationId bankLocation = createBankLocation();
		final I_C_Bank bank = InterfaceWrapperHelper.newInstance(I_C_Bank.class);
		bank.setC_Location_ID(bankLocation.getRepoId());
		bank.setName(BANK_NAME);
		InterfaceWrapperHelper.save(bank);
		return BankId.ofRepoId(bank.getC_Bank_ID());
	}

	private LocationId createBankLocation()
	{
		final I_C_Country country = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		country.setCountryCode(BANK_COUNTRY_CODE);
		country.setC_Country_ID(101);
		InterfaceWrapperHelper.save(country);

		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		location.setCity(BANK_CITY);
		location.setPostal(BANK_POSTAL);
		location.setC_Country_ID(101);
		InterfaceWrapperHelper.save(location);
		return LocationId.ofRepoId(location.getC_Location_ID());
	}

	private void testWithPublicExampleXmlFile(@NonNull final String inputXmlFileName)
	{
		testWithXmlFile("/public_examples/" + inputXmlFileName);
	}

	private void testWithXmlFile(@NonNull final String inputXmlFileName)
	{
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice450RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoice450RequestConversionService.fromCrossVersionRequest(xRequest, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
	}

	@Nullable
	private InputStream createInputStream(@NonNull final String resourceName)
	{
		return getClass().getResourceAsStream(resourceName);
	}

	private void assertXmlIsValid(@NonNull final InputStream inputStream)
	{
		final StreamSource xsdInvoice = new StreamSource(createInputStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_450/request/generalInvoiceRequest_450.xsd"));
		final StreamSource xsdEnc = new StreamSource(createInputStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_450/request/xenc-schema.xsd"));
		final StreamSource xsdSig = new StreamSource(createInputStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_450/request/xmldsig-core-schema.xsd"));

		final Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSources(xsdSig, xsdEnc, xsdInvoice); // the ordering is important for the validator to load them successfully

		final ValidationResult r = v.validateInstance(new StreamSource(inputStream));

		Assertions.assertTrue(r.isValid());
	}
}
