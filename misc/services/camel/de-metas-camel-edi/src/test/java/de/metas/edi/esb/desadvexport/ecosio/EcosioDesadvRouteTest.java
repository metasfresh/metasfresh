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

package de.metas.edi.esb.desadvexport.ecosio;

import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.metasfresh.ObjectFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class EcosioDesadvRouteTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new EcosioDesadvRoute();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(EcosioDesadvRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void test() throws Exception
	{
		final var ediExpDesadvType = new ObjectFactory().createEDIExpDesadvType();
		ediExpDesadvType.setEDIDesadvID(new BigInteger("1001"));
		ediExpDesadvType.setADClientValueAttr("ADClientValueAttr");

		//EcosioDesadvRoute.OUTPUT_DESADV_LOCAL;

		template.sendBody(
				EcosioDesadvRoute.EP_EDI_METASFRESH_XML_DESADV_CONSUMER,
				ediExpDesadvType);
	}
}