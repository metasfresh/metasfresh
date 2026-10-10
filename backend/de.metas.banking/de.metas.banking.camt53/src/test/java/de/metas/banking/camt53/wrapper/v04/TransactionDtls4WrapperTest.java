/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.banking.camt53.wrapper.v04;

import de.metas.banking.camt53.jaxb.camt053_001_04.Document;
import de.metas.banking.camt53.jaxb.camt053_001_04.EntryTransaction4;
import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the null-safety of {@link TransactionDtls4Wrapper}: a transaction with only a creditor (no debtor) and without Refs.
 */
class TransactionDtls4WrapperTest
{
	private static final String XSD_RESOURCE = "/de/metas/banking/camt53/schema/camt_053_001_04/camt.053.001.04.xsd";
	private static final String SAMPLE_RESOURCE = "/camt053_v04_edge_cases.xml";

	private static EntryTransaction4 loadTransaction() throws Exception
	{
		final URL xsd = TransactionDtls4WrapperTest.class.getResource(XSD_RESOURCE);
		assertThat(xsd).as("bundled XSD").isNotNull();
		final Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xsd);

		final Unmarshaller unmarshaller = JAXBContext.newInstance(Document.class).createUnmarshaller();
		unmarshaller.setSchema(schema); // fails if the sample does not conform to the XSD

		try (final InputStream in = TransactionDtls4WrapperTest.class.getResourceAsStream(SAMPLE_RESOURCE))
		{
			assertThat(in).as("sample xml").isNotNull();
			final Document document = unmarshaller.unmarshal(new StreamSource(in), Document.class).getValue();
			return document.getBkToCstmrStmt().getStmt().get(0).getNtry().get(0).getNtryDtls().get(0).getTxDtls().get(0);
		}
	}

	@Test
	void getCdtrNames_returnsCreditorName_whenThereIsNoDebtor() throws Exception
	{
		final TransactionDtls4Wrapper wrapper = TransactionDtls4Wrapper.builder().entryDtls(loadTransaction()).build();

		assertThat(wrapper.getCdtrNames()).isEqualTo("Only Creditor AG");
		assertThat(wrapper.getDbtrNames()).isNull();
	}

	@Test
	void getAcctSvcrRef_isNull_whenThereAreNoRefs() throws Exception
	{
		final TransactionDtls4Wrapper wrapper = TransactionDtls4Wrapper.builder().entryDtls(loadTransaction()).build();

		assertThat(wrapper.getAcctSvcrRef()).isNull();
	}
}
