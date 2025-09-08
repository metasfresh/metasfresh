package de.metas.material.dispo.commons;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQueryTest;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.organization.ClientAndOrgId;

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
		assertThat(result.getParentId()).isEqualTo(CandidateId.UNSPECIFIED);
		assertThat(result.getId()).isEqualTo(CandidateId.UNSPECIFIED);
	}

	/**
	 * Note that the material descriptor query is also unit-tested in {@link MaterialDescriptorQueryTest}
	 */
	@Test
	public void fromCandidate()
	{
		final Candidate cand = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.type(CandidateType.STOCK)
				.materialDescriptor(newMaterialDescriptor().withDate(NOW))
				.build();
		final CandidatesQuery query = CandidatesQuery.fromCandidate(cand, false);
		assertThat(query.getMaterialDescriptorQuery().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(query.getMaterialDescriptorQuery().getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(query.getMaterialDescriptorQuery().getWarehouseId()).isEqualTo(WAREHOUSE_ID);

		assertThat(query.getType()).isEqualTo(CandidateType.STOCK);
		assertThat(query.getParentId()).isEqualTo(CandidateId.UNSPECIFIED);
	}
}
