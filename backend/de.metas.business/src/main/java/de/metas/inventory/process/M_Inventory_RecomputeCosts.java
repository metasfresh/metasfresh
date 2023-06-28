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
import de.metas.inventory.InventoryId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.DB;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

public class M_Inventory_RecomputeCosts extends JavaProcess implements IProcessPrecondition
{
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final ICostElementRepository costElementRepository = SpringContextHolder.instance.getBean(ICostElementRepository.class);

	@Param(parameterName = I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID, mandatory = true)
	private AcctSchemaId p_C_AcctSchema_ID;

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
		final PInstanceId productsSelectionId = createProductsSelection();
		final Instant startDate = getStartDate().minus(1, ChronoUnit.DAYS);

		getCostElements().forEach(costElement -> recomputeCosts(costElement, productsSelectionId, startDate));

		return MSG_OK;
	}

	private PInstanceId createProductsSelection()
	{
		final Set<ProductId> productIds = inventoryDAO.retrieveUsedProductsByInventoryIds(getSelectedInventoryIds());
		if (productIds.isEmpty())
		{
			throw new AdempiereException("No Products");
		}

		return DB.createT_Selection(productIds, ITrx.TRXNAME_ThreadInherited);
	}

	private Set<InventoryId> getSelectedInventoryIds()
	{
		return retrieveSelectedRecordsQueryBuilder(I_M_Inventory.class)
				.create()
				.listIds()
				.stream()
				.map(InventoryId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void recomputeCosts(
			@NonNull final CostElement costElement,
			@NonNull final PInstanceId productsSelectionId,
			@NonNull final Instant startDate)
	{
		DB.executeFunctionCallEx(getTrxName()
				, "select \"de_metas_acct\".product_costs_recreate_from_date( p_C_AcctSchema_ID :=" + getAccountingSchemaId().getRepoId()
						+ ", p_M_CostElement_ID:=" + costElement.getId().getRepoId()
						+ ", p_m_product_selection_id:=" + productsSelectionId.getRepoId()
						+ " , p_ReorderDocs_DateAcct_Trunc:='MM'"
						+ ", p_StartDateAcct:=" + DB.TO_SQL(startDate) + "::date)"  //
				, null //
		);
	}

	private Instant getStartDate()
	{
		return inventoryDAO.getMinInventoryDate(getSelectedInventoryIds())
				.orElseThrow(() -> new AdempiereException("Cannot determine Start Date"));
	}

	private List<CostElement> getCostElements()
	{
		final AcctSchema schema = acctSchemaDAO.getById(getAccountingSchemaId());
		final CostingMethod costingMethod = schema.getCosting().getCostingMethod();

		return costElementRepository.getMaterialCostingElementsForCostingMethod(costingMethod);
	}

	private AcctSchemaId getAccountingSchemaId() {return p_C_AcctSchema_ID;}
}
