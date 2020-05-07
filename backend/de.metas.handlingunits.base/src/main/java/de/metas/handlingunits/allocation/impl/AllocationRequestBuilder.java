package de.metas.handlingunits.allocation.impl;

import java.util.ArrayList;

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
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/* package */class AllocationRequestBuilder implements IAllocationRequestBuilder
{
	private IAllocationRequest baseAllocationRequest = null;
	private IHUContext huContext = null;
	private I_M_Product product = null;
	private Quantity quantity = null;
	private Date date = null;
	private Boolean forceQtyAllocation = null;
	private ITableRecordReference fromReferencedTableRecord;
	private boolean fromReferencedTableRecordSet = false;
	private List<EmptyHUListener> emptyHUListeners = null;

	@Override
	public String toString()
	{
		return "AllocationRequestBuilder ["
				+ "baseAllocationRequest=" + baseAllocationRequest
				+ ", product=" + product
				+ ", quantity=" + quantity
				+ ", date=" + date
				+ ", fromReferencedTableRecord=" + fromReferencedTableRecord
				+ ", forceQtyAllocation=" + forceQtyAllocation
				+ ", huContext=" + huContext
				+ "]";
	}

	@Override
	public IAllocationRequestBuilder setBaseAllocationRequest(final IAllocationRequest baseAllocationRequest)
	{
		this.baseAllocationRequest = baseAllocationRequest;
		return this;
	}

	@Override
	public IAllocationRequestBuilder setHUContext(final IHUContext huContext)
	{
		this.huContext = huContext;
		return this;
	}

	public IHUContext getHUContextToUse()
	{
		if (huContext != null)
		{
			if (emptyHUListeners != null && !emptyHUListeners.isEmpty())
			{
				Check.assumeInstanceOf(huContext, IMutableHUContext.class, "huContext");
				final IMutableHUContext mutableHUContext = (IMutableHUContext)huContext;
				emptyHUListeners.forEach(mutableHUContext::addEmptyHUListener);
			}
			return huContext;
		}
		else if (baseAllocationRequest != null)
		{
			Check.assume(emptyHUListeners == null || emptyHUListeners.isEmpty(), "emptyHUListeners shall be empty");
			return baseAllocationRequest.getHUContext();
		}
		else
		{
			throw new AdempiereException("HUContext not set in " + this);
		}
	}

	@Override
	public IAllocationRequestBuilder setProduct(final I_M_Product product)
	{
		this.product = product;
		return this;
	}

	public I_M_Product getProductToUse()
	{
		if (product != null)
		{
			return product;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.getProduct();
		}

		throw new AdempiereException("Product not set in " + this);
	}

	@Override
	public IAllocationRequestBuilder setQuantity(final Quantity quantity)
	{
		this.quantity = quantity;
		return this;
	}

	private Quantity getQuantityToUse()
	{
		if (quantity != null)
		{
			// In case the quantity is same as in our base allocation, use that (in this way we preserve source Qty/UOM if any)
			if (baseAllocationRequest != null && baseAllocationRequest.getQuantity().equalsIgnoreSource(quantity))
			{
				return baseAllocationRequest.getQuantity();
			}

			return quantity;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.getQuantity();
		}

		throw new AdempiereException("Quantity not set in " + this);
	}

	@Override
	public IAllocationRequestBuilder setDate(final Date date)
	{
		this.date = date;
		return this;
	}

	@Override
	public IAllocationRequestBuilder setDateAsToday()
	{
		date = SystemTime.asDate();
		return this;
	}

	public Date getDateToUse()
	{
		if (date != null)
		{
			return date;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.getDate();
		}

		throw new AdempiereException("Date not set in " + this);
	}

	private ITableRecordReference getFromReferencedTableRecordToUse()
	{
		if (fromReferencedTableRecordSet)
		{
			return fromReferencedTableRecord;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.getReference();
		}

		throw new AdempiereException("Referenced Table/Record not set in " + this);
	}

	@Override
	public IAllocationRequestBuilder setFromReferencedTableRecord(final ITableRecordReference fromReferencedTableRecord)
	{
		this.fromReferencedTableRecord = fromReferencedTableRecord;
		fromReferencedTableRecordSet = true;

		return this;
	}

	@Override
	public IAllocationRequestBuilder setFromReferencedModel(final Object referenceModel)
	{
		final ITableRecordReference fromReferencedTableRecord = TableRecordReference.ofOrNull(referenceModel);
		return setFromReferencedTableRecord(fromReferencedTableRecord);
	}

	@Override
	public IAllocationRequestBuilder setForceQtyAllocation(final Boolean forceQtyAllocation)
	{
		this.forceQtyAllocation = forceQtyAllocation;
		return this;
	}

	private boolean isForceQtyAllocationToUse()
	{
		if (forceQtyAllocation != null)
		{
			return forceQtyAllocation;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.isForceQtyAllocation();
		}

		throw new AdempiereException("ForceQtyAllocation not set in " + this);
	}

	@Override
	public IAllocationRequestBuilder addEmptyHUListener(@NonNull final EmptyHUListener emptyHUListener)
	{
		if (emptyHUListeners == null)
		{
			emptyHUListeners = new ArrayList<>();
		}
		emptyHUListeners.add(emptyHUListener);
		return this;
	}

	@Override
	public IAllocationRequest create()
	{
		final IHUContext huContext = getHUContextToUse();
		final I_M_Product product = getProductToUse();
		final Quantity quantity = getQuantityToUse();
		final Date date = getDateToUse();
		final ITableRecordReference fromTableRecord = getFromReferencedTableRecordToUse();
		final boolean forceQtyAllocation = isForceQtyAllocationToUse();

		final AllocationRequest request = new AllocationRequest(
				huContext,
				product,
				quantity,
				date,
				fromTableRecord,
				forceQtyAllocation);
		return request;
	}
}
