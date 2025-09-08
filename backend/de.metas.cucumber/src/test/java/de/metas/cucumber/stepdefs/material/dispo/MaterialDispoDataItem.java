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

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Candidate model that consolidates a supply/demand candidate with the corresponding stock-candidate.
 * Right now i assume that this will eventually be the "normal" thing and replace the convoluted {@link Candidate}.
 */
@Value
@Builder
public class MaterialDispoDataItem
{
	@NonNull CandidateId candidateId;
	@NonNull CandidateId stockCandidateId;
	@NonNull CandidateType type;
	@Nullable CandidateBusinessCase businessCase;
	@NonNull MaterialDescriptor materialDescriptor;
	BigDecimal atp;
	BusinessCaseDetail businessCaseDetail;
	boolean simulated;

	public static MaterialDispoDataItem of(
			@NonNull final Candidate dataCandidate,
			@NonNull final Candidate stockCandidate)
	{
		return MaterialDispoDataItem.builder()
				.materialDescriptor(dataCandidate.getMaterialDescriptor())
				.candidateId(dataCandidate.getId())
				.stockCandidateId(stockCandidate.getId())
				.type(dataCandidate.getType())
				.businessCase(dataCandidate.getBusinessCase())
				.businessCaseDetail(dataCandidate.getBusinessCaseDetail())
				.atp(stockCandidate.getMaterialDescriptor().getQuantity())
				.simulated(dataCandidate.isSimulated())
				.build();
	}

	public <T extends BusinessCaseDetail> Optional<T> getBusinessCaseDetail(@NonNull final Class<T> type)
	{
		return type.isInstance(businessCaseDetail) ? Optional.of(type.cast(businessCaseDetail)) : Optional.empty();
	}

	public ProductId getProductId() {return ProductId.ofRepoId(materialDescriptor.getProductId());}

	public AttributeSetInstanceId getAttributeSetInstanceId() {return AttributeSetInstanceId.ofRepoIdOrNone(materialDescriptor.getAttributeSetInstanceId());}

	public Instant getDate() {return materialDescriptor.getDate();}

	public BigDecimal getQuantity() {return materialDescriptor.getQuantity();}
}
