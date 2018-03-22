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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.interfaces.I_C_BPartner;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public final class AllocationUtils
{
	private AllocationUtils()
	{
		super();
	}

	/**
	 * Creates initial {@link IMutableAllocationResult} using given <code>qtyToAllocate</code>.
	 *
	 * @param qtyToAllocate
	 * @return initial mutable result
	 */
	public static IMutableAllocationResult createMutableAllocationResult(@NonNull final BigDecimal qtyToAllocate)
	{
		return new MutableAllocationResult(qtyToAllocate);
	}

	/**
	 * Creates initial/empty {@link IMutableAllocationResult} using requested Qty as QtyToAllocate.
	 *
	 * @param request
	 * @return initial mutable result
	 */
	public static IMutableAllocationResult createMutableAllocationResult(@NonNull final IAllocationRequest request)
	{
		return createMutableAllocationResult(request.getQty());
	}

	/**
	 * Creates an empty allocation request builder. Use this to create an allocation request from scratch.
	 *
	 * @return
	 */
	public static IAllocationRequestBuilder createAllocationRequestBuilder()
	{
		return new AllocationRequestBuilder();
	}

	public static IAllocationRequestBuilder createQtyLoadRequestBuilder(
			final IAllocationRequest originalRequest,
			final IHUTransactionCandidate unloadTrx)
	{
		final Quantity qtyAbs = unloadTrx.getQuantity();
		Check.assume(qtyAbs.signum() <= 0, "Qty <= 0 ({})", unloadTrx);

		final Quantity qty = qtyAbs.negate();

		final IAllocationRequestBuilder builder = createAllocationRequestBuilder();
		builder.setBaseAllocationRequest(originalRequest);

		builder.setProduct(unloadTrx.getProduct());
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

	public static IAllocationRequest createQtyRequest(final IHUContext huContext, final I_M_Product product, final BigDecimal qty, final I_C_UOM uom, final Date date)
	{
		final Object referenceModel = null;
		return createQtyRequest(huContext, product, qty, uom, date, referenceModel);
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final I_M_Product product,
			final BigDecimal qty,
			final I_C_UOM uom,
			final Date date,
			final Object referenceModel)
	{
		final boolean forceQtyAllocation = false;
		return createQtyRequest(huContext, product, qty, uom, date, referenceModel, forceQtyAllocation);
	}

	/**
	 *
	 * @param huContext
	 * @param product
	 * @param qty
	 * @param uom
	 * @param date
	 * @param referenceModel May be <code>null</code>. Data record (e.g. M_receiptSchedule) from which the allocation originates.
	 * @param forceQtyAllocation true if we shall allocate the qty even if the destination is already full.
	 * @return
	 */
	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final I_M_Product product,
			final BigDecimal qty,
			final I_C_UOM uom,
			final Date date,
			final Object referenceModel,
			final boolean forceQtyAllocation)
	{
		final Quantity quantity = new Quantity(qty, uom);
		return createQtyRequest(huContext, product, quantity, date, referenceModel, forceQtyAllocation);
	}

	public static IAllocationRequest createQtyRequest(
			final IHUContext huContext,
			final I_M_Product product,
			final Quantity qty,
			final Date date,
			final Object referenceModel,
			final boolean forceQtyAllocation)
	{
		return createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(qty)
				.setDate(date)
				.setFromReferencedModel(referenceModel)
				.setForceQtyAllocation(forceQtyAllocation)
				.create();
	}

	public static IAllocationRequest createZeroQtyRequest(final IAllocationRequest request)
	{
		return createQtyRequest(request, request.getQuantity().toZero());
	}

	public static IAllocationRequest createQtyRequest(final IAllocationRequest request, final BigDecimal qty)
	{
		return createAllocationRequestBuilder()
				.setBaseAllocationRequest(request)
				.setQuantity(new Quantity(qty, request.getC_UOM()))
				.create();
	}

	public static IAllocationRequest createQtyRequest(final IAllocationRequest request, final Quantity qty)
	{
		return createAllocationRequestBuilder()
				.setBaseAllocationRequest(request)
				.setQuantity(qty)
				.create();
	}

	/**
	 * This method creates a new request that represents the portion of the given {@code request} that is not yet covered by the given {@code result}.
	 *
	 * @param request
	 * @param status
	 * @return
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
		return createAllocationRequestBuilder()
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
	 *
	 * @param to
	 * @param from
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
	 * @param qtyToAllocate
	 * @param qtyAllocated
	 * @param trxs
	 * @return {@link AllocationResult}
	 */
	public static IAllocationResult createQtyAllocationResult(final BigDecimal qtyToAllocate,
			final BigDecimal qtyAllocated,
			final List<IHUTransactionCandidate> trxs,
			final List<IHUTransactionAttribute> attributeTrxs
			)
	{
		return new AllocationResult(qtyToAllocate, qtyAllocated, trxs, attributeTrxs);
	}

	public static Object getReferencedModel(final IAllocationRequest request)
	{
		final ITableRecordReference tableRecord = request.getReference();
		if (tableRecord == null)
		{
			return null;
		}

		final IContextAware context = request.getHUContext();
		final Object referencedModel = tableRecord.getModel(context);
		return referencedModel;
	}

	/**
	 * Gets relative request qty.
	 *
	 * Relative qty is calculated as follows
	 * <ul>
	 * <li>if <code>outTrx</code> is true then qty negated
	 * <li>if <code>outTrx</code> is false then qty
	 * </ul>
	 *
	 * @param request
	 * @param outTrx true if is an outbound transaction
	 * @return relative qty
	 */
	public static Quantity getQuantity(final IAllocationRequest request, final boolean outTrx)
	{
		Check.assumeNotNull(request, "request not null");

		return request
				.getQuantity() // => Quantity (absolute)
				.negateIf(outTrx) // => Quantity (relative)
		;
	}

	/**
	 * Use this to create a new allocation request, using the given request as template.
	 *
	 * @param request
	 * @return
	 */
	public static IAllocationRequestBuilder derive(final IAllocationRequest request)
	{
		Check.assumeNotNull(request, "request not null");
		return createAllocationRequestBuilder()
				.setBaseAllocationRequest(request);
	}

	private static I_C_BPartner getC_BPartner(final IAllocationRequest request)
	{
		final Object referencedModel = AllocationUtils.getReferencedModel(request);
		if (referencedModel == null)
		{
			return null;
		}

		final Integer bpartnerId = InterfaceWrapperHelper.getValueOrNull(referencedModel, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID);
		if (bpartnerId == null || bpartnerId <= 0)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(referencedModel);
		final String trxName = InterfaceWrapperHelper.getTrxName(referencedModel);
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, trxName);
		return bpartner;
	}

	/**
	 * Creates and configures an {@link IHUBuilder} based on the given <code>request</code> (bPartner and date).
	 *
	 * @param request
	 * @return HU builder
	 */
	public static IHUBuilder createHUBuilder(final IAllocationRequest request)
	{
		final IHUContext huContext = request.getHUContext();
		final IHUBuilder huBuilder = Services.get(IHandlingUnitsDAO.class).createHUBuilder(huContext);

		huBuilder.setDate(request.getDate());

		final I_C_BPartner bpartner = getC_BPartner(request);
		huBuilder.setC_BPartner(bpartner);
		// TODO: huBuilder.setC_BPartner_Location if any

		// TODO: set the HU Storage from context to builder

		return huBuilder;
	}
}
