package de.metas.costing.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetail.CostDetailBuilder;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResult;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostSegmentAndElement.CostSegmentAndElementBuilder;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.CostingLevel;
import de.metas.costing.ICostDetailRepository;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.MoveCostsRequest;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class CostDetailService implements ICostDetailService
{
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IAcctSchemaDAO acctSchemaRepo = Services.get(IAcctSchemaDAO.class);
	private final ICostDetailRepository costDetailsRepo;
	private final ICostElementRepository costElementRepo;

	public CostDetailService(
			@NonNull final ICostDetailRepository costDetailsRepo,
			@NonNull final ICostElementRepository costElementRepo)
	{
		this.costDetailsRepo = costDetailsRepo;
		this.costElementRepo = costElementRepo;
	}

	private AcctSchema getAcctSchemaById(final AcctSchemaId acctSchemaId)
	{
		return acctSchemaRepo.getById(acctSchemaId);
	}

	@Override
	public CostDetailCreateResult createCostDetailRecordWithChangedCosts(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CostDetailPreviousAmounts previousCosts)
	{
		final CostDetail costDetail = create(request.toCostDetailBuilder()
				.changingCosts(true)
				.previousAmounts(previousCosts));

		return toCostDetailCreateResult(costDetail);
	}

	@Override
	public CostDetailCreateResult createCostDetailRecordNoCostsChanged(
			@NonNull final CostDetailCreateRequest request,
			@NonNull final CostDetailPreviousAmounts currentCosts)
	{
		final CostDetail costDetail = create(request.toCostDetailBuilder()
				.changingCosts(false)
				.previousAmounts(currentCosts));

		return toCostDetailCreateResult(costDetail);
	}

	@Override
	public CostDetail create(@NonNull final CostDetailBuilder costDetailBuilder)
	{
		return costDetailsRepo.create(costDetailBuilder);
	}

	@Override
	public boolean hasCostDetailsForProductId(@NonNull final ProductId productId)
	{
		return costDetailsRepo.hasCostDetailsForProductId(productId);
	}

	@Override
	public final Optional<CostDetail> getExistingCostDetail(final CostDetailCreateRequest request)
	{
		return costDetailsRepo.getCostDetail(CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.costElementId(request.getCostElementId()) // assume request's costing element is set
				.documentRef(request.getDocumentRef())
				// .productId(request.getProductId())
				// .attributeSetInstanceId(request.getAttributeSetInstanceId())
				.build());
	}

	@Override
	public Stream<CostDetail> streamAllCostDetailsAfter(final CostDetail costDetail)
	{
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(costDetail.getProductId(), costDetail.getAcctSchemaId());
		return costDetailsRepo.streamOrderedById(CostDetailQuery.builder()
				.acctSchemaId(costDetail.getAcctSchemaId())
				.costElementId(costDetail.getCostElementId())
				.productId(costDetail.getProductId())
				.attributeSetInstanceId(costingLevel.effectiveValueOrNull(costDetail.getAttributeSetInstanceId()))
				.clientId(costingLevel.effectiveValue(costDetail.getClientId()))
				.orgId(costingLevel.effectiveValueOrNull(costDetail.getOrgId()))
				.afterCostDetailId(costDetail.getId())
				.build());
	}

	@Override
	public List<CostDetail> getAllForDocument(final CostingDocumentRef documentRef)
	{
		return costDetailsRepo.getAllForDocument(documentRef);
	}

	@Override
	public List<CostDetail> getAllForDocumentAndAcctSchemaId(final CostingDocumentRef documentRef, final AcctSchemaId acctSchemaId)
	{
		return costDetailsRepo.getAllForDocumentAndAcctSchemaId(documentRef, acctSchemaId);
	}

	@Override
	public CostDetailCreateResult toCostDetailCreateResult(final CostDetail costDetail)
	{
		return CostDetailCreateResult.builder()
				.costSegment(extractCostSegment(costDetail))
				.costElement(costElementRepo.getById(costDetail.getCostElementId()))
				.amt(costDetail.getAmt())
				.qty(costDetail.getQty())
				.build();
	}

	private CostSegment extractCostSegment(final CostDetail costDetail)
	{
		final AcctSchema acctSchema = getAcctSchemaById(costDetail.getAcctSchemaId());
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

	@Override
	public CostSegmentAndElement extractCostSegmentAndElement(final CostDetail costDetail)
	{
		return extractCostSegment(costDetail)
				.withCostElementId(costDetail.getCostElementId());
	}

	@Override
	public CostSegmentAndElement extractCostSegmentAndElement(final CostDetailCreateRequest request)
	{
		final AcctSchema acctSchema = getAcctSchemaById(request.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(request.getProductId(), acctSchema);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		return CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(request.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(request.getProductId())
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(request.getCostElementId())
				.build();
	}

	@Override
	public CostSegmentAndElement extractOutboundCostSegmentAndElement(final MoveCostsRequest request)
	{
		return extractCommonCostSegmentAndElement(request)
				.orgId(request.getOutboundOrgId())
				.build();
	}

	@Override
	public CostSegmentAndElement extractInboundCostSegmentAndElement(final MoveCostsRequest request)
	{
		return extractCommonCostSegmentAndElement(request)
				.orgId(request.getInboundOrgId())
				.build();
	}

	private CostSegmentAndElementBuilder extractCommonCostSegmentAndElement(final MoveCostsRequest request)
	{
		final AcctSchema acctSchema = getAcctSchemaById(request.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(request.getProductId(), acctSchema);
		final CostTypeId costTypeId = acctSchema.getCosting().getCostTypeId();

		return CostSegmentAndElement.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(request.getAcctSchemaId())
				.costTypeId(costTypeId)
				.clientId(request.getClientId())
				// .orgId(null) // to be set by caller
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(request.getCostElementId());
	}

	@Override
	public void delete(final CostDetail costDetail)
	{
		costDetailsRepo.delete(costDetail);
	}
}
