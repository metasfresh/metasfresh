package de.metas.material.event;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.DateOperator;

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
		assertThat(result.getProductId()).isLessThanOrEqualTo(0);
		assertThat(result.getAttributeSetInstanceId()).isLessThanOrEqualTo(-1);
		assertThat(result.getStorageAttributesKey())
				.isSameAs(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_UNSPECIFIED);
	}

	@Test(expected = RuntimeException.class)
	public void builderForCandidate_fail()
	{
		MaterialDescriptor.builderForCompleteDescriptor().build();
	}

	@Test
	public void builderForCandidate_succeed()
	{
		final MaterialDescriptor result = MaterialDescriptor.builderForCompleteDescriptor()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.quantity(BigDecimal.TEN)
				.warehouseId(WAREHOUSE_ID)
				.build();
		assertThat(result.isComplete()).isTrue();
		assertThat(result.getQuantity()).isEqualByComparingTo("10");
		assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getDate()).isEqualTo(NOW);
	}

	@Test
	public void withoutQuantity()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builderForCompleteDescriptor()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.quantity(BigDecimal.TEN)
				.warehouseId(WAREHOUSE_ID)
				.build();
		assertThat(materialDescr.isComplete()).isTrue(); // guard

		final MaterialDescriptor result = materialDescr.withoutQuantity();

		assertThat(result.isComplete()).as("the materialDescriptor without quantity is not complete").isFalse();
		assertThat(result.getQuantity()).isNull();
		assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getDate()).isEqualTo(NOW);
	}

	@Test(expected = RuntimeException.class)
	public void completeMaterialDescriptor_with_incomplete_productdescriptor()
	{
		final ProductDescriptor incompleteProductDescriptor = ProductDescriptor.forProductIdOnly(PRODUCT_ID);
		assertThat(incompleteProductDescriptor.isComplete()).isFalse();

		MaterialDescriptor.builderForCompleteDescriptor()
				.date(NOW)
				.productDescriptor(incompleteProductDescriptor)
				.quantity(BigDecimal.TEN)
				.warehouseId(WAREHOUSE_ID)
				.build();
	}

	@Test(expected = RuntimeException.class)
	public void build_when_dateOperatorAndNoDate_then_exception()
	{
		MaterialDescriptor
				.builderForQuery()
				.dateOperator(DateOperator.AT)
				.build();
	}
}
