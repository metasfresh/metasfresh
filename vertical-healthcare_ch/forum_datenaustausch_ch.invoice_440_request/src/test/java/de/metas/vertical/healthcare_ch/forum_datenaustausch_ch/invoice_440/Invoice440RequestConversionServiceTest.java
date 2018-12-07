package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.model.XmlRequest;
import lombok.NonNull;

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

public class Invoice440RequestConversionServiceTest
{

	private Invoice440RequestConversionService invoice440RequestConversionService;

	@Before
	public void init()
	{
		invoice440RequestConversionService = new Invoice440RequestConversionService();
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
	public void exampleFile_440_tp_mvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tp_mvg_de.xml");
	}

	@Test
	public void exampleFile_440_tp_uvg_de()
	{
		testWithPublicExampleXmlFile("md_440_tp_uvg_de.xml");
	}

	private void testWithPublicExampleXmlFile(final String inputXmlFileName)
	{
		testWithXmlFile("/public_examples/" + inputXmlFileName);
	}

	@Test
	public void ownFile_ATT_1000001()
	{
		// jot
		// reference_number="00 00000 00000 00050 82100 15051"
		// schlecht
		// reference_number="00 00000 21002 15589 50100 00016"

		testWithXmlFile("/ATT_1000001.xml");
	}

	@Test
	public void ownFile_Input_1000000()
	{
		testWithXmlFile("/Input_1000000.xml");
	}

	private void testWithXmlFile(final String inputXmlFileName)
	{
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlRequest xRequest = invoice440RequestConversionService.toCrossVersionRequest(createInputStream(inputXmlFileName));

		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoice440RequestConversionService.fromCrossVersionRequest(xRequest, outputStream);

		assertXmlIsValid(new ByteArrayInputStream(outputStream.toByteArray()));
	}

	private void assertXmlIsValid(@NonNull final InputStream inputStream)
	{
		final StreamSource xsdInvoice = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/request/generalInvoiceRequest_440.xsd"));
		final StreamSource xsdEnc = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/request/xenc-schema.xsd"));
		final StreamSource xsdSig = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/request/xmldsig-core-schema.xsd"));

		final Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSources(xsdSig, xsdEnc, xsdInvoice); // the ordering is important for the validator to load them successfully

		final ValidationResult r = v.validateInstance(new StreamSource(inputStream));

		assertThat(r.getProblems()).isEmpty();
	}

	private InputStream createInputStream(final String resourceName)
	{
		final InputStream xmlInput = this.getClass().getResourceAsStream(resourceName);
		return xmlInput;
	}
}
