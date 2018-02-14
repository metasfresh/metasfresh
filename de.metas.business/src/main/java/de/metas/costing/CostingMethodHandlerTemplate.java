package de.metas.costing;

import java.math.BigDecimal;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.MAcctSchema;

import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
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
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		return null;
	}

	@Override
	public final CostDetailCreateResult createOrUpdateCost(final CostDetailCreateRequest request)
	{
		//
		// Check if existing cost detail
		final I_M_CostDetail costDetail = getExistingCostDetailOrNull(request);
		if (costDetail != null)
		{
			return createCostDetailCreateResult(costDetail, request);
		}

		return createCost(request);
	}

	protected CostDetailCreateResult createCost(final CostDetailCreateRequest request)
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
		else if (documentRef.isTableName(CostingDocumentRef.TABLE_NAME_M_ProductionLine))
		{
			return createCostForProductionLine(request);
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

	protected I_M_CostDetail getExistingCostDetailOrNull(final CostDetailCreateRequest request)
	{
		final CostDetailQuery costDetailQuery = extractCostDetailQuery(request);
		return costDetailsRepo.getCostDetailOrNull(costDetailQuery);
	}

	private static CostDetailQuery extractCostDetailQuery(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();

		return CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(costElement != null ? costElement.getId() : -1)
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

	protected CostDetailCreateResult createCostForProductionLine(final CostDetailCreateRequest request)
	{
		throw new UnsupportedOperationException();
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
		final I_M_CostDetail costDetail = prepareCostDetail(request);
		costDetail.setIsChangingCosts(false);
		costDetailsRepo.save(costDetail);

		return createCostDetailCreateResult(costDetail, request);
	}

	protected final CostDetailCreateResult createCostDetailRecordWithChangedCosts(@NonNull final CostDetailCreateRequest request, @NonNull final CurrentCost previousCosts)
	{
		final I_M_CostDetail costDetail = prepareCostDetail(request);
		costDetail.setIsChangingCosts(true);
		costDetail.setPrev_CurrentCostPrice(previousCosts.getCurrentCostPrice().getValue());
		costDetail.setPrev_CurrentCostPriceLL(previousCosts.getCurrentCostPriceLL().getValue());
		costDetail.setPrev_CurrentQty(previousCosts.getCurrentQty().getQty());
		costDetailsRepo.save(costDetail);

		return createCostDetailCreateResult(costDetail, request);
	}

	private final I_M_CostDetail prepareCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		final I_M_CostDetail costDetail = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class);
		final int costDetailClientId = costDetail.getAD_Client_ID();
		Check.assume(costDetailClientId == request.getClientId(), "same AD_Client_ID: {} vs {}", costDetailClientId, request.getClientId());
		costDetail.setAD_Org_ID(request.getOrgId());
		costDetail.setC_AcctSchema_ID(request.getAcctSchemaId());
		costDetail.setM_Product_ID(request.getProductId());
		costDetail.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId());

		final CostElement costElement = request.getCostElement();
		if (costElement != null)
		{
			costDetail.setM_CostElement_ID(costElement.getId());
		}

		costDetail.setAmt(request.getAmt().getValue());
		costDetail.setQty(request.getQty().getQty());

		costDetail.setDescription(request.getDescription());

		final CostingDocumentRef documentRef = request.getDocumentRef();
		InterfaceWrapperHelper.setValue(costDetail, documentRef.getCostDetailColumnName(), documentRef.getRecordId());

		if (documentRef.getOutboundTrx() != null)
		{
			costDetail.setIsSOTrx(documentRef.getOutboundTrx());
		}

		costDetail.setProcessed(true); // TODO: get rid of Processed flag, or always set it!

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

	private CostDetailCreateResult createCostDetailCreateResult(final I_M_CostDetail costDetail, final CostDetailCreateRequest request)
	{
		final MAcctSchema as = LegacyAdapters.convertToPO(acctSchemaRepo.retrieveAcctSchemaById(request.getAcctSchemaId()));
		final I_C_UOM uom = productBL.getStockingUOM(costDetail.getM_Product_ID());
		
		return CostDetailCreateResult.builder()
				.costSegment(extractCostSegment(request))
				.costElement(request.getCostElement())
				.amt(CostAmount.of(costDetail.getAmt(), as.getC_Currency_ID()))
				.qty(Quantity.of(costDetail.getQty(), uom))
				.costingPrecision(as.getCostingPrecision())
				.build();
	}

	private CostSegment extractCostSegment(final CostDetailCreateRequest request)
	{
		final I_C_AcctSchema as = acctSchemaRepo.retrieveAcctSchemaById(request.getAcctSchemaId());
		final IProductBL productBL = Services.get(IProductBL.class);
		final CostingLevel costingLevel = productBL.getCostingLevel(request.getProductId(), as);
		final int costTypeId = as.getM_CostType_ID();

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
