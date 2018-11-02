package de.metas.costing.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;

import org.adempiere.acct.api.AcctSchema;
import org.adempiere.acct.api.AcctSchemaId;
import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_Product;
import org.compiere.model.MOrg;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostResult;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
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
	public CurrentCost getOrNull(@NonNull final CostSegment costSegment, final CostElementId costElementId)
	{
		final I_M_Cost costRecord = getCostRecordOrNull(costSegment, costElementId);
		if (costRecord == null)
		{
			return null;
		}

		return toCurrentCost(costRecord);
	}

	private I_M_Cost getCostRecordOrNull(final CostSegment costSegment, @NonNull final CostElementId costElementId)
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
	public CurrentCost getOrCreate(@NonNull final CostSegment costSegment, final CostElementId costElementId)
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
		final Set<CostElementId> costElementIds = costElementRepo.getByCostingMethod(costingMethod)
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
	public CurrentCost create(final CostSegment costSegment, @NonNull final CostElementId costElementId)
	{
		final I_M_Cost costRecord = InterfaceWrapperHelper.newInstance(I_M_Cost.class);
		costRecord.setAD_Org_ID(costSegment.getOrgId().getRepoId());
		costRecord.setC_AcctSchema_ID(costSegment.getAcctSchemaId().getRepoId());
		costRecord.setM_CostType_ID(costSegment.getCostTypeId().getRepoId());
		costRecord.setM_Product_ID(costSegment.getProductId().getRepoId());
		costRecord.setM_AttributeSetInstance_ID(costSegment.getAttributeSetInstanceId().getRepoId());
		costRecord.setM_CostElement_ID(costElementId.getRepoId());

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
	public void createIfMissing(@NonNull final CostSegment costSegment, final CostElementId costElementId)
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
		final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
		
		ProductId productId = ProductId.ofRepoId(costRecord.getM_Product_ID());
		final I_C_UOM uom = productBL.getStockingUOM(productId);
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(costRecord.getC_AcctSchema_ID());

		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, as);

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchemaId)
				.costTypeId(CostTypeId.ofRepoId(costRecord.getM_CostType_ID()))
				.productId(productId)
				.clientId(ClientId.ofRepoId(costRecord.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(costRecord.getAD_Org_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(costRecord.getM_AttributeSetInstance_ID()))
				.build();

		final CostElementId costElementId = CostElementId.ofRepoId(costRecord.getM_CostElement_ID());
		final CostElement costElement = costElementRepo.getById(costElementId);

		return CurrentCost.builder()
				.id(costRecord.getM_Cost_ID())
				.costSegment(costSegment)
				.costElement(costElement)
				.currencyId(as.getCurrencyId())
				.precision(as.getCosting().getCostingPrecision())
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
		cost.setCurrentQty(from.getCurrentQty().getAsBigDecimal());
		cost.setCumulatedAmt(from.getCumulatedAmt().getValue());
		cost.setCumulatedQty(from.getCumulatedQty().getAsBigDecimal());
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
		ClientId clientId = ClientId.ofRepoId(product.getAD_Client_ID());
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final OrgId productOrgId = OrgId.ofRepoId(product.getAD_Org_ID());

		final List<CostElement> costElements = costElementRepo.getCostElementsWithCostingMethods(clientId);

		for (final AcctSchema as : Services.get(IAcctSchemaDAO.class).getAllByClient(clientId))
		{
			if (as.isDisallowPostingForOrg(productOrgId))
			{
				continue;
			}

			final AcctSchemaId acctSchemaId = as.getId();
			final CostTypeId costTypeId = as.getCosting().getCostTypeId();
			final CostingLevel costingLevel = Services.get(IProductCostingBL.class).getCostingLevel(product, as);

			// Create Std Costing
			if (costingLevel == CostingLevel.Client)
			{
				final CostSegment costSegment = CostSegment.builder()
						.costingLevel(costingLevel)
						.acctSchemaId(acctSchemaId)
						.costTypeId(costTypeId)
						.productId(productId)
						.clientId(clientId)
						.orgId(OrgId.ANY)
						.attributeSetInstanceId(AttributeSetInstanceId.NONE)
						.build();

				costElements.forEach(costElement -> consumer.accept(costSegment, costElement));
			}
			else if (costingLevel == CostingLevel.Organization)
			{
				final Properties ctx = Env.getCtx();
				for (final I_AD_Org org : MOrg.getOfClient(ctx, clientId.getRepoId()))
				{
					final CostSegment costSegment = CostSegment.builder()
							.costingLevel(costingLevel)
							.acctSchemaId(acctSchemaId)
							.costTypeId(costTypeId)
							.productId(productId)
							.clientId(clientId)
							.orgId(OrgId.ofRepoId(org.getAD_Org_ID()))
							.attributeSetInstanceId(AttributeSetInstanceId.NONE)
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
