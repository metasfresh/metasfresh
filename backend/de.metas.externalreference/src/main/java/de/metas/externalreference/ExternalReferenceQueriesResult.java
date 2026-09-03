package de.metas.externalreference;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class ExternalReferenceQueriesResult
{
	private static final ExternalReferenceQueriesResult EMPTY = new ExternalReferenceQueriesResult(ImmutableMap.of());

	private final ImmutableMap<ExternalReferenceQuery, Optional<ExternalReference>> map;

	private ExternalReferenceQueriesResult(final Map<ExternalReferenceQuery, Optional<ExternalReference>> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	public static ExternalReferenceQueriesResult ofMap(final Map<ExternalReferenceQuery, Optional<ExternalReference>> map)
	{
		return map.isEmpty() ? EMPTY : new ExternalReferenceQueriesResult(map);
	}

	public Optional<ExternalReference> get(final ExternalReferenceQuery query)
	{
		final Optional<ExternalReference> optionalReference = map.get(query);
		return optionalReference != null ? optionalReference : Optional.empty();
	}
}
