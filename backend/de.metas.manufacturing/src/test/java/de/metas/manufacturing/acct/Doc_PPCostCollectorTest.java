package de.metas.manufacturing.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.doc.AcctDocContext;
import de.metas.acct.doc.AcctDocRequiredServicesFacade;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.AverageInvoiceCostingMethodHandler;
import de.metas.costing.methods.AveragePOCostingMethodHandler;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.costing.methods.ManufacturingAveragePOCostingMethodHandler;
import de.metas.costing.methods.ManufacturingStandardCostingMethodHandler;
import de.metas.costing.methods.StandardCostingMethodHandler;
import de.metas.currency.CurrencyRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.junit.jupiter.api.Disabled;

import java.util.List;

@Disabled
class Post_CostCollectors_Now_ManualTest
{
	void run()
	{
		postPP_Cost_Collectors(1000667);
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
		final CurrencyRepository currenciesRepo = new CurrencyRepository();
		final @NonNull BankAccountService bankAccountService = new BankAccountService(
				new BankRepository(),
				new BankAccountAcctRepository(),
				currenciesRepo);
		return new AcctDocRequiredServicesFacade(bankAccountService, newCostingService(currenciesRepo));
	}

	@NonNull
	private static CostingService newCostingService(final CurrencyRepository currenciesRepo)
	{
		final CostElementRepository costElementRepo = new CostElementRepository();
		final CostDetailService costDetailsService = new CostDetailService(new CostDetailRepository(), costElementRepo);
		final CurrentCostsRepository currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostingMethodHandlerUtils costingMethodHandlerUtils = new CostingMethodHandlerUtils(
				currenciesRepo,
				currentCostsRepo,
				costDetailsService
		);
		return new CostingService(
				costingMethodHandlerUtils,
				costDetailsService,
				costElementRepo,
				currentCostsRepo,
				ImmutableList.of(
						new AveragePOCostingMethodHandler(costingMethodHandlerUtils),
						new AverageInvoiceCostingMethodHandler(costingMethodHandlerUtils),
						new StandardCostingMethodHandler(costingMethodHandlerUtils),
						new ManufacturingStandardCostingMethodHandler(currentCostsRepo, costDetailsService, costingMethodHandlerUtils),
						new ManufacturingAveragePOCostingMethodHandler(costingMethodHandlerUtils)
				)
		);
	}

	private void postPP_Cost_Collectors(@NonNull final Integer... ids)
	{
		if (ids.length <= 0)
		{
			return;
		}

		final List<I_PP_Cost_Collector> records = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addInArrayFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID, ids)
				.create()
				.list();

		System.out.println("Posting: " + records);

		final AcctDocContext.AcctDocContextBuilder contextTemplate = AcctDocContext.builder()
				.services(acctDocRequiredServicesFacade)
				.acctSchemas(acctSchemas);

		for (final I_PP_Cost_Collector documentModel : records)
		{
			final Doc_PPCostCollector doc = new Doc_PPCostCollector(contextTemplate.documentModel(documentModel).build());
			doc.post(true, true);
			System.out.println("Posted: " + documentModel);
		}
	}
}