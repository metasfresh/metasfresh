package de.metas.inout.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.api.ReceiptLineFindForwardToLocatorTool;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.interfaces.I_M_Movement;
import de.metas.interfaces.I_M_MovementLine;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class InOutMovementBL implements IInOutMovementBL
{
	@Override
	public List<I_M_Movement> generateMovementFromReceiptLines(
			@NonNull final List<I_M_InOutLine> receiptLines,
			@Nullable final LocatorId destinationLocatorId)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		if (Check.isEmpty(receiptLines))
		{
			// nothing to do
			return ImmutableList.of();
		}

		final I_M_InOut receipt = inOutDAO.retrieveInOut(receiptLines);

		trxManager.assertTrxNameNotNull(InterfaceWrapperHelper.getTrxName(receipt));

		// Iterate all receipt lines and group them by target warehouse
		final Map<LocatorId, List<I_M_InOutLine>> destLocatorId2InoutLines = partitionLinesByWarehouseTargetId(receiptLines, destinationLocatorId);

		return createMovementsForLinePartitions(destLocatorId2InoutLines, receipt);
	}

	private List<I_M_Movement> createMovementsForLinePartitions(
			@NonNull final Map<LocatorId, List<I_M_InOutLine>> destLocatorId2InoutLines,
			@NonNull final I_M_InOut receipt)
	{
		//
		// Generate movements for each "warehouseDestId -> inout lines" pair
		final List<I_M_Movement> movements = new ArrayList<>();
		for (final Entry<LocatorId, List<I_M_InOutLine>> movementCandidate : destLocatorId2InoutLines.entrySet())
		{
			final LocatorId locatorToId = movementCandidate.getKey();

			final I_M_Locator locatorToRecord = loadOutOfTrx(locatorToId, I_M_Locator.class);

			final List<I_M_InOutLine> linesForWarehouse = movementCandidate.getValue();
			Check.assumeNotEmpty(linesForWarehouse, "linesForWarehouse not empty");

			final I_M_Movement movement = generateMovement(receipt, locatorToRecord, linesForWarehouse);
			if (movement != null)
			{
				movements.add(movement);
			}
		}

		return movements;
	}

	private Map<LocatorId, List<I_M_InOutLine>> partitionLinesByWarehouseTargetId(
			@NonNull final List<I_M_InOutLine> receiptLines,
			@Nullable final LocatorId locatorToId)
	{
		final Map<LocatorId, List<I_M_InOutLine>> warehouseId2inoutLines = new HashMap<>();

		for (final I_M_InOutLine receiptLine : receiptLines)
		{
			// #3409 make sure the line is to be moved
			final boolean isForMovement = isCreateMovement(receiptLine);
			if (!isForMovement)
			{
				// skip the line because it is not supposed to be moved after receipt.
				continue;
			}

			final LocatorId locatorTo = ReceiptLineFindForwardToLocatorTool.findLocatorIdOrNull(receiptLine, locatorToId);
			if (locatorTo == null)
			{
				continue;
			}

			// Check: if receipt's warehouse is same as destination warehouse, we already got materials in destination warehouse
			// so it's pointless to do a movement
			if (locatorTo.getRepoId() == receiptLine.getM_Locator_ID())
			{
				continue;
			}

			// Aggregate to locatorTo -> inoutLines map
			// warehouses.put(warehouseTargetId, warehouseTarget);
			List<I_M_InOutLine> linesForWarehouse = warehouseId2inoutLines.get(locatorTo);
			if (linesForWarehouse == null)
			{
				linesForWarehouse = new ArrayList<>();
				warehouseId2inoutLines.put(locatorTo, linesForWarehouse);
			}
			linesForWarehouse.add(receiptLine);
		}

		return ImmutableMap.copyOf(warehouseId2inoutLines);
	}

	private boolean isCreateMovement(@NonNull final I_M_InOutLine inOutLine)
	{
		if (inOutLine.getM_InOut().isDropShip())
		{
			return false;
		}

		final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
		final List<I_M_ReceiptSchedule> rsForInOutLine = receiptScheduleDAO.retrieveRsForInOutLine(inOutLine);

		if (Check.isEmpty(rsForInOutLine))
		{
			// if the inoutLine doesn't have any receipt schedules, just create a movement for it to keep the old functionality untouched.
			return true;
		}

		for (final I_M_ReceiptSchedule rs : rsForInOutLine)
		{
			if (isCreateMovement(rs))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isCreateMovement(@NonNull final I_M_ReceiptSchedule rs)
	{
		if (rs.getOnMaterialReceiptWithDestWarehouse() == null)
		{
			// if nothing is set in this field, create movements. This way we keep the old functionality untouched.
			return true;
		}
		return X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateMovement.equals(rs.getOnMaterialReceiptWithDestWarehouse());
	}

	private I_M_Movement generateMovement(
			@NonNull final I_M_InOut inOut,
			@NonNull final I_M_Locator locator,
			@NonNull final List<I_M_InOutLine> lines)
	{
		Check.assume(!lines.isEmpty(), "lines not empty");

		final I_M_Movement movement = generateMovementHeader(inOut);

		generateMovementLines(movement, locator, lines);

		Services.get(IDocumentBL.class).processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return movement;
	}

	private I_M_Movement generateMovementHeader(final I_M_InOut inOut)
	{
		final I_M_Movement movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class, inOut);

		// Use Login Date as movement date because some roles will relly on the fact that they can override it (08247)
		final Properties ctx = InterfaceWrapperHelper.getCtx(inOut);
		final Timestamp movementDate = Env.getDate(ctx);
		movement.setMovementDate(movementDate);

		movement.setDocStatus(IDocument.STATUS_Drafted);
		movement.setDocAction(IDocument.ACTION_Complete);

		// 06365: Also set the linked M_InOut entry to the movement
		movement.setM_InOut(inOut);

		InterfaceWrapperHelper.save(movement);

		return movement;
	}

	private void generateMovementLines(
			@NonNull final I_M_Movement movement,
			@NonNull final I_M_Locator locator,
			@NonNull final List<I_M_InOutLine> inoutLines)
	{
		for (final I_M_InOutLine inoutLine : inoutLines)
		{
			generateMovementLine(movement, locator, inoutLine);
		}
	}

	private I_M_MovementLine generateMovementLine(
			@NonNull final I_M_Movement movement,
			@NonNull final I_M_Locator locator,
			@NonNull final I_M_InOutLine inoutLineFrom)
	{
		final IMovementBL movementBL = Services.get(IMovementBL.class);

		final I_M_MovementLine movementLine = newInstance(I_M_MovementLine.class, movement);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement_ID(movement.getM_Movement_ID());
		movementLine.setM_InOutLine(inoutLineFrom);

		final I_M_Product product = inoutLineFrom.getM_Product();
		movementLine.setM_Product(product);

		movementLine.setM_AttributeSetInstance_ID(inoutLineFrom.getM_AttributeSetInstance_ID());

		movementLine.setMovementQty(inoutLineFrom.getMovementQty());

		// move out of inout warehouse
		movementLine.setM_Locator_ID(inoutLineFrom.getM_Locator_ID());
		movementLine.setM_LocatorTo(locator);

		saveRecord(movementLine);

		movementBL.setC_Activities(movementLine);
		return movementLine;
	}

	/**
	 * Retrieve ALL movements which are linked to given shipment/receipt.
	 *
	 * NOTE: this is DAO method, but we are adding it here to keep all BL together
	 *
	 * @param inout
	 * @return movements
	 */
	private final List<I_M_Movement> retrieveMovementsForInOut(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "inout not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Movement.class, inout)
				.addEqualsFilter(I_M_Movement.COLUMNNAME_M_InOut_ID, inout.getM_InOut_ID())
				.create()
				.list(I_M_Movement.class);
	}

	@Override
	public void reverseMovements(final I_M_InOut inout)
	{
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		//
		// Iterate all linked movements and reverse them one by one (if not already reversed)
		final List<I_M_Movement> movements = retrieveMovementsForInOut(inout);
		for (final I_M_Movement movement : movements)
		{
			// Skip those movements which were already reversed
			if (docActionBL.isDocumentReversedOrVoided(movement))
			{
				// already reversed, nothing to do
				continue;
			}

			// Reverse movement
			docActionBL.processEx(movement, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
		}
	}
}
