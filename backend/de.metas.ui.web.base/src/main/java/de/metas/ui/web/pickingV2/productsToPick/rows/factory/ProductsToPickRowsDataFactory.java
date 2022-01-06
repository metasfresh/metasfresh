/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.plan.generator.CreatePickingPlanRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.model.IssueToBOMLine;
import de.metas.handlingunits.picking.plan.model.PickFromPickingOrder;
import de.metas.handlingunits.picking.plan.model.PickingPlan;
import de.metas.handlingunits.picking.plan.model.PickingPlanLine;
import de.metas.handlingunits.picking.plan.model.PickingPlanLineType;
import de.metas.handlingunits.picking.plan.model.SourceDocumentInfo;
import de.metas.product.IProductDAO;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductInfo;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowId;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowType;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsData;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Objects;

public class ProductsToPickRowsDataFactory
{
	private final PickingCandidateService pickingCandidateService;
	private final ProductInfoSupplier productInfos;
	private final LookupValueByIdSupplier locatorLookup;

	private final boolean considerAttributes;

	@Builder
	private ProductsToPickRowsDataFactory(
			@NonNull final PickingCandidateService pickingCandidateService,
			@NonNull final LookupValueByIdSupplier locatorLookup,
			final boolean considerAttributes)
	{
		this.pickingCandidateService = pickingCandidateService;
		this.locatorLookup = locatorLookup;
		productInfos = createProductInfoSupplier();

		this.considerAttributes = considerAttributes;
	}

	private static ProductInfoSupplier createProductInfoSupplier()
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		return ProductInfoSupplier.builder()
				.productsRepo(productsRepo)
				.uomsRepo(uomsRepo)
				.build();
	}

	public ProductsToPickRowsData create(final PackageableRow packageableRow)
	{
		final PickingPlan plan = pickingCandidateService.createPlan(CreatePickingPlanRequest.builder()
				.packageables(packageableRow.getPackageables())
				.considerAttributes(considerAttributes)
				.build());
		final ImmutableList<ProductsToPickRow> rows = plan.getLines()
				.stream()
				.map(this::toRow)
				.collect(ImmutableList.toImmutableList());

		return ProductsToPickRowsData.builder()
				.pickingCandidateService(pickingCandidateService)
				.rows(rows)
				.orderBy(DocumentQueryOrderBy.byFieldName(ProductsToPickRow.FIELD_Locator))
				.build();
	}

	private ProductsToPickRow toRow(final PickingPlanLine planLine)
	{
		final PickingPlanLineType planLineType = planLine.getType();
		switch (planLineType)
		{
			case PICK_FROM_HU:
				return toRow_PickFromHU(planLine);
			case PICK_FROM_PICKING_ORDER:
				return toRow_PickFromPickingOrder(planLine);
			case ISSUE_COMPONENTS_TO_PICKING_ORDER:
				return toRow_IssueComponentsToPickingOrder(planLine);
			case UNALLOCABLE:
				return toRow_Unallocable(planLine);
			default:
				throw new AdempiereException("Unknown plan line type: " + planLineType);
		}
	}

	private ProductsToPickRow toRow_PickFromHU(final PickingPlanLine planLine)
	{
		final SourceDocumentInfo sourceDocumentInfo = planLine.getSourceDocumentInfo();
		final ProductInfo productInfo = productInfos.getByProductId(planLine.getProductId());
		final PickFromHU pickFromHU = Objects.requireNonNull(planLine.getPickFromHU());

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(sourceDocumentInfo.getShipmentScheduleId())
				.pickFromHUId(pickFromHU.getTopLevelHUId())
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.rowType(ProductsToPickRowType.PICK_FROM_HU)
				.shipperId(sourceDocumentInfo.getShipperId())
				//
				.productInfo(productInfo)
				.qty(planLine.getQty())
				//
				// HU related info:
				.huReservedForThisRow(pickFromHU.isHuReservedForThisLine())
				.locator(locatorLookup.findById(pickFromHU.getLocatorId()))
				//
				// HU Attributes:
				.huCode(pickFromHU.getHuCode())
				.serialNo(pickFromHU.getSerialNo())
				.lotNumber(pickFromHU.getLotNumber())
				.expiringDate(pickFromHU.getExpiringDate())
				.repackNumber(pickFromHU.getRepackNumber())
				//
				.build()
				.withUpdatesFromPickingCandidateIfNotNull(sourceDocumentInfo.getExistingPickingCandidate());
	}

	private ProductsToPickRow toRow_PickFromPickingOrder(final PickingPlanLine planLine)
	{
		final SourceDocumentInfo sourceDocumentInfo = planLine.getSourceDocumentInfo();
		final ProductInfo productInfo = productInfos.getByProductId(planLine.getProductId());
		final PickFromPickingOrder pickFromPickingOrder = Objects.requireNonNull(planLine.getPickFromPickingOrder());

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(sourceDocumentInfo.getShipmentScheduleId())
				.pickFromPickingOrderId(pickFromPickingOrder.getOrderId())
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.rowType(ProductsToPickRowType.PICK_FROM_PICKING_ORDER)
				.shipperId(sourceDocumentInfo.getShipperId())
				//
				.productInfo(productInfo)
				.qty(planLine.getQty())
				//
				.includedRows(pickFromPickingOrder.getIssueToBOMLines()
						.stream()
						.map(this::toRow)
						.collect(ImmutableList.toImmutableList()))
				//
				.build()
				.withUpdatesFromPickingCandidateIfNotNull(sourceDocumentInfo.getExistingPickingCandidate());
	}

	private ProductsToPickRow toRow_IssueComponentsToPickingOrder(final PickingPlanLine planLine)
	{
		final SourceDocumentInfo sourceDocumentInfo = planLine.getSourceDocumentInfo();
		final ProductInfo productInfo = productInfos.getByProductId(planLine.getProductId());
		final IssueToBOMLine issueToBOMLine = Objects.requireNonNull(planLine.getIssueToBOMLine());

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(sourceDocumentInfo.getShipmentScheduleId())
				.issueToOrderBOMLineId(issueToBOMLine.getIssueToOrderBOMLineId())
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.rowType(ProductsToPickRowType.ISSUE_COMPONENTS_TO_PICKING_ORDER)
				.shipperId(sourceDocumentInfo.getShipperId())
				//
				.productInfo(productInfo)
				.qty(planLine.getQty())
				//
				.build()
				.withUpdatesFromPickingCandidateIfNotNull(sourceDocumentInfo.getExistingPickingCandidate());
	}

	private ProductsToPickRow toRow_Unallocable(@NonNull final PickingPlanLine planLine)
	{
		final SourceDocumentInfo sourceDocumentInfo = planLine.getSourceDocumentInfo();
		final ProductInfo productInfo = productInfos.getByProductId(planLine.getProductId());

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(sourceDocumentInfo.getShipmentScheduleId())
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.rowType(ProductsToPickRowType.UNALLOCABLE)
				.shipperId(sourceDocumentInfo.getShipperId())
				//
				.productInfo(productInfo)
				.qty(planLine.getQty())
				//
				.build()
				.withUpdatesFromPickingCandidateIfNotNull(sourceDocumentInfo.getExistingPickingCandidate());

	}
}
