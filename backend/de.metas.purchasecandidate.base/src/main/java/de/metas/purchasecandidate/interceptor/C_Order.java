package de.metas.purchasecandidate.interceptor;

import java.util.Map;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.order.OrderId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;

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
@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	@Autowired
	private PurchaseCandidateRepository purchaseCandidateRepo;

	/**
	 * On sales-order completion (including re-completion after reactivation), enqueue any manual
	 * {@code C_PurchaseCandidate} records tied to this sales order for automatic purchase-order generation.
	 * <p>
	 * Candidates are grouped by their matching {@code PP_Product_Planning.IsDocComplete} flag so that each
	 * resulting purchase order gets created either as draft ({@code IsDocComplete=N}) or completed
	 * ({@code IsDocComplete=Y}) per the product-planning configuration — instead of being blanket-completed
	 * regardless of setup.
	 * <p>
	 * Fixes <a href="https://github.com/metasfresh/me03/issues/29155">me03#29155</a> /
	 * <a href="https://github.com/metasfresh/mf15/issues/4039">mf15#4039</a>: previously the 1-arg
	 * {@code enqueue(ids)} overload was used, which hard-coded {@code isCompleteDoc=true} — on SO
	 * reactivation this would auto-complete purchase orders that the planning said should stay as drafts.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void scheduleCreatePurchaseOrderFromPurchaseCandidates(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return;
		}

		final OrderId salesOrderId = OrderId.ofRepoId(order.getC_Order_ID());
		final Map<Boolean, Set<PurchaseCandidateId>> candidatesByIsDocComplete =
				purchaseCandidateRepo.retrieveManualPurchaseCandidateIdsBySalesOrderIdGroupedByIsDocComplete(salesOrderId);

		for (final Map.Entry<Boolean, Set<PurchaseCandidateId>> entry : candidatesByIsDocComplete.entrySet())
		{
			final Set<PurchaseCandidateId> ids = entry.getValue();
			if (ids.isEmpty())
			{
				continue;
			}
			final boolean isCompleteDoc = entry.getKey();
			C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(ids, /* docTypeId= */ null, isCompleteDoc);
		}
	}
}
