package de.metas.util.lang;

import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
public class ExternalHeaderIdWithExternalLineIds
{
	ExternalId externalHeaderId;

	/** May be empty. */
	ImmutableList<ExternalId> externalLineIds;

	@Builder(toBuilder = true)
	private ExternalHeaderIdWithExternalLineIds(
			@NonNull final ExternalId externalHeaderId,
			@NonNull @Singular final List<ExternalId> externalLineIds)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineIds = ImmutableList.copyOf(externalLineIds);
	}
}
