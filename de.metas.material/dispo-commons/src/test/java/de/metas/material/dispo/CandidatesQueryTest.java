package de.metas.material.dispo;

import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialDescriptor.DateOperator;

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
	}

	@Test
	public void fromCandidate()
	{
		final Timestamp date = SystemTime.asTimestamp();

		final Candidate cand = Candidate.builder().type(Type.STOCK)
				.materialDescr(createMaterialDescriptor().withDate(date))
				.build();
		final CandidatesQuery query = CandidatesQuery.fromCandidate(cand);
		
		assertThat(query.getMaterialDescr().getDate()).isEqualTo(date);
		assertThat(query.getMaterialDescr().getDateOperator()).isEqualTo(DateOperator.AT);
		assertThat(query.getMaterialDescr().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(query.getMaterialDescr().getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(query.getMaterialDescr().getAttributeSetInstanceId()).isEqualTo(ATTRIBUTE_SET_INSTANCE_ID);
		assertThat(query.getMaterialDescr().getWarehouseId()).isEqualTo(WAREHOUSE_ID);

		assertThat(query.getType()).isEqualTo(Type.STOCK);
		assertThat(query.getParentId()).isEqualTo(0);
	}

	@Test(expected = RuntimeException.class)
	public void build_when_dateAndNoDateOverator_then_throwsException()
	{
		CandidatesQuery.builder()
				.materialDescr(MaterialDescriptor.builderForQuery().date(SystemTime.asTimestamp()).build())
				.build();
	}
}
