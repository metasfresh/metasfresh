package de.metas.handlingunits.allocation.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

@ToString
/* package */class AllocationRequestBuilder implements IAllocationRequestBuilder
{
	private IAllocationRequest baseAllocationRequest = null;
	private IHUContext huContext = null;
	private ProductId productId = null;
	private Quantity quantity = null;
	private ZonedDateTime date = null;
	private Boolean forceQtyAllocation = null;
	private TableRecordReference fromReferencedTableRecord;
	private boolean fromReferencedTableRecordSet = false;
	private List<EmptyHUListener> emptyHUListeners = null;

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
		return setProduct(product != null ? ProductId.ofRepoId(product.getM_Product_ID()) : null);
	}

	@Override
	public IAllocationRequestBuilder setProduct(final ProductId productId)
	{
		this.productId = productId;
		return this;
	}

	public ProductId getProductIdToUse()
	{
		if (productId != null)
		{
			return productId;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.getProductId();
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
	public IAllocationRequestBuilder setDate(final ZonedDateTime date)
	{
		this.date = date;
		return this;
	}

	@Override
	public IAllocationRequestBuilder setDateAsToday()
	{
		return setDate(SystemTime.asZonedDateTime());
	}

	public ZonedDateTime getDateToUse()
	{
		if (date != null)
		{
			return date;
		}
		else if (baseAllocationRequest != null)
		{
			return baseAllocationRequest.getDate();
		}

		final ZonedDateTime contextDate = getHUContextToUse().getDate();
		if (contextDate != null)
		{
			return contextDate;
		}

		throw new AdempiereException("Date not set in " + this);
	}

	private TableRecordReference getFromReferencedTableRecordToUse()
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
	public IAllocationRequestBuilder setFromReferencedTableRecord(final TableRecordReference fromReferencedTableRecord)
	{
		this.fromReferencedTableRecord = fromReferencedTableRecord;
		fromReferencedTableRecordSet = true;

		return this;
	}

	@Override
	public IAllocationRequestBuilder setFromReferencedModel(final Object referenceModel)
	{
		final TableRecordReference fromReferencedTableRecord = TableRecordReference.ofOrNull(referenceModel);
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
		final ProductId productId = getProductIdToUse();
		final Quantity quantity = getQuantityToUse();
		final ZonedDateTime date = getDateToUse();
		final TableRecordReference fromTableRecord = getFromReferencedTableRecordToUse();
		final boolean forceQtyAllocation = isForceQtyAllocationToUse();

		final AllocationRequest request = new AllocationRequest(
				huContext,
				productId,
				quantity,
				date,
				fromTableRecord,
				forceQtyAllocation);
		return request;
	}
}
