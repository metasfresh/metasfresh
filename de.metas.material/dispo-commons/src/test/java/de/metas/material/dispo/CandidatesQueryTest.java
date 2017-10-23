package de.metas.material.dispo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.CandidatesQuery.DateOperator;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.event.MaterialDescriptor;

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
				.materialDescr(MaterialDescriptor.builderForCandidateOrQuery()
						.date(date)
						.productId(20)
						.quantity(BigDecimal.TEN)
						.warehouseId(30)
						.build())
				.build();
		final CandidatesQuery query = CandidatesQuery.fromCandidate(cand);
		assertThat(query.getDateOperator()).isEqualTo(DateOperator.AT);
		assertThat(query.getDate()).isEqualTo(date);
		assertThat(query.getProductId()).isEqualTo(20);
		assertThat(query.getWarehouseId()).isEqualTo(30);
		assertThat(query.getType()).isEqualTo(Type.STOCK);
		assertThat(query.getParentId()).isEqualTo(0);
	}
}
