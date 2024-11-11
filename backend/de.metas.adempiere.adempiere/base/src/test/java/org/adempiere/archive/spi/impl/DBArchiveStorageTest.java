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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

public class DBArchiveStorageTest
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	private DBArchiveStorage storage;

	@Before
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
		final I_AD_Archive archive = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Archive.class, ITrx.TRXNAME_None);
		final byte[] data = createTestDataBytes();
		storage.setBinaryData(archive, data);
		InterfaceWrapperHelper.save(archive);

		Assert.assertFalse("Invalid IsFileSystem flag", archive.isFileSystem());

		final byte[] dataActual = storage.getBinaryData(archive);
		Assert.assertArrayEquals("Invalid data", data, dataActual);
	}

	private final Random random = new Random();

	private byte[] createTestDataBytes()
	{
		final byte[] data = new byte[4096];
		random.nextBytes(data);
		return data;
	}

}
