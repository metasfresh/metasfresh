package de.metas.handlingunits.movement.api.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.model.I_M_Warehouse;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.util.Services;

/**
 * Tests {@link HUMovementBL}.
 *
 * @author RC
 *
 */
public class HUMovementBLTest
{
	/** Service under test */
	private HUMovementBL huMovementBL;

	private IContextAware context;
	private AcctSchemaId acctSchemaId;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = PlainContextAware.newOutOfTrx();

		//
		// Service under test
		huMovementBL = (HUMovementBL)Services.get(IHUMovementBL.class);

		//
		// Master data
		// acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		acctSchemaId = AcctSchemaId.ofRepoId(1);
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07689_Korrektur_zu_Kostenstellenwechsel_%28102909571093%29
	 */
	@Test
	public void test_setPackingMaterialCActivity()
	{
		final I_C_Activity productActivity = createActivity();
		final I_M_Product product = createProduct(productActivity);
		final I_M_MovementLine movementLine = createMovementLine(product);

		huMovementBL.setPackingMaterialCActivity(movementLine);

		Assert.assertEquals("Movement line shall have the activity of the product", productActivity, movementLine.getC_Activity());
	}

	private I_M_Product createProduct(final I_C_Activity activity)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, context);
		InterfaceWrapperHelper.save(product);

		final I_M_Product_Acct productAcct = InterfaceWrapperHelper.newInstance(I_M_Product_Acct.class, context);
		productAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		productAcct.setM_Product(product);
		productAcct.setC_Activity(activity);
		InterfaceWrapperHelper.save(productAcct);

		return product;
	}

	private I_M_MovementLine createMovementLine(final I_M_Product product)
	{
		final I_M_Locator locatorTo = createLocator();

		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, context);
		InterfaceWrapperHelper.save(org);

		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, context);
		movementLine.setM_Product(product);
		movementLine.setM_LocatorTo(locatorTo);
		movementLine.setAD_Org(org);
		InterfaceWrapperHelper.save(movementLine);

		return movementLine;
	}

	private I_M_Locator createLocator()
	{
		final I_C_Activity activity = createActivity();

		final I_M_Warehouse warehouseDest = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, context);
		warehouseDest.setC_Activity_ID(activity.getC_Activity_ID());
		InterfaceWrapperHelper.save(warehouseDest);

		final I_M_Locator locatorTo = InterfaceWrapperHelper.newInstance(I_M_Locator.class, context);
		locatorTo.setM_Warehouse_ID(warehouseDest.getM_Warehouse_ID());
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
