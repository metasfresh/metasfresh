/*
 * #%L
 * postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

public class PostFinanceServiceTest
{

	@Test
	public void testExecutePing() throws MalformedURLException
	{
		// final URL wsdlURL = new URL("https://ebill-ki.postfinance.ch/B2BService/B2BService.svc?singleWsdl");

		// final B2BService_Service service = new B2BService_Service(wsdlURL);
		// final B2BService port = service.getUserNamePassword();

		// final Map<String, Object> reqContext = ((BindingProvider) port).getRequestContext();
		// reqContext.put(BindingProvider.USERNAME_PROPERTY, "dragos.moca@pevesoft.ro");
		// reqContext.put(BindingProvider.PASSWORD_PROPERTY, "userpassword");

		// TODO: find out why we get 502 Bad Gateway
		// final String result = port.executePing("41101000001205994", null, false, false);
		
		// Assertions.assertNotNull(result);
		//
		// Assertions.assertEquals("41101000011707505", result);
	}
}