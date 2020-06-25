package de.metas.handlingunits.inventory;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_Inventory;
import org.springframework.stereotype.Service;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class InventoryService
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final InventoryRepository inventoryRepository;

	public InventoryService(@NonNull final InventoryRepository inventoryRepository)
	{
		this.inventoryRepository = inventoryRepository;
	}

	public Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return inventoryRepository.getById(inventoryId);
	}

	public void completeDocument(@NonNull final InventoryId inventoryId)
	{
		final I_M_Inventory inventory = inventoryRepository.getRecordById(inventoryId);
		documentBL.processEx(inventory, IDocument.ACTION_Complete);
	}

	public Inventory createInventoryHeader(@NonNull final InventoryHeaderCreateRequest request)
	{
		return inventoryRepository.createInventoryHeader(request);
	}

	public Inventory createInventoryLine(@NonNull final InventoryLineCreateRequest request)
	{
		return inventoryRepository.createInventoryLine(request);
	}
}
