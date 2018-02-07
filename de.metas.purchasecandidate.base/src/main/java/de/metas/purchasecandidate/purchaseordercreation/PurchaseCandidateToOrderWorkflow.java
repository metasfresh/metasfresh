package de.metas.purchasecandidate.purchaseordercreation;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvoker;
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
	public static PurchaseCandidateToOrderWorkflow createNew()
	{
		return new PurchaseCandidateToOrderWorkflow();
	}

	private PurchaseCandidateToOrderWorkflow()
	{
	};

	private final PurchaseCandidateRepository purchaseCandidateRepo = Adempiere.getBean(PurchaseCandidateRepository.class);
	private final PurchaseOrderItemRepository purchaseOrderItemRepo = Adempiere.getBean(PurchaseOrderItemRepository.class);

	public void doIt(final List<PurchaseCandidate> proposedPurchaseCandidates)
	{
		final ILoggable loggable = Loggables.get();

		final ImmutableListMultimap<Integer, PurchaseCandidate> vendorId2purchaseCandidate = //
				Multimaps.index(proposedPurchaseCandidates, PurchaseCandidate::getVendorBPartnerId);

		final List<CompletableFuture<Void>> futures = vendorId2purchaseCandidate.asMap()
				.entrySet().stream()
				.map(entry -> CompletableFuture.runAsync(() -> createPurchaseOrderInDifferentThread(
						entry.getKey(), // vendorId
						entry.getValue(), // purchaseCandidates
						loggable)))
				.collect(Collectors.toList());

		CompletableFuture
				.allOf(futures.toArray(new CompletableFuture[futures.size()]))
				.join();
	}

	private void createPurchaseOrderInDifferentThread(
			final int vendorId,
			@NonNull final Collection<PurchaseCandidate> purchaseCandidatesWithVendorId,
			@NonNull final ILoggable loggable)
	{
		// note: if the VendorGateWayInvoker or PurchaseOrderFromCandidatesAggregator use Loggable.get() internally,
		// they shall receive "our" loggable.
		try (final IAutoCloseable autoClosable = Loggables.temporarySetLoggable(loggable))
		{
			loggable.addLog("vendorId={} - now invoking placeRemotePurchaseOrder with purchaseCandidates={}",
					vendorId, purchaseCandidatesWithVendorId);

			final VendorGatewayInvoker vendorGatewayInvoker = VendorGatewayInvoker
					.createForVendorId(vendorId);

			final Collection<PurchaseOrderItem> purchaseOrderItems = vendorGatewayInvoker
					.placeRemotePurchaseOrder(purchaseCandidatesWithVendorId);
			loggable.addLog("vendorId={} - placeRemotePurchaseOrder returned purchaseOrderItems={}",
					vendorId, purchaseOrderItems);

			final PurchaseOrderFromItemsAggregator purchaseOrdersAggregator = PurchaseOrderFromItemsAggregator.newInstance();
			purchaseOrdersAggregator
					.addAll(purchaseOrderItems.iterator())
					.closeAllGroups();
			loggable.addLog("vendorId={} - PurchaseOrderFromCandidatesAggregator created the purchase order(s)",
					vendorId, purchaseCandidatesWithVendorId);

			vendorGatewayInvoker.updateRemoteLineReferences(purchaseOrderItems);

			purchaseCandidateRepo.saveAllNoLock(purchaseCandidatesWithVendorId); // no lock because they are already locked by us
			purchaseOrderItemRepo.storeNewRecords(purchaseOrderItems);
		}
	}
}
