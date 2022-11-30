package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public final class AllocationUtils
{
	private AllocationUtils()
	{
		super();
	}

	/**
	 * Creates initial {@link IMutableAllocationResult} using given <code>qtyToAllocate</code>.
	 *
	 * @return initial mutable result
	 */
	public static IMutableAllocationResult createMutableAllocationResult(@NonNull final BigDecimal qtyToAllocate)
	{
		return new MutableAllocationResult(qtyToAllocate);
	}

	/**
	 * Creates initial/empty {@link IMutableAllocationResult} using requested Qty as QtyToAllocate.
	 *
	 * @return initial mutable result
	 */
	public static IMutableAllocationResult createMutableAllocationResult(@NonNull final IAllocationRequest request)
	{
		return createMutableAllocationResult(request.getQty());
	}

	/**
	 * Creates an empty allocation request builder. Use this to create an allocation request from scratch.
	 */
	public static IAllocationRequestBuilder builder()
	{
		return new AllocationRequestBuilder();
	}


	public static IAllocationRequestBuilder createQtyLoadRequestBuilder(
			@NonNull final IAllocationRequest originalRequest,
			final IHUTransactionCandidate unloadTrx)
	{
		final Quantity qtyAbs = unloadTrx.getQuantity();
		Check.assume(qtyAbs.signum() <= 0, "Qty <= 0 ({})", unloadTrx);

		final Quantity qty = qtyAbs.negate();

		final IAllocationRequestBuilder builder = builder();
		builder.setBaseAllocationRequest(originalRequest);

		builder.setProduct(unloadTrx.getProductId());
		builder.setQuantity(qty);
		builder.setDate(unloadTrx.getDate());

		// Referenced AD_Table_ID/Record_ID
		// First try: we are keeping the From TableId/RecordId of original request
		if (originalRequest.getReference() != null)
		{
			builder.setFromReferencedTableRecord(originalRequest.getReference());
		}

		// Second try: take it from unloadTrx
		// CASE: the original request was performed on an allocation source which is over multiple documents
		// And the actual allocation source was setting the actual referenced model in generated transaction
		else if (unloadTrx.getReferencedModel() != null)
		{
			final Object referenceModel = unloadTrx.getReferencedModel();
			builder.setFromReferencedModel(referenceModel);
		}
		// Fallback: From TableId/RecordId is not available
		else
		{
			builder.setFromReferencedTableRecord(null);
		}

		// TODO check if fromTableId/fromRecordId are the same as referenced object from "unloadTrx"

		return builder;
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final I_M_Product product,
			final BigDecimal qty,
			final I_C_UOM uom,
			final ZonedDateTime date)
	{
		final Object referenceModel = null;
		return createQtyRequest(huContext, product, qty, uom, date, referenceModel);
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final I_M_Product product,
			final BigDecimal qtyBD,
			final I_C_UOM uom,
			final ZonedDateTime date,
			@Nullable final Object referenceModel)
	{
		return builder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(new Quantity(qtyBD, uom))
				.setDate(date)
				.setFromReferencedModel(referenceModel)
				.setForceQtyAllocation(false)
				.create();
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final ProductId productId,
			final Quantity qty,
			final ZonedDateTime date)
	{
		return builder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(qty)
				.setDate(date)
				.setFromReferencedModel(null)
				.setForceQtyAllocation(false)
				.create();
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final ProductId productId,
			final Quantity qty,
			final ZonedDateTime date,
			@Nullable final Object referenceModel,
			final boolean forceQtyAllocation)
	{
		return createQtyRequest (huContext, productId, qty, date, referenceModel, forceQtyAllocation, null);
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final ProductId productId,
			final Quantity qty,
			final ZonedDateTime date,
			@Nullable final Object referenceModel,
			final boolean forceQtyAllocation,
			@Nullable final ClearanceStatusInfo clearanceStatusInfo)
	{
		return builder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(qty)
				.setDate(date)
				.setFromReferencedModel(referenceModel)
				.setForceQtyAllocation(forceQtyAllocation)
				.setClearanceStatusInfo(clearanceStatusInfo)
				.create();
	}

	public static IAllocationRequest createZeroQtyRequest(final IAllocationRequest request)
	{
		return createQtyRequest(request, request.getQuantity().toZero());
	}

	public static IAllocationRequest createQtyRequest(final IAllocationRequest request, final BigDecimal qty)
	{
		return builder()
				.setBaseAllocationRequest(request)
				.setQuantity(new Quantity(qty, request.getC_UOM()))
				.create();
	}

	public static IAllocationRequest createQtyRequest(final IAllocationRequest request, final Quantity qty)
	{
		return builder()
				.setBaseAllocationRequest(request)
				.setQuantity(qty)
				.create();
	}

	/**
	 * This method creates a new request that represents the portion of the given {@code request} that is not yet covered by the given {@code result}.
	 */
	public static IAllocationRequest createQtyRequestForRemaining(final IAllocationRequest request, final IMutableAllocationResult status)
	{
		return deriveAsQtyRequestForRemaining(request, status)
				.create();
	}

	public static IAllocationRequestBuilder deriveAsQtyRequestForRemaining(final IAllocationRequest request, final IMutableAllocationResult status)
	{
		// NOTE: we assume "status" quantities are in request's UOM
		final BigDecimal qtyToRequest = status.getQtyToAllocate();
		return builder()
				.setBaseAllocationRequest(request)
				.setQuantity(new Quantity(qtyToRequest, request.getC_UOM()));
	}

	/**
	 * Merge <code>from</code> result into <code>to</code> mutable result.
	 *
	 * i.e.
	 * <ul>
	 * <li>to's QtyToAllocated will be decreased with from's QtyAllocated
	 * <li>all transactions from <code>from</code> will be appended to <code>to</code>'s transactions list
	 * <li>all attribute transactions from <code>from</code> will be appended to <code>to</code>'s attribute transactions list
	 * </ul>
	 */
	public static void mergeAllocationResult(
			@NonNull final IMutableAllocationResult to,
			@NonNull final IAllocationResult from)
	{
		to.subtractAllocatedQty(from.getQtyAllocated());
		to.addTransactions(from.getTransactions());
		to.addAttributeTransactions(from.getAttributeTransactions());
	}

	/**
	 * @return Empty immutable {@link IAllocationResult}
	 */
	public static IAllocationResult nullResult()
	{
		return NullAllocationResult.instance;
	}

	/**
	 * Creates an immutable allocation result. For cross-package use.
	 *
	 * @return {@link AllocationResult}
	 */
	public static IAllocationResult createQtyAllocationResult(final BigDecimal qtyToAllocate,
			final BigDecimal qtyAllocated,
			final List<IHUTransactionCandidate> trxs,
			final List<IHUTransactionAttribute> attributeTrxs)
	{
		return new AllocationResult(qtyToAllocate, qtyAllocated, trxs, attributeTrxs);
	}

	@Nullable
	public static Object getReferencedModel(final IAllocationRequest request)
	{
		final ITableRecordReference tableRecord = request.getReference();
		if (tableRecord == null)
		{
			return null;
		}

		final IContextAware context = request.getHuContext();
		return tableRecord.getModel(context);
	}

	/**
	 * Gets relative request qty (negated if deallocation).
	 */
	public static Quantity getQuantity(
			@NonNull final IAllocationRequest request,
			@NonNull final AllocationDirection direction)
	{
		return request
				.getQuantity() // => Quantity (absolute)
				.negateIf(direction.isOutboundDeallocation()); // => Quantity (relative)
	}

	/**
	 * Use this to create a new allocation request, using the given request as template.
	 */
	public static IAllocationRequestBuilder derive(@NonNull final IAllocationRequest request)
	{
		return builder().setBaseAllocationRequest(request);
	}

	@Nullable
	private static BPartnerId getBPartnerId(final IAllocationRequest request)
	{
		final Object referencedModel = AllocationUtils.getReferencedModel(request);
		if (referencedModel == null)
		{
			return null;
		}

		final Integer bpartnerId = InterfaceWrapperHelper.getValueOrNull(referencedModel, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID);
		return BPartnerId.ofRepoIdOrNull(bpartnerId);
	}

	/**
	 * Creates and configures an {@link IHUBuilder} based on the given <code>request</code> (bPartner and date).
	 *
	 * @return HU builder
	 */
	public static IHUBuilder createHUBuilder(final IAllocationRequest request)
	{
		final IHUContext huContext = request.getHuContext();
		final IHUBuilder huBuilder = Services.get(IHandlingUnitsDAO.class).createHUBuilder(huContext);

		huBuilder.setDate(request.getDate());

		huBuilder.setBPartnerId(getBPartnerId(request));
		// TODO: huBuilder.setC_BPartner_Location if any

		// TODO: set the HU Storage from context to builder

		return huBuilder;
	}
}
