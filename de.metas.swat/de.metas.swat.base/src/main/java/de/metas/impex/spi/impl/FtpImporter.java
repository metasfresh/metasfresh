package de.metas.impex.spi.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;
import org.ftp4che.FTPConnection;
import org.ftp4che.FTPConnectionFactory;
import org.ftp4che.exception.ConfigurationException;
import org.ftp4che.exception.FtpIOException;
import org.ftp4che.exception.FtpWorkflowException;
import org.ftp4che.util.ftpfile.FTPFile;
import org.slf4j.Logger;

import de.metas.adempiere.util.Parameter;
import de.metas.impex.api.IInboundProcessorBL;
import de.metas.impex.exception.ConfigException;
import de.metas.impex.spi.IImportConnector;
import de.metas.logging.LogManager;
import de.metas.util.Check;

public class FtpImporter extends BaseConnector implements IImportConnector
{

	private static final String SEMAPHORE_FILE_EXT = "Semaphore-File-Ext";

	private static final String SEMAPHORE_FILE_CONTENT = "Semaphore-File-Content";

	private static final String USER = "User";

	private static final String PASSWORD = "Password";

	private static final String PORT = "Port";

	private static final String HOST = "Host";

	public final static String DELETE_REMOTE_FILES = "DeleteRemoteFile";

	public final static String REMOTE_FOLDER = "RemoteFolder";

	private static final Logger logger = LogManager.getLogger(FtpImporter.class);

	private final FileImporter fileImporter = new FileImporter();

	@Override
	protected void closeSpecific()
	{
		fileImporter.close();
	}

	@Override
	protected void openSpecific()
	{

		final String localFolder = getValue(IInboundProcessorBL.LOCAL_FOLDER);

		final boolean deleteRemoteFiles = "Y"
				.equals(getValue(DELETE_REMOTE_FILES));

		final Properties pt = new Properties();

		// pt.setProperty("connection.type", "AUTH_TLS_FTP_CONNECTION");
		pt.setProperty("connection.type", "FTP_CONNECTION");
		pt.setProperty("connection.timeout", "10000");
		pt.setProperty("connection.passive", "true");

		final String host = getValue(HOST);
		final String port = getValue(PORT);
		final String login = getValue(USER);
		final String password = getValue(PASSWORD);

		final String semFileExt = getValue(SEMAPHORE_FILE_EXT);
		final String semFileCont = getValue(SEMAPHORE_FILE_CONTENT);

		pt.setProperty("connection.host", host);
		if (!Check.isEmpty(port))
		{
			pt.setProperty("connection.port", port);
		}
		pt.setProperty("user.login", login);
		pt.setProperty("user.password", password);

		final FTPConnection connection;
		try
		{
			connection = FTPConnectionFactory.getInstance(pt);
		}
		catch (ConfigurationException e)
		{
			// TODO -> AD_Message
			ConfigException.throwNew("Fehler bei FTP-Parametern", e
					.getMessage());
			return;
		}

		try
		{

			connection.connect();
			connection.setTransferType(true);

			logger.info("Connected to server");

			final String remoteFolderName = getValue(REMOTE_FOLDER);

			connection.changeDirectory(remoteFolderName);
			final List<FTPFile> remoteFiles = connection.getDirectoryListing();

			for (final FTPFile file : remoteFiles)
			{

				// check directory
				if (file.isDirectory())
				{
					logger.info("Skipping remote directory " + file.getName());
					continue;
				}

				if (!Check.isEmpty(semFileExt))
				{

					downloadWithSemaphoreFile(localFolder, deleteRemoteFiles,
							semFileExt, semFileCont, connection, file);
				}
				else
				{

					download(localFolder, deleteRemoteFiles, connection, file);
				}
			}

			logger.info("Disconnected from server");
			connection.disconnect();

		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}

		logger.info("Invoking " + fileImporter.getClass().getSimpleName()
				+ " to access downloaded files");
		fileImporter.open(getCurrentParams());
	}

	private void downloadWithSemaphoreFile(final String localFolder,
			final boolean deleteRemoteFiles, final String semFileExt,
			final String semFileCont, final FTPConnection connection,
			final FTPFile file) throws IOException, FtpWorkflowException,
			FtpIOException
	{
		if (file.getName().endsWith(semFileExt))
		{

			logger.info("Checking semaphore file " + file.getName());

			final InputStream in = connection.downloadStream(file);
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			final StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{

				sb.append(line);
				sb.append('\n');
			}
			in.close();

			if (sb.toString().equals(semFileCont + '\n'))
			{

				final String semFileName = file.getName();
				final String fileNameToDownload = semFileName.substring(0,
						semFileName.length() - semFileExt.length());
				final FTPFile fileToDownload = new FTPFile(file.getPath()
						+ fileNameToDownload, false);

				download(localFolder, deleteRemoteFiles, connection,
						fileToDownload);
				if (deleteRemoteFiles)
				{
					// also deleting semaphore file
					connection.deleteFile(file);
				}
			}
		}
	}

	private FTPFile download(final String localFolder,
			final boolean deleteRemoteFiles, final FTPConnection connection,
			final FTPFile file) throws IOException, FtpWorkflowException,
			FtpIOException
	{
		logger.info("Downloading remote file: " + file.getName());

		final FTPFile toFile = new FTPFile(localFolder, file.getName());
		connection.downloadFile(file, toFile);
		if (deleteRemoteFiles)
		{
			connection.deleteFile(file);
		}
		return toFile;
	}

	private String getValue(final String paramName)
	{
		return (String)getCurrentParams().get(paramName).getValue();
	}

	@Override
	public InputStream connectNext(boolean lastWasSuccessfull)
	{

		return fileImporter.connectNext(lastWasSuccessfull);
	}

	@Override
	public List<Parameter> getParameters()
	{

		final List<Parameter> result = new ArrayList<Parameter>();

		// TODO get FileImporter's params to make sure be don't become
		// incompatible. But modify their display name and description

		result.add(new Parameter(IInboundProcessorBL.LOCAL_FOLDER,
				"Download-Ordner",
				"Name des Ordners, in den die Dateien heruntergeladen werden",
				DisplayType.FilePath, 10));

		result
				.add(new Parameter(
						IInboundProcessorBL.ARCHIVE_FOLDER,
						"Archiv-Ordner",

						"Names des Ordners, in den erfogreich importiere Dateien verschoben werden",
						DisplayType.FilePath, 20));

		result.add(new Parameter(HOST, "Host", "Name des FTP-Servers",
				DisplayType.String, 30));

		result.add(new Parameter(PORT, "Port", "FTP-Serverport",
				DisplayType.Integer, 40));

		result.add(new Parameter(USER, "Benutzername", "", DisplayType.String,
				50));

		result.add(new Parameter(PASSWORD, "Passwort", "", DisplayType.String,
				60));

		result.add(new Parameter(REMOTE_FOLDER, "FTP-Ordner", "",
				DisplayType.String, 70));

		result.add(new Parameter(SEMAPHORE_FILE_EXT, "Semaphore-File-Ext", "",
				DisplayType.String, 80));

		result.add(new Parameter(SEMAPHORE_FILE_CONTENT,
				"Semaphore-File-Content", "", DisplayType.FilePath, 90));

		result.add(new Parameter(DELETE_REMOTE_FILES, "FTP-Dateien loeschen",
				"Dateien nach dem Download vom FTP-Server loeschen",
				DisplayType.YesNo, 100));

		return result;
	}

}
