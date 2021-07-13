package org.adempiere.archive.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.api.IArchiveStorageFactory.AccessMode;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
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

public class ArchiveStorageFactoryTest
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Rule
	public TemporaryFolder storageFolder = new TemporaryFolder();
	private I_AD_Client client;
	private ArchiveStorageFactory factory;
	private Properties ctx;

	public static class DummyArchiveStorage extends DBArchiveStorage
	{
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

		client = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		client.setWindowsArchivePath(storageFolder.getRoot().getAbsolutePath());
		client.setUnixArchivePath(client.getWindowsArchivePath());
		client.setStoreArchiveOnFileSystem(false);
		InterfaceWrapperHelper.save(client);

		Env.setContext(ctx, "#AD_Client_ID", client.getAD_Client_ID());

		factory = new ArchiveStorageFactory();
	}

	@Test
	public void getArchiveStorage_FromContext_DB()
	{
		client.setStoreArchiveOnFileSystem(false);
		InterfaceWrapperHelper.save(client);

		final IArchiveStorage storage = factory.getArchiveStorage(ctx);
		Assert.assertTrue("Invalid DBArchiveStorage: " + storage, storage instanceof DBArchiveStorage);
	}

	@Test
	public void getArchiveStorage_FromContext_FileSystem()
	{
		Ini.setClient(false);
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.save(client);

		final IArchiveStorage storage = factory.getArchiveStorage(ctx);
		Assert.assertTrue("Invalid FilesystemArchiveStorage: " + storage, storage instanceof FilesystemArchiveStorage);
	}

	@Test
	public void getArchiveStorage_FromArchive_DB()
	{
		client.setStoreArchiveOnFileSystem(false);
		InterfaceWrapperHelper.save(client);

		final I_AD_Archive archive = factory.getArchiveStorage(ctx).newArchive(ctx, ITrx.TRXNAME_None);
		Assert.assertEquals("Invalid IsFileSystem for " + archive, false, archive.isFileSystem());

		final IArchiveStorage storage = factory.getArchiveStorage(archive);
		Assert.assertTrue("Invalid DBArchiveStorage: " + storage, storage instanceof DBArchiveStorage);
	}

	@Test
	public void getArchiveStorage_FromArchive_FileSystem()
	{
		Ini.setClient(false);
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.save(client);

		final I_AD_Archive archive = factory.getArchiveStorage(ctx).newArchive(ctx, ITrx.TRXNAME_None);
		Assert.assertEquals("Invalid IsFileSystem for " + archive, true, archive.isFileSystem());

		final IArchiveStorage storage = factory.getArchiveStorage(archive);
		Assert.assertTrue("Invalid FilesystemArchiveStorage: " + storage, storage instanceof FilesystemArchiveStorage);
	}

	/**
	 * Test: even if the archive was created using DB storage and current storage is Filesystem, when manipulating that archive we shall use DB storage.
	 */
	@Test
	public void getArchiveStorage_DifferentArchiveAndClientConfig()
	{
		//
		// Configure and create archive using DB storage
		final I_AD_Archive archive;
		{
			client.setStoreArchiveOnFileSystem(false);
			InterfaceWrapperHelper.save(client);

			final IArchiveStorage storage = factory.getArchiveStorage(ctx);

			final byte[] data = new byte[] { 65, 66, 67, 68 };

			archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
			Assert.assertEquals("Invalid IsFileSystem for " + archive, false, archive.isFileSystem());
			storage.setBinaryData(archive, data);
			InterfaceWrapperHelper.save(archive);
		}

		//
		// Configure to use filesystem storage
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.save(client);

		final IArchiveStorage storage = factory.getArchiveStorage(archive);
		Assert.assertTrue("Invalid storage: " + storage, storage instanceof DBArchiveStorage);

		//
		// Update the archive and check
		storage.setBinaryData(archive, new byte[] { 97, 98, 99 });
		InterfaceWrapperHelper.save(archive);
		Assert.assertEquals("Invalid IsFileSystem for " + archive, false, archive.isFileSystem());
	}

	/**
	 * Test how custom registered handlers are fetched when accessing from Client and Server side
	 */
	@Test
	public void getArchiveStorage_CustomRegistered()
	{
		client.setStoreArchiveOnFileSystem(true);
		InterfaceWrapperHelper.save(client);

		factory.registerArchiveStorage(IArchiveStorageFactory.StorageType.Filesystem, AccessMode.ALL, FilesystemArchiveStorage.class);
		factory.registerArchiveStorage(IArchiveStorageFactory.StorageType.Filesystem, AccessMode.CLIENT, DummyArchiveStorage.class);

		Ini.setClient(true);
		Assert.assertEquals("Invalid storage class when accessing as client",
				DummyArchiveStorage.class,
				factory.getArchiveStorage(ctx).getClass());

		Ini.setClient(false);
		Assert.assertEquals("Invalid storage class when accessing as server",
				FilesystemArchiveStorage.class,
				factory.getArchiveStorage(ctx).getClass());
	}
}
