package de.metas.costing.methods;

import org.springframework.stereotype.Service;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegment;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
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

@Service
public class CostingMethodHandlerUtils
{
	private final IAcctSchemaDAO acctSchemaRepo = Services.get(IAcctSchemaDAO.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final ICostDetailRepository costDetailsRepo;
	private final ICurrentCostsRepository currentCostsRepo;

	public CostingMethodHandlerUtils(
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailRepository costDetailsRepo)
	{
		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsRepo = costDetailsRepo;
	}

	public CostSegment extractCostSegment(final CostDetail costDetail)
	{
		final AcctSchema acctSchema = acctSchemaRepo.getById(costDetail.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(costDetail.getProductId(), acctSchema);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(costDetail.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(costDetail.getProductId())
				.clientId(costDetail.getClientId())
				.orgId(costDetail.getOrgId())
				.attributeSetInstanceId(costDetail.getAttributeSetInstanceId())
				.build();
	}

	public CostSegment extractCostSegment(final CostDetailCreateRequest request)
	{
		final AcctSchema acctSchema = acctSchemaRepo.getById(request.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(request.getProductId(), acctSchema);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(request.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(request.getProductId())
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.build();
	}

	public CostDetailCreateResult createCostDetailRecordNoCostsChanged(@NonNull final CostDetailCreateRequest request)
	{
		final CostDetail costDetail = costDetailsRepo.create(request.toCostDetailBuilder()
				.changingCosts(false));

		return createCostDetailCreateResult(costDetail, request);
	}

	public CostDetailCreateResult createCostDetailCreateResult(final CostDetail costDetail, final CostDetailCreateRequest request)
	{
		return CostDetailCreateResult.builder()
				.costSegment(extractCostSegment(costDetail))
				.costElement(request.getCostElement())
				.amt(costDetail.getAmt())
				.qty(costDetail.getQty())
				.price(costDetail.getPrice())
				.build();
	}

	protected final CostDetail getExistingCostDetailOrNull(final CostDetailCreateRequest request)
	{
		final CostDetailQuery costDetailQuery = extractCostDetailQuery(request);
		return costDetailsRepo.getCostDetailOrNull(costDetailQuery);
	}

	private static CostDetailQuery extractCostDetailQuery(final CostDetailCreateRequest request)
	{
		final CostElementId costElementId = request.isAllCostElements() ? null : request.getCostElementId();

		return CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(costElementId)
				.documentRef(request.getDocumentRef())
				.build();
	}

	public final CostDetailCreateResult createCostDetailRecordWithChangedCosts(@NonNull final CostDetailCreateRequest request, @NonNull final CurrentCost previousCosts)
	{
		final CostDetail costDetail = costDetailsRepo.create(request.toCostDetailBuilder()
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.of(previousCosts)));

		return createCostDetailCreateResult(costDetail, request);
	}

	public final CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final CostSegment costSegment = extractCostSegment(request);
		return getCurrentCost(costSegment, request.getCostElementId());
	}

	public final CurrentCost getCurrentCost(final CostSegment costSegment, final CostElementId costElementId)
	{
		return currentCostsRepo.getOrCreate(costSegment, costElementId);
	}

	public final CostAmount getCurrentCostPrice(final CostDetailCreateRequest request)
	{
		return getCurrentCost(request).getCurrentCostPrice();
	}

	public final void saveCurrentCosts(final CurrentCost currentCost)
	{
		currentCostsRepo.save(currentCost);
	}


}
