package de.metas.costrevaluation.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.adempiere.model.I_M_Product;
import de.metas.costing.CostElementId;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.costrevaluation.impl.CostRevaluationLine;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluation;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/**
 * Process to create M_CostRevaluationLine records for products stocked based on accounting schema and cost element of M_CostRevaluation
 */
public class M_CostRevaluation_CreateLines_Process extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CostRevaluationRepository costRevaluationRepo = SpringContextHolder.instance.getBean(CostRevaluationRepository.class);
	private final ICurrentCostsRepository currentCostsRepo = SpringContextHolder.instance.getBean(ICurrentCostsRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}
		else if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		else if (!isDraftDocument(context))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Already in progress/finished.");
		}
		return ProcessPreconditionsResolution.accept();
	}

	private boolean isDraftDocument(final @NonNull IProcessPreconditionsContext context)
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(context.getSingleSelectedRecordId());
		return costRevaluationRepo.isDraftedDocument(costRevaluationId);
	}

	@Override
	protected String doIt() throws Exception
	{

		final ImmutableSet<ProductId>  products = fetchProducts();
		if (products.isEmpty()) return "NoSelection@";

		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(getRecord_ID());
		final I_M_CostRevaluation costRevaluation = loadOutOfTrx(costRevaluationId, I_M_CostRevaluation.class);
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(costRevaluation.getC_AcctSchema_ID());
		final CostElementId costElementId = CostElementId.ofRepoId(costRevaluation.getM_CostElement_ID());



		for (ProductId productId : products)
		{
			createCostRevaluationLineForProduct(acctSchemaId, costElementId, productId);
		}

		return "@OK@";
	}

	private void createCostRevaluationLineForProduct(@NonNull final AcctSchemaId acctSchemaId,
														@NonNull final CostElementId costElementId,
														@NonNull final  ProductId productId)
	{
		final List<CurrentCost> costs = currentCostsRepo.getByCostElementAndProduct(acctSchemaId, costElementId,productId);

		for (final CurrentCost cost : costs)
		{
			final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(getRecord_ID());
			final CostRevaluationLine line = CostRevaluationLine.builder()
					.costRevaluationId(costRevaluationId)
					.productId(productId)
					.currentCostPrice(cost.getCostPrice())
					.currentQty(cost.getCurrentQty())
					.build();
			costRevaluationRepo.save(line);
		}
	}

	private ImmutableSet<ProductId> fetchProducts()
	{
		return queryBL
				.createQueryBuilder(I_M_Product.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product.COLUMNNAME_IsStocked, true)
				.orderBy().addColumn(I_M_Product.COLUMNNAME_Value)
				.endOrderBy()
				.create()
				.listIds(ProductId::ofRepoId);
	}

}
