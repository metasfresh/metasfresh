package de.metas.purchasecandidate.purchaseordercreation.remoteorder;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
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

public final class NullVendorGatewayInvoker implements VendorGatewayInvoker
{
	public static final String NO_REMOTE_PURCHASE_ID = "NO_REMOTE_PURCHASE_ID";

	public static final NullVendorGatewayInvoker INSTANCE = new NullVendorGatewayInvoker();

	private NullVendorGatewayInvoker()
	{
	}

	/**
	 * Does not actually place a remote purchase order, but just returns a "plain" purchase order item for each candidate.
	 */
	@Override
	public List<PurchaseItem> placeRemotePurchaseOrder(
			@NonNull final Collection<PurchaseCandidate> purchaseCandidates)
	{
		return purchaseCandidates.stream()
				.map(NullVendorGatewayInvoker::createPlainPurchaseOrderItem)
				.collect(ImmutableList.toImmutableList());

	}

	private static PurchaseOrderItem createPlainPurchaseOrderItem(
			@NonNull final PurchaseCandidate purchaseCandidate)
	{
		return purchaseCandidate.createOrderItem()
				.remotePurchaseOrderId(NO_REMOTE_PURCHASE_ID)
				.datePromised(purchaseCandidate.getDateRequired())
				.purchasedQty(purchaseCandidate.getQtyToPurchase())
				.buildAndAddToParent();
	}

	/**
	 * Does nothing
	 */
	@Override
	public void updateRemoteLineReferences(Collection<PurchaseOrderItem> purchaseOrderItem)
	{
	}
}
