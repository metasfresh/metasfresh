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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Value
public class MD_Candidate_StepDefTable
{
	@Singular
	ImmutableMap<String, MaterialDispoTableRow> rows;

	public MaterialDispoTableRow getRow(@NonNull final String identifier)
	{
		return rows.get(identifier);
	}

	public ImmutableCollection<MaterialDispoTableRow> getRows()
	{
		return rows.values();
	}

	@Value
	@Builder
	public static class MaterialDispoTableRow
	{
		@NonNull
		String identifier;

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

		public CandidatesQuery createQuery()
		{
			final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
					.productId(productId.getRepoId())
					.build();

			return CandidatesQuery.builder()
					.type(type)
					.businessCase(businessCase)
					.materialDescriptorQuery(materialDescriptorQuery)
					.build();
		}
	}
}
