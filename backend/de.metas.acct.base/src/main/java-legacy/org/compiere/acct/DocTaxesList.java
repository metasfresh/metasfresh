package org.compiere.acct;

import com.google.common.collect.ImmutableSet;
import de.metas.tax.api.TaxId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

class DocTaxesList implements Iterable<DocTax>
{
	private final HashMap<TaxId, DocTax> taxesByTaxId;

	public DocTaxesList(@NonNull final List<DocTax> list)
	{
		this.taxesByTaxId = list.stream()
				.collect(GuavaCollectors.toHashMapByKey(DocTax::getTaxId));
	}

	@Override
	public Iterator<DocTax> iterator()
	{
		return taxesByTaxId.values().iterator();
	}

	public Optional<DocTax> getByTaxId(@Nullable final TaxId taxId)
	{
		if (taxId == null)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(taxesByTaxId.get(taxId));
	}

	public void mapEach(@NonNull final UnaryOperator<DocTax> mapper)
	{
		final ImmutableSet<TaxId> taxIds = ImmutableSet.copyOf(taxesByTaxId.keySet()); // IMPORTANT: take a snapshot
		for (final TaxId taxId : taxIds)
		{
			taxesByTaxId.compute(taxId, (k, docTax) -> mapper.apply(docTax));
		}
	}

	public void add(@NonNull final DocTax docTax)
	{
		final TaxId taxId = docTax.getTaxId();
		final DocTax existingDocTax = taxesByTaxId.get(taxId);
		if (existingDocTax != null)
		{
			throw new AdempiereException("Cannot add " + docTax + " since there is already a tax: " + existingDocTax);
		}
		taxesByTaxId.put(taxId, docTax);
	}
}
