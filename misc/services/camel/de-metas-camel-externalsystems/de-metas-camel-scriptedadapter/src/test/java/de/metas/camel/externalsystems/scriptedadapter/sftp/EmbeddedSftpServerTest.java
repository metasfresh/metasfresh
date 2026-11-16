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

package de.metas.camel.externalsystems.scriptedadapter.sftp;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Smoke tests for {@link EmbeddedSftpServer}.
 * Verifies that the embedded server starts, accepts connections, and supports basic SFTP operations.
 */
class EmbeddedSftpServerTest
{
	private static final String TEST_USERNAME = "testuser";
	private static final String TEST_PASSWORD = "testpass";

	/**
	 * Verify that the server starts successfully and binds to a valid port.
	 */
	@Test
	void embeddedSftpServer_startsAndStops() throws Exception
	{
		final Path tempDir = Files.createTempDirectory("sftp-test-smoke");
		try (final EmbeddedSftpServer server = new EmbeddedSftpServer(tempDir, TEST_USERNAME, TEST_PASSWORD))
		{
			assertThat(server.getPort()).isGreaterThan(0);
			assertThat(server.getRootDir()).isEqualTo(tempDir);
		}
	}

	/**
	 * Verify end-to-end SFTP operation: connect → upload a file → read it back → verify content.
	 */
	@Test
	void embeddedSftpServer_connectWriteRead() throws Exception
	{
		final Path tempDir = Files.createTempDirectory("sftp-test-rw");
		try (final EmbeddedSftpServer server = new EmbeddedSftpServer(tempDir, TEST_USERNAME, TEST_PASSWORD))
		{
			final String fileContent = "Hello SFTP World!";
			final String remoteFilePath = "/test-file.txt";

			// Write a file via SFTP
			withSftpClient(server, sftpClient -> {
				final byte[] bytes = fileContent.getBytes(StandardCharsets.UTF_8);
				try (final OutputStream outputStream = sftpClient.write(remoteFilePath))
				{
					outputStream.write(bytes);
				}
			});

			// Verify the file is written to the virtual root dir
			final Path localFile = tempDir.resolve("test-file.txt");
			assertThat(localFile).exists();
			assertThat(Files.readString(localFile, StandardCharsets.UTF_8)).isEqualTo(fileContent);

			// Read the file back via SFTP and verify content
			final String readBack = withSftpClientResult(server, sftpClient -> {
				try (final InputStream is = sftpClient.read(remoteFilePath))
				{
					return new String(is.readAllBytes(), StandardCharsets.UTF_8);
				}
			});

			assertThat(readBack).isEqualTo(fileContent);
		}
	}

	/**
	 * Verify that wrong credentials are rejected.
	 * <p>
	 * Apache MINA SSHD throws {@link org.apache.sshd.common.SshException} when the server
	 * exhausts all authentication methods. We catch any IOException as "auth rejected".
	 */
	@Test
	void embeddedSftpServer_rejectsWrongPassword() throws Exception
	{
		final Path tempDir = Files.createTempDirectory("sftp-test-auth");
		try (final EmbeddedSftpServer server = new EmbeddedSftpServer(tempDir, TEST_USERNAME, TEST_PASSWORD))
		{
			final SshClient client = SshClient.setUpDefaultClient();
			client.start();
			boolean authRejected = false;
			try (final ClientSession session = client.connect(TEST_USERNAME, "localhost", server.getPort())
					.verify(5, TimeUnit.SECONDS)
					.getSession())
			{
				session.addPasswordIdentity("wrong-password");
				session.auth().verify(5, TimeUnit.SECONDS);
				// if we reach here, auth succeeded — that would be wrong
			}
			catch (final IOException e)
			{
				// Expected: MINA SSHD throws SshException("No more authentication methods available")
				authRejected = true;
			}
			finally
			{
				client.stop();
			}
			assertThat(authRejected).as("wrong password should be rejected").isTrue();
		}
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	@FunctionalInterface
	private interface SftpConsumer
	{
		void accept(SftpClient sftpClient) throws IOException;
	}

	@FunctionalInterface
	private interface SftpFunction<T>
	{
		T apply(SftpClient sftpClient) throws IOException;
	}

	private void withSftpClient(final EmbeddedSftpServer server, final SftpConsumer action) throws IOException
	{
		withSftpClientResult(server, sftpClient -> {
			action.accept(sftpClient);
			return null;
		});
	}

	private <T> T withSftpClientResult(final EmbeddedSftpServer server, final SftpFunction<T> action) throws IOException
	{
		final SshClient client = SshClient.setUpDefaultClient();
		client.start();
		try (final ClientSession session = client.connect(TEST_USERNAME, "localhost", server.getPort())
				.verify(5, TimeUnit.SECONDS)
				.getSession())
		{
			session.addPasswordIdentity(TEST_PASSWORD);
			session.auth().verify(5, TimeUnit.SECONDS);

			try (final SftpClient sftpClient = SftpClientFactory.instance().createSftpClient(session))
			{
				return action.apply(sftpClient);
			}
		}
		finally
		{
			client.stop();
		}
	}
}
