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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.api.IArchiveStorageFactory.AccessMode;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class ArchiveStorageFactoryTest
{
	private I_AD_Client client;
	private ArchiveStorageFactory factory;
	private Properties ctx;

	public static class DummyArchiveStorage extends DBArchiveStorage
	{
	}

	@BeforeEach
	public void beforeEach(@TempDir Path tempDir)
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

		final String archivePath = tempDir.toFile().getAbsolutePath();
		System.out.println("Using archive path: " + archivePath);

		client = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		client.setWindowsArchivePath(archivePath);
		client.setUnixArchivePath(archivePath);
		client.setStoreArchiveOnFileSystem(false);
		InterfaceWrapperHelper.save(client);

		Env.setContext(ctx, "#AD_Client_ID", client.getAD_Client_ID());

		factory = new ArchiveStorageFactory();
	}

	@Nested
	class getArchiveStorage
	{
		@Test
		public void FromContext_DB()
		{
			client.setStoreArchiveOnFileSystem(false);
			InterfaceWrapperHelper.save(client);

			final IArchiveStorage storage = factory.getArchiveStorage(ctx);
			assertThat(storage).isInstanceOf(DBArchiveStorage.class);
		}

		@Test
		public void FromContext_FileSystem()
		{
			Ini.setClient(false);
			client.setStoreArchiveOnFileSystem(true);
			InterfaceWrapperHelper.save(client);

			final IArchiveStorage storage = factory.getArchiveStorage(ctx);
			assertThat(storage).isInstanceOf(FilesystemArchiveStorage.class);
		}

		@Test
		public void FromArchive_DB()
		{
			client.setStoreArchiveOnFileSystem(false);
			InterfaceWrapperHelper.save(client);

			final I_AD_Archive archive = factory.getArchiveStorage(ctx).newArchive(ctx);
			assertThat(archive.isFileSystem()).isFalse();

			final IArchiveStorage storage = factory.getArchiveStorage(archive);
			assertThat(storage).isInstanceOf(DBArchiveStorage.class);
		}

		@Test
		public void FromArchive_FileSystem()
		{
			Ini.setClient(false);
			client.setStoreArchiveOnFileSystem(true);
			InterfaceWrapperHelper.save(client);

			final I_AD_Archive archive = factory.getArchiveStorage(ctx).newArchive(ctx);
			assertThat(archive.isFileSystem()).isTrue();

			final IArchiveStorage storage = factory.getArchiveStorage(archive);
			assertThat(storage).isInstanceOf(FilesystemArchiveStorage.class);
		}

		/**
		 * Test: even if the archive was created using DB storage and current storage is Filesystem, when manipulating that archive we shall use DB storage.
		 */
		@Test
		public void DifferentArchiveAndClientConfig()
		{
			//
			// Configure and create archive using DB storage
			final I_AD_Archive archive;
			{
				client.setStoreArchiveOnFileSystem(false);
				InterfaceWrapperHelper.save(client);

				final IArchiveStorage storage = factory.getArchiveStorage(ctx);

				final byte[] data = new byte[] { 65, 66, 67, 68 };

				archive = storage.newArchive(ctx);
				assertThat(archive.isFileSystem()).isFalse();
				storage.setBinaryData(archive, data);
				InterfaceWrapperHelper.save(archive);
			}

			//
			// Configure to use filesystem storage
			client.setStoreArchiveOnFileSystem(true);
			InterfaceWrapperHelper.save(client);

			final IArchiveStorage storage = factory.getArchiveStorage(archive);
			assertThat(storage).isInstanceOf(DBArchiveStorage.class);

			//
			// Update the archive and check
			storage.setBinaryData(archive, new byte[] { 97, 98, 99 });
			InterfaceWrapperHelper.save(archive);
			assertThat(archive.isFileSystem()).isFalse();
		}

		/**
		 * Test how custom registered handlers are fetched when accessing from Client and Server side
		 */
		@Test
		public void CustomRegistered()
		{
			client.setStoreArchiveOnFileSystem(true);
			InterfaceWrapperHelper.save(client);

			factory.registerArchiveStorage(IArchiveStorageFactory.StorageType.Filesystem, AccessMode.ALL, FilesystemArchiveStorage.class);
			factory.registerArchiveStorage(IArchiveStorageFactory.StorageType.Filesystem, AccessMode.CLIENT, DummyArchiveStorage.class);

			Ini.setClient(true);
			assertThat(factory.getArchiveStorage(ctx)).isInstanceOf(DummyArchiveStorage.class);

			Ini.setClient(false);
			assertThat(factory.getArchiveStorage(ctx)).isInstanceOf(FilesystemArchiveStorage.class);
		}
	}
}
