/*
 * #%L
 * de-metas-camel-edi
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

package de.metas.edi.esb.jaxb.metasfresh;

import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EDIImpCOLCandsTypeTest extends CamelTestSupport
{
	private ObjectFactory objectFactory = new ObjectFactory();

	@Test
	void createXML() throws JAXBException
	{
		final var ediMessage = objectFactory.createEDIMessage();

		final var olCands = objectFactory.createEDIImpCOLCandsType();
		ediMessage.setEDIImpCOLCands(olCands);

		final var olCand1 = objectFactory.createEDIImpCOLCandType();
		olCand1.setQtyEntered(new BigDecimal("10"));
		olCands.getEDIImpCOLCand().add(olCand1);

		final var olCand2 = objectFactory.createEDIImpCOLCandType();
		olCand2.setQtyEntered(new BigDecimal("20"));
		olCands.getEDIImpCOLCand().add(olCand2);

		final var jaxbContext = JAXBContext.newInstance(EDIImpCOLCandsType.class.getPackage().getName());
		final var marshaller = jaxbContext.createMarshaller();

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(ediMessage, baos);

		final String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
		assertThat(result).isEqualToIgnoringWhitespace(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
						+ "<EDI_Message>"
						+ "   <EDI_Imp_C_OLCands>"
						+ "      <EDI_Imp_C_OLCand><QtyEntered>10</QtyEntered></EDI_Imp_C_OLCand>"
						+ "      <EDI_Imp_C_OLCand><QtyEntered>20</QtyEntered></EDI_Imp_C_OLCand>"
						+ "   </EDI_Imp_C_OLCands>"
						+ "</EDI_Message>");
	}
}