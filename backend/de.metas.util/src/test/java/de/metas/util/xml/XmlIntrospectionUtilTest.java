package de.metas.util.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

/*
 * #%L
 * de.metas.util
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

public class XmlIntrospectionUtilTest
{

	@Test
	public void test() throws XMLStreamException
	{
		final String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" +
				"<invoice:request xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:invoice=\"http://www.forum-datenaustausch.ch/invoice\" xsi:schemaLocation=\"http://www.forum-datenaustausch.ch/invoice generalInvoiceRequest_440.xsd\" language=\"de\" modus=\"production\" validation_status=\"0\">\r\n" +
				"</invoice:request>\r\n" +
				"";
		final InputStream xmlStream = new ByteArrayInputStream(xmlString.getBytes());

		// invoke the method under test
		final String result = XmlIntrospectionUtil.extractXsdValueOrNull(xmlStream);

		assertThat(result).isEqualTo("http://www.forum-datenaustausch.ch/invoice generalInvoiceRequest_440.xsd");
	}
}
