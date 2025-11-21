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

import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MetasfreshToDynamicsPOJavaScriptTest
{
	private JavaScriptRepo javaScriptRepo;
	private JavaScriptExecutorService javaScriptExecutorService;

	private static final String JSON_VALID_REQUEST = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/from_mf/m2d_po_valid_request.json";
	private static final String JSON_VALID_RESPONSE = "de/metas/camel/externalsystems/scriptedadapter/convertmsg/from_mf/m2d_po_valid_response.json";

	private static final String SCRIPT_IDENTIFIER = "m2d_po_wrapper";

	@BeforeEach
	void setUp()
	{
		final Path projectRoot = Paths.get(System.getProperty("user.dir"));
		final Path scriptDir = projectRoot.resolve("javascript_dynamics365");

		javaScriptRepo = new JavaScriptRepo(scriptDir.toString());
		javaScriptExecutorService = new JavaScriptExecutorService();
	}

	@Test
	void givenValidRequest_whenExecuteScript_validateResult() throws IOException
	{
		// given
		final InputStream jsonJavaScriptRequest = this.getClass().getClassLoader().getResourceAsStream(JSON_VALID_REQUEST);
		assertThat(jsonJavaScriptRequest).isNotNull();
		final String jsonJavaScriptRequestAsString = new String(jsonJavaScriptRequest.readAllBytes(), StandardCharsets.UTF_8);

		// when
		final String retrievedContent = javaScriptRepo.get(SCRIPT_IDENTIFIER);
		final String javaScriptResult = javaScriptExecutorService.executeScript(
				SCRIPT_IDENTIFIER,
				retrievedContent,
				jsonJavaScriptRequestAsString);

		// then
		final InputStream jsonJavaScriptResponse = this.getClass().getClassLoader().getResourceAsStream(JSON_VALID_RESPONSE);
		assertThat(jsonJavaScriptResponse).isNotNull();
		final String jsonJavaScriptResponseAsString = new String(jsonJavaScriptResponse.readAllBytes(), StandardCharsets.UTF_8);
		assertThat(jsonJavaScriptResponseAsString)
				.isEqualTo(javaScriptResult);
	}
}