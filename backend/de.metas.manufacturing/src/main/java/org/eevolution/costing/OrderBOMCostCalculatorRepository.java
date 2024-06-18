package org.eevolution.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingLevel;
import de.metas.costing.IProductCostingBL;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import org.eevolution.api.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.PPOrderCost;
import org.eevolution.api.PPOrderCostTrxType;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

public class OrderBOMCostCalculatorRepository implements BOMCostCalculatorRepository
{
	// services
	private static final Logger logger = LogManager.getLogger(OrderBOMCostCalculatorRepository.class);
	private final IPPOrderBOMDAO orderBOMsRepo = Services.get(IPPOrderBOMDAO.class);
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderCostBL orderCostBL = Services.get(IPPOrderCostBL.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);

	private final PPOrderId orderId;
	private final ProductId mainProductId;
	private final AttributeSetInstanceId mainProductAsiId;
	private final Quantity mainProductQty;

	private final ImmutableSet<CostElementId> costElementIds;

	private final ClientId clientId;
	private final OrgId orgId;
	private final AcctSchema acctSchema;

	@Builder
	public OrderBOMCostCalculatorRepository(
			@NonNull final PPOrderId orderId,
			@NonNull final ProductId mainProductId,
			@NonNull final AttributeSetInstanceId mainProductAsiId,
			@NonNull final Quantity mainProductQty,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchema acctSchema,
			@NonNull final Set<CostElementId> costElementIds)
	{
		Check.assumeNotEmpty(costElementIds, "costElementIds is not empty");

		this.orderId = orderId;
		this.mainProductId = mainProductId;
		this.mainProductAsiId = mainProductAsiId;
		this.mainProductQty = mainProductQty;
		this.clientId = clientId;
		this.orgId = orgId;
		this.acctSchema = acctSchema;
		this.costElementIds = ImmutableSet.copyOf(costElementIds);
	}

	private void assertExpectedOrderId(final PPOrderId orderId)
	{
		Check.assumeEquals(this.orderId, orderId, "orderId");
	}

	@Override
	public Optional<BOM> getBOM(@NonNull final ProductId productId)
	{
		Check.assumeEquals(productId, mainProductId, "productId");

		final PPOrderCosts orderCosts = orderCostBL.getByOrderId(orderId);

		final BOM bom = getBOM(orderCosts);
		return Optional.of(bom);
	}

	public BOM getBOM(final PPOrderCosts orderCosts)
	{
		assertExpectedOrderId(orderCosts.getOrderId());

		final ImmutableList<BOMLine> bomLines = orderBOMsRepo.retrieveOrderBOMLines(orderId)
				.stream()
				.map(orderBOMLineRecord -> toCostingBOMLine(orderBOMLineRecord, orderCosts))
				.collect(ImmutableList.toImmutableList());

		return BOM.builder()
				.productId(mainProductId)
				.asiId(mainProductAsiId)
				.qty(mainProductQty)
				.lines(bomLines)
				.costPrice(getProductCostPrice(mainProductId, orderCosts, mainProductQty.getUOM()))
				.build();
	}

	private BOMLine toCostingBOMLine(
			final I_PP_Order_BOMLine orderBOMLineRecord,
			final PPOrderCosts orderCosts)
	{
		final ProductId productId = ProductId.ofRepoId(orderBOMLineRecord.getM_Product_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderBOMLineRecord.getM_AttributeSetInstance_ID());
		final BOMComponentType componentType = BOMComponentType.ofCode(orderBOMLineRecord.getComponentType());
		final Percent scrapPercent = Percent.of(orderBOMLineRecord.getScrap());

		final Quantity qty;
		if (componentType.isIssue())
		{
			qty = orderBOMBL.getQtyRequiredToIssue(orderBOMLineRecord);
		}
		else if (componentType.isReceipt())
		{
			qty = orderBOMBL.getQtyRequiredToReceive(orderBOMLineRecord);
		}
		else
		{
			throw new AdempiereException("Component type not supported: " + componentType);
		}

		final Percent coProductCostDistributionPercent = componentType.isCoProduct()
				? orderBOMBL.getCoProductCostDistributionPercent(orderBOMLineRecord, mainProductQty)
				: null;

		return BOMLine.builder()
				.componentType(componentType)
				.componentId(productId)
				.asiId(asiId)
				.qty(qty)
				.scrapPercent(scrapPercent)
				.costPrice(getProductCostPrice(productId, orderCosts, qty.getUOM()))
				.coProductCostDistributionPercent(coProductCostDistributionPercent)
				.build();

	}

	private BOMCostPrice getProductCostPrice(
			@NonNull final ProductId productId,
			@NonNull final PPOrderCosts orderCosts,
			@NonNull final I_C_UOM targetUOM)
	{
		final UomId targetUomId = UomId.ofRepoId(targetUOM.getC_UOM_ID());

		final List<BOMCostElementPrice> costElementPrices = orderCosts.getByProductAndCostElements(productId, costElementIds)
				.stream()
				.map(cost -> toBOMCostElementPrice(cost, targetUomId))
				.collect(ImmutableList.toImmutableList());

		return BOMCostPrice.builder()
				.productId(productId)
				.uomId(targetUomId)
				.costElementPrices(costElementPrices)
				.build();
	}

