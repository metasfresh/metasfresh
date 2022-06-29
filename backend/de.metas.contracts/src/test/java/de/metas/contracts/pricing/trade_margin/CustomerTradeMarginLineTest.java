/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.pricing.trade_margin;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTradeMarginLineTest
{

	@Test
	void appliesTo_line_with_customer()
	{
		final CustomerTradeMarginLine marginLine = CustomerTradeMarginLine.builder()
				.customerTradeMarginId(CustomerTradeMarginId.ofRepoId(10))
				.seqNo(10)
				.active(true)
				.customerId(BPartnerId.ofRepoId(10))
				.marginPercent(25)
				.build();

		assertThat(marginLine.appliesTo(mappingCriteria(10, 20, 30))).isTrue();
		assertThat(marginLine.appliesTo(mappingCriteria(11, 20, 30))).isFalse();
	}

	@Test
	void appliesTo_line_with_product_category()
	{
		final CustomerTradeMarginLine marginLine = CustomerTradeMarginLine.builder()
				.customerTradeMarginId(CustomerTradeMarginId.ofRepoId(10))
				.seqNo(10)
				.active(true)
				.productCategoryId(ProductCategoryId.ofRepoId(30))
				.marginPercent(25)
				.build();

		assertThat(marginLine.appliesTo(mappingCriteria(10, 20, 30))).isTrue();
		assertThat(marginLine.appliesTo(mappingCriteria(11, 20, 30))).isTrue();
		assertThat(marginLine.appliesTo(mappingCriteria(11, 20, 31))).isFalse();
	}

	@Test
	void appliesTo_line_with_product()
	{
		final CustomerTradeMarginLine marginLine = CustomerTradeMarginLine.builder()
				.customerTradeMarginId(CustomerTradeMarginId.ofRepoId(10))
				.seqNo(10)
				.active(true)
				.productId(ProductId.ofRepoId(20))
				.marginPercent(25)
				.build();

		assertThat(marginLine.appliesTo(mappingCriteria(10, 20, 30))).isTrue();
		assertThat(marginLine.appliesTo(mappingCriteria(11, 21, 30))).isFalse();
		assertThat(marginLine.appliesTo(mappingCriteria(11, 20, 31))).isTrue();
	}


	@Test
	void appliesTo_line_with_all()
	{
		final CustomerTradeMarginLine marginLine = CustomerTradeMarginLine.builder()
				.customerTradeMarginId(CustomerTradeMarginId.ofRepoId(10))
				.seqNo(10)
				.active(true)
				.customerId(BPartnerId.ofRepoId(10))
				.productId(ProductId.ofRepoId(20))
				.productCategoryId(ProductCategoryId.ofRepoId(30))
				.marginPercent(25)
				.build();

		assertThat(marginLine.appliesTo(mappingCriteria(10, 20, 30))).isTrue();
		assertThat(marginLine.appliesTo(mappingCriteria(11, 20, 30))).isFalse();
		assertThat(marginLine.appliesTo(mappingCriteria(10, 21, 30))).isFalse();
		assertThat(marginLine.appliesTo(mappingCriteria(10, 20, 31))).isFalse();

	}

	private CustomerTradeMarginLine.MappingCriteria mappingCriteria(
			final int bPartnerId,
			final int productId,
			final int productCatregoryId)
	{
		return CustomerTradeMarginLine.MappingCriteria.builder()
				.customerId(BPartnerId.ofRepoId(bPartnerId))
				.productId(ProductId.ofRepoId(productId))
				.productCategoryId(ProductCategoryId.ofRepoId(productCatregoryId))
				.build();
	}
}