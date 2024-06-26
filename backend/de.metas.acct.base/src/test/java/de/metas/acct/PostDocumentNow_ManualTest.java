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
import de.metas.costing.methods.StandardCostingMethodHandler;
import de.metas.currency.CurrencyRepository;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.tools.AdempiereToolsHelper;
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
						new StandardCostingMethodHandler(costingMethodHandlerUtils)
				)
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
			final AcctDocContext context = contextTemplate.documentModel(documentModel).build();
			final Doc_Invoice doc = new Doc_Invoice(context, orderGroupRepository);
			doc.post(true, true);
			System.out.println("Posted: " + documentModel);
		}
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
			final Doc_AllocationHdr doc = new Doc_AllocationHdr(contextTemplate.documentModel(documentModel).build());
			doc.post(true, true);
			System.out.println("Posted: " + documentModel);
		}
	}
}
