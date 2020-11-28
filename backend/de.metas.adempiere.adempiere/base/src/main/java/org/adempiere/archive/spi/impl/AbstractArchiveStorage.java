package org.adempiere.archive.spi.impl;

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


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import lombok.NonNull;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Archive;

import de.metas.util.Services;

/**
 * Abstract implementation of {@link IArchiveStorage}. At this level there is no reference to a particular storage support.
 * 
 * @author tsa
 * 
 */
public abstract class AbstractArchiveStorage implements IArchiveStorage
{
	@Override
	public void init(@NonNull final ClientId adClientId)
	{
		// nothing at this level
	}

	@Override
	public I_AD_Archive newArchive(final Properties ctx, final String trxName)
	{
		return InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, trxName);
	}

	@Override
	public InputStream getBinaryDataAsStream(final I_AD_Archive archive)
	{
		final byte[] inflatedData = getBinaryData(archive);
		if (inflatedData == null)
		{
			return null;
		}
		return new ByteArrayInputStream(inflatedData);
	}
}
