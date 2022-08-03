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
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.impl.CostRevaluationId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostRevaluation;

import java.util.Iterator;
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

		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(getRecord_ID());
		final I_M_CostRevaluation costRevaluation = loadOutOfTrx(costRevaluationId, I_M_CostRevaluation.class);

		if (products.isEmpty()) return "NoSelection@";

		int counterProcessed = 0;

		for (ProductId productId : products)
		{
			final boolean processed = createCostRevaluationLineForProduct(costRevaluation, productId);
			if (processed)
			{
				counterProcessed++;
			}
		}

		return msgBL.getMsg(getCtx(), "msg", new Object[] { counterProcessed });
	}

	private boolean createCostRevaluationLineForProduct(final @NonNull I_M_CostRevaluation costRevaluation , final @NonNull ProductId productId)
	{
		final List<I_M_Cost> costs = fetchCostForProduct(costRevaluation, productId);

		// for ()

		return true;
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

	private List<I_M_Cost> fetchCostForProduct(final @NonNull I_M_CostRevaluation costRevaluation, final @NonNull ProductId productId)
	{
		return currentCostsRepo.queryCostRecordsByProduct(productId)
				.addEqualsFilter(I_M_Cost.COLUMN_C_AcctSchema_ID.getColumnName(), costRevaluation.getC_AcctSchema_ID())
				.addEqualsFilter(I_M_Cost.COLUMN_M_CostElement_ID.getColumnName(), costRevaluation.getM_CostElement_ID())
				.create()
				.list()	;
	}
}
