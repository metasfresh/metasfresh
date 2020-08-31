package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandWithCandidates;
import de.metas.purchasecandidate.availability.AvailabilityCheckService;
import de.metas.purchasecandidate.availability.AvailabilityException;
import de.metas.purchasecandidate.availability.AvailabilityMultiResult;
import de.metas.purchasecandidate.availability.PurchaseCandidatesAvailabilityRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import de.metas.vendor.gateway.api.availability.TrackingId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

class PurchaseRowsLoader
{
	// services
	private static final Logger logger = LogManager.getLogger(PurchaseRowsLoader.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final PurchaseRowFactory purchaseRowFactory;
	private final AvailabilityCheckService availabilityCheckService;

	private static final String SYSCONFIG_ASYNC_AVAILIABILITY_CHECK = "de.metas.ui.web.order.sales.purchasePlanning.view.SalesOrder2PurchaseViewFactory.AsyncAvailiabilityCheck";

	// parameters
	private final Supplier<IView> viewSupplier;
	private final ImmutableList<PurchaseDemandWithCandidates> purchaseDemandWithCandidatesList;

	@Builder
	private PurchaseRowsLoader(
			@NonNull final List<PurchaseDemandWithCandidates> purchaseDemandWithCandidatesList,
			@NonNull final Supplier<IView> viewSupplier,
			//
			@NonNull final PurchaseRowFactory purchaseRowFactory,
			@NonNull final AvailabilityCheckService availabilityCheckService)
	{
		this.purchaseDemandWithCandidatesList = ImmutableList.copyOf(purchaseDemandWithCandidatesList);
		this.viewSupplier = viewSupplier;

		this.purchaseRowFactory = purchaseRowFactory;
		this.availabilityCheckService = availabilityCheckService;
	}

	public PurchaseRowsSupplier createPurchaseRowsSupplier()
	{
		return this::loadAndCheckAvailability;
	}

	private List<PurchaseRow> loadAndCheckAvailability()
	{
		final PurchaseRowsList rows = load();
		if (isMakeAsynchronousAvailiabilityCheck())
		{
			createAndAddAvailabilityResultRowsAsync(rows);
		}
		else
		{
			createAndAddAvailabilityResultRows(rows);
		}

		return rows.getTopLevelRows();
	}

	@VisibleForTesting
	PurchaseRowsList load()
	{
		final PurchaseRowsList.PurchaseRowsListBuilder resultBuilder = PurchaseRowsList.builder();

		for (final PurchaseDemandWithCandidates demandWithCandidates : purchaseDemandWithCandidatesList)
		{
			final PurchaseDemand demand = demandWithCandidates.getPurchaseDemand();

			final List<PurchaseRow> purchaseCandidateRows = new ArrayList<>();
			final List<TrackingId> trackingIds = new ArrayList<>();
			for (final PurchaseCandidatesGroup purchaseCandidatesGroup : demandWithCandidates.getPurchaseCandidatesGroups())
			{
				final PurchaseRow purchaseCandidateRow = purchaseRowFactory
						.lineRowBuilder()
						.purchaseCandidatesGroup(purchaseCandidatesGroup)
						.convertAmountsToCurrencyId(demand.getCurrencyIdOrNull())
						.build();

				purchaseCandidateRows.add(purchaseCandidateRow);

				final TrackingId trackingId = TrackingId.random();
				trackingIds.add(trackingId);
				resultBuilder.purchaseCandidatesGroup(trackingId, purchaseCandidatesGroup);
				resultBuilder.purchaseCandidateRow(trackingId, purchaseCandidateRow);
			}

			final PurchaseRow groupRow = purchaseRowFactory.createGroupRow(demand, purchaseCandidateRows);
			resultBuilder.topLevelRow(groupRow);

			final PurchaseRowId groupRowId = groupRow.getRowId();
			trackingIds.forEach(trackingId -> resultBuilder.trackingIdsByTopLevelRowId(trackingId, groupRowId));
		}

		return resultBuilder.build();
	}

	@VisibleForTesting
	PurchaseCandidatesAvailabilityRequest createAvailabilityRequest(@NonNull final PurchaseRowsList rows)
	{
		return PurchaseCandidatesAvailabilityRequest.of(rows.getPurchaseCandidatesGroups());
	}

	private boolean isMakeAsynchronousAvailiabilityCheck()
	{
		final Properties ctx = Env.getCtx();

		final boolean result = sysConfigBL.getBooleanValue(
				SYSCONFIG_ASYNC_AVAILIABILITY_CHECK,
				false,
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));

		return result;
	}

	@VisibleForTesting
	void createAndAddAvailabilityResultRows(final PurchaseRowsList rows)
	{
		try
		{
			final AvailabilityMultiResult availabilityCheckResult = availabilityCheckService.checkAvailability(createAvailabilityRequest(rows));
			handleResultForAsyncAvailabilityCheck_Success(rows, availabilityCheckResult);
		}
		catch (final Throwable throwable)
		{
			handleResultForAsyncAvailabilityCheck_Error(rows, throwable);
		}
	}

	private void createAndAddAvailabilityResultRowsAsync(final PurchaseRowsList rows)
	{
		availabilityCheckService.checkAvailabilityAsync(
				createAvailabilityRequest(rows),
				(result, error) -> handleResultForAsyncAvailabilityCheck(rows, result, error));
	}

