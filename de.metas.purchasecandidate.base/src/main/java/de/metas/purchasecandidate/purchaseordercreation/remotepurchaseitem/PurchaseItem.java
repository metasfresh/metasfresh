package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import org.adempiere.util.lang.ITableRecordReference;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.localorder.PurchaseOrderFromItemsAggregator;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.VendorGatewayInvoker;

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

/**
 * Instances of this interface are returned by {@link VendorGatewayInvoker} and are processed by {@link PurchaseOrderFromItemsAggregator}.
 * They are created via {@link PurchaseCandidate#createErrorItem()} and {@link PurchaseCandidate#createOrderItem()}.
 */
public interface PurchaseItem
{
	ITableRecordReference getTransactionReference();

	int getPurchaseCandidateId();

	int getPurchaseItemId();
}
