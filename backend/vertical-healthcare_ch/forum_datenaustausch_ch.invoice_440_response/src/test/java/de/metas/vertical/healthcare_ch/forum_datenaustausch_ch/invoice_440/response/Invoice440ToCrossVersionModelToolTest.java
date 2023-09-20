package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.response;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_440.response
 * %%
 * Copyright (C) 2019 metas GmbH
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

class Invoice440ToCrossVersionModelToolTest
{
	private Invoice440ResponseConversionService invoice440ResponseConversionService;

	@BeforeEach void init()
	{
		invoice440ResponseConversionService = new Invoice440ResponseConversionService();
	}

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	void toCrossVersionResponse()
	{
		final XmlResponse result = toCrossVersionResponseWithXmlFile("/Cancelation_KV_12345.xml");
		assertThat(result.getPayload().getInvoice().getRequestId()).isEqualTo("KV_12345"); // sortof smoke-test
		expect(result).toMatchSnapshot();
	}

	@SuppressWarnings({ "SameParameterValue", "UnnecessaryLocalVariable" }) private XmlResponse toCrossVersionResponseWithXmlFile(@NonNull final String inputXmlFileName)
	{
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlResponse xResponse = invoice440ResponseConversionService.toCrossVersionResponse(createInputStream(inputXmlFileName));
		return xResponse;
	}

	private InputStream createInputStream(@NonNull final String resourceName)
	{
		final InputStream xmlInput = this.getClass().getResourceAsStream(resourceName);
		assertThat(xmlInput).as("Unable to load resource %s", resourceName).isNotNull();

		return xmlInput;
	}

	private void assertXmlIsValid(@NonNull final InputStream inputStream)
	{
		final StreamSource xsdInvoice = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/response/generalInvoiceResponse_440.xsd"));
		final StreamSource xsdEnc = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/response/xenc-schema.xsd"));
		final StreamSource xsdSig = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/response/xmldsig-core-schema.xsd"));

		final Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSources(xsdSig, xsdEnc, xsdInvoice); // the ordering is important for the validator to load them successfully

		final ValidationResult r = v.validateInstance(new StreamSource(inputStream));

		Assertions.assertTrue(r.isValid());
	}
}
