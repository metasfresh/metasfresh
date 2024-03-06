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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.acct.Doc_AllocationHdr;
import org.compiere.model.I_C_AllocationHdr;
import org.junit.jupiter.api.Disabled;

import java.util.List;

@Disabled
public class PostDocumentNow_ManualTest
{
	public static void main(String[] args)
	{
		AdempiereToolsHelper.getInstance().startupMinimal();

		final List<I_C_AllocationHdr> records = Services.get(IQueryBL.class).createQueryBuilder(I_C_AllocationHdr.class)
				.addInArrayFilter(I_C_AllocationHdr.COLUMNNAME_C_AllocationHdr_ID,
						1155459,
						1155460,
						1155462,
						1197998,
						1214777,
						1214800,
						1214802,
						1214803
				)
				.create()
				.list();

		System.out.println("Posting: " + records);

		final AcctDocContext.AcctDocContextBuilder contextTemplate = AcctDocContext.builder()
				.services(newAcctDocRequiredServicesFacade())
				.acctSchemas(getAcctSchemas(ClientId.METASFRESH));

		for (final I_C_AllocationHdr documentModel : records)
		{
			final Doc_AllocationHdr doc = new Doc_AllocationHdr(contextTemplate.documentModel(documentModel).build());
			doc.post(true, true);
		}
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

	private static List<AcctSchema> getAcctSchemas(final ClientId clientId)
	{
		return Services.get(IAcctSchemaDAO.class).getAllByClient(clientId);
	}
}
