/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.acct;

import com.google.common.collect.ImmutableList;
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
import de.metas.banking.accounting.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.cache.model.ModelCacheInvalidationService;
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
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.OrderGroupRepository;
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
import org.compiere.acct.Doc_AllocationHdr;
import org.compiere.acct.Doc_Invoice;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.Disabled;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Disabled
public class PostDocumentNow_ManualTest
{
	void run()
	{
		postC_Invoice(1314888, 1322609);
		postC_AllocationHdr(
				1229729
				, 1234857
				, 1235398
				, 1237478
				, 1238514
				, 1240544
				, 1241800
				, 1242015
				, 1243294
		);
	}

	private final IQueryBL queryBL;
	private final AcctDocRequiredServicesFacade acctDocRequiredServicesFacade;
	private final List<AcctSchema> acctSchemas;

	public static void main(String[] args) {new PostDocumentNow_ManualTest().run();}

	PostDocumentNow_ManualTest()
	{
		AdempiereToolsHelper.getInstance().startupMinimal();

		this.queryBL = Services.get(IQueryBL.class);
		this.acctDocRequiredServicesFacade = newAcctDocRequiredServicesFacade();
		this.acctSchemas = Services.get(IAcctSchemaDAO.class).getAllByClient(ClientId.METASFRESH);
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
				new FAOpenItemsService(elementValueService, Optional.empty()),
				costingService,
				new DimensionService(ImmutableList.of()),
				new SalesRegionService(new SalesRegionRepository()),
				new SqlAcctDocLockService(),
				new FactAcctUserChangesService(new FactAcctUserChangesRepository())
		);
	}

	@SuppressWarnings("unused")
	private void postC_Invoice(@NonNull final Integer... ids)
	{
		if (ids.length <= 0)
		{
			return;
		}

		final List<I_C_Invoice> records = queryBL.createQueryBuilder(I_C_Invoice.class)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, Arrays.asList(ids))
				.create()
				.list();

		System.out.println("Posting: " + records);

		final AcctDocContext.AcctDocContextBuilder contextTemplate = AcctDocContext.builder()
				.services(acctDocRequiredServicesFacade)
				.acctSchemas(acctSchemas);

		final OrderGroupRepository orderGroupRepository = new OrderGroupRepository(
				new GroupCompensationLineCreateRequestFactory(),
				Optional.empty() // advisors
		);

		for (final I_C_Invoice documentModel : records)
		{
			final AcctDocContext context = contextTemplate.documentModel(toAcctDocModel(documentModel)).build();
			final Doc_Invoice doc = new Doc_Invoice(context, orderGroupRepository);
			doc.post(true, true);
			System.out.println("Posted: " + documentModel);
		}
	}

	@NonNull
	private static POAcctDocModel toAcctDocModel(final Object record)
	{
		return new POAcctDocModel(LegacyAdapters.convertToPO(record));
	}


	@SuppressWarnings("unused")
	private void postC_AllocationHdr(@NonNull final Integer... ids)
	{
		if (ids.length <= 0)
		{
			return;
		}

		final List<I_C_AllocationHdr> records = queryBL.createQueryBuilder(I_C_AllocationHdr.class)
				.addInArrayFilter(I_C_AllocationHdr.COLUMNNAME_C_AllocationHdr_ID, ids)
				.create()
				.list();

		System.out.println("Posting: " + records);

		final AcctDocContext.AcctDocContextBuilder contextTemplate = AcctDocContext.builder()
				.services(acctDocRequiredServicesFacade)
				.acctSchemas(acctSchemas);

		for (final I_C_AllocationHdr documentModel : records)
		{
			final Doc_AllocationHdr doc = new Doc_AllocationHdr(contextTemplate.documentModel(toAcctDocModel(documentModel)).build());
			doc.post(true, true);
			System.out.println("Posted: " + documentModel);
		}
	}
}
