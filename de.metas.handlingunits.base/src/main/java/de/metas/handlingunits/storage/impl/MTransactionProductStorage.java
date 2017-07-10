package de.metas.handlingunits.storage.impl;

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


import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.service.IMTransactionBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

public class MTransactionProductStorage extends AbstractProductStorage
{
	private final I_M_Transaction mtrx;
	private final boolean inbound;
	private final boolean reversal;
	private final IHUCapacityDefinition capacityTotal;

	public MTransactionProductStorage(final I_M_Transaction mtrx)
	{
		this(mtrx, Services.get(IHandlingUnitsBL.class).getC_UOM(mtrx));
	}

	public MTransactionProductStorage(final I_M_Transaction mtrx, final I_C_UOM uom)
	{
		super();
		setConsiderForceQtyAllocationFromRequest(false); // TODO: consider changing it to "true" (default)

		this.mtrx = mtrx;
		inbound = Services.get(IMTransactionBL.class).isInboundTransaction(mtrx);

		final I_M_Product product = mtrx.getM_Product();

		//
		// Convert transaction qty to storage UOM
		final BigDecimal qtyMTransactionSrc = mtrx.getMovementQty();
		final I_C_UOM uomMTransaction = Services.get(IHandlingUnitsBL.class).getC_UOM(mtrx);
		final BigDecimal qtyMTransaction = Services.get(IUOMConversionBL.class)
				.convertQty(product, qtyMTransactionSrc, uomMTransaction, uom);

		BigDecimal qtyCapacity;
		if (inbound)
		{
			reversal = qtyMTransaction.signum() < 0;
			qtyCapacity = qtyMTransaction;
		}
		else
		{
			reversal = qtyMTransaction.signum() >= 0;
			qtyCapacity = qtyMTransaction.negate();
		}

		if (reversal)
		{
			qtyCapacity = qtyCapacity.negate();
		}

		capacityTotal = capacityBL.createCapacity(qtyCapacity,
				product, uomMTransaction,
				false// allowNegativeCapacity
				);
	}

	@Override
	protected BigDecimal retrieveQtyInitial()
	{
		// Initial storage level
		BigDecimal qty = BigDecimal.ZERO;

		//
		// In case it's an inbound MTransaction (e.g. Vendor Receipts)
		// then this storage is already full with that qty
		if (inbound && !reversal)
		{
			qty = qty.add(capacityTotal.getCapacity());
		}

		//
		// Iterate all HU transactions and adjust qtyAdd and qtyRemoved
		Check.assume(HUConstants.DEBUG_07277_saveHUTrxLine, "Saving HU Trx Lines shall be active for MTransactionProductStorage to work");
		final I_M_Product storageProduct = getM_Product();
		for (final I_M_HU_Trx_Line trxLine : Services.get(IHUTrxDAO.class).retrieveReferencingTrxLines(mtrx))
		{
			// Skip not processed lines
			if (!trxLine.isProcessed())
			{
				continue;
			}

			// Make sure we have the same product
			if (mtrx.getM_Product_ID() != storageProduct.getM_Product_ID())
			{
				// NOTE: not sure but maybe a log message + continue would be enough
				throw new AdempiereException("Invalid product for transaction " + mtrx + ": "
						+ " Expected=" + storageProduct
						+ ", Actual=" + mtrx.getM_Product());
			}

			if (trxLine.getM_HU_Item_ID() > 0)
			{
				// trxLines which count for us are those which does not have the Ref_HU_Item_ID.
				// Those who have Ref_HU_Item_ID set those shall be counted on Item Storage.
				continue;
			}

			final BigDecimal trxQtyAbs = convertToStorageUOM(trxLine.getQty(), trxLine.getC_UOM());
			final BigDecimal trxQty;
			if (!reversal)
			{
				trxQty = trxQtyAbs;
			}
			else
			{
				trxQty = trxQtyAbs; // .negate();
			}

			qty = qty.add(trxQty);
		}

		return qty;
	}

	public boolean isReversal()
	{
		return reversal;
	}

	@Override
	protected IHUCapacityDefinition retrieveTotalCapacity()
	{
		return capacityTotal;
	}
}
