package de.metas.material.dispo.commons.repository;

import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

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
	@Test
	public void withoutQuantity()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.quantity(BigDecimal.TEN)
				.customerId(BPARTNER_ID)
				.warehouseId(WAREHOUSE_ID)
				.build();

		final MaterialDescriptorQuery result = MaterialDescriptorQuery.forDescriptor(
				materialDescr,
				DateAndSeqNo.atTimeNoSeqNo(materialDescr.getDate()));

		assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(result.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(result.getCustomer()).isEqualTo(BPartnerClassifier.specific(BPARTNER_ID));
		assertThat(result.getTimeRangeStart()).isNull();
		assertThat(result.getTimeRangeEnd()).isNull();

		assertThat(result.getAtTime()).isNotNull();
		assertThat(result.getAtTime().getDate()).isEqualTo(NOW);
		assertThat(result.getAtTime().getOperator()).isNull();
	}

	@Test
	public void empty_query()
	{
		final MaterialDescriptorQuery result = MaterialDescriptorQuery.builder().build();

		assertThat(result.getProductId()).isLessThanOrEqualTo(0);
		assertThat(result.getStorageAttributesKey())
				.isSameAs(AttributesKey.ALL);
		assertThat(result.getCustomer())
				.isSameAs(BPartnerClassifier.any());
	}

}
