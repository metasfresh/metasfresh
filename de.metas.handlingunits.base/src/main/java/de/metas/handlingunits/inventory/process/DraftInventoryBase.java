package de.metas.handlingunits.inventory.process;

import org.compiere.Adempiere;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.inventory.DraftInventoryLines;
import de.metas.handlingunits.inventory.DraftInventoryLinesCreator;
import de.metas.handlingunits.inventory.HUsForInventoryStrategy;
import de.metas.handlingunits.inventory.InventoryLineAggregator;
import de.metas.handlingunits.inventory.InventoryLineAggregatorFactory;
import de.metas.handlingunits.inventory.InventoryLineRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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
	private final InventoryLineRepository inventoryLineRepository = Adempiere.getBean(InventoryLineRepository.class);

	private final InventoryLineAggregatorFactory inventoryLineAggregatorFactory = Adempiere.getBean(InventoryLineAggregatorFactory.class);

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
		final I_M_Inventory inventoryRecord = getRecord(I_M_Inventory.class);
		final I_C_DocType docTypeRecord = inventoryRecord.getC_DocType();
		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(docTypeRecord.getDocBaseType(), docTypeRecord.getDocSubType());

		final HUsForInventoryStrategy strategy = createStrategy(inventoryRecord);

		final InventoryLineAggregator inventoryLineAggregator = inventoryLineAggregatorFactory.createFor(docBaseAndSubType);

		final DraftInventoryLines draftLines = DraftInventoryLines.builder()
				.inventoryRecord(inventoryRecord)
				.strategy(strategy)
				.inventoryLineRepository(inventoryLineRepository)
				.inventoryLineAggregator(inventoryLineAggregator)
				.build();

		final DraftInventoryLinesCreator draftLinesCreator = new DraftInventoryLinesCreator(draftLines);
		draftLinesCreator.execute();

		return "@Created@/@Updated@ #" + draftLinesCreator.getCountInventoryLines();
	}

	protected abstract HUsForInventoryStrategy createStrategy(I_M_Inventory inventory);
}
