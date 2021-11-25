package de.metas.material.dispo.service.event.handler.pporder;

import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderLine;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
public final class PPOrderHandlerUtils
{
	public static CandidateType extractCandidateType(final PPOrderLine ppOrderLine)
	{
		return ppOrderLine.getPpOrderLineData().isReceipt() ? CandidateType.SUPPLY : CandidateType.DEMAND;
	}

	/**
	 * Creates and returns a {@link DemandDetail} for the given {@code supplyRequiredDescriptor},
	 * if the respective candidate should have one.
	 * Supply candidates that are about *another* product that the required one (i.e. co- and by-products) may not have that demand detail.
	 * (Otherwise, their stock candidate would be connected to the resp. demand record)
	 */
	@Nullable
	public static DemandDetail computeDemandDetailOrNull(
			@NonNull final CandidateType lineCandidateType,
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		if (supplyRequiredDescriptor == null)
		{
			return null;
		}

		if (lineCandidateType == CandidateType.DEMAND)
		{
			return DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor);
		}

		final MaterialDescriptor requiredMaterialDescriptor = supplyRequiredDescriptor.getMaterialDescriptor();
		if (lineCandidateType == CandidateType.SUPPLY
				&& requiredMaterialDescriptor.getProductId() == materialDescriptor.getProductId()
				&& requiredMaterialDescriptor.getStorageAttributesKey().equals(materialDescriptor.getStorageAttributesKey()))
		{
			return DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor);
		}

		return null;
	}
	
	public static MaterialDescriptorQuery createMaterialDescriptorQuery(@NonNull final ProductDescriptor productDescriptor)
	{
		return MaterialDescriptorQuery.builder()
				.productId(productDescriptor.getProductId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.build();
	}
}
