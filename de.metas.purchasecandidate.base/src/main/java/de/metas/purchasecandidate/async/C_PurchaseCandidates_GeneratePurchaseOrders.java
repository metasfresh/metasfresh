package de.metas.purchasecandidate.async;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.async.model.I_C_Queue_Element;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.lock.api.ILockCommand;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.PurchaseCandidateToOrderWorkflow;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvokerFactory;

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
 *
 * Also, {@link #enqueue(Collection)} method is used to enqueue purchase candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_PurchaseCandidates_GeneratePurchaseOrders extends WorkpackageProcessorAdapter
{
	private final PurchaseCandidateRepository purchaseCandidateRepo = Adempiere.getBean(PurchaseCandidateRepository.class);

	public static void enqueue(final Collection<Integer> purchaseCandidateIds)
	{
		final List<TableRecordReference> purchaseCandidateRecords = //
				TableRecordReference.ofRecordIds(I_C_PurchaseCandidate.Table_Name, purchaseCandidateIds);
		if (purchaseCandidateRecords.isEmpty())
		{
			throw new AdempiereException("No purchase candidates to enqueue");
		}

		final LockOwner lockOwner = LockOwner.newOwner(C_PurchaseCandidates_GeneratePurchaseOrders.class.getSimpleName());
		final ILockCommand elementsLocker = Services.get(ILockManager.class).lock()
				.setOwner(lockOwner)
				.setAutoCleanup(false);

		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(C_PurchaseCandidates_GeneratePurchaseOrders.class)
				.newBlock()
				.newWorkpackage()
				.setElementsLocker(elementsLocker)
				.bindToThreadInheritedTrx()
				.addElements(purchaseCandidateRecords)
				.setUserInChargeId(Env.getAD_User_ID())
				.build();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final PurchaseCandidateRepository purchaseCandidateRepo = //
				Adempiere.getBean(PurchaseCandidateRepository.class);
		final VendorGatewayInvokerFactory vendorGatewayInvokerFactory = //
				Adempiere.getBean(VendorGatewayInvokerFactory.class);

		final PurchaseOrderFromItemsAggregator purchaseOrderFromItemsAggregator = //
				PurchaseOrderFromItemsAggregator.newInstance();

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
		final boolean skipAlreadyScheduledItems = true;
		final Set<Integer> purchaseCandidateIds = retrieveQueueElements(skipAlreadyScheduledItems)
				.stream()
				.map(I_C_Queue_Element::getRecord_ID)
				.filter(id -> id > 0)
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
