package de.metas.util.lang;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class ExternalHeaderIdWithExternalLineIds
{
	@NonNull
	ExternalId externalHeaderId;

	/** May be empty. */
	@NonNull
	ImmutableList<ExternalId> externalLineIds;

	@Builder
	private ExternalHeaderIdWithExternalLineIds(
			@NonNull final ExternalId externalHeaderId,
			@NonNull @Singular final List<ExternalId> externalLineIds)
	{
		this.externalHeaderId = externalHeaderId;
		this.externalLineIds = ImmutableList.copyOf(externalLineIds);
	}
	
	@NonNull
	public ImmutableSet<String> getExternalLineIdsAsString()
	{
		return getExternalLineIds()
				.stream()
				.map(ExternalId::toValue)
				.filter(Check::isNotBlank)
				.collect(ImmutableSet.toImmutableSet());
	}
}
