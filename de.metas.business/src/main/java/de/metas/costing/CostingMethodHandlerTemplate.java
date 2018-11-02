package de.metas.costing;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostDetail.CostDetailBuilder;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
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

public abstract class CostingMethodHandlerTemplate implements CostingMethodHandler
{
	private final IAcctSchemaDAO acctSchemaRepo = Services.get(IAcctSchemaDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ICurrentCostsRepository currentCostsRepo;
	private final ICostDetailRepository costDetailsRepo;

	protected CostingMethodHandlerTemplate(
			@NonNull final ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailRepository costDetailsRepo)
	{
		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsRepo = costDetailsRepo;
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final OrderLineId orderLineId)
	{
		return null;
	}

	@Override
	public final CostDetailCreateResult createOrUpdateCost(final CostDetailCreateRequest request)
	{
		//
		// Check if existing cost detail
		final CostDetail costDetail = getExistingCostDetailOrNull(request);
		if (costDetail != null)
		{
			return createCostDetailCreateResult(costDetail, request);
		}

		return createCost(request);
	}

	protected final CostDetailCreateResult createCost(final CostDetailCreateRequest request)
	{
		//
		// Create new cost detail
		final CostingDocumentRef documentRef = request.getDocumentRef();
		if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MatchPO))
		{
			return createCostForMatchPO(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MatchInv))
		{
			return createCostForMatchInvoice(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_InOutLine))
		{
			final Boolean outboundTrx = documentRef.getOutboundTrx();
			if (outboundTrx)
			{
				return createCostForMaterialShipment(request);
			}
			else
			{
				return createCostForMaterialReceipt(request);
			}
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_MovementLine))
		{
			return createCostForMovementLine(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_InventoryLine))
		{
			return createCostForInventoryLine(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_C_ProjectIssue))
		{
			return createCostForProjectIssue(request);
		}
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector))
		{
			return createCostForCostCollector(request);
		}
		else
		{
			throw new AdempiereException("Unknown documentRef: " + documentRef);
		}
	}

	protected final CostDetail getExistingCostDetailOrNull(final CostDetailCreateRequest request)
	{
		final CostDetailQuery costDetailQuery = extractCostDetailQuery(request);
		return costDetailsRepo.getCostDetailOrNull(costDetailQuery);
	}

	private static CostDetailQuery extractCostDetailQuery(final CostDetailCreateRequest request)
	{
		final CostElementId costElementId = request.isAllCostElements() ? null : request.getCostElement().getId();

		return CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(costElementId)
				.documentRef(request.getDocumentRef())
				.build();
	}

	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialShipment(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForMovementLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForInventoryLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForProjectIssue(final CostDetailCreateRequest request)
	{
		throw new UnsupportedOperationException();
	}

	protected CostDetailCreateResult createCostForCostCollector(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected abstract CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request);

	protected final CostDetailCreateResult createCostDetailRecordNoCostsChanged(@NonNull final CostDetailCreateRequest request)
	{
		final CostDetail costDetail = costDetailsRepo.create(prepareCostDetail(request)
				.changingCosts(false));

		return createCostDetailCreateResult(costDetail, request);
	}

	protected final CostDetailCreateResult createCostDetailRecordWithChangedCosts(@NonNull final CostDetailCreateRequest request, @NonNull final CurrentCost previousCosts)
	{
		final CostDetail costDetail = costDetailsRepo.create(prepareCostDetail(request)
				.changingCosts(true)
				.previousAmounts(CostDetailPreviousAmounts.of(previousCosts)));

		return createCostDetailCreateResult(costDetail, request);
	}

	private final CostDetailBuilder prepareCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		final CostDetailBuilder costDetail = CostDetail.builder()
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.acctSchemaId(request.getAcctSchemaId())
				.productId(request.getProductId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				//
				.amt(request.getAmt())
				.qty(request.getQty())
				.price(null) // TODO price
				//
				.documentRef(request.getDocumentRef())
				.description(request.getDescription());

		if (!request.isAllCostElements())
		{
			costDetail.costElementId(request.getCostElement().getId());
		}

		return costDetail;
	}

	protected final CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final CostSegment costSegment = extractCostSegment(request);
		return getCurrentCost(costSegment, request.getCostElement());
	}

	protected final CurrentCost getCurrentCost(final CostSegment costSegment, final CostElement costElement)
	{
		return currentCostsRepo.getOrCreate(costSegment, costElement.getId());
	}

	protected final CostAmount getCurrentCostPrice(final CostDetailCreateRequest request)
	{
		return getCurrentCost(request).getCurrentCostPrice();
	}

	private CostDetailCreateResult createCostDetailCreateResult(final CostDetail costDetail, final CostDetailCreateRequest request)
	{
		return CostDetailCreateResult.builder()
				.costSegment(extractCostSegment(costDetail))
				.costElement(request.getCostElement())
				.amt(costDetail.getAmt())
				.qty(costDetail.getQty())
				.price(costDetail.getPrice())
				.build();
	}

	private CostSegment extractCostSegment(final CostDetail costDetail)
	{
		final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);

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

	private CostSegment extractCostSegment(final CostDetailCreateRequest request)
	{
		final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);

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

	protected final void saveCurrentCosts(final CurrentCost currentCost)
	{
		currentCostsRepo.save(currentCost);
	}

	@Override
	public void voidCosts(final CostDetailVoidRequest request)
	{
		throw new UnsupportedOperationException();
	}
}
