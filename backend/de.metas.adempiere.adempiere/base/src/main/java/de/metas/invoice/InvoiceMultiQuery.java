package de.metas.invoice;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
public class InvoiceMultiQuery
{
	@NonNull ImmutableSet<InvoiceQuery> queries;

	@Builder
	private InvoiceMultiQuery(@NonNull @Singular final Set<InvoiceQuery> queries)
	{
		Check.assumeNotEmpty(queries, "At least one query shall be provided");
		this.queries = ImmutableSet.copyOf(queries);
	}
}
