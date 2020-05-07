package de.metas.handlingunits.movement.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.interfaces.I_M_Movement;

/**
 * Gets HUs assigned to movement lines, group them by Locator From/To and creates packing materials movement lines.
 *
 */
/* package */class HUPackingMaterialMovementLineAggregateBuilder
{
	public static final HUPackingMaterialMovementLineAggregateBuilder newInstance()
	{
		return new HUPackingMaterialMovementLineAggregateBuilder();
	}
	
	// services
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

	//
	// Parameters
	private I_M_Movement _movement;

	//
	// Status
	/**
	 * Shared list of HU IDs that were included in any of our {@link HUPackingMaterialMovementLineBuilder}s.
	 *
	 * We are keeping a shared list because we want to avoid collecting packing materials for an HU more then once.
	 */
	private final Set<Integer> seenM_HU_IDs_ToAdd = new HashSet<>();

	private HUPackingMaterialMovementLineAggregateBuilder()
	{
		super();
	}

	public HUPackingMaterialMovementLineAggregateBuilder setM_Movement(final I_M_Movement movement)
	{
		_movement = movement;
		return this;
	}

	private final I_M_Movement getM_Movement()
	{
		Check.assumeNotNull(_movement, "_movement not null");
		return _movement;
	}

	public void createPackingMaterialMovementLines()
	{
		final I_M_Movement movement = getM_Movement();

		final List<HUPackingMaterialMovementLineBuilder> packingMaterialMovementLinesBuilders = new ArrayList<>();

		final Properties ctx = InterfaceWrapperHelper.getCtx(movement);

		//
		// Get M_Product_ID sort comparator to use
		final Comparator<Integer> productIdsSortComparator = Services.get(IDocLineSortDAO.class).findDocLineSort()
				.setContext(ctx)
				.setC_BPartner_ID(movement.getC_BPartner_ID())
				.setC_DocType(movement.getC_DocType())
				.findProductIdsComparator();

		//
		// Iterate movement lines
		final List<org.compiere.model.I_M_MovementLine> movementLines = movementDAO.retrieveLines(movement);
		for (final org.compiere.model.I_M_MovementLine line : movementLines)
		{
			final I_M_MovementLine movementLine = InterfaceWrapperHelper.create(line, I_M_MovementLine.class);
			final int locatorFromId = movementLine.getM_Locator_ID();
			final int locatorToId = movementLine.getM_LocatorTo_ID();

			//
			// Here we try to find out if there are several movement lines for the same product, same locators (e.g. case of different subproducers)
			// In case we find a builder that fits, we just put the hus in it
			HUPackingMaterialMovementLineBuilder packingMaterialMovementLinesBuilderToUse = null;
			for (final HUPackingMaterialMovementLineBuilder packingMaterialMovementLinesBuilder : packingMaterialMovementLinesBuilders)
			{
				if (locatorFromId == packingMaterialMovementLinesBuilder.getM_Locator_From_ID()
						&& locatorToId == packingMaterialMovementLinesBuilder.getM_Locator_To_ID())
				{
					addHUsFromMovementLine(packingMaterialMovementLinesBuilder, movementLine);
					packingMaterialMovementLinesBuilderToUse = packingMaterialMovementLinesBuilder;
					break;
				}
			}

			//
			// only create a new builder if no other was found
			if (packingMaterialMovementLinesBuilderToUse == null)
			{
				packingMaterialMovementLinesBuilderToUse = newMovementLinesBuilder(locatorFromId, locatorToId);
				packingMaterialMovementLinesBuilderToUse.setProductIdSortComparator(productIdsSortComparator);
				addHUsFromMovementLine(packingMaterialMovementLinesBuilderToUse, movementLine);
				packingMaterialMovementLinesBuilders.add(packingMaterialMovementLinesBuilderToUse);
			}
		}

		//
		// finally, create the required movement lines for the "aggregated" candidates
		for (final HUPackingMaterialMovementLineBuilder pmmBuilder : packingMaterialMovementLinesBuilders)
		{
			final int locatorFromId = pmmBuilder.getM_Locator_From_ID();
			final int locatorToId = pmmBuilder.getM_Locator_To_ID();
			for (final HUPackingMaterialDocumentLineCandidate pmCandidate : pmmBuilder.getAndClearCandidates())
			{
				createPackingMaterialMovementLine(pmCandidate, locatorFromId, locatorToId);
			}
		}
	}

	private HUPackingMaterialMovementLineBuilder newMovementLinesBuilder(final int locatorFromId, final int locatorToId)
	{
		HUPackingMaterialMovementLineBuilder packingMaterialMovementLinesBuilderToUse;
		packingMaterialMovementLinesBuilderToUse = new HUPackingMaterialMovementLineBuilder(locatorFromId, locatorToId);
		if (seenM_HU_IDs_ToAdd != null)
		{
			packingMaterialMovementLinesBuilderToUse.setSeenM_HU_IDs_ToAdd(seenM_HU_IDs_ToAdd);
		}
		return packingMaterialMovementLinesBuilderToUse;
	}

	private void addHUsFromMovementLine(final HUPackingMaterialMovementLineBuilder packingMaterialMovementLinesBuilder, final I_M_MovementLine movementLine)
	{
		final IQueryBuilder<I_M_HU_Assignment> huAssignmentsQuery = huAssignmentDAO.retrieveHUAssignmentsForModelQuery(movementLine);
		packingMaterialMovementLinesBuilder.addHURecursively(huAssignmentsQuery);
	}

	private void createPackingMaterialMovementLine(final HUPackingMaterialDocumentLineCandidate pmCandidate, final int locatorFromId, final int locatorToId)
	{
		final I_M_Movement movement = getM_Movement();
		final I_M_MovementLine pmMovementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, movement);
		pmMovementLine.setM_Movement(movement);
		pmMovementLine.setAD_Org_ID(movement.getAD_Org_ID());
		pmMovementLine.setM_Locator_ID(locatorFromId);
		pmMovementLine.setM_LocatorTo_ID(locatorToId);

		final I_M_Product product = pmCandidate.getM_Product();
		pmMovementLine.setM_Product(product);
		pmMovementLine.setMovementQty(pmCandidate.getQtyInStockingUOM());

		pmMovementLine.setIsPackagingMaterial(true);

		InterfaceWrapperHelper.save(pmMovementLine);

		huMovementBL.setPackingMaterialCActivity(pmMovementLine); // task 07689/07690
	}
}
