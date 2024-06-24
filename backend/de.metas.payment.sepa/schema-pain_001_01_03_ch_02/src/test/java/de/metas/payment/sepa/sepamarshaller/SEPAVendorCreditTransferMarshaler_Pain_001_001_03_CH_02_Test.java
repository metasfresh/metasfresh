/*
 * #%L
 * de.metas.payment.sepa.schema.pain_001_01_03_ch_02
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.payment.sepa.sepamarshaller;

import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationProblem;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02_Test
{

	public void test_UserFile()
	{
		//use to test your own files
		testXmlSchema("/pain.001.001.03_sample.xml");
	}

	public void test_sepaTest()
	{
		testXmlSchema("/sepa_test_xml.xml");
	}

	public void testXmlSchema(@NonNull final String inputXmlFileName)
	{
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream);
	}

	private InputStream createInputStream(@NonNull final String resourceName)
	{
		final InputStream xmlInput = this.getClass().getResourceAsStream(resourceName);
		Assertions.assertThat(xmlInput).as("Unable to load resource %s", resourceName).isNotNull();

		return xmlInput;
	}

	private void assertXmlIsValid(@NonNull final InputStream inputStream)
	{
		final StreamSource xsdInvoice = new StreamSource(getClass().getResourceAsStream("/pain.001.001.03.ch.02.xsd"));

		final Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSources(xsdInvoice); // the ordering is important for the validator to load them successfully

		final ValidationResult r = v.validateInstance(new StreamSource(inputStream));

		final List<ValidationProblem> problems = new ArrayList<>();
		r.getProblems().iterator().forEachRemaining(problems::add);
		Assertions.assertThat(problems).isEmpty();
	}

}
