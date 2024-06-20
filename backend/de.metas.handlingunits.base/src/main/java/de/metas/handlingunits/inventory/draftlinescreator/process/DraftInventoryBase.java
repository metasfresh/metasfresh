package de.metas.handlingunits.inventory.draftlinescreator.process;

import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.draftlinescreator.DraftInventoryLinesCreator;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategy;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLinesCreationCtx;
import de.metas.handlingunits.inventory.draftlinescreator.aggregator.InventoryLineAggregator;
import de.metas.handlingunits.inventory.draftlinescreator.aggregator.InventoryLineAggregatorFactory;
import de.metas.inventory.InventoryId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Inventory;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public abstract class DraftInventoryBase extends JavaProcess implements IProcessPrecondition
{
	private final InventoryRepository inventoryRepo = SpringContextHolder.instance.getBean(InventoryRepository.class);

	@Override
	final public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_M_Inventory inventory = context.getSelectedModel(I_M_Inventory.class);
		if (inventory.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("inventory is processed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	final protected String doIt()
	{
		final InventoryId inventoryId = InventoryId.ofRepoId(getRecord_ID());
		final Inventory inventory = inventoryRepo.getById(inventoryId);
		final DocBaseAndSubType docBaseAndSubType = inventory.getDocBaseAndSubType();

		final HUsForInventoryStrategy strategy = createStrategy(inventory);

		final InventoryLineAggregator inventoryLineAggregator = InventoryLineAggregatorFactory.getForDocBaseAndSubType(docBaseAndSubType);

		Check.errorUnless(
				inventory.getDocStatus().isDraftedOrInProgress(),
				"the given inventory record needs to be in status 'DR' or 'IP', but is in status={}; inventory={}",
				inventory.getDocStatus(), inventory);

		final InventoryLinesCreationCtx draftLines = InventoryLinesCreationCtx.builder()
				.inventory(inventoryRepo.getById(inventoryId))
				.inventoryRepo(inventoryRepo)
				.inventoryLineAggregator(inventoryLineAggregator)
				.strategy(strategy)
				.build();

		final DraftInventoryLinesCreator draftLinesCreator = new DraftInventoryLinesCreator(draftLines);
		draftLinesCreator.execute();

		return "@Created@/@Updated@ #" + draftLinesCreator.getCountInventoryLines();
	}

	protected abstract HUsForInventoryStrategy createStrategy(Inventory inventory);
}
