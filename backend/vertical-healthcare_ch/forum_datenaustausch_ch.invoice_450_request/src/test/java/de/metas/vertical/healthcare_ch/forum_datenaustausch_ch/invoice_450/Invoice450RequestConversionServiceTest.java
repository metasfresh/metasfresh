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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPBankAcctUse;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.ProcessingMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.RequestMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsr9;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.esr.XmlEsrQR;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.TransportMod;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.SnapshotHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.annotation.Nullable;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.xmlunit.assertj.XmlAssert.assertThat;

public class Invoice450RequestConversionServiceTest
{

	private Invoice450RequestConversionService invoice450RequestConversionService;

	@BeforeClass
	public static void initStatic()
	{
		start(SnapshotHelper.SNAPSHOT_CONFIG, SnapshotHelper::toArrayAwareString);
	}

	@AfterClass
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Before
	public void init()
	{
		invoice450RequestConversionService = new Invoice450RequestConversionService();
		invoice450RequestConversionService.setUsePrettyPrint(true);
		AdempiereTestHelper.get().init();
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

		expect(exportXmlString).toMatchSnapshot();
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
	public void exampleFile_Abrechnung_Praktischer_Arzt_TARMED_450_with_augment()
	{
		final int bPartnerId = createBPartnerTestSetup();

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
	public void exampleFile_Abrechnung_Praktischer_Arzt_TARMED_450_with_augment_no_Bank()
	{
		final int bPartnerId = createBPartnerTestSetup();

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

	private int createBPartnerTestSetup()
	{
		final int bPartnerId = 10;

		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_BPartner_ID(bPartnerId);
		bankAccount.setQR_IBAN("CH0930769016110591261");
		bankAccount.setC_Currency_ID(123);
		bankAccount.setBPBankAcctUse(BPBankAcctUse.DEPOSIT.getCode());
		bankAccount.setIBAN("123");
		InterfaceWrapperHelper.save(bankAccount);

		final I_C_BPartner bPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bPartner.setC_BPartner_ID(bPartnerId);
		InterfaceWrapperHelper.save(bPartner);
		return bPartnerId;
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

		Assert.assertTrue(r.isValid());
	}
}
