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

import com.google.common.annotations.VisibleForTesting;
import de.metas.archive.ArchiveStorageConfig;
import de.metas.archive.ArchiveStorageConfigId;
import de.metas.archive.ArchiveStorageConfigRepository;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.archive.spi.impl.DBArchiveStorage;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Archive_Storage;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;

import java.util.Properties;

public class ArchiveStorageFactory implements IArchiveStorageFactory
{
	private final IClientDAO clientDAO = Services.get(IClientDAO.class);
	private ArchiveStorageConfigRepository _storageConfigRepository; // lazy

	private final CCache<ArchiveStorageConfigId, IArchiveStorage> archiveStorages = CCache.<ArchiveStorageConfigId, IArchiveStorage>builder()
			.tableName(I_AD_Archive_Storage.Table_Name)
			.build();

	private ArchiveStorageConfigRepository storageConfigRepository()
	{
		ArchiveStorageConfigRepository storageConfigRepository = this._storageConfigRepository;
		if (storageConfigRepository == null)
		{
			storageConfigRepository = this._storageConfigRepository = SpringContextHolder.instance.getBean(ArchiveStorageConfigRepository.class);
		}
		return storageConfigRepository;
	}

	@VisibleForTesting
	public void setArchiveStorage(@NonNull final ArchiveStorageConfigId configId, @NonNull final IArchiveStorage storage)
	{
		archiveStorages.put(configId, storage);
	}

	@Override
	public IArchiveStorage getArchiveStorage(final Properties ctx)
	{
		final ClientId adClientId = Env.getClientId(ctx);
		return getArchiveStorage(adClientId);
	}

	@Override
	public IArchiveStorage getArchiveStorage(@NonNull final I_AD_Archive archive)
	{
		final ArchiveStorageConfigId storageConfigId = ArchiveStorageConfigId.ofRepoIdOrNull(archive.getAD_Archive_Storage_ID());
		if (storageConfigId != null)
		{
			return getArchiveStorage(storageConfigId);
		}
		
		final ClientId adClientId = ClientId.ofRepoId(archive.getAD_Client_ID());
		return getArchiveStorage(adClientId);
	}

	@Override
	public IArchiveStorage getArchiveStorage(@NonNull final ClientId adClientId)
	{
		final I_AD_Client client = clientDAO.getById(adClientId);
		final ArchiveStorageConfigId storageConfigId = ArchiveStorageConfigId.optionalOfRepoId(client.getAD_Archive_Storage_ID()).orElse(ArchiveStorageConfigId.DATABASE);
		return getArchiveStorage(storageConfigId);
	}

	@Override
	public IArchiveStorage getArchiveStorage(@NonNull final ArchiveStorageConfigId storageConfigId)
	{
		return archiveStorages.getOrLoad(storageConfigId, this::createArchiveStorage);
	}

	private IArchiveStorage createArchiveStorage(@NonNull final ArchiveStorageConfigId storageConfigId)
	{
		final ArchiveStorageConfig storageConfig = storageConfigRepository().getById(storageConfigId);
		return switch (storageConfig.getType())
		{
			case DATABASE -> new DBArchiveStorage(storageConfig);
			case FILE_SYSTEM ->new FilesystemArchiveStorage(storageConfig);
		};
	}
}
