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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import de.metas.camel.externalsystems.scriptedadapter.convertmsg.JavaScriptTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MetasfreshToDynamicsSOJavaScriptTest
{
	private static final String JSON_VALID_REQUEST = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/from_mf/m2d_so_valid_request.json";
	private static final String JSON_VALID_RESPONSE = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/from_mf/m2d_so_valid_response.json";

	private static final String SCRIPT_IDENTIFIER = "m2d_so_wrapper";
	private JavaScriptTestHelper helper;

	@BeforeEach
	void setUp()
	{
		helper = JavaScriptTestHelper.builder()
				.scriptIdentifier(SCRIPT_IDENTIFIER)
				.build();
	}

	@Test
	void givenValidRequest_whenExecuteScript_validateResult() throws IOException
	{
		helper.test(JSON_VALID_REQUEST, JSON_VALID_RESPONSE);
	}
}