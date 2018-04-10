package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;

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

public class MaterialDescriptorQueryTest
{

	@Test(expected = RuntimeException.class)
	public void build_when_dateOperatorAndNoDate_then_exception()
	{
		MaterialDescriptorQuery.builder()
				.dateOperator(DateOperator.AT)
				.build();
	}

	@Test
	public void withoutQuantity()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.quantity(BigDecimal.TEN)
				.bPartnerId(BPARTNER_ID)
				.warehouseId(WAREHOUSE_ID)
				.build();

		final MaterialDescriptorQuery result = MaterialDescriptorQuery.forDescriptor(materialDescr);

		assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getBPartnerId()).isEqualTo(BPARTNER_ID);
		assertThat(result.getDateOperator()).isEqualTo(DateOperator.AT);
		assertThat(result.getDate()).isEqualTo(NOW);
	}

	@Test
	public void empty_query()
	{
		final MaterialDescriptorQuery result = MaterialDescriptorQuery.builder().build();

		assertThat(result.getProductId()).isLessThanOrEqualTo(0);
		assertThat(result.getStorageAttributesKey())
				.isSameAs(AttributesKey.ALL);
		assertThat(result.getBPartnerId()).isSameAs(AvailableToPromiseQuery.BPARTNER_ID_ANY);
	}

}
