package de.metas.ui.web.pickingV2.productsToPick.rows;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.picking.requests.PickRequest.IssueToPickingOrderRequest;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.pickingV2.config.PickingConfigRepositoryV2;
import de.metas.ui.web.pickingV2.config.PickingConfigV2;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.factory.ProductsToPickRowsDataFactory;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Locator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-webui-api
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
public class ProductsToPickRowsService
{
	private final PickingConfigRepositoryV2 pickingConfigRepo;
	private final IBPartnerBL bpartnersService;
	private final HUReservationService huReservationService;
	private final PickingCandidateService pickingCandidateService;

	private static final AdMessageKey MSG_TYPE_UNALLOCATED = AdMessageKey.of("de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService.UnAllocated_Type_Error");
	private static final AdMessageKey MSG_TYPE_NOT_SUPPORTED = AdMessageKey.of("de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService.TypeRow_NotSupported");

	public ProductsToPickRowsService(
			@NonNull final PickingConfigRepositoryV2 pickingConfigRepo,
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this.pickingConfigRepo = pickingConfigRepo;
		this.bpartnersService = bpartnersService;
		this.huReservationService = huReservationService;
		this.pickingCandidateService = pickingCandidateService;
	}

	public ProductsToPickRowsData createProductsToPickRowsData(final PackageableRow packageableRow)
	{
		return newProductsToPickRowsFactory()
				.create(packageableRow);
	}

	private ProductsToPickRowsDataFactory newProductsToPickRowsFactory()
	{
		final PickingConfigV2 pickingConfig = pickingConfigRepo.getPickingConfig();

		return ProductsToPickRowsDataFactory.builder()
				.bpartnersService(bpartnersService)
				.huReservationService(huReservationService)
				.pickingCandidateService(pickingCandidateService)
				.locatorLookup(LookupDataSourceFactory.instance.searchInTableLookup(I_M_Locator.Table_Name))
				//
				.considerAttributes(pickingConfig.isConsiderAttributes())
				//
				.build();
	}

	public PickRequest createPickRequest(@NonNull final ProductsToPickRow row, boolean isPickingReviewRequired)
	{
		final ProductsToPickRowType rowType = row.getType();
		if (ProductsToPickRowType.PICK_FROM_HU.equals(rowType))
		{
			return PickRequest.builder()
					.shipmentScheduleId(row.getShipmentScheduleId())
					.qtyToPick(row.getQtyEffective())
					.pickFrom(PickFrom.ofHuId(row.getPickFromHUId()))
					.autoReview(!isPickingReviewRequired)
					.build();
		}
		else if (ProductsToPickRowType.PICK_FROM_PICKING_ORDER.equals(rowType))
		{
			final ImmutableList<IssueToPickingOrderRequest> issues = row.getIncludedRows()
					.stream()
					.map(issueRow -> toIssueToPickingOrderRequest(issueRow))
					.collect(ImmutableList.toImmutableList());

			return PickRequest.builder()
					.shipmentScheduleId(row.getShipmentScheduleId())
					.qtyToPick(row.getQtyEffective())
					.pickFrom(PickFrom.ofPickingOrderId(row.getPickFromPickingOrderId()))
					.issuesToPickingOrder(issues)
					.autoReview(!isPickingReviewRequired)
					.build();
		}
		else if (ProductsToPickRowType.UNALLOCABLE.equals(rowType))
		{
			final ITranslatableString message = Services.get(IMsgBL.class)
					.getTranslatableMsgText(MSG_TYPE_UNALLOCATED);
			throw new AdempiereException(message);
		}
		else
		{
			final ITranslatableString message = Services.get(IMsgBL.class)
					.getTranslatableMsgText(MSG_TYPE_NOT_SUPPORTED);
			throw new AdempiereException(message);
		}
	}

	private static PickRequest.IssueToPickingOrderRequest toIssueToPickingOrderRequest(final ProductsToPickRow issueRow)
	{
		final HuId issueFromHUId = issueRow.getPickFromHUId();
		if (issueFromHUId == null)
		{
			throw new AdempiereException("No HU to issue from for product " + issueRow.getProductName().getDefaultValue());
		}

		return PickRequest.IssueToPickingOrderRequest.builder()
				.issueToOrderBOMLineId(issueRow.getIssueToOrderBOMLineId())
				.issueFromHUId(issueFromHUId)
				.productId(issueRow.getProductId())
				.qtyToIssue(issueRow.getQtyEffective())
				.build();
	}

	public List<PickingCandidate> createPickingCandidates(@NonNull final PackageableRow packageableRow)
	{
		final ProductsToPickRowsData productsToPickRowsData = createProductsToPickRowsData(packageableRow);
		return productsToPickRowsData.getAllRows().stream()
				.map(productsToPickRow -> pickingCandidateService.createAndSavePickingCandidates(createPickRequest(productsToPickRow, false/* isPickingReviewRequired */)))
				.map(pickHUResult -> pickHUResult.getPickingCandidate())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<WebuiPickHUResult> pick(final List<ProductsToPickRow> selectedRows)
	{
		return streamRowsEligibleForPicking(selectedRows)
				.map(row -> {
					final PickHUResult result = pickingCandidateService.pickHU(createPickRequest(row));
					return WebuiPickHUResult.of(row.getId(), result.getPickingCandidate());
				})
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<WebuiPickHUResult> setPackingInstruction(final List<ProductsToPickRow> selectedRows, final HuPackingInstructionsId huPackingInstructionsId)
	{
		final Map<PickingCandidateId, DocumentId> rowIdsByPickingCandidateId = streamRowsEligibleForPacking(selectedRows)
				.collect(ImmutableMap.toImmutableMap(ProductsToPickRow::getPickingCandidateId, ProductsToPickRow::getId));

		final Set<PickingCandidateId> pickingCandidateIds = rowIdsByPickingCandidateId.keySet();
		final List<PickingCandidate> pickingCandidates = pickingCandidateService.setHuPackingInstructionId(pickingCandidateIds, huPackingInstructionsId);

		return pickingCandidates.stream()
				.map(cand -> WebuiPickHUResult.of(rowIdsByPickingCandidateId.get(cand.getId()), cand))
				.collect(ImmutableList.toImmutableList());

	}

	public boolean anyRowsEligibleForPacking(final List<ProductsToPickRow> selectedRows)
	{
		return streamRowsEligibleForPacking(selectedRows).findAny().isPresent();
	}

	private Stream<ProductsToPickRow> streamRowsEligibleForPacking(final List<ProductsToPickRow> selectedRows)
	{
		return selectedRows
				.stream()
				.filter(ProductsToPickRow::isEligibleForPacking);
	}

	public boolean anyRowsEligibleForPicking(final List<ProductsToPickRow> selectedRows)
	{
		return streamRowsEligibleForPicking(selectedRows).findAny().isPresent();
	}

	@NonNull
	private Stream<ProductsToPickRow> streamRowsEligibleForPicking(final List<ProductsToPickRow> selectedRows)
	{
		return selectedRows
				.stream()
				.filter(ProductsToPickRow::isEligibleForPicking);
	}

	private PickRequest createPickRequest(final ProductsToPickRow row)
	{
		final PickingConfigV2 pickingConfig = getPickingConfig();
		return createPickRequest(row, pickingConfig.isPickingReviewRequired());
	}

	protected final PickingConfigV2 getPickingConfig()
	{
		return pickingConfigRepo.getPickingConfig();
	}
}
