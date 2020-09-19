/*
 * #%L
 * de-metas-common-shipmentschedule
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.filemaker;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

class XmlSerializeTests
{
	@Test
	void fmpxmlresult_checkFormat() throws IOException
	{
		final METADATA metadata = METADATA.builder()
				.field(FIELD.builder().name("field1").build())
				.field(FIELD.builder().name("field2").build())
				.field(FIELD.builder().name("field3").build())
				.build();

		final RESULTSET resultset = RESULTSET.builder()
				.found(2)
				.row(ROW.builder()
						.col(new COL(new DATA("data11")))
						.col(new COL(new DATA("data12")))
						.build())
				.row(ROW.builder()
						.col(new COL(new DATA("data21")))
						.col(new COL(new DATA(null))) // note: if we would serialized and deserialize, this would not quite be the same, but that's OK for me
						.build())
				.build();

		final FMPXMLRESULT fmpxmlresult = FMPXMLRESULT.builder()
				.errorCode("0")
				.product(new PRODUCT())
				.database(DATABASE.builder()
						.name("databaseName")// TODO set from application.properties
						.records("0")
						.build())
				.metadata(metadata)
				.resultset(resultset)
				.build();

		// when
		final String xmlString = ConfiguredXmlMapper.get().writeValueAsString(fmpxmlresult);

		// then
		assertThat(xmlString).isEqualToIgnoringNewLines("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<FMPXMLRESULT>\n"
				+ "  <ERRORCODE>0</ERRORCODE>\n"
				+ "  <PRODUCT BUILD=\"04-29-2019\" NAME=\"FileMaker\" VERSION=\"ProAdvanced 18.0.1\"/>\n"
				+ "  <DATABASE DATEFORMAT=\"D.m.yyyy\" LAYOUT=\"xml\" NAME=\"databaseName\" RECORDS=\"0\" TIMEFORMAT=\"k:mm:ss \"/>\n"
				+ "  <METADATA>\n"
				+ "    <FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"field1\" TYPE=\"TEXT\"/>\n"
				+ "    <FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"field2\" TYPE=\"TEXT\"/>\n"
				+ "    <FIELD EMPTYOK=\"YES\" MAXREPEAT=\"1\" NAME=\"field3\" TYPE=\"TEXT\"/>\n"
				+ "  </METADATA>\n"
				+ "  <RESULTSET FOUND=\"2\">\n"
				+ "    <ROW>\n"
				+ "      <COL>\n"
				+ "        <DATA>data11</DATA>\n"
				+ "      </COL>\n"
				+ "      <COL>\n"
				+ "        <DATA>data12</DATA>\n"
				+ "      </COL>\n"
				+ "    </ROW>\n"
				+ "    <ROW>\n"
				+ "      <COL>\n"
				+ "        <DATA>data21</DATA>\n"
				+ "      </COL>\n"
				+ "      <COL>\n"
				+ "        <DATA/>\n"
				+ "      </COL>\n"
				+ "    </ROW>\n"
				+ "  </RESULTSET>\n"
				+ "</FMPXMLRESULT>\n");
	}

	@Test
	void fmpxmlresult_checkSerializeDeserialize() throws IOException
	{
		final METADATA metadata = METADATA.builder()
				.field(FIELD.builder().name("field1").build())
				.field(FIELD.builder().name("field2").build())
				.field(FIELD.builder().name("field3").build())
				.build();

		final RESULTSET resultset = RESULTSET.builder()
				.found(2)
				.row(ROW.builder()
						.col(new COL(new DATA("data11")))
						.col(new COL(new DATA("data12")))
						.build())
				.row(ROW.builder()
						.col(new COL(new DATA("data21")))
						.col(new COL(new DATA("data22")))
						.build())
				.build();

		final FMPXMLRESULT fmpxmlresult = FMPXMLRESULT.builder()
				.errorCode("0")
				.product(new PRODUCT())
				.database(DATABASE.builder()
						.name("databaseName")
						.records("0")
						.build())
				.metadata(metadata)
				.resultset(resultset)
				.build();

		assertOK(fmpxmlresult,FMPXMLRESULT.class);
	}

	private <T> void assertOK(final T objectOrig, final Class<T> valueType) throws IOException
	{
		final XmlMapper xmlMapper = ConfiguredXmlMapper.get();

		final String xmlString = xmlMapper.writeValueAsString(objectOrig);
		assertThat(xmlString).isNotEmpty();

		// when
		final T object = xmlMapper.readValue(xmlString, valueType);

		// then
		assertThat(object).isEqualTo(objectOrig);
	}
}