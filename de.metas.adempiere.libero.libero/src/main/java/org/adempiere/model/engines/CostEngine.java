/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): victor.perez@e-evolution.com http://www.e-evolution.com *
 *****************************************************************************/

package org.adempiere.model.engines;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MProduct;
import org.compiere.model.MTransaction;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Cost;
import org.eevolution.model.MPPCostCollector;
import org.eevolution.model.X_PP_Cost_Collector;
import org.slf4j.Logger;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementType;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrenctCostsRepository;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;

/**
 * Cost Engine
 *
 * @author victor.perez@e-evolution.com http://www.e-evolution.com
 *
 */
public class CostEngine
{
	/** Logger */
	protected transient Logger log = LogManager.getLogger(getClass());

	public CostingMethod getCostingMethod()
	{
		return CostingMethod.StandardCosting;
	}

	public CostAmount getResourceStandardCostRate(final I_PP_Cost_Collector cc, final int S_Resource_ID, final CostDimension d, final String trxName)
	{
		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(cc);

		final I_S_Resource resource = InterfaceWrapperHelper.create(ctx, S_Resource_ID, I_S_Resource.class, ITrx.TRXNAME_None);

		final I_M_Product resourceProduct = resourceProductService.retrieveProductForResource(resource);
		return getProductStandardCostPrice(
				cc,
				resourceProduct,
				MAcctSchema.get(ctx, d.getC_AcctSchema_ID()),
				Services.get(ICostElementRepository.class).getById(d.getM_CostElement_ID()));
	}

	public CostAmount getResourceActualCostRate(final I_PP_Cost_Collector cc, final int S_Resource_ID, final CostDimension d, final String trxName)
	{
		final MAcctSchema as = MAcctSchema.get(Env.getCtx(), d.getC_AcctSchema_ID());
		if (S_Resource_ID <= 0)
		{
			return CostAmount.zero(as.getC_Currency_ID());
		}

		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
		final I_S_Resource resource = InterfaceWrapperHelper.create(Env.getCtx(), S_Resource_ID, I_S_Resource.class, ITrx.TRXNAME_None);

		final I_M_Product resourceProduct = resourceProductService.retrieveProductForResource(resource);
		return getProductActualCostPrice(
				cc,
				resourceProduct,
				as,
				Services.get(ICostElementRepository.class).getById(d.getM_CostElement_ID()),
				trxName);
	}

	public CostAmount getProductActualCostPriceOrZero(
			final I_PP_Cost_Collector cc,
			final I_M_Product product,
			final I_C_AcctSchema as,
			final CostElement element,
			final String trxName)
	{
		final CostAmount price = getProductActualCostPrice(cc, product, as, element, trxName);
		if (price == null)
		{
			return CostAmount.zero(as.getC_Currency_ID());
		}
		return price;
	}

	/**
	 *
	 * @param cc
	 * @param product
	 * @param as
	 * @param element
	 * @param failIfNoCostFound
	 * @param trxName
	 * @return cost price or null if no cost was found and <code>failIfNoCostFound</code> is <code>true</code>.
	 */
	public CostAmount getProductActualCostPrice(
			final I_PP_Cost_Collector cc,
			final I_M_Product product,
			final I_C_AcctSchema as,
			final CostElement element,
			final String trxName)
	{
		final CurrentCost cost = retrieveOrCreateCostRecord(cc, product, as, element, trxName);

		final CostAmount price = cost.getCurrentCostPriceTotal();
		return roundCost(price, as.getC_AcctSchema_ID());
	}

	private CurrentCost retrieveOrCreateCostRecord(
			final I_PP_Cost_Collector cc,
			final I_M_Product product,
			final I_C_AcctSchema as,
			final CostElement element,
			final String trxName)
	{
		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(Services.get(IProductBL.class).getCostingLevel(product, as))
				.acctSchemaId(as.getC_AcctSchema_ID())
				.costTypeId(as.getM_CostType_ID())
				.productId(product.getM_Product_ID())
				.clientId(product.getAD_Client_ID())
				.orgId(cc.getAD_Org_ID())
				.attributeSetInstanceId(cc.getM_AttributeSetInstance_ID())
				.build();
		return Services.get(ICurrenctCostsRepository.class).getOrCreate(costSegment, element.getId());
	}

