package de.metas.purchasecandidate.purchaseordercreation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderAggregationKey;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class PurchaseCandidateToOrderWorkflow
{
	private static final AdMessageKey MSG_NO_REMOTE_PURCHASE_ORDER_WAS_PLACED = AdMessageKey.of("de.metas.purchasecandidate.Event_NoRemotePurchaseOrderWasPlaced");

	private static final AdMessageKey MSG_ERROR_WHILE_PLACING_REMOTE_PURCHASE_ORDER = AdMessageKey.of("de.metas.purchasecandidate.Event_ErrorWhilePlacingRemotePurchaseOrder");

	private static final Logger logger = LogManager.getLogger(PurchaseCandidateToOrderWorkflow.class);

	private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final VendorGatewayInvokerFactory vendorGatewayInvokerFactory;
	private final PurchaseOrderFromItemsAggregator purchaseOrderFromItemsAggregator;

	private final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());

	@Builder
	private PurchaseCandidateToOrderWorkflow(
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepo,
			@NonNull final VendorGatewayInvokerFactory vendorGatewayInvokerFactory,
			@NonNull final PurchaseOrderFromItemsAggregator purchaseOrderFromItemsAggregator)
	{
		this.purchaseCandidateRepo = purchaseCandidateRepo;
		this.vendorGatewayInvokerFactory = vendorGatewayInvokerFactory;
		this.purchaseOrderFromItemsAggregator = purchaseOrderFromItemsAggregator;
	}

	public void executeForPurchaseCandidates(@NonNull final List<PurchaseCandidate> purchaseCandidates)
	{
		final BPartnerId vendorId = assertValidAndExtractVendorId(purchaseCandidates);
		try
		{
			final ArrayList<PurchaseCandidate> purchaseCandidatesSorted = new ArrayList<>(purchaseCandidates);
			purchaseCandidatesSorted.sort(Comparator.comparing(PurchaseOrderAggregationKey::fromPurchaseCandidate));

			createPurchaseOrder0(vendorId, purchaseCandidatesSorted);
		}
		catch (final Throwable t)
		{
			logAndRethrow(t, vendorId, purchaseCandidates);
		}
	}

	private BPartnerId assertValidAndExtractVendorId(@NonNull final List<PurchaseCandidate> purchaseCandidates)
	{
		if (purchaseCandidates.isEmpty())
		{
			throw new AdempiereException("The given purchaseCandidates list needs to be non-empty");
		}

		final ImmutableSet<PurchaseCandidateId> distinctIds = purchaseCandidates.stream()
				.map(PurchaseCandidate::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (distinctIds.size() != purchaseCandidates.size())
		{
			throw new AdempiereException("The given purchaseCandidates need to have unique IDs that are all > 0")
					.appendParametersToMessage()
					.setParameter("purchaseCandidates", purchaseCandidates)
					.setParameter("distinctIds", distinctIds);
		}

		final ImmutableListMultimap<BPartnerId, PurchaseCandidate> vendorId2purchaseCandidate = //
				Multimaps.index(purchaseCandidates, PurchaseCandidate::getVendorId);

		Check.errorIf(vendorId2purchaseCandidate.keySet().size() != 1,
				"The given purchaseCandidates have different vendorIds={}; purchaseCandidates={}",
				vendorId2purchaseCandidate.keySet(), purchaseCandidates);

		return vendorId2purchaseCandidate.keySet().iterator().next();
	}

	private void createPurchaseOrder0(
			@NonNull final BPartnerId vendorId,
			@NonNull final Collection<PurchaseCandidate> purchaseCandidatesWithVendorId)
	{
		final ILoggable loggable = Loggables.get();

		loggable.addLog("vendorId={} - now invoking placeRemotePurchaseOrder with purchaseCandidates={}", vendorId, purchaseCandidatesWithVendorId);

		final VendorGatewayInvoker vendorGatewayInvoker = vendorGatewayInvokerFactory.createForVendorId(vendorId);

		final List<PurchaseItem> remotePurchaseItems = vendorGatewayInvoker.placeRemotePurchaseOrder(purchaseCandidatesWithVendorId);
		loggable.addLog("vendorId={} - placeRemotePurchaseOrder (vendorGatewayInvoker-impl={}) returned remotePurchaseItems={}",
				vendorId, vendorGatewayInvoker.getClass().getSimpleName(), remotePurchaseItems);

		throwExceptionIfEmptyResult(remotePurchaseItems, purchaseCandidatesWithVendorId, vendorId);

		final List<PurchaseOrderItem> purchaseOrderItems = remotePurchaseItems.stream()
				.map(PurchaseOrderItem::castOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		purchaseOrderFromItemsAggregator
				.addAll(purchaseOrderItems.iterator())
				.closeAllGroups();
		loggable.addLog("vendorId={} - PurchaseOrderFromCandidatesAggregator created metasfresh purchase order(s) for the remotly placed orders",
				vendorId, purchaseCandidatesWithVendorId);

		vendorGatewayInvoker.updateRemoteLineReferences(purchaseOrderItems);

		purchaseCandidateRepo.saveAllNoLock(purchaseCandidatesWithVendorId); // no lock because they are already locked by us

		throwExceptionIfErrorItemsExist(remotePurchaseItems, purchaseCandidatesWithVendorId, vendorId);
	}

	private void throwExceptionIfEmptyResult(
			final List<PurchaseItem> remotePurchaseItems,
			final Collection<PurchaseCandidate> purchaseCandidatesWithVendorId,
			final BPartnerId vendorId)
	{
		if (remotePurchaseItems.isEmpty())
		{
			final I_C_BPartner vendor = Services.get(IBPartnerDAO.class).getById(vendorId);

			throw new AdempiereException(
					MSG_NO_REMOTE_PURCHASE_ORDER_WAS_PLACED,
					vendor.getValue(), vendor.getName())
					.appendParametersToMessage()
					.setParameter("purchaseCandidatesWithVendorId", purchaseCandidatesWithVendorId);
		}
	}

	private void throwExceptionIfErrorItemsExist(
			final List<PurchaseItem> remotePurchaseItems,
			final Collection<PurchaseCandidate> purchaseCandidatesWithVendorId,
			final BPartnerId vendorId)
	{
		final List<PurchaseErrorItem> purchaseErrorItems = remotePurchaseItems.stream()
				.filter(item -> item instanceof PurchaseErrorItem)
				.map(item -> (PurchaseErrorItem)item)
				.collect(ImmutableList.toImmutableList());

		if (purchaseErrorItems.isEmpty())
		{
			return; // nothing to do
		}

		final I_C_BPartner vendor = Services.get(IBPartnerDAO.class).getById(vendorId);
		throw new AdempiereException(
				MSG_ERROR_WHILE_PLACING_REMOTE_PURCHASE_ORDER,
				vendor.getValue(), vendor.getName())
				.appendParametersToMessage()
				.setParameter("purchaseCandidatesWithVendorId", purchaseCandidatesWithVendorId)
				.setParameter("purchaseErrorItems", purchaseErrorItems);
	}

	private void logAndRethrow(
			final Throwable throwable,
			final BPartnerId vendorId,
			final Collection<PurchaseCandidate> purchaseCandidates)
	{
		final ILoggable loggable = Loggables.get();
		final String message = StringUtils.formatMessage(
				"Caught {} while working with vendorId={}; message={}, purchaseCandidates={}",
				throwable.getClass(), vendorId, throwable.getMessage(), purchaseCandidates);

		logger.error(message, throwable);
		loggable.addLog(message);

		for (final PurchaseCandidate purchaseCandidate : purchaseCandidates)
		{
			purchaseCandidate.createErrorItem()
					.throwable(throwable)
					.buildAndAdd();
		}

		purchaseCandidateRepo.saveAll(purchaseCandidates);
		exceptions.add(throwable);
		throw new AdempiereException(message, throwable);
	}
}
