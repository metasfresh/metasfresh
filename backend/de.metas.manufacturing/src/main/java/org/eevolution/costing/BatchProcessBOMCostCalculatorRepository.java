package org.eevolution.costing;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.CurrentCost;
import de.metas.costing.CurrentCostId;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
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

public class BatchProcessBOMCostCalculatorRepository implements BOMCostCalculatorRepository
{
	// services
	private final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	//
	private final ICurrentCostsRepository currentCostsRepo = Adempiere.getBean(ICurrentCostsRepository.class);

	// parameters
	private final ClientId clientId;
	private final OrgId orgId;
	private final AcctSchema acctSchema;
	private final CostTypeId costTypeId;
	private final CostingMethod costingMethod;

	@Builder
	private BatchProcessBOMCostCalculatorRepository(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchema acctSchema,
			@Nullable final CostTypeId costTypeId,
			@NonNull final CostingMethod costingMethod)
	{
		this.clientId = clientId;
		this.orgId = orgId;
		this.acctSchema = acctSchema;
		this.costTypeId = costTypeId != null ? costTypeId : acctSchema.getCosting().getCostTypeId();
		this.costingMethod = costingMethod;
	}

	@Override
	public Optional<BOM> getBOM(final ProductId productId)
	{
		final ProductPlanning productPlanning = Services.get(IProductPlanningDAO.class)
				.find(ProductPlanningQuery.builder()
							  .orgId(orgId)
							  .productId(productId)
							  .includeWithNullProductId(false)
							  .build())
				.orElse(null);

		ProductBOMVersionsId bomVersionsId = null;
		if (productPlanning != null)
		{
			bomVersionsId = productPlanning.getBomVersionsId();
		}
		else
		{
			createNotice(productId, "@NotFound@ @PP_Product_Planning_ID@");
		}

		final ProductBOMId productBOMId;
		if (bomVersionsId != null)
		{
			productBOMId = productBOMsRepo.getLatestBOMIdByVersionAndType(bomVersionsId, PPOrderDocBaseType.MANUFACTURING_ORDER.getBOMTypes()).orElse(null);
		}
		else
		{
			productBOMId = productBOMsRepo.getDefaultBOMIdByProductId(productId).orElse(null);
		}

		if (productBOMId == null)
		{
			createNotice(productId, "@NotFound@ @PP_Product_BOM_ID@");
			return Optional.empty();
		}

		final I_PP_Product_BOM bomRecord = productBOMsRepo.getById(productBOMId);
		if (bomRecord == null)
		{
			createNotice(productId, "@NotFound@ @PP_Product_BOM_ID@");
			return Optional.empty();
		}
		else
		{
			return Optional.of(toCostingBOM(bomRecord));
		}

	}

	private BOM toCostingBOM(final I_PP_Product_BOM bomRecord)
	{
		final ImmutableList<BOMLine> bomLines = productBOMsRepo.retrieveLines(bomRecord)
				.stream()
				.map(this::toCostingBOMLine)
				.collect(ImmutableList.toImmutableList());

		final ProductId productId = ProductId.ofRepoId(bomRecord.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(bomRecord.getC_UOM_ID());
		final I_C_UOM uom = uomDAO.getById(uomId);

		return BOM.builder()
				.productId(productId)
				.qty(Quantity.of(1, uom))
				.lines(bomLines)
				.costPrice(getBOMCostPrice(productId, uomId))
				.build();
	}

	private BOMCostPrice getBOMCostPrice(
			final ProductId productId,
			final UomId uomId)
	{
		final CostSegment costSegment = createCostSegment(productId);

		final List<BOMCostElementPrice> costElementPrices = currentCostsRepo.getByCostSegmentAndCostingMethod(costSegment, costingMethod)
				.stream()
				.map(BatchProcessBOMCostCalculatorRepository::toBOMCostElementPrice)
				.collect(ImmutableList.toImmutableList());

		return BOMCostPrice.builder()
				.productId(productId)
				.uomId(uomId)
				.costElementPrices(costElementPrices)
				.build();
	}

	private CostSegment createCostSegment(final ProductId productId)
	{
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(productId, acctSchema);

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchema.getId())
				.costTypeId(costTypeId)
				.productId(productId)
				.clientId(clientId)
				.orgId(orgId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
	}

	private BOMLine toCostingBOMLine(final I_PP_Product_BOMLine bomLineRecord)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(bomLineRecord.getComponentType());
		final ProductId productId = ProductId.ofRepoId(bomLineRecord.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(bomLineRecord.getC_UOM_ID());
		final Percent coProductCostDistributionPercent = componentType.isCoProduct()
				? productBOMBL.getCoProductCostDistributionPercent(bomLineRecord)
				: null;

		return BOMLine.builder()
				.componentType(componentType)
				.componentId(productId)
				.qty(productBOMBL.getQtyExcludingScrap(bomLineRecord))
				.scrapPercent(Percent.of(bomLineRecord.getScrap()))
				.costPrice(getBOMCostPrice(productId, uomId))
				.coProductCostDistributionPercent(coProductCostDistributionPercent)
				.build();
	}

	private void createNotice(
			final ProductId productId,
			final String msg)
	{
		final ILoggable loggable = Loggables.get();
		if (Loggables.isNull(loggable))
		{
			return;
		}

		final String productValue = productId != null
				? productBL.getProductValueAndName(productId)
				: "-";

		loggable.addLog("WARNING: Product {}: {}", productValue, msg);
	}

	@Override
	public void save(final BOM bom)
	{
		final Set<CurrentCostId> costIds = bom.getCostIds(CurrentCostId.class);

		final Map<CurrentCostId, CurrentCost> existingCostsById = currentCostsRepo.getByIds(costIds)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(CurrentCost::getId));

		bom.streamCostPrices()
				.forEach(bomCostPrice -> save(bomCostPrice, existingCostsById));
	}

	private void save(
			@NonNull final BOMCostPrice bomCostPrice,
			final Map<CurrentCostId, CurrentCost> existingCostsByRepoId)
	{
		final ProductId productId = bomCostPrice.getProductId();

		for (final BOMCostElementPrice elementPrice : bomCostPrice.getElementPrices())
		{
			final CurrentCostId currentCostId = elementPrice.getId(CurrentCostId.class);

			CurrentCost existingCost = existingCostsByRepoId.get(currentCostId);
			if (existingCost == null)
			{
				final CostSegmentAndElement costSegmentAndElement = createCostSegment(productId)
						.withCostElementId(elementPrice.getCostElementId());
				existingCost = currentCostsRepo.create(costSegmentAndElement);
			}

			existingCost.setCostPrice(elementPrice.getCostPrice());
			currentCostsRepo.save(existingCost);
			elementPrice.setId(existingCost.getId());
		}
	}

	private static BOMCostElementPrice toBOMCostElementPrice(@NonNull final CurrentCost currentCost)
	{
		return BOMCostElementPrice.builder()
				.id(currentCost.getId())
				.costElementId(currentCost.getCostElementId())
				.costPrice(currentCost.getCostPrice())
				.build();
	}

	@Override
	public void resetComponentsCostPrices(final ProductId productId)
	{
		final CostSegment costSegment = createCostSegment(productId);
		for (final CurrentCost cost : currentCostsRepo.getByCostSegmentAndCostingMethod(costSegment, costingMethod))
		{
			cost.clearComponentsCostPrice();
			currentCostsRepo.save(cost);
		}
	}

}
