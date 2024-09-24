package de.metas.invoice;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class InvoiceMultiQuery
{
	@NonNull @Singular ImmutableSet<SingleInvoiceQuery> queries;
}
