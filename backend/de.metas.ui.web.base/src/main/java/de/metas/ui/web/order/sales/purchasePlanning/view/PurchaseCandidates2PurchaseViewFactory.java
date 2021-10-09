package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandWithCandidatesService;
import de.metas.purchasecandidate.availability.AvailabilityCheckService;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseCandidateAggregate;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseCandidateAggregator;
import de.metas.ui.web.order.sales.purchasePlanning.process.WEBUI_PurchaseCandidates_PurchaseView_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import lombok.NonNull;

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

@ViewFactory(windowId = PurchaseCandidates2PurchaseViewFactory.WINDOW_ID_STRING)
public class PurchaseCandidates2PurchaseViewFactory extends PurchaseViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "purchaseCandidates2po";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	// services
	private final PurchaseCandidateRepository purchaseCandidatesRepo;

	public PurchaseCandidates2PurchaseViewFactory(
			@NonNull final PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService,
			@NonNull final AvailabilityCheckService availabilityCheckService,
			@NonNull final PurchaseCandidateRepository purchaseCandidatesRepo,
			@NonNull final PurchaseRowFactory purchaseRowFactory)
	{
		super(WINDOW_ID,
				WEBUI_PurchaseCandidates_PurchaseView_Launcher.class, // launcherProcessClass
				purchaseDemandWithCandidatesService,
				availabilityCheckService,
				purchaseRowFactory);

		this.purchaseCandidatesRepo = purchaseCandidatesRepo;
	}

	@Override
	protected List<PurchaseDemand> getDemands(@NonNull final CreateViewRequest request)
	{
		final Set<PurchaseCandidateId> purchaseCandidateIds = PurchaseCandidateId.ofRepoIds(request.getFilterOnlyIds());
		Check.assumeNotEmpty(purchaseCandidateIds, "purchaseCandidateIds is not empty");

		final List<PurchaseCandidate> purchaseCandidates = purchaseCandidatesRepo.getAllByIds(purchaseCandidateIds);
		Check.assumeNotEmpty(purchaseCandidates, "purchaseCandidates is not empty");

		return PurchaseCandidateAggregator.aggregate(purchaseCandidates)
				.stream()
				.map(aggregate -> toPurchaseDemand(aggregate))
				.collect(ImmutableList.toImmutableList());
	}

	private static PurchaseDemand toPurchaseDemand(@NonNull final PurchaseCandidateAggregate aggregate)
	{
		return PurchaseDemand.builder()
				.existingPurchaseCandidateIds(aggregate.getPurchaseCandidateIds())
				//
				.orgId(aggregate.getOrgId())
				.warehouseId(aggregate.getWarehouseId())
				//
				.productId(aggregate.getProductId())
				.attributeSetInstanceId(aggregate.getAttributeSetInstanceId())
				//
				.qtyToDeliver(aggregate.getQtyToDeliver())
				//
				.salesPreparationDate(aggregate.getDatePromised())
				//
				.build();
	}

	@Override
	protected void onViewClosedByUser(@NonNull final PurchaseView purchaseView)
	{
		final List<PurchaseRow> rows = purchaseView.getRows();

		PurchaseRowsSaver.builder()
				.purchaseCandidatesRepo(purchaseCandidatesRepo)
				.build()
				.save(rows);
	}
}
