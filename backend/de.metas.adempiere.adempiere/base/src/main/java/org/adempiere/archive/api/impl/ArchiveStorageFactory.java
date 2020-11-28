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

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.reflect.ClassReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ArchiveStorageFactory implements IArchiveStorageFactory
{
	private static final Logger logger = LogManager.getLogger(ArchiveStorageFactory.class);
	private final IClientDAO clientDAO = Services.get(IClientDAO.class);

	@Value(staticConstructor = "of")
	private static class ArchiveStorageClassKey
	{
		@NonNull StorageType storageType;
		@NonNull AccessMode accessMode;
	}

	private final ConcurrentHashMap<ArchiveStorageClassKey, ClassReference<? extends IArchiveStorage>> storageClasses = new ConcurrentHashMap<>();

	@Value(staticConstructor = "of")
	private static class ArchiveStorageKey
	{
		@NonNull ClientId clientId;
		@NonNull StorageType storageType;
		@NonNull AccessMode accessMode;
	}

	private final CCache<ArchiveStorageKey, IArchiveStorage> archiveStorages = CCache.<ArchiveStorageKey, IArchiveStorage>builder().build();

	public ArchiveStorageFactory()
	{
		// Register defaults
		registerArchiveStorage(StorageType.Database, AccessMode.ALL, DBArchiveStorage.class);
		registerArchiveStorage(StorageType.Filesystem, AccessMode.SERVER, FilesystemArchiveStorage.class);
	}

	@Override
	public void registerArchiveStorage(
			@NonNull final StorageType storageType,
			@NonNull final AccessMode accessMode,
			@NonNull final Class<? extends IArchiveStorage> storageClass)
	{
		final ArchiveStorageClassKey key = ArchiveStorageClassKey.of(storageType, accessMode);
		storageClasses.put(key, ClassReference.of(storageClass));
		logger.info("Registered {}: {}", key, storageClass);
	}

	private Class<? extends IArchiveStorage> getArchiveStorageClass(
			@NonNull final StorageType storageType,
			@NonNull final AccessMode accessMode)
	{
		// First try: by storageType and acessMode
		ArchiveStorageClassKey key = ArchiveStorageClassKey.of(storageType, accessMode);
		ClassReference<? extends IArchiveStorage> storageClassRef = storageClasses.get(key);

		// Second try: by storageType and AccessMode.ALL
		if (storageClassRef == null)
		{
			key = ArchiveStorageClassKey.of(storageType, AccessMode.ALL);
			storageClassRef = storageClasses.get(key);
		}

		if (storageClassRef == null)
		{
			throw new AdempiereException("No archive storage class found for storage type '" + storageType + "' and access mode '" + accessMode + "'");
		}

		return storageClassRef.getReferencedClass();
	}

	private StorageType getStorageType(final ClientId adClientId)
	{
		final I_AD_Client client = clientDAO.getById(adClientId);
		if (client.isStoreArchiveOnFileSystem())
		{
			return StorageType.Filesystem;
		}
		else
		{
			return StorageType.Database;
		}
	}

	private StorageType getStorageType(final I_AD_Archive archive)
	{
		if (archive.isFileSystem())
		{
			return StorageType.Filesystem;
		}
		else
		{
			return StorageType.Database;
		}
	}

	private AccessMode getAccessMode()
	{
		if (Ini.isSwingClient())
		{
			return AccessMode.CLIENT;
		}
		else
		{
			return AccessMode.SERVER;
		}
	}

	@Override
	public IArchiveStorage getArchiveStorage(final Properties ctx)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		final StorageType storageType = getStorageType(adClientId);
		final AccessMode accessMode = getAccessMode();

		return getArchiveStorage(adClientId, storageType, accessMode);
	}

	@Override
	public IArchiveStorage getArchiveStorage(final I_AD_Archive archive)
	{
		final ClientId adClientId = ClientId.ofRepoId(archive.getAD_Client_ID());
		final StorageType storageType = getStorageType(archive);
		final AccessMode accessMode = getAccessMode();

		return getArchiveStorage(adClientId, storageType, accessMode);
	}

	private IArchiveStorage getArchiveStorage(
			final ClientId adClientId,
			final StorageType storageType,
			final AccessMode accessMode)
	{
		final ArchiveStorageKey key = ArchiveStorageKey.of(adClientId, storageType, accessMode);
		return archiveStorages.getOrLoad(key, this::createArchiveStorage);
	}

	private IArchiveStorage createArchiveStorage(@NonNull final ArchiveStorageKey key)
	{
		final Class<? extends IArchiveStorage> storageClass = getArchiveStorageClass(key.getStorageType(), key.getAccessMode());

		try
		{
			final IArchiveStorage storage = storageClass.newInstance();
			storage.init(key.getClientId());
			return storage;
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("storageClass", storageClass);
		}
	}

	@Override
	public IArchiveStorage getArchiveStorage(final Properties ctx, final StorageType storageType)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		final AccessMode accessMode = getAccessMode();
		return getArchiveStorage(adClientId, storageType, accessMode);
	}

	@Override
	public IArchiveStorage getArchiveStorage(final Properties ctx, final StorageType storageType, final AccessMode accessMode)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		return getArchiveStorage(adClientId, storageType, accessMode);
	}

	/**
	 * Remove all registered {@link IArchiveStorage} classes.
	 * <p>
	 * NOTE: to be used only in testing
	 */
	public void removeAllArchiveStorages()
	{
		storageClasses.clear();
	}
}
