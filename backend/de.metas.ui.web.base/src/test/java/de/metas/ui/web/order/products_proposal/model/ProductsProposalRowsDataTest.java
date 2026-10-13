package de.metas.ui.web.order.products_proposal.model;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilter;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdIntSequence;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.lang.SOTrx;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

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

/**
 * Pins the guarantee that order-line creation cannot lose a row to a filter: closing the view with DONE
 * builds the lines from {@link ProductsProposalRowsData#getAllRowsIncludingFilteredOut()}, never from the
 * filtered {@link ProductsProposalRowsData#getAllRows()}.
 *
 * <p>
 * The UI can no longer present that situation - a row with a typed quantity is exempt from the
 * delivery-history criterion, so it stays visible - which is exactly why the guarantee needs a test of
 * its own here rather than relying on an end-to-end scenario.
 */
public class ProductsProposalRowsDataTest
{
	@Test
	public void getAllRowsIncludingFilteredOut_returnsRowsTheFilterRemoved()
	{
		final ProductsProposalRow delivered = row(1, "delivered-product", 5, null);
		final ProductsProposalRow neverDelivered = row(2, "never-delivered-product", null, null);

		final ProductsProposalRowsData rowsData = rowsData(delivered, neverDelivered);

		rowsData.filter(ProductsProposalViewFilter.builder().onlyDelivered(true).build());

		assertThat(rowsData.getAllRows())
				.as("the filtered accessor must drop the row the filter removed")
				.containsExactly(delivered);

		assertThat(rowsData.getAllRowsIncludingFilteredOut())
				.as("order lines are built from this accessor, so it must keep every row")
				.containsExactly(delivered, neverDelivered);
	}

	@Test
	public void getAllRowsIncludingFilteredOut_keepsTheLoadedOrder()
	{
		final ProductsProposalRow first = row(1, "product-a", null, null);
		final ProductsProposalRow second = row(2, "product-b", null, null);

		final ProductsProposalRowsData rowsData = rowsData(first, second);

		assertThat(rowsData.getAllRowsIncludingFilteredOut()).containsExactly(first, second);
	}

	private static ProductsProposalRowsData rowsData(final ProductsProposalRow... rows)
	{
		return ProductsProposalRowsData.builder()
				.nextRowIdSequence(DocumentIdIntSequence.newInstance())
				.bpartnerId(BPartnerId.ofRepoId(1))
				.soTrx(SOTrx.SALES)
				.rows(Arrays.asList(rows))
				.build();
	}

	private static ProductsProposalRow row(
			final int id,
			final String productName,
			final Integer lastShipmentDays,
			final BigDecimal qty)
	{
		return ProductsProposalRow.builder()
				.id(DocumentId.of(id))
				.product(IntegerLookupValue.of(id, productName))
				.price(ProductProposalPrice.builder()
						.priceListPrice(Amount.of(10, CurrencyCode.EUR))
						.build())
				.qty(qty)
				.lastShipmentDays(lastShipmentDays)
				.seqNo(id)
				.build();
	}
}
