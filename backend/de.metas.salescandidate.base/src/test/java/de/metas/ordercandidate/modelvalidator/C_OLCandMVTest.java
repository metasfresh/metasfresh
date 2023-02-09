package de.metas.ordercandidate.modelvalidator;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.ordercandidate.AbstractOLCandTestSupport;
import de.metas.ordercandidate.api.OLCandSPIRegistry;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.ordercandidate.location.OLCandLocationsUpdaterService;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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
		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bpartnerBL);
		final OLCandSPIRegistry olCandSPIRegistry = new OLCandSPIRegistry(
				Optional.empty(),
				Optional.empty(),
				Optional.empty());
		final OLCandValidatorService olCandValidatorService = new OLCandValidatorService(olCandSPIRegistry);
		final OLCandLocationsUpdaterService olCandLocationsUpdaterService = new OLCandLocationsUpdaterService(new DocumentLocationBL(bpartnerBL));

		// Initialize C_OLCand MV Only!
		final C_OLCand orderCandidateMV = new C_OLCand(bpartnerBL, olCandValidatorService, olCandLocationsUpdaterService);
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
		olCand.setC_BPartner_ID(bpartner1.getC_BPartner_ID());
		olCand.setM_Product_ID(product1.getM_Product_ID());

		final String customProductDescription = "customDescription";
		olCand.setProductDescription(customProductDescription);
		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assertions.assertEquals(olCand.getProductDescription(), customProductDescription);
	}

	@Test
	public void testMVSetProductDescriptionFallback_BPP_ProductDescription()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner_ID(bpartner1.getC_BPartner_ID());
		olCand.setM_Product_ID(product1.getM_Product_ID());

		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assertions.assertEquals(olCand.getProductDescription(), bpp1.getProductDescription());
	}

	@Test
	public void testMVSetProductDescriptionFallback_BPP_ProductName()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner_ID(bpartner2.getC_BPartner_ID());
		olCand.setM_Product_ID(product2.getM_Product_ID());

		InterfaceWrapperHelper.save(olCand);

		// Assert same product description after saving (MV shall ignore)
		Assertions.assertEquals(olCand.getProductDescription(), bpp2.getProductName());
	}

	/** verifies that the ProductDescription is *not* set if it would be simply identical to the product's name. */
	@Test
	public void testMVSetProductDescription_not_additional_value_provided()
	{
		final IContextAware context = PlainContextAware.newOutOfTrx(ctx);

		final I_C_OLCand olCand = olCand(context, I_C_OLCand.class, false); // save=false
		olCand.setC_BPartner_ID(bpartner3.getC_BPartner_ID());
		olCand.setM_Product_ID(product3.getM_Product_ID());

		InterfaceWrapperHelper.save(olCand);

		assertThat(olCand.getProductDescription()).isNull();
	}

	@Test
	public void testSalesRepSameIdAsBPartner()
	{
		final I_C_OLCand olCand = newInstance(I_C_OLCand.class);
		olCand.setC_BPartner_ID(bpartner1.getC_BPartner_ID());
		olCand.setC_BPartner_SalesRep_ID(bpartner1.getC_BPartner_ID());

		Assertions.assertThrows(AdempiereException.class, () -> save(olCand));
	}
}
