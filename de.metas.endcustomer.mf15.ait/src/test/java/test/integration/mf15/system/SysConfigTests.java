package test.integration.mf15.system;

import static org.junit.Assert.assertEquals;

import org.compiere.util.Env;
import org.junit.Test;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
//import de.metas.datev_export.modelvalidator.InvoiceValidator;

/**
 * Tests make sure that all AD_SysConfig settings are correct for customer.
 * 
 * @author ts
 * 
 */
public class SysConfigTests extends AIntegrationTestDriver
{

	private static final int AD_ORG_ID_customer = 1000000;
	private static final int AD_CLIENT_ID = 1000000;

	@Override
	public Helper newHelper()
	{
		return new Helper();
	}

	@Test
	public void env()
	{
		assertEquals(Env.getAD_Client_ID(Env.getCtx()), AD_CLIENT_ID);
		assertEquals(Env.getAD_Org_ID(Env.getCtx()), AD_ORG_ID_customer);
	}

	
}
