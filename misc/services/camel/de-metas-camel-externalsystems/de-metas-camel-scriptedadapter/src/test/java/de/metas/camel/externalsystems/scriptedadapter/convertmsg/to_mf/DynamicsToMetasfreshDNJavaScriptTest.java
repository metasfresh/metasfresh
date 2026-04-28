/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import de.metas.camel.externalsystems.scriptedadapter.convertmsg.JavaScriptTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Tests the JavaScript transformation script (d2m_dn.js) that converts delivery note messages
 * into metasfresh receipt format.
 */
class DynamicsToMetasfreshDNJavaScriptTest
{
	private static final String JSON_VALID_REQUEST = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/to_mf/dynamics365/d2m_dn_valid_request.json";
	private static final String JSON_VALID_RESPONSE = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/to_mf/dynamics365/d2m_dn_valid_response.json";

	private static final String JSON_MULTIPLE_IDENTIFICATIONS_REQUEST = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/to_mf/dynamics365/d2m_dn_multiple_identifications_request.json";
	private static final String JSON_MULTIPLE_IDENTIFICATIONS_RESPONSE = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/to_mf/dynamics365/d2m_dn_multiple_identifications_response.json";

	private static final String JSON_INVALID_REQUEST = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/to_mf/dynamics365/d2m_dn_invalid_request.json";
	private static final String JSON_INVALID_RESPONSE = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/to_mf/dynamics365/d2m_dn_invalid_response.json";

	private JavaScriptTestHelper helper;

	@BeforeEach
	void setUp()
	{
		helper = JavaScriptTestHelper.builder()
				.scriptIdentifier("d2m_dn")
				.build();
	}

	/**
	 * Tests successful transformation with
	 * properly formatted delivery note data containing all required fields
	 */
	@Test
	void givenValidRequest_whenExecuteScript_validateResult() throws IOException
	{
		helper.test(JSON_VALID_REQUEST, JSON_VALID_RESPONSE);
	}

	/**
	 * Tests
	 * the prioritization logic when delivery note lines contain both orderLineId and external IDs
	 */
	@Test
	void givenValidRequestWithMultipleIdentificationMethods_whenExecuteScript_validateResult() throws IOException
	{
		helper.test(JSON_MULTIPLE_IDENTIFICATIONS_REQUEST, JSON_MULTIPLE_IDENTIFICATIONS_RESPONSE);
	}

	/**
	 * Tests error handling when delivery
	 * note lines lack required identification methods or contain invalid data
	 */
	@Test
	void givenInvalidRequest_whenExecuteScript_validateResult() throws IOException
	{
		helper.test(JSON_INVALID_REQUEST, JSON_INVALID_RESPONSE);
	}
}