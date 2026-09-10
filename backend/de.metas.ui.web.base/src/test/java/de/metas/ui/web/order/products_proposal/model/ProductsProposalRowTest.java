package de.metas.ui.web.order.products_proposal.model;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilter;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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

public class ProductsProposalRowTest
{
	@Test
	public void isMatching_onlyDeliveredNotSet_matchesRegardlessOfLastShipmentDays()
	{
		final ProductsProposalViewFilter filter = ProductsProposalViewFilter.builder()
				.onlyDelivered(false)
				.build();

		assertThat(row(null).isMatching(filter)).isTrue();
		assertThat(row(5).isMatching(filter)).isTrue();
	}

	@Test
	public void isMatching_onlyDeliveredSet_rowWithLastShipmentDays_matches()
	{
		final ProductsProposalViewFilter filter = ProductsProposalViewFilter.builder()
				.onlyDelivered(true)
				.build();

		assertThat(row(5).isMatching(filter)).isTrue();
	}

	@Test
	public void isMatching_onlyDeliveredSet_rowWithoutLastShipmentDays_doesNotMatch()
	{
		final ProductsProposalViewFilter filter = ProductsProposalViewFilter.builder()
				.onlyDelivered(true)
				.build();

		assertThat(row(null).isMatching(filter)).isFalse();
	}

	private static ProductsProposalRow row(final Integer lastShipmentDays)
	{
		return ProductsProposalRow.builder()
				.id(DocumentId.of(1))
				.product(IntegerLookupValue.of(1, "product-1"))
				.price(ProductProposalPrice.builder()
						.priceListPrice(Amount.of(10, CurrencyCode.EUR))
						.build())
				.qty(null)
				.lastShipmentDays(lastShipmentDays)
				.seqNo(10)
				.build();
	}
}
