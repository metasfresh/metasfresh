package de.metas.manufacturing.acct;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.GLCategoryRepository;
import de.metas.acct.accounts.AccountProviderFactory;
import de.metas.acct.accounts.BPartnerAccountsRepository;
import de.metas.acct.accounts.BPartnerGroupAccountsRepository;
import de.metas.acct.accounts.ChargeAccountsRepository;
import de.metas.acct.accounts.CostElementAccountsRepository;
import de.metas.acct.accounts.ProductAccountsRepository;
import de.metas.acct.accounts.ProductCategoryAccountsRepository;
import de.metas.acct.accounts.ProjectAccountsRepository;
import de.metas.acct.accounts.TaxAccountsRepository;
import de.metas.acct.accounts.WarehouseAccountsRepository;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.acct.doc.POAcctDocModel;
import de.metas.acct.doc.SqlAcctDocLockService;
import de.metas.acct.factacct_userchanges.FactAcctUserChangesRepository;
import de.metas.acct.factacct_userchanges.FactAcctUserChangesService;
import de.metas.acct.open_items.FAOpenItemsService;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryOverJdbc;
import de.metas.ad_reference.AdRefTableRepositoryOverJdbc;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.cache.model.impl.ModelCacheInvalidationService;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.AverageInvoiceCostingMethodHandler;
import de.metas.costing.methods.AveragePOCostingMethodHandler;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costing.methods.StandardCostingMethodHandler;
import de.metas.currency.CurrencyRepository;
import de.metas.document.dimension.DimensionService;
import de.metas.elementvalue.ChartOfAccountsRepository;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.elementvalue.ElementValueService;
import de.metas.invoice.acct.InvoiceAcctRepository;
import de.metas.invoice.matchinv.listeners.MatchInvListenersRegistry;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.MoneyService;
import de.metas.order.costs.OrderCostRepository;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.OrderCostTypeRepository;
import de.metas.order.costs.inout.InOutCostRepository;
import de.metas.sales_region.SalesRegionRepository;
import de.metas.sales_region.SalesRegionService;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.tools.AdempiereToolsHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.Disabled;

import java.util.List;
import java.util.Optional;

@Disabled
class Post_CostCollectors_Now_ManualTest
{
	void run()
	{
		postPP_Cost_Collectors(1000667, 1000766);
	}

	private final IQueryBL queryBL;
	private final AcctDocRequiredServicesFacade acctDocRequiredServicesFacade;
	private final List<AcctSchema> acctSchemas;

	public static void main(String[] args) {new Post_CostCollectors_Now_ManualTest().run();}

	Post_CostCollectors_Now_ManualTest()
	{
		AdempiereToolsHelper.getInstance().startupMinimal();

		this.queryBL = Services.get(IQueryBL.class);
		this.acctDocRequiredServicesFacade = newAcctDocRequiredServicesFacade();
		this.acctSchemas = Services.get(IAcctSchemaDAO.class).getAllByClient(ClientId.METASFRESH);

		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	private static AcctDocRequiredServicesFacade newAcctDocRequiredServicesFacade()
	{
		final ElementValueService elementValueService = new ElementValueService(
				new ElementValueRepository(),
				new TreeNodeService(new TreeNodeRepository(), new ChartOfAccountsService(new ChartOfAccountsRepository()))
		);

		final CurrencyRepository currenciesRepo = new CurrencyRepository();
		final @NonNull BankAccountService bankAccountService = new BankAccountService(
				new BankRepository(),
				currenciesRepo
		);
		final AccountProviderFactory accountProviderFactory = new AccountProviderFactory(
				new ProductAccountsRepository(),
				new ProductCategoryAccountsRepository(),
				new TaxAccountsRepository(),
				new BPartnerAccountsRepository(),
				new BPartnerGroupAccountsRepository(),
				new BankAccountAcctRepository(),
				new ChargeAccountsRepository(),
				new WarehouseAccountsRepository(),
				new ProjectAccountsRepository(),
				new CostElementAccountsRepository()
		);
		final MatchInvoiceService matchInvoiceService = new MatchInvoiceService(
				new MatchInvoiceRepository(),
				new MatchInvListenersRegistry(Optional.empty())
		);
		final MoneyService moneyService = new MoneyService(currenciesRepo);
		final OrderCostService orderCostService = new OrderCostService(
				new OrderCostRepository(),
				new OrderCostTypeRepository(),
				new InOutCostRepository(),
				matchInvoiceService,
				moneyService
		);

		final ADReferenceService adReferenceService = new ADReferenceService(
				new AdRefListRepositoryOverJdbc(),
				new AdRefTableRepositoryOverJdbc()
		);
		final CostElementRepository costElementRepo = new CostElementRepository(adReferenceService);
		final CostDetailService costDetailsService = new CostDetailService(new CostDetailRepository(), costElementRepo);
		final CurrentCostsRepository currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostingMethodHandlerUtils costingMethodHandlerUtils = new CostingMethodHandlerUtils(
				currenciesRepo,
				currentCostsRepo,
				costDetailsService
		);
		CostingService costingService = new CostingService(
				costingMethodHandlerUtils,
				costDetailsService,
				costElementRepo,
				currentCostsRepo,
				ImmutableList.of(
						new AveragePOCostingMethodHandler(
								costingMethodHandlerUtils,
								matchInvoiceService,
								orderCostService
						),
						new AverageInvoiceCostingMethodHandler(costingMethodHandlerUtils),
						new StandardCostingMethodHandler(costingMethodHandlerUtils)
				)
		);

		return new AcctDocRequiredServicesFacade(
				ModelCacheInvalidationService.newInstanceForUnitTesting(),
				elementValueService,
				new GLCategoryRepository(),
				bankAccountService,
				accountProviderFactory,
				new InvoiceAcctRepository(),
				matchInvoiceService,
				orderCostService,
				new FAOpenItemsService(Optional.empty()),
				costingService,
				new DimensionService(ImmutableList.of()),
				new SalesRegionService(new SalesRegionRepository()),
				new SqlAcctDocLockService(),
				new FactAcctUserChangesService(new FactAcctUserChangesRepository())
		);
	}

	private void postPP_Cost_Collectors(@NonNull final Integer... ids)
	{
		if (ids.length == 0)
		{
			return;
		}

		final ImmutableMap<Integer, I_PP_Cost_Collector> recordsById = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addInArrayFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID, ids)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(I_PP_Cost_Collector::getPP_Cost_Collector_ID, cc -> cc));

		System.out.println("Posting: " + recordsById.values());

		final AcctDocContext.AcctDocContextBuilder contextTemplate = AcctDocContext.builder()
				.services(acctDocRequiredServicesFacade)
				.acctSchemas(acctSchemas);

		for (final int id : ids)
		{
			final I_PP_Cost_Collector documentModel = recordsById.get(id);
			
			final Doc_PPCostCollector doc = new Doc_PPCostCollector(contextTemplate.documentModel(toAcctDocModel(documentModel)).build());
			doc.post(true, true);
			System.out.println("Posted: " + documentModel);
		}
	}

	@NonNull
	private static POAcctDocModel toAcctDocModel(final Object record)
	{
		return new POAcctDocModel(LegacyAdapters.convertToPO(record));
	}

}