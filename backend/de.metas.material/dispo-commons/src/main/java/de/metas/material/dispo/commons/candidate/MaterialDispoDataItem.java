/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.dispo.commons.candidate;

import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Candidate model that consolidates a supply/demand candidate with the corresponding stock-candidate.
 * Right now i assume that this will eventually be the "normal" thing and replace the convoluted {@link Candidate}.
 */
@Value
@Builder
public class MaterialDispoDataItem
{
	@NonNull
	CandidateType type;

	@Nullable
	CandidateBusinessCase businessCase;

	@NonNull
	MaterialDescriptor materialDescriptor;

	BigDecimal atp;

	BusinessCaseDetail businessCaseDetail;

	public static MaterialDispoDataItem of(
			@NonNull final Candidate dataCanddiate,
			@NonNull final Candidate stockCandidate)
	{
		return MaterialDispoDataItem.builder()
				.materialDescriptor(dataCanddiate.getMaterialDescriptor())
				.type(dataCanddiate.getType())
				.businessCase(dataCanddiate.getBusinessCase())
				.businessCaseDetail(dataCanddiate.getBusinessCaseDetail())
				.atp(stockCandidate.getMaterialDescriptor().getQuantity())
				.build();
	}
}
