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


import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Util.ArrayKey;

import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Check;
import de.metas.util.Services;

public class ArchiveStorageFactory implements IArchiveStorageFactory
{
	private final Map<ArrayKey, Class<? extends IArchiveStorage>> storageClasses = new ConcurrentHashMap<ArrayKey, Class<? extends IArchiveStorage>>();

	public ArchiveStorageFactory()
	{
		// Register defaults
		registerArchiveStorage(STORAGETYPE_Database, AccessMode.ALL, DBArchiveStorage.class);
		registerArchiveStorage(STORAGETYPE_Filesystem, AccessMode.SERVER, FilesystemArchiveStorage.class);
	}

	private static final ArrayKey createStorageClassesKey(final String storageType, final AccessMode accessMode)
	{
		Check.assumeNotNull(storageType, "storageType not null");
		Check.assumeNotNull(accessMode, "accessMode not null");

		final ArrayKey key = new ArrayKey(storageType, accessMode);
		return key;
	}

	@Override
	public void registerArchiveStorage(final String storageType, final AccessMode accessMode, Class<? extends IArchiveStorage> storageClass)
	{
		Check.assumeNotNull(storageClass, "storageClass not null");

		final ArrayKey key = createStorageClassesKey(storageType, accessMode);
		storageClasses.put(key, storageClass);
	}

	private Class<? extends IArchiveStorage> getArchiveStorageClass(final String storageType, final AccessMode accessMode)
	{
		// First try: by storageType and acessMode
		ArrayKey key = createStorageClassesKey(storageType, accessMode);
		Class<? extends IArchiveStorage> storageClass = storageClasses.get(key);

		// Second try: by storageType and AccessMode.ALL
		if (storageClass == null)
		{
			key = createStorageClassesKey(storageType, AccessMode.ALL);
			storageClass = storageClasses.get(key);
		}

		if (storageClass == null)
		{
			throw new AdempiereException("No archive storage class found for storage type '" + storageType + "' and access mode '" + accessMode + "'");
		}

		return storageClass;
	}

	private String getStorageType(final Properties ctx, final int adClientId)
	{
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(ctx, adClientId);
		if (client.isStoreArchiveOnFileSystem())
		{
			return STORAGETYPE_Filesystem;
		}
		else
		{
			return STORAGETYPE_Database;
		}
	}

	private String getStorageType(final I_AD_Archive archive)
	{
		if (archive.isFileSystem())
		{
			return STORAGETYPE_Filesystem;
		}
		else
		{
			return STORAGETYPE_Database;
		}
	}

	private AccessMode getAccessMode(final Properties ctx)
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
	public IArchiveStorage getArchiveStorage(Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final String storageType = getStorageType(ctx, adClientId);
		final AccessMode accessMode = getAccessMode(ctx);

		final IArchiveStorage storage = getArchiveStorage(ctx, adClientId, storageType, accessMode);
		return storage;
	}

	@Override
	public IArchiveStorage getArchiveStorage(I_AD_Archive archive)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final int adClientId = archive.getAD_Client_ID();
		final String storageType = getStorageType(archive);
		final AccessMode accessMode = getAccessMode(ctx);

		final IArchiveStorage storage = getArchiveStorage(ctx, adClientId, storageType, accessMode);
		return storage;
	}

	@Cached(cacheName = I_AD_Client.Table_Name + "#IArchiveStorage#By#AD_Client_ID")
	/* package */IArchiveStorage getArchiveStorage(@CacheCtx final Properties ctx, final int adClientId,
			final String storageType, final AccessMode accessMode)
	{
		Class<? extends IArchiveStorage> storageClass = getArchiveStorageClass(storageType, accessMode);

		try
		{
			final IArchiveStorage storage = storageClass.newInstance();
			storage.init(ctx, adClientId);

			return storage;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot load " + storageClass, e);
		}
	}

	@Override
	public IArchiveStorage getArchiveStorage(final Properties ctx, final String storageType)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final AccessMode accessMode = getAccessMode(ctx);
		return getArchiveStorage(ctx, adClientId, storageType, accessMode);
	}

	@Override
	public IArchiveStorage getArchiveStorage(final Properties ctx, final String storageType, final AccessMode accessMode)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		return getArchiveStorage(ctx, adClientId, storageType, accessMode);
	}

	/**
	 * Remove all registered {@link IArchiveStorage} classes.
	 * 
	 * NOTE: to be used only in testing
	 */
	public void removeAllArchiveStorages()
	{
		storageClasses.clear();
	}
}
