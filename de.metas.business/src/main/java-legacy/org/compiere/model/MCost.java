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
 * Contributor(s): Teo Sarca *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggerTrxItemExceptionHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.methods.LastInvoiceCostingMethodHandler;
import de.metas.costing.methods.LastPOCostingMethodHandler;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

/**
 * Product Cost Model
 *
 * @author Jorg Janke
 * @version $Id: MCost.java,v 1.6 2006/07/30 00:51:02 jjanke Exp $
 *
 * @author Carlos Ruiz - globalqss
 *         <li>integrate bug fix from Teo Sarca - [ 1619112 ] Posible problem for LastPO costing, Batch/Lot level
 *
 * @author Red1
 *         <li>FR: [ 2214883 ] Remove SQL code and Replace for Query - red1 (only non-join query)
 *
 * @author Teo Sarca
 *         <li>BF [ 2847648 ] Manufacture & shipment cost errors
 *         https://sourceforge.net/tracker/?func=detail&aid=2847648&group_id=176962&atid=934929
 */
public class MCost extends X_M_Cost
{
	/**
	 *
	 */
	private static final long serialVersionUID = -127982599769472918L;

	/**
	 * Retrieve/Calculate Current Cost Price
	 *
	 * @param product product
	 * @param M_AttributeSetInstance_ID real asi
	 * @param as accounting schema
	 * @param AD_Org_ID real org
	 * @param costingMethod AcctSchema.COSTINGMETHOD_*
	 * @param qty qty
	 * @param C_OrderLine_ID optional order line
	 * @param zeroCostsOK zero/no costs are OK
	 * @param trxName trx
	 * @return current cost price or null
	 */
	private static BigDecimal getCurrentCost(
			final I_M_Product product,
			final int M_AttributeSetInstance_ID,
			final I_C_AcctSchema as,
			final int AD_Org_ID,
			final CostingMethod costingMethod,
			final BigDecimal qty,
			final int C_OrderLine_ID,
			final boolean zeroCostsOK,
			final String trxName)
	{
		//
		// Cost segment
		final IProductBL productBL = Services.get(IProductBL.class);
		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(productBL.getCostingLevel(product, as))
				.acctSchemaId(as.getC_AcctSchema_ID())
				.costTypeId(as.getM_CostType_ID())
				.productId(product.getM_Product_ID())
				.clientId(product.getAD_Client_ID())
				.orgId(AD_Org_ID)
				.attributeSetInstanceId(M_AttributeSetInstance_ID)
				.build();

		//
		// Costing Method
		final CostingMethod costingMethodEffective;
		if (costingMethod == null)
		{
			costingMethodEffective = productBL.getCostingMethod(product, as);
			if (costingMethodEffective == null)
			{
				throw new IllegalArgumentException("No Costing Method");
			}
		}
		else
		{
			costingMethodEffective = costingMethod;
		}

		// Create/Update Costs
		Adempiere.getBean(ICostDetailService.class).processAllForProduct(product.getM_Product_ID());

		return getCurrentCost(costSegment, costingMethodEffective, qty, C_OrderLine_ID, zeroCostsOK);
	}	// getCurrentCost

	/**
	 * Get Current Cost Price for Costing Level
	 *
	 * @return cost price or null
	 */
	private static BigDecimal getCurrentCost(
			final CostSegment costSegment,
			final CostingMethod costingMethod,
			final BigDecimal qty,
			final int C_OrderLine_ID,
			final boolean zeroCostsOK)
	{
		//
		// Fetch data from M_Cost records (if any)
		BigDecimal materialCostEach = BigDecimal.ZERO;
		BigDecimal otherCostEach = BigDecimal.ZERO;
		BigDecimal percentage = BigDecimal.ZERO;
		int countCostRecords = 0;
		{
			final String sql = "SELECT"
					+ " COALESCE(SUM(c.CurrentCostPrice),0),"		// 1
					+ " ce.CostElementType, ce.CostingMethod,"		// 2,3
					+ " c.Percent, c.M_CostElement_ID ,"			// 4,5
					+ " COALESCE(SUM(c.CurrentCostPriceLL),0) "		// 6
					+ " FROM M_Cost c"
					+ " INNER JOIN M_CostElement ce ON (c.M_CostElement_ID=ce.M_CostElement_ID) "
					+ " WHERE"
					+ " c.AD_Client_ID=?" // #1
					+ " AND c.AD_Org_ID=?" // #2
					+ " AND c.M_Product_ID=?" // #3
					+ " AND (c.M_AttributeSetInstance_ID=? OR c.M_AttributeSetInstance_ID=0)"	// #4
					+ " AND c.M_CostType_ID=?" // #5
					+ " AND c.C_AcctSchema_ID=?" // #6
					+ " AND (ce.CostingMethod IS NULL OR ce.CostingMethod=?) "	// #7
					+ " GROUP BY ce.CostElementType, ce.CostingMethod, c.Percent, c.M_CostElement_ID";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
				pstmt.setInt(1, costSegment.getClientId());
				pstmt.setInt(2, costSegment.getOrgId());
				pstmt.setInt(3, costSegment.getProductId());
				pstmt.setInt(4, costSegment.getAttributeSetInstanceId());
				pstmt.setInt(5, costSegment.getCostTypeId());
				pstmt.setInt(6, costSegment.getAcctSchemaId());
				pstmt.setString(7, costingMethod != null ? costingMethod.getCode() : null);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					final BigDecimal currentCostPrice = rs.getBigDecimal(1);
					final BigDecimal currentCostPriceLL = rs.getBigDecimal(6);
					// final String costElementType = rs.getString(2);
					final String recordCostingMethod = rs.getString(3);
					final BigDecimal percent = rs.getBigDecimal(4);
					// M_CostElement_ID = rs.getInt(5);

					if (recordCostingMethod != null)
					{
						materialCostEach = materialCostEach.add(currentCostPrice).add(currentCostPriceLL);
					}
					else
					{
						otherCostEach = otherCostEach.add(currentCostPrice).add(currentCostPriceLL);
					}

					if (percent != null && percent.signum() != 0)
					{
						percentage = percentage.add(percent);
					}

					countCostRecords++;
				}
			}
			catch (final SQLException e)
			{
				throw new DBException(e, sql);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}

