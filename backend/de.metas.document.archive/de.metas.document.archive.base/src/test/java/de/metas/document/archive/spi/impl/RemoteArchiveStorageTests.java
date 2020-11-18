package de.metas.document.archive.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;
import java.util.UUID;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.api.IArchiveStorageFactory.AccessMode;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestWatcher;

import de.metas.organization.OrgId;
import de.metas.util.Services;

/**
 * Integration test for {@link RemoteArchiveStorage}
 *
 * @author tsa
 *
 */
public class RemoteArchiveStorageTests
{
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private Properties ctx;
	private IArchiveStorageFactory storageFactory;
	private I_AD_Client client;

	@Rule
	public TemporaryFolder storageFolder = new TemporaryFolder();
	private ISysConfigBL sysConfigBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		sysConfigBL = Services.get(ISysConfigBL.class);

		setupClient();
		setupRemoteArchiveEndpoint();
	}

	protected void setupClient()
	{
				//
		// Configure client level settings
		ctx = Env.getCtx();
		client = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		client.setWindowsArchivePath(storageFolder.getRoot().getAbsolutePath());
		client.setUnixArchivePath(client.getWindowsArchivePath());
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.save(client);
		Env.setContext(ctx, "#AD_Client_ID", client.getAD_Client_ID());

		Ini.setClient(true);

		//
		// Configure storages
		storageFactory = Services.get(IArchiveStorageFactory.class);
		storageFactory.registerArchiveStorage(
				IArchiveStorageFactory.StorageType.Database,
				AccessMode.ALL,
				DBArchiveStorage.class);
		storageFactory.registerArchiveStorage(
				IArchiveStorageFactory.StorageType.Filesystem,
				AccessMode.ALL,
				FilesystemArchiveStorage.class);
		storageFactory.registerArchiveStorage(
				IArchiveStorageFactory.StorageType.Filesystem,
				AccessMode.CLIENT,
				RemoteArchiveStorage.class);
	}

	protected void setupRemoteArchiveEndpoint()
	{
		sysConfigBL.setValue(RemoteArchiveStorage.SYSCONFIG_ArchiveEndpoint,
				StaticMockedArchiveEndpoint.class.getName(),
				ClientId.ofRepoId(client.getAD_Client_ID()), OrgId.ANY);
		StaticMockedArchiveEndpoint.setArchiveEndpoint(new MockedArchiveEndpoint());

	}

	private byte[] createTestData()
	{
		final byte[] data = ("test-data-" + UUID.randomUUID()).getBytes();
		return data;
	}

	/**
	 * Tests current configuration and it asserts everything is well.
	 *
	 * This test is a precondition for the rest of the tests.
	 */
	@Test
	public void testConfig()
	{
		// Client / StoreArchiveOnFileSystem / Expected Storage Class
		assertConfigStorageClass(true, false, DBArchiveStorage.class);
		assertConfigStorageClass(false, false, DBArchiveStorage.class);
		assertConfigStorageClass(true, true, RemoteArchiveStorage.class);
		assertConfigStorageClass(false, true, FilesystemArchiveStorage.class);

		// Test that remote endpoint is well configured
		final RemoteArchiveStorage remoteStorage = (RemoteArchiveStorage)storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.StorageType.Filesystem, AccessMode.CLIENT);
		Assert.assertEquals("Invalid endpoint class configured for " + remoteStorage,
				StaticMockedArchiveEndpoint.class, remoteStorage.getEndpoint().getClass());
	}

	private void assertConfigStorageClass(final boolean isClient, final boolean clientStoreOnFilesystem, final Class<?> expectedArchiveClass)
	{
		Ini.setClient(isClient);

		client.setStoreArchiveOnFileSystem(clientStoreOnFilesystem);
		InterfaceWrapperHelper.save(client);

		Assert.assertEquals("Invalid storage class for isClient=" + isClient + ", clientStoreOnFilesystem=" + clientStoreOnFilesystem,
				expectedArchiveClass,
				storageFactory.getArchiveStorage(ctx).getClass());
	}

	@Test
	public void test_getBinaryData()
	{
		Ini.setClient(true);

		final byte[] data = createTestData();

		final I_AD_Archive archive;

		// Create archive on server
		{
			Ini.setClient(false);
			final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.StorageType.Filesystem);
			archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
			storage.setBinaryData(archive, data);
			InterfaceWrapperHelper.save(archive);
		}

		// Retrieve archive on client
		{
			Ini.setClient(true);
			final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.StorageType.Filesystem);
			final byte[] dataActual = storage.getBinaryData(archive);
			Assert.assertArrayEquals("Invalid binary data", data, dataActual);
		}
	}

	@Test
	public void test_setBinaryData()
	{
		Ini.setClient(true);

		final byte[] data = createTestData();

		final IArchiveStorage storage = storageFactory.getArchiveStorage(ctx, IArchiveStorageFactory.StorageType.Filesystem, AccessMode.CLIENT);
		final I_AD_Archive archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
		storage.setBinaryData(archive, data);
		InterfaceWrapperHelper.save(archive);

		final byte[] dataActual = storage.getBinaryData(archive);
		Assert.assertArrayEquals("Invalid binary data", data, dataActual);
	}
}
