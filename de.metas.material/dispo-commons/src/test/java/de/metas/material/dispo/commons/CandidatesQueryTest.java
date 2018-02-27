package de.metas.material.dispo.commons;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;

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

public class CandidatesQueryTest
{
	@Test
	public void buildEmpty()
	{
		final CandidatesQuery result = CandidatesQuery.builder().build();
		assertThat(result).isNotNull();
		assertThat(result.getParentId()).isEqualTo(CandidatesQuery.UNSPECIFIED_PARENT_ID);
		assertThat(result.getId()).isEqualTo(CandidatesQuery.UNSPECIFIED_ID);
	}

	@Test
	public void fromCandidate()
	{
		final Timestamp date = new Timestamp(NOW.getTime());

		final Candidate cand = Candidate.builder().type(CandidateType.STOCK)
				.materialDescriptor(createMaterialDescriptor().withDate(date))
				.build();
		final CandidatesQuery query = CandidatesQuery.fromCandidate(cand, false);

		assertThat(query.getMaterialDescriptorQuery().getDate()).isEqualTo(date);
		assertThat(query.getMaterialDescriptorQuery().getDateOperator()).isEqualTo(DateOperator.AT);
		assertThat(query.getMaterialDescriptorQuery().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(query.getMaterialDescriptorQuery().getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(query.getMaterialDescriptorQuery().getWarehouseId()).isEqualTo(WAREHOUSE_ID);

		assertThat(query.getType()).isEqualTo(CandidateType.STOCK);
		assertThat(query.getParentId()).isEqualTo(CandidatesQuery.UNSPECIFIED_PARENT_ID);
	}

	@Test
	public void build_when_dateAndNoDateOperator_then_useOperatorAT()
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder().date(SystemTime.asTimestamp()).build();
		final CandidatesQuery result = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.build();
		assertThat(result.getMaterialDescriptorQuery().getDateOperator()).isSameAs(DateOperator.AT);
	}
}
