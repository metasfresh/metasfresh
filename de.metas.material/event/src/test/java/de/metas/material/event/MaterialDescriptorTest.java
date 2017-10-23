package de.metas.material.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.time.SystemTime;
import org.junit.Test;

/*
 * #%L
 * metasfresh-material-event
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

public class MaterialDescriptorTest
{

	@Test
	public void builderForQuery()
	{
		final MaterialDescriptor result = MaterialDescriptor.builderForQuery().build();
		assertThat(result.isComplete()).isFalse();
	}

	@Test(expected = RuntimeException.class)
	public void builderForCandidate_fail()
	{
		MaterialDescriptor.builderForCandidateOrQuery().build();
	}

	@Test
	public void builderForCandidate_succeed()
	{
		final Date date = SystemTime.asDate();
		final MaterialDescriptor result = MaterialDescriptor.builderForCandidateOrQuery()
				.date(date)
				.productId(10)
				.quantity(BigDecimal.TEN)
				.warehouseId(20)
				.build();
		assertThat(result.isComplete()).isTrue();
		assertThat(result.getQuantity()).isEqualByComparingTo("10");
		assertThat(result.getProductId()).isEqualTo(10);
		assertThat(result.getWarehouseId()).isEqualTo(20);
		assertThat(result.getDate()).isEqualTo(date);
	}

	@Test
	public void withouQuantity()
	{
		final Date date = SystemTime.asDate();
		final MaterialDescriptor materialDescr = MaterialDescriptor.builderForCandidateOrQuery()
				.date(date)
				.productId(10)
				.quantity(BigDecimal.TEN)
				.warehouseId(20)
				.build();
		assertThat(materialDescr.isComplete()).isTrue(); // guard
		
		final MaterialDescriptor result = materialDescr.withoutQuantity();
		assertThat(result.isComplete()).isFalse();
		assertThat(result.getQuantity()).isNull();
		assertThat(result.getProductId()).isEqualTo(10);
		assertThat(result.getWarehouseId()).isEqualTo(20);
		assertThat(result.getDate()).isEqualTo(date);
	}
}
