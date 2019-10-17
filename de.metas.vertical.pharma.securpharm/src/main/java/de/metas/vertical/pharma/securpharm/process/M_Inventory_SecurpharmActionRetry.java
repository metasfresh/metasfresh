/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.process;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.DocStatus;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

public class M_Inventory_SecurpharmActionRetry extends JavaProcess implements IProcessPrecondition
{
	private final SecurPharmService securPharmService = Adempiere.getBean(SecurPharmService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no SecurPharm config");
		}

		final InventoryId inventoryId = InventoryId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (inventoryId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no single inventory selected");
		}

		ProductsToProcess productsToProcess = getProductsToProcess(inventoryId).orElse(null);
		if (productsToProcess == null || productsToProcess.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no SecurPharm products to found to process");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getRecord_ID());
		final ProductsToProcess productsToProcess = getProductsToProcess(inventoryId).orElse(null);
		if (productsToProcess == null)
		{
			return MSG_OK;
		}

		process(productsToProcess);

		return MSG_OK;
	}

	private Optional<ProductsToProcess> getProductsToProcess(@NonNull final InventoryId inventoryId)
	{
		final Action actionToRun = getActionToRun(inventoryId);
		if (actionToRun == null)
		{
			// throw new AdempiereException("@Invalid@ @DocStatus@");
			return Optional.empty();
		}

		final ProductsToProcess.ProductsToProcessBuilder resultBuilder = ProductsToProcess.builder()
				.action(actionToRun)
				.inventoryId(inventoryId);

		for (final SecurPharmProduct product : securPharmService.findProductsToDecommissionForInventoryId(inventoryId))
		{
			if (actionToRun == Action.DECOMMISSION)
			{
				if (securPharmService.isEligibleForDecommission(product))
				{
					resultBuilder.product(product);
				}
			}
			else if (actionToRun == Action.UNDO_DECOMMISSION)
			{
				if (securPharmService.isEligibleForUndoDecommission(product))
				{
					resultBuilder.product(product);
				}
			}
		}

		final ProductsToProcess result = resultBuilder.build();
		if (result.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(result);
	}

	private void process(@NonNull final ProductsToProcess productsToProcess)
	{
		final Action action = productsToProcess.getAction();
		final InventoryId inventoryId = productsToProcess.getInventoryId();
		for (final SecurPharmProduct product : productsToProcess.getProducts())
		{
			process(product, action, inventoryId);
		}
	}

	private void process(
			@NonNull final SecurPharmProduct product,
			@NonNull final Action action,
			@NonNull final InventoryId inventoryId)
	{
		if (action == Action.DECOMMISSION)
		{
			securPharmService.decommissionProductIfEligible(product, inventoryId);
		}
		else if (action == Action.UNDO_DECOMMISSION)
		{
			securPharmService.undoDecommissionProductIfEligible(product, inventoryId);
		}
		else
		{
			throw new AdempiereException("Invalid action: " + action);
		}
	}

	private Action getActionToRun(@NonNull final InventoryId inventoryId)
	{
		final DocStatus inventoryDocStatus = Services.get(IInventoryBL.class).getDocStatus(inventoryId);
		if (DocStatus.Completed.equals(inventoryDocStatus))
		{
			return Action.DECOMMISSION;
		}
		else if (DocStatus.Reversed.equals(inventoryDocStatus))
		{
			return Action.UNDO_DECOMMISSION;
		}
		else
		{
			return null;
		}

	}

	private enum Action
	{
		DECOMMISSION, UNDO_DECOMMISSION
	}

	@Value
	@Builder
	private static class ProductsToProcess
	{
		@NonNull
		Action action;

		@NonNull
		InventoryId inventoryId;

		@NonNull
		@Singular
		ImmutableList<SecurPharmProduct> products;

		public boolean isEmpty()
		{
			return getProducts().isEmpty();
		}
	}
}