	private void handleResultForAsyncAvailabilityCheck(
			@NonNull final PurchaseRowsList rows,
			@Nullable final AvailabilityMultiResult availabilityMultiResult,
			@Nullable final Throwable error)
	{
		if (availabilityMultiResult != null)
		{
			handleResultForAsyncAvailabilityCheck_Success(rows, availabilityMultiResult);
		}
		if (error != null)
		{
			handleResultForAsyncAvailabilityCheck_Error(rows, CoalesceUtil.coalesce(error.getCause(), error));
		}
	}

	private void handleResultForAsyncAvailabilityCheck_Success(
			final PurchaseRowsList rows,
			final AvailabilityMultiResult availabilityResults)
	{
		final List<DocumentId> changedRowIds = new ArrayList<>();

		for (final TrackingId trackingId : availabilityResults.getTrackingIds())
		{
			final PurchaseRow lineRow = rows.getPurchaseRowByTrackingId(trackingId);
			if (lineRow == null)
			{
				logger.warn("No row found for {}. Skip updating the row with availability results.", trackingId);
				continue;
			}

			final ImmutableList<PurchaseRow> availabilityResultRows = availabilityResults.getByTrackingId(trackingId)
					.stream()
					.map(availabilityResult -> purchaseRowFactory.availabilityDetailSuccessBuilder()
							.lineRow(lineRow)
							.availabilityResult(availabilityResult)
							.build())
					.collect(ImmutableList.toImmutableList());
			lineRow.setAvailabilityInfoRows(availabilityResultRows);

			changedRowIds.add(rows.getTopLevelDocumentIdByTrackingId(trackingId, lineRow.getId()));
		}

		notifyViewOfChanges(changedRowIds);
	}

	private void handleResultForAsyncAvailabilityCheck_Error(final PurchaseRowsList rows, final Throwable throwable)
	{
		if (throwable instanceof AvailabilityException)
		{
			final AvailabilityException availabilityException = AvailabilityException.cast(throwable);

			final List<DocumentId> changedRowIds = new ArrayList<>();

			for (final AvailabilityException.ErrorItem errorItem : availabilityException.getErrorItems())
			{
				final TrackingId trackingId = errorItem.getTrackingId();
				final PurchaseRow lineRow = rows.getPurchaseRowByTrackingId(trackingId);
				if (lineRow == null)
				{
					logger.warn("No line row found for {}. Skip updating the row with availability errors: {}", trackingId, errorItem);
					continue;
				}

				final PurchaseRow availabilityResultRow = purchaseRowFactory.availabilityDetailErrorBuilder()
						.lineRow(lineRow)
						.throwable(errorItem.getError())
						.build();

				lineRow.setAvailabilityInfoRow(availabilityResultRow);

				changedRowIds.add(rows.getTopLevelDocumentIdByTrackingId(trackingId, lineRow.getId()));
			}

			notifyViewOfChanges(changedRowIds);
		}
		else
		{
			// TODO: display an error-message in the webui
			logger.warn("Got unknown exception while doing availability check. Ignored.", throwable);
		}
	}

	private void notifyViewOfChanges(final List<DocumentId> changedRowIds)
	{
		final IView view = viewSupplier.get();
		if (view != null)
		{
			ViewChangesCollector
					.getCurrentOrAutoflush()
					.collectRowsChanged(view, DocumentIdsSelection.of(changedRowIds));
		}
	}

	@VisibleForTesting
	static class PurchaseRowsList
	{
		@Getter
		private final ImmutableList<PurchaseRow> topLevelRows;
		private final ImmutableMap<TrackingId, PurchaseRowId> trackingIdsByTopLevelRowIds;
		@Getter
		private final ImmutableMap<TrackingId, PurchaseCandidatesGroup> purchaseCandidatesGroups;
		private final ImmutableMap<TrackingId, PurchaseRow> purchaseCandidateRows;

		@lombok.Builder
		public PurchaseRowsList(
				@NonNull @lombok.Singular final ImmutableList<PurchaseRow> topLevelRows,
				@NonNull @lombok.Singular final ImmutableMap<TrackingId, PurchaseRowId> trackingIdsByTopLevelRowIds,
				@NonNull @lombok.Singular final ImmutableMap<TrackingId, PurchaseCandidatesGroup> purchaseCandidatesGroups,
				@NonNull @lombok.Singular final ImmutableMap<TrackingId, PurchaseRow> purchaseCandidateRows)
		{
			this.topLevelRows = topLevelRows;
			this.trackingIdsByTopLevelRowIds = trackingIdsByTopLevelRowIds;
			this.purchaseCandidatesGroups = purchaseCandidatesGroups;
			this.purchaseCandidateRows = purchaseCandidateRows;
		}

		public PurchaseRow getPurchaseRowByTrackingId(TrackingId trackingId)
		{
			return purchaseCandidateRows.get(trackingId);
		}

		public DocumentId getTopLevelDocumentIdByTrackingId(final TrackingId trackingId, final DocumentId defaultValue)
		{
			final PurchaseRowId purchaseRowId = trackingIdsByTopLevelRowIds.get(trackingId);
			return purchaseRowId != null ? purchaseRowId.toDocumentId() : defaultValue;
		}

	}
}
