package de.metas.material.event;

import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.Instant;

import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import de.metas.material.event.commons.MaterialDescriptor;

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
	public void builderForCandidate_fail()
	{
		assertThatThrownBy(() -> MaterialDescriptor.builder().build())
				.isInstanceOf(RuntimeException.class);
	}

	@Test
	public void builderForCandidate_succeed()
	{
		final MaterialDescriptor result = MaterialDescriptor.builder()
				.date(Instant.parse("2019-10-11T12:13:14.15Z"))
				.productDescriptor(createProductDescriptor())
				.quantity(new BigDecimal("10"))
				.warehouseId(WarehouseId.ofRepoId(1))
				.build();

		assertThat(result.getQuantity()).isEqualByComparingTo("10");
		assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WarehouseId.ofRepoId(1));
		assertThat(result.getDate()).isEqualTo("2019-10-11T12:13:14.15Z");
	}
}
