package org.adempiere.mmovement.api.impl;

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

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.impl.AcctSchemaDAO;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.model.I_M_Warehouse;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MovementBLTest
{
	/** Service under test */
	private MovementBL movementBL;

	private IContextAware context;
	private I_C_AcctSchema acctSchema;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = PlainContextAware.newOutOfTrx();

		//
		// Service under test
		movementBL = (MovementBL)Services.get(IMovementBL.class);

		//
		// Master data
		this.acctSchema = InterfaceWrapperHelper.newInstance(I_C_AcctSchema.class, context);
		InterfaceWrapperHelper.save(acctSchema);

		// Mock accounting schema retrieval
		Services.registerService(IAcctSchemaDAO.class, new AcctSchemaDAO()
		{
			@Override
			public I_C_AcctSchema retrieveAcctSchema(final Properties ctx, final int ad_Client_ID, final int ad_Org_ID)
			{
				return acctSchema;
			}
		});
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07689_Korrektur_zu_Kostenstellenwechsel_%28102909571093%29
	 */
	@Test
	public void test_setCActivity_warehouseActivity()
	{
		final I_C_Activity productActivity = createActivity();

		I_M_Product product = createProduct(productActivity);

		final I_C_Activity activityFrom = createActivity();
		final I_M_Locator locatorFrom = createLocator(activityFrom);

		final I_C_Activity activityTo = createActivity();
		final I_M_Locator locatorTo = createLocator(activityTo);

		final I_M_MovementLine movementLine = createMovementLine(product, locatorFrom, locatorTo);

		movementBL.setC_Activities(movementLine);

		Assert.assertEquals("Movement line shall have the activity of the warehouse", activityFrom, movementLine.getC_ActivityFrom());

		Assert.assertEquals("Movement line shall have the activity of the warehouse dest", activityTo, movementLine.getC_Activity());
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07689_Korrektur_zu_Kostenstellenwechsel_%28102909571093%29
	 */
	@Test
	public void test_setCActivity_productActivity()
	{
		final I_C_Activity productActivity = createActivity();
		final I_M_Product product = createProduct(productActivity);
		final I_M_Locator locatorFrom  =createLocator(null);
		final I_M_Locator locatorTo = createLocator(null);
		final I_M_MovementLine movementLine = createMovementLine(product, locatorFrom, locatorTo);

		movementBL.setC_Activities(movementLine);

		Assert.assertEquals("Movement line shall have the activity of the product", productActivity, movementLine.getC_Activity());
		Assert.assertEquals("Movement line shall have the activityFrom of the product", productActivity, movementLine.getC_ActivityFrom());
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07689_Korrektur_zu_Kostenstellenwechsel_%28102909571093%29
	 */
	@Test
	public void test_setCActivity_noActivity()
	{
		final I_M_Product product = createProduct(null);

		final I_M_Locator locatorFrom = createLocator(null);
		final I_M_Locator locatorTo = createLocator(null);

		final I_M_MovementLine movementLine = createMovementLine(product, locatorFrom, locatorTo);

		movementBL.setC_Activities(movementLine);

		Assert.assertNull("Movement line shall have the activity null", movementLine.getC_Activity());
		Assert.assertNull("Movement line shall have the activityFrom null", movementLine.getC_ActivityFrom());
	}

	private I_M_Product createProduct(final I_C_Activity activity)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, context);
		InterfaceWrapperHelper.save(product);

		final I_M_Product_Acct productAcct = InterfaceWrapperHelper.newInstance(I_M_Product_Acct.class, context);
		productAcct.setC_AcctSchema(acctSchema);
		productAcct.setM_Product(product);
		productAcct.setC_Activity(activity);
		InterfaceWrapperHelper.save(productAcct);

		return product;
	}

	private I_M_MovementLine createMovementLine(final I_M_Product product, final I_M_Locator locatorFrom,  final I_M_Locator locatorTo)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, context);
		InterfaceWrapperHelper.save(org);

		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, context);
		movementLine.setM_Product(product);
		movementLine.setM_Locator(locatorFrom);
		movementLine.setM_LocatorTo(locatorTo);
		movementLine.setAD_Org(org);
		InterfaceWrapperHelper.save(movementLine);

		return movementLine;
	}

	private I_M_Locator createLocator(final I_C_Activity activity)
	{
		final I_M_Warehouse warehouseDest = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, context);
		warehouseDest.setC_Activity(activity);
		InterfaceWrapperHelper.save(warehouseDest);

		final I_M_Locator locatorTo = InterfaceWrapperHelper.newInstance(I_M_Locator.class, context);
		locatorTo.setM_Warehouse(warehouseDest);
		InterfaceWrapperHelper.save(locatorTo);
		return locatorTo;
	}

	private I_C_Activity createActivity()
	{
		final I_C_Activity activity = InterfaceWrapperHelper.newInstance(I_C_Activity.class, context);
		InterfaceWrapperHelper.save(activity);
		return activity;
	}

}
