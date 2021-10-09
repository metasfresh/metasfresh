package de.metas.document.archive.manualtest;

/*
 * #%L
 * de.metas.document.archive.base
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

import de.metas.document.archive.spi.impl.RemoteArchiveStorage;
import de.metas.document.archive.spi.impl.RestHttpArchiveEndpoint;
import de.metas.util.FileUtil;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Util;

import java.io.File;
import java.util.Properties;

/**
 * Manual test: connect to REST HTTP endpoint and fetch given archive
 *
 * @author tsa
 *
 */
public class GetArchiveManualTest
{

	public static void main(final String[] args) throws Exception
	{
		final GetArchiveManualTest main = new GetArchiveManualTest();
		main.run();
	}

	private final Properties ctx;
	// private ArchiveStorageFactory factory;
	private final RemoteArchiveStorage storage;

	private GetArchiveManualTest()
	{
		AdempiereTestHelper.get().staticInit();
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

		final File storageFolder = FileUtil.createTempDirectory("archive_");

		final I_AD_Client client = InterfaceWrapperHelper.newInstance(I_AD_Client.class);
		client.setWindowsArchivePath(storageFolder.getAbsolutePath());
		client.setUnixArchivePath(client.getWindowsArchivePath());
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.saveRecord(client);
		final ClientId clientId = ClientId.ofRepoId(client.getAD_Client_ID());

		Env.setClientId(ctx, clientId);
		final int adSessionId = 1014302;
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, adSessionId);

		// factory = new ArchiveStorageFactory();
		// factory.registerArchiveStorage(IArchiveStorageFactory.STORAGETYPE_Filesystem, IArchiveStorageFactory.AccessMode.CLIENT, RemoteArchiveStorage.class);

		Ini.setClient(true);

		final RestHttpArchiveEndpoint archiveEndpoint = new RestHttpArchiveEndpoint();
		final String archiveEndpointServerUrl = "http://aditv2:8183";
		archiveEndpoint.setServerUrl(archiveEndpointServerUrl);

		storage = new RemoteArchiveStorage();
		storage.setEndpoint(archiveEndpoint);
		storage.init(clientId);
	}

	public void run() throws Exception
	{
		final I_AD_Archive archive = InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, ITrx.TRXNAME_None);
		final int testArchiveId = 1004898;
		archive.setAD_Archive_ID(testArchiveId);

		final byte[] data = storage.getBinaryData(archive);

		final File outFile = File.createTempFile("archive_" + archive.getAD_Archive_ID() + "_", ".pdf");
		Util.writeBytes(outFile, data);
		System.out.println("Wrote " + outFile);
	}
}
