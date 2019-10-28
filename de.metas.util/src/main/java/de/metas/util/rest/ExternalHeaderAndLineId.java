package de.metas.util.rest;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
public class ExternalHeaderAndLineId
{
	private ExternalId externalHeaderId;

	private ImmutableList<ExternalId> externalLineIds;

	@Builder(toBuilder = true)
	private ExternalHeaderAndLineId(
			@NonNull @Singular final List<ExternalId> externalLineIds,
			@NonNull final ExternalId externalHeaderId)
	{
		this.externalLineIds = ImmutableList.copyOf(externalLineIds);
		this.externalHeaderId = externalHeaderId;
	}
}
