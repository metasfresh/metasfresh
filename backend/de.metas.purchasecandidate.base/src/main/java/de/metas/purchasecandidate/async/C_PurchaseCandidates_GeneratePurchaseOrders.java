package de.metas.purchasecandidate.async;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.document.DocTypeId;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseCandidateToOrderWorkflow;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.loadByIds;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

/**
 * Aggregates enqueued {@link PurchaseCandidate}s and generates purchase orders.
 * <p>
 * Use {@link #enqueue(Collection)}, {@link #enqueue(Collection, DocTypeId)}, or
 * {@link #enqueue(Collection, DocTypeId, boolean)} to enqueue purchase candidates.
 * The {@code isCompleteDoc} flag (default {@code true}) controls whether the generated
 * {@code C_Order} is immediately completed (DocStatus=CO) or left as a draft (DocStatus=DR).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class C_PurchaseCandidates_GeneratePurchaseOrders extends WorkpackageProcessorAdapter
{
	public static final String DOC_TYPE_ID = "C_Doctype_Target_ID";
	public static final String IS_COMPLETE_DOC = "IsCompleteDoc";
	private final PurchaseCandidateRepository purchaseCandidateRepo = SpringContextHolder.instance.getBean(PurchaseCandidateRepository.class);
	private final VendorGatewayInvokerFactory vendorGatewayInvokerFactory = SpringContextHolder.instance.getBean(VendorGatewayInvokerFactory.class);

	public static void enqueue(@NonNull final Collection<PurchaseCandidateId> purchaseCandidateIds)
	{
		enqueue(purchaseCandidateIds, null);
	}

	public static void enqueue(@NonNull final Collection<PurchaseCandidateId> purchaseCandidateIds, @Nullable final DocTypeId docTypeId)
	{
		enqueue(purchaseCandidateIds, docTypeId, /* isCompleteDoc= */ true);
	}

	/**
	 * Enqueues the given purchase candidates for asynchronous purchase order generation.
	 *
	 * @param isCompleteDoc if {@code true}, the generated C_Order will be completed (DocStatus=CO);
	 *                      if {@code false}, only a draft C_Order is created (DocStatus=DR).
	 *                      Maps to {@code PP_Product_Planning.IsDocComplete}.
	 */
	public static void enqueue(
			@NonNull final Collection<PurchaseCandidateId> purchaseCandidateIds,
			@Nullable final DocTypeId docTypeId,
			final boolean isCompleteDoc)
	{
		final Multimap<Integer, I_C_PurchaseCandidate> vendorId2purchaseCandidate = //
				loadByIds(PurchaseCandidateId.toIntSet(purchaseCandidateIds), I_C_PurchaseCandidate.class)
						.stream()
						.collect(Multimaps.toMultimap(
								I_C_PurchaseCandidate::getVendor_ID, // key function
								Functions.identity(), // value function
								MultimapBuilder.treeKeys().arrayListValues()::build)); // multimap builder

		if (vendorId2purchaseCandidate.isEmpty())
		{
			throw new AdempiereException("No purchase candidates to enqueue");
		}

		final Collection<Collection<I_C_PurchaseCandidate>> values = vendorId2purchaseCandidate.asMap().values();
		for (final Collection<I_C_PurchaseCandidate> candidateRecords : values)
		{
			final LockOwner lockOwner = LockOwner.newOwner(C_PurchaseCandidates_GeneratePurchaseOrders.class.getSimpleName());
			final ILockCommand elementsLocker = Services.get(ILockManager.class).lock()
					.setOwner(lockOwner)
					.setAutoCleanup(false);

			final List<TableRecordReference> candidateRecordReferences = //
					TableRecordReference.ofCollection(candidateRecords);

			Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(C_PurchaseCandidates_GeneratePurchaseOrders.class)
					.newWorkPackage()
					.setElementsLocker(elementsLocker)
					.bindToThreadInheritedTrx()
					.addElements(candidateRecordReferences)
					.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
					.parameter(DOC_TYPE_ID, docTypeId)
					.parameter(IS_COMPLETE_DOC, isCompleteDoc)
					.buildAndEnqueue();
		}
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage_IGNORED, final String localTrxName_IGNORED)
	{
		final BigDecimal docTypeRepoId = getParameters().getParameterAsBigDecimal(DOC_TYPE_ID);
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(docTypeRepoId != null ? docTypeRepoId.intValue() : 0);
		// Default true for backward compatibility: work packages enqueued via the UI (before this parameter existed) should still complete the order
		final boolean isCompleteDoc = getParameters().getParameterAsBoolean(IS_COMPLETE_DOC, true);
		final PurchaseOrderFromItemsAggregator purchaseOrderFromItemsAggregator = //
				PurchaseOrderFromItemsAggregator.newInstance(docTypeId, isCompleteDoc);

		PurchaseCandidateToOrderWorkflow.builder()
				.purchaseCandidateRepo(purchaseCandidateRepo)
				.vendorGatewayInvokerFactory(vendorGatewayInvokerFactory)
				.purchaseOrderFromItemsAggregator(purchaseOrderFromItemsAggregator)
				.build()
				.executeForPurchaseCandidates(getPurchaseCandidates());

		return Result.SUCCESS;
	}

	private List<PurchaseCandidate> getPurchaseCandidates()
	{
		final boolean skipAlreadyScheduledItems = false; // there is just one processor-thread, so there won't be any elements in not yet-processed preceding WPs
		final List<I_C_Queue_Element> queueElements = retrieveQueueElements(skipAlreadyScheduledItems);

		final Set<PurchaseCandidateId> purchaseCandidateIds = queueElements
				.stream()
				.map(I_C_Queue_Element::getRecord_ID)
				.map(PurchaseCandidateId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (purchaseCandidateIds.isEmpty())
		{
			throw new AdempiereException("No purchase candidates enqueued");
		}

		final List<PurchaseCandidate> purchaseCandidates = purchaseCandidateRepo.streamAllByIds(purchaseCandidateIds)
				// only those not processed; those locked are OK because *we* locked them
				.filter(purchaseCandidate -> !purchaseCandidate.isProcessed())
				.collect(ImmutableList.toImmutableList());
		if (purchaseCandidates.isEmpty())
		{
			throw new AdempiereException("No eligible purchase candidates enqueued");
		}

		return purchaseCandidates;
	}
}
