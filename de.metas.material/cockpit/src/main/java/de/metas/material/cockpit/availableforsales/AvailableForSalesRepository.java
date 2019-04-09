package de.metas.material.cockpit.availableforsales;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiResult.AvailableForSalesMultiResultBuilder;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult.Quantities;
import de.metas.material.cockpit.model.I_MD_Available_For_Sales_QueryResult;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-available-for-sales
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class AvailableForSalesRepository
{
	public AvailableForSalesMultiResult getBy(@NonNull final AvailableForSalesMultiQuery availableForSalesMultiQuery)
	{
		final AvailableForSalesMultiResultBuilder multiResult = AvailableForSalesMultiResult.builder();
		if(availableForSalesMultiQuery.getAvailableForSalesQueries().isEmpty())
		{
			return multiResult.build(); // empty query => empty result
		}

		final IQuery<I_MD_Available_For_Sales_QueryResult> //
		dbQuery = AvailableForSalesSqlHelper.createDBQueryForAvailableForSalesMultiQuery(availableForSalesMultiQuery);

		final List<I_MD_Available_For_Sales_QueryResult> records = dbQuery.list();

		final List<AvailableForSalesQuery> singleQueries = availableForSalesMultiQuery.getAvailableForSalesQueries();

		final ImmutableListMultimap<Integer, I_MD_Available_For_Sales_QueryResult> //
		queryNo2records = Multimaps.index(records, I_MD_Available_For_Sales_QueryResult::getQueryNo);

		for (int queryNo = 0; queryNo < singleQueries.size(); queryNo++)
		{
			BigDecimal qtyOnHandStock = ZERO;
			BigDecimal qtyToBeShipped = ZERO;

			for (final I_MD_Available_For_Sales_QueryResult recordForQueryNo : queryNo2records.get(queryNo))
			{
				qtyOnHandStock = qtyOnHandStock.add(recordForQueryNo.getQtyOnHandStock());
				qtyToBeShipped = qtyToBeShipped.add(recordForQueryNo.getQtyToBeShipped());
			}

			final AvailableForSalesQuery singleQuery = singleQueries.get(queryNo);
			final AvailableForSalesResult result = createSingleResult(qtyOnHandStock, qtyToBeShipped, singleQuery);
			multiResult.availableForSalesResult(result);
		}

		return multiResult.build();
	}

	private AvailableForSalesResult createSingleResult(
			@NonNull final BigDecimal qtyOnHandStock,
			@NonNull final BigDecimal qtyToBeShipped,
			@NonNull final AvailableForSalesQuery singleQuery)
	{
		final AvailableForSalesResult result = AvailableForSalesResult
				.builder()
				.availableForSalesQuery(singleQuery)
				.productId(singleQuery.getProductId())
				.storageAttributesKey(singleQuery.getStorageAttributesKey())
				.quantities(Quantities.builder()
						.qtyOnHandStock(qtyOnHandStock)
						.qtyToBeShipped(qtyToBeShipped).build())
				.build();
		return result;
	}
}
