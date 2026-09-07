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

import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;

/**
 * Camel {@link Processor} that delivers the scripted adapter output to an SFTP server.
 * <p>
 * Reads SFTP connection details from the {@link JsonExternalSystemEndpoint} stored in
 * the exchange property {@link de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants#ROUTE_MSG_FROM_MF_CONTEXT}.
 * <p>
 * Supports two authentication modes:
 * <ul>
 *   <li><b>PASSWORD</b> — username + password</li>
 *   <li><b>SSH_KEY</b> — username + SSH private key. The key is kept in memory: its bytes are bound in
 *       the Camel registry and referenced via {@code &privateKey=#bean:<id>}, so it never lands on a
 *       temp file on disk; the bean is unbound again once delivery finishes (mirrors the inbound
 *       {@code ScriptedImportConversionSftpRouteBuilder}).</li>
 * </ul>
 */
@Slf4j
@Component
public class SftpDeliveryProcessor implements Processor
{
	private static final String AUTH_TYPE_PASSWORD = ExternalSystemConstants.SFTP_AUTH_TYPE_PASSWORD;
	private static final String AUTH_TYPE_SSH_KEY = ExternalSystemConstants.SFTP_AUTH_TYPE_SSH_KEY;

	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final MsgFromMfContext msgFromMfContext = ProcessorHelper.getPropertyOrThrowError(
				exchange, ROUTE_MSG_FROM_MF_CONTEXT, MsgFromMfContext.class);

		final JsonExternalSystemEndpoint endpoint = msgFromMfContext.getEndpointParameters();

		final String host = endpoint.getSftpHost();
		if (Check.isBlank(host))
		{
			throw new RuntimeCamelException("SFTP host is not configured in endpoint parameters!");
		}

		final int port = endpoint.getSftpPort() != null ? endpoint.getSftpPort() : 22;
		final String username = endpoint.getSftpUsername();
		final String remotePath = Check.isBlank(endpoint.getSftpRemotePath()) ? "" : endpoint.getSftpRemotePath();
		final String filenamePattern = endpoint.getSftpFilenamePattern();
		final String sftpAuthType = endpoint.getSftpAuthType();

		if (Check.isBlank(username))
		{
			throw new RuntimeCamelException("SFTP username is not configured in endpoint parameters!");
		}
		if (Check.isBlank(filenamePattern))
		{
			throw new RuntimeCamelException("SFTP filename pattern is not configured in endpoint parameters!");
		}
		if (Check.isBlank(sftpAuthType))
		{
			throw new RuntimeCamelException("SFTP auth type is not configured in endpoint parameters!");
		}

		final Map<String, String> filenameVariables = buildFilenameVariables(msgFromMfContext);
		final String resolvedFilename = SftpFilenameResolver.resolve(filenamePattern, filenameVariables);

		final String body = msgFromMfContext.getScriptReturnValue();
		if (body == null)
		{
			throw new RuntimeCamelException("Script return value is null — nothing to deliver via SFTP!");
		}

		// Per-delivery, stable-within-this-exchange bean id for the in-memory SSH key. buildSftpUri binds
		// the key bytes under this id for SSH_KEY auth; the finally below unbinds it no matter how the
		// delivery ends (no-op for password auth, where nothing was bound).
		final String privateKeyBeanId = sshPrivateKeyBeanId(exchange.getExchangeId());
		final String sftpUri = buildSftpUri(endpoint, port, remotePath, username, sftpAuthType, resolvedFilename, exchange.getContext(), privateKeyBeanId);

		log.info("Delivering file via SFTP: host={}, port={}, path={}, filename={}", host, port, remotePath, resolvedFilename);

		try (final ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate())
		{
			producerTemplate.sendBody(sftpUri, body);

			log.info("SFTP delivery successful: {}", resolvedFilename);
		}
		finally
		{
			// Leave no key material behind: drop the in-memory private-key bean (no-op if password auth was used).
			exchange.getContext().getRegistry().unbind(privateKeyBeanId);
		}
	}

	@NonNull
	private static Map<String, String> buildFilenameVariables(@NonNull final MsgFromMfContext context)
	{
		final var variables = new java.util.HashMap<String, String>();
		if (!Check.isBlank(context.getOutboundDocumentNo()))
		{
			variables.put("documentno", context.getOutboundDocumentNo());
		}
		variables.put("table", context.getOutboundRecordTableName());
		variables.put("recordid", context.getOutboundRecordId());
		return variables;
	}

	@NonNull
	String buildSftpUri(
			@NonNull final JsonExternalSystemEndpoint endpoint,
			final int port,
			@NonNull final String remotePath,
			@NonNull final String username,
			@NonNull final String sftpAuthType,
			@NonNull final String resolvedFilename,
			@NonNull final CamelContext camelContext,
			@NonNull final String privateKeyBeanId)
	{
		final var sb = new StringBuilder();
		sb.append("sftp://").append(endpoint.getSftpHost()).append(":").append(port);
		sb.append("/").append(remotePath);
		sb.append("?fileName=").append(URLEncoder.encode(resolvedFilename, StandardCharsets.UTF_8));
		sb.append("&username=").append(URLEncoder.encode(username, StandardCharsets.UTF_8));

		if (AUTH_TYPE_PASSWORD.equals(sftpAuthType))
		{
			final String password = endpoint.getPassword();
			if (Check.isBlank(password))
			{
				throw new RuntimeCamelException("SFTP password auth selected but password is not configured!");
			}
			sb.append("&password=").append(URLEncoder.encode(password, StandardCharsets.UTF_8));
		}
		else if (AUTH_TYPE_SSH_KEY.equals(sftpAuthType))
		{
			final String sshPrivateKey = endpoint.getSshPrivateKey();
			if (Check.isBlank(sshPrivateKey))
			{
				throw new RuntimeCamelException("SFTP SSH_KEY auth selected but SSH private key is not configured!");
			}
			// Keep the key in memory: bind its bytes in the Camel registry and reference them via
			// #bean:<id>, so it never lands on a temp file on disk. process()'s finally unbinds the
			// same id (mirrors the inbound ScriptedImportConversionSftpRouteBuilder).
			camelContext.getRegistry().bind(privateKeyBeanId, sshPrivateKey.getBytes(StandardCharsets.UTF_8));
			sb.append("&privateKey=#bean:").append(privateKeyBeanId);
		}
		else
		{
			throw new RuntimeCamelException("Unsupported SFTP auth type: " + sftpAuthType);
		}

		sb.append("&stepwise=false");
		sb.append("&disconnect=true");
		// NOTE: Host key verification is disabled for convenience. Consider making this configurable for production use.
		sb.append("&strictHostKeyChecking=no");
		sb.append("&useUserKnownHostsFile=false");

		return sb.toString();
	}

	@NonNull
	private static String sshPrivateKeyBeanId(@NonNull final String exchangeId)
	{
		return "sftpDeliveryPrivateKey-" + exchangeId;
	}
}
