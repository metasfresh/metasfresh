package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PPOrderRef
{
	int ppOrderCandidateId;
	int ppOrderLineCandidateId;

	@Nullable PPOrderId ppOrderId;
	@Nullable PPOrderBOMLineId ppOrderBOMLineId;

	@Builder(toBuilder = true)
	@Jacksonized
	public PPOrderRef(
			final int ppOrderCandidateId,
			final int ppOrderLineCandidateId,
			@Nullable final PPOrderId ppOrderId,
			@Nullable final PPOrderBOMLineId ppOrderBOMLineId)
	{
		if (ppOrderCandidateId <= 0 && ppOrderId == null)
		{
			throw new AdempiereException("At least one of ppOrderCandidateId or ppOrderId shall be set");
		}

		this.ppOrderCandidateId = normalizeId(ppOrderCandidateId);
		this.ppOrderLineCandidateId = this.ppOrderCandidateId > 0 ? normalizeId(ppOrderLineCandidateId) : -1;
		this.ppOrderId = ppOrderId;
		this.ppOrderBOMLineId = this.ppOrderId != null ? ppOrderBOMLineId : null;
	}

	private static int normalizeId(final int id) {return id > 0 ? id : -1;}

	@Nullable
	public static PPOrderRef ofPPOrderCandidateIdOrNull(final int ppOrderCandidateId)
	{
		return ppOrderCandidateId > 0
				? builder().ppOrderCandidateId(ppOrderCandidateId).build()
				: null;
	}

	public static PPOrderRef ofPPOrderLineCandidateId(final int ppOrderCandidateId, final int ppOrderLineCandidateId)
	{
		Check.assume(ppOrderCandidateId > 0, "ppOrderCandidateId > 0");
		Check.assume(ppOrderLineCandidateId > 0, "ppOrderLineCandidateId > 0");
		return builder().ppOrderCandidateId(ppOrderCandidateId).ppOrderLineCandidateId(ppOrderLineCandidateId).build();
	}

	public static PPOrderRef ofPPOrderId(final int ppOrderId)
	{
		return ofPPOrderId(PPOrderId.ofRepoId(ppOrderId));
	}

	public static PPOrderRef ofPPOrderId(@NonNull final PPOrderId ppOrderId)
	{
		return builder().ppOrderId(ppOrderId).build();
	}

	public static PPOrderRef ofPPOrderBOMLineId(final int ppOrderId, final int ppOrderBOMLineId)
	{
		return ofPPOrderBOMLineId(PPOrderId.ofRepoId(ppOrderId), PPOrderBOMLineId.ofRepoId(ppOrderBOMLineId));
	}

	public static PPOrderRef ofPPOrderBOMLineId(@NonNull final PPOrderId ppOrderId, @NonNull final PPOrderBOMLineId ppOrderBOMLineId)
	{
		return builder().ppOrderId(ppOrderId).ppOrderBOMLineId(ppOrderBOMLineId).build();
	}

	public static PPOrderRef ofPPOrderBOMLineId(@NonNull final PPOrderAndBOMLineId ppOrderAndBOMLineId)
	{
		return ofPPOrderBOMLineId(ppOrderAndBOMLineId.getPpOrderId(), ppOrderAndBOMLineId.getLineId());
	}

	@Nullable
	public static PPOrderRef ofPPOrderAndLineIdOrNull(final int ppOrderRepoId, final int ppOrderBOMLineRepoId)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(ppOrderRepoId);
		if (ppOrderId == null)
		{
			return null;
		}

		return builder().ppOrderId(ppOrderId).ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(ppOrderBOMLineRepoId)).build();
	}

	public boolean isFinishedGoods()
	{
		return !isBOMLine();
	}

	public boolean isBOMLine()
	{
		return ppOrderBOMLineId != null;
	}

	public PPOrderRef withPPOrderAndBOMLineId(@Nullable final PPOrderAndBOMLineId newPPOrderAndBOMLineId)
	{
		final PPOrderId ppOrderIdNew = newPPOrderAndBOMLineId != null ? newPPOrderAndBOMLineId.getPpOrderId() : null;
		final PPOrderBOMLineId lineIdNew = newPPOrderAndBOMLineId != null ? newPPOrderAndBOMLineId.getLineId() : null;

		return PPOrderId.equals(this.ppOrderId, ppOrderIdNew)
				&& PPOrderBOMLineId.equals(this.ppOrderBOMLineId, lineIdNew)
				? this
				: toBuilder().ppOrderId(ppOrderIdNew).ppOrderBOMLineId(lineIdNew).build();
	}

	public PPOrderRef withPPOrderId(@Nullable final PPOrderId ppOrderId)
	{
		if (PPOrderId.equals(this.ppOrderId, ppOrderId))
		{
			return this;
		}

		return toBuilder().ppOrderId(ppOrderId).build();
	}

	@Nullable
	public static PPOrderRef withPPOrderId(@Nullable final PPOrderRef ppOrderRef, @Nullable final PPOrderId newPPOrderId)
	{
		if (ppOrderRef != null)
		{
			return ppOrderRef.withPPOrderId(newPPOrderId);
		}
		else if (newPPOrderId != null)
		{
			return ofPPOrderId(newPPOrderId);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	public static PPOrderRef withPPOrderAndBOMLineId(@Nullable final PPOrderRef ppOrderRef, @Nullable final PPOrderAndBOMLineId newPPOrderAndBOMLineId)
	{
		if (ppOrderRef != null)
		{
			return ppOrderRef.withPPOrderAndBOMLineId(newPPOrderAndBOMLineId);
		}
		else if (newPPOrderAndBOMLineId != null)
		{
			return ofPPOrderBOMLineId(newPPOrderAndBOMLineId);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	public PPOrderAndBOMLineId getPpOrderAndBOMLineId() {return PPOrderAndBOMLineId.ofNullable(ppOrderId, ppOrderBOMLineId);}
}
