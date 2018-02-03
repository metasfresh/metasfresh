package de.metas.handlingunits.allocation;

import java.math.BigDecimal;

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

import java.util.Date;

import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.quantity.Quantity;

/**
 * {@link IAllocationRequest} builder. Use it to create modified instances of your immutable {@link IAllocationRequest}.
 *
 * @author tsa
 * @see AllocationUtils#createAllocationRequestBuilder()
 * @see AllocationUtils#derive(IAllocationRequest)
 *
 */
public interface IAllocationRequestBuilder
{
	/**
	 * Create the new allocation request
	 *
	 * @return newly create allocation request
	 */
	IAllocationRequest create();

	/**
	 * Sets base {@link IAllocationRequest}.
	 *
	 * When building the new allocation request, if there are some values which were not set then those values are fetched from this allocation.
	 *
	 * @param baseAllocationRequest
	 * @return this
	 */
	IAllocationRequestBuilder setBaseAllocationRequest(final IAllocationRequest baseAllocationRequest);

	IAllocationRequestBuilder setHUContext(IHUContext huContext);

	IAllocationRequestBuilder setProduct(final I_M_Product product);

	IAllocationRequestBuilder setQuantity(Quantity quantity);

	default IAllocationRequestBuilder setQuantity(final BigDecimal qty, final I_C_UOM uom)
	{
		setQuantity(Quantity.of(qty, uom));
		return this;
	}

	IAllocationRequestBuilder setDate(final Date date);

	IAllocationRequestBuilder setDateAsToday();

	/**
	 *
	 * @param forceQtyAllocation if null, the actual value will be fetched from base allocation request (if any)
	 * @return this
	 */
	IAllocationRequestBuilder setForceQtyAllocation(final Boolean forceQtyAllocation);

	/**
	 * Sets referenced model to be set to {@link IAllocationRequest} which will be created.
	 *
	 * @param referenceModel model, {@link ITableRecordReference} or null
	 * @return this
	 */
	IAllocationRequestBuilder setFromReferencedModel(Object referenceModel);

	/**
	 * Sets referenced model to be set to {@link IAllocationRequest} which will be created.
	 *
	 * @param referenceModel {@link ITableRecordReference} or null
	 * @return this
	 */
	IAllocationRequestBuilder setFromReferencedTableRecord(ITableRecordReference fromReferencedTableRecord);
}
