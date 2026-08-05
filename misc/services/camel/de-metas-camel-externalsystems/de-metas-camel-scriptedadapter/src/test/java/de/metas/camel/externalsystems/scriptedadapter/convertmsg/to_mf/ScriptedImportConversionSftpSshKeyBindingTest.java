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

import de.metas.common.externalsystem.ExternalSystemConstants;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the SSH-key SFTP auth path keeps the private key in memory (Camel registry {@code byte[]} bean
 * referenced via {@code &privateKey=#bean:<id>}) instead of writing it to a temp file on disk, and that the
 * bean is removed again when the poll route is disabled/replaced.
 */
public class ScriptedImportConversionSftpSshKeyBindingTest extends CamelTestSupport
{
	private static final String ROUTE_KEY = "ScriptedImportConversion-100";
	private static final String PRIVATE_KEY =
			"-----BEGIN OPENSSH PRIVATE KEY-----\nZm9vYmFyYmF6cXV4\n-----END OPENSSH PRIVATE KEY-----\n";
	private static final String PRIVATE_KEY_BEAN_ID = "sftpPrivateKey-" + ROUTE_KEY;

	private ScriptedImportConversionSftpRouteBuilder routeBuilder;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		routeBuilder = new ScriptedImportConversionSftpRouteBuilder(Mockito.mock(ProducerTemplate.class));
		return routeBuilder;
	}

	private static Map<String, String> sshKeyParams()
	{
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST, "localhost");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT, "22");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME, "testuser");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE, ExternalSystemConstants.SFTP_AUTH_TYPE_SSH_KEY);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PRIVATE_KEY, PRIVATE_KEY);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH, "/inbound");
		return params;
	}

	@Test
	void sshKeyAuth_bindsPrivateKeyBytesInRegistry_andReferencesBeanInUri() throws Exception
	{
		final String uri = routeBuilder.buildSftpUri(ROUTE_KEY, sshKeyParams());

		// the key is referenced in-memory via #bean:<id> — never written to a temp file on disk
		assertThat(uri).contains("&privateKey=#bean:" + PRIVATE_KEY_BEAN_ID);
		assertThat(uri).doesNotContain("privateKeyFile");
		assertThat(uri).doesNotContain(".pem");

		// the key bytes are bound in the Camel registry under the route-key-derived id
		final byte[] bound = context.getRegistry().lookupByNameAndType(PRIVATE_KEY_BEAN_ID, byte[].class);
		assertThat(bound).isEqualTo(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
	}

	@Test
	void disableOrReplace_unbindsPrivateKeyBean() throws Exception
	{
		routeBuilder.buildSftpUri(ROUTE_KEY, sshKeyParams());
		assertThat(context.getRegistry().lookupByNameAndType(PRIVATE_KEY_BEAN_ID, byte[].class)).isNotNull();

		// disable/replace must leave no key material behind in the registry
		routeBuilder.removePollingRoute(ROUTE_KEY);

		assertThat(context.getRegistry().lookupByNameAndType(PRIVATE_KEY_BEAN_ID, byte[].class)).isNull();
	}

	@Test
	void passwordAuth_usesPasswordInUri_andBindsNoKeyBean() throws Exception
	{
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST, "localhost");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME, "testuser");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE, ExternalSystemConstants.SFTP_AUTH_TYPE_PASSWORD);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD, "testpass");

		final String uri = routeBuilder.buildSftpUri(ROUTE_KEY, params);

		assertThat(uri).contains("&password=RAW(testpass)");
		assertThat(uri).doesNotContain("privateKey");
		assertThat(context.getRegistry().lookupByNameAndType(PRIVATE_KEY_BEAN_ID, byte[].class)).isNull();
	}
}
