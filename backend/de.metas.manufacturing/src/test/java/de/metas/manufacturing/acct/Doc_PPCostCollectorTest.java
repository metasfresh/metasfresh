package de.metas.manufacturing.acct;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
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
import de.metas.acct.api.AcctSchemaId;
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
import de.metas.banking.accounting.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cost.classification.CostClassificationRepository;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.AverageInvoiceCostingMethodHandler;
import de.metas.costing.methods.AveragePOCostingMethodHandler;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costing.methods.StandardCostingMethodHandler;
import de.metas.currency.CurrencyRepository;
import de.metas.document.dimension.DimensionService;
import de.metas.elementvalue.ChartOfAccountsRepository;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.elementvalue.ElementValueService;
import de.metas.i18n.ExplainedOptional;
import de.metas.invoice.acct.InvoiceAcctRepository;
import de.metas.invoice.matchinv.listeners.MatchInvListenersRegistry;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.order.costs.OrderCostRepository;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.OrderCostTypeRepository;
import de.metas.order.costs.inout.InOutCostRepository;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.sales_region.SalesRegionRepository;
import de.metas.sales_region.SalesRegionService;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.tools.AdempiereToolsHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
				new CostClassificationRepository(),
				new GLCategoryRepository(),
				bankAccountService,
				accountProviderFactory,
				new InvoiceAcctRepository(),
				matchInvoiceService,
				orderCostService,
				new FAOpenItemsService(elementValueService, Optional.empty()),
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

/**
 * Covers {@code createFacts_CoProductReceipt}'s three outcomes for
 * {@code DocLine_CostCollector#getCreateCosts(AcctSchema)} — present (incl. zero-amount, kept), empty on a
 * reversal line (log + continue, no throw), empty on a normal receipt (throw, mirroring
 * {@code createFacts_MaterialReceipt.orElseThrow()}).
 */
class Doc_PPCostCollectorTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1);
	private static final OrgId ORG_ID = OrgId.ofRepoId(0);
	private static final ProductId CO_PRODUCT_ID = ProductId.ofRepoId(2101);

	/**
	 * The pure branch-selection seam ({@link Doc_PPCostCollector#resolveCoProductCostResult}) — no Doc/Fact
	 * machinery needed, since it is exercised BEFORE any account resolution or Fact building.
	 */
	@Nested
	class ResolveCoProductCostResult
	{
		@Test
		void present_zeroAmount_returnedUnchanged_regardlessOfReversalFlag()
		{
			final AggregatedCostAmount zeroResult = zeroAggregatedCostAmount();
			final ExplainedOptional<AggregatedCostAmount> present = ExplainedOptional.of(zeroResult);

			// branch 1 (KEEP): present is present, whether or not the line happens to be a reversal.
			assertThat(Doc_PPCostCollector.resolveCoProductCostResult(false, present)).isSameAs(zeroResult);
			assertThat(Doc_PPCostCollector.resolveCoProductCostResult(true, present)).isSameAs(zeroResult);
		}

		@Test
		void emptyOnNormalReceipt_throws()
		{
			final ExplainedOptional<AggregatedCostAmount> empty = ExplainedOptional.emptyBecause("no accountable cost elements");

			// branch 3: normal (non-reversal) receipt, empty result -> exceptional, mirrors .orElseThrow().
			assertThatThrownBy(() -> Doc_PPCostCollector.resolveCoProductCostResult(false, empty))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void emptyOnReversalLine_logsReasonAndReturnsNull_noThrow()
		{
			final Logger logbackLogger = (Logger)LoggerFactory.getLogger(Doc_PPCostCollector.class);
			final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();
			logAppender.start();
			logbackLogger.addAppender(logAppender);
			try
			{
				final ExplainedOptional<AggregatedCostAmount> empty =
						ExplainedOptional.emptyBecause("nothing to reverse - no cost details on the original receipt");

				// branch 2: reversal line, empty result -> legitimately nothing to reverse: log + continue, no throw.
				final AggregatedCostAmount result = Doc_PPCostCollector.resolveCoProductCostResult(true, empty);

				assertThat(result).isNull();
				assertThat(logAppender.list)
						.anyMatch(event -> event.getFormattedMessage().contains("nothing to reverse - no cost details on the original receipt"));
			}
			finally
			{
				logbackLogger.detachAppender(logAppender);
			}
		}

		private AggregatedCostAmount zeroAggregatedCostAmount()
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(1);
			final CostElement costElement = CostElement.builder()
					.id(CostElementId.ofRepoId(1))
					.name("Material")
					.costElementType(CostElementType.Material)
					.costingMethod(CostingMethod.AveragePO)
					.allowUserChangingCurrentCosts(false)
					.clientId(CLIENT_ID)
					.build();
			final CostSegment costSegment = CostSegment.builder()
					.costingLevel(CostingLevel.Client)
					.acctSchemaId(AcctSchemaId.ofRepoId(1))
					.costTypeId(CostTypeId.ofRepoId(1))
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.productId(CO_PRODUCT_ID)
					.attributeSetInstanceId(AttributeSetInstanceId.NONE)
					.build();
			return AggregatedCostAmount.builder()
					.costSegment(costSegment)
					.amount(costElement, CostAmountDetailed.zero(currencyId))
					.build();
		}
	}

	// NOTE: exercising the real Doc_PPCostCollector/DocLine_CostCollector object graph (constructing a real
	// PP_Cost_Collector row into a Doc via AcctDocContext) is not possible under this module's plain-JUnit
	// harness: DocLine's constructor requires InterfaceWrapperHelper.getPO(...), which the POJO/in-memory
	// test wrapper (org.adempiere.ad.wrapper.POJOInterfaceWrapperHelper.getPO) unconditionally throws
	// UnsupportedOperationException("... is not supported in JUnit testing mode") for — confirmed empirically
	// (and consistent with there being zero other JUnit tests anywhere in this codebase that call
	// Doc*.createFacts(...); the only place that does, Post_CostCollectors_Now_ManualTest above, is @Disabled
	// and requires a real, non-POJO environment via AdempiereToolsHelper.startupMinimal()). That is exactly why
	// the branch selection is covered here via the extracted, dependency-free resolveCoProductCostResult(...)
	// seam instead of through the full createFacts(AcctSchema) call site.
}