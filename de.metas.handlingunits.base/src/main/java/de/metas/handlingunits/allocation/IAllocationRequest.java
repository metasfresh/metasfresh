package de.metas.handlingunits.allocation;

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

import org.adempiere.uom.api.Quantity;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;

/**
 * Allocation Request described what we need to allocate/deallocate. Implementors are immutable. In stead of modifying it (split, create partial requests etc), use {@link IAllocationRequestBuilder} to
 * get new instances.
 *
 * @author tsa
 *
 */
public interface IAllocationRequest
{
	BigDecimal QTY_INFINITE = Quantity.QTY_INFINITE;

	IHUContext getHUContext();

	/**
	 * @return allocation date
	 */
	Date getDate();

	I_M_Product getProduct();

	BigDecimal getQty();

	/**
	 *
	 * @return true if this request is asking about infinite quantity
	 */
	boolean isInfiniteQty();

	/**
	 *
	 * @return true if this request is asking about ZERO quantity
	 */
	boolean isZeroQty();

	I_C_UOM getC_UOM();

	/**
	 *
	 * @return quantity/uom (source quantity/uom)
	 */
	Quantity getQuantity();

	/**
	 * Gets referenced model.
	 *
	 * In case you are doing allocations/deallocations, creating new HUs, the Qty changes or newly created HUs can be linked to this model. This is done in
	 * {@link de.metas.handlingunits.IHUTrxListener} implementations and those implementation decide when and how this is made.
	 *
	 * @return referenced model (e.g. a document line)
	 */
	ITableRecordReference getReference();

	/**
	 *
	 * @return <code>true</code> if we shall allocate the qty even if the destination is already full
	 */
	boolean isForceQtyAllocation();
}
