package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;

@EqualsAndHashCode
@ToString
public final class ReservedHUsPolicy
{
	public static final ReservedHUsPolicy CONSIDER_ALL = new ReservedHUsPolicy(true, null);
	public static final ReservedHUsPolicy CONSIDER_ONLY_NOT_RESERVED = new ReservedHUsPolicy(false, null);

	public static ReservedHUsPolicy onlyNotReservedExceptVhuIds(@NonNull final Collection<HuId> vhuIdsToConsiderEvenIfReserved)
	{
		return vhuIdsToConsiderEvenIfReserved.isEmpty()
				? CONSIDER_ONLY_NOT_RESERVED
				: new ReservedHUsPolicy(false, vhuIdsToConsiderEvenIfReserved);
	}

	private final boolean considerReservedVHUs;
	private final ImmutableSet<HuId> vhuIdsToConsiderEvenIfReserved;

	public ReservedHUsPolicy(
			final boolean considerReservedVHUs,
			@Nullable final Collection<HuId> vhuIdsToConsiderEvenIfReserved)
	{
		this.considerReservedVHUs = considerReservedVHUs;
		this.vhuIdsToConsiderEvenIfReserved = vhuIdsToConsiderEvenIfReserved != null && !vhuIdsToConsiderEvenIfReserved.isEmpty()
				? ImmutableSet.copyOf(vhuIdsToConsiderEvenIfReserved)
				: ImmutableSet.of();
	}

	public boolean isConsiderVHU(@NonNull final I_M_HU vhu)
	{
		if (!vhu.isReserved())
		{
			return true;
		}

		final HuId reservedVHUId = HuId.ofRepoId(vhu.getM_HU_ID());

		return considerReservedVHUs
				|| (!vhuIdsToConsiderEvenIfReserved.isEmpty() && vhuIdsToConsiderEvenIfReserved.contains(reservedVHUId));
	}
}
