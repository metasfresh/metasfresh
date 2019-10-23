package org.adempiere.archive.api;

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

import org.adempiere.archive.spi.IArchiveStorage;
import org.compiere.model.I_AD_Archive;

import de.metas.util.ISingletonService;

/**
 * Factory helper which creates instances of {@link IArchiveStorage} based on given context or given archive.
 * 
 * @author tsa
 * 
 */
public interface IArchiveStorageFactory extends ISingletonService
{
	String STORAGETYPE_Database = "DB";
	String STORAGETYPE_Filesystem = "FS";

	/**
	 * AccessMode - from where the archive is accessed (client side, server side)
	 */
	enum AccessMode
	{
		CLIENT,
		SERVER,
		ALL,
	};

	/**
	 * Register a storage handler class
	 * 
	 * @param storageType see STORAGETYPE_*
	 * @param accessMode
	 * @param storageClass
	 */
	void registerArchiveStorage(String storageType, AccessMode accessMode, Class<? extends IArchiveStorage> storageClass);

	/**
	 * Default Archive Storage for context's tenant(AD_Client_ID).
	 * 
	 * @param ctx
	 * @return
	 */
	public IArchiveStorage getArchiveStorage(final Properties ctx);

	/**
	 * Get storage for current AD_Client_ID and detected {@link AccessMode}.
	 * 
	 * @param ctx
	 * @param storageType
	 * @return
	 */
	IArchiveStorage getArchiveStorage(Properties ctx, String storageType);

	/**
	 * Get storage for current AD_Client_ID.
	 * 
	 * @param ctx
	 * @param storageType
	 * @return
	 */
	IArchiveStorage getArchiveStorage(Properties ctx, String storageType, AccessMode accessMode);

	/**
	 * Archive Storage used for given <code>archive</code>.
	 * 
	 * @param archive
	 * @return
	 */
	public IArchiveStorage getArchiveStorage(final I_AD_Archive archive);
}
