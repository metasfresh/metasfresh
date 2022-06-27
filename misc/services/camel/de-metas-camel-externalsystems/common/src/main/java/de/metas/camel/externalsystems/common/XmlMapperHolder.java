/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class XmlMapperHolder
{
	private static final XmlMapper sharedXmlObjectMapper = XmlMapperHolder.newXmlMapper();

	@NonNull
	public XmlMapper sharedXmlMapper()
	{
		return sharedXmlObjectMapper;
	}

	@NonNull
	public XmlMapper newXmlMapper()
	{
		final XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		xmlMapper.getFactory().getXMLOutputFactory().setProperty(com.ctc.wstx.api.WstxOutputProperties.P_USE_DOUBLE_QUOTES_IN_XML_DECL, true);

		return xmlMapper;
	}
}
