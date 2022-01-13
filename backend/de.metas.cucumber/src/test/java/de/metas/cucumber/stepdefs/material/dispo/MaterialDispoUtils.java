/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@UtilityClass
public class MaterialDispoUtils
{
	@NonNull
	public StockEstimateDeletedEvent createStockEstimateDeletedEvent(
			final int productId,
			final int freshQtyOnHandId,
			final int freshQtyOnHandLineId,
			@NonNull final Instant dateDoc,
			@NonNull final BigDecimal qty)
	{
		final MaterialDescriptor descriptor = createMaterialDescriptor(productId, dateDoc, qty);

		return StockEstimateDeletedEvent.builder()
				.date(dateDoc)
				.eventDate(Instant.now())
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ClientId.METASFRESH, StepDefConstants.ORG_ID))
				.materialDescriptor(descriptor)
				.plantId(StepDefConstants.PLANT_ID.getRepoId())
				.freshQtyOnHandId(freshQtyOnHandId)
				.freshQtyOnHandLineId(freshQtyOnHandLineId)
				.build();
	}

	@NonNull
	public StockEstimateCreatedEvent createStockEstimateCreatedEvent(
			final int productId,
			final int freshQtyOnHandId,
			final int freshQtyOnHandLineId,
			@NonNull final Instant dateDoc,
			@NonNull final BigDecimal qty)
	{
		final MaterialDescriptor descriptor = createMaterialDescriptor(productId, dateDoc, qty);

		return StockEstimateCreatedEvent.builder()
				.date(dateDoc)
				.eventDate(Instant.now())
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ClientId.METASFRESH, StepDefConstants.ORG_ID))
				.materialDescriptor(descriptor)
				.plantId(StepDefConstants.PLANT_ID.getRepoId())
				.freshQtyOnHandId(freshQtyOnHandId)
				.freshQtyOnHandLineId(freshQtyOnHandLineId)
				.build();
	}

	@NonNull
	public MaterialDescriptor createMaterialDescriptor(
			final int productId,
			@NonNull final Instant dateDoc,
			@NonNull final BigDecimal qty)
	{
		return MaterialDescriptor.builder()
				.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId))
				.date(dateDoc)
				.quantity(qty)
				.warehouseId(StepDefConstants.WAREHOUSE_ID)
				.build();
	}

	@Nullable
	public I_MD_Candidate getCandidateRecordById(@NonNull final CandidateId candidateId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.firstOnlyOrNull(I_MD_Candidate.class);
	}
}
