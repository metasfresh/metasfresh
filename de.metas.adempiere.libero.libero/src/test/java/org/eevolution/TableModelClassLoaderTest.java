package org.eevolution;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.ad.persistence.TableModelClassLoaderTester;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.MPPCostCollector;
import org.junit.Before;
import org.junit.Test;

/**
 * Makes sure the right libero classes are loaded.
 *
 * @author tsa
 *
 */
public class TableModelClassLoaderTest
{
	private TableModelClassLoaderTester tester = new TableModelClassLoaderTester()
			.setEntityTypeModelPackage(LiberoConstants.ENTITYTYPE_Manufacturing, "org.eevolution.model");

	@Before
	public void setup()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Test if libero classes were correctly identified
	 */
	@Test
	public void test()
	{
		tester.setTableNameEntityType(I_PP_Cost_Collector.Table_Name, LiberoConstants.ENTITYTYPE_Manufacturing)
				.assertClass(I_PP_Cost_Collector.Table_Name, MPPCostCollector.class)
				.cacheReset()
				.assertClass(I_PP_Cost_Collector.Table_Name, MPPCostCollector.class);
	}

}
