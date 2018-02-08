package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_OrderLine;

import com.google.common.base.Objects;

import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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
 * Instances of this class represent a piece of a <b>factual</b> purchase order,
 * for which the system now needs to create a {@code C_Order} etc.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
public class PurchaseOrderItem implements PurchaseItem
{
	public static PurchaseOrderItem cast(final PurchaseItem purchaseItem)
	{
		return (PurchaseOrderItem)purchaseItem;
	}

	@Getter
	private final ITableRecordReference transactionReference;

	@Getter
	private final String remotePurchaseOrderId;

	@Getter
	private final PurchaseCandidate purchaseCandidate;

	@Getter
	private final BigDecimal purchasedQty;

	@Getter
	private final Date datePromised;

	@Getter
	private int purchaseOrderId;

	@Getter
	private int purchaseOrderLineId;

	@Builder
	private PurchaseOrderItem(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final BigDecimal purchasedQty,
			@NonNull final Date datePromised,
			@NonNull final String remotePurchaseOrderId,
			@Nullable final ITableRecordReference transactionReference)
	{
		this.purchaseCandidate = purchaseCandidate;
		this.purchasedQty = purchasedQty;
		this.datePromised = datePromised;
		this.remotePurchaseOrderId = remotePurchaseOrderId;

		final boolean remotePurchaseExists = !Objects.equal(remotePurchaseOrderId, NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID);
		Check.errorIf(remotePurchaseExists && transactionReference == null,
				"If there is a remote purchase order, then the given transactionReference may not be null; remotePurchaseOrderId={}",
				remotePurchaseOrderId);
		this.transactionReference = transactionReference;
	}

	@Override
	public int getPurchaseCandidateId()
	{
		return purchaseCandidate.getPurchaseCandidateId();
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

	public void setPurchaseOrderLineIdAndMarkProcessed(final int purchaseOrderLineId)
	{
		this.purchaseOrderId = load(purchaseOrderLineId, I_C_OrderLine.class).getC_Order_ID();
		this.purchaseOrderLineId = purchaseOrderLineId;

		purchaseCandidate.markProcessed();
	}
}