	@NonNull
	private BOMCostElementPrice toBOMCostElementPrice(
			@NonNull final PPOrderCost cost,
			@NonNull final UomId targetUomId)
	{
		final CostPrice costPrice = convertCostPrice(cost.getPrice(), cost.getProductId(), targetUomId);
		return BOMCostElementPrice.builder()
				.id(cost.getId())
				.costElementId(cost.getCostElementId())
				.costPrice(costPrice)
				.build();
	}

	private CostPrice convertCostPrice(
			@NonNull final CostPrice costPrice,
			@NonNull final ProductId productId,
			@NonNull final UomId targetUomId)
	{
		if (UomId.equals(costPrice.getUomId(), targetUomId))
		{
			return costPrice;
		}

		final UomId uomId = costPrice.getUomId();
		final CurrencyPrecision costingPrecision = currencyRepository.getCostingPrecision(costPrice.getCurrencyId());

		return costPrice.convertAmounts(targetUomId, costAmount -> {
			final ProductPrice productPrice = ProductPrice.builder()
					.productId(productId)
					.uomId(uomId)
					.money(costAmount.toMoney())
					.build();
			final ProductPrice productPriceConv = uomConversionBL.convertProductPriceToUom(productPrice, targetUomId, costingPrecision);
			return CostAmount.ofProductPrice(productPriceConv);
		});
	}

	@Override
	public void save(@NonNull final BOM bom)
	{
		final PPOrderCosts orderCosts = orderCostBL.getByOrderId(orderId);
		changeOrderCostsFromBOM(orderCosts, bom);
		orderCostBL.save(orderCosts);
	}

	/**
	 * Updates order costs from BOM line costs
	 */
	public void changeOrderCostsFromBOM(
			@NonNull final PPOrderCosts currentOrderCosts,
			@NonNull final BOM fromBOM)
	{
		final LinkedHashMap<CostSegmentAndElement, PPOrderCost> newOrderCosts = new LinkedHashMap<>();

		//
		// BOM Header Product Cost
		for (final BOMCostElementPrice fromElementCostPrice : fromBOM.getElementPrices())
		{
			final CostSegmentAndElement costSegmentAndElement = createCostSegmentAndElement(fromBOM.getProductId(), fromBOM.getAsiId(), fromElementCostPrice.getCostElementId());

			PPOrderCost elementOrderCost = newOrderCosts.get(costSegmentAndElement);
			if (elementOrderCost == null)
			{
				final I_C_UOM uom = uomDAO.getById(fromElementCostPrice.getUomId());

				elementOrderCost = PPOrderCost.builder()
						.trxType(PPOrderCostTrxType.MainProduct)
						.costSegmentAndElement(costSegmentAndElement)
						.price(fromElementCostPrice.getCostPrice())
						.accumulatedQty(Quantity.zero(uom))
						.build();
			}
			else
			{
				// shall not happen
				throw new AdempiereException("Finished good cost price defined more than once for " + fromElementCostPrice.getCostElementId())
						.setParameter("costSegmentAndElement", costSegmentAndElement)
						.setParameter("elementCostPrice", fromElementCostPrice)
						.appendParametersToMessage();
			}

			newOrderCosts.put(elementOrderCost.getCostSegmentAndElement(), elementOrderCost);
		}

		//
		// BOM Line Product Cost
		for (final BOMLine fromBOMLine : fromBOM.getLines())
		{
			final PPOrderCostTrxType trxType = PPOrderCostTrxType.ofBOMComponentType(fromBOMLine.getComponentType());
			final Percent coProductCostDistributionPercent = trxType.isCoProduct()
					? fromBOMLine.getCoProductCostDistributionPercent()
					: null;

			for (final BOMCostElementPrice fromElementCostPrice : fromBOMLine.getElementPrices())
			{
				final CostSegmentAndElement costSegmentAndElement = createCostSegmentAndElement(fromBOMLine.getComponentId(), fromBOMLine.getAsiId(), fromElementCostPrice.getCostElementId());

				PPOrderCost elementOrderCost = newOrderCosts.get(costSegmentAndElement);
				if (elementOrderCost == null)
				{
					final I_C_UOM uom = uomDAO.getById(fromElementCostPrice.getUomId());

					elementOrderCost = PPOrderCost.builder()
							.trxType(trxType)
							.costSegmentAndElement(costSegmentAndElement)
							.price(fromElementCostPrice.getCostPrice())
							.coProductCostDistributionPercent(coProductCostDistributionPercent)
							.accumulatedQty(Quantity.zero(uom))
							.build();
				}
				else
				{
					// Case: we have the same component product defined multiple times in our BOM.
					// The order cost was created for the first BOM line found.
					// We assume the next BOM lines (of same component product) have EXACTLY the same cost price (maybe converted to a different UOM),
					// so it's pointless to touch the current order cost.
					logger.debug("Skip updating {} from {}. We assume first time was created correctly. Check the code for more inside info.", elementOrderCost, fromElementCostPrice);
				}

				newOrderCosts.put(elementOrderCost.getCostSegmentAndElement(), elementOrderCost);
			}
		}

		//
		currentOrderCosts.removeByProductsAndCostElements(fromBOM.getProductIds(), costElementIds);
		currentOrderCosts.addCosts(newOrderCosts.values());
	}

	private CostSegmentAndElement createCostSegmentAndElement(
			final ProductId productId,
			final AttributeSetInstanceId asiId,
			final CostElementId costElementId)
	{
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchema);

		return CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.costElementId(costElementId)
				.build();
	}

	@Override
	public void resetComponentsCostPrices(final ProductId productId)
	{
		throw new UnsupportedOperationException();
	}

}
