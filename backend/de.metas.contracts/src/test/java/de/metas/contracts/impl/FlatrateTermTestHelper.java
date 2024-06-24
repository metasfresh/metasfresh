package de.metas.contracts.impl;

import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.inoutcandidate.SubscriptionShipmentScheduleHandler;
import de.metas.contracts.interceptor.MainValidator;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.pricing.ContractDiscount;
import de.metas.contracts.pricing.SubscriptionPricingRule;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.order.compensationGroup.FlatrateConditionsExcludedProductsRepository;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.pricing.rules.Discount;
import de.metas.pricing.rules.price_list_version.PriceListVersionPricingRule;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.util.Env;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * This class sets up basic master data like partners, addresses, users, flatrate conditions, flarate transitions that can be used in testing.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class FlatrateTermTestHelper
{
	private final String invoiceCandClassname = FlatrateTerm_Handler.class.getName();
	private final String shipmentCandClassname = SubscriptionShipmentScheduleHandler.class.getName();

	private final Map<String, String> pricingRules = new HashMap<>();

	//
	// Initialization flags
	private boolean initialized = false;

	public I_AD_Client adClient;
	public I_AD_Org adOrg;

	public Properties ctx;
	public String trxName;

	public final IContextAware contextProvider = new IContextAware()
	{
		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getTrxName()
		{
			return trxName;
		}
	};

	public Properties getCtx()
	{
		return ctx;
	}

	public I_AD_Client getClient()
	{
		return adClient;
	}

	public I_AD_Org getOrg()
	{
		return adOrg;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public IContextAware getContextProvider()
	{
		return contextProvider;
	}

	/**
	 * Invokes {@link #FlatrateTermTestHelper(boolean)} with init=<code>true</code>.
	 */
	public FlatrateTermTestHelper()
	{
		this(true);
	}

	/**
	 * @param init if <code>true</code>, the constructor directly calls {@link #init()}.
	 */
	public FlatrateTermTestHelper(final boolean init)
	{
		if (init)
		{
			init();
		}
	}

	/**
	 * Final, because it's called by a constructor.
	 */
	public final void init()
	{
		if (initialized)
		{
			return;
		}

		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

		adClient = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(adClient);
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClient.getAD_Client_ID());

		adOrg = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(adOrg);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrg.getAD_Org_ID());

		Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
				.orgId(OrgId.ofRepoId(adOrg.getAD_Org_ID()))
				.build());

		addPricingRules();
		createPricingRules();

		createCILCandHandler();
		createMIolCandHandler();

		initialized = true;

		afterInitialized();
	}

	private void addPricingRules()
	{
		pricingRules.put("PriceListVersion", PriceListVersionPricingRule.class.getName());
		pricingRules.put("Discount", Discount.class.getName());
		pricingRules.put("de.metas.contracts Discount", ContractDiscount.class.getName());
		pricingRules.put("de.metas.contracts Subscription", SubscriptionPricingRule.class.getName());
	}

	private void createPricingRules()
	{
		pricingRules.forEach((name, classname) -> {
			final I_C_PricingRule priceRule = newInstance(I_C_PricingRule.class);
			priceRule.setName(name);
			priceRule.setClassname(classname);
			priceRule.setEntityType("D");
			save(priceRule);
		});
	}

	protected void afterInitialized()
	{
		// nothing here
	}

	/**
	 * Setup module interceptors: "de.metas.contracts" module - FULL (interceptors, factories, etc), like in production (used by some integration tests).
	 */
	public final void setupModuleInterceptors_Contracts_Full()
	{
		final ContractOrderService contractOrderService = new ContractOrderService();

		final IDocumentLocationBL documentLocationBL = new DummyDocumentLocationBL(new BPartnerBL(new UserRepository()));

		final OrderGroupCompensationChangesHandler groupChangesHandler = new OrderGroupCompensationChangesHandler(
				new OrderGroupRepository(
						new GroupCompensationLineCreateRequestFactory(),
						Optional.empty() // advisors
				),
				new GroupTemplateRepository(Optional.empty()),
				new FlatrateConditionsExcludedProductsRepository());

		final InOutLinesWithMissingInvoiceCandidate inoutLinesWithMissingInvoiceCandidateRepo = new InOutLinesWithMissingInvoiceCandidate();

		final MainValidator mainInterceptor = new MainValidator(
				contractOrderService,
				documentLocationBL,
				groupChangesHandler,
				inoutLinesWithMissingInvoiceCandidateRepo,
				new CallOrderContractService());

		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(mainInterceptor);
	}

	private void createCILCandHandler()
	{
		final I_C_ILCandHandler handler = newInstance(I_C_ILCandHandler.class);
		handler.setName(invoiceCandClassname);
		handler.setClassname(invoiceCandClassname);
		handler.setTableName(I_C_Flatrate_Term.Table_Name);
		save(handler);
	}

	private void createMIolCandHandler()
	{
		final I_M_IolCandHandler handler = newInstance(I_M_IolCandHandler.class);
		handler.setClassname(shipmentCandClassname);
		handler.setIsActive(true);
		handler.setTableName(I_C_SubscriptionProgress.Table_Name);
		save(handler);
	}
}
