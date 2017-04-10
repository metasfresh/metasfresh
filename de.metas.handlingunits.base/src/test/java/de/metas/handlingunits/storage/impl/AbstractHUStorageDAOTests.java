package de.metas.handlingunits.storage.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU_Storage;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AbstractHUStorageDAOTests
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Verifies that {@link AbstractHUStorageDAO#getC_UOMOrNull(java.util.List)} returns the storage's UOM if there is just a single storage.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1282
	 */
	@Test
	public void testGetC_UOMOrNull_single_storage()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMType(null);
		InterfaceWrapperHelper.save(uom);

		final I_M_HU_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage.setC_UOM(uom);
		InterfaceWrapperHelper.save(storage);

		final I_C_UOM result = new HUStorageDAO().getC_UOMOrNull(ImmutableList.of(storage));

		assertThat(result, notNullValue());
		assertThat(result, is(uom));
	}

	/**
	 * Verifies that {@link AbstractHUStorageDAO#getC_UOMOrNull(java.util.List)} returns the storage's UOM if there are two storages with the same UOM.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1282
	 */
	@Test
	public void testGetC_UOMOrNull_two_Storages_same_UOM()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMType(null);
		InterfaceWrapperHelper.save(uom);

		final I_M_HU_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage.setC_UOM(uom);
		InterfaceWrapperHelper.save(storage);

		final I_M_HU_Storage storage2 = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage2.setC_UOM(uom);
		InterfaceWrapperHelper.save(storage2);

		final I_C_UOM result = new HUStorageDAO().getC_UOMOrNull(ImmutableList.of(storage, storage2));

		assertThat(result, notNullValue());
		assertThat(result, is(uom));
	}

	/**
	 * Verifies that {@link AbstractHUStorageDAO#getC_UOMOrNull(java.util.List)} returns the <b>first</b> storage's UOM if there are two storages with different UOMs that have the same UOMType.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1282
	 */
	@Test
	public void testGetC_UOMOrNull_two_Storages_different_compatible_UOMs()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMType("type1");
		InterfaceWrapperHelper.save(uom);
		final I_M_HU_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage.setC_UOM(uom);
		InterfaceWrapperHelper.save(storage);

		final I_C_UOM uom2 = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom2.setUOMType("type1");
		InterfaceWrapperHelper.save(uom2);
		final I_M_HU_Storage storage2 = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage2.setC_UOM(uom2);
		InterfaceWrapperHelper.save(storage2);

		final I_C_UOM result = new HUStorageDAO().getC_UOMOrNull(ImmutableList.of(storage, storage2));

		assertThat(result, notNullValue());
		assertThat(result, is(uom));
	}

	/**
	 * Verifies that {@link AbstractHUStorageDAO#getC_UOMOrNull(java.util.List)} returns {@code null} if there are two storages with different UOMs that have different UOMTypes.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1282
	 */
	@Test
	public void testGetC_UOMOrNull_two_Storages_different_incompatible_UOMs()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMType("type1");
		InterfaceWrapperHelper.save(uom);
		final I_M_HU_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage.setC_UOM(uom);
		InterfaceWrapperHelper.save(storage);

		final I_C_UOM uom2 = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom2.setUOMType("type2");
		InterfaceWrapperHelper.save(uom2);
		final I_M_HU_Storage storage2 = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage2.setC_UOM(uom2);
		InterfaceWrapperHelper.save(storage2);

		final I_C_UOM result = new HUStorageDAO().getC_UOMOrNull(ImmutableList.of(storage, storage2));
		assertThat(result, nullValue());
	}

	/**
	 * Verifies that {@link AbstractHUStorageDAO#getC_UOMOrNull(java.util.List)} returns {@code null} if there are two storages with different UOMs that both have no UOMType.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1282
	 */
	@Test
	public void testGetC_UOMOrNull_two_Storages_different_incompatible_UOMs_2()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMType(null);
		InterfaceWrapperHelper.save(uom);
		final I_M_HU_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage.setC_UOM(uom);
		InterfaceWrapperHelper.save(storage);

		final I_C_UOM uom2 = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom2.setUOMType(null);
		InterfaceWrapperHelper.save(uom2);
		final I_M_HU_Storage storage2 = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		storage2.setC_UOM(uom2);
		InterfaceWrapperHelper.save(storage2);

		final I_C_UOM result = new HUStorageDAO().getC_UOMOrNull(ImmutableList.of(storage, storage2));
		assertThat(result, nullValue());
	}
}
