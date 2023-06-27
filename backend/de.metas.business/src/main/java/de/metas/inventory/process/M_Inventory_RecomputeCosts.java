/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.inventory.process;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElement;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.inventory.IInventoryDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class M_Inventory_RecomputeCosts extends JavaProcess implements IProcessPrecondition
{
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final ICostElementRepository costElementRepository = SpringContextHolder.instance.getBean(ICostElementRepository.class);

	@Param(parameterName = I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID, mandatory = true)
	private int acctSchemaId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<ProductId> productIds = inventoryDAO.retrieveUsedProductsByInventoryIds(getSelectedInventoryIds());
		createProductsSelection(productIds);

		recomputeCosts();

		return MSG_OK;
	}

	private int createProductsSelection(@NonNull final Set<ProductId> productIds)
	{
		final IQueryBuilder<I_M_Product> queryBuilder = queryBL
				.createQueryBuilder(I_M_Product.class)
				.addInArrayFilter(I_M_Product.COLUMNNAME_M_Product_ID, productIds)
				.addOnlyActiveRecordsFilter();

		return createSelection(queryBuilder, getPinstanceId());
	}

	private Set<Integer> getSelectedInventoryIds()
	{
		return retrieveSelectedRecordsQueryBuilder(I_M_Inventory.class)
				.create()
				.listIds()
				.stream()
				.collect(ImmutableSet.toImmutableSet());
	}

	public void recomputeCosts()
	{

		final List<CostElement> costElements = getCostElements();
		final Timestamp startDate = TimeUtil.addDays(getStartDate(), -1);

		costElements.forEach(costElement -> {

			DB.executeFunctionCallEx(getTrxName()
					, "select \"de_metas_acct\".product_costs_recreate_from_date( p_C_AcctSchema_ID :="+getAccountingSchemaId().getRepoId()
											 + ", p_M_CostElement_ID:=" + costElement.getId().getRepoId()
											 + ", p_m_product_selection_id:=" + getPinstanceId().getRepoId()
											 +" , p_ReorderDocs_DateAcct_Trunc:='MM'"
									 		 + ", p_StartDateAcct:='"+startDate +"'::date)"  //
					, null //
			);
		});

	}

	private Timestamp getStartDate()
	{
		return inventoryDAO.retrieveMinInvetoryDateFromSelection(getSelectedInventoryIds());
	}

	private List<CostElement> getCostElements()
	{
		final AcctSchema schema = acctSchemaDAO.getById(getAccountingSchemaId());
		final CostingMethod costingMethod = schema.getCosting().getCostingMethod();

		return costElementRepository.getMaterialCostingElementsForCostingMethod(costingMethod);
	}

	private AcctSchemaId getAccountingSchemaId()
	{
		return AcctSchemaId.ofRepoId(acctSchemaId);
	}

}