		//
		// Seed Initial Costs
		if (materialCostEach.signum() == 0)		// no costs
		{
			if (zeroCostsOK)
			{
				return BigDecimal.ZERO;
			}

			// Case: we are using StandardCosting and user explicitly set the M_Cost.CurrentCostPrice to ZERO.
			// We shall respect that.
			if (CostingMethod.StandardCosting.equals(costingMethod) && countCostRecords > 0)
			{
				return BigDecimal.ZERO;
			}

			materialCostEach = getSeedCosts(costSegment, costingMethod, C_OrderLine_ID);
		}
		if (materialCostEach == null)
		{
			return null;
		}

		// Material Costs
		BigDecimal materialCost = materialCostEach.multiply(qty);
		if (CostingMethod.StandardCosting.equals(costingMethod))
		{
			// Standard costs - just Material Costs
			return materialCost;
		}
		else if (CostingMethod.FIFO.equals(costingMethod)
				|| CostingMethod.LIFO.equals(costingMethod))
		{
			final CostElement ce = Adempiere.getBean(ICostElementRepository.class).getOrCreateMaterialCostElement(costSegment.getClientId(), costingMethod);
			materialCost = MCostQueue.getCosts(costSegment, ce.getId(), ce.getCostingMethod(), qty);
		}

		// Other Costs
		final BigDecimal otherCost = otherCostEach.multiply(qty);

		// Costs
		BigDecimal costs = otherCost.add(materialCost);
		if (costs.signum() == 0)
		{
			return null;
		}

