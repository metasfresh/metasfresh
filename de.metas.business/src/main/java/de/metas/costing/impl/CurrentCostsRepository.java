package de.metas.costing.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MOrg;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import lombok.NonNull;

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

@Component
public class CurrentCostsRepository implements ICurrentCostsRepository
{
	private static final Logger logger = LogManager.getLogger(CurrentCostsRepository.class);
	@Autowired
	private ICostElementRepository costElementRepo;

	@Override
	public CurrentCost getOrNull(@NonNull final CostSegment costSegment, final int costElementId)
	{
		final I_M_Cost costRecord = getCostRecordOrNull(costSegment, costElementId);
		if (costRecord == null)
		{
			return null;
		}

		return toCurrentCost(costRecord);
	}

	private I_M_Cost getCostRecordOrNull(final CostSegment costSegment, final int costElementId)
	{
		return retrieveCostRecords(costSegment)
				.addEqualsFilter(I_M_Cost.COLUMN_M_CostElement_ID, costElementId)
				.create()
				.firstOnly(I_M_Cost.class);
	}

	private IQueryBuilder<I_M_Cost> retrieveCostRecords(final CostSegment costSegment)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Cost.class)
				.addEqualsFilter(I_M_Cost.COLUMN_AD_Org_ID, costSegment.getOrgId())
				.addEqualsFilter(I_M_Cost.COLUMN_M_Product_ID, costSegment.getProductId())
				.addEqualsFilter(I_M_Cost.COLUMN_M_AttributeSetInstance_ID, costSegment.getAttributeSetInstanceId())
				.addEqualsFilter(I_M_Cost.COLUMN_M_CostType_ID, costSegment.getCostTypeId())
				.addEqualsFilter(I_M_Cost.COLUMN_C_AcctSchema_ID, costSegment.getAcctSchemaId());
	}

	@Override
	public CurrentCost getOrCreate(@NonNull final CostSegment costSegment, final int costElementId)
	{
		final I_M_Cost costRecord = getCostRecordOrNull(costSegment, costElementId);
		if (costRecord != null)
		{
			return toCurrentCost(costRecord);
		}

		return create(costSegment, costElementId);
	}

	@Override
	public CostResult getByCostSegmentAndCostingMethod(@NonNull final CostSegment costSegment, final CostingMethod costingMethod)
	{
		final Set<Integer> costElementIds = costElementRepo.getByCostingMethod(costingMethod)
				.stream()
				.map(CostElement::getId)
				.collect(ImmutableSet.toImmutableSet());
		if (costElementIds.isEmpty())
		{
			throw new AdempiereException("No cost elements found for costing method: " + costingMethod);
		}

		final ImmutableMap<CostElement, CostAmount> amounts = retrieveCostRecords(costSegment)
				.addInArrayFilter(I_M_Cost.COLUMN_M_CostElement_ID, costElementIds)
				.create()
				.stream(I_M_Cost.class)
				.map(this::toCurrentCost)
				.collect(ImmutableMap.toImmutableMap(CurrentCost::getCostElement, CurrentCost::getCurrentCostPrice));

		return CostResult.builder()
				.costSegment(costSegment)
				.amounts(amounts)
				.build();
	}

	@Override
	public CurrentCost create(final CostSegment costSegment, final int costElementId)
	{
		final I_M_Cost costRecord = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		costRecord.setAD_Org_ID(costSegment.getOrgId());
		costRecord.setC_AcctSchema_ID(costSegment.getAcctSchemaId());
		costRecord.setM_CostType_ID(costSegment.getCostTypeId());
		costRecord.setM_Product_ID(costSegment.getProductId());
		costRecord.setM_AttributeSetInstance_ID(costSegment.getAttributeSetInstanceId());
		costRecord.setM_CostElement_ID(costElementId);

		costRecord.setCurrentCostPrice(BigDecimal.ZERO);
		costRecord.setCurrentCostPriceLL(BigDecimal.ZERO);
		costRecord.setFutureCostPrice(BigDecimal.ZERO);
		costRecord.setFutureCostPriceLL(BigDecimal.ZERO);
		costRecord.setCurrentQty(BigDecimal.ZERO);
		costRecord.setCumulatedAmt(BigDecimal.ZERO);
		costRecord.setCumulatedQty(BigDecimal.ZERO);

		InterfaceWrapperHelper.save(costRecord);

		return toCurrentCost(costRecord);
	}

	@Override
	public void createIfMissing(@NonNull final CostSegment costSegment, final int costElementId)
	{
		getOrCreate(costSegment, costElementId);
	}

	@Override
	public void save(@NonNull final CurrentCost currentCost)
	{
		final I_M_Cost costRecord = InterfaceWrapperHelper.load(currentCost.getId(), I_M_Cost.class);
		updateCostRecord(costRecord, currentCost);
		// costRecord.setProcessed(true); // FIXME Processed is a virtual column ?!?! wtf?!
		InterfaceWrapperHelper.save(costRecord);
	}

	private CurrentCost toCurrentCost(final I_M_Cost costRecord)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final I_C_UOM uom = productBL.getStockingUOM(costRecord.getM_Product_ID());

		final MAcctSchema as = MAcctSchema.get(Env.getCtx(), costRecord.getC_AcctSchema_ID());
		final CostingLevel costingLevel = productBL.getCostingLevel(costRecord.getM_Product_ID(), as);

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(costRecord.getC_AcctSchema_ID())
				.costTypeId(costRecord.getM_CostType_ID())
				.productId(costRecord.getM_Product_ID())
				.clientId(costRecord.getAD_Client_ID())
				.orgId(costRecord.getAD_Org_ID())
				.attributeSetInstanceId(costRecord.getM_AttributeSetInstance_ID())
				.build();

		final CostElement costElement = costElementRepo.getById(costRecord.getM_CostElement_ID());

		return CurrentCost.builder()
				.id(costRecord.getM_Cost_ID())
				.costSegment(costSegment)
				.costElement(costElement)
				.currencyId(as.getC_Currency_ID())
				.precision(as.getCostingPrecision())
				.uom(uom)
				.currentCostPrice(costRecord.getCurrentCostPrice())
				.currentCostPriceLL(costRecord.getCurrentCostPriceLL())
				.currentQty(costRecord.getCurrentQty())
				.cumulatedAmt(costRecord.getCumulatedAmt())
				.cumulatedQty(costRecord.getCumulatedQty())
				.build();
	}

	private void updateCostRecord(final I_M_Cost cost, final CurrentCost from)
	{
		cost.setCurrentCostPrice(from.getCurrentCostPrice().getValue());
		cost.setCurrentCostPriceLL(from.getCurrentCostPriceLL().getValue());
		cost.setCurrentQty(from.getCurrentQty().getQty());
		cost.setCumulatedAmt(from.getCumulatedAmt().getValue());
		cost.setCumulatedQty(from.getCumulatedQty().getQty());
	}

	@Override
	public void createDefaultProductCosts(final I_M_Product product)
	{
		forEachCostSegmentAndElement(product, (costSegment, costElement) -> createIfMissing(costSegment, costElement.getId()));
	}

	@Override
	public void deleteForProduct(final I_M_Product product)
	{
		forEachCostSegmentAndElement(product, (costSegment, costElement) -> {
			final I_M_Cost costRecord = getCostRecordOrNull(costSegment, costElement.getId());
			if (costRecord != null)
			{
				InterfaceWrapperHelper.delete(costRecord);
			}
		});
	}

	private void forEachCostSegmentAndElement(final I_M_Product product, final BiConsumer<CostSegment, CostElement> consumer)
	{
		final Properties ctx = Env.getCtx();
		final List<CostElement> costElements = costElementRepo.getCostElementsWithCostingMethods(product.getAD_Client_ID());

		for (final MAcctSchema as : MAcctSchema.getClientAcctSchema(ctx, product.getAD_Client_ID()))
		{
			if (as.isSkipOrg(product.getAD_Org_ID()))
			{
				continue;
			}

			final CostingLevel costingLevel = Services.get(IProductBL.class).getCostingLevel(product, as);

			// Create Std Costing
			if (costingLevel == CostingLevel.Client)
			{
				final CostSegment costSegment = CostSegment.builder()
						.costingLevel(costingLevel)
						.acctSchemaId(as.getC_AcctSchema_ID())
						.costTypeId(as.getM_CostType_ID())
						.productId(product.getM_Product_ID())
						.clientId(product.getAD_Client_ID())
						.orgId(0)
						.attributeSetInstanceId(0)
						.build();

				costElements.forEach(costElement -> consumer.accept(costSegment, costElement));
			}
			else if (costingLevel == CostingLevel.Organization)
			{
				for (final I_AD_Org org : MOrg.getOfClient(ctx, product.getAD_Client_ID()))
				{
					final CostSegment costSegment = CostSegment.builder()
							.costingLevel(costingLevel)
							.acctSchemaId(as.getC_AcctSchema_ID())
							.costTypeId(as.getM_CostType_ID())
							.productId(product.getM_Product_ID())
							.clientId(product.getAD_Client_ID())
							.orgId(org.getAD_Org_ID())
							.attributeSetInstanceId(0)
							.build();

					costElements.forEach(costElement -> consumer.accept(costSegment, costElement));
				}
			}
			else
			{
				logger.warn("{}'s costing Level {} not supported", product.getName(), costingLevel);
			}
		}	// accounting schema loop
	}

}
