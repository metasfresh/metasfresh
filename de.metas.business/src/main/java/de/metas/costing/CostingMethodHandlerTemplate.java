package de.metas.costing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_ProductionLine;
import org.compiere.model.MCost;
import org.eevolution.model.I_PP_Cost_Collector;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * de.metas.business
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

public abstract class CostingMethodHandlerTemplate implements CostingMethodHandler
{
	private static final Logger logger = LogManager.getLogger(CostingMethodHandlerTemplate.class);

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		return null;
	}

	@Override
	public final void process(final CostDetailEvent event)
	{
		final I_M_Cost costRecord = MCost.getOrCreate(event.getCostSegment(), event.getCostElementId());
		final CostResult cost = toCostResult(costRecord, event.getPrecision());

		final CostingDocumentRef documentRef = event.getDocumentRef();
		final String documentTableName = documentRef.getTableName();
		if (I_C_OrderLine.Table_Name.equals(documentTableName))
		{
			processPurchaseOrderLine(event, cost);
		}
		else if (org.compiere.model.I_C_InvoiceLine.Table_Name.equals(documentTableName))
		{
			processPurchaseInvoice(event, cost);
		}
		else if (I_M_InOutLine.Table_Name.equals(documentTableName))
		{
			processMaterialShipment(event, cost);
		}
		else if (I_M_MovementLine.Table_Name.equals(documentTableName))
		{
			processMovementLine(event, cost);
		}
		else if (I_M_InventoryLine.Table_Name.equals(documentTableName))
		{
			processInventoryLine(event, cost);
		}
		else if (I_M_ProductionLine.Table_Name.equals(documentTableName))
		{
			processProductionLine(event, cost);
		}
		else if (I_C_ProjectIssue.Table_Name.equals(documentTableName))
		{
			processProjectIssue(event, cost);
		}
		else if (I_PP_Cost_Collector.Table_Name.equals(documentTableName))
		{
			processCostCollector(event, cost);
		}
		else
		{
			processOther(event, cost);
		}

		//
		updateCostRecord(costRecord, cost);
		InterfaceWrapperHelper.save(costRecord);
	}

	protected void processPurchaseOrderLine(final CostDetailEvent event, final CostResult cost)
	{
		// nothing on this level
	}

	protected void processPurchaseInvoice(final CostDetailEvent event, final CostResult cost)
	{
		// nothing on this level
	}

	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CostResult cost)
	{
		// nothing on this level
	}

	protected void processMaterialShipment(final CostDetailEvent event, final CostResult cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processMovementLine(final CostDetailEvent event, final CostResult cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processInventoryLine(final CostDetailEvent event, final CostResult cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processProductionLine(final CostDetailEvent event, final CostResult cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processProjectIssue(final CostDetailEvent event, final CostResult cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processCostCollector(final CostDetailEvent event, final CostResult cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processOther(final CostDetailEvent event, final CostResult cost)
	{
		logger.warn("Skip event because document is not handled: {}", event);
	}

	private CostResult toCostResult(final I_M_Cost costRecord, final int precision)
	{
		return CostResult.builder()
				.precision(precision)
				.currentCostPrice(costRecord.getCurrentCostPrice())
				.currentCostPriceLL(costRecord.getCurrentCostPriceLL())
				.currentQty(costRecord.getCurrentQty())
				.cumulatedAmt(costRecord.getCumulatedAmt())
				.cumulatedQty(costRecord.getCumulatedQty())
				.build();
	}

	private void updateCostRecord(final I_M_Cost cost, final CostResult from)
	{
		cost.setCurrentCostPrice(from.getCurrentCostPrice());
		cost.setCurrentCostPriceLL(from.getCurrentCostPriceLL());
		cost.setCurrentQty(from.getCurrentQty());
		cost.setCumulatedAmt(from.getCumulatedAmt());
		cost.setCumulatedQty(from.getCumulatedQty());
	}

	@Builder
	@Getter
	protected static final class CostResult
	{
		private final int precision;

		@NonNull
		@Setter
		private BigDecimal currentCostPrice;
		@NonNull
		private BigDecimal currentCostPriceLL;
		@NonNull
		@Setter
		private BigDecimal currentQty;

		@NonNull
		private BigDecimal cumulatedAmt;
		@NonNull
		private BigDecimal cumulatedQty;

		/**
		 * Add Cumulative Amt/Qty and Current Qty
		 *
		 * @param amt amt
		 * @param qty qty
		 */
		public void add(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
		{
			this.currentQty = this.currentQty.add(qty);

			addCumulatedAmtAndQty(amt, qty);
		}

		/**
		 * Add Amt/Qty and calculate weighted average.
		 * ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty)
		 *
		 * @param amt total amt (price * qty)
		 * @param qty qty
		 */
		public void addWeightedAverage(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
		{
			final BigDecimal currentAmt = currentCostPrice.multiply(currentQty);
			final BigDecimal newAmt = currentAmt.add(amt);
			final BigDecimal newQty = currentQty.add(qty);
			if (newQty.signum() != 0)
			{
				this.currentCostPrice = newAmt.divide(newQty, precision, RoundingMode.HALF_UP);
			}
			this.currentQty = newQty;

			addCumulatedAmtAndQty(amt, qty);
		}

		public void addCumulatedAmtAndQty(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
		{
			this.cumulatedAmt = this.cumulatedAmt.add(amt);
			this.cumulatedQty = this.cumulatedQty.add(qty);
		}
		
		public void adjustCurrentQty(@NonNull final BigDecimal qtyToAdd)
		{
			this.currentQty = this.currentQty.add(qtyToAdd);
		}
	}

}
