/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.material.dispo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

@Value
public class MD_Candidate_StepDefTable
{
	@NonNull @Getter(AccessLevel.NONE) ImmutableMap<StepDefDataIdentifier, MaterialDispoTableRow> rows;

	private MD_Candidate_StepDefTable(@NonNull final List<MaterialDispoTableRow> rows)
	{
		this.rows = Maps.uniqueIndex(rows, MaterialDispoTableRow::getIdentifier);
	}

	public static Collector<MaterialDispoTableRow, ?, MD_Candidate_StepDefTable> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(MD_Candidate_StepDefTable::new);
	}

	public int size() {return rows.size();}

	public Stream<MaterialDispoTableRow> stream() {return rows.values().stream();}

	public ImmutableSet<ProductId> getProductIds()
	{
		return stream()
				.map(MaterialDispoTableRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void forEach(@NonNull final ThrowingConsumer<MaterialDispoTableRow> consumer) throws Throwable
	{
		for (final Map.Entry<StepDefDataIdentifier, MaterialDispoTableRow> entry : rows.entrySet())
		{
			final StepDefDataIdentifier identifier = entry.getKey();
			final MaterialDispoTableRow row = entry.getValue();

			SharedTestContext.run(() -> {
				SharedTestContext.put("rowIdentifier", identifier);
				SharedTestContext.put("row", row);

				consumer.accept(row);
			});
		}
	}
}
