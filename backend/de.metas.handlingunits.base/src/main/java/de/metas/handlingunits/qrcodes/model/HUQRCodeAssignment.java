package de.metas.handlingunits.qrcodes.model;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.Check;
import de.metas.handlingunits.HuId;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class HUQRCodeAssignment
{
	@NonNull HUQRCodeUniqueId id;
	@NonNull Set<HuId> huIds;

	@NonNull
	public HuId getSingleHUId()
	{
		Check.assume(huIds.size() == 1, "Expecting only one HU assigned to qrCode=" + id.getAsString());

		return huIds.iterator().next();
	}

	public boolean isAssignedToHuId(@NonNull final HuId huId)
	{
		return huIds.contains(huId);
	}

	public Set<HuId> returnNotAssignedHUs(@NonNull final Set<HuId> huIdsToCheck)
	{
		return huIdsToCheck.stream()
				.filter(huId -> !isAssignedToHuId(huId))
				.collect(ImmutableSet.toImmutableSet());
	}
}
