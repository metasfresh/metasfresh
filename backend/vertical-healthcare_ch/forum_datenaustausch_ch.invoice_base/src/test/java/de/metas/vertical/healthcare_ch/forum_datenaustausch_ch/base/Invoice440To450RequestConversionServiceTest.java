package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base;

import de.metas.banking.api.BankRepository;
import de.metas.greeting.GreetingRepository;
import de.metas.location.LocationRepository;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.Invoice440RequestConversionService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.Invoice450RequestConversionService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlProcessing.ProcessingMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest.RequestMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.processing.XmlTransport.TransportMod;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.SnapshotHelper;
import org.compiere.SpringContextHolder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.xmlunit.assertj.XmlAssert.assertThat;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_440.request
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class Invoice440To450RequestConversionServiceTest
{

	private Invoice440RequestConversionService invoice440RequestConversionService;
	private Invoice450RequestConversionService invoice450RequestConversionService;

	@BeforeClass
	public static void initStatic()
	{
		start(SnapshotHelper.SNAPSHOT_CONFIG, SnapshotHelper::toArrayAwareString);
		AdempiereTestHelper.get().staticInit();
	}

	@AfterClass
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Before
	public void init()
	{
		invoice440RequestConversionService = new Invoice440RequestConversionService();
		invoice450RequestConversionService = new Invoice450RequestConversionService();
		SpringContextHolder.registerJUnitBean(LocationRepository.class, new LocationRepository());
		SpringContextHolder.registerJUnitBean(BankRepository.class, new BankRepository());
		SpringContextHolder.registerJUnitBean(new GreetingRepository());
	}

	@Test
	/**
	 * Test if the conversion from Invoice440 to Invoice450 works as expected, when invoice:document is present.
	 */
	public void exampleFile_44_KANTON_49_01_2019_115414041()
	{
		testWithPublicExampleXmlFile("/44_KANTON_49-01-2019_115414041.xml");
	}

	@Test
	/**
	 * When invoice:document is present but an unsuported mime type is present, the conversion should fail.
	 */
	public void exampleFile_44_KANTON_49_01_2019_115414041_csv_attached()
	{
		Assertions.assertThrows(RuntimeException.class ,() -> testWithPublicExampleXmlFile("/44_KANTON_49-01-2019_115414041_csv_attached.xml"));
	}

	@Test
	public void exampleFile_440_tg_ivg_de()
	{
		testWithPublicExampleXmlFile("md_440_tg_ivg_de.xml");
	}

	@Test
	public void exampleFile_440_tg_kvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tg_kvg_de.xml");
	}

	@Test
	public void exampleFile_440_tg_mvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tg_mvg_de.xml");
	}

	@Test
	public void exampleFile_440_tg_uvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tg_uvg_de.xml");
	}

	@Test
	public void exampleFile_440_tp_ivg_de()
	{
		testWithPublicExampleXmlFile("md_440_tp_ivg_de.xml");
	}

	@Test
	public void exampleFile_440_tp_kvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tp_kvg_de.xml");
	}

	@Test
	public void exampleFile_440_tp_kvg_de_mod_test_additionalVia()
	{
		final InputStream inputStream = createInputStream("/public_examples/md_440_tp_kvg_de.xml");
		assertXmlIsValid440(inputStream); // guard

		final XmlRequest xRequest = invoice440RequestConversionService.toCrossVersionRequest(createInputStream("/public_examples/md_440_tp_kvg_de.xml"));
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

		assertXmlIsValid450(new ByteArrayInputStream(outputStream.toByteArray()));
		final String exportXmlString = outputStream.toString();

		expect(exportXmlString).toMatchSnapshot();
	}

	@Test
	public void exampleFile_440_tp_kvg_de_mod_test_replaceVia()
	{
		final InputStream inputStream = createInputStream("/public_examples/md_440_tp_kvg_de.xml");
		assertXmlIsValid440(inputStream); // guard

		final XmlRequest xRequest = invoice440RequestConversionService.toCrossVersionRequest(createInputStream("/public_examples/md_440_tp_kvg_de.xml"));
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

		assertXmlIsValid450(new ByteArrayInputStream(outputStream.toByteArray()));
		final String exportXmlString = outputStream.toString();
		// System.out.println(exportXmlString);

		expect(exportXmlString).toMatchSnapshot();
	}

	@Test
	public void exampleFile_440_tp_mvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tp_mvg_de.xml");
	}

	@Test
	public void exampleFile_440_tp_uvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tp_uvg_de.xml");
	}

	private void testWithPublicExampleXmlFile(@NonNull final String inputXmlFileName)
	{
		testWithXmlFile("/public_examples/" + inputXmlFileName);
	}

	private void testWithXmlFile(@NonNull final String inputXmlFileName)
	{
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid440(inputStream); // guard

		final XmlRequest xRequest = invoice440RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoice450RequestConversionService.fromCrossVersionRequest(xRequest, outputStream);

		final String exportXmlString = outputStream.toString();
		// System.out.println(exportXmlString);
		assertXmlIsValid450(new ByteArrayInputStream(outputStream.toByteArray()));

		expect(exportXmlString).toMatchSnapshot();
	}

	private InputStream createInputStream(@NonNull final String resourceName)
	{
		return getClass().getResourceAsStream(resourceName);
	}

	private void assertXmlIsValid440(@NonNull final InputStream inputStream)
	{
		final StreamSource xsdInvoice = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/request/generalInvoiceRequest_440.xsd"));
		final StreamSource xsdEnc = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/request/xenc-schema.xsd"));
		final StreamSource xsdSig = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/request/xmldsig-core-schema.xsd"));

		final Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSources(xsdSig, xsdEnc, xsdInvoice); // the ordering is important for the validator to load them successfully

		final ValidationResult r = v.validateInstance(new StreamSource(inputStream));

		Assert.assertTrue(r.isValid());
	}

	private void assertXmlIsValid450(@NonNull final InputStream inputStream)
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