		//
		//
		final MAcctSchema acctSchema = MAcctSchema.get(Env.getCtx(), costSegment.getAcctSchemaId());
		final int precision = acctSchema.getCostingPrecision();
		if (percentage.signum() == 0)	// no percentages
		{
			if (costs.scale() > precision)
			{
				costs = costs.setScale(precision, RoundingMode.HALF_UP);
			}
			return costs;
		}
		//
		final BigDecimal percentCost = costs
				.multiply(percentage)
				.divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);
		costs = costs.add(percentCost);
		if (costs.scale() > precision)
		{
			costs = costs.setScale(precision, RoundingMode.HALF_UP);
		}

		return costs;
	}	// getCurrentCost

	/**
	 * Get Seed Costs
	 * 
	 * @return price or null
	 */
	static BigDecimal getSeedCosts(
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

	/**
	 * Create costing for client.
	 *
	 * NOTE:
	 * <ul>
	 * <li>this method assumes you are running out of transaction and it will always handle the transaction
	 * <li>it will never throw exception but it errors will be logged
	 * </ul>
	 *
	 * @param client client
	 */
	public static void create(final I_AD_Client client)
	{
		// Get Products.
		// If there are no products, there is no point to go forward
		final Iterator<I_M_Product> products = retrieveProductsWithNotProcessedCostDetails(client);
		if (!products.hasNext())
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(client);
		final MAcctSchema[] acctSchemas = MAcctSchema.getClientAcctSchema(ctx, client.getAD_Client_ID());

		Services.get(ITrxItemProcessorExecutorService.class)
				.<I_M_Product, Void> createExecutor()
				.setContext(ctx, ITrx.TRXNAME_None)
				.setExceptionHandler(LoggerTrxItemExceptionHandler.instance)
				.setProcessor(product -> {
					for (final MAcctSchema acctSchema : acctSchemas)
					{
						final BigDecimal cost = getCurrentCost(
								product, // product
								0, // M_AttributeSetInstance_ID
								acctSchema, // C_AcctSchema
								0, // AD_Org_ID
								null,  // CostingMethod
								BigDecimal.ONE, // Qty
								0, // C_OrderLine_ID
								false, // zeroCostsOK: create non-zero costs
								ITrx.TRXNAME_ThreadInherited);
						s_log.info("{} = {}", product, cost);
					}
				})
				.process(products);
	}

	private static final Iterator<I_M_Product> retrieveProductsWithNotProcessedCostDetails(final I_AD_Client client)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(client);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostDetail.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_CostDetail.COLUMN_Processed, false) // not processed cost details
				//
				// Collect products from those cost details
				.andCollect(I_M_CostDetail.COLUMN_M_Product_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product.COLUMN_AD_Client_ID, client.getAD_Client_ID()) // only for our client
				//
				// Retrieve an iterator of those products
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterate(I_M_Product.class);
	}

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MCost.class);

	/** Data is entered Manually */
	private boolean m_manual = true;

	public MCost(final Properties ctx, final int id, final String trxName)
	{
		super(ctx, id, trxName);
		if (is_new())
		{
			// setC_AcctSchema_ID (0);
			// setM_CostElement_ID (0);
			// setM_CostType_ID (0);
			// setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);
			//
			setCurrentCostPrice(BigDecimal.ZERO);
			setFutureCostPrice(BigDecimal.ZERO);
			setCurrentQty(BigDecimal.ZERO);
			setCumulatedAmt(BigDecimal.ZERO);
			setCumulatedQty(BigDecimal.ZERO);
		}
	}	// MCost

	public MCost(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
		m_manual = false;
	}	// MCost

	/**
	 * Set Current Cost Price
	 *
	 * @param currentCostPrice if null set to 0
	 */
	@Override
	public void setCurrentCostPrice(final BigDecimal currentCostPrice)
	{
		if (currentCostPrice != null)
		{
			super.setCurrentCostPrice(currentCostPrice);
		}
		else
		{
			super.setCurrentCostPrice(BigDecimal.ZERO);
		}
	}	// setCurrentCostPrice

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MCost[");
		sb.append("AD_Client_ID=").append(getAD_Client_ID());
		if (getAD_Org_ID() != 0)
		{
			sb.append(",AD_Org_ID=").append(getAD_Org_ID());
		}
		sb.append(",M_Product_ID=").append(getM_Product_ID());
		if (getM_AttributeSetInstance_ID() != 0)
		{
			sb.append(",AD_ASI_ID=").append(getM_AttributeSetInstance_ID());
		}
		// sb.append (",C_AcctSchema_ID=").append (getC_AcctSchema_ID());
		// sb.append (",M_CostType_ID=").append (getM_CostType_ID());
		sb.append(",M_CostElement_ID=").append(getM_CostElement_ID());
		//
		sb.append(", CurrentCost=").append(getCurrentCostPrice())
				.append(", C.Amt=").append(getCumulatedAmt())
				.append(",C.Qty=").append(getCumulatedQty())
				.append("]");
		return sb.toString();
	}	// toString

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		final CostElement ce = Adempiere.getBean(ICostElementRepository.class).getById(getM_CostElement_ID());
		// Check if data entry makes sense
		if (m_manual)
		{
			final MAcctSchema as = new MAcctSchema(getCtx(), getC_AcctSchema_ID(), null);
			final CostingLevel costingLevel = Services.get(IProductBL.class).getCostingLevel(getM_Product_ID(), as);
			if (CostingLevel.Client.equals(costingLevel))
			{
				if (getAD_Org_ID() > 0 || getM_AttributeSetInstance_ID() > 0)
				{
					throw new AdempiereException("@CostingLevelClient@");
				}
			}
			else if (CostingLevel.BatchLot.equals(costingLevel))
			{
				if (getM_AttributeSetInstance_ID() <= 0 && ce.isMaterialCostingMethod())
				{
					throw new FillMandatoryException(I_M_Cost.COLUMNNAME_M_AttributeSetInstance_ID);
				}
				if (getAD_Org_ID() != 0)
				{
					setAD_Org_ID(0);
				}
			}
		}

		// Cannot enter calculated
		if (m_manual && ce != null && ce.isCalculated())
		{
			throw new AdempiereException("@IsCalculated@");
		}
		// Percentage
		if (ce != null)
		{
			if (ce.isCalculated()
					|| CostElementType.Material.equals(ce.getCostElementType())
							&& getPercent() != 0)
			{
				setPercent(0);
			}
		}
		if (getPercent() != 0)
		{
			if (getCurrentCostPrice().signum() != 0)
			{
				setCurrentCostPrice(BigDecimal.ZERO);
			}
			if (getFutureCostPrice().signum() != 0)
			{
				setFutureCostPrice(BigDecimal.ZERO);
			}
			if (getCumulatedAmt().signum() != 0)
			{
				setCumulatedAmt(BigDecimal.ZERO);
			}
			if (getCumulatedQty().signum() != 0)
			{
				setCumulatedQty(BigDecimal.ZERO);
			}
		}
		return true;
	}	// beforeSave
}	// MCost
