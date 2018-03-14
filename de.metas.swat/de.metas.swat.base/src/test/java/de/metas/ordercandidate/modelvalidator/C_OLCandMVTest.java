package de.metas.ordercandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.ordercandidate.AbstractOLCandTestSupport;
import de.metas.ordercandidate.api.OLCandRegistry;
import de.metas.ordercandidate.model.I_C_OLCand;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		OLCandRegistry.class })
public class C_OLCandMVTest extends AbstractOLCandTestSupport
{
	private final Properties ctx;

	private I_M_Product product1;
	private I_M_Product product2;
	private I_M_Product product3;

	private I_C_BPartner bpartner1;
	private I_C_BPartner bpartner2;
	private I_C_BPartner bpartner3;

	private I_C_BPartner_Product bpp1;
	private I_C_BPartner_Product bpp2;
	private I_C_BPartner_Product bpp3;

	private I_AD_Org org1;

	public C_OLCandMVTest()
	{
		ctx = Env.getCtx();
	}

	@Override
	protected void initModelValidators()
	{
		// Initialize C_OLCand MV Only!
		final C_OLCand orderCandidateMV = new C_OLCand();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(orderCandidateMV, null);
	}

	@Override
	protected final void initDB()
	{
		// Org
		{
			org1 = org("Org1");
		}

		// C_BPartners
		{
			bpartner1 = bpartner("G001");
			bpartner2 = bpartner("G002");
			bpartner3 = bpartner("G003");
		}

		// M_Products
		{
			product1 = product("product1", 100000, org1.getAD_Org_ID());
			product2 = product("product2", 100001, org1.getAD_Org_ID());
			product3 = product("product3", 100002, org1.getAD_Org_ID());
		}

		// C_BPartner_Products
		{
			bpp1 = bpartnerProduct(bpartner1, product1, org1);
			bpp1.setProductDescription("bpp1.ProductDescription");
			InterfaceWrapperHelper.save(bpp1);

			bpp2 = bpartnerProduct(bpartner2, product2, org1);
			bpp2.setProductName("bpp1.Product1Name");
			InterfaceWrapperHelper.save(bpp2);

			bpp3 = bpartnerProduct(bpartner3, product3, org1); // duplicate, with nothing set
			InterfaceWrapperHelper.save(bpp3);
		}
	}

	@Test
	public void testMVSetProductDescriptionNoFallback()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner(bpartner1);
		olCand.setM_Product(product1);

		final String customProductDescription = "customDescription";
		olCand.setProductDescription(customProductDescription);
		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assert.assertEquals(olCand.getProductDescription(), customProductDescription);
	}

	@Test
	public void testMVSetProductDescriptionFallback_BPP_ProductDescription()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner(bpartner1);
		olCand.setM_Product(product1);

		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assert.assertEquals(olCand.getProductDescription(), bpp1.getProductDescription());
	}

	@Test
	public void testMVSetProductDescriptionFallback_BPP_ProductName()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner(bpartner2);
		olCand.setM_Product(product2);

		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assert.assertEquals(olCand.getProductDescription(), bpp2.getProductName());
	}

	@Test
	public void testMVSetProductDescriptionFallback_MProduct_Name()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner(bpartner3);
		olCand.setM_Product(product3);

		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assert.assertEquals(olCand.getProductDescription(), product3.getName());
	}
}
