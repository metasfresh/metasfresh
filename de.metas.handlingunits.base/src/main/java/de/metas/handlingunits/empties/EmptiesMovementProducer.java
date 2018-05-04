package de.metas.handlingunits.empties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

import de.metas.adempiere.docline.sort.api.IDocLineSortDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.interfaces.I_M_Movement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * Create movements based on the data collected within {@link IHUContext#getHUPackingMaterialsCollector()}.
 * <p>
 * Use {@link de.metas.handlingunits.IHandlingUnitsBL#createEmptiesMovementBuilder()} to get your instance.
 * <p>
 * * The Empties movements will be either FROM the Empties Warehouse to the current warehouse (in case of creating a new HU) or from the current warehouse TO the Empties Warehouse (in case of
 * destroying an HU).
 *
 */
public class EmptiesMovementProducer
{
	public static final EmptiesMovementProducer newInstance()
	{
		return new EmptiesMovementProducer();
	}

	public static enum EmptiesMovementDirection
	{
		FromEmptiesWarehouse, ToEmptiesWarehouse
	};

	// services
	private final transient IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	private EmptiesMovementDirection _emptiesMovementDirection = null;
	private int _referencedInOutId = -1;
	private int _referencedInventoryId = -1;
	private List<HUPackingMaterialDocumentLineCandidate> candidates = new ArrayList<>();

	private EmptiesMovementProducer()
	{
	}

	public EmptiesMovementProducer setEmptiesMovementDirection(final EmptiesMovementDirection emptiesMovementDirection)
	{
		Check.assumeNotNull(emptiesMovementDirection, "Parameter emptiesMovementDirection is not null");
		this._emptiesMovementDirection = emptiesMovementDirection;
		return this;
	}

	public EmptiesMovementProducer setEmptiesMovementDirectionAuto()
	{
		this._emptiesMovementDirection = null;
		return this;
	}

	private EmptiesMovementDirection extractEmptiesMovementDirection(final HUPackingMaterialDocumentLineCandidate candidate)
	{
		if (_emptiesMovementDirection != null)
		{
			return _emptiesMovementDirection;
		}

		// Automatically determine movement direction based on Qty.
		// * To empties warehouse (when qty > 0)
		// * From empties warehouse (when qty < 0)
		final BigDecimal qty = candidate.getQty();
		return qty.signum() >= 0 ? EmptiesMovementDirection.ToEmptiesWarehouse : EmptiesMovementDirection.FromEmptiesWarehouse;
	}

	/**
	 * Sets the referenced M_InOut.
	 * Usually it's set to "empties shipment/receipt" for which we are generating the empties movements.
	 * 
	 * @param referencedInOutId
	 */
	public EmptiesMovementProducer setReferencedInOutId(final int referencedInOutId)
	{
		this._referencedInOutId = referencedInOutId;
		return this;
	}

	private int getReferencedInOutId()
	{
		return _referencedInOutId;
	}
	
	public EmptiesMovementProducer setReferencedInventoryId(final int referencedInventoryId)
	{
		this._referencedInventoryId = referencedInventoryId;
		return this;
	}
	
	private int getReferencedInventoryId()
	{
		return _referencedInventoryId;
	}

	public EmptiesMovementProducer addCandidates(final Collection<HUPackingMaterialDocumentLineCandidate> candidates)
	{
		if (candidates == null || candidates.isEmpty())
		{
			return this;
		}

		this.candidates.addAll(candidates);
		return this;
	}

	/**
	 * @return list of the created movements
	 */
	public List<I_M_Movement> createMovements()
	{
		if (candidates.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Iterate all receipt lines and group them by target warehouse
		final ListMultimap<MovementHeaderCandidate, HUPackingMaterialDocumentLineCandidate> directionToCandidates = candidates.stream()
				.filter(candidate -> candidate.getQty().signum() != 0) // Qty != 0
				.collect(GuavaCollectors.toImmutableListMultimap(candidate -> createMovementHeaderCandidate(candidate)));

		//
		// Generate movements for each group
		final List<I_M_Movement> movements = directionToCandidates.asMap().entrySet()
				.stream()
				.map(entry -> createMovement(entry.getKey(), entry.getValue()))
				.filter(movement -> movement != null)
				.collect(ImmutableList.toImmutableList());

		return movements;
	}

	private final MovementHeaderCandidate createMovementHeaderCandidate(final HUPackingMaterialDocumentLineCandidate candidate)
	{
		final EmptiesMovementDirection movementDirection = extractEmptiesMovementDirection(candidate);

		// To/From locator
		final I_M_Locator locatorCandidate = candidate.getM_Locator();
		final I_M_Warehouse warehouseCandidate = locatorCandidate.getM_Warehouse();

		final MovementHeaderCandidate warehouseDirection = new MovementHeaderCandidate(movementDirection, warehouseCandidate.getM_Warehouse_ID());
		return warehouseDirection;
	}

	private final I_M_Movement createMovement(final MovementHeaderCandidate movementHeaderCandidate, final Collection<HUPackingMaterialDocumentLineCandidate> lines)
	{
		final I_M_Warehouse warehouse = movementHeaderCandidate.getWarehouse();
		Check.assumeNotNull(warehouse, "Parameter warehouse is not null for {}", movementHeaderCandidate);

		final EmptiesMovementDirection direction = movementHeaderCandidate.getDirection();

		//
		// Get the empties warehouse/locator
		final I_M_Locator emptiesLocator = huEmptiesService.getEmptiesLocator(warehouse);

		//
		// Iterate given packing material lines and keep only those which are eligible
		final List<HUPackingMaterialDocumentLineCandidate> linesEffective = lines.stream()
				.filter(line -> line.getM_Locator() != null) // has locator
				.filter(line -> line.getM_Locator().getM_Locator_ID() != emptiesLocator.getM_Locator_ID()) // not same as empties locator
				.filter(line -> line.getQty().signum() != 0) // have some quantity to move
				.collect(Collectors.toCollection(ArrayList::new));
		if (linesEffective.isEmpty())
		{
			// if no eligible lines found, do nothing
			return null;
		}

		//
		// Create & save the movement header
		final IContextAware context = PlainContextAware.newWithThreadInheritedTrx();
		final I_M_Movement movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class, context);
		movement.setMovementDate(Env.getDate(context.getCtx())); // use Login date (08306)
		movement.setDocStatus(IDocument.STATUS_Drafted);
		movement.setDocAction(IDocument.ACTION_Complete);
		
		// Base document reference
		if (getReferencedInOutId() > 0)
		{
			movement.setM_InOut_ID(getReferencedInOutId());
		}
		if(getReferencedInventoryId() > 0)
		{
			movement.setM_Inventory_ID(getReferencedInventoryId());
		}
		
		InterfaceWrapperHelper.save(movement);

		//
		// Sort lines by M_Product_ID
		final Comparator<Integer> productIdsComparator = Services.get(IDocLineSortDAO.class).findDocLineSort()
				.setContext(context.getCtx())
				.setC_BPartner_ID(movement.getC_BPartner_ID())
				.setC_DocType(movement.getC_DocType())
				.findProductIdsComparator();
		Collections.sort(linesEffective, HUPackingMaterialDocumentLineCandidate.comparatorFromProductIds(productIdsComparator));

		//
		// Iterate the eligible lines and generate movement lines for them
		for (final HUPackingMaterialDocumentLineCandidate line : linesEffective)
		{
			createMovementLine(
					movement,
					emptiesLocator,
					direction,
					line.getM_Product(),
					line.getQty().abs(),
					line.getM_Locator());
		}

		//
		// Complete the movement
		Services.get(IDocumentBL.class).processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return movement;
	}

	private I_M_MovementLine createMovementLine(final I_M_Movement movement, final I_M_Locator emptiesLocator, final EmptiesMovementDirection direction, final I_M_Product product, final BigDecimal qty, final I_M_Locator locator)
	{
		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, movement);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement_ID(movement.getM_Movement_ID());

		movementLine.setM_Product(product);

		movementLine.setMovementQty(qty);

		final I_M_Locator locatorFrom;
		final I_M_Locator locatorTo;

		// in case the direction is on true, we have to move from the current warehouse to the gebinde one
		if (direction == EmptiesMovementDirection.ToEmptiesWarehouse)
		{
			locatorFrom = locator;
			locatorTo = emptiesLocator;
		}
		else if (direction == EmptiesMovementDirection.FromEmptiesWarehouse)
		{
			locatorFrom = emptiesLocator;
			locatorTo = locator;
		}
		else
		{
			throw new IllegalArgumentException("Unknown direction: " + direction);
		}

		movementLine.setM_Locator(locatorFrom);
		movementLine.setM_LocatorTo(locatorTo);

		// the movement lines will be packing material ones
		movementLine.setIsPackagingMaterial(true);

		InterfaceWrapperHelper.save(movementLine);

		huMovementBL.setPackingMaterialCActivity(movementLine);
		return movementLine;
	}

	@Value
	@EqualsAndHashCode(exclude = "warehouse")
	private static class MovementHeaderCandidate
	{
		private final EmptiesMovementDirection direction;

		private final int warehouseId;
		@Getter(lazy = true)
		private final I_M_Warehouse warehouse = retrieveWarehouse();

		private I_M_Warehouse retrieveWarehouse()
		{
			if (warehouseId <= 0)
			{
				return null;
			}
			return InterfaceWrapperHelper.create(Env.getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		}
	}
}
