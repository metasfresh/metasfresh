package de.metas.handlingunits.picking.plan.generator.allocableHUStorages;

import de.metas.handlingunits.picking.plan.generator.AllocablePackageable;
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

	public Quantity allocate(
			@NonNull final AllocablePackageable allocable,
			@NonNull final Quantity requestedQtyToAllocate)
	{
		// Nothing requested to allocate
		if (requestedQtyToAllocate.isZero())
		{
			return requestedQtyToAllocate.toZero();
		}

		final Quantity qtyFreeToAllocateEffective = getQtyFreeToAllocateFor(allocable.getReservationRef().orElse(null));
		final Quantity qtyToAllocateEffective = computeEffectiveQtyToAllocate(requestedQtyToAllocate, qtyFreeToAllocateEffective);
		forceAllocate(allocable, qtyToAllocateEffective);
		return qtyToAllocateEffective;
	}

	public void forceAllocate(final AllocablePackageable allocable, final Quantity qtyToAllocate)
	{
		assertSameProductId(allocable);
		qtyFreeToAllocate = qtyFreeToAllocate.subtract(qtyToAllocate);
		allocable.allocateQty(qtyToAllocate);
	}

	public boolean hasQtyFreeToAllocateFor(@Nullable final HUReservationDocRef reservationDocRef)
	{
		return getQtyFreeToAllocateFor(reservationDocRef).isPositive();
	}

	public Quantity getQtyFreeToAllocateFor(@Nullable final HUReservationDocRef reservationDocRef)
	{
		return isReserved() && !isReservedOnlyFor(reservationDocRef)
				? qtyFreeToAllocate.toZero()
				: qtyFreeToAllocate;
	}

	private boolean isReserved()
	{
		return reservationDocRef != null;
	}

	public boolean isReservedOnlyFor(@NonNull final AllocablePackageable allocable)
	{
		return isReservedOnlyFor(allocable.getReservationRef().orElse(null));
	}

	public boolean isReservedOnlyFor(@Nullable final HUReservationDocRef reservationDocRef)
	{
		return this.reservationDocRef != null
				&& HUReservationDocRef.equals(this.reservationDocRef, reservationDocRef);
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

	private static Quantity computeEffectiveQtyToAllocate(
			@NonNull final Quantity requestedQtyToAllocate,
			@NonNull final Quantity qtyFreeToAllocate)
	{
		if (requestedQtyToAllocate.signum() <= 0)
		{
			return requestedQtyToAllocate.toZero();
		}
		else if (qtyFreeToAllocate.signum() <= 0)
		{
			return requestedQtyToAllocate.toZero();
		}
		else
		{
			return requestedQtyToAllocate.min(qtyFreeToAllocate);
		}
	}
}
