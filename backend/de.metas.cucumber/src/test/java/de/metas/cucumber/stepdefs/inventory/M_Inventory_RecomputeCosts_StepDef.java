/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.inventory;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElement;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class M_Inventory_RecomputeCosts_StepDef
{
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final ICostElementRepository costElementRepository =
			SpringContextHolder.instance.getBean(ICostElementRepository.class);

	@NonNull private final M_Inventory_StepDefData inventoryTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;

	/**
	 * Mirrors {@code de.metas.inventory.process.M_Inventory_RecomputeCosts.doIt()} —
	 * builds the product selection from the given inventory, picks the cost elements
	 * (all active material elements when no costing method is given, or just the elements
	 * matching the supplied costing method), and calls
	 * {@code de_metas_acct.product_costs_recreate_from_date(...)} once per cost element
	 * with the same {@code 'DD'} truncation the production process uses.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Inventory_ID</b> — (required, identifier-ref) inventory whose products + MovementDate drive the recompute<br>
	 *   <b>C_AcctSchema_ID</b> — (required, identifier-ref) accounting schema to recompute<br>
	 *   <b>CostingMethod</b> — (optional) costing method code (e.g. <code>A</code>=AveragePO, <code>M</code>=MovingAverageInvoice, <code>S</code>=Standard); empty = all active material elements<br>
	 * @cucumber.depends StepDefData: M_Inventory_StepDefData, C_AcctSchema_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When invoke M_Inventory_RecomputeCosts:
	 *   | M_Inventory_ID  | C_AcctSchema_ID | CostingMethod |
	 *   | inv_costUpdate  | acctSchema      | A             |
	 * </pre>
	 */
	@When("invoke M_Inventory_RecomputeCosts:")
	public void invoke(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::invokeOne);
	}

	private void invokeOne(@NonNull final DataTableRow row)
	{
		final I_M_Inventory inventory = row.getAsIdentifier(I_M_Inventory.COLUMNNAME_M_Inventory_ID).lookupNotNullIn(inventoryTable);
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		final I_C_AcctSchema acctSchema = row.getAsIdentifier(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID).lookupNotNullIn(acctSchemaTable);
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(acctSchema.getC_AcctSchema_ID());

		final CostingMethod costingMethod = row.getAsOptionalString(I_C_AcctSchema.COLUMNNAME_CostingMethod)
				.map(CostingMethod::ofCode)
				.orElse(null);

		final Set<InventoryId> inventoryIds = ImmutableSet.of(inventoryId);
		final Set<ProductId> productIds = inventoryDAO.retrieveUsedProductsByInventoryIds(inventoryIds);
		if (productIds.isEmpty())
		{
			throw new AdempiereException("Inventory " + inventoryId + " has no products");
		}

		final PInstanceId selectionId = DB.createT_Selection(productIds, ITrx.TRXNAME_ThreadInherited);
		final Instant startDate = inventoryDAO.getMinInventoryDate(inventoryIds)
				.orElseThrow(() -> new AdempiereException("Cannot determine startDate for inventory " + inventoryId));

		final List<CostElement> costElements = costingMethod != null
				? costElementRepository.getMaterialCostingElementsForCostingMethod(costingMethod)
				: costElementRepository.getActiveMaterialCostingElements(Env.getClientId());

		for (final CostElement costElement : costElements)
		{
			DB.executeFunctionCallEx(
					ITrx.TRXNAME_ThreadInherited,
					"select \"de_metas_acct\".product_costs_recreate_from_date("
							+ " p_C_AcctSchema_ID :=" + acctSchemaId.getRepoId()
							+ ", p_M_CostElement_ID:=" + costElement.getId().getRepoId()
							+ ", p_m_product_selection_id:=" + selectionId.getRepoId()
							+ ", p_ReorderDocs_DateAcct_Trunc:='DD'"
							+ ", p_StartDateAcct:=" + DB.TO_SQL(startDate) + "::date)",
					null);
		}
	}
}
