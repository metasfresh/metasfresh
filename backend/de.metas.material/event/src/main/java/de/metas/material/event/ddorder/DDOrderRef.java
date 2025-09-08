package de.metas.material.event.ddorder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class DDOrderRef
{
	int ddOrderCandidateId;
	int ddOrderId;
	int ddOrderLineId;

	@Builder(toBuilder = true)
	@Jacksonized
	private DDOrderRef(
			final int ddOrderCandidateId,
			final int ddOrderId,
			final int ddOrderLineId)
	{
		if (ddOrderCandidateId <= 0 && ddOrderId <= 0)
		{
			throw new AdempiereException("At least one of ddOrderCandidateId or ddOrderId shall be set");
		}

		this.ddOrderCandidateId = normalizeId(ddOrderCandidateId);
		this.ddOrderId = normalizeId(ddOrderId);
		this.ddOrderLineId = normalizeId(this.ddOrderId > 0 ? ddOrderLineId : -1);
	}

	private static int normalizeId(final int id) {return id > 0 ? id : -1;}

	public static class DDOrderRefBuilder
	{
		@Nullable
		public DDOrderRef buildOrNull()
		{
			if (ddOrderCandidateId <= 0 && ddOrderId <= 0)
			{
				return null;
			}

			return build();
		}

	}

	@Nullable
	public static DDOrderRef ofNullableDDOrderAndLineId(final int ddOrderId, final int ddOrderLineId)
	{
		return builder().ddOrderId(ddOrderId).ddOrderLineId(ddOrderLineId).buildOrNull();
	}

	public static DDOrderRef ofNullableDDOrderCandidateId(final int ddOrderCandidateId)
	{
		return ddOrderCandidateId > 0
				? builder().ddOrderCandidateId(ddOrderCandidateId).build()
				: null;
	}

	public DDOrderRef withDdOrderCandidateId(final int ddOrderCandidateIdNew)
	{
		final int ddOrderCandidateIdNewNorm = normalizeId(ddOrderCandidateIdNew);
		return this.ddOrderCandidateId != ddOrderCandidateIdNewNorm
				? toBuilder().ddOrderCandidateId(ddOrderCandidateIdNewNorm).build()
				: this;
	}

	public static DDOrderRef withDdOrderCandidateId(@Nullable final DDOrderRef ddOrderRef, final int ddOrderCandidateIdNew)
	{
		return ddOrderRef != null
				? ddOrderRef.withDdOrderCandidateId(ddOrderCandidateIdNew)
				: ofNullableDDOrderCandidateId(ddOrderCandidateIdNew);
	}
}
