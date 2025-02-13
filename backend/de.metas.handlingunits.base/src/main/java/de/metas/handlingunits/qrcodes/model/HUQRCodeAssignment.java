package de.metas.handlingunits.qrcodes.model;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.Check;
import de.metas.handlingunits.HuId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.Set;

@Value
public class HUQRCodeAssignment
{
	@NonNull
	public static HUQRCodeAssignment of(@NonNull final HUQRCodeUniqueId id, @NonNull final Collection<HuId> huIds)
	{
		return new HUQRCodeAssignment(id, huIds);
	}

	@NonNull HUQRCodeUniqueId id;
	@NonNull ImmutableSet<HuId> huIds;

	private HUQRCodeAssignment(@NonNull final HUQRCodeUniqueId id, @NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			throw new AdempiereException("huIds cannot be empty!")
					.appendParametersToMessage()
					.setParameter("HUQRCodeUniqueId", id);
		}

		this.id = id;
		this.huIds = ImmutableSet.copyOf(huIds);
	}

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

	@NonNull
	public ImmutableSet<HuId> returnNotAssignedHUs(@NonNull final Set<HuId> huIdsToCheck)
	{
		return huIdsToCheck.stream()
				.filter(huId -> !isAssignedToHuId(huId))
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isSingleHUAssigned()
	{
		return huIds.size() == 1;
	}
}
