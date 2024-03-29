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
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.function.ThrowingConsumer;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;

@Builder
@Value
public class MD_Candidate_StepDefTable
{
	@NonNull @Getter(AccessLevel.NONE) @Singular ImmutableMap<StepDefDataIdentifier, MaterialDispoTableRow> rows;

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

	@Value
	@Builder
	public static class MaterialDispoTableRow
	{
		@NonNull
		StepDefDataIdentifier identifier;

		@NonNull
		CandidateType type;

		@Nullable
		CandidateBusinessCase businessCase;

		@NonNull
		ProductId productId;

		@NonNull
		BigDecimal qty;

		@NonNull
		BigDecimal atp;

		@NonNull
		Instant time;

		@Nullable
		StepDefDataIdentifier attributeSetInstanceId;

		boolean simulated;

		@Nullable
		WarehouseId warehouseId;

		public CandidatesQuery createQuery()
		{
			final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
					.productId(productId.getRepoId())
					.storageAttributesKey(AttributesKey.ALL) // don't restrict on ASI for now; we might use the row's attributeSetInstanceId in this query at a later time
					.timeRangeEnd(DateAndSeqNo.builder()
							.date(time)
							.operator(DateAndSeqNo.Operator.INCLUSIVE)
							.build())
					.build();

			return CandidatesQuery.builder()
					.type(type)
					.businessCase(businessCase)
					.materialDescriptorQuery(materialDescriptorQuery)
					.simulatedQueryQualifier(this.simulated ? SimulatedQueryQualifier.ONLY_SIMULATED : SimulatedQueryQualifier.EXCLUDE_SIMULATED)
					.build();
		}
	}
}
