package de.metas.material.dispo.commons;

import static de.metas.material.event.EventTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialDescriptor.DateOperator;
import de.metas.material.event.ProductDescriptorFactory;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class RepositoryTestHelper
{
	public final Candidate stockCandidate;
	public final MaterialDescriptor materialDescriptorOfStockCandidate;

	public final Candidate laterStockCandidate;

	private final ProductDescriptorFactory productDescriptorFactory;
	
	public RepositoryTestHelper(
			@NonNull final ProductDescriptorFactory productDescriptorFactory,
			@NonNull final CandidateRepositoryCommands candidateRepositoryCommands)
	{
		this.productDescriptorFactory = productDescriptorFactory;

		materialDescriptorOfStockCandidate = MaterialDescriptor.builderForCompleteDescriptor()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("11"))
				.date(NOW)
				.build();

		stockCandidate = candidateRepositoryCommands
				.addOrUpdateOverwriteStoredSeqNo(
						Candidate.builder()
								.type(CandidateType.STOCK)
								.clientId(CLIENT_ID)
								.orgId(ORG_ID)
								.materialDescriptor(materialDescriptorOfStockCandidate)
								.build());

		final MaterialDescriptor laterMaterialDescriptor = MaterialDescriptor.builderForCompleteDescriptor()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("10"))
				.date(AFTER_NOW)
				.build();
		assertThat(laterMaterialDescriptor.isComplete()).isTrue();

		laterStockCandidate = candidateRepositoryCommands
				.addOrUpdateOverwriteStoredSeqNo(
						Candidate.builder()
								.type(CandidateType.STOCK)
								.clientId(CLIENT_ID)
								.orgId(ORG_ID)
								.materialDescriptor(laterMaterialDescriptor)
								.build());
	}

	public CandidatesQuery mkQueryForStockUntilDate(@NonNull final Date date)
	{
		return CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptor(MaterialDescriptor.builderForQuery()
						.productDescriptor(productDescriptorFactory.forProductIdOnly(PRODUCT_ID))
						.warehouseId(WAREHOUSE_ID)
						.date(date)
						.dateOperator(DateOperator.BEFORE_OR_AT)
						.build())
				.build();
	}

	public CandidatesQuery mkQueryForStockFromDate(final Date date)
	{
		return CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptor(MaterialDescriptor.builderForQuery()
						.productDescriptor(productDescriptorFactory.forProductIdOnly(PRODUCT_ID))
						.warehouseId(WAREHOUSE_ID)
						.date(date)
						.dateOperator(DateOperator.AT_OR_AFTER)
						.build())
				.build();
	}
}
