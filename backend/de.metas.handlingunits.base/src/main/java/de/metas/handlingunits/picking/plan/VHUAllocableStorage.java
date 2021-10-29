package de.metas.handlingunits.picking.plan;

import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@ToString
class VHUAllocableStorage
{
	@Getter private final int seqNo;
	@NonNull private final ProductId productId;
	@NonNull private Quantity qtyFreeToAllocate;
	@Nullable private final HUReservationDocRef reservationDocRef;

	VHUAllocableStorage(
			final int seqNo,
			final @NonNull ProductId productId,
			final @NonNull Quantity qtyFreeToAllocate,
			final @Nullable HUReservationDocRef reservationDocRef)
	{
		this.seqNo = seqNo;
		this.productId = productId;
		this.qtyFreeToAllocate = qtyFreeToAllocate.toZeroIfNegative();
		this.reservationDocRef = reservationDocRef;
	}

	public Quantity allocate(@NonNull final AllocablePackageable allocable)
	{
		return allocate(allocable, allocable.getQtyToAllocate());
	}

	public Quantity allocate(@NonNull final AllocablePackageable allocable, @NonNull final Quantity qtyToAllocate)
	{
		// Nothing required to allocate
		if (qtyToAllocate.isZero())
		{
			return qtyToAllocate.toZero();
		}

		//
		// In case this VHU storage is reserved for some other document
		// then don't touch it
		if (isReserved() && !isReservedOnlyFor(allocable))
		{
			return qtyToAllocate.toZero();
		}

		final Quantity qtyToAllocateEffective = computeEffectiveQtyToAllocate(qtyToAllocate);
		forceAllocate(allocable, qtyToAllocateEffective);
		return qtyToAllocateEffective;
	}

	public void forceAllocate(final AllocablePackageable allocable, final Quantity qtyToAllocate)
	{
		assertSameProductId(allocable);
		qtyFreeToAllocate = qtyFreeToAllocate.subtract(qtyToAllocate);
		allocable.allocateQty(qtyToAllocate);
	}

	private boolean isReserved()
	{
		return reservationDocRef != null;
	}

	public boolean isReservedOnlyFor(@NonNull final AllocablePackageable allocable)
	{
		return reservationDocRef != null && HUReservationDocRef.equals(reservationDocRef, allocable.getReservationRef().orElse(null));
	}

	private void assertSameProductId(final AllocablePackageable allocable)
	{
		if (!ProductId.equals(productId, allocable.getProductId()))
		{
			throw new AdempiereException("ProductId not matching")
					.appendParametersToMessage()
					.setParameter("allocable", allocable)
					.setParameter("storage", this);
		}
	}

	private Quantity computeEffectiveQtyToAllocate(@NonNull final Quantity requestedQtyToAllocate)
	{
		if (requestedQtyToAllocate.signum() <= 0)
		{
			return requestedQtyToAllocate.toZero();
		}
		if (qtyFreeToAllocate.signum() <= 0)
		{
			return requestedQtyToAllocate.toZero();
		}

		return requestedQtyToAllocate.min(qtyFreeToAllocate);
	}
}
