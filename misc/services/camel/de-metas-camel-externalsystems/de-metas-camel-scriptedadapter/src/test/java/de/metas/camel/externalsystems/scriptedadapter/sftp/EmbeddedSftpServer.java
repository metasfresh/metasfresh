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

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;

/**
 * Embedded SFTP server backed by Apache MINA SSHD, for use in unit/integration tests.
 * <p>
 * Binds to a random available port (use {@link #getPort()} after construction).
 * All files are written under the provided {@code rootDir} via a virtual filesystem.
 * <p>
 * Usage:
 * <pre>{@code
 * Path tempDir = Files.createTempDirectory("sftp-test");
 * try (EmbeddedSftpServer server = new EmbeddedSftpServer(tempDir, "user", "pass")) {
 *     // connect to localhost:server.getPort() with provided credentials
 * }
 * }</pre>
 */
public class EmbeddedSftpServer implements Closeable
{
	private final SshServer sshd;
	private final Path rootDir;
	private final int port;

	public EmbeddedSftpServer(final Path rootDir, final String username, final String password) throws IOException
	{
		this.rootDir = rootDir;
		this.sshd = SshServer.setUpDefaultServer();
		this.sshd.setPort(0); // bind to a random available port
		this.sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
		this.sshd.setPasswordAuthenticator((u, p, session) -> username.equals(u) && password.equals(String.valueOf(p)));
		this.sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));
		this.sshd.setFileSystemFactory(new VirtualFileSystemFactory(rootDir));
		this.sshd.start();
		this.port = sshd.getPort();
	}

	/** @return the port the server is listening on (assigned randomly at startup) */
	public int getPort()
	{
		return port;
	}

	/** @return the filesystem root directory backing this SFTP server */
	public Path getRootDir()
	{
		return rootDir;
	}

	@Override
	public void close() throws IOException
	{
		sshd.stop(true);
	}
}
