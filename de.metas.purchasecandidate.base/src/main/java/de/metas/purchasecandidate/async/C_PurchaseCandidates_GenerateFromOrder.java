package de.metas.purchasecandidate.async;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.order.IOrderDAO;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.SalesOrderLines;

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

public class C_PurchaseCandidates_GenerateFromOrder extends WorkpackageProcessorAdapter
{
	public static void enqueueOrderId(final int orderId)
	{
		Check.assumeGreaterThanZero(orderId, "orderId");

		Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(C_PurchaseCandidates_GenerateFromOrder.class)
				.newBlock()
				.newWorkpackage()
				.bindToThreadInheritedTrx()
				.addElement(TableRecordReference.of(org.compiere.model.I_C_Order.Table_Name, orderId))
				.setUserInChargeId(Env.getAD_User_ID())
				.build();
	}

	// services
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final PurchaseCandidateRepository purchaseCandidatesRepo = Adempiere.getBean(PurchaseCandidateRepository.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final List<I_C_Order> salesOrder = retrieveItems(I_C_Order.class);
		salesOrder.forEach(this::createPurchaseCandidates);

		return Result.SUCCESS;
	}

	private void createPurchaseCandidates(final I_C_Order salesOrder)
	{
		final List<Integer> salesOrderLineIds = orderDAO.retrieveAllOrderLineIds(salesOrder);
		final SalesOrderLines factory = SalesOrderLines.builder()
				.salesOrderLineIds(salesOrderLineIds)
				.purchaseCandidateRepository(purchaseCandidatesRepo)
				// TODO: introduced/set dateRequired provider
				.build();
		final List<PurchaseCandidate> purchaseCandidates = factory.getAllPurchaseCandidates();
		purchaseCandidatesRepo.saveAll(purchaseCandidates);

		purchaseCandidates.forEach(purchaseCandidate -> Loggables.get().addLog("Created/Updated " + purchaseCandidate));
	}
}
