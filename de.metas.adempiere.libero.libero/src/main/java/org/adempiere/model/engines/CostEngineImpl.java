package org.adempiere.model.engines;

import static org.adempiere.model.InterfaceWrapperHelper.copyValues;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Cost;
import org.eevolution.model.PPCostCollectorUtils;
import org.eevolution.model.X_PP_Cost_Collector;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaCosting;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

public class CostEngineImpl implements CostEngine
{
	private static final transient Logger logger = LogManager.getLogger(CostEngine.class);
	//
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsService = Services.get(IProductBL.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final IDocumentBL documentsService = Services.get(IDocumentBL.class);
	private final IAcctSchemaDAO acctSchemaRepo = Services.get(IAcctSchemaDAO.class);
	private final IPPCostCollectorDAO costCollectorsRepo = Services.get(IPPCostCollectorDAO.class);
	//
	private final ICostElementRepository costElementsRepo = Adempiere.getBean(ICostElementRepository.class);
	private final CostDetailRepository costDetailRepo = Adempiere.getBean(CostDetailRepository.class);
	private final ICurrentCostsRepository currentCostsRepo = Adempiere.getBean(ICurrentCostsRepository.class);

	CostEngineImpl()
	{
	}

	private CostingMethod getCostingMethod()
	{
		return CostingMethod.StandardCosting;
	}

	private CostAmount getProductActualCostPriceOrZero(final CostSegment costSegment, final CostElementId costElementId)
	{
		return getProductActualCostPrice(costSegment, costElementId);
	}

	@Override
	public CostAmount getProductActualCostPrice(@NonNull final CostSegment costSegment, @NonNull final CostElementId costElementId)
	{
		final CurrentCost cost = retrieveOrCreateCostRecord(costSegment, costElementId);
		final CostAmount price = cost.getCurrentCostPriceTotal();
		return roundCost(price, costSegment.getAcctSchemaId());
	}

	private CurrentCost retrieveOrCreateCostRecord(final CostSegment costSegment, final CostElementId costElementId)
	{
		return currentCostsRepo.getOrCreate(costSegment, costElementId);
	}

	private CostAmount getProductStandardCostPrice(
			final I_PP_Cost_Collector cc,
			final I_M_Product product,
			final AcctSchema acctSchema,
			final CostElementId costElementId)
	{
		final CostDimension d = new CostDimension(product,
				acctSchema,
				acctSchema.getCosting().getCostTypeId(),
				OrgId.ANY,
				AttributeSetInstanceId.NONE,
				costElementId);

		final I_PP_Order_Cost orderCostRecord = d.toQueryBuilder(I_PP_Order_Cost.class, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_PP_Order_Cost.COLUMNNAME_PP_Order_ID, cc.getPP_Order_ID())
				.create()
				.firstOnly(I_PP_Order_Cost.class);
		if (orderCostRecord == null)
		{
			return CostAmount.zero(acctSchema.getCurrencyId());
		}

		final CostAmount costs = CostAmount.of(orderCostRecord.getCurrentCostPrice().add(orderCostRecord.getCurrentCostPriceLL()), acctSchema.getCurrencyId());
		return roundCost(costs, acctSchema.getId());
	}

	private CostAmount roundCost(final CostAmount price, final AcctSchemaId acctSchemaId)
	{
		final AcctSchema acctSchema = acctSchemaRepo.getById(acctSchemaId);
		final AcctSchemaCosting acctSchemaCosting = acctSchema.getCosting();
		final int precision = acctSchemaCosting.getCostingPrecision();
		return price.roundToPrecisionIfNeeded(precision);
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
	private CostDetail getCostDetail(
			final I_PP_Cost_Collector cc,
			final I_M_Transaction mtrx,
			final AcctSchemaId acctSchemaId,
			final CostElementId costElementId)
	{
		final String whereClause = "AD_Client_ID=? AND AD_Org_ID=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_Product_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_AttributeSetInstance_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_C_AcctSchema_ID + "=?"
				// +" AND "+I_M_CostDetail.COLUMNNAME_M_CostType_ID+"=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_CostElement_ID + "=?";
		final Object[] params = new Object[] {
				mtrx.getAD_Client_ID(),
				mtrx.getAD_Org_ID(),
				cc.getPP_Cost_Collector_ID(),
				mtrx.getM_Product_ID(),
				mtrx.getM_AttributeSetInstance_ID(),
				acctSchemaId,
				// as.getM_CostType_ID(),
				costElementId,
		};
		return new Query(Env.getCtx(), I_M_CostDetail.Table_Name, whereClause, ITrx.TRXNAME_ThreadInherited)
				.setParameters(params)
				.firstOnly(I_M_CostDetail.class);
	}

	/**
	 * Create Cost Detail (Material Issue, Material Receipt)
	 */
	@Override
	public void createOrUpdateCostDetail(final I_PP_Cost_Collector cc, final I_M_Transaction mtrx)
	{
		final ProductId productId = ProductId.ofRepoId(mtrx.getM_Product_ID());
		final I_M_Product product = productsRepo.getById(productId);
		final I_C_UOM productUOM = productsService.getStockingUOM(product);
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(mtrx.getM_AttributeSetInstance_ID());
		final Quantity qty = Quantity.of(mtrx.getMovementQty(), productUOM);

		for (final AcctSchema as : getAcctSchema(mtrx))
		{
			final AcctSchemaId acctSchemaId = as.getId();

			// Cost Detail
			final CostingMethod costingMethod = productCostingBL.getCostingMethod(productId, acctSchemaId);
			// Check costing method
			if (!getCostingMethod().equals(costingMethod))
			{
				throw new LiberoException("Costing method not supported - " + costingMethod);
			}
			//
			for (final CostElement element : getCostElements())
			{
				final CostElementId costElementId = element.getId();

				//
				// Delete Unprocessed zero Differences
				deleteCostDetail(cc, acctSchemaId, costElementId, attributeSetInstanceId);
				//
				// Get Costs
				final CostSegment costSegment = createCostSegment(cc, as, product);
				final CostAmount price = getProductActualCostPriceOrZero(costSegment, costElementId);
				final CostAmount amt = roundCost(price.multiply(qty), acctSchemaId);
				//
				// Create / Update Cost Detail
				CostDetail cd = getCostDetail(cc, mtrx, acctSchemaId, costElementId);
				if (cd == null)		// createNew
				{
					cd = createCostDetail(
							acctSchemaId,
							ClientId.ofRepoId(mtrx.getAD_Client_ID()),
							OrgId.ofRepoId(mtrx.getAD_Org_ID()),
							productId,
							attributeSetInstanceId,
							costElementId,
							amt,
							qty,
							cc.getDescription());
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
				saveRecord(cd);
				processCostDetail(cd);
			} // for ELements
		} // Account Schema
	}

	private static boolean isDelta(final I_M_CostDetail costDetail)
	{
		return !(costDetail.getDeltaAmt().signum() == 0
				&& costDetail.getDeltaQty().signum() == 0);
	}	// isDelta

	private CostDetail createCostDetail(
			final AcctSchemaId acctSchemaId,
			final ClientId clientId,
			final OrgId orgId,
			final ProductId productId,
			final AttributeSetInstanceId attributeSetInstanceId,
			final CostElementId costElementId,
			final CostAmount amt,
			final Quantity qty,
			final String description)
	{
		return costDetailRepo.create(CostDetail.builder()
				.clientId(clientId)
				.orgId(orgId)
				//
				.acctSchemaId(acctSchemaId)
				.costElementId(costElementId)
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				//
				.amt(amt)
				.qty(qty)
				.price(null)// TODO
				//
				.changingCosts(false)
				//
				.description(description)
		//
		);
		// cd.setIsSOTrx(false);
	}

	private int deleteCostDetail(
			final I_PP_Cost_Collector cc,
			final AcctSchemaId acctSchemaId,
			final CostElementId costElementId,
			final AttributeSetInstanceId attributeSetInstanceId)
	{
		// Delete Unprocessed zero Differences
		final String sql = "DELETE FROM " + I_M_CostDetail.Table_Name
				+ " WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
				+ " AND " + I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_C_AcctSchema_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_AttributeSetInstance_ID + "=?"
				// + " AND "+I_M_CostDetail.COLUMNNAME_M_CostType_ID+"=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_CostElement_ID + "=?";
		final Object[] parameters = new Object[] {
				cc.getPP_Cost_Collector_ID(),
				acctSchemaId,
				attributeSetInstanceId,
				// as.getM_CostType_ID(),
				costElementId };

		final int no = DB.executeUpdateEx(sql, parameters, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			logger.info("Deleted #" + no);
		}
		return no;
	}

	private void processCostDetail(final CostDetail cd)
	{
		cd.setProcessed(true);
		saveRecord(cd);
		// Adempiere.getBean(ICostDetailService.class).processIfCostImmediate(cd);
	}

	private List<CostElement> getCostElements()
	{
		return costElementsRepo.getByCostingMethod(getCostingMethod());
	}

	private List<AcctSchema> getAcctSchema(final I_M_Transaction mtrx)
	{
		final ClientId clientId = ClientId.ofRepoId(mtrx.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(mtrx.getAD_Org_ID());
		return getAcctSchema(clientId, orgId);
	}

	private List<AcctSchema> getAcctSchema(final I_PP_Cost_Collector cc)
	{
		final ClientId clientId = ClientId.ofRepoId(cc.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(cc.getAD_Org_ID());
		return getAcctSchema(clientId, orgId);
	}

	private ImmutableList<AcctSchema> getAcctSchema(final ClientId clientId, final OrgId orgId)
	{
		return acctSchemaRepo
				.getAllByClient(clientId)
				.stream()
				.filter(acctSchema -> !acctSchema.isDisallowPostingForOrg(orgId))
				.collect(ImmutableList.toImmutableList());
	}

	private CostDetail getCostDetail(final I_PP_Cost_Collector cc, final CostElementId costElementId)
	{
		final String whereClause = I_M_CostDetail.COLUMNNAME_PP_Cost_Collector_ID + "=?"
				+ " AND " + I_M_CostDetail.COLUMNNAME_M_CostElement_ID + "=?";

		final I_M_CostDetail cd = new Query(Env.getCtx(), I_M_CostDetail.Table_Name, whereClause, ITrx.TRXNAME_ThreadInherited)
				.setParameters(new Object[] { cc.getPP_Cost_Collector_ID(), costElementId })
				.setOrderBy(I_M_CostDetail.COLUMNNAME_Qty + " DESC")  // TODO : fix this; we have 2 cost details; now we are taking the one with bigger qty; we must find a proper way
				.first(I_M_CostDetail.class);
		return cd;
	}

	private I_PP_Cost_Collector createVarianceCostCollector(final I_PP_Cost_Collector cc, @NonNull final CostCollectorType costCollectorType)
	{
		final I_PP_Cost_Collector ccv = newInstance(I_PP_Cost_Collector.class, cc);
		copyValues(cc, ccv);
		ccv.setProcessing(false);
		ccv.setProcessed(false);
		ccv.setDocStatus(X_PP_Cost_Collector.DOCSTATUS_Drafted);
		ccv.setDocAction(X_PP_Cost_Collector.DOCACTION_Complete);
		ccv.setCostCollectorType(costCollectorType.getCode());
		ccv.setDocumentNo(null); // reset
		cc.setPP_Cost_Collector_Parent(cc); // link to parent
		saveRecord(ccv);
		return ccv;
	}

	/**
	 * Create & Proce Cost Detail for Variances
	 *
	 * @param cc
	 * @param amt
	 * @param qty
	 * @param cd (optional)
	 * @param product
	 * @param acctSchemaId
	 * @param element
	 * @return
	 */
	private CostDetail createVarianceCostDetail(
			final I_PP_Cost_Collector cc,
			final CostAmount amt,
			final Quantity qty,
			final CostDetail cd,
			final I_M_Product product,
			final AcctSchemaId acctSchemaId,
			final CostElement element)
	{
		final I_M_CostDetail cdv = newInstance(I_M_CostDetail.class, cc);
		if (cd != null)
		{
			copyValues(cd, cdv);
			cdv.setProcessed(false);
		}
		if (product != null)
		{
			cdv.setM_Product_ID(product.getM_Product_ID());
			cdv.setM_AttributeSetInstance_ID(AttributeConstants.M_AttributeSetInstance_ID_None);
		}
		if (acctSchemaId != null)
		{
			cdv.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		}
		if (element != null)
		{
			cdv.setM_CostElement_ID(element.getId().getRepoId());
		}
		//
		cdv.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
		cdv.setAmt(amt.getValue());
		cdv.setQty(qty);
		cdv.setIsSOTrx(false);
		saveRecord(cdv);
		processCostDetail(cdv);
		return cdv;
	}

	@Override
	public void createActivityControl(@NonNull final I_PP_Cost_Collector cc)
	{
		if (!PPCostCollectorUtils.isCostCollectorTypeAnyOf(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl))
		{
			return;
		}

		final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
		final I_M_Product actualResourceProduct = resourceProductService.getProductByResourceId(actualResourceId);

		//
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService();
		final Quantity qty = routingService.getResourceBaseValue(actualResourceId, cc);
		for (final AcctSchema as : getAcctSchema(cc))
		{
			for (final CostElement element : getCostElements())
			{
				if (!element.isActivityControlElement())
				{
					continue;
				}
				final CostElementId costElementId = element.getId();

				final CostSegment costSegment = createCostSegment(cc, as, actualResourceId);
				final CostAmount price = getProductActualCostPrice(costSegment, costElementId);
				final CostAmount costs = price.multiply(qty).roundToPrecisionIfNeeded(as.getCosting().getCostingPrecision());
				//
				final CostDetail cd = createCostDetail(
						as.getId(),
						ClientId.ofRepoId(cc.getAD_Client_ID()),
						OrgId.ANY,
						costSegment.getProductId(),
						AttributeSetInstanceId.NONE,
						costElementId,
						costs.negate(),
						qty.negate(),
						"" // Description,
				);
				cd.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
				saveRecord(cd);
				processCostDetail(cd);
			}
		}
	}

	private CostSegment createCostSegment(
			final I_PP_Cost_Collector cc,
			final AcctSchema as,
			final ResourceId resourceId)
	{
		final I_M_Product product = resourceProductService.getProductByResourceId(resourceId);
		return createCostSegment(cc, as, product);
	}

	private CostSegment createCostSegment(
			final I_PP_Cost_Collector cc,
			final AcctSchema as,
			final I_M_Product product)
	{
		final ClientId clientId = ClientId.ofRepoId(cc.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(cc.getAD_Org_ID());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(cc.getM_AttributeSetInstance_ID());

		return CostSegment.builder()
				.costingLevel(productCostingBL.getCostingLevel(product, as))
				.acctSchemaId(as.getId())
				.costTypeId(as.getCosting().getCostTypeId())
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.clientId(clientId)
				.orgId(orgId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();
	}

	@Override
	public void createUsageVariances(final I_PP_Cost_Collector cc)
	{
		// Apply only for material Usage Variance
		if (!PPCostCollectorUtils.isCostCollectorTypeAnyOf(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance))
		{
			throw new IllegalArgumentException("Cost Collector is not Material Usage Variance");
		}
		//
		final I_M_Product product;
		final Quantity qty;
		if (cc.getPP_Order_BOMLine_ID() > 0)
		{
			product = productsRepo.getById(cc.getM_Product_ID());
			final I_C_UOM productUOM = productsService.getStockingUOM(product);
			qty = Quantity.of(cc.getMovementQty(), productUOM);
		}
		else
		{
			final ResourceId resourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());

			product = resourceProductService.getProductByResourceId(resourceId);

			final RoutingService routingService = RoutingServiceFactory.get().getRoutingService();
			qty = routingService.getResourceBaseValue(resourceId, cc);
		}

		//
		for (final AcctSchema as : getAcctSchema(cc))
		{
			final CostSegment costSegment = createCostSegment(cc, as, product);

			for (final CostElement element : getCostElements())
			{
				final CostElementId costElementId = element.getId();

				final CostAmount price = getProductActualCostPrice(costSegment, costElementId);
				final CostAmount amt = roundCost(price.multiply(qty), as.getId());
				//
				// Create / Update Cost Detail
				createVarianceCostDetail(
						cc,
						amt, qty,
						null, // no original cost detail
						product,
						as.getId(),
						element);
			} // for ELements
		} // Account Schema
	}

	@Override
	public void createRateVariances(final I_PP_Cost_Collector cc)
	{
		final I_M_Product product;
		if (PPCostCollectorUtils.isCostCollectorTypeAnyOf(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl))
		{
			final ResourceId stdResourceId = getStandardResourceId(cc);
			product = resourceProductService.getProductByResourceId(stdResourceId);
		}
		else if (PPCostCollectorUtils.isCostCollectorTypeAnyOf(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue))
		{
			final I_PP_Order_BOMLine bomLine = cc.getPP_Order_BOMLine();
			product = productsRepo.getById(bomLine.getM_Product_ID());
		}
		else
		{
			return;
		}

		I_PP_Cost_Collector ccrv = null; // Cost Collector - Rate Variance
		for (final AcctSchema as : getAcctSchema(cc))
		{
			final CostSegment costSegment = createCostSegment(cc, as, product);

			for (final CostElement element : getCostElements())
			{
				final CostElementId costElementId = element.getId();

				final CostDetail cd = getCostDetail(cc, costElementId);
				if (cd == null)
				{
					continue;
				}

				//
				final Quantity qty = cd.getQty();
				final CostAmount priceStd = getProductStandardCostPrice(cc, product, as, costElementId);
				final CostAmount priceActual = getProductActualCostPriceOrZero(costSegment, costElementId);
				final CostAmount amtStd = roundCost(priceStd.multiply(qty), as.getId());
				final CostAmount amtActual = roundCost(priceActual.multiply(qty), as.getId());
				if (amtStd.subtract(amtActual).signum() == 0)
				{
					continue;
				}

				//
				if (ccrv == null)
				{
					ccrv = createVarianceCostCollector(cc, CostCollectorType.RateVariance);
				}
				//
				createVarianceCostDetail(
						ccrv,
						amtActual.negate(),
						qty.negate(),
						cd,
						null,
						as.getId(),
						element);
				createVarianceCostDetail(
						ccrv,
						amtStd,
						qty,
						cd,
						null,
						as.getId(),
						element);
			}
		}
		//
		if (ccrv != null)
		{
			completeCostCollector(ccrv);
		}
	}

	private ResourceId getStandardResourceId(final I_PP_Cost_Collector cc)
	{
		final I_AD_WF_Node node = cc.getPP_Order_Node().getAD_WF_Node();
		final ResourceId resourceId = ResourceId.ofRepoId(node.getS_Resource_ID());
		return resourceId;
	}

	private void completeCostCollector(final I_PP_Cost_Collector cc)
	{
		documentsService.processEx(cc, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@Override
	public void createMethodVariances(final I_PP_Cost_Collector cc)
	{
		if (!PPCostCollectorUtils.isCostCollectorTypeAnyOf(cc, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl))
		{
			return;
		}

		//
		final ResourceId stdResourceId = getStandardResourceId(cc);
		final ResourceId actualResourceId = ResourceId.ofRepoId(cc.getS_Resource_ID());
		if (ResourceId.equals(actualResourceId, stdResourceId))
		{
			return;
		}

		final I_M_Product stdResourceProduct = resourceProductService.getProductByResourceId(stdResourceId);
		final I_M_Product actualResourceProduct = resourceProductService.getProductByResourceId(actualResourceId);

		//
		I_PP_Cost_Collector ccmv = null; // Cost Collector - Method Change Variance
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService();
		for (final AcctSchema as : getAcctSchema(cc))
		{
			final CostSegment stdResourceCostSegment = createCostSegment(cc, as, stdResourceProduct);
			final CostSegment actualResourceCostSegment = createCostSegment(cc, as, actualResourceProduct);

			for (final CostElement element : getCostElements())
			{
				final CostElementId costElementId = element.getId();

				final CostAmount priceStd = getProductActualCostPrice(stdResourceCostSegment, costElementId);
				final CostAmount priceActual = getProductActualCostPrice(actualResourceCostSegment, costElementId);
				if (priceStd.subtract(priceActual).signum() == 0)
				{
					continue;
				}

				//
				if (ccmv == null)
				{
					ccmv = createVarianceCostCollector(cc, CostCollectorType.MethodChangeVariance);
				}

				//
				final Quantity qty = routingService.getResourceBaseValue(actualResourceId, cc);
				final CostAmount amtStd = priceStd.multiply(qty);
				final CostAmount amtActual = priceActual.multiply(qty);
				//
				createVarianceCostDetail(
						ccmv,
						amtActual,
						qty,
						(CostDetail)null,
						actualResourceProduct,
						as.getId(),
						element);
				createVarianceCostDetail(
						ccmv,
						amtStd.negate(),
						qty.negate(),
						(CostDetail)null,
						stdResourceProduct,
						as.getId(),
						element);
			}
		}
		//
		if (ccmv != null)
		{
			completeCostCollector(ccmv);
		}
	}

	@Override
	public void createReversals(final I_PP_Cost_Collector reversalCostCollector)
	{
		Check.assumeNotNull(reversalCostCollector, "reversalCostCollector not null");

		//
		// Get the original cost collector
		final I_PP_Cost_Collector costCollector = reversalCostCollector.getReversal();
		Check.assumeNotNull(costCollector, "costCollector not null");

		//
		// Get the original cost details
		final List<CostDetail> costDetails = costCollectorsRepo.retrieveCostDetails(costCollector);

		for (final CostDetail cd : costDetails)
		{
			createReversal(cd, reversalCostCollector);
		}
	}

	private final void createReversal(final CostDetail costDetail, final I_PP_Cost_Collector reversalCostCollector)
	{
		final I_M_CostDetail costDetailReversal = newInstance(I_M_CostDetail.class);
		copyValues(costDetail, costDetailReversal, true); // honorIsCalculated=true
		costDetailReversal.setPP_Cost_Collector_ID(reversalCostCollector.getPP_Cost_Collector_ID());
		costDetailReversal.setQty(costDetail.getQty().negate());
		costDetailReversal.setAmt(costDetail.getAmt().negate());
		costDetailReversal.setDeltaQty(costDetail.getDeltaQty().negate());
		costDetailReversal.setDeltaAmt(costDetail.getDeltaAmt().negate());
		costDetailReversal.setProcessed(false);
		saveRecord(costDetailReversal);

		processCostDetail(costDetailReversal);
	}
}
