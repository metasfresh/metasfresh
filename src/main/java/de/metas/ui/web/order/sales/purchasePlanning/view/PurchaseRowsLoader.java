package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimaps;

import de.metas.printing.esb.base.util.Check;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandWithCandidates;
import de.metas.purchasecandidate.availability.AvailabilityCheckService;
import de.metas.purchasecandidate.availability.AvailabilityException;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
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
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final PurchaseRowFactory purchaseRowFactory;
	private final AvailabilityCheckService availabilityCheckService;

	private static final String SYSCONFIG_ASYNC_AVAILIABILITY_CHECK = "de.metas.ui.web.order.sales.purchasePlanning.view.SalesOrder2PurchaseViewFactory.AsyncAvailiabilityCheck";

	// parameters
	private final Supplier<IView> viewSupplier;
	private final ImmutableList<PurchaseDemandWithCandidates> purchaseDemandWithCandidatesList;

	private ImmutableMap<Equivalence.Wrapper<PurchaseCandidate>, PurchaseRow> _purchaseCandidate2purchaseRow;

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
		return () -> {

			final List<PurchaseRow> loadResult = load();
			if (isMakeAsynchronousAvailiabilityCheck())
			{
				createAndAddAvailabilityResultRowsAsync();
			}
			else
			{
				createAndAddAvailabilityResultRows();
			}

			return loadResult;
		};

	}

	@VisibleForTesting
	List<PurchaseRow> load()
	{
		final ImmutableList.Builder<PurchaseRow> result = ImmutableList.builder();
		final ImmutableMap.Builder<Equivalence.Wrapper<PurchaseCandidate>, PurchaseRow> purchaseCandidate2purchaseRowBuilder = ImmutableMap.builder();

		for (final PurchaseDemandWithCandidates demandWithCandidates : purchaseDemandWithCandidatesList)
		{
			final PurchaseDemand demand = demandWithCandidates.getPurchaseDemand();

			final ImmutableList.Builder<PurchaseRow> rows = ImmutableList.builder();
			for (final PurchaseCandidate purchaseCandidate : demandWithCandidates.getPurchaseCandidates())
			{
				final PurchaseRow candidateRow = purchaseRowFactory
						.rowFromPurchaseCandidateBuilder()
						.purchaseCandidate(purchaseCandidate)
						.purchaseDemandId(demand.getId())
						.datePromised(demand.getDatePromised())
						.convertAmountsToCurrency(demand.getCurrency())
						.build();

				purchaseCandidate2purchaseRowBuilder.put(id(purchaseCandidate), candidateRow);
				rows.add(candidateRow);
			}

			final PurchaseRow groupRow = purchaseRowFactory.createGroupRow(demand, rows.build());
			result.add(groupRow);

		}
		_purchaseCandidate2purchaseRow = purchaseCandidate2purchaseRowBuilder.build();

		return result.build();
	}

	private final Equivalence.Wrapper<PurchaseCandidate> id(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		return Equivalence.identity().wrap(purchaseCandidate);
	}

	private List<PurchaseCandidate> getAllPurchaseCandidates()
	{
		Check.assumeNotNull(_purchaseCandidate2purchaseRow, "purchaseCandidate2purchaseRow was already loaded via load(); this={}", this);

		return purchaseDemandWithCandidatesList.stream()
				.map(PurchaseDemandWithCandidates::getPurchaseCandidates)
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}

	private PurchaseRow getPurchaseRowByPurchaseCandidate(final PurchaseCandidate purchaseCandidate)
	{
		return _purchaseCandidate2purchaseRow.get(id(purchaseCandidate));
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
	void createAndAddAvailabilityResultRows()
	{
		try
		{
			final List<PurchaseCandidate> purchaseCandidates = getAllPurchaseCandidates();
			final List<AvailabilityResult> availabilityCheckResult = availabilityCheckService.checkAvailability(purchaseCandidates);
			handleResultForAsyncAvailabilityCheck_Success(availabilityCheckResult);
		}
		catch (final Throwable throwable)
		{
			handleResultForAsyncAvailabilityCheck_Error(throwable);
		}
	}

	private void createAndAddAvailabilityResultRowsAsync()
	{
		final List<PurchaseCandidate> purchaseCandidates = getAllPurchaseCandidates();
		availabilityCheckService.checkAvailabilityAsync(purchaseCandidates, this::handleResultForAsyncAvailabilityCheck);
	}

	private void handleResultForAsyncAvailabilityCheck(
			@Nullable final List<AvailabilityResult> availabilityCheckResult,
			@Nullable final Throwable error)
	{
		if (availabilityCheckResult != null)
		{
			handleResultForAsyncAvailabilityCheck_Success(availabilityCheckResult);
		}
		if (error != null)
		{
			handleResultForAsyncAvailabilityCheck_Error(Util.coalesce(error.getCause(), error));
		}
	}

	private void handleResultForAsyncAvailabilityCheck_Success(final List<AvailabilityResult> availabilityResults)
	{
		final ImmutableListMultimap<Equivalence.Wrapper<PurchaseCandidate>, AvailabilityResult> //
		availabilityResultsByPurchaseCandidadate = Multimaps.index(availabilityResults, result -> id(result.getPurchaseCandidate()));

		final List<DocumentId> changedRowIds = new ArrayList<>();

		for (final Map.Entry<Equivalence.Wrapper<PurchaseCandidate>, Collection<AvailabilityResult>> entry : availabilityResultsByPurchaseCandidadate.asMap().entrySet())
		{
			final PurchaseRow purchaseRowToAugment = getPurchaseRowByPurchaseCandidate(entry.getKey().get());
			final ImmutableList.Builder<PurchaseRow> availabilityResultRows = ImmutableList.builder();

			for (final AvailabilityResult availabilityResult : entry.getValue())
			{
				final PurchaseRow availabilityResultRow = purchaseRowFactory.rowFromAvailabilityResultBuilder()
						.parentRow(purchaseRowToAugment)
						.availabilityResult(availabilityResult)
						.build();

				availabilityResultRows.add(availabilityResultRow);
			}
			purchaseRowToAugment.setAvailabilityInfoRows(availabilityResultRows.build());
			changedRowIds.add(purchaseRowToAugment.getId());
		}

		notifyViewOfChanges(changedRowIds);
	}

	private void handleResultForAsyncAvailabilityCheck_Error(@Nullable final Throwable throwable)
	{
		if (throwable == null)
		{
			return;
		}
		if (throwable instanceof AvailabilityException)
		{
			final AvailabilityException availabilityException = AvailabilityException.cast(throwable);

			final List<DocumentId> changedRowIds = new ArrayList<>();

			final Set<Entry<PurchaseCandidate, Throwable>> entrySet = availabilityException.getPurchaseCandidate2Throwable().entrySet();
			for (final Entry<PurchaseCandidate, Throwable> purchaseCandidate2throwable : entrySet)
			{
				final PurchaseRow purchaseRowToAugment = getPurchaseRowByPurchaseCandidate(purchaseCandidate2throwable.getKey());

				final PurchaseRow availabilityResultRow = purchaseRowFactory
						.rowFromThrowableBuilder()
						.parentRow(purchaseRowToAugment)
						.throwable(purchaseCandidate2throwable.getValue())
						.build();

				purchaseRowToAugment.setAvailabilityInfoRow(availabilityResultRow);
				changedRowIds.add(purchaseRowToAugment.getId());
			}

			notifyViewOfChanges(changedRowIds);
		}
		else
		{
			// TODO: display an error-message in the webui
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
}
