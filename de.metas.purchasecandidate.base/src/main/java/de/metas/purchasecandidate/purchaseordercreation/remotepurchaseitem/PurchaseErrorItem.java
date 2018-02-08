package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ITableRecordReference;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.VendorProductInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class PurchaseErrorItem implements RemotePurchaseItem
{
	ITableRecordReference transactionReference;

	PurchaseCandidate purchaseCandidate;

	Throwable throwable;

	@Builder
	private PurchaseErrorItem(
			@NonNull final Throwable throwable,
			@NonNull final PurchaseCandidate purchaseCandidate,
			@Nullable final ITableRecordReference transactionReference)
	{
		this.throwable = throwable;
		this.purchaseCandidate = purchaseCandidate;

		this.transactionReference = transactionReference;
	}

	public int getProductId()
	{
		return purchaseCandidate.getProductId();
	}

	public int getUomId()
	{
		return purchaseCandidate.getUomId();
	}

	public int getOrgId()
	{
		return purchaseCandidate.getOrgId();
	}

	public int getWarehouseId()
	{
		return purchaseCandidate.getWarehouseId();
	}

	public VendorProductInfo getVendorProductInfo()
	{
		return purchaseCandidate.getVendorProductInfo();
	}

}