	public CostAmount getProductStandardCostPrice(final I_PP_Cost_Collector cc, final I_M_Product product, final I_C_AcctSchema as, final CostElement element)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(cc);

		final CostDimension d = new CostDimension(product,
				as, as.getM_CostType_ID(),
				0, // AD_Org_ID,
				0, // M_ASI_ID,
				element.getId());

		final I_PP_Order_Cost oc = d.toQueryBuilder(I_PP_Order_Cost.class, trxName)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_PP_Order_ID, cc.getPP_Order_ID())
				.create()
				.firstOnly(I_PP_Order_Cost.class);
		if (oc == null)
		{
			return CostAmount.zero(as.getC_Currency_ID());
		}

		final CostAmount costs = CostAmount.of(oc.getCurrentCostPrice().add(oc.getCurrentCostPriceLL()), as.getC_Currency_ID());
		return roundCost(costs, as.getC_AcctSchema_ID());
	}

	protected CostAmount roundCost(final CostAmount price, final int C_AcctSchema_ID)
	{
		final int precision = MAcctSchema.get(Env.getCtx(), C_AcctSchema_ID).getCostingPrecision();
		return price.roundToPrecisionIfNeeded(precision);
	}

	public Collection<MCost> getByElement(final MProduct product, final MAcctSchema as,
			final int M_CostType_ID, final int AD_Org_ID, final int M_AttributeSetInstance_ID, final int M_CostElement_ID)
	{
		final CostDimension cd = new CostDimension(product, as, M_CostType_ID,
				AD_Org_ID, M_AttributeSetInstance_ID,
				M_CostElement_ID);
		return cd.toQuery(MCost.class, product.get_TrxName())
				.setOnlyActiveRecords(true)
				.list();
	}

	/**
	 * Get Cost Detail
	 *
	 * @param model Model Inventory Line
	 * @param as Account Schema
	 * @param M_CostElement_ID Cost Element
	 * @param M_AttributeSetInstance_ID
	 * @return I_M_CostDetail
	 */
	private I_M_CostDetail getCostDetail(final IDocumentLine model, final MTransaction mtrx, final I_C_AcctSchema as, final int M_CostElement_ID)
	{
		final String whereClause = "AD_Client_ID=? AND AD_Org_ID=?"
				+ " AND " + model.get_TableName() + "_ID=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_Product_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_AttributeSetInstance_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_C_AcctSchema_ID + "=?"
				// +" AND "+I_M_CostDetail.COLUMNNAME_M_CostType_ID+"=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_CostElement_ID + "=?";
		final Object[] params = new Object[] {
				mtrx.getAD_Client_ID(),
				mtrx.getAD_Org_ID(),
				model.get_ID(),
				mtrx.getM_Product_ID(),
				mtrx.getM_AttributeSetInstance_ID(),
				as.getC_AcctSchema_ID(),
				// as.getM_CostType_ID(),
				M_CostElement_ID,
		};
		return new Query(mtrx.getCtx(), I_M_CostDetail.Table_Name, whereClause, mtrx.get_TrxName())
				.setParameters(params)
				.firstOnly(I_M_CostDetail.class);
	}

	/**
	 * Create Cost Detail (Material Issue, Material Receipt)
	 *
	 * @param model
	 * @param mtrx Material Transaction
	 */
	public void createCostDetail(final IDocumentLine model, final MTransaction mtrx)
	{
		final I_PP_Cost_Collector cc = model instanceof MPPCostCollector ? (MPPCostCollector)model : null;

		final Properties ctx = mtrx.getCtx();

		for (final I_C_AcctSchema as : getAcctSchema(mtrx))
		{
			// Cost Detail
			final CostingMethod costingMethod = Services.get(IProductBL.class).getCostingMethod(mtrx.getM_Product_ID(), as);
			// Check costing method
			if (!getCostingMethod().equals(costingMethod))
			{
				throw new LiberoException("Costing method not supported - " + costingMethod);
			}
			//
			final I_M_Product product = MProduct.get(ctx, mtrx.getM_Product_ID());
			for (final CostElement element : getCostElements())
			{
				//
				// Delete Unprocessed zero Differences
				deleteCostDetail(model, as, element.getId(), mtrx.getM_AttributeSetInstance_ID());
				//
				// Get Costs
				final BigDecimal qty = mtrx.getMovementQty();
				final CostAmount price = getProductActualCostPriceOrZero(cc, product, as, element, mtrx.get_TrxName());
				final CostAmount amt = roundCost(price.multiply(qty), as.getC_AcctSchema_ID());
				//
				// Create / Update Cost Detail
				I_M_CostDetail cd = getCostDetail(model, mtrx, as, element.getId());
				if (cd == null)		// createNew
				{
					cd = createCostDetail(as, mtrx.getAD_Org_ID(),
							mtrx.getM_Product_ID(), mtrx.getM_AttributeSetInstance_ID(),
							element.getId(),
							amt,
							qty,
							model.getDescription(),
							mtrx.get_TrxName());
					// cd.setMovementDate(mtrx.getMovementDate());
					// if (cost != null)
					// {
					// cd.setCurrentCostPrice(cost.getCurrentCostPrice());
					// cd.setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
					// }
					// else
					// {
					// cd.setCurrentCostPrice(Env.ZERO);
					// cd.setCurrentCostPriceLL(Env.ZERO);
					// }
					// cd.setM_CostType_ID(as.getM_CostType_ID());
					// //cd.setCostingMethod(element.getCostingMethod());
					// cd.setM_Transaction_ID(mtrx.get_ID());
					if (cc != null)
					{
						cd.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
					}
				}
				else
				{
					cd.setDeltaAmt(amt.subtract(cd.getAmt()).getValue());
					cd.setDeltaQty(mtrx.getMovementQty().subtract(cd.getQty()));
					if (isDelta(cd))
					{
						cd.setProcessed(false);
						cd.setAmt(amt.getValue());
						cd.setQty(mtrx.getMovementQty());
					}
				}
				InterfaceWrapperHelper.save(cd);
				processCostDetail(cd);
				log.info("" + cd);
			} // for ELements
		} // Account Schema
	}

	private static boolean isDelta(final I_M_CostDetail costDetail)
	{
		return !(costDetail.getDeltaAmt().signum() == 0
				&& costDetail.getDeltaQty().signum() == 0);
	}	// isDelta

	private static I_M_CostDetail createCostDetail(final I_C_AcctSchema as, final int AD_Org_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID,
			final int M_CostElement_ID, final CostAmount amt, final BigDecimal Qty,
			final String Description, final String trxName)
	{
		final I_M_CostDetail cd = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class, new PlainContextAware(Env.getCtx(), trxName));
		cd.setAD_Org_ID(AD_Org_ID);
		cd.setC_AcctSchema_ID(as.getC_AcctSchema_ID());
		cd.setM_Product_ID(M_Product_ID);
		cd.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		//
		cd.setM_CostElement_ID(M_CostElement_ID);
		//
		cd.setAmt(amt.getValue());
		cd.setQty(Qty);
		cd.setDescription(Description);

		return cd;
	}	// I_M_CostDetail

	private int deleteCostDetail(final IDocumentLine model, final I_C_AcctSchema as, final int M_CostElement_ID,
			final int M_AttributeSetInstance_ID)
	{
		// Delete Unprocessed zero Differences
		final String sql = "DELETE FROM " + I_M_CostDetail.Table_Name
				+ " WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
				+ " AND " + model.get_TableName() + "_ID=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_C_AcctSchema_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_AttributeSetInstance_ID + "=?"
				// + " AND "+I_M_CostDetail.COLUMNNAME_M_CostType_ID+"=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_CostElement_ID + "=?";
		final Object[] parameters = new Object[] { model.get_ID(),
				as.getC_AcctSchema_ID(),
				M_AttributeSetInstance_ID,
				// as.getM_CostType_ID(),
				M_CostElement_ID };

		final int no = DB.executeUpdateEx(sql, parameters, model.get_TrxName());
		if (no != 0)
		{
			log.info("Deleted #" + no);
		}
		return no;
	}

	private void processCostDetail(final I_M_CostDetail cd)
	{
		Services.get(ICostDetailService.class).processIfCostImmediate(cd);
	}

	public static boolean isActivityControlElement(final CostElement element)
	{
		final CostElementType costElementType = element.getCostElementType();
		return CostElementType.Resource.equals(costElementType)
				|| CostElementType.Overhead.equals(costElementType)
				|| CostElementType.BurdenMOverhead.equals(costElementType);
	}

	private List<CostElement> getCostElements()
	{
		return Services.get(ICostElementRepository.class).getByCostingMethod(getCostingMethod());
	}

	private Collection<MAcctSchema> getAcctSchema(final PO po)
	{
		final int AD_Org_ID = po.getAD_Org_ID();
		final MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(po.getCtx(), po.getAD_Client_ID());
		final ArrayList<MAcctSchema> list = new ArrayList<>(ass.length);
		for (final MAcctSchema as : ass)
		{
			if (!as.isSkipOrg(AD_Org_ID))
			{
				list.add(as);
			}
		}
		return list;
	}

	private I_M_CostDetail getCostDetail(final I_PP_Cost_Collector cc, final int M_CostElement_ID)
	{
		final String whereClause = I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_CostElement_ID + "=?";

		final Properties ctx = InterfaceWrapperHelper.getCtx(cc);
		final String trxName = InterfaceWrapperHelper.getTrxName(cc);
		final I_M_CostDetail cd = new Query(ctx, I_M_CostDetail.Table_Name, whereClause, trxName)
				.setParameters(new Object[] { cc.getPP_Cost_Collector_ID(), M_CostElement_ID })
				.setOrderBy(I_M_CostDetail.COLUMNNAME_Qty + " DESC")  // TODO : fix this; we have 2 cost details; now we are taking the one with bigger qty; we must find a proper way
				.first(I_M_CostDetail.class);
		return cd;
	}

	private I_PP_Cost_Collector createVarianceCostCollector(final I_PP_Cost_Collector cc, final String CostCollectorType)
	{
		final I_PP_Cost_Collector ccv = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, cc);
		InterfaceWrapperHelper.copyValues(cc, ccv);
		ccv.setProcessing(false);
		ccv.setProcessed(false);
		ccv.setDocStatus(X_PP_Cost_Collector.DOCSTATUS_Drafted);
		ccv.setDocAction(X_PP_Cost_Collector.DOCACTION_Complete);
		ccv.setCostCollectorType(CostCollectorType);
		ccv.setDocumentNo(null); // reset
		cc.setPP_Cost_Collector_Parent(cc); // link to parent
		InterfaceWrapperHelper.save(ccv);
		return ccv;
	}

	/**
	 * Create & Proce Cost Detail for Variances
	 *
	 * @param ccv
	 * @param amt
	 * @param qty
	 * @param cd (optional)
	 * @param product
	 * @param as
	 * @param element
	 * @return
	 */
	private I_M_CostDetail createVarianceCostDetail(final I_PP_Cost_Collector ccv, final CostAmount amt, final BigDecimal qty,
			final I_M_CostDetail cd, final I_M_Product product, final I_C_AcctSchema as, final CostElement element)
	{
		final I_M_CostDetail cdv = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class, ccv);
		if (cd != null)
		{
			InterfaceWrapperHelper.copyValues(cd, cdv);
			cdv.setProcessed(false);
		}
		if (product != null)
		{
			cdv.setM_Product_ID(product.getM_Product_ID());
			cdv.setM_AttributeSetInstance_ID(0);
		}
		if (as != null)
		{
			cdv.setC_AcctSchema_ID(as.getC_AcctSchema_ID());
		}
		if (element != null)
		{
			cdv.setM_CostElement_ID(element.getId());
		}
		//
		cdv.setPP_Cost_Collector_ID(ccv.getPP_Cost_Collector_ID());
		cdv.setAmt(amt.getValue());
		cdv.setQty(qty);
		InterfaceWrapperHelper.save(cdv);
		processCostDetail(cdv);
		return cdv;
	}

	public void createActivityControl(final MPPCostCollector cc)
	{
		if (!cc.isCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl))
		{
			return;
		}

		final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);

		final I_M_Product resourceProduct = resourceProductService.retrieveProductForResource(cc.getS_Resource());

		//
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(cc.getAD_Client_ID());
		final BigDecimal qty = routingService.getResourceBaseValue(cc.getS_Resource_ID(), cc);
		for (final MAcctSchema as : getAcctSchema(cc))
		{
			for (final CostElement element : getCostElements())
			{
				if (!isActivityControlElement(element))
				{
					continue;
				}
				final CostDimension d = new CostDimension(resourceProduct,
						as,
						as.getM_CostType_ID(),
						0, // AD_Org_ID,
						0, // M_ASI_ID
						element.getId());
				final CostAmount price = getResourceActualCostRate(cc, cc.getS_Resource_ID(), d, cc.get_TrxName());
				final CostAmount costs = price.multiply(qty).roundToPrecisionIfNeeded(as.getCostingPrecision());
				//
				final I_M_CostDetail cd = createCostDetail(as,
						0, // AD_Org_ID,
						d.getM_Product_ID(),
						0, // M_AttributeSetInstance_ID,
						element.getId(),
						costs.negate(),
						qty.negate(),
						"", // Description,
						cc.get_TrxName());
				cd.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
				InterfaceWrapperHelper.save(cd);
				processCostDetail(cd);
			}
		}
	}

	public void createUsageVariances(final MPPCostCollector ccuv)
	{
		// Apply only for material Usage Variance
		if (!ccuv.isCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance))
		{
			throw new IllegalArgumentException("Cost Collector is not Material Usage Variance");
		}
		//
		final I_M_Product product;
		final BigDecimal qty;
		if (ccuv.getPP_Order_BOMLine_ID() > 0)
		{
			product = MProduct.get(ccuv.getCtx(), ccuv.getM_Product_ID());
			qty = ccuv.getMovementQty();
		}
		else
		{
			final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
			product = resourceProductService.retrieveProductForResource(ccuv.getS_Resource());

			final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ccuv.getAD_Client_ID());
			qty = routingService.getResourceBaseValue(ccuv.getS_Resource_ID(), ccuv);
		}
		//
		for (final I_C_AcctSchema as : getAcctSchema(ccuv))
		{
			for (final CostElement element : getCostElements())
			{
				final CostAmount price = getProductActualCostPrice(ccuv, product, as, element, ccuv.get_TrxName());
				final CostAmount amt = roundCost(price.multiply(qty), as.getC_AcctSchema_ID());
				//
				// Create / Update Cost Detail
				createVarianceCostDetail(ccuv,
						amt, qty,
						null, // no original cost detail
						product,
						as,
						element);
			} // for ELements
		} // Account Schema
	}

	public void createRateVariances(final MPPCostCollector cc)
	{
		final I_M_Product product;
		if (cc.isCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl))
		{
			final I_AD_WF_Node node = cc.getPP_Order_Node().getAD_WF_Node();

			final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
			product = resourceProductService.retrieveProductForResource(node.getS_Resource());
		}
		else if (cc.isCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue))
		{
			final I_PP_Order_BOMLine bomLine = cc.getPP_Order_BOMLine();
			product = MProduct.get(cc.getCtx(), bomLine.getM_Product_ID());
		}
		else
		{
			return;
		}

		I_PP_Cost_Collector ccrv = null; // Cost Collector - Rate Variance
		for (final MAcctSchema as : getAcctSchema(cc))
		{
			for (final CostElement element : getCostElements())
			{
				final I_M_CostDetail cd = getCostDetail(cc, element.getId());
				if (cd == null)
				{
					continue;
				}

				//
				final BigDecimal qty = cd.getQty();
				final CostAmount priceStd = getProductStandardCostPrice(cc, product, as, element);
				final CostAmount priceActual = getProductActualCostPriceOrZero(cc, product, as, element, cc.get_TrxName());
				final CostAmount amtStd = roundCost(priceStd.multiply(qty), as.getC_AcctSchema_ID());
				final CostAmount amtActual = roundCost(priceActual.multiply(qty), as.getC_AcctSchema_ID());
				if (amtStd.subtract(amtActual).signum() == 0)
				{
					continue;
				}

				//
				if (ccrv == null)
				{
					ccrv = createVarianceCostCollector(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_RateVariance);
				}
				//
				createVarianceCostDetail(ccrv,
						amtActual.negate(), qty.negate(),
						cd, null, as, element);
				createVarianceCostDetail(ccrv,
						amtStd, qty,
						cd, null, as, element);
			}
		}
		//
		if (ccrv != null)
		{
			Services.get(IDocumentBL.class).processEx(ccrv, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
	}

	public void createMethodVariances(final MPPCostCollector cc)
	{
		if (!cc.isCostCollectorType(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl))
		{
			return;
		}
		//
		final int std_resource_id = cc.getPP_Order_Node().getAD_WF_Node().getS_Resource_ID();
		final int actual_resource_id = cc.getS_Resource_ID();
		if (std_resource_id == actual_resource_id)
		{
			return;
		}
		//
		I_PP_Cost_Collector ccmv = null; // Cost Collector - Method Change Variance
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(cc.getAD_Client_ID());
		for (final I_C_AcctSchema as : getAcctSchema(cc))
		{
			for (final CostElement element : getCostElements())
			{
				final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);

				final I_M_Product resourcePStd = resourceProductService.retrieveProductForResource(cc.getPP_Order_Node().getAD_WF_Node().getS_Resource()); // std_resource;
				final I_M_Product resourcePActual = resourceProductService.retrieveProductForResource(cc.getS_Resource()); // actual_resource;

				final CostAmount priceStd = getProductActualCostPrice(cc, resourcePStd, as, element, cc.get_TrxName());
				final CostAmount priceActual = getProductActualCostPrice(cc, resourcePActual, as, element, cc.get_TrxName());
				if (priceStd.subtract(priceActual).signum() == 0)
				{
					continue;
				}
				//
				if (ccmv == null)
				{
					ccmv = createVarianceCostCollector(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_MethodChangeVariance);
				}
				//
				final BigDecimal qty = routingService.getResourceBaseValue(cc.getS_Resource_ID(), cc);
				final CostAmount amtStd = priceStd.multiply(qty);
				final CostAmount amtActual = priceActual.multiply(qty);
				//
				createVarianceCostDetail(ccmv,
						amtActual, qty,
						null, resourcePActual, as, element);
				createVarianceCostDetail(ccmv,
						amtStd.negate(), qty.negate(),
						null, resourcePStd, as, element);
			}
		}
		//
		if (ccmv != null)
		{
			Services.get(IDocumentBL.class).processEx(ccmv, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
	}

	public void createReversals(final I_PP_Cost_Collector reversalCostCollector)
	{
		Check.assumeNotNull(reversalCostCollector, "reversalCostCollector not null");

		//
		// Get the original cost collector
		final I_PP_Cost_Collector costCollector = reversalCostCollector.getReversal();
		Check.assumeNotNull(costCollector, "costCollector not null");

		//
		// Get the original cost details
		final List<I_M_CostDetail> costDetails = Services.get(IPPCostCollectorDAO.class).retrieveCostDetails(costCollector);

		for (final I_M_CostDetail cd : costDetails)
		{
			createReversal(cd, reversalCostCollector);
		}
	}

	private final void createReversal(final I_M_CostDetail costDetail, final I_PP_Cost_Collector reversalCostCollector)
	{
		final I_M_CostDetail costDetailReversal = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class, costDetail);
		InterfaceWrapperHelper.copyValues(costDetail, costDetailReversal, true); // honorIsCalculated=true
		costDetailReversal.setPP_Cost_Collector_ID(reversalCostCollector.getPP_Cost_Collector_ID());
		costDetailReversal.setQty(costDetail.getQty().negate());
		costDetailReversal.setAmt(costDetail.getAmt().negate());
		costDetailReversal.setDeltaQty(costDetail.getDeltaQty().negate());
		costDetailReversal.setDeltaAmt(costDetail.getDeltaAmt().negate());
		costDetailReversal.setProcessed(false);
		InterfaceWrapperHelper.save(costDetailReversal);

		processCostDetail(costDetailReversal);
	}

}
