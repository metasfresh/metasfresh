package de.metas.acct.api;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class DocumentPostMultiRequest implements Iterable<DocumentPostRequest>
{
	private final ImmutableSet<DocumentPostRequest> requests;

	private DocumentPostMultiRequest(@NonNull final ImmutableSet<DocumentPostRequest> requests)
	{
		Check.assumeNotEmpty(requests, "requests is not empty");
		this.requests = requests;
	}

	public static DocumentPostMultiRequest of(@NonNull final DocumentPostRequest request)
	{
		return new DocumentPostMultiRequest(ImmutableSet.of(request));
	}

	public static DocumentPostMultiRequest ofNonEmptyCollection(@NonNull final Collection<DocumentPostRequest> requests)
	{
		return new DocumentPostMultiRequest(ImmutableSet.copyOf(requests));
	}

	public static Collector<DocumentPostRequest, ?, Optional<DocumentPostMultiRequest>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(
				list -> !list.isEmpty() ? Optional.of(ofNonEmptyCollection(list)) : Optional.empty()
		);
	}

	public ImmutableSet<DocumentPostRequest> toSet() {return requests;}

	public Stream<DocumentPostRequest> stream() {return requests.stream();}

	@Override
	@NonNull
	public Iterator<DocumentPostRequest> iterator() {return requests.iterator();}
}
