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
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;

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
 *   <li><b>SSH_KEY</b> — username + SSH private key (written to a temp file)</li>
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

		final String resolvedFilename = SftpFilenameResolver.resolve(filenamePattern, Map.of());

		final String body = msgFromMfContext.getScriptReturnValue();
		if (body == null)
		{
			throw new RuntimeCamelException("Script return value is null — nothing to deliver via SFTP!");
		}

		final String sftpUri = buildSftpUri(endpoint, port, remotePath, username, sftpAuthType, resolvedFilename);

		log.info("Delivering file via SFTP: host={}, port={}, path={}, filename={}", host, port, remotePath, resolvedFilename);

		Path tempKeyFile = null;
		try (final ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate())
		{
			if (AUTH_TYPE_SSH_KEY.equals(sftpAuthType))
			{
				tempKeyFile = writeSshKeyToTempFile(endpoint.getSshPrivateKey());
			}

			final String finalUri = AUTH_TYPE_SSH_KEY.equals(sftpAuthType) && tempKeyFile != null
					? sftpUri + "&privateKeyFile=" + tempKeyFile.toAbsolutePath()
					: sftpUri;

			producerTemplate.sendBody(finalUri, body);

			log.info("SFTP delivery successful: {}", resolvedFilename);
		}
		finally
		{
			if (tempKeyFile != null)
			{
				deleteTempKeyFileSilently(tempKeyFile);
			}
		}
	}

	@NonNull
	private static String buildSftpUri(
			@NonNull final JsonExternalSystemEndpoint endpoint,
			final int port,
			@NonNull final String remotePath,
			@NonNull final String username,
			@NonNull final String sftpAuthType,
			@NonNull final String resolvedFilename)
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
			if (Check.isBlank(endpoint.getSshPrivateKey()))
			{
				throw new RuntimeCamelException("SFTP SSH_KEY auth selected but SSH private key is not configured!");
			}
			// privateKeyFile will be appended after temp file is created
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
	private static Path writeSshKeyToTempFile(@NonNull final String sshPrivateKey) throws IOException
	{
		final Set<PosixFilePermission> ownerOnly = PosixFilePermissions.fromString("rw-------");
		final FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(ownerOnly);
		final Path tempFile = Files.createTempFile("sftp_key_", ".pem", attr);
		Files.writeString(tempFile, sshPrivateKey, StandardCharsets.UTF_8);
		log.debug("Wrote SSH private key to temp file: {}", tempFile);
		return tempFile;
	}

	private static void deleteTempKeyFileSilently(@NonNull final Path tempKeyFile)
	{
		try
		{
			Files.deleteIfExists(tempKeyFile);
			log.debug("Deleted temp SSH key file: {}", tempKeyFile);
		}
		catch (final IOException e)
		{
			log.warn("Failed to delete temp SSH key file: {}", tempKeyFile, e);
		}
	}
}
