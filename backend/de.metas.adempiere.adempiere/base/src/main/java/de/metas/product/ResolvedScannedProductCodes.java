package de.metas.product;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class ResolvedScannedProductCodes implements Iterable<ResolvedScannedProductCode>
{
	@NonNull private final ImmutableSet<ResolvedScannedProductCode> set;

	private ResolvedScannedProductCodes(@NonNull final ImmutableSet<ResolvedScannedProductCode> set)
	{
		this.set = Check.assumeNotEmpty(set, "set is not empty");
	}

	public static ResolvedScannedProductCodes ofCollection(@NonNull final Collection<ResolvedScannedProductCode> collection)
	{
		return new ResolvedScannedProductCodes(ImmutableSet.copyOf(collection));
	}

	public static Optional<ResolvedScannedProductCodes> optionalOfCollection(@Nullable final Collection<ResolvedScannedProductCode> collection)
	{
		return collection != null && !collection.isEmpty() ? Optional.of(ofCollection(collection)) : Optional.empty();
	}

	public static ResolvedScannedProductCodes ofSingleCode(@NonNull final ResolvedScannedProductCode code)
	{
		return new ResolvedScannedProductCodes(ImmutableSet.of(code));
	}

	@Override
	public @NonNull Iterator<ResolvedScannedProductCode> iterator()
	{
		return set.iterator();
	}
}
