package org.adempiere.ad.persistence;

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


import org.compiere.model.MOrder;
import org.compiere.model.PO;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Order;

public class AdempiereTableModelClassLoaderTest
{
	private TableModelClassLoaderTester tester = new TableModelClassLoaderTester()
			.setEntityTypeModelPackage(PO.ENTITYTYPE_Dictionary, null);

	@Test
	public void test_LoadStandardClasses()
	{
		tester.setTableNameEntityType(I_C_Order.Table_Name, PO.ENTITYTYPE_Dictionary)
				.assertClass(I_C_Order.Table_Name, MOrder.class)
				.cacheReset()
				.assertClass(I_C_Order.Table_Name, MOrder.class);
	}

	@Test
	public void test_EntityTypesAreReloadedAfterCacheReset()
	{
		tester
				// Make sure our EntityType is not registeded yet
				.assertEntityTypeNotExists("MyEntityType")
				// Add our EntityType to the map of entity types which will be loaded after cache reset
				.setEntityTypeModelPackage("MyEntityType", null)
				// Atm there was no cache reset so we assume our EntityType is not yet there
				.assertEntityTypeNotExists("MyEntityType")
				// Do a cache reset, we expect the entity types to be loaded again
				// and now they shall contain our entity type
				.cacheReset()
				.assertEntityTypeExists("MyEntityType");
	}
}
