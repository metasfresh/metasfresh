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

import de.metas.archive.ArchiveStorageConfig;
import de.metas.archive.ArchiveStorageConfigId;
import de.metas.archive.ArchiveStorageType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Archive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class DBArchiveStorageTest
{
	private DBArchiveStorage storage;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		storage = new DBArchiveStorage(
				ArchiveStorageConfig.builder()
						.id(ArchiveStorageConfigId.DATABASE)
						.type(ArchiveStorageType.DATABASE)
						.build()
		);
	}

	@Test
	public void test_set_getBinaryData()
	{
		final I_AD_Archive archive = storage.newArchive();
		final byte[] data = createTestDataBytes();
		storage.setBinaryData(archive, data);
		InterfaceWrapperHelper.save(archive);

		assertThat(archive.getAD_Archive_Storage_ID()).isEqualTo(ArchiveStorageConfigId.DATABASE.getRepoId());
		assertThat(storage.getBinaryData(archive)).as("Invalid data").containsExactly(data);
	}

	private final Random random = new Random();

	private byte[] createTestDataBytes()
	{
		final byte[] data = new byte[4096];
		random.nextBytes(data);
		return data;
	}

}
