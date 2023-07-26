package de.metas.invoicecandidate.spi.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

import com.google.common.collect.ImmutableList;

import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class M_Inventory_Handler extends AbstractInvoiceCandidateHandler
{
	// Services
	final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return true;
	}

	@Override
	public boolean isCreateMissingCandidatesAutomatically(final Object model)
	{
		return true;
	}

	@Override
	public List<InvoiceCandidateGenerateRequest> expandRequest(final InvoiceCandidateGenerateRequest request)
	{
		final I_M_Inventory inventory = request.getModel(I_M_Inventory.class);

		//
		// Retrieve inventory lines
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());
		final List<I_M_InventoryLine> linesForInventory = inventoryDAO.retrieveLinesForInventoryId(inventoryId);
		if (linesForInventory.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Retrieve inventory line handlers
		final Properties ctx = InterfaceWrapperHelper.getCtx(inventory);
		final List<IInvoiceCandidateHandler> inventoryLineHandlers = Services.get(IInvoiceCandidateHandlerBL.class).retrieveImplementationsForTable(ctx, I_M_InventoryLine.Table_Name);

		//
		// Create the inventory line requests
		return InvoiceCandidateGenerateRequest.ofAll(inventoryLineHandlers, linesForInventory);
	}

	@Override
	public Iterator<? extends Object> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return Collections.emptyIterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_M_Inventory inventory = InterfaceWrapperHelper.create(model, I_M_Inventory.class);
		invalidateCandidatesForInventory(inventory);

	}

	private void invalidateCandidatesForInventory(final I_M_Inventory inventory)
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(inventory.getM_Inventory_ID());

		//
		// Retrieve inventory line handlers
		final Properties ctx = InterfaceWrapperHelper.getCtx(inventory);
		final List<IInvoiceCandidateHandler> inventoryLineHandlers = Services.get(IInvoiceCandidateHandlerBL.class).retrieveImplementationsForTable(ctx, I_M_InventoryLine.Table_Name);

		for (final IInvoiceCandidateHandler handler : inventoryLineHandlers)
		{
			for (final I_M_InventoryLine line : inventoryDAO.retrieveLinesForInventoryId(inventoryId))
			{
				handler.invalidateCandidatesFor(line);
			}
		}
	}

	@Override
	public String getSourceTable()
	{
		return I_M_Inventory.Table_Name;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		throw new UnsupportedOperationException();
	}
}
