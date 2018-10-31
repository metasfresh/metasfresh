package de.metas.document.archive.rpl.requesthandler;

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


import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.util.Services;
import de.metas.util.collections.Converter;

public class ArchiveGetDataHandlerConverter implements Converter<I_AD_Archive, I_AD_Archive>
{
	public static final transient ArchiveGetDataHandlerConverter instance = new ArchiveGetDataHandlerConverter();

	@Override
	public I_AD_Archive convert(final I_AD_Archive archiveRequest)
	{
		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(archiveRequest);
		final byte[] data = storage.getBinaryData(archiveRequest);

		//
		// NOTE: We are setting directly the byte data to BinaryData column
		// We do this because replication module will fetch the values directly from there
		archiveRequest.setBinaryData(data);
		// Prevent saving this archive because this archive is prepared for replication module, but saving it will result in big inconsistencies
		InterfaceWrapperHelper.setSaveDeleteDisabled(archiveRequest, true);

		return archiveRequest;
	}

}
