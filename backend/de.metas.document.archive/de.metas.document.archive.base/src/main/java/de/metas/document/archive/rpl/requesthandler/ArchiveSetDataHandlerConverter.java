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


import java.util.Properties;

import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MTable;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.Converter;

public class ArchiveSetDataHandlerConverter implements Converter<I_AD_Archive, I_AD_Archive>
{
	public static final transient ArchiveSetDataHandlerConverter instance = new ArchiveSetDataHandlerConverter();

	@Override
	public I_AD_Archive convert(final I_AD_Archive tempArchive)
	{
		checkValid(tempArchive);

		// Assume that replication module is saving directly to AD_Archive.BinaryData
		final byte[] data = tempArchive.getBinaryData();

		final Properties ctx = InterfaceWrapperHelper.getCtx(tempArchive);
		final IArchiveStorage fsStorage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctx, IArchiveStorageFactory.StorageType.Filesystem);
		fsStorage.setBinaryData(tempArchive, data);
		InterfaceWrapperHelper.save(tempArchive);

		return tempArchive;
	}

	private void checkValid(final I_AD_Archive tempArchive)
	{
		final int expectedTableId = MTable.getTable_ID(org.compiere.model.I_AD_Archive.Table_Name);
		final int adTableId = tempArchive.getAD_Table_ID();
		Check.assume(adTableId == expectedTableId,
				"AD_Table_ID shall be {} (AD_Archive) and not {} for {}",
				expectedTableId, adTableId, tempArchive);

		Check.assume(!tempArchive.isFileSystem(), "Archive {} shall NOT be a filesystem archive", tempArchive);
	}
}
