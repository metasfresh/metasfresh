package de.metas.fresh.ordercheckup;

/*
 * #%L
 * de.metas.fresh.base
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


import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.junit.Assert;

import de.metas.adempiere.model.I_M_Product;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.X_C_Order_MFGWarehouse_Report;

public class Masterdata
{
	private final OrderCheckupTestHelper helper;
	public final Plant plant01;
	public final Plant plant02;

	Masterdata(final OrderCheckupTestHelper helper)
	{
		super();
		this.helper = helper;
		this.plant01 = new Plant("plant01");
		this.plant02 = new Plant("plant02");
	}

	public class Plant
	{
		public final String name;
		public final I_AD_User responsibleUser;
		public final I_S_Resource plant;
		public final Warehouse warehouse01;
		public final Warehouse warehouse02;

		private Plant(final String name)
		{
			super();
			this.name = name;
			responsibleUser = helper.createAD_User(name);
			plant = helper.createPlant(name, responsibleUser);
			warehouse01 = new Warehouse("warehouse01");
			warehouse02 = new Warehouse("warehouse02");
		}

		public I_C_Order_MFGWarehouse_Report retrievePlantReport()
		{
			return helper.retrieveReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Plant, null, plant);
		}

		public void assertPlantReportOrderLines(final I_C_OrderLine... expectedOrderLines)
		{
			final I_C_Order_MFGWarehouse_Report report = retrievePlantReport();
			Assert.assertNotNull("Plant report exists for " + plant.getName(), report);
			Assert.assertEquals("Plant", plant.getS_Resource_ID(), report.getPP_Plant_ID());
			Assert.assertNull("Warehouse", report.getM_Warehouse());
			Assert.assertEquals("Responsible", responsibleUser.getAD_User_ID(), report.getAD_User_Responsible_ID());
			Assert.assertTrue("Processed", report.isProcessed());
			Assert.assertTrue("Active", report.isActive());

			helper.assertReportHasOrderLines(report, expectedOrderLines);
		}

		public class Warehouse
		{
			public final String name;
			public final I_AD_User responsibleUser;
			public final I_M_Warehouse warehouse;
			public final I_M_Product product01;
			public final I_M_Product product02;

			private Warehouse(final String name)
			{
				super();
				this.name = Plant.this.name + "_" + name;
				responsibleUser = helper.createAD_User(this.name);

				warehouse = helper.createWarehouse(this.name, plant);

				product01 = helper.createProduct(this.name + "_product01", warehouse, responsibleUser);
				product02 = helper.createProduct(this.name + "_product02", warehouse, responsibleUser);
			}

			public I_C_Order_MFGWarehouse_Report retrieveWarehouseReport()
			{
				return helper.retrieveReport(X_C_Order_MFGWarehouse_Report.DOCUMENTTYPE_Warehouse, warehouse, plant);
			}

			public void assertWarehouseReportOrderLines(final I_C_OrderLine... expectedOrderLines)
			{
				final I_C_Order_MFGWarehouse_Report report = retrieveWarehouseReport();
				Assert.assertNotNull("Warehouse report exists for " + warehouse.getName(), report);
				Assert.assertEquals("Plant", warehouse.getPP_Plant_ID(), report.getPP_Plant_ID());
				Assert.assertEquals("Warehouse", warehouse.getM_Warehouse_ID(), report.getM_Warehouse_ID());
				Assert.assertEquals("Responsible", responsibleUser.getAD_User_ID(), report.getAD_User_Responsible_ID());
				Assert.assertTrue("Processed", report.isProcessed());
				Assert.assertTrue("Active", report.isActive());
				helper.assertReportHasOrderLines(report, expectedOrderLines);
			}
		}

	}
}
