/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.costing.CostElement;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.methods.LastInvoiceCostingMethodHandler;
import de.metas.costing.methods.LastPOCostingMethodHandler;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;

/**
 * Cost Queue Model
 *
 * @author Jorg Janke
 * @version $Id: MCostQueue.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MCostQueue extends X_M_CostQueue
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1782836708418500130L;

	/**
	 * Get/Create Cost Queue Record.
	 * CostingLevel is not validated
	 *
	 * @return cost queue
	 */
	public static MCostQueue get(final CostSegment costSegment, final int costElementId)
	{
		I_M_CostQueue costQ = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostQueue.class)
				.addEqualsFilter(I_M_CostQueue.COLUMN_AD_Client_ID, costSegment.getClientId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_AD_Org_ID, costSegment.getOrgId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_Product_ID, costSegment.getProductId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_AttributeSetInstance_ID, costSegment.getAttributeSetInstanceId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_CostType_ID, costSegment.getCostTypeId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_C_AcctSchema_ID, costSegment.getAcctSchemaId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_CostElement_ID, costElementId)
				.create()
				.first(I_M_CostQueue.class);

		// New
		if (costQ == null)
		{
			costQ = new MCostQueue(costSegment, costElementId);
		}
		return LegacyAdapters.convertToPO(costQ);
	}	// get

	/**
	 * Get Cost Queue Records in Lifo/Fifo order
	 * 
	 * @return cost queue or null
	 */
	public static List<MCostQueue> getQueue(final CostSegment costSegment, final int costElementId, final CostingMethod costingMethod)
	{
		final IQueryBuilder<I_M_CostQueue> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostQueue.class)
				.addEqualsFilter(I_M_CostQueue.COLUMN_AD_Client_ID, costSegment.getClientId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_AD_Org_ID, costSegment.getOrgId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_Product_ID, costSegment.getProductId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_CostType_ID, costSegment.getCostTypeId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_C_AcctSchema_ID, costSegment.getAcctSchemaId())
				.addEqualsFilter(I_M_CostQueue.COLUMN_M_CostElement_ID, costElementId)
				.addNotEqualsFilter(I_M_CostQueue.COLUMN_CurrentQty, BigDecimal.ZERO);
		if (costSegment.getAttributeSetInstanceId() > 0)
		{
			queryBuilder.addEqualsFilter(I_M_CostQueue.COLUMN_M_AttributeSetInstance_ID, costSegment.getAttributeSetInstanceId());
		}

		if (costingMethod == CostingMethod.FIFO)
		{
			queryBuilder.orderBy(I_M_CostQueue.COLUMN_M_AttributeSetInstance_ID);
		}
		else if (costingMethod == CostingMethod.LIFO)
		{
			queryBuilder.orderByDescending(I_M_CostQueue.COLUMNNAME_M_AttributeSetInstance_ID);
		}
		else
		{
			throw new AdempiereException("Costing method not supported: " + costingMethod + ". It shall be FIFO or LIFO.");
		}

		final List<I_M_CostQueue> queue = queryBuilder.create().list(I_M_CostQueue.class);
		return LegacyAdapters.convertToPOList(queue);
	}	// getQueue

	/**
	 * Adjust Qty based on in Lifo/Fifo order
	 * 
	 * @return cost price reduced or null of error
	 */
	public static BigDecimal adjustQty(final CostSegment costSegment, final int costElementId, final CostingMethod costingMethod, final Quantity Qty)
	{
		if (Qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal remainingQty = Qty.getQty();
		for (final MCostQueue queue : getQueue(costSegment, costElementId, costingMethod))
		{
			// Negative Qty i.e. add
			if (remainingQty.signum() < 0)
			{
				final BigDecimal oldQty = queue.getCurrentQty();
				final BigDecimal newQty = oldQty.subtract(remainingQty);
				queue.setCurrentQty(newQty);
				if (queue.save())
				{
					s_log.debug("Qty=" + remainingQty
							+ "(!), ASI=" + queue.getM_AttributeSetInstance_ID()
							+ " - " + oldQty + " -> " + newQty);
					return queue.getCurrentCostPrice();
				}
				else
				{
					return null;
				}
			}

			// Positive queue
			if (queue.getCurrentQty().signum() > 0)
			{
				BigDecimal reduction = remainingQty;
				if (reduction.compareTo(queue.getCurrentQty()) > 0)
				{
					reduction = queue.getCurrentQty();
				}
				final BigDecimal oldQty = queue.getCurrentQty();
				final BigDecimal newQty = oldQty.subtract(reduction);
				queue.setCurrentQty(newQty);
				if (queue.save())
				{
					s_log.debug("Qty=" + reduction
							+ ", ASI=" + queue.getM_AttributeSetInstance_ID()
							+ " - " + oldQty + " -> " + newQty);
					remainingQty = remainingQty.subtract(reduction);
				}
				else
				{
					return null;
				}
				//
				if (remainingQty.signum() == 0)
				{
					return queue.getCurrentCostPrice();
				}
			}
		}	// for queue

		s_log.debug("RemainingQty={}", remainingQty);
		return null;
	}	// adjustQty

	/**
	 * Calculate Cost based on Qty based on in Lifo/Fifo order
	 * 
	 * @return cost for qty or null of error
	 */
	public static BigDecimal getCosts(final CostSegment costSegment, final int costElementId, final CostingMethod costingMethod, final BigDecimal Qty)
	{
		if (Qty.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		//
		BigDecimal cost = BigDecimal.ZERO;
		BigDecimal remainingQty = Qty;
		BigDecimal firstPrice = null;
		BigDecimal lastPrice = null;
		//
		for (final MCostQueue queue : getQueue(costSegment, costElementId, costingMethod))
		{
			// Negative Qty i.e. add
			if (remainingQty.signum() <= 0)
			{
				final BigDecimal oldQty = queue.getCurrentQty();
				lastPrice = queue.getCurrentCostPrice();
				final BigDecimal costBatch = lastPrice.multiply(remainingQty);
				cost = cost.add(costBatch);
				s_log.info("ASI=" + queue.getM_AttributeSetInstance_ID()
						+ " - Cost=" + lastPrice + " * Qty=" + remainingQty + "(!) = " + costBatch);
				return cost;
			}

			// Positive queue
			if (queue.getCurrentQty().signum() > 0)
			{
				BigDecimal reduction = remainingQty;
				if (reduction.compareTo(queue.getCurrentQty()) > 0)
				{
					reduction = queue.getCurrentQty();
				}
				final BigDecimal oldQty = queue.getCurrentQty();
				lastPrice = queue.getCurrentCostPrice();
				final BigDecimal costBatch = lastPrice.multiply(reduction);
				cost = cost.add(costBatch);
				s_log.debug("ASI=" + queue.getM_AttributeSetInstance_ID()
						+ " - Cost=" + lastPrice + " * Qty=" + reduction + " = " + costBatch);
				remainingQty = remainingQty.subtract(reduction);
				// Done
				if (remainingQty.signum() == 0)
				{
					s_log.info("Cost=" + cost);
					return cost;
				}
				if (firstPrice == null)
				{
					firstPrice = lastPrice;
				}
			}
		}	// for queue

		if (lastPrice == null)
		{
			lastPrice = getSeedCosts(costSegment, costingMethod, 0); // C_OrderLine_ID=0
			if (lastPrice == null)
			{
				s_log.info("No Price found");
				return null;
			}
			s_log.info("No Cost Queue");
		}
		final BigDecimal costBatch = lastPrice.multiply(remainingQty);
		s_log.debug("RemainingQty=" + remainingQty + " * LastPrice=" + lastPrice + " = " + costBatch);
		cost = cost.add(costBatch);
		s_log.info("Cost=" + cost);
		return cost;
	}	// getCosts

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MCostQueue.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param ignored multi-key
	 * @param trxName trx
	 */
	public MCostQueue(final Properties ctx, final int id, final String trxName)
	{
		super(ctx, id, trxName);
		if (id == 0)
		{
			// setC_AcctSchema_ID (0);
			// setM_AttributeSetInstance_ID (0);
			// setM_CostElement_ID (0);
			// setM_CostType_ID (0);
			// setM_Product_ID (0);
			setCurrentCostPrice(BigDecimal.ZERO);
			setCurrentQty(BigDecimal.ZERO);
		}
	}	// MCostQueue

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName trx
	 */
	public MCostQueue(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MCostQueue

	/**
	 * Parent Constructor
	 *
	 * @param product Product
	 * @param M_AttributeSetInstance_ID asi
	 * @param as Acct Schema
	 * @param AD_Org_ID org
	 * @param M_CostElement_ID cost element
	 * @param trxName transaction
	 */
	private MCostQueue(final CostSegment costSegment, final int costElementId)
	{
		this(Env.getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
		setAD_Org_ID(costSegment.getOrgId());
		setC_AcctSchema_ID(costSegment.getAcctSchemaId());
		setM_CostType_ID(costSegment.getCostTypeId());
		setM_Product_ID(costSegment.getProductId());
		setM_AttributeSetInstance_ID(costSegment.getAttributeSetInstanceId());
		setM_CostElement_ID(costElementId);
	}

	/**
	 * Update Record.
	 * ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty)
	 *
	 * @param amt total Amount
	 * @param qty quantity
	 * @param precision costing precision
	 */
	public void setCosts(final BigDecimal amt, final Quantity qty, final int precision)
	{
		final BigDecimal oldSum = getCurrentCostPrice().multiply(getCurrentQty());
		final BigDecimal newSum = amt;	// is total already
		final BigDecimal sumAmt = oldSum.add(newSum);
		final BigDecimal sumQty = getCurrentQty().add(qty.getQty());
		if (sumQty.signum() != 0)
		{
			final BigDecimal cost = sumAmt.divide(sumQty, precision, BigDecimal.ROUND_HALF_UP);
			setCurrentCostPrice(cost);
		}
		//
		setCurrentQty(getCurrentQty().add(qty.getQty()));
	}	// update

	/**
	 * Get Seed Costs
	 * 
	 * @return price or null
	 */
	private static BigDecimal getSeedCosts(
			final CostSegment costSegment,
			final CostingMethod costingMethod,
			final int C_OrderLine_ID)
	{
		BigDecimal retValue = Adempiere.getBean(ICostDetailService.class).calculateSeedCosts(costSegment, costingMethod, C_OrderLine_ID);
		if (retValue != null && retValue.signum() != 0)
		{
			return retValue;
		}

		// Look for exact Order Line
		if (C_OrderLine_ID > 0)
		{
			retValue = LastPOCostingMethodHandler.getPOPrice(costSegment, C_OrderLine_ID);
			if (retValue != null && retValue.signum() != 0)
			{
				return retValue;
			}
		}

		// Look for Standard Costs first
		if (costingMethod != CostingMethod.StandardCosting)
		{
			final CostElement ce = Adempiere.getBean(ICostElementRepository.class).getOrCreateMaterialCostElement(costSegment.getClientId(), CostingMethod.StandardCosting);
			final CurrentCost cost = Adempiere.getBean(ICurrentCostsRepository.class).getOrNull(costSegment, ce.getId());
			if (cost != null && cost.getCurrentCostPrice().signum() != 0)
			{
				return cost.getCurrentCostPrice().getValue();
			}
		}

		// We do not have a price
		// PO first
		if (CostingMethod.AveragePO.equals(costingMethod)
				|| CostingMethod.LastPOPrice.equals(costingMethod)
				|| CostingMethod.StandardCosting.equals(costingMethod))
		{
			// try Last PO
			retValue = LastPOCostingMethodHandler.getLastPOPrice(costSegment);
			if (costSegment.getOrgId() > 0 && (retValue == null || retValue.signum() == 0))
			{
				retValue = LastPOCostingMethodHandler.getLastPOPrice(costSegment.toBuilder().orgId(0).build());
			}
			if (retValue != null && retValue.signum() != 0)
			{
				return retValue;
			}
		}
		else	// Inv first
		{
			// try last Inv
			retValue = LastInvoiceCostingMethodHandler.getLastInvoicePrice(costSegment);
			if (costSegment.getOrgId() > 0 && (retValue == null || retValue.signum() == 0))
			{
				retValue = LastInvoiceCostingMethodHandler.getLastInvoicePrice(costSegment.toBuilder().orgId(0).build());
			}
			if (retValue != null && retValue.signum() != 0)
			{
				return retValue;
			}
		}

		// Still Nothing
		// Inv second
		if (CostingMethod.AveragePO.equals(costingMethod)
				|| CostingMethod.LastPOPrice.equals(costingMethod)
				|| CostingMethod.StandardCosting.equals(costingMethod))
		{
			// try last Inv
			retValue = LastInvoiceCostingMethodHandler.getLastInvoicePrice(costSegment);
			if (costSegment.getOrgId() > 0 && (retValue == null || retValue.signum() == 0))
			{
				retValue = LastInvoiceCostingMethodHandler.getLastInvoicePrice(costSegment.toBuilder().orgId(0).build());
			}
			if (retValue != null && retValue.signum() != 0)
			{
				return retValue;
			}
		}
		else	// PO second
		{
			// try Last PO
			retValue = LastPOCostingMethodHandler.getLastPOPrice(costSegment);
			if (costSegment.getOrgId() > 0 && (retValue == null || retValue.signum() == 0))
			{
				retValue = LastPOCostingMethodHandler.getLastPOPrice(costSegment.toBuilder().orgId(0).build());
			}
			if (retValue != null && retValue.signum() != 0)
			{
				return retValue;
			}
		}

		// Still nothing try Purchase Price List
		// ....

		return retValue;
	}	// getSeedCosts

}	// MCostQueue
