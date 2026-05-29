package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;

@EqualsAndHashCode
@ToString
public final class ReservedHUsPolicy
{
	public static final ReservedHUsPolicy CONSIDER_ALL = builder().vhuReservedStatus(OptionalBoolean.UNKNOWN).build();
	public static final ReservedHUsPolicy CONSIDER_ONLY_NOT_RESERVED = builder().vhuReservedStatus(OptionalBoolean.FALSE).build();

	/**
	 * Creates a policy that excludes reserved VHUs but makes an exception for the given IDs.
	 * <p>
	 * <b>Important:</b> Because this policy has {@code vhuReservedStatus=FALSE},
	 * {@link #requiresRecursiveReservationGuard()} returns {@code false}, which skips the recursive
	 * reservation guard in {@link HUTransformService#husToNewCUs}.
	 * Callers must therefore also populate {@link HUTransformService#allowedReservedVhuIds}
	 * with the same IDs to ensure the direct guard ({@code assertNotReserved}) is also bypassed.
	 */
	public static ReservedHUsPolicy onlyNotReservedExceptVhuIds(@NonNull final Collection<HuId> vhuIdsToConsiderEvenIfReserved)
	{
		return vhuIdsToConsiderEvenIfReserved.isEmpty()
				? CONSIDER_ONLY_NOT_RESERVED
				: builder().vhuReservedStatus(OptionalBoolean.FALSE).alwaysConsiderVhuIds(vhuIdsToConsiderEvenIfReserved).build();
	}

	public static ReservedHUsPolicy onlyVHUIds(@NonNull final Collection<HuId> onlyVHUIds)
	{
		Check.assumeNotEmpty(onlyVHUIds, "onlyVHUIds shall not be empty");
		return builder().vhuReservedStatus(OptionalBoolean.UNKNOWN).onlyConsiderVhuIds(onlyVHUIds).build();
	}

	private final OptionalBoolean vhuReservedStatus;
	private final ImmutableSet<HuId> alwaysConsiderVhuIds;
	private final ImmutableSet<HuId> onlyConsiderVhuIds;

	@Builder
	private ReservedHUsPolicy(
			final OptionalBoolean vhuReservedStatus,
			@Nullable final Collection<HuId> alwaysConsiderVhuIds,
			@Nullable final Collection<HuId> onlyConsiderVhuIds)
	{
		this.vhuReservedStatus = vhuReservedStatus;
		this.alwaysConsiderVhuIds = alwaysConsiderVhuIds != null ? ImmutableSet.copyOf(alwaysConsiderVhuIds) : ImmutableSet.of();
		this.onlyConsiderVhuIds = onlyConsiderVhuIds != null ? ImmutableSet.copyOf(onlyConsiderVhuIds) : ImmutableSet.of();
	}

	public boolean isConsiderVHU(@NonNull final I_M_HU vhu)
	{
		final HuId vhuId = HuId.ofRepoId(vhu.getM_HU_ID());
		if (!onlyConsiderVhuIds.isEmpty() && !onlyConsiderVhuIds.contains(vhuId))
		{
			return false;
		}

		if (alwaysConsiderVhuIds.contains(vhuId))
		{
			return true;
		}

		return isHUReservedStatusMatches(vhu);
	}

	private boolean isHUReservedStatusMatches(@NonNull final I_M_HU vhu)
	{
		return vhuReservedStatus.isUnknown() || vhuReservedStatus.isTrue() == vhu.isReserved();
	}

	/**
	 * @return true if this policy would potentially process reserved VHUs
	 *         (i.e., it does NOT explicitly exclude reserved VHUs).
	 *         Used by {@link HUTransformService} to decide whether a recursive reservation-guard check is needed.
	 *         <p>
	 *         <b>Note:</b> This method only inspects {@code vhuReservedStatus} — it does NOT consider
	 *         {@link #alwaysConsiderVhuIds}. A policy created via {@link #onlyNotReservedExceptVhuIds}
	 *         returns {@code false} here even though it <em>can</em> process specific reserved VHUs.
	 *         Those VHUs must also be listed in {@link HUTransformService}'s {@code allowedReservedVhuIds}
	 *         so they pass the per-method guards.
	 */
	public boolean requiresRecursiveReservationGuard()
	{
		return vhuReservedStatus.isUnknown() || vhuReservedStatus.isTrue();
	}
}
